package com.runyao.myblog.web;

import com.runyao.myblog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Runyao Xia
 * @date: 2021/2/8
 */
@Controller
public class ArchiveShowController {

    @Autowired
    private BlogService blogService;

    // HashMap to construct the years and blogs
    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archiveBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "Archive";
    }
}
