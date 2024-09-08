package com.aza954.task_tracker_api.api.dto;

import com.aza954.task_tracker_api.store.entities.TaskEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TaskStateDto {
    @NonNull
    private Long id;

    @NonNull
    private String name;


    @JsonProperty("left_task_state_id")
    private Long leftTaskStateId;

    @JsonProperty("right_task_state_id")
    private Long rightTaskStateId;


    @JsonProperty("created_at")
    private Instant createdAt;


    List<TaskDto> taskDtoList;


}
