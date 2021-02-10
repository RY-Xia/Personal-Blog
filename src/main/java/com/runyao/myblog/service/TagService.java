package com.runyao.myblog.service;

import com.runyao.myblog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Runyao Xia
 * @date: 2021/2/1
 */
public interface TagService {

    Tag saveTag(Tag tag);
    Tag getTag(Long id);
    Page<Tag> ListTag(Pageable pageable);
    Tag update(Long id, Tag tag);
    void deleteTag(Long id);
    List<Tag> listTag();
    List<Tag> listTag(String ids);
    Tag getTagByName(String name);
    List<Tag> listByNumber(Integer size);
}
