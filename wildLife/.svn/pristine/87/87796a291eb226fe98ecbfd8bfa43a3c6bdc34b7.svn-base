package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.UserAttachments;
import com.globits.wl.dto.UserAttachmentsDto;

@Repository
public interface UserAttachmentsRepository extends JpaRepository<UserAttachments, Long> {

	@Query("select entity from UserAttachments entity where entity.user.id = ?1 ")
	List<UserAttachments> findUserAttachmentsByUserId(Long userId);

	@Query("select new com.globits.wl.dto.UserAttachmentsDto(entity) from UserAttachments entity where entity.user.id = ?1 ")
	List<UserAttachmentsDto> getByUserId(Long userId);

}
