package org.example.exception;

public class AppException extends RuntimeException {   //继承RuntimeException属于运行时异常

    public AppException(String message) {
        super(message);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
}
