package com.example.johnny.pennapps;
//package com.example.johnny.pennapps.Model.Events.TaskifySchedulable;


import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import com.example.johnny.pennapps.Model.Events.TaskifySchedulable;
import com.example.johnny.pennapps.Model.Events.TaskifyTask;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;


/**
 * Created by jasonlin on 1/22/16.
 */
public class SchedulableQueue {

    // Comparator class
    public static final class priorityComparator implements Comparator<TaskifyTask> {
        @Override
        public int compare (TaskifyTask a, TaskifyTask b) {
            Double a1 = new Double(a.getPriority());
            Double b1 = new Double(b.getPriority());
            return a1.compareTo(b1);
        }
    }

    public PriorityQueue<TaskifySchedulable> requestQueue;
//    public SchedulableQueue(PriorityQueue<TaskifySchedulable> requestQueue) {
//        this.requestQueue = requestQueue;
//    }

    public ArrayList<TaskifySchedulable> schedule () {
        ArrayList<TaskifySchedulable> schedules = new ArrayList<TaskifySchedulable>();
        return schedules;
    }

    /**
     * Original algorithm: Priority = (Deadline - processed) - remainingTime / difficulty;
     */
    public List<Interval> scheduleTasksByPriority(ArrayList<TaskifyTask> toSchedule) {
        DateTime currentTime = new DateTime();
        DateTime lastDeadline = new DateTime();
        for (TaskifyTask task : toSchedule) {
            if (task.getDeadline().isAfter(lastDeadline)) {
                lastDeadline = task.getDeadline();
            }
        }
        Duration timeToDeadline = new Duration(currentTime.getMillis(), lastDeadline.getMillis());

        // Sort by MDD
        ArrayList<TaskifyTask> mddSorted = taskifySort(toSchedule);
        ArrayList<Interval> prioritySorted = new ArrayList<Interval>();

        // Sort by MDD then by Priority
        Comparator<TaskifyTask> comp = new priorityComparator();
        PriorityQueue<TaskifyTask> queue = new PriorityQueue<TaskifyTask>(0, comp);
        for (TaskifyTask mddTask : mddSorted) {
            queue.add(mddTask);
        }
        for (TaskifyTask priorityTask : queue) {
            prioritySorted.add(priorityTask);
        }
        return prioritySorted;
    }

    public double taskifyMDD(double processed, TaskifyTask task) {
        return Math.max(processed + task.getTaskTime().getMillis(), (double)task.getDeadline().getMillis());
    }

    public ArrayList<TaskifyTask> taskifySort(ArrayList<TaskifyTask> toSchedule) {
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
