package com.aza954.task_tracker_api.api.factories;

import com.aza954.task_tracker_api.api.dto.ProjectDto;
import com.aza954.task_tracker_api.store.entities.ProjectEntity;
import org.springframework.stereotype.Component;

@Component

public class ProjectDtoFactory {
    public ProjectDto makeprojectDTO(ProjectEntity entity){
        ProjectDto projectDto = new ProjectDto(entity.getId(), entity.getName(), entity.getCreatedAt());
        return projectDto;
    }
}
