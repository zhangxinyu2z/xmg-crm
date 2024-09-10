package com._520it.crm.web.exception;

/**
 * 自定义一个预期异常
 *
 * @author dhjy
 * @version v1.0
 * @date created in 2021-05-27 12:44
 */
public class MessageException extends Exception {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public MessageException() {
    }

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public MessageException(Throwable cause) {
        super(cause);
    }

    public MessageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
