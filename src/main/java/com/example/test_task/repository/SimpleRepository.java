package com.example.test_task.repository;

import com.example.test_task.domain.SimpleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimpleRepository extends JpaRepository<SimpleEntity, Long> {
}
