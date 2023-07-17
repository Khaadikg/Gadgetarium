package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.dto.ProductRequest;
import com.peaksoft.gadgetariumm5.dto.ProductResponse;
import com.peaksoft.gadgetariumm5.dto.ProductResponseVIew;
import com.peaksoft.gadgetariumm5.model.entity.Product;
import com.peaksoft.gadgetariumm5.repository.DiscountRepository;
import com.peaksoft.gadgetariumm5.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : productRepository.findAll()) {
            productResponses.add(mapToResponse(product));
        }
        return productResponses;

    }

    public ProductResponse getProductById(Long productId) {
        Product product = productRepository.findById(productId).get();
        return mapToResponse(product);
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product product = mapToEntity(productRequest);
        productRepository.save(product);
        return mapToResponse(product);
    }

    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {
        Product product = productRepository.findById(id).get();
        product.setCategories(productRequest.getCategories());
        product.setBrand(productRequest.getBrand());
        product.setName(productRequest.getName());
        product.setSubCategory(productRequest.getSubCategory());
        product.setWarranty(productRequest.getWarranty());
        product.setReleaseDate(productRequest.getReleaseDate());
        product.setColor(productRequest.getColor());
        product.setMemory(productRequest.getMemory());
        product.setRam(productRequest.getRam());
        product.setSimCard(productRequest.getSimCard());
        product.setImage(productRequest.getImage());
        product.setVideo(productRequest.getVideo());
        product.setFile(productRequest.getFile());
        productRepository.save(product);
        return mapToResponse(product);
    }

    public void deleteProduct(Long productId) {
        productRepository.deleteById(productId);
    }

    public Product mapToEntity(ProductRequest productRequest) {
        Product product = new Product();
        product.setCategories(productRequest.getCategories());
        product.setBrand(productRequest.getBrand());
        product.setName(productRequest.getName());
        product.setSubCategory(productRequest.getSubCategory());
        product.setWarranty(productRequest.getWarranty());
        product.setReleaseDate(LocalDate.now());
        return product;
    }

    public ProductResponse mapToResponse(Product product) {
        return ProductResponse.builder()

                .brand(product.getBrand())
                .basket(product.getBasket())
                .id(product.getId())
                .name(product.getName())
                .article(product.getArticle())
                .warranty(product.getWarranty())
                .color(product.getColor())
                .memory(product.getMemory())
                .screen(product.getScreen())
                .price(product.getPrice())
                .inStock(product.getInStock())
                .rating(product.getRating())
                .releaseDate(product.getReleaseDate())
                .bodyShape(product.getBodyShape())
                .operatingSystem(product.getOperatingSystem())
                .simCard(product.getSimCard())
                .processor(product.getProcessor())
                .weight(product.getWeight())
                .gender(product.getGender())
                .waterResistance(product.getWaterResistance())
                .wirelessInterface(product.getWirelessInterface())
                .discount(product.getDiscount().getPercentage()).build();

    }

    public ProductResponseVIew searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ProductResponseVIew productResponseVIew = new ProductResponseVIew();
        productResponseVIew.setProductResponse((view(search(text, pageable))));

        return productResponseVIew;
    }

    public List<ProductResponse> view(List<Product> products) {
        List<ProductResponse> productResponses = new ArrayList<>();
        for (Product product : products) {
            productResponses.add(mapToResponse(product));


        }
        return productResponses;
    }

    private List<Product> search(String text, Pageable pageable) {
        String name = text == null ? "" : text;
        return productRepository.searchAndPagination(name.toUpperCase(), pageable);
    }
}
