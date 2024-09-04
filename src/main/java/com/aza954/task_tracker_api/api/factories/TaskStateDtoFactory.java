package com.aza954.task_tracker_api.api.factories;

import com.aza954.task_tracker_api.api.dto.TaskDto;
import com.aza954.task_tracker_api.api.dto.TaskStateDto;
import com.aza954.task_tracker_api.store.entities.TaskStateEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component

public class TaskStateDtoFactory {
    private final TaskDtoFactory taskDtoFactory;

    public TaskStateDtoFactory(TaskDtoFactory taskDtoFactory) {
        this.taskDtoFactory = taskDtoFactory;
    }

    public TaskStateDto taskStateDto(TaskStateEntity entity){
        System.out.println(entity.getTasks());
        List<TaskDto> taskDtos = null;
        if (entity.getTasks() != null){
            taskDtos = entity.getTasks().stream().map(taskDtoFactory::taskDto).collect(Collectors.toList());
        }

        TaskStateDto taskStateDto = new TaskStateDto(entity.getId(), entity.getName(),
                entity.getLeftTaskStateEntity().map(TaskStateEntity::getId).orElse(null),
                entity.getRightTaskStateEntity().map(TaskStateEntity::getId).orElse(null),
                entity.getCreatedAt(),
                taskDtos);
        return taskStateDto;
    }
}
