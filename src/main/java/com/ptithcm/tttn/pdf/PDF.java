package com.ptithcm.tttn.pdf;

import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;

public class PDF {
	   public static PdfPCell getCell(String value, Font f) {
	        PdfPCell cell = new PdfPCell(new Paragraph(value, f));
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        return cell;
	    }
}
