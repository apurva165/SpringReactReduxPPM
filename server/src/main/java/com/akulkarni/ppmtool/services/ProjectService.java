package com.akulkarni.ppmtool.services;

import com.akulkarni.ppmtool.domain.Backlog;
import com.akulkarni.ppmtool.domain.Project;
import com.akulkarni.ppmtool.domain.User;
import com.akulkarni.ppmtool.repositories.BacklogRepository;
import com.akulkarni.ppmtool.repositories.ProjectRepository;
import com.akulkarni.ppmtool.repositories.UserRepository;
import exceptions.ProjectIdException;
import exceptions.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private BacklogRepository backlogRepository;
    @Autowired
    private UserRepository userRepository;

    public Project saveOrUpdateProject(Project project, String userName) {

        if(project.getId() != null){
            Project existingProject = projectRepository.findByProjectIdentifier(project.getProjectIdentifier());

            if(existingProject != null && (!existingProject.getProjectLeader().equals(userName))){
                throw new ProjectNotFoundException("Project Not found");
            } else if (existingProject == null){
                throw new ProjectNotFoundException("Project with ID  : " + project.getProjectIdentifier() + " cannot be updated" +
                        "cos its dose non t exist" );
            }
        }

        try {

            User user = userRepository.findByUsername(userName);

            project.setUser(user);
            project.setProjectLeader(user.getUsername());
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());


            if (project.getId() == null) {
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);
        } catch (Exception e) {
            throw new ProjectIdException("Project Name should be Unique Project :" + project.getProjectIdentifier().toUpperCase() + " Exist");
        }
    }

    public Project findProjectByIdentifier(String projectId, String userName) {
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());
        if (project == null) {
            throw new ProjectIdException("Project Name should be Unique Project :" + project + " Exist");
        }

        if(!project.getProjectLeader().equals(userName)){
            throw new ProjectNotFoundException("Project Not Found on your account");
        }
        return project;
    }

    public Iterable<Project> findAllProjects(String userName) {
        return projectRepository.findAllByProjectLeader(userName);
    }

    public void deleteProjectByIdentifier(String projectId, String userName) {
        projectRepository.delete(findProjectByIdentifier(projectId, userName));
//        Project project = projectRepository.findByProjectIdentifier(projectId);
//        if (project == null) {
//            throw new ProjectIdException("Project Name should be Unique Project :" + project + " Exist");
//        }
//        projectRepository.delete(project);
    }
}
