package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.KhachHangDAO;
import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.NhanVien;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class KhachHangDAOImpl extends AbstractDao<KhachHang> implements KhachHangDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public KhachHang getCustomer(String username) {
        List<KhachHang> list = getFromQuery("FROM KhachHang k where k.taiKhoan.tenDN = ?", KhachHang.class, username);
        return list.isEmpty() ? null : list.get(0);
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
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
        return 1;
    }

    @Override
    public List<KhachHang> getAllCustomer(SessionFactory factory) {
        return getFromQuery("FROM KhachHang", KhachHang.class);
    }

}
