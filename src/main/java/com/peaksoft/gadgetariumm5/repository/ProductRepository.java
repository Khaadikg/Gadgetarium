package com.peaksoft.gadgetariumm5.repository;

import com.peaksoft.gadgetariumm5.model.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select product from Product product inner  join  Brand  b on b.id=product.brand.id where upper(product.name) like concat('%',:text,'%')or upper(b.brandName)like  concat('%',:text,'%')" +
            "or upper(product.color) like concat('%',:text,'%') or upper(product.rating) like concat('%',:text,'%')" +
            "or upper(product.screen)like concat('%',:text,'%')or cast(product.releaseDate as string)like concat('%',:text,'%') " +
            "or upper(product.memory) like concat('%',:text,'%') or upper(product.processor)like concat('%',:text,'%')" +
            "or cast(product.price as string )like concat('%',:text,'%') or cast(product.article as string )like concat('%',:text,'%')" +
            "or cast(product.simCard as string )like concat('%',:text,'%')or cast(product.warranty as string )like concat('%',:text,'%') " +
            "or cast(product.weight as string )like concat('%',:text,'%')or cast(product.discount as string ) like concat('%',:text,'%')" +
            "or upper(product.wirelessInterface )like concat('%',:text,'%')or upper(product.gender)like concat('%',:text,'%')" +
            "or upper(product.operatingSystem)like concat('%',:text,'%')" +
            "or upper(product.bodyShape)like concat('%',:text,'%')or upper(product.waterResistance)like concat('%',:text,'%')" +
            "or upper(product.subCategory) like upper(concat('%',:text,'%') )")
    List<Product> searchAndPagination(@Param("text") String text, Pageable pageable);
}

