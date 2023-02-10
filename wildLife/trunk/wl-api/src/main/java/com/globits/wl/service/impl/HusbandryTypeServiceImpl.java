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
import com.globits.wl.domain.HusbandryType;
import com.globits.wl.dto.HusbandryTypeDto;
import com.globits.wl.dto.WaterSourceDto;
import com.globits.wl.repository.HusbandryTypeRepository;
import com.globits.wl.service.HusbandryTypeService;

@Service
public class HusbandryTypeServiceImpl extends GenericServiceImpl<HusbandryType, Long> implements HusbandryTypeService {

	@Autowired
	private HusbandryTypeRepository husbandryTypeRopository;

	@Override
	public Page<HusbandryTypeDto> getListHusbandryTypes(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.husbandryTypeRopository.getListHusbandryTypes(pageable);
	}

	@Override
	public List<HusbandryTypeDto> getAll() {
		return this.husbandryTypeRopository.getAll();
	}

	@Override
	public HusbandryTypeDto husbandryTypeById(Long id) {
		return this.husbandryTypeRopository.husbandryTypeById(id);
	}

	@Override
	public HusbandryTypeDto saveHusbandryType(HusbandryTypeDto husbandryTypeDto, Long id) {
		HusbandryType husbandryType = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			husbandryType = this.husbandryTypeRopository.findOne(id);
		} else if (husbandryTypeDto.getId() != null) {
			husbandryType = this.husbandryTypeRopository.findOne(husbandryTypeDto.getId());
		}

		if (husbandryType == null) {// trường hợp thêm mới
			husbandryType = new HusbandryType();
			husbandryType.setCreateDate(currentDate);
			husbandryType.setCreatedBy(currentUserName);
		}

		if (husbandryTypeDto.getName() != null)
			husbandryType.setName(husbandryTypeDto.getName());

		if (husbandryTypeDto.getCode() != null)
			husbandryType.setCode(husbandryTypeDto.getCode());

		husbandryType = this.husbandryTypeRopository.save(husbandryType);
		husbandryTypeDto.setId(husbandryType.getId());
		return husbandryTypeDto;
	}

	@Override
	public HusbandryTypeDto removeHusbandryType(Long id) {
		HusbandryTypeDto husbandryTypeDto = null;
		if (husbandryTypeRopository != null && this.husbandryTypeRopository.exists(id)) {
			husbandryTypeDto = new HusbandryTypeDto(this.husbandryTypeRopository.findOne(id));
			this.husbandryTypeRopository.delete(id);

		}
		return husbandryTypeDto;
	}

}
