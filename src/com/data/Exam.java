package com.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Entity Class Defining Exam Object
 */
public class Exam implements Serializable {

    /**
     * Exam fields
     */
    private int examNumber;
    private ArrayList<Question> examQuestions;
    private int[] questionGrades;
    private String teacherNotes;
    private String studentNotes;
    private Course examCourse;
    private Subject examSubject;
    private Teacher examAuthorTeacher;
    private boolean used;
    private int assignedTime;

    /**
     * Exam constructor
     */
    public Exam(ArrayList<Question> examQuestions, String teacherNotes,
                String studentNotes, Course examCourse, Subject examSubject,
                Teacher examAuthorTeacher, boolean used, int assignedTime) {
        super();
        this.examQuestions = examQuestions;
        this.teacherNotes = teacherNotes;
        this.studentNotes = studentNotes;
        this.examCourse = examCourse;
        this.examSubject = examSubject;
        this.examAuthorTeacher = examAuthorTeacher;
        this.used = used;
        this.assignedTime = assignedTime;
    }

    public Exam(int examNumber, Subject examSubject, Course examCourse) {
        this.examNumber = examNumber;
        this.examSubject = examSubject;
        this.examCourse = examCourse;
    }


    /*
     * Exam getters and setters
     */
    public int getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(int examNumber) {
        this.examNumber = examNumber;
    }

    public ArrayList<Question> getExamQuestions() {
        return examQuestions;
    }

    public void setExamQuestions(ArrayList<Question> examQuestions) {
        this.examQuestions = examQuestions;
    }

    public int[] getQuestionGrades() {
        return questionGrades;
    }

    public void setQuestionGrades(int questionGrades[]) {
        this.questionGrades = questionGrades;
    }

    public String getTeacherNotes() {
        return teacherNotes;
    }

    public void setTeacherNotes(String teacherNotes) {
        this.teacherNotes = teacherNotes;
    }

    public String getStudentNotes() {
        return studentNotes;
    }

    public void setStudentNotes(String studentNotes) {
        this.studentNotes = studentNotes;
    }

    public Course getExamCourse() {
        return examCourse;
    }

    public void setExamCourse(Course examCourse) {
        this.examCourse = examCourse;
    }

    public Subject getExamSubject() {
        return examSubject;
    }

    public void setExamSubject(Subject examSubject) {
        this.examSubject = examSubject;
    }

    public Teacher getExamAuthorTeacher() {
        return examAuthorTeacher;
    }

    public void setExamAuthorTeacher(Teacher examAuthorTeacher) {
        this.examAuthorTeacher = examAuthorTeacher;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getExamIDStr() {
        return String.format("%02d", getExamSubject().getSubjectID())
                + String.format("%02d", getExamCourse().getCourseNumber())
                + String.format("%02d", getExamNumber());
    }

    public int getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(int assignedTime) {
        this.assignedTime = assignedTime;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examNumber=" + examNumber +
                ", examQuestions=" + examQuestions +
                ", questionGrades=" + Arrays.toString(questionGrades) +
                ", teacherNotes='" + teacherNotes + '\'' +
                ", studentNotes='" + studentNotes + '\'' +
                ", examCourse=" + examCourse +
                ", examSubject=" + examSubject +
                ", examAuthorTeacher=" + examAuthorTeacher +
                ", used=" + used +
                ", assignedTime=" + assignedTime +
                '}';
    }
}

