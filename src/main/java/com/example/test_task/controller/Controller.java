package com.example.test_task.controller;

import com.example.test_task.controller.dto.SimpleDto;
import com.example.test_task.controller.mapper.SimpleMapper;
import com.example.test_task.service.SimpleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/simple")
@RequiredArgsConstructor
public class Controller {
    private final SimpleMapper mapper;
    private final SimpleService service;

    @GetMapping("/{id}")
    public SimpleDto get(@PathVariable Long id) {
        var entity = service.findById(id);

        return mapper.toDto(entity);
    }

    @PostMapping("/create")
    public SimpleDto create(@RequestBody SimpleDto dto) {
        var entity = mapper.toEntity(dto);

        return mapper.toDto(service.save(entity));
    }

    @PutMapping("/update")
    public SimpleDto update(@RequestBody SimpleDto dto) {
        var entity = mapper.toEntity(dto);

        return mapper.toDto(service.update(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
