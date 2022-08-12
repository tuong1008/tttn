package com.ptithcm.tttn.DAO;


import com.ptithcm.tttn.entity.NhanVien;
import org.hibernate.SessionFactory;

import java.util.ArrayList;

public interface NhanVienDAO {
    NhanVien getStaff(SessionFactory factory, String username);

    Integer getMaxNumberByName(SessionFactory factory, String username);

    NhanVien getStaffByID(SessionFactory factory, String id);

    Integer insertStaff(SessionFactory factory, NhanVien nhanVien);

    Integer updateStaff(SessionFactory factory, NhanVien nhanVien);

    Integer deleteStaff(SessionFactory factory, NhanVien nhanVien);

    ArrayList<NhanVien> getAllStaff(SessionFactory factory);

    ArrayList<NhanVien> searchAllStaff(SessionFactory factory, String hoTen);

}
