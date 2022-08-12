package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.NhaCungCap;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class NhaCungCapDAOImpl implements NhaCungCapDAO {

    public ArrayList<NhaCungCap> getSuppliers(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        String hql = "FROM NhaCungCap";
        Query query = session.createQuery(hql);
        return (ArrayList<NhaCungCap>) query.list();
    }

}
