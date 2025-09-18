package ru.redtoss.kode.fooddeliveryservice.controllers;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;


public interface ShowErrorMessage {

    @ExceptionHandler
    default String showErrorMessage(BindingResult bindingResult) {
        StringBuilder errorsMsg = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors) {
            errorsMsg.append(fieldError.getField())
                    .append(" : ")
                    .append(fieldError.getDefaultMessage())
                    .append(";");
        }
        return errorsMsg.toString();
    }
}
