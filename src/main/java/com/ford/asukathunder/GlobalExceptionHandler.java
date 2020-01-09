package com.ford.asukathunder;

import com.ford.asukathunder.common.exception.BaseException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.core.annotation.AnnotatedElementUtils.findMergedAnnotation;

/**
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private Log log = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Message> exceptionHandle(Exception e) {
        StringBuilder stackInfo = new StringBuilder(e.toString());
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        for (StackTraceElement stackTraceElement : stackTraceElements) {
            stackInfo.append("\r\n\t").append(stackTraceElement);
        }
        log.error(stackInfo);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        Message message = new Message(status.value(), e.getCause().toString(), e.getLocalizedMessage());
        return new ResponseEntity<>(message, status);
    }

    @ExceptionHandler(BaseException.class)
    @ResponseBody
    public ResponseEntity<Message> handle(BaseException e) {
        ResponseStatus responseStatus = findMergedAnnotation(e.getClass(), ResponseStatus.class);
        HttpStatus status = responseStatus != null ? responseStatus.value() : HttpStatus.INTERNAL_SERVER_ERROR;
        Message message;
        if (e.getErrorCode() != null) {
            message = new Message(e.getErrorCode().getStatus(), e.getErrorCode().getCode(), e.getErrorCode().getReason());
        } else {
            message = new Message(status.value(), e.getCause().toString(), e.getLocalizedMessage());
        }
        return new ResponseEntity<Message>(message, status);
    }


    public class Message {

        private Integer status;
        private String code;
        private String reason;

        public Message() {
        }

        public Message(Integer status, String code, String reason) {
            this.status = status;
            this.code = code;
            this.reason = reason;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

}
