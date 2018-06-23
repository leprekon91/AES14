package com.data;


import java.util.ArrayList;
import java.util.Arrays;

public class Solved_Exam {

    /**
     * Solved exam fields
     */
    private Exam exam;
    private int studentAnswers[];
    private Student solvingStudent;
    private int examSolvingDuration;
    private Teacher examinerTeacher;
    private boolean isWordExam; //false = automatic, true = word

    private int examGrade; //fields designated for after the solution check
    private boolean approved;
    private String teacherGradingNotes;
    private boolean isCopySuspect; //false = no suspicion, true = suspected for copying

    /**
     * Solved exam constructors
     */
    public Solved_Exam(Exam exam, int[] studentAnswers, Student solvingStudent, int examSolvingDuration,
                       Teacher examinerTeacher, boolean isWordExam) {
        this.exam = exam;
        this.studentAnswers = studentAnswers;
        this.solvingStudent = solvingStudent;
        this.examSolvingDuration = examSolvingDuration;
        this.examinerTeacher = examinerTeacher;
        this.isWordExam = isWordExam;
    }

    public Solved_Exam(Exam exam, int[] studentAnswers, Student solvingStudent, int examSolvingDuration,
                       Teacher examinerTeacher, int examGrade, boolean approved, String teacherGradingNotes,
                       boolean isWordExam, boolean isCopySuspect) {
        this.exam = exam;
        this.studentAnswers = studentAnswers;
        this.solvingStudent = solvingStudent;
        this.examSolvingDuration = examSolvingDuration;
        this.examinerTeacher = examinerTeacher;
        this.setExamGrade(examGrade);
        this.setApproved(approved);
        this.setTeacherGradingNotes(teacherGradingNotes);
        this.isWordExam = isWordExam;
        this.isCopySuspect = isCopySuspect;
    }

    /**
     * Solved exam getters and setters
     */
    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public int[] getStudentAnswers() {
        return studentAnswers;
    }

    public void setStudentAnswers(int[] studentAnswers) {
        this.studentAnswers = studentAnswers;
    }

    public Student getSolvingStudent() {
        return solvingStudent;
    }

    public void setSolvingStudent(Student solvingStudent) {
        this.solvingStudent = solvingStudent;
    }

    public int getExamSolvingDuration() {
        return examSolvingDuration;
    }

    public void setExamSolvingDuration(int examSolvingDuration) {
        this.examSolvingDuration = examSolvingDuration;
    }

    public Teacher getExaminerTeacher() {
        return examinerTeacher;
    }

    public void setExaminerTeacher(Teacher examinerTeacher) {
        this.examinerTeacher = examinerTeacher;
    }

    public int getExamGrade() {
        return examGrade;
    }

    public void setExamGrade(int examGrade) {
        this.examGrade = examGrade;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getTeacherGradingNotes() {
        return teacherGradingNotes;
    }

    public void setTeacherGradingNotes(String teacherGradingNotes) {
        this.teacherGradingNotes = teacherGradingNotes;
    }

    public boolean isWordExam() {
        return isWordExam;
    }

    public void setWordExam(boolean isWordExam) {
        this.isWordExam = isWordExam;
    }

    public boolean isCopySuspect() {
        return isCopySuspect;
    }

    public void setCopySuspect(boolean isCopySuspect) {
        this.isCopySuspect = isCopySuspect;
    }

    @Override
    public String toString() {
        return "Solved_Exam{" +
                "exam=" + exam +
                "\n studentAnswers=" + Arrays.toString(studentAnswers) +
                "\n solvingStudent=" + solvingStudent +
                "\n examSolvingDuration=" + examSolvingDuration +
                "\n examinerTeacher=" + examinerTeacher +
                "\n isWordExam=" + isWordExam +
                "\n examGrade=" + examGrade +
                "\n approved=" + approved +
                "\n teacherGradingNotes='" + teacherGradingNotes + '\'' +
                "\n isCopySuspect=" + isCopySuspect +
                '}';
    }

    public static void runSolutionCalculator(ArrayList<Solved_Exam> solved_exams) {
        CheckExam((Solved_Exam[]) solved_exams.toArray());
        plagiarismSuspect((Solved_Exam[]) solved_exams.toArray());
    }

    /**
     * @param solvedExam
     */
    public static void CheckExam(Solved_Exam[] solvedExam) {
        int finalGrade = 0;
        for (int j = 0; j < solvedExam.length; j++) {
            for (int i = 0; i < solvedExam[j].getStudentAnswers().length; i++) {

                if (solvedExam[j].getExam().getExamQuestions().get(i).getCorrectAnswer() == solvedExam[j].getStudentAnswers()[i])
                    finalGrade += solvedExam[j].getExam().getExamQuestions().get(i).getGrade();

            }
            solvedExam[j].setExamGrade(finalGrade);
            finalGrade = 0;
        }
    }


    /**
     * The function checks if the student is cheating
     *
     * @param solvedExam
     */
    public static void plagiarismSuspect(Solved_Exam[] solvedExam) {
        boolean flag = true;
        for (int i = 0; i < solvedExam.length - 1; i++) {
            for (int j = i + 1; j < solvedExam.length; j++) {
                for (int k = 0; k < solvedExam[i].getStudentAnswers().length; k++) {

                    if (!(solvedExam[j].getStudentAnswers()[k] == solvedExam[i].getStudentAnswers()[k])) {
                        flag = false;
                        //break;
                    }
                }
                if ((solvedExam[i].getSolvingStudent().getUsername() != solvedExam[j].getSolvingStudent().getUsername()))
                    if ((flag) && (solvedExam[i].getExamGrade() != 100)) {

                        solvedExam[j].setCopySuspect(true);
                        solvedExam[i].setCopySuspect(true);

                    }
                flag = true;
            }


        }
    }
}

