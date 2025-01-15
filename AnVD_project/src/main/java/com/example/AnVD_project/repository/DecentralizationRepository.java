package com.example.AnVD_project.repository;

import com.example.AnVD_project.entity.Decentralization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DecentralizationRepository extends JpaRepository<Decentralization, Integer> {
}
