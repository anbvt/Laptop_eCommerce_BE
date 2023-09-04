package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.service.ColorService;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api/color")
public class ColorController {
	@Autowired
	private ColorService colorService;

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Integer id) throws InvalidRequestParameterException {
		return ResponseEntity.ok(colorService.findById(id));
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAll() {
		return ResponseEntity.ok(colorService.findAll());
	}
}
