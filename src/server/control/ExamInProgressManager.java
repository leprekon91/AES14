package server.control;

import com.data.ExamInProgress;
import server.ocsf.ConnectionToClient;
import server.sql.ExamTable;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ExamInProgressManager {
    private static ExamInProgressManager ourInstance = new ExamInProgressManager();
    Timer locker = new Timer();
    private ArrayList<ExamInProgress> eips = new ArrayList<>();

    private ExamInProgressManager() {
        locker.scheduleAtFixedRate(new LockerTask(), 0, 1000);
    }

    public static ExamInProgressManager getInstance() {
        return ourInstance;
    }

    public void addExamInProgress(ExamInProgress eip) {
        this.eips.add(eip);
        ExamTable.examSetUsed(eip.getExam());
    }

    public ArrayList<ExamInProgress> getExamInProgressArrayByTeacher(String teacherUName) {
        //set new array of exams in progress
        ArrayList<ExamInProgress> ans = new ArrayList<>();
        for (ExamInProgress eip :
                eips) {
            if (eip.getExaminingTeacher().getUsername().equals(teacherUName))
                ans.add(new ExamInProgress(
                                eip.getDateTimeStart(),
                                eip.getDateTimeEnd(),
                                eip.getStudentArrayList(),
                                eip.getPassword(),
                                eip.getExaminingTeacher(),
                                eip.getExam(),
                                eip.isWordType()
                        )
                );
        }
        return ans;
    }

    public ArrayList<ExamInProgress> getExamInProgressArrayByStudent(String studentUName) {
        ArrayList<ExamInProgress> ans = new ArrayList<ExamInProgress>();
        for (ExamInProgress eip :
                this.eips) {
            if (eip.studentExistsInList(studentUName)) {
                ans.add(new ExamInProgress(
                                eip.getDateTimeStart(),
                                eip.getDateTimeEnd(),
                                eip.getStudentArrayList(),
                                eip.getPassword(),
                                eip.getExaminingTeacher(),
                                eip.getExam(),
                                eip.isWordType()
                        )
                );
            }
        }
        return ans;
    }

    public void extendExamInProgress(ExamInProgress examInProgress, int amount) {
        for (ExamInProgress eip :
                this.eips) {
            if (eip.equals(examInProgress)) {
                eip.extendExpiryDate(amount);
            }
        }
    }

    public void studentStartsAnExam(ConnectionToClient studentClient, ExamInProgress EIP) {
        //search for EIP in List:
        for (ExamInProgress eip : this.eips) {
            if (eip.equals(EIP)) {
                ExamInProgress.studentBeginsExam(studentClient, eip);
            }
        }
    }

    public class LockerTask extends TimerTask {

        @Override
        public void run() {
            for (ExamInProgress eip :
                    eips) {
                if (eip.hasExpired()) eip.lockExam();
            }
        }
    }
}
