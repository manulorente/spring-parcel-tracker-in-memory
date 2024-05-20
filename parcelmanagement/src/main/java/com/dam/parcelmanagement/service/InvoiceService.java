package com.dam.parcelmanagement.service;

import com.dam.parcelmanagement.model.Invoice;
import com.dam.parcelmanagement.repository.InvoiceRepository;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

    public Boolean existsById(Long id) {
        return this.invoiceRepository.existsById(id);
    }

    public List<Invoice> getAllInvoices() {
        return this.invoiceRepository.findAll();
    }

    public Invoice getInvoiceById(Long id) {
        Optional<Invoice> invoice = this.invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            return invoice.get();
        } else {
            return null;
        }
    }

    @Transactional
    public Invoice createInvoice(Invoice invoice) {
        return this.invoiceRepository.save(invoice);
    }

    @Transactional
    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Optional<Invoice> invoice = this.invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            Invoice invoiceToUpdate = invoice.get();
            if (invoiceDetails.getDate() != null) {
                invoiceToUpdate.setDate(invoiceDetails.getDate());
            }
            if (invoiceDetails.getTotal() != null) {
                invoiceToUpdate.setTotal(invoiceDetails.getTotal());
            }
            return this.invoiceRepository.save(invoiceToUpdate);
        } else {
            return null;
        }
    }

    @Transactional
    public void deleteInvoice(Long id) {
        this.invoiceRepository.deleteById(id);
    }

    public byte[] getInvoicePdf(Long id) throws IOException, DocumentException {
        Invoice invoice = this.getInvoiceById(id);
    
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, byteArrayOutputStream);
    
        document.open();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
        Font headerFont = new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD);
        Paragraph header = new Paragraph("TRANSPORTES LORENTE - FACTURA", headerFont);
        header.setAlignment(Element.ALIGN_CENTER);
        document.add(header);
    
        document.add(Chunk.NEWLINE);
    
        PdfPTable detailsTable = new PdfPTable(2);
        detailsTable.setWidthPercentage(100);
        detailsTable.setSpacingBefore(10f);
        detailsTable.setSpacingAfter(10f);
    
        PdfPCell cell;
    
        cell = new PdfPCell(new Phrase("ID de Factura:"));
        cell.setBorder(Rectangle.NO_BORDER);
        detailsTable.addCell(cell);
        cell = new PdfPCell(new Phrase(invoice.getId().toString()));
        cell.setBorder(Rectangle.NO_BORDER);
        detailsTable.addCell(cell);
    
        cell = new PdfPCell(new Phrase("Fecha:"));
        cell.setBorder(Rectangle.NO_BORDER);
        detailsTable.addCell(cell);
        cell = new PdfPCell(new Phrase(dateFormat.format(invoice.getDate())));
        cell.setBorder(Rectangle.NO_BORDER);
        detailsTable.addCell(cell);
    
        cell = new PdfPCell(new Phrase("Fecha de Vencimiento:"));
        cell.setBorder(Rectangle.NO_BORDER);
        detailsTable.addCell(cell);
        cell = new PdfPCell(new Phrase(dateFormat.format(invoice.getDueDate())));
        cell.setBorder(Rectangle.NO_BORDER);
        detailsTable.addCell(cell);
    
        document.add(detailsTable);
    
        document.add(Chunk.NEWLINE);
    
        Font sectionFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Paragraph serviceDetailsHeader = new Paragraph("Detalles del Servicio", sectionFont);
        serviceDetailsHeader.setSpacingBefore(10f);
        serviceDetailsHeader.setSpacingAfter(10f);
        document.add(serviceDetailsHeader);
    
        PdfPTable serviceTable = new PdfPTable(2);
        serviceTable.setWidthPercentage(100);
        serviceTable.setSpacingBefore(10f);
        serviceTable.setSpacingAfter(10f);
    
        cell = new PdfPCell(new Phrase("Descripción"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        serviceTable.addCell(cell);
    
        cell = new PdfPCell(new Phrase("Precio"));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        serviceTable.addCell(cell);
    
        serviceTable.addCell(invoice.getServiceInfo());
        serviceTable.addCell(invoice.getPrice().toString());
    
        document.add(serviceTable);
    
        document.add(Chunk.NEWLINE);
    
        PdfPTable totalTable = new PdfPTable(2);
        totalTable.setWidthPercentage(100);
        totalTable.setSpacingBefore(10f);
        totalTable.setSpacingAfter(10f);
    
        cell = new PdfPCell(new Phrase("Impuesto:"));
        cell.setBorder(Rectangle.NO_BORDER);
        totalTable.addCell(cell);
        cell = new PdfPCell(new Phrase(invoice.getTax().toString()));
        cell.setBorder(Rectangle.NO_BORDER);
        totalTable.addCell(cell);
    
        cell = new PdfPCell(new Phrase("Total:"));
        cell.setBorder(Rectangle.NO_BORDER);
        totalTable.addCell(cell);
        cell = new PdfPCell(new Phrase(invoice.getTotal().toString()));
        cell.setBorder(Rectangle.NO_BORDER);
        totalTable.addCell(cell);
    
        document.add(totalTable);
    
        document.add(Chunk.NEWLINE);
    
        Paragraph footer = new Paragraph("¡Gracias por su confianza!", new Font(Font.FontFamily.HELVETICA, 12, Font.ITALIC));
        footer.setAlignment(Element.ALIGN_CENTER);
        document.add(footer);
    
        document.close();
    
        return byteArrayOutputStream.toByteArray();
    }
    
    
}
