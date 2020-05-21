package com.akulkarni.ppmtool.repositories;

import com.akulkarni.ppmtool.domain.ProjectTask;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectTaskRepository extends CrudRepository<ProjectTask, Long> {
    Iterable<ProjectTask> findByProjectIdentifierOrderByPriority(String id);

    ProjectTask findProjectByProjectSequence(String ptId);
}
