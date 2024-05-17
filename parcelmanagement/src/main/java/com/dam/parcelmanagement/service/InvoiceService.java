package com.dam.parcelmanagement.service;

import com.dam.parcelmanagement.exception.ResourceNotFoundException;
import com.dam.parcelmanagement.model.Invoice;
import com.dam.parcelmanagement.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;

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

    public Invoice createInvoice(Invoice invoice) {
        return this.invoiceRepository.save(invoice);
    }

    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Optional<Invoice> invoice = this.invoiceRepository.findById(id);
        if (invoice.isPresent()) {
            Invoice invoiceToUpdate = invoice.get();
            invoiceToUpdate.setPrice(invoiceDetails.getPrice());
            invoiceToUpdate.setTax(invoiceDetails.getTax());
            invoiceToUpdate.setDate(invoiceDetails.getDate());
            invoiceToUpdate.setDueDate(invoiceDetails.getDueDate());
            invoiceToUpdate.setTotal(invoiceDetails.getTotal());
            invoiceToUpdate.setCustomerInfo(invoiceDetails.getCustomerInfo());
            invoiceToUpdate.setServiceInfo(invoiceDetails.getServiceInfo());
            return this.invoiceRepository.save(invoiceToUpdate);
        } else {
            throw new ResourceNotFoundException("Invoice not found with id: " + id);
        }
    }

    public void deleteInvoice(Long id) {
        this.invoiceRepository.deleteById(id);
    }
    
}
