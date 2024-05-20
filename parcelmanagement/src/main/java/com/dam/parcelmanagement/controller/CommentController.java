package com.dam.parcelmanagement.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.security.access.prepost.PreAuthorize;

import com.dam.parcelmanagement.service.CommentService;
import com.dam.parcelmanagement.model.Comment;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/comments")
public class CommentController {

    // Logger para registrar información y eventos importantes
    private final Logger log = LoggerFactory.getLogger(UserController.class);

    // Inyecta una instancia de CommentService para utilizar sus métodos
    @Autowired
    private CommentService commentService;

    // Muestra todos los comentarios
    // Añade los comentarios y el nombre de usuario al modelo
    @GetMapping("/view")
    public String getAllComments(@ModelAttribute Comment comment, Principal principal, Model model) {
        log.info("Get all comments");
        model.addAttribute("comments", commentService.getAllComments());
        if (principal != null) {
            model.addAttribute("username", principal.getName());
        }
        model.addAttribute("newComment", comment);
        return "comments";
    }

    // Permite crear un nuevo comentario
    // Sólo accesible para usuarios con roles 'ROLE_ADMIN' o 'ROLE_CUSTOMER'
    // Añade el nuevo comentario al modelo y redirige a la vista de comentarios
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CUSTOMER')")
    @PostMapping("/create")
    public String createComment(@ModelAttribute Comment newComment, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        log.info("Create comment");
        Comment comment = this.commentService.createComment(newComment, principal.getName());
        redirectAttributes.addFlashAttribute("newComment", comment);
        return "redirect:/comments/view";
    }
}
