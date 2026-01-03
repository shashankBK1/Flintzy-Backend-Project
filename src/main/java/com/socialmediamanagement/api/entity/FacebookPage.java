package com.socialmediamanagement.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "facebook_pages")
@Getter
@Setter
public class FacebookPage {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "page_id", nullable = false)
	private String pageId;

	private String pageName;

	@Column(length = 500)
	private String pageAccessToken;

	@ManyToOne(optional = false)
	@JoinColumn(name = "user_id")
	private User user;
}
