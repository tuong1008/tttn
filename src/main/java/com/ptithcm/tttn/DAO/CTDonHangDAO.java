package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.CTDonHang;

import java.util.List;


public interface CTDonHangDAO extends Dao<CTDonHang> {
    List<CTDonHang> getDetailBills(String id);

    CTDonHang getDetailBill(String idBill, String idProduct);
}
