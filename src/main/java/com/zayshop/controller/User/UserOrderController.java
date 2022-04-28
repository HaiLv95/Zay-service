package com.zayshop.controller.User;

import com.zayshop.dto.OrderDTO;
import com.zayshop.dto.OrderDetailDTO;
import com.zayshop.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
public class UserOrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("/{username}")
    public ResponseEntity getOrderByUsername(@PathVariable @Valid String username) throws Exception {
        return ResponseEntity.ok(orderService.findAllOrderByUsername(username));
    }

    @PostMapping("/add")
    public ResponseEntity checkOutCart(@RequestBody OrderDTO dto){
        return ResponseEntity.ok(orderService.addOrder(dto));
    }
    @PostMapping("/add-cart")
    public ResponseEntity addProductToCart(@RequestBody OrderDetailDTO dto){
        orderService.addOrderDetail(dto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity getDetailByOrderId(@PathVariable @Valid Integer id) throws Exception {
        return ResponseEntity.ok(orderService.findAllOrderDetailByOrderId(id));
    }

    @PutMapping("/update")
    public ResponseEntity updateStatus(@RequestBody @Valid OrderDTO dto) throws Exception {

        return ResponseEntity.ok(orderService.updateStatus(dto));

    }
    @GetMapping("/send-email")
    public ResponseEntity sendEmail(@RequestParam("orderId") Integer orderId) throws Exception {
        orderService.sendEmail(orderId);
        return ResponseEntity.ok().build();
    }
}
