package com.globits.wl.service.impl;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.domain.FileDescription;
import com.globits.core.domain.Person;
import com.globits.core.repository.FileDescriptionRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.repository.RoleRepository;
import com.globits.security.repository.UserRepository;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Certificate;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmAnimal;
import com.globits.wl.domain.FarmAnimalProductTargetExist;
import com.globits.wl.domain.FarmAnimalType;
import com.globits.wl.domain.FarmCertificate;
import com.globits.wl.domain.FarmFileAttachment;
import com.globits.wl.domain.FarmHusbandryMethod;
import com.globits.wl.domain.FarmProductTarget;
import com.globits.wl.domain.FarmProductTargetExist;
import com.globits.wl.domain.FarmSize;
import com.globits.wl.domain.FarmStore;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.FmsRegion;
import com.globits.wl.domain.HusbandryMethod;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.Ownership;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportFormAnimalEgg;
import com.globits.wl.domain.ReportFormAnimalGiveBirth;
import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.domain.WaterSource;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.CertificateDto;
import com.globits.wl.dto.DoubtsOverlapDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FarmFileAttachmentDto;
import com.globits.wl.dto.FarmHusbandryMethodDto;
import com.globits.wl.dto.FarmStoreDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.functiondto.DensityRegionDto;
import com.globits.wl.dto.functiondto.FarmAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.FarmDuplicateDoubtsDto;
import com.globits.wl.dto.functiondto.FarmMapDeleteDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.dto.report.FarmReportDto;
import com.globits.wl.repository.AnimalReportDataRepository;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.AnimalTypeRepository;
import com.globits.wl.repository.CertificateRepository;
import com.globits.wl.repository.FarmAnimalProductTargetExistRepository;
import com.globits.wl.repository.FarmAnimalRepository;
import com.globits.wl.repository.FarmFileAttachmentRepository;
import com.globits.wl.repository.FarmHusbandryMethodRepository;
import com.globits.wl.repository.FarmHusbandryTypeRepository;
import com.globits.wl.repository.FarmProductTargetExistRepository;
import com.globits.wl.repository.FarmReportPeriodRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.FarmSizeRepository;
import com.globits.wl.repository.FarmStoreRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.FmsRegionRepository;
import com.globits.wl.repository.HusbandryMethodRepository;
import com.globits.wl.repository.HusbandryTypeRepository;
import com.globits.wl.repository.ImportExportAnimalRepository;
import com.globits.wl.repository.IndividualAnimalRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.OwnershipRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.repository.ReportForm16Repository;
import com.globits.wl.repository.ReportFormAnimalEggRepository;
import com.globits.wl.repository.ReportFormAnimalGiveBirthRepository;
import com.globits.wl.repository.ReportPeriodRepository;
import com.globits.wl.repository.WaterSourceRepository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.FmsAdministrativeUnitService;
import com.globits.wl.service.FmsUserService;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.FarmMapServiceUtil;
import com.globits.wl.utils.WLConstant;

@Service
public class FarmServiceImpl extends GenericServiceImpl<Farm, Long> implements FarmService {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private FarmFileAttachmentRepository farmFileAttachmentRepository;

	@Autowired
	private FileDescriptionRepository fileDescriptionRepository;

	@Autowired
	private FarmStoreRepository farmStoreRepository;

	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;

	@Autowired
	private HusbandryTypeRepository husbandryTypeRepository;

	@Autowired
	private HusbandryMethodRepository husbandryMethodRepository;

	@Autowired
	private WaterSourceRepository waterSourceRepository;

	@Autowired
	private FarmHusbandryTypeRepository farmHusbandryTypeRepository;

	@Autowired
	private ProductTargetRepository productTargetRepository;

	@Autowired
	private FarmAnimalRepository farmAnimalRepository;

	@Autowired
	private AnimalRepository animalRepository;
	@Autowired
	private AnimalTypeRepository animalTypeRepository;

	@Autowired
	private CertificateRepository certificateRepository;

	@Autowired
	private FarmSizeRepository farmSizeRepository;

	@Autowired
	private FmsAdministrativeUnitRepository administrativeUnitRepository;

	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ImportExportAnimalRepository importExportAnimalRepository;

	@Autowired
	private FmsRegionRepository fmsRegionRepository;
	@Autowired
	private FmsAdministrativeUnitService fmsAdministrativeUnitService;
	@Autowired
	private FmsUserService fmsUserService;
	@Autowired
	private FarmAnimalProductTargetExistRepository farmAnimalProductTargetExistRepository;
	@Autowired
	private FarmProductTargetExistRepository farmProductTargetExistRepository;
	@Autowired
	private OwnershipRepository ownershipRepository;
	@Autowired
	private AnimalReportDataRepository animalReportDataRepository;
	@Autowired
	private OriginalRepository originalRepository;
	@Autowired
	private IndividualAnimalRepository individualAnimalRepository;
	@Autowired
	private FarmReportPeriodRepository farmReportPeriodRepository;
	@Autowired
	private FarmHusbandryMethodRepository farmHusbandryMethodRepository;
	@Autowired
	private AnimalReportDataService animalReportDataService;
	@Autowired
	private ReportPeriodRepository reportPeriodRepository;
	@Autowired
	ReportFormAnimalEggRepository reportFormAnimalEggRepository;
	@Autowired
	ReportFormAnimalGiveBirthRepository reportFormAnimalGiveBirthRepository;
	@Autowired
	private ReportPeriodService reportPeriodService;

	@Autowired
	private ReportForm16Repository reportForm16Repository;

	@Override
	public Page<FarmDto> getPageDto(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.farmRepository.getPageDto(pageable);
	}

	@Override
	public List<FarmDto> getAll() {
		return this.farmRepository.getAllSimple();
	}

	@Override
	public FarmDto updateFarm(Long id, FarmDto dto) {

		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			boolean isNew = false;
			Farm farm = null;
			if (id != null) {
				farm = this.farmRepository.findOne(id);
			} else if (dto.getId() != null) {
				farm = this.farmRepository.findOne(dto.getId());
			}
//				if(dto.getMapCode()!=null && dto.getMapCode().length()>0) {
//					List<Farm> list = farmRepository.findBymapCode(dto.getMapCode());
//					if(list!=null && list.size()>0) {
//						return null;
//					}
//				}
			if (farm == null) {
				farm = new Farm();
				farm.setCreateDate(currentDate);
				farm.setCreatedBy(currentUserName);
				isNew = true;
			}
			farm.setName(dto.getName());
			if (isNew == true) {
				String code = null;
				if (dto.getDistrictCode() != null && dto.getDistrictId() != null && dto.getProvinceCode() != null) {
					code = autoGenericCode(dto.getDistrictCode(), dto.getDistrictId(), dto.getProvinceCode());
				} else if (dto.getAdministrativeUnit() != null) {
					String codeDis = null;
					String codeCity = null;
					Long districtId = null;
					FmsAdministrativeUnit ward = fmsAdministrativeUnitService
							.findByCode(dto.getAdministrativeUnit().getCode());

					if (ward != null && ward.getParent() != null) {
						districtId = ward.getParent().getId();
						codeDis = ward.getParent().getCode();
						if (ward.getParent().getParent() != null) {
							codeCity = ward.getParent().getParent().getCode();
							code = autoGenericCode(codeDis, districtId, codeCity);
						}
					}
				}

				if (code != null && code.length() > 0) {
					farm.setCode(code);
				} else {
					String log = "";
					if (dto.getAdministrativeUnit() != null) {
						log += dto.getAdministrativeUnit().getCode();
					} else {
						log = dto.getName();
					}
					System.out.println("AdministrativeUnit ERR:" + log);
					return null;
				}
			}

			/* New field fow WL */
			farm.setxVN2000(dto.getxVN2000());
			farm.setyVN2000(dto.getyVN2000());
			farm.setInterviewDate(dto.getInterviewDate());
			farm.setOwnerAge(dto.getOwnerAge());
			farm.setOwnerGender(dto.getOwnerGender());
			farm.setGcsAccuracy(dto.getGcsAccuracy());
			farm.setGcsElevation(dto.getGcsElevation());
			farm.setGcsLat(dto.getGcsLat());
			farm.setGcsLong(dto.getGcsLong());
			farm.setGcsZone(dto.getGcsZone());
			farm.setOldCode(dto.getOldCode());
			farm.setInterviewStartTime(dto.getInterviewStartTime());
			farm.setInterviewFinTime(dto.getInterviewFinTime());
			farm.setInterviewer(dto.getInterviewer());
			farm.setInName(dto.getInName());
			farm.setInAge(dto.getInAge());
			farm.setInGen(dto.getInGen());
			farm.setInTel(dto.getInTel());
			farm.setCaName(dto.getCaName());
			farm.setCaAge(dto.getCaAge());
			farm.setCaGen(dto.getCaGen());
			;
			farm.setType(dto.getType());
			farm.setYearRegistration(dto.getYearRegistration());
			farm.setMapCode(dto.getMapCode());
			if (dto.getYearRegistration() == null) {
				farm.setYearRegistration(new SimpleDateFormat("yyyy").format(new Date()));
			}
			/**/

			farm.setLongitude(dto.getLongitude());
			farm.setLatitude(dto.getLatitude());

			farm.setgMapX(dto.getgMapX());
			farm.setgMapY(dto.getgMapY());
			farm.setAdressDetail(dto.getAdressDetail());
			farm.setVillage(dto.getVillage());
			farm.setMediaLink(dto.getMediaLink());
			farm.setDescription(dto.getDescription());
			farm.setPhoneNumber(dto.getPhoneNumber());
			farm.setFax(dto.getFax());

			farm.setOwnerName(dto.getOwnerName());
			if (StringUtils.isEmpty(farm.getOwnerName())) {
				farm.setOwnerName(farm.getName());
			}
			farm.setOwnerPhoneNumber(dto.getOwnerPhoneNumber());
			farm.setOwnerCitizenCode(dto.getOwnerCitizenCode());
			farm.setOwnerEmail(dto.getOwnerEmail());
			farm.setOwnerAdress(dto.getOwnerAdress());
			farm.setOwnerDob(dto.getOwnerDob());
			farm.setOwnerPositionName(dto.getOwnerPositionName());

			farm.setVetStaffName(dto.getVetStaffName());
			farm.setVetStaffCitizenCode(dto.getVetStaffCitizenCode());
			farm.setVetStaffPhoneNumber(dto.getVetStaffPhoneNumber());
			farm.setVetStaffEmail(dto.getVetStaffEmail());
			farm.setVetStaffAdress(dto.getVetStaffAdress());

			farm.setReasonNotYetRegister(dto.getReasonNotYetRegister());
			farm.setNewRegistrationCode(dto.getNewRegistrationCode());
			farm.setOldRegistrationCode(dto.getOldRegistrationCode());
			farm.setIssuingCodeDate(dto.getIssuingCodeDate());
			farm.setFoundingDate(dto.getFoundingDate());

			farm.setTotalAcreage(dto.getTotalAcreage());
			farm.setLodgingAcreage(dto.getLodgingAcreage());
			farm.setMaxNumberOfAnimal(dto.getMaxNumberOfAnimal());
			farm.setSalanganeHouseType(dto.getSalanganeHouseType());
			farm.setSalanganeNestExploitQuantity(dto.getSalanganeNestExploitQuantity());
			farm.setMethodFeed(dto.getMethodFeed());

			// tran huu dat them cac thuoc tinh file excel moi
			farm.setOwnerCitizenDate(dto.getOwnerCitizenDate());
			farm.setOwnerCitizenLocation(dto.getOwnerCitizenLocation());
			farm.setApartmentNumber(dto.getApartmentNumber());
			farm.setStartDate(dto.getStartDate());
			farm.setTtbvmt(dto.getTtbvmt());
			farm.setTtbvmtDate(dto.getTtbvmtDate());
			farm.setDateRegistration(dto.getDateRegistration());// ngay cap gcn dk
			// tran huu dat them cac thuoc tinh file excel moi
			farm.setDateOtherRegistration(dto.getDateOtherRegistration());
			Ownership ownership = null;
			if (dto.getOwnership() != null && dto.getOwnership().getId() != null) {
				ownership = ownershipRepository.findOne(dto.getOwnership().getId());
			}
			farm.setOwnership(ownership);

			if (dto.getFarmProductTargets() != null && dto.getFarmProductTargets().size() > 0) {
				Set<FarmProductTarget> lst = new HashSet<FarmProductTarget>();
				for (ProductTargetDto fptDto : dto.getFarmProductTargets()) {
					if (fptDto != null) {
						ProductTarget pg = null;
						if (fptDto.getId() != null) {
							pg = productTargetRepository.findOne(fptDto.getId());
						} else if (fptDto.getCode() != null) {
							List<ProductTarget> list = productTargetRepository.findByCode(fptDto.getCode());
							if (list != null && list.size() > 0) {
								pg = list.get(0);
							}
						}

						if (pg != null) {
							FarmProductTarget fpt = new FarmProductTarget();
							fpt.setFarm(farm);
							fpt.setProductTarget(pg);
							lst.add(fpt);
						}
					}
				}
				if (lst.size() > 0) {
					if (farm.getFarmProductTargets() == null) {
						farm.setFarmProductTargets(lst);
					} else {
						farm.getFarmProductTargets().clear();
						farm.getFarmProductTargets().addAll(lst);
					}
				}
			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmProductTargets() != null) {
					farm.getFarmProductTargets().clear();
				}
			}
			if (dto.getHusbandryMethod() != null) {
				HusbandryMethod husbandryMethod = null;
				if (dto.getHusbandryMethod().getId() != null) {
					husbandryMethod = this.husbandryMethodRepository.findOne(dto.getHusbandryMethod().getId());
				}
				if (husbandryMethod == null) {
					husbandryMethod = new HusbandryMethod();
					husbandryMethod.setCreateDate(currentDate);
					husbandryMethod.setCreatedBy(currentUserName);
				}
				husbandryMethod.setCode(dto.getHusbandryMethod().getCode());
				husbandryMethod.setName(dto.getHusbandryMethod().getName());
				farm.setHusbandryMethod(husbandryMethod);
			}
			if (dto.getWaterResources() != null) {
				WaterSource waterSource = null;
				if (dto.getWaterResources().getId() != null) {
					waterSource = this.waterSourceRepository.findOne(dto.getWaterResources().getId());
				}
				if (waterSource == null) {
					waterSource = new WaterSource();
					waterSource.setCreateDate(currentDate);
					waterSource.setCreatedBy(currentUserName);
				}
				waterSource.setCode(dto.getWaterResources().getCode());
				waterSource.setName(dto.getWaterResources().getName());
				farm.setWaterResources(waterSource);
			}
			farm.setIsOutSourcing(dto.getIsOutSourcing());
			farm.setNumberOfLabor(dto.getNumberOfLabor());
			farm.setDistanceToResidentialArea(dto.getDistanceToResidentialArea());

