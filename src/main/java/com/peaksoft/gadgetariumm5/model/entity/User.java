package com.peaksoft.gadgetariumm5.model.entity;

import com.peaksoft.gadgetariumm5.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone_number")
    private String phoneNumber;
    private String address;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "bucked_id")
    private Bucked bucked;
    @Transient
    private Long buckedId;
    @ManyToMany
    private List<Review> reviews;
    @OneToMany
    private List<ResponseToReview> responseToReviews;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Order> orders;


}
