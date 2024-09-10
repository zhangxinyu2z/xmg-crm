package com._520it.crm.service;

import com._520it.crm.domain.GuaranteeItem;

import java.util.List;

public interface IGuaranteeItemService {
    int delete(Long id);

    int save(GuaranteeItem record);

    GuaranteeItem selectByPrimaryKey(Long id);

    List<GuaranteeItem> selectAll();

    int update(GuaranteeItem record);

    List<GuaranteeItem> selectAllItem(Long id);

    Long selectItems(Long id);

    List<GuaranteeItem> queryStatusByItem(int status);
}
