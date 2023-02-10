package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
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

import com.globits.core.domain.GlobalProperty;
import com.globits.core.repository.GlobalPropertyRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.service.RoleService;
import com.globits.security.service.UserService;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalProductTarget;
import com.globits.wl.domain.AnimalRequired;
import com.globits.wl.domain.AnimalRequiredProductTarget;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.dto.AnimalRequiredDto;
import com.globits.wl.dto.BiologicalClassDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.functiondto.AnimalSearchDto;
import com.globits.wl.dto.functiondto.UserUserAdministrativeUnitDto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.AnimalRequiredRepository;
import com.globits.wl.repository.AnimalTypeRepository;
import com.globits.wl.repository.BiologicalClassRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.service.AnimalRequiredService;
import com.globits.wl.service.AnimalTypeService;
import com.globits.wl.service.FmsUserService;
import com.globits.wl.service.SystemMessageService;
import com.globits.wl.utils.EmailUtil;
import com.globits.wl.utils.WLConstant;

@Service
public class AnimalRequiredServiceImpl extends GenericServiceImpl<AnimalRequired, Long> implements AnimalRequiredService{
	@Autowired
	private AnimalRequiredRepository repository;
	@Autowired 
	private AnimalRepository animalRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private OriginalRepository originalRepository;
	@Autowired
	private AnimalTypeRepository animalTypeRepository;
	@Autowired
	private ProductTargetRepository productTargetRepository;
	@Autowired
	AnimalTypeService animalTypeService;
	//tran huu dat khai bao de lay danh sach email can gui
	@Autowired
	private GlobalPropertyRepository globalPropertyRepository;
	
	@Autowired
	private SystemMessageService systemMessageService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private FmsUserService fmsUserService;
	//tran huu dat khai bao de lay danh sach email can gui
	
	@Autowired
	private BiologicalClassRepository biologicalClassRepository;
	
	
	@Override
	public Page<AnimalRequiredDto> getPageBySearch(AnimalSearchDto searchDto, int pageIndex, int pageSize) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		Boolean hasRole = false;
		User user  = userService.findEntityByUsername(currentUserName);
		if(user.getRoles() != null) {
			for(Role role: user.getRoles()) {
//				if(role.getName().equalsIgnoreCase(WLConstant.ROLE_ADMIN)) {
//					hasRole = true;
//				}
				if(role.getName().equalsIgnoreCase(WLConstant.ROLE_APPROVED_SPECIES)) {
					hasRole = true;
				}
//				else if(role.getName().equalsIgnoreCase(WLConstant.ROLE_SDAH)) {
//					hasRole = true;
//				}
			}
		}
		
		
		String sql = "select new com.globits.wl.dto.AnimalRequiredDto(ar) FROM AnimalRequired ar ";
		String sqlCount = "select count(*) FROM AnimalRequired ar ";
		String whereClause = " where 1 = 1";
		String orderBy = " order by ar.id desc";
		
		if(searchDto.getNameOrCode()!=null && searchDto.getNameOrCode().length()>0) {
			whereClause += " and (ar.code like :namecode or ar.name like :namecode or ar.scienceName like :namecode or ar.enName like :namecode) ";
		}
		if(searchDto.getAnimalClass()!=null && searchDto.getAnimalClass().length()>0) {
			whereClause +=" AND ar.animalClass like :animalClass ";			
		}
		if(searchDto.getOrdo()!=null && searchDto.getOrdo().length()>0) {
			whereClause +=" AND ar.ordo like :ordo ";			
		}
		if(searchDto.getFamily()!=null && searchDto.getFamily().length()>0) {
			whereClause +=" AND ar.family like :family ";			
		}
		if(searchDto.getAnimalClassSci()!=null && searchDto.getAnimalClassSci().length()>0) {
			whereClause +=" AND ar.animalClassSci like :animalClassSci ";			
		}
		if(searchDto.getOrdoSci()!=null && searchDto.getOrdoSci().length()>0) {
			whereClause +=" AND ar.ordoSci like :ordoSci ";			
		}
		if(searchDto.getFamilySci()!=null && searchDto.getFamilySci().length()>0) {
			whereClause +=" AND ar.familySci like :familySci ";			
		}
		if(searchDto.getAnimalRequiredStatus() != null) {
			whereClause += " AND ar.animalRequiredStatus = :animalRequiredStatus";
		}
		if(!hasRole &&  currentUserName != null) {
			whereClause += " AND ar.user.username = :userName";
		}
		
