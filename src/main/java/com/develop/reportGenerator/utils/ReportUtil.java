package com.develop.reportGenerator.utils;

import com.develop.reportGenerator.request.Content;
import com.develop.reportGenerator.request.RepeatPage;
import com.develop.reportGenerator.request.Report;
import org.docx4j.dml.wordprocessingDrawing.Inline;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.BinaryPartAbstractImage;
import org.docx4j.wml.*;
import org.wickedsource.docxstamper.replace.typeresolver.image.Image;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ReportUtil {

    public static P getFormatParagraph(ObjectFactory objectFactory, P paragraph) {
        PPr paragraphProperties = objectFactory.createPPr();
        Jc justification = objectFactory.createJc();
        justification.setVal(JcEnumeration.CENTER);
        paragraphProperties.setJc(justification);
        paragraph.setPPr(paragraphProperties);
        return paragraph;
    }

    public static void addImageToPara(WordprocessingMLPackage wordMLPackage, ObjectFactory objectFactory, P paragraph,
                                      byte[] bytes) throws Exception {
        R run = objectFactory.createR();
        BinaryPartAbstractImage imagePart = BinaryPartAbstractImage.createImagePart(wordMLPackage, bytes);
        Inline inline = imagePart.createImageInline(null, null, 1, 2, 4550L, false);
        Drawing drawing = objectFactory.createDrawing();
        drawing.getAnchorOrInline().add(inline);
        run.getContent().add(drawing);
        R spacerRun = objectFactory.createR();
        Text spacer = objectFactory.createText();
        spacer.setValue("   ");
        spacer.setSpace("preserve");
        spacerRun.getContent().add(spacer);
        paragraph.getContent().add(run);
        paragraph.getContent().add(spacerRun);
    }

    public static void setImagesToReport(Report report) {
        for (RepeatPage repeatPage : report.getRepeatPage()) {
            for (Content content : repeatPage.getContent()) {
                List<Image> contentImages = new ArrayList<>();
                for (String imagePath : content.getImage()) {
                    try {
                        contentImages.add(new Image(new FileInputStream(imagePath)));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                content.setImages(contentImages);
            }
        }
    }

    public static P makeTitleParagraph(String text, ObjectFactory objectFactory) {
        P titleParagraph = objectFactory.createP();
        R run = objectFactory.createR();
        RPr rpr = objectFactory.createRPr();
        RFonts font = objectFactory.createRFonts();
        HpsMeasure fontSize = objectFactory.createHpsMeasure();
        font.setAscii("Tahoma");
        font.setHAnsi("Tahoma");
        fontSize.setVal(BigInteger.valueOf(28));
        rpr.setRFonts(font);
        rpr.setSz(fontSize);
        Text title = objectFactory.createText();
        title.setValue(text);
        title.setSpace("preserve");
        run.setRPr(rpr);
        run.getContent().add(title);
        titleParagraph.getContent().add(run);
        return titleParagraph;
    }

    public static P makeSpacerParagraph(ObjectFactory objectFactory) {
        R spacerRun = objectFactory.createR();
        Text spacer = objectFactory.createText();
        spacer.setValue("   ");
        spacer.setSpace("preserve");
        spacerRun.getContent().add(spacer);
        P spacerParagraph = objectFactory.createP();
        spacerParagraph.getContent().add(spacerRun);
        return spacerParagraph;
    }
}
