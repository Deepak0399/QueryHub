package com.practice.queryHub.repositories;

import com.practice.queryHub.model.Answer;
import com.practice.queryHub.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByAnswerId(UUID answerId, Pageable pageable);
    Page<Comment> findByParentCommentId(UUID parentCommentId, Pageable pageable);

    UUID answer(Answer answer);
}
