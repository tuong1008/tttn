package com.ptithcm.tttn.DAO;

import java.util.List;

import org.hibernate.SessionFactory;

import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.KhachHang;

public interface DonHangDAO {
	DonHang getBillUnBuy(SessionFactory factory, String idCustomer);
	List<DonHang> getBills(SessionFactory factory, String idCustomer);
	int update(SessionFactory factory, DonHang donHang);
	int insert(SessionFactory factory, KhachHang khachHang);
}
