package com.aza954.task_tracker_api.store.repositories;

import com.aza954.task_tracker_api.store.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<ProjectEntity,Long> {
    Optional<ProjectEntity> findByName(String name);

    List<ProjectEntity> findAll();

    List<ProjectEntity> findAllByNameStartsWithIgnoreCase(String prefixName);
}
