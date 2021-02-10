package com.runyao.myblog.web;

import com.runyao.myblog.po.Tag;
import com.runyao.myblog.po.Type;
import com.runyao.myblog.service.BlogService;
import com.runyao.myblog.service.TagService;
import com.runyao.myblog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author Runyao Xia
 * @date: 2021/2/8
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    /**
     *
     * @param id 默认返回第一个分类的内容， 取第一个分类id， 页面初始化访问其所有的博客内容
     * @return
     */
    @GetMapping("/tags/{id}")
    public String types(@PathVariable Long id, @PageableDefault(size = 5, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        List<Tag> tags = tagService.listByNumber(500);
        if (id == -1) {
            id = tags.get(0).getId();
        }else {

        }
        BlogQuery blogQuery = new BlogQuery();
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        // 传递id
        model.addAttribute("activeTagId", id);
        return "tags";
    }
}
