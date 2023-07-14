package com.peaksoft.gadgetariumm5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peaksoft.gadgetariumm5.model.enums.Delivery;
import com.peaksoft.gadgetariumm5.model.enums.Payment;
import com.peaksoft.gadgetariumm5.model.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String applicationNumber;
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "order")
    @JsonIgnore
    private List<Product> products;
    @Enumerated(EnumType.STRING)
    private Delivery delivery;
    @JoinColumn(name="first_name")
    private String fistName;
    @JoinColumn(name = "last_name")
    private String lastName;
    private String email;
    @JoinColumn(name = "phone_number")
    private String phoneNumber;
     private String photo;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime update;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    private double total;
    private String address;
    @Enumerated(EnumType.STRING)
    private Payment payment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnore
    private List<OrderDetails> details;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JsonIgnore
    private Basket basket;
}
