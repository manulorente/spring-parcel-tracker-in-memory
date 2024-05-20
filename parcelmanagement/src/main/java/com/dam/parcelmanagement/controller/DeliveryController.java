package com.dam.parcelmanagement.controller;

import java.security.Principal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;

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

    // Inyecta una instancia de DeliveryService para utilizar sus métodos
    @Autowired
    private DeliveryService deliveryService;

    // Inyecta una instancia de UserService para utilizar sus métodos
    @Autowired
    private UserService userService;

    // Método para verificar si el usuario está autenticado y tiene los roles adecuados
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

    // Muestra la información de un envío específico
    @GetMapping("/view")
    public String viewDelivery(@RequestParam("deliveryId") String deliveryId, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        boolean isAuthenticated = isUserAuthenticated(principal);
        boolean deliveryExists = deliveryService.existsById(Long.parseLong(deliveryId));
        if (deliveryExists) {
            Delivery delivery = deliveryService.getDeliveryById(Long.parseLong(deliveryId));
            model.addAttribute("deliveries", delivery);
            model.addAttribute("userAuthenticated", isAuthenticated);
            model.addAttribute("displayDetails", false);
            return "delivery-view";
        } else {
            if (isAuthenticated) {
                redirectAttributes.addFlashAttribute("errorMessage", "No se encontró el envío con ID: " + deliveryId);
                return "redirect:/dashboard";
            }
            model.addAttribute("errorMessage", "El envío no existe o no se encuentra disponible. Por favor, verifique el ID ingresado.");
            return "index";
        }
    }

    // Redirige a la vista de detalles de un envío específico
    @PostMapping("/view")
    public String viewDelivery(@RequestParam String deliveryId, Model model) {
        model.addAttribute("deliveryId", deliveryId);
        return "redirect:/deliveries/view?deliveryId=" + deliveryId;
    }

    // Muestra los envíos de un usuario específico
    // Sólo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/{username}/view")
    public String viewUserDeliveries(Principal principal, @PathVariable String username, Model model) {
        boolean isAuthenticated = isUserAuthenticated(principal);
        List<Delivery> deliveries = this.deliveryService.getDeliveriesByUsername(username);
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("userAuthenticated", isAuthenticated);
        model.addAttribute("displayDetails", false);
        return "delivery-view";
    }

    // Muestra los detalles de un envío específico
    // Sólo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/view/details")
    public String viewDeliveryDetails(Principal principal, @RequestParam String deliveryId, Model model) {
        boolean isAuthenticated = isUserAuthenticated(principal);
        Delivery delivery = this.deliveryService.getDeliveryById(Long.parseLong(deliveryId));
        model.addAttribute("deliveries", delivery);
        model.addAttribute("userAuthenticated", isAuthenticated);
        model.addAttribute("displayDetails", true);
        return "delivery-view";
    }

    // Muestra el formulario para crear un nuevo envío
    // Sólo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @GetMapping("/create")
    public String createDeliveryForm(Principal principal, Model model) {
        Delivery delivery = new Delivery();
        delivery.setSource(new Address());
        delivery.setPacket(new Packet());
        delivery.setDestination(new Address());
        delivery.setInvoice(new Invoice());
        delivery.getInvoice().setCustomerInfo(new Address());
        model.addAttribute("delivery", delivery);
        return "delivery-new";
    }

    // Procesa la creación de un nuevo envío
    // Sólo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/create")
    public String createDelivery(Principal principal, @ModelAttribute Delivery delivery, Model model) throws ParseException {
        User user = this.userService.getUserByUsername(principal.getName());
        delivery.setSource(user.getAddress());
        this.deliveryService.createDelivery(delivery);
        return "redirect:/dashboard";
    }
}
