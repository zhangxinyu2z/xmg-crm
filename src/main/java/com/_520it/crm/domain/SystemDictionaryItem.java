package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据字典明细
 * 
 * @author xinyu
 * @date 2021/06/26
 */
@Setter
@Getter
public class SystemDictionaryItem {
	private Long id;

	private String name;

	private String intro;

	private SystemDictionary parent;
	
	/**
	 * 1：禁用	0：正常
	 */
	private Boolean state;
}