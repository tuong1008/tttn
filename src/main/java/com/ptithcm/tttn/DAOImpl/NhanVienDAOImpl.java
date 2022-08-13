package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.NhanVienDAO;
import com.ptithcm.tttn.entity.NhanVien;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class NhanVienDAOImpl extends AbstractDao<NhanVien> implements NhanVienDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public NhanVien getStaff(String username) {
        Session session = factory.getCurrentSession();
        String hql = "FROM NhanVien where tenDN = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", username);
        return (NhanVien) query.list().get(0);
    }

    public NhanVien getStaffByID(String id) {
        Session session = factory.getCurrentSession();
        String hql = "FROM NhanVien where maNV = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return (NhanVien) query.list().get(0);
    }

    @Override
    public ArrayList<NhanVien> getAllStaff(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        String hql = "FROM NhanVien";
        Query query = session.createQuery(hql);
        return (ArrayList<NhanVien>) query.list();
    }

    @Override
    public ArrayList<NhanVien> searchAllStaff(String hoTen) {
        Session session = factory.getCurrentSession();
        String hql = "FROM NhanVien Where hoTen like '%" + hoTen + "%'";
        Query query = session.createQuery(hql);
        return (ArrayList<NhanVien>) query.list();
    }


    @Override
    public Integer getMaxNumberByName(String username) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT max(substring(tenDN,length(tenDN),length(tenDN))) FROM NhanVien WHERE substring(tenDN,1,length(tenDN)-1) =:name";
        Query query = session.createQuery(hql);
        query.setParameter("name", username);
        List<String> list = query.list();
        System.out.println(list);
        return list.isEmpty() ? 0 : Integer.parseInt(list.get(0));
    }

}
