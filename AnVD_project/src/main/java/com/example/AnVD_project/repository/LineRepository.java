package com.example.AnVD_project.repository;

import com.example.AnVD_project.Entity.Lines;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LineRepository extends CrudRepository<Lines, Long> {
    @Query(value = "select l from Lines l where l.id in :ids")
    List<Lines> findByIdIn(@Param("ids") List<Long> ids);
}
