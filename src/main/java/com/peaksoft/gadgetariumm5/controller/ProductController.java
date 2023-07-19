package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.ProductRequest;
import com.peaksoft.gadgetariumm5.dto.ProductResponse;
import com.peaksoft.gadgetariumm5.dto.ProductResponseVIew;
import com.peaksoft.gadgetariumm5.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(name = "Product Auth",description = "We can create new Product")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Get All ",description = "Only Admin get all Products")
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("{id}")
    @Operation(summary = "Get by id", description = "Admin can get by id")
    public ProductResponse getByProductId(@PathVariable("id") Long id) {
        return productService.getProductById(id);
    }


    @PostMapping
    @Operation(summary = "Create",description = "Admin can create new Product")
    public ProductResponse create(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }

    @PutMapping("{id}")
    @Operation(summary = "Update",description = "Admin can update Product by id")
    public ProductResponse update(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest) {
        return productService.updateProduct(id, productRequest);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete",description = "Admim can delete product by id")
    public String delete(@PathVariable("id") Long id) {
        productService.deleteProduct(id);
        return "Successfully deleted product with id: " + id;
    }

    @GetMapping("/search")
    @Operation(summary = "Search",description = "Admin can search Product")
    public ProductResponseVIew getProducts(@RequestParam(name = "text", required = false)
                                           String text,
                                           @RequestParam int page,
                                           @RequestParam int size) {
        return productService.searchAndPagination(text, page, size);

    }

}
