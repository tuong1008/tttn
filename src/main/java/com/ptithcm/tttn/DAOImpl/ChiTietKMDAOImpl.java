package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.ChiTietKMDAO;
import com.ptithcm.tttn.entity.ChiTietKM;
import com.ptithcm.tttn.entity.NhanVien;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class ChiTietKMDAOImpl extends AbstractDao<ChiTietKM> implements ChiTietKMDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public Integer getDiscount(String idProduct) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        String hql = "SELECT C.giamGia FROM ChiTietKM C where C.pk.sanPham.maSP = :id AND (current_date() BETWEEN C.pk.khuyenMai.ngayBD AND C.pk.khuyenMai.ngayKT)";
        Query query = session.createQuery(hql);
        query.setParameter("id", idProduct);

        List<Integer> list = query.list();
        session.getTransaction().commit();

        return list.isEmpty() ? 0 : list.get(0);
    }

}
