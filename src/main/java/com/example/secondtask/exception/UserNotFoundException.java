package com.example.secondtask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super("Could not find user '" + userId + "'.");
    }

    public UserNotFoundException(String email) {
        super("Could not find user with email: '" + email + " ' ");
    }
}
