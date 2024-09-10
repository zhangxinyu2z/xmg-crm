package com._520it.crm.web.exception;

/**
 * Since:
 *
 * @author Zhang, Xinyu
 * @date 2024/9/10
 **/
public class CrmException extends RuntimeException {

    private int errorCode;
    private String errorMsg;

    public CrmException() {
        super();
    }

    public CrmException(String errorMsg) {
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public CrmException(String errorMsg, Throwable throwable) {
        super(errorMsg, throwable);
        this.errorMsg = errorMsg;
    }

    /**
     * 创建异常-返回message
     *
     * @param errorMessage
     * @param throwable
     * @return
     */
    public static CrmException createException(String errorMessage, Throwable throwable) {
        CrmException commonException;
        if (throwable instanceof CrmException) {
            commonException = (CrmException)throwable;
        } else {
            commonException = new CrmException(errorMessage, throwable);
        }
        return commonException;
    }
}
