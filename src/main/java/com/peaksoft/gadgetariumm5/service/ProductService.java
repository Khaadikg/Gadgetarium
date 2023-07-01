package com.peaksoft.gadgetariumm5.service;

import com.peaksoft.gadgetariumm5.dto.ProductResponse;
import com.peaksoft.gadgetariumm5.dto.ProductResponseVIew;
import com.peaksoft.gadgetariumm5.model.entity.Product;
import com.peaksoft.gadgetariumm5.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductResponse mapToResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setBrand(product.getBrand());
        productResponse.setColor(product.getColor());
        productResponse.setMemory(product.getMemory());
        productResponse.setScreen(product.getScreen());
        productResponse.setScreen(product.getScreen());
        return productResponse;

    }

    public ProductResponseVIew searchAndPagination(String text, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        ProductResponseVIew productResponseVIew = new ProductResponseVIew();
        productResponseVIew.setProductResponse((si(search(text, pageable))));

        return productResponseVIew;
    }

    public List<ProductResponse> si(List<Product> products) {
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
