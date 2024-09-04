package com.aza954.task_tracker_api.store.repositories;

import com.aza954.task_tracker_api.store.entities.TaskEntity;
import com.aza954.task_tracker_api.store.entities.TaskStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TaskStateRepository extends JpaRepository<TaskStateEntity,Long> {
    Optional<TaskStateEntity> findTaskStateEntityByRightTaskStateIdIsNullAndProjectId(Long projectId);
}
