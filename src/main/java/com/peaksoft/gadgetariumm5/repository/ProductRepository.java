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
    @Query("select product from Product product where" +
            " upper(product.brand.brandName) like concat('%',:text,'%') " +
            "or upper(product.name)like concat('%',:text,'%') " +
            "or upper(product.screen)like concat('%',:text,'%') " +
            "or upper(product.memory) like concat('%',:text,'%') " +
            "or upper(product.processor)like concat('%',:text,'%')" +
            "or cast(product.price as string )like concat('%',:text,'%') " +
            "or cast(product.article as string )like concat('%',:text,'%')" +
            "or cast(product.discount as string ) like concat('%',:text,'%')" +
            "or upper(product.wirelessInterface )like concat('%',:text,'%')" +
            "or upper(product.gender)like concat('%',:text,'%')" +
            "or upper(product.operatingSystem)like concat('%',:text,'%')" +
            "or upper(product.bodyShape)like concat('%',:text,'%')" +
            "or upper(product.waterResistance)like concat('%',:text,'%')" +
            "or upper(product.subCategory) like upper(concat('%',:text,'%') )")
    List<Product> searchAndPagination(@Param("text") String text, Pageable pageable);
    @Query("select p from Product p where upper(p.sort) like ('NEW') and like ('BY_STOCK')" +
            " and like ('FEATURED')" +
            "and like ('BY_PRICE_INCREASE')" +
            " and like ('BY_DECREASING_PRICE')")
    List<Product> sorting(@Param("text") String text);
    @Query("select product from Product product where upper(product.byStock) ='ДО_50' and sum(product.price)" +
            "< avg (product.price) or upper(product.byStock)='СВЫШЕ_50' and sum (product.price)> avg(product.price)")
    List<Product>searchStock(@Param("text")String text);

}

