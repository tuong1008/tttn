package com.ptithcm.tttn.controller;

import com.ptithcm.tttn.DAO.NhaCungCapDAO;
import com.ptithcm.tttn.DAO.NhanVienDAO;
import com.ptithcm.tttn.DAO.SanPhamDAO;
import com.ptithcm.tttn.entity.NhaCungCap;
import com.ptithcm.tttn.entity.NguoiDung;
import com.ptithcm.tttn.entity.SanPham;
import com.ptithcm.tttn.pdf.ProductPDFView;
import com.ptithcm.tttn.pdf.StaffPDFView;
import com.ptithcm.tttn.pdf.SupplierPDFView;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Transactional
@RequestMapping("pdf/")
public class PDFController {

    @Autowired
    SessionFactory factory;
    @Autowired
    SanPhamDAO sanPhamDAOImpl;

    @Autowired
    NhaCungCapDAO nhaCungCapDAOImpl;
    
    @Autowired
    NhanVienDAO nhanVienDAO;

    @RequestMapping("staff")
    public ModelAndView staffListReport(HttpServletRequest req) {
        List<NguoiDung> staffs = nhanVienDAO.getAllStaff();
        return new ModelAndView(new StaffPDFView(), "staffs", staffs);
    }

    @RequestMapping("product")
    public ModelAndView phoneListReport(HttpServletRequest req) {
        List<SanPham> products = sanPhamDAOImpl.getListProduct();
        return new ModelAndView(new ProductPDFView(), "products", products);
    }

    @RequestMapping("supplier")
    public ModelAndView SupplierListReport(HttpServletRequest req) {
        List<NhaCungCap> suppliers = nhaCungCapDAOImpl.getSuppliers();
        return new ModelAndView(new SupplierPDFView(), "suppliers", suppliers);
    }

}
