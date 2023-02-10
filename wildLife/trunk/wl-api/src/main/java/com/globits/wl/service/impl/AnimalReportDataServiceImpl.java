package com.globits.wl.service.impl;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Query;

import org.apache.http.client.ClientProtocolException;
import org.apache.poi.ss.formula.eval.ConcatEval;
import org.codehaus.jettison.json.JSONException;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.domain.AdministrativeUnit;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmReportPeriod;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.IndividualAnimal;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.AnimalReportDataFormDto;
import com.globits.wl.dto.DeleteMapByAreaDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FarmToSyncMap;
import com.globits.wl.dto.IndividualAnimalDto;
import com.globits.wl.dto.Report18Dto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.KeyDto;
import com.globits.wl.dto.functiondto.MapReturnDto;
import com.globits.wl.dto.functiondto.ReportAccordingToTheRedBookDto;
import com.globits.wl.dto.functiondto.ReportQuantityByCategoryCitesDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.dto.report.ReportOfAnimalReportDataDto;
import com.globits.wl.repository.AnimalReportDataRepository;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.FarmReportPeriodRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.ImportExportAnimalRepository;
import com.globits.wl.repository.IndividualAnimalRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.repository.ReportForm16Repository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.ImportExportAnimalService;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.service.ReportService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.FarmMapServiceUtil;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;
class PrimeRun implements Runnable {
	Page<FarmDto> pageFarms;
	FarmSearchDto farmSearchDto;
	List<FarmToSyncMap> list; 
	int year;
    PrimeRun(List<FarmToSyncMap> list) {
        this.list = list;
    }

    public void run() {
    	if(list!=null && list.size()>0) {
    		for (int i=0;i<list.size();i++) {
        		FarmToSyncMap farmToSync = list.get(i);
        		Farm farm = farmToSync.getFarm();
        		List<FarmAnimalTotalDto> listAnimalReportTotal = farmToSync.getListAnimalReportTotal();
    			FarmMapServiceUtil.updateFarmToMap(farm);
    			if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
    				for (FarmAnimalTotalDto farmAnimalTotalDto : listAnimalReportTotal) {
    					if (farmAnimalTotalDto != null) {
    						try {
    							FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
    						} catch (Exception e) {
    							continue;
    							// TODO: handle exception
    						}
    					}
    				}
    			}
        	}
    	}    	
    }
}

@Service
public class AnimalReportDataServiceImpl extends GenericServiceImpl<AnimalReportData, Long>
		implements AnimalReportDataService {
	@Autowired
	private AnimalReportDataRepository animalReportDataRepository;
	@Autowired
	private AnimalRepository animalRepository;
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private ProductTargetRepository productTargetRepository;
	@Autowired
	private OriginalRepository originalRepository;
	@Autowired
	private IndividualAnimalRepository individualAnimalRepository;
	@Autowired
	private FarmReportPeriodRepository farmReportPeriodRepository;
	@Autowired
	private ImportExportAnimalService importExportAnimalService;
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	@Autowired
	private AnimalReportDataService animalReportDataService;
	@Autowired
	private ReportPeriodService reportPeriodService;
	@Autowired
	private FarmService farmService;
	@Autowired
	private ReportForm16Repository reportForm16Repository;
	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;
	@Autowired
	private AnimalServiceImpl animalServiceImpl;
	@Autowired
	private ReportService reportService;
	@Override
	public AnimalReportDataDto saveAnimalReport(Long id, AnimalReportDataDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			AnimalReportData entity = null;
			if (id != null) {
				entity = animalReportDataRepository.findOne(id);
			} else if (dto.getId() != null) {
				entity = animalReportDataRepository.findOne(dto.getId());
			}
			if (entity == null) {
				entity = new AnimalReportData();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			} else {
				entity.setModifyDate(currentDate);
				entity.setModifiedBy(currentUserName);
			}
			Animal animal = null;
			if (dto.getAnimal() != null && dto.getAnimal().getId() != null) {
				animal = animalRepository.findOne(dto.getAnimal().getId());
			}
			entity.setAnimal(animal);

			Farm farm = null;
			if (dto.getFarm() != null && dto.getFarm().getId() != null) {
				farm = farmRepository.findOne(dto.getFarm().getId());
			}
			entity.setFarm(farm);

			entity.setYear(dto.getYear());
			entity.setQuarter(dto.getQuarter());
			entity.setMonth(dto.getMonth());//
			entity.setDay(dto.getDay());

			entity.setFemale(dto.getFemale());
			entity.setMale(dto.getMale());
			entity.setUnGender(dto.getUnGender());

			Integer total = 0;
			if (entity.getFemale() != null && entity.getFemale().intValue() > 0) {
				total += entity.getFemale().intValue();
			}
			if (entity.getMale() != null && entity.getMale().intValue() > 0) {
				total += entity.getMale().intValue();
			}
			if (entity.getUnGender() != null && entity.getUnGender().intValue() > 0) {
				total += entity.getUnGender().intValue();
			}
			entity.setTotal(total);
			if (dto.getType() == WLConstant.AnimalReportDataType.unDefine.getValue()) {
				entity.setTotal(dto.getTotal());
			}

			ProductTarget productTarget = null;
			if (dto.getProductTarget() != null && dto.getProductTarget().getId() != null) {
				productTarget = productTargetRepository.findOne(dto.getProductTarget().getId());
			}
			entity.setProductTarget(productTarget);
			if (productTarget != null) {
				entity.setPurpose(productTarget.getCode());
			}

			Original original = null;
			if (dto.getOriginal() != null && dto.getOriginal().getId() != null) {
				original = originalRepository.findOne(dto.getOriginal().getId());
			}
			entity.setOriginal(original);
			if (original != null) {
				entity.setSource(original.getCode());
			}
			entity.setType(dto.getType());
			entity.setRegistrationDate(dto.getRegistrationDate());
			Set<IndividualAnimal> individualAnimals = new HashSet<IndividualAnimal>();
			if (dto.getIndividualAnimals() != null && dto.getIndividualAnimals().size() > 0) {
				for (IndividualAnimalDto individualAnimalDto : dto.getIndividualAnimals()) {
					if (individualAnimalDto != null) {
						IndividualAnimal individualAnimal = null;
						if (individualAnimalDto.getId() != null) {
							individualAnimal = individualAnimalRepository.getOne(individualAnimalDto.getId());
						}
						if (individualAnimal == null) {
							individualAnimal = new IndividualAnimal();
							individualAnimal.setCreateDate(currentDate);
							individualAnimal.setCreatedBy(currentUserName);
						} else {
							individualAnimal.setModifiedBy(currentUserName);
							individualAnimal.setModifyDate(currentDate);
						}
						individualAnimal.setAnimalReportData(entity);
						individualAnimal.setName(individualAnimalDto.getName());
						individualAnimal.setCode(individualAnimalDto.getCode());
						individualAnimal.setBirthDate(individualAnimalDto.getBirthDate());
						individualAnimal.setAnimal(animal);
						individualAnimal.setStatus(individualAnimalDto.getStatus());
						individualAnimal.setGender(individualAnimalDto.getGender());
						individualAnimal.setDayOld(individualAnimalDto.getDayOld());

						individualAnimals.add(individualAnimal);
					}
				}
			}
			if (entity.getIndividualAnimals() == null) {
				entity.setIndividualAnimals(individualAnimals);
			} else {
				entity.getIndividualAnimals().clear();
				entity.getIndividualAnimals().addAll(individualAnimals);
			}
			entity.setFormId(dto.getFormId());
			entity.setFormType(dto.getFormType());
			entity.setReasonBornAtFarm(dto.getReasonBornAtFarm());
			entity.setReasonOther(dto.getReasonOther());
			if(dto.getAnimal() != null) {
				if(dto.getAnimal().getVnlist() != null) {
					entity.setVnlist(dto.getVnlist());
				}else {
					entity.setVnlist("");
				}
				if(dto.getAnimal().getVnlist06() != null) {
					entity.setVnlist06(dto.getVnlist06());
				}else {
					entity.setVnlist06("");
				}
				if(dto.getAnimal().getCites() != null) {
					entity.setCites(dto.getCites());
				}else {
					entity.setCites("");
				}
				if(dto.getAnimal().getAnimalGroup() != null) {
					entity.setAnimalGroup(dto.getAnimalGroup());
				}
			}
			if (dto.getFarmReportPeriodId() != null) {
				FarmReportPeriod frp = farmReportPeriodRepository.findOne(dto.getFarmReportPeriodId());
				entity.setFarmReportPeriod(frp);
			}
			entity = animalReportDataRepository.save(entity);
			return new AnimalReportDataDto(entity);
		}
		return null;
	}

	@Override
	public Boolean deleteById(Long id) {
		AnimalReportData animalReportData = this.animalReportDataRepository.findOne(id);
		if (animalReportData != null) {
			this.animalReportDataRepository.delete(id);
		}
		return null;
	}

	@Override
	public AnimalReportDataDto getById(Long id) {
		AnimalReportData animalReportData = this.animalReportDataRepository.findOne(id);
		if (animalReportData != null) {
			return new AnimalReportDataDto(animalReportData);
		}
		return null;
	}

	@Override
	public List<AnimalReportData> getList(Long provinceId, Long districtId, Long communeId, Integer year,
			Integer quarter, Integer month, Integer day, Long farmId, Long animalId) {
		String SQL = "SELECT a FROM AnimalReportData a WHERE 1=1 ";

		// if(provinceId!=null && provinceId>0L) {
		// SQL+=" AND a.farm.administrativeUnit.parent.parent.id= :provinceId ";
		// }
		// if(districtId!=null && districtId>0L) {
		// SQL+=" AND a.farm.administrativeUnit.parent.id= :districtId ";
		// }
		// if(communeId!=null && communeId>0L) {
		// SQL+=" AND a.farm.administrativeUnit.id= :communeId ";
		// }
		// sửa lại query theo đơn vị hành chính của báo cáo
		if (provinceId != null && provinceId > 0L) {
			SQL += " AND a.province.id= :provinceId ";
		}
		if (districtId != null && districtId > 0L) {
			SQL += " AND a.district.id= :districtId ";
		}
		if (communeId != null && communeId > 0L) {
			SQL += " AND a.administrativeUnit.id= :communeId ";
		}
		if (year != null && year > 0) {
			SQL += " AND a.year= :year ";
		}
		if (quarter != null && quarter > 0) {
			SQL += " AND a.quarter= :quarter ";
		}
		if (month != null && month > 0) {
			SQL += " AND a.month= :month ";
		}
		if (day != null && day > 0) {
			SQL += " AND a.day= :day ";
		}
		if (farmId != null && farmId > 0L) {
			SQL += " AND a.farm.id= :farmId ";
		}
		if (animalId != null && animalId > 0L) {
			SQL += " AND a.animal.id= :animalId ";
		}

		Query q = manager.createQuery(SQL, AnimalReportData.class);

		if (provinceId != null && provinceId > 0L) {
			q.setParameter("provinceId", provinceId);
		}
		if (districtId != null && districtId > 0L) {
			q.setParameter("districtId", districtId);
		}
		if (communeId != null && communeId > 0L) {
			q.setParameter("communeId", communeId);
		}
		if (year != null && year > 0L) {
			q.setParameter("year", year);
		}
		if (quarter != null && quarter > 0L) {
			q.setParameter("quarter", quarter);
		}
		if (month != null && month > 0L) {
			q.setParameter("month", month);
		}
		if (day != null && day > 0L) {
			q.setParameter("day", day);
		}
		if (farmId != null && farmId > 0L) {
			q.setParameter("farmId", farmId);
		}
		if (animalId != null && animalId > 0L) {
			q.setParameter("animalId", animalId);
		}
		return q.getResultList();
	}

	@Override
	public List<AnimalReportData> getListAnimalReportData(AnimalReportDataSearchDto searchDto) {
		Long provinceId = searchDto.getProvinceId();
		Long districtId = searchDto.getDistrictId();
		Long communeId = searchDto.getCommuneId();
		Integer year = searchDto.getYear();
		Integer quarter = searchDto.getQuarter();
		Integer month = searchDto.getMonth();
		Integer day = searchDto.getDay();
		Long farmId = searchDto.getFarmId();
		Long animalId = searchDto.getAnimalId();
		List<String> listCites = searchDto.getListCites();

		String SQL = "SELECT a FROM AnimalReportData a WHERE 1=1 ";

		// if(provinceId!=null && provinceId>0L) {
		// SQL+=" AND a.farm.administrativeUnit.parent.parent.id= :provinceId ";
		// }
		// if(districtId!=null && districtId>0L) {
		// SQL+=" AND a.farm.administrativeUnit.parent.id= :districtId ";
		// }
		// if(communeId!=null && communeId>0L) {
		// SQL+=" AND a.farm.administrativeUnit.id= :communeId ";
		// }
		// sửa lại query theo đơn vị hành chính của báo cáo
		if (provinceId != null && provinceId > 0L) {
			SQL += " AND a.province.id= :provinceId ";
		}
		if (districtId != null && districtId > 0L) {
			SQL += " AND a.district.id= :districtId ";
		}
		if (communeId != null && communeId > 0L) {
			SQL += " AND a.administrativeUnit.id= :communeId ";
		}
		if (year != null && year > 0) {
			SQL += " AND a.year= :year ";
		}
		if (quarter != null && quarter > 0) {
			SQL += " AND a.quarter= :quarter ";
		}
		if (month != null && month > 0) {
			SQL += " AND a.month= :month ";
		}
		if (day != null && day > 0) {
			SQL += " AND a.day= :day ";
		}
		if (farmId != null && farmId > 0L) {
			SQL += " AND a.farm.id= :farmId ";
		}
		if (animalId != null && animalId > 0L) {
			SQL += " AND a.animal.id= :animalId ";
		}
		if (listCites != null && listCites.size() > 0) {
			SQL += " AND a.animal.cites in (:listCites) ";
		}

		Query q = manager.createQuery(SQL, AnimalReportData.class);

		if (provinceId != null && provinceId > 0L) {
			q.setParameter("provinceId", provinceId);
		}
		if (districtId != null && districtId > 0L) {
			q.setParameter("districtId", districtId);
		}
		if (communeId != null && communeId > 0L) {
			q.setParameter("communeId", communeId);
		}
		if (year != null && year > 0L) {
			q.setParameter("year", year);
		}
		if (quarter != null && quarter > 0L) {
			q.setParameter("quarter", quarter);
		}
		if (month != null && month > 0L) {
			q.setParameter("month", month);
		}
		if (day != null && day > 0L) {
			q.setParameter("day", day);
		}
		if (farmId != null && farmId > 0L) {
			q.setParameter("farmId", farmId);
		}
		if (animalId != null && animalId > 0L) {
			q.setParameter("animalId", animalId);
		}
		if (listCites != null && listCites.size() > 0) {
			q.setParameter("listCites", listCites);
		}
		return q.getResultList();
	}

	@Override
	public Integer saveAnimalReport(AnimalReportDataFormDto dto) {
		Integer ret = 0;
		if (dto != null) {
			UUID formId = UUID.randomUUID();
			if (dto.getTotalParent() != null && dto.getTotalParent() > 0) {
				AnimalReportDataDto parent = new AnimalReportDataDto();
				parent.setAnimal(dto.getAnimal());
				parent.setFarm(dto.getFarm());
				parent.setYear(dto.getYear());
				parent.setQuarter(dto.getQuarter());
				parent.setMonth(dto.getMonth());
				parent.setDay(dto.getDay());
				parent.setType(WLConstant.AnimalReportDataType.animalParent.getValue());
				parent.setTotal(dto.getTotalParent());
				parent.setMale(dto.getMaleParent());
				parent.setFemale(dto.getFemaleParent());
				parent.setUnGender(dto.getUnGenderParent());
				parent.setOriginal(dto.getOriginal());
				parent.setSource(dto.getSource());
				parent.setPurpose(dto.getPurpose());
				parent.setProductTarget(dto.getProductTarget());
				parent.setFormId(formId);
				parent.setFormType(WLConstant.AnimalReportDataFormType.firstType.getValue());
				parent.setFarmReportPeriodId(dto.getFarmReportPeriodId());
				this.saveAnimalReport(null, parent);
				ret += 1;
			}

			if (dto.getTotalChild() != null && dto.getTotalChild() > 0) {
				AnimalReportDataDto child = new AnimalReportDataDto();
				child.setAnimal(dto.getAnimal());
				child.setFarm(dto.getFarm());
				child.setYear(dto.getYear());
				child.setQuarter(dto.getQuarter());
				child.setMonth(dto.getMonth());
				child.setDay(dto.getDay());
				child.setType(WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
				child.setTotal(dto.getTotalChild());
				child.setMale(dto.getMaleChild());
				child.setFemale(dto.getFemaleChild());
				child.setUnGender(dto.getUnGenderChild());
				child.setOriginal(dto.getOriginal());
				child.setSource(dto.getSource());
				child.setPurpose(dto.getPurpose());
				child.setProductTarget(dto.getProductTarget());
				child.setFormId(formId);
				child.setFormType(WLConstant.AnimalReportDataFormType.firstType.getValue());
				child.setFarmReportPeriodId(dto.getFarmReportPeriodId());
				this.saveAnimalReport(null, child);
				ret += 1;
			}

			if (dto.getTotalChildUnder1YO() != null && dto.getTotalChildUnder1YO() > 0) {
				AnimalReportDataDto childUnder1YO = new AnimalReportDataDto();
				childUnder1YO.setAnimal(dto.getAnimal());
				childUnder1YO.setFarm(dto.getFarm());
				childUnder1YO.setYear(dto.getYear());
				childUnder1YO.setQuarter(dto.getQuarter());
				childUnder1YO.setMonth(dto.getMonth());
				childUnder1YO.setDay(dto.getDay());
				childUnder1YO.setType(WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
				childUnder1YO.setTotal(dto.getTotalChildUnder1YO());
				childUnder1YO.setMale(dto.getMaleChildUnder1YO());
				childUnder1YO.setFemale(dto.getFemaleChildUnder1YO());
				childUnder1YO.setUnGender(dto.getTotalChildUnder1YO());
				childUnder1YO.setOriginal(dto.getOriginal());
				childUnder1YO.setSource(dto.getSource());
				childUnder1YO.setPurpose(dto.getPurpose());
				childUnder1YO.setProductTarget(dto.getProductTarget());
				childUnder1YO.setFormId(formId);
				childUnder1YO.setFarmReportPeriodId(dto.getFarmReportPeriodId());
				childUnder1YO.setFormType(WLConstant.AnimalReportDataFormType.firstType.getValue());
				this.saveAnimalReport(null, childUnder1YO);
				ret += 1;
			}
		}
		return ret;
	}

	@Override
	public Integer saveListAnimalReport(List<AnimalReportDataFormDto> dtos) {
		Integer ret = 0;
		if (dtos != null && dtos.size() > 0) {
			for (AnimalReportDataFormDto animalReportDataFormDto : dtos) {
				Integer r = this.saveAnimalReport(animalReportDataFormDto);
				ret += r;
			}
		}
		return ret;
	}

	@Override
	public List<ReportAccordingToTheRedBookDto> reportAnimalAccordingBySearch(AnimalReportDataSearchDto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if (!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if (listWardId == null || listWardId.size() == 0) {
				return null;
			}
		}

		List<ReportAccordingToTheRedBookDto> result = new ArrayList<ReportAccordingToTheRedBookDto>();
		
		
		String whereClause = "";
		String sql="SELECT year,sum(total) AS totalAnimalByYear, animalName ";
		String orderby=" GROUP BY year,animalName ORDER BY year DESC,animalName ";
		if (searchDto.getProvinceId() != null) {
			whereClause += " AND tb.provinceId = :provinceId";
		}
		if (searchDto.getDistrictId() != null) {
			whereClause += " AND tb.districtId = :districtId";
		}
		if (searchDto.getCommuneId() != null) {
			whereClause += " AND tb.wardId = :wardId";
		}
		if (searchDto.getAnimalClass() != null) {
			whereClause += " AND tb.animalClass = :animalClass ";
		}
		//List animal Class
		if(searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size()>0) {
			whereClause += " AND tb.animalClass in (:listAnimalClass) ";
		}
		if (searchDto.getAnimalOrdo() != null) {
			whereClause += " AND tb.animalOrdo = :animalOrdo ";
		}
		//List animal ordo
		if(searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size()>0) {
			whereClause += " AND tb.animalOrdo in (:listAnimalOrdo) ";
		}
		if (searchDto.getAnimalFamily() != null) {
			whereClause += " AND tb.animalFamily = :animalFamily ";
		}
		//List animal family
		if(searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size()>0) {
			whereClause += " AND tb.animalFamily in (:listAnimalFamily) ";
		}
		if (searchDto.getAnimalIds() != null && searchDto.getAnimalIds().size()>0) {
			whereClause += " AND tb.animalId in (:animalId) ";
		}

		if (searchDto.getYear() != null) {
			whereClause += " AND tb.year = :year ";
		}
		
		sql+=" FROM (SELECT year,total,animalName FROM"
				+ "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
				+ "  f.date_report AS dateReport,"
				+ "  p.year,"
				+ "  prov.id AS provinceId,"
				+ "  prov.name AS provinceName,"
				+ "  prov.code AS provCode,"
				+ "  dis.id AS districtId,"
				+ "  dis.name AS districtName,"
				+ "  dis.code AS disCode,"
				+ "  w.id AS wardId,"
				+ "  w.name AS wardName,"
				+ "  w.code AS wardCode,"
				+ "  a.id AS animalId," 
				+ "  a.ordo AS animalOrdo," 
				+ "  a.animal_class AS animalClass," 
				+ "  a.family AS animalFamily," 
				+ "  a.name AS animalName,"
				+ "  a.cites, "
				+ "  fa.id AS farmId,"
				+ "  f.total AS total"
				+ "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
				+ "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
				+ "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
				+ "  AND prov.parent_id IS NULL"
				+ "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
				+ "  AND dis.parent_id IS NOT NULL"
				+ "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
				+ "  AND w.parent_id IS NOT NULL"
				+ "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//				"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" + 
//				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" + 
//				"   AND w.parent_id IS NOT NULL" + 
//				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" + 
//				"   AND dis.parent_id IS NOT NULL" 
				+ " ) as tb where tb.rowNumber=1 and tb.total<>0 " +whereClause+" ) as t ) as tb1 ";
		Query query = manager.createNativeQuery(sql+orderby).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer( ReportAccordingToTheRedBookDto.class));
		
		
		if (searchDto.getProvinceId() != null) {
			query.setParameter("provinceId", searchDto.getProvinceId());
		}
		if (searchDto.getDistrictId() != null) {
			query.setParameter("districtId", searchDto.getDistrictId());
		}
		if (searchDto.getCommuneId() != null) {
			query.setParameter("wardId", searchDto.getCommuneId());
		}
		if (searchDto.getAnimalClass() != null) {
			query.setParameter("animalClass", searchDto.getAnimalClass());
		}
		//list animal class
		if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size()>0) {
			query.setParameter("listAnimalClass", searchDto.getListAnimalClass());
		}
		if (searchDto.getAnimalOrdo() != null) {
			query.setParameter("animalOrdo", searchDto.getAnimalOrdo());
		}
		//list animal ordo
		if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size()>0) {
			query.setParameter("listAnimalOrdo", searchDto.getListAnimalOrdo());
		}
		if (searchDto.getAnimalFamily() != null) {
			query.setParameter("animalFamily", searchDto.getAnimalFamily());
		}
		//list animal family
		if (searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size()>0) {
			query.setParameter("listAnimalFamily", searchDto.getListAnimalFamily());
		}
		if (searchDto.getAnimalIds() != null && searchDto.getAnimalIds().size()>0) {
			query.setParameter("animalId", searchDto.getAnimalIds());
		}
		if(searchDto.getYear()!=null){
			query.setParameter("year", searchDto.getYear());
		}

