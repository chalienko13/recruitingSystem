package com.netcracker.solutions.kpi.service.impl;

import com.netcracker.solutions.kpi.persistence.dao.DaoUtil;
import com.netcracker.solutions.kpi.service.DaoUtilService;

/**
 * Created by IO on 21.05.2016.
 */
@Deprecated
public class DaoUtilServiceImpl implements DaoUtilService {

    private DaoUtil daoUtil;

    public DaoUtilServiceImpl(DaoUtil daoUtil) {
        this.daoUtil = daoUtil;
    }

    @Override
    public boolean connectionTest() {
        return daoUtil.checkConnection();
    }
}
