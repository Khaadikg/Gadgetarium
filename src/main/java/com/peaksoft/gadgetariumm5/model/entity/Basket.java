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
    @Column(name = "discount")
    private double discount;
    @Column(name = "total")
    private double total;
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name ="user_id")
    private User user;
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy = "basket")
    @JsonIgnore
    private List<Product> products;
    @Column(name = "grand_total")
    private double grandTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "basket")
    private List<Order> orders;
}
