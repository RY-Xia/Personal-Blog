package com.runyao.myblog.service;

import com.runyao.myblog.po.Comment;

import java.util.List;

/**
 * @author Runyao Xia
 * @date: 2021/2/8
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long id);
    Comment saveComment(Comment comment);
}
