package com.dn.tech.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class HotelDTO {
	
	private Long id;
	private String name;
	private String location;
	private String about;
}
