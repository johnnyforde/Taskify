package com.example.johnny.pennapps.Model.Events;

/**
 * Abstract class that defines the behavior for events and tasks that can be scheduled
 *
 * Created by kevinlee on 1/23/16.
 */
public abstract class TaskifySchedulable {

    protected String name;
    protected int priority;

    public String getName() {
        return name;
    }

    public abstract int getPriority();
}
