package com.hopebaron.noteapp;

public class EmptyNoteParameterException extends Exception {
    public EmptyNoteParameterException(String parameterName) {
        super(String.format("%s can't be empty", parameterName));
    }
}