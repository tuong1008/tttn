package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.SanPham;

import java.util.List;

public interface SanPhamDAO extends Dao<SanPham> {
    List<SanPham> getListProduct();

    List<SanPham> getListHotSaleProduct(int bigSaleOffPercent);

    List<SanPham> getListNewProduct();

    List<SanPham> getListHotProdduct(int monthNumber);

    List<SanPham> getListProductByName(String name);

    List<SanPham> getListProductByNameBrand(String name);

    SanPham getProduct(String maSP);
}
