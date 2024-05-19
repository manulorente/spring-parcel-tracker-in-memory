package com.dam.parcelmanagement.service;

import com.dam.parcelmanagement.repository.ReportRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.transaction.Transactional;

import com.dam.parcelmanagement.model.Comment;
import com.dam.parcelmanagement.model.Invoice;
import com.dam.parcelmanagement.model.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private CommentService commentService;

    public List<Report> getAllReports() {
        return this.reportRepository.findAll();
    }

    public Report getReportById(Long id) {
        Optional<Report> report = this.reportRepository.findById(id);
        if (report.isPresent()) {
            return report.get();
        } else {
            return null;
        }
    }

    @Transactional
    public Report createReport() {
        Report report = new Report();

        List<Comment> comments = this.commentService.getAllComments();
        double totalRating = 0.0;
        for (Comment comment : comments) {
            totalRating += comment.getRating();
        }
        double averageRating = comments.isEmpty() ? 0.0 : totalRating / comments.size();
        report.setAverageRating(averageRating);
    
        List<Invoice> invoices = this.invoiceService.getAllInvoices();
        double totalIncome = 0.0;
        for (Invoice invoice : invoices) {
            totalIncome += invoice.getTotal();
        }
        report.setTotalIncome(totalIncome);

        long numberOfDeliveries = this.deliveryService.getAllDeliveries().size();
        report.setNumberOfDeliveries(numberOfDeliveries);

        report.setDate(new Date());
        
        return this.reportRepository.save(report);
    }

    @Transactional
    public void deleteReport(Long id) {
        this.reportRepository.deleteById(id);
    }

    public byte[] getReportPdf(Report report) throws IOException, DocumentException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, byteArrayOutputStream);

        document.open();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        
        document.addTitle("Current Operations Report");
        document.addSubject("Operations Report");
        document.addKeywords("report, operations, statistics");
        document.addAuthor("Parcel Management");
        document.addCreator("Parcel Management");

        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        Paragraph title = new Paragraph("Current Operations Report", titleFont);
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);
        
        document.add(new Paragraph(" "));

        Font sectionTitleFont = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
        Font textFont = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

        document.add(new Paragraph("Report Date: " + dateFormat.format(new Date()), textFont));
        document.add(new Paragraph(" "));

        Paragraph averageRatingTitle = new Paragraph("Average Rating", sectionTitleFont);
        document.add(averageRatingTitle);
        document.add(new Paragraph("" + report.getAverageRating(), textFont));
        document.add(new Paragraph(" "));

        Paragraph totalIncomeTitle = new Paragraph("Total Income", sectionTitleFont);
        document.add(totalIncomeTitle);
        document.add(new Paragraph("" + report.getTotalIncome(), textFont));
        document.add(new Paragraph(" "));

        Paragraph numberOfDeliveriesTitle = new Paragraph("Number of Deliveries", sectionTitleFont);
        document.add(numberOfDeliveriesTitle);
        document.add(new Paragraph("" + report.getNumberOfDeliveries(), textFont));
        document.add(new Paragraph(" "));

        Paragraph commentsTitle = new Paragraph("Customer Comments", sectionTitleFont);
        document.add(commentsTitle);
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        PdfPCell cell1 = new PdfPCell(new Phrase("Date"));
        cell1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell1);

        PdfPCell cell2 = new PdfPCell(new Phrase("Rating"));
        cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell2);

        PdfPCell cell3 = new PdfPCell(new Phrase("Comment"));
        cell3.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(cell3);

        for (Comment comment : commentService.getAllComments()) {
            table.addCell(dateFormat.format(comment.getDate()));
            table.addCell(comment.getRating().toString());
            table.addCell(comment.getDescription());
        }

        document.add(table);

        document.close();

        return byteArrayOutputStream.toByteArray();

    }
    
}
