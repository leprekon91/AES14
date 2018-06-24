package com.data;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

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

        this.readByteArray();
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

    public String writeByteArray() {

        Path path = Paths.get("C:/solutions/" + hashCode() + ".pdf");
        try {
            Files.write(path, solvedExamByteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path.toAbsolutePath().toString();
    }

    public void readByteArray() {
        Path path = Paths.get(getPath());
        try {
            byte[] data = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word_Solved_Exam that = (Word_Solved_Exam) o;
        return Arrays.equals(getSolvedExamByteArray(), that.getSolvedExamByteArray()) &&
                Objects.equals(getPath(), that.getPath());
    }

    @Override
    public int hashCode() {

        int result = Objects.hash(getPath());
        result = 31 * result + Arrays.hashCode(getSolvedExamByteArray());
        return result;
    }
}
