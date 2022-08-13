package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.LoaiSPDAO;
import com.ptithcm.tttn.entity.LoaiSP;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class LoaiSPDAOImpl extends AbstractDao<LoaiSP> implements LoaiSPDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<LoaiSP> getListCategory() {
        return getFromQuery("FROM LoaiSP", LoaiSP.class);
    }

    @Override
    public List<LoaiSP> searchByName(String name) {
        return getFromQuery("FROM LoaiSP a WHERE a.tenLoai LIKE ?", LoaiSP.class, "%" + name + "%");
    }
}
