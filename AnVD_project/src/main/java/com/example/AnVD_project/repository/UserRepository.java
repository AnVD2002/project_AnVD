package com.example.AnVD_project.repository;

import com.example.AnVD_project.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select u from User u where u.email = :email")
    public Optional<User> FindByEmail(@Param("email") String email);
}
