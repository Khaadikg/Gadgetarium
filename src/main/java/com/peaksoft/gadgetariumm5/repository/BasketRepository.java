package com.peaksoft.gadgetariumm5.repository;

import com.peaksoft.gadgetariumm5.model.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface BasketRepository extends JpaRepository<Basket,Long> {

//    @Query("select b from Basket b where b.user.id = :id and b.product.id = :prdId" )
//    Optional<Basket> findByUserId(Long id,Long prdId);
}
