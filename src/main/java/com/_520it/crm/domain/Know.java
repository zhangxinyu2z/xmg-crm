package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 知识库
 */
@Setter@Getter
@Alias("Know")
public class Know {
    private Long id;
    private String context;
	@Override
	public String toString() {
		return "Know [id=" + id + ", context=" + context + "]";
	}
    
    
}