package com.dam.parcelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dam.parcelmanagement.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    
}
