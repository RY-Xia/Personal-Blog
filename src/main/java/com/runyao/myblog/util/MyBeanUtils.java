package com.runyao.myblog.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Runyao Xia
 * @date: 2021/2/5
 */
public class MyBeanUtils {

    /**
     *
     */
    public static String[] getNullPropertyName(Object source) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(source);
        PropertyDescriptor[] pd = beanWrapper.getPropertyDescriptors();
        List<String> nullPropertyNames = new ArrayList<>();
        for (PropertyDescriptor p : pd) {
            String name = p.getName();
            if (beanWrapper.getPropertyValue(name) == null) {
                nullPropertyNames.add(name);
            }
        }
        return nullPropertyNames.toArray(new String[nullPropertyNames.size()]);
    }
}
