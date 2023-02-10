package com.globits.wl.rest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.globits.core.Constants;
import com.globits.core.dto.PersonDto;
import com.globits.core.utils.CommonUtils;
import com.globits.core.utils.FileUtils;
import com.globits.core.utils.ImageUtils;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.security.dto.PasswordChangeDto;
import com.globits.security.dto.PhotoCropperDto;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;
import com.globits.security.dto.UserFilterDto;
import com.globits.security.service.UserService;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.FmsRegionDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.functiondto.UserUserAdministrativeUnitDto;
import com.globits.wl.service.FmsAdministrativeUnitService;
import com.globits.wl.service.FmsUserService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/fms_users")
public class RestFmsUserController {
	@Autowired
	private FmsUserService userService;
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getbyusername/{username}/", method = RequestMethod.GET)
	public UserDto findByUsername(@PathVariable("username") String username){
		return userService.findByUsername(username);
	} 
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/e/{email}", method = RequestMethod.GET)
	public ResponseEntity<UserDto> findByEmail(@PathVariable("email") String email){

		if (CommonUtils.isEmpty(email)) {
			return new ResponseEntity<>(new UserDto(), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(userService.findByEmail(email), HttpStatus.OK);
	} 
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getbyid/{userId}", method = RequestMethod.GET)
	public UserDto getUser(@PathVariable("userId") Long userId) {
		return userService.findByUserId(userId);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{pageIndex}/{pageSize}/{username}", method = RequestMethod.GET)
	public Page<UserDto> searchUsers(@PathVariable int pageIndex, @PathVariable int pageSize,
			@PathVariable String username) {
		return userService.searchByPageBasicInfo(pageIndex, pageSize, "%" + username + "%");
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<UserDto> getUsers(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return userService.findByPage(pageIndex, pageSize);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/search/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<UserDto>> searchUsers(@RequestBody UserFilterDto filter,
			@PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize) {
		if (filter == null) {
			return new ResponseEntity<Page<UserDto>>(
					new PageImpl(new ArrayList<>(), new PageRequest(pageIndex, pageSize), 0), HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(userService.findAllPageable(filter, pageIndex, pageSize), HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/simple/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public Page<UserDto> findByPageBasicInfo(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return userService.findByPageBasicInfo(pageIndex, pageSize);
	}

	@Secured({"ROLE_ADMIN","ROLE_SDAH"})//"ROLE_DLP",
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public UserDto updateUser(@RequestBody UserDto user) {
		return userService.save(user);
	}
	@Secured({"ROLE_ADMIN","ROLE_SDAH"})//"ROLE_DLP",
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public UserDto saveUser(@RequestBody UserDto user) {
		return userService.save(user);
	}

	@Secured({"ROLE_ADMIN","ROLE_SDAH"})//"ROLE_DLP",
	@RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
	public UserDto removeUser(@PathVariable("userId") String userId) {
		return userService.deleteById(new Long(userId));
	}
	
	@Secured({"ROLE_ADMIN","ROLE_SDAH"})//"ROLE_DLP",
	@RequestMapping(value = "/remove", method = RequestMethod.DELETE)
	public ResponseEntity<UserDto> removeUser(@RequestBody UserDto dto) {
		if(dto!=null && dto.getId()!=null){
			Long userId = dto.getId();
			dto = userService.deleteById(new Long(userId));
			return new ResponseEntity<>(dto, HttpStatus.OK);
		}
		return new ResponseEntity<>(new UserDto(), HttpStatus.BAD_REQUEST);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getCurrentUser", method = RequestMethod.GET)
	public UserDto getCurrentUser() {
		UserDto user = userService.getCurrentUser();

		if (user != null) {
			user.setPassword(null);
		}

		return user;
	}

	
	@Secured({"ROLE_ADMIN","ROLE_SDAH"})//"ROLE_DLP",
	@RequestMapping(value = "/password", method = RequestMethod.PUT)
	public ResponseEntity<UserDto> changePassword(@RequestBody UserDto user) {
		return new ResponseEntity<UserDto>(userService.changePassword(user), HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH"})
	@RequestMapping(value = "/password/self", method = RequestMethod.PUT)
	public ResponseEntity<UserDto> changeMyPassword(@RequestBody UserDto dto) {

		User user = SecurityUtils.getCurrentUser();
		if (user == null) {
			return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.FORBIDDEN);
		}

		if (!user.getUsername().equals(dto.getUsername())) {
			return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.FORBIDDEN);
		}
		if(dto.getId()!=null && !dto.getId().equals(user.getId())){
			return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<UserDto>(userService.changePassword(dto), HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH"})
	@RequestMapping(value = "/password/valid", method = RequestMethod.POST)
	public ResponseEntity<Boolean> passwordValid(@RequestBody PasswordChangeDto dto) {
		if (dto == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
		}

		User user = SecurityUtils.getCurrentUser();
		if (user == null) {
			return new ResponseEntity<Boolean>(false, HttpStatus.FORBIDDEN);
		}

		Boolean matched = SecurityUtils.passwordsMatch(user.getPassword(), dto.getPassword());

		return new ResponseEntity<Boolean>(matched, HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/username/{username}/{pageIndex}/{pageSize}", method = RequestMethod.GET)
	public ResponseEntity<Page<UserDto>> findByPageUsername(@PathVariable("username") String username, @PathVariable("pageIndex") int pageIndex, @PathVariable("pageSize") int pageSize) {
		return new ResponseEntity<Page<UserDto>>(userService.findByPageUsername(username, pageIndex, pageSize), HttpStatus.OK);
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH"})
	@RequestMapping(value = "/email_in_use")
	public ResponseEntity<Boolean> emailInUse(@RequestBody UserDto dto) {
		if (dto == null || CommonUtils.isEmpty(dto.getEmail())) {
			return new ResponseEntity<>(Boolean.TRUE, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(userService.emailAlreadyUsed(dto), HttpStatus.OK);
	}
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(path = "/searchDto/{pageIndex}/{pageSize}", method = RequestMethod.POST)	
	public ResponseEntity<Page<UserDto>> getListSimpleByNameCode(@RequestBody UserUserAdministrativeUnitDto dto,@PathVariable int pageIndex, @PathVariable int pageSize) {
		
		Page<UserDto> results = userService.searchUserDto(dto, pageIndex, pageSize);
		return new ResponseEntity<Page<UserDto>>(results, HttpStatus.OK);
	}
	
	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getRolesByAccount", method = RequestMethod.GET)
	public List<RoleDto> getRolesByAccount() {
		return userService.getRoles();
	}
	
}
