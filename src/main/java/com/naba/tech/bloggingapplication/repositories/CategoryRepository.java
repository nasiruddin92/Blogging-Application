package com.naba.tech.bloggingapplication.repositories;

import com.naba.tech.bloggingapplication.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
