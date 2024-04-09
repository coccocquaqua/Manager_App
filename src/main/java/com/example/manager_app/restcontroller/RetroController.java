package com.example.manager_app.restcontroller;

import com.example.manager_app.dto.UserProjectReponse;
import com.example.manager_app.model.Retro;
import com.example.manager_app.model.Users;
import com.example.manager_app.repository.RetroRepository;
import com.example.manager_app.service.RetroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/Retro")
public class RetroController {
    @Autowired
    RetroService retroService;
    @Autowired
    RetroRepository retroRepository;

    @GetMapping()
    public ResponseEntity<?> getAll(@RequestParam(defaultValue = "1") int page) {
        if (page < 1) page = 1;
        int pageNumber = page - 1;
        int pageSize = 1;
        Pageable pageable = PageRequest.of(pageNumber,pageSize);
        Page<Retro> list=retroService.getAll(pageable);
        return ResponseEntity.ok(list);
    }
    @GetMapping("/retro/{retroId}")
    public ResponseEntity<?> getById(@PathVariable Integer retroId) {
        Optional<Retro> retro = retroRepository.findById(retroId);
        return ResponseEntity.ok(retro);
    }
    @GetMapping("/end-date")
    public ResponseEntity<?> getInProgressReviews() {
        List<Retro>list=retroService.getRetroByEndDate();
        return ResponseEntity.ok(list);
    }
    //user
    @GetMapping("/project/{projectId}")
    public ResponseEntity<?> getRetroByProjectAndDate(@PathVariable Integer projectId ) {
        List<Retro>list=retroService.getRetroByProjectIdAndDate(projectId);
        return ResponseEntity.ok(list);
    }
    @PostMapping
    public ResponseEntity<Retro>post(@RequestBody Retro retro){
        return ResponseEntity.ok(retroService.addRetro(retro));
    }
    @PutMapping
    public ResponseEntity<Retro>put(@RequestBody Retro retro){
        return ResponseEntity.ok(retroService.updateRetro(retro));
    }
}
