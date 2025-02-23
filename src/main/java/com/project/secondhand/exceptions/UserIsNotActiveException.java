package com.project.secondhand.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserIsNotActiveException extends RuntimeException {
    public UserIsNotActiveException(String message) {super(message);}
}
