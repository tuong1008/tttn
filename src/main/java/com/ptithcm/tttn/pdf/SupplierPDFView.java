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
import com.ptithcm.tttn.entity.NhaCungCap;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.io.IOUtils;

public class SupplierPDFView extends AbstractPdfView {

    protected void buildPdfDocument(Map<String, Object> model, Document document, PdfWriter pdfWriter,
            HttpServletRequest request, HttpServletResponse response) throws Exception {
        String rootDir = request.getSession().getServletContext().getRealPath("/");
        Path path = Paths.get(rootDir + "WEB-INF" + File.separator + "resource" + File.separator + "TIMES.ttf");
        BaseFont bf = BaseFont.createFont(path.toAbsolutePath().toString(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font font = new Font(bf, 13);
        Font fontTitle = new Font(bf, 15);

        Paragraph p = new Paragraph("DANH SÁCH NHÀ CUNG CẤP", fontTitle);
        p.setAlignment(Element.ALIGN_CENTER);
        p.setSpacingAfter(20);
        PdfPTable table;
        table = new PdfPTable(new float[]{3, 3, 3, 5, 3});
        table.setWidthPercentage(110);
        table.addCell(PDF.getCell("Mã NCC", fontTitle));
        table.addCell(PDF.getCell("Tên NCC", fontTitle));
        table.addCell(PDF.getCell("SDT", fontTitle));
        table.addCell(PDF.getCell("Địa chỉ", fontTitle));
        table.addCell(PDF.getCell("Email", fontTitle));

        ArrayList<NhaCungCap> suppliers = (ArrayList<NhaCungCap>) model.get("suppliers");

        for (NhaCungCap s : suppliers) {
            table.addCell(PDF.getCell(s.getMaNCC(), font));
            table.addCell(PDF.getCell(s.getTenNCC(), font));
            table.addCell(PDF.getCell(s.getSdt(), font));
            table.addCell(PDF.getCell(s.getDiaChi(), font));
            table.addCell(PDF.getCell(s.getEmail(), font));
        }
        document.add(p);
        document.add(table);
    }

}
