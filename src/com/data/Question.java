/*
 * Copyright (c) 2018.  Written by Andrey Grabarnick Email: Reist2009@gmail.com
 */

package com.data;

import java.io.Serializable;
import java.util.Arrays;

public class Question implements Serializable {
    /**
	 * default serial ID
	 */
	private static final long serialVersionUID = 1L;
	//Entity Class for single QuestionListFragment.Data.Question
    private String _QID;
    private int _teacherID;
    private String _questionText;
    private String[] _ans;
    private int _correct;

    public Question(String _QID, int _teacherID, String _questionText, String[] _ans, int correct) {
        this._QID = _QID;
        this._teacherID = _teacherID;
        this._questionText = _questionText;
        this._ans = _ans;
        this._correct = correct;
    }

    //Getters & Setters-------------------------------------------------------------------------------------------------
    public String get_QID() {
        return _QID;
    }

    public void set_QID(String _QID) {
        this._QID = _QID;
    }

    public int get_teacherID() {
        return _teacherID;
    }

    public void set_teacherID(int _teacherID) {
        this._teacherID = _teacherID;
    }

    public String get_questionText() {
        return _questionText;
    }

    public void set_questionText(String _questionText) {
        this._questionText = _questionText;
    }

    public String[] get_ans() {
        return _ans;
    }

    public void set_ans(String[] _ans) {
        this._ans = _ans;
    }

    public int get_correct() {
        return _correct;
    }

    public void set_correct(int correct) {
        this._correct = correct;
    }
    //Getters & Setters-END---------------------------------------------------------------------------------------------

    @Override
    public String toString() {
        return "\n-------\n" +
                "_QID= " + _QID + '\n' +
                ", _teacherID= " + _teacherID + '\n' +
                ", _questionText= " + _questionText + '\n' +
                ", _ans=" + Arrays.toString(_ans) +'\n'+
                ", _correct=" + _correct +'\n'+
                '}';
    }
}
