package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.DonHangDAO;
import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.NguoiDung;
import com.ptithcm.tttn.model.Revenue;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

public class DonHangDAOImpl extends AbstractDao<DonHang> implements DonHangDAO {

    @Override
    public DonHang getBillUnBuy(String idCustomer) {
        return getFromQuery("FROM DonHang D WHERE D.khachHang.userId =? AND D.trangThai = ?", DonHang.class, idCustomer,"Giỏ hàng").get(0);
    }

    @Override
    public List<DonHang> getBills(String idCustomer) {
        return getFromQuery("FROM DonHang D WHERE D.trangThai <> ? AND D.khachHang.userId =?", DonHang.class,"Giỏ hàng" , idCustomer);
    }

    @Override
    public List<DonHang> getBillUnconfirm() {
        return getFromQuery("FROM DonHang B WHERE B.trangThai = ? AND B.nhanVienD.userId = NULL", DonHang.class, "Chờ xác nhận");
    }

    @Override
    public List<DonHang> getBillCancel() {
        return getFromQuery("FROM DonHang WHERE trangThai = ?", DonHang.class, "Đã hủy");
    }

    public List<DonHang> searchBills(String from, String to) {
        try {
            return getFromQuery("FROM DonHang where ngayTao >=? AND ngayTao <=?", DonHang.class,
                    new SimpleDateFormat("yyyy-MM-dd").parse(from),
                    new SimpleDateFormat("yyyy-MM-dd").parse(to));
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Revenue> revenue(String from, String to) {
        Session session = sessionFactory.openSession();
        Transaction tran = session.beginTransaction();
        String sql = "exec DoanhThu '" + from + "' , '" + to + "'";
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Revenue> revenues = new ArrayList<Revenue>();
        List data = query.list();
        for (Object object : data) {
            Map row = (Map) object;
            revenues.add(new Revenue((Integer) row.get("NAM"), (Integer) row.get("THANG"), ((BigDecimal) row.get("DOANHTHU")).intValue()));
        }
        tran.commit();
        session.close();
        return revenues;
    }

    @Override
    public List<DonHang> getBillComplete(HttpSession sessions) {
        String hql = "FROM DonHang where trangThai = ?";
        NguoiDung staff = (NguoiDung) sessions.getAttribute("staff");
        if (staff.getTaiKhoan().getQuyen().getMaQuyen() == 4) {
            hql += " AND nhanVienG.userId = '" + staff.getUserId() + "'";
        }
        List<DonHang> list = getFromQuery(hql, DonHang.class, "Đã hoàn thành");
        return list;
    }

    @Override
    public List<DonHang> getBillDelivering(HttpSession sessions) {
        String hql = "FROM DonHang where nhanVienD.userId <>NULL AND nhanVienG <>NULL AND trangThai = ? AND ngayNhan = NULL";
        NguoiDung staff = (NguoiDung) sessions.getAttribute("staff");
        if (staff.getTaiKhoan().getQuyen().getMaQuyen() == 4) {
            hql += " AND nhanVienG.userId = '" + staff.getUserId() + "'";
        }
        List<DonHang> list = getFromQuery(hql, DonHang.class, "Đang Giao");
        return list;
    }

    @Override
    public int insert(NguoiDung k) {
        DonHang dh = new DonHang(nextPK("DonHang", "DH", "maDH"), "Giỏ hàng", k.getHoTen(), k.getDiaChi(), k.getSdt(), k.getEmail(), new Date(), null, 0,
                null, null, k, null, null);
        Session session = sessionFactory.openSession();
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

    @Override
    public List<DonHang> searchBillUnconfirm(String from, String to) {
        Session session = sessionFactory.openSession();
        String hql = "FROM DonHang where trangThai = :trangThai AND nhanVienD.maNV = NULL AND ngayTao >=:from AND ngayTao <=:to";
        Query query = session.createQuery(hql);
        try {
            query.setParameter("trangThai", "Chờ xác nhận");
            query.setParameter("from", new SimpleDateFormat("yyyy-MM-dd").parse(from));
            query.setParameter("to", new SimpleDateFormat("yyyy-MM-dd").parse(to));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<DonHang> list = query.list();
        session.close();
        return list;
    }

    @Override
	public List<DonHang> searchBillComplete(HttpSession sessions, String from, String to) {
		Session session = sessionFactory.openSession();
		String hql = "FROM DonHang where trangThai = :trangThai AND ngayTao >=:from AND ngayTao <=:to";
		NguoiDung staff= (NguoiDung) sessions.getAttribute("staff");
		if(staff.getTaiKhoan().getQuyen().getMaQuyen() == 4) {
			hql += " AND nhanVienG.maNV = '" + staff.getUserId()+"'";
		}
		Query query = session.createQuery(hql);
		try {    
			query.setParameter("trangThai", "Đã hoàn thành");
			query.setParameter("from", new SimpleDateFormat("yyyy-MM-dd").parse(from));
			query.setParameter("to", new SimpleDateFormat("yyyy-MM-dd").parse(to));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<DonHang> list = query.list();
                session.close();
		return list;
	}

    @Override
    public List<DonHang> searchBillCancel(String from, String to) {
        Session session = sessionFactory.openSession();
        String hql = "FROM DonHang where trangThai = :trangThai AND ngayTao >=:from AND ngayTao <=:to";
        Query query = session.createQuery(hql);
        try {
            query.setParameter("trangThai", "Đã hủy");
            query.setParameter("from", new SimpleDateFormat("yyyy-MM-dd").parse(from));
            query.setParameter("to", new SimpleDateFormat("yyyy-MM-dd").parse(to));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<DonHang> list = query.list();
        session.close();
        return list;
    }

    @Override
    public List<DonHang> searchBillDelivering(HttpSession sessions, String from, String to) {
        Session session = sessionFactory.openSession();
        String hql = "FROM DonHang where nhanVienD.maNV <>NULL AND nhanVienG <>NULL AND trangThai = :trangThai AND ngayTao >=:from AND ngayTao <=:to";
        NguoiDung staff = (NguoiDung) sessions.getAttribute("staff");
        if (staff.getTaiKhoan().getQuyen().getMaQuyen() == 4) {
            hql += " AND nhanVienG.maNV = '" + staff.getUserId()+ "'";
        }
        Query query = session.createQuery(hql);
        try {
            query.setParameter("trangThai", "Đang Giao");
            query.setParameter("from", new SimpleDateFormat("yyyy-MM-dd").parse(from));
            query.setParameter("to", new SimpleDateFormat("yyyy-MM-dd").parse(to));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        List<DonHang> list = query.list();
        session.close();
        return list;
    }
}
