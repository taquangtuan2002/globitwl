package com.globits.wl.service.impl;

import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmHusbandryMethod;
import com.globits.wl.domain.FarmProductTarget;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.HusbandryMethod;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FarmHusbandryMethodDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.HusbandryMethodDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.functiondto.DataDvhdDto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.ImportResultDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.repository.AnimalReportDataRepository;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.HusbandryMethodRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.repository.ReportForm16Repository;
import com.globits.wl.repository.ReportPeriodRepository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.AnimalService;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.FmsAdministrativeUnitService;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.FarmMapServiceUtil;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ReportPeriodServiceImpl extends GenericServiceImpl<ReportPeriod, Long> implements ReportPeriodService {

	@Autowired
	private ReportPeriodRepository reportPeriodRepository;

	@Autowired
	private ReportForm16Repository reportForm16Repository;

	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private AnimalReportDataService animalReportDataService;

	@Autowired
	private FarmService farmService;

	@Autowired
	private FmsAdministrativeUnitService fmsAdministrativeUnitService;

	@Autowired
	private ProductTargetRepository producTargetRepository;

	@Autowired
	private OriginalRepository originalRepository;

	@Autowired
	private AnimalService animalService;

	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;

	@Autowired
	private AnimalReportDataRepository animalReportDataRepository;

	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	
	@Autowired
	private AnimalServiceImpl animalServiceImpl;
	
	@Autowired
	private HusbandryMethodRepository husbandryMethodRepository;

	@Override
	public ReportPeriodDto saveOrUpdate(ReportPeriodDto dto) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		List<Long> animalIds = new ArrayList<Long>();
		

		boolean isEdit = false;
		ReportPeriod reportPeriodEdit = null;
		Date dateReport = null;
		try {
			dateReport = WLDateTimeUtil.numberToDate(dto.getDate(), dto.getMonth(), dto.getYear());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// TODO Auto-generated method stub
		if (dto != null && dto.getType() != null) {
			ReportPeriod entity = null;

			if (dto.getId() != null) {
				entity = reportPeriodRepository.getOne(dto.getId());
			}
			if (entity == null || entity.getId() == null) {
				entity = new ReportPeriod();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			} else {
				entity.setModifiedBy(currentUserName);
				entity.setModifyDate(currentDate);

				isEdit = false;
				reportPeriodEdit = reportPeriodRepository.getOne(entity.getId());
			}

			entity.setType(dto.getType());
			entity.setYear(dto.getYear());
			entity.setMonth(dto.getMonth());
			entity.setDate(dto.getDate());

			Farm farm = farmRepository.getOne(dto.getFarm().getId());
			if (farm == null) {
				return null;
			}
			
			entity.setFarm(farm);
			
			
			FmsAdministrativeUnit administrativeUnit = null;
			if (dto.getAdministrativeUnitDto() != null) {
				administrativeUnit = fmsAdministrativeUnitRepository.findOne(dto.getAdministrativeUnitDto().getId());
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
			if(entity.getAdministrativeUnit() == null || entity.getProvince()==null || entity.getDistrict()==null) {
				if(farm.getAdministrativeUnit() != null) {
					entity.setAdministrativeUnit(farm.getAdministrativeUnit());
				}
				if(farm.getAdministrativeUnit().getParent() != null) {
					entity.setDistrict(farm.getAdministrativeUnit().getParent());
				}
				if(farm.getAdministrativeUnit().getParent() != null && farm.getAdministrativeUnit().getParent().getParent() != null) {
					entity.setProvince(farm.getAdministrativeUnit().getParent().getParent());
				}
			}
			
			Set<ReportForm16> list = new HashSet<ReportForm16>();
			if (dto.getReportItems() != null && dto.getReportItems().size() > 0) {
				for (ReportForm16Dto reportForm16Dto : dto.getReportItems()) {
					ReportForm16 item = null;
					Original original = null;
					if (reportForm16Dto != null && reportForm16Dto.getId() != null) {
						item = reportForm16Repository.getOne(reportForm16Dto.getId());
					}
//					if(item == null) {
//						item = new ReportForm16();
//					}
//					if (reportForm16Dto != null && reportForm16Dto.getOriginal().getId() != null) {
//						original = originalRepository.getOrinalFindById(reportForm16Dto.getOriginal().getId());
//						if(original !=  null) {
//							item.setOriginal(original);
//						}
//						
//					}
					
					//tran huu dat check cac form 16 nam ngoai quy ko duoc sua start
					
					boolean isUpdate= true;
					int quy=currentDate.getMonthOfYear()/4;// xac dinh quy hien tai
					if (item == null || item.getId() == null) {
						item = new ReportForm16();
						item.setCreateDate(currentDate);
						item.setCreatedBy(currentUserName);
						if (reportForm16Dto != null && reportForm16Dto.getOriginal() != null && reportForm16Dto.getOriginal().getId() != null) {
							original = originalRepository.getOrinalFindById(reportForm16Dto.getOriginal().getId());
							if(original !=  null) {
								item.setOriginal(original);
							}
							
						}
					}else {
						if(item.getId()!=null && item.getDateReport()!=null) {// check item da ton tai chua
							Calendar cal = Calendar.getInstance();
							cal.setTime(item.getDateReport());
							int monthOfDateReort= cal.get(Calendar.MONTH)+1;
							int yearOfDateReport= cal.get(Calendar.YEAR);
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
					}
					
					//tran huu dat check cac form 16 nam ngoai quy ko duoc sua end
					isUpdate = true; // tạm thời bỏ theo quý
					if(isUpdate==true) {// dieu kien neu duoc sua
						item.setModifiedBy(currentUserName);
						item.setModifyDate(currentDate);
						item.setType(dto.getType());
						item.setDateReport(dateReport);
						if (reportForm16Dto.getAnimal() != null && reportForm16Dto.getAnimal().getId() != null) {
							animalIds.add(reportForm16Dto.getAnimal().getId());
						}
						Animal animal = animalRepository.getOne(reportForm16Dto.getAnimal().getId());
						if (animal == null) {
							return null;
						}
						if (reportForm16Dto != null && reportForm16Dto.getOriginal() != null && reportForm16Dto.getOriginal().getId() != null) {
							original = originalRepository.getOrinalFindById(reportForm16Dto.getOriginal().getId());
							if(original ==  null) {
								return null;
							}
							
						}
						
						
						
						item.setOriginal(original);
						item.setAnimal(animal);
						item.setFarm(farm);
						
						Integer total=0;
						Integer male=0;
						Integer female=0;
						Integer unGender=0;
//						Original original1 = item.getOriginal();
//						if(original1 != null ) {
//							this.originalRepository.findOne(original1.getId());
//						}
//						if(original1 != null )
						
//						item.setOriginal(reportForm16Dto.getOriginal());
						
						item.setMaleParent(reportForm16Dto.getMaleParent());
							if(reportForm16Dto.getMaleParent()!=null) {
								total+=reportForm16Dto.getMaleParent();
								male+=reportForm16Dto.getMaleParent();
							}
						item.setFemaleParent(reportForm16Dto.getFemaleParent());
							if(reportForm16Dto.getFemaleParent()!=null) {
								total+=reportForm16Dto.getFemaleParent();
								female+=reportForm16Dto.getFemaleParent();
							}
						item.setUnGenderParent(reportForm16Dto.getUnGenderParent());
						if(reportForm16Dto.getUnGenderParent()!=null) {
							total+=reportForm16Dto.getUnGenderParent();
							unGender+=reportForm16Dto.getUnGenderParent();
						}
						
						item.setMaleGilts(reportForm16Dto.getMaleGilts());
							if(reportForm16Dto.getMaleGilts()!=null) {
								total+=reportForm16Dto.getMaleGilts();
								male+=reportForm16Dto.getMaleGilts();
							}
						item.setFemaleGilts(reportForm16Dto.getFemaleGilts());
							if(reportForm16Dto.getFemaleGilts()!=null) {
								total+=reportForm16Dto.getFemaleGilts();
								female+=reportForm16Dto.getFemaleGilts();
							}
						item.setUnGenderGilts(reportForm16Dto.getUnGenderGilts());
						if(reportForm16Dto.getUnGenderGilts()!=null) {
							total+=reportForm16Dto.getUnGenderGilts();
							unGender+=reportForm16Dto.getUnGenderGilts();
						}
							
						item.setMaleChildUnder1YearOld(reportForm16Dto.getMaleChildUnder1YearOld());
						if(reportForm16Dto.getMaleChildUnder1YearOld()!=null) {
							total+=reportForm16Dto.getMaleChildUnder1YearOld();
							male+=reportForm16Dto.getMaleChildUnder1YearOld();
						}
						item.setFemaleChildUnder1YearOld(reportForm16Dto.getFemaleChildUnder1YearOld());
						if(reportForm16Dto.getFemaleChildUnder1YearOld()!=null) {
							total+=reportForm16Dto.getFemaleChildUnder1YearOld();
							female+=reportForm16Dto.getFemaleChildUnder1YearOld();
						}
						item.setChildUnder1YearOld(reportForm16Dto.getChildUnder1YearOld());
						if(reportForm16Dto.getChildUnder1YearOld()!=null) {
							total+=reportForm16Dto.getChildUnder1YearOld();
							unGender+=reportForm16Dto.getChildUnder1YearOld();
						}
							
						item.setMaleChildOver1YearOld(reportForm16Dto.getMaleChildOver1YearOld());
							if(reportForm16Dto.getMaleChildOver1YearOld()!=null) {
								total+=reportForm16Dto.getMaleChildOver1YearOld();
								male+=reportForm16Dto.getMaleChildOver1YearOld();
							}
						item.setFemaleChildOver1YearOld(reportForm16Dto.getFemaleChildOver1YearOld());
							if(reportForm16Dto.getFemaleChildOver1YearOld()!=null) {
								total+=reportForm16Dto.getFemaleChildOver1YearOld();
								female+=reportForm16Dto.getFemaleChildOver1YearOld();
							}
						item.setUnGenderChildOver1YearOld(reportForm16Dto.getUnGenderChildOver1YearOld());
							if(reportForm16Dto.getUnGenderChildOver1YearOld()!=null) {
								total+=reportForm16Dto.getUnGenderChildOver1YearOld();
								unGender+=reportForm16Dto.getUnGenderChildOver1YearOld();
							}
						
						//tran huu dat them import va export
						item.setImportMaleParent(reportForm16Dto.getImportMaleParent());
						item.setImportFemaleParent(reportForm16Dto.getImportFemaleParent());
						item.setImportUnGenderParent(reportForm16Dto.getImportUnGenderParent());
						
						item.setImportMaleGilts(reportForm16Dto.getImportMaleGilts());
						item.setImportFemaleGilts(reportForm16Dto.getImportFemaleGilts());
						item.setImportUnGenderGilts(reportForm16Dto.getImportUnGenderGilts());
						
						item.setImportMaleChildUnder1YearOld(reportForm16Dto.getImportMaleChildUnder1YearOld());
						item.setImportFemaleChildUnder1YearOld(reportForm16Dto.getImportFemaleChildUnder1YearOld());
						item.setImportMaleChildOver1YearOld(reportForm16Dto.getImportMaleChildOver1YearOld());
						
						item.setImportUnGenderChildOver1YearOld(reportForm16Dto.getImportUnGenderChildOver1YearOld());
						item.setImportChildUnder1YearOld(reportForm16Dto.getImportChildUnder1YearOld());
						item.setImportFemaleChildOver1YearOld(reportForm16Dto.getImportFemaleChildOver1YearOld());
						
						item.setImportReason(reportForm16Dto.getImportReason());
						Integer totalImport=0;
						if(reportForm16Dto.getImportMaleParent() != null) totalImport+=reportForm16Dto.getImportMaleParent();
						if(reportForm16Dto.getImportFemaleParent() != null) totalImport+=reportForm16Dto.getImportFemaleParent();
						if(reportForm16Dto.getImportUnGenderParent() != null) totalImport+=reportForm16Dto.getImportUnGenderParent();
						
						if(reportForm16Dto.getImportMaleGilts() != null) totalImport+=reportForm16Dto.getImportMaleGilts();
						if(reportForm16Dto.getImportFemaleGilts() != null) totalImport+=reportForm16Dto.getImportFemaleGilts();
						if(reportForm16Dto.getImportUnGenderGilts() != null) totalImport+=reportForm16Dto.getImportUnGenderGilts();
						
						if(reportForm16Dto.getImportMaleChildUnder1YearOld() != null) totalImport+=reportForm16Dto.getImportMaleChildUnder1YearOld();
						if(reportForm16Dto.getImportFemaleChildUnder1YearOld() != null) totalImport+=reportForm16Dto.getImportFemaleChildUnder1YearOld();
						if(reportForm16Dto.getImportMaleChildOver1YearOld() != null) totalImport+=reportForm16Dto.getImportMaleChildOver1YearOld();
						
						if(reportForm16Dto.getImportUnGenderChildOver1YearOld() != null) totalImport+=reportForm16Dto.getImportUnGenderChildOver1YearOld();
						if(reportForm16Dto.getImportChildUnder1YearOld() != null) totalImport+=reportForm16Dto.getImportChildUnder1YearOld();
						if(reportForm16Dto.getImportFemaleChildOver1YearOld() != null) totalImport+=reportForm16Dto.getImportFemaleChildOver1YearOld();
						item.setTotalImport(totalImport);
						
						item.setExportMaleParent(reportForm16Dto.getExportMaleParent());
						item.setExportFemaleParent(reportForm16Dto.getExportFemaleParent());
						item.setExportUnGenderParent(reportForm16Dto.getExportUnGenderParent());
						
						item.setExportMaleGilts(reportForm16Dto.getExportMaleGilts());
						item.setExportFemaleGilts(reportForm16Dto.getExportFemaleGilts());
						item.setExportUnGenderGilts(reportForm16Dto.getExportUnGenderGilts());
						
						item.setExportMaleChildUnder1YearOld(reportForm16Dto.getExportMaleChildUnder1YearOld());
						item.setExportFemaleChildUnder1YearOld(reportForm16Dto.getExportFemaleChildUnder1YearOld());
						item.setExportChildUnder1YearOld(reportForm16Dto.getExportChildUnder1YearOld());
						
						item.setExportFemaleChildOver1YearOld(reportForm16Dto.getExportFemaleChildOver1YearOld());
						item.setExportMaleChildOver1YearOld(reportForm16Dto.getExportMaleChildOver1YearOld());
						item.setExportUnGenderChildOver1YearOld(reportForm16Dto.getExportUnGenderChildOver1YearOld());
						item.setExportReason(reportForm16Dto.getExportReason());
						
						Integer totalExport=0;
						if(reportForm16Dto.getExportMaleParent() != null) totalExport+=reportForm16Dto.getExportMaleParent();
						if(reportForm16Dto.getExportFemaleParent() != null) totalExport+=reportForm16Dto.getExportFemaleParent();
						if(reportForm16Dto.getExportUnGenderParent() != null) totalExport+=reportForm16Dto.getExportUnGenderParent();
						
						if(reportForm16Dto.getExportMaleGilts() != null) totalExport+=reportForm16Dto.getExportMaleGilts();
						if(reportForm16Dto.getExportFemaleGilts() != null) totalExport+=reportForm16Dto.getExportFemaleGilts();
						if(reportForm16Dto.getExportUnGenderGilts() != null) totalExport+=reportForm16Dto.getExportUnGenderGilts();
						
						if(reportForm16Dto.getExportMaleChildUnder1YearOld() != null) totalExport+=reportForm16Dto.getExportMaleChildUnder1YearOld();
						if(reportForm16Dto.getExportFemaleChildUnder1YearOld() != null) totalExport+=reportForm16Dto.getExportFemaleChildUnder1YearOld();
						if(reportForm16Dto.getExportChildUnder1YearOld() != null) totalExport+=reportForm16Dto.getExportChildUnder1YearOld();
						
						if(reportForm16Dto.getExportFemaleChildOver1YearOld() != null) totalExport+=reportForm16Dto.getExportFemaleChildOver1YearOld();
						if(reportForm16Dto.getExportMaleChildOver1YearOld() != null) totalExport+=reportForm16Dto.getExportMaleChildOver1YearOld();
						if(reportForm16Dto.getExportUnGenderChildOver1YearOld() != null) totalExport+=reportForm16Dto.getExportUnGenderChildOver1YearOld();
						
						item.setTotalExport(totalExport);
								
						//tran huu dat them import va export
						
						item.setMaleImport(reportForm16Dto.getMaleImport());
						item.setFemaleImport(reportForm16Dto.getFemaleImport());
						item.setUnGenderImport(reportForm16Dto.getUnGenderImport());
						item.setMaleExport(reportForm16Dto.getMaleExport());
						item.setFemaleExport(reportForm16Dto.getFemaleExport());
						item.setUnGenderExport(reportForm16Dto.getUnGenderExport());
						
						
						item.setTotal(total);
						item.setMale(male);
						item.setFemale(female);
						item.setUnGender(unGender);
						
						item.setTotalImport(reportForm16Dto.getTotalImport());
						item.setTotalExport(reportForm16Dto.getTotalExport());
						
						item.setNote(reportForm16Dto.getNote());
						if(reportForm16Dto.getAnimal() != null) {
							if(reportForm16Dto.getAnimal().getVnlist() != null) {
								item.setVnlist(reportForm16Dto.getAnimal().getVnlist());
							}else {
								item.setVnlist("");
							}
							if(reportForm16Dto.getAnimal().getVnlist06() != null) {
								item.setVnlist06(reportForm16Dto.getAnimal().getVnlist06());
							}else {
								item.setVnlist06("");
							}
							if(reportForm16Dto.getAnimal().getCites() != null) {
								item.setCites(reportForm16Dto.getAnimal().getCites());
							}else {
								item.setCites("");
							}
							String animalGroupByYear = "";
							animalGroupByYear = animalServiceImpl.updateAnimalGroup(reportForm16Dto.getAnimal());
							if(animalGroupByYear != null && animalGroupByYear.length() >0) {
								item.setAnimalGroup(animalGroupByYear);
							}
						}
						item.setConfirmForestProtection(reportForm16Dto.getConfirmForestProtection());
						item.setReportPeriod(entity);
						if(administrativeUnit != null && administrativeUnit.getId() != null) {
							item.setAdministrativeUnit(administrativeUnit);
							if(administrativeUnit.getParent() != null) {
								item.setDistrict(administrativeUnit.getParent());
							}
							if(administrativeUnit.getParent().getParent() != null) {
								item.setProvince(administrativeUnit.getParent().getParent());
							}
						}
						if(item.getAdministrativeUnit() == null || item.getDistrict()==null || item.getProvince()==null) {
							if(farm.getAdministrativeUnit() != null) {
								item.setAdministrativeUnit(farm.getAdministrativeUnit());
							}
							if(farm.getAdministrativeUnit().getParent() != null) {
								item.setDistrict(farm.getAdministrativeUnit().getParent());
							}
							if(farm.getAdministrativeUnit().getParent() != null && farm.getAdministrativeUnit().getParent().getParent() != null) {
								item.setProvince(farm.getAdministrativeUnit().getParent().getParent());
							}
							
						}
						
						list.add(item);
					}
					
					
				}
			}
			if (list.size() == 0) {
				return null;
			}
			//Xóa dữ liệu AnimalReportData nếu trong entity.getReportItems() có bản ghi bị bớt đi so với trước
			if(entity.getId()!=null && entity.getId()>0L) {
				List<Long> itemIds = new ArrayList<Long>();
				if(list!=null && list.size()>0) {
					for (ReportForm16 it : list) {
						if(it.getId()!=null) {
							itemIds.add(it.getId());
						}
					}
				}
				List<ReportForm16> listRemovedItem = new ArrayList<ReportForm16>();
				if(itemIds!=null && itemIds.size()>0) {
					listRemovedItem = reportForm16Repository.getListRemoveFromPeriodList(entity.getId(), itemIds);
				}
				else {
					listRemovedItem = reportForm16Repository.getListRemoveFromPeriodList(entity.getId());
				}
				if(listRemovedItem!=null && listRemovedItem.size()>0) {
					for (ReportForm16 reportForm16 : listRemovedItem) {
						this.updateAnimalReportDataBeforeDeleteReportForm16(reportForm16);
					}
				}
			}
			
			if (entity.getReportItems() == null) {
				entity.setReportItems(list);
			} else {
				entity.getReportItems().clear();
				entity.getReportItems().addAll(list);
			}
			
			if(entity.getId()!=null && entity.getId()>0L) {
				List<Long> itemIds = new ArrayList<Long>();
				if(entity.getReportItems()!=null && entity.getReportItems().size()>0) {
					for (ReportForm16 it : entity.getReportItems()) {
						if(it.getId()!=null) {
							itemIds.add(it.getId());
						}
					}
				}
				List<ReportForm16> listRemovedItem = new ArrayList<ReportForm16>();
				if(itemIds!=null && itemIds.size()>0) {
					listRemovedItem = reportForm16Repository.getListRemoveFromPeriodList(entity.getId(), itemIds);
				}
				else {
					listRemovedItem = reportForm16Repository.getListRemoveFromPeriodList(entity.getId());
				}
				if(listRemovedItem!=null && listRemovedItem.size()>0) {
					for (ReportForm16 reportForm16 : listRemovedItem) {
						this.updateAnimalReportDataBeforeDeleteReportForm16(reportForm16);
						animalIds= getListAnimalIds(entity.getFarm().getId(),entity.getYear());
						animalReportDataService.updateAnimalReportDataByFarmAnimalYear(entity.getFarm().getId(),animalIds, entity.getYear());
					}
				}
			}
			
			entity = reportPeriodRepository.save(entity);
			if (entity != null && entity.getReportItems() != null && entity.getReportItems().size() > 0) {
				for (ReportForm16 rf : entity.getReportItems()) {
					if (rf.getTotal() >= 0) {
						ReportPeriodDto resultDto = new ReportPeriodDto(entity);
						this.updateDataFromReportPeriodToAnimalReportData(resultDto);
						// Nếu sửa mà thay đổi tháng - năm thì sẽ phải cập nhật lại cả dữ liệu tháng -
						// năm cũ bị sửa.
						if (isEdit && reportPeriodEdit != null && (!entity.getYear().equals(reportPeriodEdit.getYear())
								|| !entity.getMonth().equals(reportPeriodEdit.getMonth()))) {
							reportPeriodEdit = reportPeriodRepository.getOne(entity.getId());
							this.updateDataFromReportPeriodToAnimalReportData(resultDto);
						}
						animalIds= getListAnimalIds(entity.getFarm().getId(),entity.getYear());
						// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
						animalReportDataService.updateAnimalReportDataByFarmAnimalYear(entity.getFarm().getId(),animalIds, entity.getYear());

//						for(Long animalId : animalIds) {
//							List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByFarmIdAndAnimalId(entity.getFarm().getId(), animalId);
						List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService
								.getAllAnimalLastReportedByRecordMonthDayIsNull(entity.getFarm().getId(), null);
						if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
							for (FarmAnimalTotalDto farmAnimalTotalDto : listAnimalReportTotal) {
								if (farmAnimalTotalDto != null) {
									try {
										FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
									} catch (Exception e) {
										// TODO: handle exception
										continue;
									}
								}
							}
						}
//						}				
						return resultDto;
					}
				}
			}
			return null;
		}
		return null;
	}

	/**
	 * Kết xuất ReportForm16 về AnimalReportData
	 */
	@Override
	public void updateDataFromReportPeriodToAnimalReportData(ReportPeriodDto dto) {
		// TODO Auto-generated method stub

		ReportPeriodDto reportPeriod = null;
		List<ReportPeriod> listData = null;

		if(dto != null && dto.getFarm() != null && dto.getFarm().getId() != null && dto.getYear() != null && dto.getMonth() != null && dto.getDate() != null && dto.getType() != null) {
			listData = reportPeriodRepository.findReportPeriodByYearMonthDate(dto.getYear(), dto.getMonth(), dto.getDate(), dto.getFarm().getId(),
					dto.getType());
		}
		// Nếu là bản ghi cuối cùng của tháng thì sẽ cập nhật dữ liệu vào bảng animal report data
		if (listData != null && listData.size() > 0) {
			int size = listData.size() -1;
			for (int i = 0; i < listData.size(); i++) {

				reportPeriod = new ReportPeriodDto(listData.get(size));
				size--;
				
				List<AnimalReportDataDto> listAnimalReportDataUpdate = new ArrayList<AnimalReportDataDto>();
				for (ReportForm16Dto reportForm16 : reportPeriod.getReportItems()) {
					// animalParent đàn bố mẹ
					AnimalReportDataDto animalParent = new AnimalReportDataDto();
					animalParent.setType(WLConstant.AnimalReportDataType.animalParent.getValue());
					animalParent.setAnimal(reportForm16.getAnimal());
					animalParent.setFarm(reportPeriod.getFarm());
					animalParent.setYear(reportPeriod.getYear());
					animalParent.setMonth(reportPeriod.getMonth());
					animalParent.setDay(reportPeriod.getDate());
					animalParent.setMale(reportForm16.getMaleParent());
					animalParent.setFemale(reportForm16.getFemaleParent());
					animalParent.setUnGender(0);
					animalParent.setAdministrativeUnitDto(reportForm16.getAdministrativeUnitDto());
					animalParent.setDistrictDto(reportForm16.getDistrictDto());
					animalParent.setProvinceDto(reportForm16.getProvinceDto());
					if(reportForm16.getAnimal() != null) {
						if(reportForm16.getAnimal().getVnlist() != null) {
							animalParent.setVnlist(reportForm16.getAnimal().getVnlist());
						}else {
							animalParent.setVnlist("");
						}
						if(reportForm16.getAnimal().getVnlist06() != null) {
							animalParent.setVnlist06(reportForm16.getAnimal().getVnlist06());
						}else {
							animalParent.setVnlist("");
						}
						if(reportForm16.getAnimal().getCites() != null) {
							animalParent.setCites(reportForm16.getAnimal().getCites());
						}else {
							animalParent.setCites("");
						}
						String animalGroupByYear = "";
						animalGroupByYear = animalServiceImpl.updateAnimalGroup(reportForm16.getAnimal());
						if(animalGroupByYear != null && animalGroupByYear.length() >0) {
							animalParent.setAnimalGroup(animalGroupByYear);
						}
					}
					listAnimalReportDataUpdate.add(animalParent);

					// gilts hậu bị
					AnimalReportDataDto gilts = new AnimalReportDataDto();
					gilts.setType(WLConstant.AnimalReportDataType.gilts.getValue());
					gilts.setAnimal(reportForm16.getAnimal());
					gilts.setFarm(reportPeriod.getFarm());
					gilts.setYear(reportPeriod.getYear());
					gilts.setMonth(reportPeriod.getMonth());
					gilts.setDay(reportPeriod.getDate());
					gilts.setMale(reportForm16.getMaleGilts());
					gilts.setFemale(reportForm16.getFemaleGilts());
					gilts.setUnGender(0);
					gilts.setAdministrativeUnitDto(reportForm16.getAdministrativeUnitDto());
					gilts.setDistrictDto(reportForm16.getDistrictDto());
					gilts.setProvinceDto(reportForm16.getProvinceDto());
					if(reportForm16.getAnimal() != null) {
						if(reportForm16.getAnimal().getVnlist() != null) {
							gilts.setVnlist(reportForm16.getAnimal().getVnlist());
						}else {
							gilts.setVnlist("");
						}
						if(reportForm16.getAnimal().getVnlist06() != null) {
							gilts.setVnlist06(reportForm16.getAnimal().getVnlist06());
						}else {
							gilts.setVnlist("");
						}
						if(reportForm16.getAnimal().getCites() != null) {
							gilts.setCites(reportForm16.getAnimal().getCites());
						}else {
							gilts.setCites("");
						}
						String animalGroupByYear = "";
						animalGroupByYear = animalServiceImpl.updateAnimalGroup(reportForm16.getAnimal());
						if(animalGroupByYear != null && animalGroupByYear.length() >0) {
							gilts.setAnimalGroup(animalGroupByYear);
						}
					}
					listAnimalReportDataUpdate.add(gilts);

					// childUnder1YearOld Con non dưới 1 tuổi
					AnimalReportDataDto childUnder1YearOld = new AnimalReportDataDto();
					childUnder1YearOld.setType(WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
					childUnder1YearOld.setAnimal(reportForm16.getAnimal());
					childUnder1YearOld.setFarm(reportPeriod.getFarm());
					childUnder1YearOld.setYear(reportPeriod.getYear());
					childUnder1YearOld.setMonth(reportPeriod.getMonth());
					childUnder1YearOld.setDay(reportPeriod.getDate());
					childUnder1YearOld.setMale(0);
					childUnder1YearOld.setFemale(0);
					childUnder1YearOld.setUnGender(reportForm16.getChildUnder1YearOld());
					childUnder1YearOld.setAdministrativeUnitDto(reportForm16.getAdministrativeUnitDto());
					childUnder1YearOld.setDistrictDto(reportForm16.getDistrictDto());
					childUnder1YearOld.setProvinceDto(reportForm16.getProvinceDto());
					if(reportForm16.getAnimal() != null) {
						if(reportForm16.getAnimal().getVnlist() != null) {
							childUnder1YearOld.setVnlist(reportForm16.getAnimal().getVnlist());
						}else {
							childUnder1YearOld.setVnlist("");
						}
						if(reportForm16.getAnimal().getVnlist06() != null) {
							childUnder1YearOld.setVnlist06(reportForm16.getAnimal().getVnlist06());
						}else {
							childUnder1YearOld.setVnlist("");
						}
						if(reportForm16.getAnimal().getCites() != null) {
							childUnder1YearOld.setCites(reportForm16.getAnimal().getCites());
						}else {
							childUnder1YearOld.setCites("");
						}
						String animalGroupByYear = "";
						animalGroupByYear = animalServiceImpl.updateAnimalGroup(reportForm16.getAnimal());
						if(animalGroupByYear != null && animalGroupByYear.length() >0) {
							childUnder1YearOld.setAnimalGroup(animalGroupByYear);
						}
					}
					listAnimalReportDataUpdate.add(childUnder1YearOld);

					// childOver1YearOld Con non trên 1 tuổi
					AnimalReportDataDto childOver1YearOld = new AnimalReportDataDto();
					childOver1YearOld.setType(WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
					childOver1YearOld.setAnimal(reportForm16.getAnimal());
					childOver1YearOld.setFarm(reportPeriod.getFarm());
					childOver1YearOld.setYear(reportPeriod.getYear());
					childOver1YearOld.setMonth(reportPeriod.getMonth());
					childOver1YearOld.setDay(reportPeriod.getDate());
					childOver1YearOld.setMale(reportForm16.getMaleChildOver1YearOld());
					childOver1YearOld.setFemale(reportForm16.getFemaleChildOver1YearOld());
					childOver1YearOld.setUnGender(reportForm16.getUnGenderChildOver1YearOld());
					childOver1YearOld.setAdministrativeUnitDto(reportForm16.getAdministrativeUnitDto());
					childOver1YearOld.setDistrictDto(reportForm16.getDistrictDto());
					childOver1YearOld.setProvinceDto(reportForm16.getProvinceDto());
					if(reportForm16.getAnimal() != null) {
						if(reportForm16.getAnimal().getVnlist() != null) {
							childOver1YearOld.setVnlist(reportForm16.getAnimal().getVnlist());
						}else {
							childOver1YearOld.setVnlist("");
						}
						if(reportForm16.getAnimal().getVnlist06() != null) {
							childOver1YearOld.setVnlist06(reportForm16.getAnimal().getVnlist06());
						}else {
							childOver1YearOld.setVnlist("");
						}
						if(reportForm16.getAnimal().getCites() != null) {
							childOver1YearOld.setCites(reportForm16.getAnimal().getCites());
						}else {
							childOver1YearOld.setCites("");
						}
						String animalGroupByYear = "";
						animalGroupByYear = animalServiceImpl.updateAnimalGroup(reportForm16.getAnimal());
						if(animalGroupByYear != null && animalGroupByYear.length() >0) {
							childOver1YearOld.setAnimalGroup(animalGroupByYear);
						}
					}
					listAnimalReportDataUpdate.add(childOver1YearOld);
					
					//Nhập động vật
//					AnimalReportDataDto importAnimal = new AnimalReportDataDto();
//					importAnimal.setType(WLConstant.AnimalReportDataType.importAnimal.getValue());
//					importAnimal.setAnimal(reportForm16.getAnimal());
//					importAnimal.setFarm(reportPeriod.getFarm());
//					importAnimal.setYear(reportPeriod.getYear());
//					importAnimal.setMonth(reportPeriod.getMonth());
//					importAnimal.setDay(reportPeriod.getDate());
//					
//					importAnimal.setMale(reportForm16.getMaleImport());
//					importAnimal.setFemale(reportForm16.getFemaleImport());
//					importAnimal.setUnGender(reportForm16.getUnGenderImport());
//					
//					importAnimal.setAdministrativeUnitDto(reportForm16.getAdministrativeUnitDto());
//					importAnimal.setDistrictDto(reportForm16.getDistrictDto());
//					importAnimal.setProvinceDto(reportForm16.getProvinceDto());
//					listAnimalReportDataUpdate.add(importAnimal);
//					//Xuất động vật
//					AnimalReportDataDto exportAnimal = new AnimalReportDataDto();
//					exportAnimal.setType(WLConstant.AnimalReportDataType.exportAnimal.getValue());
//					exportAnimal.setAnimal(reportForm16.getAnimal());
//					exportAnimal.setFarm(reportPeriod.getFarm());
//					exportAnimal.setYear(reportPeriod.getYear());
//					exportAnimal.setMonth(reportPeriod.getMonth());
//					exportAnimal.setDay(reportPeriod.getDate());
//					if(reportForm16.getMaleExport()!=null)
//						exportAnimal.setMale(reportForm16.getMaleExport()*(-1));
//					if(reportForm16.getFemaleExport()!=null)
//						exportAnimal.setFemale(reportForm16.getFemaleExport()*(-1));
//					if(reportForm16.getUnGenderExport()!=null)
//						exportAnimal.setUnGender(reportForm16.getUnGenderExport()*(-1));
//					
//					exportAnimal.setAdministrativeUnitDto(reportForm16.getAdministrativeUnitDto());
//					exportAnimal.setDistrictDto(reportForm16.getDistrictDto());
//					exportAnimal.setProvinceDto(reportForm16.getProvinceDto());
//					listAnimalReportDataUpdate.add(exportAnimal);
				}
				List<AnimalReportDataDto> listAnimalReportDataDto = animalReportDataService.saveUpdateListAnimalReportBy16A(listAnimalReportDataUpdate);
			}
		}
//		}

	}
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
		animalParent.setUnGender(0);
		animalParent.setTotal(male+female+animalParent.getUnGender());
		
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
		gilts.setUnGender(0);
		gilts.setTotal(female+male);
		
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
		
		childUnder1YearOld.setMale(0);
		childUnder1YearOld.setFemale(0);
		childUnder1YearOld.setUnGender(reportForm16.getChildUnder1YearOld());
		childUnder1YearOld.setTotal(childUnder1YearOld.getUnGender());
		
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
		int unGender = 0; if(reportForm16.getUnGenderChildOver1YearOld()!=null) unGender = reportForm16.getUnGenderChildOver1YearOld();
		childOver1YearOld.setTotal(male+female+unGender);
		
		childOver1YearOld.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
		childOver1YearOld.setDistrict(reportForm16.getDistrict());
		childOver1YearOld.setProvince(reportForm16.getProvince());
		listAnimalReportDataUpdate.add(childOver1YearOld);
		
		//Nhập động vật
//		AnimalReportData importAnimal = new AnimalReportData();
//		importAnimal.setType(WLConstant.AnimalReportDataType.importAnimal.getValue());
//		importAnimal.setAnimal(reportForm16.getAnimal());
//		importAnimal.setFarm(reportForm16.getFarm());
//		importAnimal.setYear(reportForm16.getReportPeriod().getYear());
//		importAnimal.setMonth(reportForm16.getReportPeriod().getMonth());
//		importAnimal.setDay(reportForm16.getReportPeriod().getDate());
//		
//		importAnimal.setMale(reportForm16.getMaleImport());
//		importAnimal.setFemale(reportForm16.getFemaleImport());
//		importAnimal.setUnGender(reportForm16.getUnGenderImport());
//		Integer totalIm=0;
//		if(reportForm16.getMaleImport()!=null) {
//			totalIm+=reportForm16.getMaleImport();
//		}
//		if(reportForm16.getFemaleImport()!=null) {
//			totalIm+=reportForm16.getFemaleImport();
//		}
//		if(reportForm16.getUnGenderImport()!=null) {
//			totalIm+=reportForm16.getUnGenderImport();
//		}
//		importAnimal.setTotal(totalIm);
//		
//		importAnimal.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
//		importAnimal.setDistrict(reportForm16.getDistrict());
//		importAnimal.setProvince(reportForm16.getProvince());
//		listAnimalReportDataUpdate.add(importAnimal);
		
		//Xuất động vật
//		AnimalReportData exportAnimal = new AnimalReportData();
//		exportAnimal.setType(WLConstant.AnimalReportDataType.exportAnimal.getValue());
//		exportAnimal.setAnimal(reportForm16.getAnimal());
//		exportAnimal.setFarm(reportForm16.getReportPeriod().getFarm());
//		exportAnimal.setYear(reportForm16.getReportPeriod().getYear());
//		exportAnimal.setMonth(reportForm16.getReportPeriod().getMonth());
//		exportAnimal.setDay(reportForm16.getReportPeriod().getDate());
//		
//		
//		
//		
//		Integer totalEx=0;
//		if(reportForm16.getMaleExport()!=null) {
//			totalEx+=reportForm16.getMaleExport();
//			exportAnimal.setMale(reportForm16.getMaleExport()*(-1));
//		}
//		if(reportForm16.getFemaleExport()!=null) {
//			exportAnimal.setFemale(reportForm16.getFemaleExport()*(-1));
//			totalEx+=reportForm16.getFemaleExport();
//		}
//		if(reportForm16.getUnGenderExport()!=null) {
//			exportAnimal.setUnGender(reportForm16.getUnGenderExport()*(-1));
//			totalEx+=reportForm16.getUnGenderExport();
//		}
//		exportAnimal.setTotal(totalEx*(-1));
//		
//		exportAnimal.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
//		exportAnimal.setDistrict(reportForm16.getDistrict());
//		exportAnimal.setProvince(reportForm16.getProvince());
//		listAnimalReportDataUpdate.add(exportAnimal);
		
		List<AnimalReportData> listAnimalReportData = animalReportDataRepository.save(listAnimalReportDataUpdate);
	}
	@Override
	public List<AnimalReportData> updateDataFromReportForm16ToAnimalReportDataByEntityConvertOnly(ReportForm16 reportForm16) {
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
		animalParent.setUnGender(0);
		animalParent.setTotal(male+female+animalParent.getUnGender());
		
		animalParent.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
		animalParent.setDistrict(reportForm16.getDistrict());
		animalParent.setProvince(reportForm16.getProvince());
		listAnimalReportDataUpdate.add(animalParent);

		// gilts hậu bị
		AnimalReportData gilts = null;
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
		gilts.setUnGender(0);
		gilts.setTotal(female+male);
		
		gilts.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
		gilts.setDistrict(reportForm16.getDistrict());
		gilts.setProvince(reportForm16.getProvince());
		listAnimalReportDataUpdate.add(gilts);

		// childUnder1YearOld Con non dưới 1 tuổi
		AnimalReportData childUnder1YearOld = null;
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
		
		childUnder1YearOld.setMale(0);
		childUnder1YearOld.setFemale(0);
		childUnder1YearOld.setUnGender(reportForm16.getChildUnder1YearOld());
		childUnder1YearOld.setTotal(childUnder1YearOld.getUnGender());
		
		childUnder1YearOld.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
		childUnder1YearOld.setDistrict(reportForm16.getDistrict());
		childUnder1YearOld.setProvince(reportForm16.getProvince());
		listAnimalReportDataUpdate.add(childUnder1YearOld);

		// childOver1YearOld Con non trên 1 tuổi		
		AnimalReportData childOver1YearOld = null;
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
		int unGender = 0; if(reportForm16.getUnGenderChildOver1YearOld()!=null) unGender = reportForm16.getUnGenderChildOver1YearOld();
		childOver1YearOld.setTotal(male+female+unGender);
		
		childOver1YearOld.setAdministrativeUnit(reportForm16.getAdministrativeUnit());
		childOver1YearOld.setDistrict(reportForm16.getDistrict());
		childOver1YearOld.setProvince(reportForm16.getProvince());
		listAnimalReportDataUpdate.add(childOver1YearOld);		
		
		return listAnimalReportDataUpdate;
	}
	
	@Override
	public void updateAnimalReportDataBeforeDeleteReportPeriod(ReportPeriod reportPeriod) {
		if (reportPeriod != null) {
			for (ReportForm16 reportForm16 : reportPeriod.getReportItems()) {

				// animalParent đàn bố mẹ
				List<AnimalReportData> list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(),
						reportPeriod.getFarm().getId(), reportForm16.getAnimal().getId(),
						WLConstant.AnimalReportDataType.animalParent.getValue());
				if (list != null && list.size() > 0) {
					for (AnimalReportData animalReportData : list) {
						animalReportDataRepository.delete(animalReportData.getId());
					}
				}
				list = new ArrayList<AnimalReportData>();

				// gilts hậu bị
				list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
						reportForm16.getAnimal().getId(), WLConstant.AnimalReportDataType.gilts.getValue());
				if (list != null && list.size() > 0) {
					for (AnimalReportData animalReportData : list) {
						animalReportDataRepository.delete(animalReportData.getId());
					}
				}
				list = new ArrayList<AnimalReportData>();

				// childUnder1YearOld Con non dưới 1 tuổi
				list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
						reportForm16.getAnimal().getId(),
						WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
				if (list != null && list.size() > 0) {
					for (AnimalReportData animalReportData : list) {
						animalReportDataRepository.delete(animalReportData.getId());
					}
				}
				list = new ArrayList<AnimalReportData>();

				// childOver1YearOld Con non trên 1 tuổi
				list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
						reportForm16.getAnimal().getId(), WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
				if (list != null && list.size() > 0) {
					for (AnimalReportData animalReportData : list) {
						animalReportDataRepository.delete(animalReportData.getId());
					}
				}
				list = new ArrayList<AnimalReportData>();
				
				//Nhập
				list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
						reportForm16.getAnimal().getId(), WLConstant.AnimalReportDataType.importAnimal.getValue());
				if (list != null && list.size() > 0) {
					for (AnimalReportData animalReportData : list) {
						animalReportDataRepository.delete(animalReportData.getId());
					}
				}
				list = new ArrayList<AnimalReportData>();
				
				//Xuất
				list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
						reportForm16.getAnimal().getId(), WLConstant.AnimalReportDataType.exportAnimal.getValue());
				if (list != null && list.size() > 0) {
					for (AnimalReportData animalReportData : list) {
						animalReportDataRepository.delete(animalReportData.getId());
					}
				}
				list = new ArrayList<AnimalReportData>();				
			}
		}
