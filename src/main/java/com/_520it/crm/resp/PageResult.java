package com._520it.crm.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 组件需要的数据信息
 * 
 * @author zhang xinyu
 * @date 2021/06/16
 */
@Setter
@Getter
public class PageResult {
	/** 当前页总记录数 */
	private Integer total;
	/** 分页表数据 */
	private List rows;

	public PageResult(Integer total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
}
