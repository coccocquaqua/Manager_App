package com.example.manager_app.service;

import com.example.manager_app.model.Project;
import com.example.manager_app.repository.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.Mockito.when;
@SpringBootTest
public class ProjectServiceTest1 {
    @Autowired
    ProjectService projectService;

    @MockBean
    ProjectRepository projectRepository;

    @Test
    public void testGetProjectById() {
        Project project = new Project();
        project.setId(1);
        project.setName("Test Project");

        when(projectRepository.findAll()).thenReturn(List.of(project));

        List<Project> found = projectService.getAll();
        System.out.println(found);
    }
}
