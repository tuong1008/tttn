package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.DonHangDAO;
import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.KhachHang;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class DonHangDAOImpl extends AbstractDao<DonHang> implements DonHangDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public DonHang getBillUnBuy(String idCustomer) {
        Session session = factory.getCurrentSession();
        String hql = "FROM DonHang D WHERE D.khachHang.maKH =:idCustomer AND D.trangThai = 0";
        Query query = session.createQuery(hql);
        query.setParameter("idCustomer", idCustomer);
        List<DonHang> list = query.list();
        return list.get(0);
    }

    @Override
    public List<DonHang> getBills(String idCustomer) {
        Session session = factory.getCurrentSession();
        String hql = "FROM DonHang D WHERE (D.trangThai = 1 OR D.trangThai = 2) AND D.khachHang.maKH =:idCustomer";
        Query query = session.createQuery(hql);
        query.setParameter("idCustomer", idCustomer);
        List<DonHang> list = query.list();
        return list;
    }

    @Override
    public int insert(KhachHang k) {
        DonHang dh = new DonHang("DH005", 0, k.getHoTen(), k.getDiaChi(), k.getSdt(), k.getEmail(), new Date(), null, 0,
                null, null, k, null, null);
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(dh);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            System.out.println(e);
            return 0;
        } finally {
            session.close();
        }
        return 1;
    }

}
