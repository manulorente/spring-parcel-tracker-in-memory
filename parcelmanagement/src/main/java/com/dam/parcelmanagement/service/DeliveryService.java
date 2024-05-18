package com.dam.parcelmanagement.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.model.Address;
import com.dam.parcelmanagement.model.Delivery;
import com.dam.parcelmanagement.model.Invoice;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.repository.DeliveryRepository;
import com.dam.parcelmanagement.exception.ResourceNotFoundException;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private InvoiceService invoiceService;

    
    public List<Delivery> getAllDeliveries() {
        return this.deliveryRepository.findAll();
    }

    public Boolean existsById(Long id) {
        return this.deliveryRepository.existsById(id);
    }

    public Delivery getDeliveryById(Long id) {
        return this.deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
    }

    public Delivery createDelivery(Delivery delivery) {
        User user = this.userService.getUserById(delivery.getSource().getId());
        delivery.setSource(this.addressService.getAddressById(user.getAddress().getId()));
        Address destination = delivery.getDestination();
        if (this.addressService.getAddressById(destination.getId()) == null) {
            this.addressService.createAddress(destination);
        }
        Invoice invoice = new Invoice();
        Double packetVolume = delivery.getPacket().getPacketHeight()*delivery.getPacket().getPacketWidth()*delivery.getPacket().getPacketLength();
        Double price;
        Integer days;
        Double tax = 0.1;
        // calculate price based on packet type
        switch (delivery.getPacket().getPacketType()) {
            case ENVELOPE:
                price = 2.0;
                break;
            case BOX:
                price = 5.0 + packetVolume/1000;
                break;
            case DOCUMENT:
                price = 1.0;
                break;
            default:
                price = 0.0;
                break;
        }
        // if weight is greater than 1kg, add 10% to the price
        if (delivery.getPacket().getPacketWeight() > 1.0) {
            price *= 1.1;
        }
        // calculate price based on transportation type
        switch (delivery.getTransportation()) {
            case URGENT:
                price *= 1.5;
                days = 1;
                break;
            case EXPRESS:
                price *= 1.2;
                days = 3;
                break;
            default:
                price *= 1.0;
                days = 7;
                break;
        }
        invoice.setPrice(price);
        invoice.setTax(tax);
        invoice.setTotal(invoice.getPrice()*(1+invoice.getTax()));
        invoice.setDate(new Date());
        invoice.setDueDate(new Date(invoice.getDate().getTime() + days*24*60*60*1000));
        invoice.setCustomerInfo(delivery.getSource());
        invoice.setServiceInfo(" Delivery  from " + delivery.getSource().toString() + " to " + delivery.getDestination().toString());
        delivery.setInvoice(this.invoiceService.createInvoice(invoice));
        return this.deliveryRepository.save(delivery);
    }

    public Delivery updateDelivery(Long id, Delivery deliveryDetails) {
        Delivery delivery = this.deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
        delivery.setPacket(deliveryDetails.getPacket());
        delivery.setTransportation(deliveryDetails.getTransportation());
        delivery.setDestination(deliveryDetails.getDestination());
        delivery.setDeliveryDate(deliveryDetails.getDeliveryDate());
        delivery.setEstimatedArrivalDate(deliveryDetails.getEstimatedArrivalDate());
        delivery.setStatus(deliveryDetails.getStatus());
        return this.deliveryRepository.save(delivery);
    }

    public void deleteDelivery(Long id) {
        this.deliveryRepository.deleteById(id);
    }
    
}
