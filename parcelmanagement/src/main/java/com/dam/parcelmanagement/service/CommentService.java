package com.dam.parcelmanagement.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.model.Comment;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.repository.CommentRepository;

import jakarta.transaction.Transactional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public List<Comment> getAllComments() {
        return this.commentRepository.findAll();
    }
    
    @Transactional
    public Comment createComment(Comment comment, String username) {
        User user = this.userService.getUserByUsername(username);
        Comment newComment = new Comment();
        newComment.setDescription(comment.getDescription());
        newComment.setRating(comment.getRating());
        newComment.setUser(user);
        newComment.setDate(new Date());
        return this.commentRepository.save(newComment);
    }
    
}
