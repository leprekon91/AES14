package server.control;

import com.data.ExamInProgress;

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
    }

    public ArrayList<ExamInProgress> getExamInProgressArrayByTeacher(String teacherUName) {
        //set new array of exams in progress
        ArrayList<ExamInProgress> ans = new ArrayList<>();
        for (ExamInProgress eip :
                eips) {
            if (eip.getExam().getExamAuthorTeacher().getUsername().equals(teacherUName))
                ans.add(new ExamInProgress(
                                eip.getDateTimeStart(),
                                eip.getDateTimeEnd(),
                                eip.getStudentArrayList(),
                                eip.getPassword(),
                                eip.getExaminingTeacher(),
                                eip.getExam()
                        )
                );
        }
        return ans;
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
