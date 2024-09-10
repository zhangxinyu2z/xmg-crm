package com._520it.crm.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 树形菜单需要的参数
 * 
 * @author xinyu
 * @date 2021/06/21
 */
@Setter
@Getter
public class Menu {
	/** 节点id */
	private Long id;

	/** 节点文本 */
	private String text;

	/** 节点图标id */
	private String iconcls;

	/** 节点是否被选中 */
	private Boolean checked;

	/** 节点状态，'open' 或 'closed' */
	private String state;

	/** 节点自定义属性 */
	private String attributes;

	/** 子节点 */
	private List<Menu> children;

	/** 菜单访问权限匹配的表达式 */
	private String function;
}