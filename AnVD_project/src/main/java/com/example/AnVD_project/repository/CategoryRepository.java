package com.example.AnVD_project.repository;

import com.example.AnVD_project.Entity.Categories;
import com.example.AnVD_project.Entity.Lines;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends CrudRepository<Categories, Long> {
    @Query(value = "select c from Categories c where c.id in :ids")
    List<Categories> findByIdIn(@Param("ids") List<Long> ids);
}