		Query query  = manager.createQuery(sql + whereClause + orderBy, AnimalRequiredDto.class);
		Query queryCount  = manager.createQuery(sqlCount + whereClause);
		
		if(searchDto.getNameOrCode()!=null && searchDto.getNameOrCode().length()>0) {
			query.setParameter("namecode", '%'+searchDto.getNameOrCode()+'%');
			queryCount.setParameter("namecode", '%'+searchDto.getNameOrCode()+'%');
		}
		//Lớp
		if(searchDto.getAnimalClass()!=null && searchDto.getAnimalClass().length()>0) {
			query.setParameter("animalClass", '%'+searchDto.getAnimalClass()+'%');
			queryCount.setParameter("animalClass", '%'+searchDto.getAnimalClass()+'%');	
		}
		//Bộ
		if(searchDto.getOrdo()!=null && searchDto.getOrdo().length()>0) {
			query.setParameter("ordo", '%'+searchDto.getOrdo()+'%');
			queryCount.setParameter("ordo", '%'+searchDto.getOrdo()+'%');
		}
		//HỌ
		if(searchDto.getFamily()!=null && searchDto.getFamily().length()>0) {
			query.setParameter("family", '%'+searchDto.getFamily()+'%');
			queryCount.setParameter("family", '%'+searchDto.getFamily()+'%');
		}
		
		//Lớp
		if(searchDto.getAnimalClassSci()!=null && searchDto.getAnimalClassSci().length()>0) {
			query.setParameter("animalClassSci", '%'+searchDto.getAnimalClassSci()+'%');
			queryCount.setParameter("animalClassSci", '%'+searchDto.getAnimalClassSci()+'%');	
		}
		//Bộ
		if(searchDto.getOrdoSci()!=null && searchDto.getOrdoSci().length()>0) {
			query.setParameter("ordoSci", '%'+searchDto.getOrdoSci()+'%');
			queryCount.setParameter("ordoSci", '%'+searchDto.getOrdoSci()+'%');
		}
		//HỌ
		if(searchDto.getFamilySci()!=null && searchDto.getFamilySci().length()>0) {
			query.setParameter("familySci", '%'+searchDto.getFamilySci()+'%');
			queryCount.setParameter("familySci", '%'+searchDto.getFamilySci()+'%');
		}
		
		if(searchDto.getAnimalRequiredStatus() != null) {
			query.setParameter("animalRequiredStatus", searchDto.getAnimalRequiredStatus());
			queryCount.setParameter("animalRequiredStatus", searchDto.getAnimalRequiredStatus());			
		}
		if(!hasRole &&  currentUserName != null) {
			query.setParameter("userName", currentUserName);
			queryCount.setParameter("userName", currentUserName);
		}
		if(pageIndex > 0) {
			pageIndex--;
		}else {
			pageIndex = 0;
		}
		query.setFirstResult(pageIndex* pageSize);
		query.setMaxResults(pageSize);
		Long total = 0L;
		Object obj = queryCount.getSingleResult();
		if(obj != null) {
			total = (Long)obj;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<AnimalRequiredDto> page = new PageImpl<AnimalRequiredDto>(query.getResultList(), pageable, total);
		return page;
	}

	@Override
	public AnimalRequiredDto getById(Long id) {
		AnimalRequired animalRequired = repository.findOne(id);
		return new AnimalRequiredDto(animalRequired);
	}

