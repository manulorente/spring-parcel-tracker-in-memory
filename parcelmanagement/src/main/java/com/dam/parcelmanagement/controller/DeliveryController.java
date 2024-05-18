package com.dam.parcelmanagement.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dam.parcelmanagement.service.DeliveryService;

@Controller
@RequestMapping("/deliveries")
public class DeliveryController {

    private final Logger log = LoggerFactory.getLogger(DeliveryController.class);

    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/track")
    public String trackDeliveryForm(@RequestParam("deliveryId") String deliveryId, Model model) {
        if (deliveryId != null) {
            log.info("Tracking delivery with id: " + deliveryId);
            model.addAttribute("deliveryId", deliveryId);
            model.addAttribute("deliveryStatus", "IN_TRANSIT");
        }
        return "delivery-view";
    }

    @PostMapping("/track")
    public String getMethodName(@RequestParam String deliveryId, Model model) {
        log.info("Tracking delivery with id: " + deliveryId);
        model.addAttribute("deliveryId", deliveryId);
        return "redirect:/deliveries/track?deliveryId=" + deliveryId;
    }
    
}
