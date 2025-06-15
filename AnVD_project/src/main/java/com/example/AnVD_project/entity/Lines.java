package com.example.AnVD_project.entity;

import com.example.AnVD_project.common.EntityCommon;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Table(name = "`lines`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Lines extends EntityCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nm_line")
    private String nmLine;

    @Column(name = "cd_line")
    private String cdLine;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "line")
    @JsonManagedReference("line-categories")
    private List<Categories> categories;
}
