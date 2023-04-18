package ru.tinkoff.edu.java.bot.controller.exception_handler;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.bot.dto.ApiErrorResponse;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestControllerAdvice
public class BotExceptionHandler {

    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<ApiErrorResponse> handleBadRequestError(Exception e){
        ApiErrorResponse response = createError(e, HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(400).body(response);
    }

    private ApiErrorResponse createError(Exception ex, String description, String code){
        return new ApiErrorResponse(
                description, code, ex.toString(), ex.getMessage(),
                Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
        );
    }
}
