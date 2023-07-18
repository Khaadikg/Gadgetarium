package com.peaksoft.gadgetariumm5.repository;

import com.peaksoft.gadgetariumm5.model.entity.Discount;
import com.peaksoft.gadgetariumm5.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Pageable;
import java.util.List;

public interface DiscountRepository extends JpaRepository<Discount,Long> {
  //  List<Product> getProductsByDiscountId(@PathVariable("id")Long id, Pageable pageable);

}
