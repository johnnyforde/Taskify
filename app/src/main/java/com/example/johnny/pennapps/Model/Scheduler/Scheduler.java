package com.example.johnny.pennapps.Model.Scheduler;

import com.example.johnny.pennapps.Model.Events.TaskifyCalendarEvent;

import org.joda.time.Hours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Schedules
 *
 * Created by kevinlee on 1/23/16.
 */
public class Scheduler {

    // Maps DateTime objects represented as longs to TaskifyCalendarEvents
    // Keys for every hour block only
    private Map<Long, TaskifyCalendarEvent> availabilities = new HashMap<Long, TaskifyCalendarEvent>();
    private List<TaskifyCalendarEvent> schedule = new ArrayList<TaskifyCalendarEvent>();

    public Scheduler() {

    }

    /**
     * Adds time commitments to the HashMap
     *
     * @param commitments
     */
    public void addCommitments(List<TaskifyCalendarEvent> commitments) {
        for (TaskifyCalendarEvent commitment : commitments) {

            // Get the duration in hours of the commitment
            int numOfHours = commitment.getInterval().toDuration().toStandardHours().getHours();

            // Block off the duration of the event in the HashMap
            for (int i = 0; i < numOfHours; i++) {
                long offset = Hours.hours(i).toStandardDuration().getMillis();
                availabilities.put(commitment.getInterval().getStartMillis() + offset, commitment);
            }
        }

        // Add commitments to the schedule
        schedule.addAll(commitments);
    }

    /**
     * Adds time commitments to the HashMap
     *
     */
    public void addTasks(List<TaskifyCalendarEvent> tasks) {

    }

    public Map<Long, TaskifyCalendarEvent> getAvailabilities() {
        return availabilities;
    }

    public List<TaskifyCalendarEvent> getSchedule() {
        return schedule;
    }
}
