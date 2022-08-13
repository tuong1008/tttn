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
        List<TaiKhoan> list = getFromQuery("FROM TaiKhoan C WHERE C.tenDN = ? AND C.matKhau = ?", TaiKhoan.class, userName, pass);
        return list.isEmpty() ? "" : list.get(0).getQuyen().getTenQuyen();
    }

    @Override
    public Integer updatePass(String newPass, String userName) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.beginTransaction();

            String hql = "UPDATE TaiKhoan SET matKhau = :pass WHERE tenDN = :id";
            Query query = session.createQuery(hql);
            query.setParameter("pass", newPass);
            query.setParameter("id", userName);
            query.executeUpdate();

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return 1;
    }

    @Override
    public TaiKhoan getAccount(String username) {
        List<TaiKhoan> list = getFromQuery("FROM TaiKhoan C WHERE C.tenDN = ?", TaiKhoan.class, username);
        return list.isEmpty() ? null : list.get(0);
    }

}
