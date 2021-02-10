package com.runyao.myblog.web;

import com.runyao.myblog.NotFoundException;
import com.runyao.myblog.po.Blog;
import com.runyao.myblog.service.BlogService;
import com.runyao.myblog.service.TagService;
import com.runyao.myblog.service.TypeService;
import com.runyao.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"},
                        direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeByNumber(6));
        model.addAttribute("tags", tagService.listByNumber(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(6));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 15, sort = {"updateTime"},
                         direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, "%" + query + "%")); //模糊查询
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,  Model model) {
        Blog b = blogService.getBlog(id);
        model.addAttribute("blog", blogService.getBlogAndConvert(id));
        return "blog";
    }
    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model) {
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "_fragment :: newBlogList";
    }
}
