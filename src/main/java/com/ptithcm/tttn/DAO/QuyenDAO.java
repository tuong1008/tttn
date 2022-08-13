package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.Quyen;

import java.util.ArrayList;

public interface QuyenDAO extends Dao<Quyen> {
    ArrayList<Quyen> getAllRole();

    Quyen getRole(int idRole);
}
