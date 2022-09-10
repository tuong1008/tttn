package com.ptithcm.tttn.pdf;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ptithcm.tttn.entity.CTDonHang;
import com.ptithcm.tttn.entity.DonHang;
import com.ptithcm.tttn.entity.HoaDon;
import com.ptithcm.tttn.entity.NguoiDung;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BillPDFView extends AbstractPdfView {

    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rootDir = request.getSession().getServletContext().getRealPath("/");
        Path path = Paths.get(rootDir + "WEB-INF" + File.separator + "resource" + File.separator + "times.ttf");
        BaseFont bf = BaseFont.createFont(path.toAbsolutePath().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 13);
        Font fontTitle = new Font(bf, 15);
        Font fontNameCompany = new Font(bf, 12);

        Paragraph nameCompany = new Paragraph("Công ty cổ phần Nike", fontNameCompany);
        nameCompany.setAlignment(Element.ALIGN_LEFT);
        nameCompany.setSpacingAfter(28);

        Paragraph p1 = new Paragraph("Người lập hóa đơn", fontNameCompany);
        p1.setAlignment(Element.ALIGN_RIGHT);
        p1.setSpacingAfter(15);

        Paragraph p2 = new Paragraph(((NguoiDung) request.getSession().getAttribute("staff")).getHoTen(),
                fontNameCompany);
        p2.setAlignment(Element.ALIGN_RIGHT);
        p2.setSpacingAfter(15);

        Paragraph p3 = new Paragraph("Ngày lập: " + (new SimpleDateFormat("dd/MM/yyyy").format(new Date())),
                fontNameCompany);
        p3.setAlignment(Element.ALIGN_RIGHT);
        p3.setSpacingAfter(15);

        Paragraph p = new Paragraph("HÓA ĐƠN MUA HÀNG", fontTitle);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20);

        HoaDon billl = (HoaDon) model.get("billl");
        DonHang bill = billl.getDonHang();
        List<CTDonHang> detailBills = (List<CTDonHang>) bill.getCtDonHangs();
        NguoiDung customer = billl.getDonHang().getKhachHang();
        document.add(nameCompany);
        document.add(new Paragraph("MÃ HÓA ĐƠN: " + billl.getMaHD(), font));
        document.add(new Paragraph("MÃ SỐ THUẾ: " + billl.getMaSoThue(), font));
        document.add(new Paragraph("HỌ TÊN NGƯỜI NHẬN: " + bill.getHoTenNN(), font));
        document.add(new Paragraph("ĐỊA CHỈ NHẬN: " + bill.getDiaChiNN(), font));
        document.add(new Paragraph("SỐ ĐIỆN THOẠI: " + bill.getSdtNN(), font));

        PdfPTable table;
        table = new PdfPTable(new float[]{3, 3, 5, 5});
        table.setWidthPercentage(100);
        table.addCell(PDF.getCell("Tên sản phẩm", fontTitle));
        table.addCell(PDF.getCell("Số lượng", fontTitle));
        table.addCell(PDF.getCell("Đơn giá", fontTitle));
        table.addCell(PDF.getCell("Thành tiền", fontTitle));

        Long sum = 0L;
        for (CTDonHang s : detailBills) {
            table.addCell(PDF.getCell(s.getPk().getSanPham().getTenSP(), font));
            table.addCell(PDF.getCell(s.getSl() + "", font));
            table.addCell(PDF.getCell(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(s.getGia()) + "", font));
            table.addCell(PDF.getCell(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format((s.getGia() * s.getSl())) + "", font));
            sum = sum + (s.getGia() * s.getSl());
        }
        table.addCell(PDF.getCell("", font));
        table.addCell(PDF.getCell("", font));
        table.addCell(PDF.getCell("Tổng tiền", font));
        table.addCell(PDF.getCell(NumberFormat.getCurrencyInstance(new Locale("vi", "VN")).format(sum) + "", font));

        document.add(nameCompany);
        document.add(p1);
        document.add(p2);
        document.add(p3);
        document.add(p);
        document.add(table);
    }

    public static String convertDateString(String dateString) {
        return dateString.substring(8) + '-' + dateString.substring(5, 7) + "-" + dateString.substring(0, 4);
    }

}
