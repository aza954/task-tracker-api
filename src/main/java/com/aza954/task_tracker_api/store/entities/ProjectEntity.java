package com.aza954.task_tracker_api.store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project")
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)


    private Long id;
    @Column(unique = true)
    private String name;

    private Instant createdAt = Instant.now();

    @OneToMany
    @JoinColumn(name = "project_id",referencedColumnName = "id")
    private List<TaskStateEntity> taskStates = new ArrayList<>();
    public ProjectEntity(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public List<TaskStateEntity> getTaskStates() {
        return taskStates;
    }

    public void setTaskStates(List<TaskStateEntity> taskStates) {
        this.taskStates = taskStates;
    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", taskStates=" + taskStates +
                '}';
    }
}
