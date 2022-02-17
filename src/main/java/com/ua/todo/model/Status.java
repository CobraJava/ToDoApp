package com.ua.todo.model;

public enum Status {

    NEW("NEW"), DONE("DONE");

    private String value;

    private Status(String value) {
        this.value = value;
    }
}
