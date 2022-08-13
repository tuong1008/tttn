package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.KhachHang;
import org.hibernate.SessionFactory;

import java.util.List;

public interface DonHangDAO extends Dao<DonHang> {
    DonHang getBillUnBuy(String idCustomer);

    List<DonHang> getBills(String idCustomer);

    int insert(KhachHang khachHang);
}
