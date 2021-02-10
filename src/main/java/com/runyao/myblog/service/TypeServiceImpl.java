package com.runyao.myblog.service;

import com.runyao.myblog.NotFoundException;
import com.runyao.myblog.dao.TypeRepository;
import com.runyao.myblog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Runyao Xia
 * @date: 2021/2/1
 */
@Service
public class TypeServiceImpl implements TypeService{

    @Autowired
    private TypeRepository typeRepository;

    /** @Transaction The @Transactional annotation is metadata that specifies that an interface, class,
     * or method must have transactional semantics; for example,
     * "start a brand new read-only transaction when this method is invoked, suspending any existing transaction"
     */

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }
    @Transactional
    @Override
    public Page<Type> ListType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }
    @Transactional
    @Override
    public Type update(Long id, Type type) {
        Type t = getType(id);
        if (t == null) {
            throw new NotFoundException("Category not found....");
        }
        BeanUtils.copyProperties(type, t);
        return typeRepository.save(t);
    }
    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    @Override
    public List<Type> ListType() {

        return typeRepository.findAll();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Override
    public List<Type> listTypeByNumber(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, sort);
        return typeRepository.findTop(pageable);
    }
}
