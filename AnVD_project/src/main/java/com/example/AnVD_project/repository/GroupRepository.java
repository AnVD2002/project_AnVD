package com.example.AnVD_project.repository;

import com.example.AnVD_project.entity.Groups;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends CrudRepository<Groups, Long> {

    @Query(value = "select g from Groups g where g.id in :ids")
    List<Groups> findByIdIn(@Param("ids") List<Long> ids);
}
