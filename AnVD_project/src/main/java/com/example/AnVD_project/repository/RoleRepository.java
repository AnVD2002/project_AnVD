package com.example.AnVD_project.repository;

import com.example.AnVD_project.entity.Role;
import com.example.AnVD_project.enums.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findRoleByRoleName(RoleEnum roleName);
}
