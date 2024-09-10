package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 移交记录vo
 *
 * @author zhangxinyu
 */
@Setter
@Getter
public class CustomerTransferVO {
    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 新负责人
     */
    private Long inchargeuserId;
    private String reason;
}
