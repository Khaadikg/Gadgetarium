package com.peaksoft.gadgetariumm5.model.entity;

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
    private String photo;
    @CreationTimestamp
    private LocalDateTime created;
    @UpdateTimestamp
    private LocalDateTime update;
    @ManyToOne(cascade ={CascadeType.REFRESH,CascadeType.DETACH,CascadeType.REFRESH,CascadeType.PERSIST})
    @JoinColumn(name = "user_id")
    private User user;
    private Long sum;
    private String address;
    @Enumerated(EnumType.STRING)
    private Payment payment;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany( cascade = CascadeType.ALL)
    @JoinColumn(name = "order_details_id")
    private List<OrderDetails> details;
    @ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
    private Bucked bucked;
}
