package com.data;

import java.io.Serializable;

public class ExtensionRequest implements Serializable {
    Exam exam;
    Teacher teacher;
    private int extAmnt;
    private boolean approved;

    public ExtensionRequest(Exam exam, Teacher teacher, int extAmnt) {
        this.exam = exam;
        this.teacher = teacher;
        this.extAmnt = extAmnt;
        this.approved = Boolean.parseBoolean(null);
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getExtAmnt() {
        return extAmnt;
    }

    public void setExtAmnt(int extAmnt) {
        this.extAmnt = extAmnt;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    @Override
    public String
    toString() {
        return "ExtensionRequest{" +
                "exam=" + exam +
                ", teacher=" + teacher +
                ", extAmnt=" + extAmnt +
                ", approved=" + approved +
                '}';
    }
}
