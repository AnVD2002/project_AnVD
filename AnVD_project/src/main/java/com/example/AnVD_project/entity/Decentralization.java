package com.example.AnVD_project.entity;

import com.example.AnVD_project.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Table(name = "decentralization")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Decentralization {
    @Id
    @Column(name = "id")
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name= "role_name")
    @Enumerated(EnumType.STRING)
    private RoleEnum roleName;
    @JsonManagedReference
    @OneToMany(fetch = FetchType.LAZY,mappedBy = "decentralization" )
    private List<User> user;
}
