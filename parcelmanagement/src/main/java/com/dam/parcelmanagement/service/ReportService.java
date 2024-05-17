package com.dam.parcelmanagement.service;

import com.dam.parcelmanagement.repository.ReportRepository;
import com.dam.parcelmanagement.model.Comment;
import com.dam.parcelmanagement.model.Invoice;
import com.dam.parcelmanagement.model.Report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private InvoiceService invoiceService;

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

    public Report createReport() {
        Report report = new Report();
        List<Comment> comments = this.commentService.getAllComments();
        List<Comment> commentsLastWeek = comments.stream().filter(comment -> comment.getDate().getTime() > System.currentTimeMillis() - 7*24*60*60*1000).toList();
        Double averageRating = commentsLastWeek.stream().mapToDouble(Comment::getRating).average().orElse(0.0);
        report.setAverageRating(averageRating);
        List<Invoice> invoices = this.invoiceService.getAllInvoices();
        List<Invoice> invoicesLastWeek = invoices.stream().filter(invoice -> invoice.getDate().getTime() > System.currentTimeMillis() - 7*24*60*60*1000).toList();
        Double totalIncome = invoicesLastWeek.stream().mapToDouble(Invoice::getTotal).sum();
        report.setTotalIncome(totalIncome);
        report.setNumberOfDeliveries((long) invoicesLastWeek.size());
        return this.reportRepository.save(report);
    }

    public void deleteReport(Long id) {
        this.reportRepository.deleteById(id);
    }

    public Report getLastWeekReport() {
        List<Report> reports = this.getAllReports();
        return reports.stream().filter(report -> report.getDate().getTime() > System.currentTimeMillis() - 7*24*60*60*1000).findFirst().orElse(null);
    }

    public void generatePDFReport() {
        // generate PDF report

    }

    
}
