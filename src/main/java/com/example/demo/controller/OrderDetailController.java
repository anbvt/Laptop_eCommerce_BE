package com.example.demo.controller;

import com.example.demo.entity.Orders;
import com.example.demo.service.OrderDetailService;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/order-detail")
public class OrderDetailController {
    @Autowired
    private OrderDetailService orderDetailService;

    @GetMapping("/products-sold")
    public ResponseEntity<?> ProductsSold(){
        return ResponseEntity.ok(orderDetailService.productsSold());
    }
}
