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
import com.globits.wl.dto.ReportFormAnimalEggDto;
import com.globits.wl.dto.ReportFormAnimalEggPeriodDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.ReportFormAnimalEggPeriodRepository;
import com.globits.wl.repository.ReportFormAnimalEggRepository;
import com.globits.wl.service.ReportFormAnimalEggPeriodService;

@Service
public class ReportFormAnimalEggPeriodServiceImpl extends GenericServiceImpl<ReportFormAnimalEggPeriod, Long> implements ReportFormAnimalEggPeriodService  {
	@Autowired
	private ReportFormAnimalEggPeriodRepository repository;
	@Autowired
	private ReportFormAnimalEggRepository reportFormAnimalEggRepository;
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private AnimalRepository animalRepository;

	@Override
	public ReportFormAnimalEggPeriodDto saveOrUpdate(ReportFormAnimalEggPeriodDto dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		
		if(dto != null) {
			ReportFormAnimalEggPeriod entity = null;
			if(dto.getId() != null) {
				entity = this.repository.getOne(dto.getId());
			}
			if(entity == null) {
				entity = new ReportFormAnimalEggPeriod();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			}else {
				entity.setModifiedBy(currentUserName);
				entity.setModifyDate(currentDate);
			}
			
			entity.setYear(dto.getYear());
			entity.setMonth(dto.getMonth());
			entity.setDate(dto.getDate());
			Set<ReportFormAnimalEgg> listItem = new HashSet<ReportFormAnimalEgg>();
			if(dto.getReportFormAnimalEggs() != null && dto.getReportFormAnimalEggs().size() > 0) {
				for(ReportFormAnimalEggDto reportFormAnimalEggDto: dto.getReportFormAnimalEggs()) {
					if(reportFormAnimalEggDto != null) {
						ReportFormAnimalEgg reportFormAnimalEgg = null;
						if(reportFormAnimalEggDto.getId() != null) {
							reportFormAnimalEgg = reportFormAnimalEggRepository.findOne(reportFormAnimalEggDto.getId());
						}
						if(reportFormAnimalEgg == null) {
							reportFormAnimalEgg = new ReportFormAnimalEgg();
							reportFormAnimalEgg.setCreateDate(currentDate);
							reportFormAnimalEgg.setCreatedBy(currentUserName);
						}else {
							reportFormAnimalEgg.setModifyDate(currentDate);
							reportFormAnimalEgg.setModifiedBy(currentUserName);
						}
						
						Farm farm = null;
						if(dto.getFarm() != null && dto.getFarm().getId() != null) {
							farm = farmRepository.findOne(dto.getFarm().getId());
							entity.setFarm(farm);				
						}
						Animal animal = null;
						if(reportFormAnimalEggDto.getAnimal() != null && reportFormAnimalEggDto.getAnimal().getId() != null) {
							animal = animalRepository.findOne(reportFormAnimalEggDto.getAnimal().getId());
							reportFormAnimalEgg.setAnimal(animal);
						}
						reportFormAnimalEgg.setDateReport(reportFormAnimalEggDto.getDateReport());
						reportFormAnimalEgg.setParentMale(reportFormAnimalEggDto.getParentMale());
						reportFormAnimalEgg.setParentFemale(reportFormAnimalEggDto.getParentFemale());
						reportFormAnimalEgg.setQuantityEgg(reportFormAnimalEggDto.getQuantityEgg());
						reportFormAnimalEgg.setQuantityEggWarm(reportFormAnimalEggDto.getQuantityEggWarm());
						reportFormAnimalEgg.setQuantityChildHatch(reportFormAnimalEggDto.getQuantityChildHatch());
						reportFormAnimalEgg.setQuantityChildDie(reportFormAnimalEggDto.getQuantityChildDie());
						reportFormAnimalEgg.setQuantityChildLive(reportFormAnimalEggDto.getQuantityChildLive());
						reportFormAnimalEgg.setQuantityChildIncrement(reportFormAnimalEggDto.getQuantityChildIncrement());
//						entity.setRemainQuantity(dto.getRemainQuantity());
						Integer remainQuantity=0;
						if(reportFormAnimalEggDto.getQuantityChildIncrement()!=null) {
							remainQuantity=reportFormAnimalEggDto.getQuantityChildIncrement();
						}
						if(reportFormAnimalEggDto.getQuantityChildSeparateCaptivity()!=null) {
							remainQuantity=remainQuantity-reportFormAnimalEggDto.getQuantityChildSeparateCaptivity();
						}
						reportFormAnimalEgg.setRemainQuantity(remainQuantity);
						reportFormAnimalEgg.setQuantityChildSeparateCaptivity(reportFormAnimalEggDto.getQuantityChildSeparateCaptivity());
						reportFormAnimalEgg.setNote(reportFormAnimalEggDto.getNote());
						reportFormAnimalEgg.setReportFormAnimalEggPeriod(entity);
						listItem.add(reportFormAnimalEgg);
					}
				}
			}
			if(entity.getReportFormAnimalEggs() == null || entity.getReportFormAnimalEggs().size() == 0) {
				entity.setReportFormAnimalEggs(listItem);
			}else {
				entity.getReportFormAnimalEggs().clear();
				entity.getReportFormAnimalEggs().addAll(listItem);
			}
			entity = this.repository.save(entity);
		}
		
		return dto;
	}

