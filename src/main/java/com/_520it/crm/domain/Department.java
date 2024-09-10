package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 部门表
 * 
 * @author zhang xinyu
 * @date 2021/06/15
 */
@Setter@Getter
public class Department {
	private Long id;
	private String sn;
	private String name;
	private Employee manager;
	private Department parent;
	private Boolean state;
}