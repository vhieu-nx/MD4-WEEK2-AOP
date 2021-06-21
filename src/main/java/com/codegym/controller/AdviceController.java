package com.codegym.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice

public class AdviceController {

    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception ex) {
        return "error";
    }

}
