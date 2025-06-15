package com.example.AnVD_project.entity;

import com.example.AnVD_project.common.EntityCommon;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Table(name = "categories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Categories extends EntityCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nm_categories")
    private String nmCategories;

    @Column(name = "cd_categories")
    private String cdCategories;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "line_id", insertable = false, updatable = false)
    private Long lineId;

    @ManyToOne
    @JsonBackReference("line-categories")
    @JoinColumn(name = "line_id")
    private Lines line;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
    @JsonManagedReference("line-groups")
    private List<Groups> groups;
}
