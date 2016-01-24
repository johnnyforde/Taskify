package com.example.johnny.pennapps.Model.Scheduler;

import com.example.johnny.pennapps.Model.Events.TaskifyCalendarEvent;
import com.example.johnny.pennapps.Model.Events.TaskifyCommitment;
import com.example.johnny.pennapps.Model.Events.TaskifyTask;
import com.firebase.client.Firebase;

import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Schedules
 *
 * Created by kevinlee on 1/23/16.
 */
public class Scheduler {

    // List of all commitments
    private List<TaskifyCommitment> commitments = new ArrayList<TaskifyCommitment>();
    // List of all tasks
    private List<TaskifyTask> tasks = new ArrayList<TaskifyTask>();
    // Maps DateTime objects represented as longs to TaskifyCalendarEvents keys for every hour block only
    private Map<Long, TaskifyCalendarEvent> availabilities = new HashMap<Long, TaskifyCalendarEvent>();
    // List of the sorted and prioritized calendar event
    private List<TaskifyCalendarEvent> schedule = new ArrayList<TaskifyCalendarEvent>();
    // Firebase database for storing schedule and event information
    private Firebase database;

    public Scheduler() {
        database = new Firebase("https://pennTaskify.firebaseio.com/");
    }

    public static final class priorityComparator implements Comparator<TaskifyTask> {
        @Override
        public int compare(TaskifyTask lhs, TaskifyTask rhs) {
            Double taskL = new Double(lhs.getPriority());
            Double taskR = new Double(rhs.getPriority());
            return taskL.compareTo(taskR);
        }
    }

    public void addTask(TaskifyTask task) {
        tasks.add(task);
    }

    public void addTasks(List<TaskifyTask> tasks) {
        this.tasks.addAll(tasks);
    }

    public void addCommitment(TaskifyCommitment commitment) {
        commitments.add(commitment);
    }

    public void addCommitments(List<TaskifyCommitment> commitments) {
        this.commitments.addAll(commitments);
    }

    public Map<Long, TaskifyCalendarEvent> getAvailabilities() {
        return availabilities;
    }

    public List<TaskifyCalendarEvent> getSchedule() {
        return schedule;
    }

    /**
     * Reschedules based on the commitments and tasks
     */
    public void reschedule() {
        // Clear the current schedule and list of availabilities
        schedule.clear();
        availabilities.clear();

        // Schedule static time commitments
        scheduleCommitments();

        // Schedule tasks over time commitments
        scheduleTasks();
    }

    /**
     * Adds time commitments to the HashMap and schedule
     * Only called by reschedule
     */
    private void scheduleCommitments() {

        // Add all commitments to the schedule
        for (TaskifyCommitment commitment : commitments) {
            schedule.addAll(commitment.getScheduledTimes());
        }

        // Add all the events to the hashmap to indicate that their time blocks can not be scheduled over
        for (TaskifyCalendarEvent events : schedule) {

            // Get the duration in hours of the commitment
            int numOfHours = events.getInterval().toDuration().toStandardHours().getHours();

            // Block off the duration of the event in the HashMap
            for (int i = 0; i < numOfHours; i++) {
                long offset = Hours.hours(i).toStandardDuration().getMillis();
                availabilities.put(events.getInterval().getStartMillis() + offset, events);
            }
            database.child("commitments").setValue(events.getEvent().getName());
        }
    }

    /**
     * Schedules tasks over a schedule filled with existing events
     */
    private void scheduleTasks() {

        Queue<TaskifyTask> pq = new PriorityQueue<TaskifyTask>(tasks.size(), new priorityComparator());
        for (TaskifyTask task : tasks) {
            pq.add(task);
        }

        DateTime now = new DateTime();
        // Creates a new DateTime object rounded to the next hour to begin scheduling
        DateTime dateTime = new DateTime(now.getYear(), now.getMonthOfYear(), now.getDayOfMonth(), now.getHourOfDay() + 1, 0);

        while (!pq.isEmpty()) {
            // If the next hour does not already have a scheduled event
            if (availabilities.get(dateTime.getMillis()) != null) {
                // Schedule task with the highest priority next
                TaskifyTask taskToSchedule = pq.remove();
                // Create a one hour calender event for the task
                TaskifyCalendarEvent scheduledEvent = new TaskifyCalendarEvent(new Interval(dateTime, dateTime.plusHours(1)), taskToSchedule);
                // Allocate this hour block to this event
                availabilities.put(dateTime.getMillis(), scheduledEvent);
                // Add this event to the schedule
                schedule.add(scheduledEvent);
                // Increment task process by one hour
                taskToSchedule.incrementTaskProcessByHour();

                // If the task is not completed, add it back to the priority queue for rescheduling
                if (!taskToSchedule.isCompleted()) {
                    pq.add(taskToSchedule);
                }
            }

            dateTime = dateTime.plusHours(1);
        }
    }
}
