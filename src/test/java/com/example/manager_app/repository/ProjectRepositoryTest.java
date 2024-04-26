package com.example.manager_app.repository;

import com.example.manager_app.model.Project;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@SpringBootTest(classes = {com.example.manager_app.ManagerAppApplication.class})
public class ProjectRepositoryTest {
    @Autowired
    ProjectRepository projectRepository;
    @Test
    @BeforeEach // chạy trước mỗi test
    public void testCreateRetro() {
        Project project = Project.builder()
                .name("Retro 1")
                .description("description")
                .build();
        projectRepository.save(project);
    }
    @Test
    public void getall(){
       // testCreateRetro();
        System.out.println(projectRepository.findAll());
    }
}
