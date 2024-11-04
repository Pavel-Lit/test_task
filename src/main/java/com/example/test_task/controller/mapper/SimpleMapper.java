package com.example.test_task.controller.mapper;

import com.example.test_task.controller.dto.SimpleDto;
import com.example.test_task.domain.SimpleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SimpleMapper {

    SimpleDto toDto(SimpleEntity entity);

    SimpleEntity toEntity(SimpleDto dto);
}
