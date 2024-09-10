package com._520it.crm.service;

import java.util.List;

import com._520it.crm.domain.Log;

/**
 * @author zhang xinyu
 * @date 2021/06/19
 */
public interface LogService {
	int deleteByPrimaryKey(Long id);

    int insert(Log record);

    Log selectByPrimaryKey(Long id);

    List<Log> selectAll();

    int updateByPrimaryKey(Log record);
}
