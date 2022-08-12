package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.KhachHang;
import org.hibernate.SessionFactory;

import java.util.List;

public interface DonHangDAO {
    DonHang getBillUnBuy(SessionFactory factory, String idCustomer);

    List<DonHang> getBills(SessionFactory factory, String idCustomer);

    int update(SessionFactory factory, DonHang donHang);

    int insert(SessionFactory factory, KhachHang khachHang);
}
