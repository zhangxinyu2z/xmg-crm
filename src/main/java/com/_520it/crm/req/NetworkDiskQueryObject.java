package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 *
 */
@Getter
@Setter
@ToString
public class NetworkDiskQueryObject extends PageReq {

    private String keyword;

    private Long pid;

    private Long currentId;
}
