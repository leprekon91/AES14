package com.data;

import java.io.Serializable;
import java.util.Objects;

public class ExtensionRequest implements Serializable {
    ExamInProgress eip;
    Teacher teacher;
    private int extAmnt;
    private boolean approved;
    private String textContent;

    public ExtensionRequest(ExamInProgress exam, Teacher teacher, String textContent, int extAmnt) {
        this.eip = exam;
        this.teacher = teacher;
        this.extAmnt = extAmnt;
        this.textContent = textContent;
        this.approved = Boolean.parseBoolean(null);
    }

    public ExamInProgress getExamInProgress() {
        return eip;
    }

    public void setExamInProgress(ExamInProgress eip) {
        this.eip = eip;
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

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    @Override
    public String toString() {
        return "ExtensionRequest{" +
                "exam=" + eip +
                ", teacher=" + teacher +
                ", extAmnt=" + extAmnt +
                ", approved=" + approved +
                ", textContent='" + textContent + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtensionRequest that = (ExtensionRequest) o;
        return getExtAmnt() == that.getExtAmnt() &&
                isApproved() == that.isApproved() &&
                Objects.equals(eip, that.eip) &&
                Objects.equals(getTeacher(), that.getTeacher()) &&
                Objects.equals(getTextContent(), that.getTextContent());
    }

    @Override
    public int hashCode() {

        return Objects.hash(eip, getTeacher(), getExtAmnt(), isApproved(), getTextContent());
    }
}
