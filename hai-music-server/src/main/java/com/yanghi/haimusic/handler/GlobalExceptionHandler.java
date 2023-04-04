package com.yanghi.haimusic.handler;

import com.yanghi.haimusic.utils.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

/**
 * 全局异常处理
 * @author 泗安
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理参数校验失败时抛出的 MethodArgumentNotValidException 异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleValidationException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : allErrors) {
            errorMsg.append(error.getDefaultMessage()).append("; ");
        }
        return Result.failed(101, errorMsg.toString());
    }

    /**
     * 处理参数绑定失败时抛出的 BindException 异常
     */
    @ExceptionHandler(BindException.class)
    public Result handleBindException(BindException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : allErrors) {
            errorMsg.append(error.getDefaultMessage()).append("; ");
        }
        return Result.failed(101, errorMsg.toString());
    }

    /**
     * 处理其他异常
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return Result.failed(e.getMessage());
    }

}