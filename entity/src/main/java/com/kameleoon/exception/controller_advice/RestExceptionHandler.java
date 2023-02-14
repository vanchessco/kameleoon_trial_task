package com.kameleoon.exception.controller_advice;


import com.kameleoon.exception.NotFoundException;
import com.kameleoon.exception.QuoteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ExceptionDetail> handleNotFoundException(WebRequest request, NotFoundException e) {
        ExceptionDetail ex = ExceptionDetailConstructor.construct(request, e);
        ex.setTitle("Object not found");
        ex.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(ex, null, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionDetail> handleQuoteException(WebRequest request, QuoteException e) {
        ExceptionDetail ex = ExceptionDetailConstructor.construct(request, e);
        ex.setTitle("Unprocessable entity");
        ex.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());

        return new ResponseEntity<>(ex, null, HttpStatus.UNPROCESSABLE_ENTITY);
    }


    @ExceptionHandler
    public ResponseEntity<ExceptionDetail> handleIllegalArgumentException(WebRequest request, IllegalArgumentException e) {
        ExceptionDetail ex = ExceptionDetailConstructor.construct(request, e);
        ex.setTitle("Illegal argument");
        ex.setStatus(HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(ex, null, HttpStatus.BAD_REQUEST);
    }
}
