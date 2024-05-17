package com.dam.parcelmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dam.parcelmanagement.model.Comment;
import com.dam.parcelmanagement.model.User;
import com.dam.parcelmanagement.repository.CommentRepository;

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

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found with id " + commentId));

    }
    
    public Comment createComment(Long userId, Double rating, String description) {
        User user = this.userService.getUserById(userId);
        return this.commentRepository.save(new Comment(user, rating, description));
    }

    public Comment updateComment(Long commentId, Comment comment) {
        Optional<Comment> existingComment = commentRepository.findById(commentId);
        if (existingComment.isPresent()) {
            Comment updatedComment = existingComment.get();
            updatedComment.setUser(comment.getUser());
            updatedComment.setRating(comment.getRating());
            updatedComment.setDescription(comment.getDescription());
            return this.commentRepository.save(updatedComment);
        } else {
            throw new RuntimeException("Comment not found with id " + commentId);
        }
    }

    public void deleteComment(Long commentId) {
        this.commentRepository.deleteById(commentId);
    }

    public void deleteAllComments() {
        this.commentRepository.deleteAll();
    }
    
}
