package com.cfex.customer.exception;

import com.cfex.customer.entity.response.ResultEntity;
import com.cfexlib.datasource.exception.AWSSecretAccessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.ParseException;

@Slf4j
//@Generated
@RestControllerAdvice
public class GlobalExceptionHandler {
    // TODO service error stack and requestBody

    @ExceptionHandler(value = {Exception.class})
    public ResultEntity<Void> exceptionHandler(HttpServletRequest request, Exception e) {
        return ResultEntity.<Void>of().error(request)
                .code("B0000000")
                .exception(e);
    }

    @ExceptionHandler(value = {CustomerServiceException.class})
    public ResultEntity<Void> customerServiceExceptionHandler(HttpServletRequest request, CustomerServiceException e) {
        return ResultEntity.<Void>of().error(request)
                .code(e.getCode())
                .exception(e);
    }

    @ExceptionHandler(value = {DataNotFoundException.class})
    public ResultEntity<Void> dataNotFoundExceptionHandler(HttpServletRequest request, DataNotFoundException e) {
        return ResultEntity.<Void>of().error(request)
                .code(e.getCode())
                .exception(e);
    }

    @ExceptionHandler(value = {MissingServletRequestParameterException.class})
    public ResultEntity<Void> missingParameterExceptionHandler(HttpServletRequest request, MissingServletRequestParameterException e) {
        return ResultEntity.<Void>of().error(request)
                .code("A0100400")
                .exception(e);
    }

    @ExceptionHandler(value = {ValidationException.class})
    public ResultEntity<Void> validationExceptionHandler(HttpServletRequest request, ValidationException e) {
        return ResultEntity.<Void>of().error(request)
                .code("A0100400")
                .exception(e);
    }

//    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
//    public ResultEntity<Void> notValidExceptionHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
//        return ResultEntity.<Void>of().error(request)
//                .code("A0100400")
//                .publicMessage(e.getAllErrors().getFirst().getDefaultMessage())
//                .privateMessage(e.getAllErrors().getFirst().getDefaultMessage());
//    }

    @ExceptionHandler(value = {BindException.class})
    public ResultEntity<Void> bindExceptionHandler(HttpServletRequest request, BindException e) {
        return ResultEntity.<Void>of().error(request)
                .code("A0100400")
                .exception(e);
    }

    @ExceptionHandler(value = {ParseException.class})
    public ResultEntity<Void> parseExceptionHandler(HttpServletRequest request, ParseException e) {
        return ResultEntity.<Void>of().error(request)
                .code("A0100400")
                .exception(e);
    }

    @ExceptionHandler(value = {AWSSecretAccessException.class})
    public ResultEntity<Void> awsSecretAccessExceptionHandler(HttpServletRequest request, AWSSecretAccessException e) {
        return ResultEntity.<Void>of().error(request)
                .code("A0100110")
                .exception(e);
    }

}
