package com.peaksoft.gadgetariumm5.controller;

import com.peaksoft.gadgetariumm5.dto.DiscountRequest;
import com.peaksoft.gadgetariumm5.dto.DiscountResponse;
import com.peaksoft.gadgetariumm5.dto.SimpleResponse;
import com.peaksoft.gadgetariumm5.service.DiscountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/discounts")
@RequiredArgsConstructor
public class DiscountController {
    private final DiscountService discountService;
    @GetMapping
    public List<DiscountResponse> discounts(){
        return discountService.getAllDiscounts();
    }
    @PostMapping
    public DiscountResponse create(@RequestBody DiscountRequest discountRequest){
      return discountService.create(discountRequest);
    }
    @PutMapping("id")
    public DiscountResponse update(@PathVariable("id")Long id,@RequestBody DiscountRequest discountRequest){
        return discountService.update(id,discountRequest);
    }
    @GetMapping("{id}")
    public DiscountResponse getById(@PathVariable("id")Long id){
        return discountService.getById(id);
    }
    @DeleteMapping("{id}")
    public SimpleResponse delete(@PathVariable("id")Long id){
        return discountService.delete(id);
    }

}
