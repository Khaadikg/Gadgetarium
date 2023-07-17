package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.ProductRequest;
import com.peaksoft.gadgetariumm5.dto.ProductResponse;
import com.peaksoft.gadgetariumm5.dto.ProductResponseVIew;
import com.peaksoft.gadgetariumm5.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Products.")
public class ProductController {
    private final ProductService productService;


    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    public ProductResponse getByProductId(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }


    @PostMapping
    public ProductResponse create(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PutMapping("{id}")
    public ProductResponse update(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "Successfully deleted product with id: " + id;
    }

    @GetMapping("/search")
    @Operation(summary = "Search product.")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public ProductResponseVIew getProducts(@RequestParam(name = "text", required = false)
                                           String text,
                                           @RequestParam int page,
                                           @RequestParam int size) {
        return productService.searchAndPagination(text, page, size);

    }

}
