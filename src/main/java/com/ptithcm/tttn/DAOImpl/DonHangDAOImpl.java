package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.DonHangDAO;
import com.ptithcm.tttn.common.Utils;
import com.ptithcm.tttn.entity.CTDonHang;
import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.NhanVien;
import com.ptithcm.tttn.model.Revenue;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;

public class DonHangDAOImpl extends AbstractDao<DonHang> implements DonHangDAO {

    @Autowired
    SessionFactory factory;

    @Override
    public DonHang getBillUnBuy(String idCustomer) {
        return getFromQuery("FROM DonHang D WHERE D.khachHang.maKH =? AND D.trangThai = 0", DonHang.class, idCustomer).get(0);
    }

    @Override
    public List<DonHang> getBills(String idCustomer) {
        return getFromQuery("FROM DonHang D WHERE (D.trangThai = 1 OR D.trangThai = -1) AND D.khachHang.maKH =?", DonHang.class, idCustomer);
    }

    @Override
    public List<DonHang> getBillUnconfirm() {
        return getFromQuery("FROM DonHang B WHERE B.trangThai = 1 AND B.nhanVienD.maNV = NULL", DonHang.class);
    }

    @Override
    public List<DonHang> getBillCancel() {
        return getFromQuery("FROM DonHang WHERE trangThai = -1", DonHang.class);
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
        Session session = factory.getCurrentSession();
        String sql = "exec DoanhThu '" + from + "' , '" + to + "'";
        SQLQuery query = session.createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List<Revenue> revenues = new ArrayList<Revenue>();
        List data = query.list();
        for (Object object : data) {
            Map row = (Map) object;
            revenues.add(new Revenue((Integer) row.get("NAM"), (Integer) row.get("THANG"), ((BigDecimal) row.get("DOANHTHU")).intValue()));
        }
        return revenues;
    }

    @Override
    public List<DonHang> getBillComplete(HttpSession sessions) {
        String hql = "FROM DonHang where nhanVienD.maNV <>NULL AND nhanVienG.maNV <>NULL AND trangThai = 1 AND ngayNhan <> NULL";
        NhanVien staff = (NhanVien) sessions.getAttribute("staff");
        if (staff.getTaiKhoan().getQuyen().getMaQuyen() == 4) {
            hql += " AND nhanVienG.maNV = '" + staff.getMaNV() + "'";
        }
        List<DonHang> list = getFromQuery(hql, DonHang.class);
        return list;
    }

    @Override
    public List<DonHang> getBillDelivering(HttpSession sessions) {
        String hql = "FROM DonHang where nhanVienD.maNV <>NULL AND nhanVienG.maNV <>NULL AND trangThai = 1 AND ngayNhan = NULL";
        NhanVien staff = (NhanVien) sessions.getAttribute("staff");
        if (staff.getTaiKhoan().getQuyen().getMaQuyen() == 4) {
            hql += " AND nhanVienG.maNV = '" + staff.getMaNV() + "'";
        }
        List<DonHang> list = getFromQuery(hql, DonHang.class);
        return list;
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
