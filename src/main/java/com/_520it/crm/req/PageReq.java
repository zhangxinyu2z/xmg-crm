package com._520it.crm.req;

import lombok.Getter;
import lombok.Setter;

/**
 * 页面传递的分页信息
 *
 * @author xinyu
 * @date 2021/06/19
 */
@Setter
@Getter
public class PageReq {
    /**
     * 请求的是第几页
     */
    private Integer page;

    /**
     * 请求页的记录条数
     */
    private Integer rows;

    /**
     * 查询记录的起始位置
     */
    public Integer getStart() {
        return this.page == null ? 0 : (this.page - 1) * this.rows;
    }
}
