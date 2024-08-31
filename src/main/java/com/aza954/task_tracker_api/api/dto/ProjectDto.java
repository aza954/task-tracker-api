package com.aza954.task_tracker_api.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.Instant;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class ProjectDto {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    @JsonProperty("created_at")
    private Instant createdAt;


    @Override
    public String toString() {
        return "ProjectDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
