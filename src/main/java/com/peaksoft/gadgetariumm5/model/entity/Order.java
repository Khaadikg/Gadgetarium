package com.peaksoft.gadgetariumm5.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.peaksoft.gadgetariumm5.model.enums.Delivery;
import com.peaksoft.gadgetariumm5.model.enums.Payment;
import com.peaksoft.gadgetariumm5.model.enums.Status;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "orders")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String applicationNumber;

    @Enumerated(EnumType.STRING)
    private Delivery delivery;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.total, total)
                == 0 && id.equals(order.id)
                && applicationNumber.equals(order.applicationNumber)
                && delivery == order.delivery
                && fistName.equals(order.fistName)
                && lastName.equals(order.lastName)
                && email.equals(order.email)
                && phoneNumber.equals(order.phoneNumber)
                && Objects.equals(photo, order.photo)
                && created.equals(order.created)
                && update.equals(order.update)
                && address.equals(order.address)
                && payment == order.payment
                && status == order.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, applicationNumber,
                delivery, fistName,
                lastName, email, phoneNumber,
                photo, created, update, total,
                address, payment, status);
    }

    @JoinColumn(name = "first_name")
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
    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;
    private double total;
    private String address;
    @Enumerated(EnumType.STRING)
    private Payment payment;
    @Enumerated(EnumType.STRING)
    private Status status;

}
