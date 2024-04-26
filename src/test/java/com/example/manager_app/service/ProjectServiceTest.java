package com.example.manager_app.service;

import com.example.manager_app.model.Project;
import com.example.manager_app.repository.ProjectRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {
    @InjectMocks // khởi tạo đối tượng cho ProjectService(đối tợng muốn kiểm thử) và tự động inject các trường đánh dấu là @Mock vào đối tượng đó
    ProjectService projectService;

    @Mock // tạo ra đối tượng giả cho đối tượng thực của hệ thống
    ProjectRepository projectRepository;

    @BeforeEach // Được chạy trước mỗi test method
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetProjectById() {
        Project project = new Project();
        project.setId(1);
        project.setName("Test Project");

        when(projectRepository.findAll()).thenReturn(List.of(project)); // giả lập dữ liệu trả về từ repository
        //thenReturn là phương thức của Mockito, nó giúp chúng ta giả lập việc trả về giá trị từ một phương thức nào đó
        List<Project> found = projectService.getAll();
        System.out.println(found);
    }
}
