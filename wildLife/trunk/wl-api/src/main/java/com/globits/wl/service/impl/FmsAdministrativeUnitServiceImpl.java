package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.List;

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

import com.globits.core.domain.AdministrativeUnit;
import com.globits.core.dto.AdministrativeUnitDto;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.FmsRegion;
import com.globits.wl.domain.UserAdministrativeUnit;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.FarmAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.FmsRegionRepository;
import com.globits.wl.service.FmsAdministrativeUnitService;
import com.globits.wl.service.UserAdministrativeUnitService;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Service
public class FmsAdministrativeUnitServiceImpl extends GenericServiceImpl<FmsAdministrativeUnit, Long>
		implements FmsAdministrativeUnitService {

	@Autowired
	private FmsAdministrativeUnitRepository administrativeUnitRepository;

	@Autowired
	private FmsRegionRepository fmsRegionRepository;
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;

	@Override
	public Page<FmsAdministrativeUnitDto> getListAdministrative(Long Id, int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.administrativeUnitRepository.getListAdministrative(Id, pageable);
	}

	@Override
	public FmsAdministrativeUnitDto getAdministrativeUnitById(Long id) {
		FmsAdministrativeUnit au = administrativeUnitRepository.findById(id);
		FmsAdministrativeUnitDto dto = new FmsAdministrativeUnitDto();
		if (au != null) {
			dto = new FmsAdministrativeUnitDto(au, true);
		}
		return dto;
	}

	@Override
	public FmsAdministrativeUnitDto saveAdministrative(FmsAdministrativeUnitDto fmsAdministrativeUnitDto, Long id) {
		FmsAdministrativeUnit fmsAdministrativeUnit = null;
		Boolean isEdit = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			isEdit = true;
			fmsAdministrativeUnit = this.administrativeUnitRepository.findOne(id);
		} else if (fmsAdministrativeUnitDto.getId() != null) {
			fmsAdministrativeUnit = this.administrativeUnitRepository.findOne(fmsAdministrativeUnitDto.getId());
		} else if (fmsAdministrativeUnitDto.getCode() != null) {
			List<FmsAdministrativeUnit> aus = this.administrativeUnitRepository
					.findListByCode(fmsAdministrativeUnitDto.getCode());
			if (aus != null && aus.size() == 1) {
				fmsAdministrativeUnit = aus.get(0);
			} else if (aus != null && aus.size() > 1) {
				for (FmsAdministrativeUnit item : aus) {
					if (item.getName().equals(fmsAdministrativeUnitDto.getName())) {
						fmsAdministrativeUnit = item;
						break;
					}
				}
			}
		}

		if (fmsAdministrativeUnit == null) {// trường hợp thêm mới
			fmsAdministrativeUnit = new FmsAdministrativeUnit();
			fmsAdministrativeUnit.setCreateDate(currentDate);
			fmsAdministrativeUnit.setCreatedBy(currentUserName);
		}

		if (fmsAdministrativeUnitDto.getName() != null)
			fmsAdministrativeUnit.setName(fmsAdministrativeUnitDto.getName());

		if (fmsAdministrativeUnitDto.getCode() != null)
			fmsAdministrativeUnit.setCode(fmsAdministrativeUnitDto.getCode());

		if (fmsAdministrativeUnitDto.getMapCode() != null)
			fmsAdministrativeUnit.setMapCode(fmsAdministrativeUnitDto.getMapCode());

		if (fmsAdministrativeUnitDto.getLatitude() != null)
			fmsAdministrativeUnit.setLatitude(fmsAdministrativeUnitDto.getLatitude());

		if (fmsAdministrativeUnitDto.getLongitude() != null)
			fmsAdministrativeUnit.setLongitude(fmsAdministrativeUnitDto.getLongitude());

		if (fmsAdministrativeUnitDto.getgMapX() != null)
			fmsAdministrativeUnit.setgMapX(fmsAdministrativeUnitDto.getgMapX());

		if (fmsAdministrativeUnitDto.getgMapY() != null)
			fmsAdministrativeUnit.setgMapY(fmsAdministrativeUnitDto.getgMapY());

		if (fmsAdministrativeUnitDto.getTotalAcreage() != null)
			fmsAdministrativeUnit.setTotalAcreage(fmsAdministrativeUnitDto.getTotalAcreage());

		if (fmsAdministrativeUnitDto.getRegionDto() != null) {
			FmsRegion fmsRegion = null;
			if (fmsAdministrativeUnitDto.getRegionDto().getId() != null) {
				fmsRegion = this.fmsRegionRepository.findOne(fmsAdministrativeUnitDto.getRegionDto().getId());
			}
			if (fmsRegion == null) {
				// fmsRegion = new FmsRegion();
				// fmsRegion.setCreateDate(currentDate);
				// fmsRegion.setCreatedBy(currentUserName);
			}
			/*
			 * fmsRegion.setName(fmsAdministrativeUnitDto.getRegionDto().getName());
			 * fmsRegion.setCode(fmsAdministrativeUnitDto.getRegionDto().getCode());
			 * fmsRegion.setDescription(fmsAdministrativeUnitDto.getRegionDto().
			 * getDescription());
			 * fmsRegion.setAcreage(fmsAdministrativeUnitDto.getRegionDto().getAcreage());
			 */
			fmsAdministrativeUnit.setRegion(fmsRegion);
		} else {
			fmsAdministrativeUnit.setRegion(null);
		}

		if (fmsAdministrativeUnitDto.getParentDto() != null) {
			FmsAdministrativeUnit parent = null;
			if (fmsAdministrativeUnitDto.getParentDto().getId() != null) {
				parent = this.administrativeUnitRepository.findOne(fmsAdministrativeUnitDto.getParentDto().getId());
			} else if (fmsAdministrativeUnitDto.getParentDto().getCode() != null) {
				List<FmsAdministrativeUnit> aus = this.administrativeUnitRepository
						.findListByCode(fmsAdministrativeUnitDto.getParentDto().getCode());
				if (aus != null && aus.size() == 1) {
					parent = aus.get(0);
				} else if (aus != null && aus.size() > 1) {
					for (FmsAdministrativeUnit item : aus) {
						if (item.getName().equals(fmsAdministrativeUnitDto.getParentDto().getName())) {
							parent = item;
							break;
						}
					}
				}
			}
			if (parent != null) {
				fmsAdministrativeUnit.setParent(parent);
				if (parent.getLevel() != null && parent.getLevel() > 0) {
					fmsAdministrativeUnit.setLevel(parent.getLevel() + 1);
				}
			}
		} else {
			fmsAdministrativeUnit.setLevel(1); // level = 1 là cấp thành phố
			fmsAdministrativeUnit.setParent(null);
		}

		this.administrativeUnitRepository.save(fmsAdministrativeUnit);
		fmsAdministrativeUnitDto.setId(fmsAdministrativeUnit.getId());
		return fmsAdministrativeUnitDto;

	}

	@Override
	public FmsAdministrativeUnitDto removeAdministrative(Long id) {
		FmsAdministrativeUnitDto fmsAdministrativeUnitDto = null;
		if (administrativeUnitRepository != null && this.administrativeUnitRepository.exists(id)) {
			fmsAdministrativeUnitDto = new FmsAdministrativeUnitDto(this.administrativeUnitRepository.findOne(id));
			this.administrativeUnitRepository.delete(id);

		}
		return fmsAdministrativeUnitDto;
	}

	@Override
	public FmsAdministrativeUnitDto deleteAdministrativeUnit(Long id) {
		List<FmsAdministrativeUnit> list = new ArrayList<FmsAdministrativeUnit>();
		FmsAdministrativeUnitDto ret = new FmsAdministrativeUnitDto();
		FmsAdministrativeUnit au = administrativeUnitRepository.findOne(id);
		if (au != null && au.getId() != null) {
			ret.setId(au.getId());
			ret.setCode(au.getCode());
			ret.setName(au.getName());
			list = administrativeUnitRepository.getListdministrativeUnitbyParent(au.getId());
			if (list != null && list.size() > 0) {
				// không xóa được thư mục cha=> phải xóa thư mục con trước
			} else {
				administrativeUnitRepository.delete(au.getId());
				ret.setCode("-1");
			}
		}
		return ret;
	}

	@Override
	public List<FmsAdministrativeUnitDto> getAll() {
		// TODO Auto-generated method stub
		List<FmsAdministrativeUnitDto> dtos = this.administrativeUnitRepository.getAll();
		return dtos;
	}

	@Override
	public List<FmsAdministrativeUnitDto> getAllCity() {
		return this.administrativeUnitRepository.getAllCity();
	}

	@Override
	public List<FmsAdministrativeUnitDto> getListWard(Long administrativeId) {
		if (administrativeId != null) {
			String sql = " select new com.globits.wl.dto.FmsAdministrativeUnitDto(fa,true) from FmsAdministrativeUnit fa  where (1=1) and fa.parent.parent is NOT NULL ";
			String whereClause = "";
			if (administrativeId != null) {
				whereClause += " and (fa.parent.id  = :Id or fa.parent.parent.id = :Id) ";
			}
			sql += whereClause;
			sql += " order by fa.parent.name asc ";
			Query q = manager.createQuery(sql, FmsAdministrativeUnitDto.class);
			if (administrativeId != null) {
				q.setParameter("Id", administrativeId);
			}
			List<FmsAdministrativeUnitDto> listResult = q.getResultList();
			if (listResult != null) {
				return listResult;
			}
		}
		return null;
	}

