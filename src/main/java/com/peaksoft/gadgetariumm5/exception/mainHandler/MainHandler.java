package com.peaksoft.gadgetariumm5.exception.mainHandler;

import com.peaksoft.gadgetariumm5.exception.IncorrectLoginException;
import com.peaksoft.gadgetariumm5.exception.NotFoundException;
import com.peaksoft.gadgetariumm5.exception.UserAlreadyExistException;
import com.peaksoft.gadgetariumm5.exception.exceptionResponse.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MainHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ExceptionResponse notFoundException(NotFoundException e) { // если сущность  не была найдена
        return new ExceptionResponse(HttpStatus.NOT_FOUND, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(IncorrectLoginException.class) // если (sign-up) почта или пароль не вписываются в дресс код
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse incorrectLoginHandle(IncorrectLoginException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class) // если (sign-in) данные почты/пароля не правильные
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse badCredentialHandle(BadCredentialsException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, e.getClass().getName(), e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistException.class)  // при регистрации(sign-up) дана уже существующая почта
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionResponse userAlreadyExist(UserAlreadyExistException e) {
        return new ExceptionResponse(HttpStatus.FORBIDDEN, e.getClass().getName(), e.getMessage());
    }
}



