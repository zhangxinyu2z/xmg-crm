package com._520it.crm.service;

import com._520it.crm.domain.Guarantee;
import com._520it.crm.req.PageReq;
import com._520it.crm.resp.PageResult;

import java.util.List;

public interface IGuaranteeService {

    /**
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * @param record
     * @return
     */
    int save(Guarantee record);

    /**
     * @param id
     * @return
     */
    Guarantee selectByPrimaryKey(Long id);

    /**
     * @return
     */
    List<Guarantee> selectAll();

    /**
     * @param record
     * @return
     */
    int update(Guarantee record);

    /**
     * 所有的保修单
     * 动态查询+分页
     *
     * @param qo
     * @return
     */
    PageResult queryByCondition(PageReq qo);

    /**
     * 修改延保时间
     * @param id
     * @return
     */
    int updateTime(Long id);

}
