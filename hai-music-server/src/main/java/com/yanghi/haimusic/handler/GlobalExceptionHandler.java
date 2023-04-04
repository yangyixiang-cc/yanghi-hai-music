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
     * 当控制器方法的参数校验失败时，会抛出 MethodArgumentNotValidException 异常，
     * 此时可以通过定义全局异常处理器中的 handleMethodArgumentNotValidException 方法来处理该异常。
     * @param e
     * @return
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        StringBuilder errorMsg = new StringBuilder();
        for (ObjectError error : allErrors) {
            errorMsg.append(error.getDefaultMessage()).append("; ");
        }
        return Result.failed(101, errorMsg.toString());
    }

    //  处理绑定异常

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
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        return Result.failed(e.getMessage());
    }
}