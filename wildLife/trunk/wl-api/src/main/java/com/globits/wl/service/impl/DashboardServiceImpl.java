package com.globits.wl.service.impl;

import com.globits.wl.domain.ReportForm16;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.functiondto.*;
import com.globits.core.dto.SocialClassDto;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.dto.DashboardVolatilityDto;
import com.globits.wl.repository.ReportForm16Repository;
import com.globits.wl.response.DashboardResponse;
import com.globits.wl.response.DashboardTableResponse;
import com.globits.wl.service.DashboardService;
import com.globits.wl.service.LinkService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.NumberUtils;
import com.globits.wl.utils.WLConstant;

import com.globits.wl.utils.WLDateTimeUtil;
import com.google.common.base.MoreObjects;
import org.apache.commons.lang3.ObjectUtils;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    public EntityManager manager;
    @Autowired
    private UserAdministrativeUnitService userAdministrativeUnitService;
    @Autowired
    private ReportForm16Repository reportForm16Repository;

    public Long totalSpeciesNumberPLI(Integer year) {
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
        String countSql = "select count(DISTINCT entity.animal.id) from ReportForm16 as entity where entity.cites ='I' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            Integer currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalSpeciesNumberPLII(Integer year) {
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
        String countSql = "select count(DISTINCT entity.animal.id) from ReportForm16 as entity where entity.cites ='II' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalSpeciesNumberPLIII(Integer year) {
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
        String countSql = "select count(DISTINCT entity.animal.id) from ReportForm16 as entity where entity.cites ='III' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalSpeciesNumberDVHD(Integer year) {
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
        String countSql = "select count(DISTINCT entity.animal.id) from ReportForm16 as entity where entity.cites ='' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalFarmsNumberPLI(Integer year) {
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
        String countSql = "select count(DISTINCT entity.farm.id) from ReportForm16 as entity where entity.cites ='I' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalFarmsNumberPLII(Integer year) {
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
        String countSql = "select count(DISTINCT entity.farm.id) from ReportForm16 as entity where entity.cites ='II' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalFarmsNumberPLIII(Integer year) {
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
        String countSql = "select count(DISTINCT entity.farm.id) from ReportForm16 as entity where entity.cites ='III' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalFarmsNumberDVHD(Integer year) {
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
        String countSql = "select count(DISTINCT entity.farm.id) from ReportForm16 as entity where entity.cites ='' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalAnimalsNumberPLI(Integer year) {
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
        String countSql = "select sum( entity.total) from ReportForm16 as entity where entity.cites ='I' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalAnimalsNumberPLII(Integer year) {
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
        String countSql = "select sum( entity.total) from ReportForm16 as entity where entity.cites ='II' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalAnimalsNumberPLIII(Integer year) {
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
        String countSql = "select sum( entity.total) from ReportForm16 as entity where entity.cites ='III' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalAnimalsNumberDVHD(Integer year) {
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
        String countSql = "select sum( entity.total) from ReportForm16 as entity where entity.cites ='' ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    public Long totalAnimalsNumber(Integer year) {
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
        String countSql = "select count(Distinct entity.animal.id) from ReportForm16  as entity where (1=1) ";
        String whereClause = "";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        } else {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        countSql += whereClause;
        Query qCount = manager.createQuery(countSql);
        if (year != null) {
            qCount.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            qCount.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            qCount.setParameter("listWardId", listWardId);
        }
        Long count = (Long) qCount.getSingleResult();
        return count;
    }

    @Override
    public DashboardCitesDto Cites(SearchDto searchDto) {
        DashboardCitesDto dashboardCitesDto = new DashboardCitesDto();
        if (totalSpeciesNumberPLI(searchDto.getYear()) != null) {
            dashboardCitesDto
                    .setSpeciesNumberPLI(NumberUtils.convertNumberToString(totalSpeciesNumberPLI(searchDto.getYear())));
        } else {
            dashboardCitesDto.setSpeciesNumberPLI("0");
        }
        if (totalSpeciesNumberPLII(searchDto.getYear()) != null) {
            dashboardCitesDto.setSpeciesNumberPLII(
                    NumberUtils.convertNumberToString(totalSpeciesNumberPLII(searchDto.getYear())));
        } else {
            dashboardCitesDto.setSpeciesNumberPLII("0");
        }
        if (totalSpeciesNumberPLIII(searchDto.getYear()) != null) {
            dashboardCitesDto.setSpeciesNumberPLIII(
                    NumberUtils.convertNumberToString(totalSpeciesNumberPLIII(searchDto.getYear())));
        } else {
            dashboardCitesDto.setSpeciesNumberPLIII("0");
        }
        if (totalSpeciesNumberDVHD(searchDto.getYear()) != null) {
            dashboardCitesDto.setSpeciesNumberDVHD(
                    NumberUtils.convertNumberToString(totalSpeciesNumberDVHD(searchDto.getYear())));
        } else {
            dashboardCitesDto.setSpeciesNumberDVHD("0");
        }
        if (totalFarmsNumberPLI(searchDto.getYear()) != null) {
            dashboardCitesDto
                    .setFarmsNumberPLI(NumberUtils.convertNumberToString(totalFarmsNumberPLI(searchDto.getYear())));
        } else {
            dashboardCitesDto.setFarmsNumberPLII("0");
        }
        if (totalFarmsNumberPLII(searchDto.getYear()) != null) {
            dashboardCitesDto
                    .setFarmsNumberPLII(NumberUtils.convertNumberToString(totalFarmsNumberPLII(searchDto.getYear())));
        } else {
            dashboardCitesDto.setFarmsNumberPLII("0");
        }
        if (totalFarmsNumberPLIII(searchDto.getYear()) != null) {
            dashboardCitesDto
                    .setFarmsNumberPLIII(NumberUtils.convertNumberToString(totalFarmsNumberPLIII(searchDto.getYear())));
        } else {
            dashboardCitesDto.setFarmsNumberPLIII("0");
        }
        if (totalFarmsNumberDVHD(searchDto.getYear()) != null) {
            dashboardCitesDto
                    .setFarmsNumberDVHD(NumberUtils.convertNumberToString(totalFarmsNumberDVHD(searchDto.getYear())));
        } else {
            dashboardCitesDto.setFarmsNumberDVHD("0");
        }
        if (totalAnimalsNumberPLI(searchDto.getYear()) != null) {
            dashboardCitesDto
                    .setAnimalsNumberPLI(NumberUtils.convertNumberToString(totalAnimalsNumberPLI(searchDto.getYear())));
        } else {
            dashboardCitesDto.setAnimalsNumberPLI("0");
        }
        if (totalAnimalsNumberPLII(searchDto.getYear()) != null) {
            dashboardCitesDto.setAnimalsNumberPLII(
                    NumberUtils.convertNumberToString(totalAnimalsNumberPLII(searchDto.getYear())));
        } else {
            dashboardCitesDto.setAnimalsNumberPLII("0");
        }
        if (totalAnimalsNumberPLIII(searchDto.getYear()) != null) {
            dashboardCitesDto.setAnimalsNumberPLIII(
                    NumberUtils.convertNumberToString(totalAnimalsNumberPLIII(searchDto.getYear())));
        } else {
            dashboardCitesDto.setAnimalsNumberPLIII("0");
        }
        if (totalAnimalsNumberDVHD(searchDto.getYear()) != null) {
            dashboardCitesDto.setAnimalsNumberDVHD(
                    NumberUtils.convertNumberToString(totalAnimalsNumberDVHD(searchDto.getYear())));
        } else {
            dashboardCitesDto.setAnimalsNumberDVHD("0");
        }
        if (totalAnimalsNumber(searchDto.getYear()) != null) {
            dashboardCitesDto
                    .setAnimalNumber(NumberUtils.convertNumberToString(totalAnimalsNumber(searchDto.getYear())));
        } else {
            dashboardCitesDto.setAnimalNumber("0");
        }
        if (totalFarmProvidedCodeNumberOfGoverment(searchDto.getYear()) != null) {
            dashboardCitesDto.setOtherFarmProvidedCodeNumber(NumberUtils.convertNumberToString(totalFarmProvidedCodeNumberOfGoverment(searchDto.getYear())));
        } else {
            dashboardCitesDto.setOtherFarmProvidedCodeNumber("0");
        }
        if (farmProvidedCodeInYearNumberOfGoverment(searchDto.getYear()) != null) {
            dashboardCitesDto.setOtherFarmProvidedCodeInYearNumber(NumberUtils.convertNumberToString(farmProvidedCodeInYearNumberOfGoverment(searchDto.getYear())));
        } else {
            dashboardCitesDto.setOtherFarmProvidedCodeInYearNumber("0");
        }
        long totalAnimal = 0;
        long totalFarm = 0;
        long totalSpecies = 0;
        long totalFarmProvidedCodeNumber = 0;
        long totalFarmProvidedCodeInYearNumber = 0;
        if (totalAnimalsNumberPLI(searchDto.getYear()) != null) {
            totalAnimal += totalAnimalsNumberPLI(searchDto.getYear());
        }
        if (totalAnimalsNumberPLII(searchDto.getYear()) != null) {
            totalAnimal += totalAnimalsNumberPLII(searchDto.getYear());
        }
        if (totalAnimalsNumberPLIII(searchDto.getYear()) != null) {
            totalAnimal += totalAnimalsNumberPLIII(searchDto.getYear());
        }
        if (totalAnimalsNumberDVHD(searchDto.getYear()) != null) {
            totalAnimal += totalAnimalsNumberDVHD(searchDto.getYear());
        }
        if (totalFarmsNumberPLI(searchDto.getYear()) != null) {
            totalFarm += totalFarmsNumberPLI(searchDto.getYear());
        }
        if (totalFarmsNumberPLII(searchDto.getYear()) != null) {
            totalFarm += totalFarmsNumberPLII(searchDto.getYear());
        }
        if (totalFarmsNumberPLIII(searchDto.getYear()) != null) {
            totalFarm += totalFarmsNumberPLIII(searchDto.getYear());
        }
        if (totalFarmsNumberDVHD(searchDto.getYear()) != null) {
            totalFarm += totalFarmsNumberDVHD(searchDto.getYear());
        }
        if (totalSpeciesNumberPLI(searchDto.getYear()) != null) {
            totalSpecies += totalSpeciesNumberPLI(searchDto.getYear());
        }
        if (totalSpeciesNumberPLII(searchDto.getYear()) != null) {
            totalSpecies += totalSpeciesNumberPLII(searchDto.getYear());
        }
        if (totalSpeciesNumberPLIII(searchDto.getYear()) != null) {
            totalSpecies += totalSpeciesNumberPLIII(searchDto.getYear());
        }
        if (totalSpeciesNumberDVHD(searchDto.getYear()) != null) {
            totalSpecies += totalSpeciesNumberDVHD(searchDto.getYear());
        }
        if (totalFarmProvidedCodeNumberOfCites(searchDto.getYear()) != null) {
            totalFarmProvidedCodeNumber += totalFarmProvidedCodeNumberOfCites(searchDto.getYear());
        }
        if (totalFarmProvidedCodeNumberOfGoverment(searchDto.getYear()) != null) {
            totalFarmProvidedCodeNumber += totalFarmProvidedCodeNumberOfGoverment(searchDto.getYear());
        }
        if (farmProvidedCodeInYearNumberOfCites(searchDto.getYear()) != null) {
            totalFarmProvidedCodeInYearNumber += farmProvidedCodeInYearNumberOfCites(searchDto.getYear());
        }
        if (farmProvidedCodeInYearNumberOfGoverment(searchDto.getYear()) != null) {
            totalFarmProvidedCodeInYearNumber += farmProvidedCodeInYearNumberOfGoverment(searchDto.getYear());
        }
        dashboardCitesDto.setTotalAnimal(NumberUtils.convertNumberToString(totalAnimal));
        dashboardCitesDto.setTotalFarm(NumberUtils.convertNumberToString(totalFarm));
        dashboardCitesDto.setTotalSpecies(NumberUtils.convertNumberToString(totalSpecies));
        dashboardCitesDto.setTotalFarmProvidedCodeNumber(NumberUtils.convertNumberToString(totalFarmProvidedCodeNumber));
        dashboardCitesDto.setTotalFarmProvidedCodeInYearNumber(NumberUtils.convertNumberToString(totalFarmProvidedCodeInYearNumber));

        if (farmProvidedCodeInYearNumberOfCites(searchDto.getYear()) != null) {
            dashboardCitesDto.setFarmProvidedCodeInYearNumber(
                    NumberUtils.convertNumberToString(farmProvidedCodeInYearNumberOfCites(searchDto.getYear())));
        } else {
            dashboardCitesDto.setFarmProvidedCodeInYearNumber("0");
        }
        if (totalFarmProvidedCodeNumberOfCites(searchDto.getYear()) != null) {
            dashboardCitesDto.setFarmProvidedCodeNumber(
                    NumberUtils.convertNumberToString(totalFarmProvidedCodeNumberOfCites(searchDto.getYear())));
        } else {
            dashboardCitesDto.setFarmProvidedCodeNumber("0");
        }

        return dashboardCitesDto;
    }

    public Long totalFarmsNumber(Integer year) {
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
        String qCount = " select count(entity)from Farm entity where entity.name is not null and entity.isDuplicate is null ";
        String whereClause = " ";

        whereClause += "AND entity.yearRegistration =:year";

        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        qCount += whereClause;
        Query query = manager.createQuery(qCount);
        if (year != null) {
            query.setParameter("year", Integer.toString(year));
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            query.setParameter("year", year);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Long totalFarmsNeedProvidedCodeNumber(Integer year) {
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
        String qCount = " select count(DISTINCT entity.farm.id)from ReportForm16 as entity where  (entity.cites = 'II' or entity.vnlist06 = 'IIB') ";
        String whereClause = " ";
        if (year != null) {
            whereClause += "AND  year(entity.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        qCount += whereClause;
        Query query = manager.createQuery(qCount);
        if (year != null) {
            query.setParameter("year", year);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Long totalFarmProvidedCodeNumber(Integer year) {
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
        String qCount = " select count(DISTINCT entity.id)from Farm as entity inner join ReportForm16 as f16 on f16.farm.id = entity.id  where (f16.cites = 'II' or f16.vnlist06 = 'IIB') and entity.status = 3 and entity.name is not null and entity.isDuplicate is null  ";
        String whereClause = " ";
        whereClause += " AND year(f16.dateReport) =:year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        qCount += whereClause;
        Query query = manager.createQuery(qCount);
        if (year != null) {
            query.setParameter("year", year);

        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            query.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Long totalFarmProvidedCodeNumberOfCites(Integer year) {
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
        String qCount = " select count(DISTINCT entity.id)from Farm as entity inner join ReportForm16 as f16 on f16.farm.id = entity.id  where f16.cites = 'II'  and entity.status = 3 and entity.name is not null and entity.isDuplicate is null  ";
        String whereClause = " ";
        whereClause += " AND year(f16.dateReport) =:year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        qCount += whereClause;
        Query query = manager.createQuery(qCount);
        if (year != null) {
            query.setParameter("year", year);
        } else {
            Date date = new Date();
            Integer currentYear = WLDateTimeUtil.getYear(date);
            query.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public Long farmProvidedCodeInYearNumber(SearchDto searchDto) {
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
        String qCount = " select count(entity)from Farm entity  where entity.status = 3 and entity.name is not null and entity.isDuplicate is null ";
        String whereClause = " ";
        if (searchDto.getYear() != null) {
            whereClause += " AND year(f16.dateReport) =:year";
//			whereClause += " AND year(entity.dateOtherRegistration) =:dateYear";
        } else {
            whereClause += " AND year(f16.dateReport) =:year";
        }
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        qCount += whereClause;
        Query query = manager.createQuery(qCount);
        if (searchDto.getYear() != null) {
            query.setParameter("year", searchDto.getYear());
        } else {
            Date date = new Date();
            Integer year = WLDateTimeUtil.getYear(date);
            query.setParameter("year", year);
//			query.setParameter("dateYear", year);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Long farmProvidedCodeInYearNumberOfCites(Integer year) {
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
        String qCount = " select count(entity.id)from Farm entity inner join ReportForm16 as f16 on f16.farm.id = entity.id  where entity.status = 3 and entity.name is not null and entity.isDuplicate is null and f16.cites = 'II' ";
        String whereClause = " ";
        whereClause += " AND year(f16.dateReport) =:year";
        whereClause += " AND year(entity.dateOtherRegistration) =:dateYear";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        qCount += whereClause;
        Query query = manager.createQuery(qCount);
        if (year != null) {
            query.setParameter("year", year);
            query.setParameter("dateYear", year);

        } else {
            Date date = new Date();
            Integer currentYear = WLDateTimeUtil.getYear(date);
            query.setParameter("year", currentYear);
            query.setParameter("dateYear", currentYear);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public DashboardFarmDto getFarm(SearchDto searchDto) {
        DashboardFarmDto dashBoardFarmDto = new DashboardFarmDto();
        dashBoardFarmDto.setFarmsNumber(totalFarmsNumber(searchDto.getYear()));
        dashBoardFarmDto.setFarmProvidedCodeNumber(totalFarmProvidedCodeNumber(searchDto.getYear()));
        dashBoardFarmDto.setFarmsNeedProvidedCodeNumber(totalFarmsNeedProvidedCodeNumber(searchDto.getYear()));
        Float a = (float) totalFarmProvidedCodeNumber(searchDto.getYear());
        Float b = (float) totalFarmsNeedProvidedCodeNumber(searchDto.getYear());
        Float farmProvidedCodePercent = (a / b) * 100;
        dashBoardFarmDto.setFarmProvidedCodePercent((float) Math.ceil(farmProvidedCodePercent * 10) / 10);
        return dashBoardFarmDto;
    }

    @Override
    public DashboardVolatilityDto Volatility(SearchReportForm16Dto searchDto) {

        DashboardVolatilityDto dashBoardVolatilityDto = new DashboardVolatilityDto();
        //số  bản ghi lâm sản trong năm
        dashBoardVolatilityDto.setTotalListOfForestProductsByYear(totalListOfForestProductsByYear(searchDto.getYear()));

        //	dashBoardVolatilityDto.setIncreaseVolatilityOfTheBase(mostRecentTotalase(searchDto.getYear()) - farthestTotalBase(searchDto.getYear()));
        //	dashBoardVolatilityDto.setIncreasedVariationOfTheIndividual(currentNumberOfIndividuals(searchDto.getYear()) - numberOfIndividualsFirst(searchDto.getYear()));

        //	dashBoardVolatilityDto.setDecreasedVolatilityOfTheBase(farthestTotalBase(searchDto.getYear()) - mostRecentTotalase(searchDto.getYear()));
        //	dashBoardVolatilityDto.setDecreasedIndividualVariability(numberOfIndividualsFirst(searchDto.getYear()) - currentNumberOfIndividuals(searchDto.getYear()));

        //	dashBoardVolatilityDto.setChangesInTotalHouseholds(Math.abs(totalNumberOfCurrentRecords(searchDto.getYear()) - totalNumberOfPreviousRecords(searchDto.getYear())));


        // xuất bản kê lâm sản
        dashBoardVolatilityDto.setExportListOfForestProducts(ExportListOfForestProducts(searchDto.getYear()));
        // số cơ sở ngừng gây nuôi
        dashBoardVolatilityDto.setTotalEstablishmentStopsFarming(TotalEstablishmentStopsFarming(searchDto.getYear()));
        // số cơ sở đang ký nuôi mới
        dashBoardVolatilityDto.setRegistrationFacilityInTheYear(RegistrationFacilityInTheYear(searchDto.getYear()));
        // nhập bản kê lâm sản
        dashBoardVolatilityDto.setImportListOfForestProducts(importListOfForestProducts(searchDto.getYear()));
        dashBoardVolatilityDto = upAndDownVolatility(searchDto.getYear(), dashBoardVolatilityDto);


        // số hộ câp nhat trong năm
        if(dashBoardVolatilityDto != null) {
            if (upAndDownVolatility(searchDto.getYear(), dashBoardVolatilityDto) == null) {
                dashBoardVolatilityDto.setChangesInTotalHouseholds(0);
            } else if (dashBoardVolatilityDto.getIncreaseVolatilityOfTheBase() == null) {
                dashBoardVolatilityDto.setChangesInTotalHouseholds(upAndDownVolatility(searchDto.getYear(), dashBoardVolatilityDto).getDecreasedVolatilityOfTheBase());
            } else if (dashBoardVolatilityDto.getDecreasedVolatilityOfTheBase() == null) {
                dashBoardVolatilityDto.setChangesInTotalHouseholds(upAndDownVolatility(searchDto.getYear(), dashBoardVolatilityDto).getIncreaseVolatilityOfTheBase());
            } else if (dashBoardVolatilityDto.getIncreaseVolatilityOfTheBase() != null || dashBoardVolatilityDto.getDecreasedVolatilityOfTheBase() != null) {
                dashBoardVolatilityDto.setChangesInTotalHouseholds(upAndDownVolatility(searchDto.getYear(), dashBoardVolatilityDto).getIncreaseVolatilityOfTheBase() + upAndDownVolatility(searchDto.getYear(), dashBoardVolatilityDto).getDecreasedVolatilityOfTheBase());
            } else if (dashBoardVolatilityDto.getIncreaseVolatilityOfTheBase() == null || dashBoardVolatilityDto.getDecreasedVolatilityOfTheBase() == null) {
                dashBoardVolatilityDto.setChangesInTotalHouseholds(0);
            }
        }


        return dashBoardVolatilityDto;
    }

    public Long totalListOfForestProductsByYear(Integer year) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null) {
            currentUser = (User) authentication.getPrincipal();
        }
        boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
                || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP)
                || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH);
        List<Long> listWardId = null;
        if (!isAdmin) {
            listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
            if (listWardId == null || listWardId.size() == 0) {
                return null;
            }
        }
        String qCount = "select count(rp) from ForestProductsList rp where (1=1)";
        String where = "";

        if (year != null) {
            where += " AND year(rp.modifyDate) = :year";
        } else {
            where += " AND year(rp.modifyDate) = :year";
        }
        if (!isAdmin) {
            where += " AND rp.administrativeUnitFrom.id in :listWardId";
        }
        qCount += where;
        Query q = manager.createQuery(qCount);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

//	public Long mostRecentTotalase(Integer year) {
//
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = null;
//		if (authentication != null) {
//			currentUser = (User) authentication.getPrincipal();
//		}
//		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
//				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
//		List<Long> listWardId = null;
//		if (!isAdmin) {
//			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
//			if (listWardId == null || listWardId.size() == 0) {
//				return null;
//			}
//		}
//		String qCount = "select count(DISTINCT rp.farm.id) from ReportForm16 rp where (1=1)";
//		String where = "";
//		if (year != null) {
//			where += " AND  year(rp.modifyDate) = :year";
//		} else {
//			where += " AND  year(rp.modifyDate) = :year";
//		}
//		if (!isAdmin) {
//			where += " AND rp.administrativeUnit.id in :listWardId";
//		}
//		qCount += where;
//		Query q = manager.createQuery(qCount);
//		if (year != null) {
//			q.setParameter("year", year);
//		} else {
//			Date date = new Date();
//			int currentYear = WLDateTimeUtil.getYear(date);
//			q.setParameter("year", currentYear);
//		}
//		if (!isAdmin) {
//			q.setParameter("listWardId", listWardId);
//		}
//		Long result = (Long) q.getSingleResult();
//		return result;
//	}
//
//	public Long farthestTotalBase(Integer year) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = null;
//		if (authentication != null) {
//			currentUser = (User) authentication.getPrincipal();
//		}
//		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
//				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
//		List<Long> listWardId = null;
//		if (!isAdmin) {
//			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
//			if (listWardId == null || listWardId.size() == 0) {
//				return null;
//			}
//		}
//		String qCount = "select count(DISTINCT rp.farm.id) from ReportForm16 rp where (1=1)";
//		String where = "";
//		if (year != null) {
//			where += " AND  year(rp.dateReport) = :year ";
//		} else {
//			where += " AND  year(rp.dateReport) = :year ";
//		}
//		if (!isAdmin) {
//			where += " AND rp.administrativeUnit.id in :listWardId";
//		}
//		qCount += where;
//		Query q = manager.createQuery(qCount);
//		if (year != null) {
//			q.setParameter("year", year);
//		} else {
//			Date date = new Date();
//			int currentYear = WLDateTimeUtil.getYear(date);
//			q.setParameter("year", currentYear);
//		}
//		if (!isAdmin) {
//			q.setParameter("listWardId", listWardId);
//		}
//		Long result = (Long) q.getSingleResult();
//		return result;
//	}
//
//	public Long currentNumberOfIndividuals(Integer year) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = null;
//		if (authentication != null) {
//			currentUser = (User) authentication.getPrincipal();
//		}
//		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
//				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
//		List<Long> listWardId = null;
//		if (!isAdmin) {
//			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
//			if (listWardId == null || listWardId.size() == 0) {
//				return null;
//			}
//		}
//
//		String qCount = "select count(rp.total) from ReportForm16 rp where (1=1)";
//		String where = "";
//		if (year != null) {
//			where += " AND year(rp.modifyDate) = :year ";
//		} else {
//			where += " AND year(rp.modifyDate) = :year ";
//		}
//		if (!isAdmin) {
//			where += " AND rp.administrativeUnit.id in :listWardId";
//		}
//		qCount += where;
//		Query q = manager.createQuery(qCount);
//		if (year != null) {
//			q.setParameter("year", year);
//		} else {
//			Date date = new Date();
//			int currentYear = WLDateTimeUtil.getYear(date);
//			q.setParameter("year", currentYear);
//		}
//		if (!isAdmin) {
//			q.setParameter("listWardId", listWardId);
//		}
//		Long result = (Long) q.getSingleResult();
//		return result;
//	}
//
//	public Long numberOfIndividualsFirst(Integer year) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = null;
//		if (authentication != null) {
//			currentUser = (User) authentication.getPrincipal();
//		}
//		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
//				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
//		List<Long> listWardId = null;
//		if (!isAdmin) {
//			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
//			if (listWardId == null || listWardId.size() == 0) {
//				return null;
//			}
//		}
//
//		String qCount = "select count(rp.total) from ReportForm16 rp where (1=1)";
//		String where = " ";
//		if (year != null) {
//			where += " AND year(rp.dateReport) = :year ";
//		} else {
//			where += " AND year(rp.dateReport) = :year ";
//		}
//		if (!isAdmin) {
//			where += " AND rp.administrativeUnit.id in :listWardId";
//		}
//		qCount += where;
//		Query q = manager.createQuery(qCount);
//		if (year != null) {
//			q.setParameter("year", year);
//		} else {
//			Date date = new Date();
//			int currentYear = WLDateTimeUtil.getYear(date);
//			q.setParameter("year", currentYear);
//		}
//		if (!isAdmin) {
//			q.setParameter("listWardId", listWardId);
//		}
//		Long result = (Long) q.getSingleResult();
//		return result;
//
//	}

    public Long totalNumberOfCurrentRecords(Integer year) {
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
        String qCount = "select count(DISTINCT rp.farm.id) from ReportForm16 rp where (1=1)";
        String where = "";
        if (year != null) {
            where += "  AND year(rp.modifyDate) = :year ";
        } else {
            where += "  AND year(rp.modifyDate) = :year ";
        }
        if (!isAdmin) {
            where += " AND rp.administrativeUnit.id in :listWardId";
        }
        qCount += where;
        Query q = manager.createQuery(qCount);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;

    }

    public Long totalNumberOfPreviousRecords(Integer year) {
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
        String qCount = "select count(DISTINCT rp.farm.id) from ReportForm16 rp where (1=1)";
        String where = "";

        if (year != null) {
            where += " AND year(rp.dateReport) = :year";
        } else {
            where += " AND year(rp.dateReport) = :year";
        }
        if (!isAdmin) {
            where += " AND rp.administrativeUnit.id in :listWardId";
        }
        qCount += where;
        Query q = manager.createQuery(qCount);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;

    }

    public Long ExportListOfForestProducts(Integer year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null) {
            currentUser = (User) authentication.getPrincipal();
        }
        boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
                || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP)
                || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH);
        List<Long> listWardId = null;
        if (!isAdmin) {
            listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
            if (listWardId == null || listWardId.size() == 0) {
                return null;
            }
        }
        String qCount = "select count(f.canceled) from ForestProductsList f where f.canceled = 3 ";
        String where = "";

        if (year != null) {
            where += " AND  year(f.modifyDate) = :year";
        } else {
            where += " AND  year(f.modifyDate) = :year";
        }
        if (!isAdmin) {
            where += " AND f.administrativeUnitFrom.id in :listWardId";
        }

        qCount += where;
        Query q = manager.createQuery(qCount);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long TotalEstablishmentStopsFarming(Integer year) {
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
        String sql = "select count(re.farm) from ReportForm16 re where (re.male + re.female + unGender) = 0  ";
        String whereClause = "";
        if (year != null) {
            whereClause += " AND year(re.modifyDate) = :year";
        } else {
            whereClause += " AND year(re.modifyDate) = :year";
        }
        if (!isAdmin) {
            whereClause += " AND re.administrativeUnit.id in :listWardId";
        }

        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long RegistrationFacilityInTheYear(Integer year) {
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
        String sql = "select count( f.status) from Farm f where f.status =3  ";
        String whereClause = "";
        if (year != null) {
            whereClause += " AND year(f.dateRegistration) = :year";
        } else {
            whereClause += " AND year(f.dateRegistration) = :year";
        }

        if (!isAdmin) {
            whereClause += " AND f.ownerAdministrativeUnit.id in :listWardId";
        }

        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long importListOfForestProducts(Integer year) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null) {
            currentUser = (User) authentication.getPrincipal();
        }
        boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
                || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP)
                || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH);
        List<Long> listWardId = null;
        if (!isAdmin) {
            listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
            if (listWardId == null || listWardId.size() == 0) {
                return null;
            }
        }
        String qCount = "select count(im.forestProductsList) from ImportExportAnimal im where im.type = 1";
        String where = "";

        if (year != null) {
            where += " AND year(im.modifyDate) = :year";
        } else {
            where += " AND year(im.modifyDate) = :year";
        }
        if (!isAdmin) {
            where += " AND im.province.id in :listWardId";
        }
        qCount += where;
        Query q = manager.createQuery(qCount);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

//	public Long unchangedAtTheBeginningTheYear(Integer year) {
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		User currentUser = null;
//		if (authentication != null) {
//			currentUser = (User) authentication.getPrincipal();
//		}
//		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
//				|| SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP ) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH );
//		List<Long> listWardId = null;
//		if (!isAdmin) {
//			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
//			if (listWardId == null || listWardId.size() == 0) {
//				return null;
//			}
//		}
//		String qCount = "select count(DISTINCT total) from (select re.total,count(re.total) over (Partition by re.total) as SL from ReportForm16 re where (1=1)";
//		String where = "";
//
//		if (year != null) {
//			where += " AND year(re.dateReport) = :year";
//		}else {
//			where += " AND year(re.dateReport) = :year";
//		}
//		if (!isAdmin) {
//			where += " AND re.administrativeUnit.id in :listWardId ) t where t.SL > 1";
//		}
//
//		qCount += where;
//		Query q = manager.createQuery(qCount);
//		if (year != null) {
//			q.setParameter("year", year);
//		}
//		else{
//            Date date = new Date();
//            int currentYear = WLDateTimeUtil.getYear(date);
//            q.setParameter("year",currentYear);
//		}
//		if (!isAdmin) {
//			q.setParameter("listWardId", listWardId);
//		}
//		Long result = (Long) q.getSingleResult();
//		return result;
//	}

    //	 Dashboard phân nhóm theo NĐCP start
    public Long getNumberSpeciesIB(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.animal.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'IB' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberFarmIB(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.farm.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'IB' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";

        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberIndividualIB(Integer year) {
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
        String sql = "Select Sum(entity.total) From ReportForm16 entity " + " Where entity.vnlist06 = 'IB' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }


    public Long getNumberSpeciesKQL(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.animal.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'KQL' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberFarmKQL(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.farm.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'KQL' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberIndividualKQL(Integer year) {
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
        String sql = "Select Sum(entity.total) From ReportForm16 entity " + " Where entity.vnlist06 = 'KQL' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }


    public Long getNumberSpeciesPLCites(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.animal.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'PL_CITES' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberFarmPLCites(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.farm.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'PL_CITES' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberIndividualPLCites(Integer year) {
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
        String sql = "Select Sum(entity.total) From ReportForm16 entity " + " Where entity.vnlist06 = 'PL_CITES' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }


    public Long getNumberSpeciesIIB(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.animal.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'IIB' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }


    public Long getNumberFarmIIB(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.farm.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'IIB' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberIndividualIIB(Integer year) {
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
        String sql = "Select Sum(entity.total) From ReportForm16 entity " + " Where entity.vnlist06 = 'IIB' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberSpeciesDVRTT(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.animal.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'DVRTT' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberFarmDVRTT(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.farm.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = 'DVRTT' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberIndividualDVRTT(Integer year) {
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
        String sql = "Select Sum(entity.total) From ReportForm16 entity " + " Where entity.vnlist06 = 'DVRTT' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberSpecies(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.animal.id) From ReportForm16 entity "
                + " Where entity.vnlist06 = '' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberFarm(Integer year) {
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
        String sql = "Select Count(DISTINCT entity.farm.id) From ReportForm16 entity " + " Where entity.vnlist06 = '' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long getNumberIndividual(Integer year) {
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
        String sql = "Select Sum(entity.total) From ReportForm16 entity " + " Where entity.vnlist06 = '' ";
        String whereClause = "";
        whereClause += " AND Year(entity.dateReport) = :year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long totalFarmProvidedCodeNumberOfGoverment(Integer year) {
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
        String qCount = " select count(DISTINCT entity.id)from Farm as entity inner join ReportForm16 as f16 on f16.farm.id = entity.id  where f16.vnlist06 = 'IIB'  and entity.status = 3  and entity.name is not null and entity.isDuplicate is null  ";
        String whereClause = " ";
        whereClause += " AND year(f16.dateReport) =:year";
        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        qCount += whereClause;
        Query query = manager.createQuery(qCount);
        if (year != null) {
            query.setParameter("year", year);
        } else {
            Date date = new Date();
            Integer currentYear = WLDateTimeUtil.getYear(date);
            query.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    public Long farmProvidedCodeInYearNumberOfGoverment(Integer year) {
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
        String qCount = " select count(entity.id)from Farm entity inner join ReportForm16 as f16 on f16.farm.id = entity.id  where entity.status = 3  and entity.name is not null and entity.isDuplicate is null and f16.vnlist06 = 'IIB' ";
        String whereClause = " ";
        whereClause += " AND year(f16.dateReport) =:year";
        whereClause += " AND year(entity.dateOtherRegistration) =:dateYear";

        if (!isAdmin) {
            whereClause += " AND entity.administrativeUnit.id in :listWardId";
        }
        qCount += whereClause;
        Query query = manager.createQuery(qCount);
        if (year != null) {
            query.setParameter("year", year);
            query.setParameter("dateYear", year);

        } else {
            Date date = new Date();
            Integer currentYear = WLDateTimeUtil.getYear(date);
            query.setParameter("year", currentYear);
            query.setParameter("dateYear", currentYear);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        Long count = (Long) query.getSingleResult();
        return count;
    }

    @Override
    public DashboardGovermentDecreeDto getNumberGovermentDecree(SearchReportForm16Dto searchDto) {
        DashboardGovermentDecreeDto dto = new DashboardGovermentDecreeDto();
        if (getNumberSpeciesIB(searchDto.getYear()) != null) {
            dto.setNumberSpeciesIB(NumberUtils.convertNumberToString(getNumberSpeciesIB(searchDto.getYear())));
        } else {
            dto.setNumberSpeciesIB("0");
        }
        if (getNumberFarmIB(searchDto.getYear()) != null) {
            dto.setNumberFarmIB(NumberUtils.convertNumberToString(getNumberFarmIB(searchDto.getYear())));
        } else {
            dto.setNumberFarmIB("0");
        }
        if (getNumberIndividualIB(searchDto.getYear()) != null) {
            dto.setNumberIndividualIB(NumberUtils.convertNumberToString(getNumberIndividualIB(searchDto.getYear())));
        } else {
            dto.setNumberIndividualIB("0");
        }

        if (getNumberSpeciesIIB(searchDto.getYear()) != null) {
            dto.setNumberSpeciesIIB(NumberUtils.convertNumberToString(getNumberSpeciesIIB(searchDto.getYear())));
        } else {
            dto.setNumberSpeciesIIB("0");
        }
        if (getNumberFarmIIB(searchDto.getYear()) != null) {
            dto.setNumberFarmIIB(NumberUtils.convertNumberToString(getNumberFarmIIB(searchDto.getYear())));
        } else {
            dto.setNumberFarmIIB("0");
        }
        if (getNumberIndividualIIB(searchDto.getYear()) != null) {
            dto.setNumberIndividualIIB(NumberUtils.convertNumberToString(getNumberIndividualIIB(searchDto.getYear())));
        } else {
            dto.setNumberIndividualIIB("0");
        }

        if (getNumberSpeciesDVRTT(searchDto.getYear()) != null) {
            dto.setNumberSpeciesDVRTT(NumberUtils.convertNumberToString(getNumberSpeciesDVRTT(searchDto.getYear())));
        } else {
            dto.setNumberSpeciesDVRTT("0");
        }
        if (getNumberFarmDVRTT(searchDto.getYear()) != null) {
            dto.setNumberFarmDVRTT(NumberUtils.convertNumberToString(getNumberFarmDVRTT(searchDto.getYear())));
        } else {
            dto.setNumberFarmDVRTT("0");
        }

        if (getNumberIndividualDVRTT(searchDto.getYear()) != null) {
            dto.setNumberIndividualDVRTT(
                    NumberUtils.convertNumberToString(getNumberIndividualDVRTT(searchDto.getYear())));
        } else {
            dto.setNumberIndividualDVRTT("0");
        }

//		if (getNumberSpecies(searchDto.getYear()) != null) {
//			dto.setNumberSpecies(NumberUtils.convertNumberToString(getNumberSpecies(searchDto.getYear())));
//		} else {
//			dto.setNumberSpecies("0");
//		}
//		if (getNumberFarm(searchDto.getYear()) != null) {
//			dto.setNumberFarm(NumberUtils.convertNumberToString(getNumberFarm(searchDto.getYear())));
//		} else {
//			dto.setNumberFarm("0");
//		}
//		if (getNumberIndividual(searchDto.getYear()) != null) {
//			dto.setNumberIndividual(NumberUtils.convertNumberToString(getNumberIndividual(searchDto.getYear())));
//		} else {
//			dto.setNumberIndividual("0");
//		}

        if (totalFarmProvidedCodeNumberOfCites(searchDto.getYear()) != null) {
            dto.setOtherFarmProvidedCodeNumber(NumberUtils.convertNumberToString(totalFarmProvidedCodeNumberOfCites(searchDto.getYear())));
        } else {
            dto.setOtherFarmProvidedCodeNumber("0");
        }
        if (farmProvidedCodeInYearNumberOfCites(searchDto.getYear()) != null) {
            dto.setOtherFarmProvidedCodeInYearNumber(NumberUtils.convertNumberToString(farmProvidedCodeInYearNumberOfCites(searchDto.getYear())));
        } else {
            dto.setOtherFarmProvidedCodeInYearNumber("0");
        }
        long otherAnimal = 0;
        long otherFarm = 0;
        long otherSpecies = 0;


        if (getNumberSpeciesKQL(searchDto.getYear()) != null) {
            otherSpecies += getNumberSpeciesKQL(searchDto.getYear());
        }
        if (getNumberSpeciesPLCites(searchDto.getYear()) != null) {
            otherSpecies += getNumberSpeciesPLCites(searchDto.getYear());
        }
        if (getNumberFarmKQL(searchDto.getYear()) != null) {
            otherFarm += getNumberFarmKQL(searchDto.getYear());
        }
        if (getNumberFarmPLCites(searchDto.getYear()) != null) {
            otherFarm += getNumberFarmPLCites(searchDto.getYear());
        }
        if (getNumberIndividualKQL(searchDto.getYear()) != null) {
            otherAnimal += getNumberIndividualKQL(searchDto.getYear());
        }
        if (getNumberIndividualPLCites(searchDto.getYear()) != null) {
            otherAnimal += getNumberIndividualPLCites(searchDto.getYear());
        }

        dto.setNumberSpecies(NumberUtils.convertNumberToString(otherSpecies));

        dto.setNumberFarm(NumberUtils.convertNumberToString(otherFarm));

        dto.setNumberIndividual(NumberUtils.convertNumberToString(otherAnimal));


        long totalIndividual = 0;
        long totalFarm = 0;
        long totalSpecies = 0;
        long totalFarmProvidedCodeNumber = 0;
        long totalFarmProvidedCodeInYearNumber = 0;

        if (getNumberIndividualIB(searchDto.getYear()) != null) {
            totalIndividual += getNumberIndividualIB(searchDto.getYear());
        }
//		if (getNumberIndividual(searchDto.getYear()) != null) {
//			totalIndividual += getNumberIndividual(searchDto.getYear());
//		}
        totalIndividual += otherAnimal;
        if (getNumberIndividualIIB(searchDto.getYear()) != null) {
            totalIndividual += getNumberIndividualIIB(searchDto.getYear());
        }
        if (getNumberIndividualDVRTT(searchDto.getYear()) != null) {
            totalIndividual += getNumberIndividualDVRTT(searchDto.getYear());
        }
//		if (getNumberFarm(searchDto.getYear()) != null) {
//			totalFarm += getNumberFarm(searchDto.getYear());
//		}
        totalFarm += otherFarm;
        if (getNumberFarmIB(searchDto.getYear()) != null) {
            totalFarm += getNumberFarmIB(searchDto.getYear());
        }
        if (getNumberFarmIIB(searchDto.getYear()) != null) {
            totalFarm += getNumberFarmIIB(searchDto.getYear());
        }
        if (getNumberFarmDVRTT(searchDto.getYear()) != null) {
            totalFarm += getNumberFarmDVRTT(searchDto.getYear());
        }
//		if (getNumberSpecies(searchDto.getYear()) != null) {
//			totalSpecies += getNumberSpecies(searchDto.getYear());
//		}
        totalSpecies += otherSpecies;
        if (getNumberSpeciesIB(searchDto.getYear()) != null) {
            totalSpecies += getNumberSpeciesIB(searchDto.getYear());
        }
        if (getNumberSpeciesIIB(searchDto.getYear()) != null) {
            totalSpecies += getNumberSpeciesIIB(searchDto.getYear());
        }
        if (getNumberSpeciesDVRTT(searchDto.getYear()) != null) {
            totalSpecies += getNumberSpeciesDVRTT(searchDto.getYear());
        }


        if (totalFarmProvidedCodeNumberOfCites(searchDto.getYear()) != null) {
            totalFarmProvidedCodeNumber += totalFarmProvidedCodeNumberOfCites(searchDto.getYear());
        }
        if (totalFarmProvidedCodeNumberOfGoverment(searchDto.getYear()) != null) {
            totalFarmProvidedCodeNumber += totalFarmProvidedCodeNumberOfGoverment(searchDto.getYear());
        }
        if (farmProvidedCodeInYearNumberOfCites(searchDto.getYear()) != null) {
            totalFarmProvidedCodeInYearNumber += farmProvidedCodeInYearNumberOfCites(searchDto.getYear());
        }
        if (farmProvidedCodeInYearNumberOfGoverment(searchDto.getYear()) != null) {
            totalFarmProvidedCodeInYearNumber += farmProvidedCodeInYearNumberOfGoverment(searchDto.getYear());
        }
        dto.setTotalIndividual(NumberUtils.convertNumberToString(totalIndividual));
        dto.setTotalFarm(NumberUtils.convertNumberToString(totalFarm));
        dto.setTotalSpecies(NumberUtils.convertNumberToString(totalSpecies));
        dto.setTotalFarmProvidedCodeNumber(NumberUtils.convertNumberToString(totalFarmProvidedCodeNumber));
        dto.setTotalFarmProvidedCodeInYearNumber(NumberUtils.convertNumberToString(totalFarmProvidedCodeInYearNumber));
        if (totalFarmProvidedCodeNumberOfGoverment(searchDto.getYear()) != null) {
            dto.setTotalFarmProvidedCodeNumberOfGoverment(
                    NumberUtils.convertNumberToString(totalFarmProvidedCodeNumberOfGoverment(searchDto.getYear())));
        } else {
            dto.setTotalFarmProvidedCodeNumberOfGoverment("0");
        }
        if (farmProvidedCodeInYearNumberOfGoverment(searchDto.getYear()) != null) {
            dto.setTotalFarmProvidedCodeNumberInYearOfGoverment(
                    NumberUtils.convertNumberToString(farmProvidedCodeInYearNumberOfGoverment(searchDto.getYear())));
        } else {
            dto.setTotalFarmProvidedCodeNumberInYearOfGoverment("0");
        }
        return dto;
    }

    @Override
    public DashBoardCitesVnDto CitesVn(SearchReportForm16Dto searchDto) {
        DashBoardCitesVnDto dashCitesVn = new DashBoardCitesVnDto();

        dashCitesVn
                .setNumberOfCodesToBeIssuedInTheYearCitesI(NumberOfCodesToBeIssuedInTheYearCitesI(searchDto.getYear()));// số
        // hộ
        // cần
        // cấp
        // mã
        // nhóm
        // cites:I
        dashCitesVn.setNumberOfCodesToBeIssuedInTheYearCitesII(
                NumberOfCodesToBeIssuedInTheYearCitesII(searchDto.getYear()));

        return dashCitesVn;
    }

    public Long NumberOfCodesToBeIssuedInTheYearCitesI(Integer year) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User currentUser = null;
        if (authentication != null) {
            currentUser = (User) authentication.getPrincipal();
        }
        boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN)
                || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP)
                || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_SDAH);
        List<Long> listWardId = null;
        if (!isAdmin) {
            listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
            if (listWardId == null || listWardId.size() == 0) {
                return null;
            }
        }
        String sql = "select count(*)from Farm fa inner join ReportForm16 f16 on f16.farm.id = fa.id  where  fa.status= 2 and f16.cites = 'I' ";
        String whereClause = "";
        if (year != null) {
            whereClause += " AND year(f16.modifyDate) = :year";
        } else {
            whereClause += " AND year(f16.modifyDate) = :year";
        }
        if (!isAdmin) {
            whereClause += " AND f16.administrativeUnit.id in :listWardId";
        }

        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public Long NumberOfCodesToBeIssuedInTheYearCitesII(Integer year) {
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
        String sql = "select count(*)from Farm fa inner join ReportForm16 f16 on f16.farm.id = fa.id  where  fa.status= 2 and f16.cites = 'II' ";
        String whereClause = "";
        if (year != null) {
            whereClause += " AND year(f16.modifyDate) = :year";
        } else {
            whereClause += " AND year(f16.modifyDate) = :year";
        }
        if (!isAdmin) {
            whereClause += " AND f16.administrativeUnit.id in :listWardId";
        }

        sql += whereClause;
        Query q = manager.createQuery(sql);
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        Long result = (Long) q.getSingleResult();
        return result;
    }

    public DashboardVolatilityDto upAndDownVolatility(Integer year, DashboardVolatilityDto dto) {
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
        String whereClause = "";
        if (!isAdmin) {
            whereClause += " AND w.id in :listWardId ";
        }
        String sql = "select count(distinct farmId) as countFarm, sum(total) as total, temp\r\n"
                + "from (select tb1.animalId,tb2.farmId,tb1.total - tb2.total as total,case\r\n"
                + "                 when tb1.total > tb2.total then 1\r\n"
                + "                 when tb1.total < tb2.total then -1\r\n"
                + "                 end               as temp\r\n"
                + "      from (select ROW_NUMBER() OVER (PARTITION BY f16.farm_id,f16.animal_id ORDER BY f16.date_report,f16.modify_date,f16.create_date DESC )\r\n"
                + "                                 AS rowNumber,\r\n"
                + "                   f16.animal_id as animalId,\r\n"
                + "                   f16.farm_id   as farmId,\r\n" + "                   f16.total     as total\r\n"
                + "            from tbl_report_form16 as f16\r\n"
                + "                  INNER JOIN tbl_report_period p ON f16.report_period_id = p.id\n"
                + "                  INNER JOIN tbl_fms_administrative_unit prov ON prov.id = p.province_id AND prov.parent_id IS NULL\n"
                + "                  INNER JOIN tbl_fms_administrative_unit dis ON dis.id = p.district_id AND dis.parent_id IS NOT NULL\n"
                + "                  INNER JOIN tbl_fms_administrative_unit w\n"
                + "                             ON w.id = p.administrative_unit_id AND w.parent_id IS NOT NULL\n"
                + "            where 1 = 1\r\n" + "              and year(f16.date_report) = :year " + whereClause
                + " ) \r\n" + "               as tb1 \r\n"
                + "               left join (select ROW_NUMBER() OVER (PARTITION BY f16.farm_id,f16.animal_id ORDER BY f16.date_report,f16.modify_date,f16.create_date DESC )\r\n"
                + "                                               AS rowNumber,\r\n"
                + "                                 f16.animal_id as animalId,\r\n"
                + "                                 f16.farm_id   as farmId,\r\n"
                + "                                 f16.total     as total\r\n"
                + "                          from tbl_report_form16 as f16\r\n"
                + "                          where 1 = 1\r\n"
                + "                            and year(f16.date_report) <:year)\r\n"
                + "          as tb2 on tb1.animalId = tb2.animalId and tb1.farmId = tb2.farmId\r\n"
                + "      where tb1.rowNumber = 1\r\n" + "        and tb2.rowNumber = 1) as tbl\r\n"
                + "where tbl.temp is not null\r\n" + "group by tbl.temp";

        Query q = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashBoardUpDownDto.class));
        if (year != null) {
            q.setParameter("year", year);
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashBoardUpDownDto> result = (List<DashBoardUpDownDto>) q.getResultList();
        if (result != null) {

            for (DashBoardUpDownDto rs : result) {

                if (rs.getTemp() > 0) {
                    dto.setIncreaseVolatilityOfTheBase(Math.abs(rs.getCountFarm()));
                    dto.setIncreasedVariationOfTheIndividual(Math.abs(rs.getTotal()));
                } else {
                    dto.setDecreasedIndividualVariability(Math.abs(rs.getTotal()));
                    dto.setDecreasedVolatilityOfTheBase(Math.abs(rs.getCountFarm()));
                }


            }
        }
        if (result == null) {
            dto.setChangesInTotalHouseholds(0);
        }

        return dto;

    }

    @Override
    public DashBoardUpDownDto upDown(SearchReportForm16Dto searchDto) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public DashboardTotalDtoCites totalDtoCites(DashboardTotalDtoCites dto) {
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
        String whereClause = "";
        String groupBy = "group by cites";

        if (!isAdmin) {
            whereClause += "AND f16.administrativeUnit.id in :listWardId ";
        }
        String sql = "select count(distinct farmId) as totalFarm, sum(total) as totalAnimal ,count(distinct animalId) as totalSpecies, cites\n" +
                "      from (select ROW_NUMBER() OVER (PARTITION BY f16.farm_id,f16.animal_id ORDER BY f16.date_report,f16.modify_date,f16.create_date DESC )\n" +
                "                                               AS rowNumber,\n" +
                "                                 f16.animal_id as animalId,\n" +
                "                                 f16.farm_id   as farmId,\n" +
                "                                 f16.total     as total,\n" +
                "								 f16.cites     as cites\n" +
                "            from tbl_report_form16 as f16               \n" +
                "            where year(f16.date_report) = :year " + whereClause + ")\n" +
                "               as tb1                \n" +
                "      where tb1.rowNumber = 1      \n";
        Query q = manager.createNativeQuery(sql + groupBy).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalDtoCites.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalDtoCites> result = (List<DashboardTotalDtoCites>) q.getResultList();
        int otherAnimal = 0;
        int otherFarm = 0;
        int otherSpecies = 0;
        int temp = 0;
        int totalFarm = 0;
        int totalAnimal = 0;
        int totalSpecies = 0;
        long totalFarmProvidedCodeNumber = 0;
        long totalFarmProvidedCodeInYearNumber = 0;
        dto.setAnimalsNumberPLI(temp);
        dto.setAnimalsNumberPLII(temp);
        dto.setAnimalsNumberPLIII(temp);
        dto.setFarmsNumberPLI(temp);
        dto.setFarmsNumberPLII(temp);
        dto.setFarmsNumberPLIII(temp);
        dto.setSpeciesNumberPLI(temp);
        dto.setSpeciesNumberPLII(temp);
        dto.setSpeciesNumberPLIII(temp);
        dto.setAnimalsNumberOther(otherAnimal);
        dto.setFarmsNumberOther(otherFarm);
        dto.setSpeciesNumberOther(otherSpecies);
        if (result != null && result.size() > 0) {
            for (DashboardTotalDtoCites listCites : result) {
                totalAnimal += listCites.getTotalAnimal();

                totalFarm += listCites.getTotalFarm();

                totalSpecies += listCites.getTotalSpecies();

            }
        }
        if (this.citesPLI(dto) != null) {
            dto.setAnimalsNumberPLI(citesPLI(dto).getAnimalsNumberPLI());
            dto.setFarmsNumberPLI(citesPLI(dto).getFarmsNumberPLI());
            dto.setSpeciesNumberPLI(citesPLI(dto).getSpeciesNumberPLI());
        }
        if (this.citesPLII(dto) != null) {
            dto.setAnimalsNumberPLII(citesPLII(dto).getAnimalsNumberPLII());
            dto.setFarmsNumberPLII(citesPLII(dto).getFarmsNumberPLII());
            dto.setSpeciesNumberPLII(citesPLII(dto).getSpeciesNumberPLII());
        }
        if (this.citesPLIII(dto) != null) {
            dto.setAnimalsNumberPLIII(citesPLIII(dto).getAnimalsNumberPLIII());
            dto.setFarmsNumberPLIII(citesPLIII(dto).getFarmsNumberPLIII());
            dto.setSpeciesNumberPLIII(citesPLIII(dto).getSpeciesNumberPLIII());
        }
        if (this.citesOther(dto) != null) {
            dto.setAnimalsNumberOther(citesOther(dto).getAnimalsNumberOther());
            dto.setFarmsNumberOther(citesOther(dto).getFarmsNumberOther());
            dto.setSpeciesNumberOther(citesOther(dto).getSpeciesNumberOther());
        }

        dto.setTotalAnimal(totalAnimal);
        dto.setTotalFarm(totalFarm);
        dto.setTotalSpecies(totalSpecies);

        if (totalFarmProvidedCodeNumberOfCites(dto.getYear()) != null) {
            totalFarmProvidedCodeNumber += totalFarmProvidedCodeNumberOfCites(dto.getYear());
        }
        if (totalFarmProvidedCodeNumberOfGoverment(dto.getYear()) != null) {
            totalFarmProvidedCodeNumber += totalFarmProvidedCodeNumberOfGoverment(dto.getYear());
        }
        if (farmProvidedCodeInYearNumberOfCites(dto.getYear()) != null) {
            totalFarmProvidedCodeInYearNumber += farmProvidedCodeInYearNumberOfCites(dto.getYear());
        }
        if (farmProvidedCodeInYearNumberOfGoverment(dto.getYear()) != null) {
            totalFarmProvidedCodeInYearNumber += farmProvidedCodeInYearNumberOfGoverment(dto.getYear());
        }
        dto.setTotalFarmProvidedCodeNumber(totalFarmProvidedCodeNumber);
        dto.setTotalFarmProvidedCodeInYearNumber(totalFarmProvidedCodeInYearNumber);

        if (farmProvidedCodeInYearNumberOfCites(dto.getYear()) != null) {
            dto.setFarmProvidedCodeInYearNumber(farmProvidedCodeInYearNumberOfCites(dto.getYear()));
        }
        if (totalFarmProvidedCodeNumberOfCites(dto.getYear()) != null) {
            dto.setFarmProvidedCodeNumber(totalFarmProvidedCodeNumberOfCites(dto.getYear()));
        }
        if (totalFarmProvidedCodeNumberOfGoverment(dto.getYear()) != null) {
            dto.setOtherFarmProvidedCodeNumber(totalFarmProvidedCodeNumberOfGoverment(dto.getYear()));
        }
        if (farmProvidedCodeInYearNumberOfGoverment(dto.getYear()) != null) {
            dto.setOtherFarmProvidedCodeInYearNumber(farmProvidedCodeInYearNumberOfGoverment(dto.getYear()));
        }
        return dto;
    }

    @Override
    public DashboardTotalGrovermentDecreeDto totalGroverment(DashboardTotalGrovermentDecreeDto dto) {
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
        String whereClause = "";
        String groupBy = "group by vnlist06";

        if (!isAdmin) {
            whereClause += "AND f16.administrativeUnit.id in :listWardId ";
        }
        String sql = "select count(distinct farmId) as totalFarm, sum(total) as totalAnimal ,count(distinct animalId) as totalSpecies, vnlist06\n" +
                "      from (select ROW_NUMBER() OVER (PARTITION BY f16.farm_id,f16.animal_id ORDER BY f16.date_report,f16.modify_date,f16.create_date DESC )\n" +
                "                                               AS rowNumber,\n" +
                "                                 f16.animal_id as animalId,\n" +
                "                                 f16.farm_id   as farmId,\n" +
                "                                 f16.total     as total,\n" +
                "								 f16.vnlist06     as vnlist06\n" +
                "            from tbl_report_form16 as f16               \n" +
                "            where year(f16.date_report) = :year " + whereClause + ")\n" +
                "               as tb1                \n" +
                "      where tb1.rowNumber = 1      \n";
        Query q = manager.createNativeQuery(sql + groupBy).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalGrovermentDecreeDto.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalGrovermentDecreeDto> result = (List<DashboardTotalGrovermentDecreeDto>) q.getResultList();
        int otherAnimal = 0;
        int otherFarm = 0;
        int otherSpecies = 0;
        int temp = 0;
        int totalFarm = 0;
        int totalAnimal = 0;
        int totalSpecies = 0;
        long totalFarmProvidedCodeNumber = 0;
        long totalFarmProvidedCodeInYearNumber = 0;
        dto.setNumberFarmIB(temp);
        dto.setNumberAnimalIB(temp);
        dto.setNumberFarmDVRTT(temp);
        dto.setNumberFarmIIB(temp);
        dto.setNumberAnimalIIB(temp);
        dto.setNumberAnimalDVRTT(temp);
        dto.setNumberFarmDVRTT(temp);
        dto.setNumberAnimalDVRTT(temp);
        dto.setNumberSpeciesDVRTT(temp);
        dto.setNumberSpeciesOther(otherSpecies);
        dto.setNumberAnimalOther(otherAnimal);
        dto.setNumberFarmOther(otherFarm);
        if (result != null && result.size() > 0) {
            for (DashboardTotalGrovermentDecreeDto listGroverment : result) {
                totalAnimal += listGroverment.getTotalAnimal();
                totalFarm += listGroverment.getTotalFarm();
                totalSpecies += listGroverment.getTotalSpecies();
            }
        }
        if (this.grovermentIB(dto) != null) {
            dto.setNumberSpeciesIB(grovermentIB(dto).getNumberSpeciesIB());
            dto.setNumberFarmIB(grovermentIB(dto).getNumberFarmIB());
            dto.setNumberAnimalIB(grovermentIB(dto).getNumberAnimalIB());
        }
        if (this.grovermentIIB(dto) != null) {
            dto.setNumberSpeciesIIB(grovermentIIB(dto).getNumberSpeciesIIB());
            dto.setNumberFarmIIB(grovermentIIB(dto).getNumberFarmIIB());
            dto.setNumberAnimalIIB(grovermentIIB(dto).getNumberAnimalIIB());
        }
        if (this.grovermentDVRTT(dto) != null) {
            dto.setNumberSpeciesDVRTT(grovermentDVRTT(dto).getNumberSpeciesDVRTT());
            dto.setNumberFarmDVRTT(grovermentDVRTT(dto).getNumberFarmDVRTT());
            dto.setNumberAnimalDVRTT(grovermentDVRTT(dto).getNumberAnimalDVRTT());
        }
        if (this.grovermentOther(dto) != null) {
            dto.setNumberSpeciesOther(grovermentOther(dto).getNumberSpeciesOther());
            dto.setNumberFarmOther(grovermentOther(dto).getNumberFarmOther());
            dto.setNumberAnimalOther(grovermentOther(dto).getNumberAnimalOther());
        }


        dto.setTotalAnimal(totalAnimal);
        dto.setTotalFarm(totalFarm);
        dto.setTotalSpecies(totalSpecies);

        if (totalFarmProvidedCodeNumberOfCites(dto.getYear()) != null) {
            totalFarmProvidedCodeNumber += totalFarmProvidedCodeNumberOfCites(dto.getYear());
        }
        if (totalFarmProvidedCodeNumberOfGoverment(dto.getYear()) != null) {
            totalFarmProvidedCodeNumber += totalFarmProvidedCodeNumberOfGoverment(dto.getYear());
        }
        if (farmProvidedCodeInYearNumberOfCites(dto.getYear()) != null) {
            totalFarmProvidedCodeInYearNumber += farmProvidedCodeInYearNumberOfCites(dto.getYear());
        }
        if (farmProvidedCodeInYearNumberOfGoverment(dto.getYear()) != null) {
            totalFarmProvidedCodeInYearNumber += farmProvidedCodeInYearNumberOfGoverment(dto.getYear());
        }
        dto.setTotalFarmProvidedCodeNumber(totalFarmProvidedCodeNumber);
        dto.setTotalFarmProvidedCodeInYearNumber(totalFarmProvidedCodeInYearNumber);

        if (farmProvidedCodeInYearNumberOfGoverment(dto.getYear()) != null) {
            dto.setFarmProvidedCodeInYearNumber(farmProvidedCodeInYearNumberOfGoverment(dto.getYear()));
        }
        if (totalFarmProvidedCodeNumberOfGoverment(dto.getYear()) != null) {
            dto.setFarmProvidedCodeNumber(totalFarmProvidedCodeNumberOfGoverment(dto.getYear()));
        }
        if (totalFarmProvidedCodeNumberOfCites(dto.getYear()) != null) {
            dto.setOtherFarmProvidedCodeNumber(totalFarmProvidedCodeNumberOfCites(dto.getYear()));
        }
        if (farmProvidedCodeInYearNumberOfCites(dto.getYear()) != null) {
            dto.setOtherFarmProvidedCodeInYearNumber(farmProvidedCodeInYearNumberOfCites(dto.getYear()));
        }
        return dto;
    }

    public DashboardTotalGrovermentDecreeDto grovermentIB(DashboardTotalGrovermentDecreeDto dto) {
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
        String whereClause = "";
        if (!isAdmin) {
            whereClause += "AND tb1.listWardId in :listWardId ";
        }
        String sql = "Select Count(DISTINCT farmId) as numberFarmIB, sum(totalAnimal) as numberAnimalIB, count(DISTINCT Species) as numberSpeciesIB from \n" +
                "(select ROW_NUMBER() OVER (PARTITION BY entity.farm_id,entity.animal_id ORDER BY entity.date_report,entity.modify_date,entity.create_date DESC )\n" +
                "AS rowNumber, \n" +
                "entity.farm_id as farmId, \n" +
                "entity.animal_id as Species,\n" +
                "entity.total as totalAnimal,\n" +
                "entity.vnlist06 as vnlist06,\n" +
                "entity.administrative_unit_id as listWardId \n" +
                "From tbl_report_form16 entity where year(date_report) =:year) \n" +
                "as tb1 Where tb1.rowNumber = 1 and tb1.vnlist06 = 'IB' ";
        Query q = manager.createNativeQuery(sql + whereClause).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalGrovermentDecreeDto.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalGrovermentDecreeDto> result = (List<DashboardTotalGrovermentDecreeDto>) q.getResultList();
        for (DashboardTotalGrovermentDecreeDto dtos : result) {
            if (dtos != null) {
                dto.setNumberAnimalIB(dtos.getNumberAnimalIB());
                dto.setNumberFarmIB(dtos.getNumberFarmIB());
                dto.setNumberSpeciesIB(dtos.getNumberSpeciesIB());
            }
        }

        return dto;
    }

    public DashboardTotalGrovermentDecreeDto grovermentIIB(DashboardTotalGrovermentDecreeDto dto) {
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
        String whereClause = "";
        if (!isAdmin) {
            whereClause += "AND tb1.listWardId in :listWardId ";
        }
        String sql = "Select Count(DISTINCT farmId) as numberFarmIIB, sum(totalAnimal) as numberAnimalIIB, count(DISTINCT Species) as numberSpeciesIIB from \n" +
                "(select ROW_NUMBER() OVER (PARTITION BY entity.farm_id,entity.animal_id ORDER BY entity.date_report,entity.modify_date,entity.create_date DESC )\n" +
                "AS rowNumber, \n" +
                "entity.farm_id as farmId, \n" +
                "entity.animal_id as Species,\n" +
                "entity.total as totalAnimal,\n" +
                "entity.vnlist06 as vnlist06,\n" +
                "entity.administrative_unit_id as listWardId \n" +
                "From tbl_report_form16 entity where year(date_report) =:year) \n" +
                "as tb1 Where tb1.rowNumber = 1 and tb1.vnlist06 = 'IIB' ";
        Query q = manager.createNativeQuery(sql + whereClause).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalGrovermentDecreeDto.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalGrovermentDecreeDto> result = (List<DashboardTotalGrovermentDecreeDto>) q.getResultList();
        for (DashboardTotalGrovermentDecreeDto dtos : result) {
            if (dtos != null) {
                dto.setNumberAnimalIIB(dtos.getNumberAnimalIIB());
                dto.setNumberFarmIIB(dtos.getNumberFarmIIB());
                dto.setNumberSpeciesIIB(dtos.getNumberSpeciesIIB());
            }
        }

        return dto;
    }

    public DashboardTotalGrovermentDecreeDto grovermentDVRTT(DashboardTotalGrovermentDecreeDto dto) {
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
        String whereClause = "";
        if (!isAdmin) {
            whereClause += "AND tb1.listWardId in :listWardId ";
        }
        String sql = "Select Count(DISTINCT farmId) as numberFarmDVRTT, sum(totalAnimal) as numberAnimalDVRTT, count(DISTINCT Species) as numberSpeciesDVRTT from \n" +
                "(select ROW_NUMBER() OVER (PARTITION BY entity.farm_id,entity.animal_id ORDER BY entity.date_report,entity.modify_date,entity.create_date DESC )\n" +
                "AS rowNumber, \n" +
                "entity.farm_id as farmId, \n" +
                "entity.animal_id as Species,\n" +
                "entity.total as totalAnimal,\n" +
                "entity.vnlist06 as vnlist06,\n" +
                "entity.administrative_unit_id as listWardId \n" +
                "From tbl_report_form16 entity where year(date_report) =:year) \n" +
                "as tb1 Where tb1.rowNumber = 1 and tb1.vnlist06 = 'DVRTT' ";
        Query q = manager.createNativeQuery(sql + whereClause).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalGrovermentDecreeDto.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalGrovermentDecreeDto> result = (List<DashboardTotalGrovermentDecreeDto>) q.getResultList();
        for (DashboardTotalGrovermentDecreeDto dtos : result) {
            if (dtos != null) {
                dto.setNumberAnimalDVRTT(dtos.getNumberAnimalDVRTT());
                dto.setNumberFarmDVRTT(dtos.getNumberFarmDVRTT());
                dto.setNumberSpeciesDVRTT(dtos.getNumberSpeciesDVRTT());
            }
        }

        return dto;
    }

    public DashboardTotalGrovermentDecreeDto grovermentOther(DashboardTotalGrovermentDecreeDto dto) {
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
        String whereClause = "";
        if (!isAdmin) {
            whereClause += "AND tb1.listWardId in :listWardId ";
        }
        String sql = "Select Count(DISTINCT farmId) as numberFarmOther, sum(totalAnimal) as numberAnimalOther, count(DISTINCT Species) as numberSpeciesOther from \n" +
                "(select ROW_NUMBER() OVER (PARTITION BY entity.farm_id,entity.animal_id ORDER BY entity.date_report,entity.modify_date,entity.create_date DESC )\n" +
                "AS rowNumber, \n" +
                "entity.farm_id as farmId, \n" +
                "entity.animal_id as Species,\n" +
                "entity.total as totalAnimal,\n" +
                "entity.vnlist06 as vnlist06,\n" +
                "entity.administrative_unit_id as listWardId \n" +
                "From tbl_report_form16 entity where year(date_report) =:year) \n" +
                "as tb1 Where tb1.rowNumber = 1 and tb1.vnlist06 != 'DVRTT'  and tb1.vnlist06 != 'IB' and  tb1.vnlist06 != 'IIB' ";
        Query q = manager.createNativeQuery(sql + whereClause).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalGrovermentDecreeDto.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalGrovermentDecreeDto> result = (List<DashboardTotalGrovermentDecreeDto>) q.getResultList();
        for (DashboardTotalGrovermentDecreeDto dtos : result) {
            if (dtos != null) {
                dto.setNumberAnimalOther(dtos.getNumberAnimalOther());
                dto.setNumberFarmOther(dtos.getNumberFarmOther());
                dto.setNumberSpeciesOther(dtos.getNumberSpeciesOther());
            }
        }

        return dto;
    }

    public DashboardTotalDtoCites citesPLI(DashboardTotalDtoCites dto) {
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
        String whereClause = "";
        if (!isAdmin) {
            whereClause += "AND tb1.listWardId in :listWardId ";
        }
        String sql = "Select Count(DISTINCT farmId) as farmsNumberPLI, sum(totalAnimal) as animalsNumberPLI, count(DISTINCT Species) as speciesNumberPLI from \n" +
                "(select ROW_NUMBER() OVER (PARTITION BY entity.farm_id,entity.animal_id ORDER BY entity.date_report,entity.modify_date,entity.create_date DESC )\n" +
                "AS rowNumber, \n" +
                "entity.farm_id as farmId, \n" +
                "entity.animal_id as Species,\n" +
                "entity.total as totalAnimal,\n" +
                "entity.cites as cites,\n" +
                "entity.administrative_unit_id as listWardId \n" +
                "From tbl_report_form16 entity where year(date_report) =:year) \n" +
                "as tb1 Where tb1.rowNumber = 1 and tb1.cites = 'I' ";
        Query q = manager.createNativeQuery(sql + whereClause).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalDtoCites.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalDtoCites> result = (List<DashboardTotalDtoCites>) q.getResultList();
        for (DashboardTotalDtoCites dtos : result) {
            if (dtos != null) {
                dto.setAnimalsNumberPLI(dtos.getAnimalsNumberPLI());
                dto.setFarmsNumberPLI(dtos.getFarmsNumberPLI());
                dto.setSpeciesNumberPLI(dtos.getSpeciesNumberPLI());
            }
        }

        return dto;
    }

    public DashboardTotalDtoCites citesPLII(DashboardTotalDtoCites dto) {
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
        String whereClause = "";
        if (!isAdmin) {
            whereClause += "AND tb1.listWardId in :listWardId ";
        }
        String sql = "Select Count(DISTINCT farmId) as farmsNumberPLII, sum(totalAnimal) as animalsNumberPLII, count(DISTINCT Species) as speciesNumberPLII from \n" +
                "(select ROW_NUMBER() OVER (PARTITION BY entity.farm_id,entity.animal_id ORDER BY entity.date_report,entity.modify_date,entity.create_date DESC )\n" +
                "AS rowNumber, \n" +
                "entity.farm_id as farmId, \n" +
                "entity.animal_id as Species,\n" +
                "entity.total as totalAnimal,\n" +
                "entity.cites as cites,\n" +
                "entity.administrative_unit_id as listWardId \n" +
                "From tbl_report_form16 entity where year(date_report) =:year) \n" +
                "as tb1 Where tb1.rowNumber = 1 and tb1.cites = 'II' ";
        Query q = manager.createNativeQuery(sql + whereClause).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalDtoCites.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalDtoCites> result = (List<DashboardTotalDtoCites>) q.getResultList();
        for (DashboardTotalDtoCites dtos : result) {
            if (dtos != null) {
                dto.setAnimalsNumberPLII(dtos.getAnimalsNumberPLII());
                dto.setFarmsNumberPLII(dtos.getFarmsNumberPLII());
                dto.setSpeciesNumberPLII(dtos.getSpeciesNumberPLII());
            }
        }

        return dto;
    }


    public DashboardTotalDtoCites citesPLIII(DashboardTotalDtoCites dto) {
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
        String whereClause = "";
        if (!isAdmin) {
            whereClause += "AND tb1.listWardId in :listWardId ";
        }
        String sql = "Select Count(DISTINCT farmId) as farmsNumberPLIII, sum(totalAnimal) as animalsNumberPLIII, count(DISTINCT Species) as speciesNumberPLIII from \n" +
                "(select ROW_NUMBER() OVER (PARTITION BY entity.farm_id,entity.animal_id ORDER BY entity.date_report,entity.modify_date,entity.create_date DESC )\n" +
                "AS rowNumber, \n" +
                "entity.farm_id as farmId, \n" +
                "entity.animal_id as Species,\n" +
                "entity.total as totalAnimal,\n" +
                "entity.cites as cites,\n" +
                "entity.administrative_unit_id as listWardId \n" +
                "From tbl_report_form16 entity where year(date_report) =:year) \n" +
                "as tb1 Where tb1.rowNumber = 1 and tb1.cites = 'III' ";
        Query q = manager.createNativeQuery(sql + whereClause).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalDtoCites.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalDtoCites> result = (List<DashboardTotalDtoCites>) q.getResultList();
        for (DashboardTotalDtoCites dtos : result) {
            if (dtos != null) {
                dto.setAnimalsNumberPLIII(dtos.getAnimalsNumberPLIII());
                dto.setFarmsNumberPLIII(dtos.getFarmsNumberPLIII());
                dto.setSpeciesNumberPLIII(dtos.getSpeciesNumberPLIII());
            }
        }

        return dto;
    }

    public DashboardTotalDtoCites citesOther(DashboardTotalDtoCites dto) {
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
        String whereClause = "";
        if (!isAdmin) {
            whereClause += "AND tb1.listWardId in :listWardId ";
        }
        String sql = "Select Count(DISTINCT farmId) as farmsNumberOther, sum(totalAnimal) as animalsNumberOther, count(DISTINCT Species) as speciesNumberOther from \n" +
                "(select ROW_NUMBER() OVER (PARTITION BY entity.farm_id,entity.animal_id ORDER BY entity.date_report,entity.modify_date,entity.create_date DESC )\n" +
                "AS rowNumber, \n" +
                "entity.farm_id as farmId, \n" +
                "entity.animal_id as Species,\n" +
                "entity.total as totalAnimal,\n" +
                "entity.cites as cites,\n" +
                "entity.administrative_unit_id as listWardId \n" +
                "From tbl_report_form16 entity where year(date_report) =:year) \n" +
                "as tb1 Where tb1.rowNumber = 1 and tb1.cites != 'I' and tb1.cites != 'II' and tb1.cites != 'III'  ";
        Query q = manager.createNativeQuery(sql + whereClause).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTotalDtoCites.class));
        if (dto.getYear() != null) {
            q.setParameter("year", dto.getYear());
        } else {
            Date date = new Date();
            int currentYear = WLDateTimeUtil.getYear(date);
            q.setParameter("year", currentYear);
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
        }
        List<DashboardTotalDtoCites> result = (List<DashboardTotalDtoCites>) q.getResultList();
        for (DashboardTotalDtoCites dtos : result) {
            if (dtos != null) {
                dto.setAnimalsNumberOther(dtos.getAnimalsNumberOther());
                dto.setFarmsNumberOther(dtos.getFarmsNumberOther());
                dto.setSpeciesNumberOther(dtos.getSpeciesNumberOther());
            }
        }
        return dto;
    }


    @Override
    public DashboardResponse getDataDashboard(SearchDto search) {
        DashboardResponse response = new DashboardResponse();
        response.setCites(this.getDataDashboardCites(search, "cites"));
        response.setVn06(this.getDataDashboardCites(search, "vnlist06"));
        Integer countFarmCertificatedGroupI = 0;
        Integer countFarmCertificatedGroupII = 0;
        if (this.getDataDashboardCertificatedGroupI(search) != null && (this.getDataDashboardCertificatedGroupI(search)).size() >0) {
            DashboardTableResponse DashboardCertificatedGroupI = (this.getDataDashboardCertificatedGroupI(search)).get(0);
            countFarmCertificatedGroupI += DashboardCertificatedGroupI.getCountFarm();
        }
        if (this.getDataDashboardCertificatedGroupII(search) != null && (this.getDataDashboardCertificatedGroupII(search)).size() >0) {
            DashboardTableResponse DashboardCertificatedGroupII = (this.getDataDashboardCertificatedGroupII(search)).get(0);
            countFarmCertificatedGroupII += DashboardCertificatedGroupII.getCountFarm();
        }
        response.setCountFarmCertificatedGroupI(countFarmCertificatedGroupI);
        response.setCountFarmCertificatedGroupII(countFarmCertificatedGroupII);
        return response;
    }

    List<DashboardTableResponse> getDataDashboardCertificatedGroupII(SearchDto search){
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
            if (CollectionUtils.isEmpty(listWardId)) {
                return null;
            }
        }
        String whereClause = "";
        int year = LocalDate.now().getYear();
        String sql = "select count( DISTINCT countFarm)  as countFarm from\n" +
                "(SELECT DISTINCT tb.farmId AS countFarm\n" +
                "FROM\n" +
                "  (SELECT ROW_NUMBER() OVER (PARTITION BY f16.farm_id\n" +
                "                             ORDER BY f16.date_report,\n" +
                "                                      f16.modify_date,\n" +
                "                                      f16.create_date DESC) AS rowNumber,\n" +
                "\t\t\t\t\t\t\t\t\tf16.farm_id AS farmId,\n" +
                "             f16.administrative_unit_id   AS listWardId\n" +
                "   FROM tbl_report_form16 AS f16\n" +
                "   INNER JOIN tbl_report_period p ON f16.report_period_id = p.id\n" +
                "   INNER JOIN tbl_animal a ON a.id = f16.animal_id\n" +
                "   INNER JOIN tbl_fms_administrative_unit prov ON prov.id = p.province_id\n" +
                "   AND prov.parent_id IS NULL\n" +
                "   INNER JOIN tbl_fms_administrative_unit dis ON dis.id = p.district_id\n" +
                "   AND dis.parent_id IS NOT NULL\n" +
                "   INNER JOIN tbl_fms_administrative_unit w ON w.id = p.administrative_unit_id\n" +
                "   AND w.parent_id IS NOT NULL\n" +
                "   INNER JOIN tbl_farm fa ON fa.id = f16.farm_id\n" +
                "   AND (fa.isDuplicate IS NULL\n" +
                "        OR fa.isDuplicate = 0)\n" +
                "   WHERE year(f16.date_report) = :year and (f16.cites = 'II' or f16.vnlist06 = 'IIB') and fa.new_registration_code is  null) AS tb\n" +
                "WHERE tb.rowNumber = 1 \n";

                String sqlExcept = "except\n" +
                "SELECT DISTINCT tb.farmId AS countFarm\n" +
                "FROM\n" +
                "  (SELECT ROW_NUMBER() OVER (PARTITION BY f16.farm_id\n" +
                "                             ORDER BY f16.date_report,\n" +
                "                                      f16.modify_date,\n" +
                "                                      f16.create_date DESC) AS rowNumber,\n" +
                "\t\t\t\t\t\t\t\t\tf16.farm_id AS farmId,\n" +
                "             f16.administrative_unit_id   AS listWardId\n" +
                "   FROM tbl_report_form16 AS f16\n" +
                "   INNER JOIN tbl_report_period p ON f16.report_period_id = p.id\n" +
                "   INNER JOIN tbl_animal a ON a.id = f16.animal_id\n" +
                "   INNER JOIN tbl_fms_administrative_unit prov ON prov.id = p.province_id\n" +
                "   AND prov.parent_id IS NULL\n" +
                "   INNER JOIN tbl_fms_administrative_unit dis ON dis.id = p.district_id\n" +
                "   AND dis.parent_id IS NOT NULL\n" +
                "   INNER JOIN tbl_fms_administrative_unit w ON w.id = p.administrative_unit_id\n" +
                "   AND w.parent_id IS NOT NULL\n" +
                "   INNER JOIN tbl_farm fa ON fa.id = f16.farm_id\n" +
                "   AND (fa.isDuplicate IS NULL\n" +
                "        OR fa.isDuplicate = 0)\n" +
                "   WHERE year(f16.date_report) = :year and (f16.cites = 'I' or f16.vnlist06 = 'IB') and fa.new_registration_code is  null) AS tb\n" +
                "WHERE tb.rowNumber = 1  ";
        if (!isAdmin) {
            whereClause += "  AND tb.listWardId in (:listWardId) ";
        }
        String sql2 = " ) as tb1";
        sql += whereClause + sqlExcept + whereClause +sql2;
        Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTableResponse.class));
        if (search.getYear() != null) {
            query.setParameter("year", search.getYear());
        } else {
            query.setParameter("year", year);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        List<DashboardTableResponse> result = (List<DashboardTableResponse>) query.getResultList();
        return result;
    }

    List<DashboardTableResponse> getDataDashboardCertificatedGroupI(SearchDto search){
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
            if (CollectionUtils.isEmpty(listWardId)) {
                return null;
            }
        }
        String whereClause = "";
        if (!isAdmin) {
            whereClause += " ANd tb.listWardId in (:listWardId) ";
        }
        int year = LocalDate.now().getYear();
        String sql = "select count( DISTINCT countFarmId) as countFarm  from\n" +
                "(SELECT DISTINCT tb.farmId AS countFarmId\n" +
                "FROM\n" +
                "  (SELECT ROW_NUMBER() OVER (PARTITION BY f16.farm_id\n" +
                "                             ORDER BY f16.date_report,\n" +
                "                                      f16.modify_date,\n" +
                "                                      f16.create_date DESC) AS rowNumber,\n" +

                "\t\t\t\t\t\t\t\t\tf16.farm_id AS farmId,\n" +
                "             f16.administrative_unit_id   AS listWardId\n" +
                "   FROM tbl_report_form16 AS f16\n" +
                "   INNER JOIN tbl_report_period p ON f16.report_period_id = p.id\n" +
                "   INNER JOIN tbl_animal a ON a.id = f16.animal_id\n" +
                "   INNER JOIN tbl_fms_administrative_unit prov ON prov.id = p.province_id\n" +
                "   AND prov.parent_id IS NULL\n" +
                "   INNER JOIN tbl_fms_administrative_unit dis ON dis.id = p.district_id\n" +
                "   AND dis.parent_id IS NOT NULL\n" +
                "   INNER JOIN tbl_fms_administrative_unit w ON w.id = p.administrative_unit_id\n" +
                "   AND w.parent_id IS NOT NULL\n" +
                "   INNER JOIN tbl_farm fa ON fa.id = f16.farm_id\n" +
                "   AND (fa.isDuplicate IS NULL\n" +
                "        OR fa.isDuplicate = 0)\n" +
                "   WHERE year(f16.date_report) = :year and (f16.cites = 'I' or f16.vnlist06 = 'IB') and fa.new_registration_code is  null) AS tb\n" +
                "WHERE tb.rowNumber = 1   \n" ;

               String sqlExcept  =  "except\n" +
                "SELECT DISTINCT tb.farmId AS countFarmId\n" +
                "FROM\n" +
                "  (SELECT ROW_NUMBER() OVER (PARTITION BY f16.farm_id\n" +
                "                             ORDER BY f16.date_report,\n" +
                "                                      f16.modify_date,\n" +
                "                                      f16.create_date DESC) AS rowNumber,\n" +
                "\t\t\t\t\t\t\t\t\tf16.farm_id AS farmId,\n" +
                "             f16.administrative_unit_id   AS listWardId\n" +
                "   FROM tbl_report_form16 AS f16\n" +
                "   INNER JOIN tbl_report_period p ON f16.report_period_id = p.id\n" +
                "   INNER JOIN tbl_animal a ON a.id = f16.animal_id\n" +
                "   INNER JOIN tbl_fms_administrative_unit prov ON prov.id = p.province_id\n" +
                "   AND prov.parent_id IS NULL\n" +
                "   INNER JOIN tbl_fms_administrative_unit dis ON dis.id = p.district_id\n" +
                "   AND dis.parent_id IS NOT NULL\n" +
                "   INNER JOIN tbl_fms_administrative_unit w ON w.id = p.administrative_unit_id\n" +
                "   AND w.parent_id IS NOT NULL\n" +
                "   INNER JOIN tbl_farm fa ON fa.id = f16.farm_id\n" +
                "   AND (fa.isDuplicate IS NULL\n" +
                "        OR fa.isDuplicate = 0)\n" +
                "   WHERE year(f16.date_report) = :year and (f16.cites = 'II' or f16.vnlist06 = 'IIB') and fa.new_registration_code is  null) AS tb\n" +
                "WHERE tb.rowNumber = 1  ";
        String sql2 = " ) as tb1";
        sql += whereClause + sqlExcept + whereClause +sql2;
        Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class)
                .setResultTransformer(new AliasToBeanResultTransformer(DashboardTableResponse.class));
        if (search.getYear() != null) {
            query.setParameter("year", search.getYear());
        } else {
            query.setParameter("year", year);
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        List<DashboardTableResponse> result = (List<DashboardTableResponse>)query.getResultList();
        return result;
    }

    List<DashboardTableResponse> getDataDashboardCites(SearchDto search, String type) {
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
            if (CollectionUtils.isEmpty(listWardId)) {
                return null;
            }
        }
        String whereClause = "";
        int year = LocalDate.now().getYear();
        String selectTotal = "select count(distinct farmId) as totalCountFarm,\n" +
                "       count(distinct animalId) as totalCountAnimal,\n" +
                "       sum(total) as totalSumAnimal,\n" +
                "       count(distinct dateRegistration) as totalCountFarmCertificatedInYear,\n" +
                "       count(distinct oldRegistrationCode) as totalCountFarmCertificated\n" +
                "       from (";
        String select = "select type,count(distinct farmId) as countFarm,\n" +
                "       count(distinct animalId) as countAnimal,\n" +
                "       sum(total) as sumAnimal,\n" +
                "       count(distinct dateRegistration) as countFarmCertificatedInYear,\n" +
                "       count(distinct oldRegistrationCode) as countFarmCertificated\n" +
                "       from ( ";

        String sql1 =
                "SELECT case\n" +
                "           when tb.vnlist06 = 'IB' then 'NDCP-IB'\n" +
                "           when tb.vnlist06 = 'IIB' then 'NDCP-IIB'\n" +
                "           when tb.vnlist06 = 'DVRTT' then 'NDCP-DVRTT'\n" +
                "           else 'Khác' end as type,tb.farmId,tb.animalId,tb.total,tb.listWardId,\n" +
                "       case when isnull(tb.oldRegistrationCode,null) is null then null\n" +
                "           else tb.farmId end as oldRegistrationCode,\n" +
                "        case when tb.dateRegistration is not null and year(tb.dateRegistration) = :year then tb.farmId end as dateRegistration ";
        String sql2 =
                "SELECT case\n" +
                "           when tb.cites = 'I' then 'PL-I'\n" +
                "           when tb.cites = 'II' then 'PL-II'\n" +
                "           when tb.cites = 'III' then 'PL-III'\n" +
                "           else 'Khác' end as type,tb.farmId,tb.animalId,tb.total,tb.listWardId,\n" +
                "       case when isnull(tb.oldRegistrationCode,null) is null then null\n" +
                "           else tb.farmId end as oldRegistrationCode,\n" +
                "        case when tb.dateRegistration is not null and year(tb.dateRegistration) = :year then tb.farmId end as dateRegistration ";

        String sql = "FROM (SELECT (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc))\n" +
                "                                        AS rowNumber,\n" +
                "             p.year,\n" +
                "             w.id                       AS wardId,\n" +
                "             a.vnlist06,\n" +
                "             a.cites,\n" +
                "             a.id                       AS animalId,\n" +
                "             fa.id                      AS farmId,\n" +
                "             fa.date_registration AS dateRegistration,\n" +
                "             fa.new_registration_code   AS oldRegistrationCode,\n" +
                "             f.total                    AS total,\n" +
                "             f.administrative_unit_id   AS listWardId\n" +
                "      FROM tbl_report_form16 f\n" +
                "               INNER JOIN tbl_report_period p ON f.report_period_id = p.id\n" +
                "               INNER JOIN tbl_animal a ON a.id = f.animal_id\n" +
                "               INNER JOIN tbl_fms_administrative_unit prov ON prov.id = p.province_id\n" +
                "          AND prov.parent_id IS NULL\n" +
                "               INNER JOIN tbl_fms_administrative_unit dis ON dis.id = p.district_id\n" +
                "          AND dis.parent_id IS NOT NULL\n" +
                "               INNER JOIN tbl_fms_administrative_unit w ON w.id = p.administrative_unit_id\n" +
                "          AND w.parent_id IS NOT NULL\n" +
                "               INNER JOIN tbl_farm fa ON fa.id = f.farm_id and (fa.isDuplicate is null or fa.isDuplicate = 0)) as tb\n" +
                "where tb.rowNumber = 1\n" +
                "  and tb.total <> 0\n" +
                "  and tb.year = :year) as tb1 ";
        if (!isAdmin) {
            whereClause += " where tb1.listWardId in :listWardId ";
        }
        String sqlGroup = " group by type";
        String sqlTotalCites = selectTotal + sql2 + sql + whereClause;
        String sqlTotalVnlist06 = selectTotal + sql1 + sql + whereClause;
        String sqlCites = select + sql2 + sql + whereClause + sqlGroup;
        String sqlVnlsit06 = select + sql1 + sql + whereClause+ sqlGroup;

        if (type.equals("cites")) {
            Query queryCites = manager.createNativeQuery(sqlCites).unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new AliasToBeanResultTransformer(DashboardTableResponse.class));
            if (search.getYear() != null) {
                queryCites.setParameter("year", search.getYear());
            } else {
                queryCites.setParameter("year", year);
            }
            if (!isAdmin) {
                queryCites.setParameter("listWardId", listWardId);
            }
            List<DashboardTableResponse> result = (List<DashboardTableResponse>) queryCites.getResultList();
            Query queryTotalCites = manager.createNativeQuery(sqlTotalCites).unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new AliasToBeanResultTransformer(DashboardTableResponse.class));
            if (search.getYear() != null) {
                queryTotalCites.setParameter("year", search.getYear());
            } else {
                queryTotalCites.setParameter("year", year);
            }
            if (!isAdmin) {
                queryTotalCites.setParameter("listWardId", listWardId);
            }
            List<DashboardTableResponse> listTotalCites = (List<DashboardTableResponse>) queryTotalCites.getResultList();
            if (listTotalCites != null && listTotalCites.size() >0) {
                result.add(listTotalCites.get(0));
            }
            return result;

        }
        if (type.equals("vnlist06")) {
            Query queryVnList06 = manager.createNativeQuery(sqlVnlsit06).unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new AliasToBeanResultTransformer(DashboardTableResponse.class));
            if (search.getYear() != null) {
                queryVnList06.setParameter("year", search.getYear());
            } else {
                queryVnList06.setParameter("year", year);
            }
            if (!isAdmin) {
                queryVnList06.setParameter("listWardId", listWardId);
            }
            List<DashboardTableResponse> result = (List<DashboardTableResponse>) queryVnList06.getResultList();
            Query queryTotalVnList06 = manager.createNativeQuery(sqlTotalVnlist06).unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new AliasToBeanResultTransformer(DashboardTableResponse.class));
            if (search.getYear() != null) {
                queryTotalVnList06.setParameter("year", search.getYear());
            } else {
                queryTotalVnList06.setParameter("year", year);
            }
            if (!isAdmin) {
                queryTotalVnList06.setParameter("listWardId", listWardId);
            }
            List<DashboardTableResponse> listTotalVnlist06 = (List<DashboardTableResponse>) queryTotalVnList06.getResultList();
            if (listTotalVnlist06 != null && listTotalVnlist06.size() > 0) {
                result.add(listTotalVnlist06.get(0));
            }
        return result;
    }
        return null;
    }

}
