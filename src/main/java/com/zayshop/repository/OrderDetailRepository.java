package com.zayshop.repository;

import com.zayshop.entity.Order;
import com.zayshop.entity.Orderdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<Orderdetail, Integer> {
    List<Orderdetail> findAllByOrderID(Order order);
}
