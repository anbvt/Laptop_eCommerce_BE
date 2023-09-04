package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Users;

public interface UsersRepository extends JpaRepository<Users, Integer> {
	Optional<Users> findByEmailAndPassword(String email, String password);

	Optional<Users> findByEmail(String email);

	List<Users> findByIsAdmin(Boolean isAdmin);

	Users findByFullname(String name);

	@Query(value = "SELECT usr.id, usr.password, usr.fullname, usr.email, usr.phone, usr.is_admin\n" +
			"FROM orders ord RIGHT JOIN users usr ON ord.customerid = usr.id\n" +
			"WHERE EXTRACT(MONTH FROM ord.buy_date) = EXTRACT(MONTH FROM CURRENT_DATE)\n" +
			"GROUP BY usr.id, usr.password, usr.fullname, usr.email, usr.phone, usr.is_admin\n" +
			"HAVING COUNT(ord.id) = (\n" +
			"    SELECT MAX(total_ord)\n" +
			"    FROM (\n" +
			"        SELECT usr.id, COUNT(ord.id) AS total_ord\n" +
			"        FROM orders ord RIGHT JOIN users usr ON ord.customerid = usr.id\n" +
			"        GROUP BY usr.id\n" +
			"    ) AS subquery\n" +
			");\n", nativeQuery = true)
	List<Users> customersBuyMostInMonth();

	List<Users> findByActive(boolean active);

	List<Users> findByActiveAndCreatedAtBefore(boolean active, LocalDateTime createAt);

	Optional<Users> findByToken(String token);
}
