package com.example.manager_app.repository;

import com.example.manager_app.model.Retro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RetroRepository extends JpaRepository<Retro,Integer> {
List<Retro>findAllByEndDateAfter(LocalDate currentDate);
List<Retro>findRetroByProjectId(Integer projectId);
}
