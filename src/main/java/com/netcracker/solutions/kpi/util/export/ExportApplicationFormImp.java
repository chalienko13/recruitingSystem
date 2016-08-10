package com.netcracker.solutions.kpi.util.export;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.netcracker.solutions.kpi.config.PropertiesReader;
import com.netcracker.solutions.kpi.persistence.model.*;
import com.netcracker.solutions.kpi.persistence.model.enums.FormQuestionTypeEnum;
import com.netcracker.solutions.kpi.service.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.List;

@Service
public class ExportApplicationFormImp implements ExportApplicationForm {
    private final static int FONT_SIZE_BIG = 20;
    private final static int FONT_SIZE_MIDDLE = 13;
    private final static String PHOTO_PATH = "photodir.path";
    private final static char CHECKED = '\u00FE';
    private final static char UNCHECKED = '\u00A8';
    private static Logger log = LoggerFactory.getLogger(ExportApplicationFormImp.class.getName());
    private Font fontCheckboxes;
    private Font fontBig;
    private Font fontMiddle;

    @Autowired
    private UserService userService;// = ServiceFactory.getUserService();
    @Autowired
    private FormAnswerService formAnswerService;// = ServiceFactory.getFormAnswerService();

    @Autowired
    private PropertiesReader propertiesReader;

    public ExportApplicationFormImp() throws IOException, DocumentException {
        //Initializing fonts
        fontBig = new Font(Font.FontFamily.TIMES_ROMAN, FONT_SIZE_BIG, Font.BOLD);
        fontMiddle = new Font(Font.FontFamily.TIMES_ROMAN, FONT_SIZE_MIDDLE, Font.BOLD);

        fontCheckboxes = new Font(Font.FontFamily.TIMES_ROMAN, 16f, Font.BOLD);
    }

    @Override
    public void export(ApplicationForm applicationForm, HttpServletResponse response) throws IOException, DocumentException {
        //getting output stream and creating document
        User user = userService.getUserByID(applicationForm.getUser().getId());

        Document document = new Document(PageSize.A4, 50, 50, 50, 50);
        PdfWriter writer = PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        //writing ID and name of Student
        String appFormId = "# " + applicationForm.getId();
        document.add(new Paragraph(appFormId, fontBig));
        document.add(new Paragraph(user.getLastName() + " " + user.getFirstName() + " " + user.getSecondName(), fontBig));
        LineSeparator line2 = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -10);
        document.add(line2);
        document.add(new Paragraph("  "));

        //writing basic information of student
        List<String> selectInputQuestions = new ArrayList<>();
        List<String> selectInputAnswers = new ArrayList<>();
        for (FormQuestion formQuestion : applicationForm.getQuestions()) {
            setSelectInputAnswersAndQuestions(formQuestion, selectInputQuestions, selectInputAnswers, applicationForm);
        }
        PdfPTable table = new PdfPTable(3);
        insertBaseStudentData(table, user, selectInputQuestions, selectInputAnswers);

        // Inserting Image in document
        String photoDirPath = propertiesReader.propertiesReader(PHOTO_PATH);
        URL url1 = new File(photoDirPath + applicationForm.getPhotoScope()).toURI().toURL();
        insertImage(table, url1);
        // Image inserted

