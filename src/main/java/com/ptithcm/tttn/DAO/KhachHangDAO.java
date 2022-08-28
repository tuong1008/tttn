package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.KhachHang;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.SessionFactory;

import java.util.List;

public interface KhachHangDAO extends Dao<KhachHang> {
    KhachHang getCustomer(String username);

    Integer updateCustomer(KhachHang customer);

    Integer insertCustomer(KhachHang customer, TaiKhoan taiKhoan);

    List<KhachHang> getAllCustomer(SessionFactory factory);
    
    Integer setStatus(int status, String idCustomer);
    
    List<KhachHang> searchCustomers(String nameCustomer);
}
