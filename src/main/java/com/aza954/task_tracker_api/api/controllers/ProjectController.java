package com.aza954.task_tracker_api.api.controllers;

import com.aza954.task_tracker_api.api.dto.ProjectDto;
import com.aza954.task_tracker_api.api.exceptions.BadRequestException;
import com.aza954.task_tracker_api.api.exceptions.NotFoundException;
import com.aza954.task_tracker_api.api.factories.ProjectDtoFactory;
import com.aza954.task_tracker_api.store.entities.ProjectEntity;
import com.aza954.task_tracker_api.store.repositories.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RestController
public class ProjectController {
    private final ProjectDtoFactory projectDtoFactory;
    private final ProjectRepository projectRepository;
    @Autowired
    public ProjectController(ProjectDtoFactory projectDtoFactory, ProjectRepository projectRepository) {
        this.projectDtoFactory = projectDtoFactory;
        this.projectRepository = projectRepository;
    }

    public static final String FETCH_PROJECT = "/api/projects";
    public static final String CREATE_PROJECT = "/api/projects";
    public static final String EDIT_PROJECT = "/api/projects/{project_id}";
    public static final String DELETE_PROJECT = "/api/projects/{project_id}";


    @GetMapping(FETCH_PROJECT)
    public List<ProjectDto> fetchProjects(@RequestParam(value = "prefix_name", required = false) Optional<String> optionalPrefixName){
        optionalPrefixName = optionalPrefixName.filter(prefixName -> !prefixName.trim().isEmpty());

        List<ProjectEntity> projectList;

        if (optionalPrefixName.isPresent()){
            projectList = projectRepository.findAllByNameStartsWithIgnoreCase(optionalPrefixName.get());

        }else {
            projectList = projectRepository.findAll();
        }


        return projectList.stream().map(projectDtoFactory::makeprojectDTO).collect(Collectors.toList());
    }

    @PostMapping(CREATE_PROJECT)
    public ProjectDto createProject(@RequestParam String name){
        //TODO
//        return projectDtoFactory.makeprojectDTO();
        ProjectEntity projectEntity = projectRepository.findByName(name).orElse(null);
        if (projectEntity != null){
            throw new BadRequestException("Проект с этим именем уже есть");
        }
        ProjectEntity project = new ProjectEntity(name);
        projectRepository.saveAndFlush(project);

        return projectDtoFactory.makeprojectDTO(project);

//        return null;
    }

    @PatchMapping(EDIT_PROJECT)
    public ProjectDto editProject(@PathVariable(name = "project_id") long project_id ,@RequestParam String name){
        if (name.trim().isEmpty()){
            throw  new BadRequestException("Имя не может быть пустым");
        }
       ProjectEntity project = projectRepository.findById(project_id)
               .orElseThrow(() -> new NotFoundException("Не найдено"));

       projectRepository.findByName(name)
               .filter(anotherProject -> !Objects.equals(anotherProject.getId(),project_id))
               .ifPresent(anotherProject ->{
                   throw new BadRequestException("Занят");
               });

       project.setName(name);
       project = projectRepository.saveAndFlush(project);
       return projectDtoFactory.makeprojectDTO(project);
    }

    @DeleteMapping(DELETE_PROJECT)
    public boolean deleteProject(@PathVariable(name = "project_id") long project_id){
        Optional<ProjectEntity> projectEntity = projectRepository.findById(project_id);
        if (projectEntity.isPresent()){
            projectRepository.delete(projectEntity.get());
            return true;
        }
        else {
            return false;
        }
    }


}
