package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.FmsAdministrativeUnitSystemMessage;
import com.globits.wl.dto.FmsAdministrativeUnitSystemMessageDto;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.FmsAdministrativeUnitSystemMessageRepository;
import com.globits.wl.repository.SystemMessageRepository;
import com.globits.wl.service.FmsAdministrativeUnitSystemMessageService;

@Service
public class FmsAdministrativeUnitSystemMessageServiceImpl extends GenericServiceImpl<FmsAdministrativeUnitSystemMessage, Long>
implements FmsAdministrativeUnitSystemMessageService{
	
	@Autowired
	private FmsAdministrativeUnitSystemMessageRepository fmsAdministrativeUnitSystemMessageRepository;
	
	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;
	
	
	@Autowired
	
	private SystemMessageRepository systemMessageRepository;
	
	@Override
	public List<FmsAdministrativeUnitSystemMessageDto> saveOrUpdateList(
			List<FmsAdministrativeUnitSystemMessageDto> listDto) {
		if(listDto!=null && listDto.size()>0) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			List<FmsAdministrativeUnitSystemMessageDto> listDtoUpdate= new ArrayList<FmsAdministrativeUnitSystemMessageDto>();
			for(FmsAdministrativeUnitSystemMessageDto dto: listDto) {
				FmsAdministrativeUnitSystemMessage entity= null;
				if(dto.getId()!=null) {
					entity= fmsAdministrativeUnitSystemMessageRepository.findOne(dto.getId());
					entity.setModifiedBy(currentUserName);
					entity.setModifyDate(currentDate);
					if(dto.getAdministrativeUnit().getId()!=null) {
						entity.setAdministrativeUnit(fmsAdministrativeUnitRepository.findById(dto.getAdministrativeUnit().getId()));
					}
					if(dto.getSystemMessage().getId()!=null) {
						entity.setSystemMessage(systemMessageRepository.findOne(dto.getSystemMessage().getId()));
					}
					entity= fmsAdministrativeUnitSystemMessageRepository.save(entity);
					listDtoUpdate.add(new FmsAdministrativeUnitSystemMessageDto(entity));
				}else {
					entity= new FmsAdministrativeUnitSystemMessage();
					entity.setCreateDate(currentDate);
					entity.setCreatedBy(currentUserName);
					if(dto.getAdministrativeUnit().getId()!=null) {
						entity.setAdministrativeUnit(fmsAdministrativeUnitRepository.findById(dto.getAdministrativeUnit().getId()));
					}
					if(dto.getSystemMessage().getId()!=null) {
						entity.setSystemMessage(systemMessageRepository.findOne(dto.getSystemMessage().getId()));
					}
					entity= fmsAdministrativeUnitSystemMessageRepository.save(entity);
					listDtoUpdate.add(new FmsAdministrativeUnitSystemMessageDto(entity));
				}
			}
			return listDtoUpdate;
		}
		
		return listDto;
	}

	@Override
	public List<FmsAdministrativeUnitSystemMessageDto> findAll() {
		List<FmsAdministrativeUnitSystemMessageDto> listDto= new ArrayList<FmsAdministrativeUnitSystemMessageDto>();
		List<FmsAdministrativeUnitSystemMessage> listEntity= new ArrayList<FmsAdministrativeUnitSystemMessage>();
		listEntity= fmsAdministrativeUnitSystemMessageRepository.findAll();
		if(listEntity!=null && listEntity.size()>0) {
			for(FmsAdministrativeUnitSystemMessage entity: listEntity) {
				FmsAdministrativeUnitSystemMessageDto dto= new FmsAdministrativeUnitSystemMessageDto(entity);
				listDto.add(dto);
			}
		}
		return listDto;
	}

}
