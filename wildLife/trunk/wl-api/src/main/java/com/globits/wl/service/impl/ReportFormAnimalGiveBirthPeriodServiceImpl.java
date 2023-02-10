package com.globits.wl.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.ReportFormAnimalEgg;
import com.globits.wl.domain.ReportFormAnimalEggPeriod;
import com.globits.wl.domain.ReportFormAnimalGiveBirth;
import com.globits.wl.domain.ReportFormAnimalGiveBirthPeriod;
import com.globits.wl.dto.ReportFormAnimalEggDto;
import com.globits.wl.dto.ReportFormAnimalEggPeriodDto;
import com.globits.wl.dto.ReportFormAnimalGiveBirthDto;
import com.globits.wl.dto.ReportFormAnimalGiveBirthPeriodDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.ReportFormAnimalEggRepository;
import com.globits.wl.repository.ReportFormAnimalGiveBirthPeriodRepository;
import com.globits.wl.repository.ReportFormAnimalGiveBirthRepository;
import com.globits.wl.service.ReportFormAnimalGiveBirthPeriodService;

@Service
public class ReportFormAnimalGiveBirthPeriodServiceImpl extends GenericServiceImpl<ReportFormAnimalGiveBirthPeriod, Long> implements ReportFormAnimalGiveBirthPeriodService {
	@Autowired
	private ReportFormAnimalGiveBirthPeriodRepository repository;
	
	@Autowired
	private ReportFormAnimalGiveBirthRepository reportFormAnimalGiveBirthRepository;
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private AnimalRepository animalRepository;

	@Override
	public ReportFormAnimalGiveBirthPeriodDto saveOrUpdate(ReportFormAnimalGiveBirthPeriodDto dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		
		if(dto != null) {
			ReportFormAnimalGiveBirthPeriod entity = null;
			if(dto.getId() != null) {
				entity = this.repository.getOne(dto.getId());
			}
			if(entity == null) {
				entity = new ReportFormAnimalGiveBirthPeriod();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			}else {
				entity.setModifiedBy(currentUserName);
				entity.setModifyDate(currentDate);
			}
			
			entity.setYear(dto.getYear());
			entity.setMonth(dto.getMonth());
			entity.setDate(dto.getDate());
			Set<ReportFormAnimalGiveBirth> listItem = new HashSet<ReportFormAnimalGiveBirth>();
			if(dto.getReportFormAnimalGiveBirths() != null && dto.getReportFormAnimalGiveBirths().size() > 0) {
				for(ReportFormAnimalGiveBirthDto reportFormAnimalGiveBirthDto: dto.getReportFormAnimalGiveBirths()) {
					if(reportFormAnimalGiveBirthDto != null) {
						ReportFormAnimalGiveBirth reportFormAnimalGiveBirth = null;
						if(reportFormAnimalGiveBirthDto.getId() != null) {
							reportFormAnimalGiveBirth = reportFormAnimalGiveBirthRepository.findOne(reportFormAnimalGiveBirthDto.getId());
						}
						if(reportFormAnimalGiveBirth == null) {
							reportFormAnimalGiveBirth = new ReportFormAnimalGiveBirth();
							reportFormAnimalGiveBirth.setCreateDate(currentDate);
							reportFormAnimalGiveBirth.setCreatedBy(currentUserName);
						}else {
							reportFormAnimalGiveBirth.setModifyDate(currentDate);
							reportFormAnimalGiveBirth.setModifiedBy(currentUserName);
						}
						
						Farm farm = null;
						if(dto.getFarm() != null && dto.getFarm().getId() != null) {
							farm = farmRepository.findOne(dto.getFarm().getId());
							entity.setFarm(farm);				
						}
						Animal animal = null;
						if(reportFormAnimalGiveBirthDto.getAnimal() != null && reportFormAnimalGiveBirthDto.getAnimal().getId() != null) {
							animal = animalRepository.findOne(reportFormAnimalGiveBirthDto.getAnimal().getId());
							reportFormAnimalGiveBirth.setAnimal(animal);
						}
						reportFormAnimalGiveBirth.setDateReport(reportFormAnimalGiveBirthDto.getDateReport());
						reportFormAnimalGiveBirth.setParentMale(reportFormAnimalGiveBirthDto.getParentMale());
						reportFormAnimalGiveBirth.setParentFemale(reportFormAnimalGiveBirthDto.getParentFemale());
						reportFormAnimalGiveBirth.setQuantityChildHatch(reportFormAnimalGiveBirthDto.getQuantityChildHatch());
						reportFormAnimalGiveBirth.setQuantityChildDie(reportFormAnimalGiveBirthDto.getQuantityChildDie());
						reportFormAnimalGiveBirth.setQuantityChildLive(reportFormAnimalGiveBirthDto.getQuantityChildLive());
						reportFormAnimalGiveBirth.setQuantityChildIncrement(reportFormAnimalGiveBirthDto.getQuantityChildIncrement());
//						entity.setRemainQuantity(dto.getRemainQuantity());
						Integer remainQuantity=0;
						if(reportFormAnimalGiveBirthDto.getQuantityChildIncrement()!=null) {
							remainQuantity=reportFormAnimalGiveBirthDto.getQuantityChildIncrement();
						}
						if(reportFormAnimalGiveBirthDto.getQuantityChildSeparateCaptivity()!=null) {
							remainQuantity=remainQuantity-reportFormAnimalGiveBirthDto.getQuantityChildSeparateCaptivity();
						}
						reportFormAnimalGiveBirth.setRemainQuantity(remainQuantity);
						reportFormAnimalGiveBirth.setQuantityChildSeparateCaptivity(reportFormAnimalGiveBirthDto.getQuantityChildSeparateCaptivity());
						reportFormAnimalGiveBirth.setNote(reportFormAnimalGiveBirthDto.getNote());
						reportFormAnimalGiveBirth.setReportFormAnimalGiveBirthPeriod(entity);
						listItem.add(reportFormAnimalGiveBirth);
					}
				}
			}
			if(entity.getReportFormAnimalGiveBirths() == null || entity.getReportFormAnimalGiveBirths().size() == 0) {
				entity.setReportFormAnimalGiveBirths(listItem);
			}else {
				entity.getReportFormAnimalGiveBirths().clear();
				entity.getReportFormAnimalGiveBirths().addAll(listItem);
			}
			entity = this.repository.save(entity);
		}
		
		return dto;
	}

