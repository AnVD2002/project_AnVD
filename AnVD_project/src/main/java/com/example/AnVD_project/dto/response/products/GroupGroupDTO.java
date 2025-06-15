package com.example.AnVD_project.dto.response.products;

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
