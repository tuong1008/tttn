package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.QuyenDAO;
import com.ptithcm.tttn.entity.Quyen;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class QuyenDAOImpl extends AbstractDao<Quyen> implements QuyenDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public ArrayList<Quyen> getAllRole() {
        Session session = factory.getCurrentSession();
        String hql = "FROM Quyen";
        Query query = session.createQuery(hql);
        return (ArrayList<Quyen>) query.list();
    }

    @Override
    public Quyen getRole(int idRole) {
        Session session = factory.getCurrentSession();
        String hql = "FROM Quyen Where maQuyen =:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", idRole);
        ArrayList<Quyen> list = (ArrayList<Quyen>) query.list();
        return list.isEmpty() ? null : list.get(0);
    }

}
