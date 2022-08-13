package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.SanPham;

import java.util.ArrayList;

public interface SanPhamDAO extends Dao<SanPham> {
    ArrayList<SanPham> getListProduct();

    ArrayList<SanPham> getListHotSaleProduct(int bigSaleOffPercent);

    ArrayList<SanPham> getListNewProduct();

    ArrayList<SanPham> getListHotProdduct(int monthNumber);

    ArrayList<SanPham> getListProductByName(String name);

    ArrayList<SanPham> getListProductByNameBrand(String name);

    SanPham getProduct(String maSP);
}
