package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.ProductResponseVIew;
import com.peaksoft.gadgetariumm5.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @GetMapping("/search")
    public ProductResponseVIew getProducts(@RequestParam(name = "text", required = false)
                                           String text,
                                           @RequestParam int page,
                                           @RequestParam int size) {
        return productService.searchAndPagination(text, page, size);

    }
}
