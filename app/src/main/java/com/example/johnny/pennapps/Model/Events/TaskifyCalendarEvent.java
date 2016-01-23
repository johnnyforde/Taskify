package com.example.johnny.pennapps.Model.Events;

import java.util.Date;

/**
 * Calendar event marked by start and end time
 *
 * Created by kevinlee on 1/23/16.
 */
public class TaskifyCalendarEvent {

    private final Date startTime;
    private final Date endTime;

    public TaskifyCalendarEvent(Date startTime, Date endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return this.startTime;
    }

    public Date getEndTime() {
        return this.endTime;
    }
}
