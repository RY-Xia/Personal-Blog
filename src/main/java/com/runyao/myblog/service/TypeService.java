package com.runyao.myblog.service;

import com.runyao.myblog.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Runyao Xia
 * @date: 2021/2/1
 */
public interface TypeService {

    Type saveType(Type type);
    Type getType(Long id);
    Page<Type> ListType(Pageable pageable);
    Type update(Long id, Type type);
    void deleteType(Long id);
    List<Type> ListType();
    Type getTypeByName(String name);
    List<Type> listTypeByNumber(Integer size);
}
