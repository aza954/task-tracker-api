package com.aza954.task_tracker_api.store.entities;

import com.aza954.task_tracker_api.api.dto.TaskStateDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Getter
@Setter
@Table(name = "task_state")
public class TaskStateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;


    private String name;

    @OneToOne
    private TaskStateEntity leftTaskState;
    @OneToOne
    private TaskStateEntity rightTaskState;


    @Builder.Default
    private Instant createdAt = Instant.now();

    @ManyToOne
    private ProjectEntity project;


    @OneToMany
    @JoinColumn(name = "task_state_id", referencedColumnName = "id")
    private List<TaskEntity> tasks = new ArrayList<>();

    public Optional<TaskStateEntity> getLeftTaskStateEntity(){
        return Optional.ofNullable(leftTaskState);
    }
    public Optional<TaskStateEntity> getRightTaskStateEntity(){
        return Optional.ofNullable(rightTaskState);
    }


}
