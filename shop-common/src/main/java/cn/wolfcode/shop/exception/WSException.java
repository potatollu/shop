package cn.wolfcode.shop.exception;

public class WSException extends RuntimeException{
    public WSException(String message) {
        super(message);
    }

    public WSException(String message, Throwable cause) {
        super(message, cause);
    }
}
