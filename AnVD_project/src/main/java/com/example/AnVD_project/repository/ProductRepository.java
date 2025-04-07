package com.example.AnVD_project.repository;

import com.example.AnVD_project.Entity.Lines;
import com.example.AnVD_project.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {
    @Query(value = "select p from Products p where p.id in :ids")
    List<Products> findByIdIn(@Param("ids") List<Long> ids);
}
