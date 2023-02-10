package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import com.globits.wl.domain.*;
import com.globits.wl.repository.*;
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

import com.globits.core.domain.FileDescription;
import com.globits.core.repository.FileDescriptionRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.UserService;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.ForestProductsListDetailDto;
import com.globits.wl.dto.ForestProductsListDto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.SystemMessageDto;
import com.globits.wl.dto.UserAttachmentsDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.service.ForestProductsListService;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.service.SystemMessageService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.service.UserAttachmentsService;
import com.globits.wl.utils.EmailUtil;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ForestProductsListServiceImpl extends GenericServiceImpl<ForestProductsList, Long>
		implements ForestProductsListService {
	@Autowired
	ForestProductsListRepository forestProductsListRepository;
	@Autowired
	ForestProductsListDetailRepository forestProductsListDetailRepository;

	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;

	@Autowired
	private AnimalRepository animalRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FileDescriptionRepository fileDescriptionRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private ImportExportAnimalRepository importExportAnimalRepository;
	
	@Autowired
	private SystemMessageService systemMessageService;
	
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	
	@Autowired
	private ReportForm16Repository reportForm16Repository;
	
	@Autowired
	private ReportPeriodRepository reportPeriodRepository;
	
	@Autowired
	private ReportPeriodService reportPeriodService;
	
	@Autowired
	private UserAttachmentsService userAttachmentsService;
	
	@Autowired
	UserAttachmentsRepository userAttachmentsRepository;

	@Autowired
	AdministrativeOrganizationRepository administrativeOrganizationRepository;

	@Override
	public ForestProductsListDto saveOrUpdate(ForestProductsListDto dto) {
		if (dto != null) {
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			Boolean isNew = true;
			ForestProductsList entity = null;
			if (dto.getId() != null) {
				isNew = false;
				entity = forestProductsListRepository.getOne(dto.getId());
			}
			if (entity == null) {
				entity = new ForestProductsList();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			} else {
				if (!entity.getCreatedBy().equals(currentUserName) && dto.getCanceled()!=4) {
					return null;
				}
				entity.setModifiedBy(currentUserName);
				entity.setModifyDate(currentDate);
			}
			Farm farm = null;
			if(isNew) {
				if (dto.getFarm() != null && dto.getFarm().getId() != null) {
					farm = farmRepository.getById(dto.getFarm().getId());
					entity.setFarm(farm);
				}
			} else {
				farm = entity.getFarm();
			}
			entity.setCode(dto.getCode());
			entity.setOwnerConfirm(dto.getOwnerConfirm());
			entity.setDateIssue(dto.getDateIssue());
			entity.setOriginal(dto.getOriginal());
			entity.setInvoiceCode(dto.getInvoiceCode());
			entity.setInvoiceDate(dto.getInvoiceDate());
			entity.setVehicle(dto.getVehicle());
			entity.setVehicleRegistrationPlate(dto.getVehicleRegistrationPlate());
			entity.setTransportDuration(dto.getTransportDuration());
			entity.setTransportStart(dto.getTransportStart());
			entity.setTransportEnd(dto.getTransportEnd());
			entity.setTransportFrom(dto.getTransportFrom());
			entity.setTransportTo(dto.getTransportTo());
			entity.setStatusConfirmName(dto.getStatusConfirmName());
			entity.setStatusConfirmDate(dto.getStatusConfirmDate());
			entity.setVillageFrom(dto.getVillageFrom());
			entity.setVillageTo(dto.getVillageTo());
			entity.setBuyerPhonenumber(dto.getBuyerPhoneNumber());
			if(dto.getOrganizationName()!= null) {
				entity.setOrganizationName(dto.getOrganizationName());
			}
			if(dto.getProvinceName() != null) {
				entity.setProvinceName(dto.getProvinceName());
			}
			if(dto.getOrganizationCreatedFplName() != null) {
				entity.setOrganizationCreatedFplName(dto.getOrganizationCreatedFplName());
			}
			if(dto.getAddressConfirm() != null) {
				entity.setAddressConfirm(dto.getAddressConfirm());
			}
			if(dto.getAddressCreatedFpl() != null) {
				entity.setAddressCreatedFpl(dto.getAddressCreatedFpl());
			}
			if(dto.getSignatureName() != null) {
				entity.setSignatureName(dto.getSignatureName());
			}
			if (dto.getAdministrativeOrganization() != null && dto.getAdministrativeOrganization().getId() != null){
				AdministrativeOrganization administrativeOrganization = administrativeOrganizationRepository.findOne(dto.getAdministrativeOrganization().getId());
				if(administrativeOrganization != null){
					entity.setAdministrativeOrganization(administrativeOrganization);
				}
			}
			
			entity.setNoteConfirm(dto.getNoteConfirm());
			// Neu xac nhan thay doi du lieu farm BKLS thay doi => thay doi du lieu farm
			if(dto.getIsCheckFarm() != null && dto.getIsCheckFarm() == true) {
				if(dto.getFarm().getVillage() != null) {
					entity.getFarm().setVillage(dto.getFarm().getVillage()); // Thôn
				}
				if(dto.getFarm().getPhoneNumber() != null) {
					entity.getFarm().setPhoneNumber(dto.getFarm().getPhoneNumber()); // Số điện thoại
				}
				if(dto.getFarm().getAdressDetail() != null) {
					entity.getFarm().setAdressDetail(dto.getFarm().getAdressDetail()); // Địa chỉ chi tiết
				}
				if(dto.getFarm().getNewRegistrationCode() != null) {
					entity.getFarm().setNewRegistrationCode(dto.getFarm().getNewRegistrationCode()); // Mã số cơ sở
				}
				if(dto.getFarm().getBusinessRegistrationNumber() != null) {
					entity.getFarm().setBusinessRegistrationNumber(dto.getFarm().getBusinessRegistrationNumber()); // Số đăng ký kinh doanh
				}
				
				// Lưu ngược tỉnh, huyện xã vào trong bảng Farm
//				if(dto.getFarm().getAdministrativeUnit() != null) {
//					FmsAdministrativeUnitDto auDto = dto.getFarm().getAdministrativeUnit();
//					FmsAdministrativeUnit au = null;
//					if(auDto.getId() != null) {
//						au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
//						if(au != null) {
//							entity.getFarm().setAdministrativeUnit(au);
//						} else if(auDto.getCode() != null) {
//							List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
//							if(list!=null && list.size() > 0) {
//								au = list.get(0);
//								entity.getFarm().setAdministrativeUnit(au);
//							}
//						}
//					}
//				}
				
				// Lưu ngược vào bảng ForestProductList
				// Tỉnh
//				if(dto.getProvinceFrom() != null) {
//					FmsAdministrativeUnitDto provinceFromDto = dto.getProvinceFrom();
//					FmsAdministrativeUnit provinceFrom = null;
//					if(provinceFromDto.getId() != null) {
//						provinceFrom = this.fmsAdministrativeUnitRepository.findOne(provinceFromDto.getId());
//						if(provinceFrom != null) {
//							entity.setProvinceFrom(provinceFrom);
//						}
//					}
//					else if(provinceFromDto.getCode() != null) {
//						List<FmsAdministrativeUnit> list=fmsAdministrativeUnitRepository.findListByCode(provinceFromDto.getCode());
//						if(list!=null && list.size()>0){
//							provinceFrom=list.get(0);
//							entity.setProvinceFrom(provinceFrom);
//						}	
//					}
//				}
				
				// Huyện
//				if (dto.getAdministrativeUnitFrom() != null) {
//					FmsAdministrativeUnitDto auDto = dto.getAdministrativeUnitFrom();
//					FmsAdministrativeUnit au = null;
//					if (auDto.getId() != null) {
//						au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
//						if (au != null) {
//							entity.setAdministrativeUnitFrom(au);
//						}
//					} else if (auDto.getCode() != null) {
//						List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
//						if (list != null && list.size() > 0) {
//							au = list.get(0);
//							entity.setAdministrativeUnitFrom(au);
//						}
//					}
//
//				} else {
//					FmsAdministrativeUnit au = null;
//					if (dto.getProvinceIdFrom() != null || dto.getProvinceCodeFrom() != null) {
//						if (dto.getProvinceIdFrom() != null) {
//							au = this.fmsAdministrativeUnitRepository.findOne(dto.getProvinceIdFrom());
//							if (au != null) {
//								entity.setAdministrativeUnitFrom(au);
//							}
//						} else {
//							if (dto.getProvinceCodeFrom() != null) {
//								List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
//										.findListByCode(dto.getProvinceCodeFrom());
//								if (list != null && list.size() > 0) {
//									au = list.get(0);
//									entity.setAdministrativeUnitFrom(au);
//								}
//							}
//						}
//					} else {
//						if (dto.getAdministrativeUnitFrom() == null) {
//							entity.setAdministrativeUnitFrom(null);
//						}
//					}
//				}
//				
				// Xã
//				if(dto.getCommuneFrom() != null) {
//					FmsAdministrativeUnitDto communeFromDto = dto.getCommuneFrom();
//					FmsAdministrativeUnit communeFrom = null;
//					if(communeFromDto.getId() != null) {
//						communeFrom = this.fmsAdministrativeUnitRepository.findOne(communeFromDto.getId());
//						if(communeFrom != null) {
//							entity.setCommuneFrom(communeFrom);
//						}
//					}
//					else if(communeFromDto.getCode() != null) {
//						List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(communeFromDto.getCode());
//						if(list != null) {
//							communeFrom = list.get(0);
//							entity.setCommuneFrom(communeFrom);
//						}
//					}
//				}
				
			}
			FileDescription file= null;
			if (dto.getFile() != null && dto.getFile().getId()!=null) {
				file = fileDescriptionRepository.findOne(dto.getFile().getId());
				if(file!=null) {
					entity.setFile(file);
				}
			}
			// Them tinh From
			if(dto.getProvinceFrom() != null) {
				FmsAdministrativeUnitDto provinceFromDto = dto.getProvinceFrom();
				FmsAdministrativeUnit provinceFrom = null;
				if(provinceFromDto.getId() != null) {
					provinceFrom = this.fmsAdministrativeUnitRepository.findOne(provinceFromDto.getId());
					if(provinceFrom != null) {
						entity.setProvinceFrom(provinceFrom);
					}
				}
				else if(provinceFromDto.getCode() != null) {
					List<FmsAdministrativeUnit> list=fmsAdministrativeUnitRepository.findListByCode(provinceFromDto.getCode());
					if(list!=null && list.size()>0){
						provinceFrom=list.get(0);
						entity.setProvinceFrom(provinceFrom);
					}	
				}
			}
			
			// Them tinh To
			if(dto.getProvinceTo() != null) {
				FmsAdministrativeUnitDto provinceToDto = dto.getProvinceTo();
				FmsAdministrativeUnit provinceTo = null;
				if(provinceToDto.getId() != null) {
					provinceTo = this.fmsAdministrativeUnitRepository.findOne(provinceToDto.getId());
					if(provinceTo != null) {
						entity.setProvinceTo(provinceTo);
					}
				}
			
				else if(provinceToDto.getCode() != null) {
					List<FmsAdministrativeUnit> list=fmsAdministrativeUnitRepository.findListByCode(provinceToDto.getCode());
					if(list!=null && list.size()>0){
						provinceTo=list.get(0);
						entity.setProvinceTo(provinceTo);
					}	
				}
			}
			
			
			//them huyen
			if (dto.getAdministrativeUnitFrom() != null) {
				FmsAdministrativeUnitDto auDto = dto.getAdministrativeUnitFrom();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if (au != null) {
						entity.setAdministrativeUnitFrom(au);
					}
				} else if (auDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if (list != null && list.size() > 0) {
						au = list.get(0);
						entity.setAdministrativeUnitFrom(au);
					}
				}

			} else {
				FmsAdministrativeUnit au = null;
				if (dto.getProvinceIdFrom() != null || dto.getProvinceCodeFrom() != null) {
					if (dto.getProvinceIdFrom() != null) {
						au = this.fmsAdministrativeUnitRepository.findOne(dto.getProvinceIdFrom());
						if (au != null) {
							entity.setAdministrativeUnitFrom(au);
						}
					} else {
						if (dto.getProvinceCodeFrom() != null) {
							List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
									.findListByCode(dto.getProvinceCodeFrom());
							if (list != null && list.size() > 0) {
								au = list.get(0);
								entity.setAdministrativeUnitFrom(au);
							}
						}
					}
				} else {
					if (dto.getAdministrativeUnitFrom() == null) {
						entity.setAdministrativeUnitFrom(null);
					}
				}

			}
			
			// Huyện to
			if (dto.getAdministrativeUnitTo() != null) {
				FmsAdministrativeUnitDto auDto = dto.getAdministrativeUnitTo();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if(au!=null) {
						entity.setAdministrativeUnitTo(au);
					}
				}else if(auDto.getCode()!=null){
					List<FmsAdministrativeUnit> list=fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if(list!=null && list.size()>0){
						au=list.get(0);
						entity.setAdministrativeUnitTo(au);
					}				
				}
				
			}else {
				FmsAdministrativeUnit au = null;
				if(dto.getProvinceIdTo()!=null || dto.getProvinceCodeTo()!=null) {
					if(dto.getProvinceIdTo()!=null) {
						au= this.fmsAdministrativeUnitRepository.findOne(dto.getProvinceIdTo());
						if(au!=null) {
							entity.setAdministrativeUnitTo(au);
						}
					}else {
						if(dto.getProvinceCodeTo()!=null) {
							List<FmsAdministrativeUnit> list=fmsAdministrativeUnitRepository.findListByCode(dto.getProvinceCodeTo());
							if(list!=null && list.size()>0){
								au=list.get(0);
								entity.setAdministrativeUnitTo(au);
							}	
						}
					}
				}else {
					if(dto.getAdministrativeUnitFrom()==null) {
						entity.setAdministrativeUnitTo(null);
					}
				}
				
			}

			// Them Xa From
			if(dto.getCommuneFrom() != null) {
				FmsAdministrativeUnitDto communeFromDto = dto.getCommuneFrom();
				FmsAdministrativeUnit communeFrom = null;
				if(communeFromDto.getId() != null) {
					communeFrom = this.fmsAdministrativeUnitRepository.findOne(communeFromDto.getId());
					if(communeFrom != null) {
						entity.setCommuneFrom(communeFrom);
					}
				}
				else if(communeFromDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(communeFromDto.getCode());
					if(list != null) {
						communeFrom = list.get(0);
						entity.setCommuneFrom(communeFrom);
					}
				}
			}
			
			// The Xa To
			if(dto.getCommuneTo() != null) {
				FmsAdministrativeUnitDto communeToDto = dto.getCommuneTo();
				FmsAdministrativeUnit communeTo = null;
				if(communeToDto.getId() != null) {
					communeTo = this.fmsAdministrativeUnitRepository.findOne(communeToDto.getId());
					if(communeTo != null) {
						entity.setCommuneTo(communeTo);
					}
				}
				else if(communeToDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(communeToDto.getCode());
					if(list != null) {
						communeTo = list.get(0);
						entity.setCommuneFrom(communeTo);
					}
				}
			}
			User confirmByUser = null;
			confirmByUser = userService.findEntityByUsername(currentUserName);
			entity.setConfirmByUser(confirmByUser);
			entity.setUserConfirmName(currentUserName);
			entity.setNoteConfirm(dto.getNoteConfirm());
			entity.setBuyerName(dto.getBuyerName());
			entity.setBuyerDetailAddress(dto.getBuyerDetailAddress());
			entity.setCanceled(dto.getCanceled());
			// khi tạo bảng kê lâm sản sẽ tạo 1 phiếu mẫu 16 với reportItems theo từng detail
			boolean checkRPExist=false;// check xem đã tồn tại report của list details chưa
			ReportPeriod rp = null;
			ReportPeriod rpback=null;
			// check xem cos rp cungf ngay chua
			if(dto.getCanceled()!=null && (dto.getCanceled()==0 || dto.getCanceled() ==4)) {
				SearchReportPeriodDto searchDto= new SearchReportPeriodDto();
				if(farm!=null && farm.getId()!=null) {
					searchDto.setFarmId(farm.getId());
				}
				searchDto.setYear(Calendar.getInstance().get(Calendar.YEAR));
				searchDto.setDate(Calendar.getInstance().get(Calendar.DATE));
				searchDto.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
				List<ReportPeriod> listRp= reportPeriodService.getAllEntity(searchDto);
				if(listRp!=null && listRp.size()>0) {
					rp= listRp.get(0);
				}
			}
			if(dto.getCanceled()!=null && dto.getCanceled()==1) {
				SearchReportPeriodDto searchDto= new SearchReportPeriodDto();
				if(farm!=null && farm.getId()!=null) {
					searchDto.setFarmId(farm.getId());
				}
				searchDto.setYear(Calendar.getInstance().get(Calendar.YEAR));
				searchDto.setDate(Calendar.getInstance().get(Calendar.DATE));
				searchDto.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
				List<ReportPeriod> listRp= reportPeriodService.getAllEntity(searchDto);
				if(listRp!=null && listRp.size()>0) {
					rpback= listRp.get(0);
				}
			}
			

//			if(entity.getDetails() )
			
			Set<ForestProductsListDetail> details = new HashSet<ForestProductsListDetail>();
			if (dto.getDetails() != null && dto.getDetails().size() > 0) {
				if(rp==null && dto.getCanceled()!=null && (dto.getCanceled()==0 || dto.getCanceled() ==4)) {
					rp = new ReportPeriod();
					rp.setFarm(farm);
					if(farm.getAdministrativeUnit() != null) {
						rp.setAdministrativeUnit(farm.getAdministrativeUnit());
					}
					if(farm.getAdministrativeUnit().getParent() != null) {
						rp.setDistrict(farm.getAdministrativeUnit().getParent());
					}
					if(farm.getAdministrativeUnit().getParent() != null && farm.getAdministrativeUnit().getParent().getParent() != null) {
						rp.setProvince(farm.getAdministrativeUnit().getParent().getParent());
					}
					rp.setYear(Calendar.getInstance().get(Calendar.YEAR));
					rp.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
					rp.setDate(Calendar.getInstance().get(Calendar.DATE));
					rp.setType(1);
					rp = reportPeriodRepository.save(rp);
				
				}
				for (ForestProductsListDetailDto foDetailDto : dto.getDetails()) {
					if (foDetailDto != null) {
						ForestProductsListDetail forestProductsListDetail = null;
						if (foDetailDto.getId() != null) {
							forestProductsListDetail = forestProductsListDetailRepository.getOne(foDetailDto.getId());
						}
						if (forestProductsListDetail == null) {
							forestProductsListDetail = new ForestProductsListDetail();
							forestProductsListDetail.setCreateDate(currentDate);
							forestProductsListDetail.setCreatedBy(currentUserName);
							;
						} else {
							forestProductsListDetail.setModifiedBy(currentUserName);
							forestProductsListDetail.setModifyDate(currentDate);
						}
						forestProductsListDetail.setForestProductsList(entity);

						Animal animal = animalRepository.findOne(foDetailDto.getAnimal().getId());
						if (foDetailDto.getAnimal() != null && foDetailDto.getAnimal().getId() != null) {
							animal = animalRepository.findOne(foDetailDto.getAnimal().getId());
						}
						forestProductsListDetail.setAnimal(animal);
						forestProductsListDetail.setQuantity(foDetailDto.getQuantity());
						forestProductsListDetail.setAmount(foDetailDto.getAmount());
						forestProductsListDetail.setUnit(foDetailDto.getUnit());
						forestProductsListDetail.setNote(foDetailDto.getNote());
						forestProductsListDetail.setGroupAnimalType(foDetailDto.getGroupAnimalType());
						forestProductsListDetail.setCode(foDetailDto.getCode());
						
						forestProductsListDetail.setTotal(foDetailDto.getTotal());
						forestProductsListDetail.setMale(foDetailDto.getMale());
						forestProductsListDetail.setFemale(foDetailDto.getFemale());
						forestProductsListDetail.setUnGender(foDetailDto.getUnGender());
						
						forestProductsListDetail.setMaleParent(foDetailDto.getMaleParent());
						forestProductsListDetail.setFemaleParent(foDetailDto.getFemaleParent());
						forestProductsListDetail.setUnGenderParent(foDetailDto.getUnGenderParent());
						
						forestProductsListDetail.setMaleGilts(foDetailDto.getMaleGilts());
						forestProductsListDetail.setFemaleGilts(foDetailDto.getFemaleGilts());
						forestProductsListDetail.setUnGenderGilts(foDetailDto.getUnGenderGilts());
						
						forestProductsListDetail.setMaleChildUnder1YearOld(foDetailDto.getMaleChildUnder1YearOld());
						forestProductsListDetail.setFemaleChildUnder1YearOld(foDetailDto.getFemaleChildUnder1YearOld());
						forestProductsListDetail.setChildUnder1YearOld(foDetailDto.getChildUnder1YearOld());
						
						forestProductsListDetail.setMaleChildOver1YearOld(foDetailDto.getMaleChildOver1YearOld());
						forestProductsListDetail.setFemaleChildOver1YearOld(foDetailDto.getFemaleChildOver1YearOld());
						forestProductsListDetail.setUnGenderChildOver1YearOld(foDetailDto.getUnGenderChildOver1YearOld());
					
						ReportForm16 rp16Old = null;
						if(foDetailDto.getReportForm16Old() != null && foDetailDto.getReportForm16Old().getId() != null) {
							rp16Old = reportForm16Repository.getOne(foDetailDto.getReportForm16Old().getId());
							if(rp16Old == null) {
								return null;
							}
							forestProductsListDetail.setReportForm16Old(rp16Old);
						}
						if(dto.getCanceled()!=null && (dto.getCanceled()==0 || dto.getCanceled() ==4)) {
						SearchReportPeriodDto searchDto2= new SearchReportPeriodDto();
						searchDto2.setAnimalId(animal.getId());
						searchDto2.setFarmId(farm.getId());
						
						ReportPeriodDto reportperiod1= reportPeriodService.getLastRecordReportPeriodByFarmAndAnimal(searchDto2);
						ReportForm16 rp16Lastest1 = null;
						for(ReportForm16Dto rpdto: reportperiod1.getReportItems()) {
							if(rpdto.getAnimal()!=null && rpdto.getAnimal().getId()!=null && rpdto.getAnimal().getId()==animal.getId()) {
								rp16Lastest1 = reportForm16Repository.findOne(rpdto.getId());
								break;
							}
						}
						
						ReportForm16 rp16 = null;
						if(Calendar.getInstance().get(Calendar.YEAR) == reportperiod1.getYear() && Calendar.getInstance().get(Calendar.MONTH) + 1 == reportperiod1.getMonth()
								&& Calendar.getInstance().get(Calendar.DATE) == reportperiod1.getDate()) {
							rp16=rp16Lastest1;
						}else {
							if(foDetailDto.getReportForm16() != null && foDetailDto.getReportForm16().getId() != null) {
								rp16 = reportForm16Repository.getOne(foDetailDto.getReportForm16().getId());
							} else {
								rp16 = new ReportForm16();
								rp16.setDateReport(new Date());
								if(farm.getAdministrativeUnit() != null) {
									rp16.setAdministrativeUnit(farm.getAdministrativeUnit());
								}
								if(farm.getAdministrativeUnit().getParent() != null) {
									rp16.setDistrict(farm.getAdministrativeUnit().getParent());
								}
								if(farm.getAdministrativeUnit().getParent() != null && farm.getAdministrativeUnit().getParent().getParent() != null) {
									rp16.setProvince(farm.getAdministrativeUnit().getParent().getParent());
								}
								rp16.setFarm(farm);
								rp16.setReportPeriod(rp);
								rp16.setAnimal(animal);
							}
						}
						if(foDetailDto.getReportForm16Old().getTotal() != null) {
							if(foDetailDto.getTotal() != null) {
								rp16.setTotal(foDetailDto.getReportForm16Old().getTotal() - foDetailDto.getTotal());
							} else {
								rp16.setTotal(foDetailDto.getReportForm16Old().getTotal());
							}
							
						}
						if(foDetailDto.getReportForm16Old().getMale() != null) {
							if(foDetailDto.getMale() != null) {
								rp16.setMale(foDetailDto.getReportForm16Old().getMale() - foDetailDto.getMale());
							} else {
								rp16.setMale(foDetailDto.getReportForm16Old().getMale());
							}
						}
						if(foDetailDto.getReportForm16Old().getFemale() != null) {
							if(foDetailDto.getFemale() != null) {
								rp16.setFemale(foDetailDto.getReportForm16Old().getFemale() - foDetailDto.getFemale());
							} else {
								rp16.setFemale(foDetailDto.getReportForm16Old().getFemale());
							}
						}
						if(foDetailDto.getReportForm16Old().getUnGender() != null) {
							if(foDetailDto.getUnGender() != null) {
								rp16.setUnGender(foDetailDto.getReportForm16Old().getUnGender() - foDetailDto.getUnGender());
							} else {
								rp16.setUnGender(foDetailDto.getReportForm16Old().getUnGender());
							}
						}
						
						if(foDetailDto.getReportForm16Old().getMaleParent() != null) {
							if(foDetailDto.getMaleParent() != null) {
								rp16.setMaleParent(foDetailDto.getReportForm16Old().getMaleParent() - foDetailDto.getMaleParent());
								if(rp16.getExportMaleParent()!=null) {
									rp16.setExportMaleParent(rp16.getExportMaleParent()+foDetailDto.getMaleParent());
								}else {
									rp16.setExportMaleParent(foDetailDto.getMaleParent());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getMaleParent());
							} else {
								rp16.setMaleParent(foDetailDto.getReportForm16Old().getMaleParent());
							}
						}
						if(foDetailDto.getReportForm16Old().getFemaleParent() != null) {
							if(foDetailDto.getFemaleParent() != null) {
								rp16.setFemaleParent(foDetailDto.getReportForm16Old().getFemaleParent() - foDetailDto.getFemaleParent());
								if(rp16.getExportFemaleParent()!=null) {
									rp16.setExportFemaleParent(rp16.getExportFemaleParent()+foDetailDto.getFemaleParent());
								}else {
									rp16.setExportFemaleParent(foDetailDto.getFemaleParent());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getFemaleParent());
							} else {
								rp16.setFemaleParent(foDetailDto.getReportForm16Old().getFemaleParent());
							}
						}
						if(foDetailDto.getReportForm16Old().getUnGenderParent() != null) {
							if(foDetailDto.getUnGenderParent() != null) {
								rp16.setUnGenderParent(foDetailDto.getReportForm16Old().getUnGenderParent() - foDetailDto.getUnGenderParent());
								if(rp16.getExportUnGenderParent()!=null) {
									rp16.setExportUnGenderParent(rp16.getExportUnGenderParent()+foDetailDto.getUnGenderParent());
								}else {
									rp16.setExportUnGenderParent(foDetailDto.getUnGenderParent());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getUnGenderParent());
							} else {
								rp16.setUnGenderParent(foDetailDto.getReportForm16Old().getUnGenderParent());
							}
						}
						
						if(foDetailDto.getReportForm16Old().getMaleGilts() != null ) {
							if(foDetailDto.getMaleGilts() != null) {
								rp16.setMaleGilts(foDetailDto.getReportForm16Old().getMaleGilts() - foDetailDto.getMaleGilts());
								if(rp16.getExportMaleGilts()!=null) {
									rp16.setExportMaleGilts(rp16.getExportMaleGilts()+foDetailDto.getMaleGilts());
								}else {
									rp16.setExportMaleGilts(foDetailDto.getMaleGilts());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getMaleGilts());
							} else {
								rp16.setMaleGilts(foDetailDto.getReportForm16Old().getMaleGilts());
							}
						}
						if(foDetailDto.getReportForm16Old().getFemaleGilts() != null) {
							if( foDetailDto.getFemaleGilts() != null) {
								rp16.setFemaleGilts(foDetailDto.getReportForm16Old().getFemaleGilts() - foDetailDto.getFemaleGilts());
								if(rp16.getExportFemaleGilts()!=null) {
									rp16.setExportFemaleGilts(rp16.getExportFemaleGilts()+foDetailDto.getFemaleGilts());
								}else {
									rp16.setExportFemaleGilts(foDetailDto.getFemaleGilts());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getFemaleGilts());
							} else {
								rp16.setFemaleGilts(foDetailDto.getReportForm16Old().getFemaleGilts());
							}
						}
						if(foDetailDto.getReportForm16Old().getUnGenderGilts() != null) {
							if( foDetailDto.getUnGenderGilts() != null) {
								rp16.setUnGenderGilts(foDetailDto.getReportForm16Old().getUnGenderGilts() - foDetailDto.getUnGenderGilts());
								if(rp16.getExportUnGenderGilts()!=null) {
									rp16.setExportUnGenderGilts(rp16.getExportUnGenderGilts()+foDetailDto.getUnGenderGilts());
								}else {
									rp16.setExportUnGenderGilts(foDetailDto.getUnGenderGilts());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getUnGenderGilts());
							} else {
								rp16.setUnGenderGilts(foDetailDto.getReportForm16Old().getUnGenderGilts());
							}
						}
						
						if(foDetailDto.getReportForm16Old().getMaleChildUnder1YearOld() != null) {
							if(foDetailDto.getMaleChildUnder1YearOld() != null) {
								rp16.setMaleChildUnder1YearOld(foDetailDto.getReportForm16Old().getMaleChildUnder1YearOld() - foDetailDto.getMaleChildUnder1YearOld());
								if(rp16.getExportMaleChildUnder1YearOld()!=null) {
									rp16.setExportMaleChildUnder1YearOld(rp16.getExportMaleChildUnder1YearOld()+foDetailDto.getMaleChildUnder1YearOld());
								}else {
									rp16.setExportMaleChildUnder1YearOld(foDetailDto.getMaleChildUnder1YearOld());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getMaleChildUnder1YearOld());
							} else {
								rp16.setMaleChildUnder1YearOld(foDetailDto.getReportForm16Old().getMaleChildUnder1YearOld());
							}
						}
						if(foDetailDto.getReportForm16Old().getFemaleChildUnder1YearOld() != null) {
							if(foDetailDto.getFemaleChildUnder1YearOld() != null) {
								rp16.setFemaleChildUnder1YearOld(foDetailDto.getReportForm16Old().getFemaleChildUnder1YearOld() - foDetailDto.getFemaleChildUnder1YearOld());
								if(rp16.getExportFemaleChildUnder1YearOld()!=null) {
									rp16.setExportFemaleChildUnder1YearOld(rp16.getExportFemaleChildUnder1YearOld()+foDetailDto.getFemaleChildUnder1YearOld());
								}else {
									rp16.setExportFemaleChildUnder1YearOld(foDetailDto.getFemaleChildUnder1YearOld());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getFemaleChildUnder1YearOld());
							} else {
								rp16.setFemaleChildUnder1YearOld(foDetailDto.getReportForm16Old().getFemaleChildUnder1YearOld());
							}
						}
						if(foDetailDto.getReportForm16Old().getChildUnder1YearOld() != null) {
							if(foDetailDto.getChildUnder1YearOld() != null) {
								rp16.setChildUnder1YearOld(foDetailDto.getReportForm16Old().getChildUnder1YearOld() - foDetailDto.getChildUnder1YearOld());
								if(rp16.getExportChildUnder1YearOld()!=null) {
									rp16.setExportChildUnder1YearOld(rp16.getExportChildUnder1YearOld()+foDetailDto.getChildUnder1YearOld());
								}else {
									rp16.setExportChildUnder1YearOld(foDetailDto.getChildUnder1YearOld());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getChildUnder1YearOld());
							} else {
								rp16.setChildUnder1YearOld(foDetailDto.getReportForm16Old().getChildUnder1YearOld());
							}
						}
						
						if(foDetailDto.getReportForm16Old().getMaleChildOver1YearOld() != null) {
							if(foDetailDto.getMaleChildOver1YearOld() != null) {
								rp16.setMaleChildOver1YearOld(foDetailDto.getReportForm16Old().getMaleChildOver1YearOld() - foDetailDto.getMaleChildOver1YearOld());
								if(rp16.getExportMaleChildOver1YearOld()!=null) {
									rp16.setExportMaleChildOver1YearOld(rp16.getExportMaleChildOver1YearOld()+foDetailDto.getMaleChildOver1YearOld());
								}else {
									rp16.setExportMaleChildOver1YearOld(foDetailDto.getMaleChildOver1YearOld());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getMaleChildOver1YearOld());
							} else {
								rp16.setMaleChildOver1YearOld(foDetailDto.getReportForm16Old().getMaleChildOver1YearOld());
							}
						}
							
						if(foDetailDto.getReportForm16Old().getFemaleChildOver1YearOld()!= null) {
							if(foDetailDto.getFemaleChildOver1YearOld() != null) {
								rp16.setFemaleChildOver1YearOld(foDetailDto.getReportForm16Old().getFemaleChildOver1YearOld() - foDetailDto.getFemaleChildOver1YearOld());
								if(rp16.getExportFemaleChildOver1YearOld()!=null) {
									rp16.setExportFemaleChildOver1YearOld(rp16.getExportFemaleChildOver1YearOld()+foDetailDto.getFemaleChildOver1YearOld());
								}else {
									rp16.setExportFemaleChildOver1YearOld(foDetailDto.getFemaleChildOver1YearOld());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getFemaleChildOver1YearOld());
							} else {
								rp16.setFemaleChildOver1YearOld(foDetailDto.getReportForm16Old().getFemaleChildOver1YearOld());
							}
						}
						
						if(foDetailDto.getReportForm16Old().getUnGenderChildOver1YearOld() != null) {
							if(foDetailDto.getUnGenderChildOver1YearOld() != null) {
								rp16.setUnGenderChildOver1YearOld(foDetailDto.getReportForm16Old().getUnGenderChildOver1YearOld() - foDetailDto.getUnGenderChildOver1YearOld());
								if(rp16.getExportUnGenderChildOver1YearOld()!=null) {
									rp16.setExportUnGenderChildOver1YearOld(rp16.getExportUnGenderChildOver1YearOld()+foDetailDto.getUnGenderChildOver1YearOld());
								}else {
									rp16.setExportUnGenderChildOver1YearOld(foDetailDto.getUnGenderChildOver1YearOld());
								}
								rp16.setTotalExport((rp16.getTotalExport()==null?0:rp16.getTotalExport())+foDetailDto.getUnGenderChildOver1YearOld());
							} else {
								rp16.setUnGenderChildOver1YearOld(foDetailDto.getReportForm16Old().getUnGenderChildOver1YearOld());
							}
						}
						rp16 = reportForm16Repository.save(rp16);
						forestProductsListDetail.setReportForm16(rp16);
						AnimalReportDataSearchDto animalSearch= new AnimalReportDataSearchDto();
						if(farm!=null && farm.getId()!=null) {
							animalSearch.setFarmId(farm.getId());
						}
						if(animal!=null && animal.getId()!=null) {
							animalSearch.setAnimalId(animal.getId());
						}
						List<ForestProductsListDetailDto> listf= this.findAllByFPL(animalSearch);
						for(ForestProductsListDetailDto fpld: listf) {
							ForestProductsListDetail fp= forestProductsListDetailRepository.getOne(fpld.getId());
							if(fp!=null) {
								fp.setReportForm16Old(rp16);
							}
							forestProductsListDetailRepository.save(fp);
						}
						}
						
						if(dto.getCanceled()!=null && dto.getCanceled()==1){// điều kiện xét đv quay lại
							
							if(rpback==null) {
								rpback = new ReportPeriod();
								rpback.setFarm(farm);
								if(farm.getAdministrativeUnit() != null) {
									rpback.setAdministrativeUnit(farm.getAdministrativeUnit());
								}
								if(farm.getAdministrativeUnit().getParent() != null) {
									rpback.setDistrict(farm.getAdministrativeUnit().getParent());
								}
								if(farm.getAdministrativeUnit().getParent() != null && farm.getAdministrativeUnit().getParent().getParent() != null) {
									rpback.setProvince(farm.getAdministrativeUnit().getParent().getParent());
								}
								
								rpback.setYear(Calendar.getInstance().get(Calendar.YEAR));
								rpback.setMonth(Calendar.getInstance().get(Calendar.MONTH) + 1);
								rpback.setDate(Calendar.getInstance().get(Calendar.DATE));
								rpback.setType(1);
								rpback = reportPeriodRepository.save(rpback);
							}
							
							SearchReportPeriodDto searchDto= new SearchReportPeriodDto();
							searchDto.setAnimalId(animal.getId());
							searchDto.setFarmId(farm.getId());
							
							ReportPeriodDto reportperiod= reportPeriodService.getLastRecordReportPeriodByFarmAndAnimal(searchDto);
							ReportForm16 rp16Lastest = null;
							for(ReportForm16Dto rpdto: reportperiod.getReportItems()) {
								if(rpdto.getAnimal()!=null && rpdto.getAnimal().getId()!=null && rpdto.getAnimal().getId()==animal.getId()) {
									rp16Lastest = reportForm16Repository.findOne(rpdto.getId());
									break;
								}
							}
							
							ReportForm16 rp16Back = null;
							if(Calendar.getInstance().get(Calendar.YEAR) == reportperiod.getYear() && Calendar.getInstance().get(Calendar.MONTH) + 1 == reportperiod.getMonth()
									&& Calendar.getInstance().get(Calendar.DATE) == reportperiod.getDate()) {
								rp16Back=rp16Lastest;
							}else {
								if(foDetailDto.getReportForm16Back() != null && foDetailDto.getReportForm16Back().getId() != null) {
									rp16Back = reportForm16Repository.getOne(foDetailDto.getReportForm16Back().getId());
								} else {
									rp16Back = new ReportForm16();
									rp16Back.setDateReport(new Date());
									rp16Back.setFarm(farm);
									rp16Back.setReportPeriod(rpback);
									rp16Back.setAnimal(animal);
								}
							}
							
							if(rp16Lastest.getTotal() != null) {
								if(foDetailDto.getTotalBack() != null) {
									rp16Back.setTotal(rp16Lastest.getTotal() + foDetailDto.getTotalBack());
								} else {
									rp16Back.setTotal(rp16Lastest.getTotal());
								}
								
							}
							if(rp16Lastest.getMale() != null) {
								if(foDetailDto.getMaleBack() != null) {
									rp16Back.setMale(rp16Lastest.getMale() + foDetailDto.getMaleBack());
								} else {
									rp16Back.setMale(rp16Lastest.getMale());
								}
							}
							if(rp16Lastest.getFemale() != null) {
								if(foDetailDto.getFemaleBack() != null) {
									rp16Back.setFemale(rp16Lastest.getFemale() + foDetailDto.getFemaleBack());
								} else {
									rp16Back.setFemale(rp16Lastest.getFemale());
								}
							}
							if(rp16Lastest.getUnGender() != null) {
								if(foDetailDto.getUnGenderBack() != null) {
									rp16Back.setUnGender(rp16Lastest.getUnGender() + foDetailDto.getUnGenderBack());
								} else {
									rp16Back.setUnGender(rp16Lastest.getUnGender());
								}
							}
							
							if(rp16Lastest.getMaleParent() != null) {
								if(foDetailDto.getMaleParentBack() != null) {
									rp16Back.setMaleParent(rp16Lastest.getMaleParent() + foDetailDto.getMaleParentBack());
									if(rp16Back.getImportMaleParent()!=null) {
										rp16Back.setImportMaleParent(rp16Back.getImportMaleParent()+foDetailDto.getMaleParentBack());
									}else {
										rp16Back.setImportMaleParent(foDetailDto.getMaleParentBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getMaleParentBack());
								} else {
									rp16Back.setMaleParent(rp16Lastest.getMaleParent());
								}
							}
							if(rp16Lastest.getFemaleParent() != null) {
								if(foDetailDto.getFemaleParentBack() != null) {
									rp16Back.setFemaleParent(rp16Lastest.getFemaleParent() + foDetailDto.getFemaleParentBack());
									if(rp16Back.getImportFemaleParent()!=null) {
										rp16Back.setImportFemaleParent(rp16Back.getImportFemaleParent()+foDetailDto.getFemaleParentBack());
									}else {
										rp16Back.setImportFemaleParent(foDetailDto.getFemaleParentBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getFemaleParentBack());
								} else {
									rp16Back.setFemaleParent(rp16Lastest.getFemaleParent());
								}
							}
							if(rp16Lastest.getUnGenderParent() != null) {
								if(foDetailDto.getUnGenderParentBack() != null) {
									rp16Back.setUnGenderParent(rp16Lastest.getUnGenderParent() + foDetailDto.getUnGenderParentBack());
									if(rp16Back.getImportUnGenderParent()!=null) {
										rp16Back.setImportUnGenderParent(rp16Back.getImportUnGenderParent()+foDetailDto.getUnGenderParentBack());
									}else {
										rp16Back.setImportUnGenderParent(foDetailDto.getUnGenderParentBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getUnGenderParentBack());
								} else {
									rp16Back.setUnGenderParent(rp16Lastest.getUnGenderParent());
								}
							}
							
							if(rp16Lastest.getMaleGilts() != null ) {
								if(foDetailDto.getMaleGiltsBack() != null) {
									rp16Back.setMaleGilts(rp16Lastest.getMaleGilts() + foDetailDto.getMaleGiltsBack());
									if(rp16Back.getImportMaleGilts()!=null) {
										rp16Back.setImportMaleGilts(rp16Back.getImportMaleGilts()+foDetailDto.getMaleGiltsBack());
									}else {
										rp16Back.setImportMaleGilts(foDetailDto.getMaleGiltsBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getMaleGiltsBack());
								} else {
									rp16Back.setMaleGilts(rp16Lastest.getMaleGilts());
								}
							}
							if(rp16Lastest.getFemaleGilts() != null) {
								if( foDetailDto.getFemaleGiltsBack() != null) {
									rp16Back.setFemaleGilts(rp16Lastest.getFemaleGilts() + foDetailDto.getFemaleGiltsBack());
									if(rp16Back.getImportFemaleGilts()!=null) {
										rp16Back.setImportFemaleGilts(rp16Back.getImportFemaleGilts()+foDetailDto.getFemaleGiltsBack());
									}else {
										rp16Back.setImportFemaleGilts(foDetailDto.getFemaleGiltsBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getFemaleGiltsBack());
								} else {
									rp16Back.setFemaleGilts(rp16Lastest.getFemaleGilts());
								}
							}
							if(rp16Lastest.getUnGenderGilts() != null) {
								if( foDetailDto.getUnGenderGiltsBack() != null) {
									rp16Back.setUnGenderGilts(rp16Lastest.getUnGenderGilts() + foDetailDto.getUnGenderGiltsBack());
									if(rp16Back.getImportUnGenderGilts()!=null) {
										rp16Back.setImportUnGenderGilts(rp16Back.getImportUnGenderGilts()+foDetailDto.getUnGenderGiltsBack());
									}else {
										rp16Back.setImportUnGenderGilts(foDetailDto.getUnGenderGiltsBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getUnGenderGiltsBack());
								} else {
									rp16Back.setUnGenderGilts(rp16Lastest.getUnGenderGilts());
								}
							}
							
							if(rp16Lastest.getMaleChildUnder1YearOld() != null) {
								if(foDetailDto.getMaleChildUnder1YearOldBack() != null) {
									rp16Back.setMaleChildUnder1YearOld(rp16Lastest.getMaleChildUnder1YearOld() + foDetailDto.getMaleChildUnder1YearOldBack());
									if(rp16Back.getImportMaleChildUnder1YearOld()!=null) {
										rp16Back.setImportMaleChildUnder1YearOld(rp16Back.getImportMaleChildUnder1YearOld()+foDetailDto.getMaleChildUnder1YearOldBack());
									}else {
										rp16Back.setImportMaleChildUnder1YearOld(foDetailDto.getMaleChildUnder1YearOldBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getMaleChildUnder1YearOldBack());
								} else {
									rp16Back.setMaleChildUnder1YearOld(rp16Lastest.getMaleChildUnder1YearOld());
								}
							}
							if(rp16Lastest.getFemaleChildUnder1YearOld() != null) {
								if(foDetailDto.getFemaleChildUnder1YearOldBack() != null) {
									rp16Back.setFemaleChildUnder1YearOld(rp16Lastest.getFemaleChildUnder1YearOld() + foDetailDto.getFemaleChildUnder1YearOldBack());
									if(rp16Back.getImportFemaleChildUnder1YearOld()!=null) {
										rp16Back.setImportFemaleChildUnder1YearOld(rp16Back.getImportFemaleChildUnder1YearOld()+foDetailDto.getFemaleChildUnder1YearOldBack());
									}else {
										rp16Back.setImportFemaleChildUnder1YearOld(foDetailDto.getFemaleChildUnder1YearOldBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getFemaleChildUnder1YearOldBack());
								} else {
									rp16Back.setFemaleChildUnder1YearOld(rp16Lastest.getFemaleChildUnder1YearOld());
								}
							}
							if(rp16Lastest.getChildUnder1YearOld() != null) {
								if(foDetailDto.getChildUnder1YearOldBack() != null) {
									rp16Back.setChildUnder1YearOld(rp16Lastest.getChildUnder1YearOld() + foDetailDto.getChildUnder1YearOldBack());
									if(rp16Back.getImportChildUnder1YearOld()!=null) {
										rp16Back.setImportChildUnder1YearOld(rp16Back.getImportChildUnder1YearOld()+foDetailDto.getChildUnder1YearOldBack());
									}else {
										rp16Back.setImportChildUnder1YearOld(foDetailDto.getChildUnder1YearOldBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getChildUnder1YearOldBack());
								} else {
									rp16Back.setChildUnder1YearOld(rp16Lastest.getChildUnder1YearOld());
								}
							}
							
							if(rp16Lastest.getMaleChildOver1YearOld() != null) {
								if(foDetailDto.getMaleChildOver1YearOldBack() != null) {
									rp16Back.setMaleChildOver1YearOld(rp16Lastest.getMaleChildOver1YearOld() + foDetailDto.getMaleChildOver1YearOldBack());
									if(rp16Back.getImportMaleChildOver1YearOld()!=null) {
										rp16Back.setImportMaleChildOver1YearOld(rp16Back.getImportMaleChildOver1YearOld()+foDetailDto.getMaleChildOver1YearOldBack());
									}else {
										rp16Back.setImportMaleChildOver1YearOld(foDetailDto.getMaleChildOver1YearOldBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getMaleChildOver1YearOldBack());
								} else {
									rp16Back.setMaleChildOver1YearOld(rp16Lastest.getMaleChildOver1YearOld());
								}
							}
								
							if(rp16Lastest.getFemaleChildOver1YearOld()!= null) {
								if(foDetailDto.getFemaleChildOver1YearOldBack() != null) {
									rp16Back.setFemaleChildOver1YearOld(rp16Lastest.getFemaleChildOver1YearOld() + foDetailDto.getFemaleChildOver1YearOldBack());
									if(rp16Back.getImportFemaleChildOver1YearOld()!=null) {
										rp16Back.setImportFemaleChildOver1YearOld(rp16Back.getImportFemaleChildOver1YearOld()+foDetailDto.getFemaleChildOver1YearOldBack());
									}else {
										rp16Back.setImportFemaleChildOver1YearOld(foDetailDto.getFemaleChildOver1YearOldBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getFemaleChildOver1YearOldBack());
								} else {
									rp16Back.setFemaleChildOver1YearOld(rp16Lastest.getFemaleChildOver1YearOld());
								}
							}
							
							if(rp16Lastest.getUnGenderChildOver1YearOld() != null) {
								if(foDetailDto.getUnGenderChildOver1YearOldBack() != null) {
									rp16Back.setUnGenderChildOver1YearOld(rp16Lastest.getUnGenderChildOver1YearOld() + foDetailDto.getUnGenderChildOver1YearOldBack());
									if(rp16Back.getImportUnGenderChildOver1YearOld()!=null) {
										rp16Back.setImportUnGenderChildOver1YearOld(rp16Back.getImportUnGenderChildOver1YearOld()+foDetailDto.getUnGenderChildOver1YearOldBack());
									}else {
										rp16Back.setImportUnGenderChildOver1YearOld(foDetailDto.getUnGenderChildOver1YearOldBack());
									}
									rp16Back.setTotalImport((rp16Back.getTotalImport()==null?0:rp16Back.getTotalImport())+foDetailDto.getUnGenderChildOver1YearOldBack());
								} else {
									rp16Back.setUnGenderChildOver1YearOld(rp16Lastest.getUnGenderChildOver1YearOld());
								}
							}
							rp16Back = reportForm16Repository.save(rp16Back);
							forestProductsListDetail.setReportForm16Back(rp16Back);
							AnimalReportDataSearchDto animalSearch= new AnimalReportDataSearchDto();
							if(farm!=null && farm.getId()!=null) {
								animalSearch.setFarmId(farm.getId());
							}
							if(animal!=null && animal.getId()!=null) {
								animalSearch.setAnimalId(animal.getId());
							}
							List<ForestProductsListDetailDto> listf= this.findAllByFPL(animalSearch);
							for(ForestProductsListDetailDto fpld: listf) {
								ForestProductsListDetail fp= forestProductsListDetailRepository.getOne(fpld.getId());
								if(fp!=null) {
									fp.setReportForm16Old(rp16Back);
									
								}
								forestProductsListDetailRepository.save(fp);
							}
						}
						
						details.add(forestProductsListDetail);
					}
				}
			}
			if (entity.getDetails() == null) {
				entity.setDetails(details);
			} else {
				entity.getDetails().clear();
				entity.getDetails().addAll(details);
			}

//			Set<ImportExportAnimal> exports = new HashSet<ImportExportAnimal>();
//			if(dto.getExports() != null && dto.getExports().size() > 0) {
//				for(ImportExportAnimalDto importExportAnimalDto: dto.getExports()) {
//					if(importExportAnimalDto != null) {
//						ImportExportAnimal importExportAnimal = null;
//						if(importExportAnimalDto.getId() != null) {
//							importExportAnimal = importExportAnimalRepository.findById(importExportAnimalDto.getId());
//						}
//						if(importExportAnimal != null) {
//							importExportAnimal.setForestProductsList(entity);
//							exports.add(importExportAnimal);
//						}
//					}
//					
//				}
//			}
//			if(entity.getExports() == null) {
//				entity.setExports(exports);
//			}else {
//				entity.getExports().clear();
//				entity.getExports().addAll(exports);
//			}
			entity = forestProductsListRepository.save(entity);
			
			//goij ham tao thong bao
			ForestProductsListDto forestDto= new ForestProductsListDto(entity);
			SystemMessageDto systemMessdto= systemMessageService.saveMessageForestProduct(forestDto);
			//nhan email
//			if(entity.getAdministrativeUnitTo()!=null) {
//				List<UserAdministrativeUnit> listUserAdministrativeUnitsProvince= new ArrayList<>();
//				if(entity.getAdministrativeUnitTo().getParent()!=null) {
//					listUserAdministrativeUnitsProvince= userAdministrativeUnitService.getListUserByAdministrativeUnitId(entity.getAdministrativeUnitTo().getParent());
//				}
//				List<UserAdministrativeUnit> listUserAdministrativeUnits= userAdministrativeUnitService.getListUserByAdministrativeUnitId(entity.getAdministrativeUnitTo());
//				List<User> listUser= new ArrayList<User>();
//				if(listUserAdministrativeUnits!=null && listUserAdministrativeUnits.size()>0) {
//					for(UserAdministrativeUnit userAdminUnit: listUserAdministrativeUnits) {
//						listUser.add(userAdminUnit.getUser());
//					}
//				}
//				if(listUserAdministrativeUnitsProvince!=null && listUserAdministrativeUnitsProvince.size()>0) {
//					for(UserAdministrativeUnit userAdminUnit: listUserAdministrativeUnitsProvince) {
//						listUser.add(userAdminUnit.getUser());
//					}
//				}
//				String listEmail= ";";
//				if(listUser.size()>0) {
//					for(User user : listUser) {
//						if(user.getEmail()!=null ) {
//							listEmail+=user.getEmail();
//							listEmail+=";";
//						}
//						
//					}
//				}
//				System.out.println(listUser);
//				String email[]=listEmail.split(";");
//				System.out.println(email);
//				if(email.length>0) {
//					EmailUtil.sendEmailForestProduct("Có bảng kê lâm sản mới", email, "Bảng kê lâm sản mới", forestDto);
//				}
//				
//			}
//			//nhan email
			
			
			//Don vi cap ban ke thay doi => thay doi thong tin co quan userAttachment
//			Long currentUserID = modifiedUser.getId();
//			List<UserAttachmentsDto> listUserAttachments = userAttachmentsService.getByUserId(currentUserID);
//			
//			for (UserAttachmentsDto userAttachemtsdto : listUserAttachments) {
//				UserAttachments ua = null;
//				if(userAttachemtsdto.getId() != null) {
//					ua = userAttachmentsRepository.findOne(userAttachemtsdto.getId());
//				}
//				if(dto.getOrganizationName() != null && !dto.getOrganizationName().equals("")) {
//					ua.setOrganizationName(dto.getOrganizationName());
//				}
//				ua = userAttachmentsRepository.save(ua);				
//			}
			return forestDto;
		}
		return dto;
	}

	@Override
	public ForestProductsListDto getById(Long id) {
		ForestProductsListDto result = null;
		ForestProductsList entity = forestProductsListRepository.findOne(id);
		if (entity != null) {
			result = new ForestProductsListDto(entity);
		}
		return result;
	}

	@Override
	public Page<ForestProductsListDto> getSearchByPage(AnimalReportDataSearchDto searchDto, int pageIndex,
			int pageSize) {
		//tran huu dat lay cac tinh thanh cua user hien tai
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		
		
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		boolean isAdministrativeUnitRole = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH) 
											|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DISTRICT)
											|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_WARD);
		boolean isFarmerRole =  SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_FAMER);
		List<Long> fmsAdministrativeUnitIds = new ArrayList<Long>();
		
		if(!isAdmin && isAdministrativeUnitRole) {
			List<FmsAdministrativeUnit> list = userAdministrativeUnitService.getAdministrativeUnitByLoginUser();
			for(FmsAdministrativeUnit fmsAdministrativeUnit: list) {
				if(fmsAdministrativeUnit!= null) fmsAdministrativeUnitIds.add(fmsAdministrativeUnit.getId());
			}
			if(list!=null && list.size()>0) {
				for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
					if(fmsAdministrativeUnit.getParent()==null) {//Cấp tỉnh
						searchDto.setProvinceId(fmsAdministrativeUnit.getId());
					}
					else if(fmsAdministrativeUnit.getParent()!=null 
							&& fmsAdministrativeUnit.getParent().getParent()==null) {//Cấp huyện
						if(!fmsAdministrativeUnitIds.contains(searchDto.getDistrictId()))
						searchDto.setDistrictId(fmsAdministrativeUnit.getId());
					}
					else if(fmsAdministrativeUnit.getParent()!=null 
							&& fmsAdministrativeUnit.getParent().getParent()!=null 
							&& fmsAdministrativeUnit.getParent().getParent().getParent()==null) {//Cấp xã
						searchDto.setCommuneId(fmsAdministrativeUnit.getId());
					}
					else {
						return null;
					}
				}
			}
			else {
				return null;
			}
		}
		else if(isFarmerRole){
			searchDto.setNameOrCode(currentUser.getUsername());
		}
		//them
		List<Long> listWardId = null;
		if (!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if (listWardId == null || listWardId.size() == 0) {
				return null;
			}
		}
		//tran huu dat lay cac tinh thanh cua user hien tai
		
		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		String sql = "select new com.globits.wl.dto.ForestProductsListDto(fpl) FROM ForestProductsList fpl ";
		String sqlCount = "select count(*) FROM ForestProductsList fpl ";
		String whereClause = " where 1=1 ";
		
		String orderBy = " order by fpl.dateIssue desc ";

		//tran huu dat test query theo tinh xa
		if(searchDto.getCommuneId()!=null) {
			whereClause += " AND fpl.farm.administrativeUnit.id = :communeId";
		}
		if(searchDto.getDistrictId()!=null) {
			whereClause += " AND fpl.farm.administrativeUnit.parent.id = :districtId";
		}
		if(searchDto.getProvinceId()!=null) {
			whereClause += " AND fpl.farm.administrativeUnit.parent.parent.id  = :provinceId";
		}
		//tran huu dat test query theo tinh xa
		if (searchDto.getFarmId() != null) {
			whereClause += " AND fpl.farm.id = :farmId";
		}
		if (searchDto.getNameOrCode() != null) {
			whereClause += " AND (fpl.code like :nameOrCode or fpl.farm.name like :nameOrCode or fpl.farm.code like :nameOrCode or fpl.confirmByUser.username like :nameOrCode) ";
		}
		if (searchDto.getFromDate() != null) {
			whereClause += " AND fpl.dateIssue >= :fromDate";
		}
		if (searchDto.getToDate() != null) {
			whereClause += " AND fpl.dateIssue <= :toDate";
		}
		if (!isAdmin) {
			whereClause += " and (fpl.farm.administrativeUnit.id in (:listCommuneId)) ";
		}
		Query query = manager.createQuery(sql + whereClause + orderBy, ForestProductsListDto.class);
		Query queryCount = manager.createQuery(sqlCount + whereClause, Long.class);
		
		
		//tran huu dat test set param
		if(searchDto.getCommuneId()!=null) {			
			query.setParameter("communeId", searchDto.getCommuneId());
			queryCount.setParameter("communeId", searchDto.getCommuneId());
		}
		if(searchDto.getDistrictId()!=null) {	
				query.setParameter("districtId", searchDto.getDistrictId());
				queryCount.setParameter("districtId", searchDto.getDistrictId());
		}
		if(searchDto.getProvinceId()!=null) {
			query.setParameter("provinceId", searchDto.getProvinceId());
			queryCount.setParameter("provinceId", searchDto.getProvinceId());
		}
		
		//tran huu dat test set param
		
		if (searchDto.getFarmId() != null) {
			query.setParameter("farmId", searchDto.getFarmId());
			queryCount.setParameter("farmId", searchDto.getFarmId());
		}
		if (searchDto.getNameOrCode() != null) {
			query.setParameter("nameOrCode", "%" + searchDto.getNameOrCode() + "%");
			queryCount.setParameter("nameOrCode", "%" + searchDto.getNameOrCode() + "%");
		}
		if (searchDto.getFromDate() != null) {
			query.setParameter("fromDate", WLDateTimeUtil.getStartOfDay(searchDto.getFromDate()));
			queryCount.setParameter("fromDate", WLDateTimeUtil.getStartOfDay(searchDto.getFromDate()));
		}
		if (searchDto.getToDate() != null) {
			query.setParameter("toDate", WLDateTimeUtil.getEndOfDay(searchDto.getToDate()));
			queryCount.setParameter("toDate", WLDateTimeUtil.getEndOfDay(searchDto.getToDate()));
		}
		//them
		if (!isAdmin) {
			query.setParameter("listCommuneId", listWardId);
			queryCount.setParameter("listCommuneId", listWardId);
		}
		query.setFirstResult(pageIndex * pageSize);
		query.setMaxResults(pageSize);
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		Long total = 0L;
		Object obj = queryCount.getSingleResult();
		if (obj != null) {
			total = (Long) obj;
		}
		Page<ForestProductsListDto> page = new PageImpl<ForestProductsListDto>(query.getResultList(), pageable, total);
		return page;
	}

	@Override
	public List<ForestProductsListDto> getAllBySearch(AnimalReportDataSearchDto searchDto) {
		String sql = "select new com.globits.wl.dto.ForestProductsListDto(fpl) FROM ForestProductsList fpl ";
		String whereClause = " where 1=1 ";
		String orderBy = " order by fpl.dateIssue desc ";

		if (searchDto.getFarmId() != null) {
			whereClause += " AND fpl.farm.id = :farmId";
		}
		Query query = manager.createQuery(sql + whereClause + orderBy, ForestProductsListDto.class);

		if (searchDto.getFarmId() != null) {
			query.setParameter("farmId", searchDto.getFarmId());
		}
		return query.getResultList();
	}

	@Override
	public Boolean deleteById(Long id) {
		Boolean flag = false;
		try {
			forestProductsListRepository.delete(id);
			flag = true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}

	/**
	 * Xóa set null cho trường forestProductsList của bản ghi ImportExportAnimal
	 * theo id
	 */
	@Override
	public Boolean deleteLinkedByExportAnimalId(Long id) {
//		ImportExportAnimal importExportAnimal = this.importExportAnimalRepository.findById(id);
//		if(importExportAnimal == null)return false;
//		importExportAnimal.setForestProductsList(null);
//		this.importExportAnimalRepository.save(importExportAnimal);
		return true;
	}

	@Override
	public ForestProductsListDto sendEmail(ForestProductsListDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			ForestProductsList entity = null;
			if (dto.getId() != null) {
				entity = forestProductsListRepository.getOne(dto.getId());
			}
			if (entity == null) {
				return null;
			} 
//			if(!entity.getCreatedBy().equals(currentUserName)) {
//				return null;
//			}
			entity.setModifiedBy(currentUserName);
			entity.setModifyDate(currentDate);
			entity.setCanceled(0);
			ForestProductsListDto forestDto= new ForestProductsListDto(entity);
			//nhan email
			String listEmail= ";";
//			if(entity.getAdministrativeUnitTo()!=null) {
//				List<UserAdministrativeUnit> listUserAdministrativeUnitsProvince= new ArrayList<>();
//				if(entity.getAdministrativeUnitTo().getParent()!=null) {
//					listUserAdministrativeUnitsProvince= userAdministrativeUnitService.getListUserByAdministrativeUnitId(entity.getAdministrativeUnitTo().getParent());
//				}
//				List<UserAdministrativeUnit> listUserAdministrativeUnits= userAdministrativeUnitService.getListUserByAdministrativeUnitId(entity.getAdministrativeUnitTo());
//				List<User> listUser= new ArrayList<User>();
//				if(listUserAdministrativeUnits!=null && listUserAdministrativeUnits.size()>0) {
//					for(UserAdministrativeUnit userAdminUnit: listUserAdministrativeUnits) {
//						listUser.add(userAdminUnit.getUser());
//					}
//				}
//				if(listUserAdministrativeUnitsProvince!=null && listUserAdministrativeUnitsProvince.size()>0) {
//					for(UserAdministrativeUnit userAdminUnit: listUserAdministrativeUnitsProvince) {
//						listUser.add(userAdminUnit.getUser());
//					}
//				}
//				
//				if(listUser.size()>0) {
//					for(User user : listUser) {
//						if(user.getEmail()!=null ) {
//							listEmail+=user.getEmail();
//							listEmail+=";";
//						}
//						
//					}
//				}
//				
//			}
//			if(dto.getListEmail().size()>0) {
//				for(String s : dto.getListEmail()) {
//					listEmail+=s;
//					listEmail+=";";
//				}
//				
//			}
//			
//			String email[]=listEmail.split(";");
			//System.out.println(email);
			Set<String> listEmailSend = this.getEmailAddressToSend(dto);
			for(String s : listEmailSend) {
				listEmail += s;
				listEmail += ";";
			}
			String email[]=listEmail.split(";");
			EmailUtil.sendEmailForestProduct("Có bảng kê lâm sản mới", email, "Bảng kê lâm sản mới", forestDto);
//			//nhan email
			return forestDto;
		}
		return null;
	}
	@Override
	public Set<String> getEmailAddressToSend(ForestProductsListDto dto) {
		Set<String> emails = new HashSet<String>();
		if(dto!=null) {
			ForestProductsList entity = null;
			if (dto.getId() != null) {
				entity = forestProductsListRepository.getOne(dto.getId());
			}
			if (entity == null) {
				return null;
				
			} 
//			Farm farm = null;
//			if (dto.getFarm() != null && dto.getFarm().getId() != null) {
//				farm = farmRepository.getById(dto.getFarm().getId());
//				entity.setFarm(farm);
//			}
			
			//them tinh
//			if (dto.getAdministrativeUnitTo() != null) {
//				FmsAdministrativeUnitDto auDto = dto.getAdministrativeUnitTo();
//				FmsAdministrativeUnit au = null;
//				if (auDto.getId() != null) {
//					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
//					if(au!=null) {
//						entity.setAdministrativeUnitTo(au);
//					}
//				}else if(auDto.getCode()!=null){
//					List<FmsAdministrativeUnit> list=fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
//					if(list!=null && list.size()>0){
//						au=list.get(0);
//						entity.setAdministrativeUnitTo(au);
//					}				
//				}
//				
//			}else {
//				FmsAdministrativeUnit au = null;
//				if(dto.getProvinceIdTo()!=null || dto.getProvinceCodeTo()!=null) {
//					if(dto.getProvinceIdTo()!=null) {
//						au= this.fmsAdministrativeUnitRepository.findOne(dto.getProvinceIdTo());
//						if(au!=null) {
//							entity.setAdministrativeUnitTo(au);
//						}
//					}else {
//						if(dto.getProvinceCodeTo()!=null) {
//							List<FmsAdministrativeUnit> list=fmsAdministrativeUnitRepository.findListByCode(dto.getProvinceCodeTo());
//							if(list!=null && list.size()>0){
//								au=list.get(0);
//								entity.setAdministrativeUnitTo(au);
//							}	
//						}
//					}
//				}else {
//					if(dto.getAdministrativeUnitFrom()==null) {
//						entity.setAdministrativeUnitTo(null);
//					}
//				}
//				
//			}
			//them tinh

			//nhan email
			if(entity.getAdministrativeUnitTo()!=null) {
				List<UserAdministrativeUnit> listUserAdministrativeUnitsProvince= new ArrayList<>();
				if(entity.getAdministrativeUnitTo().getParent()!=null) {
					listUserAdministrativeUnitsProvince= userAdministrativeUnitService.getListUserByAdministrativeUnitId(entity.getAdministrativeUnitTo().getParent());
				}
				List<UserAdministrativeUnit> listUserAdministrativeUnits= userAdministrativeUnitService.getListUserByAdministrativeUnitId(entity.getAdministrativeUnitTo());
				List<User> listUser= new ArrayList<User>();
				if(listUserAdministrativeUnits!=null && listUserAdministrativeUnits.size()>0) {
					for(UserAdministrativeUnit userAdminUnit: listUserAdministrativeUnits) {
						listUser.add(userAdminUnit.getUser());
					}
				}
				if(listUserAdministrativeUnitsProvince!=null && listUserAdministrativeUnitsProvince.size()>0) {
					for(UserAdministrativeUnit userAdminUnit: listUserAdministrativeUnitsProvince) {
						listUser.add(userAdminUnit.getUser());
					}
				}
				String listEmail= ";";
				if(listUser.size()>0) {
					for(User user : listUser) {
						if(user.getEmail()!=null ) {
							listEmail+=user.getEmail();
							listEmail+=";";
						}
						
					}
				}
				String email[]=listEmail.split(";");
				
				if(email.length>0) {
					for(String e : email) {
						if(!StringUtils.isEmpty(e)) {
							emails.add(e);
						}
						
					}
					//EmailUtil.sendEmailForestProduct("Có bảng kê lâm sản mới", email, "Bảng kê lâm sản mới", forestDto);
				}
				
			}
			if(entity.getFarm() != null && entity.getFarm().getOwnerEmail() != null) {
				emails.add(entity.getFarm().getOwnerEmail());
			}
			return emails;
		}
		return emails;
	}

	@Override
	public List<ForestProductsListDetailDto> findAllByFPL(AnimalReportDataSearchDto searchDto) {
		if(searchDto!=null) {
			if(searchDto.getFarmId()!=null && searchDto.getAnimalId()!=null) {
				String sql = "select new com.globits.wl.dto.ForestProductsListDetailDto(fpld) FROM ForestProductsListDetail fpld ";
				String whereClause = " where fpld.forestProductsList is not null AND fpld.forestProductsList.canceled = :cancel ";
	
				if(searchDto.getSkipFPLD()!=null) {
					whereClause += " AND fpld.id != :id";
				}
				if (searchDto.getFarmId() != null) {
					whereClause += " AND fpld.forestProductsList.farm.id = :farmId";
				}
				if (searchDto.getAnimalId() != null) {
					whereClause += " AND fpld.animal.id = :animalId";
				}
				Query query = manager.createQuery(sql + whereClause , ForestProductsListDetailDto.class);

				query.setParameter("cancel", 2);
				if (searchDto.getSkipFPLD() != null) {
					query.setParameter("id", searchDto.getSkipFPLD());
				}
				if (searchDto.getFarmId() != null) {
					query.setParameter("farmId", searchDto.getFarmId());
				}
				if (searchDto.getAnimalId() != null) {
					query.setParameter("animalId", searchDto.getAnimalId());
				}
				return query.getResultList();
			}
		}
		return null;
	}

}
