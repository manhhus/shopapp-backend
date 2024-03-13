package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDetailDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok("createOrderDetail");
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.ok("getOrderDetail" + id);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        return ResponseEntity.ok("getOrderDetails" + orderId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
                                         @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        return ResponseEntity.ok("update order" + id +", newOrderDetail " + orderDetailDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        return ResponseEntity.noContent().build();
    }

}
