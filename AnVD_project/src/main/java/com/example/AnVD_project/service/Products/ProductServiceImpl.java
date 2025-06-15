package com.example.AnVD_project.service.Products;

import com.example.AnVD_project.DTO.Request.Product.ProductRequestDTO;
import com.example.AnVD_project.DTO.Response.Products.*;
import com.example.AnVD_project.Entity.Categories;
import com.example.AnVD_project.Entity.Groups;
import com.example.AnVD_project.Entity.Lines;
import com.example.AnVD_project.Entity.Products;
import com.example.AnVD_project.enums.ResponseEnum;
import com.example.AnVD_project.exception.BusinessException;
import com.example.AnVD_project.repository.CategoryRepository;
import com.example.AnVD_project.repository.GroupRepository;
import com.example.AnVD_project.repository.LineRepository;
import com.example.AnVD_project.repository.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final LineRepository lineRepository;

    private final GroupRepository groupRepository;

    private final CategoryRepository categoryRepository;

    private final ProductRepository productRepository;

    @Transactional
    public void saveProducts(List<ProductRequestDTO> request) {
        if (CollectionUtils.isEmpty(request)) {
            throw new BusinessException(ResponseEnum.INVALID_INPUT.getCode(), ResponseEnum.INVALID_INPUT.getMessage());
        }

        List<ProductRequestDTO> isCreate = request.stream()
                .filter(rq -> ObjectUtils.isEmpty(rq.getProductId()))
                .toList();

        List<ProductRequestDTO> isUpdate = request.stream()
                .filter(rq -> !ObjectUtils.isEmpty(rq.getProductId()) && !rq.getIsDeleted())
                .toList();

        List<ProductRequestDTO> isDelete = request.stream()
                .filter(ProductRequestDTO::getIsDeleted)
                .toList();

        if (!CollectionUtils.isEmpty(isCreate)) {
            createProduct(isCreate);
        }
        if (!CollectionUtils.isEmpty(isUpdate)) {
            updateProduct(isUpdate);
        }
        if (!CollectionUtils.isEmpty(isDelete)) {
            deleteProduct(isDelete);
        }
    }

    public void createProduct(List<ProductRequestDTO> request) {

    try {
        List<Products> newProducts = new ArrayList<>();

        List<Long> lineIds = request.stream().map(ProductRequestDTO::getLineId).toList();

        List<Long> groupIds = request.stream().map(ProductRequestDTO::getGroupId).toList();

        List<Long> categoryIds = request.stream().map(ProductRequestDTO::getCategoryId).toList();

        List<String> productNames = request.stream().map(ProductRequestDTO::getProductName).toList();

        List<Lines> lines = new ArrayList<>();

        List<Groups> groups = new ArrayList<>();

        List<Products> products = new ArrayList<>();

        List<Categories> categories = new ArrayList<>();

        if (!CollectionUtils.isEmpty(lineIds)) {
            lines = lineRepository.findByIdIn(lineIds);
        }

        if (!CollectionUtils.isEmpty(groupIds)) {
            groups = groupRepository.findByIdIn(groupIds);
        }

        if (!CollectionUtils.isEmpty(categoryIds)) {
            categories = categoryRepository.findByIdIn(categoryIds);
        }

        if (!CollectionUtils.isEmpty(productNames)) {
            products = productRepository.findByProductNames(productNames);
        }

        for (ProductRequestDTO rq : request) {

            Groups group = groups.stream()
                    .filter(g -> g.getId().equals(rq.getCategoryId()))
                    .findFirst()
                    .orElse(null);

            Products p = products.stream()
                    .filter(ps -> ps.getNmProduct().equals(rq.getProductName()) && ObjectUtils.isEmpty(ps.getDeleteAt()))
                    .findFirst()
                    .orElse(null);

            if (!ObjectUtils.isEmpty(p)) {
                throw new BusinessException(ResponseEnum.PRODUCT_ALREADY_EXISTS.getCode(), ResponseEnum.PRODUCT_ALREADY_EXISTS.getMessage());
            }

            Products deleteSoft = products.stream()
                    .filter(ps -> ps.getNmProduct().equals(rq.getProductName()) && !ObjectUtils.isEmpty(ps.getDeleteAt()))
                    .findFirst()
                    .orElse(null);

            if (!ObjectUtils.isEmpty(deleteSoft)) {
                createForProductDeletedSoft(deleteSoft, rq, group);
                newProducts.add(deleteSoft);
            }
            else {
                Products newProduct = Products.builder()
                        .nmProduct(rq.getProductName())
                        .costPrice(rq.getCostPrice())
                        .sellingPrice(rq.getSellingPrice())
                        .description(rq.getProductDescription())
                        .createAt(Instant.now())
                        .image(rq.getImageUrl())
                        .group(group)
                        .build();

                newProducts.add(newProduct);
            }
        }
        productRepository.saveAll(newProducts);


        for (Products p : newProducts) {
            Categories category = categories.stream()
                    .filter(c -> c.getId().equals(p.getGroup().getCategoryId()))
                    .findFirst()
                    .orElse(null);

            Lines line = new Lines();

            if (!ObjectUtils.isEmpty(category)) {
                line = lines.stream()
                        .filter(l -> l.getId().equals(category.getLineId()))
                        .findFirst()
                        .orElse(null);
            }

            if (ObjectUtils.isEmpty(line) || ObjectUtils.isEmpty(category) || ObjectUtils.isEmpty(p.getGroup())) {
                throw new BusinessException(ResponseEnum.INVALID_INPUT.getCode(), ResponseEnum.INVALID_INPUT.getMessage());
            }

            String cdProduct = createCdProduct(line.getCdLine(), category.getCdCategories(), p.getGroup().getCdGroup(), p.getId());

            p.setCdProduct(cdProduct);

        }

        productRepository.saveAll(products);
    }
    catch (Exception e) {
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi xử lý sản phẩm", e);
    }


    }

    void createForProductDeletedSoft(Products product, ProductRequestDTO request, Groups group) {
        product.setDeleteAt(null);
        supUpdateProduct(request, product, group);
    }

    public void updateProduct(List<ProductRequestDTO> request) {
        List<Long> productIds = request.stream().map(ProductRequestDTO::getProductId).toList();

        List<Products> products = new ArrayList<>();

        List<Long> groupIds = request.stream().map(ProductRequestDTO::getGroupId).toList();

        List<Groups> groups = new ArrayList<>();

        if (!CollectionUtils.isEmpty(groupIds)) {
            groups = groupRepository.findByIdIn(groupIds);
        }

        if (!CollectionUtils.isEmpty(productIds)) {
            products = productRepository.findByIdIn(productIds);
        }

        for (ProductRequestDTO rq : request) {

            Products product = products.stream().filter(p -> p.getId().equals(rq.getProductId()) && ObjectUtils.isEmpty(p.getDeleteAt())).findFirst().orElse(null);

            Groups group = groups.stream()
                    .filter(g -> g.getId().equals(rq.getGroupId()))
                    .findFirst()
                    .orElse(null);

            if (!ObjectUtils.isEmpty(product)) {
                supUpdateProduct(rq, product, group);
            }

        }
    }

    private void supUpdateProduct(ProductRequestDTO rq, Products product, Groups group) {
        product.setNmProduct(rq.getProductName());
        product.setDescription(rq.getProductDescription());
        product.setImage(rq.getImageUrl());
        product.setCostPrice(rq.getCostPrice());
        product.setSellingPrice(rq.getSellingPrice());
        product.setGroup(group);

        String cdProduct = createCdProduct(rq.getLineCd(), rq.getCategoryCd(), rq.getGroupCd(), product.getId());

        product.setCdProduct(cdProduct);
    }

    public void deleteProduct(List<ProductRequestDTO> request) {
        List<Long> productIds = request.stream().map(ProductRequestDTO::getProductId).toList();

        List<Products> products = new ArrayList<>();

        if (!CollectionUtils.isEmpty(productIds)) {
            products = productRepository.findByIdIn(productIds);
        }

        for (Products p : products) {
            p.setDeleteAt(Instant.now());
        }

        productRepository.saveAll(products);

    }

    public String createCdProduct(String cdLine, String cdCategory, String cdGroup, Long productId) {
        if (productId == null) {
            throw new BusinessException(ResponseEnum.INVALID_INPUT.getCode(), ResponseEnum.INVALID_INPUT.getMessage());
        }
        return cdLine + cdGroup + cdCategory + productId;
    }


    public List<ProductsResponse> getDataProducts(String cdLine , String cdCategory, String cdGroup) {
        List<ProductsResponseDTO> responsesDTO = productRepository.findProduct(cdLine, cdGroup, cdCategory);

        Map<String, ProductsResponse> lineMap = new LinkedHashMap<>();

        if (CollectionUtils.isEmpty(responsesDTO)) {
            throw new BusinessException(ResponseEnum.PRODUCT_NOT_FOUND.getCode(), ResponseEnum.PRODUCT_NOT_FOUND.getMessage());
        }

        for (ProductsResponseDTO dto : responsesDTO) {

            ProductsResponse line = lineMap.computeIfAbsent(dto.getCdLine(), cl -> {
                ProductsResponse res = new ProductsResponse();
                res.setCdLine(cdLine);
                res.setNmLine(dto.getNmLine());
                res.setGroupByCategory(new ArrayList<>());
                return res;
            });

            CategoryGroupDTO category = line.getGroupByCategory().stream()
                    .filter(cat -> cat.getCdCategory().equals(dto.getCdCategory()))
                    .findFirst()
                    .orElseGet(() -> {
                        CategoryGroupDTO newCat = new CategoryGroupDTO();
                        newCat.setCdCategory(dto.getCdCategory());
                        newCat.setNmCategory(dto.getNmCategory());
                        newCat.setGroupGroupDTO(new ArrayList<>());
                        line.getGroupByCategory().add(newCat);
                        return newCat;
                    });

            GroupGroupDTO group = category.getGroupGroupDTO().stream()
                    .filter(gr -> gr.getCdGroup().equals(dto.getCdGroup()))
                    .findFirst()
                    .orElseGet(() -> {
                        GroupGroupDTO newGroup = new GroupGroupDTO();
                        newGroup.setCdGroup(dto.getCdGroup());
                        newGroup.setNmGroup(dto.getNmGroup());
                        newGroup.setProductGroupDTO(new ArrayList<>());
                        category.getGroupGroupDTO().add(newGroup);
                        return newGroup;
                    });

            ProductGroupDTO product = new ProductGroupDTO();
            product.setCdProduct(dto.getCdProduct());
            product.setProductId(dto.getProductId());
            product.setDescription(dto.getDescription());
            product.setNmProduct(dto.getNmProduct());
            product.setCostPrice(dto.getCostPrice());
            product.setSellingPrice(dto.getSellingPrice());
            product.setImage(dto.getImage());

            group.getProductGroupDTO().add(product);
        }

        return new ArrayList<>(lineMap.values());

    }

}
