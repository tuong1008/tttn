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
//        Session session = sessionFactory.getCurrentSession();
//        String hql = "FROM NhaCungCap";
//        Query query = session.createQuery(hql);
//        return (ArrayList<NhaCungCap>) query.list();
        return getFromQuery("FROM NhaCungCap", NhaCungCap.class);
    }

}
