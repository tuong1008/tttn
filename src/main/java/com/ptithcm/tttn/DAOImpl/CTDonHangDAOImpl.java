package com.ptithcm.tttn.DAOImpl;

import com.ptithcm.tttn.DAO.AbstractDao;
import com.ptithcm.tttn.DAO.CTDonHangDAO;
import com.ptithcm.tttn.entity.CTDonHang;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class CTDonHangDAOImpl extends AbstractDao<CTDonHang> implements CTDonHangDAO {
    @Autowired
    SessionFactory factory;

    @Override
    public List<CTDonHang> getDetailBills(String id) {
        return getFromQuery("FROM CTDonHang C WHERE C.pk.donHang.maDH = ?", CTDonHang.class, id);
    }

    @Override
    public CTDonHang getDetailBill(String idBill, String idProduct) {
        List<CTDonHang> list = getFromQuery("FROM CTDonHang D WHERE D.pk.donHang.maDH = ? AND D.pk.sanPham.maSP = ?", CTDonHang.class, idBill, idProduct);
        return list.isEmpty() ? null : list.get(0);
    }
}
