package com.dam.parcelmanagement.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dam.parcelmanagement.service.CommentService;
import com.dam.parcelmanagement.model.Comment;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/comments")
public class CommentController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CommentService commentService;

    @GetMapping("/view")
    public String getAllComments(Model model, Principal principal) {
        log.info("Get all comments");
        model.addAttribute("comments", commentService.getAllComments());
        model.addAttribute("comment", new Comment());
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        return "comments";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping("/create")
    public String createComment(Principal principal, Model model) {
        log.info("Show comment creation form");
        model.addAttribute("comment", new Comment());
        model.addAttribute("username", principal.getName());
        return "comments";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/create")
    public String createComment(@ModelAttribute Comment comment, Principal principal) {
        log.info("Create comment");
        this.commentService.createComment(comment, principal.getName());
        return "redirect:/comments/view";
    }


    
}
