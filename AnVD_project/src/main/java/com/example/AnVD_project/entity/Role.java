package com.example.AnVD_project.Entity;

import com.example.AnVD_project.common.EntityCommon;
import com.example.AnVD_project.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@Table(name = "role")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Role extends EntityCommon {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "role")
    private List<User> user;
}
