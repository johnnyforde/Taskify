package com.example.johnny.pennapps.Model.Events;

import java.util.Date;
import java.sql.Time;

/**
 * Created by kevinlee on 1/23/16.
 */
public class TaskifyTask extends TaskifySchedulable {

    // User defined difficulty of task
    private double  difficulty;
    // Deadline that the task must be completed by
    private final Date deadline;
    // Amount of time the task would take
    private final Time taskTime;
    // Amount of time spent working on this task
    private final Time taskCompleted;
    // Whether the task is optional or can be dropped if necessary
    private final boolean optional;

    public TaskifyTask(String name, int difficulty, Date deadline, Time estimateTime, Time timeSpent, boolean optional) {
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
        return Math.max(this.taskTime.getTime(), this.deadline.getTime() - this.taskCompleted.getTime()) / difficulty;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public String toString() {
        return "Task: " + this.name + "; Deadline: " + this.deadline + "; Task Time: " + this.taskTime
                + "; Completed: " + this.taskCompleted;
    }
}
