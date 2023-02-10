package com.globits.wl.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;



import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import com.globits.cms.Const;
import com.globits.core.Constants;
import com.globits.core.domain.Ethnics;
import com.globits.core.domain.Person;
import com.globits.core.dto.PersonDto;
import com.globits.core.repository.EthnicsRepository;
import com.globits.core.repository.PersonRepository;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.CommonUtils;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.domain.UserGroup;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;
import com.globits.security.dto.UserFilterDto;
import com.globits.security.dto.UserGroupDto;
import com.globits.security.repository.RoleRepository;
import com.globits.security.repository.UserGroupRepository;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.RoleService;
import com.globits.wl.domain.UserAdministrativeUnit;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.UserExportDto;
import com.globits.wl.dto.functiondto.UserUserAdministrativeUnitDto;
import com.globits.wl.service.FmsAdministrativeUnitService;
import com.globits.wl.service.FmsUserService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.ImportExportExcelUtil;

@Service
public class FmsUserServiceImpl extends GenericServiceImpl<User, Long>
		implements FmsUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserGroupRepository groupRepos;

	@Autowired
	private RoleRepository roleRepos;

	@Autowired
	private PersonRepository personRepos;

	@Autowired
	private EthnicsRepository ethnicsRepos;
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	@Autowired
	private FmsAdministrativeUnitService fmsAdministrativeUnitService;
	@Autowired
	private RoleService roleService;

	@Autowired
	EntityManager manager;
	@Override
	@Transactional(readOnly = true)
	public UserDto findByUserId(Long userId) {
		User user = userRepository.findById(userId);

		if (user != null) {
			return new UserDto(user);
		} else {
			return null;
		}
	}
	@Override
	public UserDto deleteById(Long userId) {
		User user = userRepository.findById(userId);
		if (user != null) {
			//xóa đơn vị trực thuộc
			userAdministrativeUnitService.deleteUserAdministrativeUnit(userId);
			userRepository.delete(user);
			return new UserDto(user);
		} else {
			return null;
		}
	}
	//
	@Override
	@Transactional(readOnly = true)
	public UserDto findByUsername(String username) {
		User user = userRepository.findByUsername(username);
		if (user != null) {
			return new UserDto(user);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public UserDto findByEmail(String email) {
		User retUser = userRepository.findByEmail(email);

		if (retUser != null) {
			UserDto dto = new UserDto(retUser);
			dto.setPassword(null);

			return dto;
		}

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserDto> findByPage(int pageIndex, int pageSize) {
		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);

		Page<User> page = userRepository.findAll(pageable);

		List<User> _content = page.getContent();
		List<UserDto> content = new ArrayList<UserDto>();

		for (User entity : _content) {

			// No password disclosed
			// entity.setPassword(null);

			content.add(new UserDto(entity));
		}

		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserDto> findByPageBasicInfo(int pageIndex, int pageSize) {
		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);

		Page<User> page = userRepository.findByPageBasicInfo(pageable);

		List<User> _content = page.getContent();
		List<UserDto> content = new ArrayList<UserDto>();

		for (User entity : _content) {

			// No password disclosed
			// entity.setPassword(null);

			content.add(new UserDto(entity));
		}

		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserDto> searchByPageBasicInfo(int pageIndex, int pageSize, String username) {
		Pageable pageable = new PageRequest(pageIndex - 1, pageSize);

		Page<User> page = userRepository.searchByPageBasicInfo(pageable, username);

		List<User> _content = page.getContent();
		List<UserDto> content = new ArrayList<UserDto>();

		for (User entity : _content) {

			// No password disclosed
			// entity.setPassword(null);

			content.add(new UserDto(entity));
		}

		return new PageImpl<>(content, pageable, page.getTotalElements());
	}

	@Override
	@Transactional(readOnly = true)
	public Page<UserDto> findAllPageable(final UserFilterDto filter, int pageIndex, int pageSize) {
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		String sql = "from User u";
		String sqlCount = "select count(distinct u.id) from User u ";
		
		String clause =" where (1=1)";
		if (!CommonUtils.isEmpty(filter.getKeyword())) {
			clause +=" and (u.username like :keyword or u.email like :keyword or (u.person!=null and u.person.displayName like :keyword))";
		}
		
		if (filter.getActive() != null) {
			clause +=" and (u.active = :active)";
		}
		List<Long> roleIds = new ArrayList<>();
		if (filter.getRoles() != null && filter.getRoles().length > 0) {
			for (RoleDto dto : filter.getRoles()) {
				if (CommonUtils.isPositive(dto.getId(), true)) {
					roleIds.add(dto.getId());
				}
			}
			sql +=" join fetch u.roles roles ";
			sqlCount +=" join u.roles roles ";
			clause +=" and roles.id in :roleIds";
		}	
		List<Long> groupIds = new ArrayList<>();
		if (filter.getGroups() != null && filter.getGroups().length > 0) {
			
			
			for (UserGroupDto dto : filter.getGroups()) {
				if (CommonUtils.isPositive(dto.getId(), true)) {
					groupIds.add(dto.getId());
				}
			}
			sql +=" join fetch u.groups groups";
			sqlCount +=" join u.groups groups";
			clause +=" and groups.id in :groupIds";
		}
		sql+=clause;
		sqlCount+=clause;
		Query q = manager.createQuery(sql);
		Query qCount = manager.createQuery(sqlCount);
		if (!CommonUtils.isEmpty(filter.getKeyword())) {
			q.setParameter("keyword", '%'+filter.getKeyword()+'%');
			qCount.setParameter("keyword", '%'+filter.getKeyword()+'%');
		}
		
		if (filter.getActive() != null) {
			q.setParameter("active", filter.getActive());
			qCount.setParameter("active", filter.getActive());
		}
		
		if (filter.getRoles() != null && filter.getRoles().length > 0) {
			q.setParameter("roleIds", roleIds);
			qCount.setParameter("roleIds", roleIds);
		}	
		if (filter.getGroups() != null && filter.getGroups().length > 0) {
			q.setParameter("groupIds", groupIds);
			qCount.setParameter("groupIds", groupIds);
		}
		int startPosition = (pageIndex-1)* pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<User> users= q.getResultList();
		Long numberResult = (Long)qCount.getSingleResult();
		
		List<UserDto> userDtos = new ArrayList<>();
		for (User u : users) {
			userDtos.add(new UserDto(u));
		}

		return new PageImpl<>(userDtos, pageable, numberResult);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDto getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User) authentication.getPrincipal();
		if (user != null && user.getUsername() != null) {
			User entity = userRepository.findByUsernameAndPerson(user.getUsername());

			if (entity != null) {
				return new UserDto(entity);
			}
		}

		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public byte[] getProfilePhoto(String username) {
		if (username == null || username.trim().isEmpty()) {
			return null;
		}

		User user = userRepository.findByUsernameAndPerson(username);

		if (user == null || user.getPerson() == null || user.getPerson().getPhoto() == null) {
			if(user.getPerson()!=null && user.getPerson().getImagePath()!=null) {
				String filePath =  user.getPerson().getImagePath();
				try {
			        File file = new File(filePath);
			        byte[] data = new byte[(int) file.length()];
			        InputStream is = new FileInputStream(file);
			        is.read(data);
			        is.close();
			        return data;
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (IOException e) {
			        e.printStackTrace();
			    }
			}
			return null;
		}

		return user.getPerson().getPhoto();
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public User saveUser(UserDto userDto) {

		if (userDto == null) {
			throw new IllegalArgumentException();
		}

		User user = null;

		if (CommonUtils.isPositive(userDto.getId(), true)) {
			user = userRepository.findById(userDto.getId());
		}

		if (user == null) {
			user = userDto.toEntity();

			user.setJustCreated(true);

			if (userDto.getPassword() != null && userDto.getPassword().length() > 0) {
				user.setPassword(SecurityUtils.getHashPassword(userDto.getPassword()));
			}

		} else {
			user.setUsername(userDto.getUsername());//Nếu muốn cho đổi username thì bỏ đoạn rem này ra
			user.setEmail(userDto.getEmail());
			if (userDto.getPassword() != null && userDto.getPassword().length() > 0) {
				user.setPassword(SecurityUtils.getHashPassword(userDto.getPassword()));
			}
		}

		if (userDto.getRoles() != null) {
			List<Role> rs = new ArrayList<Role>();

			for (RoleDto d : userDto.getRoles()) {
				Role r = roleRepos.findOne(d.getId());

				if (r != null) {
					rs.add(r);
				}
			}

			user.getRoles().clear();
			user.getRoles().addAll(rs);
		}

		if (userDto.getGroups() != null) {
			List<UserGroup> gs = new ArrayList<>();

			for (UserGroupDto d : userDto.getGroups()) {
				UserGroup g = groupRepos.findOne(d.getId());

				if (g != null) {
					gs.add(g);
				}
			}

			user.getGroups().clear();
			user.getGroups().addAll(gs);
		}

		PersonDto personDto = userDto.getPerson();
		Person person = null;

		if (personDto != null && CommonUtils.isPositive(personDto.getId(), true)) {
			person = personRepos.findOne(personDto.getId());
		}

		if (person != null) {
			person.setFirstName(personDto.getFirstName());
			person.setLastName(personDto.getLastName());
			person.setDisplayName(personDto.getDisplayName());
			person.setBirthDate(personDto.getBirthDate());
			person.setBirthPlace(personDto.getBirthPlace());
			person.setEmail(personDto.getEmail());
			person.setEndDate(personDto.getEndDate());

			if (personDto.getEthnics() != null && CommonUtils.isPositive(personDto.getEthnics().getId(), true)) {

				Ethnics e = ethnicsRepos.findOne(personDto.getEthnics().getId());

				if (e != null) {
					person.setEthnics(e);
				}
			}

			person.setGender(personDto.getGender());
			person.setIdNumber(personDto.getIdNumber());
			person.setIdNumberIssueBy(personDto.getIdNumberIssueBy());
			person.setIdNumberIssueDate(personDto.getIdNumberIssueDate());
		} else {
			person = personDto.toEntity();
		}

		user.setPerson(person);
		person.setUser(user);
		user.setActive(userDto.getActive());
		user = userRepository.save(user);

		return user;
	}
	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserDto save(UserDto userDto) {

		if (userDto == null) {
			throw new IllegalArgumentException();
		}

		User user = null;

		if (CommonUtils.isPositive(userDto.getId(), true)) {
			user = userRepository.findById(userDto.getId());
		}

		if (user == null) {
			user = userDto.toEntity();

			user.setJustCreated(true);

			if (userDto.getPassword() != null && userDto.getPassword().length() > 0) {
				user.setPassword(SecurityUtils.getHashPassword(userDto.getPassword()));
			}

		} else {
			user.setUsername(userDto.getUsername());//Nếu muốn cho đổi username thì bỏ đoạn rem này ra
			user.setEmail(userDto.getEmail());
			if (userDto.getPassword() != null && userDto.getPassword().length() > 0 && userDto.getChangePass()) {
				user.setPassword(SecurityUtils.getHashPassword(userDto.getPassword()));
			}
		}

		if (userDto.getRoles() != null) {
			List<Role> rs = new ArrayList<Role>();

			for (RoleDto d : userDto.getRoles()) {
				Role r = roleRepos.findOne(d.getId());

				if (r != null) {
					rs.add(r);
				}
			}

			user.getRoles().clear();
			user.getRoles().addAll(rs);
		}

		if (userDto.getGroups() != null) {
			List<UserGroup> gs = new ArrayList<>();

			for (UserGroupDto d : userDto.getGroups()) {
				UserGroup g = groupRepos.findOne(d.getId());

				if (g != null) {
					gs.add(g);
				}
			}

			user.getGroups().clear();
			user.getGroups().addAll(gs);
		}

		PersonDto personDto = userDto.getPerson();
		Person person = null;

		if (personDto != null && CommonUtils.isPositive(personDto.getId(), true)) {
			person = personRepos.findOne(personDto.getId());
		}

		if (person != null) {
			person.setFirstName(personDto.getFirstName());
			person.setLastName(personDto.getLastName());
			person.setDisplayName(personDto.getDisplayName());
			person.setBirthDate(personDto.getBirthDate());
			person.setBirthPlace(personDto.getBirthPlace());
			person.setEmail(personDto.getEmail());
			person.setEndDate(personDto.getEndDate());

			if (personDto.getEthnics() != null && CommonUtils.isPositive(personDto.getEthnics().getId(), true)) {

				Ethnics e = ethnicsRepos.findOne(personDto.getEthnics().getId());

				if (e != null) {
					person.setEthnics(e);
				}
			}

			person.setGender(personDto.getGender());
			person.setIdNumber(personDto.getIdNumber());
			person.setIdNumberIssueBy(personDto.getIdNumberIssueBy());
			person.setIdNumberIssueDate(personDto.getIdNumberIssueDate());
		} else {
			person = personDto.toEntity();
		}

		user.setPerson(person);
		person.setUser(user);
		user.setActive(userDto.getActive());
		user = userRepository.save(user);

		if (user != null) {
			return new UserDto(user);
		} else {
			return null;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserDto savePhoto(UserDto dto) {

		if (dto == null) {
			throw new RuntimeException();
		}

		User user = null;

		if (dto.getId() != null && dto.getId() > 0) {
			user = userRepository.findOne(dto.getId());
		}

		if (user == null) {
			throw new RuntimeException();
		}

		Person person = user.getPerson();
		if (person == null) {
			person = new Person();
		}

		person.setPhoto(dto.getPerson().getPhoto());
		person.setPhotoCropped(dto.getPerson().getPhotoCropped());

		person.setUser(user);
		user.setPerson(person);

		// Save
		user = userRepository.save(user);

		if (user != null) {
			return new UserDto(user);
		} else {
			throw new RuntimeException();
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean passwordMatch(UserDto dto) {

		if (dto == null || !CommonUtils.isPositive(dto.getId(), true)) {
			return false;
		}

		User user = userRepository.findOne(dto.getId());

		if (user != null) {
			return SecurityUtils.passwordsMatch(user.getPassword(), dto.getPassword());
		} else {
			return false;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public UserDto changePassword(UserDto dto) {

		if (dto == null || !CommonUtils.isPositive(dto.getId(), true) || CommonUtils.isEmpty(dto.getPassword())) {
			return null;
		}

		User user = userRepository.findOne(dto.getId());

		if (user == null) {
			return null;
		}

		user.setPassword(SecurityUtils.getHashPassword(dto.getPassword()));

		user = userRepository.save(user);

		if (user == null) {
			return null;
		} else {
			return new UserDto(user);
		}
	}

	@Override
	public Page<UserDto> findByPageUsername(String username, int pageIndex, int pageSize) {
		Pageable pageable = new PageRequest(pageIndex, pageSize);	
		Page<UserDto> page = userRepository.findByPageUsername(username, pageable);
		return page;
	}
	@Override
	@Transactional(readOnly = true)
	public boolean emailAlreadyUsed(UserDto dto) {

		if (dto == null || CommonUtils.isEmpty(dto.getEmail())) {
			return false;
		}

		User user = userRepository.findByEmail(dto.getEmail());

		return (user!=null);
	}

	@Override
	@Transactional
	public User updateUserLastLogin(Long userId) {
		User user = userRepository.findOne(userId);
		user.setLastLoginTime(new Date());
		return userRepository.save(user);
	}

	@Override
	public User findEntityByUsername(String username) {
		User user = userRepository.findByUsername(username);
		return user;
	}
	@Override
	public Page<UserDto> searchUserDto(UserUserAdministrativeUnitDto searchDto, int pageIndex, int pageSize) {	
		/*Lấy đơn vị hành chính theo user
		 * admin thì lấy tất
		 * cấp tỉnh - huyện - xã thì lấy theo phân quyền tương ứng
		*/
		List<Long> list=new ArrayList<Long>();
		if(searchDto.getAdministrativeUnitId()!=null && searchDto.getAdministrativeUnitId()>0){
			list=fmsAdministrativeUnitService.getAllAdministrativeUnitIdByParentId(searchDto.getAdministrativeUnitId());			
		}
		else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();			 
			if (authentication != null) {
				User currentUser = (User) authentication.getPrincipal();
				boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
						|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
				if(!isAdmin) {
					List<UserAdministrativeUnit> listUserAdministrativeUnit = userAdministrativeUnitService.findAdministrativeUnitByUserId(currentUser.getId());
					if(listUserAdministrativeUnit!=null && listUserAdministrativeUnit.size()>0) {
						for (UserAdministrativeUnit userAdministrativeUnit : listUserAdministrativeUnit) {
							if(userAdministrativeUnit.getAdminUnit()!=null) {
								List<Long> ret = fmsAdministrativeUnitService.getAllAdministrativeUnitIdByParentId(userAdministrativeUnit.getAdminUnit().getId());
								if(ret!=null && ret.size()>0) {
									list.addAll(ret);
								}
							}
						}
					}
				}
			}
		}
		if(list!=null && list.size()>0){
			searchDto.setListAdministrativeUnitId(list);
		}
		/*hết đoạn xử lý lấy đơn vị hành chính theo user*/
		
		searchDto.setListRoleId(new ArrayList<Long>());
		List<RoleDto> roles =this.getRoles();
		for(RoleDto roleDto:roles) {
			if(roleDto != null) {
				searchDto.getListRoleId().add(roleDto.getId());
			}
		}
		if(searchDto.getRoleId() != null && !searchDto.getListRoleId().contains(searchDto.getRoleId())) {
			searchDto.setRoleId(null);
		}
		
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameCode();
		String administrative="";
		String joinRole="";
		String joinRoleCount = "";
		if(searchDto.getListAdministrativeUnitId()!=null && searchDto.getListAdministrativeUnitId().size()>0){
			administrative +=" inner join UserAdministrativeUnit uad on u.id=uad.user.id ";			
		}
		if(searchDto.getRoleId() != null && searchDto.getRoleId() > 0) {
			joinRole += " inner join u.roles r ON r.id=:roleId ";
			joinRoleCount+= " inner join u.roles r ON r.id=:roleId ";
		}
		
		
		String sql = " select u from User u "+ administrative+ joinRole+ "where (1=1)";
		String sqlCount = "select count(u.id) from User u"+ administrative+ joinRoleCount + " where (1=1)";
		String whereClause ="";
		
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (u.username like :namecode)";
		}
		
		if(searchDto.getListAdministrativeUnitId()!=null && searchDto.getListAdministrativeUnitId().size()>0){
			whereClause += " and uad.adminUnit.id  in :adminUnits";
		}
//		if(searchDto.getRoleId() != null) {
//			whereClause += " and roles.id = :roleId ";
//		}
//		else if(searchDto.getListRoleId()!= null && searchDto.getListRoleId().size() > 0) {
//			whereClause += " and roles.id in :roleIds ";
//		}
		
		sql +=whereClause;
		
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,User.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}		
		
		if(searchDto.getListAdministrativeUnitId()!=null && searchDto.getListAdministrativeUnitId().size()>0){
			q.setParameter("adminUnits", searchDto.getListAdministrativeUnitId());
			qCount.setParameter("adminUnits", searchDto.getListAdministrativeUnitId());		
		}
		if(searchDto.getRoleId() != null && searchDto.getRoleId()>0) {
			q.setParameter("roleId", searchDto.getRoleId());
			qCount.setParameter("roleId", searchDto.getRoleId());
		}
//		else if(searchDto.getListRoleId()!= null && searchDto.getListRoleId().size() > 0) {
//			q.setParameter("roleIds", searchDto.getListRoleId());
//			qCount.setParameter("roleIds", searchDto.getListRoleId());
//		}
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		Set<User>users = new HashSet<User>(q.getResultList());
		List<UserDto>userDtos = new ArrayList<UserDto>();
		for(User user: users) {
			if(user != null) {
				userDtos.add(new UserDto(user));
			}
		}
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<UserDto> page = new PageImpl<>(userDtos, pageable,numberResult);		
		return page;
	}
	@Override
	public List<RoleDto> getRoles() {
		List<RoleDto> roles=roleService.findAll();
		List<RoleDto> ret=new ArrayList<RoleDto>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;		
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			if(modifiedUser.getRoles()!=null && modifiedUser.getRoles().size()>0) {
				for (Role role : modifiedUser.getRoles()) {
					if(role.getName()!=null && (role.getName().equals(WLConstant.ROLE_ADMIN)||role.getName().equals(WLConstant.ROLE_DLP))) {
						ret.addAll(roles);
						break;
					}else if(role.getName()!=null && (role.getName().equals(WLConstant.ROLE_SDAH))) {
						for (RoleDto item : roles) {
							if(item.getName().equals(WLConstant.ROLE_ADMIN) || item.getName().equals(WLConstant.ROLE_DLP)|| item.getName().equals(Constants.ROLE_USER)) {
								continue;
							}else {
								ret.add(item);
							}
						}
						break;
					}else if(role.getName()!=null && (role.getName().equals(WLConstant.ROLE_SDAH_VIEW))) {
						for (RoleDto item : roles) {
							if(item.getName().equals(WLConstant.ROLE_ADMIN) || item.getName().equals(WLConstant.ROLE_DLP) || item.getName().equals(WLConstant.ROLE_SDAH)|| item.getName().equals(Constants.ROLE_USER)) {
								continue;
							}else {
								ret.add(item);
							}
						}
						break;
					}
					else if(role.getName()!=null && (role.getName().equals(WLConstant.ROLE_DISTRICT))) {
						for (RoleDto item : roles) {
							if(item.getName().equals(WLConstant.ROLE_ADMIN) || item.getName().equals(WLConstant.ROLE_DLP)|| item.getName().equals(WLConstant.ROLE_SDAH)|| item.getName().equals(WLConstant.ROLE_DISTRICT)|| item.getName().equals(Constants.ROLE_USER)) {
								continue;
							}else {
								ret.add(item);
							}
						}
						break;
					}else if(role.getName()!=null && (role.getName().equals(WLConstant.ROLE_WARD))) {
						for (RoleDto item : roles) {
							if(item.getName().equals(WLConstant.ROLE_ADMIN) || item.getName().equals(WLConstant.ROLE_DLP)|| item.getName().equals(WLConstant.ROLE_SDAH)|| item.getName().equals(WLConstant.ROLE_DISTRICT)|| item.getName().equals(Constants.ROLE_USER)) {
								continue;
							}else {
								ret.add(item);
							}
						}
						break;
					}
					
				}
			}
		}
		// TODO Auto-generated method stub
		return ret;
	}
	
	@SuppressWarnings("unchecked")
	public List<UserExportDto> getSearchUserDto(UserUserAdministrativeUnitDto searchDto) {	
		//đoạn này xử lý nếu tài khoản là cấp tỉnh => lấy list danh sách đơn vị hành chính thuộc cấp tỉnh
		List<Long> list=new ArrayList<Long>();
		if(searchDto.getAdministrativeUnitId()!=null && searchDto.getAdministrativeUnitId()>0){
			list=fmsAdministrativeUnitService.getAllAdministrativeUnitIdByParentId(searchDto.getAdministrativeUnitId());			
		}
		else {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();			 
			if (authentication != null) {
				User currentUser = (User) authentication.getPrincipal();
				boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
						|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
				if(!isAdmin) {
					List<UserAdministrativeUnit> listUserAdministrativeUnit = userAdministrativeUnitService.findAdministrativeUnitByUserId(currentUser.getId());
					if(listUserAdministrativeUnit!=null && listUserAdministrativeUnit.size()>0) {
						for (UserAdministrativeUnit userAdministrativeUnit : listUserAdministrativeUnit) {
							if(userAdministrativeUnit.getAdminUnit()!=null) {
								List<Long> ret = fmsAdministrativeUnitService.getAllAdministrativeUnitIdByParentId(userAdministrativeUnit.getAdminUnit().getId());
								if(ret!=null && ret.size()>0) {
									list.addAll(ret);
								}
							}
						}
					}
				}
			}
		}
		if(list!=null && list.size()>0){
			searchDto.setListAdministrativeUnitId(list);
		}
		searchDto.setListRoleId(new ArrayList<Long>());
		for(RoleDto roleDto:this.getRoles()) {
			if(roleDto != null) {
				searchDto.getListRoleId().add(roleDto.getId());
			}
		}
		if(searchDto.getRoleId() != null && !searchDto.getListRoleId().contains(searchDto.getRoleId())) {
			searchDto.setRoleId(null);
		}
		
		String namecode = searchDto.getNameCode();
		String administrative="";
		String joinRole="";
		administrative +=" left join UserAdministrativeUnit uad on u.id=uad.user.id ";	
		if(searchDto.getListRoleId()!= null && searchDto.getListRoleId().size() > 0) {
			joinRole += " join u.roles roles ";
		}
		
		String sql = " select new com.globits.wl.dto.functiondto.UserExportDto(u, uad) from User u "+ administrative + joinRole + "where (1=1)";
		String whereClause ="";
		
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (u.username like :namecode)";
		}
		
		if(searchDto.getListAdministrativeUnitId()!=null && searchDto.getListAdministrativeUnitId().size()>0){
			whereClause += " and uad.adminUnit.id  in :adminUnits";
		}
		
		if(searchDto.getRoleId() != null) {
			whereClause += " and roles.id = :roleId ";
		}else if(searchDto.getListRoleId()!= null && searchDto.getListRoleId().size() > 0) {
			whereClause += " and roles.id in :roleIds ";
		}
		
		sql +=whereClause;

		Query q = manager.createQuery(sql, UserExportDto.class);
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
		}		
		
		if(searchDto.getListAdministrativeUnitId()!=null && searchDto.getListAdministrativeUnitId().size()>0){
			q.setParameter("adminUnits", searchDto.getListAdministrativeUnitId());
		}
		
		if(searchDto.getRoleId() != null) {
			q.setParameter("roleId", searchDto.getRoleId());
		}else if(searchDto.getListRoleId()!= null && searchDto.getListRoleId().size() > 0) {
			q.setParameter("roleIds", searchDto.getListRoleId());
		}
		
		return q.getResultList();
	}

	@Override
	public void exportSearchUser(WebRequest request, HttpServletResponse response,
			UserUserAdministrativeUnitDto searchDto) {
		List<UserExportDto> list= this.getSearchUserDto(searchDto);
		try {
			ImportExportExcelUtil.exportFileUserSearch(response, list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public List<UserDto> findAllByRoleId(UserUserAdministrativeUnitDto searchDto) {
		if(searchDto!=null && searchDto.getRoleId()!=null) {
			String joinRole = " inner join u.roles r ON r.id=:roleId ";
			String sql = " select u from User u "+ joinRole+ "where (1=1)";
			String whereClause ="";
			if(searchDto.getRoleId() != null) {
				whereClause += " and r.id = :roleId ";
			}
			sql +=whereClause;
			Query q = manager.createQuery(sql,User.class);
			q.setParameter("roleId", searchDto.getRoleId());
			Set<User> users = new HashSet<User>(q.getResultList());
			List<UserDto> userDtos = new ArrayList<UserDto>();
			for(User user: users) {
				if(user != null) {
					userDtos.add(new UserDto(user));
				}
			}
			return userDtos;
		}
		
		return null;
	}
	
	
}