			if (dto.getAdministrativeUnit() != null) {
				FmsAdministrativeUnitDto auDto = dto.getAdministrativeUnit();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if (au != null)
						farm.setAdministrativeUnit(au);
				} else if (auDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if (list != null && list.size() > 0) {
						au = list.get(0);
						farm.setAdministrativeUnit(au);
					}
				}

			}

			farm.setOwnerVillage(dto.getOwnerVillage());
			if (dto.getOwnerAdministrativeUnit() != null) {
				FmsAdministrativeUnitDto auDto = dto.getOwnerAdministrativeUnit();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if (au != null) {
						farm.setOwnerAdministrativeUnit(au);
					}
				} else if (auDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if (list != null && list.size() > 0) {
						au = list.get(0);
						farm.setOwnerAdministrativeUnit(au);
					}
				}

			} else {
				FmsAdministrativeUnit au = null;
				if (dto.getOwnerDistrictId() != null || dto.getOwnerDistrictCode() != null) {
					if (dto.getOwnerDistrictId() != null) {
						au = this.fmsAdministrativeUnitRepository.findOne(dto.getOwnerDistrictId());
						if (au != null) {
							farm.setOwnerAdministrativeUnit(au);
						}
					} else {
						if (dto.getOwnerDistrictCode() != null) {
							List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
									.findListByCode(dto.getOwnerDistrictCode());
							if (list != null && list.size() > 0) {
								au = list.get(0);
								farm.setOwnerAdministrativeUnit(au);
							}
						}
					}
				} else {
					if (dto.getOwnerProvinceId() != null || dto.getOwnerProvinceCode() != null) {
						if (dto.getOwnerProvinceId() != null) {
							au = this.fmsAdministrativeUnitRepository.findOne(dto.getOwnerProvinceId());
							if (au != null) {
								farm.setOwnerAdministrativeUnit(au);
							}
						} else {
							if (dto.getOwnerProvinceCode() != null) {
								List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
										.findListByCode(dto.getOwnerProvinceCode());
								if (list != null && list.size() > 0) {
									au = list.get(0);
									farm.setOwnerAdministrativeUnit(au);
								}
							}
						}
					} else {
						if (dto.getOwnerAdministrativeUnit() == null) {
							farm.setOwnerAdministrativeUnit(null);
						}
					}
				}
			}

			Set<FarmFileAttachment> farmList = new HashSet<FarmFileAttachment>();
			if (dto.getAttachments() != null && dto.getAttachments().size() > 0) {
				for (FarmFileAttachmentDto ffaDto : dto.getAttachments()) {
					if (ffaDto != null) {
						FarmFileAttachment ffa = null;
						if (ffaDto.getId() != null) {
							ffa = this.farmFileAttachmentRepository.findOne(ffaDto.getId());
						}
						if (ffa == null) {
							ffa = new FarmFileAttachment();
							ffa.setCreateDate(currentDate);
							ffa.setCreatedBy(currentUserName);
						}

						if (ffaDto.getFile() != null) {
							ffa.setFarm(farm);
							FileDescription file = null;
							if (ffaDto.getFile().getId() != null) {
								file = this.fileDescriptionRepository.findOne(ffaDto.getFile().getId());
							}
							if (file == null) {
								file = new FileDescription();
								file.setCreateDate(currentDate);
								file.setCreatedBy(currentUserName);
							}

							ffa.setFile(file);
							ffa = this.farmFileAttachmentRepository.save(ffa);
							farmList.add(ffa);
						}

					}
				}
				if (farmList != null && farmList.size() > 0) {
					if (farm.getAttachments() == null) {
						farm.setAttachments(farmList);
					} else {
						farm.getAttachments().clear();
						farm.getAttachments().addAll(farmList);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getAttachments() != null) {
					farm.getAttachments().clear();
				}
			}
			Set<AnimalReportData> animalReportDatas = new HashSet<AnimalReportData>();
//				if(dto.getAnimalReportDatas() != null && dto.getAnimalReportDatas().size() > 0) {
//					for(AnimalReportDataDto animalReportDataDto: dto.getAnimalReportDatas()) {
//						if(animalReportDataDto != null) {
//							AnimalReportData animalReportData = null;
//							if(animalReportDataDto.getId() != null) {
//								animalReportData = animalReportDataRepository.getOne(animalReportDataDto.getId());
//							}
//							if(animalReportData == null) {
//								animalReportData = new AnimalReportData();
//								animalReportData.setCreateDate(currentDate);
//								animalReportData.setCreatedBy(currentUserName);
//							}else {
//								animalReportData.setModifiedBy(currentUserName);
//								animalReportData.setModifyDate(currentDate);
//							}
//							Animal animal4ReportData = null;
//							if (animalReportDataDto.getAnimal() != null && animalReportDataDto.getAnimal().getId() != null) {
//								animal4ReportData = animalRepository.findOne(animalReportDataDto.getAnimal().getId());
//							}
//							animalReportData.setAnimal(animal4ReportData);
//
//							animalReportData.setFarm(farm);
//
//							animalReportData.setYear(animalReportDataDto.getYear());
//							animalReportData.setQuarter(animalReportDataDto.getQuarter());
//							animalReportData.setMonth(animalReportDataDto.getMonth());
//							animalReportData.setDay(animalReportDataDto.getMonth());
//
//							animalReportData.setFemale(animalReportDataDto.getFemale());
//							animalReportData.setMale(animalReportDataDto.getMale());
//							animalReportData.setUnGender(animalReportDataDto.getUnGender());
//							animalReportData.setFormId(animalReportDataDto.getFormId());
//							animalReportData.setFormType(animalReportDataDto.getFormType());
//							if(animalReportDataDto.getFarmReportPeriod()!=null && animalReportDataDto.getFarmReportPeriod().getId()!=null) {
//								FarmReportPeriod farmReportPeriod = farmReportPeriodRepository.findOne(animalReportDataDto.getFarmReportPeriod().getId());
//								animalReportData.setFarmReportPeriod(farmReportPeriod);
//							}
//							
//							Integer total = 0;
//							if (animalReportData.getFemale() != null && animalReportData.getFemale().intValue() > 0) {
//								total += animalReportData.getFemale().intValue();
//							}
//							if (animalReportData.getMale() != null && animalReportData.getMale().intValue() > 0) {
//								total += animalReportData.getMale().intValue();
//							}
//							if (animalReportData.getUnGender() != null && animalReportData.getUnGender().intValue() > 0) {
//								total += animalReportData.getUnGender().intValue();
//							}
//							animalReportData.setTotal(total);
//
//							ProductTarget productTarget = null;
//							if (animalReportDataDto.getProductTarget() != null && animalReportDataDto.getProductTarget().getId() != null) {
//								productTarget = productTargetRepository.findOne(animalReportDataDto.getProductTarget().getId());
//							}
//							animalReportData.setProductTarget(productTarget);
//							if (productTarget != null) {
//								animalReportData.setPurpose(productTarget.getCode());
//							}
//
//							Original original = null;
//							if (animalReportDataDto.getOriginal() != null && animalReportDataDto.getOriginal().getId() != null) {
//								original = originalRepository.findOne(animalReportDataDto.getOriginal().getId());
//							}
//							animalReportData.setOriginal(original);
//							if (original != null) {
//								animalReportData.setSource(original.getCode());
//							}
//							animalReportData.setType(animalReportDataDto.getType());
//							animalReportData.setRegistrationDate(animalReportDataDto.getRegistrationDate());
//							
//							//START
//							Set<IndividualAnimal> individualAnimals = new HashSet<IndividualAnimal>();
//							if (animalReportDataDto.getIndividualAnimals() != null && animalReportDataDto.getIndividualAnimals().size() > 0) {
//								animalReportData.setTotal(0);
//								animalReportData.setFemale(0);
//								animalReportData.setMale(0);
//								animalReportData.setUnGender(0);
//								for (IndividualAnimalDto individualAnimalDto : animalReportDataDto.getIndividualAnimals()) {
//									if (individualAnimalDto != null) {
//										IndividualAnimal individualAnimal = null;
//										if (individualAnimalDto.getId() != null) {
//											individualAnimal = individualAnimalRepository.getOne(individualAnimalDto.getId());
//										}
//										if(individualAnimal == null) {
//											individualAnimal = new IndividualAnimal();
//											individualAnimal.setCreateDate(currentDate);
//											individualAnimal.setCreatedBy(currentUserName);
//										}else {
//											individualAnimal.setModifiedBy(currentUserName);
//											individualAnimal.setModifyDate(currentDate);
//										}
//										individualAnimal.setAnimalReportData(animalReportData);
//										individualAnimal.setName(individualAnimalDto.getName());
//										individualAnimal.setCode(individualAnimalDto.getCode());
//										individualAnimal.setBirthDate(individualAnimalDto.getBirthDate());
//										individualAnimal.setAnimal(animal4ReportData);
//										individualAnimal.setStatus(individualAnimalDto.getStatus());
//										individualAnimal.setGender(individualAnimalDto.getGender());
//										individualAnimal.setDayOld(individualAnimalDto.getDayOld());
//										if(individualAnimal.getGender() == WLConstant.AnimalGender.Male.getValue()) {
//											animalReportData.setMale(animalReportData.getMale() + 1);
//											animalReportData.setTotal(animalReportData.getTotal() + 1);
//										}else if(individualAnimal.getGender() == WLConstant.AnimalGender.Female.getValue()) {
//											animalReportData.setFemale(animalReportData.getFemale() + 1);
//											animalReportData.setTotal(animalReportData.getTotal() + 1);
//										}else if(individualAnimal.getGender() != WLConstant.AnimalGender.UnGender.getValue()) {
//											animalReportData.setUnGender(animalReportData.getUnGender() + 1);
//											animalReportData.setTotal(animalReportData.getTotal() + 1);
//										}
//
//										individualAnimals.add(individualAnimal);
//									}
//								}
//							}
//							if(animalReportData.getIndividualAnimals() == null) {
//								animalReportData.setIndividualAnimals(individualAnimals);
//							}
//							else {
//								animalReportData.getIndividualAnimals().clear();
//								animalReportData.getIndividualAnimals().addAll(individualAnimals);
//							}
//							/// END Individual							
//							animalReportDatas.add(animalReportData);
//						}
//					}
//					if(farm.getAnimalReportDatas()==null) {
//						farm.setAnimalReportDatas(animalReportDatas);
//					}
//					else {
//						farm.getAnimalReportDatas().clear();
//						farm.getAnimalReportDatas().addAll(animalReportDatas);
//					}					
//				}
			Set<FarmHusbandryMethod> farmHusbandryMethods = new HashSet<FarmHusbandryMethod>();
			if (dto.getFarmHusbandryMethods() != null && dto.getFarmHusbandryMethods().size() > 0) {
				for (FarmHusbandryMethodDto farmHusbandryMethodDto : dto.getFarmHusbandryMethods()) {
					if (farmHusbandryMethodDto != null) {
						FarmHusbandryMethod farmHusbandryMethod = null;
						if (dto.getId() != null && farmHusbandryMethodDto != null
								&& farmHusbandryMethodDto.getHusbandryMethod() != null) {
							List<FarmHusbandryMethod> listFarmHusbandryMethods = farmHusbandryMethodRepository
									.findByFarmIdAndHusbandryMethodId(farm.getId(),
											farmHusbandryMethodDto.getHusbandryMethod().getId());
							if (listFarmHusbandryMethods != null && listFarmHusbandryMethods.size() > 0) {
								farmHusbandryMethod = listFarmHusbandryMethods.get(0);
							}
						}
						if (farmHusbandryMethod == null) {
							farmHusbandryMethod = new FarmHusbandryMethod();
							farmHusbandryMethod.setCreateDate(currentDate);
							farmHusbandryMethod.setCreatedBy(currentUserName);
						}
						farmHusbandryMethod.setFarm(farm);
						if (farmHusbandryMethodDto.getHusbandryMethod() != null
								&& farmHusbandryMethodDto.getHusbandryMethod().getId() != null) {
							HusbandryMethod husbandryMethod = husbandryMethodRepository
									.findOne(farmHusbandryMethodDto.getHusbandryMethod().getId());
							if (husbandryMethod != null) {
								farmHusbandryMethod.setHusbandryMethod(husbandryMethod);
								farmHusbandryMethods.add(farmHusbandryMethod);
							}
						}
					}
				}
			}
			if (farm.getFarmHusbandryMethods() == null) {
				farm.setFarmHusbandryMethods(farmHusbandryMethods);
			} else {
				farm.getFarmHusbandryMethods().clear();
				farm.getFarmHusbandryMethods().addAll(farmHusbandryMethods);
			}
			/** list stores */

			Set<FarmStore> stores = new HashSet<FarmStore>();
			if (dto.getStores() != null && dto.getStores().size() > 0) {
				for (FarmStoreDto farmStoreDto : dto.getStores()) {
					if (farmStoreDto != null) {
						FarmStore farmStore = null;
						if (farmStoreDto.getId() != null) {
							farmStore = this.farmStoreRepository.findOne(farmStoreDto.getId());
						}
						if (farmStore == null) {
							farmStore = new FarmStore();
							farmStore.setCreateDate(currentDate);
							farmStore.setCreatedBy(currentUserName);
						}
						farmStore.setCode(farmStoreDto.getCode());
						farmStore.setName(farmStoreDto.getName());
						farmStore.setAdress(farmStoreDto.getAdress());
						farmStore.setPhoneNumber(farmStoreDto.getPhoneNumber());

						if (farmStoreDto.getAdministrativeUnitDto() != null) {
							FmsAdministrativeUnit fau = null;
							if (farmStoreDto.getAdministrativeUnitDto().getId() != null) {
								fau = this.fmsAdministrativeUnitRepository
										.findOne(farmStoreDto.getAdministrativeUnitDto().getId());
							}
							if (fau == null) {
								fau = new FmsAdministrativeUnit();
								fau.setCreateDate(currentDate);
								fau.setCreatedBy(currentUserName);
							}
							fau.setCode(farmStoreDto.getAdministrativeUnitDto().getCode());
							fau.setLevel(farmStoreDto.getAdministrativeUnitDto().getLevel());
							fau.setName(farmStoreDto.getAdministrativeUnitDto().getName());

							farmStore.setAdministrativeUnit(fau);
						}
						farmStore.setFarm(farm);
						stores.add(farmStore);
					}
				}
				if (farm.getStores() == null) {
					farm.setStores(stores);
				} else {
					farm.getStores().clear();
					farm.getStores().addAll(stores);
				}
			}

			/** FarmAnimal */
			Set<FarmAnimal> farmAnimals = new HashSet<FarmAnimal>();
			if (dto.getFarmAnimals() != null && dto.getFarmAnimals().size() > 0) {
				for (AnimalDto farmAnimalDto : dto.getFarmAnimals()) {
					if (farmAnimalDto != null) {
						Animal animal = null;
						if (farmAnimalDto.getId() != null) {
							animal = animalRepository.findOne(farmAnimalDto.getId());
						} else if (farmAnimalDto.getCode() != null) {
							List<Animal> ans = animalRepository.findByCode(farmAnimalDto.getCode());
							if (ans != null && ans.size() > 0) {
								animal = ans.get(0);
							}
						}

						if (animal != null) {
							FarmAnimal fpt = new FarmAnimal();
							fpt.setFarm(farm);
							fpt.setAnimal(animal);
							farmAnimals.add(fpt);
						}
					}

				}
				if (farmAnimals != null && farmAnimals.size() > 0) {
					if (farm.getFarmAnimals() == null) {
						farm.setFarmAnimals(farmAnimals);
					} else {
						farm.getFarmAnimals().clear();
						farm.getFarmAnimals().addAll(farmAnimals);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmAnimals() != null) {
					farm.getFarmAnimals().clear();
				}
			}
			farm.setBusinessRegistrationDate(dto.getBusinessRegistrationDate());
			farm.setBusinessRegistrationNumber(dto.getBusinessRegistrationNumber());
			farm.setStatus(dto.getStatus());
			farm.setStatusFarm(dto.getStatusFarm());
			if (farm.getStatusFarm() == null || (farm.getStatusFarm() != null
					&& farm.getStatusFarm().equals(WLConstant.FarmStatus.active.getValue()))) {
				farm.setStopDate(null);
			} else {
				farm.setStopDate(dto.getStopDate());
			}
			/** FarmAnimalType */
			Set<FarmAnimalType> farmAnimalTypes = new HashSet<FarmAnimalType>();
			if (dto.getFarmAnimalTypes() != null && dto.getFarmAnimalTypes().size() > 0) {
				for (AnimalTypeDto farmAnimalTypeDto : dto.getFarmAnimalTypes()) {
					if (farmAnimalTypeDto != null) {
						AnimalType animalType = animalTypeRepository.findOne(farmAnimalTypeDto.getId());
						if (animalType != null) {
							FarmAnimalType fpt = new FarmAnimalType();
							fpt.setFarm(farm);
							fpt.setAnimalType(animalType);
							farmAnimalTypes.add(fpt);
						}
					}

				}
				if (farmAnimalTypes != null && farmAnimalTypes.size() > 0) {
					if (farm.getFarmAnimalTypes() == null) {
						farm.setFarmAnimalTypes(farmAnimalTypes);
					} else {
						farm.getFarmAnimalTypes().clear();
						farm.getFarmAnimalTypes().addAll(farmAnimalTypes);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmAnimalTypes() != null) {
					farm.getFarmAnimalTypes().clear();
				}
			}
			/////////// FarmCertificate
			Set<FarmCertificate> FarmCertificates = new HashSet<FarmCertificate>();
			if (dto.getFarmCertificates() != null && dto.getFarmCertificates().size() > 0) {
				for (CertificateDto farmCertificateDto : dto.getFarmCertificates()) {
					if (farmCertificateDto != null) {
						Certificate certificate = certificateRepository.findOne(farmCertificateDto.getId());
						if (certificate != null) {
							FarmCertificate fpt = new FarmCertificate();
							fpt.setFarm(farm);
							fpt.setCertificate(certificate);
							FarmCertificates.add(fpt);
						}
					}

				}
				if (FarmCertificates != null && FarmCertificates.size() > 0) {
					if (farm.getFarmCertificates() == null) {
						farm.setFarmCertificates(FarmCertificates);
					} else {
						farm.getFarmCertificates().clear();
						farm.getFarmCertificates().addAll(FarmCertificates);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmCertificates() != null) {
					farm.getFarmCertificates().clear();
				}
			}

			if (dto.getFarmSize() != null && dto.getFarmSize().getId() != null && dto.getFarmSize().getId() > 0) {
				FarmSize farmSize = this.farmSizeRepository.findOne(dto.getFarmSize().getId());
				if (farmSize != null) {
					farm.setFarmSize(farmSize);
				}
			}
//				System.out.println(farm.getCode());
			Farm farmTemp = this.farmRepository.save(farm);
			// khi thêm mới cơ sở tạo luôn tài khoản nông dân
			if (isNew == true) {
				if (farm.getCode() != null) {
					User user = null;
					user = userRepository.findByUsername(farm.getCode());
					if (user == null) {
						user = new User();
					}
					user.setUsername(farm.getCode());

					if (dto.getOwnerCitizenCode() != null) {
						String password = SecurityUtils.getHashPassword(dto.getOwnerCitizenCode());
						user.setPassword(password);
					} else {
						String password = SecurityUtils.getHashPassword("123456");
						user.setPassword(password);
					}
					if (dto.getOwnerEmail() != null) {
						user.setEmail(dto.getOwnerEmail());
					} else {
						user.setEmail(farm.getCode() + "@gmail.com");
					}
					user.setActive(true);
					HashSet<Role> temp = new HashSet<Role>();
					try {
						Role r = roleRepository.findByName(WLConstant.ROLE_FAMER);
						if (r != null) {
							temp.add(r);

						}

					} catch (Exception e) {
						// TODO: handle exception
					}

					if (user.getRoles() == null) {
						user.setRoles(temp);
					} else {
						user.getRoles().clear();
						user.getRoles().addAll(temp);
					}
					Person person = user.getPerson();
					if (person == null) {
						person = new Person();
					}
					if (dto.getName() != null) {
						person.setDisplayName(dto.getName());
					}

					person.setUser(user);
					user.setPerson(person);

					// Save
					user = userRepository.save(user);
					// save useradministrativeunit
					if (dto.getAdministrativeUnit() != null && user != null && user.getId() != null) {
						List<FmsAdministrativeUnitDto> listAdministrativeUnit = new ArrayList<FmsAdministrativeUnitDto>();
						listAdministrativeUnit.add(dto.getAdministrativeUnit());
						userAdministrativeUnitService.saveAdministrativeUnitByUserId(user.getId(),
								listAdministrativeUnit);
					}
				}

			}
			if (farmTemp.getId() != null) {
				dto.setId(farmTemp.getId());
			}

			try {
				if (isNew) {
					FarmMapServiceUtil.createFarmTolMap(farmTemp);
				} else {
					FarmMapServiceUtil.updateFarmToMap(farmTemp);
				}
				List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService
						.getAllAnimalLastReportedByRecordMonthDayIsNull(farmTemp.getId(), null);
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
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}

			return new FarmDto(farmTemp);
		}

		return null;
	}

	@Override
	public Farm updateFarmToEntity(Long id, FarmDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			boolean isNew = false;
			Farm farm = null;
			if (id != null) {
				farm = this.farmRepository.findOne(id);
			} else if (dto.getId() != null) {
				farm = this.farmRepository.findOne(dto.getId());
			}
			if (farm == null) {
				farm = new Farm();
				farm.setCreateDate(currentDate);
				farm.setCreatedBy(currentUserName);
				isNew = true;
			}
			farm.setName(dto.getName());
			if (isNew == true) {
				String code = null;
				if (dto.getDistrictCode() != null && dto.getDistrictId() != null && dto.getProvinceCode() != null) {
					code = autoGenericCode(dto.getDistrictCode(), dto.getDistrictId(), dto.getProvinceCode());
				} else if (dto.getAdministrativeUnit() != null) {
					String codeDis = null;
					String codeCity = null;
					Long districtId = null;
					FmsAdministrativeUnit ward = fmsAdministrativeUnitService
							.findByCode(dto.getAdministrativeUnit().getCode());

					if (ward != null && ward.getParent() != null) {
						districtId = ward.getParent().getId();
						codeDis = ward.getParent().getCode();
						if (ward.getParent().getParent() != null) {
							codeCity = ward.getParent().getParent().getCode();
							code = autoGenericCode(codeDis, districtId, codeCity);
						}
					}
				}

				if (code != null && code.length() > 0) {
					farm.setCode(code);
				} else {
					String log = "";
					if (dto.getAdministrativeUnit() != null) {
						log += dto.getAdministrativeUnit().getCode();
					} else {
						log = dto.getName();
					}
					System.out.println("AdministrativeUnit ERR:" + log);
					return null;
				}
			}
			farm.setOwnerVillage(dto.getOwnerVillage());
			if (dto.getOwnerAdministrativeUnit() != null) {
				FmsAdministrativeUnitDto auDto = dto.getOwnerAdministrativeUnit();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if (au != null) {
						farm.setOwnerAdministrativeUnit(au);
					}
				} else if (auDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if (list != null && list.size() > 0) {
						au = list.get(0);
						farm.setOwnerAdministrativeUnit(au);
					}
				}

			} else {
				FmsAdministrativeUnit au = null;
				if (dto.getOwnerDistrictId() != null || dto.getOwnerDistrictCode() != null) {
					if (dto.getOwnerDistrictId() != null) {
						au = this.fmsAdministrativeUnitRepository.findOne(dto.getOwnerDistrictId());
						if (au != null) {
							farm.setOwnerAdministrativeUnit(au);
						}
					} else {
						if (dto.getOwnerDistrictCode() != null) {
							List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
									.findListByCode(dto.getOwnerDistrictCode());
							if (list != null && list.size() > 0) {
								au = list.get(0);
								farm.setOwnerAdministrativeUnit(au);
							}
						}
					}
				} else {
					if (dto.getOwnerProvinceId() != null || dto.getOwnerProvinceCode() != null) {
						if (dto.getOwnerProvinceId() != null) {
							au = this.fmsAdministrativeUnitRepository.findOne(dto.getOwnerProvinceId());
							if (au != null) {
								farm.setOwnerAdministrativeUnit(au);
							}
						} else {
							if (dto.getOwnerProvinceCode() != null) {
								List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
										.findListByCode(dto.getOwnerProvinceCode());
								if (list != null && list.size() > 0) {
									au = list.get(0);
									farm.setOwnerAdministrativeUnit(au);
								}
							}
						}
					} else {
						if (dto.getOwnerAdministrativeUnit() == null) {
							farm.setOwnerAdministrativeUnit(null);
						}
					}
				}
			}
			/* New field fow WL */
			farm.setxVN2000(dto.getxVN2000());
			farm.setyVN2000(dto.getyVN2000());
			farm.setInterviewDate(dto.getInterviewDate());
			farm.setOwnerAge(dto.getOwnerAge());
			farm.setOwnerGender(dto.getOwnerGender());
			farm.setGcsAccuracy(dto.getGcsAccuracy());
			farm.setGcsElevation(dto.getGcsElevation());
			farm.setGcsLat(dto.getGcsLat());
			farm.setGcsLong(dto.getGcsLong());
			farm.setGcsZone(dto.getGcsZone());
			farm.setOldCode(dto.getOldCode());
			farm.setInterviewStartTime(dto.getInterviewStartTime());
			farm.setInterviewFinTime(dto.getInterviewFinTime());
			farm.setInterviewer(dto.getInterviewer());
			farm.setInName(dto.getInName());
			farm.setInAge(dto.getInAge());
			farm.setInGen(dto.getInGen());
			farm.setInTel(dto.getInTel());
			farm.setCaName(dto.getCaName());
			farm.setCaAge(dto.getCaAge());
			farm.setCaGen(dto.getCaGen());
			;
			farm.setType(dto.getType());
			farm.setYearRegistration(dto.getYearRegistration());
			farm.setMapCode(dto.getMapCode());
			if (dto.getYearRegistration() == null) {
				farm.setYearRegistration(new SimpleDateFormat("yyyy").format(new Date()));
			}
			/**/

			farm.setLongitude(dto.getLongitude());
			farm.setLatitude(dto.getLatitude());

			farm.setgMapX(dto.getgMapX());
			farm.setgMapY(dto.getgMapY());
			farm.setAdressDetail(dto.getAdressDetail());
			farm.setVillage(dto.getVillage());
			farm.setMediaLink(dto.getMediaLink());
			farm.setDescription(dto.getDescription());
			farm.setPhoneNumber(dto.getPhoneNumber());
			farm.setFax(dto.getFax());

			farm.setOwnerName(dto.getOwnerName());
			if (StringUtils.isEmpty(farm.getOwnerName())) {
				farm.setOwnerName(farm.getName());
			}
			farm.setOwnerPhoneNumber(dto.getOwnerPhoneNumber());
			farm.setOwnerCitizenCode(dto.getOwnerCitizenCode());
			farm.setOwnerEmail(dto.getOwnerEmail());
			farm.setOwnerAdress(dto.getOwnerAdress());
			farm.setOwnerDob(dto.getOwnerDob());

			farm.setVetStaffName(dto.getVetStaffName());
			farm.setVetStaffCitizenCode(dto.getVetStaffCitizenCode());
			farm.setVetStaffPhoneNumber(dto.getVetStaffPhoneNumber());
			farm.setVetStaffEmail(dto.getVetStaffEmail());
			farm.setVetStaffAdress(dto.getVetStaffAdress());

			farm.setReasonNotYetRegister(dto.getReasonNotYetRegister());
			farm.setNewRegistrationCode(dto.getNewRegistrationCode());
			farm.setOldRegistrationCode(dto.getOldRegistrationCode());
			farm.setIssuingCodeDate(dto.getIssuingCodeDate());
			farm.setFoundingDate(dto.getFoundingDate());

			farm.setTotalAcreage(dto.getTotalAcreage());
			farm.setLodgingAcreage(dto.getLodgingAcreage());
			farm.setMaxNumberOfAnimal(dto.getMaxNumberOfAnimal());
			farm.setSalanganeHouseType(dto.getSalanganeHouseType());
			farm.setSalanganeNestExploitQuantity(dto.getSalanganeNestExploitQuantity());
			farm.setMethodFeed(dto.getMethodFeed());
			// tran huu dat them cac thuoc tinh file excel moi
			farm.setOwnerCitizenDate(dto.getOwnerCitizenDate());
			farm.setOwnerCitizenLocation(dto.getOwnerCitizenLocation());
			farm.setApartmentNumber(dto.getApartmentNumber());
			farm.setStartDate(dto.getStartDate());
			farm.setTtbvmt(dto.getTtbvmt());
			farm.setTtbvmtDate(dto.getTtbvmtDate());
			farm.setDateRegistration(dto.getDateRegistration());// ngay cap gcn dk
			// tran huu dat them cac thuoc tinh file excel moi
			farm.setDateOtherRegistration(dto.getDateOtherRegistration());
			Ownership ownership = null;
			if (dto.getOwnership() != null && dto.getOwnership().getId() != null) {
				ownership = ownershipRepository.findOne(dto.getOwnership().getId());
			}
			farm.setOwnership(ownership);

			if (dto.getFarmProductTargets() != null && dto.getFarmProductTargets().size() > 0) {
				Set<FarmProductTarget> lst = new HashSet<FarmProductTarget>();
				for (ProductTargetDto fptDto : dto.getFarmProductTargets()) {
					if (fptDto != null) {
						ProductTarget pg = null;
						if (fptDto.getId() != null) {
							pg = productTargetRepository.findOne(fptDto.getId());
						} else if (fptDto.getCode() != null) {
							List<ProductTarget> list = productTargetRepository.findByCode(fptDto.getCode());
							if (list != null && list.size() > 0) {
								pg = list.get(0);
							}
						}

						if (pg != null) {
							FarmProductTarget fpt = new FarmProductTarget();
							fpt.setFarm(farm);
							fpt.setProductTarget(pg);
							lst.add(fpt);
						}
					}
				}
				if (lst.size() > 0) {
					if (farm.getFarmProductTargets() == null) {
						farm.setFarmProductTargets(lst);
					} else {
						farm.getFarmProductTargets().clear();
						farm.getFarmProductTargets().addAll(lst);
					}
				}
			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmProductTargets() != null) {
					farm.getFarmProductTargets().clear();
				}
			}
			if (dto.getHusbandryMethod() != null) {
				HusbandryMethod husbandryMethod = null;
				if (dto.getHusbandryMethod().getId() != null) {
					husbandryMethod = this.husbandryMethodRepository.findOne(dto.getHusbandryMethod().getId());
				}
				if (husbandryMethod == null) {
					husbandryMethod = new HusbandryMethod();
					husbandryMethod.setCreateDate(currentDate);
					husbandryMethod.setCreatedBy(currentUserName);
				}
				husbandryMethod.setCode(dto.getHusbandryMethod().getCode());
				husbandryMethod.setName(dto.getHusbandryMethod().getName());
				farm.setHusbandryMethod(husbandryMethod);
			}
			if (dto.getWaterResources() != null) {
				WaterSource waterSource = null;
				if (dto.getWaterResources().getId() != null) {
					waterSource = this.waterSourceRepository.findOne(dto.getWaterResources().getId());
				}
				if (waterSource == null) {
					waterSource = new WaterSource();
					waterSource.setCreateDate(currentDate);
					waterSource.setCreatedBy(currentUserName);
				}
				waterSource.setCode(dto.getWaterResources().getCode());
				waterSource.setName(dto.getWaterResources().getName());
				farm.setWaterResources(waterSource);
			}
			farm.setIsOutSourcing(dto.getIsOutSourcing());
			farm.setNumberOfLabor(dto.getNumberOfLabor());
			farm.setDistanceToResidentialArea(dto.getDistanceToResidentialArea());

			if (dto.getAdministrativeUnit() != null) {
				FmsAdministrativeUnitDto auDto = dto.getAdministrativeUnit();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if (au != null)
						farm.setAdministrativeUnit(au);
				} else if (auDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if (list != null && list.size() > 0) {
						au = list.get(0);
						farm.setAdministrativeUnit(au);
					}
				}

			}

			Set<FarmFileAttachment> farmList = new HashSet<FarmFileAttachment>();
			if (dto.getAttachments() != null && dto.getAttachments().size() > 0) {
				for (FarmFileAttachmentDto ffaDto : dto.getAttachments()) {
					if (ffaDto != null) {
						FarmFileAttachment ffa = null;
						if (ffaDto.getId() != null) {
							ffa = this.farmFileAttachmentRepository.findOne(ffaDto.getId());
						}
						if (ffa == null) {
							ffa = new FarmFileAttachment();
							ffa.setCreateDate(currentDate);
							ffa.setCreatedBy(currentUserName);
						}

						if (ffaDto.getFile() != null) {
							ffa.setFarm(farm);
							FileDescription file = null;
							if (ffaDto.getFile().getId() != null) {
								file = this.fileDescriptionRepository.findOne(ffaDto.getFile().getId());
							}
							if (file == null) {
								file = new FileDescription();
								file.setCreateDate(currentDate);
								file.setCreatedBy(currentUserName);
							}

							ffa.setFile(file);
							ffa = this.farmFileAttachmentRepository.save(ffa);
							farmList.add(ffa);
						}

					}
				}
				if (farmList != null && farmList.size() > 0) {
					if (farm.getAttachments() == null) {
						farm.setAttachments(farmList);
					} else {
						farm.getAttachments().clear();
						farm.getAttachments().addAll(farmList);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getAttachments() != null) {
					farm.getAttachments().clear();
				}
			}
			Set<AnimalReportData> animalReportDatas = new HashSet<AnimalReportData>();
			Set<FarmHusbandryMethod> farmHusbandryMethods = new HashSet<FarmHusbandryMethod>();
			if (dto.getFarmHusbandryMethods() != null && dto.getFarmHusbandryMethods().size() > 0) {
				for (FarmHusbandryMethodDto farmHusbandryMethodDto : dto.getFarmHusbandryMethods()) {
					if (farmHusbandryMethodDto != null) {
						FarmHusbandryMethod farmHusbandryMethod = null;
						if (dto.getId() != null && farmHusbandryMethodDto != null
								&& farmHusbandryMethodDto.getHusbandryMethod() != null) {
							List<FarmHusbandryMethod> listFarmHusbandryMethods = farmHusbandryMethodRepository
									.findByFarmIdAndHusbandryMethodId(farm.getId(),
											farmHusbandryMethodDto.getHusbandryMethod().getId());
							if (listFarmHusbandryMethods != null && listFarmHusbandryMethods.size() > 0) {
								farmHusbandryMethod = listFarmHusbandryMethods.get(0);
							}
						}
						if (farmHusbandryMethod == null) {
							farmHusbandryMethod = new FarmHusbandryMethod();
							farmHusbandryMethod.setCreateDate(currentDate);
							farmHusbandryMethod.setCreatedBy(currentUserName);
						}
						farmHusbandryMethod.setFarm(farm);
						if (farmHusbandryMethodDto.getHusbandryMethod() != null
								&& farmHusbandryMethodDto.getHusbandryMethod().getId() != null) {
							HusbandryMethod husbandryMethod = husbandryMethodRepository
									.findOne(farmHusbandryMethodDto.getHusbandryMethod().getId());
							if (husbandryMethod != null) {
								farmHusbandryMethod.setHusbandryMethod(husbandryMethod);
								farmHusbandryMethods.add(farmHusbandryMethod);
							}
						}
					}
				}
			}
			if (farm.getFarmHusbandryMethods() == null) {
				farm.setFarmHusbandryMethods(farmHusbandryMethods);
			} else {
				farm.getFarmHusbandryMethods().clear();
				farm.getFarmHusbandryMethods().addAll(farmHusbandryMethods);
			}
			/** list stores */

			Set<FarmStore> stores = new HashSet<FarmStore>();
			if (dto.getStores() != null && dto.getStores().size() > 0) {
				for (FarmStoreDto farmStoreDto : dto.getStores()) {
					if (farmStoreDto != null) {
						FarmStore farmStore = null;
						if (farmStoreDto.getId() != null) {
							farmStore = this.farmStoreRepository.findOne(farmStoreDto.getId());
						}
						if (farmStore == null) {
							farmStore = new FarmStore();
							farmStore.setCreateDate(currentDate);
							farmStore.setCreatedBy(currentUserName);
						}
						farmStore.setCode(farmStoreDto.getCode());
						farmStore.setName(farmStoreDto.getName());
						farmStore.setAdress(farmStoreDto.getAdress());
						farmStore.setPhoneNumber(farmStoreDto.getPhoneNumber());

						if (farmStoreDto.getAdministrativeUnitDto() != null) {
							FmsAdministrativeUnit fau = null;
							if (farmStoreDto.getAdministrativeUnitDto().getId() != null) {
								fau = this.fmsAdministrativeUnitRepository
										.findOne(farmStoreDto.getAdministrativeUnitDto().getId());
							}
							if (fau == null) {
								fau = new FmsAdministrativeUnit();
								fau.setCreateDate(currentDate);
								fau.setCreatedBy(currentUserName);
							}
							fau.setCode(farmStoreDto.getAdministrativeUnitDto().getCode());
							fau.setLevel(farmStoreDto.getAdministrativeUnitDto().getLevel());
							fau.setName(farmStoreDto.getAdministrativeUnitDto().getName());

							farmStore.setAdministrativeUnit(fau);
						}
						farmStore.setFarm(farm);
						stores.add(farmStore);
					}
				}
				if (farm.getStores() == null) {
					farm.setStores(stores);
				} else {
					farm.getStores().clear();
					farm.getStores().addAll(stores);
				}
			}

			/** FarmAnimal */
			Set<FarmAnimal> farmAnimals = new HashSet<FarmAnimal>();
			if (dto.getFarmAnimals() != null && dto.getFarmAnimals().size() > 0) {
				for (AnimalDto farmAnimalDto : dto.getFarmAnimals()) {
					if (farmAnimalDto != null) {
						Animal animal = null;
						if (farmAnimalDto.getId() != null) {
							animal = animalRepository.findOne(farmAnimalDto.getId());
						} else if (farmAnimalDto.getCode() != null) {
							List<Animal> ans = animalRepository.findByCode(farmAnimalDto.getCode());
							if (ans != null && ans.size() > 0) {
								animal = ans.get(0);
							}
						}

						if (animal != null) {
							FarmAnimal fpt = new FarmAnimal();
							fpt.setFarm(farm);
							fpt.setAnimal(animal);
							farmAnimals.add(fpt);
						}
					}

				}
				if (farmAnimals != null && farmAnimals.size() > 0) {
					if (farm.getFarmAnimals() == null) {
						farm.setFarmAnimals(farmAnimals);
					} else {
						farm.getFarmAnimals().clear();
						farm.getFarmAnimals().addAll(farmAnimals);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmAnimals() != null) {
					farm.getFarmAnimals().clear();
				}
			}
			farm.setBusinessRegistrationDate(dto.getBusinessRegistrationDate());
			farm.setBusinessRegistrationNumber(dto.getBusinessRegistrationNumber());
			farm.setStatus(dto.getStatus());
			farm.setStatusFarm(dto.getStatusFarm());
			if (farm.getStatusFarm() == null || (farm.getStatusFarm() != null
					&& farm.getStatusFarm().equals(WLConstant.FarmStatus.active.getValue()))) {
				farm.setStopDate(null);
			} else {
				farm.setStopDate(dto.getStopDate());
			}
			/** FarmAnimalType */
			Set<FarmAnimalType> farmAnimalTypes = new HashSet<FarmAnimalType>();
			if (dto.getFarmAnimalTypes() != null && dto.getFarmAnimalTypes().size() > 0) {
				for (AnimalTypeDto farmAnimalTypeDto : dto.getFarmAnimalTypes()) {
					if (farmAnimalTypeDto != null) {
						AnimalType animalType = animalTypeRepository.findOne(farmAnimalTypeDto.getId());
						if (animalType != null) {
							FarmAnimalType fpt = new FarmAnimalType();
							fpt.setFarm(farm);
							fpt.setAnimalType(animalType);
							farmAnimalTypes.add(fpt);
						}
					}

				}
				if (farmAnimalTypes != null && farmAnimalTypes.size() > 0) {
					if (farm.getFarmAnimalTypes() == null) {
						farm.setFarmAnimalTypes(farmAnimalTypes);
					} else {
						farm.getFarmAnimalTypes().clear();
						farm.getFarmAnimalTypes().addAll(farmAnimalTypes);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmAnimalTypes() != null) {
					farm.getFarmAnimalTypes().clear();
				}
			}
			/////////// FarmCertificate
			Set<FarmCertificate> FarmCertificates = new HashSet<FarmCertificate>();
			if (dto.getFarmCertificates() != null && dto.getFarmCertificates().size() > 0) {
				for (CertificateDto farmCertificateDto : dto.getFarmCertificates()) {
					if (farmCertificateDto != null) {
						Certificate certificate = certificateRepository.findOne(farmCertificateDto.getId());
						if (certificate != null) {
							FarmCertificate fpt = new FarmCertificate();
							fpt.setFarm(farm);
							fpt.setCertificate(certificate);
							FarmCertificates.add(fpt);
						}
					}

				}
				if (FarmCertificates != null && FarmCertificates.size() > 0) {
					if (farm.getFarmCertificates() == null) {
						farm.setFarmCertificates(FarmCertificates);
					} else {
						farm.getFarmCertificates().clear();
						farm.getFarmCertificates().addAll(FarmCertificates);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmCertificates() != null) {
					farm.getFarmCertificates().clear();
				}
			}

			if (dto.getFarmSize() != null && dto.getFarmSize().getId() != null && dto.getFarmSize().getId() > 0) {
				FarmSize farmSize = this.farmSizeRepository.findOne(dto.getFarmSize().getId());
				if (farmSize != null) {
					farm.setFarmSize(farmSize);
				}
			}
//				System.out.println(farm.getCode());
			Farm farmTemp = this.farmRepository.save(farm);
			// khi thêm mới cơ sở tạo luôn tài khoản nông dân
			if (isNew == true) {
				if (farm.getCode() != null) {
					User user = null;
					user = userRepository.findByUsername(farm.getCode());
					if (user == null) {
						user = new User();
					}
					user.setUsername(farm.getCode());

					if (dto.getOwnerCitizenCode() != null) {
						String password = SecurityUtils.getHashPassword(dto.getOwnerCitizenCode());
						user.setPassword(password);
					} else {
						String password = SecurityUtils.getHashPassword("123456");
						user.setPassword(password);
					}
					if (dto.getOwnerEmail() != null) {
						user.setEmail(dto.getOwnerEmail());
					} else {
						user.setEmail(farm.getCode() + "@gmail.com");
					}
					user.setActive(true);
					HashSet<Role> temp = new HashSet<Role>();
					try {
						Role r = roleRepository.findByName(WLConstant.ROLE_FAMER);
						if (r != null) {
							temp.add(r);

						}

					} catch (Exception e) {
						// TODO: handle exception
					}

					if (user.getRoles() == null) {
						user.setRoles(temp);
					} else {
						user.getRoles().clear();
						user.getRoles().addAll(temp);
					}
					Person person = user.getPerson();
					if (person == null) {
						person = new Person();
					}
					if (dto.getName() != null) {
						person.setDisplayName(dto.getName());
					}

					person.setUser(user);
					user.setPerson(person);

					// Save
					user = userRepository.save(user);
					// save useradministrativeunit
					if (dto.getAdministrativeUnit() != null && user != null && user.getId() != null) {
						List<FmsAdministrativeUnitDto> listAdministrativeUnit = new ArrayList<FmsAdministrativeUnitDto>();
						listAdministrativeUnit.add(dto.getAdministrativeUnit());
						userAdministrativeUnitService.saveAdministrativeUnitByUserId(user.getId(),
								listAdministrativeUnit);
					}
				}

			}
			if (farmTemp.getId() != null) {
				dto.setId(farmTemp.getId());
			}

			try {
				if (isNew) {
					FarmMapServiceUtil.createFarmTolMap(farmTemp);
				} else {
					FarmMapServiceUtil.updateFarmToMap(farmTemp);
				}
				List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService
						.getAllAnimalLastReportedByRecordMonthDayIsNull(farmTemp.getId(), null);
				if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
					for (FarmAnimalTotalDto farmAnimalTotalDto : listAnimalReportTotal) {
						if (farmAnimalTotalDto != null) {
							try {
								FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
							} catch (Exception e) {
								// TODO: handle exception
//									e.printStackTrace();
								System.out.println("Lỗi khi đồng bộ bản đồ cơ sở:" + farmTemp.getCode());
								continue;
							}
						}
					}
				}
			} catch (Exception e) {
//					e.printStackTrace();
				System.out.println("Lỗi khi đồng bộ bản đồ cơ sở:" + farmTemp.getCode());
				// TODO: handle exception
			}

			return (farmTemp);
		}

		return null;
	}

	@Override
	public FarmDto createFarm(FarmDto dto) {
		return updateFarm(null, dto);
	}

	@Override
	public FarmDto reGenerateFarmCode(Long farmId) {
		Farm f = this.farmRepository.getOne(farmId);
		if (f != null && f.getAdministrativeUnit() != null && f.getAdministrativeUnit().getParent() != null
				&& f.getAdministrativeUnit().getParent().getParent() != null) {
			String oldCode = f.getCode();
			String newCode = this.autoGenericCode(f.getAdministrativeUnit().getParent().getCode(),
					f.getAdministrativeUnit().getParent().getId(),
					f.getAdministrativeUnit().getParent().getParent().getCode());
			f.setOldSystemCode(oldCode);
			f.setCode(newCode);
			f = farmRepository.save(f);
//			try {
//				FarmMapServiceUtil.createFarmTolMap(f);
//			} catch (JSONException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//Đồng bộ trại với mã mới sang
//			List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByRecordMonthDayIsNull(f.getId(), null);
////			Set<Integer> years = new HashSet<Integer>();
//			if(listAnimalReportTotal!=null && listAnimalReportTotal.size()>0) {
//				for (FarmAnimalTotalDto ard : listAnimalReportTotal) {
//					try {
//						FarmMapServiceUtil.pushFarmAnimalMap(ard);
//					} catch (Exception e) {
//						//TODO: handle exception
//						e.printStackTrace();
//						continue;
//					}
////					years.add(ard.getYear());
//				}
//			}
//			//Xóa trại với mã cũ
//			FarmMapDeleteDto farmMapDelete = new FarmMapDeleteDto();
//			farmMapDelete.setCode(oldCode);
//			Integer year = 0;
//			try {
//				year = Integer.parseInt(f.getYearRegistration());
//			} catch (Exception e) {
//				year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
//			}
//			farmMapDelete.setYear(year);
//			farmMapDelete.setYear(2020);
//			FarmMapServiceUtil.deleteFarmMap(farmMapDelete);			
//
//			List<Farm> lst= this.farmRepository.findByCode(oldCode);
//			if(lst!=null) {
//				for (Farm farm : lst) {
//					//Xóa trại trùng mã cũ
//					farmMapDelete = new FarmMapDeleteDto();
//					farmMapDelete.setCode(farm.getCode());
//					year = 0;
//					try {
//						year = Integer.parseInt(farm.getYearRegistration());
//					} catch (Exception e) {
//						year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
//					}
//					farmMapDelete.setYear(year);
//					FarmMapServiceUtil.deleteFarmMap(farmMapDelete);
//					
//					//Đồng bộ lại trại hiện có
//					try {
//						FarmMapServiceUtil.createFarmTolMap(farm);
//					} catch (JSONException | IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByRecordMonthDayIsNull(farm.getId(), null);
////					Set<Integer> years = new HashSet<Integer>();
//					if(listAnimalReportTotal!=null && listAnimalReportTotal.size()>0) {
//						for (FarmAnimalTotalDto ard : listAnimalReportTotal) {
//							try {
//								FarmMapServiceUtil.pushFarmAnimalMap(ard);
//							} catch (Exception e) {
//								//TODO: handle exception
//								e.printStackTrace();
//								continue;
//							}
////							years.add(ard.getYear());
//						}
//					}
//				}
//			}

			return new FarmDto(f);
		}
		return null;
	}

	@Override
	public FarmDto deleteById(Long id) {
		if (id != null) {
			Farm farm = farmRepository.findOne(id);
			if (farm != null) {

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
				List<Long> listAdministrativeUnitId = userAdministrativeUnitService
						.getAdministrativeUnitIdByLoginUser();
				if (!isAdmin) {
					if (farm.getAdministrativeUnit() == null || farm.getAdministrativeUnit().getParent() == null
							|| farm.getAdministrativeUnit().getParent().getParent() == null) {
						return null;
					}
					if (isAdministrativeUnitRole) {
						if (SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH)) {
							if (farm.getAdministrativeUnit().getParent() != null
									&& farm.getAdministrativeUnit().getParent().getParent() != null) {
								if (!listAdministrativeUnitId
										.contains(farm.getAdministrativeUnit().getParent().getParent().getId())) {
									return null;
								}
							}
						}
					} else if (isFarmerRole) {
						return null;
					}
				}

				List<ImportExportAnimal> list = importExportAnimalRepository.findByFarm(id);
				if (list != null && list.size() > 0) {
					FarmDto dto = new FarmDto();
					dto.setCode("-1");
					return dto;
				} else {
					Farm f = farmRepository.findOne(id);
					List<FarmProductTargetExist> listProductExist = farmProductTargetExistRepository.getByFarm(id);
					if (listProductExist != null) {
						farmProductTargetExistRepository.delete(listProductExist);
					}
					List<FarmAnimalProductTargetExist> listFarmAnimalProductTargetExist = farmAnimalProductTargetExistRepository
							.getByFarm(id);
					if (listFarmAnimalProductTargetExist != null) {
						farmAnimalProductTargetExistRepository.delete(listFarmAnimalProductTargetExist);
					}
					if (f.getFarmProductTargets() != null) {
						f.getFarmProductTargets().clear();
					}
					if (f.getFarmAnimalTypes() != null) {
						f.getFarmAnimalTypes().clear();
					}
					if (f.getFarmAnimals() != null) {
						f.getFarmAnimals().clear();
					}
					if (f.getAttachments() != null) {
						f.getAttachments().clear();
					}
					if (f.getFarmCertificates() != null) {
						f.getFarmCertificates().clear();
					}
					f = this.farmRepository.save(f);
					this.farmRepository.delete(id);
					User user = userRepository.findByUsername(farm.getCode());
					if (user != null)
						fmsUserService.deleteById(user.getId());

					FarmDto farmDto = new FarmDto(f, false);
					FarmMapDeleteDto farmMapDelete = new FarmMapDeleteDto();
					farmMapDelete.setCode(farmDto.getCode());
					Integer year = 0;
					try {
						year = Integer.parseInt(farmDto.getYearRegistration());
					} catch (Exception e) {
						year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
					}
					farmMapDelete.setYear(year);
//					try {
					FarmMapServiceUtil.deleteFarmMap(farmMapDelete);
//					} catch (ClientProtocolException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (JSONException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
				}
			}
		}
		return null;
	}

	@Override
	public FarmDto getById(Long id) {
		if (id != null) {
			Farm farm = this.farmRepository.getById(id);
			if (farm != null) {
				return new FarmDto(farm);
			}
		}
		return null;
	}

	@Override
	public List<AnimalDto> removeListByParent(Long parentId, List<AnimalDto> list) {
		List<AnimalDto> animals = new ArrayList<AnimalDto>();
		Animal ani = animalRepository.findOne(parentId);
		if (ani != null && ani.getId() != null && list != null && list.size() > 0) {
			for (AnimalDto animalDto : list) {
				if (animalDto != null && animalDto.getParent() != null && animalDto.getParent().getId() != null
						&& animalDto.getParent().getId() != ani.getId()) {
					animals.add(animalDto);
				}
			}
		}
		return animals;
	}

	@Override
	public Page<FarmDto> searchFarmDto(FarmSearchDto searchDto, int pageIndex, int pageSize) {

		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		String animalclause = "";
		String animalTypeclause = "";
		String fileclause = "";
		String targetclause = "";
		String producTargetExistclause = "";
		String animalProducTargetExistclause = "";
		String certificateclause = "";
		String namecode = "";

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
		boolean isAdministrativeUnitRoleView = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH_VIEW)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DISTRICT)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_WARD);
		boolean isFarmerRole = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_FAMER);
		List<Long> fmsAdministrativeUnitIds = new ArrayList<Long>();
		if (!isAdmin && isAdministrativeUnitRole) {
			List<FmsAdministrativeUnit> list = userAdministrativeUnitService.getAdministrativeUnitByLoginUser();
			for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
				if (fmsAdministrativeUnit != null)
					fmsAdministrativeUnitIds.add(fmsAdministrativeUnit.getId());
			}
			if (list != null && list.size() > 0) {
				for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
					if (fmsAdministrativeUnit.getParent() == null) {// Cấp tỉnh
						searchDto.setProvince(fmsAdministrativeUnit.getId());
					} else if (fmsAdministrativeUnit.getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent() == null) {// Cấp huyện
						if (!fmsAdministrativeUnitIds.contains(searchDto.getDistrict()))
							searchDto.setDistrict(fmsAdministrativeUnit.getId());
					} else if (fmsAdministrativeUnit.getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent().getParent() == null) {// Cấp xã
						searchDto.setWard(fmsAdministrativeUnit.getId());
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
		if (!isAdmin && isAdministrativeUnitRoleView) {
			List<FmsAdministrativeUnit> list = userAdministrativeUnitService.getAdministrativeUnitByLoginUser();
			for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
				if (fmsAdministrativeUnit != null)
					fmsAdministrativeUnitIds.add(fmsAdministrativeUnit.getId());
			}
			if (list != null && list.size() > 0) {
				for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
					if (fmsAdministrativeUnit.getParent() == null) {// Cấp tỉnh
						searchDto.setProvince(fmsAdministrativeUnit.getId());
					} else if (fmsAdministrativeUnit.getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent() == null) {// Cấp huyện
						if (!fmsAdministrativeUnitIds.contains(searchDto.getDistrict()))
							searchDto.setDistrict(fmsAdministrativeUnit.getId());
					} else if (fmsAdministrativeUnit.getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent().getParent() == null) {// Cấp xã
						searchDto.setWard(fmsAdministrativeUnit.getId());
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
		if (searchDto != null && searchDto.getNameOrCode() != null) {
			namecode = searchDto.getNameOrCode();
		}

		if (searchDto != null && searchDto.getAnimalParentId() != null) {
			animalclause += " inner join FarmAnimal a on fa.id=a.farm.id ";
		}
		if (searchDto != null && searchDto.getAnimalTypeId() != null) {
			animalTypeclause += " inner join FarmAnimalType fat on fa.id=fat.farm.id ";
		}
		if (searchDto != null && searchDto.getFileId() != null) {
			fileclause += " inner join FarmFileAttachment ffa on fa.id=ffa.farm.id ";
		}
//		if(searchDto.getProductTargetId()!=null){
//			targetclause +=" inner join FarmProductTarget fpt on fa.id=fpt.farm.id ";
//		}
		if (searchDto != null
				&& ((searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0)
						|| (searchDto.getToBalanceNumber() != null && searchDto.getToBalanceNumber() > 0))
				&& (searchDto != null && searchDto.getProductTargetId() != null && searchDto.getAnimalId() == null)) {
			producTargetExistclause += " inner join FarmProductTargetExist fpte on fa.id=fpte.farm.id ";
		}
		if (searchDto != null
				&& ((searchDto != null && searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0)
						|| (searchDto.getToBalanceNumber() != null && searchDto.getToBalanceNumber() > 0))
				&& (searchDto != null && searchDto.getProductTargetId() != null && searchDto.getAnimalId() != null)) {
			animalProducTargetExistclause += " inner join FarmAnimalProductTargetExist fapte on fa.id=fapte.farm.id ";
		}
		if (searchDto != null && searchDto.getCertificateId() != null && searchDto.getCertificateId() > 0) {
			certificateclause += " inner join FarmCertificate fc on fa.id=fc.farm.id ";
		}

		String sql = " select  new com.globits.wl.dto.FarmDto(fa,true) from Farm fa " + animalclause + animalTypeclause
				+ fileclause + targetclause + producTargetExistclause + animalProducTargetExistclause
				+ certificateclause
				+ "where fa.name is not null and (fa.isDuplicate is null or fa.isDuplicate <> true) ";
		String sqlCount = "select count(fa.id) from Farm fa" + animalclause + animalTypeclause + fileclause
				+ targetclause + producTargetExistclause + animalProducTargetExistclause + certificateclause
				+ " where fa.name is not null and (fa.isDuplicate is null or fa.isDuplicate <> true) ";
		String whereClause = "";

		if (namecode != null && namecode.length() > 0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode or fa.ownerName like :namecode or fa.mapCode like :namecode)";
		}
		if (searchDto != null) {
			if (searchDto.getWard() != null) {
				whereClause += " and fa.administrativeUnit.id = :wardId";
			}
			if (searchDto.getDistrict() != null) {
				if (searchDto.getShowListFarmSelect() != null && searchDto.getShowListFarmSelect() == true && !isAdmin
						&& isAdministrativeUnitRole && fmsAdministrativeUnitIds.size() > 0) {
					whereClause += " and fa.administrativeUnit.parent.id in :districtIds";
				} else {
					whereClause += " and fa.administrativeUnit.parent.id = :districtId";
				}

			}
			if (searchDto.getProvince() != null) {
				whereClause += " and fa.administrativeUnit.parent.parent.id  = :provinceId";
			}
			if (searchDto.getRegion() != null) {
				whereClause += " and fa.administrativeUnit.parent.parent.region.id = :regionId";
			}
			if (searchDto.getAnimalParentId() != null) {
				whereClause += " and a.animal.id = :animalParentId";
			}
			if (searchDto.getAnimalTypeId() != null) {
				whereClause += " and fat.animalType.id = :animalTypeId";
			}
			if (searchDto.getFileId() != null) {
				whereClause += " and ffa.file.id = :fileId";
			}
			if (searchDto.getHusbandryMethodId() != null) {
				whereClause += " and fa.husbandryMethod.id = :husbandryMethodId";
			}
			if (searchDto.getLodgingAcreage() != null && searchDto.getLodgingAcreage() > 0) {
				whereClause += " and fa.lodgingAcreage = :lodgingAcreage";
			}
			if (searchDto.getMaxNumberOfAnimal() != null && searchDto.getMaxNumberOfAnimal() > 0) {
				whereClause += " and fa.maxNumberOfAnimal = :maxNumberOfAnimal";
			}
			if (searchDto.getOwnerName() != null && searchDto.getOwnerName().length() > 0) {
				whereClause += " and (fa.ownerName like :ownerName) ";
			}
			// if(searchDto.getProductTargetId()!=null){
			// whereClause += " and fpt.productTarget.id = :productTargetId";
			// }
			if (searchDto.getTotalAcreage() != null && searchDto.getTotalAcreage() > 0) {
				whereClause += " and fa.totalAcreage = :totalAcreage";
			}
			if (searchDto.getVetStaffName() != null && searchDto.getVetStaffName().length() > 0) {
				whereClause += " and (fa.vetStaffName like :vetStaffName) ";
			}
			if (searchDto.getWaterResourceId() != null) {
				whereClause += " and fa.waterResources.id = :waterResourceId";
			}
			if (searchDto.getAdressDetail() != null && searchDto.getAdressDetail().length() > 0) {
				whereClause += " and fa.adressDetail like :adressDetail";
			}
			if (searchDto.getStatus() != null) {
				whereClause += " and fa.status = :status";
			}
			if (searchDto.getPhoneNumber() != null && searchDto.getPhoneNumber().length() > 0) {
				whereClause += " and fa.phoneNumber = :phoneNumber";
			}
			if (searchDto.getBusinessRegistrationDate() != null) {
				whereClause += " and fa.businessRegistrationDate = :businessRegistrationDate";
			}
			if (searchDto.getBusinessRegistrationNumber() != null
					&& searchDto.getBusinessRegistrationNumber().length() > 0) {
				whereClause += " and fa.businessRegistrationNumber = :businessRegistrationNumber";
			}
			if (searchDto.getDistanceToResidentialArea() != null && searchDto.getDistanceToResidentialArea() > 0) {
				whereClause += " and fa.distanceToResidentialArea = :distanceToResidentialArea";
			}
			if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
					&& searchDto.getAnimalId() == null && searchDto.getProductTargetId() == null) {
				whereClause += " and fa.balanceNumber >= :balanceNumber";
			}
			if (searchDto.getToBalanceNumber() != null && searchDto.getToBalanceNumber() > 0
					&& searchDto.getAnimalId() == null && searchDto.getProductTargetId() == null) {
				whereClause += " and fa.balanceNumber <= :toBalanceNumber";
			}

			if (searchDto.getFax() != null && searchDto.getFax().length() > 0) {
				whereClause += " and fa.fax = :fax";
			}
			if (searchDto.getLatitude() != null && searchDto.getLatitude().length() > 0) {
				whereClause += " and fa.latitude = :latitude";
			}
			if (searchDto.getLongitude() != null && searchDto.getLongitude().length() > 0) {
				whereClause += " and fa.longitude = :longitude";
			}
			if (searchDto.getOwnerCitizenCode() != null && searchDto.getOwnerCitizenCode().length() > 0) {
				whereClause += " and fa.ownerCitizenCode = :ownerCitizenCode";
			}
			if (searchDto.getVetStaffCitizenCode() != null && searchDto.getVetStaffCitizenCode().length() > 0) {
				whereClause += " and fa.vetStaffCitizenCode = :vetStaffCitizenCode";
			}
			if (searchDto.getIsOutSourcing() != null && searchDto.getIsOutSourcing()) {
				whereClause += " and fa.isOutSourcing = :isOutSourcing";
			}
			if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
					&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() == null) {
				whereClause += "  and fpte.quantity >= :productTargetExist and fpte.productTarget.id="
						+ searchDto.getProductTargetId();
			}
			if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
					&& searchDto.getAnimalId() != null && searchDto.getProductTargetId() != null) {
				whereClause += "  and fapte.quantity >= :animalProductTargetExist and fapte.productTarget.id="
						+ searchDto.getProductTargetId() + "  and fapte.animal.id=" + searchDto.getAnimalId();
			}

			if (searchDto.getToBalanceNumber() != null && searchDto.getToBalanceNumber() > 0
					&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() == null) {
				whereClause += "  and fpte.quantity <= :productTargetExistTo and fpte.productTarget.id="
						+ searchDto.getProductTargetId();
			}
			if (searchDto.getToBalanceNumber() != null && searchDto.getToBalanceNumber() > 0
					&& searchDto.getAnimalId() != null && searchDto.getProductTargetId() != null) {
				whereClause += "  and fapte.quantity <= :animalProductTargetExistTo and fapte.productTarget.id="
						+ searchDto.getProductTargetId() + "  and fapte.animal.id=" + searchDto.getAnimalId();
			}
			if (searchDto.getCreatedBy() != null) {
				whereClause += "  and fa.createdBy like :createdBy";
			}
			if (searchDto.getModifiedBy() != null) {
				whereClause += "  and fa.modifiedBy like :modifiedBy";
			}
			if (searchDto.getCreateDateFrom() != null) {
				whereClause += "  and fa.createDate >= :createDateFrom";
			}
			if (searchDto.getCreateDateTo() != null) {
				whereClause += "  and fa.createDate <= :createDateTo";
			}

			if (searchDto.getModifiedDateFrom() != null) {
				whereClause += "  and fa.modifyDate >= :modifiedDateFrom";
			}
			if (searchDto.getModifiedDateTo() != null) {
				whereClause += "  and fa.modifyDate <= :modifiedDateTo";
			}

			if (searchDto.getCertificateId() != null && searchDto.getCertificateId() > 0) {
				whereClause += " and  fc.certificate.id= :certificateId ";
			}
			if (searchDto.getOwnership() != null && searchDto.getOwnership().getId() != null) {
				whereClause += " and  fa.ownership is not null and fa.ownership.id= :ownershipId ";
			}
			if (searchDto.getYearRegistration() != null) {
				String operatorCompare = ">";
				if (searchDto.getCheckQuantityLessThanZero() != null && searchDto.getCheckQuantityLessThanZero()) {
					operatorCompare = "<=";
				}
				String subWherePeriod = "";
				String subWhereArd = "";
				if (searchDto.getProvince() != null && searchDto.getProvince() > 0L) {
					subWherePeriod += " AND rp.province.id=:provinceId ";
					subWhereArd += " AND ard.province.id=:provinceId ";
				}
				if (searchDto.getDistrict() != null) {
					if (searchDto.getShowListFarmSelect() != null && searchDto.getShowListFarmSelect() == true
							&& !isAdmin && isAdministrativeUnitRole && fmsAdministrativeUnitIds.size() > 0) {
						subWherePeriod += " AND rp.district.id in :districtIds ";
						subWhereArd += " AND ard.district.id in :districtIds ";
					} else {
						subWherePeriod += " AND rp.district.id = :districtId ";
						subWhereArd += " AND ard.district.id = :districtId ";
					}
				}
				if (searchDto.getWard() != null) {
					subWherePeriod += " AND rp.administrativeUnit.id = :wardId ";
					subWhereArd += " AND ard.administrativeUnit.id = :wardId ";
				}

				whereClause += " and fa.id in ( SELECT distinct(rp.farm.id) FROM ReportPeriod rp WHERE rp.year=:yearRegistration "
						+ subWherePeriod + " ) ";
				// + " AND fa.id in (SELECT distinct(ard.farm.id) FROM AnimalReportData ard
				// WHERE ard.year=:yearRegistration AND ard.month is null AND ard.day is null
				// GROUP BY ard.farm.id having SUM(ard.total) "+operatorCompare+" 0) ";//>0 )

			}
			//status fram
			//ko hd có chọn năm 
			if (searchDto.getStatusFarm() != null && searchDto.getStatusFarm() == 1 && searchDto.getYear()>0) {
				whereClause += " AND fa.id=farm_id in(select farm_id from (\r\n" + "select \r\n"
						+ "ROW_NUMBER ( )   \r\n" + "    OVER ( PARTITION BY p.farm_id,p.animal_id \r\n"
						+ "	order by p.date_report desc,p.modify_date desc) as ranks\r\n"
						+ "	,p.farm_id,p.animal_id  ,p.male,p.female,p.un_gender ,p.date_report,p.modify_date,\r\n"
						+ "	(p.male+p.female+p.un_gender) as quantity\r\n" + "\r\n" + "from tbl_report_form16 p\r\n"
						+ "inner join tbl_report_period rp on p.report_period_id=rp.id\r\n" + "where rp.year=:year\r\n"
						+ ") as tb1\r\n" + "where tb1.ranks=1 and tb1.quantity=0)";
			}
		//	ko hd ko chọn năm
			if(searchDto.getStatusFarm()!=null&& searchDto.getStatusFarm()==1) {
				whereClause +="AND fa.statusFarm=1";
			}
			//ko chọn trạng thái || tt có hd
//			if (searchDto.getStatusFarm()==null) {
//				whereClause += "AND fa.statusFarm=0";
//			}else if(searchDto.getStatusFarm() != null && searchDto.getStatusFarm() == 0) {
//				whereClause += "AND fa.statusFarm=0";
//			}
		}
		sql += whereClause;
		if (searchDto.getCheckSort() != null && searchDto.getCheckSort() == true) {
			sql += " order by fa.name asc, fa.administrativeUnit.parent.parent.name asc, fa.administrativeUnit.parent.name asc, fa.administrativeUnit.name asc,  fa.yearRegistration asc, fa.createDate asc, fa.modifyDate asc";
		}
		if (searchDto.getCheckSort() != null && searchDto.getCheckSort() == false) {
			sql += " order by fa.administrativeUnit.parent.parent.name asc, fa.administrativeUnit.parent.name asc, fa.administrativeUnit.name asc, fa.name asc, fa.yearRegistration asc, fa.createDate asc, fa.modifyDate asc";
		}

		sqlCount += whereClause;

		Query q = manager.createQuery(sql, FarmDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (namecode != null && namecode.length() > 0) {
			q.setParameter("namecode", '%' + namecode + '%');
			qCount.setParameter("namecode", '%' + namecode + '%');
		}
		if (searchDto != null) {
			if (searchDto.getWard() != null) {
				q.setParameter("wardId", searchDto.getWard());
				qCount.setParameter("wardId", searchDto.getWard());
			}
			if (searchDto.getDistrict() != null) {
				if (searchDto.getShowListFarmSelect() != null && searchDto.getShowListFarmSelect() == true && !isAdmin
						&& isAdministrativeUnitRole && fmsAdministrativeUnitIds.size() > 0) {
					q.setParameter("districtIds", fmsAdministrativeUnitIds);
					qCount.setParameter("districtIds", fmsAdministrativeUnitIds);
				} else {
					q.setParameter("districtId", searchDto.getDistrict());
					qCount.setParameter("districtId", searchDto.getDistrict());
				}
			}
			if (searchDto.getProvince() != null) {
				q.setParameter("provinceId", searchDto.getProvince());
				qCount.setParameter("provinceId", searchDto.getProvince());
			}
			if (searchDto.getRegion() != null) {
				q.setParameter("regionId", searchDto.getRegion());
				qCount.setParameter("regionId", searchDto.getRegion());
			}
			if (searchDto.getAnimalParentId() != null) {
				q.setParameter("animalParentId", searchDto.getAnimalParentId());
				qCount.setParameter("animalParentId", searchDto.getAnimalParentId());
			}
			if (searchDto.getAnimalTypeId() != null) {
				q.setParameter("animalTypeId", searchDto.getAnimalTypeId());
				qCount.setParameter("animalTypeId", searchDto.getAnimalTypeId());
			}
			if (searchDto.getFileId() != null) {
				q.setParameter("fileId", searchDto.getFileId());
				qCount.setParameter("fileId", searchDto.getFileId());
			}
			if (searchDto.getHusbandryMethodId() != null) {
				q.setParameter("husbandryMethodId", searchDto.getHusbandryMethodId());
				qCount.setParameter("husbandryMethodId", searchDto.getHusbandryMethodId());
			}
			if (searchDto.getLodgingAcreage() != null && searchDto.getLodgingAcreage() > 0) {
				q.setParameter("lodgingAcreage", searchDto.getLodgingAcreage());
				qCount.setParameter("lodgingAcreage", searchDto.getLodgingAcreage());
			}
			if (searchDto.getMaxNumberOfAnimal() != null && searchDto.getMaxNumberOfAnimal() > 0) {
				q.setParameter("maxNumberOfAnimal", searchDto.getMaxNumberOfAnimal());
				qCount.setParameter("maxNumberOfAnimal", searchDto.getMaxNumberOfAnimal());
			}
			if (searchDto.getOwnerName() != null && searchDto.getOwnerName().length() > 0) {
				q.setParameter("ownerName", searchDto.getOwnerName());
				qCount.setParameter("ownerName", searchDto.getOwnerName());
			}
			// if(searchDto.getProductTargetId()!=null){
			// q.setParameter("productTargetId", searchDto.getProductTargetId());
			// qCount.setParameter("productTargetId", searchDto.getProductTargetId());
			// }
			if (searchDto.getTotalAcreage() != null && searchDto.getTotalAcreage() > 0) {
				q.setParameter("totalAcreage", searchDto.getTotalAcreage());
				qCount.setParameter("totalAcreage", searchDto.getTotalAcreage());
			}
			if (searchDto.getVetStaffName() != null && searchDto.getVetStaffName().length() > 0) {
				q.setParameter("vetStaffName", searchDto.getVetStaffName());
				qCount.setParameter("vetStaffName", searchDto.getVetStaffName());
			}
			if (searchDto.getWaterResourceId() != null) {
				q.setParameter("waterResourceId", searchDto.getWaterResourceId());
				qCount.setParameter("waterResourceId", searchDto.getWaterResourceId());
			}
			if (searchDto.getAdressDetail() != null && searchDto.getAdressDetail().length() > 0) {
				q.setParameter("adressDetail", searchDto.getAdressDetail());
				qCount.setParameter("adressDetail", searchDto.getAdressDetail());
			}
			if (searchDto.getStatus() != null) {
				q.setParameter("status", searchDto.getStatus());
				qCount.setParameter("status", searchDto.getStatus());
			}
			if (searchDto.getPhoneNumber() != null && searchDto.getPhoneNumber().length() > 0) {
				q.setParameter("phoneNumber", searchDto.getPhoneNumber());
				qCount.setParameter("phoneNumber", searchDto.getPhoneNumber());
			}
			if (searchDto.getBusinessRegistrationDate() != null) {
				q.setParameter("businessRegistrationDate", searchDto.getBusinessRegistrationDate());
				qCount.setParameter("businessRegistrationDate", searchDto.getBusinessRegistrationDate());
			}
			if (searchDto.getBusinessRegistrationNumber() != null
					&& searchDto.getBusinessRegistrationNumber().length() > 0) {
				q.setParameter("businessRegistrationNumber", searchDto.getBusinessRegistrationNumber());
				qCount.setParameter("businessRegistrationNumber", searchDto.getBusinessRegistrationNumber());
			}
			if (searchDto.getDistanceToResidentialArea() != null && searchDto.getDistanceToResidentialArea() > 0) {
				q.setParameter("distanceToResidentialArea", searchDto.getDistanceToResidentialArea());
				qCount.setParameter("distanceToResidentialArea", searchDto.getDistanceToResidentialArea());
			}
			if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
					&& searchDto.getProductTargetId() == null && searchDto.getAnimalId() == null) {
				q.setParameter("balanceNumber", searchDto.getBalanceNumber());
				qCount.setParameter("balanceNumber", searchDto.getBalanceNumber());
			}
			if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
					&& searchDto.getProductTargetId() == null && searchDto.getAnimalId() == null) {
				q.setParameter("balanceNumber", searchDto.getBalanceNumber());
				qCount.setParameter("balanceNumber", searchDto.getBalanceNumber());
			}
			if (searchDto.getToBalanceNumber() != null && searchDto.getToBalanceNumber() > 0
					&& searchDto.getProductTargetId() == null && searchDto.getAnimalId() == null) {
				q.setParameter("toBalanceNumber", searchDto.getToBalanceNumber());
				qCount.setParameter("toBalanceNumber", searchDto.getToBalanceNumber());
			}
			if (searchDto.getToBalanceNumber() != null && searchDto.getToBalanceNumber() > 0
					&& searchDto.getProductTargetId() == null && searchDto.getAnimalId() == null) {
				q.setParameter("toBalanceNumber", searchDto.getToBalanceNumber());
				qCount.setParameter("toBalanceNumber", searchDto.getToBalanceNumber());
			}
			if (searchDto.getFax() != null && searchDto.getFax().length() > 0) {
				q.setParameter("fax", searchDto.getFax());
				qCount.setParameter("fax", searchDto.getFax());
			}
			if (searchDto.getLatitude() != null && searchDto.getLatitude().length() > 0) {
				q.setParameter("latitude", searchDto.getLatitude());
				qCount.setParameter("latitude", searchDto.getLatitude());
			}
			if (searchDto.getLongitude() != null && searchDto.getLongitude().length() > 0) {
				q.setParameter("longitude", searchDto.getLongitude());
				qCount.setParameter("longitude", searchDto.getLongitude());
			}
			if (searchDto.getOwnerCitizenCode() != null && searchDto.getOwnerCitizenCode().length() > 0) {
				q.setParameter("ownerCitizenCode", searchDto.getOwnerCitizenCode());
				qCount.setParameter("ownerCitizenCode", searchDto.getOwnerCitizenCode());
			}
			if (searchDto.getVetStaffCitizenCode() != null && searchDto.getVetStaffCitizenCode().length() > 0) {
				q.setParameter("vetStaffCitizenCode", searchDto.getVetStaffCitizenCode());
				qCount.setParameter("vetStaffCitizenCode", searchDto.getVetStaffCitizenCode());
			}
			if (searchDto.getIsOutSourcing() != null && searchDto.getIsOutSourcing()) {
				q.setParameter("isOutSourcing", searchDto.getIsOutSourcing());
				qCount.setParameter("isOutSourcing", searchDto.getIsOutSourcing());
			}

			if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
					&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() == null) {
				q.setParameter("productTargetExist", searchDto.getBalanceNumber());
				qCount.setParameter("productTargetExist", searchDto.getBalanceNumber());
				// q.setParameter("producTargetExistId", searchDto.getProductTargetExistId());
				// qCount.setParameter("producTargetExistId",
				// searchDto.getProductTargetExistId());
			}
			if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
					&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() != null) {
				q.setParameter("animalProductTargetExist", searchDto.getBalanceNumber());
				qCount.setParameter("animalProductTargetExist", searchDto.getBalanceNumber());
				//
				// q.setParameter("animalProductTargetId",
				// searchDto.getAnimalProductTargetId());
				// qCount.setParameter("animalProductTargetId",
				// searchDto.getAnimalProductTargetId());
				//
				// q.setParameter("animalExistId", searchDto.getAnimalExistId());
				// qCount.setParameter("animalExistId", searchDto.getAnimalExistId());
			}
			if (searchDto.getToBalanceNumber() != null && searchDto.getToBalanceNumber() > 0
					&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() == null) {
				q.setParameter("productTargetExistTo", searchDto.getToBalanceNumber());
				qCount.setParameter("productTargetExistTo", searchDto.getToBalanceNumber());
			}
			if (searchDto.getToBalanceNumber() != null && searchDto.getToBalanceNumber() > 0
					&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() != null) {
				q.setParameter("animalProductTargetExistTo", searchDto.getToBalanceNumber());
				qCount.setParameter("animalProductTargetExistTo", searchDto.getToBalanceNumber());
			}
			if (searchDto.getCreateDateFrom() != null) {
				q.setParameter("createDateFrom", new LocalDateTime(searchDto.getCreateDateFrom()));
				qCount.setParameter("createDateFrom", new LocalDateTime(searchDto.getCreateDateFrom()));
			}
			if (searchDto.getCreateDateTo() != null) {
				q.setParameter("createDateTo", new LocalDateTime(searchDto.getCreateDateTo()));
				qCount.setParameter("createDateTo", new LocalDateTime(searchDto.getCreateDateTo()));
			}
			if (searchDto.getModifiedDateFrom() != null) {
				q.setParameter("modifiedDateFrom", new LocalDateTime(searchDto.getModifiedDateFrom()));
				qCount.setParameter("modifiedDateFrom", new LocalDateTime(searchDto.getModifiedDateFrom()));
			}
			if (searchDto.getModifiedDateTo() != null) {
				q.setParameter("modifiedDateTo", new LocalDateTime(searchDto.getModifiedDateTo()));
				qCount.setParameter("modifiedDateTo", new LocalDateTime(searchDto.getModifiedDateTo()));
			}
			if (searchDto.getCreatedBy() != null) {
				q.setParameter("createdBy", "%" + searchDto.getCreatedBy() + "%");
				qCount.setParameter("createdBy", "%" + searchDto.getCreatedBy() + "%");
			}
			if (searchDto.getModifiedBy() != null) {
				q.setParameter("modifiedBy", "%" + searchDto.getModifiedBy() + "%");
				qCount.setParameter("modifiedBy", "%" + searchDto.getModifiedBy() + "%");
			}

			if (searchDto.getCertificateId() != null && searchDto.getCertificateId() > 0) {
				q.setParameter("certificateId", searchDto.getCertificateId());
				qCount.setParameter("certificateId", searchDto.getCertificateId());
			}
			if (searchDto.getOwnership() != null && searchDto.getOwnership().getId() != null) {
				q.setParameter("ownershipId", searchDto.getOwnership().getId());
				qCount.setParameter("ownershipId", searchDto.getOwnership().getId());
			}
			if (searchDto.getYearRegistration() != null) {
				q.setParameter("yearRegistration", Integer.valueOf(searchDto.getYearRegistration()));
				qCount.setParameter("yearRegistration", Integer.valueOf(searchDto.getYearRegistration()));
			}
			if (searchDto.getStatusFarm() != null && searchDto.getStatusFarm() == 1 && searchDto.getYear() > 0) {
				q.setParameter("year", Integer.valueOf(searchDto.getYear()));
				qCount.setParameter("year", Integer.valueOf(searchDto.getYear()));
			}

		}
		q.setFirstResult((pageIndex) * pageSize);
		q.setMaxResults(pageSize);

		Long numberResult = (Long) qCount.getSingleResult();
		List<FarmDto> ret = q.getResultList();
		if (ret != null && ret.size() > 0) {
			for (FarmDto f : ret) {
				// System.out.println("Phone number: "+f.getPhoneNumber());
				if (f.getPhoneNumber() != null && f.getPhoneNumber().indexOf('E') != -1) {
					DecimalFormat df = new DecimalFormat("#");
					try {
						double a = Double.parseDouble(f.getPhoneNumber());
						String d = df.format(a);
						String zero = "0";
						String d2 = zero.concat(d);
						// System.out.println(d2);
						Farm farm = this.farmRepository.findOne(f.getId());
						farm.setPhoneNumber(d2);
						f.setPhoneNumber(d2);
					} catch (Exception e) {
						System.out.println(f.getName() + " Ép kiểu số điện thoại lỗi");
					}
				}
				if (f.getOwnerPhoneNumber() != null && f.getOwnerPhoneNumber().indexOf('E') != -1) {
					DecimalFormat df = new DecimalFormat("#");
					try {
						double a = Double.parseDouble(f.getOwnerPhoneNumber());
						String d = df.format(a);
						String zero = "0";
						String d2 = zero.concat(d);
						// System.out.println(d2);
						Farm farm = this.farmRepository.findOne(f.getId());
						farm.setOwnerPhoneNumber(d2);
						f.setOwnerPhoneNumber(d2);
					} catch (Exception e) {
						System.out.println(f.getName() + " Ép kiểu số điện thoại lỗi");
					}
				}
			}
		}

		Page<FarmDto> page = new PageImpl<>(ret, pageable, numberResult);
		return page;
	}

	@Override
	public List<FarmDto> searchFarmDto(FarmSearchDto searchDto) {
		String animalclause = "";
		String animalTypeclause = "";
		String fileclause = "";
		String targetclause = "";
		String producTargetExistclause = "";
		String animalProducTargetExistclause = "";
		String certificateclause = "";

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

		boolean isAdministrativeUnitRoleView = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH_VIEW)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DISTRICT)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_WARD);

		boolean isFarmerRole = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_FAMER);
		List<Long> fmsAdministrativeUnitIds = new ArrayList<Long>();
		if (!isAdmin && isAdministrativeUnitRole) {
					List<FmsAdministrativeUnit> list = userAdministrativeUnitService.getAdministrativeUnitByLoginUser();
					for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
						if (fmsAdministrativeUnit != null)
							fmsAdministrativeUnitIds.add(fmsAdministrativeUnit.getId());
					}
					if (list != null && list.size() > 0) {
						for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
							if (fmsAdministrativeUnit.getParent() == null) {// Cấp tỉnh
								searchDto.setProvince(fmsAdministrativeUnit.getId());
							} else if (fmsAdministrativeUnit.getParent() != null
									&& fmsAdministrativeUnit.getParent().getParent() == null) {// Cấp huyện
								if (!fmsAdministrativeUnitIds.contains(searchDto.getDistrict()))
									searchDto.setDistrict(fmsAdministrativeUnit.getId());
							} else if (fmsAdministrativeUnit.getParent() != null
									&& fmsAdministrativeUnit.getParent().getParent() != null
									&& fmsAdministrativeUnit.getParent().getParent().getParent() == null) {// Cấp xã
								searchDto.setWard(fmsAdministrativeUnit.getId());
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
		if (!isAdmin && isAdministrativeUnitRoleView) {
			List<FmsAdministrativeUnit> list = userAdministrativeUnitService.getAdministrativeUnitByLoginUser();
			for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
				if (fmsAdministrativeUnit != null)
					fmsAdministrativeUnitIds.add(fmsAdministrativeUnit.getId());
			}
			if (list != null && list.size() > 0) {
				for (FmsAdministrativeUnit fmsAdministrativeUnit : list) {
					if (fmsAdministrativeUnit.getParent() == null) {// Cấp tỉnh
						searchDto.setProvince(fmsAdministrativeUnit.getId());
					} else if (fmsAdministrativeUnit.getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent() == null) {// Cấp huyện
						if (!fmsAdministrativeUnitIds.contains(searchDto.getDistrict()))
							searchDto.setDistrict(fmsAdministrativeUnit.getId());
					} else if (fmsAdministrativeUnit.getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent() != null
							&& fmsAdministrativeUnit.getParent().getParent().getParent() == null) {// Cấp xã
						searchDto.setWard(fmsAdministrativeUnit.getId());
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
		if (searchDto.getAnimalParentId() != null) {
			animalclause += " inner join FarmAnimal a on fa.id=a.farm.id ";
		}
		if (searchDto.getAnimalTypeId() != null) {
			animalTypeclause += " inner join FarmAnimalType fat on fa.id=fat.farm.id ";
		}
		if (searchDto.getFileId() != null) {
			fileclause += " inner join FarmFileAttachment ffa on fa.id=ffa.farm.id ";
		}
//		if(searchDto.getProductTargetId()!=null){
//			targetclause +=" inner join FarmProductTarget fpt on fa.id=fpt.farm.id ";
//		}
		if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
				&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() == null) {
			producTargetExistclause += " inner join FarmProductTargetExist fpte on fa.id=fpte.farm.id ";
		}
		if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
				&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() != null) {
			animalProducTargetExistclause += " inner join FarmAnimalProductTargetExist fapte on fa.id=fapte.farm.id ";
		}
		if (searchDto.getCertificateId() != null && searchDto.getCertificateId() > 0) {
			certificateclause += " inner join FarmCertificate fc on fa.id=fc.farm.id ";
		}

		String sql = " select new com.globits.wl.dto.FarmDto(fa,true) from Farm fa " + animalclause + animalTypeclause
				+ fileclause + targetclause + producTargetExistclause + animalProducTargetExistclause
				+ certificateclause
				+ "where fa.name is not null and (fa.isDuplicate is null or fa.isDuplicate <> true) ";
		String whereClause = "";
		if (namecode != null && namecode.length() > 0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode or fa.ownerName like :namecode)";
		}
		if (searchDto.getWard() != null) {
			whereClause += " and fa.administrativeUnit.id = :wardId";
		}
		if (searchDto.getDistrict() != null) {
			whereClause += " and fa.administrativeUnit.parent.id = :districtId";
		}
		if (searchDto.getProvince() != null) {
			whereClause += " and fa.administrativeUnit.parent.parent.id  = :provinceId";
		}
		if (searchDto.getRegion() != null) {
			whereClause += " and fa.administrativeUnit.parent.parent.region.id = :regionId";
		}
		if (searchDto.getAnimalParentId() != null) {
			whereClause += " and a.animal.id = :animalParentId";
		}
		if (searchDto.getAnimalTypeId() != null) {
			whereClause += " and fat.animalType.id = :animalTypeId";
		}
		if (searchDto.getFileId() != null) {
			whereClause += " and ffa.file.id = :fileId";
		}
		if (searchDto.getHusbandryMethodId() != null) {
			whereClause += " and fa.husbandryMethod.id = :husbandryMethodId";
		}
		if (searchDto.getLodgingAcreage() != null && searchDto.getLodgingAcreage() > 0) {
			whereClause += " and fa.lodgingAcreage = :lodgingAcreage";
		}
		if (searchDto.getMaxNumberOfAnimal() != null && searchDto.getMaxNumberOfAnimal() > 0) {
			whereClause += " and fa.maxNumberOfAnimal = :maxNumberOfAnimal";
		}
		if (searchDto.getOwnerName() != null && searchDto.getOwnerName().length() > 0) {
			whereClause += " and fa.ownerName like :ownerName ";
		}
//		if(searchDto.getProductTargetId()!=null){
//			whereClause += " and fpt.productTarget.id = :productTargetId";
//		}
		if (searchDto.getTotalAcreage() != null && searchDto.getTotalAcreage() > 0) {
			whereClause += " and fa.totalAcreage = :totalAcreage";
		}
		if (searchDto.getVetStaffName() != null && searchDto.getVetStaffName().length() > 0) {
			whereClause += " and fa.vetStaffName like :vetStaffName ";
		}
		if (searchDto.getWaterResourceId() != null) {
			whereClause += " and fa.waterResources.id = :waterResourceId";
		}
		if (searchDto.getAdressDetail() != null && searchDto.getAdressDetail().length() > 0) {
			whereClause += " and fa.adressDetail like :adressDetail";
		}
		if (searchDto.getStatus() != null) {
			whereClause += " and fa.status = :status";
		}
		if (searchDto.getPhoneNumber() != null && searchDto.getPhoneNumber().length() > 0) {
			whereClause += " and fa.phoneNumber = :phoneNumber";
		}
		if (searchDto.getBusinessRegistrationDate() != null) {
			whereClause += " and fa.businessRegistrationDate = :businessRegistrationDate";
		}
		if (searchDto.getBusinessRegistrationNumber() != null
				&& searchDto.getBusinessRegistrationNumber().length() > 0) {
			whereClause += " and fa.businessRegistrationNumber = :businessRegistrationNumber";
		}
		if (searchDto.getDistanceToResidentialArea() != null && searchDto.getDistanceToResidentialArea() > 0) {
			whereClause += " and fa.distanceToResidentialArea = :distanceToResidentialArea";
		}
		if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
				&& searchDto.getProductTargetId() == null && searchDto.getAnimalId() == null) {
			whereClause += " and fa.balanceNumber >= :balanceNumber";
		}

//		if(searchDto.getFromBalanceNumber()!=null) {
//			whereClause += " and (fa.balanceNumbe>= :fromBalanceNumbe)";
//		}
//		if(searchDto.getToBalanceNumber()!=null) {
//			whereClause += " and (fa.balanceNumbe<= :toBalanceNumbe)";
//		}

		if (searchDto.getFax() != null && searchDto.getFax().length() > 0) {
			whereClause += " and fa.fax = :fax";
		}
		if (searchDto.getLatitude() != null && searchDto.getLatitude().length() > 0) {
			whereClause += " and fa.latitude = :latitude";
		}
		if (searchDto.getLongitude() != null && searchDto.getLongitude().length() > 0) {
			whereClause += " and fa.longitude = :longitude";
		}
		if (searchDto.getOwnerCitizenCode() != null && searchDto.getOwnerCitizenCode().length() > 0) {
			whereClause += " and fa.ownerCitizenCode = :ownerCitizenCode";
		}
		if (searchDto.getVetStaffCitizenCode() != null && searchDto.getVetStaffCitizenCode().length() > 0) {
			whereClause += " and fa.vetStaffCitizenCode = :vetStaffCitizenCode";
		}
		if (searchDto.getIsOutSourcing() != null && searchDto.getIsOutSourcing()) {
			whereClause += " and fa.isOutSourcing = :isOutSourcing";
		}
		if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
				&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() == null) {
			whereClause += "  and fpte.quantity >= :productTargetExist and fpte.productTarget.id="
					+ searchDto.getProductTargetId();
		}
		if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0 && searchDto.getAnimalId() != null
				&& searchDto.getProductTargetId() != null) {
			whereClause += "  and fapte.quantity >= :animalProductTargetExist and fapte.productTarget.id="
					+ searchDto.getProductTargetId() + "  and fapte.animal.id=" + searchDto.getAnimalId();
		}
		if (searchDto.getCertificateId() != null && searchDto.getCertificateId() > 0) {
			whereClause += " and  fc.certificate.id= :certificateId ";
		}
		if (searchDto.getYearRegistration() != null) {
			whereClause += "  and fa.id in ( SELECT distinct(rp.farm.id) FROM ReportPeriod rp WHERE rp.year=:yearRegistration)";
		}

		sql += whereClause;
		sql += " order by fa.modifyDate desc, fa.code asc, fa.name asc ";

		Query q = manager.createQuery(sql, FarmDto.class);

		if (namecode != null && namecode.length() > 0) {
			q.setParameter("namecode", '%' + namecode + '%');
		}
		if (searchDto.getWard() != null) {
			q.setParameter("wardId", searchDto.getWard());
		}
		if (searchDto.getDistrict() != null) {
			q.setParameter("districtId", searchDto.getDistrict());
		}
		if (searchDto.getProvince() != null) {
			q.setParameter("provinceId", searchDto.getProvince());
		}
		if (searchDto.getRegion() != null) {
			q.setParameter("regionId", searchDto.getRegion());
		}
		if (searchDto.getAnimalParentId() != null) {
			q.setParameter("animalParentId", searchDto.getAnimalParentId());
		}
		if (searchDto.getAnimalTypeId() != null) {
			q.setParameter("animalTypeId", searchDto.getAnimalTypeId());
		}
		if (searchDto.getFileId() != null) {
			q.setParameter("fileId", searchDto.getFileId());
		}
		if (searchDto.getHusbandryMethodId() != null) {
			q.setParameter("husbandryMethodId", searchDto.getHusbandryMethodId());
		}
		if (searchDto.getLodgingAcreage() != null && searchDto.getLodgingAcreage() > 0) {
			q.setParameter("lodgingAcreage", searchDto.getLodgingAcreage());
		}
		if (searchDto.getMaxNumberOfAnimal() != null && searchDto.getMaxNumberOfAnimal() > 0) {
			q.setParameter("maxNumberOfAnimal", searchDto.getMaxNumberOfAnimal());
		}
		if (searchDto.getOwnerName() != null && searchDto.getOwnerName().length() > 0) {
			q.setParameter("ownerName", searchDto.getOwnerName());
		}