	@Override
	public AnimalRequiredDto createAnimalRequired(AnimalRequiredDto dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		
		User user = userService.findEntityByUsername(currentUserName);
		Boolean hasRole = false;
		if(user.getRoles() != null) {
			for(Role role: user.getRoles()) {
				if(role.getName().equalsIgnoreCase(WLConstant.ROLE_ADMIN)) {
					hasRole = true;
				}
//				else if(role.getName().equalsIgnoreCase(WLConstant.ROLE_SDAH)) {
//					hasRole = true;
//				}
			}
		}
		if(dto != null) {
			AnimalRequired animal = null;
			if (dto.getId() != null) {
				animal = this.repository.findOne(dto.getId());
			} else if (dto.getId() != null) {
				animal = this.repository.findOne(dto.getId());
			}
			if (animal == null) {
				animal = new AnimalRequired();
				animal.setCreateDate(currentDate);
				animal.setCreatedBy(currentUserName);
				animal.setUser(user);
				animal.setAnimalRequiredStatus(WLConstant.AnimalRequiredStatus.NEW.getValue());
			}else {
				animal.setModifyDate(currentDate);
				animal.setModifiedBy(currentUserName);
				
				if(animal.getUser() == null || (user.getId() != animal.getUser().getId() && !hasRole)) {
					return null;
				}
			}

			animal.setCode(dto.getCode());
			if (dto.getDescription() != null) {
				animal.setDescription(dto.getDescription());
			}
			animal.setName(dto.getName());
			if(dto.getOrderCode() != null) {
				animal.setOrderCode(dto.getOrderCode());
			}else {
				animal.setOrderCode(0);
			}
			//Mặc định ReportCode = Code
			if(dto.getReportCode()==null || dto.getReportCode()=="") {
				dto.setReportCode(dto.getCode());
			}
			//Mặc định ReportName = Name
			if(dto.getReportName()==null || dto.getReportName()=="") {
				dto.setReportName(dto.getName());
			}
			animal.setReportCode(dto.getReportCode());
			animal.setReportName(dto.getReportName());
			animal.setLiveStockMethod(dto.getLiveStockMethod());
			animal.setReportType(dto.getReportType());
			animal.setFarmingTime(dto.getFarmingTime());
			animal.setWeightGain(dto.getWeightGain());
			animal.setLoss(dto.getLoss());
			animal.setEggYield(dto.getEggYield());
			animal.setScienceName(dto.getScienceName());
			animal.setEnName(dto.getEnName());
			animal.setCites(dto.getCites());
			animal.setVnlist(dto.getVnlist());
			animal.setAniGroup(dto.getAniGroup());
			animal.setAnimalClass(dto.getAnimalClass());
			animal.setOldCode(dto.getOldCode());
			animal.setOrdo(dto.getOrdo());
			animal.setFamily(dto.getFamily());
			animal.setCites(dto.getCites());
			animal.setVnlist(dto.getVnlist());
			animal.setVnlist06(dto.getVnlist06());
			animal.setReproductionForm(dto.getReproductionForm());
			animal.setAnimalGroup(dto.getAnimalGroup());
			animal.setAnimalClassSci(dto.getAnimalClassSci());
			animal.setOrdoSci(dto.getOrdoSci());
			animal.setFamilySci(dto.getFamilySci());
			if (dto.getOriginalDto() != null) {
				Original original = null;
				if (dto.getOriginalDto().getId() != null) {
					original = this.originalRepository.findOne(dto.getOriginalDto().getId());
				}
				if (original == null) {
					original = new Original();
				}
				original.setCode(dto.getOriginalDto().getCode());
				original.setDescription(dto.getOriginalDto().getDescription());
				original.setName(dto.getOriginalDto().getName());
				animal.setOriginal(original);

			}else{
				animal.setOriginal(null);
			}
			if (dto.getTypeDto() != null) {
				AnimalType animalType = null;
				if (dto.getTypeDto().getId() != null) {
					animalType = this.animalTypeRepository.findOne(dto.getTypeDto().getId());
				}
				if (animalType == null) {
					animalType = new AnimalType();
//					animalType.setCreateDate(currentDate);
//					animalType.setCreatedBy(currentUserName);
				}
				animalType.setCode(dto.getTypeDto().getCode());
				animalType.setDescription(dto.getTypeDto().getDescription());
				animalType.setName(dto.getTypeDto().getName());
				animal.setType(animalType);

			}else{
				animal.setType(null);
			}
			
			/** AnimalProductTarget*/
			Set<AnimalRequiredProductTarget> animalProductTargets = new HashSet<AnimalRequiredProductTarget>();
			if (dto.getAnimalProductTargets() != null && dto.getAnimalProductTargets().size() > 0){
				for (ProductTargetDto farmAnimalTypeDto : dto.getAnimalProductTargets()) {
					if(farmAnimalTypeDto!=null) {						
						ProductTarget productTarget = productTargetRepository.findOne(farmAnimalTypeDto.getId());
						if(productTarget!=null) {
							AnimalRequiredProductTarget  fpt = new AnimalRequiredProductTarget();
							fpt.setAnimalRequired(animal);
							fpt.setProductTarget(productTarget);
							animalProductTargets.add(fpt);
						}
					}
					
				}
				if(animalProductTargets!=null && animalProductTargets.size()>0){
					if (animal.getAnimalProductTargets() == null) {
						animal.setAnimalProductTargets(animalProductTargets);
					} else {
						animal.getAnimalProductTargets().clear();
						animal.getAnimalProductTargets().addAll(animalProductTargets);
					}
				}				
			
			}else {//Nếu submit list trống lên thì xóa hết
				if(animal.getAnimalProductTargets()!=null) {
					animal.getAnimalProductTargets().clear();
				}
			}
			animal = this.repository.save(animal);
			
			//lay user co 2 quyen dc hien thong bao
			List<User> listUser= new ArrayList<>();
			
			List<UserDto> listUserRoleApproved=null;
			UserUserAdministrativeUnitDto searchDto= new UserUserAdministrativeUnitDto();
			Role roleApproved= roleService.findByName(WLConstant.ROLE_APPROVED_SPECIES);
			if(roleApproved!=null) {
				searchDto.setRoleId(roleApproved.getId());
				listUserRoleApproved= fmsUserService.findAllByRoleId(searchDto);
			}
			
			if(listUserRoleApproved!=null && listUserRoleApproved.size()>0) {
				for(UserDto userDto: listUserRoleApproved) {
					listUser.add(userService.findById(userDto.getId()));
				}
				
			}
//			if(listUser.size()>0) {
//				String listEmail= "";
//				for(User userEmail:listUser) {
//					listEmail+=userEmail.getEmail();
//					listEmail+=";";
//				}
//				String listEmailIsSended[]= listEmail.split(";");
//				EmailUtil.sendEmailNotification("Yêu cầu thêm loài", listEmailIsSended, "Yêu cầu thêm loài vật nuôi mới", new AnimalRequiredDto(animal));
//				
//			}
			
			//danh sach user duoc nhan thong bao
			dto.setId(animal.getId());
			
				
			
			systemMessageService.saveMessageAnimalRequired(new AnimalRequiredDto(animal));
			return dto;
			
		}
		return null;
	}

