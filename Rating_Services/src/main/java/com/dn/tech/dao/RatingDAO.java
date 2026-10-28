package com.dn.tech.dao;

import com.dn.tech.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingDAO extends JpaRepository<Rating, Long> {
	List<Rating> findByUserId(String userId);
	List<Rating> findByHotelId(String hotelId);
}
