package com.project.shopapp.controllers;

import com.project.shopapp.dtos.OrderDetailDTO;
import com.project.shopapp.models.Order;
import com.project.shopapp.models.OrderDetail;
import com.project.shopapp.responses.OrderDetailResponse;
import com.project.shopapp.services.IOrderDetailService;
import com.project.shopapp.services.OrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/order_details")
public class OrderDetailController {
    private final IOrderDetailService orderDetailService;
    private static final Logger logger = LoggerFactory.getLogger(OrderDetailController.class);
    @PostMapping("")
    public ResponseEntity<?> createOrderDetail(
            @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetailResponse orderDetailResponse = OrderDetailResponse
                    .fromOrderDetail(orderDetailService.createOrderDetail(orderDetailDTO));
            logger.info("New OrderDetailDTO created: {}", orderDetailDTO);
            return ResponseEntity.ok(orderDetailResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Long id) {
        try {
            OrderDetailResponse getOrderDetailResponse = OrderDetailResponse
                    .fromOrderDetail(orderDetailService.getOrderDetail(id));
            return ResponseEntity.ok(getOrderDetailResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("orderId") Long orderId) {
        try {
            List<OrderDetailResponse> orderDetailResponses = new ArrayList<OrderDetailResponse>();
            List<OrderDetail> orderDetails = orderDetailService.findByOrderId(orderId);
            for(OrderDetail orderDetail: orderDetails) {
                orderDetailResponses.add(OrderDetailResponse.fromOrderDetail(orderDetail));
            }
            return ResponseEntity.ok(orderDetailResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Long id,
                                         @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            OrderDetailResponse newOrderDetailResponse = OrderDetailResponse
                    .fromOrderDetail(orderDetailService.updateOrderDetail(id,orderDetailDTO));
            logger.info("OrderDetail {} updated: {}", id, orderDetailDTO);
            return ResponseEntity.ok(newOrderDetailResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Long id) {
        orderDetailService.deleteById(id);
        logger.info("OrderDetail deleted: {}", id);
        return ResponseEntity.noContent().build();
    }

}