	@Override
	public Page<ReportFormAnimalEggPeriodDto> getPageBySearch(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize) {
		String sql = "SELECT new com.globits.wl.dto.ReportFormAnimalEggPeriodDto(rf) FROM ReportFormAnimalEggPeriod rf";
		String sqlCount = "SELECT count(*) FROM ReportFormAnimalEggPeriod rf";
		String whereClause = " where 1=1";
		if(searchDto.getFarmId() != null) {
			whereClause += " AND rf.farm.id = :farmId";
		}
		Query query = manager.createQuery(sql + whereClause + " order by rf.year desc, rf.month desc, rf.date  desc ", ReportFormAnimalEggPeriodDto.class);
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
		
		Page<ReportFormAnimalEggPeriodDto> page = new PageImpl<ReportFormAnimalEggPeriodDto>(query.getResultList(), pageable, total);
		return page;
	}

	@Override
	public ReportFormAnimalEggPeriodDto getById(Long id) {
		return new ReportFormAnimalEggPeriodDto(this.repository.findOne(id));
	}

	@Override
	public ReportFormAnimalEggPeriodDto deleteById(Long id) {
		ReportFormAnimalEggPeriod reportFormAnimalEggPeriod = repository.findOne(id);
		if(reportFormAnimalEggPeriod != null) {
			repository.delete(id);
			return new ReportFormAnimalEggPeriodDto(reportFormAnimalEggPeriod);
		}
		return null;
	}

	@Override
	public Boolean checkDuplicateYearMonthDateReport16C(SearchReportPeriodDto searchDto) {
		if(searchDto != null) {
			List<ReportFormAnimalEggPeriod> list = repository.findByYearMonthDateFarmIdAnimalId(searchDto.getYear(), searchDto.getMonth(), searchDto.getDate(), searchDto.getFarmId());
			ReportFormAnimalEggPeriod reportFormAnimalEggPeriod = null;
			if(list != null && list.size() > 0) {
				reportFormAnimalEggPeriod = list.get(0);
			}
			
			if(reportFormAnimalEggPeriod == null) {
				return false;
			}
			
			if(searchDto.getId() == null) {
				return true;
			}
			if(searchDto.getId() != null && searchDto.getId() == reportFormAnimalEggPeriod.getId()) {
				return false;
			}
			if(searchDto.getId() != null && searchDto.getId() != reportFormAnimalEggPeriod.getId()) {
				return true;
			}
			
		}
		return false;
	}
}
