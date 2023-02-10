package com.globits.wl.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.Query;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.Report18Dto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.functiondto.SearchReportForm16Dto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.dto.report.Report16FormDto;
import com.globits.wl.repository.AnimalReportDataRepository;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.ReportForm16Repository;
import com.globits.wl.repository.ReportPeriodRepository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.ImportExportAnimalService;
import com.globits.wl.service.ReportForm16Service;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ReportForm16ServiceImpl extends GenericServiceImpl<ReportForm16, Long> implements ReportForm16Service {
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportForm16ServiceImpl.class);
	@Autowired
	private ReportForm16Repository reportForm16Repository;

	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private ImportExportAnimalService importExportAnimalService;

	@Autowired
	private ReportPeriodRepository reportPeriodRepository;

	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	
	@Autowired
	private AnimalServiceImpl animalServiceImpl;
	
	@Autowired
	private ReportPeriodService reportPeriodService;
	
	@Autowired
	private AnimalReportDataRepository animalReportDataRepository;
	
	@Autowired
	private AnimalReportDataService animalReportDataService;

	@Autowired
	private ResourceLoader resourceLoader;
	
	@Override
	public List<AnimalDto> getListAnimalFromReportForm16BySearch(SearchReportForm16Dto dto) {
		
		String sql = "select DISTINCT new com.globits.wl.dto.AnimalDto(rp.animal) from ReportForm16 rp ";
		String whereClause = " where (1=1) ";
		
		if (dto.getFarmId() != null) {
			whereClause += " and (rp.farm.id =:farmId )";
		}
		sql += whereClause;
		Query q = manager.createQuery(sql, AnimalDto.class);

		if (dto.getFarmId() != null) {
			q.setParameter("farmId", dto.getFarmId());
		}
		
		List<AnimalDto> result = q.getResultList();
		return result;
	}
	
	
	@Override
	public Page<ReportForm16Dto> searchByPage(SearchReportForm16Dto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return null;
			}
		}
		if(searchDto != null) {
			int pageIndex = searchDto.getPageIndex();
			int pageSize = searchDto.getPageSize();

			if (pageIndex > 0)
				pageIndex = pageIndex - 1;
			else
				pageIndex = 0;
			Pageable pageable = new PageRequest(pageIndex, pageSize);

			String sql = "select new com.globits.wl.dto.ReportForm16Dto(rp) from ReportForm16 rp where (1=1)";
			String sqlCount = "select count(rp.id) from ReportForm16 rp  where (1=1)";
			String whereClause = "";
			
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				whereClause += " and (rp.reportPeriod.farm.code like :text or rp.reportPeriod.farm.name like :text )";
			}
			if (searchDto.getYear() != null) {
				whereClause += " and (rp.reportPeriod.year =:year )";
			}
			if (searchDto.getMonth() != null) {
				whereClause += " and (rp.reportPeriod.month =:month )";
			}
			if (searchDto.getDay() != null) {
				whereClause += " and (rp.reportPeriod.date =:day )";
			}
			// lấy theo lớp
			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				whereClause += " and (rp.animal.animalClass like :animalClass )";
			}
			// lấy theo bộ
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				whereClause += " and (rp.animal.ordo like :animalOrdo )";
			}
			// lấy theo họ
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				whereClause += " and (rp.animal.family like :animalFamily )";
			}
			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}
			if (searchDto.getWardId() != null) {
				whereClause += " and (rp.administrativeUnit.id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (rp.district.id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (rp.province.id =:provinceId )";
			}
			if(!isAdmin) {
				whereClause += " and (rp.administrativeUnit.id in (:listWardId)) ";
			}
//			if (searchDto.getWardId() != null) {
//				whereClause += " and (rp.reportPeriod.farm.administrativeUnit.id =:wardId )";
//			} else if (searchDto.getDistrictId() != null) {
//				whereClause += " and (rp.reportPeriod.farm.district.id =:districtId )";
//			} else if (searchDto.getProvinceId() != null) {
//				whereClause += " and (rp.reportPeriod.farm.province.id =:provinceId )";
//			}
//			if(!isAdmin) {
//				whereClause += " and (rp.reportPeriod.farm.administrativeUnit.id in (:listWardId)) ";
//			}
			if (searchDto.getType() != null) {
				whereClause += " and (rp.type =:type )";
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				whereClause += " and (rp.reportPeriod.id =:reportPeriodId )";
			}
			if (searchDto.getFarmId() != null) {
				whereClause += " and (rp.farm.id =:farmId )";
			}

			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}

			if (searchDto.getDateReport() != null) {
				whereClause += " and (rp.dateReport =:dateReport )";
			}

			sql += whereClause;
