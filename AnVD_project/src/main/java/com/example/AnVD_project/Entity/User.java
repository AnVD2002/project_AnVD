package com.example.AnVD_project.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private String id;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "number_phone")
    private String numberPhone;
    @Column(name = "role_id")
    private Long roleId;
    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "role_id")
    private Role role;
}
