package com.peaksoft.gadgetariumm5.repository;

import com.peaksoft.gadgetariumm5.model.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {

}
