package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class SalaryQueryObejct extends PageReq{

	
	 //查询条件
    private String keyword;
    private Integer year;
    private Integer month;
    private Long eid;
	
}
