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
    private int subjectId;
    private String teacherId;

    /*
     * Question constructor
     */
    public Question(String questionText, String[] possibleAnswers, int correctAnswer, int questionSubject, String teacherID) {
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.subjectId = questionSubject;
        this.teacherId = teacherID;
        this.possibleAnswers = possibleAnswers;
    }

    public Question(int QID, int subjectId) {
        this.QID = QID;
        this.subjectId = subjectId;
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

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
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

    public void setQID(int QID) {
        this.QID = QID;
    }

    @Override
    public String toString() {
        return "Question{" +
                "QID=" + QID +
                ", questionText='" + questionText + '\'' +
                ", possibleAnswers=" + Arrays.toString(possibleAnswers) +
                ", correctAnswer=" + correctAnswer +
                ", subjectId=" + subjectId +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }
}