        table.setHorizontalAlignment(Element.ALIGN_LEFT);
        table.setWidthPercentage(100);
        document.add(table);
        LineSeparator line0 = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -10);
        document.add(line0);
        document.add(Chunk.NEWLINE);

        //inserting Answers and questions
        insertRadioCheckboxAnswersAndQuestions(applicationForm, document);
        insertTextAreaAnswersAndQuestions(applicationForm, document);

        document.close();
        writer.close();
    }

    private void insertBaseStudentData(PdfPTable baseTable, User user,
                                       List<String> selectInputQuestions, List<String> selectInputAnswers) {
        // Left table
        PdfPTable innerTable1 = new PdfPTable(1);
        PdfPCell cell = new PdfPCell();
        cell.setBorder(PdfPCell.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setMinimumHeight(20);
        cell.setPhrase(new Phrase("Email:"));
        innerTable1.addCell(cell);
        for (String string : selectInputQuestions) {
            cell.setPhrase(new Phrase(string + ":"));
            innerTable1.addCell(cell);
        }
        PdfPCell cellTableLeft = new PdfPCell(innerTable1);
        cellTableLeft.setBorder(PdfPCell.NO_BORDER);
        baseTable.addCell(cellTableLeft);
        // Right table
        PdfPTable innerTable2 = new PdfPTable(1);
        cell.setPhrase(new Phrase(user.getLastName()));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(user.getEmail()));
        innerTable2.addCell(cell);
        for (String string : selectInputAnswers) {
            cell.setPhrase(new Phrase(string));
            innerTable2.addCell(cell);
        }
        PdfPCell cellTableRight = new PdfPCell(innerTable2);
        cellTableRight.setBorder(PdfPCell.NO_BORDER);
        baseTable.addCell(cellTableRight);
    }

    private void setSelectInputAnswersAndQuestions(FormQuestion formQuestion, List<String> selectRadioQuestions,
                                                   List<String> selectRadioAnswers, ApplicationForm applicationForm) {

        if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.INPUT.getTitle())) {
            for (FormAnswer answer : formAnswerService.getByApplicationFormAndQuestion(applicationForm, formQuestion)) {
                selectRadioQuestions.add(formQuestion.getTitle());
                selectRadioAnswers.add(answer.getAnswer());
            }
        }
        if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.SELECT.getTitle())
                ) {
            for (FormAnswerVariant formAnswerVariant : formQuestion.getFormAnswerVariants()) {
                for (FormAnswer answer : formAnswerService.getByApplicationFormAndQuestion(applicationForm, formQuestion)) {
                    if (String.valueOf(formAnswerVariant.getAnswer()).equals(String.valueOf(
                            answer.getFormAnswerVariant().getAnswer()))) {
                        selectRadioQuestions.add(formQuestion.getTitle());
                        selectRadioAnswers.add(answer.getFormAnswerVariant().getAnswer());
                    }
                }
            }
        }

    }

    private void insertRadioCheckboxAnswersAndQuestions(ApplicationForm applicationForm, Document document) throws DocumentException {
        try {
            for (FormQuestion formQuestion : applicationForm.getQuestions()) {
                Paragraph paragraph = new Paragraph();
                document.add(paragraph);

                if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.CHECKBOX.getTitle())
                        || formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.RADIO.getTitle())) {
                    PdfPTable qaTable = new PdfPTable(2);
                    qaTable.setWidthPercentage(90);
                    PdfPTable qTable = new PdfPTable(1);
                    PdfPTable aTable = new PdfPTable(1);
                    PdfPCell cell = new PdfPCell();
                    cell.setBorder(PdfPCell.NO_BORDER);
                    cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPhrase(new Phrase(formQuestion.getTitle(), fontMiddle));
                    qTable.addCell(cell);
                    PdfPCell cellTableLeft = new PdfPCell(qTable);
                    cellTableLeft.setBorder(PdfPCell.NO_BORDER);
                    qaTable.addCell(cellTableLeft);

                    Phrase phrase = new Phrase(formQuestion.getTitle(), fontMiddle);
                    paragraph.add(phrase);
                    for (FormAnswerVariant formAnswerVariant : formQuestion.getFormAnswerVariants()) {
                        Paragraph paragraph1 = new Paragraph();
                        boolean flag = false;
                        for (FormAnswer answer : formAnswerService.getByApplicationFormAndQuestion(applicationForm,
                                formQuestion)) {
                            if (String.valueOf(formAnswerVariant.getAnswer()).equals(String.valueOf(
                                    answer.getFormAnswerVariant().getAnswer()))) {
                                flag = true;
                            }
                        }
                        if (flag) {
                            Phrase phraseCheck = new Phrase(String.valueOf(CHECKED), fontCheckboxes);
                            paragraph1.add(phraseCheck);
                        } else {
                            Phrase phraseUnCheck = new Phrase(String.valueOf(UNCHECKED), fontCheckboxes);
                            paragraph1.add(phraseUnCheck);
                        }
                        Phrase variant = new Phrase(formAnswerVariant.getAnswer());
                        paragraph1.add(variant);
                        cell.setPhrase(paragraph1);
                        aTable.addCell(cell);
                    }
                    PdfPCell cellTableRight = new PdfPCell(aTable);
                    cellTableRight.setBorder(PdfPCell.NO_BORDER);
                    qaTable.addCell(cellTableRight);
                    document.add(qaTable);
                    LineSeparator line2 = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -10);
                    document.add(line2);
                    document.add(Chunk.NEWLINE);
                }
            }
        } catch (DocumentException e) {
            log.error("Error while inserting Radio and Checkbox Answers And Questions {}", e);
            throw new DocumentException("Cannot Radio and Checkbox Answers And Questions");
        }

    }

    private void insertTextAreaAnswersAndQuestions(ApplicationForm applicationForm, Document document) throws DocumentException {
        try {
            for (FormQuestion formQuestion : applicationForm.getQuestions()) {
                if (formQuestion.getQuestionType().getTypeTitle().equals(FormQuestionTypeEnum.TEXTAREA.getTitle())) {
                    for (FormAnswer answer : formAnswerService.getByApplicationFormAndQuestion(applicationForm, formQuestion)) {
                        Paragraph paragraph = new Paragraph(formQuestion.getTitle(), fontMiddle);
                        paragraph.setAlignment(Element.ALIGN_CENTER);

                        document.add(paragraph);

                        Paragraph paragraph1 = new Paragraph(answer.getAnswer());
                        document.add(paragraph1);
                        LineSeparator line2 = new LineSeparator(1, 100, null, Element.ALIGN_CENTER, -10);
                        document.add(line2);
                    }
                }
            }
        } catch (DocumentException e) {
            log.error("Error while inserting TextArea Answers And Questions {}", e);
            throw new DocumentException("Cannot insert TextArea Answers And Questions");
        }
    }

    private void insertImage(PdfPTable table, URL url) throws IOException, BadElementException {
        try {
            PdfPCell cellImg;
            cellImg = new PdfPCell(getImage(url));
            cellImg.setBorder(PdfPCell.NO_BORDER);
            cellImg.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cellImg);
        } catch (IOException e) {
            log.error("Error while inserting Student Image in Application Form {}", e);
            throw new IOException();
        } catch (BadElementException e) {
            log.error("Error while inserting Student Image in Application Form {}", e);
            throw new BadElementException("Inserting Student Image Error");
        }

    }

    private Image getImage(URL url) throws IOException, BadElementException {
        Image image;
        image = Image.getInstance(url);
        image.setAlignment(Image.ALIGN_RIGHT | Image.TEXTWRAP);
        image.scaleAbsolute(105, 140); //scale of image, must be 3:4
        return image;
    }
}
