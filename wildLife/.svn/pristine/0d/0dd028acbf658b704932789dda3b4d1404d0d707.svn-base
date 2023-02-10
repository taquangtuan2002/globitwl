package com.globits.wl.service;



import java.util.List;

import com.globits.core.service.GenericService;
import com.globits.security.domain.User;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.UserAdministrativeUnit;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.UserAdministrativeUnitDto;


public interface UserAdministrativeUnitService extends GenericService<UserAdministrativeUnit, Long> {
	
	List<UserAdministrativeUnit> saveAdministrativeUnitByUserName(String userName, List<FmsAdministrativeUnitDto> listFmsAdministrativeUnit);
	List<UserAdministrativeUnit> saveAdministrativeUnitByUserId(Long userId, List<FmsAdministrativeUnitDto> listAdministrativeUnit);

	List<UserAdministrativeUnit> findAdministrativeUnitByUserNameAndAdministrativeUnit(String userName, Long trainingBaseId);

	List<UserAdministrativeUnit> findAdministrativeUnitByUserIdAndAdministrativeUnit(Long userId, Long trainingBaseId);

	List<UserAdministrativeUnit> findAdministrativeUnitByUserName(String userName);

	List<UserAdministrativeUnit> findAdministrativeUnitByUserId(Long userId);

	List<UserAdministrativeUnit> getUserAdministrativeUnitByLoginUser();

	List<FmsAdministrativeUnitDto> findAdministrativeUnitDtoByUserId(Long userId);

	List<FmsAdministrativeUnitDto> findAdministrativeUnitDtoByUserName(String userName);

	List<Long> getAdministrativeUnitIdByLoginUser();

	List<FmsAdministrativeUnit> getAdministrativeUnitByLoginUser();

	List<FmsAdministrativeUnitDto> getAdministrativeUnitDtoByLoginUser();
	public void deleteUserAdministrativeUnit(Long userId);
	List<String> getListUserNameByAdministrativeUnitIds(List<Long> administrativeUnitIds);
	List<Long> getListWardIdByLoginUser();
	Boolean checkUserAdministrativeUnitPermission(Long administrativeUnitId);
	List<String> getListAdministrativeUnitCodeByUserLogin();
	
	List<UserAdministrativeUnit> getListUserByAdministrativeUnitId(FmsAdministrativeUnit adminUnit);

	public UserAdministrativeUnitDto getOneByUserId(Long userId);
}
	