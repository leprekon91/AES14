package com.data;

import com.Contract;
import server.ocsf.ConnectionToClient;
import server.sql.AuthorizeUser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Entity Class For Exam In Progress
 */
public class ExamInProgress {
    private LocalDateTime dateTimeStart;            //when will the exam start
    private LocalDateTime dateTimeEnd;              //when will the exam end
    private ArrayList<Student> studentArrayList;    //list of students to take the exam
    private String password;                        //access password for students
    private Teacher examiningTeacher;               //Teacher that conducts the exam
    private Exam exam;                              //Exam Object

    //Constructor
    public ExamInProgress(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, ArrayList<Student> studentArrayList, String password, Teacher examiningTeacher, Exam exam) {
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.studentArrayList = studentArrayList;
        this.password = password;
        this.examiningTeacher = examiningTeacher;
        this.exam = exam;
    }

    //Getters and setters
    public LocalDateTime getDateTimeStart() {
        return dateTimeStart;
    }

    public void setDateTimeStart(LocalDateTime dateTimeStart) {
        this.dateTimeStart = dateTimeStart;
    }

    public LocalDateTime getDateTimeEnd() {
        return dateTimeEnd;
    }

    public void setDateTimeEnd(LocalDateTime dateTimeEnd) {
        this.dateTimeEnd = dateTimeEnd;
    }

    public ArrayList<Student> getStudentArrayList() {
        return studentArrayList;
    }

    public void setStudentArrayList(ArrayList<Student> studentArrayList) {
        this.studentArrayList = studentArrayList;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Teacher getExaminingTeacher() {
        return examiningTeacher;
    }

    public void setExaminingTeacher(Teacher examiningTeacher) {
        this.examiningTeacher = examiningTeacher;
    }

    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    /**
     * Check whether the exam has begun
     *
     * @return true if yes, false otherwise
     */
    public boolean hasBegun() {
        return LocalDateTime.now().isAfter(dateTimeStart);
    }

    /**
     * Check whether the exam has stopped
     *
     * @return true if yes, false otherwise
     */
    public boolean hasStopped() {
        return LocalDateTime.now().isAfter(dateTimeEnd);
    }

    /**
     * A user has requested to begin an exam: check his credentials,password and has the exam started.
     *
     * @param client   client thread that tries to start the exam
     * @param Password password string for the exam
     */
    public void beginExam(ConnectionToClient client, String Password) {
        try {
            String username = AuthorizeUser.getInstance().findUsernameByClient(client);
            if (username != null) {
                if (studentExistsInList(username) && Password.equals(getPassword()) && this.hasBegun()) {
                    client.sendToClient(new Message(Contract.BEGIN_EXAM, getExam()));
                }
            } else client.sendToClient(new Message(Contract.CANT_BEGIN_EXAM, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * find user in the students list to be tested
     *
     * @param username username String
     * @return true if exists, false - otherwise
     */
    public boolean studentExistsInList(String username) {
        for (Student s : studentArrayList) {
            if (s.getUsername().equals(username)) return true;
        }
        return false;
    }


}
