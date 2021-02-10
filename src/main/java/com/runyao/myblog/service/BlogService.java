package com.runyao.myblog.service;

import com.runyao.myblog.po.Blog;
import com.runyao.myblog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

/**
 * @author Runyao Xia
 * @date: 2021/2/3
 */
public interface BlogService {
    Blog getBlog(Long id);
    // covert markdown to html page
    Blog getBlogAndConvert(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);

    // for search fields, find the blogs by key words
    Page<Blog> listBlog(Pageable pageable, String query);

    Page<Blog> listBlog(Long id, Pageable pageable);

    Blog saveBlog(Blog blog);
    Blog update(Long id, Blog blog);
    void deleteBlog(Long id);
    List<Blog> listRecommendBlogTop(Integer size);

    // archive 匹配年份
    Map<String, List<Blog>> archiveBlog();

    // 拿到博客数量---> for archive
    Long countBlog();
}
