package com.runyao.myblog.web.admin;

import com.runyao.myblog.po.Tag;
import com.runyao.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author Runyao Xia
 * @date: 2021/2/1
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    // @Pageable 根据前端参数自动封装
    public String tags(@PageableDefault(size=6, sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        /*The object Pageable contains content, ifLastOrNot, tatal pages, number of contents each page, sort,....*/
        model.addAttribute("page", tagService.ListTag(pageable));
        return "admin/tags";
    }

    @GetMapping("/tags/tagInput")
    public String addInput(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tag-input";
    }

    @GetMapping("/tags/{id}/tagInput")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        // System.out.println(id);
        return "admin/tag-input";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes) {
        Tag t1 = tagService.getTagByName(tag.getName());
        if (t1 != null) {
            result.rejectValue("name", "nameError", "This name is existed...");
        }
        if (result.hasErrors()) {
            return "admin/tag-input";
        }
        // 把tag对象自动封装到 name 值
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            redirectAttributes.addFlashAttribute("Message", "Error..");
        }else {
            redirectAttributes.addFlashAttribute("message",  "Post Successfully");

        }
        return "redirect:/admin/tags";
    }
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id,  RedirectAttributes redirectAttributes) {
        // tag must be near to the result
        Tag t1 = tagService.getTagByName(tag.getName());
        Tag thisTag = tagService.getTag(id);
        if (thisTag.getName().equals(tag.getName())) return "redirect:/admin/tags";
        if (t1 != null) {
            result.rejectValue("name", "nameError", "This name is existed...");
        }
        if (result.hasErrors()) {
            return "admin/tag-input";
        }
        // 把tag对象自动封装到 name 值
        Tag t = tagService.update(id, tag);
        if (t == null) {
            redirectAttributes.addFlashAttribute("Message", "Update Error..");
        }else {
            redirectAttributes.addFlashAttribute("message",  "Update Successfully");

        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        tagService.deleteTag(id);
        redirectAttributes.addFlashAttribute("message", "delete successfully");
        return "redirect:/admin/tags";
    }
}
