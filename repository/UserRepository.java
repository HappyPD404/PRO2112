package com.example.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
	Page<User> findAllByUsernameLike(String keyword, Pageable pageable);
	
	@Query(value = "SELECT "
			+ "MONTH(u.last_login_date) AS month, "
			+ "COUNT(u.id) AS login "
			+ "FROM users u "
			+ "WHERE YEAR(u.last_login_date)=:year "
			+ "GROUP BY MONTH(u.last_login_date) "
			+ "ORDER BY MONTH(u.last_login_date) ASC", nativeQuery = true)
	List<Object[]> statisticsViewMonthByYear(@Param("year")int year);
	
	@Query(value = "SELECT "
			+ "COUNT(u.id) AS users "
			+ "FROM users u "
			+ "WHERE u.created_date >= :dateNow AND u.created_date < :dateTo", nativeQuery = true)
	Integer getCustomersByDate(@Param("dateNow")String dateNow, @Param("dateTo")String dateTo);
	
	@Query(value = "SELECT "
			+ "COUNT(u.id) AS users "
			+ "FROM users u "
			+ "WHERE MONTH(u.created_date) = :month", nativeQuery = true)
	Integer getCustomersByMonth(@Param("month")int month);
	
	@Query(value = "SELECT "
			+ "COUNT(u.id) AS users "
			+ "FROM users u "
			+ "WHERE YEAR(u.created_date) = :year", nativeQuery = true)
	Integer getCustomersByYear(@Param("year")int year);

}