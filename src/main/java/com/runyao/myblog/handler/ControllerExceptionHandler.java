package com.runyao.myblog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler (HttpServletRequest request, Exception exc) throws Exception {
        logger.error("Request URL : {}, Exception : {}", request.getRequestURL(), exc);
        // 不能拦截所有异常
        if (AnnotationUtils.findAnnotation(exc.getClass(), ResponseStatus.class) != null) {
            throw exc; // 交给springboot来处理
        }
        ModelAndView mv = new ModelAndView();
        mv.addObject("url", request.getRequestURL());
        mv.addObject("exception", exc);
        mv.setViewName("error/error");
        return mv;
    }
}
