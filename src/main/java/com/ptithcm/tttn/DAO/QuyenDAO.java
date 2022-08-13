package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.Quyen;

import java.util.List;

public interface QuyenDAO extends Dao<Quyen> {
    List<Quyen> getAllRole();

    Quyen getRole(int idRole);
}
