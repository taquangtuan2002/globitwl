package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.service.RoleService;
import com.globits.wl.domain.AnimalRequired;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.FmsAdministrativeUnitSystemMessage;
import com.globits.wl.domain.ForestProductsList;
import com.globits.wl.domain.SystemMessage;
import com.globits.wl.domain.UserAdministrativeUnit;
import com.globits.wl.domain.UserViewedNotification;
import com.globits.wl.dto.AnimalRequiredDto;
import com.globits.wl.dto.FmsAdministrativeUnitSystemMessageDto;
import com.globits.wl.dto.ForestProductsListDto;
import com.globits.wl.dto.SystemMessageDto;
import com.globits.wl.dto.UserViewedNotificationDto;
import com.globits.wl.dto.functiondto.UserUserAdministrativeUnitDto;
import com.globits.wl.repository.AnimalRequiredRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.FmsAdministrativeUnitSystemMessageRepository;
import com.globits.wl.repository.ForestProductsListRepository;
import com.globits.wl.repository.SystemMessageRepository;
import com.globits.wl.repository.UserViewedNotificationRepository;
import com.globits.wl.service.FmsUserService;
import com.globits.wl.service.SystemMessageService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.service.UserViewedNotificationService;
import com.globits.wl.utils.WLConstant;

@Service
public class SystemMessageServiceImpl extends GenericServiceImpl<SystemMessage, Long> implements SystemMessageService {

	@Autowired
	private UserViewedNotificationRepository userViewedNotificationRepository;
	
	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;
	
	@Autowired
	private UserViewedNotificationService userViewedNotificationService;
	
	@Autowired
	private FmsAdministrativeUnitSystemMessageRepository fmsAdministrativeUnitSystemMessageRepository;
	
	@Autowired
	private SystemMessageRepository systemMessageRepository;
	
	@Autowired
	private ForestProductsListRepository forestProductsListRepository;
	
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	
	@Autowired
	private AnimalRequiredRepository animalRequiredRepository;
	
	@Autowired
	private FmsUserService userService;
	
	@Autowired
	private RoleService roleService;
	
	
	@Override
	public SystemMessageDto saveOrUpdate(SystemMessageDto dto) {
		if(dto!=null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			
			SystemMessage entity= null;
			if(dto.getId()!=null) {
				entity= systemMessageRepository.findOne(dto.getId());
			}
			if(entity==null) {
				entity= new SystemMessage();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			}else {
				entity.setModifiedBy(currentUserName);
				entity.setModifyDate(currentDate);
			}
			entity.setContent(dto.getContent());
			entity.setTitle(dto.getTitle());
			entity.setRecordId(dto.getRecordId());
			entity.setStatus(dto.getStatus());
			entity.setType(dto.getType());
			entity.setRoles(dto.getRoles());
			entity.setTableName("");
			entity.setSender(modifiedUser);
			//danh sach co so duoc nhan thong bao
			Set<FmsAdministrativeUnitSystemMessage> fmsAdministrativeUnits = new HashSet<FmsAdministrativeUnitSystemMessage>();
			if(dto.getFmsAdministrativeUnits()!=null  && dto.getFmsAdministrativeUnits().size()>0) {
				for(FmsAdministrativeUnitSystemMessageDto fmsAdmindto: dto.getFmsAdministrativeUnits()) {
					FmsAdministrativeUnitSystemMessage fmsAdminEntity= null;
					if(fmsAdmindto.getId()!=null) {
						fmsAdminEntity= fmsAdministrativeUnitSystemMessageRepository.findOne(fmsAdmindto.getId());
						fmsAdministrativeUnits.add(fmsAdminEntity);
					}else {
						fmsAdminEntity= new FmsAdministrativeUnitSystemMessage(); 
						fmsAdminEntity.setCreateDate(currentDate);
						fmsAdminEntity.setCreatedBy(currentUserName);
						
						fmsAdminEntity.setSystemMessage(entity);
						fmsAdminEntity.setAdministrativeUnit(fmsAdministrativeUnitRepository.findById(fmsAdmindto.getAdministrativeUnit().getId()));
					}
					fmsAdministrativeUnits.add(fmsAdminEntity);
				}
			}
			if(entity.getFmsAdministrativeUnits()==null) {
				entity.setFmsAdministrativeUnits(fmsAdministrativeUnits);
			}else {
				entity.getFmsAdministrativeUnits().clear();
				entity.setFmsAdministrativeUnits(fmsAdministrativeUnits);
			}
			//danh sach user duoc nhan thong bao
			Set<UserViewedNotification> userViewedNotifications = new HashSet<UserViewedNotification>();
			if(dto.getUserViewedNotifications()!=null && dto.getUserViewedNotifications().size()>0) {
				for(UserViewedNotificationDto userViewedDto: dto.getUserViewedNotifications()) {
					UserViewedNotification userViewedEntity= null;
					if(userViewedDto.getId()!=null) {
						userViewedEntity= userViewedNotificationRepository.findOne(userViewedDto.getId());
						userViewedNotifications.add(userViewedEntity);
					}else {
						userViewedEntity= new UserViewedNotification();
						userViewedDto= userViewedNotificationService.saveOrUpdate(userViewedDto);
						userViewedEntity= userViewedNotificationRepository.findOne(userViewedDto.getId());
					}
					userViewedNotifications.add(userViewedEntity);
				}
			}
			if(entity.getUserViewedNotifications()==null) {
				entity.setUserViewedNotifications(userViewedNotifications);
			}else {
				entity.getUserViewedNotifications().clear();
				entity.setUserViewedNotifications(userViewedNotifications);
			}
			entity= systemMessageRepository.save(entity);
			return new SystemMessageDto(entity);
		}
		return dto;
	}

