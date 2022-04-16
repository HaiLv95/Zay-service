package com.zayshop.service;

import com.zayshop.dto.OrderDTO;
import com.zayshop.entity.Order;
import com.zayshop.repository.OrderRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<OrderDTO> getOrder(String status) {
        List<Order>listOrder = orderRepository.searchOrderByStatus(status);
        List<OrderDTO> listDTO = listOrder.stream().map(item -> OrderDTO.builder()
                .id(item.getId())
                .address(item.getAddress())
                .orderDate(item.getOrderDate())
                .nameReceived(item.getNameReceived())
                .username(item.getUsername().getUsername())
                .phoneNumber(item.getPhoneNumber())
                .status(item.getStatus())
                .build()).collect(Collectors.toList());
        return listDTO;
    }
}
