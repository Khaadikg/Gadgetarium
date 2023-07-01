package com.peaksoft.gadgetariumm5.model.entity;

import com.peaksoft.gadgetariumm5.model.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int price;
    private int article;
    private File file;
    @ManyToOne
    private Brand brand;
    private String screen;
    private String color;
    private String rating;
    private String memory;
    @JoinColumn(name = "release_date")
    private LocalDate releaseDate;
    @Enumerated(EnumType.STRING)
    private OperatingSystem operatingSystem;
    private int simCard;
    private int warranty;
    private String processor;
    private int weight;
    @Enumerated(EnumType.STRING)
    private WirelessInterface wirelessInterface;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private BodyShape bodyShape;
    @OneToMany
    @JoinTable(name = "products_review",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))
    private List<Review> reviews;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "products_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private List<SubCategory> categories;
    @Enumerated(EnumType.STRING)
    private WaterResistance waterResistance;
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST})
    @JoinColumn(name = "bucket_id")
    private Bucked bucked;
    private int discount;


}