	@Override
	public List<SystemMessageDto> findAll() {
		List<SystemMessage> listEntity= systemMessageRepository.findAll();
		List<SystemMessageDto> listDto= new ArrayList<SystemMessageDto>();
		if(listEntity!=null && listEntity.size()>0) {
			for(SystemMessage systemMessage: listEntity) {
				SystemMessageDto dto= new SystemMessageDto(systemMessage);
				listDto.add(dto);
			}
		}
		return listDto;
	}

	@Override
	public SystemMessageDto findById(long id) {
		SystemMessage entity= null;
		entity= systemMessageRepository.findOne(id);
		if(entity!=null) {
			SystemMessageDto dto= new SystemMessageDto(entity);
			return dto;
		}
		return null;
	}

	@Override
	public SystemMessageDto saveMessageForestProduct(ForestProductsListDto forestProductDto) {
		if( forestProductDto!=null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			SystemMessage entity= null;
			if(entity==null) {
				entity= new SystemMessage();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			}
			ForestProductsList forestProductsListEntity=null;
			if(forestProductDto.getId()!=null) {
				forestProductsListEntity= forestProductsListRepository.findOne(forestProductDto.getId());
			}
			FmsAdministrativeUnit fmsAdministrativeUnitTo= null;
			fmsAdministrativeUnitTo= forestProductsListEntity.getAdministrativeUnitTo();
			
			List<UserAdministrativeUnit> listUserAdministrativeUnits= null;
			if(fmsAdministrativeUnitTo != null && fmsAdministrativeUnitTo.getId() != null)
				listUserAdministrativeUnits= userAdministrativeUnitService.getListUserByAdministrativeUnitId(fmsAdministrativeUnitTo);
			
			List<User> listUser= new ArrayList<User>();
			if(listUserAdministrativeUnits!=null && listUserAdministrativeUnits.size()>0) {
				for(UserAdministrativeUnit userAdminUnit: listUserAdministrativeUnits) {
					listUser.add(userAdminUnit.getUser());
				}
			}
			
			if(forestProductDto.getAdministrativeUnitFrom()!=null) {
				entity.setContent("có bản kê lâm sản mới của "+forestProductDto.getAdministrativeUnitFrom().getName());
			}
			
			entity.setTitle("Có bảng kê lâm sản mới");
			entity.setRecordId(forestProductDto.getId());
			entity.setStatus(1);
			entity.setType(1);
			entity.setSender(modifiedUser);
			entity.setTableName("ForestProductsList");
			//danh sach co so duoc nhan thong bao
			Set<FmsAdministrativeUnitSystemMessage> fmsAdministrativeUnits = new HashSet<FmsAdministrativeUnitSystemMessage>();
			if(fmsAdministrativeUnitTo!=null) {
				FmsAdministrativeUnitSystemMessage fmsAdminEntity= null;
				fmsAdminEntity= new FmsAdministrativeUnitSystemMessage(); 
				fmsAdminEntity.setCreateDate(currentDate);
				fmsAdminEntity.setCreatedBy(currentUserName);
				fmsAdminEntity.setSystemMessage(entity);
				fmsAdminEntity.setAdministrativeUnit(fmsAdministrativeUnitRepository.findById(fmsAdministrativeUnitTo.getId()));
				fmsAdministrativeUnits.add(fmsAdminEntity);
			}
			entity.setFmsAdministrativeUnits(fmsAdministrativeUnits);
			
			//danh sach user duoc nhan thong bao
			Set<UserViewedNotification> userViewedNotifications = new HashSet<UserViewedNotification>();
			if(listUser.size()>0) {
				for(User user: listUser) {
					UserViewedNotification userViewedEntity= new UserViewedNotification();
					userViewedEntity.setUser(user);
					userViewedEntity.setSystemMessage(entity);
					userViewedNotifications.add(userViewedEntity);
				}
			}
			
			entity.setUserViewedNotifications(userViewedNotifications);
			StringBuilder roles= new StringBuilder();
			for(User user: listUser) {
				for(Role role: user.getRoles()) {
					if(role.getName().equals("ROLE_SDAH")) {
						roles.append(role.getName()+" ");
					}
				}
			}
			entity.setRoles(roles.toString());
			
			entity= systemMessageRepository.save(entity);
			
//			if(listUser.size()>0) {
//				for(User user: listUser) {
//					UserViewedNotification userViewedEntity= new UserViewedNotification();
//					userViewedEntity.setUser(user);
//					userViewedEntity.setSystemMessage(entity);
//					UserViewedNotificationDto userViewedDto= new UserViewedNotificationDto(userViewedEntity);
//					userViewedDto= userViewedNotificationService.saveOrUpdate(userViewedDto);
//					userViewedEntity= userViewedNotificationRepository.findOne(userViewedDto.getId());
//					userViewedNotifications.add(userViewedEntity);
//				}
//			}
			return new SystemMessageDto(entity);
		}
		return null;
	}

