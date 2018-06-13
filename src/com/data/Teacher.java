package com.data;

import java.util.ArrayList;

public class Teacher extends User {
    /**
     * teacher entity additional fields
     */
    private ArrayList<Exam> teacherExamsList;
    private ArrayList<Subject> teacherSubjectList;

    /**
     * teacher entity constructors
     */
    public Teacher(User user) {
        super(user.getUsername(), user.getPass());
        this.setFirst_name(user.getFirst_name());
        this.setLast_name(user.getLast_name());
        this.setType(user.getType());
    }

    public Teacher(User user,
                   ArrayList<Exam> teacherExamsList,
                   ArrayList<Subject> teacherSubjectList) {
        super(user.getUsername(), user.getPass());
        this.setFirst_name(user.getFirst_name());
        this.setLast_name(user.getLast_name());
        this.setType(user.getType());
        this.teacherExamsList = teacherExamsList;
        this.teacherSubjectList = teacherSubjectList;
    }

    /**
     * teacher entity getters and setters
     */
    public ArrayList<Exam> getTeacherExamsList() {
        return teacherExamsList;
    }

    public void setTeacherExamsList(ArrayList<Exam> teacherExamsList) {
        this.teacherExamsList = teacherExamsList;
    }

    public ArrayList<Subject> getTeacherSubjectList() {
        return teacherSubjectList;
    }

    public void setTeacherSubjectList(ArrayList<Subject> teacherSubjectList) {
        this.teacherSubjectList = teacherSubjectList;
    }

}