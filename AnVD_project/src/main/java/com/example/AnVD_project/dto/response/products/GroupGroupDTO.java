package com.example.AnVD_project.DTO.Response.Products;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GroupGroupDTO {
   private String nmGroup;
   private String cdGroup;
   private List<ProductGroupDTO> productGroupDTO;
}
