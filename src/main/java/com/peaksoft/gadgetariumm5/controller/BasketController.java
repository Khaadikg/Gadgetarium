package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.BasketResponse;
import com.peaksoft.gadgetariumm5.dto.ProductResponse;
import com.peaksoft.gadgetariumm5.service.ShoppingCartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
@Tag(name = "Basket controller", description = "add product")
public class BasketController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping("/getAll")
    @Operation(summary = "", description = "User get all Basket")
    public BasketResponse getAll(Principal principal) {
        return shoppingCartService.getAllBasket(principal.getName());
    }

    @PostMapping("/add")
    @Operation(summary = "", description = "User add product to Basket")
    public ProductResponse addToBasket(@RequestParam Long id, Principal principal) {
        return shoppingCartService.addToBasket(id, principal.getName());
    }

    @PostMapping("minus")
    @Operation(summary = "", description = "User minus product from Basket")
    public String minus(@RequestParam Long id, Principal principal) {
        shoppingCartService.minus(id, principal.getName());
        return "Product successfully got minus!";
    }

    @DeleteMapping("delete")
    @Operation(summary = "", description = "User delete product from Basket")
    public String deleteProductBasket(@RequestParam("id") Long id, Principal principal) {
        shoppingCartService.deleteProduct(id, principal.getName());
        return "Product deleted!";
    }
}
