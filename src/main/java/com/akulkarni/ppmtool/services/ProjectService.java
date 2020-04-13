package com.akulkarni.ppmtool.services;

import com.akulkarni.ppmtool.domain.Project;
import com.akulkarni.ppmtool.repositories.ProjectRepository;
import exceptions.ProjectIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    public Project saveOrUpdateProject(Project project) {
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project Name should be Unique Project :" + project.getProjectIdentifier().toUpperCase() + " Exist");
        }
    }

    public Project findProjectByIdentifier(String projectId) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project Name should be Unique Project :" + project + " Exist");
        }
        return project;
    }

    public void deleteProjectByProjectId(String projectId){
        Project project = projectRepository.deleteByProjectIdentifier(projectId.toUpperCase());
        projectRepository.delete(project);
    }

}
