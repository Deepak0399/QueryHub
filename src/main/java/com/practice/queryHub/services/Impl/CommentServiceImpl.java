package com.practice.queryHub.services.Impl;

import com.practice.queryHub.dtos.CommentDTO;
import com.practice.queryHub.exception.CommentNotFoundException;
import com.practice.queryHub.model.Answer;
import com.practice.queryHub.model.Comment;
import com.practice.queryHub.repositories.AnswerRepository;
import com.practice.queryHub.repositories.CommentRepository;
import com.practice.queryHub.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AnswerRepository answerRepository;
    @Override
    public Comment createComment(CommentDTO commentDTO) {
        Comment comment = new Comment();
        comment.setId(UUID.randomUUID());
        comment.setContent(commentDTO.getContent());
        Optional<Answer> answer = answerRepository.findById(commentDTO.getAnswerId());
        answer.ifPresent(comment::setAnswer);

        if (commentDTO.getParentCommentId() != null) {
            Optional<Comment> parentComment = commentRepository.findById(commentDTO.getParentCommentId());
            parentComment.ifPresent(comment::setParentComment);
        }

        return commentRepository.save(comment);
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
        return commentRepository.findByAnswerId(answerId, PageRequest.of(offset, limit)).getContent();
    }

    @Override
    public List<Comment> getRepliesByCommentId(UUID commentId, int offset, int limit) {
        return commentRepository.findByParentCommentId(commentId, PageRequest.of(offset, limit)).getContent();
    }

    @Override
    public void deleteComment(UUID commentId) {
        if (commentRepository.findById(commentId).isPresent()) {
            commentRepository.deleteById(commentId);
        }
        else {
            throw new CommentNotFoundException("Comment Not Available To Delete");
        }
    }
}
