package com.practice.queryHub.services;

import com.practice.queryHub.dtos.CommentDTO;
import com.practice.queryHub.model.Comment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public interface CommentService {

    Comment createComment(CommentDTO commentDTO);
    Optional<Comment> getCommentById(UUID commentId);
    List<Comment> getCommentsByAnswerId(UUID answerId, int page, int size);
    List<Comment> getRepliesByCommentId(UUID commentId, int page, int size);

}
