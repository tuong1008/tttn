package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;

public class KhachHangDAOImpl implements KhachHangDAO {

    @Override
    public KhachHang getCustomer(SessionFactory factory, String username) {
        Session session = factory.getCurrentSession();
        String hql = "FROM KhachHang where tenDN = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", username);
        return (KhachHang) query.list().get(0);
    }

    @Override
    public Integer updateCustomer(SessionFactory factory, KhachHang customer) {
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
    public Integer insertCustomer(SessionFactory factory, KhachHang customer, TaiKhoan taiKhoan) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<KhachHang> getAllCustomer(SessionFactory factory) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
