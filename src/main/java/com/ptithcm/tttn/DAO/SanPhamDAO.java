package com.ptithcm.tttn.DAO;

import java.util.ArrayList;

import org.hibernate.SessionFactory;

import com.ptithcm.tttn.entity.SanPham;

public interface SanPhamDAO {
	ArrayList<SanPham> getListProduct(SessionFactory factory);
	ArrayList<SanPham> getListHotSaleProduct(SessionFactory factory, int bigSaleOffPercent);
	ArrayList<SanPham> getListNewProduct(SessionFactory factory);
	ArrayList<SanPham> getListHotProdduct(SessionFactory factory, int monthNumber);
	ArrayList<SanPham> getListProductByName(SessionFactory factory, String name);
	ArrayList<SanPham> getListProductByNameBrand(SessionFactory factory, String name);
	SanPham getProduct(SessionFactory factory, String maSP);
	int update(SessionFactory factory, SanPham p);
}
