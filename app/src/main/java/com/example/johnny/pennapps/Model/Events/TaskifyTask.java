package com.example.johnny.pennapps.Model.Events;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import java.util.List;

/**
 * Tasks to be scheduled
 *
 * Created by kevinlee on 1/23/16.
 */
public class TaskifyTask extends TaskifySchedulable {

    // User defined difficulty of task, range {1-10}
    private double  difficulty;
    // Deadline that the task must be completed by
    private final DateTime deadline;
    // Amount of time the task would take, Jason: is this dynamic/changing?
    private final Duration taskTime;
    // Amount of time spent working on this task
    private final Duration taskCompleted;
    // Whether the task is optional or can be dropped if necessary
    private final boolean optional;
    // List of scheduled times to be dedicated to a task
    private List<Interval> scheduledTimes;

    public TaskifyTask(String name, int difficulty, DateTime deadline, Duration estimateTime, Duration timeSpent, boolean optional) {
        this.name = name;
        this.difficulty = difficulty;
        this.deadline = deadline;
        this.taskTime = estimateTime;
        this.taskCompleted = timeSpent;
        this.optional = optional;
    }

    /** Modified due-date scheduling heuristic.
     * https://en.wikipedia.org/wiki/Modified_due-date_scheduling_heuristic
     *
     * @return priority based on parameters
     */
    @Override
    public double getPriority() {
        DateTime currentTime = new DateTime();
        Duration timeToDeadline = new Duration(currentTime.getMillis(), this.deadline.getMillis());

        return Math.max(this.taskTime.getMillis(), timeToDeadline.getMillis() - this.taskCompleted.getMillis()) / difficulty;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public List<Interval> getScheduledTimes() {
        return scheduledTimes;
    }

    public void setScheduledTimes(List<Interval> scheduledTimes) {
        this.scheduledTimes = scheduledTimes;
    }

    // getter for deadline
    public DateTime getDeadline() {
        return this.deadline;
    }

    // getter for duration
    public Duration getTaskTime() {
        return this.taskTime;
    }

    public Duration getTaskCompleted() {
        return this.taskCompleted;
    }

    public void setScheduledTimes() {

    }

    public String toString() {
        return "Task: " + this.name + "; Deadline: " + this.deadline + "; Task Time: " + this.taskTime
                + "; Completed: " + this.taskCompleted;
    }
}
