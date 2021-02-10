package com.runyao.myblog.dao;

import com.runyao.myblog.po.Comment;
import com.runyao.myblog.po.Tag;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Runyao Xia
 * @date: 2021/2/8
 */

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
