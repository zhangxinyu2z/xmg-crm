package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 部门表
 * 
 * @author zhang xinyu
 * @date 2021/06/15
 */
@Setter@Getter
public class Department implements Serializable {
	private static final long serialVersionUID = -2379248016835247601L;
	private Long id;
	private String sn;
	private String name;
	private Employee manager;
	private Department parent;
	private Boolean state;
}