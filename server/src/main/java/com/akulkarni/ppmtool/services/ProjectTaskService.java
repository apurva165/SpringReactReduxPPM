package com.akulkarni.ppmtool.services;

import com.akulkarni.ppmtool.domain.Backlog;
import com.akulkarni.ppmtool.domain.Project;
import com.akulkarni.ppmtool.domain.ProjectTask;
import com.akulkarni.ppmtool.repositories.BacklogRepository;
import com.akulkarni.ppmtool.repositories.ProjectRepository;
import com.akulkarni.ppmtool.repositories.ProjectTaskRepository;
import exceptions.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProjectTaskService {

    @Autowired
    private BacklogRepository backlogRepository;

    @Autowired
    ProjectService projectService;

    @Autowired
    private ProjectTaskRepository projectTaskRepository;

    @Autowired
    ProjectRepository projectRepository;


    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String userName){

            //PTs to be added to a specific project, project != null, BL exists
           // Backlog backlog = backlogRepository.findByProjectIdentifier(projectIdentifier);
            Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, userName).getBacklog();
            //set the bl to pt
            projectTask.setBacklog(backlog);
            //we want our project sequence to be like this: IDPRO-1  IDPRO-2  ...100 101
            Integer BacklogSequence = backlog.getPTSequence();
            // Update the BL SEQUENCE
            BacklogSequence++;

            backlog.setPTSequence(BacklogSequence);

            //Add Sequence to Project Task
            projectTask.setProjectSequence(backlog.getProjectIdentifier()+"-"+BacklogSequence);
            projectTask.setProjectIdentifier(projectIdentifier);

            //INITIAL priority when priority null

            //INITIAL status when status is null
            if(projectTask.getStatus()==""|| projectTask.getStatus()==null){
                projectTask.setStatus("TO_DO");
            }

            if(projectTask.getPriority()==null||projectTask.getPriority()==0){ //In the future we need projectTask.getPriority()== 0 to handle the form
                projectTask.setPriority(3);
            }

            return projectTaskRepository.save(projectTask);
    }

    public Iterable<ProjectTask>findBackLogById(String id, String username){

        projectService.findProjectByIdentifier(id, username);
//        Project project = projectRepository.findByProjectIdentifier(id);
//
//        if(project == null){
//            throw new  ProjectNotFoundException("Project ID with " + id + "does not Exist");
//        }
        return projectTaskRepository.findByProjectIdentifierOrderByPriority(id);
    }



    public ProjectTask findPTByProjectSequence(String backlog_id, String pt_id, String username){
        projectService.findProjectByIdentifier(backlog_id, username);

        ProjectTask projectTask = projectTaskRepository.findProjectByProjectSequence(pt_id);
        if (projectTask == null){
            throw new ProjectNotFoundException("project ID with project : " + pt_id  + " Not found");
        }
        if(!projectTask.getProjectIdentifier().equals(backlog_id)){
            throw new ProjectNotFoundException("task " + pt_id + " does not exist in project : " + backlog_id);
        }
        return projectTask;
    }

    public ProjectTask updateByProjectSequence(ProjectTask updatedTask, String backlog_id, String pt_id, String userName){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, userName);
        projectTask = updatedTask;
        return projectTaskRepository.save(projectTask);
    }

    public void deletePTByProjectSequence(String backlog_id, String pt_id, String userName){
        ProjectTask projectTask = findPTByProjectSequence(backlog_id, pt_id, userName);

//        Backlog backlog = projectTask.getBacklog();
//        List<ProjectTask> pts = projectTask.getBacklog().getProjectTasks();
//        pts.remove(projectTask);
//        backlogRepository.save(backlog);
        projectTaskRepository.delete(projectTask);
    }
}
