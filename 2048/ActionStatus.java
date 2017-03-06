//package com.cs323.assign1.stateActions;

public enum ActionStatus {
    CONTINUE(0, "Successful move, the game continues."),
    WIN(1, "You won, the game ended!"),
    NO_MORE_MOVES(2,"No more moves, the game ended!"),
    INVALID_MOVE(3,"Invalid move!");

    private final int code;
    private final String description;

//constructor
    private ActionStatus(final int code, final String description) {
        this.code = code;
        this.description = description;
    }

//getter for code
    public int getCode() {
        return code;
    }

//getter for description
    public String getDescription() {
        return description;
    }
}
