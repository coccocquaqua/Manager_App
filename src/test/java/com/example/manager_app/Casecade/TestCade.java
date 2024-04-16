package com.example.manager_app.Casecade;

import com.example.manager_app.model.Project;
import com.example.manager_app.model.Retro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.junit.Assert;

@SpringBootTest
@Transactional
public class TestCade {
    @Autowired
    EntityManager entityManager;

    @Test
    @Transactional
    @DirtiesContext
    public void run() {
        Project project = new Project();
        project.setName("project55");
        entityManager.persist(project);
        Retro retro = new Retro();
        retro.setName("retro99");
        retro.setProject(project);
        entityManager.persist(retro); //chèn một đối tượng mới vào cơ sở dữ liệu và bắt đầu quản lý đối tượng đó bởi EntityManager
        // In thông tin Retro
        System.out.println("sau khi persist" + retro);
        entityManager.detach(project); //tách đối tuwowgnj ra khỏi quản lí của manager và không theo dõi thay đổi nữa
        System.out.println("sau khi detach" + project);

        // Kiểm tra trạng thái của project trước khi thay đổi
        if (entityManager.contains(project)) {
            // Nếu project đang được quản lý bởi EntityManager, thực hiện thay đổi
            project.setName("project non");
        } else {
            // Nếu project đã được detach, thông báo và không thực hiện thay đổi
            System.out.println("Không thể thực hiện thay đổi trên project vì nó đã bị detach.");
        }

        // Thực hiện thay đổi trên project (ở đây là thay đổi tên)
        project.setName("Updated Project A");

        // Sử dụng merge để đồng bộ thay đổi của project với cơ sở dữ liệu
        Project mergedProject = entityManager.merge(project);

        // In thông tin của project sau khi merge
        System.out.println("Thông tin Project sau khi merge: " + mergedProject);
        // In lại thông tin Retro sau khi thay đổi
        System.out.println("Thông tin Retro sau khi thay đổi projectId:" + retro);

        // Xóa đối tượng Retro khỏi cơ sở dữ liệu
        entityManager.remove(retro);
        entityManager.flush();
        // In thông tin của Retro sau khi xóa
        System.out.println("Thông tin Retro sau khi xóa: " + retro);
    }
    @Test
    @Transactional
    @DirtiesContext
    public void remove(){
        Project project = new Project();
        project.setName("project65");
        entityManager.persist(project);
        Retro retro = new Retro();
        retro.setName("retro99");
        retro.setProject(project);
        entityManager.persist(retro);
        entityManager.flush();//chèn một đối tượng mới vào cơ sở dữ liệu và bắt đầu quản lý đối tượng đó bởi EntityManager
        System.out.println("sau khi persist" + project);
        entityManager.remove(retro);
        entityManager.flush();
        // In thông tin của Retro sau khi xóa
        System.out.println("Thông tin Retro sau khi xóa: " + project);
        Assert.assertNull(entityManager.find(Retro.class,retro.getId()));
    }
}
