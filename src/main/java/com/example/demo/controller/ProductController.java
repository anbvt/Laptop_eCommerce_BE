package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Product;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.ProductFilter;
import com.example.demo.service.ProductService;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api")
public class ProductController {
	@Autowired
	private ProductService productService;

	@GetMapping("/products")
	public ResponseEntity<Page<Product>> getAll(
			@RequestParam(value = "p", defaultValue = "0") Optional<Integer> p,
			@RequestParam(value = "field", defaultValue = "id") String field) {
		Sort sort = Sort.by(Direction.ASC, field);
		Page<Product> productsPage = productService.findAll(PageRequest.of(p.orElse(0), 6, sort));
		return ResponseEntity.ok(productsPage);
	}

	@GetMapping("/product/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) throws InvalidRequestParameterException {
		return ResponseEntity.ok(productService.findById(id));
	}

	@PostMapping("/products/filter")
	public ResponseEntity<?> filterProduct(@RequestBody ProductFilter p) {
		return ResponseEntity
				.ok(productService.filterProduct(p.getRam(), p.getRom(), p.getDisplay(), p.getOs(), p.getBrandid()));
	}

	@GetMapping("/products-buy-most-in-month")
	public ResponseEntity<?> productsBuyMostInMonth() {
		return ResponseEntity.ok(productService.productsBuyMostInMonth());
	}

	@PutMapping("/product/edit")
	public void update(@RequestBody Product product) {
		productService.update(product);
	}

	@DeleteMapping("/product/delete/{id}")
	public void update(@PathVariable("id") Integer id) {
		productService.delete(id);
	}

	@PostMapping("/product/save")
	public ResponseEntity<?> save(@RequestBody Product p) throws InvalidRequestParameterException {
		return ResponseEntity.ok(productService.insert(p));
	}
}
