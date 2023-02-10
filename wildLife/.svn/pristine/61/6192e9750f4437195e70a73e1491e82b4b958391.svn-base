package com.globits.wl.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.service.UserAdministrativeUnitService;

public class PermissionUtil {
	@Autowired
	private static UserAdministrativeUnitService userAdministrativeUnitService;
	public static boolean checkUserAdministrativeUnitPermission(Long administrativeUnitId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return false;
			}
			else if(!listWardId.contains(administrativeUnitId)) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return true;
		}
	}
}
