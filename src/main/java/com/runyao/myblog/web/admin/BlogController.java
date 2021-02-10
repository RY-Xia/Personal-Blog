package com.runyao.myblog.web.admin;

import com.runyao.myblog.po.Blog;
import com.runyao.myblog.po.User;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.ParseException;

/**
 * @author Runyao Xia
 * @date: 2021/1/30
 */

@Controller
@RequestMapping("/admin")
public class BlogController {

    private static final String INPUT = "admin/input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs"; // 注意redirect就后面放的是域名而不是html名

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blog(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                       BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.ListType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }
    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         BlogQuery blog, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }
    @GetMapping("/blogs/input")
    public String input(Model model) {
        // 初始化为了拿到修改页面的值
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return INPUT;
    }
    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.ListType());
        model.addAttribute("tags", tagService.listTag());
    }
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id,  Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return INPUT;
    }

    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes redirectAttributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));// line 77 in input.html, front-end only passes the id
        blog.setTags(tagService.listTag(blog.getTagIds())); // line 91 in input.html
        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        }else {
            b = blogService.update(blog.getId(), blog);
        }
        if (b == null) {
            redirectAttributes.addFlashAttribute("Message", "Error..");
        }else {
            redirectAttributes.addFlashAttribute("Message", "Success");
        }
        return REDIRECT_LIST;
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        blogService.deleteBlog(id);
        redirectAttributes.addFlashAttribute("message", "delete successfully");
        return REDIRECT_LIST;
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model) {
        model.addAttribute("newBlogs", blogService.listRecommendBlogTop(3));
        return "_fragment :: newBlogList";
    }
}
