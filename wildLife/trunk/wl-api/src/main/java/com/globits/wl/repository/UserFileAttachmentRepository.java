package com.globits.wl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.globits.wl.domain.FarmFileAttachment;
import com.globits.wl.domain.UserFileAttachment;

@Repository
public interface UserFileAttachmentRepository extends JpaRepository<UserFileAttachment, Long> {

}
