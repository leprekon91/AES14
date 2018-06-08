package com.data;


public class Course {
    /*
     * Course fields
     */
    private String courseNumber;
    private String courseName;
    private Subject courseSubject;

    /*
     * Course constructor
     */
    public Course(String courseNumber, String courseName, Subject courseSubject) {
        super();
        this.courseNumber = courseNumber;
        this.courseName = courseName;
        this.courseSubject = courseSubject;
    }

    /*
     * Course getters and setters
     */
    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Subject getCourseSubject() {
        return courseSubject;
    }

    public void setCourseSubject(Subject courseSubject) {
        this.courseSubject = courseSubject;
    }


}
