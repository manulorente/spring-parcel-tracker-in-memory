package com.dam.parcelmanagement.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dam.parcelmanagement.service.CommentService;
import com.dam.parcelmanagement.model.Comment;

@Controller
@RequestMapping("/comments")
public class CommentController {

        private final Logger log = LoggerFactory.getLogger(UserController.class);

        @Autowired
        private CommentService commentService;

        @GetMapping("")
        public List<Comment> getComments() {
            log.info("Getting all comments");
            return this.commentService.getAllComments();
        }

        @GetMapping("/{id}")
        public Comment getCommentById(@PathVariable Long id) {
            log.info("Getting comment by id: " + id);
            return this.commentService.getCommentById(id);
        }

        @PutMapping("/{id}")
        public Comment updateComment(@PathVariable Long id, @RequestBody Comment commentDetails) {
            log.info("Updating comment with id: " + id);
            return this.commentService.updateComment(id, commentDetails);
        }

        @PostMapping("")
        public Comment createComment(@RequestBody Comment comment) {
            log.info("Creating comment");
            return this.commentService.createComment(comment.getUser().getId(), comment.getRating(), comment.getDescription());
        }

        @DeleteMapping("/{id}")
        public void deleteComment(@PathVariable Long id) {
            log.info("Deleting comment with id: " + id);
            this.commentService.deleteComment(id);
        }

        @DeleteMapping("")
        public void deleteAllComments() {
            log.info("Deleting all comments");
            this.commentService.deleteAllComments();
        }
        

    
}
