package com.dam.parcelmanagement.controller;

import java.io.IOException;

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
    
    // Inyecta una instancia de ReportService para gestionar los informes
    @Autowired
    private ReportService reportService;

    // Método para crear y mostrar un informe en formato PDF
    // Solo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/create")
    public void createReport(HttpServletResponse response) throws IOException, DocumentException {
        // Crea un nuevo informe semanal
        Report weeklyReport = this.reportService.createReport();
        // Obtiene los datos del PDF del informe como un array de bytes
        byte[] pdfData = this.reportService.getReportPdf(weeklyReport);

        // Configura la respuesta HTTP para que el navegador muestre el PDF en línea
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=invoice.pdf");
        response.setContentLength(pdfData.length);

        // Escribe los datos del PDF en el output stream de la respuesta
        response.getOutputStream().write(pdfData);
        response.getOutputStream().flush();
    }
}
