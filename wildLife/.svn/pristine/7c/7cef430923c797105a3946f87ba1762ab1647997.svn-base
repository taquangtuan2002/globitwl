package com.globits.wl.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.ReportForm16GetParentService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.WLConstant;
@Service
public class ReportForm16GetParentServiceImpl extends GenericServiceImpl<ReportForm16, Long> implements ReportForm16GetParentService{
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	@Override
	public List<ReportForm16Dto> getReport16aBySearchDtoRecently(SearchReportPeriodDto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if (!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if (listWardId == null || listWardId.size() == 0) {
				return null;
			}
		}
		if (searchDto != null && searchDto.getPageIndex() != null && searchDto.getPageSize() != null) {
			int pageIndex = searchDto.getPageIndex();
			int pageSize = searchDto.getPageSize();

			if (pageIndex > 0)
				pageIndex = pageIndex - 1;
			else
				pageIndex = 0;
			Pageable pageable = new PageRequest(pageIndex, pageSize);

			String sql = "select new com.globits.wl.dto.ReportForm16Dto(rp16) from ReportForm16 rp16 where (1=1)";
			String whereClause = "";

			whereClause += " and (rp16.reportPeriod.type = " + WLConstant.ReportPeriod16Type.Report16A.getValue() + " )";

			if (searchDto.getFarmId() != null) {
				whereClause += " and (rp16.farm.id =:farmId )";
			}

//			if (searchDto.getDateReport() != null) {
//				whereClause += " and (rp16.dateReport =:dateReport )";
//			}
			if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
				whereClause += " and (rp16.farm.code LIKE :text OR rp16.farm.name LIKE :text )";
			}
//			if (searchDto.getYear() != null && searchDto.getMonth() != null && searchDto.getDate() != null) {
//				whereClause += " and (( rp16.reportPeriod.year = :year AND rp16.reportPeriod.month < :month) and (rp16.reportPeriod.month < :month) OR (rp16.reportPeriod.month=:month and rp16.reportPeriod.date<=:date) )";
//			}

			if (searchDto.getWardId() != null) {
				whereClause += " and (rp16.farm.administrativeUnit.id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (rp16.farm.administrativeUnit.parent.id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (rp16.farm.administrativeUnit.parent.parent.id =:provinceId )";
			}
			if (!isAdmin) {
				whereClause += " and (rp16.farm.administrativeUnit.id in (:listWardId)) ";
			}
			if (searchDto.getAnimalId() != null) {
				whereClause += " and ( rp16.animal IS NOT NULL AND rp16.animal.id = :animalId ) ";
			}
			if(searchDto.getDateReport()!=null) {
				whereClause += " and rp16.dateReport <= :dateReport";
			}
			
			sql += whereClause;
			sql += " order by rp16.dateReport desc ";

			Query q = manager.createQuery(sql, ReportForm16Dto.class);
			
			
			if (searchDto.getFarmId() != null) {
				q.setParameter("farmId", searchDto.getFarmId());
			}

//			if (searchDto.getDateReport() != null) {
//				q.setParameter("dateReport", searchDto.getDateReport());
//			}
			
			if (searchDto.getDateReport() != null) {
				q.setParameter("dateReport",searchDto.getDateReport());
			}
			
			if (searchDto.getText() != null && StringUtils.hasText(searchDto.getText())) {
				q.setParameter("text", "%" + searchDto.getText() + "%");
			}
//
//			if (searchDto.getYear() != null && searchDto.getMonth() != null && searchDto.getDate() != null) {
//				q.setParameter("year", searchDto.getYear());
//				
//				q.setParameter("month", searchDto.getMonth());
//				
//				q.setParameter("date", searchDto.getDate());
//			}

			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
			}
			if (!isAdmin) {
				q.setParameter("listWardId", listWardId);
			}
			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
			}

			List<ReportForm16Dto> result = q.getResultList();
			List<ReportForm16Dto> newResult = new ArrayList<ReportForm16Dto>();
			ReportForm16Dto r = new ReportForm16Dto();
			if(result.size() > 0)
			{
				r = result.get(0);
			}

			
			newResult.add(r);
			
			return newResult;
		}
		return null;
	}

}
