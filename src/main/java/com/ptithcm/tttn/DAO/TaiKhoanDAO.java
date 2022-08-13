package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.TaiKhoan;

public interface TaiKhoanDAO extends Dao<TaiKhoan> {
    String getRole(String pass, String userName);

    TaiKhoan getAccount(String username);

    Integer updatePass(String newPass, String userName);
}
