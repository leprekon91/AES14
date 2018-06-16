package com.data;


public class Solved_Exam {

    /**
     * Solved exam fields
     */
    private Exam exam;
    private int studentAnswers[];
    private Student solvingStudent;
    private int examSolvingDuration;
    private Teacher examinerTeacher;

    private int examGrade; //fields designated for after the solution check
    private boolean approved;
    private String teacherGradingNotes;

    /**
     * Solved exam constructors
     */
    public Solved_Exam(Exam exam, int[] studentAnswers, Student solvingStudent, int examSolvingDuration, Teacher examinerTeacher) {
        this.exam = exam;
        this.studentAnswers = studentAnswers;
        this.solvingStudent = solvingStudent;
        this.examSolvingDuration = examSolvingDuration;
        this.examinerTeacher = examinerTeacher;
    }

    public Solved_Exam(Exam exam, int[] studentAnswers, Student solvingStudent, int examSolvingDuration,
                       Teacher examinerTeacher, int examGrade, boolean approved, String teacherGradingNotes) {
        this.exam = exam;
        this.studentAnswers = studentAnswers;
        this.solvingStudent = solvingStudent;
        this.examSolvingDuration = examSolvingDuration;
        this.examinerTeacher = examinerTeacher;
        this.setExamGrade(examGrade);
        this.setApproved(approved);
        this.setTeacherGradingNotes(teacherGradingNotes);
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
}

