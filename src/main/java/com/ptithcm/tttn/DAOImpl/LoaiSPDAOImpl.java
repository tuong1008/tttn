package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.LoaiSPDAO;
import com.ptithcm.tttn.entity.LoaiSP;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public class LoaiSPDAOImpl implements LoaiSPDAO {

    @Override
    public ArrayList<LoaiSP> getListCategory(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        String hql = "FROM LoaiSP";
        Query query = session.createQuery(hql);
        return (ArrayList<LoaiSP>) query.list();
    }

}
