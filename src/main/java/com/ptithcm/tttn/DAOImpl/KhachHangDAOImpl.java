package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.KhachHangDAO;
import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class KhachHangDAOImpl extends AbstractDao<KhachHang> implements KhachHangDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public KhachHang getCustomer(String username) {
        Session session = factory.getCurrentSession();
        String hql = "FROM KhachHang where tenDN = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", username);
        return (KhachHang) query.list().get(0);
    }

    @Override
    public Integer updateCustomer(KhachHang customer) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(customer);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            System.out.print(e);
            return 0;
        } finally {
            session.close();
        }
        return 1;
    }

    @Override
    public Integer insertCustomer(KhachHang customer, TaiKhoan taiKhoan) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(taiKhoan);
            customer.setTaiKhoan(taiKhoan);
            session.save(customer);
            t.commit();
        } catch (HibernateException e) {
            t.rollback();
            System.out.print("hello: " + e);
            return 0;
        } finally {
            session.close();
        }
        return 1;
    }

    @Override
    public ArrayList<KhachHang> getAllCustomer(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        String hql = "FROM KhachHang";
        Query query = session.createQuery(hql);
        return (ArrayList<KhachHang>) query.list();
    }

}
