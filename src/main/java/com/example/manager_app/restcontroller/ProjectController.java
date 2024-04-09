package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.*;
import com.example.manager_app.model.Project;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.ProjectRepository;
import com.example.manager_app.security.JwtUtils;
import com.example.manager_app.security.UserDetailServiceImpl;
import com.example.manager_app.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/Project")
public class ProjectController {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private ProjectService projectService;

   // @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllPage(@RequestParam(defaultValue = "1") int page) {
        if (page < 1) page = 1;
        int pageNumber = page - 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<ProjectByUserRespone> projects = projectService.getAllPage(pageable);
        return ResponseEntity.ok(projects);
    }

    @GetMapping("getall")
    public ResponseEntity<?> getAllPage() {
        List<Project> projects = projectService.getAll();
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getById(@PathVariable Integer projectId) {
        ProjectByIdResponse project = projectService.getById(projectId);
        return ResponseEntity.ok(project);
    }
//user
    @GetMapping("/filter-user/{userId}")
    public ResponseEntity<?> getUserByProject(@PathVariable Integer userId) {
        List<ProjectByUserRespone> projectByUser = projectService.getProjectByUser(userId);
        return ResponseEntity.ok(projectByUser);
    }

    @PostMapping
    public ResponseEntity<?> post(@RequestBody AddProjectRequest projectRequest) {
        Project project = projectRequest.getProject();
        List<UserProjectReponse> usersList = projectRequest.getUsers();
        List<ProjectByUserRespone> projectByUserRespone = projectService.addUser(project, usersList, projectRequest.getRole());
        return ResponseEntity.ok(projectByUserRespone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsers(@PathVariable Integer id) {
        projectService.deteleProject(id);
        return ResponseEntity.ok().build();
    }
}