	@Override
	public AnimalRequiredDto updateRequired(AnimalRequiredDto dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		
		if(dto != null) {
			AnimalRequired animal = null;
			if (dto.getId() != null) {
				animal = this.repository.findOne(dto.getId());
			} else if (dto.getId() != null) {
				animal = this.repository.findOne(dto.getId());
			}
			if (animal == null) {
				animal = new AnimalRequired();
				animal.setCreateDate(currentDate);
				animal.setCreatedBy(currentUserName);
			}else {
				animal.setModifyDate(currentDate);
				animal.setModifiedBy(currentUserName);
			}

			animal.setCode(dto.getCode());
			if (dto.getDescription() != null) {
				animal.setDescription(dto.getDescription());
			}
			animal.setName(dto.getName());
			if(dto.getOrderCode() != null) {
				animal.setOrderCode(dto.getOrderCode());
			}else {
				animal.setOrderCode(0);
			}
			//Mặc định ReportCode = Code
			if(dto.getReportCode()==null || dto.getReportCode()=="") {
				dto.setReportCode(dto.getCode());
			}
			//Mặc định ReportName = Name
			if(dto.getReportName()==null || dto.getReportName()=="") {
				dto.setReportName(dto.getName());
			}
			animal.setReportCode(dto.getReportCode());
			animal.setReportName(dto.getReportName());
			animal.setLiveStockMethod(dto.getLiveStockMethod());
			animal.setReportType(dto.getReportType());
			animal.setFarmingTime(dto.getFarmingTime());
			animal.setWeightGain(dto.getWeightGain());
			animal.setLoss(dto.getLoss());
			animal.setEggYield(dto.getEggYield());
			animal.setScienceName(dto.getScienceName());
			animal.setEnName(dto.getEnName());
			animal.setCites(dto.getCites());
			animal.setVnlist(dto.getVnlist());
			animal.setAniGroup(dto.getAniGroup());
			animal.setAnimalClass(dto.getAnimalClass());
			animal.setOldCode(dto.getOldCode());
			animal.setOrdo(dto.getOrdo());
			animal.setFamily(dto.getFamily());
			animal.setCites(dto.getCites());
			animal.setVnlist(dto.getVnlist());
			animal.setVnlist06(dto.getVnlist06());
			animal.setReproductionForm(dto.getReproductionForm());
			animal.setAnimalGroup(dto.getAnimalGroup());
			if (dto.getOriginalDto() != null) {
				Original original = null;
				if (dto.getOriginalDto().getId() != null) {
					original = this.originalRepository.findOne(dto.getOriginalDto().getId());
				}
				if (original == null) {
					original = new Original();
				}
				original.setCode(dto.getOriginalDto().getCode());
				original.setDescription(dto.getOriginalDto().getDescription());
				original.setName(dto.getOriginalDto().getName());
				animal.setOriginal(original);

			}else{
				animal.setOriginal(null);
			}
			if (dto.getTypeDto() != null) {
				AnimalType animalType = null;
				if (dto.getTypeDto().getId() != null) {
					animalType = this.animalTypeRepository.findOne(dto.getTypeDto().getId());
				}
				if (animalType == null) {
					animalType = new AnimalType();
//					animalType.setCreateDate(currentDate);
//					animalType.setCreatedBy(currentUserName);
				}
				animalType.setCode(dto.getTypeDto().getCode());
				animalType.setDescription(dto.getTypeDto().getDescription());
				animalType.setName(dto.getTypeDto().getName());
				animal.setType(animalType);

			}else{
				animal.setType(null);
			}
			
			/** AnimalProductTarget*/
			Set<AnimalRequiredProductTarget> animalProductTargets = new HashSet<AnimalRequiredProductTarget>();
			if (dto.getAnimalProductTargets() != null && dto.getAnimalProductTargets().size() > 0){
				for (ProductTargetDto farmAnimalTypeDto : dto.getAnimalProductTargets()) {
					if(farmAnimalTypeDto!=null) {						
						ProductTarget productTarget = productTargetRepository.findOne(farmAnimalTypeDto.getId());
						if(productTarget!=null) {
							AnimalRequiredProductTarget  fpt = new AnimalRequiredProductTarget();
							fpt.setAnimalRequired(animal);
							fpt.setProductTarget(productTarget);
							animalProductTargets.add(fpt);
						}
					}
					
				}
				if(animalProductTargets!=null && animalProductTargets.size()>0){
					if (animal.getAnimalProductTargets() == null) {
						animal.setAnimalProductTargets(animalProductTargets);
					} else {
						animal.getAnimalProductTargets().clear();
						animal.getAnimalProductTargets().addAll(animalProductTargets);
					}
				}				
			
			}else {//Nếu submit list trống lên thì xóa hết
				if(animal.getAnimalProductTargets()!=null) {
					animal.getAnimalProductTargets().clear();
				}
			}
			
			animal.setAnimalRequiredStatus(dto.getAnimalRequiredStatus());
			
			animal = this.repository.save(animal);
			dto.setId(animal.getId());
			return dto;
			
		}
		return null;
	}

