package com.practice.queryHub.services.Impl;

import com.practice.queryHub.dtos.CommentDTO;
import com.practice.queryHub.exception.CommentNotFoundException;
import com.practice.queryHub.model.Comment;
import com.practice.queryHub.repositories.CommentRepository;
import com.practice.queryHub.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    @Override
    public Comment createComment(CommentDTO commentDTO) {
        return null;
    }

    @Override
    public Optional<Comment> getCommentById(UUID commentId) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            return comment;
        }
        else {
            throw new CommentNotFoundException("Comment Not Found");
        }
    }

    @Override
    public List<Comment> getCommentsByAnswerId(UUID answerId, int offset, int limit) {
//        commentRepository.findByAnswerId(answerId, (Pageable) PageRequest.of(offset, limit));
        return null;
    }

    @Override
    public List<Comment> getRepliesByCommentId(UUID commentId, int page, int size) {
        return List.of();
    }
}
