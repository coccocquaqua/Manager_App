package com.example.manager_app.controller;

import com.example.manager_app.model.Project;
import com.example.manager_app.restcontroller.ProjectController;
import com.example.manager_app.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ProjectControllTest {

    @InjectMocks
    ProjectController projectController;

    @Mock
    ProjectService projectService;

    private MockMvc mockMvc; // tạo môi trường giả lập thực hiện cá request HTTP đến API của ưng dụng

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
    }

    @Test
    public void testGetProject() throws Exception {
        // Giả lập dữ liệu trả về từ projectService.getAll()
        List<Project> mockProjects = new ArrayList<>();
        Project project = new Project();
        project.setId(1);
        project.setName("Test Project");
        mockProjects.add(project);

        when(projectService.getAll()).thenReturn(mockProjects);
        mockMvc.perform(get("/api/Project/admin/getall")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());

    }
}
