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

import javax.sound.sampled.Line;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

        List<Lines> lines = lineRepository.findByIdIn(lineIds);

        List<Products> products = productRepository.findByIdIn(productIds);

        List<Groups> groups = groupRepository.findByIdIn(groupIds);

        List<Categories> categories = categoryRepository.findByIdIn(categoryIds);


        for (CrudProductRequestDTO rq : request) {

            Products p = products.stream()
                    .filter(ps->ps.getNmProduct().equals(rq.getProductName()))
                    .findFirst()
                    .orElse(null);

            if (!ObjectUtils.isEmpty(p)) {
                throw new BusinessException(ResponseEnum.PRODUCT_ALREADY_EXISTS.getCode(), ResponseEnum.PRODUCT_ALREADY_EXISTS.getMessage());
            }

            Groups group = groups.stream()
                    .filter(g -> g.getId().equals(rq.getCategoryId()))
                    .findFirst()
                    .orElse(null);

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

    public void updateProduct(List<CrudProductRequestDTO> request) {

    }

    public void deleteProduct(List<CrudProductRequestDTO> request) {

    }

    public String createCdProduct(String cdLine, String cdCategory, String cdGroup, Long productId) {
        if (productId == null) {
            throw new BusinessException(ResponseEnum.INVALID_INPUT.getCode(), ResponseEnum.INVALID_INPUT.getMessage());
        }
        return cdLine + cdGroup + cdCategory + productId;
    }

}
