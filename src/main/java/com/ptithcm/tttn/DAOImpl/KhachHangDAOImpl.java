package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.KhachHangDAO;
import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @Override
    public Integer setStatus(int status, String idCustomer) {
        KhachHang cus = getOne(KhachHang.class, idCustomer);
        cus.setTrangThai(status);
        String result = update(cus);
        if (result.equals("")){
            return 1;
        } else {
            return 0;
        }
//		Session session = factory.getCurrentSession();
//		try {
//			String hql = "UPDATE KhachHang SET trangThai = :status WHERE maKH = :id";
//			Query query = session.createQuery(hql);
//			query.setParameter("status", status);
//			query.setParameter("id", idCustomer);
//			query.executeUpdate();
//                        System.out.println(hql);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return 0;
//		}
//		return 1;
	}

    @Override
    public List<KhachHang> searchCustomers(String nameCustomer) {
		Session session = factory.getCurrentSession();
		String hql = "FROM KhachHang where hoTen LIKE :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", "%" + nameCustomer + "%");
		List<KhachHang> list = query.list();
		return list;
	}
}
