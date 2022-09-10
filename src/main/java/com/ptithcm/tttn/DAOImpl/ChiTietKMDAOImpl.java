package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.ChiTietKMDAO;
import com.ptithcm.tttn.entity.ChiTietKM;
import com.ptithcm.tttn.entity.NguoiDung;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ChiTietKMDAOImpl extends AbstractDao<ChiTietKM> implements ChiTietKMDAO {

    @Override
    public Integer getDiscount(String idProduct) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String hql = "SELECT C.giamGia FROM ChiTietKM C where C.pk.sanPham.maSP = :id AND (current_date() BETWEEN C.pk.khuyenMai.ngayBD AND C.pk.khuyenMai.ngayKT)";
        Query query = session.createQuery(hql);
        query.setParameter("id", idProduct);

        List<Integer> list = query.list();
        session.getTransaction().commit();

        int giamGiaLonNhat = 0;
        if (list != null) {
            for (int i : list) {
                if (giamGiaLonNhat < i) {
                    giamGiaLonNhat = i;
                }
            }
        }

        session.close();

        return giamGiaLonNhat;
    }

    @Override
    public List<ChiTietKM> getDetailPromotions(Integer id) {
        return getFromQuery("FROM ChiTietKM WHERE pk.khuyenMai.maKM =?", ChiTietKM.class, id);
    }

    @Override
    public void deleteByPromotionId(Integer id) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        String hql = "delete from ChiTietKM where pk.khuyenMai.maKM = :id";
        Query query = session.createQuery(hql);
        query.setParameter("id", id);

        query.executeUpdate();
        session.getTransaction().commit();
        
        session.close();
    }

}
