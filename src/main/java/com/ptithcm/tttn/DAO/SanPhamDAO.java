package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.SanPham;

import java.util.List;

public interface SanPhamDAO extends Dao<SanPham> {
    List<SanPham> getListProduct();
    
    List<SanPham> getListProductToSale();

    List<SanPham> getListHotSaleProduct(int bigSaleOffPercent);

    List<SanPham> getListNewProduct();

    List<SanPham> getListHotProdduct(int monthNumber);

    List<SanPham> getListProductByNameForSale(String name);
    
    List<SanPham> getListProductByName(String name);
    
    List<SanPham> getListProductByNameBrandForSale(String name);

    List<SanPham> getListProductByNameBrand(String name);
    
    List<SanPham> getListProductByIDBrand(String id);

    SanPham getProduct(String maSP);
    
    List<SanPham> getCustomListProduct(String[] nameBrands, String fromPrice, String toPrice, String[] categories);
}
