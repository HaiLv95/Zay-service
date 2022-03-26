package com.zayshop.repository;

import com.zayshop.entity.Orderdetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends JpaRepository<Orderdetail, Integer> {
}
