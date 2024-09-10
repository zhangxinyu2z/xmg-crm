package com._520it.crm.util;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhang xinyu
 * @date 2021/06/18
 */
@Setter
@Getter
public class AJAXResult {

	private boolean success; // 默认为false
	private String msg; // 提示信息

	public AJAXResult(String msg) {
		super();
		this.msg = msg;
	}

	public AJAXResult(boolean success, String msg) {
		super();
		this.success = success;
		this.msg = msg;
	}

}
