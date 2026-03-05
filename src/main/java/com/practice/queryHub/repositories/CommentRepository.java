package com.practice.queryHub.repositories;

import com.practice.queryHub.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByAnswerId(UUID answerId, Pageable pageable);
    Page<Comment> findByParentCommentId(UUID parentCommentId, Pageable pageable);
}