//		}
	}
	@Override
	public void updateAnimalReportDataBeforeDeleteReportForm16(ReportForm16 reportForm16) {
		System.out.println("updateAnimalReportDataBeforeDeleteReportForm16");
		if(reportForm16!=null && reportForm16.getReportPeriod()!=null) {
			ReportPeriod reportPeriod = reportForm16.getReportPeriod();
			// animalParent đàn bố mẹ
			List<AnimalReportData> list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(),
					reportPeriod.getFarm().getId(), reportForm16.getAnimal().getId(),
					WLConstant.AnimalReportDataType.animalParent.getValue());
			if (list != null && list.size() > 0) {
				for (AnimalReportData animalReportData : list) {
					animalReportDataRepository.delete(animalReportData.getId());
				}
			}
			list = new ArrayList<AnimalReportData>();

			// gilts hậu bị
			list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
					reportForm16.getAnimal().getId(), WLConstant.AnimalReportDataType.gilts.getValue());
			if (list != null && list.size() > 0) {
				for (AnimalReportData animalReportData : list) {
					animalReportDataRepository.delete(animalReportData.getId());
				}
			}
			list = new ArrayList<AnimalReportData>();

			// childUnder1YearOld Con non dưới 1 tuổi
			list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
					reportForm16.getAnimal().getId(),
					WLConstant.AnimalReportDataType.childUnder1YearOld.getValue());
			if (list != null && list.size() > 0) {
				for (AnimalReportData animalReportData : list) {
					animalReportDataRepository.delete(animalReportData.getId());
				}
			}
			list = new ArrayList<AnimalReportData>();

			// childOver1YearOld Con non trên 1 tuổi
			list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
					reportForm16.getAnimal().getId(), WLConstant.AnimalReportDataType.childOver1YearOld.getValue());
			if (list != null && list.size() > 0) {
				for (AnimalReportData animalReportData : list) {
					animalReportDataRepository.delete(animalReportData.getId());
				}
			}
			list = new ArrayList<AnimalReportData>();
			
			//Nhập
			list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
					reportForm16.getAnimal().getId(), WLConstant.AnimalReportDataType.importAnimal.getValue());
			if (list != null && list.size() > 0) {
				for (AnimalReportData animalReportData : list) {
					animalReportDataRepository.delete(animalReportData.getId());
				}
			}
			list = new ArrayList<AnimalReportData>();
			
			//Xuất
			list = animalReportDataRepository.findByYearAndMonthFarmAnimal(reportPeriod.getYear(), reportPeriod.getMonth(), reportPeriod.getDate(), reportPeriod.getFarm().getId(),
					reportForm16.getAnimal().getId(), WLConstant.AnimalReportDataType.exportAnimal.getValue());
			if (list != null && list.size() > 0) {
				for (AnimalReportData animalReportData : list) {
					animalReportDataRepository.delete(animalReportData.getId());
				}
			}
			list = new ArrayList<AnimalReportData>();	
		}
	}
	@Override
	public List<AnimalReportData> findByYearAndMonthFarmAnimal(Integer year, Integer month, Integer day, Long farmId, Long animalId,int type) {
		String SQL="select a from AnimalReportData a where a.year = :year and a.month = :month and a.day= :day and a.farm.id = :farmId and a.animal.id = :animalId and a.type = :type";
		Query q = manager.createQuery(SQL,AnimalReportData.class);
		q.setParameter("year", year);
		q.setParameter("month", month);
		q.setParameter("day", day);
		q.setParameter("farmId", farmId);
		q.setParameter("animalId", animalId);
		q.setParameter("type", type);
		return q.getResultList();
	}
	
	@Override
	public Page<ReportPeriodDto> searchByPage(SearchReportPeriodDto searchDto) {
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
		if (searchDto != null && searchDto.getPageIndex() != null && searchDto.getPageSize() != null) {
			int pageIndex = searchDto.getPageIndex();
			int pageSize = searchDto.getPageSize();

			if (pageIndex > 0)
				pageIndex = pageIndex - 1;
			else
				pageIndex = 0;
			Pageable pageable = new PageRequest(pageIndex, pageSize);

			String sql = "select new com.globits.wl.dto.ReportPeriodDto(rp) from ReportPeriod rp where (1=1)";
			String sqlCount = "select count(rp.id) from ReportPeriod rp  where (1=1)";
			String whereClause = "";

			if (searchDto.getType() != null) {
				whereClause += " and (rp.type =:type )";
			}

			if (searchDto.getFarmId() != null) {
				whereClause += " and (rp.farm.id =:farmId )";
			}

			if (searchDto.getDateReport() != null) {
				whereClause += " and (rp.dateReport =:dateReport )";
			}
			if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
				whereClause += " and (rp.farm.code LIKE :text OR rp.farm.name LIKE :text )";
			}
			if (searchDto.getYear() != null) {
				whereClause += " and (rp.year = :year)";
			}
			if (searchDto.getMonth() != null) {
				whereClause += " and (rp.month = :month)";
			}
			if (searchDto.getDate() != null) {
				whereClause += " and (rp.date = :date)";
			}

			if (searchDto.getWardId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.parent.id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.parent.parent.id =:provinceId )";
			}
			if (!isAdmin) {
				whereClause += " and (rp.farm.administrativeUnit.id in (:listWardId)) ";
			}
			sql += whereClause;
