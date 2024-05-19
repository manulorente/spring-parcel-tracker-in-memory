package com.dam.parcelmanagement.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dam.parcelmanagement.service.InvoiceService;
import com.itextpdf.text.DocumentException;

import jakarta.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/invoices")
public class InvoiceController {

    private final Logger log = LoggerFactory.getLogger(InvoiceController.class);
    
    @Autowired
    private InvoiceService invoiceService;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/{invoiceId}/view-pdf")
    public void viewInvoicePdf(HttpServletResponse response, @PathVariable String invoiceId) throws IOException, DocumentException{
        log.info("View invoice PDF with id: " + invoiceId);
        byte[] pdfData = this.invoiceService.getInvoicePdf(Long.parseLong(invoiceId));

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=invoice.pdf");
        response.setContentLength(pdfData.length);

        response.getOutputStream().write(pdfData);
        response.getOutputStream().flush();
        response.getOutputStream().flush();
    }
}