	@Override
	public AnimalRequiredDto animalRequiredApplyToAnimal(AnimalRequiredDto dto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		
		User user  = userService.findEntityByUsername(currentUserName);
		Boolean hasRole = false;
		//tran huu dat them quyen phe duyet
		if(user.getRoles() != null) {
			for(Role role: user.getRoles()) {
				if(role.getName().equalsIgnoreCase(WLConstant.ROLE_ADMIN) || role.getName().equalsIgnoreCase(WLConstant.ROLE_APPROVED_SPECIES)) {
					hasRole = true;
				}
			}
		}
		if(!hasRole) {
			return null;
		}
		
		this.updateRequired(dto);
		
		AnimalRequired animalRequired = null;
		if(dto != null && dto.getId() != null) {
			animalRequired = this.repository.findOne(dto.getId());			
		}
		if(animalRequired.getAnimalRequiredStatus() == null || animalRequired.getAnimalRequiredStatus().intValue() != WLConstant.AnimalRequiredStatus.PENDING.getValue().intValue()) {
			return null;// Only apply from animal PENDING
		}
		
		if(animalRequired != null) {
			Animal animal = null;
			
			if (animal == null) {
				animal = new Animal();
				animal.setCreateDate(currentDate);
				animal.setCreatedBy(currentUserName);
			}

			//animal.setCode(animalRequired.getCode());
			if (animalRequired.getDescription() != null) {
				animal.setDescription(animalRequired.getDescription());
			}
			animal.setName(animalRequired.getName());
			if(animalRequired.getOrderCode() != null) {
				animal.setOrderCode(animalRequired.getOrderCode());
			}else {
				animal.setOrderCode(0);
			}
			//Mặc định ReportCode = Code
			if(animalRequired.getReportCode()==null || animalRequired.getReportCode()=="") {
				animalRequired.setReportCode(animalRequired.getCode());
			}
			//Mặc định ReportName = Name
			if(animalRequired.getReportName()==null || animalRequired.getReportName()=="") {
				dto.setReportName(animalRequired.getName());
			}
			animal.setReportCode(animalRequired.getReportCode());
			animal.setReportName(animalRequired.getReportName());
			animal.setLiveStockMethod(animalRequired.getLiveStockMethod());
			animal.setReportType(animalRequired.getReportType());
			animal.setFarmingTime(animalRequired.getFarmingTime());
			animal.setWeightGain(animalRequired.getWeightGain());
			animal.setLoss(animalRequired.getLoss());
			animal.setEggYield(animalRequired.getEggYield());
			animal.setScienceName(animalRequired.getScienceName());
			animal.setEnName(animalRequired.getEnName());
			animal.setCites(animalRequired.getCites());
			animal.setVnlist(animalRequired.getVnlist());
			animal.setAniGroup(animalRequired.getAniGroup());
			animal.setAnimalGroup(animalRequired.getAnimalGroup());
			
			animal.setOldCode(animalRequired.getOldCode());
			animal.setOrdo(animalRequired.getOrdo());
			animal.setFamily(animalRequired.getFamily());
			animal.setCites(animalRequired.getCites());
			animal.setVnlist(animalRequired.getVnlist());
			animal.setVnlist06(animalRequired.getVnlist06());
			animal.setReproductionForm(animalRequired.getReproductionForm());
			
			// Tạo hash map để chuyển đổi dữ liệu từ Lớp, bộ, họ khoa học => tiếng việt
			// với yêu cầu thêm loài(chỉ có tên Latin, ko có tên tiếng việt)
			if(animalRequired.getAnimalClassSci() == null) {
				return null;
			}
			List<BiologicalClassDto> listBiologicalClass = biologicalClassRepository.getBiologicalClassBySci(animalRequired.getAnimalClassSci(), WLConstant.BiologicalClass.animalClass.getValue());
			if(listBiologicalClass != null && listBiologicalClass.size() > 0) {
				for(BiologicalClassDto bDto : listBiologicalClass) {
					if(bDto != null && bDto.getName().trim().length() > 0 ) {
						animal.setAnimalClass(bDto.getName().trim());
						
						// lấy theo mã của file import
						long count = animalRepository.count();
						count = count + 1;
						
						String code = "";
						String animalClass = animal.getAnimalClass().trim().toLowerCase();
						if(animalClass.equals("Lớp lưỡng cư".trim().toLowerCase())) {
							code = "A";
						} else if(animalClass.equals("Lớp bò sát".trim().toLowerCase())) {
							code = "R";
						} else if(animalClass.equals("Lớp chim".trim().toLowerCase())) {
							code = "B";
						} else if(animalClass.equals("Lớp cá vây tia".trim().toLowerCase())) {
							code = "F";
						} else if(animalClass.equals("Lớp côn trùng".trim().toLowerCase())) {
							code = "CT";
						} else if(animalClass.equals("Lớp hình nhện".trim().toLowerCase())) {
							code = "CT";
						} else if(animalClass.equals("Lớp thú".trim().toLowerCase())) {
							code = "M";
						} else {
							code = "A";
						}
						
						// ưu tiên chữ cái đầu trong bảng BiologicalClass
						if(bDto.getCode() != null) {
							code = bDto.getCode();
						}
						
						animal.setCode(code + count);
						break;
					}
				}
			}
			
			if(animal.getAnimalClass() == null || animal.getCode() == null) {
				return null;
			}
			
			if(animalRequired.getOrdoSci() != null) {
				List<BiologicalClassDto> listOrdo = biologicalClassRepository.getBiologicalClassBySci(animalRequired.getOrdoSci(),  WLConstant.BiologicalClass.ordo.getValue());
				if(listOrdo != null && listOrdo.size() > 0) {
					for(BiologicalClassDto bDto : listOrdo) {
						if(bDto != null && bDto.getName().trim().length() > 0 ) {
							animal.setOrdo(bDto.getName().trim());
							break;
						}
					}
				}
			}
			
			if(animalRequired.getFamilySci() != null) {
				List<BiologicalClassDto> listAnimalFamily = biologicalClassRepository.getBiologicalClassBySci(animalRequired.getFamilySci(),  WLConstant.BiologicalClass.family.getValue());
				if(listAnimalFamily != null && listAnimalFamily.size() > 0) {
					for(BiologicalClassDto bDto : listAnimalFamily) {
						if(bDto != null && bDto.getName().trim().length() > 0 ) {
							animal.setFamily(bDto.getName().trim());
							break;
						}
					}
				}
			}
			
			if (animalRequired.getOriginal() != null) {
				Original original = null;
				if (animalRequired.getOriginal().getId() != null) {
					original = this.originalRepository.findOne(animalRequired.getOriginal().getId());
				}
				if (original == null) {
					original = new Original();
				}
				original.setCode(animalRequired.getOriginal().getCode());
				original.setDescription(animalRequired.getOriginal().getDescription());
				original.setName(animalRequired.getOriginal().getName());
				animal.setOriginal(original);

			}else{
				animal.setOriginal(null);
			}
			if (animalRequired.getType() != null) {
				AnimalType animalType = null;
				if (animalRequired.getType().getId() != null) {
					animalType = this.animalTypeRepository.findOne(animalRequired.getType().getId());
				}
				if (animalType == null) {
					animalType = new AnimalType();
//					animalType.setCreateDate(currentDate);
//					animalType.setCreatedBy(currentUserName);
				}
				animalType.setCode(animalRequired.getType().getCode());
				animalType.setDescription(animalRequired.getType().getDescription());
				animalType.setName(animalRequired.getType().getName());
				animal.setType(animalType);

			}else{
				animal.setType(null);
			}
			
			/** AnimalProductTarget*/
			Set<AnimalProductTarget> animalProductTargets = new HashSet<AnimalProductTarget>();
			if (animalRequired.getAnimalProductTargets() != null && animalRequired.getAnimalProductTargets().size() > 0){
				for (AnimalRequiredProductTarget animalRequiredProductTarget : animalRequired.getAnimalProductTargets()) {
					if(animalRequiredProductTarget!=null) {						
						ProductTarget productTarget = animalRequiredProductTarget.getProductTarget();
						if(productTarget!=null) {
							AnimalProductTarget  fpt = new AnimalProductTarget();
							fpt.setAnimal(animal);
							fpt.setProductTarget(productTarget);
							animalProductTargets.add(fpt);
						}
					}
					
				}
				if(animalProductTargets!=null && animalProductTargets.size()>0){
					if (animal.getAnimalProductTargets() == null) {
						animal.setAnimalProductTargets(animalProductTargets);
					} else {
						animal.getAnimalProductTargets().clear();
						animal.getAnimalProductTargets().addAll(animalProductTargets);
					}
				}				
			
			}else {//Nếu submit list trống lên thì xóa hết
				if(animal.getAnimalProductTargets()!=null) {
					animal.getAnimalProductTargets().clear();
				}
			}
//			String email="";
//			if(animalRequired.getUser()!=null && animalRequired.getUser().getEmail()!=null) {
//				email=animalRequired.getUser().getEmail();
//			}
//			String listEmail[]= email.split(" ");
			
			animalRequired.setAnimalRequiredStatus(WLConstant.AnimalRequiredStatus.DONE.getValue());// AnimalRequiredStatus => DONE
			this.repository.save(animalRequired);
			
			animal = this.animalRepository.save(animal);
			
			dto.setId(animal.getId());
			
			
			
			
			List<User> listUser= new ArrayList<>();
			
			List<UserDto> listUserRoleApproved=null;
			UserUserAdministrativeUnitDto searchDto= new UserUserAdministrativeUnitDto();
			Role roleApproved= roleService.findByName(WLConstant.ROLE_APPROVED_SPECIES);
			if(roleApproved!=null) {
				searchDto.setRoleId(roleApproved.getId());
				listUserRoleApproved= fmsUserService.findAllByRoleId(searchDto);
			}
			
			if(listUserRoleApproved!=null && listUserRoleApproved.size()>0) {
				for(UserDto userDto: listUserRoleApproved) {
					listUser.add(userService.findById(userDto.getId()));
				}
				
			}
			
			if(animalRequired.getAnimalRequiredStatus()==2 || animalRequired.getAnimalRequiredStatus()==3) {
				
				User currentUser= userService.findById(modifiedUser.getId());
				if(currentUser.getRoles() != null) {
					for(Role role: currentUser.getRoles()) {					
						if(role.getName().equalsIgnoreCase(WLConstant.ROLE_APPROVED_SPECIES)) {
							listUser.remove(currentUser);
						}			
					}
				}
			}
			if(animalRequired.getUser()!=null) {
				listUser.add(animalRequired.getUser());
			}
			if(listUser.size()>0) {
				String listEmail= "";
				for(User userEmail:listUser) {
					listEmail+=userEmail.getEmail();
					listEmail+=";";
				}
				String listEmailIsSended[]= listEmail.split(";");
				EmailUtil.sendEmailNotificationAccept("Phản hồi yêu cầu thêm loài", listEmailIsSended, "Phản hồi yêu cầu thêm loài", new AnimalRequiredDto(animalRequired));
				
			}
			
//			if(listEmail.length>0) {
//				EmailUtil.sendEmailNotificationAccept("Phản hồi yêu cầu thêm loài", listEmail, "Phản hồi yêu cầu thêm loài", new AnimalRequiredDto(animalRequired));
//			}
			systemMessageService.saveMessageAnimalRequired(new AnimalRequiredDto(animalRequired));
			return dto;
			
		}
		return null;
	}

