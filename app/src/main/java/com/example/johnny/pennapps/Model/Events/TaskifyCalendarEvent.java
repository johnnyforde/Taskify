package com.example.johnny.pennapps.Model.Events;

import org.joda.time.Interval;

/**
 * Represents events on a calendar
 *
 * Created by kevinlee on 1/23/16.
 */
public class TaskifyCalendarEvent {
    // Time interval that event takes during
    private Interval interval;
    // Reference to scheduable object this event represents
    private TaskifySchedulable event;

    public TaskifyCalendarEvent(Interval interval, TaskifySchedulable event) {
        this.interval = interval;
        this.event = event;
    }

    public Interval getInterval() {
        return interval;
    }

    public TaskifySchedulable getEvent() {
        return event;
    }

    @Override
    public String toString() {
        return "" + event.getName() + ": " + interval;
    }
}
