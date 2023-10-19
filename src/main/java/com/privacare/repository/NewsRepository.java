package com.privacare.repository;

import com.privacare.model.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {

    @Transactional
    @Modifying
    @Query("update News n set n.title = coalesce(:title,n.title),  n.content =  coalesce(:content,n.content) where n.id = :id")
    Integer updateNewsBy(@Param("id") UUID newsId, @Param("title") String title, @Param("content") String content);
}