package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.ProductService;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api")
public class SearchController {
	@Autowired
	ProductService productService;

	@GetMapping("/search")
	public ResponseEntity<?> findByProductNameOrBrandName(@RequestParam("keyword") String key) {
		return ResponseEntity.ok(productService.findByName(key));
	}
}
