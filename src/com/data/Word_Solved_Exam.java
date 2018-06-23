package com.data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;

public class Word_Solved_Exam extends Solved_Exam implements Serializable {

    /**
     * Word solved exam additional fields
     */
    private byte[] solvedExamByteArray;
    private String path;

    /**
     * Solved exam constructors
     */
    public Word_Solved_Exam(
            Exam exam,
            Student solvingStudent,
            int examSolvingDuration,
            Teacher examinerTeacher,
            int examGrade,
            boolean approved,
            String teacherGradingNotes,
            boolean isWordExam,
            boolean isCopySuspect,
            File solvedExamFile
    ) {

        super(exam, null, solvingStudent, examSolvingDuration, examinerTeacher, examGrade, approved,
                teacherGradingNotes, isWordExam, isCopySuspect);


        try {
            this.path = solvedExamFile.getPath();
            this.solvedExamByteArray = Files.readAllBytes(solvedExamFile.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Word_Solved_Exam(Exam exam, Student solvingStudent, int examSolvingDuration,
                            Teacher examinerTeacher, int examGrade, boolean approved, String teacherGradingNotes,
                            boolean isWordExam, boolean isCopySuspect, String fileLocation) {
        super(exam, null, solvingStudent, examSolvingDuration, examinerTeacher, examGrade, approved,
                teacherGradingNotes, isWordExam, isCopySuspect);
        this.path = fileLocation;
        File file = new File(fileLocation);
        try {
            this.solvedExamByteArray = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*
     * Solved word exam getters and setters
     */
    public byte[] getSolvedExamByteArray() {
        return solvedExamByteArray;
    }

    public void setSolvedExamByteArray(byte[] solvedExamByteArray) {
        this.solvedExamByteArray = solvedExamByteArray;
    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
