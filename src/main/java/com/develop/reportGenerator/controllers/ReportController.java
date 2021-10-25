package com.develop.reportGenerator.controllers;

import com.develop.reportGenerator.request.Content;
import com.develop.reportGenerator.request.RepeatPage;
import com.develop.reportGenerator.request.Report;
import com.develop.reportGenerator.services.TemplateService;
import com.develop.reportGenerator.utils.ReportUtil;
import com.google.gson.Gson;
import org.apache.log4j.BasicConfigurator;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.org.apache.poi.util.IOUtils;
import org.docx4j.wml.ObjectFactory;
import org.docx4j.wml.P;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.DocxStamperConfiguration;
import org.wickedsource.docxstamper.replace.typeresolver.image.Image;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.develop.reportGenerator.utils.ReportUtil.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class ReportController {

    TemplateService templateService = new TemplateService();

    @PostMapping(value = "/makeReport", produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
    public @ResponseBody byte[] makeReport(@RequestBody String requestReport, @RequestParam("templateId") int templateId){
        long time = System.currentTimeMillis();
        Report report = new Gson().fromJson(requestReport, Report.class);
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        File outputFile = new File("reports/report-"+ report.getPrjTitle()
                +"-"+ dateFormat.format(new Date())+".docx");
        BasicConfigurator.configure();
        ReportUtil.setImagesToReport(report);
        InputStream templateInputStream;
        OutputStream documentOutputStream = null;
        WordprocessingMLPackage document = null;
        try {
            if(templateService.findTemplate((long) templateId).isPresent()) {
                templateInputStream = new ByteArrayInputStream(templateService.findTemplate((long) templateId).get().getFile());
                documentOutputStream = new FileOutputStream(outputFile);
                document = WordprocessingMLPackage.load(templateInputStream);
            }
            else {
                throw new ResponseStatusException(NOT_FOUND, "Unable to find template");
            }

        } catch (Docx4JException | IOException e) {
            e.printStackTrace();
        }
        int contentCounter = 0;
        ObjectFactory objectFactory = new ObjectFactory();
        for(RepeatPage repeatPage : report.getRepeatPage()) {
            for (Content content : repeatPage.getContent()) {
                contentCounter++;
                document.getMainDocumentPart().addObject(makeTitleParagraph(repeatPage.getType() + " " +
                        content.getText() , objectFactory));
                document.getMainDocumentPart().addObject(makeSpacerParagraph(objectFactory));
                P imagesParagraph = objectFactory.createP();
                for(Image image: content.getImages()){
                    try {
                        addImageToPara(document, objectFactory, imagesParagraph, image.getImageBytes());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                imagesParagraph = getFormatParagraph(objectFactory, imagesParagraph);
                document.getMainDocumentPart().addObject(imagesParagraph);
                if(contentCounter != report.getRepeatPage().size() - 1) {
                    document.getMainDocumentPart().addObject(makeSpacerParagraph(objectFactory));
                }
            }
        }
        DocxStamper stamper = new DocxStamperConfiguration().build();
        stamper.stamp(document, report, documentOutputStream);
        FileInputStream documentInputStream;
        byte[] outputDocumentArray = null;
        try {
            documentOutputStream.close();
            documentInputStream = new FileInputStream(outputFile);
            outputDocumentArray = IOUtils.toByteArray(documentInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Elapsed time(milliseconds): "+ (System.currentTimeMillis() - time));
        return outputDocumentArray;
    }

}
