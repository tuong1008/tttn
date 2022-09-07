package com.ptithcm.tttn.pdf;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.document.AbstractPdfView;

import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.ptithcm.tttn.entity.NguoiDung;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;

public class StaffPDFView extends AbstractPdfView {

    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rootDir = request.getSession().getServletContext().getRealPath("/");
        Path path = Paths.get(rootDir + "WEB-INF" + File.separator + "resource" + File.separator + "TIMES.ttf");
        BaseFont bf = BaseFont.createFont(path.toAbsolutePath().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 13);
        Font fontTitle = new Font(bf, 15);

        Paragraph p = new Paragraph("DANH SÁCH NHÂN VIÊN", fontTitle);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        PdfPTable table;
        table = new PdfPTable(new float[]{5, 10, 3, 5, 7, 10});
        table.setWidthPercentage(100);
        table.addCell(PDF.getCell("Mã NV", fontTitle));
        table.addCell(PDF.getCell("Họ tên", fontTitle));
        table.addCell(PDF.getCell("Giới tính", fontTitle));
        table.addCell(PDF.getCell("Địa chỉ", fontTitle));
        table.addCell(PDF.getCell("Số điện thoại", fontTitle));
        table.addCell(PDF.getCell("Email", fontTitle));

        ArrayList<NguoiDung> staffs = (ArrayList<NguoiDung>) model.get("staffs");

        for (NguoiDung s : staffs) {
            table.addCell(PDF.getCell(s.getUserId(), font));
            table.addCell(PDF.getCell(s.getHoTen(), font));
            table.addCell(PDF.getCell(s.getGioiTinh(), font));
            table.addCell(PDF.getCell(s.getDiaChi(), font));
            table.addCell(PDF.getCell(s.getSdt(), font));
            table.addCell(PDF.getCell(s.getEmail(), font));
        }
        document.add(p);
        document.add(table);
    }

}
