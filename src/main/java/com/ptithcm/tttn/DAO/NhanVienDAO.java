package com.ptithcm.tttn.DAO;


import com.ptithcm.tttn.entity.NguoiDung;
import java.util.ArrayList;

import java.util.List;

public interface NhanVienDAO extends Dao<NguoiDung> {
    NguoiDung getStaff(String username);

    Integer getMaxNumberByName(String username);

    NguoiDung getStaffByID(String id);

    List<NguoiDung> getAllStaff();

    List<NguoiDung> searchAllStaff(String hoTen);
    
    List<NguoiDung> getShippers();

}
