package com.example.johnny.pennapps;
//package com.example.johnny.pennapps.Model.Events.TaskifySchedulable;


import java.util.PriorityQueue;
import java.util.ArrayList;
import com.example.johnny.pennapps.Model.Events.TaskifySchedulable;


/**
 * Created by jasonlin on 1/22/16.
 */
public class SchedulableQueue {

    public PriorityQueue<TaskifySchedulable> requestQueue;
    public SchedulableQueue(PriorityQueue<TaskifySchedulable> requestQueue) {
        this.requestQueue = requestQueue;
    }

    public ArrayList<TaskifySchedulable> schedule () {
        ArrayList<TaskifySchedulable> schedules = new ArrayList<TaskifySchedulable>();
        return schedules;
    }
}
