package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.common.enums.RequestParameterEnum;
import com.example.demo.common.enums.RequestStatusEnum;
import com.example.demo.entity.Users;
import com.example.demo.exception.InvalidRequestParameterException;
import com.example.demo.model.MailUserInfo;
import com.example.demo.repository.UsersRepository;

import jakarta.mail.MessagingException;

@Service
public class UsersService implements BaseService<Users, Integer> {

	@Autowired
	EmailService emailService;

	@Autowired
	private UsersRepository usersRepository;

	@Override
	public List<Users> findAll() {
		return usersRepository.findAll();
	}

	@Override
	public Users findById(Integer id) throws InvalidRequestParameterException {
		return usersRepository.findById(id)
				.orElseThrow(() -> new InvalidRequestParameterException("id", RequestParameterEnum.NOTHING));
	}

	public Users login(String email, String password) throws InvalidRequestParameterException {
		usersRepository.findByEmail(email)
				.orElseThrow(() -> new InvalidRequestParameterException("EMAIL", RequestParameterEnum.NOT_EXISTS));
		return usersRepository.findByEmailAndPassword(email, password)
				.orElseThrow(() -> new InvalidRequestParameterException("PASSWORD", RequestParameterEnum.WRONG));
	}

	public List<Users> findByIsAdmin(Boolean isAdmin) {
		return usersRepository.findByIsAdmin(isAdmin);
	}

	public RequestStatusEnum save(Users user) throws InvalidRequestParameterException {
		if (usersRepository.findByEmail(user.getEmail()).isPresent()) {
			throw new InvalidRequestParameterException("User", RequestParameterEnum.EXISTS);
		}
		return (usersRepository.save(user) != null ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public int deleteById(Integer id) throws InvalidRequestParameterException {
		if (!usersRepository.existsById(id)) {
			throw new InvalidRequestParameterException("id", RequestParameterEnum.NOT_FOUND);
		}
		try {
			usersRepository.delete(usersRepository.findById(id).get());
			return 1;
		} catch (Exception e) {
			return 0;
		}
	}

	public RequestStatusEnum update(Users user) throws InvalidRequestParameterException {
		if (!usersRepository.existsById(user.getId())) {
			throw new InvalidRequestParameterException("User", RequestParameterEnum.NOT_EXISTS);
		}
		return (usersRepository.save(user) != null ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
	}

	public List<Users> customersBuyMostInMonth() {
		return usersRepository.customersBuyMostInMonth();
	}

	public Optional<Users> findByEmail(String email) {
		return usersRepository.findByEmail(email);
	}

	public List<Users> findByActive(boolean active) {
		return usersRepository.findByActive(active);
	}

	public void saveAll(List<Users> list) {
		usersRepository.saveAll(list);
	}

	public void deleteAll(List<Users> list) {
		usersRepository.deleteAll(list);
	}

	public List<Users> findByActiveAndCreatedAtBefore(boolean active, LocalDateTime time) {
		return usersRepository.findByActiveAndCreatedAtBefore(active, time);
	}

	public String registration(Users users) throws InvalidRequestParameterException {
		Optional<Users> us = usersRepository.findByEmail(users.getEmail());
		if (us.isPresent()) {
			if (us.get().getToken() != null)
				// Exists Token
				throw new InvalidRequestParameterException("Token", RequestParameterEnum.EXISTS);
		}
		try {
			return (emailService.sendCode(
					new MailUserInfo(users.getEmail(), "Mã xác minh tài khoản của bạn trên Zuhot Cinema", users)));
		} catch (MessagingException ex) {
			throw new InvalidRequestParameterException("Email", RequestParameterEnum.WRONG);
		}
	}

	public RequestStatusEnum registrationConfirm(String OTP) throws InvalidRequestParameterException {
		Users users = usersRepository.findByToken(OTP)
				.orElseThrow(() -> new InvalidRequestParameterException("Token", RequestParameterEnum.NOT_EXISTS));
		if (users.getToken().equals(OTP)) {
			users.setActive(true);
			return (usersRepository.save(users) != null ? RequestStatusEnum.SUCCESS : RequestStatusEnum.FAILURE);
		}
		return RequestStatusEnum.FAILURE;
	}
}
