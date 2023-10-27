package com.privacare.repository;

import com.privacare.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    List<Task> findByCategoryIdOrderByCreatedAtDesc(Integer id);

    @Transactional
    @Modifying
    @Query("update Task t set t.content = :content,  t.state = :state where t.id = :id")
    Integer updateTaskBy(@Param("id") UUID taskId, @Param("content") String content, @Param("state") String state);
}