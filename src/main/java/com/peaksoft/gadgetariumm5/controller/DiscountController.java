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
@Tag(name = "Discount Auth",description = "We can create new Discount")
public class DiscountController {
    private final DiscountService discountService;
    @GetMapping
    public List<DiscountResponse> discounts(){
        return discountService.getAllDiscounts();
    }
    @PostMapping
    @Operation(summary = "Create",description = "Admin can create new Discount")
    public DiscountResponse create(@RequestBody DiscountRequest discountRequest){
      return discountService.create(discountRequest);
    }
    @PutMapping("id")
    @Operation(summary = "Update",description = "Admin can update Discount")
    public DiscountResponse update(@PathVariable("id")Long id,@RequestBody DiscountRequest discountRequest){
        return discountService.update(id,discountRequest);
    }
    @GetMapping("{id}")
    @Operation(summary = "Get by id",description = "Admin can get Discount by id")
    public DiscountResponse getById(@PathVariable("id")Long id){
        return discountService.getById(id);
    }
    @DeleteMapping("{id}")
    @Operation(summary = "Delete",description = "Admin can delete Discount by id")
    public SimpleResponse delete(@PathVariable("id")Long id){
        return discountService.delete(id);
    }

}
