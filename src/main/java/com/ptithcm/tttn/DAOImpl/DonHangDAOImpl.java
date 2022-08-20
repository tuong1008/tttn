package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.DonHangDAO;
import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.KhachHang;
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
        List<DonHang> list = getFromQuery("FROM DonHang D WHERE D.khachHang.maKH = ? AND D.trangThai = 0", DonHang.class, idCustomer);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<DonHang> getBills(String idCustomer) {
        return getFromQuery("FROM DonHang D WHERE (D.trangThai = 1 OR D.trangThai = 2) AND D.khachHang.maKH = ?", DonHang.class, idCustomer);
    }

    @Override
    public int insert(KhachHang k) {
        DonHang dh = new DonHang(nextPK("DonHang", "DH", "maDH"), 0, k.getHoTen(), k.getDiaChi(), k.getSdt(), k.getEmail(), new Date(), null, 0,
                null, null, k, null, null);
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(dh);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            e.printStackTrace();
            return 0;
        } finally {
            session.close();
        }
        return 1;
    }

   

}
