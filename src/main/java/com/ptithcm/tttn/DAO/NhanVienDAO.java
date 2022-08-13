package com.ptithcm.tttn.DAO;


import com.ptithcm.tttn.entity.NhanVien;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public interface NhanVienDAO extends Dao<NhanVien> {
    NhanVien getStaff(String username);

    Integer getMaxNumberByName(String username);

    NhanVien getStaffByID(String id);

    ArrayList<NhanVien> getAllStaff(SessionFactory factory);

    ArrayList<NhanVien> searchAllStaff(String hoTen);

}
