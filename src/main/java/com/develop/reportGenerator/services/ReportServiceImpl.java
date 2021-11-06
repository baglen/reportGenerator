package com.develop.reportGenerator.services;

import com.develop.reportGenerator.exceptions.ReportException;
import com.develop.reportGenerator.exceptions.TemplateNotFoundException;
import com.develop.reportGenerator.models.Template;
import com.develop.reportGenerator.repositories.TemplateRepository;
import com.develop.reportGenerator.request.Content;
import com.develop.reportGenerator.request.RepeatPage;
import com.develop.reportGenerator.request.Report;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.org.apache.poi.util.IOUtils;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.springframework.stereotype.Service;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;
import org.wickedsource.docxstamper.replace.typeresolver.image.Image;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.develop.reportGenerator.utils.ReportUtil.*;


@Service
public class ReportServiceImpl implements ReportService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ReportServiceImpl.class);

    final TemplateRepository templateRepository;

    public ReportServiceImpl(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    @Override
    public byte[] makeReport(Report requestReport, Long templateId) {
        long time = System.currentTimeMillis();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        File outputFile = new File("reports/report-" + requestReport.getPrjTitle()
                + "-" + dateFormat.format(new Date()) + ".docx");
        setImagesToReport(requestReport);
        InputStream templateInputStream;
        OutputStream documentOutputStream = null;
        WordprocessingMLPackage document;
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new TemplateNotFoundException(templateId));
        templateInputStream = new ByteArrayInputStream(template.getFile());
        try {
            document = WordprocessingMLPackage.load(templateInputStream);
            documentOutputStream = new FileOutputStream(outputFile);
        } catch (FileNotFoundException e) {
            log.error("Output file not found", e);
            throw new ReportException("Output file not found");
        } catch (Docx4JException e) {
            log.error("Error occurred while loading template", e);
            throw new ReportException("Error occurred while loading template");
        }
        int contentCounter = 0;
        ObjectFactory objectFactory = new ObjectFactory();
        for (RepeatPage repeatPage : requestReport.getRepeatPage()) {
            for (Content content : repeatPage.getContent()) {
                contentCounter++;
                document.getMainDocumentPart().addObject(makeTitleParagraph(repeatPage.getType() + " " +
                        content.getText(), objectFactory));
                document.getMainDocumentPart().addObject(makeSpacerParagraph(objectFactory));
                P imagesParagraph = objectFactory.createP();
                for (Image image : content.getImages()) {
                    try {
                        addImageToPara(document, objectFactory, imagesParagraph, image.getImageBytes());
                    } catch (Exception e) {
                        log.error("Error occurred while attaching images", e);
                        throw new ReportException("Error occurred while attaching images");
                    }
                }
                imagesParagraph = getFormatParagraph(objectFactory, imagesParagraph);
                document.getMainDocumentPart().addObject(imagesParagraph);
                if (contentCounter != requestReport.getRepeatPage().size() - 1) {
                    document.getMainDocumentPart().addObject(makeSpacerParagraph(objectFactory));
                }
            }
        }
        DocxStamper stamper = new DocxStamperConfiguration().build();
        stamper.stamp(document, requestReport, documentOutputStream);
        FileInputStream documentInputStream;
        byte[] outputDocumentArray;
        try {
            documentOutputStream.close();
            documentInputStream = new FileInputStream(outputFile);
            outputDocumentArray = IOUtils.toByteArray(documentInputStream);
        } catch (FileNotFoundException e) {
            log.error("Report file not found", e);
            throw new ReportException("Report file not found");
        } catch (IOException e) {
            log.error("Error occurred while converting report", e);
            throw new ReportException("Error occurred while converting report");
        }
        log.info("Elapsed time(milliseconds): " + (System.currentTimeMillis() - time));
        return outputDocumentArray;
    }
}
