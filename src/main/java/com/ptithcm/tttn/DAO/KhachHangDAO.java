package com.ptithcm.tttn.DAO;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import com.ptithcm.tttn.entity.NguoiDung;
import com.ptithcm.tttn.entity.TaiKhoan;
import org.hibernate.SessionFactory;

import java.util.List;

public interface KhachHangDAO extends Dao<NguoiDung> {
    NguoiDung getCustomer(String username);

    String updateCustomer(NguoiDung customer);

    String insertCustomer(NguoiDung customer, TaiKhoan taiKhoan);

    List<NguoiDung> getAllCustomer(SessionFactory factory);
    
    Integer setStatus(int status, String idCustomer);
    
    List<NguoiDung> searchCustomers(String nameCustomer);
}
