package com.guli.teacher.exception;

import com.guli.common.exception.EduException;
import com.guli.common.result.Result;
import com.guli.common.result.ResultCode;
import com.guli.common.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)//捕获全局异常Exception
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().message("出错了");
    }

    @ExceptionHandler(BadSqlGrammarException.class)
    @ResponseBody
    public Result error(BadSqlGrammarException e) {
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(ResultCode.SQL_ERROR).message("SQL语法错误");
    }

    @ExceptionHandler(EduException.class)
    @ResponseBody
    public Result error(EduException e){
        e.printStackTrace();
        log.error(ExceptionUtil.getMessage(e));
        return Result.error().code(e.getCode()).message(e.getMsg());
    }
}
