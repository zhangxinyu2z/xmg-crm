package com._520it.crm.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 数据字典
 */
@Getter@Setter
public class SystemDictionary implements Serializable {
    private static final long serialVersionUID = -5636511421137155406L;
    private Long id;

    private String sn;

    private String name;

    private String intro;

}