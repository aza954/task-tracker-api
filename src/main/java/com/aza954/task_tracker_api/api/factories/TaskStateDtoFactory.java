package com.aza954.task_tracker_api.api.factories;

import com.aza954.task_tracker_api.api.dto.TaskStateDto;
import com.aza954.task_tracker_api.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

@Component

public class TaskStateDtoFactory {
    public TaskStateDto taskStateDto(TaskStateEntity entity){
        TaskStateDto taskStateDto = new TaskStateDto(entity.getId(), entity.getName(), entity.getOrdinal(), entity.getCreatedAt());
        return taskStateDto;
    }
}
