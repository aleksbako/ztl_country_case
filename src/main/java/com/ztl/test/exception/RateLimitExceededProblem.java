package com.ztl.test.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class RateLimitExceededProblem extends RuntimeException {
    public RateLimitExceededProblem(String message) {
        super(message);
    }

}