package com.socialmediamanagement.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PublishPostRequest {

	@NotBlank(message = "PageID is reqiured")
	private String pageId;
	
	@NotBlank(message = "Post message cannot be empty")
	private String message;
}