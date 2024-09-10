package com._520it.crm.service.impl;

import com._520it.crm.domain.Guarantee;
import com._520it.crm.mapper.GuaranteeMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.IGuaranteeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author zhangxinyu
 */
@Service
public class GuaranteeServiceImpl implements IGuaranteeService {
    @Autowired
    private GuaranteeMapper dao;

    @Override
    public int delete(Long id) {
        //删除保修但是把对应的明细也删掉
        int effectCount = dao.deleteByPrimaryKey(id);
        dao.deleteByItems(id);
        return effectCount;
    }

    @Override
    public int save(Guarantee record) {

        return dao.insert(record);
    }

    @Override
    public Guarantee selectByPrimaryKey(Long id) {

        return dao.selectByPrimaryKey(id);
    }

    @Override
    public List<Guarantee> selectAll() {

        return dao.selectAll();
    }

    @Override
    public int update(Guarantee record) {

        return dao.updateByPrimaryKey(record);
    }


    @Override
    public PageResult queryByCondition(QueryObject qo) {
        Long count = dao.selectByConditionCount(qo);
        if (count > 0) {
            List<Guarantee> result = dao.selectByCondition(qo);
            return new PageResult(count, result);
        } else {
            return PageResult.EMPTY;
        }

    }

    /**
     * {@inheritDoc}
     * 延保一年
     *
     * @param id
     * @return
     */
    @Override
    public int updateTime(Long id) {
        Guarantee guarantee = dao.selectByPrimaryKey(id);
        Calendar c = Calendar.getInstance();
        c.setTime(guarantee.getDuetime());
        c.add(Calendar.YEAR, 1);
        Date date = c.getTime();
        guarantee.setDuetime(date);
        return dao.updateByPrimaryKey(guarantee);
    }
}
