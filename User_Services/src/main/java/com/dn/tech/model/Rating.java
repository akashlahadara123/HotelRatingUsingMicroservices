package com.dn.tech.model;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Rating {
	
	private Long id;
	private String ratingId;
	private String userId;
	private String hotelId;
	private int rating;
	private String feedback;
	@Transient
	private Hotel hotel;
}
