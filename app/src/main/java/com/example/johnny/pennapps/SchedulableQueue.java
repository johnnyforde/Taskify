package com.example.johnny.pennapps;
//package com.example.johnny.pennapps.Model.Events.TaskifySchedulable;


import com.example.johnny.pennapps.Model.Events.TaskifySchedulable;
import com.example.johnny.pennapps.Model.Events.TaskifyTask;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;


/**
 * Created by jasonlin on 1/22/16.
 */
public class SchedulableQueue {

    public PriorityQueue<TaskifySchedulable> requestQueue;
    public SchedulableQueue(PriorityQueue<TaskifySchedulable> requestQueue) {
        this.requestQueue = requestQueue;
    }

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

        // Incomplete
        return new ArrayList<Interval>();
    }

    public double taskifyMDD(double processed, TaskifyTask task) {
        return Math.max(processed + task.getTaskTime().getMillis(), (double)task.getDeadline().getMillis());
    }

    public ArrayList<TaskifyTask> taskifySort(ArrayList<TaskifyTask> toSchedule) {
        ArrayList<TaskifyTask> unsortedTasks =  new ArrayList<TaskifyTask>(toSchedule);
        ArrayList<TaskifyTask> sortedTasks = new ArrayList<TaskifyTask>();

        while (!unsortedTasks.isEmpty()) {
            TaskifyTask bestTask = unsortedTasks.get(0);
//            double bestFinishTime =
        }

        return sortedTasks;
        // Incomplete
    }

}
