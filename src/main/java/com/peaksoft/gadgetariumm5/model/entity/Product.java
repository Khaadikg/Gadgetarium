package com.peaksoft.gadgetariumm5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peaksoft.gadgetariumm5.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import lombok.*;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name ="products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;
    private int discountProduct;
    private int inStock;
    private int article;
    private File file;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "brand_id")
    @JsonIgnore
    private Brand brand;
    private String screen;
    private String color;
    private String rating;
    private String memory;
    @CreatedDate
    @JoinColumn(name = "release_date")
    private LocalDate releaseDate;
    @Enumerated(EnumType.STRING)
    private OperatingSystem operatingSystem;
    private int simCard;
    private int warranty;
    private String processor;
    private double weight;
    @Enumerated(EnumType.STRING)
    private WirelessInterface wirelessInterface;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private BodyShape bodyShape;
    @Enumerated(EnumType.STRING)
    private SubCategory subCategory;
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "products_reviews",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))
    private List<Review> reviews;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<Category> categories;
    @Enumerated(EnumType.STRING)
    private WaterResistance waterResistance;
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "products_baskets",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_id"))
    @JsonIgnore
    private List<Basket> basketList;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "product")
    @JsonIgnore
    private List<ProductAmount> productAmountList;
    private String image;
    private int ram;
    private String video;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "discount_id")
    private Discount discount;
    @Transient
    private Long discountId;
    @Enumerated(EnumType.STRING)
    private Sort sort;
    @Enumerated(EnumType.STRING)
    private ByStock byStock;



}