//			sql += " order by rp.province.name asc, rp.district.name asc, rp.administrativeUnit.name asc, rp.farm.code asc, rp.animal.code asc, rp.dateReport asc ";
			sql += " order by rp.dateReport desc";
			sqlCount += whereClause;

			Query q = manager.createQuery(sql, ReportForm16Dto.class);
			Query qCount = manager.createQuery(sqlCount);

			if (searchDto.getYear() != null) {
				q.setParameter("year", searchDto.getYear());
				qCount.setParameter("year", searchDto.getYear());
			}
			if (searchDto.getMonth() != null) {
				q.setParameter("month", searchDto.getMonth());
				qCount.setParameter("month", searchDto.getMonth());
			}
			if (searchDto.getDay() != null) {
				q.setParameter("day", searchDto.getDay());
				qCount.setParameter("day", searchDto.getDay());
			}
			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}
			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
				qCount.setParameter("wardId", searchDto.getWardId());
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
				qCount.setParameter("districtId", searchDto.getDistrictId());
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
				qCount.setParameter("provinceId", searchDto.getProvinceId());
			}
			if(!isAdmin) {
				q.setParameter("listWardId", listWardId);
				qCount.setParameter("listWardId", listWardId);
			}
			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				q.setParameter("animalClass", "%" + searchDto.getAnimalClass() + "%");
				qCount.setParameter("animalClass", "%" + searchDto.getAnimalClass() + "%");
			}
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				q.setParameter("animalOrdo", "%" + searchDto.getAnimalOrdo() + "%");
				qCount.setParameter("animalOrdo", "%" + searchDto.getAnimalOrdo() + "%");
			}
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				q.setParameter("animalFamily", "%" + searchDto.getAnimalFamily() + "%");
				qCount.setParameter("animalFamily", "%" + searchDto.getAnimalFamily() + "%");
			}
			
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				q.setParameter("text", "%" + searchDto.getTextSearch() + "%");
				qCount.setParameter("text", "%" + searchDto.getTextSearch() + "%");
			}
			if (searchDto.getType() != null) {
				q.setParameter("type", searchDto.getType());
				qCount.setParameter("type", searchDto.getType());
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				q.setParameter("reportPeriodId", searchDto.getReportPeriodId());
				qCount.setParameter("reportPeriodId", searchDto.getReportPeriodId());
			}
			if (searchDto.getFarmId() != null) {
				q.setParameter("farmId", searchDto.getFarmId());
				qCount.setParameter("farmId", searchDto.getFarmId());
			}

			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}

			if (searchDto.getDateReport() != null) {
				q.setParameter("dateReport", searchDto.getDateReport());
				qCount.setParameter("dateReport", searchDto.getDateReport());
			}

			q.setFirstResult((pageIndex) * pageSize);
			q.setMaxResults(pageSize);

			Long numberResult = (Long) qCount.getSingleResult();
			Page<ReportForm16Dto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
			return page;
		}
		return null;
	}

	@Override
	public ReportForm16Dto saveOrUpdate(ReportForm16Dto dto) {
		// TODO Auto-generated method stub
		if (dto != null && dto.getType() != null && dto.getFarm() != null && dto.getFarm().getId() != null
				&& dto.getAnimal() != null && dto.getAnimal().getId() != null) {

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			ReportForm16 entity = null;

			if (dto.getId() != null) {
				entity = reportForm16Repository.getOne(dto.getId());
			}

			if (entity == null || entity.getId() == null) {
				entity = new ReportForm16();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			} else {
				entity.setModifiedBy(currentUserName);
				entity.setModifyDate(currentDate);
			}

			entity.setType(dto.getType());
			entity.setDateReport(dto.getDateReport());

			Farm farm = farmRepository.getOne(dto.getFarm().getId());
			if (farm == null) {
				return null;
			}
			entity.setFarm(farm);

			Animal animal = animalRepository.getOne(dto.getAnimal().getId());
			if (animal == null) {
				return null;
			}
			entity.setAnimal(animal);

			if (dto.getImportExportAnimal() != null && dto.getImportExportAnimal().getId() != null) {
				ImportExportAnimal ie = importExportAnimalService.findById(dto.getImportExportAnimal().getId());
				if (ie != null) {
					entity.setImportExportAnimal(ie);
				}
			}

			if (dto.getReportPeriod() != null && dto.getReportPeriod().getId() != null) {
				ReportPeriod rp = reportPeriodRepository.findOne(dto.getReportPeriod().getId());
				if (rp != null) {
					entity.setReportPeriod(rp);
				}
			}
			Integer total=0;
			Integer male=0;
			Integer feMale=0;
			Integer unGender=0;
			
			entity.setMaleParent(dto.getMaleParent());
				if(dto.getMaleParent()!=null) {
					total+=dto.getMaleParent();
					male+=dto.getMaleParent();
				}
			entity.setFemaleParent(dto.getFemaleParent());
				if(dto.getFemaleParent()!=null) {
					total+=dto.getFemaleParent();
					feMale+=dto.getFemaleParent();
				}
			entity.setMaleGilts(dto.getMaleGilts());
				if(dto.getMaleGilts()!=null) {
					total+=dto.getMaleGilts();
					male+=dto.getMaleGilts();
				}
			entity.setFemaleGilts(dto.getFemaleGilts());
				if(dto.getFemaleGilts()!=null) {
					total+=dto.getFemaleGilts();
					feMale+=dto.getFemaleGilts();
				}
			entity.setChildUnder1YearOld(dto.getChildUnder1YearOld());
				if(dto.getChildUnder1YearOld()!=null) {
					total+=dto.getChildUnder1YearOld();
					unGender+=dto.getChildUnder1YearOld();
				}
			entity.setMaleChildOver1YearOld(dto.getMaleChildOver1YearOld());
				if(dto.getMaleChildOver1YearOld()!=null) {
					total+=dto.getMaleChildOver1YearOld();
					male+=dto.getMaleChildOver1YearOld();
				}
			entity.setFemaleChildOver1YearOld(dto.getFemaleChildOver1YearOld());
				if(dto.getFemaleChildOver1YearOld()!=null) {
					total+=dto.getFemaleChildOver1YearOld();
					feMale+=dto.getFemaleChildOver1YearOld();
				}
			entity.setUnGenderChildOver1YearOld(dto.getUnGenderChildOver1YearOld());
				if(dto.getUnGenderChildOver1YearOld()!=null) {
					total+=dto.getUnGenderChildOver1YearOld();
					unGender+=dto.getUnGenderChildOver1YearOld();
				}
			//tran huu dat them import va export
			entity.setImportMaleParent(dto.getImportMaleParent());
			entity.setImportFemaleParent(dto.getImportFemaleParent());
			entity.setImportMaleGilts(dto.getImportMaleGilts());
			entity.setImportFemaleGilts(dto.getImportFemaleGilts());
			entity.setImportMaleChildOver1YearOld(dto.getImportMaleChildOver1YearOld());
			entity.setImportUnGenderChildOver1YearOld(dto.getImportUnGenderChildOver1YearOld());
			entity.setImportChildUnder1YearOld(dto.getImportChildUnder1YearOld());
			entity.setImportFemaleChildOver1YearOld(dto.getImportFemaleChildOver1YearOld());
			entity.setImportReason(dto.getImportReason());
			
			entity.setExportChildUnder1YearOld(dto.getExportChildUnder1YearOld());
			entity.setExportFemaleChildOver1YearOld(dto.getExportFemaleChildOver1YearOld());
			entity.setExportFemaleGilts(dto.getExportFemaleGilts());
			entity.setExportFemaleParent(dto.getExportFemaleParent());
			entity.setExportMaleChildOver1YearOld(dto.getExportMaleChildOver1YearOld());
			entity.setExportMaleGilts(dto.getExportMaleGilts());
			entity.setExportMaleParent(dto.getExportMaleParent());
			entity.setExportUnGenderChildOver1YearOld(dto.getExportUnGenderChildOver1YearOld());
			entity.setExportReason(dto.getExportReason());
				
			//tran huu dat them import va export
				
			entity.setMaleImport(dto.getMaleImport());
			entity.setFemaleImport(dto.getFemaleImport());
			entity.setUnGenderImport(dto.getUnGenderImport());
			entity.setMaleExport(dto.getMaleExport());
			entity.setFemaleExport(dto.getFemaleExport());
			entity.setUnGenderExport(dto.getUnGenderExport());
			
			entity.setTotal(total);
			entity.setMale(male);
			entity.setFemale(feMale);
			entity.setUnGender(unGender);
			
			entity.setNote(dto.getNote());
			
			if(dto.getAnimal() != null) {
				if(dto.getAnimal().getVnlist() != null) {
					entity.setVnlist(dto.getAnimal().getVnlist());
				}else {
					entity.setVnlist("");
				}
				if(dto.getAnimal().getVnlist06() != null) {
					entity.setVnlist06(dto.getAnimal().getVnlist06());
				}else {
					entity.setVnlist06("");
				}
				if(dto.getAnimal().getCites() != null) {
					entity.setCites(dto.getAnimal().getCites());
				}else {
					entity.setCites("");
				}
				String animalGroupByYear = "";
				animalGroupByYear = animalServiceImpl.updateAnimalGroup(dto.getAnimal());
				if(animalGroupByYear != null && animalGroupByYear.length() >0) {
					entity.setAnimalGroup(animalGroupByYear);
				}
			}
			
			entity = reportForm16Repository.save(entity);
			if (entity != null && entity.getId() != null) {
				return new ReportForm16Dto(entity);
			}

		}
		return null;
	}

	@Override
	public ReportForm16Dto getById(Long id) {
		// TODO Auto-generated method stub
		if (id != null && id > 0) {
			ReportForm16 result = reportForm16Repository.getOne(id);
			if (result != null) {
				return new ReportForm16Dto(result);
			}
		}
		return null;
	}

	@Override
	public List<ReportForm16Dto> createByImportExportAnimal(ImportExportAnimal ie) {
		if (ie != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(ie.getDateIssue());
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
			String[] dates = simpleDateFormat.format(ie.getDateIssue()).split("/");
			Integer year = Integer.parseInt(dates[2]);
			Integer month = Integer.parseInt(dates[1]);
			Integer dayOfMonth = Integer.parseInt(dates[0]);

//			List<ReportPeriod> reportPeriods = reportPeriodRepository.findReportPeriodByYearMonthDate(calendar.YEAR, calendar.MONTH+1, calendar.DAY_OF_MONTH,  ie.getFarm().getId(), 1);
			List<ReportPeriod> reportPeriods = reportPeriodRepository.findReportPeriodByYearMonthDate(year, month,
					dayOfMonth, ie.getFarm().getId(), 1);
			ReportPeriodDto period = null;
			if (reportPeriods != null && reportPeriods.size() > 0) {
				period = new ReportPeriodDto(reportPeriods.get(0));
			} else {
				ReportPeriod reportPeriod = new ReportPeriod();
				reportPeriod.setFarm(ie.getFarm());
				reportPeriod.setYear(year);
				reportPeriod.setMonth(month);
				reportPeriod.setDate(dayOfMonth);
				reportPeriod.setType(1);
				reportPeriod = reportPeriodRepository.save(reportPeriod);
				period = new ReportPeriodDto(reportPeriod);
			}

			List<ReportForm16Dto> ret = new ArrayList<ReportForm16Dto>();
			List<Report16FormDto> listReport16FormDto = importExportAnimalService.getReport16FormDto(null,
					ie.getDateIssue(), ie.getFarm().getId(), ie.getAnimal().getId());
			if (listReport16FormDto != null && listReport16FormDto.size() > 0) {
				for (Report16FormDto report16FormDto : listReport16FormDto) {
					com.globits.wl.dto.ReportForm16Dto re = new ReportForm16Dto();

					ImportExportAnimalDto importExportAnimalDto = new ImportExportAnimalDto();
					importExportAnimalDto.setId(ie.getId());
					re.setImportExportAnimal(importExportAnimalDto);

					FarmDto farmDto = new FarmDto();
					farmDto.setId(ie.getFarm().getId());
					re.setFarm(farmDto);

					AnimalDto animalDto = new AnimalDto();
					animalDto.setId(ie.getAnimal().getId());
					re.setAnimal(animalDto);

					re.setReportPeriod(period);

					re.setType(1);
					re.setDateReport(ie.getDateIssue());

					re.setTotal(report16FormDto.getTotal().intValue());
					re.setMale(report16FormDto.getTotalMale().intValue());
					re.setFemale(report16FormDto.getTotalFeMale().intValue());
					re.setUnGender(report16FormDto.getTotalUnGen().intValue());

					re.setMaleParent(report16FormDto.getParentMale().intValue());
					re.setFemaleParent(report16FormDto.getParentFeMale().intValue());

					re.setMaleGilts(report16FormDto.getGiltsMale().intValue());
					re.setFemaleGilts(report16FormDto.getGiltsFeMale().intValue());

					re.setChildUnder1YearOld(report16FormDto.getTotalUnder1YO().intValue());

					re.setMaleChildOver1YearOld(report16FormDto.getOver1YOMale().intValue());
					re.setFemaleChildOver1YearOld(report16FormDto.getOver1YOFeMale().intValue());
					re.setUnGenderChildOver1YearOld(report16FormDto.getOver1YOUnGen().intValue());

					re.setMaleImport(report16FormDto.getImportMale().intValue());
					re.setFemaleImport(report16FormDto.getImportFeMale().intValue());
					re.setUnGenderImport(report16FormDto.getImportUnGen().intValue());
					re.setMaleExport(report16FormDto.getExportMale().intValue());
					re.setFemaleExport(report16FormDto.getExportFeMale().intValue());
					re.setUnGenderExport(report16FormDto.getExportUnGen().intValue());
					re.setNote("Kết xuất từ nhập - xuất đàn");
					ReportForm16Dto retDto = this.saveOrUpdate(re);
					ret.add(retDto);
				}
			}
			return ret;
		}
		return null;
	}

	@Override
	public List<ReportPeriod> getListImportDataReportForm16(SearchReportForm16Dto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return null;
			}
		}
		String sql = "select rp from  ReportPeriod rp where (1=1) ";
		String whereClause = "";

		if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
			whereClause += " and (rp.farm.code LIKE :text OR rp.farm.name LIKE :text )";
		}
		if (searchDto.getYear() != null) {
			whereClause += " and (rp.year = :year)";
		}
		if (searchDto.getMonth() != null) {
			whereClause += " and (rp.month = :month)";
		}
		if (searchDto.getDay() != null) {
			whereClause += " and (rp.date = :day)";
		}

		if (searchDto.getWardId() != null) {
			whereClause += " and (rp.farm.administrativeUnit.id =:wardId )";
		} else if (searchDto.getDistrictId() != null) {
			whereClause += " and (rp.farm.administrativeUnit.parent.id =:districtId )";
		} else if (searchDto.getProvinceId() != null) {
			whereClause += " and (rp.farm.administrativeUnit.parent.parent.id =:provinceId )";
		}
		if(!isAdmin) {
			whereClause += " and (rp.farm.administrativeUnit.id in (:listWardId)) ";
		}
		sql += whereClause;
		sql += " order by rp.farm.code asc, rp.farm.name asc ";

		Query q = manager.createQuery(sql, ReportPeriod.class);

		if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
			q.setParameter("text", searchDto.getTextSearch());
		}
		if (searchDto.getYear() != null) {
			q.setParameter("year", searchDto.getYear());
		}
		if (searchDto.getMonth() != null) {
			q.setParameter("month", searchDto.getMonth());
		}
		if (searchDto.getDay() != null) {
			q.setParameter("day", searchDto.getDay());
		}

		if (searchDto.getWardId() != null) {
			q.setParameter("wardId", searchDto.getWardId());
		} else if (searchDto.getDistrictId() != null) {
			q.setParameter("districtId", searchDto.getDistrictId());
		} else if (searchDto.getProvinceId() != null) {
			q.setParameter("provinceId", searchDto.getProvinceId());
		}
		if(!isAdmin) {
			q.setParameter("listWardId", listWardId);
			whereClause += " and (rp.farm.administrativeUnit.id in (:listWardId)) ";
		}
		List<ReportPeriod> listReportForm16 = q.getResultList();
		return listReportForm16;
	}

	@Override
	public List<ReportForm16> getListReportForm16(SearchReportForm16Dto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return null;
			}
		}

		if((searchDto.getProvinceId() != null && searchDto.getYear() != null && (searchDto.getDistrictId() != null || searchDto.getWardId() != null)) || searchDto.getTextSearch() != null && searchDto.getYear() != null)  {
			String sql = "select rp from ReportForm16 rp where (1=1)";
			String sqlCount = "select count(rp.id) from ReportForm16 rp  where (1=1)";
			String whereClause = "";

			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				whereClause += " and (rp.reportPeriod.farm.code like :text or rp.reportPeriod.farm.name like :text )";
			}
			if (searchDto.getYear() != null) {
				whereClause += " and (rp.reportPeriod.year =:year )";
			}
			if (searchDto.getMonth() != null) {
				whereClause += " and (rp.reportPeriod.month =:month )";
			}
			if (searchDto.getDay() != null) {
				whereClause += " and (rp.reportPeriod.date =:day )";
			}
			// lấy theo lớp
			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				whereClause += " and (rp.animal.animalClass like :animalClass )";
			}
			// lấy theo bộ
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				whereClause += " and (rp.animal.ordo like :animalOrdo )";
			}
			// lấy theo họ
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				whereClause += " and (rp.animal.family like :animalFamily )";
			}
			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}
			if (searchDto.getWardId() != null) {
				whereClause += " and (rp.reportPeriod.farm.administrativeUnit.id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (rp.reportPeriod.farm.administrativeUnit.parent.id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (rp.reportPeriod.farm.administrativeUnit.parent.parent.id =:provinceId )";
			}
			if(!isAdmin) {
				whereClause += " and (rp.reportPeriod.farm.administrativeUnit.id in (:listWardId)) ";
			}
			if (searchDto.getType() != null) {
				whereClause += " and (rp.type =:type )";
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				whereClause += " and (rp.reportPeriod.id =:reportPeriodId )";
			}
			if (searchDto.getFarmId() != null) {
				whereClause += " and (rp.farm.id =:farmId )";
			}

			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}

			if (searchDto.getDateReport() != null) {
				whereClause += " and (rp.dateReport =:dateReport )";
			}

			sql += whereClause;
			sql += " order by rp.farm.code asc, rp.animal.code asc, rp.dateReport asc ";
			sqlCount += whereClause;

			Query q = manager.createQuery(sql, ReportForm16.class);
			Query qCount = manager.createQuery(sqlCount);

			if (searchDto.getYear() != null) {
				q.setParameter("year", searchDto.getYear());
				qCount.setParameter("year", searchDto.getYear());
			}
			if (searchDto.getMonth() != null) {
				q.setParameter("month", searchDto.getMonth());
				qCount.setParameter("month", searchDto.getMonth());
			}
			if (searchDto.getDay() != null) {
				q.setParameter("day", searchDto.getDay());
				qCount.setParameter("day", searchDto.getDay());
			}
			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}
			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
				qCount.setParameter("wardId", searchDto.getWardId());
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
				qCount.setParameter("districtId", searchDto.getDistrictId());
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
				qCount.setParameter("provinceId", searchDto.getProvinceId());
			}

			if(!isAdmin) {
				q.setParameter("listWardId", listWardId);
				qCount.setParameter("listWardId", listWardId);
			}

			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				q.setParameter("animalClass", "%" + searchDto.getAnimalClass() + "%");
				qCount.setParameter("animalClass", "%" + searchDto.getAnimalClass() + "%");
			}
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				q.setParameter("animalOrdo", "%" + searchDto.getAnimalOrdo() + "%");
				qCount.setParameter("animalOrdo", "%" + searchDto.getAnimalOrdo() + "%");
			}
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				q.setParameter("animalFamily", "%" + searchDto.getAnimalFamily() + "%");
				qCount.setParameter("animalFamily", "%" + searchDto.getAnimalFamily() + "%");
			}
			
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				q.setParameter("text", "%" + searchDto.getTextSearch() + "%");
				qCount.setParameter("text", "%" + searchDto.getTextSearch() + "%");
			}
			if (searchDto.getType() != null) {
				q.setParameter("type", searchDto.getType());
				qCount.setParameter("type", searchDto.getType());
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				q.setParameter("reportPeriodId", searchDto.getReportPeriodId());
				qCount.setParameter("reportPeriodId", searchDto.getReportPeriodId());
			}
			if (searchDto.getFarmId() != null) {
				q.setParameter("farmId", searchDto.getFarmId());
				qCount.setParameter("farmId", searchDto.getFarmId());
			}

			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}

			if (searchDto.getDateReport() != null) {
				q.setParameter("dateReport", searchDto.getDateReport());
				qCount.setParameter("dateReport", searchDto.getDateReport());
			}
			List<ReportForm16> result = q.getResultList();
			if(result != null) {
				return result;
			}
		}
		return null;
	}

	@Override
	public List<ReportForm16> getListBy(Long farmId, Long animalId, Integer year) {
		/*Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return null;
			}
		}*/

		String sql = "select rp from ReportForm16 rp where (1=1)";
		String sqlCount = "select count(rp.id) from ReportForm16 rp  where (1=1)";
		String whereClause = "";

		if (year != null) {
			whereClause += " and (rp.reportPeriod.year =:year )";
		}
		if (farmId != null) {
			whereClause += " and (rp.farm.id =:farmId )";
		}

		if (animalId != null) {
			whereClause += " and (rp.animal.id =:animalId )";
		}

		sql += whereClause;
		sql += " order by rp.farm.code asc, rp.animal.code asc, rp.dateReport asc ";
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, ReportForm16.class);
		Query qCount = manager.createQuery(sqlCount);

		if (year != null) {
			q.setParameter("year", year);
			qCount.setParameter("year", year);
		}
		if (farmId != null) {
			q.setParameter("farmId", farmId);
			qCount.setParameter("farmId", farmId);
		}

		if (animalId != null) {
			q.setParameter("animalId", animalId);
			qCount.setParameter("animalId", animalId);
		}

		List<ReportForm16> result = q.getResultList();
		if(result != null) {
			return result;
		}
		
		return null;
	}

	@Override
	public List<ReportForm16Dto> getListReportForm16Dto(SearchReportForm16Dto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return null;
			}
		}
		if(searchDto != null) {
			String sql = "select new com.globits.wl.dto.ReportForm16Dto(rp) from ReportForm16 rp where (1=1)";
			String sqlCount = "select count(rp.id) from ReportForm16 rp  where (1=1)";
			String whereClause = "";
			
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				whereClause += " and (rp.reportPeriod.farm.code like :text or rp.reportPeriod.farm.name like :text )";
			}
			if (searchDto.getYear() != null) {
				whereClause += " and (rp.reportPeriod.year =:year )";
			}
			if (searchDto.getMonth() != null) {
				whereClause += " and (rp.reportPeriod.month =:month )";
			}
			if (searchDto.getDay() != null) {
				whereClause += " and (rp.reportPeriod.date =:day )";
			}
			// lấy theo lớp
			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				whereClause += " and (rp.animal.animalClass like :animalClass )";
			}
			// lấy theo bộ
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				whereClause += " and (rp.animal.ordo like :animalOrdo )";
			}
			// lấy theo họ
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				whereClause += " and (rp.animal.family like :animalFamily )";
			}
			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}
			if (searchDto.getWardId() != null) {
				whereClause += " and (rp.administrativeUnit.id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (rp.district.id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (rp.province.id =:provinceId )";
			}
			if(!isAdmin) {
				whereClause += " and (rp.administrativeUnit.id in (:listWardId)) ";
			}
