package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.enums.RequestParameterEnum;
import com.example.demo.common.enums.RequestStatusEnum;
import com.example.demo.entity.Brand;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.repository.BrandRepository;

@Service
public class BrandService implements BaseService<Brand, Integer> {
	@Autowired
	BrandRepository repo;

	@Override
	public List<Brand> findAll() {
		return repo.findAll();
	}

	@Override
	public Brand findById(Integer id) throws InvalidRequestParameterException {
		if (id == null) {
			throw new InvalidRequestParameterException("id", RequestParameterEnum.NOTHING);
		}
		return repo.findById(id)
				.orElseThrow(() -> new InvalidRequestParameterException("id", RequestParameterEnum.NOT_FOUND));
	}

	public RequestStatusEnum save(Brand brand) throws InvalidRequestParameterException {
		if (brand == null) {
			throw new InvalidRequestParameterException("Brand", RequestParameterEnum.NOTHING);
		}
		Brand br = repo.findByName(brand.getName());
		if (br != null) {
			throw new InvalidRequestParameterException("Brand", RequestParameterEnum.EXISTS);
		}
		return (repo.save(brand) != null ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public RequestStatusEnum deleteById(Integer id) throws InvalidRequestParameterException {
		if (!repo.existsById(id)) {
			throw new InvalidRequestParameterException("id", RequestParameterEnum.NOT_FOUND);
		}
		repo.delete(repo.findById(id).get());
		return RequestStatusEnum.SUCCESS;
	}

	public RequestStatusEnum update(Integer id, Brand brand) throws InvalidRequestParameterException {
		if (!repo.existsById(id)) {
			throw new InvalidRequestParameterException("brand", RequestParameterEnum.NOT_EXISTS);
		}
		return (repo.save(brand) != null ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}
}
