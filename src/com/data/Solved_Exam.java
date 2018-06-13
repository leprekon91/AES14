package com.data;


public class Solved_Exam {

    /**
     * Solved exam fields
     */
    private Exam exam;
    private int answers[];
    private Student solvingStudent;
    private int examDuration;
    private Teacher examinerTeacher;

    /**
     * Solved exam constructor
     */
    public Solved_Exam(Exam exam, int[] answers, Student solvingStudent, int examDuration) {
        super();
        this.exam = exam;
        this.answers = answers;
        this.solvingStudent = solvingStudent;
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

    public int[] getAnswers() {
        return answers;
    }

    public void setAnswers(int[] answers) {
        this.answers = answers;
    }

    public Student getSolvingStudent() {
        return solvingStudent;
    }

    public void setSolvingStudent(Student solvingStudent) {
        this.solvingStudent = solvingStudent;
    }

    public int getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(int examDuration) {
        this.examDuration = examDuration;
    }

    public Teacher getExaminerTeacher() {
        return examinerTeacher;
    }

    public void setExaminerTeacher(Teacher examinerTeacher) {
        this.examinerTeacher = examinerTeacher;
    }
}