//			if (searchDto.getWardId() != null) {
//				whereClause += " and (rp.reportPeriod.farm.administrativeUnit.id =:wardId )";
//			} else if (searchDto.getDistrictId() != null) {
//				whereClause += " and (rp.reportPeriod.farm.district.id =:districtId )";
//			} else if (searchDto.getProvinceId() != null) {
//				whereClause += " and (rp.reportPeriod.farm.province.id =:provinceId )";
//			}
//			if(!isAdmin) {
//				whereClause += " and (rp.reportPeriod.farm.administrativeUnit.id in (:listWardId)) ";
//			}
			if (searchDto.getType() != null) {
				whereClause += " and (rp.type =:type )";
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				whereClause += " and (rp.reportPeriod.id =:reportPeriodId )";
			}
			if (searchDto.getFarmId() != null) {
				whereClause += " and (rp.farm.id =:farmId )";
			}

			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}

			if (searchDto.getDateReport() != null) {
				whereClause += " and (rp.dateReport =:dateReport )";
			}

			sql += whereClause;
			sql += " order by rp.province.name asc, rp.district.name asc, rp.administrativeUnit.name asc, rp.farm.code asc, rp.animal.code asc, rp.dateReport asc ";
			sqlCount += whereClause;

			Query q = manager.createQuery(sql, ReportForm16Dto.class);
			Query qCount = manager.createQuery(sqlCount);

			if (searchDto.getYear() != null) {
				q.setParameter("year", searchDto.getYear());
				qCount.setParameter("year", searchDto.getYear());
			}
			if (searchDto.getMonth() != null) {
				q.setParameter("month", searchDto.getMonth());
				qCount.setParameter("month", searchDto.getMonth());
			}
			if (searchDto.getDay() != null) {
				q.setParameter("day", searchDto.getDay());
				qCount.setParameter("day", searchDto.getDay());
			}
			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}
			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
				qCount.setParameter("wardId", searchDto.getWardId());
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
				qCount.setParameter("districtId", searchDto.getDistrictId());
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
				qCount.setParameter("provinceId", searchDto.getProvinceId());
			}
			if(!isAdmin) {
				q.setParameter("listWardId", listWardId);
				qCount.setParameter("listWardId", listWardId);
			}
			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				q.setParameter("animalClass", "%" + searchDto.getAnimalClass() + "%");
				qCount.setParameter("animalClass", "%" + searchDto.getAnimalClass() + "%");
			}
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				q.setParameter("animalOrdo", "%" + searchDto.getAnimalOrdo() + "%");
				qCount.setParameter("animalOrdo", "%" + searchDto.getAnimalOrdo() + "%");
			}
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				q.setParameter("animalFamily", "%" + searchDto.getAnimalFamily() + "%");
				qCount.setParameter("animalFamily", "%" + searchDto.getAnimalFamily() + "%");
			}
			
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				q.setParameter("text", "%" + searchDto.getTextSearch() + "%");
				qCount.setParameter("text", "%" + searchDto.getTextSearch() + "%");
			}
			if (searchDto.getType() != null) {
				q.setParameter("type", searchDto.getType());
				qCount.setParameter("type", searchDto.getType());
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				q.setParameter("reportPeriodId", searchDto.getReportPeriodId());
				qCount.setParameter("reportPeriodId", searchDto.getReportPeriodId());
			}
			if (searchDto.getFarmId() != null) {
				q.setParameter("farmId", searchDto.getFarmId());
				qCount.setParameter("farmId", searchDto.getFarmId());
			}

			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}

			if (searchDto.getDateReport() != null) {
				q.setParameter("dateReport", searchDto.getDateReport());
				qCount.setParameter("dateReport", searchDto.getDateReport());
			}
			List<ReportForm16Dto> listResult = q.getResultList();
			return listResult;
		}
		return null;
	}
	
	@Override
	public List<ReportForm16Dto> getListReportForm16DtoDetail(SearchReportForm16Dto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return null;
			}
		}
		if(searchDto != null) {
			String sql = "SELECT distinct t.id as form16Id" + 
					"      ,t.child_under_1year_old as childUnder1YearOld" + 
					"      ,t.date_report as dateReport" + 
					"      ,t.female" + 
					"      ,t.female_child_over_1year_old as femaleChildOver1YearOld" + 
					"      ,t.female_export as femaleExport" + 
					"      ,t.female_gilts as femaleGilts" + 
					"      ,t.female_import as femaleImport" + 
					"      ,t.female_parent as femaleParent" + 
					"      ,t.male" + 
					"      ,t.male_child_over_1year_old as maleChildOver1YearOld" + 
					"      ,t.male_export as maleExport" + 
					"      ,t.male_gilts as maleGilts" + 
					"      ,t.male_import as maleImport" + 
					"      ,t.male_parent as maleParent" + 
					"      ,t.note" + 
					"      ,t.total" + 
					"      ,t.type" + 
					"      ,t.un_gender as unGender" + 
					"      ,t.un_gender_child_over_1year_old as unGenderChildOver1YearOld" + 
					"      ,t.un_gender_export as unGenderExport" + 
					"      ,t.un_gender_import as unGenderImport" + 
					"      ,t.animal_id as animalId" + 
					"      ,t.farm_id as farmId" + 
					"      ,t.report_period_id as periodId" + 
					"      ,t.administrative_unit_id as wardId" + 
					"      ,t.district_id as disId" + 
					"      ,t.province_id as provId" + 
					"      ,t.is_last_record as isLastRecord" + 
					"      ,t.animal_group as animalGroup" + 
					"      ,t.cites" + 
					"      ,t.vnlist" + 
					"      ,t.vnlist06" + 
					"      ,t.export_child_under_1_year_old as exportChildUnder1YearOld" + 
					"      ,t.export_female_chid_over_1_year_old as exportFemaleChildOver1YearOld" + 
					"      ,t.export_female_gilts as exportFemaleGilts" + 
					"      ,t.export_female_parent as exportFemaleParent" + 
					"      ,t.export_male_child_over_1_year_old as exportMaleChildOver1YearOld" + 
					"      ,t.export_male_gilts as exportMaleGilts" + 
					"      ,t.export_male_parent as exportMaleParent" + 
					"      ,t.export_reason as exportReason" + 
					"      ,t.export_ungender_child_over_1_year_old as exportUnGenderChildOver1YearOld" + 
					"      ,t.import_child_under_1_year_old as importChildUnder1YearOld" + 
					"      ,t.import_female_child_over_year_old as importFemaleChildOver1YearOld" + 
					"      ,t.import_female_gilts as importFemaleGilts " + 
					"      ,t.import_female_parent as importFemaleParent" + 
					"      ,t.import_male_child_over_1_year_old as importMaleChildOver1YearOld" + 
					"      ,t.import_male_gilts as importMaleGilts" + 
					"      ,t.import_male_parent as importMaleParent" + 
					"      ,t.import_reason as importReason" + 
					"      ,t.import_ungender_child_over_1_year_old as importUnGenderChildOver1YearOld" + 
					"      ,t.total_export as totalExport" + 
					"      ,t.total_import as totalImport" + 
					"      ,t.original_id as originalId" + 
					"      ,t.export_female_child_under_1year_old as exportFemaleChildUnder1YearOld" + 
					"      ,t.export_male_child_under_1year_old as exportMaleChildUnder1YearOld" + 
					"      ,t.export_unGender_gilts as exportUnGenderGilts" + 
					"      ,t.export_unGender_parent as exportUnGenderParent" + 
					"      ,t.female_child_under_1year_old as femaleChildUnder1YearOld" + 
					"      ,t.import_female_child_under_1year_old as importFemaleChildUnder1YearOld" + 
					"      ,t.import_male_child_under_1year_old as importMaleChildUnder1YearOld" + 
					"      ,t.import_unGender_gilts as importUnGenderGilts" + 
					"      ,t.import_unGender_parent as importUnGenderParent" + 
					"      ,t.male_child_under_1year_old as maleChildUnder1YearOld" + 
					"      ,t.unGender_gilts as unGenderGilts" + 
					"      ,t.unGender_parent as unGenderParent" + 
					"  FROM tbl_report_form16 t "
					+ " inner join tbl_farm f on f.id = t.farm_id ";
					
			//String sqlCount = "select count(rp.id) from ReportForm16 rp  where (1=1)";
			String whereClause = "";
			
			if (searchDto.getYear() != null) {
				whereClause += " and (YEAR(t.date_report) =:year )";
			}
			if (searchDto.getMonth() != null) {
				whereClause += " and (MONTH(t.date_report) =:month )";
			}
			if (searchDto.getDay() != null) {
				whereClause += " and (DAY(t.date_report) =:day )";
			}
			if (searchDto.getWardId() != null) {
				whereClause += " and (t.administrative_unit_id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (t.district_id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (t.province_id =:provinceId )";
			}
			if(searchDto.getTextSearch()!=null) {
				whereClause += " and (f.code LIKE :text OR f.name LIKE :text )";
			}
			sql += whereClause;
			sql += " order by t.farm_id asc ";
			//sqlCount += whereClause;

			Query q = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(ReportForm16Dto.class));
			//Query q = manager.createQuery(sql, ReportForm16Dto.class);
			//Query qCount = manager.createQuery(sqlCount);

			if (searchDto.getYear() != null) {
				q.setParameter("year", searchDto.getYear());
				//qCount.setParameter("year", searchDto.getYear());
			}
			if (searchDto.getMonth() != null) {
				q.setParameter("month", searchDto.getMonth());
				//qCount.setParameter("year", searchDto.getYear());
			}
			if (searchDto.getDay() != null) {
				q.setParameter("day", searchDto.getDay());
				//qCount.setParameter("year", searchDto.getYear());
			}
			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
				//qCount.setParameter("wardId", searchDto.getWardId());
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
				//qCount.setParameter("districtId", searchDto.getDistrictId());
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
				//qCount.setParameter("provinceId", searchDto.getProvinceId());
			}
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				q.setParameter("text", "%" + searchDto.getTextSearch() + "%");
			}

			@SuppressWarnings("unchecked")
			List<ReportForm16Dto> listResult = (List<ReportForm16Dto>)q.getResultList();
			return listResult;
		}
		return null;
	}
	
	@Override
	public List<ReportForm16Dto> getListReportForm16DtoNativeQueryForExport(SearchReportForm16Dto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return null;
			}
		}
		String whereClause = "";
		
		if (searchDto.getYear() != null) {
			whereClause += " and (YEAR(t.date_report) =:year )";
		}
		if (searchDto.getWardId() != null) {
			whereClause += " and (t.administrative_unit_id =:wardId )";
		} else if (searchDto.getDistrictId() != null) {
			whereClause += " and (t.district_id =:districtId )";
		} else if (searchDto.getProvinceId() != null) {
			whereClause += " and (t.province_id =:provinceId )";
		}
