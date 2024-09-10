package com._520it.crm.service.impl;

import com._520it.crm.domain.GuaranteeItem;
import com._520it.crm.mapper.GuaranteeItemMapper;
import com._520it.crm.service.IGuaranteeItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuaranteeItemServiceImpl implements IGuaranteeItemService {
    @Autowired
    private GuaranteeItemMapper dao;

    @Override
    public int delete(Long id) {

        return dao.deleteByPrimaryKey(id);
    }

    @Override
    public int save(GuaranteeItem record) {
        return dao.insert(record);
    }

    @Override
    public GuaranteeItem selectByPrimaryKey(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public List<GuaranteeItem> selectAll() {
        return dao.selectAll();
    }

    @Override
    public int update(GuaranteeItem record) {
        return dao.updateByPrimaryKey(record);
    }

    /**
     * 根据保修单查询对应的明细
     */
    @Override
    public List<GuaranteeItem> selectAllItem(Long id) {
        return dao.selectAllItem(id);
    }

    /**
     * 查询当前保修单对应的保修记录次数，如果保修次数操作3次不再满足条件
     *
     * @param id
     * @return
     */
    @Override
    public Long selectItems(Long id) {
        return dao.selectItems(id);
    }

    /**
     * 根据明细的状态查询出对应的明细集合
     */
    @Override
    public List<GuaranteeItem> queryStatusByItem(int status) {
        return dao.queryStatusByItem(status);
    }

}
