package com.data;


import java.util.ArrayList;

public class Student extends User {
    /**
     * student entity additional fields
     */
    private ArrayList<Exam> studentExamsList;
    private ArrayList<Subject> studentSubjectList;
    private ArrayList<Course> studentCourseList;

    /**
     * student entity constructors
     */
    public Student(User user) {
        super(user.getUsername(), user.getPass());
        this.setFirst_name(user.getFirst_name());
        this.setLast_name(user.getLast_name());
        this.setType(user.getType());
    }

    public Student(User user,
                   ArrayList<Exam> studentExamsList,
                   ArrayList<Subject> studentSubjectList,
                   ArrayList<Course> studentCourseList) {
        super(user.getUsername(), user.getPass());
        this.setFirst_name(user.getFirst_name());
        this.setLast_name(user.getLast_name());
        this.setType(user.getType());
        this.studentExamsList = studentExamsList;
        this.studentSubjectList = studentSubjectList;
        this.studentCourseList = studentCourseList;
    }

    /**
     * student entity getters and setters
     */
    public ArrayList<Exam> getStudentExamsList() {
        return studentExamsList;
    }

    public void setStudentExamsList(ArrayList<Exam> studentExamsList) {
        this.studentExamsList = studentExamsList;
    }

    public ArrayList<Subject> getStudentSubjectList() {
        return studentSubjectList;
    }

    public void setStudentSubjectList(ArrayList<Subject> studentSubjectList) {
        this.studentSubjectList = studentSubjectList;
    }

    public ArrayList<Course> getStudentCourseList() {
        return studentCourseList;
    }

    public void setStudentCourseList(ArrayList<Course> studentCourseList) {
        this.studentCourseList = studentCourseList;
    }
}
