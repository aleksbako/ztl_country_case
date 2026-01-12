package com.ztl.test.exception;

//Move to library
public class NotFoundProblem extends RuntimeException {
    public NotFoundProblem(String message) {
        super(message);
    }
}
