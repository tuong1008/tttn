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
import com.ptithcm.tttn.entity.SanPham;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;

public class ProductPDFView extends AbstractPdfView {

    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rootDir = request.getSession().getServletContext().getRealPath("/");
        Path path = Paths.get(rootDir + "WEB-INF" + File.separator + "resource" + File.separator + "times.ttf");
        BaseFont bf = BaseFont.createFont(path.toAbsolutePath().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 13);
        Font fontTitle = new Font(bf, 15);

        Paragraph p = new Paragraph("DANH SÁCH SẢN PHẨM", fontTitle);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        PdfPTable table;
        table = new PdfPTable(new float[]{2, 2, 10, 2, 2, 2, 2});//Thiáº¿t láº­p tá»‰ lá»‡ giá»¯a cÃ¡c cá»™t trong báº£ng
        table.setWidthPercentage(110);//Thiáº¿t láº­p chiá»?u rá»™ng
        table.addCell(PDF.getCell("Mã SP", fontTitle));
        table.addCell(PDF.getCell("Tên SP", fontTitle));
        table.addCell(PDF.getCell("Mô tả", fontTitle));
        table.addCell(PDF.getCell("Giá", fontTitle));
        table.addCell(PDF.getCell("Số lượng tồn", fontTitle));
        table.addCell(PDF.getCell("Mã loại", fontTitle));
        table.addCell(PDF.getCell("Mã nhà cung cấp", fontTitle));

        ArrayList<SanPham> sanPhams = (ArrayList<SanPham>) model.get("products");

        for (SanPham s : sanPhams) {
            table.addCell(PDF.getCell(s.getMaSP(), font));
            table.addCell(PDF.getCell(s.getTenSP(), font));
            table.addCell(PDF.getCell(s.getMoTa(), font));
            table.addCell(PDF.getCell(s.getGia() + "", font));
            table.addCell(PDF.getCell(s.getSlt() + "", font));
            table.addCell(PDF.getCell(s.getLoaiSP().getMaLoai(), font));
            table.addCell(PDF.getCell(s.getNhaCungCap().getMaNCC(), font));
        }
        document.add(p);
        document.add(table);
    }

}
