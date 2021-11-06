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
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.springframework.stereotype.Service;
import org.wickedsource.docxstamper.replace.typeresolver.image.Image;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

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
        setImagesToReport(requestReport);
        WordprocessingMLPackage document;
        Template template = templateRepository.findById(templateId)
                .orElseThrow(() -> new TemplateNotFoundException(templateId));
        try {
            document = WordprocessingMLPackage.load(new ByteArrayInputStream(template.getFile()));
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
        ByteArrayOutputStream file = new ByteArrayOutputStream();
        try {
            document.save(file);
        } catch (Docx4JException e) {
            log.error("Error occurred while saving report", e);
            throw new ReportException("Error occurred while saving report");
        }
        log.info("Elapsed time(milliseconds): " + (System.currentTimeMillis() - time));
        return file.toByteArray();
    }
}
