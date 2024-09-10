package com._520it.crm.service;

import com._520it.crm.domain.NetworkDisk;
import com._520it.crm.page.PageResult;
import com._520it.crm.query.NetworkDiskQueryObject;

/**
 *
 */
public interface INetworkDiskService {


    void save(NetworkDisk NetworkDisk);

    void delete(Long id);

    void update(NetworkDisk NetworkDisk);

    NetworkDisk get(Long id);

    PageResult query(NetworkDiskQueryObject qo);






}
