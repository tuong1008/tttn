/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.ptithcm.tttn.api;

import com.ptithcm.tttn.DAO.CTDonHangDAO;
import com.ptithcm.tttn.DAO.DonHangDAO;
import com.ptithcm.tttn.DAO.HoaDonDAO;
import com.ptithcm.tttn.DAO.SanPhamDAO;
import com.ptithcm.tttn.common.Utils;
import com.ptithcm.tttn.entity.CTDonHang;
import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.HoaDon;
import com.ptithcm.tttn.entity.NguoiDung;
import com.ptithcm.tttn.entity.SanPham;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Transactional
@Controller
public class ApiForDelivery {
    @Autowired
    DonHangDAO donHangDAO;
    
    @Autowired
    HoaDonDAO hoaDonDAO;
    
    @Autowired
    CTDonHangDAO ctDonHangDAO;
    
    @Autowired
    SanPhamDAO sanPhamDAO;
    
    @RequestMapping(value = "api-update-shipper-billDetail/{id}.htm", method = RequestMethod.POST)
    public ResponseEntity<String> updateShipper(HttpServletRequest request, ModelMap model, HttpSession session,
            @PathVariable("id") String id, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        request.setCharacterEncoding("UTF-8");
        JsonReader rdr = Json.createReader(request.getInputStream());
        JsonObject obj = rdr.readObject();

        String shipperName = obj.getJsonString("shipperName").getString();
        DonHang bill = donHangDAO.getOne(DonHang.class, id);
        bill.setNhanVienG(shipperName);
        donHangDAO.update(bill);
        
        return ResponseEntity.ok().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8))
                .body("Cập nhật shipper cho đơn hàng thành công");
    }
    
    @RequestMapping(value = "api-update-status-billDetail/{id}.htm", method = RequestMethod.POST)
    public ResponseEntity<String> updateStatus(HttpServletRequest request, ModelMap model, HttpSession session,
            @PathVariable("id") String id, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        request.setCharacterEncoding("UTF-8");
        JsonReader rdr = Json.createReader(request.getInputStream());
        JsonObject obj = rdr.readObject();

        String statusOrder = obj.getJsonString("statusOrder").getString();
        DonHang bill = donHangDAO.getOne(DonHang.class, id);
        bill.setTrangThai(statusOrder);
        donHangDAO.update(bill);
        
        return ResponseEntity.ok().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8))
                .body("Cập nhật trạng thái cho đơn hàng thành công");
    }
    
    @RequestMapping(value = "api-complete-order/{id}.htm", method = RequestMethod.POST)
    public ResponseEntity<String> completeOrder(HttpServletRequest request, ModelMap model, HttpSession session,
            @PathVariable("id") String id, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        DonHang bill = donHangDAO.getOne(DonHang.class, id);
        bill.setNgayNhan(new Date());
        bill.setTrangThai("Đã hoàn thành");
        donHangDAO.update(bill);
        HoaDon billl = new HoaDon(hoaDonDAO.nextPK("HoaDon", "HD", "maHD"), new Date(), Utils.getMST(), null, bill, null);
        hoaDonDAO.save(billl);
        
        return ResponseEntity.ok().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8))
                .body("Đơn hàng đã đánh dấu hoàn thành");
    }
    
    @RequestMapping(value = "api-cancel-order/{id}.htm", method = RequestMethod.POST)
    public ResponseEntity<String> cancelOrder(HttpServletRequest request, ModelMap model, HttpSession session,
            @PathVariable("id") String id, HttpServletResponse response) throws UnsupportedEncodingException, IOException {
        request.setCharacterEncoding("UTF-8");
        
        DonHang bill = donHangDAO.getOne(DonHang.class, id);
        bill.setNgayNhan(new Date());
        bill.setTrangThai("Đã hoàn trả");
        donHangDAO.update(bill);
        
        List<CTDonHang> cts = ctDonHangDAO.getDetailBills(bill.getMaDH());

        for (CTDonHang ct : cts) {
            SanPham s = ct.getPk().getSanPham();
            s.setSlt(s.getSlt() + ct.getSl());
            sanPhamDAO.update(s);
        }
        
        return ResponseEntity.ok().contentType(new MediaType(MediaType.TEXT_PLAIN, StandardCharsets.UTF_8))
                .body("Đơn hàng đã hoàn trả cho cửa hàng");
    }

}
