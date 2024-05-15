package com.dam.parcelmanagement.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LandingController {

    private final Logger log = LoggerFactory.getLogger(LandingController.class);

    @GetMapping(value = {"/", "/index"})
    public String index(Model model) {
        log.info("Landing page accessed");
        model.addAttribute("message", "Welcome to the Parcel Management System!");
        return "index";
    }
    
}
