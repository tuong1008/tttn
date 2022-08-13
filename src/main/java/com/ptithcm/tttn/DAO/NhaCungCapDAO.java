package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.NhaCungCap;

import java.util.ArrayList;

public interface NhaCungCapDAO extends Dao<NhaCungCap> {

    ArrayList<NhaCungCap> getSuppliers();

}
