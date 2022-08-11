package com.ptithcm.tttn.DAO;

import java.util.ArrayList;

import org.hibernate.SessionFactory;

import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.TaiKhoan;

public interface KhachHangDAO {
	KhachHang getCustomer(SessionFactory factory, String username);
	Integer updateCustomer(SessionFactory factory, KhachHang customer);
	Integer insertCustomer(SessionFactory factory, KhachHang customer, TaiKhoan taiKhoan);
	ArrayList<KhachHang> getAllCustomer(SessionFactory factory);
}
