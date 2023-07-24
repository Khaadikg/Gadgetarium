package com.peaksoft.gadgetariumm5.dto;

import com.peaksoft.gadgetariumm5.model.entity.Product;
import com.peaksoft.gadgetariumm5.model.entity.ProductAmount;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasketResponse {
    private Long id;
    private int amount;
    private double discount;
    private double total;
    private double grandTotal;
    private List<Product> productList;
    private List<ProductAmount> productAmountList;
}
