package com.socialmediamanagement.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.socialmediamanagement.api.entity.FacebookPage;

public interface FacebookPageRepository extends JpaRepository<FacebookPage, Long> {

	Optional<FacebookPage> findByPageIdAndUser_Id(String pageId, Long userId);
}
