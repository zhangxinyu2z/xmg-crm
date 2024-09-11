package com._520it.crm.resp;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

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
	private Long total;
	/** 分页表数据 */
	private List rows;

	public static final PageResult EMPTY = new PageResult(0L, Collections.emptyList());

	private Map<String, Object> data = new HashMap();

	public PageResult(Long total, List rows) {
		super();
		this.total = total;
		this.rows = rows;
	}
}