//		String sql = "select new com.globits.wl.dto.functiondto.ReportAccordingToTheRedBookDto(SUM(na.total) as total, na.animal.name as animalName, na.year as year)  "
//				+ "FROM AnimalReportData na where 1=1 and na.year is not null  AND na.month is null AND na.day is null ";
//		String whereClause = "";
//		if (searchDto.getAnimalIds() != null && searchDto.getAnimalIds().size() > 0) {
//			whereClause += " and na.animal.id in (:listAnimalId) ";
//		}
		// if(searchDto.getCommuneId() != null) {
		// whereClause += " and na.farm.administrativeUnit.id = :communeId ";
		// }
		// if(searchDto.getDistrictId() != null) {
		// whereClause += " and na.farm.administrativeUnit.parent.id = :districtId ";
		// }
		// if(searchDto.getProvinceId() != null) {
		// whereClause += " and na.farm.administrativeUnit.parent.parent.id =
		// :provinceId ";
		// }
		// if(!isAdmin) {
		// whereClause += " and (na.farm.administrativeUnit.id in (:listWardId)) ";
		// }
		// sửa lại query đơn vị hành chính của báo cáo
//		if (searchDto.getCommuneId() != null) {
//			whereClause += " and na.administrativeUnit.id = :communeId ";
//		}
//		if (searchDto.getDistrictId() != null) {
//			whereClause += " and na.district.id = :districtId ";
//		}
//		if (searchDto.getProvinceId() != null) {
//			whereClause += " and na.province.id = :provinceId ";
//		}
//		
//		if (searchDto.getProvinceId() != null) {
//			whereClause += " and na.province.id = :provinceId ";
//		}
//		
//		if (searchDto.getAnimalClass() != null) {
//			whereClause += " and na.animal.animalClass = :animalClass ";
//		}
//		
//		if (searchDto.getAnimalOrdo() != null) {
//			whereClause += " and na.animal.ordo = :animalOrdo ";
//		}
//		
//		if (searchDto.getAnimalFamily() != null) {
//			whereClause += " and na.animal.family = :animalFamily ";
//		}
//		
//		if (!isAdmin) {
//			whereClause += " and (na.administrativeUnit.id in (:listWardId)) ";
//		}
//		String groupBy = " group by na.animal.id, na.animal.name, na.year ";
//		Query query = manager.createQuery(sql + whereClause + groupBy, ReportAccordingToTheRedBookDto.class);
//		if (searchDto.getAnimalIds() != null && searchDto.getAnimalIds().size() > 0) {
//			query.setParameter("listAnimalId", searchDto.getAnimalIds());
//		}
//		if (searchDto.getProvinceId() != null) {
//			query.setParameter("provinceId", searchDto.getProvinceId());
//		}
//		if (searchDto.getDistrictId() != null) {
//			query.setParameter("districtId", searchDto.getDistrictId());
//		}
//		if (searchDto.getCommuneId() != null) {
//			query.setParameter("communeId", searchDto.getCommuneId());
//		}
//		
//		if (searchDto.getAnimalClass() != null) {
//			query.setParameter("animalClass", searchDto.getAnimalClass());
//		}
//		
//		if (searchDto.getAnimalOrdo() != null) {
//			query.setParameter("animalOrdo", searchDto.getAnimalOrdo());
//		}
//		
//		if (searchDto.getAnimalFamily() != null) {
//			query.setParameter("animalFamily", searchDto.getAnimalFamily());
//		}
//		
//		if (!isAdmin) {
//			query.setParameter("listWardId", listWardId);
//		}
		result = query.getResultList();
		return result;
	}

	@Override
	public Integer saveListBearReport(List<AnimalReportDataFormDto> dtos) {
		Integer ret = 0;
		if (dtos != null && dtos.size() > 0) {
			for (AnimalReportDataFormDto animalReportDataFormDto : dtos) {
				UUID formId = UUID.randomUUID();
				if (animalReportDataFormDto != null) {
					int count = 0;
					if (animalReportDataFormDto.getTotalBearHasChip() != null
							&& animalReportDataFormDto.getTotalBearHasChip() > 0) {
						AnimalReportDataDto bearHasChip = new AnimalReportDataDto();
						bearHasChip.setAnimal(animalReportDataFormDto.getAnimal());
						bearHasChip.setFarm(animalReportDataFormDto.getFarm());
						bearHasChip.setYear(animalReportDataFormDto.getYear());
						bearHasChip.setQuarter(animalReportDataFormDto.getQuarter());
						bearHasChip.setMonth(animalReportDataFormDto.getMonth());
						bearHasChip.setDay(animalReportDataFormDto.getDay());
						bearHasChip.setType(WLConstant.AnimalReportDataType.hasChip.getValue());
						bearHasChip.setFormType(WLConstant.AnimalReportDataFormType.secondType.getValue());
						bearHasChip.setTotal(animalReportDataFormDto.getTotalBearHasChip());
						bearHasChip.setMale(animalReportDataFormDto.getMaleBearHasChip());
						bearHasChip.setFemale(animalReportDataFormDto.getFemaleBearHasChip());
						bearHasChip.setOriginal(animalReportDataFormDto.getOriginal());
						bearHasChip.setSource(animalReportDataFormDto.getSource());
						bearHasChip.setPurpose(animalReportDataFormDto.getPurpose());
						bearHasChip.setProductTarget(animalReportDataFormDto.getProductTarget());
						bearHasChip.setFormId(formId);
						bearHasChip.setFarmReportPeriodId(animalReportDataFormDto.getFarmReportPeriodId());
						bearHasChip.setIndividualAnimals(new ArrayList<IndividualAnimalDto>());
						if (animalReportDataFormDto.getListChipCode() != null
								&& animalReportDataFormDto.getListChipCode().size() > 0) {
							for (String chipCode : animalReportDataFormDto.getListChipCode()) {
								IndividualAnimalDto individual = new IndividualAnimalDto();
								individual.setCode(chipCode);
								bearHasChip.getIndividualAnimals().add(individual);
							}
						}

						this.saveAnimalReport(null, bearHasChip);
						count += 1;
					}

					if (animalReportDataFormDto.getTotalBearNoChip() != null
							&& animalReportDataFormDto.getTotalBearNoChip() > 0) {
						AnimalReportDataDto bearNoChip = new AnimalReportDataDto();
						bearNoChip.setAnimal(animalReportDataFormDto.getAnimal());
						bearNoChip.setFarm(animalReportDataFormDto.getFarm());
						bearNoChip.setYear(animalReportDataFormDto.getYear());
						bearNoChip.setQuarter(animalReportDataFormDto.getQuarter());
						bearNoChip.setMonth(animalReportDataFormDto.getMonth());
						bearNoChip.setDay(animalReportDataFormDto.getDay());
						bearNoChip.setType(WLConstant.AnimalReportDataType.noChip.getValue());
						bearNoChip.setFormType(WLConstant.AnimalReportDataFormType.secondType.getValue());
						bearNoChip.setTotal(animalReportDataFormDto.getTotalBearNoChip());
						bearNoChip.setMale(animalReportDataFormDto.getMaleBearNoChip());
						bearNoChip.setFemale(animalReportDataFormDto.getFemaleBearNoChip());
						bearNoChip.setOriginal(animalReportDataFormDto.getOriginal());
						bearNoChip.setSource(animalReportDataFormDto.getSource());
						bearNoChip.setPurpose(animalReportDataFormDto.getPurpose());
						bearNoChip.setProductTarget(animalReportDataFormDto.getProductTarget());
						bearNoChip.setFormId(formId);
						bearNoChip.setReasonBornAtFarm(animalReportDataFormDto.getReasonBornAtFarm());
						bearNoChip.setReasonOther(animalReportDataFormDto.getReasonOther());
						bearNoChip.setFarmReportPeriodId(animalReportDataFormDto.getFarmReportPeriodId());
						this.saveAnimalReport(null, bearNoChip);
						count += 1;
					}

					ret += count;
				}
			}
		}
		return ret;
	}

	@Override
	public Integer saveNormalAnimal(List<AnimalReportDataFormDto> dtos) {
		Integer ret = 0;
		if (dtos != null && dtos.size() > 0) {
			for (AnimalReportDataFormDto animalReportDataFormDto : dtos) {
				UUID formId = UUID.randomUUID();
				if (animalReportDataFormDto != null) {
					int count = 0;
					if (animalReportDataFormDto.getTotalNormal() != null
							&& animalReportDataFormDto.getTotalNormal() > 0) {

						AnimalReportDataDto normalAnimal = new AnimalReportDataDto();

						normalAnimal.setAnimal(animalReportDataFormDto.getAnimal());
						normalAnimal.setFarm(animalReportDataFormDto.getFarm());
						normalAnimal.setYear(animalReportDataFormDto.getYear());
						normalAnimal.setQuarter(animalReportDataFormDto.getQuarter());
						normalAnimal.setMonth(animalReportDataFormDto.getMonth());
						normalAnimal.setDay(animalReportDataFormDto.getDay());

						normalAnimal.setFarmReportPeriodId(animalReportDataFormDto.getFarmReportPeriodId());

						normalAnimal.setType(WLConstant.AnimalReportDataType.unDefine.getValue());
						normalAnimal.setFormType(WLConstant.AnimalReportDataFormType.thirdType.getValue());
						normalAnimal.setFormId(formId);
						normalAnimal.setTotal(animalReportDataFormDto.getTotalNormal());

						this.saveAnimalReport(null, normalAnimal);
						count += 1;
					}
					ret += count;
				}
			}
		}
		return ret;
	}

	@Override
	public List<AnimalReportDataDto> convertFromImportExportAnimal2ReportAnimalData(
			AnimalReportDataSearchDto searchDto) {
		List<AnimalReportDataDto> results = new ArrayList<AnimalReportDataDto>();

		List<AnimalReportDataDto> listImportExport = importExportAnimalService.getAllLessThanDate(searchDto);

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}

		for (AnimalReportDataDto dto : listImportExport) {
			List<AnimalReportDataDto> animalReportDatas = animalReportDataRepository.findByYearAndMonth(
					searchDto.getYear(), searchDto.getMonth(), dto.getFarm().getId(), dto.getAnimal().getId());
			AnimalReportDataDto animalReportDataDto = null;
			if (animalReportDatas != null && animalReportDatas.size() > 0) {
				animalReportDataDto = animalReportDatas.get(0);
			}
			Integer total = 0;
			// if(dto.getMale() < 0) {
			// dto.setMale(0);
			// }else {
			// total += dto.getMale();
			// }
			// if(dto.getFemale() < 0) {
			// dto.setFemale(0);
			// }else {
			// total += dto.getFemale();
			// }
			// if(dto.getUnGender() < 0) {
			// dto.setUnGender(0);
			// }else {
			// total += dto.getUnGender();
			// }
			total += dto.getMale();
			total += dto.getFemale();
			total += dto.getUnGender();
			dto.setTotal(total);
			dto.setType(WLConstant.AnimalReportDataType.unDefine.getValue());
			if (animalReportDataDto == null) {
				dto.setYear(searchDto.getYear());
				dto.setMonth(searchDto.getMonth());
				dto.setDay(searchDto.getDay());
				AnimalReportDataDto ret = this.saveAnimalReport(null, dto);
				results.add(ret);
			} else {
				animalReportDataDto.setType(dto.getType());
				animalReportDataDto.setTotal(dto.getTotal());
				animalReportDataDto.setFemale(dto.getFemale());
				animalReportDataDto.setMale(dto.getMale());
				animalReportDataDto.setUnGender(dto.getUnGender());
				animalReportDataDto.setAnimal(dto.getAnimal());
				animalReportDataDto.setFarm(dto.getFarm());
				AnimalReportDataDto ret = this.saveAnimalReport(animalReportDataDto.getId(), animalReportDataDto);
				results.add(ret);
			}
		}

		return results;
	}

	@Override
	public AnimalReportDataDto saveUpdateAnimalReportBy16A(AnimalReportDataDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			AnimalReportData entity = null;
			if (dto.getYear() != null && dto.getMonth() != null && dto.getFarm() != null && dto.getAnimal() != null) {
				List<AnimalReportData> list = animalReportDataRepository.findByYearAndMonthFarmAnimal(dto.getYear(),
						dto.getMonth(), dto.getDay(), dto.getFarm().getId(), dto.getAnimal().getId(), dto.getType());
				if (list != null && list.size() > 0) {
					entity = list.get(0);
				}

			} else {
				return null;
			}

			if (entity == null) {
				entity = new AnimalReportData();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			} else {
				entity.setModifyDate(currentDate);
				entity.setModifiedBy(currentUserName);
			}
			Animal animal = null;
			if (dto.getAnimal() != null && dto.getAnimal().getId() != null) {
				animal = animalRepository.findOne(dto.getAnimal().getId());
			}
			entity.setAnimal(animal);

			Farm farm = null;
			if (dto.getFarm() != null && dto.getFarm().getId() != null) {
				farm = farmRepository.findOne(dto.getFarm().getId());
			}
			entity.setFarm(farm);

			entity.setYear(dto.getYear());
			entity.setQuarter(dto.getQuarter());
			entity.setMonth(dto.getMonth());//
			entity.setDay(dto.getDay());

			entity.setFemale(dto.getFemale());
			entity.setMale(dto.getMale());
			entity.setUnGender(dto.getUnGender());

			Integer total = 0;
			if (entity.getFemale() != null) {
				total += entity.getFemale().intValue();
			}
			if (entity.getMale() != null) {
				total += entity.getMale().intValue();
			}
			if (entity.getUnGender() != null) {
				total += entity.getUnGender().intValue();
			}
			entity.setTotal(total);
			if (dto.getType() == WLConstant.AnimalReportDataType.unDefine.getValue()) {
				entity.setTotal(dto.getTotal());
			}
			FmsAdministrativeUnit administrativeUnits = null;
			if (dto.getAdministrativeUnitDto() != null && dto.getAdministrativeUnitDto().getId() != null) {
				administrativeUnits = fmsAdministrativeUnitRepository.findById(dto.getAdministrativeUnitDto().getId());
				if (administrativeUnits != null) {
					entity.setAdministrativeUnit(administrativeUnits);
					if (administrativeUnits.getParent() != null) {
						entity.setDistrict(administrativeUnits.getParent());
					}
					if (administrativeUnits.getParent().getParent() != null) {
						entity.setProvince(administrativeUnits.getParent().getParent());
					}
				}

			}
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
			ProductTarget productTarget = null;
			if (dto.getProductTarget() != null && dto.getProductTarget().getId() != null) {
				productTarget = productTargetRepository.findOne(dto.getProductTarget().getId());
			}
			entity.setProductTarget(productTarget);
			if (productTarget != null) {
				entity.setPurpose(productTarget.getCode());
			}

			Original original = null;
			if (dto.getOriginal() != null && dto.getOriginal().getId() != null) {
				original = originalRepository.findOne(dto.getOriginal().getId());
			}
			entity.setOriginal(original);
			if (original != null) {
				entity.setSource(original.getCode());
			}
			entity.setType(dto.getType());
			entity.setRegistrationDate(dto.getRegistrationDate());
			Set<IndividualAnimal> individualAnimals = new HashSet<IndividualAnimal>();
			if (dto.getIndividualAnimals() != null && dto.getIndividualAnimals().size() > 0) {
				for (IndividualAnimalDto individualAnimalDto : dto.getIndividualAnimals()) {
					if (individualAnimalDto != null) {
						IndividualAnimal individualAnimal = null;
						if (individualAnimalDto.getId() != null) {
							individualAnimal = individualAnimalRepository.getOne(individualAnimalDto.getId());
						}
						if (individualAnimal == null) {
							individualAnimal = new IndividualAnimal();
							individualAnimal.setCreateDate(currentDate);
							individualAnimal.setCreatedBy(currentUserName);
						} else {
							individualAnimal.setModifiedBy(currentUserName);
							individualAnimal.setModifyDate(currentDate);
						}
						individualAnimal.setAnimalReportData(entity);
						individualAnimal.setName(individualAnimalDto.getName());
						individualAnimal.setCode(individualAnimalDto.getCode());
						individualAnimal.setBirthDate(individualAnimalDto.getBirthDate());
						individualAnimal.setAnimal(animal);
						individualAnimal.setStatus(individualAnimalDto.getStatus());
						individualAnimal.setGender(individualAnimalDto.getGender());
						individualAnimal.setDayOld(individualAnimalDto.getDayOld());

						individualAnimals.add(individualAnimal);
					}
				}
				if (entity.getIndividualAnimals() == null) {
					entity.setIndividualAnimals(individualAnimals);
				} else {
					entity.getIndividualAnimals().clear();
					entity.getIndividualAnimals().addAll(individualAnimals);
				}
			}
			entity.setFormId(dto.getFormId());
			entity.setFormType(dto.getFormType());
			entity.setReasonBornAtFarm(dto.getReasonBornAtFarm());
			entity.setReasonOther(dto.getReasonOther());
			if (dto.getFarmReportPeriodId() != null) {
				FarmReportPeriod frp = farmReportPeriodRepository.findOne(dto.getFarmReportPeriodId());
				entity.setFarmReportPeriod(frp);
			}
			entity = animalReportDataRepository.save(entity);
			return new AnimalReportDataDto(entity);
		}
		return null;
	}

	@Override
	public List<AnimalReportDataDto> saveUpdateListAnimalReportBy16A(List<AnimalReportDataDto> dtos) {
		if (dtos != null && dtos.size() > 0) {
			List<AnimalReportDataDto> ret = new ArrayList<AnimalReportDataDto>();
			for (AnimalReportDataDto animalReportDataDto : dtos) {
				AnimalReportDataDto dto = this.saveUpdateAnimalReportBy16A(animalReportDataDto);
				if (dto != null) {
					ret.add(dto);
				}
			}
			return ret;
		}
		return null;
	}

	@Override
	public Page<AnimalReportDataDto> getPageBySearch(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize) {
		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		boolean isAdministrativeUnitRole = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DISTRICT)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_WARD);
		boolean isFarmerRole = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_FAMER);
		if (!isAdmin && isAdministrativeUnitRole) {
			List<FmsAdministrativeUnit> list = userAdministrativeUnitService.getAdministrativeUnitByLoginUser();
			if (list != null && list.size() > 0) {
				for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
					if (fmsAdministrativeUnit.getParent() == null) {// Cấp tỉnh
						searchDto.setProvinceId(fmsAdministrativeUnit.getId());
					} else if (fmsAdministrativeUnit.getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent() == null) {// Cấp huyện
						searchDto.setDistrictId(fmsAdministrativeUnit.getId());
					} else if (fmsAdministrativeUnit.getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent().getParent() == null) {// Cấp xã
						searchDto.setCommuneId(fmsAdministrativeUnit.getId());
					} else {
						return null;
					}
				}
			} else {
				return null;
			}
		} else if (isFarmerRole) {
			searchDto.setNameOrCode(currentUser.getUsername());
		}
		String namecode = searchDto.getNameOrCode();

		Pageable pageable = new PageRequest(pageIndex, pageSize);
		String sql = "SELECT new com.globits.wl.dto.AnimalReportDataDto(ard) FROM AnimalReportData ard ";
		String sqlCount = "SELECT count(*) FROM AnimalReportData ard ";
		String whereClause = " where 1=1";
		if (searchDto.getCommuneId() != null) {
			whereClause += " and ard.farm.administrativeUnit.id = :wardId";
		}
		if (searchDto.getDistrictId() != null) {
			whereClause += " and ard.farm.administrativeUnit.parent.id = :districtId";
		}
		if (searchDto.getProvinceId() != null) {
			whereClause += " AND ard.farm.administrativeUnit.parent.parent.id = :provinceId";
		}
		if (searchDto.getYear() != null) {
			whereClause += " AND ard.year = :year";
		}
		if (namecode != null && namecode.length() > 0) {
			whereClause += " AND (ard.farm.code like :namecode or ard.farm.name like :namecode or ard.farm.ownerName like :namecode)";
		}
		if (searchDto.getAnimalId() != null) {
			whereClause += " AND ard.animal.id = :animalId";
		}

		Query query = manager.createQuery(sql + whereClause, AnimalReportDataDto.class);
		Query queryCount = manager.createQuery(sqlCount + whereClause);

		query.setFirstResult(pageSize * pageIndex);
		query.setMaxResults(pageSize);
		if (searchDto.getCommuneId() != null) {
			query.setParameter("wardId", searchDto.getCommuneId());
			queryCount.setParameter("wardId", searchDto.getCommuneId());
		}
		if (searchDto.getDistrictId() != null) {
			query.setParameter("districtId", searchDto.getDistrictId());
			queryCount.setParameter("districtId", searchDto.getDistrictId());
		}
		if (searchDto.getProvinceId() != null) {
			query.setParameter("provinceId", searchDto.getProvinceId());
			queryCount.setParameter("provinceId", searchDto.getProvinceId());
		}
		if (searchDto.getYear() != null) {
			query.setParameter("year", searchDto.getYear());
			queryCount.setParameter("year", searchDto.getYear());
		}
		if (namecode != null && namecode.length() > 0) {
			query.setParameter("namecode", "%" + namecode + "%");
			queryCount.setParameter("namecode", "%" + namecode + "%");
		}
		if (searchDto.getAnimalId() != null) {
			query.setParameter("animalId", searchDto.getAnimalId());
			queryCount.setParameter("animalId", searchDto.getAnimalId());
		}

		Long total = 0L;
		Object obj = queryCount.getSingleResult();
		if (obj != null) {
			total = (Long) obj;
		}
		Page<AnimalReportDataDto> page = new PageImpl<AnimalReportDataDto>(query.getResultList(), pageable, total);
		return page;
	}

	@Override
	public Page<AnimalReportDataDto> getViewExcelExportAnimalReportData(AnimalReportDataSearchDto searchDto,
			int pageIndex, int pageSize) {
		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Long provinceId = searchDto.getProvinceId();
		Long districtId = searchDto.getDistrictId();
		Long communeId = searchDto.getCommuneId();
		Integer year = searchDto.getYear();
		Integer quarter = searchDto.getQuarter();
		Integer month = searchDto.getMonth();
		Integer day = searchDto.getDay();
		Long farmId = searchDto.getFarmId();
		Long animalId = searchDto.getAnimalId();
		List<String> listCites = searchDto.getListCites();

		String sql = "SELECT new com.globits.wl.dto.AnimalReportDataDto(a) FROM AnimalReportData a ";
		String sqlCount = "SELECT count(a) FROM AnimalReportData a ";

		String whereClause = " WHERE 1=1 ";

		if (provinceId != null && provinceId > 0L) {
			whereClause += " AND a.farm.administrativeUnit.parent.parent.id= :provinceId ";
		}
		if (districtId != null && districtId > 0L) {
			whereClause += " AND a.farm.administrativeUnit.parent.id= :districtId ";
		}
		if (communeId != null && communeId > 0L) {
			whereClause += " AND a.farm.administrativeUnit.id= :communeId ";
		}
		if (year != null && year > 0) {
			whereClause += " AND a.year= :year ";
		}
		if (quarter != null && quarter > 0) {
			whereClause += " AND a.quarter= :quarter ";
		}
		if (month != null && month > 0) {
			whereClause += " AND a.month= :month ";
		}
		if (day != null && day > 0) {
			whereClause += " AND a.day= :day ";
		}
		if (farmId != null && farmId > 0L) {
			whereClause += " AND a.farm.id= :farmId ";
		}
		if (animalId != null && animalId > 0L) {
			whereClause += " AND a.animal.id= :animalId ";
		}
		if (listCites != null && listCites.size() > 0) {
			whereClause += " AND a.animal.cites in (:listCites) ";
		}

		Query q = manager.createQuery(sql + whereClause
				+ " order by a.farm.administrativeUnit.parent.parent.id, a.farm.administrativeUnit.parent.id, a.farm.administrativeUnit.id",
				AnimalReportDataDto.class);
		Query qCount = manager.createQuery(sqlCount + whereClause);

		q.setFirstResult(pageIndex * pageSize);
		q.setMaxResults(pageSize);

		if (provinceId != null && provinceId > 0L) {
			q.setParameter("provinceId", provinceId);
			qCount.setParameter("provinceId", provinceId);
		}
		if (districtId != null && districtId > 0L) {
			q.setParameter("districtId", districtId);
			qCount.setParameter("districtId", districtId);
		}
		if (communeId != null && communeId > 0L) {
			q.setParameter("communeId", communeId);
			qCount.setParameter("communeId", communeId);
		}
		if (year != null && year > 0L) {
			q.setParameter("year", year);
			qCount.setParameter("year", year);
		}
		if (quarter != null && quarter > 0L) {
			q.setParameter("quarter", quarter);
			qCount.setParameter("quarter", quarter);
		}
		if (month != null && month > 0L) {
			q.setParameter("month", month);
			qCount.setParameter("month", month);
		}
		if (day != null && day > 0L) {
			q.setParameter("day", day);
			qCount.setParameter("day", day);
		}
		if (farmId != null && farmId > 0L) {
			q.setParameter("farmId", farmId);
			qCount.setParameter("farmId", farmId);
		}
		if (animalId != null && animalId > 0L) {
			q.setParameter("animalId", animalId);
			qCount.setParameter("animalId", animalId);
		}
		if (listCites != null && listCites.size() > 0) {
			q.setParameter("listCites", listCites);
			qCount.setParameter("listCites", listCites);
		}
		Long total = 0L;
		Object obj = qCount.getSingleResult();
		if (obj != null) {
			total = (Long) obj;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<AnimalReportDataDto> page = new PageImpl<AnimalReportDataDto>(q.getResultList(), pageable, total);
		return page;
	}

	@Override
	public List<AnimalDto> getAllAnimalReported(Long farmId) {
		String sql = "select distinct new com.globits.wl.dto.AnimalDto(ard.animal) FROM AnimalReportData ard where ard.farm.id = :farmId";
		Query query = manager.createQuery(sql, AnimalDto.class);
		query.setParameter("farmId", farmId);
		List<AnimalDto> list = query.getResultList();

		return list;
	}

	/** Bản ghi các animal cuối cùng của 1 trại */
	@Override
	public List<FarmAnimalTotalDto> getAllAnimalLastReported(Long farmId) {
		List<FarmAnimalTotalDto> result = new ArrayList<FarmAnimalTotalDto>();

		String sqlReport = "select new com.globits.wl.dto.functiondto.FarmAnimalTotalDto(ard.farm, ard.animal, max(ard.total), ard.year, ard.month, ard.day, ard.type) FROM AnimalReportData ard where ard.farm.id = :farmId GROUP BY ard.farm, ard.animal, ard.year, ard.month, ard.day, ard.type";
		Query queryReport = manager.createQuery(sqlReport, FarmAnimalTotalDto.class);
		queryReport.setParameter("farmId", farmId);
		List<FarmAnimalTotalDto> listReport = queryReport.getResultList();
		Map<String, FarmAnimalTotalDto> map = new HashMap<String, FarmAnimalTotalDto>();
		for (FarmAnimalTotalDto farmAnimalTotalDto : listReport) {
			if (farmAnimalTotalDto.getFarmCode() != null && farmAnimalTotalDto.getAnimalCode() != null) {
				String key = farmAnimalTotalDto.getFarmCode() + "-" + farmAnimalTotalDto.getAnimalCode() + "-"
						+ farmAnimalTotalDto.getType();
				if (map.containsKey(key)) {
					FarmAnimalTotalDto itemValue = map.get(key);

					int day1 = itemValue.getDay() != null ? itemValue.getDay().intValue() : 1;
					int month1 = itemValue.getMonth() != null ? itemValue.getMonth().intValue() : 1;
					int year1 = itemValue.getYear() != null ? itemValue.getYear().intValue() : 1;

					int day2 = farmAnimalTotalDto.getDay() != null ? farmAnimalTotalDto.getDay().intValue() : 1;
					int month2 = farmAnimalTotalDto.getMonth() != null ? farmAnimalTotalDto.getMonth().intValue() : 1;
					int year2 = farmAnimalTotalDto.getYear() != null ? farmAnimalTotalDto.getYear().intValue() : 1;

					try {
						Date date1 = WLDateTimeUtil.numberToDate(day1, month1, year1);
						Date date2 = WLDateTimeUtil.numberToDate(day2, month2, year2);
						if (date1.compareTo(date2) < 0) {
							map.put(key, farmAnimalTotalDto);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					map.put(key, farmAnimalTotalDto);
				}
			}
		}

		Map<String, FarmAnimalTotalDto> mapAnimal = new HashMap<String, FarmAnimalTotalDto>();
		for (FarmAnimalTotalDto item : map.values()) {
			String key = item.getAnimalCode();
			if (mapAnimal.containsKey(key)) {
				FarmAnimalTotalDto itemValue = mapAnimal.get(key);
				if (item.getTotal() != null) {
					Integer total = itemValue.getTotal().intValue() + item.getTotal().intValue();
					itemValue.setTotal(total);
				}
			} else {
				item.setType(null);
				mapAnimal.put(key, item);
			}
		}

		for (FarmAnimalTotalDto item : mapAnimal.values()) {
			if (item != null) {
				result.add(item);
			}
		}

		return result;
	}

	/**
	 * Bản ghi các animal cuối cùng của 1 trại theo bản ghi có month is null and day
	 * is null
	 */
	@Override
	public List<FarmAnimalTotalDto> getAllAnimalLastReportedByRecordMonthDayIsNull(Long farmId, Integer year) {
		List<FarmAnimalTotalDto> result = new ArrayList<FarmAnimalTotalDto>();

		String sqlReport = "select new com.globits.wl.dto.functiondto.FarmAnimalTotalDto(ard.farm, ard.animal, SUM(ard.total), ard.year) "
				+ " FROM AnimalReportData ard "
				+ " where ard.farm.id = :farmId AND ard.month is null and ard.day is null ";
		if (year != null && year > 0) {
			sqlReport += " AND ard.year = :year ";
		}
		sqlReport += " GROUP BY ard.farm, ard.animal, ard.year";
		Query queryReport = manager.createQuery(sqlReport, FarmAnimalTotalDto.class);
		queryReport.setParameter("farmId", farmId);
		if (year != null && year > 0) {
			queryReport.setParameter("year", year);
		}
		result = queryReport.getResultList();

		return result;
	}

	@Override
	public List<FarmAnimalTotalDto> getAllAnimalLastReportedByFarmIdAndAnimalId(Long farmId, Long animalId) {
		List<FarmAnimalTotalDto> result = new ArrayList<FarmAnimalTotalDto>();
		String sqlReport = "select new com.globits.wl.dto.functiondto.FarmAnimalTotalDto(ard.farm, ard.animal, max(ard.total), ard.year, ard.month, ard.day, ard.type) "
				+ "FROM AnimalReportData ard where ard.farm.id = :farmId and ard.animal.id = :animalId "
				+ "GROUP BY ard.farm, ard.animal, ard.year, ard.month, ard.day, ard.type ";
		Query queryReport = manager.createQuery(sqlReport, FarmAnimalTotalDto.class);
		queryReport.setParameter("farmId", farmId);
		queryReport.setParameter("animalId", animalId);
		List<FarmAnimalTotalDto> listReport = queryReport.getResultList();
		Map<String, FarmAnimalTotalDto> map = new HashMap<String, FarmAnimalTotalDto>();
		for (FarmAnimalTotalDto farmAnimalTotalDto : listReport) {
			if (farmAnimalTotalDto.getFarmCode() != null && farmAnimalTotalDto.getAnimalCode() != null) {
				String key = farmAnimalTotalDto.getFarmCode() + "-" + farmAnimalTotalDto.getAnimalCode() + "-"
						+ farmAnimalTotalDto.getType();
				if (map.containsKey(key)) {
					FarmAnimalTotalDto itemValue = map.get(key);

					int day1 = itemValue.getDay() != null ? itemValue.getDay().intValue() : 1;
					int month1 = itemValue.getMonth() != null ? itemValue.getMonth().intValue() : 1;
					int year1 = itemValue.getYear() != null ? itemValue.getYear().intValue() : 1;

					int day2 = farmAnimalTotalDto.getDay() != null ? farmAnimalTotalDto.getDay().intValue() : 1;
					int month2 = farmAnimalTotalDto.getMonth() != null ? farmAnimalTotalDto.getMonth().intValue() : 1;
					int year2 = farmAnimalTotalDto.getYear() != null ? farmAnimalTotalDto.getYear().intValue() : 1;

					try {
						Date date1 = WLDateTimeUtil.numberToDate(day1, month1, year1);
						Date date2 = WLDateTimeUtil.numberToDate(day2, month2, year2);
						if (date1.compareTo(date2) < 0) {
							map.put(key, farmAnimalTotalDto);
						}
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else {
					map.put(key, farmAnimalTotalDto);
				}
			}
		}

		Map<String, FarmAnimalTotalDto> mapAnimal = new HashMap<String, FarmAnimalTotalDto>();
		for (FarmAnimalTotalDto item : map.values()) {
			String key = item.getAnimalCode();
			if (mapAnimal.containsKey(key)) {
				FarmAnimalTotalDto itemValue = mapAnimal.get(key);
				if (item.getTotal() != null) {
					Integer total = itemValue.getTotal().intValue() + item.getTotal().intValue();
					itemValue.setTotal(total);
				}
			} else {
				item.setType(null);
				mapAnimal.put(key, item);
			}
		}

		for (FarmAnimalTotalDto item : mapAnimal.values()) {
			if (item != null) {
				result.add(item);
			}
		}

		return result;
	}

	// Cập nhật 1 trại chưa lên bản đồ
	@Override
	public List<AnimalReportData> updateOneFarmtoMap(Boolean isConvert, String farmCode, int year) {
		List<AnimalReportData> result = new ArrayList<AnimalReportData>();
		List<Farm> farms = farmRepository.findByCode(farmCode);
		if (farms != null && farms.size() > 0) {
			for (Farm farm : farms) {
				if (farm.getId() != null) {

					if (isConvert) {
						SearchReportPeriodDto searchDto = new SearchReportPeriodDto();
						searchDto.setFarmId(farm.getId());
						searchDto.setPageIndex(1);
						searchDto.setPageSize(100000);
						// Kết xuất ReportForm16 về AnimalReportData
						reportPeriodService.updateDataFromReportPeriodToAnimalReportDataForAll(searchDto);

						// List<Long> listAnimalIdInForm16 =
						// this.reportForm16Repository.getListAnimalIdByFarmId(farm.getId());

						List<Long> listAnimalIdInAnimalReportData = this.animalReportDataRepository
								.getListByFarmIdAndYear(farm.getId(), year);
						List<Long> listAnimalIds = new ArrayList<Long>();
						// if(listAnimalIdInForm16 != null && listAnimalIdInForm16.size() >0) {
						// for(Long animalId:listAnimalIdInForm16) {
						// listAnimalIds.add(animalId);
						// }
						// }
						if (listAnimalIdInAnimalReportData != null && listAnimalIdInAnimalReportData.size() > 0) {
							for (Long animalId : listAnimalIdInAnimalReportData) {
								listAnimalIds.add(animalId);
							}
						}
						/* System.out.println("Cập nhật trại: " + farm.getCode()); */
						/* System.out.println("Số loài cập nhật: " + listAnimalIds.size()); */
						// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
						result = this.updateAnimalReportDataByFarmAnimalYear(farm.getId(), listAnimalIds, year);
						/* System.out.println("Kết quả cập nhật: " + result); */
					}

					// gọi hàm cập nhật farm (Nếu chưa có farm sẽ tạo mới..)
					FarmMapServiceUtil.updateFarmToMap(farm);
					
					// Đẩy dữ liệu sang Map
					AnimalReportDataSearchDto search = new AnimalReportDataSearchDto();
					search.setFarmId(farm.getId());
					search.setYear(year);
					List<Report18Dto> animalLastYear= reportService.reportActivityOfEndangeredPreciousRareNormarlAndCitesNativeQuery(search);
					if(animalLastYear!=null && animalLastYear.size()>0) {
						for(Report18Dto a : animalLastYear) {
							FarmAnimalTotalDto fa = new FarmAnimalTotalDto();
							fa.setFarmCode(a.getFarmCode());
							fa.setAnimalCode(a.getAnimalCode());
							fa.setTotal(a.getTotal());
							fa.setYear(a.getYear());
							try {
								FarmMapServiceUtil.pushFarmAnimalMap(fa);
							} catch (Exception e) {
								continue;
							}
							
							
						}
					}
					
					
					
//					List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService
//							.getAllAnimalLastReportedByRecordMonthDayIsNull(farm.getId(), year);
//					if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
//						for (FarmAnimalTotalDto farmAnimalTotalDto : listAnimalReportTotal) {
//							if (farmAnimalTotalDto != null) {
//								try {
//									/* System.out.println(farmAnimalTotalDto); */
//									FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
//								} catch (Exception e) {
//									continue;
//									// TODO: handle exception
//								}
//							}
//						}
//					}
				}
			}
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	public FarmToSyncMap getFarmtoMap(Boolean isConvert, String farmCode, int year) {
		FarmToSyncMap result = new FarmToSyncMap();
		//List<AnimalReportData> result = new ArrayList<AnimalReportData>();
		List<Farm> farms = farmRepository.findByCode(farmCode);
		if (farms != null && farms.size() > 0) {
			for (Farm farm : farms) {
				if (farm.getId() != null) {

					if (isConvert) {
						SearchReportPeriodDto searchDto = new SearchReportPeriodDto();
						searchDto.setFarmId(farm.getId());
						searchDto.setPageIndex(1);
						searchDto.setPageSize(100000);
						// Kết xuất ReportForm16 về AnimalReportData
						reportPeriodService.updateDataFromReportPeriodToAnimalReportDataForAll(searchDto);

						List<Long> listAnimalIdInAnimalReportData = this.animalReportDataRepository
								.getListByFarmIdAndYear(farm.getId(), year);
						List<Long> listAnimalIds = new ArrayList<Long>();
						if (listAnimalIdInAnimalReportData != null && listAnimalIdInAnimalReportData.size() > 0) {
							for (Long animalId : listAnimalIdInAnimalReportData) {
								listAnimalIds.add(animalId);
							}
						}
						List<AnimalReportData> list = this.updateAnimalReportDataByFarmAnimalYear(farm.getId(), listAnimalIds, year);
					}

					// gọi hàm cập nhật farm (Nếu chưa có farm sẽ tạo mới..)
					//FarmMapServiceUtil.updateFarmToMap(farm);
					result.setFarm(farm);
					// Đẩy dữ liệu sang Map
					List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByRecordMonthDayIsNull(farm.getId(), year);
					result.setListAnimalReportTotal(listAnimalReportTotal);
					if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
						
//						for (FarmAnimalTotalDto farmAnimalTotalDto : listAnimalReportTotal) {
//							if (farmAnimalTotalDto != null) {
//								try {
//									/* System.out.println(farmAnimalTotalDto); */
//									result.getListAnimalReportTotal().add(farmAnimalTotalDto)
//									//FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
//								} catch (Exception e) {
//									continue;
//									// TODO: handle exception
//								}
//							}
//						}
					}
				}
			}
			if (result != null) {
				return result;
			}
		}
		return null;
	}
	
	private void updateAnimalReportDataInThread(FarmSearchDto farmSearchDto, int pageIndex, int pageSize,int year) {
		Page<FarmDto> pageFarms = this.farmService.searchFarmDto(farmSearchDto, pageIndex, pageSize);
		for (FarmDto farm : pageFarms) {
			if (farm != null && farm.getCode() != null) {
				this.updateOneFarmtoMap(farmSearchDto.getIsConvert(), farm.getCode(), year);
			}
		}
	}
	@Override
	public void updateAnimalReportDataToRecordMonthDayIsNull(FarmSearchDto farmSearchDto, int pageIndex, int pageSize,
			int year) {
//		farmSearchDto.setYear(year);
//		Page<FarmDto> pageFarms = this.farmService.searchFarmDto(farmSearchDto, pageIndex, pageSize);
//		List<FarmToSyncMap> list = new ArrayList<FarmToSyncMap>();
//		for (FarmDto farm : pageFarms) {
//			if (farm != null && farm.getCode() != null) {
//				FarmToSyncMap map =this.getFarmtoMap(farmSearchDto.getIsConvert(), farm.getCode(), year);
//				list.add(map);
//			}
//		}		
//		 PrimeRun p = new PrimeRun(list);
//		 new Thread(p).start();
		farmSearchDto.setYearRegistration(String.valueOf(year));
		Page<FarmDto> pageFarms = this.farmService.searchFarmDto(farmSearchDto, pageIndex, pageSize);		
		for (FarmDto farm : pageFarms) {
			if (farm != null && farm.getCode() != null) {
				this.updateOneFarmtoMap(farmSearchDto.getIsConvert(), farm.getCode(), year);
			}
		}
	}

	// Cập nhật các trại chưa lên bản đồ
	@Override
	public List<AnimalReportDataDto> updateAllFarmToMap(int pageIndex, int pageSize, int year,
			FarmSearchDto farmSearchDto) {
		List<AnimalReportDataDto> ret = new ArrayList<AnimalReportDataDto>();
		Page<FarmDto> pageFarms = this.farmService.searchFarmDto(farmSearchDto, pageIndex, pageSize);
		for (FarmDto farm : pageFarms) {
			if (farm.getId() != null) {
				Farm f = farmRepository.findOne(farm.getId());
				if (f != null) {
					try {
						MapReturnDto obj = FarmMapServiceUtil.updateFarmToMap(f);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						continue;
					}

					List<Long> listAnimalIds = this.animalReportDataRepository.getListAnimalIdByFarmId(farm.getId());
					/* System.out.print(pageFarms.getSize()); */
					/* System.out.print("Cập nhật trại: " + farm.getCode()); */

					SearchReportPeriodDto searchDto = new SearchReportPeriodDto();
					searchDto.setFarmId(farm.getId());
					searchDto.setPageIndex(1);
					searchDto.setPageSize(100000);
					// Kết xuất ReportForm16 về AnimalReportData
					reportPeriodService.updateDataFromReportPeriodToAnimalReportDataForAll(searchDto);

					// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
					List<AnimalReportData> result = this.updateAnimalReportDataByFarmAnimalYear(farm.getId(),
							listAnimalIds, year);
					// Đẩy dữ liệu sang Map
					List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService
							.getAllAnimalLastReportedByRecordMonthDayIsNull(farm.getId(), null);
					if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
						for (FarmAnimalTotalDto farmAnimalTotalDto : listAnimalReportTotal) {
							if (farmAnimalTotalDto != null) {
								try {
									FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
									continue;
								}
							}
						}
					}
					if (result != null && result.size() > 0) {
						for (AnimalReportData animalReportData : result) {
							ret.add(new AnimalReportDataDto(animalReportData));
						}
					}
				}
			}
		}
		return ret;
	}

	/**
	 * Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo loại cho 1 trại, trong
	 * 1 năm (GROUP BY farmId, year, type)
	 */
	@Override
	public List<AnimalReportData> updateAnimalReportDataByFarmAnimalYear(Long farmId, List<Long> animalIds,
			Integer year) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}

		List<AnimalReportData> result = new ArrayList<AnimalReportData>();
		Farm farm = null;
		farm = farmRepository.findOne(farmId);
		Animal animal = null;
		for (Long animalId : animalIds) {
			if (animalId == null) {
				continue;
			}
			animal = animalRepository.findOne(animalId);
			AnimalReportData rawAnimalParen = null;
			AnimalReportData rawChildOver1YearOld = null;
			AnimalReportData rawChildUnder1YearOld = null;
			AnimalReportData rawGilts = null;
			// AnimalReportData rawImport = null;
			// AnimalReportData rawExport = null;
			List<AnimalReportData> listRawAnimalParen = animalReportDataRepository
					.findByYearAndMonthFarmAnimalAndMonthDayNull(year, farmId, animalId,
							WLConstant.AnimalReportDataType.animalParent.getValue());
			if (listRawAnimalParen != null && listRawAnimalParen.size() > 0) {
				rawAnimalParen = listRawAnimalParen.get(0);
			}
			List<AnimalReportData> listChildOver1YearOld = animalReportDataRepository
					.findByYearAndMonthFarmAnimalAndMonthDayNull(year, farmId, animalId,
							WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
			if (listChildOver1YearOld != null && listChildOver1YearOld.size() > 0) {
				rawChildOver1YearOld = listChildOver1YearOld.get(0);
			}
			List<AnimalReportData> listChildUnder1YearOld = animalReportDataRepository
					.findByYearAndMonthFarmAnimalAndMonthDayNull(year, farmId, animalId,
							WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
			if (listChildUnder1YearOld != null && listChildUnder1YearOld.size() > 0) {
				rawChildUnder1YearOld = listChildUnder1YearOld.get(0);
			}
			List<AnimalReportData> listGilts = animalReportDataRepository.findByYearAndMonthFarmAnimalAndMonthDayNull(
					year, farmId, animalId, WLConstant.AnimalReportDataType.gilts.getValue());
			if (listGilts != null && listGilts.size() > 0) {
				rawGilts = listGilts.get(0);
			}

			// List<AnimalReportData> listimport =
			// animalReportDataRepository.findByYearAndMonthFarmAnimalAndMonthDayNull(year,
			// farmId, animalId, WLConstant.AnimalReportDataType.importAnimal.getValue());
			// if(listimport != null && listimport .size() > 0) {
			// rawImport = listimport.get(0);
			// }

			// List<AnimalReportData> listExport =
			// animalReportDataRepository.findByYearAndMonthFarmAnimalAndMonthDayNull(year,
			// farmId, animalId, WLConstant.AnimalReportDataType.exportAnimal.getValue());
			// if(listExport != null && listExport .size() > 0) {
			// rawExport = listExport.get(0);
			// }

			AnimalReportData totalAnimalParent = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.animalParent.getValue(), year);

			AnimalReportData totalChildOver1YearOld = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.childOver1YearOld.getValue(), year);

			AnimalReportData totalChildUnder1YearOld = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.childUnder1YearOld.getValue(), year);

			AnimalReportData totalGilts = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.gilts.getValue(), year);

			AnimalReportData totalImport = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.importAnimal.getValue(), year);

			// AnimalReportData totalExport = getAnimalReportDataLastUpdateBy(farmId,
			// animalId, WLConstant.AnimalReportDataType.exportAnimal.getValue(), year);

			if (totalAnimalParent != null) {
				if (rawAnimalParen == null) {
					rawAnimalParen = new AnimalReportData();
					rawAnimalParen.setCreateDate(currentDate);
					rawAnimalParen.setCreatedBy(currentUserName);
					rawAnimalParen.setAnimal(animal);
					rawAnimalParen.setFarm(farm);
					rawAnimalParen.setYear(year);
					rawAnimalParen.setType(WLConstant.AnimalReportDataType.animalParent.getValue());
				}
				rawAnimalParen.setTotal(totalAnimalParent.getTotal() != null ? totalAnimalParent.getTotal() : 0);
				rawAnimalParen.setMale(totalAnimalParent.getMale() != null ? totalAnimalParent.getMale() : 0);
				rawAnimalParen.setFemale(totalAnimalParent.getFemale() != null ? totalAnimalParent.getFemale() : 0);
				rawAnimalParen
						.setUnGender(totalAnimalParent.getUnGender() != null ? totalAnimalParent.getUnGender() : 0);
				rawAnimalParen.setAdministrativeUnit(totalAnimalParent.getAdministrativeUnit());
				rawAnimalParen.setDistrict(totalAnimalParent.getDistrict());
				rawAnimalParen.setProvince(totalAnimalParent.getProvince());
				if(totalAnimalParent.getAnimal() != null) {
					if(totalAnimalParent.getAnimal().getVnlist() != null) {
						rawAnimalParen.setVnlist(totalAnimalParent.getAnimal().getVnlist());
					}else {
						rawAnimalParen.setVnlist("");
					}
					if(totalAnimalParent.getAnimal().getVnlist06() != null) {
						rawAnimalParen.setVnlist06(totalAnimalParent.getAnimal().getVnlist06());
					}else {
						rawAnimalParen.setVnlist06("");
					}
					if(totalAnimalParent.getAnimal().getCites() != null) {
						rawAnimalParen.setCites(totalAnimalParent.getAnimal().getCites());
					}else {
						rawAnimalParen.setCites("");
					}
					String animalGroupByYear = "";
					AnimalDto animalDto = animalRepository.getAnimalById(animalId);
					animalGroupByYear = animalServiceImpl.updateAnimalGroup(animalDto);
					if(animalGroupByYear != null && animalGroupByYear.length() >0) {
						rawAnimalParen.setAnimalGroup(animalGroupByYear);
					}
				}
				rawAnimalParen = this.animalReportDataRepository.save(rawAnimalParen);
				result.add(rawAnimalParen);
			} else if (rawAnimalParen != null) {
				rawAnimalParen.setTotal(0);
				rawAnimalParen.setMale(0);
				rawAnimalParen.setFemale(0);
				rawAnimalParen.setUnGender(0);
				rawAnimalParen.setAdministrativeUnit(rawAnimalParen.getFarm().getAdministrativeUnit());
				rawAnimalParen.setDistrict(rawAnimalParen.getFarm().getAdministrativeUnit().getParent());
				rawAnimalParen.setProvince(rawAnimalParen.getFarm().getAdministrativeUnit().getParent().getParent());
				if(rawAnimalParen.getAnimal() != null) {
					if(rawAnimalParen.getAnimal().getVnlist() != null) {
						rawAnimalParen.setVnlist(rawAnimalParen.getAnimal().getVnlist());
					}else {
						rawAnimalParen.setVnlist("");
					}
					if(rawAnimalParen.getAnimal().getVnlist06() != null) {
						rawAnimalParen.setVnlist06(rawAnimalParen.getAnimal().getVnlist06());
					}else {
						rawAnimalParen.setVnlist06("");
					}
					if(rawAnimalParen.getAnimal().getCites() != null) {
						rawAnimalParen.setCites(rawAnimalParen.getAnimal().getCites());
					}else {
						rawAnimalParen.setCites("");
					}
					String animalGroupByYear = "";
					AnimalDto animalDto = animalRepository.getAnimalById(animalId);
					animalGroupByYear = animalServiceImpl.updateAnimalGroup(animalDto);
					if(animalGroupByYear != null && animalGroupByYear.length() >0) {
						rawAnimalParen.setAnimalGroup(animalGroupByYear);
					}
				}
				rawAnimalParen = this.animalReportDataRepository.save(rawAnimalParen);
				result.add(rawAnimalParen);
			}

			if (totalChildOver1YearOld != null) {
				if (rawChildOver1YearOld == null) {
					rawChildOver1YearOld = new AnimalReportData();
					rawChildOver1YearOld.setCreateDate(currentDate);
					rawChildOver1YearOld.setCreatedBy(currentUserName);
					rawChildOver1YearOld.setAnimal(animal);
					rawChildOver1YearOld.setFarm(farm);
					rawChildOver1YearOld.setYear(year);
					rawChildOver1YearOld.setType(WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
				}
				rawChildOver1YearOld
						.setTotal(totalChildOver1YearOld.getTotal() != null ? totalChildOver1YearOld.getTotal() : 0);
				rawChildOver1YearOld
						.setMale(totalChildOver1YearOld.getMale() != null ? totalChildOver1YearOld.getMale() : 0);
				rawChildOver1YearOld
						.setFemale(totalChildOver1YearOld.getFemale() != null ? totalChildOver1YearOld.getFemale() : 0);
				rawChildOver1YearOld.setUnGender(
						totalChildOver1YearOld.getUnGender() != null ? totalChildOver1YearOld.getUnGender() : 0);

				rawChildOver1YearOld.setAdministrativeUnit(totalChildOver1YearOld.getAdministrativeUnit());
				rawChildOver1YearOld.setDistrict(totalChildOver1YearOld.getDistrict());
				rawChildOver1YearOld.setProvince(totalChildOver1YearOld.getProvince());
				if(totalChildOver1YearOld.getAnimal() != null) {
					if(totalChildOver1YearOld.getAnimal().getVnlist() != null) {
						rawChildOver1YearOld.setVnlist(totalChildOver1YearOld.getAnimal().getVnlist());
					}else {
						rawChildOver1YearOld.setVnlist("");
					}
					if(totalChildOver1YearOld.getAnimal().getVnlist06() != null) {
						rawChildOver1YearOld.setVnlist06(totalChildOver1YearOld.getAnimal().getVnlist06());
					}else {
						rawChildOver1YearOld.setVnlist06("");
					}
					if(totalChildOver1YearOld.getAnimal().getCites() != null) {
						rawChildOver1YearOld.setCites(totalChildOver1YearOld.getAnimal().getCites());
					}else {
						rawChildOver1YearOld.setCites("");
					}
					String animalGroupByYear = "";
					AnimalDto animalDto = animalRepository.getAnimalById(animalId);
					animalGroupByYear = animalServiceImpl.updateAnimalGroup(animalDto);
					if(animalGroupByYear != null && animalGroupByYear.length() >0) {
						rawChildOver1YearOld.setAnimalGroup(animalGroupByYear);
					}
				}
				rawChildOver1YearOld = this.animalReportDataRepository.save(rawChildOver1YearOld);
				result.add(rawChildOver1YearOld);

			} else if (rawChildOver1YearOld != null) {
				rawChildOver1YearOld.setTotal(0);
				rawChildOver1YearOld.setMale(0);
				rawChildOver1YearOld.setFemale(0);
				rawChildOver1YearOld.setUnGender(0);

				rawChildOver1YearOld.setAdministrativeUnit(rawChildOver1YearOld.getFarm().getAdministrativeUnit());
				rawChildOver1YearOld.setDistrict(rawChildOver1YearOld.getFarm().getAdministrativeUnit().getParent());
				rawChildOver1YearOld
						.setProvince(rawChildOver1YearOld.getFarm().getAdministrativeUnit().getParent().getParent());
				if(rawChildOver1YearOld.getAnimal() != null) {
					if(rawChildOver1YearOld.getAnimal().getVnlist() != null) {
						rawChildOver1YearOld.setVnlist(rawChildOver1YearOld.getAnimal().getVnlist());
					}else {
						rawChildOver1YearOld.setVnlist("");
					}
					if(rawChildOver1YearOld.getAnimal().getVnlist06() != null) {
						rawChildOver1YearOld.setVnlist06(rawChildOver1YearOld.getAnimal().getVnlist06());
					}else {
						rawChildOver1YearOld.setVnlist06("");
					}
					if(rawChildOver1YearOld.getAnimal().getCites() != null) {
						rawChildOver1YearOld.setCites(rawChildOver1YearOld.getAnimal().getCites());
					}else {
						rawChildOver1YearOld.setCites("");
					}
					String animalGroupByYear = "";
					AnimalDto animalDto = animalRepository.getAnimalById(animalId);
					animalGroupByYear = animalServiceImpl.updateAnimalGroup(animalDto);
					if(animalGroupByYear != null && animalGroupByYear.length() >0) {
						rawChildOver1YearOld.setAnimalGroup(animalGroupByYear);
					}
				}
				rawChildOver1YearOld = this.animalReportDataRepository.save(rawChildOver1YearOld);
				result.add(rawChildOver1YearOld);
			}
			if (totalChildUnder1YearOld != null) {
				if (rawChildUnder1YearOld == null) {
					rawChildUnder1YearOld = new AnimalReportData();
					rawChildUnder1YearOld.setCreateDate(currentDate);
					rawChildUnder1YearOld.setCreatedBy(currentUserName);
					rawChildUnder1YearOld.setAnimal(animal);
					rawChildUnder1YearOld.setFarm(farm);
					rawChildUnder1YearOld.setYear(year);
					rawChildUnder1YearOld.setType(WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
				}
				rawChildUnder1YearOld
						.setTotal(totalChildUnder1YearOld.getTotal() != null ? totalChildUnder1YearOld.getTotal() : 0);
				rawChildUnder1YearOld
						.setMale(totalChildUnder1YearOld.getMale() != null ? totalChildUnder1YearOld.getMale() : 0);
				rawChildUnder1YearOld.setFemale(
						totalChildUnder1YearOld.getFemale() != null ? totalChildUnder1YearOld.getFemale() : 0);
				rawChildUnder1YearOld.setUnGender(
						totalChildUnder1YearOld.getUnGender() != null ? totalChildUnder1YearOld.getUnGender() : 0);

				rawChildUnder1YearOld.setAdministrativeUnit(totalChildUnder1YearOld.getAdministrativeUnit());
				rawChildUnder1YearOld.setDistrict(totalChildUnder1YearOld.getDistrict());
				rawChildUnder1YearOld.setProvince(totalChildUnder1YearOld.getProvince());
				
				if(totalChildUnder1YearOld.getAnimal() != null) {
					if(totalChildUnder1YearOld.getAnimal().getVnlist() != null) {
						rawChildUnder1YearOld.setVnlist(totalChildUnder1YearOld.getAnimal().getVnlist());
					}else {
						rawChildUnder1YearOld.setVnlist("");
					}
					if(totalChildUnder1YearOld.getAnimal().getVnlist06() != null) {
						rawChildUnder1YearOld.setVnlist06(totalChildUnder1YearOld.getAnimal().getVnlist06());
					}else {
						rawChildUnder1YearOld.setVnlist06("");
					}
					if(totalChildUnder1YearOld.getAnimal().getCites() != null) {
						rawChildUnder1YearOld.setCites(totalChildUnder1YearOld.getAnimal().getCites());
					}else {
						rawChildUnder1YearOld.setCites("");
					}
					String animalGroupByYear = "";
					AnimalDto animalDto = animalRepository.getAnimalById(animalId);
					animalGroupByYear = animalServiceImpl.updateAnimalGroup(animalDto);
					if(animalGroupByYear != null && animalGroupByYear.length() >0) {
						rawChildUnder1YearOld.setAnimalGroup(animalGroupByYear);
					}
				}
				rawChildUnder1YearOld = this.animalReportDataRepository.save(rawChildUnder1YearOld);
				result.add(rawChildUnder1YearOld);
			} else if (rawChildUnder1YearOld != null) {
				rawChildUnder1YearOld.setTotal(0);
				rawChildUnder1YearOld.setMale(0);
				rawChildUnder1YearOld.setFemale(0);
				rawChildUnder1YearOld.setUnGender(0);

				rawChildUnder1YearOld.setAdministrativeUnit(rawChildUnder1YearOld.getFarm().getAdministrativeUnit());
				rawChildUnder1YearOld.setDistrict(rawChildUnder1YearOld.getFarm().getAdministrativeUnit().getParent());
				rawChildUnder1YearOld
						.setProvince(rawChildUnder1YearOld.getFarm().getAdministrativeUnit().getParent().getParent());
				if(rawChildUnder1YearOld.getAnimal() != null) {
					if(rawChildUnder1YearOld.getAnimal().getVnlist() != null) {
						rawChildUnder1YearOld.setVnlist(rawChildUnder1YearOld.getAnimal().getVnlist());
					}else {
						rawChildUnder1YearOld.setVnlist("");
					}
					if(rawChildUnder1YearOld.getAnimal().getVnlist06() != null) {
						rawChildUnder1YearOld.setVnlist06(rawChildUnder1YearOld.getAnimal().getVnlist06());
					}else {
						rawChildUnder1YearOld.setVnlist06("");
					}
					if(rawChildUnder1YearOld.getAnimal().getCites() != null) {
						rawChildUnder1YearOld.setCites(rawChildUnder1YearOld.getAnimal().getCites());
					}else {
						rawChildUnder1YearOld.setCites("");
					}
					String animalGroupByYear = "";
					AnimalDto animalDto = animalRepository.getAnimalById(animalId);
					animalGroupByYear = animalServiceImpl.updateAnimalGroup(animalDto);
					if(animalGroupByYear != null && animalGroupByYear.length() >0) {
						rawChildUnder1YearOld.setAnimalGroup(animalGroupByYear);
					}
				}
				rawChildUnder1YearOld = this.animalReportDataRepository.save(rawChildUnder1YearOld);
				result.add(rawChildUnder1YearOld);
			}

			if (totalGilts != null) {
				if (rawGilts == null) {
					rawGilts = new AnimalReportData();
					rawGilts.setCreateDate(currentDate);
					rawGilts.setCreatedBy(currentUserName);
					rawGilts.setAnimal(animal);
					rawGilts.setFarm(farm);
					rawGilts.setYear(year);
					rawGilts.setType(WLConstant.AnimalReportDataType.gilts.getValue());
				}
				rawGilts.setTotal(totalGilts.getTotal() != null ? totalGilts.getTotal() : 0);
				rawGilts.setMale(totalGilts.getMale() != null ? totalGilts.getMale() : 0);
				rawGilts.setFemale(totalGilts.getFemale() != null ? totalGilts.getFemale() : 0);
				rawGilts.setUnGender(totalGilts.getUnGender() != null ? totalGilts.getUnGender() : 0);

				rawGilts.setAdministrativeUnit(totalGilts.getAdministrativeUnit());
				rawGilts.setDistrict(totalGilts.getDistrict());
				rawGilts.setProvince(totalGilts.getProvince());
				if(totalGilts.getAnimal() != null) {
					if(totalGilts.getAnimal().getVnlist() != null) {
						rawGilts.setVnlist(totalGilts.getAnimal().getVnlist());
					}else {
						rawGilts.setVnlist("");
					}
					if(totalGilts.getAnimal().getVnlist06() != null) {
						rawGilts.setVnlist06(totalGilts.getAnimal().getVnlist06());
					}else {
						rawGilts.setVnlist06("");
					}
					if(totalGilts.getAnimal().getCites() != null) {
						rawGilts.setCites(totalGilts.getAnimal().getCites());
					}else {
						rawGilts.setCites("");
					}
					String animalGroupByYear = "";
					AnimalDto animalDto = animalRepository.getAnimalById(animalId);
					animalGroupByYear = animalServiceImpl.updateAnimalGroup(animalDto);
					if(animalGroupByYear != null && animalGroupByYear.length() >0) {
						rawGilts.setAnimalGroup(animalGroupByYear);
					}
				}
				rawGilts = this.animalReportDataRepository.save(rawGilts);
				result.add(rawGilts);
			} else if (rawGilts != null) {
				rawGilts.setTotal(0);
				rawGilts.setMale(0);
				rawGilts.setFemale(0);
				rawGilts.setUnGender(0);

				rawGilts.setAdministrativeUnit(rawGilts.getFarm().getAdministrativeUnit());
				rawGilts.setDistrict(rawGilts.getFarm().getAdministrativeUnit().getParent());
				rawGilts.setProvince(rawGilts.getFarm().getAdministrativeUnit().getParent().getParent());
				if(rawGilts.getAnimal() != null) {
					if(rawGilts.getAnimal().getVnlist() != null) {
						rawGilts.setVnlist(rawGilts.getAnimal().getVnlist());
					}else {
						rawGilts.setVnlist("");
					}
					if(rawGilts.getAnimal().getVnlist06() != null) {
						rawGilts.setVnlist06(rawGilts.getAnimal().getVnlist06());
					}else {
						rawGilts.setVnlist06("");
					}
					if(rawGilts.getAnimal().getCites() != null) {
						rawGilts.setCites(rawGilts.getAnimal().getCites());
					}else {
						rawGilts.setCites("");
					}
					String animalGroupByYear = "";
					AnimalDto animalDto = animalRepository.getAnimalById(animalId);
					animalGroupByYear = animalServiceImpl.updateAnimalGroup(animalDto);
					if(animalGroupByYear != null && animalGroupByYear.length() >0) {
						rawGilts.setAnimalGroup(animalGroupByYear);
					}
				}
				rawGilts = this.animalReportDataRepository.save(rawGilts);
				result.add(rawGilts);
			}

			// if(totalImport!=null) {
			// if(rawImport == null) {
			// rawImport = new AnimalReportData();
			// rawImport.setCreateDate(currentDate);
			// rawImport.setCreatedBy(currentUserName);
			// rawImport.setAnimal(animal);
			// rawImport.setFarm(farm);
			// rawImport.setYear(year);
			// rawImport.setType(WLConstant.AnimalReportDataType.importAnimal.getValue());
			// }
			// rawImport.setTotal(totalImport.getTotal() != null?totalImport.getTotal(): 0);
			// rawImport.setMale(totalImport.getMale() != null? totalImport.getMale(): 0);
			// rawImport.setFemale(totalImport.getFemale() != null? totalImport.getFemale():
			// 0);
			// rawImport.setUnGender(totalImport.getUnGender() != null?
			// totalImport.getUnGender(): 0);
			//
			// rawImport.setAdministrativeUnit(totalImport.getAdministrativeUnit());
			// rawImport.setDistrict(totalImport.getDistrict());
			// rawImport.setProvince(totalImport.getProvince());
			//
			// rawImport=this.animalReportDataRepository.save(rawImport);
			// result.add(rawImport);
			// }
			// else if(rawImport != null) {
			// rawImport.setTotal(0);
			// rawImport.setMale(0);
			// rawImport.setFemale(0);
			// rawImport.setUnGender(0);
			//
			// rawImport.setAdministrativeUnit(rawImport.getFarm().getAdministrativeUnit());
			// rawImport.setDistrict(rawImport.getFarm().getAdministrativeUnit().getParent());
			// rawImport.setProvince(rawImport.getFarm().getAdministrativeUnit().getParent().getParent());
			//
			// rawImport = this.animalReportDataRepository.save(rawImport);
			// result.add(rawImport);
			// }
			//
			// if(totalExport!=null) {
			// if(rawExport == null) {
			// rawExport = new AnimalReportData();
			// rawExport.setCreateDate(currentDate);
			// rawExport.setCreatedBy(currentUserName);
			// rawExport.setAnimal(animal);
			// rawExport.setFarm(farm);
			// rawExport.setYear(year);
			// rawExport.setType(WLConstant.AnimalReportDataType.exportAnimal.getValue());
			// }
			// rawExport.setTotal(totalExport.getTotal() != null?totalExport.getTotal(): 0);
			// rawExport.setMale(totalExport.getMale() != null? totalExport.getMale(): 0);
			// rawExport.setFemale(totalExport.getFemale() != null? totalExport.getFemale():
			// 0);
			// rawExport.setUnGender(totalExport.getUnGender() != null?
			// totalExport.getUnGender(): 0);
			//
			// rawExport.setAdministrativeUnit(totalExport.getAdministrativeUnit());
			// rawExport.setDistrict(totalExport.getDistrict());
			// rawExport.setProvince(totalExport.getProvince());
			//
			// rawExport=this.animalReportDataRepository.save(rawExport);
			// result.add(rawExport);
			// }
			// else if(rawExport != null) {
			// rawExport.setTotal(0);
			// rawExport.setMale(0);
			// rawExport.setFemale(0);
			// rawExport.setUnGender(0);
			//
			// rawExport.setAdministrativeUnit(rawImport.getFarm().getAdministrativeUnit());
			// rawExport.setDistrict(rawImport.getFarm().getAdministrativeUnit().getParent());
			// rawExport.setProvince(rawImport.getFarm().getAdministrativeUnit().getParent().getParent());
			//
			// rawExport = this.animalReportDataRepository.save(rawExport);
			// result.add(rawExport);
			// }
		}
		return result;
	}

	@Override
	public List<AnimalReportData> updateAnimalReportDataByFarmAnimalYearConvertOnly(Long farmId, List<Long> animalIds,
			Integer year) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}

		List<AnimalReportData> result = new ArrayList<AnimalReportData>();
		Farm farm = null;
		farm = farmRepository.findOne(farmId);
		Animal animal = null;
		for (Long animalId : animalIds) {
			if (animalId == null) {
				continue;
			}
			animal = animalRepository.findOne(animalId);
			AnimalReportData rawAnimalParen = null;
			AnimalReportData rawChildOver1YearOld = null;
			AnimalReportData rawChildUnder1YearOld = null;
			AnimalReportData rawGilts = null;
			List<AnimalReportData> listRawAnimalParen = animalReportDataRepository
					.findByYearAndMonthFarmAnimalAndMonthDayNull(year, farmId, animalId,
							WLConstant.AnimalReportDataType.animalParent.getValue());
			if (listRawAnimalParen != null && listRawAnimalParen.size() > 0) {
				rawAnimalParen = listRawAnimalParen.get(0);
			}
			List<AnimalReportData> listChildOver1YearOld = animalReportDataRepository
					.findByYearAndMonthFarmAnimalAndMonthDayNull(year, farmId, animalId,
							WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
			if (listChildOver1YearOld != null && listChildOver1YearOld.size() > 0) {
				rawChildOver1YearOld = listChildOver1YearOld.get(0);
			}
			List<AnimalReportData> listChildUnder1YearOld = animalReportDataRepository
					.findByYearAndMonthFarmAnimalAndMonthDayNull(year, farmId, animalId,
							WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
			if (listChildUnder1YearOld != null && listChildUnder1YearOld.size() > 0) {
				rawChildUnder1YearOld = listChildUnder1YearOld.get(0);
			}
			List<AnimalReportData> listGilts = animalReportDataRepository.findByYearAndMonthFarmAnimalAndMonthDayNull(
					year, farmId, animalId, WLConstant.AnimalReportDataType.gilts.getValue());
			if (listGilts != null && listGilts.size() > 0) {
				rawGilts = listGilts.get(0);
			}

			AnimalReportData totalAnimalParent = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.animalParent.getValue(), year);

			AnimalReportData totalChildOver1YearOld = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.childOver1YearOld.getValue(), year);

			AnimalReportData totalChildUnder1YearOld = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.childUnder1YearOld.getValue(), year);

			AnimalReportData totalGilts = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.gilts.getValue(), year);

			AnimalReportData totalImport = getAnimalReportDataLastUpdateBy(farmId, animalId,
					WLConstant.AnimalReportDataType.importAnimal.getValue(), year);

			if (totalAnimalParent != null) {
				if (rawAnimalParen == null) {
					rawAnimalParen = new AnimalReportData();
					rawAnimalParen.setCreateDate(currentDate);
					rawAnimalParen.setCreatedBy(currentUserName);
					rawAnimalParen.setAnimal(animal);
					rawAnimalParen.setFarm(farm);
					rawAnimalParen.setYear(year);
					rawAnimalParen.setType(WLConstant.AnimalReportDataType.animalParent.getValue());
				}
				rawAnimalParen.setTotal(totalAnimalParent.getTotal() != null ? totalAnimalParent.getTotal() : 0);
				rawAnimalParen.setMale(totalAnimalParent.getMale() != null ? totalAnimalParent.getMale() : 0);
				rawAnimalParen.setFemale(totalAnimalParent.getFemale() != null ? totalAnimalParent.getFemale() : 0);
				rawAnimalParen
						.setUnGender(totalAnimalParent.getUnGender() != null ? totalAnimalParent.getUnGender() : 0);
				rawAnimalParen.setAdministrativeUnit(totalAnimalParent.getAdministrativeUnit());
				rawAnimalParen.setDistrict(totalAnimalParent.getDistrict());
				rawAnimalParen.setProvince(totalAnimalParent.getProvince());
				result.add(rawAnimalParen);
			} else if (rawAnimalParen != null) {
				rawAnimalParen.setTotal(0);
				rawAnimalParen.setMale(0);
				rawAnimalParen.setFemale(0);
				rawAnimalParen.setUnGender(0);
				rawAnimalParen.setAdministrativeUnit(rawAnimalParen.getFarm().getAdministrativeUnit());
				rawAnimalParen.setDistrict(rawAnimalParen.getFarm().getAdministrativeUnit().getParent());
				rawAnimalParen.setProvince(rawAnimalParen.getFarm().getAdministrativeUnit().getParent().getParent());
				result.add(rawAnimalParen);
			}

			if (totalChildOver1YearOld != null) {
				if (rawChildOver1YearOld == null) {
					rawChildOver1YearOld = new AnimalReportData();
					rawChildOver1YearOld.setCreateDate(currentDate);
					rawChildOver1YearOld.setCreatedBy(currentUserName);
					rawChildOver1YearOld.setAnimal(animal);
					rawChildOver1YearOld.setFarm(farm);
					rawChildOver1YearOld.setYear(year);
					rawChildOver1YearOld.setType(WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
				}
				rawChildOver1YearOld
						.setTotal(totalChildOver1YearOld.getTotal() != null ? totalChildOver1YearOld.getTotal() : 0);
				rawChildOver1YearOld
						.setMale(totalChildOver1YearOld.getMale() != null ? totalChildOver1YearOld.getMale() : 0);
				rawChildOver1YearOld
						.setFemale(totalChildOver1YearOld.getFemale() != null ? totalChildOver1YearOld.getFemale() : 0);
				rawChildOver1YearOld.setUnGender(
						totalChildOver1YearOld.getUnGender() != null ? totalChildOver1YearOld.getUnGender() : 0);

				rawChildOver1YearOld.setAdministrativeUnit(totalChildOver1YearOld.getAdministrativeUnit());
				rawChildOver1YearOld.setDistrict(totalChildOver1YearOld.getDistrict());
				rawChildOver1YearOld.setProvince(totalChildOver1YearOld.getProvince());
				result.add(rawChildOver1YearOld);

			} else if (rawChildOver1YearOld != null) {
				rawChildOver1YearOld.setTotal(0);
				rawChildOver1YearOld.setMale(0);
				rawChildOver1YearOld.setFemale(0);
				rawChildOver1YearOld.setUnGender(0);

				rawChildOver1YearOld.setAdministrativeUnit(rawChildOver1YearOld.getFarm().getAdministrativeUnit());
				rawChildOver1YearOld.setDistrict(rawChildOver1YearOld.getFarm().getAdministrativeUnit().getParent());
				rawChildOver1YearOld
						.setProvince(rawChildOver1YearOld.getFarm().getAdministrativeUnit().getParent().getParent());
				result.add(rawChildOver1YearOld);
			}
			if (totalChildUnder1YearOld != null) {
				if (rawChildUnder1YearOld == null) {
					rawChildUnder1YearOld = new AnimalReportData();
					rawChildUnder1YearOld.setCreateDate(currentDate);
					rawChildUnder1YearOld.setCreatedBy(currentUserName);
					rawChildUnder1YearOld.setAnimal(animal);
					rawChildUnder1YearOld.setFarm(farm);
					rawChildUnder1YearOld.setYear(year);
					rawChildUnder1YearOld.setType(WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
				}
				rawChildUnder1YearOld
						.setTotal(totalChildUnder1YearOld.getTotal() != null ? totalChildUnder1YearOld.getTotal() : 0);
				rawChildUnder1YearOld
						.setMale(totalChildUnder1YearOld.getMale() != null ? totalChildUnder1YearOld.getMale() : 0);
				rawChildUnder1YearOld.setFemale(
						totalChildUnder1YearOld.getFemale() != null ? totalChildUnder1YearOld.getFemale() : 0);
				rawChildUnder1YearOld.setUnGender(
						totalChildUnder1YearOld.getUnGender() != null ? totalChildUnder1YearOld.getUnGender() : 0);

				rawChildUnder1YearOld.setAdministrativeUnit(totalChildUnder1YearOld.getAdministrativeUnit());
				rawChildUnder1YearOld.setDistrict(totalChildUnder1YearOld.getDistrict());
				rawChildUnder1YearOld.setProvince(totalChildUnder1YearOld.getProvince());
				result.add(rawChildUnder1YearOld);
			} else if (rawChildUnder1YearOld != null) {
				rawChildUnder1YearOld.setTotal(0);
				rawChildUnder1YearOld.setMale(0);
				rawChildUnder1YearOld.setFemale(0);
				rawChildUnder1YearOld.setUnGender(0);

				rawChildUnder1YearOld.setAdministrativeUnit(rawChildUnder1YearOld.getFarm().getAdministrativeUnit());
				rawChildUnder1YearOld.setDistrict(rawChildUnder1YearOld.getFarm().getAdministrativeUnit().getParent());
				rawChildUnder1YearOld
						.setProvince(rawChildUnder1YearOld.getFarm().getAdministrativeUnit().getParent().getParent());
				result.add(rawChildUnder1YearOld);
			}

			if (totalGilts != null) {
				if (rawGilts == null) {
					rawGilts = new AnimalReportData();
					rawGilts.setCreateDate(currentDate);
					rawGilts.setCreatedBy(currentUserName);
					rawGilts.setAnimal(animal);
					rawGilts.setFarm(farm);
					rawGilts.setYear(year);
					rawGilts.setType(WLConstant.AnimalReportDataType.gilts.getValue());
				}
				rawGilts.setTotal(totalGilts.getTotal() != null ? totalGilts.getTotal() : 0);
				rawGilts.setMale(totalGilts.getMale() != null ? totalGilts.getMale() : 0);
				rawGilts.setFemale(totalGilts.getFemale() != null ? totalGilts.getFemale() : 0);
				rawGilts.setUnGender(totalGilts.getUnGender() != null ? totalGilts.getUnGender() : 0);

				rawGilts.setAdministrativeUnit(totalGilts.getAdministrativeUnit());
				rawGilts.setDistrict(totalGilts.getDistrict());
				rawGilts.setProvince(totalGilts.getProvince());
				result.add(rawGilts);
			} else if (rawGilts != null) {
				rawGilts.setTotal(0);
				rawGilts.setMale(0);
				rawGilts.setFemale(0);
				rawGilts.setUnGender(0);

				rawGilts.setAdministrativeUnit(rawGilts.getFarm().getAdministrativeUnit());
				rawGilts.setDistrict(rawGilts.getFarm().getAdministrativeUnit().getParent());
				rawGilts.setProvince(rawGilts.getFarm().getAdministrativeUnit().getParent().getParent());
				result.add(rawGilts);
			}
		}
		return result;
	}

	/**
	 * Hàm trả về tổng bản ghi cuối cùng 1 loài theo type , năm
	 */
	// public Integer getTotalLastUpdateBy(Long farmId, Long animalId, Integer
	// type,Integer year) {
	// Integer result = 0;
	// Date maxDate = null;
	// List<AnimalReportData> datas =
	// animalReportDataRepository.findListAnimalReportDataBy(farmId, animalId, type,
	// year);
	// for(AnimalReportData animalReportData: datas) {
	// if(animalReportData != null) {
	// int yearReport = animalReportData.getYear() != null ?
	// animalReportData.getYear(): 1;
	// int monthReport = animalReportData.getMonth() != null ?
	// animalReportData.getMonth(): 1;
	// int dayReport = animalReportData.getDay() != null ?
	// animalReportData.getDay(): 1;
	//
	// try {
	// Date date = WLDateTimeUtil.numberToDate(dayReport, monthReport, yearReport);
	// if(maxDate == null) {
	// maxDate = date;
	// result = animalReportData.getTotal();
	// }else if(maxDate.compareTo(date) == 0) {
	// if(animalReportData.getTotal().intValue() > result.intValue()) {
	// result = animalReportData.getTotal();
	// }
	// }else if(maxDate.compareTo(date) < 0) {
	// maxDate = date;
	// result = animalReportData.getTotal();
	// }
	// } catch (ParseException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// }
	//
	// return result;
	// }

	/**
	 * Hàm trả về bản ghi cuối cùng 1 loài theo type , năm
	 */
	public AnimalReportData getAnimalReportDataLastUpdateBy(Long farmId, Long animalId, Integer type, Integer year) {
		AnimalReportData result = null;
		Date maxDate = null;
		List<AnimalReportData> datas = animalReportDataRepository.findListAnimalReportDataBy(farmId, animalId, type,
				year);

		if (datas != null && datas.size() > 0) {
			result = datas.get(0);
		}
		return result;
	}

	@Override
	public List<AnimalReportDataDto> updateFarmAnimalYearForAllFarm(int pageIndex, int pageSize, int year,
			FarmSearchDto dto) {
		/* System.out.println("Start Sync"+new Date()); */
		Page<FarmDto> results = this.farmService.searchFarmDto(dto, pageIndex, pageSize);
		List<AnimalReportDataDto> ret = new ArrayList<AnimalReportDataDto>();

		if (results != null && results.getContent() != null && results.getContent().size() > 0) {
			/* System.out.println("Tổng số trại: "+results.getTotalElements()); */
			int i = 0;
			for (FarmDto farmDto : results) {
				/*
				 * System.out.println(i+"/"+results.getTotalElements()+" Cập nhật trại: " +
				 * farmDto.getCode());
				 */
				try {
					List<Long> listAnimalIds = this.animalReportDataRepository.getListAnimalIdByFarmId(farmDto.getId());
					// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
					List<AnimalReportData> result = this.updateAnimalReportDataByFarmAnimalYear(farmDto.getId(),
							listAnimalIds, year);
					for (AnimalReportData re : result) {
						ret.add(new AnimalReportDataDto(re));
					}

				} catch (Exception e) {
					/*
					 * System.out.println("---------------------ERR: Lỗi ở trại: " +
					 * farmDto.getCode());
					 */
					e.printStackTrace();
					continue;
					// TODO: handle exception
				}
				i += 1;
			}
		}
		/* System.out.println("End Sync"+new Date()); */
		return ret;
	}

	@Override
	public void updateAdministrative() {
		List<AnimalReportData> list = animalReportDataRepository.findAll();
		if (list != null && list.size() > 0) {
			for (AnimalReportData animalReportData : list) {
				if (animalReportData.getFarm() != null && animalReportData.getFarm().getAdministrativeUnit() != null) {
					if (animalReportData.getAdministrativeUnit() == null)
						animalReportData.setAdministrativeUnit(animalReportData.getFarm().getAdministrativeUnit());
					if (animalReportData.getDistrict() == null)
						animalReportData.setDistrict(animalReportData.getFarm().getAdministrativeUnit().getParent());
					if (animalReportData.getProvince() == null)
						animalReportData.setProvince(
								animalReportData.getFarm().getAdministrativeUnit().getParent().getParent());
				}

			}
			animalReportDataRepository.save(list);
		}

	}

	@Override
	public boolean deleteDataMapByAdministrativeUnit(FarmSearchDto farmSearchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<String> listCode = null;
		if (!isAdmin) {
			listCode = userAdministrativeUnitService.getListAdministrativeUnitCodeByUserLogin();
			if (listCode == null || listCode.size() <= 0) {
				return false;
			}
		}

		// TODO Auto-generated method stub
		Long area_id = null;
		if (farmSearchDto.getWard() != null && farmSearchDto.getWard() > 0) {
			area_id = farmSearchDto.getWard();
		} else if (farmSearchDto.getDistrict() != null && farmSearchDto.getDistrict() > 0) {
			area_id = farmSearchDto.getDistrict();
		} else if (farmSearchDto.getProvince() != null && farmSearchDto.getProvince() > 0) {
			area_id = farmSearchDto.getProvince();
		}

		if (area_id != null && area_id > 0) {
			FmsAdministrativeUnit unit = fmsAdministrativeUnitRepository.getOne(area_id);
			if (unit != null && unit.getCode() != null
					&& (isAdmin || (!isAdmin && listCode.contains(unit.getCode())))) {
				DeleteMapByAreaDto dto = new DeleteMapByAreaDto();
				dto.setAreaId(unit.getCode());
				dto.setYear(farmSearchDto.getYearRegistration());
				MapReturnDto result = FarmMapServiceUtil.deleteDataMapByAdministrativeUnit(dto);
				if (result != null && result.getStatus().equals("OK")) {
					return true;
				}
			}
		}
		return false;
	}
}