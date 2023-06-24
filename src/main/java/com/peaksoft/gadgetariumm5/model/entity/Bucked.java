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
public class Bucked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "amount")
    private int amount;
    @Column(name = "discount")
    private int discount;
    @Column(name = "total")
    private int total;
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name ="user_id")
    private User user;
    @OneToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    private List<Product> products;
    private Long grandTotal;
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "bucked")
    private List<Order> orders;
}
