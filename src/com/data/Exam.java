package com.data;

import java.util.ArrayList;

/**
 *  Entity Class Defining Exam Object
 */
public class Exam {

    /*
     * Exam fields
     */
    private String examNumber;
    private ArrayList<Question> examQuestions;
    private String teacherNotes;
    private String studentNotes;
    private Course examCourse;
    private Subject examSubject;
    private Teacher examAuthorTeacher;

    /*
     * Exam constructor
     */
    public Exam(String examNumber, ArrayList<Question> examQuestions, String teacherNotes, String studentNotes,
                Course examCourse, Subject examSubject, Teacher examAuthorTeacher) {
        super();
        this.examNumber = examNumber;
        this.examQuestions = examQuestions;
        this.teacherNotes = teacherNotes;
        this.studentNotes = studentNotes;
        this.examCourse = examCourse;
        this.examSubject = examSubject;
        this.examAuthorTeacher = examAuthorTeacher;
    }

    /*
     * Exam getters and setters
     */
    public String getExamNumber() {
        return examNumber;
    }

    public void setExamNumber(String examNumber) {
        this.examNumber = examNumber;
    }

    public ArrayList<Question> getExamQuestions() {
        return examQuestions;
    }

    public void setExamQuestions(ArrayList<Question> examQuestions) {
        this.examQuestions = examQuestions;
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

}


