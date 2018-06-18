package com.data;


import java.io.Serializable;

public class Course implements Serializable {
    /*
     * Course fields
     */
    private int courseNumber;
    private String courseName;
    private Subject courseSubject;

    /*
     * Course constructor
     */
    public Course(int courseNumber, String courseName, Subject courseSubject) {
        super();
        this.courseNumber = courseNumber;
        this.courseName = courseName.substring(0, 1).toUpperCase()
                + courseName.substring(1).toLowerCase();
        this.courseSubject = courseSubject;
    }

    /*
     * Course getters and setters
     */
    public int getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(int courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName.substring(0, 1).toUpperCase()
                + courseName.substring(1).toLowerCase();
    }

    public Subject getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(Subject courseSubject) {
        this.courseSubject = courseSubject;
    }

    @Override
    public String toString() {
        return courseSubject.getSubjectID() + "\u275A" + courseNumber +
                " - " + courseName;
    }
}