//package com.cs323.assign1.stateActions;
public enum Direction {

    UP(0,"Up"),
    RIGHT(1,"Right"),
    DOWN(2,"Down"),
    LEFT(3,"Left");
    private final int code;
    private final String description;

//constructor
    private Direction(final int code, final String description) {
        this.code = code;
        this.description = description;
    }

//Getter for code
    public int getCode() {
        return code;
    }

//Getter for description
    public String getDescription() {
        return description;
    }

//Overloads the toString and returns the description of the move.

    @Override
    public String toString() {
        return description;
    }
}
