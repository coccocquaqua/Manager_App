package com.example.manager_app.service;

import com.example.manager_app.dto.ProjectByUserRespone;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.User_Project;
import com.example.manager_app.repository.ProjectRepository;
import com.example.manager_app.repository.User_ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    User_ProjectRepository user_projectRepository;
    @Autowired
    ProjectRepository projectRepository;
    public List<ProjectByUserRespone> getProjectByUser(Integer userId) {
        List<ProjectByUserRespone> projectByUserResponeList = new ArrayList<>();
        List<User_Project> user_projects = user_projectRepository.findUser_ProjectByUsersId(userId);
        for (User_Project userProject:user_projects) {
            Optional<Project> projectOptional=projectRepository.findById(userProject.getProject().getId());
            if(projectOptional.isPresent()){
                Project project=projectOptional.get();
                projectByUserResponeList.add(new ProjectByUserRespone(project.getId(),project.getName()));
            }
        }
        return projectByUserResponeList;
    }
}
