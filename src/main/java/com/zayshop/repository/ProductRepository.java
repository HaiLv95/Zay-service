package com.zayshop.repository;

import com.zayshop.dto.ProductDTO;
import com.zayshop.entity.Category;
import com.zayshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("select p from Product p where (:productName is null or p.name like concat('%', :productName, '%'))" +
            " and (:categoryID is null or p.categoryID.id = :categoryID)" +
            "and  p.available = 1")
    Page<Product> searchAll(@Param("productName") String productName, @Param("categoryID") Integer categoryID, Pageable pageable);
}
