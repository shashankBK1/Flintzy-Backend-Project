package com.socialmediamanagement.api.entity;

import java.time.LocalDateTime;

import com.socialmediamanagement.api.utility.AuthProvider;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
  
	private String name;
	
	private String email;
	
	@Column(length = 500)
    private String facebookAccessToken;
	
    @Enumerated(EnumType.STRING)
	private AuthProvider provider;

	private LocalDateTime createdAt;
}
