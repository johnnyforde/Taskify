package com.example.johnny.pennapps.Model.Events;

import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.List;

/**
 * Events to be scheduled
 *
 * Created by kevinlee on 1/23/16.
 */
public class TaskifyCommitment extends TaskifySchedulable {

    // Constant for highest priority for scheduling
    private static final double HIGHEST_PRIORITY = 0;

    // Start time of this event
    private DateTime startTime;
    // End time of this event
    private DateTime endTime;
    // Whether or not this event repeats
    private boolean repeats;
    // List of scheduled times to be dedicated to a task
    private List<Interval> scheduledTimes;

    public TaskifyCommitment(String name, boolean repeats, DateTime startTime, DateTime endTime) {
        this.priority = HIGHEST_PRIORITY;
        this.repeats = repeats;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Task: " + this.name + "; Start time: " + this.startTime + "; End time: " + this.endTime;
    }
}
