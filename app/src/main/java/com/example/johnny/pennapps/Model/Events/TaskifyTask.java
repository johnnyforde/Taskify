package com.example.johnny.pennapps.Model.Events;

import java.util.Date;
import java.sql.Time;

/**
 * Created by kevinlee on 1/23/16.
 */
public class TaskifyTask extends TaskifySchedulable {

    private final Date deadline;
    private final Time estimateTime;
    private final Time timeSpent;

    public TaskifyTask(String name, Date deadline, int priority, Time estimateTime, Time timeSpent) {
        this.name = name;
        this.deadline = deadline;
        this.priority = priority;
        this.estimateTime = estimateTime;
        this.timeSpent = timeSpent;
    }

    public Date getDate() {
        return deadline;
    }

    public int getPriority() {
        return priority;
    }

    public String toString() {
        return "Task: " + this.name + "; Deadline: " + this.deadline + "; Estimate Time: " + this.estimateTime
                + "; Time Spent: " + this.timeSpent;
    }
}