//			sql += " order by rp.farm.code, rp.farm.name, rp.year, rp.month, rp.date asc ";
			sql += " order by rp.year desc, rp.month desc, rp.date desc";
			sqlCount += whereClause;

			Query q = manager.createQuery(sql, ReportPeriodDto.class);
			Query qCount = manager.createQuery(sqlCount);

			if (searchDto.getType() != null) {
				q.setParameter("type", searchDto.getType());
				qCount.setParameter("type", searchDto.getType());
			}

			if (searchDto.getFarmId() != null) {
				q.setParameter("farmId", searchDto.getFarmId());
				qCount.setParameter("farmId", searchDto.getFarmId());
			}

			if (searchDto.getDateReport() != null) {
				q.setParameter("dateReport", searchDto.getDateReport());
				qCount.setParameter("dateReport", searchDto.getDateReport());
			}
			if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
				q.setParameter("text", "%" + searchDto.getText() + "%");
				qCount.setParameter("text", "%" + searchDto.getText() + "%");
			}

			if (searchDto.getYear() != null) {
				q.setParameter("year", searchDto.getYear());
				qCount.setParameter("year", searchDto.getYear());
			}
			if (searchDto.getMonth() != null) {
				q.setParameter("month", searchDto.getMonth());
				qCount.setParameter("month", searchDto.getMonth());
			}
			if (searchDto.getDate() != null) {
				q.setParameter("date", searchDto.getDate());
				qCount.setParameter("date", searchDto.getDate());
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
			if (!isAdmin) {
				q.setParameter("listWardId", listWardId);
				qCount.setParameter("listWardId", listWardId);
			}
			q.setFirstResult((pageIndex) * pageSize);
			q.setMaxResults(pageSize);

			Long numberResult = (Long) qCount.getSingleResult();
			Page<ReportPeriodDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
			return page;
		}
		return null;
	}

	
	
	@Override
	public ReportPeriodDto getById(Long id) {
		if (id != null && id > 0) {
			Boolean isHasPermission = false;
			ReportPeriod result = reportPeriodRepository.getOne(id);
			if (result != null && result.getFarm() != null && result.getFarm().getAdministrativeUnit() != null && result.getFarm().getAdministrativeUnit().getId() != null) {
				isHasPermission = userAdministrativeUnitService.checkUserAdministrativeUnitPermission(result.getFarm().getAdministrativeUnit().getId());
			}
			if (isHasPermission) {
				return new ReportPeriodDto(result);
			} else
				return null;
		}
		return null;
	}

	@Override
	public Boolean checkDuplicateYearMonthDate(SearchReportPeriodDto searchDto) {
		if (searchDto != null) {
			List<ReportPeriod> reportPeriods = reportPeriodRepository.findReportPeriodByYearMonthDate(
					searchDto.getYear(), searchDto.getMonth(), searchDto.getDate(), searchDto.getFarmId(),
					searchDto.getType());
			ReportPeriod reportPeriod = null;
			if (reportPeriods != null && reportPeriods.size() > 0) {
				reportPeriod = reportPeriods.get(0);
			}
			// chưa thấy báo cáo theo năm tháng ngày của trại đó => ok
			if (reportPeriod == null) {
				return false;
			}
			if (searchDto.getId() == null) {
				return true;
			}
			if (searchDto.getId() != null && searchDto.getId() == reportPeriod.getId()) {
				return false;
			}
			if (searchDto.getId() != null && searchDto.getId().longValue() != reportPeriod.getId().longValue()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<ReportPeriodDto> getAll(SearchReportPeriodDto searchDto) {
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
		String sql = "select new com.globits.wl.dto.ReportPeriodDto(rp) from ReportPeriod rp where (1=1)";
		String whereClause = "";

		if (searchDto.getType() != null) {
			whereClause += " and (rp.type =:type )";
		}

		if (searchDto.getFarmId() != null) {
			whereClause += " and (rp.farm.id =:farmId )";
		}

		if (searchDto.getDateReport() != null) {
			whereClause += " and (rp.dateReport =:dateReport )";
		}
		if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
			whereClause += " and (rp.farm.code LIKE :text OR rp.farm.name LIKE :text )";
		}
		if (searchDto.getYear() != null) {
			whereClause += " and (rp.year = :year)";
		}
		if (searchDto.getMonth() != null) {
			whereClause += " and (rp.month = :month)";
		}
		if (searchDto.getDate() != null) {
			whereClause += " and (rp.date = :date)";
		}

		if (searchDto.getWardId() != null) {
			whereClause += " and (rp.farm.administrativeUnit.id =:wardId )";
		} else if (searchDto.getDistrictId() != null) {
			whereClause += " and (rp.farm.administrativeUnit.parent.id =:districtId )";
		} else if (searchDto.getProvinceId() != null) {
			whereClause += " and (rp.farm.administrativeUnit.parent.parent.id =:provinceId )";
		}
		if (!isAdmin) {
			whereClause += " and (rp.farm.administrativeUnit.id in (:listWardId) )";
		}
		sql += whereClause;
		sql += " order by rp.farm.code, rp.farm.name, rp.year, rp.month, rp.date asc ";

		Query q = manager.createQuery(sql, ReportPeriodDto.class);

		if (searchDto.getType() != null) {
			q.setParameter("type", searchDto.getType());
		}

		if (searchDto.getFarmId() != null) {
			q.setParameter("farmId", searchDto.getFarmId());
		}

		if (searchDto.getDateReport() != null) {
			q.setParameter("dateReport", searchDto.getDateReport());
		}
		if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
			q.setParameter("text", "%" + searchDto.getText() + "%");
		}

		if (searchDto.getYear() != null) {
			q.setParameter("year", searchDto.getYear());
		}
		if (searchDto.getMonth() != null) {
			q.setParameter("month", searchDto.getMonth());
		}
		if (searchDto.getDate() != null) {
			q.setParameter("date", searchDto.getDate());
		}

		if (searchDto.getWardId() != null) {
			q.setParameter("wardId", searchDto.getWardId());
		} else if (searchDto.getDistrictId() != null) {
			q.setParameter("districtId", searchDto.getDistrictId());
		} else if (searchDto.getProvinceId() != null) {
			q.setParameter("provinceId", searchDto.getProvinceId());
		}
		if (!isAdmin) {
			q.setParameter("listWardId", listWardId);
		}
		return q.getResultList();
	}

	@Override
	public List<ReportPeriod> getAllEntity(SearchReportPeriodDto searchDto) {
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

		String sql = "select rp from ReportPeriod rp where (1=1) ";
		String whereClause = "";

		if (searchDto.getType() != null) {
			whereClause += " and (rp.type =:type )";
		}

		if (searchDto.getFarmId() != null) {
			whereClause += " and (rp.farm.id =:farmId )";
		}

		if (searchDto.getDateReport() != null) {
			whereClause += " and (rp.dateReport =:dateReport )";
		}
		if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
			whereClause += " and (rp.farm.code LIKE :text OR rp.farm.name LIKE :text )";
		}
		if (searchDto.getYear() != null) {
			whereClause += " and (rp.year = :year)";
		}
		if (searchDto.getMonth() != null) {
			whereClause += " and (rp.month = :month)";
		}
		if (searchDto.getDate() != null) {
			whereClause += " and (rp.date = :date)";
		}

		if (searchDto.getWardId() != null) {
			whereClause += " and (rp.farm.administrativeUnit.id =:wardId )";
		} else if (searchDto.getDistrictId() != null) {
			whereClause += " and (rp.farm.administrativeUnit.parent.id =:districtId )";
		} else if (searchDto.getProvinceId() != null) {
			whereClause += " and (rp.farm.administrativeUnit.parent.parent.id =:provinceId )";
		}
		if (!isAdmin) {
			whereClause += " and (rp.farm.administrativeUnit.id in (:listWardId) )";
		}
		sql += whereClause;
		sql += " order by rp.farm.code, rp.farm.name, rp.year, rp.month, rp.date asc ";

		Query q = manager.createQuery(sql, ReportPeriod.class);

		if (searchDto.getType() != null) {
			q.setParameter("type", searchDto.getType());
		}

		if (searchDto.getFarmId() != null) {
			q.setParameter("farmId", searchDto.getFarmId());
		}

		if (searchDto.getDateReport() != null) {
			q.setParameter("dateReport", searchDto.getDateReport());
		}
		if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
			q.setParameter("text", "%" + searchDto.getText() + "%");
		}

		if (searchDto.getYear() != null) {
			q.setParameter("year", searchDto.getYear());
		}
		if (searchDto.getMonth() != null) {
			q.setParameter("month", searchDto.getMonth());
		}
		if (searchDto.getDate() != null) {
			q.setParameter("date", searchDto.getDate());
		}

		if (searchDto.getWardId() != null) {
			q.setParameter("wardId", searchDto.getWardId());
		} else if (searchDto.getDistrictId() != null) {
			q.setParameter("districtId", searchDto.getDistrictId());
		} else if (searchDto.getProvinceId() != null) {
			q.setParameter("provinceId", searchDto.getProvinceId());
		}
		if (!isAdmin) {
			q.setParameter("listWardId", listWardId);
		}
		return q.getResultList();
	}
	
	@Override
	public List<Long> getListAnimalIds(Long farmId, Integer year) {
		List<Long> listAnimalIdInAnimalReportData = this.animalReportDataRepository.getListByFarmIdAndYear(farmId, year);
		List<Long> listAnimalIds = new ArrayList<Long>();
//		if(listAnimalIdInForm16 != null && listAnimalIdInForm16.size() >0) {
//			for(Long animalId:listAnimalIdInForm16) {
//				listAnimalIds.add(animalId);
//			}
//		}
		if(listAnimalIdInAnimalReportData != null && listAnimalIdInAnimalReportData.size() >0) {
			for(Long animalId:listAnimalIdInAnimalReportData) {
				listAnimalIds.add(animalId);
			}
		}
		return listAnimalIds;
		//result = this.updateAnimalReportDataByFarmAnimalYear(farm.getId(),listAnimalIds,year);
	}
	@Override
	public void updateListFromFileImport(List<DataDvhdDto> list) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}

		Farm farm = null;
		String preFarmCode = null;
		ReportPeriodDto reportPeriodDto = new ReportPeriodDto();
		Set<ReportForm16Dto> reportItems = new HashSet<ReportForm16Dto>();
		Date prevDate = null;
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		int listSize = list.size();
		for (int index = 0; index < listSize; index++) {
			DataDvhdDto rawData = list.get(index);
			if (rawData != null) {
				reportPeriodDto = new ReportPeriodDto();
				reportItems = new HashSet<ReportForm16Dto>();
				ReportForm16Dto reportForm16Dto = rawData.convertToReportForm16();

				List<FmsAdministrativeUnit> listAdministrativeUnits = null;
				if(rawData.getWardCode() != null && rawData.getWardCode().trim().length()>0) {
					listAdministrativeUnits = fmsAdministrativeUnitRepository.findListByCode(rawData.getWardCode());
					if (listAdministrativeUnits != null && listAdministrativeUnits.size()>0) {
						reportPeriodDto.setAdministrativeUnitDto(new FmsAdministrativeUnitDto(listAdministrativeUnits.get(0)));
					}
				}else if(listAdministrativeUnits==null || listAdministrativeUnits.size()==0) {
					listAdministrativeUnits = fmsAdministrativeUnitRepository.getAdministrativeUnitByName(rawData.getWardName().trim(), rawData.getDistrictName().trim(), rawData.getPronvinceName().trim());
					if (listAdministrativeUnits != null && listAdministrativeUnits.size()>0) {
						reportPeriodDto.setAdministrativeUnitDto(new FmsAdministrativeUnitDto(listAdministrativeUnits.get(0)));
					}
				}
				
				if (farm == null) {
					if (StringUtils.hasText(rawData.getFarmCode())) {
						// Nếu tồn tại farmCode tìm farm theo farmCode
						List<Farm> farms = farmRepository.findByCode(rawData.getFarmCode());
						if (preFarmCode != null && preFarmCode.equalsIgnoreCase(rawData.getFarmCode())) {

						} else if (farms != null && farms.size() > 0) {
							farm = farms.get(0);
						} else {
							if (!StringUtils.hasText(rawData.getWardCode())) {
								List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository
										.getAdministrativeUnitByName(rawData.getWardName().trim(),
												rawData.getDistrictName().trim(), rawData.getPronvinceName().trim());
								if (listFind != null && listFind.size() > 0) {
									rawData.setWardCode(listFind.get(0).getCode());
								}
							}
							farms = farmRepository.findBymapCodeAndAdministrativeUnitCode(rawData.getFarmCode().trim(),
									rawData.getWardCode());
							System.out.println("rawData: " + rawData.getFarmName() + "**" + rawData.getFarmCode() + "**"
									+ rawData.getWardCode());
							if (farms != null && farms.size() > 0) {
								farm = farms.get(0);
							}
						}

						if (farm == null) {
							// Nếu không tồn tại farmCode tạo mới farm với farmCode tự sinh
							FarmDto farmDto = rawData.convertToFarmDto();
							if (farmDto.getAdministrativeUnit() == null) {
								System.out.println(rawData.getWardName().trim() + "," + rawData.getDistrictName().trim()
										+ "," + rawData.getPronvinceName().trim());
								List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository.getAdministrativeUnitByName(rawData.getWardName().trim(),
												rawData.getDistrictName().trim(), rawData.getPronvinceName().trim());
								if (listFind != null && listFind.size() > 0) {
									farmDto.setAdministrativeUnit(new FmsAdministrativeUnitDto(listFind.get(0)));
									rawData.setWardCode(listFind.get(0).getCode());
									System.out.println(rawData.getWardCode());
								}
							}
							farmDto.setMapCode(rawData.getFarmCode());
							if (StringUtils.hasText(rawData.getProductTargetCode())) {
								List<ProductTarget> productTargets = producTargetRepository
										.findByCode(rawData.getProductTargetCode());
								if (productTargets != null && productTargets.size() > 0) {
									ProductTarget productTarget = productTargets.get(0);
									farmDto.setFarmProductTargets(new ArrayList<ProductTargetDto>());
									farmDto.getFarmProductTargets().add(new ProductTargetDto(productTarget));
								}
							}
//							if (StringUtils.hasText(rawData.getOriginalCode())) {
//								List<Original> originals = originalRepository.findByCode(rawData.getOriginalCode());
//								if (originals != null && originals.size() > 0) {
//									Original original = originals.get(0);
//								}
//							}

							farmDto = farmService.createFarm(farmDto);

							try {
								farm = farmService.findById(farmDto.getId());
							} catch (Exception e) {
								// TODO: handle exception
								continue;
							}
						}
					} else {
						// Nếu không tồn tại farmCode tạo mới farm với farmCode tự sinh
						FarmDto farmDto = rawData.convertToFarmDto();
						farmDto.setMapCode(rawData.getFarmCode());
						if (farmDto.getAdministrativeUnit() == null) {
							System.out.println(rawData.getWardName().trim() + "," + rawData.getDistrictName().trim()
									+ "," + rawData.getPronvinceName().trim());
							List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository
									.getAdministrativeUnitByName(rawData.getWardName().trim(),
											rawData.getDistrictName().trim(), rawData.getPronvinceName().trim());
							if (listFind != null && listFind.size() > 0) {
								farmDto.setAdministrativeUnit(new FmsAdministrativeUnitDto(listFind.get(0)));
								rawData.setWardCode(listFind.get(0).getCode());
								System.out.println(rawData.getWardCode());
							}
						}
						if (StringUtils.hasText(rawData.getProductTargetCode())) {
							List<ProductTarget> productTargets = producTargetRepository
									.findByCode(rawData.getProductTargetCode());
							if (productTargets != null && productTargets.size() > 0) {
								ProductTarget productTarget = productTargets.get(0);
								farmDto.setFarmProductTargets(new ArrayList<ProductTargetDto>());
								farmDto.getFarmProductTargets().add(new ProductTargetDto(productTarget));
							}
						}
						if (StringUtils.hasText(rawData.getOriginalCode())) {
							List<Original> originals = originalRepository.findByCode(rawData.getOriginalCode());
							if (originals != null && originals.size() > 0) {
								Original original = originals.get(index);
							}
						}

						farmDto = farmService.createFarm(farmDto);

						try {
							farm = farmService.findById(farmDto.getId());
						} catch (Exception e) {
							// TODO: handle exception
							continue;
						}
					}
				} else if (farm != null) {
					if (StringUtils.hasText(rawData.getFarmCode())) {
						// Nếu tồn tại farmCode => kiểm tra
						if (preFarmCode != null && rawData.getFarmCode().equalsIgnoreCase(preFarmCode)) {
							// Nếu farmCode bằng farmCode duyệt trước đó.
						} else {
							// Nếu farmCode không bằng farmCode duyệt trước đó
							List<Farm> farms = farmRepository.findByCode(rawData.getFarmCode());
							if (farms != null && farms.size() > 0) {
								farm = farms.get(0);
							} else {
								if (!StringUtils.hasText(rawData.getWardCode())) {
									List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository
											.getAdministrativeUnitByName(rawData.getWardName().trim(),
													rawData.getDistrictName().trim(),
													rawData.getPronvinceName().trim());
									if (listFind != null && listFind.size() > 0) {
										rawData.setWardCode(listFind.get(0).getCode());
									}
								}
								farms = farmRepository.findBymapCodeAndAdministrativeUnitCode(
										rawData.getFarmCode().trim(), rawData.getWardCode());
								System.out.println("rawData: " + rawData.getFarmName() + "**" + rawData.getFarmCode()
										+ "**" + rawData.getWardCode());
								if (farms != null && farms.size() > 0) {
									farm = farms.get(0);
								}
							}

							if (farms == null || farms.size() == 0) {
								// Nếu không tồn tại farmCode tạo mới farm với farmCode tự sinh
								FarmDto farmDto = rawData.convertToFarmDto();
								if (farmDto.getAdministrativeUnit() == null) {
									System.out.println(
											rawData.getWardName().trim() + "," + rawData.getDistrictName().trim() + ","
													+ rawData.getPronvinceName().trim());
									List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository
											.getAdministrativeUnitByName(rawData.getWardName().trim(),
													rawData.getDistrictName().trim(),
													rawData.getPronvinceName().trim());
									if (listFind != null && listFind.size() > 0) {
										farmDto.setAdministrativeUnit(new FmsAdministrativeUnitDto(listFind.get(0)));
										rawData.setWardCode(listFind.get(0).getCode());
										System.out.println(rawData.getWardCode());
									}
								}
								farmDto.setMapCode(rawData.getFarmCode());
								if (StringUtils.hasText(rawData.getProductTargetCode())) {
									List<ProductTarget> productTargets = producTargetRepository
											.findByCode(rawData.getProductTargetCode());
									if (productTargets != null && productTargets.size() > 0) {
										ProductTarget productTarget = productTargets.get(0);
										farmDto.setFarmProductTargets(new ArrayList<ProductTargetDto>());
										farmDto.getFarmProductTargets().add(new ProductTargetDto(productTarget));
									}
								}
//								if (StringUtils.hasText(rawData.getOriginalCode())) {
//									List<Original> originals = originalRepository.findByCode(rawData.getOriginalCode());
//									if (originals != null && originals.size() > 0) {
//										Original original = originals.get(0);
//									}
//								}

								farmDto = farmService.createFarm(farmDto);

								try {
									farm = farmService.findById(farmDto.getId());
								} catch (Exception e) {
									// TODO: handle exception
									continue;
								}
							}
						}
					} else {
						// Nếu không tồn tại farmCode thì tạo mới 1 farm
						FarmDto farmDto = rawData.convertToFarmDto();
						if (farmDto.getAdministrativeUnit() == null) {
							System.out.println(rawData.getWardName().trim() + "," + rawData.getDistrictName().trim()
									+ "," + rawData.getPronvinceName().trim());
							List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository
									.getAdministrativeUnitByName(rawData.getWardName().trim(),
											rawData.getDistrictName().trim(), rawData.getPronvinceName().trim());
							if (listFind != null && listFind.size() > 0) {
								farmDto.setAdministrativeUnit(new FmsAdministrativeUnitDto(listFind.get(0)));
								rawData.setWardCode(listFind.get(0).getCode());
								System.out.println(rawData.getWardCode());
							}
						}
						farmDto.setMapCode(rawData.getFarmCode());
						if (StringUtils.hasText(rawData.getProductTargetCode())) {
							List<ProductTarget> productTargets = producTargetRepository
									.findByCode(rawData.getProductTargetCode());
							if (productTargets != null && productTargets.size() > 0) {
								ProductTarget productTarget = productTargets.get(0);
								farmDto.setFarmProductTargets(new ArrayList<ProductTargetDto>());
								farmDto.getFarmProductTargets().add(new ProductTargetDto(productTarget));
							}
						}
						if (StringUtils.hasText(rawData.getOriginalCode())) {
							List<Original> originals = originalRepository.findByCode(rawData.getOriginalCode());
							if (originals != null && originals.size() > 0) {
								Original original = originals.get(index);
							}
						}

						farmDto = farmService.createFarm(farmDto);
						if (farmDto != null) {
							farm = farmService.findById(farmDto.getId());
						} else {
							// Nếu không tồn tại farmCode tạo mới farm với farmCode tự sinh
							farmDto = rawData.convertToFarmDto();
							farmDto.setMapCode(rawData.getFarmCode());
							if (farmDto.getAdministrativeUnit() == null) {
								System.out.println(rawData.getWardName().trim() + "," + rawData.getDistrictName().trim()
										+ "," + rawData.getPronvinceName().trim());
								List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository
										.getAdministrativeUnitByName(rawData.getWardName().trim(),
												rawData.getDistrictName().trim(), rawData.getPronvinceName().trim());
								if (listFind != null && listFind.size() > 0) {
									farmDto.setAdministrativeUnit(new FmsAdministrativeUnitDto(listFind.get(0)));
									rawData.setWardCode(listFind.get(0).getCode());
									System.out.println(rawData.getWardCode());
								}
							}
							if (StringUtils.hasText(rawData.getProductTargetCode())) {
								List<ProductTarget> productTargets = producTargetRepository
										.findByCode(rawData.getProductTargetCode());
								if (productTargets != null && productTargets.size() > 0) {
									ProductTarget productTarget = productTargets.get(0);
									farmDto.setFarmProductTargets(new ArrayList<ProductTargetDto>());
									farmDto.getFarmProductTargets().add(new ProductTargetDto(productTarget));
								}
							}
							if (StringUtils.hasText(rawData.getOriginalCode())) {
								List<Original> originals = originalRepository.findByCode(rawData.getOriginalCode());
								if (originals != null && originals.size() > 0) {
									Original original = originals.get(index);
								}
							}

							farmDto = farmService.createFarm(farmDto);

							try {
								farm = farmService.findById(farmDto.getId());
							} catch (Exception e) {
								// TODO: handle exception
								continue;
							}
						}
					}
				}
				preFarmCode = rawData.getFarmCode();

				FarmDto farmDto = new FarmDto();
				if (farm != null && farm.getId() != null) {
					farmDto.setId(farm.getId());
				}
				reportPeriodDto.setFarm(farmDto);
				
				AnimalDto animalDto = new AnimalDto();
				List<Animal> animals = null;
				if (StringUtils.hasText(rawData.getAnimalCode())) {
					animals = animalRepository.findByCode(rawData.getAnimalCode());
				}
				if (animals == null) {
					animals = animalRepository.findByAnimalName(rawData.getAnimalScienceName());
				}
				if (animals != null && animals.size() > 0) {
					animalDto.setId(animals.get(0).getId());
					animalDto.setName(animals.get(0).getName());
				} else {
					System.out.println(
							"Không tìm thấy loài: " + rawData.getAnimalScienceName() + "-" + rawData.getAnimalName());
					FileWriter fw;
//					try {
//						fw = new FileWriter("E:\\MyWork\\Projects_Java_Web\\wildLife\\Doc\\NotFoundAnimal.txt");
//						fw.write("Không tìm thấy loài: " + rawData.getAnimalScienceName()+"-"+rawData.getAnimalName() + "\n");
//			            fw.close();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}

					continue;
				}
				if (animalDto.getId() != null) {
					reportForm16Dto.setAnimal(animalDto);
				}
				reportItems.add(reportForm16Dto);

				System.out.println(farm.getCode() + "**" + farm.getName());
				reportPeriodDto.setReportItems(reportItems);
				reportPeriodDto.setType(WLConstant.ReportPeriod16Type.Report16A.getValue());
//				try {
				String date = format.format(rawData.getDateUpdate());
				String[] dates = date.split("/");
				int day = Integer.parseInt(dates[0]);
				int month = Integer.parseInt(dates[1]);
				int year = Integer.parseInt(dates[2]);

				reportPeriodDto.setDate(day);
				reportPeriodDto.setMonth(month);
				reportPeriodDto.setYear(year);

				List<ReportPeriod> reportPeriods = reportPeriodRepository.findByDayMonthYearType(day, month, year, WLConstant.ReportPeriod16Type.Report16A.getValue(), farm.getId());
				if (reportPeriods != null && reportPeriods.size() > 0) {
					ReportPeriod reportPeriod = reportPeriods.get(0);
					
					ReportForm16 item = null;
					if (reportForm16Dto != null && reportForm16Dto.getId() != null) {
						item = reportForm16Repository.getOne(reportForm16Dto.getId());
					}

					if (item == null || item.getId() == null) {
						item = new ReportForm16();
						item.setCreateDate(currentDate);
						item.setCreatedBy(currentUserName);
					} else {
						item.setModifiedBy(currentUserName);
						item.setModifyDate(currentDate);
					}

					item.setType(WLConstant.ReportPeriod16Type.Report16A.getValue());
					item.setDateReport(rawData.getDateUpdate());
					Animal animal = animalRepository.getOne(reportForm16Dto.getAnimal().getId());
					item.setAnimal(animal);
					Integer total=0;
					
					item.setMale(reportForm16Dto.getMale());
					item.setFemale(reportForm16Dto.getFemale());
					item.setUnGender(reportForm16Dto.getUnGender());
					
					item.setMaleParent(reportForm16Dto.getMaleParent());
						if(reportForm16Dto.getMaleParent()!=null) total+=reportForm16Dto.getMaleParent();
					item.setFemaleParent(reportForm16Dto.getFemaleParent());
						if(reportForm16Dto.getFemaleParent()!=null) total+=reportForm16Dto.getFemaleParent();
					item.setMaleGilts(reportForm16Dto.getMaleGilts());
						if(reportForm16Dto.getMaleGilts()!=null) total+=reportForm16Dto.getMaleGilts();
					item.setFemaleGilts(reportForm16Dto.getFemaleGilts());
						if(reportForm16Dto.getFemaleGilts()!=null) total+=reportForm16Dto.getFemaleGilts();
					item.setChildUnder1YearOld(reportForm16Dto.getChildUnder1YearOld());
						if(reportForm16Dto.getChildUnder1YearOld()!=null) total+=reportForm16Dto.getChildUnder1YearOld();
					item.setMaleChildOver1YearOld(reportForm16Dto.getMaleChildOver1YearOld());
						if(reportForm16Dto.getMaleChildOver1YearOld()!=null) total+=reportForm16Dto.getMaleChildOver1YearOld();
					item.setFemaleChildOver1YearOld(reportForm16Dto.getFemaleChildOver1YearOld());
						if(reportForm16Dto.getFemaleChildOver1YearOld()!=null) total+=reportForm16Dto.getFemaleChildOver1YearOld();
					item.setUnGenderChildOver1YearOld(reportForm16Dto.getUnGenderChildOver1YearOld());
						if(reportForm16Dto.getUnGenderChildOver1YearOld()!=null) total+=reportForm16Dto.getUnGenderChildOver1YearOld();
						
					item.setMaleImport(reportForm16Dto.getMaleImport());
					item.setFemaleImport(reportForm16Dto.getFemaleImport());
					item.setUnGenderImport(reportForm16Dto.getUnGenderImport());
					item.setMaleExport(reportForm16Dto.getMaleExport());
					item.setFemaleExport(reportForm16Dto.getFemaleExport());
					item.setUnGenderExport(reportForm16Dto.getUnGenderExport());
					
					item.setTotal(total);
					
					item.setNote(reportForm16Dto.getNote());
					item.setConfirmForestProtection(reportForm16Dto.getConfirmForestProtection());
					item.setFarm(farm);
					item.setReportPeriod(reportPeriod);
					
					if(listAdministrativeUnits != null && listAdministrativeUnits.size() >0) {
						item.setAdministrativeUnit(listAdministrativeUnits.get(0));
						if(listAdministrativeUnits.get(0).getParent()!=null) {
							item.setDistrict(listAdministrativeUnits.get(0).getParent());
							if(listAdministrativeUnits.get(0).getParent().getParent()!=null) {
								item.setProvince(listAdministrativeUnits.get(0).getParent().getParent());
							}
						}
					}
					if (item.getTotal() == 0) {
						continue;
					}
					item = reportForm16Repository.save(item);
					//reportPeriod.getReportItems().add(item);
					System.out.println(farm.getCode() + "**" + farm.getName() + "**" + animal.getName() + "**" + animal.getCode());
					// Cập nhật tới animalReportData
					
					//this.updateDataFromReportPeriodToAnimalReportData(reportPeriodDto);
					updateDataFromReportForm16ToAnimalReportDataByEntity(item);
					List<Long> animalIds= this.getListAnimalIds(reportPeriodDto.getFarm().getId(),reportPeriodDto.getYear());
					// Cập nhật tới báo cáo theo năm
					animalReportDataService.updateAnimalReportDataByFarmAnimalYear(reportPeriodDto.getFarm().getId(), animalIds, reportPeriodDto.getYear());

//						List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByFarmIdAndAnimalId(farm.getId(), animalDto.getId());
					List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService
							.getAllAnimalLastReportedByRecordMonthDayIsNull(farm.getId(), null);
					if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
						FarmAnimalTotalDto farmAnimalTotalDto = listAnimalReportTotal.get(0);
						if (farmAnimalTotalDto != null) {
							try {
								FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}

						}
					}
				} else {
					if(listAdministrativeUnits != null && listAdministrativeUnits.size() >0) {
						reportPeriodDto.setAdministrativeUnitDto(new FmsAdministrativeUnitDto());
						reportPeriodDto.getAdministrativeUnitDto().setId(listAdministrativeUnits.get(0).getId());
					}
					reportPeriodDto = this.saveOrUpdate(reportPeriodDto);
					// Cập nhật tới animalReportData
					if (reportPeriodDto != null) {
						this.updateDataFromReportPeriodToAnimalReportData(reportPeriodDto);
						List<Long> animalIds= getListAnimalIds(reportPeriodDto.getFarm().getId(),reportPeriodDto.getYear());
						// Cập nhật tới báo cáo theo năm
						animalReportDataService.updateAnimalReportDataByFarmAnimalYear(
								reportPeriodDto.getFarm().getId(), animalIds,
								reportPeriodDto.getYear());
						List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService
								.getAllAnimalLastReportedByFarmIdAndAnimalId(farm.getId(), animalDto.getId());
						if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
							FarmAnimalTotalDto farmAnimalTotalDto = listAnimalReportTotal.get(0);
							if (farmAnimalTotalDto != null) {
								try {
									FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
								} catch (Exception e) {
									e.printStackTrace();
									continue;
								}

							}
						}
					}

				}
