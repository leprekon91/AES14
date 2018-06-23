package com;

import com.data.Exam;
import com.data.Question;
import com.data.Teacher;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class WordDocument
        extends XWPFDocument {
    public void addTitle(String titleStr, String colorHex, String font) {
        XWPFParagraph title = createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun titleRun = title.createRun();
        titleRun.setText(titleStr);
        titleRun.setColor(colorHex);
        titleRun.setBold(true);
        titleRun.setFontFamily(font);
        titleRun.setFontSize(20);
        titleRun.setUnderline(UnderlinePatterns.DASHED_HEAVY);
    }

    public void addSubTitle(String subTitleStr, String colorHex, String font) {
        XWPFParagraph subTitle = createParagraph();
        subTitle.setAlignment(ParagraphAlignment.LEFT);

        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText(subTitleStr);
        subTitleRun.setColor(colorHex);
        subTitleRun.setBold(true);
        subTitleRun.setFontFamily(font);
        subTitleRun.setFontSize(12);
        subTitleRun.setTextPosition(20);
    }

    public void addImage(String imageLocation, int width, int height) {
        XWPFParagraph image = createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(0);
        try {
            Path imagePath = Paths.get(ClassLoader.getSystemResource(imageLocation).toURI());
            imageRun.addPicture(Files.newInputStream(imagePath), 5,
                    imagePath.getFileName().toString(), Units.toEMU(width), Units.toEMU(height));
        } catch (URISyntaxException | InvalidFormatException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addParagraph(String text) {
        XWPFParagraph para = createParagraph();
        para.setAlignment(ParagraphAlignment.BOTH);
        XWPFRun paraRun = para.createRun();
        paraRun.setFontSize(10);
        paraRun.setText(text);
    }

    public void writeDocument(String location) {
        try {
            FileOutputStream out = new FileOutputStream(location);
            write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createExamDoc(Exam exam, Teacher examineeTeacher, String fileLocation, String examSemesterDate)
            throws IOException {
        WordDocument wd = new WordDocument();

        String title = exam.getExamCourse().getCourseName() +
                " Exam (" + exam.getExamIDStr() + ")" + " - " + examSemesterDate;
        String Teacher = examineeTeacher.getFullName();
        String studentNotes = exam.getStudentNotes();


        wd.addTitle(title, "FF1010", null);
        wd.addSubTitle("Course Teacher:  ", "000000", null);
        wd.addParagraph(Teacher);
        wd.addSubTitle("Notes:  ", "000000", null);
        wd.addParagraph(studentNotes);
        int qnum = 1;
        for (Question q : exam.getExamQuestions()) {
            wd.addSubTitle("(Question #" + qnum + ")" + " - [" + exam.getQuestionGrades()[qnum - 1] + "points]", "000000", null);
            wd.addSubTitle(" > " + q.getQuestionText(), "000000", null);
            wd.addParagraph("❶ " + q.getPossibleAnswers()[0]);
            wd.addParagraph("❷ " + q.getPossibleAnswers()[1]);
            wd.addParagraph("❸ " + q.getPossibleAnswers()[2]);
            wd.addParagraph("❹ " + q.getPossibleAnswers()[3]);
            qnum++;
        }
        wd.addTitle("Good Luck!", "FF1010", "Times New Roman");
        wd.writeDocument(fileLocation);
        wd.close();
    }
}