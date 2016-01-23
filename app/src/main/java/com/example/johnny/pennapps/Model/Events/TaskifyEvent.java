package com.example.johnny.pennapps.Model.Events;

import java.util.Date;

/**
 * Created by kevinlee on 1/23/16.
 */
public class TaskifyEvent extends TaskifySchedulable {

    // Constant for highest priority for scheduling
    private static final double HIGHEST_PRIORITY = 0;

    private Date startTime;
    private Date endTime;

    public TaskifyEvent(String name, Date startTime, Date endTime) {
        this.priority = HIGHEST_PRIORITY;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Task: " + this.name + "; Start time: " + this.startTime + "; End time: " + this.endTime;
    }
}
