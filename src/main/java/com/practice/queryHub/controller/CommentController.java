package com.practice.queryHub.controller;

import com.practice.queryHub.dtos.CommentDTO;
import com.practice.queryHub.model.Comment;
import com.practice.queryHub.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentDTO commentDTO) {
        Comment comment = commentService.createComment(commentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<Comment> getCommentById(@PathVariable UUID commentId) {
        Optional<Comment> comment = commentService.getCommentById(commentId);
        return comment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/answer/{answerId}")
    public ResponseEntity<List<Comment>> getCommentByAnswerId(@PathVariable UUID answerId, @RequestParam int page, @RequestParam int size) {
        List<Comment> comments = commentService.getCommentsByAnswerId(answerId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @GetMapping("comment/{commentId}")
    public ResponseEntity<List<Comment>> getRepliesByCommentId(@PathVariable UUID commentId, @RequestParam int page, @RequestParam int size) {
        List<Comment> replies = commentService.getRepliesByCommentId(commentId, page, size);
        return ResponseEntity.status(HttpStatus.OK).body(replies);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable UUID commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
