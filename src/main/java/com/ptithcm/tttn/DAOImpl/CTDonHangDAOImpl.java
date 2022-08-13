package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.CTDonHangDAO;
import com.ptithcm.tttn.entity.CTDonHang;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CTDonHangDAOImpl extends AbstractDao<CTDonHang> implements CTDonHangDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public List<CTDonHang> getDetailBills(String id) {
        Session session = factory.getCurrentSession();
        String hql = "FROM CTDonHang C WHERE C.pk.donHang.maDH =:id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);
        return query.list();
    }

    @Override
    public CTDonHang getDetailBill(String idBill, String idProduct) {
        Session session = factory.getCurrentSession();
        String hql = "FROM CTDonHang D WHERE D.pk.donHang.maDH =:idBill AND D.pk.sanPham.maSP =:idProduct";
        Query query = session.createQuery(hql);
        query.setParameter("idBill", idBill);
        query.setParameter("idProduct", idProduct);
        List<CTDonHang> list = query.list();
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

}