	@Override
	public Page<ReportFormAnimalGiveBirthPeriodDto> getPageBySearch(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize) {
		String sql = "SELECT new com.globits.wl.dto.ReportFormAnimalGiveBirthPeriodDto(rf) FROM ReportFormAnimalGiveBirthPeriod rf";
		String sqlCount = "SELECT count(*) FROM ReportFormAnimalGiveBirthPeriod rf";
		String whereClause = " where 1=1";
		if(searchDto.getFarmId() != null) {
			whereClause += " AND rf.farm.id = :farmId";
		}
		Query query = manager.createQuery(sql + whereClause + " order by rf.year desc, rf.month desc, rf.date  desc ", ReportFormAnimalGiveBirthPeriodDto.class);
		Query queryCount = manager.createQuery(sqlCount + whereClause);
		
		if(searchDto.getFarmId() != null) {
			query.setParameter("farmId", searchDto.getFarmId());
			queryCount.setParameter("farmId", searchDto.getFarmId());
		}
		
		Long total = 0L;
		Object obj = queryCount.getSingleResult();
		if(obj != null) {
			total = (Long)obj;
		}
		
		if(pageIndex > 0) {
			pageIndex--;
		}else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		Page<ReportFormAnimalGiveBirthPeriodDto> page = new PageImpl<ReportFormAnimalGiveBirthPeriodDto>(query.getResultList(), pageable, total);
		return page;
	}

	@Override
	public ReportFormAnimalGiveBirthPeriodDto getById(Long id) {
		return new ReportFormAnimalGiveBirthPeriodDto(this.repository.findOne(id));
	}

	@Override
	public ReportFormAnimalGiveBirthPeriodDto deleteById(Long id) {
		ReportFormAnimalGiveBirthPeriod reportFormAnimalGiveBirthPeriod = repository.findOne(id);
		if(reportFormAnimalGiveBirthPeriod != null) {
			repository.delete(id);
			return new ReportFormAnimalGiveBirthPeriodDto(reportFormAnimalGiveBirthPeriod);
		}
		return null;
	}

	@Override
	public Boolean checkDuplicateYearMonthDateReport16D(SearchReportPeriodDto searchDto) {
		if(searchDto != null) {
			List<ReportFormAnimalGiveBirthPeriod> list = repository.findByYearMonthDateFarmIdAnimalId(searchDto.getYear(), searchDto.getMonth(), searchDto.getDate(), searchDto.getFarmId());
			ReportFormAnimalGiveBirthPeriod reportFormAnimalGiveBirthPeriod = null;
			if(list != null && list.size() > 0) {
				reportFormAnimalGiveBirthPeriod = list.get(0);
			}
			
			if(reportFormAnimalGiveBirthPeriod == null) {
				return false;
			}
			
			if(searchDto.getId() == null) {
				return true;
			}
			if(searchDto.getId() != null && searchDto.getId() == reportFormAnimalGiveBirthPeriod.getId()) {
				return false;
			}
			if(searchDto.getId() != null && searchDto.getId() != reportFormAnimalGiveBirthPeriod.getId()) {
				return true;
			}
			
		}
		return false;
	}
}
