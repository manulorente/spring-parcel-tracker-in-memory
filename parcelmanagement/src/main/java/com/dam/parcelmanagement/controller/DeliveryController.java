package com.dam.parcelmanagement.controller;

import java.security.Principal;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dam.parcelmanagement.model.Delivery;
import com.dam.parcelmanagement.model.UserRole;
import com.dam.parcelmanagement.service.DeliveryService;

@Controller
@RequestMapping("/deliveries")
public class DeliveryController {

    private final Logger log = LoggerFactory.getLogger(DeliveryController.class);

    @Autowired
    private DeliveryService deliveryService;

        private boolean isUserAuthenticated(Principal principal) {
        if (principal instanceof Authentication) {
            Authentication authentication = (Authentication) principal;
            Collection<String> roles = authentication.getAuthorities().stream()
                    .map(r -> r.getAuthority())
                    .toList();
            return roles.contains(UserRole.ROLE_ADMIN.name()) || roles.contains(UserRole.ROLE_CUSTOMER.name());
        }
        return false;
    }

    @GetMapping("/track")
    public String trackDeliveryForm(@RequestParam("deliveryId") String deliveryId, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        boolean isAuthenticated = isUserAuthenticated(principal);
        boolean deliveryExists = deliveryService.existsById(Long.parseLong(deliveryId));
        if (deliveryExists) {
            Delivery delivery = deliveryService.getDeliveryById(Long.parseLong(deliveryId));
            model.addAttribute("delivery", delivery);
            model.addAttribute("userAuthenticated", isAuthenticated);
            log.info("Tracking delivery with id: " + deliveryId);
            return "delivery-view";
        } else {
            if (isAuthenticated) {
                redirectAttributes.addFlashAttribute("errorMessage", "Delivery not found");
                return "redirect:/dashboard";
            }
            log.info("Invalid delivery id provided");
            model.addAttribute("errorMessage", "Delivery not found");
            return "index";
        }
    }

    @PostMapping("/track")
    public String getMethodName(@RequestParam String deliveryId, Model model) {
        log.info("Tracking delivery with id: " + deliveryId);
        model.addAttribute("deliveryId", deliveryId);
        return "redirect:/deliveries/track?deliveryId=" + deliveryId;
    }
    
}
