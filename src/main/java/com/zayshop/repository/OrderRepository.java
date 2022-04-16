package com.zayshop.repository;

import com.zayshop.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    @Query("select o from Order o where (:status is null or o.status = :status)")
    List<Order> searchOrderByStatus(@Param("status") String status);
}
