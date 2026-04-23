package com.todolist2.repository;

import com.todolist2.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllBySchedule_Id(Long scheduleId);
}
