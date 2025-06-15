package com.example.AnVD_project.repository;

import com.example.AnVD_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    @Query(value = "select u from User u where u.email = :email")
    Optional<User> findByEmail(@Param("email") String email);
    @Query(value = "select u from User u where u.email =:email and u.password =:password ")
    Optional<User> findByUserNamePassword(String email, String password);

    @Query("select new com.example.AnVD_project.dto.response.user.UserResponseDTO(u.id, u.name, u.password, u.numberPhone, u.email) " +
            "from User u")
    List<User> findAllUsers();
}