//		if(searchDto.getTextSearch()!=null) {
//			whereClause += " and (f.code LIKE :text OR f.name LIKE :text )";
//		}
		if(searchDto != null) {
			String sql = "SELECT "
					+ " form16Id, childUnder1YearOld, dateReport, female, femaleChildOver1YearOld, femaleExport, femaleGilts, femaleImport, femaleParent, male, maleChildOver1YearOld, maleExport, maleGilts, maleImport, maleParent, note, total, type, unGender, unGenderChildOver1YearOld, unGenderExport, unGenderImport, animalId, farmId, periodId, wardId, disId, provId, isLastRecord, animalGroup, cites, vnlist, vnlist06, exportChildUnder1YearOld, exportFemaleChildOver1YearOld, exportFemaleGilts, exportFemaleParent, exportMaleChildOver1YearOld, exportMaleGilts, exportMaleParent, exportReason, exportUnGenderChildOver1YearOld, importChildUnder1YearOld, importFemaleChildOver1YearOld, importFemaleGilts, importFemaleParent, importMaleChildOver1YearOld, importMaleGilts, importMaleParent, importReason, importUnGenderChildOver1YearOld, totalExport, totalImport, originalId, exportFemaleChildUnder1YearOld, exportMaleChildUnder1YearOld, exportUnGenderGilts, exportUnGenderParent, femaleChildUnder1YearOld, importFemaleChildUnder1YearOld, importMaleChildUnder1YearOld, importUnGenderGilts, importUnGenderParent, maleChildUnder1YearOld, unGenderGilts, unGenderParent"
					+ "  "
					+ "  FROM (SELECT (ROW_NUMBER() OVER (PARTITION BY t.farm_id, t.animal_id ORDER BY t.date_report DESC,t.modify_date desc, t.create_date desc)) AS rowNumber"+
					"      ,t.id as form16Id" + 
					"      ,t.child_under_1year_old as childUnder1YearOld" + 
					"      ,t.date_report as dateReport" + 
					"      ,t.female" + 
					"      ,t.female_child_over_1year_old as femaleChildOver1YearOld" + 
					"      ,t.female_export as femaleExport" + 
					"      ,t.female_gilts as femaleGilts" + 
					"      ,t.female_import as femaleImport" + 
					"      ,t.female_parent as femaleParent" + 
					"      ,t.male" + 
					"      ,t.male_child_over_1year_old as maleChildOver1YearOld" + 
					"      ,t.male_export as maleExport" + 
					"      ,t.male_gilts as maleGilts" + 
					"      ,t.male_import as maleImport" + 
					"      ,t.male_parent as maleParent" + 
					"      ,t.note" + 
					"      ,t.total" + 
					"      ,t.type" + 
					"      ,t.un_gender as unGender" + 
					"      ,t.un_gender_child_over_1year_old as unGenderChildOver1YearOld" + 
					"      ,t.un_gender_export as unGenderExport" + 
					"      ,t.un_gender_import as unGenderImport" + 
					"      ,t.animal_id as animalId" + 
					"      ,t.farm_id as farmId" + 
					"      ,t.report_period_id as periodId" + 
					"      ,t.administrative_unit_id as wardId" + 
					"      ,t.district_id as disId" + 
					"      ,t.province_id as provId" + 
					"      ,t.is_last_record as isLastRecord" + 
					"      ,t.animal_group as animalGroup" + 
					"      ,t.cites" + 
					"      ,t.vnlist" + 
					"      ,t.vnlist06" + 
					"      ,t.export_child_under_1_year_old as exportChildUnder1YearOld" + 
					"      ,t.export_female_chid_over_1_year_old as exportFemaleChildOver1YearOld" + 
					"      ,t.export_female_gilts as exportFemaleGilts" + 
					"      ,t.export_female_parent as exportFemaleParent" + 
					"      ,t.export_male_child_over_1_year_old as exportMaleChildOver1YearOld" + 
					"      ,t.export_male_gilts as exportMaleGilts" + 
					"      ,t.export_male_parent as exportMaleParent" + 
					"      ,t.export_reason as exportReason" + 
					"      ,t.export_ungender_child_over_1_year_old as exportUnGenderChildOver1YearOld" + 
					"      ,t.import_child_under_1_year_old as importChildUnder1YearOld" + 
					"      ,t.import_female_child_over_year_old as importFemaleChildOver1YearOld" + 
					"      ,t.import_female_gilts as importFemaleGilts " + 
					"      ,t.import_female_parent as importFemaleParent" + 
					"      ,t.import_male_child_over_1_year_old as importMaleChildOver1YearOld" + 
					"      ,t.import_male_gilts as importMaleGilts" + 
					"      ,t.import_male_parent as importMaleParent" + 
					"      ,t.import_reason as importReason" + 
					"      ,t.import_ungender_child_over_1_year_old as importUnGenderChildOver1YearOld" + 
					"      ,t.total_export as totalExport" + 
					"      ,t.total_import as totalImport" + 
					"      ,t.original_id as originalId" + 
					"      ,t.export_female_child_under_1year_old as exportFemaleChildUnder1YearOld" + 
					"      ,t.export_male_child_under_1year_old as exportMaleChildUnder1YearOld" + 
					"      ,t.export_unGender_gilts as exportUnGenderGilts" + 
					"      ,t.export_unGender_parent as exportUnGenderParent" + 
					"      ,t.female_child_under_1year_old as femaleChildUnder1YearOld" + 
					"      ,t.import_female_child_under_1year_old as importFemaleChildUnder1YearOld" + 
					"      ,t.import_male_child_under_1year_old as importMaleChildUnder1YearOld" + 
					"      ,t.import_unGender_gilts as importUnGenderGilts" + 
					"      ,t.import_unGender_parent as importUnGenderParent" + 
					"      ,t.male_child_under_1year_old as maleChildUnder1YearOld" + 
					"      ,t.unGender_gilts as unGenderGilts" + 
					"      ,t.unGender_parent as unGenderParent" + 
					"  FROM tbl_report_form16 t inner join tbl_farm f on t.farm_id = f.id "
					+ "WHERE 1=1 "+whereClause+") as tb WHERE tb.rowNumber=1";
			//String sqlCount = "select count(rp.id) from ReportForm16 rp  where (1=1)";
			
			//sql += whereClause;
			//sql += " order by t.farm_id asc ";
			//sqlCount += whereClause;

			Query q = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(ReportForm16Dto.class));
			//Query q = manager.createQuery(sql, ReportForm16Dto.class);
			//Query qCount = manager.createQuery(sqlCount);

			if (searchDto.getYear() != null) {
				q.setParameter("year", searchDto.getYear());
				//qCount.setParameter("year", searchDto.getYear());
			}
			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
				//qCount.setParameter("wardId", searchDto.getWardId());
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
				//qCount.setParameter("districtId", searchDto.getDistrictId());
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
				//qCount.setParameter("provinceId", searchDto.getProvinceId());
			}
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				q.setParameter("text", "%" + searchDto.getTextSearch() + "%");
			}

			@SuppressWarnings("unchecked")
			List<ReportForm16Dto> listResult = (List<ReportForm16Dto>)q.getResultList();
			return listResult;
		}
		return null;
	}

	@Override
	public List<ReportForm16Dto> getListReportForm16ByFarmId(long farmId) {
		Farm farmEntity= farmRepository.findOne(farmId);
		List<ReportForm16Dto> listReportDto= new ArrayList<ReportForm16Dto>();
		List<ReportForm16> listReportEntity= new ArrayList<ReportForm16>();
		if(farmEntity!=null) {
			listReportEntity= reportForm16Repository.findAllByFarm(farmEntity);
		}
		if(listReportEntity.size()>0 && listReportEntity!=null) {
			for(ReportForm16 entity: listReportEntity) {
				ReportForm16Dto dto= new ReportForm16Dto(entity);
				//tran huu dat check editable
				if(dto.getDateReport()!=null) {
					try {
						dto.setEditable(WLDateTimeUtil.checkQuarter(dto.getDateReport()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						dto.setEditable(true);
					}
				}
				listReportDto.add(dto);
			}
		}
		return listReportDto;
	}
	
	@Override
	public List<ReportForm16Dto> getListReportForm16ByFarmIdAndAnimalId(long farmId, long animalId) {
	
		List<ReportForm16Dto> listReportDto= new ArrayList<ReportForm16Dto>();
		List<ReportForm16> listReportEntity= new ArrayList<ReportForm16>();
		if(farmId>0 && animalId > 0) {
			listReportEntity= reportForm16Repository.findAllByFarmAndSortDate(farmId, animalId);
		}
		if(listReportEntity.size()>0 && listReportEntity!=null) {
			for(ReportForm16 entity: listReportEntity) {
				ReportForm16Dto dto= new ReportForm16Dto(entity);
				//tran huu dat check editable
				if(dto.getDateReport()!=null) {
					try {
						dto.setEditable(WLDateTimeUtil.checkQuarter(dto.getDateReport()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						//e.printStackTrace();
						dto.setEditable(true);
					}
				}
				listReportDto.add(dto);
			}
		}
		return listReportDto;
	}

	@Override
	public List<ReportForm16Dto> saveList(List<ReportForm16Dto> listDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		List<Long> animalIds = new ArrayList<Long>();
		
		List<ReportForm16Dto> listDtoSaved= new ArrayList<>();
		for(ReportForm16Dto dto: listDto) {
			//tran huu dat check cac form 16 nam ngoai quy ko duoc sua start
			boolean isUpdate= true;
			int quy=currentDate.getMonthOfYear()/4;// xac dinh quy hien tai
			if(dto.getDateReport()!=null) {// check item da ton tai chua
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(dto.getDateReport());
				int monthOfDateReort= cal1.get(Calendar.MONTH)+1;
				int yearOfDateReport= cal1.get(Calendar.YEAR);
				switch (quy) {
					case 0: //quy 1
						if(monthOfDateReort>=1 && monthOfDateReort<=3 && yearOfDateReport==currentDate.getYear()) {
							isUpdate=true;
						}else {
							isUpdate= false;
						}
						break;
					case 1: //quy 2
						if(monthOfDateReort>=4 && monthOfDateReort<=6 && yearOfDateReport==currentDate.getYear()) {
							isUpdate=true;
						}else {
							isUpdate= false;
						}
						break;
					case 2: //quy 3
						if(monthOfDateReort>=7 && monthOfDateReort<=9 && yearOfDateReport==currentDate.getYear()) {
							isUpdate=true;
						}else {
							isUpdate= false;
						}
						break;
					case 3: //quy 4
						if(monthOfDateReort>=10 && monthOfDateReort<=12 && yearOfDateReport==currentDate.getYear()) {
							isUpdate=true;
						}else {
							isUpdate= false;
						}
						break;
				}
			}
			//tran huu dat check cac form 16 nam ngoai quy ko duoc sua end
			
			isUpdate = true; // tạm thời bỏ theo quý
			if(isUpdate==true) {//neu duoc phep them hoac cap nhat
				if(dto.getDateReport()!=null) {
					Calendar cal = Calendar.getInstance();
					cal.setTime(dto.getDateReport());
					int day= cal.get(Calendar.DATE);
					int month=cal.get(Calendar.MONTH)+1;
					int year= cal.get(Calendar.YEAR);
					List<ReportPeriod> listReportPeriod= new ArrayList<>();
					Farm  farm= null;
					ReportPeriod reportPeriod= null;// lay bao cao dau tien.
					if(dto.getFarm()!=null && dto.getFarm().getId()!=null) {
						farm= farmRepository.findOne(dto.getFarm().getId());
						if(farm!=null) {
							listReportPeriod= reportPeriodRepository.findByDayMonthYearType(day, month, year, 1, farm.getId());
						}
					}
					if(listReportPeriod.size()>0) {// neu lon hon 0 la ton tai bao cao roi
						reportPeriod= listReportPeriod.get(0);
					}
					ReportForm16 entity = null;
					if (dto.getId() != null) {
						entity = reportForm16Repository.findOne(dto.getId());
					}

					if (entity == null || entity.getId() == null) {
						entity = new ReportForm16();
						entity.setCreateDate(currentDate);
						entity.setCreatedBy(currentUserName);
					} else {
						entity.setModifiedBy(currentUserName);
						entity.setModifyDate(currentDate);
					}

					entity.setType(dto.getType());
					entity.setDateReport(dto.getDateReport());

					farm = farmRepository.findOne(dto.getFarm().getId());
					if (farm != null) {
						entity.setFarm(farm);
					}
					

					Animal animal = animalRepository.findOne(dto.getAnimal().getId());
					if (animal != null) {
						entity.setAnimal(animal);
					}
					
					if (dto.getImportExportAnimal() != null && dto.getImportExportAnimal().getId() != null) {
						ImportExportAnimal ie = importExportAnimalService.findById(dto.getImportExportAnimal().getId());
						if (ie != null) {
							entity.setImportExportAnimal(ie);
						}
					}
					
					Integer total=0;
					Integer male=0;
					Integer feMale=0;
					Integer unGender=0;
					
					entity.setMaleParent(dto.getMaleParent());
					if(dto.getMaleParent()!=null) {
						total+=dto.getMaleParent();
						male+=dto.getMaleParent();
					}
					entity.setFemaleParent(dto.getFemaleParent());
					if(dto.getFemaleParent()!=null) {
						total+=dto.getFemaleParent();
						feMale+=dto.getFemaleParent();
					}
					entity.setUnGenderParent(dto.getUnGenderParent());
					if(dto.getUnGenderParent()!=null) {
						total+=dto.getUnGenderParent();
						unGender+=dto.getUnGenderParent();
					}
					
					entity.setMaleGilts(dto.getMaleGilts());
						if(dto.getMaleGilts()!=null) {
							total+=dto.getMaleGilts();
							male+=dto.getMaleGilts();
						}
					entity.setFemaleGilts(dto.getFemaleGilts());
						if(dto.getFemaleGilts()!=null) {
							total+=dto.getFemaleGilts();
							feMale+=dto.getFemaleGilts();
						}
					entity.setUnGenderGilts(dto.getUnGenderGilts());
					if(dto.getUnGenderGilts()!=null) {
						total+=dto.getUnGenderGilts();
						unGender+=dto.getUnGenderGilts();
					}
						
					entity.setMaleChildUnder1YearOld(dto.getMaleChildUnder1YearOld());
					if(dto.getMaleChildUnder1YearOld()!=null) {
						total+=dto.getMaleChildUnder1YearOld();
						male+=dto.getMaleChildUnder1YearOld();
					}
					entity.setFemaleChildUnder1YearOld(dto.getFemaleChildUnder1YearOld());
					if(dto.getFemaleChildUnder1YearOld()!=null) {
						total+=dto.getFemaleChildUnder1YearOld();
						feMale+=dto.getFemaleChildUnder1YearOld();
					}
					entity.setChildUnder1YearOld(dto.getChildUnder1YearOld());
					if(dto.getChildUnder1YearOld()!=null) {
						total+=dto.getChildUnder1YearOld();
						unGender+=dto.getChildUnder1YearOld();
					}
					
					entity.setMaleChildOver1YearOld(dto.getMaleChildOver1YearOld());
						if(dto.getMaleChildOver1YearOld()!=null) {
							total+=dto.getMaleChildOver1YearOld();
							male+=dto.getMaleChildOver1YearOld();
						}
					entity.setFemaleChildOver1YearOld(dto.getFemaleChildOver1YearOld());
						if(dto.getFemaleChildOver1YearOld()!=null) {
							total+=dto.getFemaleChildOver1YearOld();
							feMale+=dto.getFemaleChildOver1YearOld();
						}
					entity.setUnGenderChildOver1YearOld(dto.getUnGenderChildOver1YearOld());
						if(dto.getUnGenderChildOver1YearOld()!=null) {
							total+=dto.getUnGenderChildOver1YearOld();
							unGender+=dto.getUnGenderChildOver1YearOld();
						}
						
					//tran huu dat them import va export
					Integer totalImport=0;
					Integer totalExport=0;
					
					entity.setImportMaleParent(dto.getImportMaleParent());
					if(dto.getImportMaleParent() != null) {
						totalImport += dto.getImportMaleParent();
					}
					entity.setImportFemaleParent(dto.getImportFemaleParent());
					if(dto.getImportFemaleParent() != null) {
						totalImport += dto.getImportFemaleParent();
					}
					entity.setImportUnGenderParent(dto.getImportUnGenderParent());
					if(dto.getImportUnGenderParent() != null) {
						totalImport += dto.getImportUnGenderParent();
					}
					
					entity.setImportMaleGilts(dto.getImportMaleGilts());
					if(dto.getImportMaleGilts() != null) {
						totalImport += dto.getImportMaleGilts();
					}
					entity.setImportFemaleGilts(dto.getImportFemaleGilts());
					if(dto.getImportFemaleGilts() != null) {
						totalImport += dto.getImportFemaleGilts();
					}
					entity.setImportUnGenderGilts(dto.getImportUnGenderGilts());
					if(dto.getImportUnGenderGilts() != null) {
						totalImport += dto.getImportUnGenderGilts();
					}
					
					entity.setImportMaleChildUnder1YearOld(dto.getImportMaleChildUnder1YearOld());
					if(dto.getImportMaleChildUnder1YearOld() != null) {
						totalImport += dto.getImportMaleChildUnder1YearOld();
					}
					entity.setImportFemaleChildUnder1YearOld(dto.getImportFemaleChildUnder1YearOld());
					if(dto.getImportFemaleChildUnder1YearOld() != null) {
						totalImport += dto.getImportFemaleChildUnder1YearOld();
					}
					entity.setImportMaleChildOver1YearOld(dto.getImportMaleChildOver1YearOld());
					if(dto.getImportMaleChildOver1YearOld() != null) {
						totalImport += dto.getImportMaleChildOver1YearOld();
					}
					
					entity.setImportUnGenderChildOver1YearOld(dto.getImportUnGenderChildOver1YearOld());
					if(dto.getImportUnGenderChildOver1YearOld() != null) {
						totalImport += dto.getImportUnGenderChildOver1YearOld();
					}
					entity.setImportChildUnder1YearOld(dto.getImportChildUnder1YearOld());
					if(dto.getImportChildUnder1YearOld() != null) {
						totalImport += dto.getImportChildUnder1YearOld();
					}
					entity.setImportFemaleChildOver1YearOld(dto.getImportFemaleChildOver1YearOld());
					if(dto.getImportFemaleChildOver1YearOld() != null) {
						totalImport += dto.getImportFemaleChildOver1YearOld();
					}
					entity.setImportReason(dto.getImportReason());
					
					// xuất
					entity.setExportMaleParent(dto.getExportMaleParent());
					if(dto.getExportMaleParent() != null) {
						totalExport += dto.getExportMaleParent();
					}
					entity.setExportFemaleParent(dto.getExportFemaleParent());
					if(dto.getExportFemaleParent() != null) {
						totalExport += dto.getExportFemaleParent();
					}
					entity.setExportUnGenderParent(dto.getExportUnGenderParent());
					if(dto.getExportUnGenderParent() != null) {
						totalExport += dto.getExportUnGenderParent();
					}
					
					entity.setExportMaleGilts(dto.getExportMaleGilts());
					if(dto.getExportMaleGilts() != null) {
						totalExport += dto.getExportMaleGilts();
					}
					entity.setExportFemaleGilts(dto.getExportFemaleGilts());
					if(dto.getExportFemaleGilts() != null) {
						totalExport += dto.getExportFemaleGilts();
					}
					entity.setExportUnGenderGilts(dto.getExportUnGenderGilts());
					if(dto.getExportUnGenderGilts() != null) {
						totalExport += dto.getExportUnGenderGilts();
					}
					
					entity.setExportMaleChildUnder1YearOld(dto.getExportMaleChildUnder1YearOld());
					if(dto.getExportMaleChildUnder1YearOld() != null) {
						totalExport += dto.getExportMaleChildUnder1YearOld();
					}
					entity.setExportFemaleChildUnder1YearOld(dto.getExportFemaleChildUnder1YearOld());
					if(dto.getExportFemaleChildUnder1YearOld() != null) {
						totalExport += dto.getExportFemaleChildUnder1YearOld();
					}
					entity.setExportChildUnder1YearOld(dto.getExportChildUnder1YearOld());
					if(dto.getExportChildUnder1YearOld() != null) {
						totalExport += dto.getExportChildUnder1YearOld();
					}
					
					entity.setExportFemaleChildOver1YearOld(dto.getExportFemaleChildOver1YearOld());
					if(dto.getExportFemaleChildOver1YearOld() != null) {
						totalExport += dto.getExportFemaleChildOver1YearOld();
					}
					
					entity.setExportMaleChildOver1YearOld(dto.getExportMaleChildOver1YearOld());
					if(dto.getExportMaleChildOver1YearOld() != null) {
						totalExport += dto.getExportMaleChildOver1YearOld();
					}
					entity.setExportUnGenderChildOver1YearOld(dto.getExportUnGenderChildOver1YearOld());
					if(dto.getExportUnGenderChildOver1YearOld() != null) {
						totalExport += dto.getExportUnGenderChildOver1YearOld();
					}
					
					entity.setExportReason(dto.getExportReason());	
					
					//tran huu dat them import va export
						
					entity.setMaleImport(dto.getMaleImport());
					entity.setFemaleImport(dto.getFemaleImport());
					entity.setUnGenderImport(dto.getUnGenderImport());
					entity.setMaleExport(dto.getMaleExport());
					entity.setFemaleExport(dto.getFemaleExport());
					entity.setUnGenderExport(dto.getUnGenderExport());
					
					entity.setTotal(total);
					entity.setMale(male);
					entity.setFemale(feMale);
					entity.setUnGender(unGender);
					entity.setTotalImport(totalImport);
					entity.setTotalExport(totalExport);
					
					entity.setNote(dto.getNote());
					
					if(dto.getAnimal() != null) {
						if(dto.getAnimal().getVnlist() != null) {
							entity.setVnlist(dto.getAnimal().getVnlist());
						}else {
							entity.setVnlist("");
						}
						if(dto.getAnimal().getVnlist06() != null) {
							entity.setVnlist06(dto.getAnimal().getVnlist06());
						}else {
							entity.setVnlist06("");
						}
						if(dto.getAnimal().getCites() != null) {
							entity.setCites(dto.getAnimal().getCites());
						}else {
							entity.setCites("");
						}
						String animalGroupByYear = "";
						animalGroupByYear = animalServiceImpl.updateAnimalGroup(dto.getAnimal());
						if(animalGroupByYear != null && animalGroupByYear.length() >0) {
							entity.setAnimalGroup(animalGroupByYear);
						}
					}
					
					FmsAdministrativeUnit administrativeUnit = null;
					if(farm!=null) {
						administrativeUnit= farm.getAdministrativeUnit();
						if(administrativeUnit != null) {
							entity.setAdministrativeUnit(administrativeUnit);
							if(administrativeUnit.getParent() != null) {
								entity.setDistrict(administrativeUnit.getParent());
							}
							if(administrativeUnit.getParent() != null && administrativeUnit.getParent().getParent() != null) {
								entity.setProvince(administrativeUnit.getParent().getParent());
							}
						}
					}
					if (reportPeriod!=null) {
						entity.setReportPeriod(reportPeriod);
						entity = reportForm16Repository.save(entity);
					}else {
						reportPeriod= new ReportPeriod();
						reportPeriod.setCreateDate(currentDate);
						reportPeriod.setCreatedBy(currentUserName);
						reportPeriod.setAdministrativeUnit(administrativeUnit);
						if(farm!=null) {
							reportPeriod.setFarm(farm);
						}
						reportPeriod.setDate(day);
						reportPeriod.setMonth(month);
						reportPeriod.setYear(year);
						reportPeriod.setType(1);
						Set<ReportForm16> list= new HashSet<>();
						list.add(entity);
						reportPeriod.setReportItems(list);
						if(administrativeUnit != null) {
							reportPeriod.setAdministrativeUnit(administrativeUnit);
							if(administrativeUnit.getParent() != null) {
								reportPeriod.setDistrict(administrativeUnit.getParent());
							}
							if(administrativeUnit.getParent() != null && administrativeUnit.getParent().getParent() != null) {
								reportPeriod.setProvince(administrativeUnit.getParent().getParent());
							}
						}
						ReportPeriodDto reportPeriodDto= new ReportPeriodDto(reportPeriod);
						reportPeriodDto= reportPeriodService.saveOrUpdate(reportPeriodDto);
						if(reportPeriodDto!=null) {
							for(ReportForm16Dto reportForm16Dto: reportPeriodDto.getReportItems()) {
								if(reportForm16Dto!=null) {
									entity= reportForm16Repository.findOne(reportForm16Dto.getId());
									break;
								}
							}
						}
						
					}
					
					
//					this.updateDataFromReportForm16ToAnimalReportDataByEntity(entity);
//					animalIds= reportPeriodService.getListAnimalIds(entity.getFarm().getId(),entity.getReportPeriod().getYear());
//					// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
//					animalReportDataService.updateAnimalReportDataByFarmAnimalYear(entity.getFarm().getId(),animalIds, entity.getReportPeriod().getYear());
					listDtoSaved.add(new ReportForm16Dto(entity));
					
				}
			}
		}
//		listForm16Before.removeAll(listForm16Remove);
//		
//		if(listForm16Before.size()>0) {//xoas ban ghi
//			for(ReportForm16 dto1: listForm16Before) {
//				reportPeriodService.updateAnimalReportDataBeforeDeleteReportForm16(dto1);
//				animalIds= reportPeriodService.getListAnimalIds(dto1.getFarm().getId(),dto1.getReportPeriod().getYear());
//				// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
//				animalReportDataService.updateAnimalReportDataByFarmAnimalYear(dto1.getFarm().getId(),animalIds, dto1.getReportPeriod().getYear());
//			}
//		}
//		if(listForm16Before.size()>0) {//xoas ban ghi
//			for(ReportForm16 dto1: listForm16Before) {
//				reportForm16Repository.delete(dto1.getId());
//			}
//		}
		return listDtoSaved;
	}
	
	//tran huu dat them ham cap nhat animal
	private void updateDataFromReportForm16ToAnimalReportDataByEntity(ReportForm16 reportForm16) {
		Integer year = reportForm16.getReportPeriod().getYear();
		Integer month = reportForm16.getReportPeriod().getMonth();
		Integer day = reportForm16.getReportPeriod().getDate();
		Long farmId = reportForm16.getFarm().getId();
		Long animalId = reportForm16.getAnimal().getId();
		List<AnimalReportData> listAnimalReportDataUpdate = new ArrayList<AnimalReportData>();
		AnimalReportData animalParent = null;
		List<AnimalReportData> lst = null;
		lst = animalReportDataRepository.findByYearAndMonthFarmAnimal(year, month, day, farmId, animalId, WLConstant.AnimalReportDataType.animalParent.getValue());
		if(lst!=null && lst.size()>0) {
			animalParent = lst.get(0);
		}
		if(animalParent==null) {
			animalParent = new AnimalReportData();
		}
		animalParent.setType(WLConstant.AnimalReportDataType.animalParent.getValue());
		animalParent.setAnimal(reportForm16.getAnimal());
		animalParent.setFarm(reportForm16.getReportPeriod().getFarm());
		animalParent.setYear(reportForm16.getReportPeriod().getYear());
		animalParent.setMonth(reportForm16.getReportPeriod().getMonth());
		animalParent.setDay(reportForm16.getReportPeriod().getDate());
		
		animalParent.setMale(reportForm16.getMaleParent());
		int male=0; if(animalParent.getMale()!=null) male = animalParent.getMale();
		animalParent.setFemale(reportForm16.getFemaleParent());
		int female=0; if(animalParent.getFemale()!=null) female = animalParent.getFemale();
		animalParent.setUnGender(reportForm16.getUnGender());
		int unGender=0; if(animalParent.getUnGender()!=null) unGender = animalParent.getUnGender();
		animalParent.setTotal(male+female+unGender);
		
		animalParent.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
		animalParent.setDistrict(reportForm16.getDistrict());
		animalParent.setProvince(reportForm16.getProvince());
		listAnimalReportDataUpdate.add(animalParent);

		// gilts hậu bị
		AnimalReportData gilts = null;
		lst = null;
		lst = animalReportDataRepository.findByYearAndMonthFarmAnimal(year, month, day, farmId, animalId, WLConstant.AnimalReportDataType.gilts.getValue());
		if(lst!=null && lst.size()>0) {
			gilts = lst.get(0);
		}
		if(gilts==null) {
			gilts = new AnimalReportData();
		}
		
		gilts.setType(WLConstant.AnimalReportDataType.gilts.getValue());
		gilts.setAnimal(reportForm16.getAnimal());
		gilts.setFarm(reportForm16.getReportPeriod().getFarm());
		gilts.setYear(reportForm16.getReportPeriod().getYear());
		gilts.setMonth(reportForm16.getReportPeriod().getMonth());
		gilts.setDay(reportForm16.getReportPeriod().getDate());
		
		gilts.setFemale(reportForm16.getFemaleGilts());
		female=0; if(gilts.getFemale()!=null) female = gilts.getFemale();		
		gilts.setMale(reportForm16.getMaleGilts());
		male=0; if(gilts.getMale()!=null) male = gilts.getMale();
		gilts.setUnGender(reportForm16.getUnGenderGilts());
		unGender=0; if(reportForm16.getUnGenderGilts()!=null) unGender = reportForm16.getUnGenderGilts();
		gilts.setTotal(female+male+unGender);
		
		gilts.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
		gilts.setDistrict(reportForm16.getDistrict());
		gilts.setProvince(reportForm16.getProvince());
		listAnimalReportDataUpdate.add(gilts);

		// childUnder1YearOld Con non dưới 1 tuổi
		AnimalReportData childUnder1YearOld = null;
		lst = null;
		lst = animalReportDataRepository.findByYearAndMonthFarmAnimal(year, month, day, farmId, animalId, WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
		if(lst!=null && lst.size()>0) {
			childUnder1YearOld = lst.get(0);
		}
		if(childUnder1YearOld==null) {
			childUnder1YearOld = new AnimalReportData();
		}
		
		childUnder1YearOld.setType(WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
		childUnder1YearOld.setAnimal(reportForm16.getAnimal());
		childUnder1YearOld.setFarm(reportForm16.getReportPeriod().getFarm());
		childUnder1YearOld.setYear(reportForm16.getReportPeriod().getYear());
		childUnder1YearOld.setMonth(reportForm16.getReportPeriod().getMonth());
		childUnder1YearOld.setDay(reportForm16.getReportPeriod().getDate());
		
		childUnder1YearOld.setMale(reportForm16.getMaleChildUnder1YearOld());
		childUnder1YearOld.setFemale(reportForm16.getFemaleChildUnder1YearOld());
		childUnder1YearOld.setUnGender(reportForm16.getChildUnder1YearOld());
		
		if(reportForm16.getMaleChildUnder1YearOld() == null) {
			reportForm16.setMaleChildUnder1YearOld(0);
		}
		if(reportForm16.getFemaleChildUnder1YearOld() == null) {
			reportForm16.setFemaleChildUnder1YearOld(0);
		}
		if(childUnder1YearOld.getUnGender() == null) {
			childUnder1YearOld.setUnGender(0);
		}
		childUnder1YearOld.setTotal(reportForm16.getMaleChildUnder1YearOld()+reportForm16.getFemaleChildUnder1YearOld()+ childUnder1YearOld.getUnGender());
		
		childUnder1YearOld.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
		childUnder1YearOld.setDistrict(reportForm16.getDistrict());
		childUnder1YearOld.setProvince(reportForm16.getProvince());
		listAnimalReportDataUpdate.add(childUnder1YearOld);

		// childOver1YearOld Con non trên 1 tuổi
		AnimalReportData childOver1YearOld = null;
		lst = null;
		lst = animalReportDataRepository.findByYearAndMonthFarmAnimal(year, month, day, farmId, animalId, WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
		if(lst!=null && lst.size()>0) {
			childOver1YearOld = lst.get(0);
		}
		if(childOver1YearOld==null) {
			childOver1YearOld = new AnimalReportData();
		}
		
		childOver1YearOld.setType(WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
		childOver1YearOld.setAnimal(reportForm16.getAnimal());
		childOver1YearOld.setFarm(reportForm16.getReportPeriod().getFarm());
		childOver1YearOld.setYear(reportForm16.getReportPeriod().getYear());
		childOver1YearOld.setMonth(reportForm16.getReportPeriod().getMonth());
		childOver1YearOld.setDay(reportForm16.getReportPeriod().getDate());
		
		childOver1YearOld.setMale(reportForm16.getMaleChildOver1YearOld());
		male=0; if(childOver1YearOld.getMale()!=null) male = childOver1YearOld.getMale();
		childOver1YearOld.setFemale(reportForm16.getFemaleChildOver1YearOld());
		female=0; if(childOver1YearOld.getFemale()!=null) female = childOver1YearOld.getFemale();
		childOver1YearOld.setUnGender(reportForm16.getUnGenderChildOver1YearOld());
		unGender = 0; if(reportForm16.getUnGenderChildOver1YearOld()!=null) unGender = reportForm16.getUnGenderChildOver1YearOld();
		childOver1YearOld.setTotal(male+female+unGender);
		
		childOver1YearOld.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
		childOver1YearOld.setDistrict(reportForm16.getDistrict());
		childOver1YearOld.setProvince(reportForm16.getProvince());
		listAnimalReportDataUpdate.add(childOver1YearOld);
		
		
		List<AnimalReportData> listAnimalReportData = animalReportDataRepository.save(listAnimalReportDataUpdate);
	}

	@Override
	public ReportForm16Dto deleteById(Long idForm16) {
		List<Long> animalIds = new ArrayList<Long>();
		if(idForm16!=null) {
			ReportForm16 entity= this.reportForm16Repository.findOne(idForm16);
			if(entity!=null) {
				ReportForm16Dto dto= new ReportForm16Dto(entity);
				reportPeriodService.updateAnimalReportDataBeforeDeleteReportForm16(entity);
				animalIds= reportPeriodService.getListAnimalIds(entity.getFarm().getId(),entity.getReportPeriod().getYear());
				// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
				animalReportDataService.updateAnimalReportDataByFarmAnimalYear(entity.getFarm().getId(),animalIds, entity.getReportPeriod().getYear());
				this.reportForm16Repository.delete(idForm16);
				return dto;
			}
		}
		return null;
	}


	public void mergePeriod(ReportForm16Dto r1, ReportForm16Dto r2) {
		ReportForm16 entityR1= this.reportForm16Repository.findOne(r1.getId());
		ReportForm16 entityR2= this.reportForm16Repository.findOne(r2.getId());
		//Set giá trị cho form16 thuộc farm cũ:
		entityR2.setReportPeriod(entityR1.getReportPeriod());
		entityR2.setFarm(entityR1.getFarm());
		//Đẩy form16 thuộc farm cũ vào period farm chính:
		ReportPeriodDto reportPeriodDto = reportPeriodService.getById(entityR1.getReportPeriod().getId());
		if(reportPeriodDto.getReportItems()==null) {
			reportPeriodDto.setReportItems(new HashSet<ReportForm16Dto>());
		}
		ReportForm16Dto f16Dto = new ReportForm16Dto(entityR2);
		reportPeriodDto.getReportItems().add(f16Dto);
		reportPeriodService.saveOrUpdate(reportPeriodDto);
	}
	@Override
	public List<ReportForm16Dto> mergePeriodForForm16(List<ReportForm16Dto> listDto, Long farmId, Long dupId) {
		// TODO Auto-generated method stub
		List<ReportForm16Dto> listSub = this.getListReportForm16ByFarmId(dupId);
		List<ReportForm16Dto> listMain = this.getListReportForm16ByFarmId(farmId);
		//listSub.addAll(listDto);
		Set<String> datePeriodremove = new HashSet<String>();
		if(listMain!=null&&listSub!=null) {
			//TH cả 2 đã có period tại 1 ngày xác định: Hợp nhất khi bên farm chính k có f16(bởi bị xóa hết f16) + farm phụ có f16 => cần xử lý period
			//Kiểm tra xem farm chính có period nào có size = 0 hay k để xóa tránh trường hợp có 2 period cùng ngày
			List<ReportPeriod> listPeriodMainFarm = new ArrayList<ReportPeriod>();
			SearchReportPeriodDto searchDto = new SearchReportPeriodDto();
			searchDto.setFarmId(farmId);
			listPeriodMainFarm = reportPeriodService.getAllEntity(searchDto);
			for(ReportPeriod p : listPeriodMainFarm) {
				if(p.getReportItems()!=null && p.getReportItems().size()==0) {
					reportPeriodService.delete(p.getId());
				}
			}
			//TH cả 2 đã có period: Hợp nhất khi bên farm chính có f16 + farm phụ không có f16 => xử lý period farm phụ bị hợp nhất
			//Xử lý period trùng ngày
			List<ReportPeriod> listPeriodSubFarm = new ArrayList<ReportPeriod>();
			SearchReportPeriodDto searchSubDto = new SearchReportPeriodDto();
			searchSubDto.setFarmId(dupId);
			listPeriodSubFarm = reportPeriodService.getAllEntity(searchSubDto);
			for(ReportPeriod p : listPeriodSubFarm) {
				if(p.getReportItems()!=null && p.getReportItems().size()==0) {
					reportPeriodService.delete(p.getId());
				}
			}
			//TH cả 2 đã có period: Hợp nhất period khi 2 bên đều có dữ liệu 16
			for(ReportForm16Dto r1: listMain) {
				for(ReportForm16Dto r2: listSub) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String dateRpR1 = sdf.format(r1.getDateReport());
					String dateRpR2 = sdf.format(r2.getDateReport());
					if(dateRpR1.equals(dateRpR2)) {
						this.mergePeriod(r1,r2);
						datePeriodremove.add(dateRpR2);
					}
				}
			}
			
			//TH chỉ 1 bên có period: Farm chính chưa có period + farm phụ có hoặc chính đã có period
			// phụ chưa có period => đã hợp nhất ở service khác
		}
		if(datePeriodremove!=null && datePeriodremove.size()>0) {
			List<ReportPeriod> listPeriodRemove = new ArrayList<ReportPeriod>();
			SearchReportPeriodDto searchDto = new SearchReportPeriodDto();
			searchDto.setFarmId(dupId);
			listPeriodRemove = reportPeriodService.getAllEntity(searchDto);
			for(ReportPeriod p : listPeriodRemove) {
				Calendar cal = Calendar.getInstance();
				cal.set(p.getYear(), p.getMonth()-1,p.getDate(),0,0);
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				String dateDelete = sdf.format(cal.getTime());
				for(String d : datePeriodremove) {
					if(dateDelete.equals(d)) {
						reportPeriodService.delete(p.getId());
					}
				}
			}
		}
		return null;
	}
}
