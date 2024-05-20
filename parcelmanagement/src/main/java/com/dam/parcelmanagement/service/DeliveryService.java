package com.dam.parcelmanagement.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.model.Address;
import com.dam.parcelmanagement.model.Delivery;
import com.dam.parcelmanagement.model.Invoice;
import com.dam.parcelmanagement.model.Packet;
import com.dam.parcelmanagement.model.PacketStatus;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.repository.DeliveryRepository;

import jakarta.transaction.Transactional;

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

    public Boolean existsById(Long id) {
        return this.deliveryRepository.existsById(id);
    }
    
    public List<Delivery> getAllDeliveries() {
        return this.deliveryRepository.findAll();
    }

    public Delivery getDeliveryById(Long id) {
        return this.deliveryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Delivery not found with id: " + id));
    }

    public List<Delivery> getDeliveriesByUsername (String username) {
        User user = this.userService.getUserByUsername(username);
        Address address = this.addressService.getAddressById(user.getAddress().getId());
        return this.deliveryRepository.findBySourceId(address.getId());
        
    }
    @Transactional
    public Delivery createDelivery(Delivery delivery) throws ParseException {
        User user = userService.getUserById(delivery.getSource().getId());
        delivery.setSource(addressService.getAddressById(user.getAddress().getId()));
        addressService.createAddress(delivery.getDestination());

        Double packetVolume = delivery.getPacket().getPacketHeight() * delivery.getPacket().getPacketWidth() * delivery.getPacket().getPacketLength();
        Double price = calculatePrice(delivery.getPacket(), packetVolume);
        Integer days = calculateDaysAndStatus(delivery);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(new Date());
        Date deliveryDate = dateFormat.parse(formattedDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(deliveryDate);
        cal.add(Calendar.DATE, days);
        Date estimatedArrivalDate = cal.getTime();

        delivery.setDeliveryDate(deliveryDate);
        delivery.setEstimatedArrivalDate(estimatedArrivalDate);

        Invoice invoice = createInvoice(delivery, price);
        delivery.setInvoice(invoiceService.createInvoice(invoice));

        return deliveryRepository.save(delivery);
    }

    @Transactional
    public Delivery updateDelivery(Long id, Delivery delivery) {
        Delivery existingDelivery = this.getDeliveryById(id);
        existingDelivery.setSource(delivery.getSource());
        existingDelivery.setDestination(delivery.getDestination());
        existingDelivery.setPacket(delivery.getPacket());
        existingDelivery.setTransportation(delivery.getTransportation());
        existingDelivery.setInvoice(delivery.getInvoice());
        return this.deliveryRepository.save(existingDelivery);
    }

    @Transactional
    public void deleteDelivery(Long id) {
        this.deliveryRepository.deleteById(id);
    }
    
    private Double calculatePrice(Packet packet, Double packetVolume) {
        Double price;
        switch (packet.getPacketType()) {
            case ENVELOPE:
                price = 2.0;
                break;
            case BOX:
                price = 5.0 + packetVolume / 1000;
                break;
            case DOCUMENT:
                price = 1.0;
                break;
            default:
                price = 0.0;
                break;
        }
        if (packet.getPacketWeight() > 1.0) {
            price *= 1.1;
        }
        return price;
    }

    private Integer calculateDaysAndStatus(Delivery delivery) {
        Integer days;
        switch (delivery.getTransportation()) {
            case URGENT:
                delivery.setStatus(PacketStatus.IN_TRANSIT);
                days = 1;
                break;
            case EXPRESS:
                delivery.setStatus(PacketStatus.PENDING);
                days = 3;
                break;
            default:
                delivery.setStatus(PacketStatus.PENDING);
                days = 7;
                break;
        }
        return days;
    }

    private Invoice createInvoice(Delivery delivery, Double price) {
        Invoice invoice = new Invoice();
        invoice.setServiceInfo("Envío de " + delivery.getPacket().getPacketType() + " a " + delivery.getDestination().getCity() + ", " + delivery.getDestination().getCountry());
        invoice.setPrice(price);
        invoice.setTax(0.1);
        invoice.setTotal(price * (1 + 0.1));
        invoice.setDate(delivery.getDeliveryDate());
        invoice.setDueDate(delivery.getEstimatedArrivalDate());
        invoice.setCustomerInfo(delivery.getSource());
        return invoice;
    }
}
