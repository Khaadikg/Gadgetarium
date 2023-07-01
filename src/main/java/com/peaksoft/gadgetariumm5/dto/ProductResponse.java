package com.peaksoft.gadgetariumm5.dto;

import com.peaksoft.gadgetariumm5.model.entity.Brand;
import com.peaksoft.gadgetariumm5.model.entity.Bucked;
import com.peaksoft.gadgetariumm5.model.entity.Review;
import com.peaksoft.gadgetariumm5.model.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class ProductResponse {
    private Long id;
    private String name;
    private double price;
    private int article;
    private int inStock;
    private File file;
    private Brand brand;
    private String screen;
    private String color;
    private String rating;
    private String memory;
    private LocalDate releaseDate;
    private OperatingSystem operatingSystem;
    private int simCard;
    private int warranty;
    private String processor;
    private double weight;
    private WirelessInterface wirelessInterface;
    private Gender gender;
    private BodyShape bodyShape;
    private WaterResistance waterResistance;
    private Bucked bucked;
    private int discount;


}



