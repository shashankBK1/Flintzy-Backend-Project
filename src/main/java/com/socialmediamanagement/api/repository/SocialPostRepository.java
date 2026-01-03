package com.socialmediamanagement.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialmediamanagement.api.entity.SocialPost;

public interface SocialPostRepository extends JpaRepository<SocialPost, Long> {

}
