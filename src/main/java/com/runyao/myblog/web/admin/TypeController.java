package com.runyao.myblog.web.admin;

import com.runyao.myblog.po.Type;

import com.runyao.myblog.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    // @Pageable 根据前端参数自动封装
    public String types(@PageableDefault(size=6, sort = {"id"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, Model model) {
        /*The object Pageable contains content, ifLastOrNot, tatal pages, number of contents each page, sort,....*/
        model.addAttribute("page", typeService.ListType(pageable));
        return "admin/types";
    }

    @GetMapping("/types/typeInput")
    public String addInput(Model model) {
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    @GetMapping("/types/{id}/typeInput")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        // System.out.println(id);
        return "admin/type-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes) {
        Type t1 = typeService.getTypeByName(type.getName());
        if (t1 != null) {
            result.rejectValue("name", "nameError", "This name is existed...");
        }
        if (result.hasErrors()) {
            return "admin/type-input";
        }
        // 把type对象自动封装到 name 值
        Type t = typeService.saveType(type);
        if (t == null) {
            redirectAttributes.addFlashAttribute("Message", "Error..");
        }else {
            redirectAttributes.addFlashAttribute("message",  "Post Successfully");

        }
        return "redirect:/admin/types";
    }
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, @PathVariable Long id,  RedirectAttributes redirectAttributes) {
        // type must be near to the result
        Type t1 = typeService.getTypeByName(type.getName());
        Type thisType = typeService.getType(id);
        if (thisType.getName().equals(type.getName())) return "redirect:/admin/types";
        if (t1 != null) {
            result.rejectValue("name", "nameError", "This name is existed...");
        }
        if (result.hasErrors()) {
            return "admin/type-input";
        }
        // 把type对象自动封装到 name 值
        Type t = typeService.update(id, type);
        if (t == null) {
            redirectAttributes.addFlashAttribute("Message", "Update Error..");
        }else {
            redirectAttributes.addFlashAttribute("message",  "Update Successfully");

        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        typeService.deleteType(id);
        redirectAttributes.addFlashAttribute("message", "delete successfully");
        return "redirect:/admin/types";
    }
}
