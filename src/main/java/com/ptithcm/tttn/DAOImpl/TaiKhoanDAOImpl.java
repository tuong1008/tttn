package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.TaiKhoanDAO;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class TaiKhoanDAOImpl extends AbstractDao<TaiKhoan> implements TaiKhoanDAO {
    @Autowired
    SessionFactory sessionFactory;

    @Override
    public String getRole(String pass, String userName) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM TaiKhoan C WHERE C.tenDN = :id AND C.matKhau = :pass";
        Query query = session.createQuery(hql);
        query.setParameter("id", userName);
        query.setParameter("pass", pass);
        List<TaiKhoan> list = query.list();
        return list.isEmpty() ? "" : list.get(0).getQuyen().getTenQuyen();
    }

    @Override
    public Integer updatePass(String newPass, String userName) {
        Session session = sessionFactory.getCurrentSession();
        try {
            String hql = "UPDATE TaiKhoan SET matKhau = :pass WHERE tenDN = :id";
            Query query = session.createQuery(hql);
            query.setParameter("pass", newPass);
            query.setParameter("id", userName);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.print(e);
            return 0;
        }
        return 1;
    }

    @Override
    public TaiKhoan getAccount(String username) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM TaiKhoan C WHERE C.tenDN = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", username);
        List<TaiKhoan> list = query.list();
        return list.isEmpty() ? null : list.get(0);
    }

}
