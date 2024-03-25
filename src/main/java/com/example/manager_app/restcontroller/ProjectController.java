package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.ProjectByUserRespone;
import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.model.Project;
import com.example.manager_app.repository.ProjectRepository;
import com.example.manager_app.security.JwtUtils;
import com.example.manager_app.security.UserDetailServiceImpl;
import com.example.manager_app.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<?> getAllPage() {
        List<Project> projects = projectRepository.findAll();
        return ResponseEntity.ok(projects);
    }
    @GetMapping("/filter-user/{userId}")
    public ResponseEntity<?> getUserByProject(@PathVariable Integer userId) {
        List<ProjectByUserRespone> projectByUser = projectService.getProjectByUser(userId);
        return ResponseEntity.ok(projectByUser);
    }
}
