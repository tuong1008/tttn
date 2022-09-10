package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.NhaCungCapDAO;
import com.ptithcm.tttn.entity.NhaCungCap;
import java.util.ArrayList;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

public class NhaCungCapDAOImpl extends AbstractDao<NhaCungCap> implements NhaCungCapDAO {

    public List<NhaCungCap> getSuppliers() {
        return getFromQuery("FROM NhaCungCap", NhaCungCap.class);
    }

    @Override
    public ArrayList<NhaCungCap> searchAllSuppliers(String name) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        SQLQuery sqlQuery = session.createSQLQuery("EXEC TimKiemNCCTheoTen ?").addEntity(NhaCungCap.class);
        sqlQuery.setString(0, name);
        List<NhaCungCap> list = sqlQuery.list();
        session.getTransaction().commit();
        session.close();
        return (ArrayList<NhaCungCap>) list;
    }
}
