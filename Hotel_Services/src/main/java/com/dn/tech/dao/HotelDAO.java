package com.dn.tech.dao;

import com.dn.tech.model.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HotelDAO extends JpaRepository<Hotel, Long> {
	//  boolean existsBy(String name);
}
