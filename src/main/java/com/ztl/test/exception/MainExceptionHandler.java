package com.ztl.test.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//Move to library
@RestControllerAdvice
public class MainExceptionHandler {

    @ExceptionHandler(NotFoundProblem.class)
    public ResponseEntity<ProblemDetail> handleNotFound(NotFoundProblem ex) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problem.setTitle("Resource not found");
        problem.setDetail(ex.getMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problem);
    }

    @ExceptionHandler(RateLimitExceededProblem.class)
    public ResponseEntity<?> RateLimitExceededException(final RateLimitExceededProblem e) {

        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.TOO_MANY_REQUESTS);
        problem.setTitle("Resource not found");
        problem.setDetail(e.getMessage());

        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(problem);
    }

}
