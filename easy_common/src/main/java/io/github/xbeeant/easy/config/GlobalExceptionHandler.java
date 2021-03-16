package io.github.xbeeant.easy.config;


import io.github.xbeeant.core.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 全局异常拦截
 *
 * @author xiaobiao
 * @version 1.0.0
 * @date 2020/12/10
 */
@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ApiResponse<Object> dataIntegrityViolationException(Exception e, Throwable t) {
        logger.error("DataIntegrityViolationException", e);
        ApiResponse<Object> response = new ApiResponse<>();
        response.setResult(500, "SQL异常");
        return response;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ApiResponse<Object> illegalArgumentException(Exception e, Throwable t) {
        logger.error("IllegalArgumentException", e);
        ApiResponse<Object> response = new ApiResponse<>();
        response.setResult(500, "参数格式或值异常");
        return response;
    }
}
