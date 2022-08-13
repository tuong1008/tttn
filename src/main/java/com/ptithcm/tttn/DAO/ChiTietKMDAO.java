package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.ChiTietKM;

public interface ChiTietKMDAO extends Dao<ChiTietKM> {
    Integer getDiscount(String maSP);
}
