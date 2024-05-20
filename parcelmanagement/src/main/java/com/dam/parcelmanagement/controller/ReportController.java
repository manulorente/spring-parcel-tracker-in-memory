package com.dam.parcelmanagement.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

import com.dam.parcelmanagement.model.Report;
import com.dam.parcelmanagement.service.ReportService;

@Controller
@RequestMapping("/reports")
public class ReportController {

    private final Logger log = LoggerFactory.getLogger(InvoiceController.class);
    
    @Autowired
    private ReportService reportService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/create")
    public void createReport(HttpServletResponse response) throws IOException, DocumentException{
        log.info("View latest report in PDF");
        Report weeklyReport = this.reportService.createReport();
        byte[] pdfData = this.reportService.getReportPdf(weeklyReport);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=invoice.pdf");
        response.setContentLength(pdfData.length);

        response.getOutputStream().write(pdfData);
        response.getOutputStream().flush();
        response.getOutputStream().flush();
    }
}
