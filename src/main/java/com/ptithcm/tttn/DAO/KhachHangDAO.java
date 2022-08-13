package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public interface KhachHangDAO extends Dao<KhachHang> {
    KhachHang getCustomer(String username);

    Integer updateCustomer(KhachHang customer);

    Integer insertCustomer(KhachHang customer, TaiKhoan taiKhoan);

    ArrayList<KhachHang> getAllCustomer(SessionFactory factory);
}
