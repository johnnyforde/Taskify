package com.example.johnny.pennapps.Model.Scheduler;

import android.util.Log;

import com.example.johnny.pennapps.Model.Events.TaskifyCalendarEvent;
import com.example.johnny.pennapps.Model.Events.TaskifySchedulable;
import com.example.johnny.pennapps.Model.Events.TaskifyTask;
import com.firebase.client.Firebase;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;


/**
 * Created by jasonlin on 1/22/16.
 */
public class ScheduleAlgorithm {

    // Lists of scheduledTimes to return to Scheduler to be scheduled
    private List<TaskifyCalendarEvent> scheduledTimes = new ArrayList<TaskifyCalendarEvent>();
    private Firebase database;

    public ScheduleAlgorithm() {
        database = new Firebase("https://pennTaskify.firebaseio.com/");
    }
    // Comparator class
    public static final class priorityComparator implements Comparator<TaskifyTask> {
        @Override
        public int compare (TaskifyTask a, TaskifyTask b) {
            Double a1 = new Double(a.getPriority());
            Double b1 = new Double(b.getPriority());
            return a1.compareTo(b1);
        }
    } // end of inner class

    public List<TaskifyCalendarEvent> getScheduledTimes() {
        return scheduledTimes;
    }

    public PriorityQueue<TaskifySchedulable> requestQueue;


    /**
     * Original algorithm: Priority = (Deadline - processed) - remainingTime / difficulty;
     */
    public List<TaskifyCalendarEvent> scheduleTasksByPriority(List<TaskifyTask> toSchedule, Map<Long, TaskifyCalendarEvent> availabilities) {
        // Sort by MDD
        List<TaskifyTask> mddSorted = taskifySort(toSchedule);
        String in  = new String("number of tasks" + toSchedule.size());
        Log.i("toSchedule", in);
        Log.i("shouldbecorrect", mddSorted.get(0).getDeadline().toString());

        // Sort by MDD then by Priority
        Comparator<TaskifyTask> comp = new priorityComparator();
//        PriorityQueue<TaskifyTask> queue = new PriorityQueue<TaskifyTask>(mddSorted.size(), comp);
        LinkedList<TaskifyTask> queue = new LinkedList<TaskifyTask>();
        queue.offer(toSchedule.get(0));

//
//        // Final sorting algorithm
        if(scheduledTimes!=null) scheduledTimes.clear();

        DateTime currentTime = new DateTime();
        DateTime latestDeadline = toSchedule.get(0).getDeadline();
//        Log.i("hilatest", latestDeadline.toString());
//        Duration totalTime = new Duration(0);
//        for (TaskifyTask task : toSchedule) {
//            Log.i("toSchedule", "enters toschedule loop");
//            if (task.getDeadline().isAfter(latestDeadline)) {
//                latestDeadline = task.getDeadline();
//            }
//            totalTime = totalTime.withDurationAdded(task.getTaskTime(),1);
//        }
//        Duration timeToDeadline = new Duration(currentTime.getMillis(), latestDeadline.getMillis());
//        if (totalTime.isLongerThan(timeToDeadline)) {
//            Log.i("jason","Cannot schedule! Total time required exceeds existing time");
//        }

        // Create DateTime object for start of schedule period
        DateTime scheduleStart = new DateTime(currentTime.getYear(), currentTime.getMonthOfYear(), currentTime.getDayOfMonth(), currentTime.getHourOfDay()+1,0);
        Log.e("start", scheduleStart.toString());
//        DateTime currentHour = new DateTime(currentTime.getYear(), currentTime.getMonthOfYear(), currentTime.getDayOfMonth(), currentTime.getHourOfDay()+1, 0);
                // Create DateTime object for end of schedule period
        DateTime scheduleEnd = new DateTime (latestDeadline.getYear(), latestDeadline.getMonthOfYear(), latestDeadline.getDayOfMonth(), latestDeadline.getHourOfDay(), latestDeadline.getMinuteOfHour());
//
//
        while(!(scheduleStart.isBefore(scheduleEnd) || queue.isEmpty())) {
            TaskifyTask candidate = queue.peek();

            // taskTime (amount of time to complete the task) should be real time, varying
            int numOfHours = candidate.getTaskTime().toStandardHours().getHours();
//            DateTime tentativeEnd = new DateTime(scheduleStart.getMillis()+Hours.hours(numOfHours).toStandardDuration().getMillis());
            Log.i("numOfHours","hello");
            if(availabilities.get(scheduleEnd.getMillis()) != null) {
                Log.i("jason", "Gets into for loop");
                Interval taskInterval = new Interval(scheduleStart.toInstant(), scheduleEnd.toInstant());
                database.child("tasks").setValue(candidate.getName());
                for (int i=0; i<numOfHours; i++) {
                    Log.i("jason", "Gets into for loop");
                    long offset = Hours.hours(i).toStandardDuration().getMillis();
                    scheduledTimes.add(new TaskifyCalendarEvent(new Interval(taskInterval), candidate));
                    database.child("tasks").setValue(candidate.getName());
                    availabilities.put(scheduleStart.getMillis()+offset, new TaskifyCalendarEvent(taskInterval, candidate));
                    queue.remove();
                }
                scheduleStart = new DateTime(scheduleStart.getMillis()+Hours.hours(numOfHours).toStandardDuration().getMillis());
//                queue.remove();
            }
            else {
                scheduleStart = new DateTime(scheduleStart.getMillis() + Hours.hours(1).toStandardDuration().getMillis());
            }
        }
        return scheduledTimes;
    }


    public double taskifyMDD(double processed, TaskifyTask task) {
        return Math.max(processed + task.getTaskTime().getMillis(), (double)task.getDeadline().getMillis());
    }

    public ArrayList<TaskifyTask> taskifySort(List<TaskifyTask> toSchedule) {
        ArrayList<TaskifyTask> unsortedTasks =  new ArrayList<TaskifyTask>(toSchedule);
        ArrayList<TaskifyTask> sortedTasks = new ArrayList<TaskifyTask>();
        double processed = 0;

        while (!unsortedTasks.isEmpty()) {
            TaskifyTask bestTask = unsortedTasks.get(0);
            double bestFinishTime = taskifyMDD(bestTask.getTaskCompleted().getMillis(), bestTask);
            for (TaskifyTask task : unsortedTasks) {
                double finishTime = taskifyMDD(task.getTaskCompleted().getMillis(), task);
                if (finishTime < bestFinishTime) {
                    bestFinishTime = finishTime;
                    bestTask = task;
                }
            }
            sortedTasks.add(bestTask);
            unsortedTasks.remove(bestTask);
            processed += bestTask.getTaskTime().getMillis();
        }

        return sortedTasks;
    }
}
