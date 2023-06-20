package com.peaksoft.gadgetariumm5.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String photo;
    @ManyToMany
    private List<Product> products;
    private String commentary;
    private int rade;
    @ManyToMany
    @JoinTable(name = "reviews_to_response_reviews",
            joinColumns = @JoinColumn(name = "reviews_id"),
            inverseJoinColumns = @JoinColumn(name = "response_reviews_id"))
    private List<ResponseToReview> responseToReviews;
    @ManyToMany
    private List<User> users;

}
