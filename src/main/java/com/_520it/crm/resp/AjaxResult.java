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
public class AjaxResult implements Serializable {

    private static final long serialVersionUID = 7913297459170624556L;
    private boolean success;
    private String msg;

    public AjaxResult() {
    }

    public AjaxResult(String msg) {
        super();
        this.msg = msg;
    }

    public AjaxResult(boolean success, String msg) {
        super();
        this.success = success;
        this.msg = msg;
    }

    public static AjaxResult createResponse() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        ajaxResult.setMsg("操作成功");
        return ajaxResult;
    }

    public static AjaxResult createResponse(String message) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(true);
        ajaxResult.setMsg(message);
        return ajaxResult;
    }

    public static AjaxResult createResponse(boolean success, String message) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setSuccess(success);
        ajaxResult.setMsg(message);
        return ajaxResult;
    }

    public static void setFailResponse(AjaxResult ajaxResult) {
        ajaxResult.setSuccess(false);
        ajaxResult.setMsg("操作失败");
    }

    public static void setFailResponse(AjaxResult ajaxResult, String msg) {
        ajaxResult.setSuccess(false);
        ajaxResult.setMsg(msg);
    }

}
