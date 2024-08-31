package com.aza954.task_tracker_api.api.factories;

import com.aza954.task_tracker_api.api.dto.TaskDto;
import com.aza954.task_tracker_api.store.entities.TaskEntity;
import org.springframework.stereotype.Component;

@Component

public class TaskDtoFactory {
    public TaskDto taskDto(TaskEntity entity){
        TaskDto taskDto = new TaskDto(entity.getId(), entity.getName(), entity.getCreatedAt(), entity.getDescription());
        return taskDto;
    }
}
