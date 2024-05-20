package com.dam.parcelmanagement.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dam.parcelmanagement.model.Address;
import com.dam.parcelmanagement.model.Delivery;
import com.dam.parcelmanagement.model.Invoice;
import com.dam.parcelmanagement.model.Packet;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.model.UserRole;
import com.dam.parcelmanagement.service.DeliveryService;
import com.dam.parcelmanagement.service.UserService;


@Controller
@RequestMapping("/deliveries")
public class DeliveryController {

    private final Logger log = LoggerFactory.getLogger(DeliveryController.class);

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/view")
    public String viewDelivery(@RequestParam("deliveryId") String deliveryId, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        boolean isAuthenticated = isUserAuthenticated(principal);
        boolean deliveryExists = deliveryService.existsById(Long.parseLong(deliveryId));
        if (deliveryExists) {
            Delivery delivery = deliveryService.getDeliveryById(Long.parseLong(deliveryId));
            model.addAttribute("deliveries", delivery);
            model.addAttribute("userAuthenticated", isAuthenticated);
            model.addAttribute("displayDetails", false);
            log.info("Tracking delivery with id: " + deliveryId);
            return "delivery-view";
        } else {
            if (isAuthenticated) {
                redirectAttributes.addFlashAttribute("errorMessage", "Delivery not found");
                return "redirect:/dashboard";
            }
            log.info("Invalid delivery id provided");
            model.addAttribute("errorMessage", "El envío no existe o no se encuentra disponible. Por favor, verifique el ID ingresado.");
            return "index";
        }
    }    

    @PostMapping("/view")
    public String viewDelivery(@RequestParam String deliveryId, Model model) {
        log.info("Tracking delivery with id: " + deliveryId);
        model.addAttribute("deliveryId", deliveryId);
        return "redirect:/deliveries/view?deliveryId=" + deliveryId;
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/{username}/view")
    public String viewUserDeliveries(Principal principal, @PathVariable String username, Model model) {
        log.info("Showing deliveries for user: " + username);
        boolean isAuthenticated = isUserAuthenticated(principal);
        List<Delivery> deliveries = this.deliveryService.getDeliveriesByUsername(username);
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("userAuthenticated", isAuthenticated);
        model.addAttribute("displayDetails", false);
        return "delivery-view";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/view/details")
    public String viewDeliveryDetails (Principal principal, @RequestParam String deliveryId, Model model) {
        log.info("Showing delivery details for delivery with id: " + deliveryId);
        boolean isAuthenticated = isUserAuthenticated(principal);
        Delivery delivery = this.deliveryService.getDeliveryById(Long.parseLong(deliveryId));
        model.addAttribute("deliveries", delivery);
        model.addAttribute("userAuthenticated", isAuthenticated);
        model.addAttribute("displayDetails", true);
        return "delivery-view";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/create")
    public String createDeliveryForm(Principal principal, Model model) {
        log.info("Showing delivery creation form");
        Delivery delivery = new Delivery();
        delivery.setSource(new Address());
        delivery.setPacket(new Packet());
        delivery.setDestination(new Address());
        delivery.setInvoice(new Invoice());
        delivery.getInvoice().setCustomerInfo(new Address());
        model.addAttribute("delivery", delivery);
        return "delivery-new";
    }

    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/create")
    public String createDelivery(Principal principal, @ModelAttribute Delivery delivery, Model model) throws ParseException {
        log.info("Creating delivery");
        User user = this.userService.getUserByUsername(principal.getName());
        delivery.setSource(user.getAddress());
        this.deliveryService.createDelivery(delivery);
        return "redirect:/dashboard";
    }

    
}
