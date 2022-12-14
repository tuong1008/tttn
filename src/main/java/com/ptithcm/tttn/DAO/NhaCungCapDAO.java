package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.NhaCungCap;
import java.util.ArrayList;

import java.util.List;

public interface NhaCungCapDAO extends Dao<NhaCungCap> {

    List<NhaCungCap> getSuppliers();    

    ArrayList<NhaCungCap> searchAllSuppliers(String name);
}
