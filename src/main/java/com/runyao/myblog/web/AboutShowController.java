package com.runyao.myblog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Runyao Xia
 * @date: 2021/2/8
 */
@Controller
public class AboutShowController {

    @GetMapping("About")
    public String about() {
        return "About";
    }
}
