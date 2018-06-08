package com.data;

public class Subject {
    /*
     * Subject fields
     */
    private int subjectID;
    private String subjectName;

    /*
     * Subject constructor
     */
    public Subject(int subjectNumber, String subjectName) {
        super();
        this.subjectID = subjectNumber;
        this.subjectName = subjectName;
    }

    /*
     * Subject getters and setters
     */
    public int getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(int subjectID) {
        this.subjectID = subjectID;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

}

