package com.example.test_task.service;

import com.example.test_task.domain.SimpleEntity;
import com.example.test_task.repository.SimpleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SimpleService {
    private final SimpleRepository repository;

    public SimpleEntity findById(Long id) {
        return repository.findById(id).orElseThrow(()-> new RuntimeException("Entity not found"));
    }

    public SimpleEntity save(SimpleEntity entity) {
        return repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public SimpleEntity update(SimpleEntity entity) {
        var entityFromDb = this.findById(entity.getId());
        entityFromDb.setData(entity.getData());
        return repository.save(entity);
    }
}
