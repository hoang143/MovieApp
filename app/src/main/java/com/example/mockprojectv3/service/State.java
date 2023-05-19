package com.example.mockprojectv3.service;
public class State<T> {
    private Status status;
    private T data;
    private String message;

    public State(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public enum Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    public static <T> State<T> success(T data) {
        return new State<>(Status.SUCCESS, data, null);
    }

    public static <T> State<T> error(String message) {
        return new State<>(Status.ERROR, null, message);
    }

    public static <T> State<T> loading() {
        return new State<>(Status.LOADING, null, null);
    }
}
