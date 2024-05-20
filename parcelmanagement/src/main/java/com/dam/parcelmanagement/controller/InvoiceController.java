package com.dam.parcelmanagement.controller;

import java.io.IOException;

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
    
    // Inyecta una instancia de InvoiceService para gestionar las facturas
    @Autowired
    private InvoiceService invoiceService;

    // Método para mostrar una factura en formato PDF
    // Solo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/{invoiceId}/view-pdf")
    public void viewInvoicePdf(HttpServletResponse response, @PathVariable String invoiceId) throws IOException, DocumentException {
        // Obtiene los datos del PDF de la factura como un array de bytes
        byte[] pdfData = this.invoiceService.getInvoicePdf(Long.parseLong(invoiceId));

        // Configura la respuesta HTTP para que el navegador muestre el PDF en línea
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=invoice.pdf");
        response.setContentLength(pdfData.length);

        // Escribe los datos del PDF en el output stream de la respuesta
        response.getOutputStream().write(pdfData);
        response.getOutputStream().flush();
    }
}
