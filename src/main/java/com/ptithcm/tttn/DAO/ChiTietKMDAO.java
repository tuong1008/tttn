package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.ChiTietKM;
import java.util.List;

public interface ChiTietKMDAO extends Dao<ChiTietKM> {
    Integer getDiscount(String maSP);
    List<ChiTietKM> getDetailPromotions(Integer id);
    void deleteByPromotionId(Integer id);
}
