package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 字典明细
 * @author xinyu
 * @date 2021/06/26
 */
@Setter@Getter
public class SystemDictionaryItemQueryObject extends PageReq {
	/**
	 * 数据字典项id
	 */
	private Long id;
}
