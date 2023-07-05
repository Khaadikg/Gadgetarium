package com.peaksoft.gadgetariumm5.dto;

import com.peaksoft.gadgetariumm5.model.entity.Basket;
import com.peaksoft.gadgetariumm5.model.entity.Brand;
import com.peaksoft.gadgetariumm5.model.entity.Category;
import com.peaksoft.gadgetariumm5.model.entity.Review;
import com.peaksoft.gadgetariumm5.model.enums.*;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
@Getter
@Setter
public class ProductRequest {
    private String name;
    private int price;
    private int discount;
    private int inStock;
    private int article;
    private File file;
    private Brand brand;
    private String screen;
    private String color;
    private String rating;
    private String memory;
    private OperatingSystem operatingSystem;
    private int simCard;
    private int warranty;
    private String processor;
    private int weight;
    private WirelessInterface wirelessInterface;
    private Gender gender;
    private BodyShape bodyShape;
    private SubCategory subCategory;
    private List<Review> reviews;
    private List<Category> categories;
    private WaterResistance waterResistance;
    private Basket basket;
    private LocalDate releaseDate;
    private String image;
    private int ram;
    private String video;

}
