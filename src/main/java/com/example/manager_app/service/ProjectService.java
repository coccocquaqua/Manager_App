package com.example.manager_app.service;

import com.example.manager_app.dto.ProjectByIdResponse;
import com.example.manager_app.dto.ProjectByUserRespone;
import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.model.*;
import com.example.manager_app.repository.ProjectRepository;
import com.example.manager_app.repository.UserRepository;
import com.example.manager_app.repository.User_ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final User_ProjectRepository user_projectRepository;
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UserService userService;

    private UserRepository userRepository;
    // Setter method for user injection
   @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public ProjectService(User_ProjectRepository user_projectRepository) {
        this.user_projectRepository = user_projectRepository;
    }


    public List<Project> getAll() {
        return projectRepository.findAll();
    }

    public Page<ProjectByUserRespone> getAllPage(Pageable pageable) {
        Page<Project> projects = projectRepository.findAll(pageable);
        List<User_Project> userProjects = user_projectRepository.findAll();
        List<ProjectByUserRespone> list = new ArrayList<>();
        for (Project item : projects) {
            ProjectByUserRespone projectByUserRespone = new ProjectByUserRespone();
            projectByUserRespone.setId(item.getId());
            projectByUserRespone.setName(item.getName());
            projectByUserRespone.setDescription(item.getDescription());
            for (User_Project item1 : userProjects) {
                if (item1.getProject().getId() == item.getId()) {
                    projectByUserRespone.setUserName(item1.getUsers().getUsername());
                    projectByUserRespone.setRole(item1.getRole());
                    projectByUserRespone.setStatus(item1.getStatus1());
                    break;
                }
            }
            list.add(projectByUserRespone);
        }
        return new PageImpl<>(list, pageable, projects.getTotalElements());
    }

    public List<ProjectByUserRespone> getProjectByUser(Integer userId) {
        List<ProjectByUserRespone> projectByUserResponeList = new ArrayList<>();
        List<User_Project> user_projects = user_projectRepository.findUser_ProjectByUsersId(userId);
        Optional<Users> usersOptional = userRepository.findById(userId);
        Users users = usersOptional.get();
        for (User_Project userProject : user_projects) {
            Optional<Project> projectOptional = projectRepository.findById(userProject.getProject().getId());
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                projectByUserResponeList.add(new ProjectByUserRespone(project.getId(), project.getName(), project.getDescription(), users.getUsername(), userProject.getRole(), userProject.getStatus1()));

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

    public ProjectByIdResponse getById(Integer projectId) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        ProjectByIdResponse projectByUserRespone = new ProjectByIdResponse();
        projectByUserRespone.setId(optionalProject.get().getId());
        projectByUserRespone.setName(optionalProject.get().getName());
        projectByUserRespone.setDescription(optionalProject.get().getDescription());
        List<UserProjectReponse> listUser = userService.getUserByProject(projectId);
        List<UserProjectReponse> list = new ArrayList<>();
        for (UserProjectReponse item1 : listUser) {
            if (item1.getStatus().equalsIgnoreCase("đang hoạt động")) {
                list.add(item1);

            }
            projectByUserRespone.setUsers(list);
        }
        List<User_Project> userProjectList = user_projectRepository.findUser_ProjectByProjectId(projectId);
        for (User_Project item : userProjectList) {
            projectByUserRespone.setRole(item.getRole());
        }
        return projectByUserRespone;
    }

    public List<ProjectByUserRespone> addUser(Project project, List<UserProjectReponse> userList, String role) {
        List<ProjectByUserRespone> responseList = new ArrayList<>();
        if (project.getId() == null) {
            projectRepository.save(project);
        }

        Optional<Project> projectOptional = projectRepository.findById(project.getId());
        if (projectOptional.isPresent()) {
            Project project1 = projectOptional.get();

            // Lấy danh sách người dùng hiện tại của dự án từ cơ sở dữ liệu
            List<User_Project> userProjectsInDB = user_projectRepository.findUser_ProjectByProjectId(project1.getId());

            List<User_Project> projectToAdd = new ArrayList<>();
            List<Integer> projectToDelete = new ArrayList<>();

            // Duyệt qua danh sách người dùng truyền vào từ client
            for (UserProjectReponse userFromClient : userList) {
                boolean userExistsInDB = false;

                // Kiểm tra xem người dùng có tồn tại trong cơ sở dữ liệu không
                for (User_Project userProjectInDB : userProjectsInDB) {
                    if (userProjectInDB.getUsers().getId().equals(userFromClient.getId())) {
                        userExistsInDB = true;
                        break;
                    }
                }

                // Nếu người dùng không tồn tại trong cơ sở dữ liệu, thêm vào danh sách để thêm mới
                if (!userExistsInDB) {
                    Optional<Users> userOptional = userRepository.findById(userFromClient.getId());
                    if (userOptional.isPresent()) {
                        Users user = userOptional.get();
                        User_Project newUserProject = new User_Project();
                        newUserProject.setProject(project1);
                        newUserProject.setUsers(user);
                        newUserProject.setRole(role);
                        newUserProject.setStatus(1);
                        projectToAdd.add(newUserProject);
                    }
                }
            }

            // Xóa các người dùng không được truyền vào từ client
            for (User_Project userProjectInDB : userProjectsInDB) {
                boolean userExistsInClientList = false;
                for (UserProjectReponse userFromClient : userList) {
                    if (userProjectInDB.getUsers().getId().equals(userFromClient.getId())) {
                        userExistsInClientList = true;
                        break;
                    }
                }

                // Nếu người dùng không tồn tại trong danh sách từ client, cập nhật status thành 0
                if (!userExistsInClientList) {
                    projectToDelete.add(userProjectInDB.getUsers().getId());
                } else {
                    projectToDelete.add(userProjectInDB.getUsers().getId());
                }
            }

            // Cập nhật status của người dùng
            for (Integer userProjectId : projectToDelete) {
                boolean userExistsInClientList = false;

                // Kiểm tra xem người dùng có tồn tại trong danh sách client không
                for (UserProjectReponse userFromClient : userList) {
                    if (userFromClient.getId().equals(userProjectId)) {
                        userExistsInClientList = true;
                        break;
                    }
                }

                // Nếu user không tồn tại trong danh sách client, cập nhật status thành 0
                if (!userExistsInClientList) {
                    user_projectRepository.updateStatusByUsersIdAndProjectId(0, userProjectId, project.getId());

                }

                // Ngược lại, cập nhật status thành 1
                else {
                    user_projectRepository.updateStatusByUsersIdAndProjectId(1, userProjectId, project.getId());
                    System.out.println(userProjectId);
                }
            }
            // Lưu các người dùng mới vào cơ sở dữ liệu
            user_projectRepository.saveAll(projectToAdd);

            // Cập nhật thông tin dự án
            project1.setName(project.getName());
            project1.setDescription(project.getDescription());
            projectRepository.save(project1);

            // Tìm vai trò của người dùng trong dự án và thêm vào response
            for (User_Project userProject : userProjectsInDB) {
                if (userProject.getStatus() == 1) {
                    // Tạo đối tượng response
                    ProjectByUserRespone projectByUserRespone = new ProjectByUserRespone();
                    projectByUserRespone.setId(project.getId());
                    projectByUserRespone.setName(project.getName());
                    projectByUserRespone.setDescription(project.getDescription());
                    if (userProject.getProject() != null && userProject.getProject().getId() != null && userProject.getProject().getId() == project.getId()) {
                        projectByUserRespone.setRole(userProject.getRole());
                        projectByUserRespone.setStatus(userProject.getStatus1());
                        projectByUserRespone.setUserName(userProject.getUsers().getUsername());
                    }
                    responseList.add(projectByUserRespone);
                }

            }

        }

        return responseList;
    }

}
