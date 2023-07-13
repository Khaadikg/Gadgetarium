package com.peaksoft.gadgetariumm5.dto;

import com.peaksoft.gadgetariumm5.model.entity.Basket;
import com.peaksoft.gadgetariumm5.model.entity.Brand;
import com.peaksoft.gadgetariumm5.model.enums.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.File;
import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder
public class ProductResponse {
    private Brand brand;
    private Basket basket;
    private Long id;
    private String name;
    private double price;
    private String rating;
    private int article;
    private int inStock;
    private String screen;
    private OperatingSystem operatingSystem;
    private String color;
    private String memory;
    private LocalDate releaseDate;
    private int simCard;
    private int warranty;
    private String processor;
    private double weight;
    private WirelessInterface wirelessInterface;
    private Gender gender;
    private BodyShape bodyShape;
    private WaterResistance waterResistance;
    private int discount;
    private File file;
    private Sort sort;
    private ByStock stock;
}



