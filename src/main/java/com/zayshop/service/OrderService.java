package com.zayshop.service;

import com.zayshop.dto.OrderDTO;
import com.zayshop.dto.OrderDetailDTO;
import com.zayshop.entity.Order;
import com.zayshop.entity.Orderdetail;
import com.zayshop.repository.OrderDetailRepository;
import com.zayshop.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository detailRepository;

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

    public void handlingOrder(OrderDTO dto) throws Exception {
        log.info("start handlingOrder");
        Optional<Order> optional = orderRepository.findById(dto.getId());
        if (optional.isEmpty()) throw new Exception("Not found Order");
        Order entity = new Order();
        BeanUtils.copyProperties(dto, entity);
        entity.setUsername(optional.get().getUsername());
        orderRepository.save(entity);
        log.info("end handlingOrder");
    }

    public List<OrderDetailDTO> getOrderDetailById(Integer id) throws Exception {
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isEmpty()) throw new Exception("Not found Order");
        List<Orderdetail> orderdetails = detailRepository.findAllByOrderID(optional.get());
        List<OrderDetailDTO> listDto = orderdetails.stream().map(
                item -> OrderDetailDTO.builder()
                        .id(item.getId())
                        .orderId(item.getOrderID().getId())
                        .productId(item.getProductID().getId())
                        .price(item.getPrice())
                        .productName(item.getProductID().getName())
                        .quantity(item.getQuantity())
                        .build()
        ).collect(Collectors.toList());
        return listDto;
    }
}
