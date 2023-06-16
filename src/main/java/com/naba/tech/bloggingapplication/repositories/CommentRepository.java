package com.naba.tech.bloggingapplication.repositories;

import com.naba.tech.bloggingapplication.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
