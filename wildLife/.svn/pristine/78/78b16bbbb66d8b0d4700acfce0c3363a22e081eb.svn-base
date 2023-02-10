package com.globits.wl.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.data.domain.Page;
import org.springframework.web.context.request.WebRequest;

import com.globits.core.service.GenericService;
import com.globits.security.domain.User;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;
import com.globits.security.dto.UserFilterDto;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.FarmAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.UserExportDto;
import com.globits.wl.dto.functiondto.UserUserAdministrativeUnitDto;

public interface FmsUserService  extends GenericService<User, Long>{
	public User findEntityByUsername(String username);
	public UserDto findByUsername(String username);
	
	public UserDto findByEmail(String email);

	public Page<UserDto> searchByPageBasicInfo(int pageIndex, int pageSize, String username);

	public Page<UserDto> findByPageBasicInfo(int pageIndex, int pageSize);

	public UserDto save(UserDto user);

	public Page<UserDto> findByPage(int pageIndex, int pageSize);

	public UserDto getCurrentUser();

	public byte[] getProfilePhoto(String username);

	public UserDto savePhoto(UserDto dto);

	public boolean passwordMatch(UserDto dto);

	public UserDto changePassword(UserDto dto);
	
	public Page<UserDto> findByPageUsername(String username, int pageIndex, int pageSize);

	public Page<UserDto> findAllPageable(UserFilterDto filter, int pageIndex, int pageSize);

	public boolean emailAlreadyUsed(UserDto dto);

	public UserDto findByUserId(Long userId);
	public UserDto deleteById(Long userId);
	User updateUserLastLogin(Long userId);
	User saveUser(UserDto userDto);
	public Page<UserDto> searchUserDto(UserUserAdministrativeUnitDto searchDto, int pageIndex, int pageSize);
	public List<RoleDto> getRoles();
	public void exportSearchUser(WebRequest request, HttpServletResponse response, UserUserAdministrativeUnitDto searchDto);
	
	List<UserDto> findAllByRoleId(UserUserAdministrativeUnitDto searchDto);
}
	