package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public interface KhachHangDAO {
    KhachHang getCustomer(SessionFactory factory, String username);

    Integer updateCustomer(SessionFactory factory, KhachHang customer);

    Integer insertCustomer(SessionFactory factory, KhachHang customer, TaiKhoan taiKhoan);

    ArrayList<KhachHang> getAllCustomer(SessionFactory factory);
}
