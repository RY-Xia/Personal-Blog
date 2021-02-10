package com.runyao.myblog.dao;

import com.runyao.myblog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author Runyao Xia
 * @date: 2021/2/3
 */

public interface BlogRepository extends JpaRepository<Blog, Long>, JpaSpecificationExecutor<Blog> {
    @Query("select b from Blog b where b.recommend = true")
    List<Blog> findTop(Pageable pageable);

    @Query("select b from Blog b where b.title like ?1 or b.content like ?1")
    Page<Blog> findByQuery(Pageable pageable, String query);

    @Transactional
    @Modifying
    @Query("update Blog b set b.viewNumber = b.viewNumber+1 where b.id=?1")
    int updateViews(Long id);

    // Jpa语法和mysql语法不一样， function是mysql内的方法
    @Query(value= "SELECT date_format(b.update_time, '%Y') as year  from t_blog b GROUP by year ORDER BY year DESC", nativeQuery=true)
    List<String> findGroupYear();
    // 上面找到年份之后，找到对应年份的blog

    @Query("select b from Blog b where function('date_format', b.updateTime, '%Y') =?1")
    List<Blog> findByYear(String year);
}
