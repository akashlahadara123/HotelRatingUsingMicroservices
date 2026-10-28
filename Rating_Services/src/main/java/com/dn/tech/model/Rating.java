package com.dn.tech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rating_Table",schema = "system")
public class Rating {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String ratingId;
	private String userId;
	private String hotelId;
	private int rating;
	private String feedback;
}