	@Override
	public Object getSystemMessageOfTableName(long systemMessageId) {
		SystemMessage entity= new SystemMessage();
		entity= systemMessageRepository.findOne(systemMessageId);
		Object object= new Object();
		Object objectDto=new Object();
		if(entity!=null) {
			String tableName= entity.getTableName();
			long recordId= entity.getRecordId();
			//lọc các trường hợp để lấy table tương ứng trong th
			switch(tableName) {
				case "ForestProductsList"://laays thong tin bang ke lam san
					object= forestProductsListRepository.findOne(recordId);
					if(object!=null) {
						objectDto= new ForestProductsListDto((ForestProductsList) object);
					}
					break;
				case "AnimalRequired":
					object= animalRequiredRepository.findOne(recordId);
					if(object!=null) {
						objectDto= new AnimalRequiredDto((AnimalRequired) object);
					}
					break;
				default:
					object=null;
					objectDto= null;
					break;
			}
		}
		return objectDto;
	}

	@Override
	public SystemMessageDto saveMessageAnimalRequired(AnimalRequiredDto animalRequiredDto) {
		if(animalRequiredDto!=null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			SystemMessage entity= null;
			if(entity==null) {
				entity= new SystemMessage();
				entity.setCreateDate(currentDate);
				entity.setCreatedBy(currentUserName);
			}
			AnimalRequired animalRequiredEntity= null;
			if(animalRequiredDto.getId()!=null) {
				animalRequiredEntity= animalRequiredRepository.findOne(animalRequiredDto.getId());
			}
			if(animalRequiredDto.getUser()!=null) {
				entity.setContent("có yêu cầu thêm loài mới của "+animalRequiredDto.getUser().getDisplayName());
			}
			if(animalRequiredDto.getAnimalRequiredStatus()==5) {
				entity.setTitle("Có yêu cầu thêm mới loài ");
			}
			if(animalRequiredDto.getAnimalRequiredStatus()==2) {
				entity.setTitle("Yêu cầu thêm mới loài đã được xác nhận ");
			}
			if(animalRequiredDto.getAnimalRequiredStatus()==3) {
				entity.setTitle("yêu cầu thêm mới loài đã bị từ chối ");
			}
			entity.setRecordId(animalRequiredDto.getId());
			entity.setStatus(1);
			entity.setType(1);
			entity.setSender(modifiedUser);
			entity.setTableName("AnimalRequired");
			List<User> listUser= new ArrayList<>();
			
			//lay user co 2 quyen dc hien thong bao
			
			List<UserDto> listUserRoleApproved=null;
			UserUserAdministrativeUnitDto searchDto= new UserUserAdministrativeUnitDto();
			Role roleApproved= roleService.findByName(WLConstant.ROLE_APPROVED_SPECIES);
			if(roleApproved!=null) {
				searchDto.setRoleId(roleApproved.getId());
				listUserRoleApproved= userService.findAllByRoleId(searchDto);
			}
			
			if(listUserRoleApproved!=null && listUserRoleApproved.size()>0) {
				for(UserDto userDto: listUserRoleApproved) {
					listUser.add(userService.findById(userDto.getId()));
				}
				
			}
			if(animalRequiredDto.getAnimalRequiredStatus()==2 || animalRequiredDto.getAnimalRequiredStatus()==3) {
				User currentUser= userService.findById(modifiedUser.getId());
				if(currentUser.getRoles() != null) {
					for(Role role: currentUser.getRoles()) {					
						if(role.getName().equalsIgnoreCase(WLConstant.ROLE_APPROVED_SPECIES)) {
							listUser.remove(currentUser);
						}			
					}
				}
			}
			//danh sach user duoc nhan thong bao
			Set<UserViewedNotification> userViewedNotifications = new HashSet<UserViewedNotification>();
			if(listUser.size()>0) {
				for(User user: listUser) {
					UserViewedNotification userViewedEntity= new UserViewedNotification();
					userViewedEntity.setUser(user);
					userViewedEntity.setSystemMessage(entity);
					userViewedNotifications.add(userViewedEntity);
				}
			}
			entity.setUserViewedNotifications(userViewedNotifications);
			entity= systemMessageRepository.save(entity);
			
			return new SystemMessageDto(entity);
			
		}
		return null;
	}

	

}
