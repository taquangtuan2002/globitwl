package com.globits.wl.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.globits.wl.domain.UserAdministrativeUnit;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.UserAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.service.FmsAdministrativeUnitService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.WLConstant;

@RestController
@RequestMapping("/api/user_administrative")
public class RestUserAdministrativeUnitController {
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/save/{userId}", method = RequestMethod.POST)
	public List<UserAdministrativeUnit> saveList(@RequestBody List<FmsAdministrativeUnitDto> listAdministrativeUnit,@PathVariable Long userId) {
		return userAdministrativeUnitService.saveAdministrativeUnitByUserId(userId, listAdministrativeUnit);
	}	
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/get_administrativeUnit_by_userid/{userId}", method = RequestMethod.GET)
	public List<FmsAdministrativeUnitDto> findAdministrativeUnitDtoByUserId(@PathVariable Long userId) {
		return userAdministrativeUnitService.findAdministrativeUnitDtoByUserId(userId);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/get_administrativeUnit_by_username/{userName}", method = RequestMethod.GET)
	public List<FmsAdministrativeUnitDto> findAdministrativeUnitDtoByUserName(@PathVariable String userName) {
		return userAdministrativeUnitService.findAdministrativeUnitDtoByUserName(userName);
	}
	@Secured({"ROLE_ADMIN"})
	@RequestMapping(value = "/get_administrativeUnit_dto_by_loginuser", method = RequestMethod.GET)
	public List<FmsAdministrativeUnitDto> getAdministrativeUnitDtoByLoginUser() {
		return userAdministrativeUnitService.getAdministrativeUnitDtoByLoginUser();
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getListAdministrativeUnitCodeByUserLogin", method = RequestMethod.GET)
	public List<String> getListAdministrativeUnitCodeByUserLogin(){
		return userAdministrativeUnitService.getListAdministrativeUnitCodeByUserLogin();
	}

	@Secured({"ROLE_ADMIN","ROLE_DLP","ROLE_SDAH","ROLE_DISTRICT","ROLE_WARD","ROLE_FAMER","ROLE_USER", WLConstant.ROLE_SDAH_VIEW})
	@RequestMapping(value = "/getByUserId/{userId}", method = RequestMethod.GET)
	public UserAdministrativeUnitDto getOneByUserId(@PathVariable("userId") Long userId){
		return userAdministrativeUnitService.getOneByUserId(userId);
	}
}