//	@Override
//	public List<FmsAdministrativeUnitDto> getAllByParentId(Long parentId) {
//		if (parentId != null) {
////			List<Long> lstAdminUnit = userAdministrativeUnitService.getAdministrativeUnitIdByLoginUser();
////			if(lstAdminUnit==null || lstAdminUnit.size()<1) {
////				lstAdminUnit=new ArrayList<Long>();
////				lstAdminUnit.add(-1L);
////			}
//			return this.administrativeUnitRepository.getAllByParentId(parentId);
//		}
//		return null;
//	}
	@Override
	public List<FmsAdministrativeUnitDto> getAllByParentId(Long parentId) {
		if (parentId != null) {
			List<Long> listWard = new ArrayList<Long>();
			List<Long> listDistrict = new ArrayList<Long>();
			List<Long> listProvince = new ArrayList<Long>();
			String sql = "select new com.globits.wl.dto.FmsAdministrativeUnitDto(a,true) from FmsAdministrativeUnit a "
					+ " where a.parent.id = :parentId ";
			List<FmsAdministrativeUnit> lstAdminUnit = userAdministrativeUnitService.getAdministrativeUnitByLoginUser();
			if (lstAdminUnit != null && lstAdminUnit.size() > 0) {
				for (FmsAdministrativeUnit fmsAdministrativeUnit : lstAdminUnit) {
					if (fmsAdministrativeUnit.getParent() == null) {
						listProvince.add(fmsAdministrativeUnit.getId());
					} else if (fmsAdministrativeUnit.getParent().getParent() == null) {
						listDistrict.add(fmsAdministrativeUnit.getId());
						listProvince.add(fmsAdministrativeUnit.getParent().getId());
					} else {
						listWard.add(fmsAdministrativeUnit.getId());
						listDistrict.add(fmsAdministrativeUnit.getParent().getId());
						listProvince.add(fmsAdministrativeUnit.getParent().getParent().getId());
					}
				}
				sql += " AND ( 1=2 ";
				if (listWard != null && listWard.size() > 0) {
					sql += " OR (a.id in (:listWard)) OR (a.id in (:listDistrict))  ";
				} else if (listDistrict != null && listDistrict.size() > 0) {
					sql += " OR (a.parent.id in (:listDistrict)) OR (a.id in (:listDistrict)) ";
				} else if (listProvince != null && listProvince.size() > 0) {
					sql += " OR (a.parent.id in (:listProvince)) OR (a.parent.parent.id in (:listProvince)) ";
				}
				sql += "		) ";
				// OR (a.parent.id in (:listDistrict)) OR (a.parent.id in (:listProvince)) OR
				// (a.parent.parent.id in (:listProvince))
			}
			Query q = manager.createQuery(sql, FmsAdministrativeUnitDto.class);
			q.setParameter("parentId", parentId);
			if (listWard != null && listWard.size() > 0) {
				q.setParameter("listWard", listWard);
				q.setParameter("listDistrict", listDistrict);
			} else if (listDistrict != null && listDistrict.size() > 0) {
				q.setParameter("listDistrict", listDistrict);
			} else if (listProvince != null && listProvince.size() > 0) {
				q.setParameter("listProvince", listProvince);
			}
			return q.getResultList();
		}
		return null;
	}

	@Override
	public Page<FmsAdministrativeUnitDto> searchDto(SearchDto searchDto, int pageIndex, int pageSize) {
		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		String namecode = searchDto.getNameOrCode();

		String sql = " select fa from FmsAdministrativeUnit fa  left join fetch fa.subAdministrativeUnits where fa.parent is null";
		String sqlCount = "select count(fa.id) from FmsAdministrativeUnit fa where fa.parent is null";
		String whereClause = "";
		if (namecode != null && namecode.length() > 0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}

		sql += whereClause;
		sql += " order by fa.code asc ";
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, FmsAdministrativeUnit.class);
		Query qCount = manager.createQuery(sqlCount);

		if (namecode != null && namecode.length() > 0) {
			q.setParameter("namecode", '%' + namecode + '%');
			qCount.setParameter("namecode", '%' + namecode + '%');
		}

		q.setFirstResult((pageIndex) * pageSize);
		q.setMaxResults(pageSize);

		Long numberResult = (Long) qCount.getSingleResult();

		List<FmsAdministrativeUnit> lst = q.getResultList();
		List<FmsAdministrativeUnitDto> ret = new ArrayList<FmsAdministrativeUnitDto>();
		if (lst != null && !lst.isEmpty()) {
			for (FmsAdministrativeUnit cs : lst) {
				ret.add(new FmsAdministrativeUnitDto(cs));
			}
		}

		Page<FmsAdministrativeUnitDto> page = new PageImpl<>(ret, pageable, numberResult);
		return page;
	}

	@Override
	public List<FmsAdministrativeUnitDto> saveOrUpdateList(List<FmsAdministrativeUnitDto> listFmsAdministrativeUnit) {
		ArrayList<FmsAdministrativeUnitDto> ret = new ArrayList<FmsAdministrativeUnitDto>();
		for (int i = 0; i < listFmsAdministrativeUnit.size(); i++) {
			FmsAdministrativeUnitDto dto = listFmsAdministrativeUnit.get(i);
			saveAdministrativeImport(dto, dto.getId());
		}
		return ret;
	}

	@Override
	public FmsAdministrativeUnitDto checkDuplicateCode(String code) {
		FmsAdministrativeUnitDto viewCheckDuplicateCodeDto = new FmsAdministrativeUnitDto();
		FmsAdministrativeUnit au = null;
		List<FmsAdministrativeUnit> list = administrativeUnitRepository.findListByCode(code);
		if (list != null && list.size() > 0) {
			au = list.get(0);
		}
		if (list == null) {
			viewCheckDuplicateCodeDto.setDuplicate(false);
		} else if (list != null && list.size() > 0) {
			viewCheckDuplicateCodeDto.setDuplicate(true);
			viewCheckDuplicateCodeDto.setDupCode(au.getCode());
			viewCheckDuplicateCodeDto.setDupName(au.getName());
		}
		return viewCheckDuplicateCodeDto;
	}

	@Override
	public Page<FmsAdministrativeUnitDto> getListTree(Integer pageIndex, Integer pageSize) {
		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		List<FmsAdministrativeUnit> dads = administrativeUnitRepository.findTreeAdministrativeUnitNotSub(pageable);
		Long countElement = administrativeUnitRepository.countDadAdministrativeUnit();
		List<FmsAdministrativeUnitDto> dtos = new ArrayList<FmsAdministrativeUnitDto>();
		for (FmsAdministrativeUnit cs : dads) {
			dtos.add(new FmsAdministrativeUnitDto(cs));
		}

		Page<FmsAdministrativeUnitDto> finalPage = new PageImpl<FmsAdministrativeUnitDto>(dtos, pageable, countElement);

		return finalPage;
	}

	@Override
	public List<FmsAdministrativeUnitDto> getChildrenByParentId(Long parentId) {
		List<FmsAdministrativeUnitDto> list = new ArrayList<FmsAdministrativeUnitDto>();
		FmsAdministrativeUnitDto dto = administrativeUnitRepository.getById(parentId);
		if (dto != null && dto.getChildren() != null && dto.getChildren().size() > 0) {
			list = dto.getChildren();
		}

		return list;
	}

	@Override
	public List<FmsAdministrativeUnitDto> getAllCityByRegion(Long regionId) {
		return this.administrativeUnitRepository.getAllCityByRegion(regionId);
	}

	public FmsAdministrativeUnitDto saveAdministrativeImport(FmsAdministrativeUnitDto fmsAdministrativeUnitDto,
			Long id) {
		FmsAdministrativeUnit fmsAdministrativeUnit = null;
		Boolean isEdit = false;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			isEdit = true;
			fmsAdministrativeUnit = this.administrativeUnitRepository.findOne(id);
		} else if (fmsAdministrativeUnitDto.getId() != null) {
			fmsAdministrativeUnit = this.administrativeUnitRepository.findOne(fmsAdministrativeUnitDto.getId());
		} else if (fmsAdministrativeUnitDto.getCode() != null) {
			List<FmsAdministrativeUnit> aus = this.administrativeUnitRepository
					.findListByCode(fmsAdministrativeUnitDto.getCode());
			if (aus != null && aus.size() == 1) {
				fmsAdministrativeUnit = aus.get(0);
			} else if (aus != null && aus.size() > 1) {
				for (FmsAdministrativeUnit item : aus) {
					if (item.getName().equals(fmsAdministrativeUnitDto.getName())) {
						fmsAdministrativeUnit = item;
						break;
					}
				}
			}
		}

		if (fmsAdministrativeUnit == null) {// trường hợp thêm mới
			fmsAdministrativeUnit = new FmsAdministrativeUnit();
			fmsAdministrativeUnit.setCreateDate(currentDate);
			fmsAdministrativeUnit.setCreatedBy(currentUserName);
		}

		if (fmsAdministrativeUnitDto.getName() != null)
			fmsAdministrativeUnit.setName(fmsAdministrativeUnitDto.getName());

		if (fmsAdministrativeUnitDto.getCode() != null)
			fmsAdministrativeUnit.setCode(fmsAdministrativeUnitDto.getCode());

		if (fmsAdministrativeUnitDto.getMapCode() != null)
			fmsAdministrativeUnit.setMapCode(fmsAdministrativeUnitDto.getMapCode());

		if (fmsAdministrativeUnitDto.getLatitude() != null)
			fmsAdministrativeUnit.setLatitude(fmsAdministrativeUnitDto.getLatitude());

		if (fmsAdministrativeUnitDto.getLongitude() != null)
			fmsAdministrativeUnit.setLongitude(fmsAdministrativeUnitDto.getLongitude());

		if (fmsAdministrativeUnitDto.getgMapX() != null)
			fmsAdministrativeUnit.setgMapX(fmsAdministrativeUnitDto.getgMapX());

		if (fmsAdministrativeUnitDto.getgMapY() != null)
			fmsAdministrativeUnit.setgMapY(fmsAdministrativeUnitDto.getgMapY());

		if (fmsAdministrativeUnitDto.getTotalAcreage() != null)
			fmsAdministrativeUnit.setTotalAcreage(fmsAdministrativeUnitDto.getTotalAcreage());

		if (fmsAdministrativeUnitDto.getRegionDto() != null) {
			FmsRegion fmsRegion = null;
			if (fmsAdministrativeUnitDto.getRegionDto().getId() != null) {
				fmsRegion = this.fmsRegionRepository.findOne(fmsAdministrativeUnitDto.getRegionDto().getId());
			}

			fmsAdministrativeUnit.setRegion(fmsRegion);
		}

		if (fmsAdministrativeUnitDto.getParentDto() != null) {
			FmsAdministrativeUnit parent = null;
			if (fmsAdministrativeUnitDto.getParentDto().getId() != null) {
				parent = this.administrativeUnitRepository.findOne(fmsAdministrativeUnitDto.getParentDto().getId());
			} else if (fmsAdministrativeUnitDto.getParentDto().getCode() != null) {
				List<FmsAdministrativeUnit> aus = this.administrativeUnitRepository
						.findListByCode(fmsAdministrativeUnitDto.getParentDto().getCode());
				if (aus != null && aus.size() == 1) {
					parent = aus.get(0);
				} else if (aus != null && aus.size() > 1) {
					for (FmsAdministrativeUnit item : aus) {
						if (item.getName().equals(fmsAdministrativeUnitDto.getParentDto().getName())) {
							parent = item;
							break;
						}
					}
				}
			}
			if (parent != null) {
				fmsAdministrativeUnit.setParent(parent);
				if (parent.getLevel() != null && parent.getLevel() > 0) {
					fmsAdministrativeUnit.setLevel(parent.getLevel() + 1);
				}
			}
		} else {
			fmsAdministrativeUnit.setLevel(1); // level = 1 là cấp thành phố
			fmsAdministrativeUnit.setParent(null);
		}

		this.administrativeUnitRepository.save(fmsAdministrativeUnit);
		fmsAdministrativeUnitDto.setId(fmsAdministrativeUnit.getId());
		return fmsAdministrativeUnitDto;

	}

	@Override
	public Page<FmsAdministrativeUnitDto> getListAdministrativeByCodeOrName(Long Id, String code, int pageIndex,
			int pageSize) {
		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		String sql = " select new com.globits.wl.dto.FmsAdministrativeUnitDto(fa) from FmsAdministrativeUnit fa  where (1=1) and fa.id != :id ";
		String sqlCount = "select count(fa.id) from FmsAdministrativeUnit fa where (1=1) and fa.id != :id";
		String whereClause = "";

		if (code != null && code.length() > 0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode)";
		}

		sql += whereClause;
		sql += " order by fa.code asc ";
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, FmsAdministrativeUnitDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (Id != null && Id > 0) {
			q.setParameter("id", Id);
			qCount.setParameter("id", Id);
		}

		if (code != null && code.length() > 0) {
			q.setParameter("namecode", '%' + code + '%');
			qCount.setParameter("namecode", '%' + code + '%');
		}

		q.setFirstResult((pageIndex) * pageSize);
		q.setMaxResults(pageSize);

		Long numberResult = (Long) qCount.getSingleResult();
		Page<FmsAdministrativeUnitDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
		return page;
	}

	@Override
	public List<FmsAdministrativeUnitDto> getAllByLevel(Integer level) {
		String sql = " select new com.globits.wl.dto.FmsAdministrativeUnitDto(fa,true,1) from FmsAdministrativeUnit fa  where (1=1) ";

		String whereClause = "";
		if (level != null && level > 0) {
			whereClause += " and (fa.level = :level)";
		}

		sql += whereClause;
		sql += " order by fa.code asc ";

		Query q = manager.createQuery(sql, FmsAdministrativeUnitDto.class);

		if (level != null && level > 0) {
			q.setParameter("level", level);

		}

		List<FmsAdministrativeUnitDto> page = q.getResultList();
		return page;
	}

	/*
	 * Tính tổng diện tích các tỉnh thuộc 1 vùng
	 * 
	 * @see
	 * com.globits.wl.service.FmsAdministrativeUnitService#sumTotalAcreageByRegion(
	 * java.lang.Long)
	 */
	@Override
	public double sumTotalAcreageByRegion(Long regionId) {
		double count = 0;
		String sqlCount = "select SUM(iea.totalAcreage) from  FmsAdministrativeUnit iea  where (1=1) and iea.parent is null ";
		String whereClause = "";

		if (regionId != null) {
			whereClause += " and iea.region.id  = :regionId";
		}
		sqlCount += whereClause;

		Query qCount = manager.createQuery(sqlCount);

		if (regionId != null) {
			qCount.setParameter("regionId", regionId);
		}
		Double results = (Double) qCount.getSingleResult();
		if (results != null) {
			count = results;
		}
		return count;
	}

	@Override
	public List<FarmAdministrativeUnitDto> getListProvinceByRegion(Long regionId) {
		String sql = " select new com.globits.wl.dto.FmsAdministrativeUnitDto(fa,true,1) from FmsAdministrativeUnit fa  where (1=1) and fa.parent is null ";

		String whereClause = "";
		if (regionId != null) {
			whereClause += " and fa.region.id  = :regionId";
		}

		sql += whereClause;
		sql += " order by fa.code asc ";

		Query q = manager.createQuery(sql, FmsAdministrativeUnitDto.class);

		if (regionId != null) {
			q.setParameter("regionId", regionId);
		}

		List<FmsAdministrativeUnitDto> page = q.getResultList();
		List<FarmAdministrativeUnitDto> list = new ArrayList<FarmAdministrativeUnitDto>();
		if (page != null && page.size() > 0) {
			for (FmsAdministrativeUnitDto item : page) {
				FarmAdministrativeUnitDto dto = new FarmAdministrativeUnitDto();
				dto.setCodeAu(item.getCode());
				dto.setNameAu(item.getName());
				list.add(dto);
			}
		}
		return list;
	}
	/*
	 * Hàm lấy tất cả id của đơn vị hành chính (cha,con,cháu)
	 * 
	 * @see com.globits.wl.service.FmsAdministrativeUnitService#
	 * getAllAdministrativeUnitIdByParentId(java.lang.Long)
	 */

	@Override
	public List<Long> getAllAdministrativeUnitIdByParentId(Long parentId) {
		List<Long> ret = new ArrayList<Long>();
		ret.add(parentId);
		List<Long> list = administrativeUnitRepository.getAllIdByParentId(parentId);
		if (list != null && list.size() > 0) {
			ret.addAll(list);
			for (Long long1 : list) {
				List<Long> lst = new ArrayList<Long>();
				lst = administrativeUnitRepository.getAllIdByParentId(long1);
				if (lst != null && lst.size() > 0) {
					ret.addAll(lst);
				}
			}
		}
		// FmsAdministrativeUnit au=administrativeUnitRepository.findById(parentId);
		return ret;
	}

	@Override
	public FmsAdministrativeUnit findByCode(String code) {
		List<FmsAdministrativeUnit> list = administrativeUnitRepository.findListByCode(code);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<FmsAdministrativeUnitDto> updateVN2000(List<FmsAdministrativeUnitDto> list) {
		if(!CollectionUtils.isEmpty(list)){
			List<FmsAdministrativeUnitDto> rs = new ArrayList<>();
			for(FmsAdministrativeUnitDto dto : list){
				if(StringUtils.hasText(dto.getCode()) && StringUtils.hasText(dto.getVn2000())){
					List<FmsAdministrativeUnit> listByCode = administrativeUnitRepository.findListByCode(dto.getCode().split("\\.", 2)[0]);
					if(!CollectionUtils.isEmpty(listByCode)){
						FmsAdministrativeUnit entity = listByCode.get(0);
						entity.setVn2000(dto.getVn2000());
						entity = administrativeUnitRepository.save(entity);
						if(!ObjectUtils.isEmpty(entity)){
							rs.add(new FmsAdministrativeUnitDto(entity,true,1));
						}
					}
				}
			}
			return rs;
		}
		return null;
	}

//	public List<Long> getAllAdministrativeUnitIdByParentId(Long parentId){
//		List <Long> ret=new ArrayList<Long>();
//		ret.add(parentId);
//		List<Long> list=administrativeUnitRepository.getAllIdByParentId(parentId);
//		if(list!=null && list.size()>0){
//			for (Long child : list) {
//				
//			}
//		}
//		ret.addAll(list);
//		
//		return ret;
//	}
}
