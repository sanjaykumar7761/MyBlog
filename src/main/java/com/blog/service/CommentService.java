package com.blog.service;

import com.blog.entities.Comment;
import com.blog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto saveComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentByPostId(long postId);

    CommentDto getCommentById(long postId, Long commentId);


    CommentDto updateComment(long postId, long id, CommentDto commentDto);

    void deleteComment(long postId, long id);
}

