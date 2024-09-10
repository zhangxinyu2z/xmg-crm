package com._520it.crm.resp;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author zhang xinyu
 * @date 2021/06/18
 */
@Setter
@Getter
public class AJAXResult implements Serializable {

    private static final long serialVersionUID = 7913297459170624556L;
    private boolean success;
    private String msg;

    public AJAXResult() {
    }

    public AJAXResult(String msg) {
        super();
        this.msg = msg;
    }

    public AJAXResult(boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    public static AJAXResult createResponse() {
        AJAXResult ajaxResult = new AJAXResult();
        ajaxResult.setSuccess(true);
        ajaxResult.setMsg("操作成功");
        return ajaxResult;
    }

    public static AJAXResult createResponse(String message) {
        AJAXResult ajaxResult = new AJAXResult();
        ajaxResult.setSuccess(true);
        ajaxResult.setMsg(message);
        return ajaxResult;
    }

    public static AJAXResult createResponse(boolean success, String message) {
        AJAXResult ajaxResult = new AJAXResult();
        ajaxResult.setSuccess(success);
        ajaxResult.setMsg(message);
        return ajaxResult;
    }

}
