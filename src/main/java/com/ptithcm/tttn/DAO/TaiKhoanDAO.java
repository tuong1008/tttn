package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.SessionFactory;

public interface TaiKhoanDAO {
    String getRole(SessionFactory factory, String pass, String userName);

    TaiKhoan getAccount(SessionFactory factory, String username);

    Integer updatePass(SessionFactory factory, String newPass, String userName);

    Integer deleteAccount(SessionFactory factory, TaiKhoan taiKhoan);

    Integer insertAccount(SessionFactory factory, TaiKhoan taiKhoan);
}
