package com.example.AnVD_project.repository;

import com.example.AnVD_project.DTO.Response.Products.ProductsResponseDTO;
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
    @Query(value = "select p from Products p where p.nmProduct in :names")
    List<Products> findByProductNames(@Param("names") List<String> names);


    @Query(value =
            "select new com.example.AnVD_project.DTO.Response.Products.ProductsResponseDTO(" +
                    "l.cdLine,l.nmLine,c.cdCategories, c.nmCategories ,g.cdGroup, g.nmGroup ,p.cdProduct,p.id," +
                    "p.description,p.nmProduct,p.costPrice,p.sellingPrice,p.image ) " +
                    "from Groups g " +
                    "inner join Categories c on c.id = g.categoryId " +
                    "inner join Lines l on l.id = c.lineId " +
                    "inner join Products p on g.id = p.groupId " +
                    "where c.cdCategories = :cdCategory " +
                    "and g.cdGroup = :cdGroup " +
                    "and l.cdLine = :cdLine"
    )
    List<ProductsResponseDTO> findProduct(
            @Param("cdLine") String cdLine,
            @Param("cdGroup") String cdGroup,
            @Param("cdCategory") String cdCategory
    );
}
