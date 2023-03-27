package ru.tinkoff.edu.java.scrapper.controller.exception_handler;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;

@RestControllerAdvice
public class ScrapperExceptionHandler {

    @ExceptionHandler(TypeMismatchException.class)
    protected ResponseEntity<ApiErrorResponse> handleBadRequestError(Exception e){
        ApiErrorResponse response = createError(e, HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(400).body(response);
        //return handleExceptionInternal(e, body, new HttpHeaders(), HttpStatusCode.valueOf(400), request);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiErrorResponse> handleNotFoundError(Exception e){
        ApiErrorResponse response = createError(e, HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(404).body(response);
    }

    private ApiErrorResponse createError(Exception ex, String description, String code){
        return new ApiErrorResponse(description, code, ex.toString(), ex.getMessage(), null);
    }
}
