package com._520it.crm.service.impl;

import com._520it.crm.domain.Vacate;
import com._520it.crm.mapper.VacateMapper;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.QueryObject;
import com._520it.crm.service.IVacateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangxinyu
 */
@Service
public class VacateServiceImpl implements IVacateService {

    @Autowired
    private VacateMapper dao;

    @Override
    public int save(Vacate vacate) {
        return dao.insert(vacate);
    }

    @Override
    public int delete(Long id) {
        Vacate vacate = dao.selectByPrimaryKey(id);

        if (vacate.getState() == Vacate.NORMAL) {
            dao.deleteByPrimaryKey(id);
            return 1;
        }
        return -1;
    }

    @Override
    public int update(Vacate vacate) {
        Vacate vac = dao.selectByPrimaryKey(vacate.getId());
        //判断是否处于审核状态
        if (vac.getState() == Vacate.NORMAL) {
            return dao.updateByPrimaryKey(vacate);
        }
        return 0;
    }

    @Override
    public Vacate get(Long id) {
        return dao.selectByPrimaryKey(id);
    }

    @Override
    public List<Vacate> listAll() {
        return dao.selectAll();
    }

    @Override
    public PageResult queryByCondition(QueryObject qo) {
        // 根据查询条件查询出总条数
        Long count = dao.queryByConditionCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<Vacate> listData = dao.queryByCondition(qo);
            return new PageResult(count, listData);
        }
    }

    @Override
    public PageResult queryVacateByEid(QueryObject qo) {
        // 根据查询条件查询出总条数
        Long count = dao.queryVacateByEidCount(qo);
        if (count == 0) {
            return PageResult.EMPTY;
        } else {
            // 返回查询的结果集
            List<Vacate> listData = dao.queryVacateByEid(qo);
            return new PageResult(count, listData);
        }
    }

    /**
     * 请假单审核
     *
     * @param vacate
     * @return
     */
    @Override
    public int audit(Vacate vacate) {
        System.out.println(vacate);
        System.out.println(vacate.getId());
        Vacate v = dao.selectByPrimaryKey(vacate.getId());
        if (v.getState() == Vacate.AUDIT) {
            return 0;
        }
        vacate.setBegintime(v.getBegintime());
        vacate.setEndtime(v.getEndtime());
        vacate.setEmployee(v.getEmployee());
        vacate.setState(Vacate.AUDIT);
        return dao.updateByPrimaryKey(vacate);
    }
}
