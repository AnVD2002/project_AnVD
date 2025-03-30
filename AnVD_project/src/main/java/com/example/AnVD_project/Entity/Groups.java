package com.example.AnVD_project.Entity;

import com.example.AnVD_project.common.EntityCommon;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Table(name = "`groups`")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Groups extends EntityCommon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nm_group")
    private String nmGroup;

    @Column(name = "cd_group")
    private String cdGroup;

    @Column(name = "image")
    private String image;

    @Column(name = "description")
    private String description;

    @Column(name = "category_id", insertable = false, updatable = false)
    private Long categoryId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "group")
    @JsonManagedReference("group-products")
    private List<Products> products;

    @ManyToOne
    @JsonBackReference("category-groups")
    @JoinColumn(name = "category_id")
    private Categories category;
}
