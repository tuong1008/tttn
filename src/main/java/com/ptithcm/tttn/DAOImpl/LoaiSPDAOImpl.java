package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.LoaiSPDAO;
import com.ptithcm.tttn.entity.LoaiSP;

import java.util.List;

public class LoaiSPDAOImpl extends AbstractDao<LoaiSP> implements LoaiSPDAO {
    @Override
    public List<LoaiSP> getListCategory() {
        return getFromQuery("FROM LoaiSP", LoaiSP.class);
    }
}
