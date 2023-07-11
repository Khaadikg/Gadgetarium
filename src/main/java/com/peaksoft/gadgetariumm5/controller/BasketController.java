package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.BasketResponse;
import com.peaksoft.gadgetariumm5.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/basket")
@RequiredArgsConstructor
public class BasketController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping("/getAll")
    public BasketResponse getAll(Principal principal) {
        System.out.println(principal.getName() + "------");
        return shoppingCartService.getAllBasket(principal.getName());
    }

    @PostMapping("/add")
    public String addToBasket(@RequestParam Long id, Principal principal) {
        System.out.println(principal.getName());
        shoppingCartService.addToBasket(id, principal.getName());
        return "Suuuu";
    }

    @PostMapping("plus")
    public String minus(@RequestParam Long id,Principal principal) {
        shoppingCartService.productAmount(id, principal.getName());
        return "Suuuuuu";
    }

//    @DeleteMapping("delete")
//    public String deleteProductBasket(@RequestParam("id") Long id, Principal principal) {
//        shoppingCartService.deleteProduct(id, principal.getName());
//        return "Suuuuu";
//    }


}
