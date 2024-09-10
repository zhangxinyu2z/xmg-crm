package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Setter
@Getter
public class Log  implements Serializable {
	private static final long serialVersionUID = 6966680481832834664L;
	private Long id;

	private Employee opuser;

	private Date optime;

	private String opip;

	private String function;

	private String params;
}