package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/9/20.
 */
@Setter
@Getter
public class CustomerResourcePoolQueryObject extends PageReq {
    private Long userId;
    private String keyword; // 姓名 和电话
    private Integer status; // 开发状态
}
