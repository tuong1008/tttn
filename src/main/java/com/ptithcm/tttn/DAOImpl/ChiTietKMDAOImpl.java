package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.ChiTietKMDAO;
import com.ptithcm.tttn.entity.ChiTietKM;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class ChiTietKMDAOImpl extends AbstractDao<ChiTietKM> implements ChiTietKMDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public Integer getDiscount(String idProduct) {
        Session session = factory.getCurrentSession();
        String hql = "SELECT C.giamGia FROM ChiTietKM C where C.pk.sanPham.maSP = :id AND (current_date() BETWEEN C.pk.khuyenMai.ngayBD AND C.pk.khuyenMai.ngayKT)";
        Query query = session.createQuery(hql);
        query.setParameter("id", idProduct);
        if (query.list().isEmpty())
            return 0;
        return (Integer) query.list().get(0);
    }

}
