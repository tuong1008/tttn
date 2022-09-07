package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.KhachHangDAO;
import com.ptithcm.tttn.entity.NguoiDung;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class KhachHangDAOImpl extends AbstractDao<NguoiDung> implements KhachHangDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public NguoiDung getCustomer(String username) {
        List<NguoiDung> list = getFromQuery("FROM NguoiDung n.taiKhoan.quyen.tenQuyen = 'Customer' and k where k.taiKhoan.tenDN = ?", NguoiDung.class, username);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public String updateCustomer(NguoiDung customer) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.update(customer);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            Throwable error = e.getCause();
            return error.getMessage();
        } finally {
            session.close();
        }
        return "";
    }

    @Override
    public String insertCustomer(NguoiDung customer, TaiKhoan taiKhoan) {
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        try {
            session.save(taiKhoan);
            customer.setTaiKhoan(taiKhoan);
            session.save(customer);
            t.commit();
        } catch (Exception e) {
            t.rollback();
//            e.printStackTrace();
            Throwable error = e.getCause();
            return error.getMessage();
        } finally {
            session.close();
        }
        return "";
    }

    @Override
    public List<NguoiDung> getAllCustomer(SessionFactory factory) {
        return getFromQuery("FROM NguoiDung n WHERE n.taiKhoan.quyen.tenQuyen <> 'Customer'", NguoiDung.class);
    }

    @Override
    public Integer setStatus(int status, String idCustomer) {
        NguoiDung cus = getOne(NguoiDung.class, idCustomer);
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
    public List<NguoiDung> searchCustomers(String nameCustomer) {
		Session session = factory.getCurrentSession();
		String hql = "FROM NguoiDung where hoTen LIKE :name";
		Query query = session.createQuery(hql);
		query.setParameter("name", "%" + nameCustomer + "%");
		List<NguoiDung> list = query.list();
		return list;
	}
}
