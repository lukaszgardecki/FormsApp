package org.example.formsapp;

import com.documents4j.api.DocumentType;
import com.documents4j.job.LocalConverter;

import org.apache.poi.xwpf.usermodel.*;

import java.awt.*;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.Map;

public class FileGenerator {

    public static XWPFDocument generateDocumentFromTemplate(Map<String, String> data, String template) {
        XWPFDocument doc = null;

        try (InputStream inputStream = FileGenerator.class.getResourceAsStream(template)) {
            if (inputStream == null) throw new FileNotFoundException("Nie znaleziono szablonu: " + template);

            doc = new XWPFDocument(inputStream);

            for (XWPFParagraph p : doc.getParagraphs()) {
                replacePlaceholders(p, data);
            }

            for (XWPFTable tbl : doc.getTables()) {
                for (XWPFTableRow row : tbl.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph p : cell.getParagraphs()) {
                            replacePlaceholders(p, data);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }


    public static void saveAsPdfOnDesktop(XWPFDocument document, String fileName) {
        String desktop = System.getProperty("user.home") + File.separator + "Desktop";
        File pdfFile = new File(desktop, fileName + ".pdf");
        saveAsPdf(document, pdfFile.getAbsolutePath());
    }

    public static void printXWPFDocument(XWPFDocument document, String destFileName) {
        try {
            File docxFile = File.createTempFile(destFileName, ".docx");
            try (FileOutputStream fos = new FileOutputStream(docxFile)) {
                document.write(fos);
            }

            PrinterJob job = PrinterJob.getPrinterJob();

            if (job.printDialog()) {
                Desktop.getDesktop().print(docxFile);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveDocxOnDesktop(XWPFDocument doc, String destFileName) {
        String katalogUzytkownika = System.getProperty("user.home");
        String destFilePath = katalogUzytkownika + File.separator + "Desktop" + File.separator + destFileName + ".docx";
        File destFile = new File(destFilePath);
        saveDocument(doc, destFile);
    }

    private static void saveDocument(XWPFDocument doc, File destFile) {
        try (FileOutputStream fos = new FileOutputStream(destFile)) {
            doc.write(fos);
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku.");
        } catch (IOException e) {
            System.out.println("Błąd podczas zapisywania pliku.");
        }
    }

    private static void saveAsPdf(XWPFDocument document, String destPath) {
        try (
                ByteArrayOutputStream docxBytes = new ByteArrayOutputStream();
                OutputStream pdfOut = new FileOutputStream(destPath)
        ) {
            document.write(docxBytes);

            try (InputStream docxIn =
                         new ByteArrayInputStream(docxBytes.toByteArray())) {

                LocalConverter.builder()
                        .baseFolder(new File(System.getProperty("java.io.tmpdir")))
                        .build()
                        .convert(docxIn).as(DocumentType.DOCX)
                        .to(pdfOut).as(DocumentType.PDF)
                        .execute();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void replacePlaceholders(XWPFParagraph p, Map<String, String> data) {
        for (XWPFRun run : p.getRuns()) {
            String text = run.getText(0);
            if (text != null) {
                for (Map.Entry<String, String> entry : data.entrySet()) {
                    String placeholder = "${" + entry.getKey() + "}";
                    if (text.contains(placeholder)) {
                        text = text.replace(placeholder, entry.getValue());
                        run.setText(text, 0);
                    }
                }
            }
        }
    }
}
