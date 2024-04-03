package com.example.manager_app.service;

import com.example.manager_app.model.Project;
import com.example.manager_app.model.Retro;
import com.example.manager_app.repository.ProjectRepository;
import com.example.manager_app.repository.RetroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RetroService {
    @Autowired
    RetroRepository retroRepository;
    @Autowired
    ProjectRepository projectRepository;

    public List<Retro> getRetroByEndDate() {
        LocalDate currentDate = LocalDate.now();
        List<Retro> allRetro = retroRepository.findAll();
        //Lọc các đợt review còn hạn
        List<Retro> list = allRetro.stream()
                .filter(retro -> retro.getEndDate().isAfter(currentDate))
                .collect(Collectors.toList());
        return list;
    }
   public List<Retro> getRetroByProjectIdAndDate(Integer projectId) {
       LocalDate currentDate = LocalDate.now();
        List<Retro>retroList=getRetroByEndDate();
        List<Retro>list=new ArrayList<>();
       for (Retro retro : retroList) {
           if (retro.getProject().getId().equals(projectId) && retro.getEndDate().isAfter(currentDate)) {
               list.add(retro);
           }
       }

       return list;
   }
}