	@Override
	public AnimalRequiredDto changeAnimalRequiredStatus(AnimalRequiredDto dto) {
		AnimalRequired animalRequired = null;
		if(dto.getId() != null) {
			
			animalRequired = this.repository.getOne(dto.getId());
			animalRequired.setFeedBack(dto.getFeedBack());
			animalRequired.setAnimalRequiredStatus(dto.getAnimalRequiredStatus());
			animalRequired = this.repository.save(animalRequired);
			if(dto.getAnimalRequiredStatus()==3) {
				systemMessageService.saveMessageAnimalRequired(new AnimalRequiredDto(animalRequired));
			}
			
			if(dto.getAnimalRequiredStatus()==1) {
				List<User> listUser= new ArrayList<>();
				
				List<UserDto> listUserRoleApproved=null;
				UserUserAdministrativeUnitDto searchDto= new UserUserAdministrativeUnitDto();
				Role roleApproved= roleService.findByName(WLConstant.ROLE_APPROVED_SPECIES);
				if(roleApproved!=null) {
					searchDto.setRoleId(roleApproved.getId());
					listUserRoleApproved= fmsUserService.findAllByRoleId(searchDto);
				}
				
				if(listUserRoleApproved!=null && listUserRoleApproved.size()>0) {
					for(UserDto userDto: listUserRoleApproved) {
						listUser.add(userService.findById(userDto.getId()));
					}
					
				}
				if(listUser.size()>0) {
					String listEmail= "";
					for(User userEmail:listUser) {
						listEmail+=userEmail.getEmail();
						listEmail+=";";
					}
					String listEmailIsSended[]= listEmail.split(";");
					EmailUtil.sendEmailNotification("Yêu cầu thêm loài", listEmailIsSended, "Yêu cầu thêm loài vật nuôi mới", new AnimalRequiredDto(animalRequired));
					
				}
			}
			if(dto.getAnimalRequiredStatus()==3) {
				
				List<User> listUser= new ArrayList<>();
				
				List<UserDto> listUserRoleApproved=null;
				UserUserAdministrativeUnitDto searchDto= new UserUserAdministrativeUnitDto();
				Role roleApproved= roleService.findByName(WLConstant.ROLE_APPROVED_SPECIES);
				if(roleApproved!=null) {
					searchDto.setRoleId(roleApproved.getId());
					listUserRoleApproved= fmsUserService.findAllByRoleId(searchDto);
				}
				
				if(listUserRoleApproved!=null && listUserRoleApproved.size()>0) {
					for(UserDto userDto: listUserRoleApproved) {
						listUser.add(userService.findById(userDto.getId()));
					}
					
				}
				
				if(animalRequired.getAnimalRequiredStatus()==2 || animalRequired.getAnimalRequiredStatus()==3) {
					Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
					User modifiedUser = null;
					if (authentication != null) {
						modifiedUser = (User) authentication.getPrincipal();
					}
					User currentUser= userService.findById(modifiedUser.getId());
					if(currentUser.getRoles() != null) {
						for(Role role: currentUser.getRoles()) {					
							if(role.getName().equalsIgnoreCase(WLConstant.ROLE_APPROVED_SPECIES)) {
								listUser.remove(currentUser);
							}			
						}
					}
				}
				if(animalRequired.getUser()!=null) {
					listUser.add(animalRequired.getUser());
				}
				if(listUser.size()>0) {
					String listEmail= "";
					for(User userEmail:listUser) {
						listEmail+=userEmail.getEmail();
						listEmail+=";";
					}
					String listEmailIsSended[]= listEmail.split(";");
					EmailUtil.sendEmailNotificationAccept("Phản hồi yêu cầu thêm loài", listEmailIsSended, "Phản hồi yêu cầu thêm loài", new AnimalRequiredDto(animalRequired));
					
				}
			}
			return new AnimalRequiredDto(animalRequired);
		}
		return null;
	}
	
	
}
