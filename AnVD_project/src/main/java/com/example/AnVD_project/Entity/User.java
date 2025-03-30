package com.example.AnVD_project.Entity;

import com.example.AnVD_project.common.EntityCommon;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Table(name = "user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User extends EntityCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "number_phone")
    private String numberPhone;
    @Column(name = "role_id", insertable = false, updatable = false)
    private Long roleId;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "role_id")
    private Role role;
}