//				} catch (Exception e) {
//					// TODO: handle exception
//				}

			}
		}
	}
	@Override
	public ImportResultDto<DataDvhdDto> updateFromFileImport(List<DataDvhdDto> list) {
		ImportResultDto<DataDvhdDto> ret = new ImportResultDto<DataDvhdDto>();
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		if(list!=null && list.size()>0) {
			ret.setTotalRow(list.size());
			for (DataDvhdDto rawData : list) {
				Farm farm=null;
				FmsAdministrativeUnit fmsAdministrativeUnit = null;
				Animal animal = null;
				/**
				 * Tìm kiếm đơn vị hành chính
				 */
				if (StringUtils.hasText(rawData.getWardCode())) {
					List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository.findListByCode(rawData.getWardCode());
					if(listFind!=null && listFind.size()>0) {
						fmsAdministrativeUnit=listFind.get(0);
					}										
				}
				if(fmsAdministrativeUnit==null) {
					List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository.getAdministrativeUnitByName(rawData.getWardName().trim(),rawData.getDistrictName().trim(), rawData.getPronvinceName().trim());
					if(listFind!=null && listFind.size()>0) {
						fmsAdministrativeUnit=listFind.get(0);
					}
				}
				//Tìm không thấy đơn vị hành chính bỏ qua dòng này
				if(fmsAdministrativeUnit==null) {
					rawData.setErrContent("Không tìm thấy đơn vị hành chính; "+rawData.getErrContent());
					ret.getListErr().add(rawData);
					continue;
				}
				/**
				 * Tìm kiếm loài
				 */
				List<Animal> animals = null;
				if (StringUtils.hasText(rawData.getAnimalCode())) {
					animals = animalRepository.findByCode(rawData.getAnimalCode());
				}
				if (animals == null) {
					animals = animalRepository.findByAnimalName(rawData.getAnimalScienceName());
				}
				animal = animals.get(0);
				if(animal==null) {
					rawData.setErrContent("Không tìm thấy Loài: "+rawData.getErrContent());
					ret.getListErr().add(rawData);
					continue;
				}
				/**
				 * Tìm kiếm trại
				 */
				if(rawData.getFarmCode()!=null && rawData.getFarmCode().length()>0) {//Tìm theo mã hệ thống
					List<Farm> farms = farmRepository.findByCode(rawData.getFarmCode());
					if(farms!=null && farms.size()>0) {
						farm=farms.get(0);
					}
				}
				if(farm==null) {//Tìm theo mã tự đặt của cơ sở và đơn vị hành chính
					List<Farm> farms  = farmRepository.findBymapCodeAndAdministrativeUnitCode(rawData.getFarmCode().trim(),	fmsAdministrativeUnit.getCode());
					if(farms!=null && farms.size()>0) {
						farm=farms.get(0);
					}
				}
				//Nếu tìm thấy và không phải trường hợp tạo mới thì update thông tin trại
				if(farm!=null) {
					farm.setNewRegistrationCode(rawData.getRegisterCode());
					farm.setOldRegistrationCode(rawData.getOldRegistrationCode());					
					farm.setLatitude(rawData.getLatitude());
					farm.setLongitude(rawData.getLongitude());
					farm.setOwnerName(rawData.getOwnerName());
					farm.setPhoneNumber(rawData.getPhoneNumber());
					farm.setOwnerCitizenCode(rawData.getOwnerCitizenCode());
					farm.setOwnerPhoneNumber(rawData.getOwnerPhoneNumber());
					farm.setVillage(rawData.getVillage());
					farm = farmRepository.save(farm);
				}				
				//Nếu không thấy thì tạo mới trại
				else if(farm==null) {
					FarmDto farmDto = rawData.convertToFarmDto();
					farmDto.setAdministrativeUnit(new FmsAdministrativeUnitDto(fmsAdministrativeUnit));
					farmDto.setMapCode(rawData.getFarmCode());
					if (StringUtils.hasText(rawData.getProductTargetCode())) {
						List<ProductTarget> productTargets = producTargetRepository.findByCode(rawData.getProductTargetCode());
						if (productTargets != null && productTargets.size() > 0) {
							ProductTarget productTarget = productTargets.get(0);
							farmDto.setFarmProductTargets(new ArrayList<ProductTargetDto>());
							farmDto.getFarmProductTargets().add(new ProductTargetDto(productTarget));
						}
					}
					farmDto = farmService.createFarm(farmDto);
					if(farmDto!=null && farmDto.getId()!=null && farmDto.getId()>0L) {
						farm = farmRepository.getOne(farmDto.getId());						
					}
				}
				//Nếu không tìm thấy trại và tạo mới trại không thành công thì bỏ qua dòng này
				if(farm==null) {
					rawData.setErrContent("Không tìm thấy trại, tạo mới trại không thành công!; "+rawData.getErrContent());
					ret.getListErr().add(rawData);
					continue;
				}
				//Check số liệu dòng hiện tại
				ReportForm16Dto reportForm16Dto = rawData.convertToReportForm16();
				reportForm16Dto.setDateReport(rawData.getDateUpdate());
				reportForm16Dto.setAnimal(new AnimalDto(animal));
				reportForm16Dto.setAdministrativeUnitDto(new FmsAdministrativeUnitDto(fmsAdministrativeUnit));
				
//				if (reportForm16Dto.getTotal() == 0) {
//					rawData.setErrContent("Số lượng bằng 0 ; "+rawData.getErrContent());
//					ret.getListErr().add(rawData);
//					continue;
//				}
				
				String date = format.format(rawData.getDateUpdate());
				String[] dates = date.split("/");
				int day = Integer.parseInt(dates[0]);
				int month = Integer.parseInt(dates[1]);
				int year = Integer.parseInt(dates[2]);
				
				List<ReportPeriod> reportPeriods = reportPeriodRepository.findByDayMonthYearType(day, month, year, WLConstant.ReportPeriod16Type.Report16A.getValue(), farm.getId());
				if (reportPeriods != null && reportPeriods.size() > 0) {
					ReportPeriod reportPeriod = reportPeriods.get(0);
					ReportForm16 item = null;
//					for (ReportForm16 reportForm16 : reportPeriod.getReportItems()) {
//						if(reportForm16.getAnimal().getId().equals(animal.getId())) {
//							/**
//							 * Lấy ReportForm16 có sẵn
//							 */
//							item = reportForm16;
//							item = rawData.updateReportForm16(item);
//							item.setType(WLConstant.ReportPeriod16Type.Report16A.getValue());
//							item.setDateReport(rawData.getDateUpdate());
//							item.setAnimal(animal);							
//							item.setAdministrativeUnit(fmsAdministrativeUnit);
//							if(fmsAdministrativeUnit.getParent()!=null) {
//								item.setDistrict(fmsAdministrativeUnit.getParent());
//								if(fmsAdministrativeUnit.getParent().getParent()!=null) {
//									item.setProvince(fmsAdministrativeUnit.getParent().getParent());
//								}
//							}
//							item.setFarm(farm);
//							break;
//						}
//					}
					if(item==null) {
						item = this.createReportForm16Entity(rawData, reportPeriod, animal, fmsAdministrativeUnit, farm, null);		
					}		
					item = reportForm16Repository.save(item);
					if(item==null || item.getId()==null || item.getId()==0) {
						rawData.setErrContent("Không lưu được bản ghi báo cáo 16; "+rawData.getErrContent());
						ret.getListErr().add(rawData);
						continue;
					}
					// Cập nhật tới animalReportData
					
					this.updateDataFromReportForm16ToAnimalReportDataByEntity(item);
					List<Long> animalIds= getListAnimalIds(farm.getId(),year);
					// Cập nhật tới báo cáo theo năm
					animalReportDataService.updateAnimalReportDataByFarmAnimalYear(farm.getId(), animalIds, year);
					List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByRecordMonthDayIsNull(farm.getId(), null);
					if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
						FarmAnimalTotalDto farmAnimalTotalDto = listAnimalReportTotal.get(0);
						if (farmAnimalTotalDto != null) {
							try {
								FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
							} catch (Exception e) {
								e.printStackTrace();
								continue;
							}

						}
					}
				}
				else {					
					ReportPeriodDto reportPeriodDto = new ReportPeriodDto();
					reportPeriodDto.setAdministrativeUnitDto(new FmsAdministrativeUnitDto(fmsAdministrativeUnit));
					reportPeriodDto.setType(WLConstant.ReportPeriod16Type.Report16A.getValue());
					reportPeriodDto.setFarm(new FarmDto(farm));
					reportPeriodDto.setDate(day);
					reportPeriodDto.setMonth(month);
					reportPeriodDto.setYear(year);
					reportPeriodDto.setReportItems(new HashSet<ReportForm16Dto>());
					
					reportPeriodDto.getReportItems().add(reportForm16Dto);
					
					reportPeriodDto = this.saveOrUpdate(reportPeriodDto);
					/**
					 *  Cập nhật tới animalReportData
					 */
					if (reportPeriodDto != null) {
						this.updateDataFromReportPeriodToAnimalReportData(reportPeriodDto);
						List<Long> animalIds= getListAnimalIds(reportPeriodDto.getFarm().getId(),reportPeriodDto.getYear());
						/**
						 *  Cập nhật tới báo cáo theo năm
						 */
						animalReportDataService.updateAnimalReportDataByFarmAnimalYear(farm.getId(), animalIds, reportPeriodDto.getYear());
						List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByFarmIdAndAnimalId(farm.getId(), animal.getId());
						if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
							FarmAnimalTotalDto farmAnimalTotalDto = listAnimalReportTotal.get(0);
							if (farmAnimalTotalDto != null) {
								try {
									FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
								} catch (Exception e) {
									e.printStackTrace();
									continue;
								}

							}
						}
					}
					else {
						rawData.setErrContent("Tạo kỳ báo cáo lỗi; "+rawData.getErrContent());
						ret.getListErr().add(rawData);						
						continue;
					}
				}
				ret.setTotalSuccess(ret.getTotalSuccess()+1);
			}
		}
		return ret;
	}
	public static int countR = 0;
	@Override
	public DataDvhdDto updateFromFileImportByOneRow(DataDvhdDto rawData) {
		System.out.println(++countR);
		if(rawData!=null) {
			Farm farm=null;
			Original original = null;
			HusbandryMethod husbandryMethod = null;
			FmsAdministrativeUnit fmsAdministrativeUnit = null;
			Animal animal = null;
			FarmDto farmDto = null;
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			ReportForm16 item = null;
			ReportPeriod reportPeriod=null;
			List<AnimalReportData> listAnimalReportData=null;
			List<AnimalReportData> listAnimalReportDataEndYear=null;
			/**
			 * Tìm kiếm đơn vị hành chính
			 */
			if(rawData.getDateUpdate()==null) {
				System.out.println(++countR+"----date");
				rawData.setErrContent("Không rõ ngày - tháng"+rawData.getErrContent());
				return rawData;
			}
			if(rawData.getDateUpdate()!=null) {
				try {
					final String regex1 = "\\d{2}/\\d{2}/\\d{4}";
					final String regex2 = "\\d{1}/\\d{1}/\\d{4}";
					
					SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
					String date = sdf.format(rawData.getDateUpdate());
					Pattern pattern1 = Pattern.compile(regex1, Pattern.MULTILINE);
			        Matcher matcher1 = pattern1.matcher(date);
			        
			        Pattern pattern2 = Pattern.compile(regex2, Pattern.MULTILINE);
			        Matcher matcher2 = pattern2.matcher(date);
			        
			        boolean isMatch1 = matcher1.matches();
			        boolean isMatch2 = matcher2.matches();
			        Date dt = null;
			        if(isMatch1 || isMatch2) {
			        	//System.out.println("Ngày cấp khớp");
			            try 
			            {
			                 dt = sdf.parse(date);
			                 Date todayDate = new Date();
			                 Date todayDateFormat = sdf.parse(sdf.format(todayDate));
			                 if(todayDateFormat.before(dt)) {
			                	 rawData.setErrContent("Ngày cập nhật báo cáo không được vượt qua ngày hiện tại!");
									return rawData;
			                 }
			            }
			            catch (Exception e)
			            {
			            	System.out.println("Ép Ngày lỗi");
			            }
			        	
			        }else {
			        	//System.out.println("Ngày cấp không khớp");
			        	rawData.setErrContent("Ngày cập nhật báo cáo không đúng định dạng(vd:08/08/2021)");
						return rawData;
			        }
				}catch(Exception ex) {
					rawData.setErrContent("Số điện thoại nhập không đúng định dạng");
					return rawData;
				}
			}
			
			if(rawData.getLatitude() !=null && StringUtils.hasText(rawData.getLatitude())) {
				try {
					char[] lttCharArray = rawData.getLatitude().toCharArray();
					StringBuilder ltt = new StringBuilder();
					for(char l : lttCharArray) {
						if(l==',') {
							l='.';
						}
						ltt.append(String.valueOf(l));
					}
					if(Float.parseFloat(ltt.toString()) >=8.3 && Float.parseFloat(ltt.toString())<=23.5) {
						
					}else {
						rawData.setErrContent("vĩ độ không đúng (vĩ độ phải lớn hơn 8.3 và nhỏ hơn 23.5)");
						return rawData;
					}
					//System.out.println("Vĩ độ:"+ltt.toString());
				}catch (NumberFormatException e) {
					
					//e.printStackTrace();
					rawData.setErrContent("vĩ độ không đúng");
					System.out.println("Vĩ độ không đúng định dạng");
					return rawData;
				}
				
			}
//			else {
//				rawData.setErrContent("vĩ độ không được để trống");
//				System.out.println("Vĩ độ không được để trống");
//				return rawData;
//			}
			
			
			if(rawData.getPhoneNumber()!=null && rawData.getPhoneNumber().length()>0) {
				try {
					Long a =(long) Float.parseFloat(rawData.getPhoneNumber());
				}catch(Exception e) {
					rawData.setErrContent("định dạng sđt cơ sở không đúng");
					return rawData;
				}
			}
			if(rawData.getOwnerPhoneNumber()!=null && rawData.getOwnerPhoneNumber().length()>0) {
				try {
					Long a =(long) Float.parseFloat(rawData.getOwnerPhoneNumber());
				}catch(Exception e) {
					rawData.setErrContent("định dạng sđt chủ sở hữu không đúng");
					return rawData;
				}
			}
			
			if(rawData.getLongitude() !=null && StringUtils.hasText(rawData.getLongitude())) {
					try {
						char[] lttCharArray = rawData.getLongitude().toCharArray();
						StringBuilder ltt = new StringBuilder();
						for(char l : lttCharArray) {
							if(l==',') {
								l='.';
							}
							ltt.append(String.valueOf(l));
						}
						if(Float.parseFloat(ltt.toString()) >=101.7 && Float.parseFloat(ltt.toString())<=109.5) {
							
						}else {
							rawData.setErrContent("Kinh độ không đúng (kinh độ phải lớn hơn 101.7 và nhỏ hơn 109.5)");
							return rawData;
						}
						//System.out.println("Kinh độ: "+ltt.toString());
					}catch (NumberFormatException e) {
						rawData.setErrContent("Kinh độ không đúng");
						System.out.println("Kinh độ sai định dạng");
						return rawData;
					}
					
			}
//			else {
//				rawData.setErrContent("Kinh độ không được để trống");
//				System.out.println("Kinh độ không được để trống");
//				return rawData;
//			}
			if (StringUtils.hasText(rawData.getWardCode())) {
				List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository.findListByCode(rawData.getWardCode());
				if(listFind!=null && listFind.size()>0) {
					fmsAdministrativeUnit=listFind.get(0);
				}										
			}
			if(fmsAdministrativeUnit==null) {
				List<FmsAdministrativeUnit> listFind = fmsAdministrativeUnitRepository.getAdministrativeUnitByName(rawData.getWardName().trim(),rawData.getDistrictName().trim(), rawData.getPronvinceName().trim());
				if(listFind!=null && listFind.size()>0) {
					fmsAdministrativeUnit=listFind.get(0);
				}
			}
			//Tìm không thấy đơn vị hành chính bỏ qua dòng này
			if(fmsAdministrativeUnit==null) {
				rawData.setErrContent("Không tìm thấy đơn vị hành chính; "+rawData.getErrContent());
				return rawData;
			}
			/**
			 * Tìm kiếm loài
			 */
			List<Animal> animals = null;
			System.out.println(rawData.getAnimalCode());
			if(rawData.getAnimalCode().equals(" M106"))
			{
				System.out.println("ok");
			}
			if (StringUtils.hasText(rawData.getAnimalCode())) {
				animals = animalRepository.findByCode(rawData.getAnimalCode().trim());
			}
			if (animals == null) {
				animals = animalRepository.findByAnimalName(rawData.getAnimalScienceName());
			}
			if(animals!=null && animals.size()>0) {
				animal = animals.get(0);
			}			
			if(animal==null) {
				System.out.println("Bản ghi lỗi animals:" +"AnimalCode"+ rawData.getAnimalCode() +"- FarmName"+rawData.getFarmName()+"-"+rawData.getAnimalScienceName());
				rawData.setErrContent("Không tìm thấy Loài; "+rawData.getErrContent());
				return rawData;
			}
			/**
			 * Tìm kiếm trại
			 */
			if(rawData.getFarmCode()!=null && rawData.getFarmCode().length()>0) {//Tìm theo mã hệ thống
				List<Farm> farms = farmRepository.findByCode(rawData.getFarmCode());
				if(farms!=null && farms.size()>0) {
					farm=farms.get(0);
				}
			}
			if(farm==null) {//Tìm theo mã tự đặt của cơ sở và đơn vị hành chính
				List<Farm> farms  = farmRepository.findBymapCodeAndAdministrativeUnitCode(rawData.getFarmCode().trim(),	fmsAdministrativeUnit.getCode());
				if(farms!=null && farms.size()>0) {
					farm=farms.get(0);
				}
			}
			//Nếu tìm thấy và không phải trường hợp tạo mới thì update thông tin trại
			if(farm!=null) {
				if(StringUtils.hasText(rawData.getRegisterCode()))
					farm.setNewRegistrationCode(rawData.getRegisterCode());
				if(StringUtils.hasText(rawData.getOldRegistrationCode()))
					farm.setOldRegistrationCode(rawData.getOldRegistrationCode());
				if(StringUtils.hasText(rawData.getLatitude()))
					farm.setLatitude(rawData.getLatitude());
				if(StringUtils.hasText(rawData.getLongitude()))
					farm.setLongitude(rawData.getLongitude());
				if(StringUtils.hasText(rawData.getOwnerName()))
					farm.setOwnerName(rawData.getOwnerName());
//				if(StringUtils.hasText(rawData.getPhoneNumber()))
//					farm.setPhoneNumber(rawData.getPhoneNumber());
				
				//dob
				if (rawData.getOwnerYearOfBirth() != null) {
					try {
						Date dob = WLDateTimeUtil.numberToDate(1, 1, Integer.parseInt(rawData.getOwnerYearOfBirth()));
						farm.setOwnerDob(dob);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if(rawData.getPhoneNumber() != null && StringUtils.hasText(rawData.getPhoneNumber())) {
					try {
//						final String regex = "^0[0-9]{9,10}$";
						final String regex = "^0[0-9]{5,20}$";
						Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
				        Matcher matcher = pattern.matcher(rawData.getPhoneNumber());
				        
				        boolean isMatch = matcher.matches();
				        if(isMatch) {
				        	//System.out.println("SDT Update - Khớp");
				        	farm.setPhoneNumber(rawData.getPhoneNumber());
				        }else {
				        	//System.out.println("SDT Update - Không khớp");
				        	rawData.setErrContent("Số điện thoại nhập không đúng định dạng");
							return rawData;
				        }
					}catch(Exception ex) {
						rawData.setErrContent("Số điện thoại nhập không đúng định dạng");
						return rawData;
					}
				}
				if(StringUtils.hasText(rawData.getOwnerCitizenCode()))
					farm.setOwnerCitizenCode(rawData.getOwnerCitizenCode());
//				if(StringUtils.hasText(rawData.getOwnerPhoneNumber()))
//					farm.setOwnerPhoneNumber(rawData.getOwnerPhoneNumber());
				if(rawData.getOwnerPhoneNumber() != null && StringUtils.hasText(rawData.getOwnerPhoneNumber())) {
					try {
//						final String regex = "^0[0-9]{9,10}$";// 9 hoac 10 so
						final String regex = "^0[0-9]{5,20}$"; // bat dau 0 va tu 0 den 9
						Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
				        Matcher matcher = pattern.matcher(rawData.getOwnerPhoneNumber());
				        
				        boolean isMatch = matcher.matches();
				        if(isMatch) {
				        	//System.out.println("SDT Owner Update - Khớp");
				        	farm.setOwnerPhoneNumber(rawData.getOwnerPhoneNumber());
				        }else {
				        	//System.out.println("SDT Owner Update - Không khớp");
				        	rawData.setErrContent("Số điện thoại chủ sở hữu nhập không đúng định dạng");
							return rawData;
				        }
					}catch(Exception ex) {
						rawData.setErrContent("Số điện thoại chủ sở hữu nhập không đúng định dạng");
						return rawData;
					}
				}
				if(StringUtils.hasText(rawData.getVillage()))
					farm.setVillage(rawData.getVillage());
				if(rawData.getStartDate()!=null ) {
					farm.setStartDate(rawData.getStartDate());
				}
				try {
					if(rawData.getRegisterStatus()!=null ) {
						double a= Double.valueOf(rawData.getRegisterStatus());
						//System.out.println("row a: "+a);
						if(a==1) {
							farm.setStatus(3);
						}else {
							farm.setStatus((int)a);
						}
					}
				} catch (Exception e) {
					
				}
//				if(rawData.getDateRegistration()!=null) {
//					farm.setDateRegistration(rawData.getDateRegistration());
//				}
				if(rawData.getDateRegistration() != null) {
					try {
						final String regex1 = "\\d{2}/\\d{2}/\\d{4}";
						final String regex2 = "\\d{1}/\\d{1}/\\d{4}";
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						String date = sdf.format(rawData.getDateRegistration());
						Pattern pattern1 = Pattern.compile(regex1, Pattern.MULTILINE);
				        Matcher matcher1 = pattern1.matcher(date);
				        
				        Pattern pattern2 = Pattern.compile(regex2, Pattern.MULTILINE);
				        Matcher matcher2 = pattern2.matcher(date);
				        
				        boolean isMatch1 = matcher1.matches();
				        boolean isMatch2 = matcher2.matches();
				        Date dt = null;
				        if(isMatch1 || isMatch2) {
				            try 
				            {
				                 dt = sdf.parse(date);
				                 farm.setDateRegistration(dt);
				            }
				            catch (Exception e)
				            {
				            	System.out.println("Ép ngày lỗi");
				            }
				        	//System.out.println("Ngày cấp khớp");
				        	
				        }else {
				        	//System.out.println("Ngày cấp không khớp");
				        	rawData.setErrContent("Ngày cấp mã đăng ký(theo 06) nhập không đúng định dạng(vd:08/08/2021)");
							return rawData;
				        }
					}catch(Exception ex) {
						rawData.setErrContent("Số điện thoại nhập không đúng định dạng");
						return rawData;
					}
				}	
				
				if(rawData.getDateOtherRegistration() != null) {
					try {
						final String regex1 = "\\d{2}/\\d{2}/\\d{4}";
						final String regex2 = "\\d{1}/\\d{1}/\\d{4}";						
						
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						String date = sdf.format(rawData.getDateOtherRegistration());
						Pattern pattern1 = Pattern.compile(regex1, Pattern.MULTILINE);
				        Matcher matcher1 = pattern1.matcher(date);
				        
				        Pattern pattern2 = Pattern.compile(regex2, Pattern.MULTILINE);
				        Matcher matcher2 = pattern2.matcher(date);
				        
				        boolean isMatch1 = matcher1.matches();
				        boolean isMatch2 = matcher2.matches();
				        Date dt = null;
				        if(isMatch1 || isMatch2) {
				        	//System.out.println("Ngày cấp khớp");
				            try 
				            {
				                 dt = sdf.parse(date);
				                 farm.setDateOtherRegistration(dt);
				            }
				            catch (Exception e)
				            {
				            	System.out.println("Ép Ngày lỗi");
				            }
				        	
				        }else {
				        	//System.out.println("Ngày cấp không khớp");
				        	rawData.setErrContent("Ngày cấp mã CN đăng ký nhập không đúng định dạng(vd:08/08/2021)");
							return rawData;
				        }
					}catch(Exception ex) {
						rawData.setErrContent("Số điện thoại nhập không đúng định dạng");
						return rawData;
					}
				}
				if(rawData.getApartmentNumber()!=null) {
					farm.setApartmentNumber(rawData.getApartmentNumber());
				}
				if(rawData.getAdressDetail() != null) {
					farm.setAdressDetail(rawData.getAdressDetail());
				}
				if(rawData.getOwnerCitizenDate()!=null ) {
					farm.setOwnerCitizenDate(rawData.getOwnerCitizenDate());
				}
				if(StringUtils.hasText(rawData.getOwnerCitizenLocation())) {
					farm.setOwnerCitizenLocation(rawData.getOwnerCitizenLocation());
				}
				if(StringUtils.hasText(rawData.getTtbvmt())) {
					farm.setTtbvmt(rawData.getTtbvmt());
				}
				if(rawData.getTtbvmtDate()!=null) {
					farm.setTtbvmtDate(rawData.getTtbvmtDate());
				}
				try {
					if(rawData.getLodgingAcreage()!=null ) {
						farm.setLodgingAcreage(Double.valueOf(rawData.getLodgingAcreage()));
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if(StringUtils.hasText(rawData.getBusinessRegistrationNumber())) {
					farm.setBusinessRegistrationNumber(rawData.getBusinessRegistrationNumber());
				}
				
//				if(StringUtils.hasText(rawData.getBusinessRegistrationNumber())) {
//					farm.setBusinessRegistrationNumber(rawData.getBusinessRegistrationNumber());
//				}
				if (StringUtils.hasText(rawData.getProductTargetCode())) {
					int dem = 0;
					String ptm="";
					String[] ptmArr = new String[] {"Z","Q","R","S","P","O"};
					String tm="";
					try {
						for(String w:rawData.getProductTargetCode().split(",",0)) {
							for(String x:ptmArr) {
								if(x.compareTo(w)==0) {
									ptm="Z";
								}
							}
							if(w.compareTo("T") == 0) {
								tm="T";
							}
							dem++;
						}
						if(tm.compareTo("T") == 0 && ptm.compareTo("Z") == 0) {
							rawData.setErrContent("Mục đích nuôi không được vừa thương mại vừa phi thương mại(chỉ nhập Z hoặc T)");
							return rawData;
						}else {
							Set<FarmProductTarget> farmProducts= farm.getFarmProductTargets();
							farmProducts.clear();
							for(String w:rawData.getProductTargetCode().split(",",0)) {
								
								List<ProductTarget> productTargets = producTargetRepository.findByCode(w.trim());
								if (productTargets != null && productTargets.size() > 0) {
									ProductTarget productTarget = productTargets.get(0);
									FarmProductTarget fpt= new FarmProductTarget();
									fpt.setFarm(farm);
									fpt.setProductTarget(productTarget);
									farmProducts.add(fpt);
								}else {
									System.out.println("Không tìm thấy product target");
								}
							}
							farm.setFarmProductTargets(farmProducts);
						}
					}
					catch(Exception e) {
						e.printStackTrace();
						rawData.setErrContent("Mục đích nuôi không đúng định dạng");
						return rawData;
					}
				}
				// Loại hình nuôi
				if (rawData.getHusbandryMethods()!=null && StringUtils.hasText(rawData.getHusbandryMethods())) {
					String[] husArr = new String[] {"SS","ST","SS-ST"};
					String validHusCode = "";
					try {
						String[] oriDataArr = rawData.getHusbandryMethods().split(",",0);
						if(oriDataArr.length>1) {
							rawData.setErrContent("chỉ được chọn 1 loại hình nuôi, vui lòng không điền thêm dấu phảy!");
							return rawData;
						}else {
							for(String x:husArr) {
								if(oriDataArr[0].compareTo(x)==0) {
									validHusCode="Y";
								}
							}
							if(validHusCode.compareTo("Y")==0) {
								List<HusbandryMethod> huslist = husbandryMethodRepository.findByCode(oriDataArr[0]);
								if (huslist != null && huslist.size() > 0) {
									HusbandryMethod husMethod = huslist.get(0);
									FarmHusbandryMethod farmHusbandryMethod =  new FarmHusbandryMethod();
									farmHusbandryMethod.setHusbandryMethod(husMethod);
									farmHusbandryMethod.setFarm(farm);
									farm.getFarmHusbandryMethods().clear();
									farm.getFarmHusbandryMethods().add(farmHusbandryMethod);
								}
							}else {
								rawData.setErrContent("loại hình nuôi không nằm trong danh sách hợp lệ:{SS,ST,SS-ST}");
								return rawData;
							}
						}
					}catch(Exception e) {
						System.out.print(e);
						rawData.setErrContent("loại hình nuôi không nằm trong danh sách hợp lệ:{SS,ST,SS-ST}");
						return rawData;
					}
				}
				
			}
			//Nếu không thấy thì tạo mới trại
			if(farm==null) {
				farmDto = rawData.convertToFarmDto();
				farmDto.setAdministrativeUnit(new FmsAdministrativeUnitDto(fmsAdministrativeUnit));
				farmDto.setMapCode(rawData.getFarmCode());
				
				
				
				if(rawData.getDateRegistration() != null) {
					
					try {
						final String regex1 = "\\d{2}/\\d{2}/\\d{4}";
						final String regex2 = "\\d{1}/\\d{1}/\\d{4}";
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						String date = sdf.format(rawData.getDateRegistration());
						Pattern pattern1 = Pattern.compile(regex1, Pattern.MULTILINE);
				        Matcher matcher1 = pattern1.matcher(date);
				        
				        Pattern pattern2 = Pattern.compile(regex2, Pattern.MULTILINE);
				        Matcher matcher2 = pattern2.matcher(date);
				        
				        boolean isMatch1 = matcher1.matches();
				        boolean isMatch2 = matcher2.matches();
				        Date dt = null;
				        if(isMatch1||isMatch2) {
				        	//System.out.println("Ngày cấp khớp");
				            try 
				            {
				                 dt = sdf.parse(date);
				                 farmDto.setDateRegistration(dt);
				            }
				            catch (Exception e)
				            {
				            	System.out.println("Ép ngày lỗi");
				            }
				        	
				        }else {
				        	//System.out.println("Ngày cấp không khớp");
				        	rawData.setErrContent("Ngày cấp mã đăng ký(theo 06) nhập không đúng định dạng(vd:08/08/2021)");
							return rawData;
				        }
					}catch(Exception ex) {
						rawData.setErrContent("Số điện thoại nhập không đúng định dạng");
						return rawData;
					}
				}
				if(rawData.getDateOtherRegistration() != null) {
					
					try {
						final String regex1 = "\\d{2}/\\d{2}/\\d{4}";
						final String regex2 = "\\d{1}/\\d{1}/\\d{4}";
						SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
						String date = sdf.format(rawData.getDateOtherRegistration());
						Pattern pattern1 = Pattern.compile(regex1, Pattern.MULTILINE);
				        Matcher matcher1 = pattern1.matcher(date);
				        
				        Pattern pattern2 = Pattern.compile(regex2, Pattern.MULTILINE);
				        Matcher matcher2 = pattern2.matcher(date);
				        
				        boolean isMatch1 = matcher1.matches();
				        boolean isMatch2 = matcher2.matches();
				        Date dt = null;
				        if(isMatch1||isMatch2) {
				        	//System.out.println("Ngày cấp khớp");
				            try 
				            {
				                 dt = sdf.parse(date);
				                 farmDto.setDateOtherRegistration(dt);
				            }
				            catch (Exception e)
				            {
				            	System.out.println("Ép ngày lỗi");
				            }
				        	
				        }else {
				        	//System.out.println("Ngày cấp không khớp");
				        	rawData.setErrContent("Ngày cấp mã CN đăng ký nhập không đúng định dạng(vd:08/08/2021)");
							return rawData;
				        }
					}catch(Exception ex) {
						rawData.setErrContent("Số điện thoại nhập không đúng định dạng");
						return rawData;
					}
				}
				farmDto.setBusinessRegistrationNumber(rawData.getBusinessRegistrationNumber());
				//Validate phoneNumber
				if(rawData.getPhoneNumber() != null && StringUtils.hasText(rawData.getPhoneNumber())) {
					try {
//						final String regex = "^0[0-9]{9,10}$";
						final String regex = "^0[0-9]{5,20}$";  
						Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
				        Matcher matcher = pattern.matcher(rawData.getPhoneNumber());
				        
				        boolean isMatch = matcher.matches();
				        if(isMatch) {
				        	//System.out.println("SDT Create - Khớp");
				        	farmDto.setPhoneNumber(rawData.getPhoneNumber());
				        }else {
				        	//System.out.println("SDT Create - Không Khớp");
				        	rawData.setErrContent("Số điện thoại nhập không đúng định dạng");
							return rawData;
				        }
					}catch(Exception ex) {
						rawData.setErrContent("Số điện thoại nhập không đúng định dạng");
						return rawData;
					}
				}
				if(rawData.getOwnerPhoneNumber() != null && StringUtils.hasText(rawData.getOwnerPhoneNumber())) {
					try {
//						final String regex = "^0[0-9]{9,10}$";
						final String regex = "^0[0-9]{5,20}$";
						Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
				        Matcher matcher = pattern.matcher(rawData.getOwnerPhoneNumber());
				        
				        boolean isMatch = matcher.matches();
				        if(isMatch) {
				        	//System.out.println("SDT Owner Create - Khớp");
				        	farmDto.setOwnerPhoneNumber(rawData.getOwnerPhoneNumber());
				        }else {
				        	//System.out.println("SDT Owner Create - Không khớp");
				        	rawData.setErrContent("Số điện thoại chủ sở hữu nhập không đúng định dạng");
							return rawData;
				        }
					}catch(Exception ex) {
						rawData.setErrContent("Số điện thoại chủ sở hữu nhập không đúng định dạng");
						return rawData;
					}
				}
				//Xử lý mục đích nuôi không cho chọn vừa thương mại và phi thương mại
				if (StringUtils.hasText(rawData.getProductTargetCode())) {
					int dem = 0;
					String ptm="";
					String[] ptmArr = new String[] {"Z","Q","R","S","O"};
					String tm="";
					try {
						for(String w:rawData.getProductTargetCode().split(",",0)) {
							for(String x:ptmArr) {
								if(x.compareTo(w)==0) {
									ptm="Z";
								}
							}
							if(w.compareTo("T") == 0) {
								tm="T";
							}
							dem++;
						}
						if(tm.compareTo("T") == 0 && ptm.compareTo("Z") == 0) {
							rawData.setErrContent("Mục đích nuôi không được vừa thương mại vừa phi thương mại(chỉ nhập Z hoặc T)");
							return rawData;
						}else {
							if(dem==1) {
								List<ProductTarget> productTargets = producTargetRepository.findByCode(rawData.getProductTargetCode());
								if (productTargets != null && productTargets.size() > 0) {
									ProductTarget productTarget = productTargets.get(0);
									farmDto.setFarmProductTargets(new ArrayList<ProductTargetDto>());
									farmDto.getFarmProductTargets().add(new ProductTargetDto(productTarget));
								}else {
									System.out.println("Không tìm thấy product target");
								}
							}else if(dem>1){
								for(int i=0;i<dem;i++) {
									farmDto.setFarmProductTargets(new ArrayList<ProductTargetDto>());
									for(String w:rawData.getProductTargetCode().split(",",0)) {
										List<ProductTarget> productTargets = producTargetRepository.findByCode(w);
										if (productTargets != null && productTargets.size() > 0) {
											ProductTarget productTarget = productTargets.get(0);
											farmDto.getFarmProductTargets().add(new ProductTargetDto(productTarget));
										}else {
											System.out.println("Không tìm thấy product target");
										}
									}
								}
							}
						}
					}
					catch(Exception e) {
						e.printStackTrace();
						rawData.setErrContent("Mục đích nuôi không đúng định dạng");
						return rawData;
					}
				}
				// Loại hình nuôi
				if (rawData.getHusbandryMethods()!=null && StringUtils.hasText(rawData.getHusbandryMethods())) {
					String[] husArr = new String[] {"SS","ST","SS-ST"};
					String validHusCode = "";
					try {
						String[] oriDataArr = rawData.getHusbandryMethods().split(",",0);
						if(oriDataArr.length>1) {
							rawData.setErrContent("chỉ được chọn 1 loại hình nuôi, vui lòng không điền thêm dấu phảy!");
							return rawData;
						}else {
							for(String x:husArr) {
								if(oriDataArr[0].compareTo(x)==0) {
									validHusCode="Y";
								}
							}
							if(validHusCode.compareTo("Y")==0) {
								List<HusbandryMethodDto> huslist = husbandryMethodRepository.findByCode2(oriDataArr[0]);
								if (huslist != null && huslist.size() > 0) {
									HusbandryMethodDto husMethod = huslist.get(0);
									FarmHusbandryMethodDto farmHusbandryMethod =  new FarmHusbandryMethodDto();
									farmHusbandryMethod.setHusbandryMethod(husMethod);
									farmHusbandryMethod.setFarm(farmDto);
									Set<FarmHusbandryMethodDto> farmHusbandryMethods = new HashSet<FarmHusbandryMethodDto>();
									farmDto.setFarmHusbandryMethods(farmHusbandryMethods);
//									farmDto.getFarmHusbandryMethods().clear();
									farmDto.getFarmHusbandryMethods().add(farmHusbandryMethod);
								}
							}else {
								rawData.setErrContent("loại hình nuôi không nằm trong danh sách hợp lệ:{SS,ST,SS-ST}");
								return rawData;
							}
						}
					}catch(Exception e) {
						System.out.print(e);
						rawData.setErrContent("loại hình nuôi không nằm trong danh sách hợp lệ:{SS,ST,SS-ST}");
						return rawData;
					}
				}
			}

			//Nguon goc
			if (rawData.getOriginalCode()!=null && StringUtils.hasText(rawData.getOriginalCode())) {
				String[] originalArr = new String[] {"C","R","U","W","NK","I"};
				String validOriginalCode = "";
				try {
					String[] oriDataArr = rawData.getOriginalCode().split(",",0);
					if(oriDataArr.length>1) {
						rawData.setErrContent("chỉ được chọn 1 nguồn gốc vật nuôi, vui lòng không điền thêm dấu phảy!");
						return rawData;
					}else {
						for(String x:originalArr) {
							if(oriDataArr[0].compareTo(x)==0) {
								validOriginalCode="Y";
							}
						}
						if(validOriginalCode.compareTo("Y")==0) {
							List<Original> oriList = originalRepository.findByCode(oriDataArr[0]);
							original = oriList.get(0);
						}else {
							rawData.setErrContent("nguồn gốc vật nuôi không nằm trong danh sách hợp lệ:{C,R,U,W,NK,I}");
							return rawData;
						}
					}
				}catch(Exception e) {
					rawData.setErrContent("nguồn gốc vật nuôi không nằm trong danh sách hợp lệ:{C,R,U,W,NK,I}");
					return rawData;
				}
			}
			
			

			String date = format.format(rawData.getDateUpdate());
			String[] dates = date.split("/");
			int day = Integer.parseInt(dates[0]);
			int month = Integer.parseInt(dates[1]);
			int year = Integer.parseInt(dates[2]);
			List<ReportPeriod> reportPeriods = null;
			if(farm!=null) {
				reportPeriods = reportPeriodRepository.findByDayMonthYearType(day, month, year, WLConstant.ReportPeriod16Type.Report16A.getValue(), farm.getId());
			}
			
			if (reportPeriods != null && reportPeriods.size() > 0) {
				reportPeriod = reportPeriods.get(0);				
				if(reportPeriod.getReportItems()!=null && reportPeriod.getReportItems().size()>0) {
					for (ReportForm16 reportForm16 : reportPeriod.getReportItems()) {
						if(reportForm16.getAnimal().getId().equals(animal.getId())) {
							/**
							 * Lấy ReportForm16 có sẵn
							 */
							item = reportForm16;
							item = rawData.updateReportForm16(item);
							item.setType(WLConstant.ReportPeriod16Type.Report16A.getValue());
							item.setDateReport(rawData.getDateUpdate());
							item.setAnimal(animal);			
							item.setOriginal(original);
							item.setAdministrativeUnit(fmsAdministrativeUnit);
							if(fmsAdministrativeUnit.getParent()!=null) {
								item.setDistrict(fmsAdministrativeUnit.getParent());
								if(fmsAdministrativeUnit.getParent().getParent()!=null) {
									item.setProvince(fmsAdministrativeUnit.getParent().getParent());
								}
							}
							item.setFarm(farm);
							break;
						}
					}
				}
				if(item==null){
					/**
					 * Tạo mới ReportForm16
					 */
					item = this.createReportForm16Entity(rawData, reportPeriod, animal, fmsAdministrativeUnit, farm, original);	
				}
			}
			else {
				/**
				 * Tạo mới ReportForm16
				 */
				item = rawData.convertToReportForm16Entity();
				item.setDateReport(rawData.getDateUpdate());
				item.setAnimal((animal));
				item.setAdministrativeUnit((fmsAdministrativeUnit));
				item.setFarm(farm);
				item.setOriginal(original);
				item.setAnimal(animal);
				if(fmsAdministrativeUnit.getParent()!=null) {
					item.setDistrict(fmsAdministrativeUnit.getParent());
					if(fmsAdministrativeUnit.getParent().getParent()!=null) {
						item.setProvince(fmsAdministrativeUnit.getParent().getParent());
					}
				}
//				if (item.getTotal() == 0) {
//					rawData.setErrContent("Số lượng bằng 0 ; "+rawData.getErrContent());
//					return rawData;
//				}
				/**
				 * Tạo mới ReportPeriod
				 */
				reportPeriod = new ReportPeriod();
				reportPeriod.setType(WLConstant.ReportPeriod16Type.Report16A.getValue());
				reportPeriod.setAdministrativeUnit(fmsAdministrativeUnit);
				if(fmsAdministrativeUnit.getParent()!=null) {
					reportPeriod.setDistrict(fmsAdministrativeUnit.getParent());
					if(fmsAdministrativeUnit.getParent().getParent()!=null) {
						reportPeriod.setProvince(fmsAdministrativeUnit.getParent().getParent());
					}
				}
				reportPeriod.setDate(day);
				reportPeriod.setMonth(month);
				reportPeriod.setYear(year);
				reportPeriod.setFarm(farm);
				reportPeriod.setReportItems(new HashSet<ReportForm16>());
				reportPeriod.getReportItems().add(item);
				item.setReportPeriod(reportPeriod);
			}
			// Cập nhật tới animalReportData
			
			if(farm!=null) {
				farm = farmRepository.save(farm);
			}
			if(farm==null && farmDto!=null){
				farm = farmService.updateFarmToEntity(null,farmDto);
			}
			if(farm==null) {
				rawData.setErrContent("Không tìm thấy trại và tạo mới trại không thành công!; "+rawData.getErrContent());
				return rawData;
			}
			if(reportPeriod!=null) {
				reportPeriod.setFarm(farm);
			}
			if(item!=null) {
				item.setFarm(farm);
			}
//			listAnimalReportData = this.updateDataFromReportForm16ToAnimalReportDataByEntityConvertOnly(item);
			reportPeriod = reportPeriodRepository.save(reportPeriod);
//			if(listAnimalReportData!=null) {
//				animalReportDataRepository.save(listAnimalReportData);
//			}
			rawData.setFarmId(farm.getId());
			rawData.setYear(year);
			rawData.setFarm(farm);
			return rawData;
		}
		return null;
	}
	
	public ReportForm16 createReportForm16Entity(DataDvhdDto rawData,ReportPeriod reportPeriod,Animal animal,FmsAdministrativeUnit fmsAdministrativeUnit,Farm farm, Original original) {
		if(rawData!=null && reportPeriod!=null && animal!=null && fmsAdministrativeUnit!=null && farm!=null) {
			ReportForm16Dto reportForm16Dto = rawData.convertToReportForm16();
			ReportForm16 item = new ReportForm16();
			item.setType(WLConstant.ReportPeriod16Type.Report16A.getValue());
			item.setDateReport(rawData.getDateUpdate());
			item.setAnimal(animal);
			
			Integer total=0;
			Integer male=0;
			Integer female=0;
			Integer unGender=0;
			
			item.setMaleParent(reportForm16Dto.getMaleParent());
				if(reportForm16Dto.getMaleParent()!=null) {
					total+=reportForm16Dto.getMaleParent();
					male+=reportForm16Dto.getMaleParent();
				}
			item.setFemaleParent(reportForm16Dto.getFemaleParent());
				if(reportForm16Dto.getFemaleParent()!=null) {
					total+=reportForm16Dto.getFemaleParent();
				}
			item.setMaleGilts(reportForm16Dto.getMaleGilts());
				if(reportForm16Dto.getMaleGilts()!=null) {
					total+=reportForm16Dto.getMaleGilts();
					male+=reportForm16Dto.getMaleGilts();
				}
			item.setFemaleGilts(reportForm16Dto.getFemaleGilts());
				if(reportForm16Dto.getFemaleGilts()!=null) {
					total+=reportForm16Dto.getFemaleGilts();
					female+=reportForm16Dto.getFemaleGilts();
				}
			item.setChildUnder1YearOld(reportForm16Dto.getChildUnder1YearOld());
				if(reportForm16Dto.getChildUnder1YearOld()!=null) {
					total+=reportForm16Dto.getChildUnder1YearOld();
					unGender+=reportForm16Dto.getChildUnder1YearOld();
				}
			item.setMaleChildOver1YearOld(reportForm16Dto.getMaleChildOver1YearOld());
				if(reportForm16Dto.getMaleChildOver1YearOld()!=null) {
					total+=reportForm16Dto.getMaleChildOver1YearOld();
					male+=reportForm16Dto.getMaleChildOver1YearOld();
				}
			item.setFemaleChildOver1YearOld(reportForm16Dto.getFemaleChildOver1YearOld());
				if(reportForm16Dto.getFemaleChildOver1YearOld()!=null) {
					total+=reportForm16Dto.getFemaleChildOver1YearOld();
					female+=reportForm16Dto.getFemaleChildOver1YearOld();
				}
			item.setUnGenderChildOver1YearOld(reportForm16Dto.getUnGenderChildOver1YearOld());
				if(reportForm16Dto.getUnGenderChildOver1YearOld()!=null) {
					total+=reportForm16Dto.getUnGenderChildOver1YearOld();
					unGender+=reportForm16Dto.getUnGenderChildOver1YearOld();
				}
				
			item.setMaleImport(reportForm16Dto.getMaleImport());
			item.setFemaleImport(reportForm16Dto.getFemaleImport());
			item.setUnGenderImport(reportForm16Dto.getUnGenderImport());
			item.setMaleExport(reportForm16Dto.getMaleExport());
			item.setFemaleExport(reportForm16Dto.getFemaleExport());
			item.setUnGenderExport(reportForm16Dto.getUnGenderExport());
			
			item.setTotal(total);
			item.setMale(male);
			item.setFemale(female);
			item.setUnGender(unGender);
			
			item.setNote(reportForm16Dto.getNote());
			item.setConfirmForestProtection(reportForm16Dto.getConfirmForestProtection());
			item.setFarm(farm);
			item.setOriginal(original);
			item.setReportPeriod(reportPeriod);
			if(reportPeriod.getReportItems()==null) {
				reportPeriod.setReportItems(new HashSet<ReportForm16>());
			}
			reportPeriod.getReportItems().add(item);
			
			
			item.setAdministrativeUnit(fmsAdministrativeUnit);
			if(fmsAdministrativeUnit.getParent()!=null) {
				item.setDistrict(fmsAdministrativeUnit.getParent());
				if(fmsAdministrativeUnit.getParent().getParent()!=null) {
					item.setProvince(fmsAdministrativeUnit.getParent().getParent());
				}
			}
			return item;
		}
		return null;
	}
	
	@Override
	public int updateDataFromReportPeriodToAnimalReportDataForAll(SearchReportPeriodDto searchDto) {
		Page<ReportPeriodDto> list = this.searchByPage(searchDto);
		int ret = 0;
		if (list != null && list.getContent() != null && list.getContent().size() > 0) {
			for (ReportPeriodDto reportPeriodDto : list) {
				if (reportPeriodDto.getFarm() != null) {
					ret += 1;
					System.out.println("updateDataFromReportPeriodToAnimalReportDataForAll " + ret + "/"
							+ list.getTotalElements() + " :" + reportPeriodDto.getFarm().getCode());
					this.updateDataFromReportPeriodToAnimalReportData(reportPeriodDto);
				}
			}
		}
		return ret;
	}

	@Override
	public Page<ReportPeriodDto> getReport16aBySearchDto(SearchReportPeriodDto searchDto) {
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
		if (searchDto != null && searchDto.getPageIndex() != null && searchDto.getPageSize() != null) {
			int pageIndex = searchDto.getPageIndex();
			int pageSize = searchDto.getPageSize();

			if (pageIndex > 0)
				pageIndex = pageIndex - 1;
			else
				pageIndex = 0;
			Pageable pageable = new PageRequest(pageIndex, pageSize);

			String sql = "select new com.globits.wl.dto.ReportPeriodDto(rp) from ReportPeriod rp LEFT JOIN ReportForm16 rp16 ON rp.id = rp16.reportPeriod.id where (1=1)";
			String sqlCount = "select count(rp.id) from ReportPeriod rp LEFT JOIN ReportForm16 rp16 ON rp.id = rp16.reportPeriod.id where (1=1)";
			String whereClause = "";

			whereClause += " and (rp.type = " + WLConstant.ReportPeriod16Type.Report16A.getValue() + " )";

			if (searchDto.getFarmId() != null) {
				whereClause += " and (rp.farm.id =:farmId )";
			}

			if (searchDto.getDateReport() != null) {
				whereClause += " and (rp.dateReport =:dateReport )";
			}
			if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
				whereClause += " and (rp.farm.code LIKE :text OR rp.farm.name LIKE :text )";
			}
			if (searchDto.getYear() != null && searchDto.getMonth() != null && searchDto.getDate() != null) {
				whereClause += " and ( (rp.year < :year ) OR ( rp.year = :year AND rp.month < :month)  OR ( rp.year = :year AND rp.month <= :month AND rp.date <= :date ) )";
			}

			if (searchDto.getWardId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.parent.id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.parent.parent.id =:provinceId )";
			}
			if (!isAdmin) {
				whereClause += " and (rp.farm.administrativeUnit.id in (:listWardId)) ";
			}
			if (searchDto.getAnimalId() != null) {
				whereClause += " and ( rp16.animal IS NOT NULL AND rp16.animal.id = :animalId ) ";
			}
			sql += whereClause;
			sql += " order by rp.farm.code, rp.farm.name, rp.year, rp.month, rp.date asc ";
			sqlCount += whereClause;

			Query q = manager.createQuery(sql, ReportPeriodDto.class);
			Query qCount = manager.createQuery(sqlCount);

			if (searchDto.getFarmId() != null) {
				q.setParameter("farmId", searchDto.getFarmId());
				qCount.setParameter("farmId", searchDto.getFarmId());
			}

			if (searchDto.getDateReport() != null) {
				q.setParameter("dateReport", searchDto.getDateReport());
				qCount.setParameter("dateReport", searchDto.getDateReport());
			}
			if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
				q.setParameter("text", "%" + searchDto.getText() + "%");
				qCount.setParameter("text", "%" + searchDto.getText() + "%");
			}

			if (searchDto.getYear() != null && searchDto.getMonth() != null && searchDto.getDate() != null) {
				q.setParameter("year", searchDto.getYear());
				qCount.setParameter("year", searchDto.getYear());
				
				q.setParameter("month", searchDto.getMonth());
				qCount.setParameter("month", searchDto.getMonth());
				
				q.setParameter("date", searchDto.getDate());
				qCount.setParameter("date", searchDto.getDate());
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
			if (!isAdmin) {
				q.setParameter("listWardId", listWardId);
				qCount.setParameter("listWardId", listWardId);
			}
			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}
			q.setFirstResult((pageIndex) * pageSize);
			q.setMaxResults(pageSize);

			Long numberResult = (Long) qCount.getSingleResult();
			Page<ReportPeriodDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
			return page;
		}
		return null;
	}
	
	@Override
	public ReportPeriodDto getLastRecordReportPeriodByFarmAndAnimal(SearchReportPeriodDto dto) {
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
		String sql = "select new com.globits.wl.dto.ReportPeriodDto(rp) from ReportPeriod rp join ReportForm16 r on rp.id = r.reportPeriod.id ";
		String whereClause = " where (1=1) ";
		String orderBy = " order by rp.year DESC, rp.month DESC, rp.date DESC ";
		
		if(dto.getFarmId() == null || dto.getAnimalId() == null) {
			return null;
		}

		if (dto.getFarmId() != null) {
			whereClause += " and (rp.farm.id = :farmId ) ";
		}
		
		if(dto.getAnimalId() != null) {
			whereClause += " and (r.animal.id = :animalId) ";
		}

		if (!isAdmin) {
			whereClause += " and (rp.farm.administrativeUnit.id in (:listWardId) ) ";
		}
		sql += whereClause + orderBy;

		Query q = manager.createQuery(sql, ReportPeriodDto.class);

		if (dto.getFarmId() != null) {
			q.setParameter("farmId", dto.getFarmId());
		}
		
		if (dto.getAnimalId() != null) {
			q.setParameter("animalId", dto.getAnimalId());
		}
		
		if (!isAdmin) {
			q.setParameter("listWardId", listWardId);
		}
		
		List<ReportPeriodDto> result = q.getResultList();
		
		if(result != null && result.size() > 0) {
			return result.get(0);
		}
		
		return null;
	}
	
}