//		if(searchDto.getProductTargetId()!=null){
//			q.setParameter("productTargetId", searchDto.getProductTargetId());
//		}
		if (searchDto.getTotalAcreage() != null && searchDto.getTotalAcreage() > 0) {
			q.setParameter("totalAcreage", searchDto.getTotalAcreage());
		}
		if (searchDto.getVetStaffName() != null && searchDto.getVetStaffName().length() > 0) {
			q.setParameter("vetStaffName", searchDto.getVetStaffName());
		}
		if (searchDto.getWaterResourceId() != null) {
			q.setParameter("waterResourceId", searchDto.getWaterResourceId());
		}
		if (searchDto.getAdressDetail() != null && searchDto.getAdressDetail().length() > 0) {
			q.setParameter("adressDetail", searchDto.getAdressDetail());
		}
		if (searchDto.getStatus() != null) {
			q.setParameter("status", searchDto.getStatus());
		}
		if (searchDto.getPhoneNumber() != null && searchDto.getPhoneNumber().length() > 0) {
			q.setParameter("phoneNumber", searchDto.getPhoneNumber());
		}
		if (searchDto.getBusinessRegistrationDate() != null) {
			q.setParameter("businessRegistrationDate", searchDto.getBusinessRegistrationDate());
		}
		if (searchDto.getBusinessRegistrationNumber() != null
				&& searchDto.getBusinessRegistrationNumber().length() > 0) {
			q.setParameter("businessRegistrationNumber", searchDto.getBusinessRegistrationNumber());
		}
		if (searchDto.getDistanceToResidentialArea() != null && searchDto.getDistanceToResidentialArea() > 0) {
			q.setParameter("distanceToResidentialArea", searchDto.getDistanceToResidentialArea());
		}
		if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0 && searchDto.getAnimalId() == null
				&& searchDto.getProductTargetId() == null) {
			q.setParameter("balanceNumber", searchDto.getBalanceNumber());
		}
		if (searchDto.getFax() != null && searchDto.getFax().length() > 0) {
			q.setParameter("fax", searchDto.getFax());
		}
		if (searchDto.getLatitude() != null && searchDto.getLatitude().length() > 0) {
			q.setParameter("latitude", searchDto.getLatitude());
		}
		if (searchDto.getLongitude() != null && searchDto.getLongitude().length() > 0) {
			q.setParameter("longitude", searchDto.getLongitude());
		}
		if (searchDto.getOwnerCitizenCode() != null && searchDto.getOwnerCitizenCode().length() > 0) {
			q.setParameter("ownerCitizenCode", searchDto.getOwnerCitizenCode());
		}
		if (searchDto.getVetStaffCitizenCode() != null && searchDto.getVetStaffCitizenCode().length() > 0) {
			q.setParameter("vetStaffCitizenCode", searchDto.getVetStaffCitizenCode());
		}
		if (searchDto.getIsOutSourcing() != null && searchDto.getIsOutSourcing()) {
			q.setParameter("isOutSourcing", searchDto.getIsOutSourcing());
		}
		if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
				&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() == null) {
			q.setParameter("productTargetExist", searchDto.getBalanceNumber());
//			q.setParameter("producTargetExistId", searchDto.getProductTargetExistId());			
		}
		if (searchDto.getBalanceNumber() != null && searchDto.getBalanceNumber() > 0
				&& searchDto.getProductTargetId() != null && searchDto.getAnimalId() != null) {
			q.setParameter("animalProductTargetExist", searchDto.getBalanceNumber());
//			q.setParameter("animalProductTargetId", searchDto.getAnimalProductTargetId());			
//			q.setParameter("animalExistId", searchDto.getAnimalExistId());

		}
		if (searchDto.getCertificateId() != null && searchDto.getCertificateId() > 0) {
			q.setParameter("certificateId", searchDto.getCertificateId());
		}
		if (searchDto.getYearRegistration() != null) {
			q.setParameter("yearRegistration", Integer.valueOf(searchDto.getYearRegistration()));
		}
		List<FarmDto> ret = q.getResultList();
		// xử lý thêm tồn theo vật nuôi + hướng sản phẩm
