package com.data;

import java.io.File;

public class Word_Solved_Exam extends Solved_Exam {
    /**
     * Word solved exam additional fields
     */
    private File solvedExamFile;

    /**
     * Solved exam constructors
     */
    public Word_Solved_Exam(Exam exam, Student solvingStudent, int examSolvingDuration,
                            Teacher examinerTeacher, int examGrade, boolean approved, String teacherGradingNotes,
                            boolean isWordExam, boolean isCopySuspect, File solvedExamFile) {
        super(exam, null, solvingStudent, examSolvingDuration, examinerTeacher, examGrade, approved,
                teacherGradingNotes, isWordExam, isCopySuspect);
        this.solvedExamFile = solvedExamFile;
    }

    public Word_Solved_Exam(Exam exam, Student solvingStudent, int examSolvingDuration,
                            Teacher examinerTeacher, int examGrade, boolean approved, String teacherGradingNotes,
                            boolean isWordExam, boolean isCopySuspect, String fileLocation) {
        super(exam, null, solvingStudent, examSolvingDuration, examinerTeacher, examGrade, approved,
                teacherGradingNotes, isWordExam, isCopySuspect);

        File file = new File(fileLocation);
        if (file.exists()) System.out.println("Success!");  //delete after check
        else System.out.println("Error, the file doesn't exist on the server."); //delete after check
    }

    /**
     * Solved word exam getters and setters
     */
    public File getSolvedExamFile() {
        return solvedExamFile;
    }

    public void setsolvedExamFile(File solvedExamFile) {
        this.solvedExamFile = solvedExamFile;
    }

}
