package com.dn.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class RatingDTO {
	public RatingDTO()
	{
		System.out.println("User DTO Object Will be Created");
	}
	private Long id;
	private String ratingId;
	private String userId;
	private String hotelId;
	private int rating;
	private String feedback;
}
