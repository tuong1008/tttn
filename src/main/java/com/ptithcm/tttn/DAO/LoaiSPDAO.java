package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.LoaiSP;

import java.util.List;

public interface LoaiSPDAO extends Dao<LoaiSP> {
    List<LoaiSP> getListCategory();

    List<LoaiSP> searchByName(String name);
}
