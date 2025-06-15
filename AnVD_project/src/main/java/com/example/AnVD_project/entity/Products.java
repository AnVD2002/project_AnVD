package com.example.AnVD_project.entity;

import com.example.AnVD_project.common.EntityCommon;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Table(name = "products")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Products extends EntityCommon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nm_product")
    private String nmProduct;

    @Column(name = "cd_product")
    private String cdProduct;

    @Column(name = "image")
    private String image;

    @Column(name = "cost_price")
    private Double costPrice;

    @Column(name = "selling_price")
    private Double sellingPrice;

    @Column(name = "description")
    private String description;

    @Column(name = "group_id", insertable = false, updatable = false)
    private Long groupId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonBackReference("group-products")
    private Groups group;

}
