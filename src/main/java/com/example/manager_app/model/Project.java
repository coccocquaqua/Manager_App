package com.example.manager_app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
//    @OneToMany(fetch = FetchType.EAGER, mappedBy = "project", cascade = {CascadeType.PERSIST,CascadeType.ALL})
//    @JsonIgnore
//    private List<Retro> retro = new ArrayList<>();

}
