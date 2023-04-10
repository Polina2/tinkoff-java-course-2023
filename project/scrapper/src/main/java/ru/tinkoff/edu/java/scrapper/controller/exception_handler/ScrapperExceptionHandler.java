package ru.tinkoff.edu.java.scrapper.controller.exception_handler;

import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ServerWebInputException;
import ru.tinkoff.edu.java.scrapper.dto.ApiErrorResponse;

@RestControllerAdvice
public class ScrapperExceptionHandler {

    @ExceptionHandler({TypeMismatchException.class, ServerWebInputException.class})
    public ResponseEntity<ApiErrorResponse> handleBadRequestError(Exception e){
        ApiErrorResponse response = createError(e, HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(400).body(response);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ApiErrorResponse> handleNotFoundError(Exception e){
        ApiErrorResponse response = createError(e, HttpStatus.NOT_FOUND.getReasonPhrase(), HttpStatus.NOT_FOUND.toString());
        return ResponseEntity.status(404).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleInternalServerError(Exception e){
        ApiErrorResponse response = createError(
                e,
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                HttpStatus.INTERNAL_SERVER_ERROR.toString()
        );
        return ResponseEntity.status(500).body(response);
    }

    private ApiErrorResponse createError(Exception ex, String description, String code){
        return new ApiErrorResponse(description, code, ex.toString(), ex.getMessage(), null);
    }
}
