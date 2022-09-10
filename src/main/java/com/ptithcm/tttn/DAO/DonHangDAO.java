package com.ptithcm.tttn.DAO;

import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.NguoiDung;
import com.ptithcm.tttn.model.Revenue;
import org.hibernate.SessionFactory;

import java.util.List;
import javax.servlet.http.HttpSession;

public interface DonHangDAO extends Dao<DonHang> {

    int insert(NguoiDung khachHang);

    DonHang getBillUnBuy(String idCustomer);

    List<DonHang> getBills(String idCustomer);

    List<DonHang> getBillUnconfirm();

    List<DonHang> getBillCancel();

    List<DonHang> getBillComplete(HttpSession session);

    List<DonHang> getBillDelivering(HttpSession session);

    List<DonHang> searchBills(String from, String to);

    List<DonHang> searchBillUnconfirm(String from, String to);

    List<DonHang> searchBillCancel(String from, String to);

    List<DonHang> searchBillComplete(HttpSession session, String from, String to);

    List<DonHang> searchBillDelivering(HttpSession session, String from, String to);

    List<Revenue> revenue(String from, String to);
}
