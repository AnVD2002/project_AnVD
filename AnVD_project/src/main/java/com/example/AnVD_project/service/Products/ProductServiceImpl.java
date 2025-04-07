package com.example.AnVD_project.service.Products;

import com.example.AnVD_project.DTO.Product.CrudProductRequestDTO;
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
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    LineRepository lineRepository;

    GroupRepository groupRepository;

    CategoryRepository categoryRepository;

    ProductRepository productRepository;


    public void CrudProducts(List<CrudProductRequestDTO> request) {
        if (CollectionUtils.isEmpty(request)) {
            throw new BusinessException(ResponseEnum.INVALID_INPUT.getCode(), ResponseEnum.INVALID_INPUT.getMessage());
        }

        List<CrudProductRequestDTO> isCreate = request.stream()
                .filter(rq -> ObjectUtils.isEmpty(rq.getProductId()))
                .toList();

        List<CrudProductRequestDTO> isUpdate = request.stream()
                .filter(rq -> !ObjectUtils.isEmpty(rq.getProductId()))
                .toList();

        List<CrudProductRequestDTO> isDelete = request.stream()
                .filter(CrudProductRequestDTO::getIsDeleted)
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

    public void createProduct(List<CrudProductRequestDTO> request) {
        List<Products> newProducts = new ArrayList<>();

        List<Long> lineIds = request.stream().map(CrudProductRequestDTO::getLineId).toList();

        List<Long> groupIds = request.stream().map(CrudProductRequestDTO::getGroupId).toList();

        List<Long> categoryIds = request.stream().map(CrudProductRequestDTO::getCategoryId).toList();

        List<Long> productIds = request.stream().map(CrudProductRequestDTO::getProductId).toList();

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

        if (!CollectionUtils.isEmpty(productIds)) {
            products = productRepository.findByIdIn(productIds);
        }

        for (CrudProductRequestDTO rq : request) {

            Groups group = groups.stream()
                    .filter(g -> g.getId().equals(rq.getCategoryId()))
                    .findFirst()
                    .orElse(null);

            Products p = products.stream()
                    .filter(ps -> ps.getNmProduct().equals(rq.getProductName()) && !ObjectUtils.isEmpty(ps.getDeleteAt()))
                    .findFirst()
                    .orElse(null);

            if (!ObjectUtils.isEmpty(p)) {
                throw new BusinessException(ResponseEnum.PRODUCT_ALREADY_EXISTS.getCode(), ResponseEnum.PRODUCT_ALREADY_EXISTS.getMessage());
            }

            Products deleteSoft = products.stream()
                    .filter(ps -> ps.getNmProduct().equals(rq.getProductName()) && ObjectUtils.isEmpty(ps.getDeleteAt()))
                    .findFirst()
                    .orElse(null);

            if (!ObjectUtils.isEmpty(deleteSoft)) {
                createForProductDeletedSoft(deleteSoft, rq, group);
            }

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

    void createForProductDeletedSoft(Products product, CrudProductRequestDTO request, Groups group) {
        product.setDeleteAt(null);
        supUpdateProduct(request, product, group);
        productRepository.save(product);
    }

    public void updateProduct(List<CrudProductRequestDTO> request) {
        List<Long> productIds = request.stream().map(CrudProductRequestDTO::getProductId).toList();

        List<Products> products = new ArrayList<>();

        List<Long> groupIds = request.stream().map(CrudProductRequestDTO::getGroupId).toList();

        List<Groups> groups = new ArrayList<>();

        if (!CollectionUtils.isEmpty(groupIds)) {
            groups = groupRepository.findByIdIn(groupIds);
        }

        if (!CollectionUtils.isEmpty(productIds)) {
            products = productRepository.findByIdIn(productIds);
        }

        for (CrudProductRequestDTO rq : request) {

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

    private void supUpdateProduct(CrudProductRequestDTO rq, Products product, Groups group) {
        product.setNmProduct(rq.getProductName());
        product.setDescription(rq.getProductDescription());
        product.setImage(rq.getImageUrl());
        product.setCostPrice(rq.getCostPrice());
        product.setSellingPrice(rq.getSellingPrice());
        product.setGroup(group);

        String cdProduct = createCdProduct(rq.getLineCd(), rq.getCategoryCd(), rq.getGroupCd(), rq.getProductId());

        product.setCdProduct(cdProduct);
    }

    public void deleteProduct(List<CrudProductRequestDTO> request) {
        List<Long> productIds = request.stream().map(CrudProductRequestDTO::getProductId).toList();

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

}
