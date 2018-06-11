package com.data;


import java.io.Serializable;
import java.util.Arrays;

public class Question implements Serializable {
    /*
     * Question fields
     */
    private int QID;
    private String questionText;
    private String possibleAnswers[];
    private int correctAnswer;
    private Subject subject;
    private Teacher author;

    /*
     * Question constructor
     */
    public Question(String questionText, String[] possibleAnswers, int correctAnswer, Subject questionSubject, Teacher teacherID) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.subject = questionSubject;
        this.author = teacherID;
        this.possibleAnswers = possibleAnswers;
    }

    public Question(int QID, Subject subjectId) {
        this.QID = QID;
        this.subject = subjectId;
    }

    /*
     * Question getters and setters
     */
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subjectId) {
        this.subject = subjectId;
    }

    public Teacher getAuthor() {
        return author;
    }

    public void setAuthor(Teacher teacher) {
        this.author = teacher;
    }

    public String[] getPossibleAnswers() {
        return possibleAnswers;
    }

    public void setPossibleAnswers(String[] possibleAnswers) {
        this.possibleAnswers = possibleAnswers;
    }

    public String getAns(int index) {
        return this.possibleAnswers[index];
    }

    public int getQID() {
        return QID;
    }

    public String getQIDString() {
        if (subject != null)
            return String.format("%02d", getSubject().getSubjectID()) + String.format("%03d", QID);
        return "00000";
    }

    public void setQID(int QID) {
        this.QID = QID;
    }

    @Override
    public String toString() {
        return "Question{" +
                "QID=" + String.format("%03d", QID) +
                ", questionText='" + questionText + '\'' +
                ", possibleAnswers=" + Arrays.toString(possibleAnswers) +
                ", correctAnswer=" + correctAnswer +
                ", subject=" + subject +
                ", author='" + author + '\'' +
                '}';
    }
}

