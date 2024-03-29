package com.example.manager_app.service;

import com.example.manager_app.dto.ProjectByUserRespone;
import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.Role;
import com.example.manager_app.model.User_Project;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.ProjectRepository;
import com.example.manager_app.repository.UserRepository;
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
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;

    public List<ProjectByUserRespone> getProjectByUser(Integer userId) {
        List<ProjectByUserRespone> projectByUserResponeList = new ArrayList<>();
        List<User_Project> user_projects = user_projectRepository.findUser_ProjectByUsersId(userId);
        for (User_Project userProject : user_projects) {
            Optional<Project> projectOptional = projectRepository.findById(userProject.getProject().getId());
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                projectByUserResponeList.add(new ProjectByUserRespone(project.getId(), project.getName(), userProject.getRole()));
            }
        }
        return projectByUserResponeList;
    }

    public void deteleProject(Integer project) {
        List<User_Project> userProjectList = user_projectRepository.findUser_ProjectByProjectId(project);
        for (User_Project item : userProjectList) {
            user_projectRepository.deleteById(item.getId());

        }
        projectRepository.deleteById(project);
    }

    public ProjectByUserRespone addUser(Project project, List<UserProjectReponse> userList, String role) {
        if (project.getId() == null) {
            projectRepository.save(project);
        }
        Optional<Project> projectOptional = projectRepository.findById(project.getId());
        List<User_Project> user_projects = null;
        if (projectOptional.isPresent()) {
            Project project1 = projectOptional.get();
            List<UserProjectReponse> userByProject = userService.getUserByProject(project1.getId());
            System.out.println(userByProject + "hhh");
            List<User_Project> projectToAdd = new ArrayList<>();
            List<Integer> projectToDelete = new ArrayList<>();
            //List<Project> projects = projectRepository.findAll();
            user_projects = user_projectRepository.findAll();
            // lọc ra các user cần thêm
            for (UserProjectReponse item : userList) {
                for (UserProjectReponse item1 : userByProject) {
                    if (item.getId() == item1.getId()) {
                        continue;
                    }

                }
                Optional<Users> usersOptional = userRepository.findById(item.getId());
                if (projectOptional.isPresent()) {
                    User_Project userProject = new User_Project();
                    userProject.setProject(project1);
                    userProject.setUsers(usersOptional.get());
                    userProject.setRole(role);
                    projectToAdd.add(userProject);
                }
            }
            for (UserProjectReponse item : userByProject) {
                System.out.println(item);
                projectToDelete.add(item.getId());
            }
            for (Integer item : projectToDelete) {
                System.out.println(project1.getId());
                System.out.println(item);
                user_projectRepository.deleteUser_ProjectByUsersIdAndProjectId(item, project.getId());
            }
            project1.setName(project.getName());
            project1.setDescription(project.getDescription());
            projectRepository.save(project1);
            user_projectRepository.saveAll(projectToAdd);

        }

        ProjectByUserRespone projectByUserRespone = new ProjectByUserRespone();
        projectByUserRespone.setId(project.getId());
        projectByUserRespone.setName(project.getName());
        projectByUserRespone.setDescription(project.getDescription());
        for (User_Project item1 : user_projects) {
            if (item1.getProject() != null && item1.getProject().getId() != null && item1.getProject().getId() == project.getId()) {
                projectByUserRespone.setRole(item1.getRole());
                break;
            }
        }
        return projectByUserRespone;
    }
}
