package com.zayshop.controller.Admin;

import com.zayshop.dto.OrderDTO;
import com.zayshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @GetMapping("")
    public ResponseEntity getOrder(@RequestParam(required = false) String status) {
        return ResponseEntity.ok(orderService.getOrder(status));
    }
    @GetMapping("/{id}")
    public ResponseEntity getOrder(@PathVariable @Valid Integer id) throws Exception {
        return ResponseEntity.ok(orderService.getOrderDetailById(id));
    }

    @PutMapping("")
    public ResponseEntity handlingOrder(@RequestBody OrderDTO dto) {
        try {
            orderService.handlingOrder(dto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
