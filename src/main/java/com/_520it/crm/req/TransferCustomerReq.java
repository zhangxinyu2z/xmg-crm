package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author zhangxinyu
 * @date 2024/9/24
 **/
@Setter
@Getter
public class TransferCustomerReq {

    /**
     * 客户id
     */
    private Long customerId;

    /** 移交人 */
    private Long transUserId;

    /** 原负责人 */
    private Long oldSellerId;

    /**
     * 新负责人
     */
    private Long newSellerId;

    /** 移交原因 */
    private String transReason;
}
