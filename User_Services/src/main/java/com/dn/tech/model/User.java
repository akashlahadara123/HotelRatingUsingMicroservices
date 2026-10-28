package com.dn.tech.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@Table(name = "user_table")
@Builder
public class User {
	public User()
	{
		System.out.println("User class Object created");
	}
	 @Id
	 @GeneratedValue(strategy = GenerationType.AUTO)
	 private Long id;
	 private String userId;
	 private String name;
	 private String email;
	 private String about;
	 @Transient
	 private List<Rating> ratingsList=new ArrayList<>();
	 @Transient
	 private Hotel hotel;
}
