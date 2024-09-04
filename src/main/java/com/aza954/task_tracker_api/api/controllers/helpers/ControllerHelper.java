package com.aza954.task_tracker_api.api.controllers.helpers;

import com.aza954.task_tracker_api.api.exceptions.BadRequestException;
import com.aza954.task_tracker_api.api.exceptions.NotFoundException;
import com.aza954.task_tracker_api.store.entities.ProjectEntity;
import com.aza954.task_tracker_api.store.repositories.ProjectRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class ControllerHelper {
    private final ProjectRepository projectRepository;

    public ControllerHelper(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public ProjectEntity projectFromId(long projectId){

        return projectRepository.findById(projectId)
                .orElseThrow(() -> new NotFoundException("Не найдено"));
    }
}
