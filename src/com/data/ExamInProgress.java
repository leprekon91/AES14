package com.data;

import com.Contract;
import server.ocsf.ConnectionToClient;
import server.sql.AuthorizeUser;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Entity Class For Exam In Progress
 */
public class ExamInProgress implements Serializable {
    private LocalDateTime dateTimeStart;            //when will the exam start
    private LocalDateTime dateTimeEnd;              //when will the exam end
    private ArrayList<Student> studentArrayList;    //list of students to take the exam
    private ArrayList<ConnectionToClient> examineeList;//current students that are examined
    private ArrayList<Solved_Exam> solutions;       //solutions list
    private String password;                        //access password for students
    private Teacher examiningTeacher;               //Teacher that conducts the exam
    private Exam exam;                              //Exam Object
    private boolean wordType;

    //Constructor
    public ExamInProgress(LocalDateTime dateTimeStart, LocalDateTime dateTimeEnd, ArrayList<Student> studentArrayList, String password, Teacher examiningTeacher, Exam exam, boolean wordType) {
        this.dateTimeStart = dateTimeStart;
        this.dateTimeEnd = dateTimeEnd;
        this.studentArrayList = studentArrayList;
        this.password = password;
        this.examiningTeacher = examiningTeacher;
        this.exam = exam;
        this.wordType = wordType;
        this.solutions = new ArrayList<>();
        this.examineeList = new ArrayList<>();

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
     * Check whether the exam has expired
     *
     * @return true if yes, false otherwise
     */
    public boolean hasExpired() {
        return LocalDateTime.now().isAfter(dateTimeEnd);
    }

    /**
     * A user has requested to begin an exam: check his credentials,password and has the exam started.
     *
     * @param client   client thread that tries to start the exam
     * @param Password password string for the exam
     */
    public void studentBeginsExam(ConnectionToClient client, String Password) {
        try {
            String username = AuthorizeUser.getInstance().findUsernameByClient(client);
            if (username != null) {
                if (studentExistsInList(username) && Password.equals(getPassword()) && this.hasBegun()) {
                    this.examineeList.add(client);
                    if (isWordType()) {
                        client.sendToClient(new Message(Contract.BEGIN_EXAM_WORD, this));
                    }
                    client.sendToClient(new Message(Contract.BEGIN_EXAM, this));
                }
            } else client.sendToClient(new Message(Contract.CANT_BEGIN_EXAM, null));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Student has submitted his solution.
     * check whether the exam has not expired and that he is part of the examinees list.
     *
     * @param client   student client
     * @param solution student's solution
     */
    public void studentSubmitsExamSolution(ConnectionToClient client, Solved_Exam solution) {
        try {
            if (studentExistsInList(solution.getSolvingStudent().getUsername()) &&
                    examineeList.contains(client) &&
                    !this.hasExpired()) {
                solutions.add(solution);//add solution to solution list
                examineeList.remove(client);//remove student's client from examinees list
                client.sendToClient(new Message(Contract.EXAM_SUBMITTED, solution));

            } else {
                client.sendToClient(new Message(Contract.CANT_SUBMIT_EXAM, solution));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lock the current exam.
     * The Teacher has requested an exam lock.
     */
    public void lockExam() {
        if (!hasExpired()) {
            dateTimeEnd = LocalDateTime.now();
        }
        try {
            for (ConnectionToClient c :
                    examineeList) {

                c.sendToClient(new Message(Contract.EXAM_LOCK, null));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extend the expiry time by minutes
     *
     * @param minutes extension amount.
     */
    public void extendExpiryDate(int minutes) {
        if (!hasExpired()) dateTimeEnd.plusMinutes(minutes);
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


    public boolean isWordType() {
        return wordType;
    }

    public void setWordType(boolean wordType) {
        this.wordType = wordType;
    }
}
