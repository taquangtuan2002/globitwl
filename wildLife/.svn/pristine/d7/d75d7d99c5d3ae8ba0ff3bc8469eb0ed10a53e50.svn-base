package com.globits.wl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.UserAttachments;
import com.globits.wl.domain.UserFileUpload;
import com.globits.wl.dto.UserAttachmentsDto;
import com.globits.wl.dto.UserFileUploadDto;

@Repository
public interface UserFileUploadRepository extends JpaRepository<UserFileUpload, Long> {
	@Query("select entity from UserFileUpload entity where entity.user.id = ?1 ")
	List<UserFileUpload> findUserFileUploadByUserId(Long userId);

	@Query("select new com.globits.wl.dto.UserFileUploadDto(entity) from UserFileUpload entity where entity.user.id = ?1 ")
	List<UserFileUploadDto> getByUserId(Long userId);
	
	@Query("select new com.globits.wl.dto.UserFileUploadDto(entity) from UserFileUpload entity where entity.id = ?1 ")
	UserFileUploadDto getUserFileUploadById(Long id);
}
