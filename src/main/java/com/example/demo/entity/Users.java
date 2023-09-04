package com.example.demo.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@SequenceGenerator(name = "user_generator", sequenceName = "users_id_seq", allocationSize = 1)
	@Column
	private int id;

	@Column
	private String password;

	@Column
	private String fullname;

	@Column
	private String email;

	@Column
	private String phone;

	@Column(name = "is_admin")
	private boolean isAdmin;

	@Column
	private boolean active;

	@Column
	private String token;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	private List<Orders> orders;

	@CreationTimestamp
	@Column
	private LocalDateTime createdAt;

	public Users(String password, String fullname, String email, String phone, String token) {
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.token = token;
		this.isAdmin = false;
		this.active = false;
	}

	public Users(String password, String fullname, String email, String phone, String token, boolean active) {
		this.password = password;
		this.fullname = fullname;
		this.email = email;
		this.phone = phone;
		this.token = token;
		this.active = active;
		this.isAdmin = false;
	}
}
