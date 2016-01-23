package com.example.johnny.pennapps.Model.Events;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;

import java.util.List;

/**
 * Events to be scheduled
 *
 * Created by kevinlee on 1/23/16.
 */
public class TaskifyCommitment extends TaskifySchedulable {

    // Constant for highest priority for scheduling
    private static final double HIGHEST_PRIORITY = 0;
    // Constant for one day
    private static final int ONE_DAY = 1;
    // Constant for three days
    private static final int THREE_DAYS = 3;
    // Constant for five days
    private static final int SIX_DAYS = 6;
    // Constant for one week
    private static final int ONE_WEEK_IN_DAYS = 7;
    // Constant for two weeks
    private static final int TWO_WEEKS_IN_DAYS = 14;
    // Constant for one month
    private static final int ONE_MONTH_IN_DAYS = 30;

    // Start time of this event
    private LocalTime startTime;
    // End time of this event
    private LocalTime endTime;
    //Start date of this event
    private LocalDate startDate;
    private LocalDate endDate;
    // Whether or not this event repeats
    private boolean repeats;
    // How often and event repeats
    private RepeatingInterval repeatingInterval;
    // List of scheduled times to be dedicated to a task
    private List<Interval> scheduledTimes;

    /**
     * Constructor for events that occur once
     *
     * @param name          name of event
     * @param startDateTime starting date and time of event
     * @param endDateTime   ending date and time of event
     */
    public TaskifyCommitment(String name, DateTime startDateTime, DateTime endDateTime) {
        this.name = name;
        this.priority = HIGHEST_PRIORITY;
        this.repeats = false;
        this.startTime = startDateTime.toLocalTime();
        this.endTime = endDateTime.toLocalTime();
        this.startDate = startDateTime.toLocalDate();
        this.endDate = endDateTime.toLocalDate();

        generateScheduledTimes();
    }


    /**
     * Constructor for repeating events
     *
     * @param name              name of event
     * @param startTime         starting time of the event
     * @param endTime           ending time of the event
     * @param startDate         starting date of the event
     * @param endDate           ending date of the event
     * @param repeatingInterval interval for which the event repeats
     */
    public TaskifyCommitment(String name, LocalTime startTime, LocalTime endTime, LocalDate startDate, LocalDate endDate, RepeatingInterval repeatingInterval) {
        this.name = name;
        this.priority = HIGHEST_PRIORITY;
        this.repeats = true;
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
        this.endDate = endDate;
        this.repeatingInterval = repeatingInterval;

        generateScheduledTimes();
    }

    public void generateScheduledTimes() {

        // Clear all current scheduled events
        scheduledTimes.clear();

        // Create DateTime object for start of first iteration
        DateTime scheduleStart = new DateTime(startDate.getYear(), startDate.getMonthOfYear(), startDate.getDayOfMonth(), startTime.getHourOfDay(), startTime.getMinuteOfHour());
        // Create DateTime object for end of first
        DateTime scheduleEnd = new DateTime (endDate.getYear(), endDate.getMonthOfYear(), endDate.getDayOfMonth(), endTime.getHourOfDay(), endTime.getMinuteOfHour());

        if (!repeats) {

            // Only add a single instance if the schedule does not repeat
            scheduledTimes.add(new Interval(scheduleStart.toInstant(), scheduleEnd.toInstant()));
        } else {
            // Loop while the start of the schedule event is before the end date
            while(scheduleStart.isBefore(endDate.toDateTime(new LocalTime(0,0)))) {
                // Add the current schedule Interval to the list of scheduled times
                scheduledTimes.add(new Interval(scheduleStart.toInstant(), scheduleEnd.toInstant()));

                // Increment the start and end times by number of days depending on repeatingInterval
                switch (repeatingInterval) {
                    case DAILY:
                        scheduleStart.plusDays(ONE_DAY);
                        scheduleEnd.plusDays(ONE_DAY);
                        break;
                    case WEEKLY:
                        scheduleStart.plusDays(ONE_WEEK_IN_DAYS);
                        scheduleEnd.plusDays(ONE_WEEK_IN_DAYS);
                        break;
                    case BIWEEKLY:
                        scheduleStart.plusDays(TWO_WEEKS_IN_DAYS);
                        scheduleEnd.plusDays(TWO_WEEKS_IN_DAYS);
                        break;
                    case MONTHLY:
                        scheduleStart.plusDays(ONE_MONTH_IN_DAYS);
                        scheduleEnd.plusDays(ONE_MONTH_IN_DAYS);
                        break;
                    case WEEKDAYS:
                        if (scheduleStart.getDayOfWeek() == DateTimeConstants.FRIDAY) {
                            scheduleStart.plusDays(THREE_DAYS);
                            scheduleEnd.plusDays(THREE_DAYS);
                        } else {
                            scheduleStart.plusDays(ONE_DAY);
                            scheduleEnd.plusDays(ONE_DAY);
                        }
                        break;
                    case WEEKENDS:
                        if (scheduleStart.getDayOfWeek() == DateTimeConstants.SUNDAY) {
                            scheduleStart.plusDays(SIX_DAYS);
                            scheduleEnd.plusDays(SIX_DAYS);
                        } else {
                            scheduleStart.plusDays(ONE_DAY);
                            scheduleEnd.plusDays(ONE_DAY);
                        }
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public String toString() {
        return "Task: " + this.name + "; Start time: " + this.startTime + "; End time: " + this.endTime;
    }
}
