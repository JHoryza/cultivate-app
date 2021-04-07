package com.example.growapp;

import java.io.Serializable;

public class Habit implements Serializable {

    private int id;
    private String name;
    private int interval;
    private String timeUnit;
    private int currentState;
    private String timestamp;

    public Habit(String name, int interval, String timeUnit, int currentState, String timestamp) {
        this.name = name;
        this.interval = interval;
        this.timeUnit = timeUnit;
        this.currentState = currentState;
        this.timestamp = timestamp;
    }

    public Habit(int id, String name, int interval, String timeUnit, int currentState, String timestamp) {
        this.id = id;
        this.name = name;
        this.interval = interval;
        this.timeUnit = timeUnit;
        this.currentState = currentState;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }

    public int getCurrentState() {
        return currentState;
    }

    public void setCurrentState(int currentState) {
        this.currentState = currentState;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
