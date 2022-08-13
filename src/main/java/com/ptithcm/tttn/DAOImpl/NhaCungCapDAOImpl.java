package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.NhaCungCapDAO;
import com.ptithcm.tttn.entity.NhaCungCap;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class NhaCungCapDAOImpl extends AbstractDao<NhaCungCap> implements NhaCungCapDAO {
    @Autowired
    SessionFactory sessionFactory;

    public ArrayList<NhaCungCap> getSuppliers() {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM NhaCungCap";
        Query query = session.createQuery(hql);
        return (ArrayList<NhaCungCap>) query.list();
    }

}
