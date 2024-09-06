package com.aza954.task_tracker_api.api.controllers;

import com.aza954.task_tracker_api.api.controllers.helpers.ControllerHelper;
import com.aza954.task_tracker_api.api.dto.TaskStateDto;
import com.aza954.task_tracker_api.api.exceptions.BadRequestException;
import com.aza954.task_tracker_api.api.factories.TaskStateDtoFactory;
import com.aza954.task_tracker_api.store.entities.ProjectEntity;
import com.aza954.task_tracker_api.store.entities.TaskStateEntity;
import com.aza954.task_tracker_api.store.repositories.ProjectRepository;
import com.aza954.task_tracker_api.store.repositories.TaskRepository;
import com.aza954.task_tracker_api.store.repositories.TaskStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@RestController
public class TaskStateController {
    private final TaskStateRepository taskStateRepository;
    private final TaskStateDtoFactory taskStateDtoFactory;
    private final ControllerHelper controllerHelper;
    private final ProjectRepository projectRepository;

    public static final String GET_TASK_STATES = "/api/projects/{project_id}/task-states";
    public static final String CREATE_TASK_STATE = "/api/projects/{project_id}/task-states";
    public static final String EDIT_PROJECT = "/api/projects/{project_id}";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";

    public TaskStateController(TaskStateRepository taskStateRepository, TaskStateDtoFactory taskStateDtoFactory, ControllerHelper controllerHelper, ProjectRepository projectRepository) {
        this.taskStateRepository = taskStateRepository;
        this.taskStateDtoFactory = taskStateDtoFactory;
        this.controllerHelper = controllerHelper;
        this.projectRepository = projectRepository;
    }


    @GetMapping(GET_TASK_STATES)
    public List<TaskStateDto> getTaskState(@PathVariable(name = "project_id") long projectId){
        ProjectEntity projectEntity =  controllerHelper.projectFromId(projectId);

        return projectEntity.getTaskStates().stream().map(taskStateDtoFactory::taskStateDto).collect(Collectors.toList());
    }


    @PostMapping(CREATE_TASK_STATE)
    public TaskStateDto createTaskState(@PathVariable(name = "project_id") long projectId,
                                        @RequestParam(name = "task_state_name") String taskSateName){
        if (taskSateName.trim().isEmpty()){
            throw new BadRequestException("Task state name can't be empty");
        }
        ProjectEntity projectEntity =  controllerHelper.projectFromId(projectId);
        projectEntity.getTaskStates().stream().map(TaskStateEntity::getName).filter(anotherTaskSateName -> anotherTaskSateName.equalsIgnoreCase(taskSateName)).findAny().ifPresent(it -> {
            throw new BadRequestException("Таска с этим именем уже есть");
        });
        TaskStateEntity anotherTaskState = taskStateRepository.findTaskStateEntityByRightTaskStateIdIsNullAndProjectId(projectId)
                .orElse(null);
        TaskStateEntity taskStateEntity = taskStateRepository
                .saveAndFlush(
                        TaskStateEntity.builder()
                                .name(taskSateName)
                                .project(projectEntity)

                                .build()
                        );
        System.out.println(taskStateEntity.getLeftTaskState());

        System.out.println(anotherTaskState + " another task");
        if (anotherTaskState != null){
            anotherTaskState.setRightTaskState(taskStateEntity);
            taskStateEntity.setLeftTaskState(anotherTaskState);
        }
        System.out.println(taskStateEntity.getLeftTaskState());
        return taskStateDtoFactory.taskStateDto(taskStateEntity);
    }


}
