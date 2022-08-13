package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.NhaCungCapDAO;
import com.ptithcm.tttn.entity.NhaCungCap;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class NhaCungCapDAOImpl extends AbstractDao<NhaCungCap> implements NhaCungCapDAO {
    @Autowired
    SessionFactory sessionFactory;

    public List<NhaCungCap> getSuppliers() {
        return getFromQuery("FROM NhaCungCap", NhaCungCap.class);
    }
}