//		if(searchDto.getViewAnimalProductTargetExist()!=null && searchDto.getViewAnimalProductTargetExist()) {
//			for (FarmDto farmDto : ret) {
//				 List<FarmAnimalProductTargetExistDto> farmAnimalProductTargetExists = new ArrayList<FarmAnimalProductTargetExistDto>();
//				 if(farmDto.getId()!=null) {
//					 List<FarmAnimalProductTargetExist> list=farmAnimalProductTargetExistRepository.getByFarm(farmDto.getId());
//					 if(list!=null && list.size()>0) {
//						 for (FarmAnimalProductTargetExist farmAnimalProductTargetExist : list) {
//							 FarmAnimalProductTargetExistDto fape=new FarmAnimalProductTargetExistDto();
//							 fape.setQuantity(farmAnimalProductTargetExist.getQuantity());
//							 if(farmAnimalProductTargetExist.getAnimal()!=null) {
//								 AnimalDto aDto=new AnimalDto();
//								 aDto.setId(farmAnimalProductTargetExist.getAnimal().getId());
//								 aDto.setCode(farmAnimalProductTargetExist.getAnimal().getCode());
//								 aDto.setName(farmAnimalProductTargetExist.getAnimal().getName());
//								 fape.setAnimal(aDto);
//							 }
//							 if(farmAnimalProductTargetExist.getProductTarget()!=null) {
//								 ProductTargetDto tarDto=new ProductTargetDto();
//								 tarDto.setId(farmAnimalProductTargetExist.getProductTarget().getId());
//								 tarDto.setCode(farmAnimalProductTargetExist.getProductTarget().getCode());
//								 tarDto.setName(farmAnimalProductTargetExist.getProductTarget().getName());
//								 fape.setProductTarget(tarDto);
//							 }
//							 farmAnimalProductTargetExists.add(fape);
//						}
//					 }
//				 }
//				 farmDto.setFarmAnimalProductTargetExists(farmAnimalProductTargetExists);
//			}
//		}
		return ret;
	}

	@Override
	public String autoGenericCode(String codeDistrict, Long districtId, String codeCity) {
		FarmDto dto = new FarmDto();
		String code = "";
		String district = "";// huyện
		String city = "";// tỉnh
		String stt = "";
		long count = 0;
		String countD = "";
		if (codeDistrict != null && codeDistrict != "") {
			district = codeDistrict;
		}
		if (codeCity != null && codeCity != "") {
			city = codeCity;
		}
		count = farmRepository.count(districtId);
		count = count + 1;
		String preCode = city + "." + district;
		if (count < 10) {
			code = ".0000" + count;
		} else if (count >= 10 && count < 100) {
			code = ".000" + count;
		} else if (count >= 100 && count < 1000) {
			code = ".00" + count;
		} else if (count >= 1000 && count < 10000) {
			code = ".0" + count;
		} else if (count >= 10000 && count < 100000) {
			code = "." + count;
		}

		Long countAgain = farmRepository.countByCode(preCode + code);
		while (countAgain != null && countAgain > 0) {
			count += 1;
			if (count < 10) {
				code = ".0000" + count;
			} else if (count >= 10 && count < 100) {
				code = ".000" + count;
			} else if (count >= 100 && count < 1000) {
				code = ".00" + count;
			} else if (count >= 1000 && count < 10000) {
				code = ".0" + count;
			} else if (count >= 10000 && count < 100000) {
				code = "." + count;
			}
			countAgain = farmRepository.countByCode(preCode + code);
		}
		return (preCode + code);
	}

	@Override
	public List<FarmDto> saveListImportFarm(List<FarmDto> listFarm) {
		ArrayList<FarmDto> ret = new ArrayList<FarmDto>();
		for (int i = 0; i < listFarm.size(); i++) {
			FarmDto dto = listFarm.get(i);
//			if(dto.getCode()!=null)
			this.createFarm(dto);
		}
		return ret;
	}

	// hàm tính số con/1 tỉnh hoặc huyện hoặc xã (level=1 -tỉnh, level=2 -huyện,
	// level=3 -xã)
	public double countFarmByProvince(Long cityId, int level) {
		double count = 0;
		String sqlCount = "select SUM(iea.remainQuantity) from  ImportExportAnimal iea  where (1=1) and iea.type=1";
		String whereClause = "";

		if (cityId != null && level == 1) {// cấp tỉnh
			whereClause += " and iea.farm.administrativeUnit.parent.parent.id  = :provinceId";
		} else if (cityId != null && level == 2) {// cấp huyện
			whereClause += " and iea.farm.administrativeUnit.parent.id  = :provinceId";
		} else if (cityId != null && level == 3) {// cấp xã
			whereClause += " and iea.farm.administrativeUnit.id  = :provinceId";
		}
		sqlCount += whereClause;

		Query qCount = manager.createQuery(sqlCount);

		if (cityId != null) {
			qCount.setParameter("provinceId", cityId);
		}
		Double results = (Double) qCount.getSingleResult();
		if (results != null) {
			count = results;
		}
		return count;
	}

	@Override
	public FarmAdministrativeUnitDto countFarmByAdministrativeUnit(Long provinceId, int level) {
		FarmAdministrativeUnitDto dto = new FarmAdministrativeUnitDto();

		if (provinceId != null) {
			double count = countFarmByProvince(provinceId, level);
			dto.setQuantity(count);
			FmsAdministrativeUnit au = administrativeUnitRepository.findOne(provinceId);
			if (au != null) {
				dto.setIdAu(au.getId());
				dto.setCodeAu(au.getCode());
				dto.setNameAu(au.getName());
				dto.setMapCodeAu(au.getMapCode());
				dto.setIdAu(provinceId);
				if (au.getRegion() != null) {
					dto.setRegionId(au.getRegion().getId());
					dto.setRegionName(au.getRegion().getName());
				}
				if (au.getTotalAcreage() != null) {
					dto.setTotalAcreage(au.getTotalAcreage());
//					dto.set
				} else {
					dto.setTotalAcreage(0D);
					dto.setCount(0D);
				}
				if (dto.getTotalAcreage() > 0) {
					dto.setCount(Math.round(dto.getQuantity() / dto.getTotalAcreage()));
				}

			}
		}
		return dto;
	}

	@Override
	public List<FarmAdministrativeUnitDto> countFarmByListAdministrativeUnit(List<Long> provinceIds, int level) {
		List<FarmAdministrativeUnitDto> ret = new ArrayList<FarmAdministrativeUnitDto>();
		if (provinceIds != null && provinceIds.size() > 0) {
			for (Long item : provinceIds) {
				FarmAdministrativeUnitDto dto = this.countFarmByAdministrativeUnit(item, level);
				ret.add(dto);
			}
		}
		return ret;
	}

	@Override
	public List<FarmAdministrativeUnitDto> countFarmListAdministrativeUnitByMapCode(String mapCode, int level) {
		List<FarmAdministrativeUnitDto> ret = new ArrayList<FarmAdministrativeUnitDto>();
		if (mapCode != null) {
			List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByMapCode(mapCode);
			if (list != null && list.size() > 0) {
				FmsAdministrativeUnit au = list.get(0);
//				List<Long> lstAdminUnit = userAdministrativeUnitService.getAdministrativeUnitIdByLoginUser();
//				if(lstAdminUnit==null || lstAdminUnit.size()<1) {
//					lstAdminUnit=new ArrayList<Long>();
//					lstAdminUnit.add(-1L);
//				}
				List<FmsAdministrativeUnitDto> lstDistrict = fmsAdministrativeUnitRepository
						.getAllByParentId(au.getId());
				if (lstDistrict != null && lstDistrict.size() > 0) {
					for (FmsAdministrativeUnitDto fmsAdministrativeUnitDto : lstDistrict) {
						FarmAdministrativeUnitDto dto = countFarmByAdministrativeUnit(fmsAdministrativeUnitDto.getId(),
								level);
						if (dto.getQuantity() > 0)
							ret.add(dto);
					}
				}
			}
		}
		// TODO Auto-generated method stub
		return ret;
	}

	@Override
	public List<FarmAdministrativeUnitDto> countFarmListAdministrativeUnitById(Long id, int level) {
		List<FarmAdministrativeUnitDto> ret = new ArrayList<FarmAdministrativeUnitDto>();
		if (id != null) {
			FmsAdministrativeUnit au = fmsAdministrativeUnitRepository.findById(id);
			if (au != null && au.getId() != null) {
//				List<Long> lstAdminUnit = userAdministrativeUnitService.getAdministrativeUnitIdByLoginUser();
//				List<FmsAdministrativeUnitDto> lstDistrict=fmsAdministrativeUnitRepository.getAllByParentId(au.getId());
				List<FmsAdministrativeUnitDto> lstDistrict = fmsAdministrativeUnitService.getAllByParentId(au.getId());
				if (lstDistrict != null && lstDistrict.size() > 0) {
					for (FmsAdministrativeUnitDto fmsAdministrativeUnitDto : lstDistrict) {
						FarmAdministrativeUnitDto dto = countFarmByAdministrativeUnit(fmsAdministrativeUnitDto.getId(),
								level);
						if (dto.getQuantity() > 0)
							ret.add(dto);
					}
				}
			}
		}
		// TODO Auto-generated method stub
		return ret;
	}

	/*
	 * Tính tổng tồn hiện tại của tất cả cơ sở chăn nuôi
	 *
	 */
	public void countBalanceNumberAllFarm() {
		List<Farm> list = farmRepository.findAll();
		if (list != null) {
			for (Farm farm : list) {
				Double total = importExportAnimalRepository.sumRemainQuantity(farm.getId());
				farm.setBalanceNumber(total);
				farmRepository.save(farm);
			}
		}
	}

	/*
	 * Tính tổng tồn hiện tại của cơ sở chăn nuôi
	 */
	@Override
	public void countBalanceNumberByFarm(Long farmId) {
		Farm farm = farmRepository.findOne(farmId);
		if (farm != null) {
			Double total = importExportAnimalRepository.sumRemainQuantity(farm.getId());
			farm.setBalanceNumber(total);
			farmRepository.save(farm);
		}
	}

	@Override
	/*
	 * Tính mật độ cơ sở chăn nuôi cho 1 vùng
	 * 
	 * @see com.globits.wl.service.FarmService#countDensityRegion(java.lang.Long)
	 */
	public DensityRegionDto countDensityRegion(Long regionId) {
		DensityRegionDto ret = new DensityRegionDto();
		List<FarmAdministrativeUnitDto> list = new ArrayList<FarmAdministrativeUnitDto>();
		double totalArea = 0;// tổng diện tích đất của 1 vùng
		double quantity = 0;// tổng số con của 1 vùng
		double density = 0;// mật độ của 1 vùng
		FmsRegion region = fmsRegionRepository.findOne(regionId);
		if (region != null) {
			ret.setRegionId(regionId);
			ret.setRegionName(region.getName());
			totalArea = fmsAdministrativeUnitService.sumTotalAcreageByRegion(regionId);
			quantity = sumQuantityRegion(regionId);
			density = quantity / totalArea;
			ret.setDensityRegion(density);
			list = fmsAdministrativeUnitService.getListProvinceByRegion(regionId);
			ret.setFarmAdministrativeUnits(list);
			ret.setNumberProvince(list.size());
		}
		return ret;
	}

	public double sumQuantityRegion(Long regionId) {
		double count = 0;
		String sqlCount = "select SUM(iea.balanceNumber) from  Farm iea  where (1=1) ";
		String whereClause = "";

		if (regionId != null) {// vùng
			whereClause += " and iea.administrativeUnit.parent.parent.region.id  = :regionId";
		}
		sqlCount += whereClause;

		Query qCount = manager.createQuery(sqlCount);

		if (regionId != null) {
			qCount.setParameter("regionId", regionId);
		}
		Double results = (Double) qCount.getSingleResult();
		if (results != null) {
			count = results;
		}
		return count;
	}
	/*
	 * Tính mật độ của tất cả các vùng
	 * 
	 * @see com.globits.wl.service.FarmService#countDensityListRegion()
	 */

	@Override
	public List<DensityRegionDto> countDensityListRegion() {
		List<DensityRegionDto> ret = new ArrayList<DensityRegionDto>();
		List<FmsRegionDto> list = fmsRegionRepository.getAll();
		if (list != null && list.size() > 0) {
			for (FmsRegionDto fmsRegionDto : list) {
				DensityRegionDto item = new DensityRegionDto();
				item = countDensityRegion(fmsRegionDto.getId());
				ret.add(item);
			}
		}
		return ret;
	}

	/*
	 * Tính mật độ cơ sở chăn nuôi cho 1 vùng theo map code đơn vị hành chính
	 * 
	 * @see com.globits.wl.service.FarmService#countDensityRegion(java.lang.Long)
	 */
	@Override
	public DensityRegionDto countDensityMapCodeAu(String mapCode) {
		DensityRegionDto ret = new DensityRegionDto();
		FmsAdministrativeUnit au = null;
		List<FmsAdministrativeUnit> listAu = fmsAdministrativeUnitRepository.findListByMapCode(mapCode);
		if (listAu != null && listAu.size() > 0) {
			au = listAu.get(0);
		}
		if (au != null && au.getRegion() != null) {
			List<FarmAdministrativeUnitDto> list = new ArrayList<FarmAdministrativeUnitDto>();
			double totalArea = 0;// tổng diện tích đất của 1 vùng
			double quantity = 0;// tổng số con của 1 vùng
			double density = 0;// mật độ của 1 vùng
			FmsRegion region = fmsRegionRepository.findOne(au.getRegion().getId());
			if (region != null) {
				ret.setRegionId(au.getRegion().getId());
				ret.setRegionName(region.getName());
				totalArea = fmsAdministrativeUnitService.sumTotalAcreageByRegion(au.getRegion().getId());
				quantity = sumQuantityRegion(au.getRegion().getId());
				density = quantity / totalArea;
				ret.setDensityRegion(density);
				list = fmsAdministrativeUnitService.getListProvinceByRegion(au.getRegion().getId());
				ret.setFarmAdministrativeUnits(list);
				ret.setNumberProvince(list.size());
			}
		}

		return ret;
	}

	public FarmDto saveImportFarm(FarmDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			boolean isNew = false;
			Farm farm = null;
			// Tìm theo mã định danh cơ sở, nếu có rồi thì chỉ update
			List<Farm> listFarm = this.farmRepository.findByCode(dto.getCode());
			if (listFarm != null && listFarm.size() == 1) {
				farm = listFarm.get(0);
			} else if (listFarm != null && listFarm.size() > 1) {
				for (Farm f : listFarm) {
					if (dto.getName() != null && dto.getName().equals(f.getName())) {
						farm = f;
						break;
					}
				}
			}

			if (farm == null) {
				farm = new Farm();
				farm.setCreateDate(currentDate);
				farm.setCreatedBy(currentUserName);
				isNew = true;
			}
			farm.setBalanceNumber(0D);
			farm.setName(dto.getName());
			farm.setCode(dto.getCode());
			farm.setLongitude(dto.getLongitude());
			farm.setLatitude(dto.getLatitude());

			farm.setgMapX(dto.getgMapX());
			farm.setgMapY(dto.getgMapY());
			farm.setAdressDetail(dto.getAdressDetail());
			farm.setVillage(dto.getVillage());
			farm.setMediaLink(dto.getMediaLink());
			farm.setDescription(dto.getDescription());
			farm.setPhoneNumber(dto.getPhoneNumber());
			farm.setFax(dto.getFax());

			farm.setOwnerName(dto.getOwnerName());
			farm.setOwnerPhoneNumber(dto.getOwnerPhoneNumber());
			farm.setOwnerCitizenCode(dto.getOwnerCitizenCode());
			farm.setOwnerEmail(dto.getOwnerEmail());
			farm.setOwnerAdress(dto.getOwnerAdress());

			farm.setVetStaffName(dto.getVetStaffName());
			farm.setVetStaffCitizenCode(dto.getVetStaffCitizenCode());
			farm.setVetStaffPhoneNumber(dto.getVetStaffPhoneNumber());
			farm.setVetStaffEmail(dto.getVetStaffEmail());
			farm.setVetStaffAdress(dto.getVetStaffAdress());

			farm.setTotalAcreage(dto.getTotalAcreage());
			farm.setLodgingAcreage(dto.getLodgingAcreage());
			farm.setMaxNumberOfAnimal(dto.getMaxNumberOfAnimal());

			// tran huu dat them cac thuoc tinh file excel moi
			// tran huu dat them cac thuoc tinh file excel moi
			farm.setOwnerCitizenDate(dto.getOwnerCitizenDate());
			farm.setOwnerCitizenLocation(dto.getOwnerCitizenLocation());
			farm.setApartmentNumber(dto.getApartmentNumber());
			farm.setStartDate(dto.getStartDate());
			farm.setTtbvmt(dto.getTtbvmt());
			farm.setTtbvmtDate(dto.getTtbvmtDate());
			farm.setDateRegistration(dto.getDateRegistration());// ngay cap gcn dk
			// tran huu dat them cac thuoc tinh file excel moi
			// tran huu dat them cac thuoc tinh file excel moi
			farm.setDateOtherRegistration(dto.getDateOtherRegistration());

			if (dto.getFarmProductTargets() != null && dto.getFarmProductTargets().size() > 0) {
				Set<FarmProductTarget> lst = new HashSet<FarmProductTarget>();
				for (ProductTargetDto fptDto : dto.getFarmProductTargets()) {
					if (fptDto != null) {
						ProductTarget pg = null;
						if (fptDto.getId() != null) {
							pg = productTargetRepository.findOne(fptDto.getId());
						} else if (fptDto.getCode() != null) {
							List<ProductTarget> list = productTargetRepository.findByCode(fptDto.getCode());
							if (list != null && list.size() > 0) {
								pg = list.get(0);
							}
						}

						if (pg != null) {
							FarmProductTarget fpt = new FarmProductTarget();
							fpt.setFarm(farm);
							fpt.setProductTarget(pg);
							lst.add(fpt);
						}
					}
				}
				if (lst.size() > 0) {
					if (farm.getFarmProductTargets() == null) {
						farm.setFarmProductTargets(lst);
					} else {
						farm.getFarmProductTargets().clear();
						farm.getFarmProductTargets().addAll(lst);
					}
				}
			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmProductTargets() != null) {
					farm.getFarmProductTargets().clear();
				}
			}
			if (dto.getHusbandryMethod() != null) {
				HusbandryMethod husbandryMethod = null;
				if (dto.getHusbandryMethod().getId() != null) {
					husbandryMethod = this.husbandryMethodRepository.findOne(dto.getHusbandryMethod().getId());
				}
				if (husbandryMethod == null) {
					husbandryMethod = new HusbandryMethod();
					husbandryMethod.setCreateDate(currentDate);
					husbandryMethod.setCreatedBy(currentUserName);
				}
				husbandryMethod.setCode(dto.getHusbandryMethod().getCode());
				husbandryMethod.setName(dto.getHusbandryMethod().getName());
				farm.setHusbandryMethod(husbandryMethod);
			}
			if (dto.getWaterResources() != null) {
				WaterSource waterSource = null;
				if (dto.getWaterResources().getId() != null) {
					waterSource = this.waterSourceRepository.findOne(dto.getWaterResources().getId());
				}
				if (waterSource == null) {
					waterSource = new WaterSource();
					waterSource.setCreateDate(currentDate);
					waterSource.setCreatedBy(currentUserName);
				}
				waterSource.setCode(dto.getWaterResources().getCode());
				waterSource.setName(dto.getWaterResources().getName());
				farm.setWaterResources(waterSource);
			}
			farm.setIsOutSourcing(dto.getIsOutSourcing());
			farm.setNumberOfLabor(dto.getNumberOfLabor());
			farm.setDistanceToResidentialArea(dto.getDistanceToResidentialArea());

			if (dto.getAdministrativeUnit() != null) {
				FmsAdministrativeUnitDto auDto = dto.getAdministrativeUnit();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if (au != null)
						farm.setAdministrativeUnit(au);
				} else if (auDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if (list != null && list.size() > 0) {
						au = list.get(0);
						farm.setAdministrativeUnit(au);
					}
				}

			}
			farm.setOwnerVillage(dto.getOwnerVillage());
			if (dto.getOwnerAdministrativeUnit() != null) {
				FmsAdministrativeUnitDto auDto = dto.getOwnerAdministrativeUnit();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if (au != null) {
						farm.setOwnerAdministrativeUnit(au);
					}
				} else if (auDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if (list != null && list.size() > 0) {
						au = list.get(0);
						farm.setOwnerAdministrativeUnit(au);
					}
				}

			} else {
				FmsAdministrativeUnit au = null;
				if (dto.getOwnerDistrictId() != null || dto.getOwnerDistrictCode() != null) {
					if (dto.getOwnerDistrictId() != null) {
						au = this.fmsAdministrativeUnitRepository.findOne(dto.getOwnerDistrictId());
						if (au != null) {
							farm.setOwnerAdministrativeUnit(au);
						}
					} else {
						if (dto.getOwnerDistrictCode() != null) {
							List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
									.findListByCode(dto.getOwnerDistrictCode());
							if (list != null && list.size() > 0) {
								au = list.get(0);
								farm.setOwnerAdministrativeUnit(au);
							}
						}
					}
				} else {
					if (dto.getOwnerProvinceId() != null || dto.getOwnerProvinceCode() != null) {
						if (dto.getOwnerProvinceId() != null) {
							au = this.fmsAdministrativeUnitRepository.findOne(dto.getOwnerProvinceId());
							if (au != null) {
								farm.setOwnerAdministrativeUnit(au);
							}
						} else {
							if (dto.getOwnerProvinceCode() != null) {
								List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
										.findListByCode(dto.getOwnerProvinceCode());
								if (list != null && list.size() > 0) {
									au = list.get(0);
									farm.setOwnerAdministrativeUnit(au);
								}
							}
						}
					} else {
						if (dto.getOwnerAdministrativeUnit() == null) {
							farm.setOwnerAdministrativeUnit(null);
						}
					}
				}
			}

			Set<FarmFileAttachment> farmList = new HashSet<FarmFileAttachment>();
			if (dto.getAttachments() != null && dto.getAttachments().size() > 0) {
				for (FarmFileAttachmentDto ffaDto : dto.getAttachments()) {
					if (ffaDto != null) {
						FarmFileAttachment ffa = null;
						if (ffaDto.getId() != null) {
							ffa = this.farmFileAttachmentRepository.findOne(ffaDto.getId());
						}
						if (ffa == null) {
							ffa = new FarmFileAttachment();
							ffa.setCreateDate(currentDate);
							ffa.setCreatedBy(currentUserName);
						}

						if (ffaDto.getFile() != null) {
							ffa.setFarm(farm);
							FileDescription file = null;
							if (ffaDto.getFile().getId() != null) {
								file = this.fileDescriptionRepository.findOne(ffaDto.getFile().getId());
							}
							if (file == null) {
								file = new FileDescription();
								file.setCreateDate(currentDate);
								file.setCreatedBy(currentUserName);
							}

							ffa.setFile(file);
							ffa = this.farmFileAttachmentRepository.save(ffa);
							farmList.add(ffa);
						}

					}
				}
				if (farmList != null && farmList.size() > 0) {
					if (farm.getAttachments() == null) {
						farm.setAttachments(farmList);
					} else {
						farm.getAttachments().clear();
						farm.getAttachments().addAll(farmList);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getAttachments() != null) {
					farm.getAttachments().clear();
				}
			}

			/** list stores */

//			farm.setStores(new HashSet<FarmStore>());
//			farm.getStores().clear();
			Set<FarmStore> stores = new HashSet<FarmStore>();
			if (dto.getStores() != null && dto.getStores().size() > 0)
				for (FarmStoreDto farmStoreDto : dto.getStores()) {

					if (farmStoreDto != null) {
						FarmStore farmStore = null;
						if (farmStoreDto.getId() != null) {
							farmStore = this.farmStoreRepository.findOne(farmStoreDto.getId());
						}
						if (farmStore == null) {
							farmStore = new FarmStore();
							farmStore.setCreateDate(currentDate);
							farmStore.setCreatedBy(currentUserName);
						}
						farmStore.setCode(farmStoreDto.getCode());
						farmStore.setName(farmStoreDto.getName());
						farmStore.setAdress(farmStoreDto.getAdress());
						farmStore.setPhoneNumber(farmStoreDto.getPhoneNumber());

						if (farmStoreDto.getAdministrativeUnitDto() != null) {
							FmsAdministrativeUnit fau = null;
							if (farmStoreDto.getAdministrativeUnitDto().getId() != null) {
								fau = this.fmsAdministrativeUnitRepository
										.findOne(farmStoreDto.getAdministrativeUnitDto().getId());
							}
							if (fau == null) {
								fau = new FmsAdministrativeUnit();
								fau.setCreateDate(currentDate);
								fau.setCreatedBy(currentUserName);
							}
							fau.setCode(farmStoreDto.getAdministrativeUnitDto().getCode());
							fau.setLevel(farmStoreDto.getAdministrativeUnitDto().getLevel());
							fau.setName(farmStoreDto.getAdministrativeUnitDto().getName());

							farmStore.setAdministrativeUnit(fau);
						}
						farmStore.setFarm(farm);
						stores.add(farmStore);
					}
				}
			if (farm.getStores() == null) {
				farm.setStores(stores);
			} else {
				farm.getStores().clear();
				farm.getStores().addAll(stores);
			}
			/** FarmAnimal */
			Set<FarmAnimal> farmAnimals = new HashSet<FarmAnimal>();
			if (dto.getFarmAnimals() != null && dto.getFarmAnimals().size() > 0) {
				for (AnimalDto farmAnimalDto : dto.getFarmAnimals()) {
					if (farmAnimalDto != null) {
						Animal animal = null;
						if (farmAnimalDto.getId() != null) {
							animal = animalRepository.findOne(farmAnimalDto.getId());
						} else if (farmAnimalDto.getCode() != null) {
							List<Animal> ans = animalRepository.findByCode(farmAnimalDto.getCode());
							if (ans != null && ans.size() > 0) {
								animal = ans.get(0);
							}
						}

						if (animal != null) {
							FarmAnimal fpt = new FarmAnimal();
							fpt.setFarm(farm);
							fpt.setAnimal(animal);
							farmAnimals.add(fpt);
						}
					}

				}
				if (farmAnimals != null && farmAnimals.size() > 0) {
					if (farm.getFarmAnimals() == null) {
						farm.setFarmAnimals(farmAnimals);
					} else {
						farm.getFarmAnimals().clear();
						farm.getFarmAnimals().addAll(farmAnimals);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmAnimals() != null) {
					farm.getFarmAnimals().clear();
				}
			}
			farm.setBusinessRegistrationDate(dto.getBusinessRegistrationDate());
			farm.setBusinessRegistrationNumber(dto.getBusinessRegistrationNumber());
			farm.setStatus(dto.getStatus());
			farm.setStatusFarm(dto.getStatusFarm());
			if (farm.getStatusFarm() == null || (farm.getStatusFarm() != null
					&& farm.getStatusFarm().equals(WLConstant.FarmStatus.active.getValue()))) {
				farm.setStopDate(null);
			} else {
				farm.setStopDate(dto.getStopDate());
			}
			/** FarmAnimalType */
			Set<FarmAnimalType> farmAnimalTypes = new HashSet<FarmAnimalType>();
			if (dto.getFarmAnimalTypes() != null && dto.getFarmAnimalTypes().size() > 0) {
				for (AnimalTypeDto farmAnimalTypeDto : dto.getFarmAnimalTypes()) {
					if (farmAnimalTypeDto != null) {
						AnimalType animalType = animalTypeRepository.findOne(farmAnimalTypeDto.getId());
						if (animalType != null) {
							FarmAnimalType fpt = new FarmAnimalType();
							fpt.setFarm(farm);
							fpt.setAnimalType(animalType);
							farmAnimalTypes.add(fpt);
						}
					}

				}
				if (farmAnimalTypes != null && farmAnimalTypes.size() > 0) {
					if (farm.getFarmAnimalTypes() == null) {
						farm.setFarmAnimalTypes(farmAnimalTypes);
					} else {
						farm.getFarmAnimalTypes().clear();
						farm.getFarmAnimalTypes().addAll(farmAnimalTypes);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmAnimalTypes() != null) {
					farm.getFarmAnimalTypes().clear();
				}
			}
			/////////// FarmCertificate
			Set<FarmCertificate> FarmCertificates = new HashSet<FarmCertificate>();
			if (dto.getFarmCertificates() != null && dto.getFarmCertificates().size() > 0) {
				for (CertificateDto farmCertificateDto : dto.getFarmCertificates()) {
					if (farmCertificateDto != null) {
						Certificate certificate = certificateRepository.findOne(farmCertificateDto.getId());
						if (certificate != null) {
							FarmCertificate fpt = new FarmCertificate();
							fpt.setFarm(farm);
							fpt.setCertificate(certificate);
							FarmCertificates.add(fpt);
						}
					}

				}
				if (FarmCertificates != null && FarmCertificates.size() > 0) {
					if (farm.getFarmCertificates() == null) {
						farm.setFarmCertificates(FarmCertificates);
					} else {
						farm.getFarmCertificates().clear();
						farm.getFarmCertificates().addAll(FarmCertificates);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmCertificates() != null) {
					farm.getFarmCertificates().clear();
				}
			}

			////////////////////////////////////

			if (dto.getFarmSize() != null && dto.getFarmSize().getId() > 0) {
				FarmSize farmSize = this.farmSizeRepository.findOne(dto.getFarmSize().getId());
				if (farmSize != null) {
					farm.setFarmSize(farmSize);
				}
			}
			Farm farmTemp = this.farmRepository.save(farm);
			// khi thêm mới cơ sở tạo luôn tài khoản nông dân
			if (isNew == true) {
				if (farm.getCode() != null) {
					User user = null;
					user = userRepository.findByUsername(farm.getCode());
					if (user == null) {
						user = new User();
					}
					user.setUsername(farm.getCode());
					if (dto.getOwnerCitizenCode() != null) {
						String password = SecurityUtils.getHashPassword(dto.getOwnerCitizenCode());
						user.setPassword(password);
					} else {
						String password = SecurityUtils.getHashPassword("123456");
						user.setPassword(password);
					}
					if (dto.getOwnerEmail() != null) {
						user.setEmail(dto.getOwnerEmail());
					} else {
						user.setEmail(dto.getCode() + "@gmail.com");
					}
					user.setActive(true);
					HashSet<Role> temp = new HashSet<Role>();
					try {
						Role r = roleRepository.findByName(WLConstant.ROLE_FAMER);
						if (r != null) {
							temp.add(r);

						}

					} catch (Exception e) {
						// TODO: handle exception
					}

					if (user.getRoles() == null) {
						user.setRoles(temp);
					} else {
						user.getRoles().clear();
						user.getRoles().addAll(temp);
					}
					Person person = user.getPerson();
					if (person == null) {
						person = new Person();
					}
					if (dto.getName() != null) {
						person.setDisplayName(dto.getName());
					}

					person.setUser(user);
					user.setPerson(person);

					// Save
					user = userRepository.save(user);
					// save useradministrativeunit
					if (dto.getAdministrativeUnit() != null && user != null && user.getId() != null) {
						List<FmsAdministrativeUnitDto> listAdministrativeUnit = new ArrayList<FmsAdministrativeUnitDto>();
						listAdministrativeUnit.add(dto.getAdministrativeUnit());
						userAdministrativeUnitService.saveAdministrativeUnitByUserId(user.getId(),
								listAdministrativeUnit);
					}
				}

			}
			if (farmTemp.getId() != null) {
				dto.setId(farmTemp.getId());
			}

			return new FarmDto(farmTemp);
		}
		return null;
	}

	public FarmDto updateFarmImport(Long id, FarmDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			boolean isNew = false;
			Farm farm = null;
			if (id != null) {
				farm = this.farmRepository.findOne(id);
			} else if (dto.getId() != null) {
				farm = this.farmRepository.findOne(id);
			} else if (dto.getCode() != null) {
				List<Farm> list = this.farmRepository.findByCode(dto.getCode());
				if (list != null && list.size() == 1) {
					farm = list.get(0);
				} else if (list != null && list.size() > 1) {
					for (Farm farm2 : list) {
						if (dto.getName() != null && dto.getName().equals(farm2.getName())) {
							farm = farm2;
							break;
						}
					}
				}
			}

			if (farm == null) {
				farm = new Farm();
				farm.setCreateDate(currentDate);
				farm.setCreatedBy(currentUserName);
				isNew = true;
			}
			farm.setName(dto.getName());
			if (isNew == true) {
				String code = null;
				if (dto.getDistrictCode() != null && dto.getDistrictId() != null && dto.getProvinceCode() != null) {
					code = autoGenericCode(dto.getDistrictCode(), dto.getDistrictId(), dto.getProvinceCode());
				} else if (dto.getAdministrativeUnit() != null) {
					String codeDis = null;
					String codeCity = null;
					Long districtId = null;
					FmsAdministrativeUnit ward = fmsAdministrativeUnitService
							.findByCode(dto.getAdministrativeUnit().getCode());
					if (ward != null && ward.getParent() != null) {

						districtId = ward.getParent().getId();
						codeDis = ward.getParent().getCode();

						if (ward.getParent().getParent() != null) {
							codeCity = ward.getParent().getParent().getCode();

							code = autoGenericCode(codeDis, districtId, codeCity);
						}
					}
				}
				if (code != null && code.length() > 0) {
					farm.setCode(code);
				}
			}

			farm.setLongitude(dto.getLongitude());
			farm.setLatitude(dto.getLatitude());

			farm.setgMapX(dto.getgMapX());
			farm.setgMapY(dto.getgMapY());
			farm.setAdressDetail(dto.getAdressDetail());
			farm.setVillage(dto.getVillage());
			farm.setMediaLink(dto.getMediaLink());
			farm.setDescription(dto.getDescription());
			farm.setPhoneNumber(dto.getPhoneNumber());
			farm.setFax(dto.getFax());

			farm.setOwnerName(dto.getOwnerName());
			farm.setOwnerPhoneNumber(dto.getOwnerPhoneNumber());
			farm.setOwnerCitizenCode(dto.getOwnerCitizenCode());
			farm.setOwnerEmail(dto.getOwnerEmail());
			farm.setOwnerAdress(dto.getOwnerAdress());

			farm.setVetStaffName(dto.getVetStaffName());
			farm.setVetStaffCitizenCode(dto.getVetStaffCitizenCode());
			farm.setVetStaffPhoneNumber(dto.getVetStaffPhoneNumber());
			farm.setVetStaffEmail(dto.getVetStaffEmail());
			farm.setVetStaffAdress(dto.getVetStaffAdress());

			farm.setTotalAcreage(dto.getTotalAcreage());
			farm.setLodgingAcreage(dto.getLodgingAcreage());
			farm.setMaxNumberOfAnimal(dto.getMaxNumberOfAnimal());
			// tran huu dat them cac thuoc tinh file excel moi
			farm.setOwnerCitizenDate(dto.getOwnerCitizenDate());
			farm.setOwnerCitizenLocation(dto.getOwnerCitizenLocation());
			farm.setApartmentNumber(dto.getApartmentNumber());
			farm.setStartDate(dto.getStartDate());
			farm.setTtbvmt(dto.getTtbvmt());
			farm.setTtbvmtDate(dto.getTtbvmtDate());
			farm.setDateRegistration(dto.getDateRegistration());// ngay cap gcn dk
			// tran huu dat them cac thuoc tinh file excel moi
			farm.setDateOtherRegistration(dto.getDateOtherRegistration());

			if (dto.getFarmProductTargets() != null && dto.getFarmProductTargets().size() > 0) {
				Set<FarmProductTarget> lst = new HashSet<FarmProductTarget>();
				for (ProductTargetDto fptDto : dto.getFarmProductTargets()) {
					if (fptDto != null) {
						ProductTarget pg = null;
						if (fptDto.getId() != null) {
							pg = productTargetRepository.findOne(fptDto.getId());
						} else if (fptDto.getCode() != null) {
							List<ProductTarget> list = productTargetRepository.findByCode(fptDto.getCode());
							if (list != null && list.size() > 0) {
								pg = list.get(0);
							}
						}

						if (pg != null) {
							FarmProductTarget fpt = new FarmProductTarget();
							fpt.setFarm(farm);
							fpt.setProductTarget(pg);
							lst.add(fpt);
						}
					}
				}
				if (lst.size() > 0) {
					if (farm.getFarmProductTargets() == null) {
						farm.setFarmProductTargets(lst);
					} else {
						farm.getFarmProductTargets().clear();
						farm.getFarmProductTargets().addAll(lst);
					}
				}
			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmProductTargets() != null) {
					farm.getFarmProductTargets().clear();
				}
			}
			if (dto.getHusbandryMethod() != null) {
				HusbandryMethod husbandryMethod = null;
				if (dto.getHusbandryMethod().getId() != null) {
					husbandryMethod = this.husbandryMethodRepository.findOne(dto.getHusbandryMethod().getId());
				}
				if (husbandryMethod == null) {
					husbandryMethod = new HusbandryMethod();
					husbandryMethod.setCreateDate(currentDate);
					husbandryMethod.setCreatedBy(currentUserName);
				}
				husbandryMethod.setCode(dto.getHusbandryMethod().getCode());
				husbandryMethod.setName(dto.getHusbandryMethod().getName());
				farm.setHusbandryMethod(husbandryMethod);
			}
			if (dto.getWaterResources() != null) {
				WaterSource waterSource = null;
				if (dto.getWaterResources().getId() != null) {
					waterSource = this.waterSourceRepository.findOne(dto.getWaterResources().getId());
				}
				if (waterSource == null) {
					waterSource = new WaterSource();
					waterSource.setCreateDate(currentDate);
					waterSource.setCreatedBy(currentUserName);
				}
				waterSource.setCode(dto.getWaterResources().getCode());
				waterSource.setName(dto.getWaterResources().getName());
				farm.setWaterResources(waterSource);
			}
			farm.setIsOutSourcing(dto.getIsOutSourcing());
			farm.setNumberOfLabor(dto.getNumberOfLabor());
			farm.setDistanceToResidentialArea(dto.getDistanceToResidentialArea());

			if (dto.getAdministrativeUnit() != null) {
				FmsAdministrativeUnitDto auDto = dto.getAdministrativeUnit();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if (au != null)
						farm.setAdministrativeUnit(au);
				} else if (auDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if (list != null && list.size() > 0) {
						au = list.get(0);
						farm.setAdministrativeUnit(au);
					}
				}

			}
			farm.setOwnerVillage(dto.getOwnerVillage());
			if (dto.getOwnerAdministrativeUnit() != null) {
				FmsAdministrativeUnitDto auDto = dto.getOwnerAdministrativeUnit();
				FmsAdministrativeUnit au = null;
				if (auDto.getId() != null) {
					au = this.fmsAdministrativeUnitRepository.findOne(auDto.getId());
					if (au != null) {
						farm.setOwnerAdministrativeUnit(au);
					}
				} else if (auDto.getCode() != null) {
					List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository.findListByCode(auDto.getCode());
					if (list != null && list.size() > 0) {
						au = list.get(0);
						farm.setOwnerAdministrativeUnit(au);
					}
				}

			} else {
				FmsAdministrativeUnit au = null;
				if (dto.getOwnerDistrictId() != null || dto.getOwnerDistrictCode() != null) {
					if (dto.getOwnerDistrictId() != null) {
						au = this.fmsAdministrativeUnitRepository.findOne(dto.getOwnerDistrictId());
						if (au != null) {
							farm.setOwnerAdministrativeUnit(au);
						}
					} else {
						if (dto.getOwnerDistrictCode() != null) {
							List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
									.findListByCode(dto.getOwnerDistrictCode());
							if (list != null && list.size() > 0) {
								au = list.get(0);
								farm.setOwnerAdministrativeUnit(au);
							}
						}
					}
				} else {
					if (dto.getOwnerProvinceId() != null || dto.getOwnerProvinceCode() != null) {
						if (dto.getOwnerProvinceId() != null) {
							au = this.fmsAdministrativeUnitRepository.findOne(dto.getOwnerProvinceId());
							if (au != null) {
								farm.setOwnerAdministrativeUnit(au);
							}
						} else {
							if (dto.getOwnerProvinceCode() != null) {
								List<FmsAdministrativeUnit> list = fmsAdministrativeUnitRepository
										.findListByCode(dto.getOwnerProvinceCode());
								if (list != null && list.size() > 0) {
									au = list.get(0);
									farm.setOwnerAdministrativeUnit(au);
								}
							}
						}
					} else {
						if (dto.getOwnerAdministrativeUnit() == null) {
							farm.setOwnerAdministrativeUnit(null);
						}
					}
				}
			}

			Set<FarmFileAttachment> farmList = new HashSet<FarmFileAttachment>();
			if (dto.getAttachments() != null && dto.getAttachments().size() > 0) {
				for (FarmFileAttachmentDto ffaDto : dto.getAttachments()) {
					if (ffaDto != null) {
						FarmFileAttachment ffa = null;
						if (ffaDto.getId() != null) {
							ffa = this.farmFileAttachmentRepository.findOne(ffaDto.getId());
						}
						if (ffa == null) {
							ffa = new FarmFileAttachment();
							ffa.setCreateDate(currentDate);
							ffa.setCreatedBy(currentUserName);
						}

						if (ffaDto.getFile() != null) {
							ffa.setFarm(farm);
							FileDescription file = null;
							if (ffaDto.getFile().getId() != null) {
								file = this.fileDescriptionRepository.findOne(ffaDto.getFile().getId());
							}
							if (file == null) {
								file = new FileDescription();
								file.setCreateDate(currentDate);
								file.setCreatedBy(currentUserName);
							}

							ffa.setFile(file);
							ffa = this.farmFileAttachmentRepository.save(ffa);
							farmList.add(ffa);
						}

					}
				}
				if (farmList != null && farmList.size() > 0) {
					if (farm.getAttachments() == null) {
						farm.setAttachments(farmList);
					} else {
						farm.getAttachments().clear();
						farm.getAttachments().addAll(farmList);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getAttachments() != null) {
					farm.getAttachments().clear();
				}
			}

			/** list stores */

//			farm.setStores(new HashSet<FarmStore>());
//			farm.getStores().clear();
			Set<FarmStore> stores = new HashSet<FarmStore>();
			if (dto.getStores() != null && dto.getStores().size() > 0)
				for (FarmStoreDto farmStoreDto : dto.getStores()) {

					if (farmStoreDto != null) {
						FarmStore farmStore = null;
						if (farmStoreDto.getId() != null) {
							farmStore = this.farmStoreRepository.findOne(farmStoreDto.getId());
						}
						if (farmStore == null) {
							farmStore = new FarmStore();
							farmStore.setCreateDate(currentDate);
							farmStore.setCreatedBy(currentUserName);
						}
						farmStore.setCode(farmStoreDto.getCode());
						farmStore.setName(farmStoreDto.getName());
						farmStore.setAdress(farmStoreDto.getAdress());
						farmStore.setPhoneNumber(farmStoreDto.getPhoneNumber());

						if (farmStoreDto.getAdministrativeUnitDto() != null) {
							FmsAdministrativeUnit fau = null;
							if (farmStoreDto.getAdministrativeUnitDto().getId() != null) {
								fau = this.fmsAdministrativeUnitRepository
										.findOne(farmStoreDto.getAdministrativeUnitDto().getId());
							}
							if (fau == null) {
								fau = new FmsAdministrativeUnit();
								fau.setCreateDate(currentDate);
								fau.setCreatedBy(currentUserName);
							}
							fau.setCode(farmStoreDto.getAdministrativeUnitDto().getCode());
							fau.setLevel(farmStoreDto.getAdministrativeUnitDto().getLevel());
							fau.setName(farmStoreDto.getAdministrativeUnitDto().getName());

							farmStore.setAdministrativeUnit(fau);
						}
						farmStore.setFarm(farm);
						stores.add(farmStore);
					}
				}
			if (farm.getStores() == null) {
				farm.setStores(stores);
			} else {
				farm.getStores().clear();
				farm.getStores().addAll(stores);
			}
			/** FarmAnimal */
			Set<FarmAnimal> farmAnimals = new HashSet<FarmAnimal>();
			if (dto.getFarmAnimals() != null && dto.getFarmAnimals().size() > 0) {
				for (AnimalDto farmAnimalDto : dto.getFarmAnimals()) {
					if (farmAnimalDto != null) {
						Animal animal = null;
						if (farmAnimalDto.getId() != null) {
							animal = animalRepository.findOne(farmAnimalDto.getId());
						} else if (farmAnimalDto.getCode() != null) {
							List<Animal> ans = animalRepository.findByCode(farmAnimalDto.getCode());
							if (ans != null && ans.size() > 0) {
								animal = ans.get(0);
							}
						}

						if (animal != null) {
							FarmAnimal fpt = new FarmAnimal();
							fpt.setFarm(farm);
							fpt.setAnimal(animal);
							farmAnimals.add(fpt);
						}
					}

				}
				if (farmAnimals != null && farmAnimals.size() > 0) {
					if (farm.getFarmAnimals() == null) {
						farm.setFarmAnimals(farmAnimals);
					} else {
						farm.getFarmAnimals().clear();
						farm.getFarmAnimals().addAll(farmAnimals);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmAnimals() != null) {
					farm.getFarmAnimals().clear();
				}
			}
			farm.setBusinessRegistrationDate(dto.getBusinessRegistrationDate());
			farm.setBusinessRegistrationNumber(dto.getBusinessRegistrationNumber());
			farm.setStatus(dto.getStatus());
			/** FarmAnimalType */
			Set<FarmAnimalType> farmAnimalTypes = new HashSet<FarmAnimalType>();
			if (dto.getFarmAnimalTypes() != null && dto.getFarmAnimalTypes().size() > 0) {
				for (AnimalTypeDto farmAnimalTypeDto : dto.getFarmAnimalTypes()) {
					if (farmAnimalTypeDto != null) {
						AnimalType animalType = animalTypeRepository.findOne(farmAnimalTypeDto.getId());
						if (animalType != null) {
							FarmAnimalType fpt = new FarmAnimalType();
							fpt.setFarm(farm);
							fpt.setAnimalType(animalType);
							farmAnimalTypes.add(fpt);
						}
					}

				}
				if (farmAnimalTypes != null && farmAnimalTypes.size() > 0) {
					if (farm.getFarmAnimalTypes() == null) {
						farm.setFarmAnimalTypes(farmAnimalTypes);
					} else {
						farm.getFarmAnimalTypes().clear();
						farm.getFarmAnimalTypes().addAll(farmAnimalTypes);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmAnimalTypes() != null) {
					farm.getFarmAnimalTypes().clear();
				}
			}
			/////////// FarmCertificate
			Set<FarmCertificate> FarmCertificates = new HashSet<FarmCertificate>();
			if (dto.getFarmCertificates() != null && dto.getFarmCertificates().size() > 0) {
				for (CertificateDto farmCertificateDto : dto.getFarmCertificates()) {
					if (farmCertificateDto != null) {
						Certificate certificate = certificateRepository.findOne(farmCertificateDto.getId());
						if (certificate != null) {
							FarmCertificate fpt = new FarmCertificate();
							fpt.setFarm(farm);
							fpt.setCertificate(certificate);
							FarmCertificates.add(fpt);
						}
					}

				}
				if (FarmCertificates != null && FarmCertificates.size() > 0) {
					if (farm.getFarmCertificates() == null) {
						farm.setFarmCertificates(FarmCertificates);
					} else {
						farm.getFarmCertificates().clear();
						farm.getFarmCertificates().addAll(FarmCertificates);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (farm.getFarmCertificates() != null) {
					farm.getFarmCertificates().clear();
				}
			}

			////////////////////////////////////

			if (dto.getFarmSize() != null && dto.getFarmSize().getId() > 0) {
				FarmSize farmSize = this.farmSizeRepository.findOne(dto.getFarmSize().getId());
				if (farmSize != null) {
					farm.setFarmSize(farmSize);
				}
			}
			Farm farmTemp = this.farmRepository.save(farm);
			// khi thêm mới cơ sở tạo luôn tài khoản nông dân
			if (isNew == true) {
				if (farm.getCode() != null) {
					User user = null;
					user = userRepository.findByUsername(farm.getCode());
					if (user == null) {
						user = new User();
					}
					user.setUsername(farm.getCode());
					if (dto.getOwnerCitizenCode() != null) {
						String password = SecurityUtils.getHashPassword(dto.getOwnerCitizenCode());
						user.setPassword(password);
					} else {
						String password = SecurityUtils.getHashPassword("123456");
						user.setPassword(password);
					}
					if (dto.getOwnerEmail() != null) {
						user.setEmail(dto.getOwnerEmail());
					} else {
						user.setEmail(dto.getCode() + "@gmail.com");
					}
					user.setActive(true);
					HashSet<Role> temp = new HashSet<Role>();
					try {
						Role r = roleRepository.findByName(WLConstant.ROLE_FAMER);
						if (r != null) {
							temp.add(r);

						}

					} catch (Exception e) {
						// TODO: handle exception
					}

					if (user.getRoles() == null) {
						user.setRoles(temp);
					} else {
						user.getRoles().clear();
						user.getRoles().addAll(temp);
					}
					Person person = user.getPerson();
					if (person == null) {
						person = new Person();
					}
					if (dto.getName() != null) {
						person.setDisplayName(dto.getName());
					}

					person.setUser(user);
					user.setPerson(person);

					// Save
					user = userRepository.save(user);
					// save useradministrativeunit
					if (dto.getAdministrativeUnit() != null && user != null && user.getId() != null) {
						List<FmsAdministrativeUnitDto> listAdministrativeUnit = new ArrayList<FmsAdministrativeUnitDto>();
						listAdministrativeUnit.add(dto.getAdministrativeUnit());
						userAdministrativeUnitService.saveAdministrativeUnitByUserId(user.getId(),
								listAdministrativeUnit);
					}
				}

			}
			if (farmTemp.getId() != null) {
				dto.setId(farmTemp.getId());
			}

			return new FarmDto(farmTemp);
		}
		return null;
	}

	@Override
	public Boolean deleteFarmAnimalProductTargetExist() {
		List<FarmAnimalProductTargetExist> list = new ArrayList<FarmAnimalProductTargetExist>();
		list = farmAnimalProductTargetExistRepository.getAll();
		if (list != null && list.size() > 0) {
			farmAnimalProductTargetExistRepository.delete(list);
		}
		return null;
	}

	@Override
	public Boolean deleteFarmProductTargetExist() {
		List<FarmProductTargetExist> list = new ArrayList<FarmProductTargetExist>();
		list = farmProductTargetExistRepository.getAll();
		if (list != null && list.size() > 0) {
			farmProductTargetExistRepository.delete(list);
		}
		return null;
	}

	@Override
	public Boolean deleteFarmAnimalProductTargetExistByFarmId(Long farmId) {
		List<FarmAnimalProductTargetExist> list = new ArrayList<FarmAnimalProductTargetExist>();
		list = farmAnimalProductTargetExistRepository.getByFarm(farmId);
		if (list != null && list.size() > 0) {
			farmAnimalProductTargetExistRepository.delete(list);
		}
		return null;
	}

	@Override
	public Boolean deleteFarmProductTargetExistByFarmId(Long farmId) {
		List<FarmProductTargetExist> list = new ArrayList<FarmProductTargetExist>();
		list = farmProductTargetExistRepository.getByFarm(farmId);
		if (list != null && list.size() > 0) {
			farmProductTargetExistRepository.delete(list);
		}
		return null;
	}

	@Override
	public List<FarmDto> getFarmByUserLogin() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();

			String sql = "";
			boolean isAdmin = false;
			boolean isDLP = false; // cục chăn nuôi
			boolean isSDAH = false;// tỉnh
			boolean isDISTRICT = false;// huyện
			boolean isWARD = false;// xã
			boolean isFarmer = false;// nông dân

			if (modifiedUser.getRoles() != null && modifiedUser.getRoles().size() > 0) {
				for (Role role : modifiedUser.getRoles()) {
					if (role.getName().equals(WLConstant.ROLE_ADMIN)) {
						isAdmin = true;
					}
					if (role.getName().equals(WLConstant.ROLE_DLP)) {
						isDLP = true;
					}
					if (role.getName().equals(WLConstant.ROLE_SDAH)) {
						isSDAH = true;
					}
					if (role.getName().equals(WLConstant.ROLE_DISTRICT)) {
						isDISTRICT = true;
					}
					if (role.getName().equals(WLConstant.ROLE_WARD)) {
						isWARD = true;
					}
					if (role.getName().equals(WLConstant.ROLE_FAMER)) {
						isFarmer = true;
					}
				}
			}

			sql = "select new com.globits.wl.dto.FarmDto(fa) from Farm fa where (1=1) ";
			Long administrativeUnitId = null;

			if (isAdmin) {
				// Nếu là admin thì k cần thêm điều kiện gì vì xem được hết.
			} else if (isDLP) {
				// Nếu là cục chăn nuôi thì k cần thêm điều kiện gì vì xem được hết.
			} else if (isSDAH) {
				// Nếu là tỉnh thì cần thêm điều kiện gì vì xem được hết các trang trại thuộc
				// tỉnh user đăng nhập.
				List<FmsAdministrativeUnitDto> list = userAdministrativeUnitService
						.getAdministrativeUnitDtoByLoginUser();
				if (list != null && list.size() > 0) {
					administrativeUnitId = list.get(0).getId();

					sql += " and fa.administrativeUnit.parent.parent.id  = :provinceId";
				}
			} else if (isDISTRICT) {
				// Nếu là tỉnh thì cần thêm điều kiện gì vì xem được hết các trang trại thuộc
				// huyện user đăng nhập.
				List<FmsAdministrativeUnitDto> list = userAdministrativeUnitService
						.getAdministrativeUnitDtoByLoginUser();
				if (list != null && list.size() > 0) {
					administrativeUnitId = list.get(0).getId();

					sql += " and fa.administrativeUnit.parent.id = :districtId";
				}
			} else if (isWARD) {
				// Nếu là tỉnh thì cần thêm điều kiện gì vì xem được hết các trang trại thuộc xã
				// user đăng nhập.
				List<FmsAdministrativeUnitDto> list = userAdministrativeUnitService
						.getAdministrativeUnitDtoByLoginUser();
				if (list != null && list.size() > 0) {
					administrativeUnitId = list.get(0).getId();

					sql += " and fa.administrativeUnit.id = :wardId";
				}
			} else if (isFarmer) {
				// Nếu là farmer thì cần thêm điều kiện lọc theo code = username của farmer đăng
				// nhập.
				sql = "select new com.globits.wl.dto.FarmDto(fa) from Farm fa where fa.code =:username ";
			}

			Query q = manager.createQuery(sql, FarmDto.class);

			if (isFarmer && !isAdmin) {
				q.setParameter("username", modifiedUser.getUsername());
			}

			if (isSDAH && administrativeUnitId != null) {
				q.setParameter("provinceId", administrativeUnitId);
			}

			if (isDISTRICT && administrativeUnitId != null) {
				q.setParameter("districtId", administrativeUnitId);
			}

			if (isWARD && administrativeUnitId != null) {
				q.setParameter("wardId", administrativeUnitId);
			}

			return q.getResultList();
		}

		return null;
	}

	@Override
	public List<FarmReportDto> getSumQuantity(ReportParamDto paramDto) {
		if (paramDto == null) {
			return null;
		}
		String sql = " SELECT ";
		sql += "SUM(iea.totalAcreage), " + "SUM(iea.lodgingAcreage), " + "SUM(iea.maxNumberOfAnimal), "
				+ "SUM(iea.balanceNumber), " + "COUNT(iea.id) %s "; // Tính tổng diện tích,đếm số trang trại
		sql += "FROM Farm iea WHERE 1=1 ";
		Farm f = null;

		String whereClause = "";
		if (paramDto.getFromDate() != null) {
			whereClause += " AND iea.createDate >= :fromDate ";
		}
		if (paramDto.getToDate() != null) {
			whereClause += " AND iea.createDate <= :toDate ";
		}
		if (paramDto.getCode() != null && paramDto.getCode() != "") {
			whereClause += " AND iea.code like :code ";
		}
		if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
			whereClause += " AND iea.id = :farmId ";
		}
		if (paramDto.getOwnershipId() != null && paramDto.getOwnershipId() > 0) {
			whereClause += " AND iea.ownership.id= :ownershipId ";
		}
		if (paramDto.getWardId() != null && paramDto.getWardId() > 0) {
			whereClause += " AND iea.administrativeUnit.id = :wardId ";
		}
		if (paramDto.getDistrictId() != null && paramDto.getDistrictId() > 0) {
			whereClause += " AND iea.administrativeUnit.parent.id = :districtId ";
		}
		if (paramDto.getProvinceId() != null && paramDto.getProvinceId() > 0) {
			whereClause += " AND iea.administrativeUnit.parent.parent.id = :provinceId ";
		}
		if (paramDto.getRegionId() != null && paramDto.getRegionId() > 0) {
			whereClause += " AND iea.administrativeUnit.parent.parent.region.id = :regionId ";
		}
		if (paramDto.getStatus() != null) {
			whereClause += " AND iea.status = :status ";
		}

		String farm = " ";
		String ward = " ";
		String district = " ";
		String province = " ";
		String region = " ";
		String status = " ";
		String orderByClause = "";

		List<String> columns = new ArrayList<String>();
		columns.add(WLConstant.FunctionAndGroupByItem.totalAcreage.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.lodgingAcreage.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.maxNumberOfAnimal.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.balanceNumber.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.countFarm.getValue());

		String groupByClause = "";
		String selectClause = "";
		if (paramDto.getGroupByItems() != null && paramDto.getGroupByItems().size() > 0) {

			for (String grItem : paramDto.getGroupByItems()) {
				if (WLConstant.FunctionAndGroupByItem.status.getValue().equals(grItem)) {
					groupByClause += " iea.status, ";
					status = " ,iea.status ";
					selectClause += status;
					columns.add(WLConstant.FunctionAndGroupByItem.status.getValue());
				}
				if (WLConstant.FunctionAndGroupByItem.ward.getValue().equals(grItem)) {
					groupByClause += " iea.administrativeUnit.id,iea.administrativeUnit.name, ";
					ward = " ,iea.administrativeUnit.id,iea.administrativeUnit.name ";
					selectClause += ward;
					columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue() + "id");
					columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue() + "name");
				}
				if (WLConstant.FunctionAndGroupByItem.district.getValue().equals(grItem)) {
					groupByClause += " iea.administrativeUnit.parent.id,iea.administrativeUnit.parent.name, ";
					district = " ,iea.administrativeUnit.parent.id,iea.administrativeUnit.parent.name ";
					selectClause += district;
					columns.add(WLConstant.FunctionAndGroupByItem.district.getValue() + "id");
					columns.add(WLConstant.FunctionAndGroupByItem.district.getValue() + "name");
				}
				if (WLConstant.FunctionAndGroupByItem.province.getValue().equals(grItem)) {
					groupByClause += " iea.administrativeUnit.parent.parent.id,iea.administrativeUnit.parent.parent.name, ";
					province = " ,iea.administrativeUnit.parent.parent.id,iea.administrativeUnit.parent.parent.name ";
					selectClause += province;
					columns.add(WLConstant.FunctionAndGroupByItem.province.getValue() + "id");
					columns.add(WLConstant.FunctionAndGroupByItem.province.getValue() + "name");
				}
				if (WLConstant.FunctionAndGroupByItem.region.getValue().equals(grItem)) {
					groupByClause += " iea.administrativeUnit.parent.parent.region.id,iea.administrativeUnit.parent.parent.region.name, ";
					region = " ,iea.administrativeUnit.parent.parent.region.id,iea.administrativeUnit.parent.parent.region.name ";
					selectClause += region;
					columns.add(WLConstant.FunctionAndGroupByItem.region.getValue() + "id");
					columns.add(WLConstant.FunctionAndGroupByItem.region.getValue() + "name");
				}
			}
		}
		sql = String.format(sql, selectClause);
		if (groupByClause != "") {
			groupByClause = " GROUP BY " + groupByClause.substring(0, groupByClause.length() - 2);
		}

		sql = sql + whereClause + groupByClause;
		System.out.println(sql);
		Query q = manager.createQuery(sql);

		if (paramDto.getFromDate() != null) {
			q.setParameter("fromDate", paramDto.getFromDate());
		}
		if (paramDto.getToDate() != null) {
			q.setParameter("toDate", paramDto.getToDate());
		}
		if (paramDto.getCode() != null && paramDto.getCode() != "") {
			q.setParameter("code", paramDto.getCode());
		}
		if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
			q.setParameter("farmId", paramDto.getFarmId());
		}
		if (paramDto.getOwnershipId() != null && paramDto.getOwnershipId() > 0) {
			q.setParameter("ownershipId", paramDto.getOwnershipId());
		}
		if (paramDto.getWardId() != null && paramDto.getWardId() > 0) {
			q.setParameter("wardId", paramDto.getWardId());
		}
		if (paramDto.getDistrictId() != null && paramDto.getDistrictId() > 0) {
			q.setParameter("districtId", paramDto.getDistrictId());
		}
		if (paramDto.getProvinceId() != null && paramDto.getProvinceId() > 0) {
			q.setParameter("provinceId", paramDto.getProvinceId());
		}
		if (paramDto.getRegionId() != null && paramDto.getRegionId() > 0) {
			q.setParameter("regionId", paramDto.getRegionId());
		}
		if (paramDto.getStatus() != null) {
			q.setParameter("status", paramDto.getStatus());
		}

		List<Object[]> results = q.getResultList();
		List<FarmReportDto> ret = new ArrayList<FarmReportDto>();

		if (results != null) {
			for (Object[] re : results) {
				FarmReportDto io = new FarmReportDto(re, columns);
				if (paramDto.getDensityCoefficient() != null && paramDto.getDensityCoefficient() > 0) {
					io.setLivestockCapacityByLodging(io.getLodgingAcreage() * paramDto.getDensityCoefficient());
					io.setLivestockCapacityByTotal(io.getTotalAcreage() * paramDto.getDensityCoefficient());
				}
				ret.add(io);
			}
		}
		return ret;
	}

	@Override
	public void setCodeForAllFarm() {
		List<Farm> list = farmRepository.findNullCodeFarm();
		for (Farm farm : list) {
			if (farm.getCode() == null || farm.getCode().length() > 0) {
				if (farm.getAdministrativeUnit() != null && farm.getAdministrativeUnit().getParent() != null
						&& farm.getAdministrativeUnit().getParent().getParent() != null) {
					try {
						String code = this.autoGenericCode(farm.getAdministrativeUnit().getParent().getCode(),
								farm.getAdministrativeUnit().getParent().getId(),
								farm.getAdministrativeUnit().getParent().getParent().getCode());
//						System.out.println(code);
						farm.setCode(code);
						farmRepository.save(farm);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Override
	public FarmDto mergeFarm(Long farmId, Long duplicateFarmId) {
		// Xóa hết dữ liệu animalReportData của farm bị duplicate và chạy lại hàm kết
		// xuất dữ liệu của farm chính
		if (farmId != null && farmId > 0 && duplicateFarmId != null && duplicateFarmId > 0L) {
			Farm farm = farmRepository.findOne(farmId);
			Farm duplicateFarm = farmRepository.findOne(duplicateFarmId);
			if (farm != null && duplicateFarm != null) {
				List<AnimalReportData> list = animalReportDataRepository.getListEntityByFarmId(duplicateFarmId);
				if (list != null && list.size() > 0) {

					// xóa dữ liệu animalReportData của farm bị duplicate
					for (AnimalReportData animalReportData : list) {
						animalReportDataRepository.delete(animalReportData);
					}
					/*
					 * for (AnimalReportData animalReportData : list) {
					 * animalReportData.setFarm(farm); } animalReportDataRepository.save(list);
					 */
				}
				List<ReportPeriod> listPeriod = reportPeriodRepository.findByFarmId(duplicateFarmId);
				if (listPeriod != null && listPeriod.size() > 0) {
					for (ReportPeriod reportPeriod : listPeriod) {
						reportPeriod.setFarm(farm);
						if (reportPeriod.getReportItems() != null && reportPeriod.getReportItems().size() > 0) {
							for (ReportForm16 item : reportPeriod.getReportItems()) {
								item.setFarm(farm);
							}
						}
					}
					reportPeriodRepository.save(listPeriod);
				}
				List<ReportFormAnimalEgg> listReportFormAnimalEgg = reportFormAnimalEggRepository
						.getListByFarm(duplicateFarmId);
				if (listReportFormAnimalEgg != null && listReportFormAnimalEgg.size() > 0) {
					for (ReportFormAnimalEgg reportFormAnimalEgg : listReportFormAnimalEgg) {
						reportFormAnimalEgg.setFarm(farm);
					}
					reportFormAnimalEggRepository.save(listReportFormAnimalEgg);
				}
				List<ReportFormAnimalGiveBirth> listReportFormAnimalGiveBirth = reportFormAnimalGiveBirthRepository
						.getListByFarmId(duplicateFarmId);
				if (listReportFormAnimalGiveBirth != null && listReportFormAnimalGiveBirth.size() > 0) {
					for (ReportFormAnimalGiveBirth reportFormAnimalGiveBirth : listReportFormAnimalGiveBirth) {
						reportFormAnimalGiveBirth.setFarm(farm);
					}
					reportFormAnimalGiveBirthRepository.save(listReportFormAnimalGiveBirth);
				}
				List<ImportExportAnimal> listImEx = importExportAnimalRepository.findByFarm(duplicateFarmId);
				if (listImEx != null && listImEx.size() > 0) {
					for (ImportExportAnimal importExportAnimal : listImEx) {
						importExportAnimal.setFarm(farm);
					}
					importExportAnimalRepository.save(listImEx);
				}
			}

			// Kết xuất dữ liệu farm chính

			// get list year by farm chính Id
			List<Integer> listYear = reportPeriodRepository.getListYearByFarmId(farm.getId());
			if (listYear != null && listYear.size() > 0) {
				for (Integer year : listYear) {
					SearchReportPeriodDto searchDto = new SearchReportPeriodDto();
					searchDto.setFarmId(farm.getId());
					searchDto.setPageIndex(1);
					searchDto.setPageSize(100000);
					// Kết xuất ReportForm16 về AnimalReportData
					reportPeriodService.updateDataFromReportPeriodToAnimalReportDataForAll(searchDto);

					List<Long> listAnimalIdInAnimalReportData = reportForm16Repository
							.getListAnimalIdByFarmIdAndYear(farm.getId(), year);
					List<Long> listAnimalIds = new ArrayList<Long>();
//					
					if (listAnimalIdInAnimalReportData != null && listAnimalIdInAnimalReportData.size() > 0) {
						for (Long animalId : listAnimalIdInAnimalReportData) {
							listAnimalIds.add(animalId);
						}
					}
					// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
					animalReportDataService.updateAnimalReportDataByFarmAnimalYear(farm.getId(), listAnimalIds, year);
				}
			}

			// ========end Kết xuất dữ liệu farm chính

			duplicateFarm.setIsDuplicate(true);
			farmRepository.save(duplicateFarm);
			FarmMapDeleteDto farmMapDelete = new FarmMapDeleteDto();
			farmMapDelete.setCode(duplicateFarm.getCode());
			Integer year = 0;
			try {
				year = Integer.parseInt(duplicateFarm.getYearRegistration());
			} catch (Exception e) {
				year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			}
			farmMapDelete.setYear(year);
//			try {
			FarmMapServiceUtil.deleteFarmMap(farmMapDelete);
//			} catch (JSONException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			try {
				FarmMapServiceUtil.updateFarmToMap(farm);
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
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
			return new FarmDto(farm);
		}
		return null;
	}

	@Override
	public List<FarmDuplicateDoubtsDto> farmDupDoubt(String yearA, String yearB) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);

		List<FarmDuplicateDoubtsDto> results = new ArrayList<FarmDuplicateDoubtsDto>();
		String SQL = " SELECT d,f FROM Farm d inner join Farm f " + " ON d.name=f.name "
//				+ " AND d.yearRegistration=:yearA AND f.yearRegistration=:yearB "
				+ " AND (d.isDuplicate is null or d.isDuplicate <> true) "
				+ " AND (f.isDuplicate is null or f.isDuplicate <> true) " + " AND f.id<>d.id ";

		if (!isAdmin) {
			SQL += " AND d.administrativeUnit.id in (:listWard)  AND f.administrativeUnit.id in (:listWard) ";
		}
		SQL += " WHERE d.administrativeUnit.parent.id=f.administrativeUnit.parent.id ";

		Query q = manager.createQuery(SQL);
//		q.setParameter("yearA", yearA);
//		q.setParameter("yearB", yearB);
		if (!isAdmin) {
			List<Long> listWard = userAdministrativeUnitService.getListWardIdByLoginUser();
			q.setParameter("listWard", listWard);
		}
		List<Object[]> ret = q.getResultList();
		for (Object[] objects : ret) {
			if (objects.length == 2) {
				results.add(new FarmDuplicateDoubtsDto((Farm) objects[0], (Farm) objects[1]));
			}
		}
		return results;
	}

	@Override
	public List<FarmDuplicateDoubtsDto> farmDupDoubtCommune(String yearA, String yearB) {
		List<FarmDuplicateDoubtsDto> results = new ArrayList<FarmDuplicateDoubtsDto>();
		String SQL = " SELECT d,f FROM Farm d inner join Farm f " + " ON d.name=f.name "
				+ " AND d.yearRegistration=:yearA AND f.yearRegistration=:yearB "
				+ " AND (d.isDuplicate is null or d.isDuplicate <> true) "
				+ " AND (f.isDuplicate is null or f.isDuplicate <> true) "
				+ " WHERE d.administrativeUnit.id=f.administrativeUnit.id ";
		Query q = manager.createQuery(SQL);
		q.setParameter("yearA", yearA);
		q.setParameter("yearB", yearB);
		List<Object[]> ret = q.getResultList();
		for (Object[] objects : ret) {
			if (objects.length == 2) {
				results.add(new FarmDuplicateDoubtsDto((Farm) objects[0], (Farm) objects[1]));
			}
		}
		return results;
	}

	@Override
	public FarmDto getByIdSummary(Long id) {
		if (id != null) {
			Farm farm = this.farmRepository.getById(id);
			if (farm != null) {
				FarmDto dto = new FarmDto(farm);
				if (dto.getAnimalReportDatas() != null && dto.getAnimalReportDatas().size() > 0) {
					List<AnimalReportDataDto> animalReportDataSummarys = new ArrayList<AnimalReportDataDto>();
					Hashtable<Long, AnimalReportDataDto> hashAinimal = new Hashtable<Long, AnimalReportDataDto>();
					for (AnimalReportDataDto animalReportDataDto : dto.getAnimalReportDatas()) {
						AnimalReportDataDto item = hashAinimal.get(animalReportDataDto.getAnimal().getId());
						if (item == null) {
							item = new AnimalReportDataDto();
							item = animalReportDataDto;
							animalReportDataSummarys.add(item);
							hashAinimal.put(animalReportDataDto.getAnimal().getId(), item);
						} else {
							if (animalReportDataSummarys != null && animalReportDataSummarys.size() > 0) {
								for (int i = 0; i < animalReportDataSummarys.size(); i++) {
									if (animalReportDataSummarys.get(i) != null
											&& animalReportDataSummarys.get(i).getAnimal() != null
											&& animalReportDataSummarys.get(i).getAnimal().getId() != null
											&& animalReportDataSummarys.get(i).getAnimal().getId()
													.equals(animalReportDataDto.getAnimal().getId())) {
										if (animalReportDataDto.getYear() != null
												&& animalReportDataSummarys.get(i).getYear() != null
												&& animalReportDataDto.getYear() > animalReportDataSummarys.get(i)
														.getYear()) {
											animalReportDataSummarys.remove(i);
											animalReportDataSummarys.add(animalReportDataDto);
										} else if (animalReportDataDto.getYear() != null
												&& animalReportDataSummarys.get(i).getYear() != null
												&& animalReportDataSummarys.get(i).getYear()
														.equals(animalReportDataDto.getYear())
												&& animalReportDataDto.getMonth() != null
												&& animalReportDataSummarys.get(i).getMonth() != null
												&& animalReportDataDto.getMonth() > animalReportDataSummarys.get(i)
														.getMonth()) {
											animalReportDataSummarys.remove(i);
											animalReportDataSummarys.add(animalReportDataDto);
										}
									}
								}
							}
						}
					}
					if (animalReportDataSummarys != null && animalReportDataSummarys.size() > 0) {
						dto.setAnimalReportDataSummarys(animalReportDataSummarys);
					}
				}
				return dto;
			}
		}
		return null;
	}

	@Override
	public void updateAllFarmInfoToMap(int pageIndex, int pageSize) {
		Page<FarmDto> farms = this.getPageDto(pageIndex, pageSize);

		for (FarmDto farmDto : farms) {
			if (farmDto != null) {
				try {
					Farm farm = farmRepository.findOne(farmDto.getId());
					Object obj = FarmMapServiceUtil.createFarmTolMap(farm);

//					List<FarmAnimalTotalDto> dtos = animalReportDataService.getAllAnimalLastReported(farmDto.getId());
					/** Thay thế hàm báo cáo theo bản ghi null month , day */
					List<FarmAnimalTotalDto> dtos = animalReportDataService
							.getAllAnimalLastReportedByRecordMonthDayIsNull(farmDto.getId(), null);
					for (FarmAnimalTotalDto farmAnimalTotalDto : dtos) {
						if (farmAnimalTotalDto != null) {
							FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
						}
					}

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	@Override
	public void updateAllFarmInfoToMap(int pageIndex, int pageSize, int year, Long provinceId) {
		if (pageIndex < 1) {
			pageIndex = 0;
		} else {
			pageIndex = pageIndex - 1;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
//		List<Long> farmIds=animalReportDataRepository.getListFarmIdByYear(year);
		Page<Farm> farms = farmRepository.getListByYearWithReport(year, provinceId, pageable);

		for (Farm farm : farms) {
			try {
				Object obj = FarmMapServiceUtil.createFarmTolMap(farm);
//					List<FarmAnimalTotalDto> dtos = animalReportDataService.getAllAnimalLastReported(farmDto.getId());
				/** Thay thế hàm báo cáo theo bản ghi null month , day */
				List<FarmAnimalTotalDto> dtos = animalReportDataService
						.getAllAnimalLastReportedByRecordMonthDayIsNull(farm.getId(), null);
				for (FarmAnimalTotalDto farmAnimalTotalDto : dtos) {
					if (farmAnimalTotalDto != null) {
						FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
					}
				}

			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateAllFarmCreatedFarmToMap(int pageIndex, int pageSize) {
		Page<FarmDto> farms = this.getPageDto(pageIndex, pageSize);

		for (FarmDto farmDto : farms) {
			if (farmDto != null) {
				try {
					Farm farm = farmRepository.findOne(farmDto.getId());
					FarmMapServiceUtil.updateFarmToMap(farm);

					System.out.println("Sync: " + farmDto.getCode() + "*-*" + (new Date()));
					List<FarmAnimalTotalDto> dtos = animalReportDataService.getAllAnimalLastReported(farmDto.getId());
					/** Thay thế hàm báo cáo theo bản ghi null month , day */
//					List<FarmAnimalTotalDto> dtos = animalReportDataService.getAllAnimalLastReportedByRecordMonthDayIsNull(farmDto.getId());
					for (FarmAnimalTotalDto farmAnimalTotalDto : dtos) {
						if (farmAnimalTotalDto != null) {
							System.out.println("Sync: " + farmDto.getCode() + "*-*" + farmAnimalTotalDto.getAnimalCode()
									+ "*-*" + (new Date()));
							FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
						}
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					continue;
				}
			}
		}

	}

	@Override
	public void updateAllFarmCreatedFarmToMap(int pageIndex, int pageSize, int year, Long provinceId) {
		if (pageIndex < 1) {
			pageIndex = 0;
		} else {
			pageIndex = pageIndex - 1;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
//		List<Long> farmIds=animalReportDataRepository.getListFarmIdByYear(year);
		Page<Farm> farms = farmRepository.getListByYearWithReport(year, provinceId, pageable);
		for (Farm farm : farms.getContent()) {
			try {
				Object obj = FarmMapServiceUtil.updateFarmToMap(farm);

				System.out.println("Sync: " + farm.getCode() + "*-*" + (new Date()));
//				List<FarmAnimalTotalDto> dtos = animalReportDataService.getAllAnimalLastReported(farm.getId());
				/** Thay thế hàm báo cáo theo bản ghi null month , day */
				List<FarmAnimalTotalDto> dtos = animalReportDataService
						.getAllAnimalLastReportedByRecordMonthDayIsNull(farm.getId(), null);
				for (FarmAnimalTotalDto farmAnimalTotalDto : dtos) {
					if (farmAnimalTotalDto != null) {
						System.out.println("Sync: " + farm.getCode() + "*-*" + farmAnimalTotalDto.getAnimalCode()
								+ "*-*" + (new Date()));
						FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
					}
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				continue;
			}
		}
	}

	@Override
	public FarmDto updateCoordinates(String farmCode, String lat, String lng)
			throws ClientProtocolException, JSONException, IOException {
		List<Farm> ret = this.farmRepository.findByCode(farmCode);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWard = null;
		if (!isAdmin) {
			listWard = userAdministrativeUnitService.getListWardIdByLoginUser();
		}
		if (!isAdmin && (listWard == null || listWard.size() == 0)) {
			return null;
		}
		if (ret != null && ret.size() > 0) {

			Farm f = ret.get(0);
			if (!isAdmin && !listWard.contains(f.getAdministrativeUnit().getId())) {
				return null;
			}
			f.setLatitude(lat);
			f.setLongitude(lng);
			f = farmRepository.save(f);
			try {
				FarmMapServiceUtil.updateFarmToMap(f);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			return new FarmDto(f);
		}
		return null;
	}

	@Override
	public Page<DoubtsOverlapDto> searchByPageDoubtsOverlap(SearchDto dto) {
		Integer pageIndex = 0;
		Integer pageSize = 25;
		if (dto.getPageIndex() != null) {
			pageIndex = dto.getPageIndex();
		}
		if (dto.getPageSize() != null) {
			pageSize = dto.getPageSize();
		}

		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		String sql = " select new com.globits.wl.dto.DoubtsOverlapDto(fa.name, fa.administrativeUnit.name, fa.administrativeUnit.parent.name, fa.administrativeUnit.parent.parent.name, fa.administrativeUnit.id, fa.administrativeUnit.parent.id, fa.administrativeUnit.parent.parent.id) "
				+ "from Farm fa ";
		String sqlCount = "select count(fa.name) from Farm fa ";
		String whereClause = " where (1=1) ";
		String joinTable = "";
		String groupBy = " GROUP BY fa.name, fa.administrativeUnit.name, fa.administrativeUnit.parent.name, fa.administrativeUnit.parent.parent.name, "
				+ "fa.administrativeUnit.id, fa.administrativeUnit.parent.id, fa.administrativeUnit.parent.parent.id having count(fa.name) > 1 ";

		whereClause += " And fa.name IS NOT NULL ";

		sql += joinTable + whereClause + groupBy;
		sqlCount += joinTable + whereClause + groupBy;

		Query q = manager.createQuery(sql, DoubtsOverlapDto.class);

		q.setFirstResult((pageIndex) * pageSize);
		q.setMaxResults(pageSize);

		Page<DoubtsOverlapDto> page = new PageImpl<>(q.getResultList(), pageable, q.getResultList().size());
		return page;

	}

	@Override
	public FarmDto mergeFarmNew(Long farmId, Long duplicateFarmId, FarmDto farmMergeDto) {
		// Xóa hết dữ liệu animalReportData của farm bị duplicate, xóa những report16
		// không được chọn và chạy lại hàm kết xuất dữ liệu của farm chính
		if (farmId != null && farmId > 0 && duplicateFarmId != null && duplicateFarmId > 0L) {
			Farm farm = farmRepository.findOne(farmId);
			Farm duplicateFarm = farmRepository.findOne(duplicateFarmId);
			if (farm != null && duplicateFarm != null) {
				List<AnimalReportData> list = animalReportDataRepository.getListEntityByFarmId(duplicateFarmId);
				if (list != null && list.size() > 0) {

					// xóa dữ liệu animalReportData của farm bị duplicate
					for (AnimalReportData animalReportData : list) {
						animalReportDataRepository.delete(animalReportData);
					}
					/*
					 * for (AnimalReportData animalReportData : list) {
					 * animalReportData.setFarm(farm); } animalReportDataRepository.save(list);
					 */
				}
				List<ReportPeriod> listPeriod = reportPeriodRepository.findByFarmId(duplicateFarmId);
				if (listPeriod != null && listPeriod.size() > 0) {
					for (ReportPeriod reportPeriod : listPeriod) {
						reportPeriod.setFarm(farm);
						if (reportPeriod.getReportItems() != null && reportPeriod.getReportItems().size() > 0) {
							for (ReportForm16 item : reportPeriod.getReportItems()) {
								item.setFarm(farm);
							}
						}
					}
					reportPeriodRepository.save(listPeriod);
				}
				List<ReportFormAnimalEgg> listReportFormAnimalEgg = reportFormAnimalEggRepository
						.getListByFarm(duplicateFarmId);
				if (listReportFormAnimalEgg != null && listReportFormAnimalEgg.size() > 0) {
					for (ReportFormAnimalEgg reportFormAnimalEgg : listReportFormAnimalEgg) {
						reportFormAnimalEgg.setFarm(farm);
					}
					reportFormAnimalEggRepository.save(listReportFormAnimalEgg);
				}
				List<ReportFormAnimalGiveBirth> listReportFormAnimalGiveBirth = reportFormAnimalGiveBirthRepository
						.getListByFarmId(duplicateFarmId);
				if (listReportFormAnimalGiveBirth != null && listReportFormAnimalGiveBirth.size() > 0) {
					for (ReportFormAnimalGiveBirth reportFormAnimalGiveBirth : listReportFormAnimalGiveBirth) {
						reportFormAnimalGiveBirth.setFarm(farm);
					}
					reportFormAnimalGiveBirthRepository.save(listReportFormAnimalGiveBirth);
				}
				List<ImportExportAnimal> listImEx = importExportAnimalRepository.findByFarm(duplicateFarmId);
				if (listImEx != null && listImEx.size() > 0) {
					for (ImportExportAnimal importExportAnimal : listImEx) {
						importExportAnimal.setFarm(farm);
					}
					importExportAnimalRepository.save(listImEx);
				}
			}

			// Kết xuất dữ liệu farm chính

			// get list year by farm chính Id
			List<Integer> listYear = reportPeriodRepository.getListYearByFarmId(farm.getId());
			if (listYear != null && listYear.size() > 0) {
				for (Integer year : listYear) {
					SearchReportPeriodDto searchDto = new SearchReportPeriodDto();
					searchDto.setFarmId(farm.getId());
					searchDto.setPageIndex(1);
					searchDto.setPageSize(100000);
					// Kết xuất ReportForm16 về AnimalReportData
					reportPeriodService.updateDataFromReportPeriodToAnimalReportDataForAll(searchDto);

					List<Long> listAnimalIdInAnimalReportData = reportForm16Repository
							.getListAnimalIdByFarmIdAndYear(farm.getId(), year);
					List<Long> listAnimalIds = new ArrayList<Long>();
//							
					if (listAnimalIdInAnimalReportData != null && listAnimalIdInAnimalReportData.size() > 0) {
						for (Long animalId : listAnimalIdInAnimalReportData) {
							listAnimalIds.add(animalId);
						}
					}
					// Cập nhật bản ghi cuối cùng tổng số cả thế 1 loài theo farmId và year, type
					animalReportDataService.updateAnimalReportDataByFarmAnimalYear(farm.getId(), listAnimalIds, year);
				}
			}

			// ========end Kết xuất dữ liệu farm chính

			duplicateFarm.setIsDuplicate(true);
			farmRepository.save(duplicateFarm);
			FarmMapDeleteDto farmMapDelete = new FarmMapDeleteDto();
			farmMapDelete.setCode(duplicateFarm.getCode());
			Integer year = 0;
			try {
				year = Integer.parseInt(duplicateFarm.getYearRegistration());
			} catch (Exception e) {
				year = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
			}
			farmMapDelete.setYear(year);
//					try {
			FarmMapServiceUtil.deleteFarmMap(farmMapDelete);
//					} catch (JSONException | IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//					try {
//						FarmMapServiceUtil.updateFarmToMap(farm);
//						List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByRecordMonthDayIsNull(farm.getId(), null);
//						if(listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
//							for (FarmAnimalTotalDto farmAnimalTotalDto : listAnimalReportTotal) {
//								if(farmAnimalTotalDto != null) {
//									try {
//										FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
//									} catch (Exception e) {
//										//TODO: handle exception
//										e.printStackTrace();
//										continue;
//									}							
//								}
//							}						
//						}
//					} catch (Exception e) {
//						e.printStackTrace();
//						// TODO: handle exception
//					}

			FarmDto farmDto = null;
			try {
				if (farmMergeDto != null) {
					if (farmMergeDto.getCode() != null) {
						farm.setCode(farmMergeDto.getCode());
					}
					if (farmMergeDto.getName() != null) {
						farm.setName(farmMergeDto.getName());
					}
					if (farmMergeDto.getCode() != null) {
						farm.setCode(farmMergeDto.getCode());
					}
					if (farmMergeDto.getOwnerName() != null) {
						farm.setOwnerName(farmMergeDto.getOwnerName());
					}
					if (farmMergeDto.getOwnerPhoneNumber() != null) {
						farm.setOwnerPhoneNumber(farmMergeDto.getOwnerPhoneNumber());
					}
					// chua lam thay doi tinh huyen
					if (farmMergeDto.getLongitude() != null) {
						farm.setLongitude(farmMergeDto.getLongitude());
					}
					if (farmMergeDto.getLatitude() != null) {
						farm.setLatitude(farmMergeDto.getLatitude());
					}
					if (farmMergeDto.getStatus() != null) {
						farm.setStatus(farmMergeDto.getStatus());
					}
					if (farmMergeDto.getStatusFarm() != null) {
						farm.setStatusFarm(farmMergeDto.getStatusFarm());
					}
					if (farmMergeDto.getFoundingDate() != null) {
						farm.setFoundingDate(farmMergeDto.getFoundingDate());
					}
					if (farmMergeDto.getDateRegistration() != null) {
						farm.setDateRegistration(farmMergeDto.getDateRegistration());
					}
					if (farmMergeDto.getOldRegistrationCode() != null) {
						farm.setOldRegistrationCode(farmMergeDto.getOldRegistrationCode());
					}
					if (farmMergeDto.getNewRegistrationCode() != null) {
						farm.setNewRegistrationCode(farmMergeDto.getNewRegistrationCode());
					}
					if (farmMergeDto.getOwnerDob() != null) {
						farm.setOwnerDob(farmMergeDto.getOwnerDob());
					}
					if (farmMergeDto.getOwnerCitizenCode() != null) {
						farm.setOwnerCitizenCode(farmMergeDto.getOwnerCitizenCode());
					}
					if (farmMergeDto.getYearRegistration() != null) {
						farm.setYearRegistration(farmMergeDto.getYearRegistration());
					}
					if (farmMergeDto.getMapCode() != null) {
						farm.setMapCode(farmMergeDto.getMapCode());
					}
					if (farmMergeDto.getAdressDetail() != null) {
						farm.setAdressDetail(farmMergeDto.getAdressDetail());
					}
					if (farmMergeDto.getVillage() != null) {
						farm.setVillage(farmMergeDto.getVillage());
					}
					if (farmMergeDto.getWardsName() != null) {
						System.out.println(duplicateFarm);
						if (farmMergeDto.getWardsName().equals(duplicateFarm.getAdministrativeUnit().getName())) {
							farm.setAdministrativeUnit(duplicateFarm.getAdministrativeUnit());
						}
					} else {
						if (farmMergeDto.getDistrictName() != null) {
							if (farmMergeDto.getDistrictName()
									.equals(duplicateFarm.getAdministrativeUnit().getName())) {
								farm.setAdministrativeUnit(duplicateFarm.getAdministrativeUnit());
							}
						} else {
							if (farmMergeDto.getProvinceName() != null) {
								if (farmMergeDto.getProvinceName()
										.equals(duplicateFarm.getAdministrativeUnit().getName())) {
									farm.setAdministrativeUnit(duplicateFarm.getAdministrativeUnit());
								}
							}
						}
					}
				}
				farmDto = this.updateFarm(farm.getId(), new FarmDto(farm));
			} catch (Exception e) {
				System.out.println("lỗi lưu farm khi merge 2 farm");
			}
			if (farmDto != null) {
				return farmDto;
			}
			return new FarmDto(farm);
		}
		return null;
	}

}
