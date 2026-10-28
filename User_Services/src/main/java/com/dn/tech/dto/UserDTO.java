package com.dn.tech.dto;

import java.util.ArrayList;
import java.util.List;

import com.dn.tech.model.Rating;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
	
	 private Long id;
	 private String userId;
	 private String name;
	 private String email;
	 private String about;
	 private List<Rating> ratingsList=new ArrayList<>();
}
