package com.peaksoft.gadgetariumm5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "baskets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    private int amount;
    @Column(name = "product_amount")
    private int productAmount;
    @Column(name = "discount")
    private double discount;
    @Column(name = "total")
    private double total;
    @Column(name = "grand_total")
    private double grandTotal;
    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    @ManyToMany(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "basketList")
    @JsonIgnore
    private List<Product> productList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "basket")
    @JsonIgnore
    private List<ProductAmount> productAmountList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "basket")
    private List<Order> orders;

}
