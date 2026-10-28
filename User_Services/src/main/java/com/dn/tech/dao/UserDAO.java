package com.dn.tech.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dn.tech.model.User;
@Repository
public interface UserDAO extends JpaRepository<User,Long>{
	 Optional<User> findByUserId(String userId); // Method to find user by userId
	 boolean existsByEmail(String name);
}
