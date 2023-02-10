package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.security.domain.User;
import com.globits.wl.domain.UserViewedNotification;

@Repository
public interface UserViewedNotificationRepository extends JpaRepository<UserViewedNotification, Long>{

	@Query(" SELECT o FROM UserViewedNotification o WHERE o.user.id=?1  ORDER BY o.createDate DESC")
	List<UserViewedNotification> findByUser(Long userId);
	
}
