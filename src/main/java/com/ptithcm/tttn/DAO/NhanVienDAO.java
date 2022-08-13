package com.ptithcm.tttn.DAO;


import com.ptithcm.tttn.entity.NhanVien;

import java.util.List;

public interface NhanVienDAO extends Dao<NhanVien> {
    NhanVien getStaff(String username);

    Integer getMaxNumberByName(String username);

    NhanVien getStaffByID(String id);

    List<NhanVien> getAllStaff();

    List<NhanVien> searchAllStaff(String hoTen);

}
