package com.peaksoft.gadgetariumm5.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "bucked")
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
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH}, mappedBy = "basket")
    private List<Product> products;
    @Column(name = "grand_total")
    private double grandTotal;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "bucked")
    private Long grandTotal;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "basket")
    private List<Order> orders;
}
