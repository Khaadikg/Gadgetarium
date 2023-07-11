package com.peaksoft.gadgetariumm5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "productAmount")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductAmount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    private double discount;
    private double total;
    private double grandTotal;
    private Long productId;
    private Long userId;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productAmount")
    @JsonIgnore
    private List<Product> productList;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    @JsonIgnore
    @JoinColumn(name = "basket_id")
    private Basket basket;
}
