package com.globits.wl.service.impl;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.InjectionPlant;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.InjectionPlantDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.InjectionPlantRepository;
import com.globits.wl.service.InjectionPlantService;

@Service
public class InjectionPlantServiceImpl extends GenericServiceImpl<InjectionPlant, Long>
		implements InjectionPlantService {
	private static final String ImportExportAnimal = null;
	@Autowired
	private InjectionPlantRepository injectionPlantRepository;

	@Override
	public Page<InjectionPlantDto> getPageDto(int pageIndex, int pageSize) {
//		if (pageIndex > 1) {
//			pageIndex--;
//		} else {
//			pageIndex = 0;
//		}
		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);
		return this.injectionPlantRepository.getPageDto(pageable);
	}

	@Override
	public List<InjectionPlantDto> getAllDto() {
		return injectionPlantRepository.getAllDto();
	}

	@Override
	public InjectionPlantDto getInjectionPlantById(Long id) {
		if (id != null) {
			InjectionPlant injectionPlant = null;
			injectionPlant = this.injectionPlantRepository.findOne(id);
			if (injectionPlant != null) {
				return new InjectionPlantDto(injectionPlant);
			}
		}
		return null;
	}

	@Override
	public InjectionPlantDto createInjectionPlant(InjectionPlantDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			InjectionPlant injectionPlant = null;
			if (dto.getId() != null) {
				injectionPlant = this.injectionPlantRepository.findOne(dto.getId());
			}

			if (injectionPlant == null) {
				injectionPlant = new InjectionPlant();
				injectionPlant.setCreateDate(currentDate);
				injectionPlant.setCreatedBy(currentUserName);
			}
			if (dto.getInjectionDate() != null) {
				injectionPlant.setInjectionDate(dto.getInjectionDate());
			}
			if (dto.getDrug() != null) {
				injectionPlant.setDrug(dto.getDrug());
			}
			if (dto.getInjectionRound() != null) {
				injectionPlant.setInjectionRound(dto.getInjectionRound());
			}

			InjectionPlant injectionPlant2 = this.injectionPlantRepository.save(injectionPlant);

			dto.setId(injectionPlant2.getId());

			return dto;
		}
		return null;

	}

	@Override
	public InjectionPlantDto updateInjectionPlant(Long id, InjectionPlantDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			InjectionPlant injectionPlant = null;
			if (id != null) {
				injectionPlant = this.injectionPlantRepository.findOne(id);
			} else if (dto.getId() != null) {
				injectionPlant = this.injectionPlantRepository.findOne(dto.getId());
			}

			if (injectionPlant == null) {
				injectionPlant = new InjectionPlant();
				injectionPlant.setCreateDate(currentDate);
				injectionPlant.setCreatedBy(currentUserName);
			}
			if (dto.getInjectionDate() != null) {
				injectionPlant.setInjectionDate(dto.getInjectionDate());
			}
			if (dto.getDrug() != null) {
				injectionPlant.setDrug(dto.getDrug());
			}
			if (dto.getInjectionRound() != null) {
				injectionPlant.setInjectionRound(dto.getInjectionRound());
			}
			InjectionPlant injectionPlant2 = this.injectionPlantRepository.save(injectionPlant);

			dto.setId(injectionPlant2.getId());

			return dto;
		}
		return null;
	}

	@Override
	public InjectionPlantDto deleteById(Long id) {
		if(id!= null) {
			InjectionPlantDto injectionPlantDto = null;
			InjectionPlant injectionPlant = new InjectionPlant();
			injectionPlant = this.injectionPlantRepository.findOne(id);
			if(injectionPlant != null) {
				injectionPlantDto = new InjectionPlantDto(injectionPlant);
				this.injectionPlantRepository.delete(id);
				return injectionPlantDto;
			}
		}
		return null;
	}

	@Override
	public boolean deleteByIds(List<Long> ids) {
		
		if(ids != null && ids.size() > 0) {
			for(Long id :ids) {
				this.injectionPlantRepository.delete(id);
			}
			return true;
		}
		return false;
	}



}
