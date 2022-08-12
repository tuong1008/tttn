package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.CTDonHang;
import org.hibernate.SessionFactory;

import java.util.List;


public interface CTDonHangDAO {
    List<CTDonHang> getDetailBills(SessionFactory factory, String id);

    int deleteDetailBill(SessionFactory factory, CTDonHang ctDonHang);

    int insertDetailBill(SessionFactory factory, CTDonHang ctDonHang);

    int updateDetailBill(SessionFactory factory, CTDonHang ctDonHang);

    CTDonHang getDetailBill(SessionFactory factory, String idBill, String idProduct);
}
