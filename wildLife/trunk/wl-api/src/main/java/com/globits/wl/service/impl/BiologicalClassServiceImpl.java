package com.globits.wl.service.impl;

import java.util.List;
import java.util.Set;

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
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.BiologicalClass;
import com.globits.wl.domain.LiveStockProduct;
import com.globits.wl.domain.Unit;
import com.globits.wl.dto.BiologicalClassDto;
import com.globits.wl.dto.UnitDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.repository.BiologicalClassRepository;
import com.globits.wl.repository.LiveStockProductRepository;
import com.globits.wl.service.BiologicalClassService;
import com.globits.wl.service.UnitService;
import com.globits.wl.utils.WLConstant;

@Service
public class BiologicalClassServiceImpl extends GenericServiceImpl<BiologicalClass, Long> implements BiologicalClassService {

	@Autowired
	private BiologicalClassRepository repository;
	@Override
	public Page<BiologicalClassDto> searchByPage(SearchDto dto) {

		if (dto == null) {
			return null;
		}

		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		String whereClause = " where (1=1) ";
		String orderBy = " ORDER BY entity.createDate DESC ";

		String sqlCount = "select count(entity.id) from BiologicalClass as entity ";
		String sql = "select new com.globits.wl.dto.BiologicalClassDto(entity, false) from BiologicalClass entity  ";

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			whereClause += " AND ( entity.name LIKE :text OR entity.code LIKE :text OR entity.sci LIKE :text) ";
		}
		if (dto.getType() != null) {
			whereClause += " AND ( entity.type =:type) ";
		}
		if(dto.getCurrentType() != null) {
			whereClause += " AND ( entity.type =: parentType) ";
		}
		if(dto.getAnimalClass() != null) {
			whereClause += " AND ( entity.parent.name =: animalClass) ";
		}
		if(dto.getOrdo() != null) {
			whereClause += " AND ( entity.parent.name =: ordo) ";
		}
		if(dto.getAnimalClassSci() != null) {
			whereClause += " AND ( entity.parent.sci =: animalClassSci) ";
		}
		if(dto.getOrdoSci() != null) {
			whereClause += " AND ( entity.parent.sci =: ordoSci) ";
		}
		sql += whereClause + orderBy;
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, BiologicalClassDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (dto.getText() != null && StringUtils.hasText(dto.getText())) {
			q.setParameter("text", '%' + dto.getText() + '%');
			qCount.setParameter("text", '%' + dto.getText() + '%');
		}
		if (dto.getType() != null) {
			q.setParameter("type", dto.getType());
			qCount.setParameter("type", dto.getType());
		}
		if (dto.getCurrentType() != null) {
			q.setParameter("parentType", dto.getCurrentType() - 1);
			qCount.setParameter("parentType", dto.getCurrentType() - 1);
		}
		if(dto.getAnimalClass() != null) {
			q.setParameter("animalClass", dto.getAnimalClass().trim());
			qCount.setParameter("animalClass", dto.getAnimalClass().trim());
		}
		if(dto.getOrdo() != null) {
			q.setParameter("ordo", dto.getOrdo().trim());
			qCount.setParameter("ordo", dto.getOrdo().trim());
		}
		if(dto.getAnimalClassSci() != null) {
			q.setParameter("animalClassSci", dto.getAnimalClassSci().trim());
			qCount.setParameter("animalClassSci", dto.getAnimalClassSci().trim());
		}
		if(dto.getOrdoSci() != null) {
			q.setParameter("ordoSci", dto.getOrdoSci().trim());
			qCount.setParameter("ordoSci", dto.getOrdoSci().trim());
		}
		int startPosition = pageIndex * pageSize;
		q.setFirstResult(startPosition);
		q.setMaxResults(pageSize);
		List<BiologicalClassDto> entities = q.getResultList();
		long count = (long) qCount.getSingleResult();

		Pageable pageable = new PageRequest(pageIndex, pageSize);
		Page<BiologicalClassDto> result = new PageImpl<BiologicalClassDto>(entities, pageable, count);
		return result;
	}

	@Override
	public BiologicalClassDto saveOrUpdate(BiologicalClassDto dto) {
		if(dto != null) {
			BiologicalClass entity = null;
			if(dto.getId() != null) {
				entity = repository.findId(dto.getId());
			}
			if(entity == null) {
				entity = new BiologicalClass();
			}
			BiologicalClassDto bcOrigin = null;
			if(entity != null) {
				bcOrigin = new BiologicalClassDto(entity);
			}
			
			String biologicalNameOrigin = "";
			if(entity.getName() != null) {
				biologicalNameOrigin = entity.getName();
			}
			entity.setName(dto.getName().trim());
			
			String biologicalSciOrigin = "";
			if(entity.getSci() != null) {
				biologicalSciOrigin = entity.getSci();
			}
			entity.setSci(dto.getSci().trim());
			entity.setCode(dto.getCode().trim());
			entity.setType(dto.getType());
			
			if(dto.getParent() != null && dto.getParent().getId() != null) {
				if(dto.getParent().getType() >= dto.getType()) {
					return null;
				}
				BiologicalClass parent = repository.getOne(dto.getParent().getId());
				if(parent == null) {
					return null;
				}
				entity.setParent(parent);
			}
			
			if(entity.getType() == WLConstant.BiologicalClass.animalClass.getValue()) {
				entity.setParent(null);
			}
			
			entity = repository.save(entity);
			
			// nếu sửa tên lớp thì update tên lớp ở bảng animal 
			if(entity.getType() == WLConstant.BiologicalClass.animalClass.getValue()) {
				if(dto.getId() != null && (!biologicalNameOrigin.equals(entity.getName()) || !biologicalSciOrigin.equals(entity.getSci())) ) {
					repository.updateAnimalClassForAnimal(entity.getName().trim(), entity.getSci(), biologicalNameOrigin);
				}
			}
			if(entity.getType() == WLConstant.BiologicalClass.ordo.getValue()) {
				// nếu sửa tên bộ thì update tên bộ ở bảng animal 
				if(dto.getId() != null && (!biologicalNameOrigin.equals(entity.getName()) || !biologicalSciOrigin.equals(entity.getSci())) ) {
					repository.updateAnimalClassForOrdo(entity.getName().trim(), entity.getSci(), biologicalNameOrigin);
				}
			}
			if(entity.getType() == WLConstant.BiologicalClass.family.getValue()) {
				// nếu sửa tên họ thì update tên họ ở bảng animal 
				if(dto.getId() != null && (!biologicalNameOrigin.equals(entity.getName()) || !biologicalSciOrigin.equals(entity.getSci())) ) {
					repository.updateAnimalClassForFamily(entity.getName().trim(), entity.getSci(), biologicalNameOrigin);
				}
			}
			// nếu sửa lớp cha
			if(bcOrigin.getParent() != null && !bcOrigin.getParent().getId().equals(entity.getParent().getId())) {
				if(entity.getType() == WLConstant.BiologicalClass.ordo.getValue()) {
					repository.updateAnimalClassWhenChangeParentOrdo(entity.getParent().getName(), entity.getParent().getSci(), bcOrigin.getName(), bcOrigin.getParent().getName());
				}
				if(entity.getType() == WLConstant.BiologicalClass.family.getValue()) {
					repository.updateAnimalClassWhenChangeParentFamily(entity.getParent().getName(), entity.getParent().getSci(), entity.getParent().getParent().getName(), entity.getParent().getParent().getSci(), bcOrigin.getName(), bcOrigin.getParent().getName());
				}
			}
			
			return new BiologicalClassDto(entity);
		}
		return null;
	}

	@Override
	public BiologicalClassDto getById(Long id) {
		// TODO Auto-generated method stub
		if(id != null) {
			return new BiologicalClassDto(repository.getOne(id));
		}
		return null;
	}

	@Override
	public Boolean deleteById(Long id) {
		
		BiologicalClass bc = repository.getOne(id);
		
		Long count = repository.checkReferenceBiologicalClass(id);
		if(count > 0) {
			return false;
		}
		count = repository.checkReferenceAnimal(bc.getName());
		if(count > 0) {
			return false;
		}
		repository.delete(id);
		return true;
	}

	@Override
	public List<BiologicalClassDto> saveOrUpdateListImport(List<BiologicalClassDto> list) {
		if(list != null && list.size() >0) {
			for(BiologicalClassDto dto : list) {
				if(dto == null) {
					continue;
				}
				BiologicalClass classBiological = new BiologicalClass();
				if(dto.getClassAnimal() != null) {
					List<BiologicalClass> listClass = repository.getByNameAndType(dto.getClassAnimal(), WLConstant.BiologicalClass.animalClass.getValue());
					if(listClass != null && listClass.size() > 0) {
						listClass.get(0).setSci(dto.getClassAnimalE());
						listClass.get(0).setCode(dto.getClassCode());
						classBiological = repository.save(listClass.get(0));
					} else {
						BiologicalClass bc = new BiologicalClass();
						bc.setName(dto.getClassAnimal());
						bc.setSci(dto.getClassAnimalE());
						bc.setType(WLConstant.BiologicalClass.animalClass.getValue());
						bc.setCode(dto.getClassCode());
						bc.setParent(null);
						classBiological = repository.save(bc);
					}
				}
				
				BiologicalClass ordoBiological = new BiologicalClass();
				if(dto.getOrdo() != null) {
					List<BiologicalClass> listOrdo = repository.getByNameAndType(dto.getOrdo(), WLConstant.BiologicalClass.ordo.getValue());
					if(listOrdo != null && listOrdo.size() > 0) {
						listOrdo.get(0).setSci(dto.getOrdoE());
						listOrdo.get(0).setCode(null);
						if(classBiological.getId() != null) {
							listOrdo.get(0).setParent(classBiological);
						}
						ordoBiological = repository.save(listOrdo.get(0));
					} else {
						BiologicalClass bc = new BiologicalClass();
						bc.setName(dto.getOrdo());
						bc.setSci(dto.getOrdoE());
						bc.setType(WLConstant.BiologicalClass.ordo.getValue());
						bc.setCode(null);
						if(classBiological.getId() != null) {
							bc.setParent(classBiological);
						}
						ordoBiological = repository.save(bc);
					}
				} else {
					continue;
				}
				
				if(dto.getFamily() != null) {
					List<BiologicalClass> listFamily = repository.getByNameAndType(dto.getFamily(), WLConstant.BiologicalClass.family.getValue());
					if(listFamily != null && listFamily.size() > 0) {
						listFamily.get(0).setSci(dto.getFamilyE());
						listFamily.get(0).setCode(null);
						if(ordoBiological.getId() != null) {
							listFamily.get(0).setParent(ordoBiological);
						}
						repository.save(listFamily.get(0));
					} else {
						BiologicalClass bc = new BiologicalClass();
						bc.setName(dto.getFamily());
						bc.setSci(dto.getFamilyE());
						bc.setType(WLConstant.BiologicalClass.family.getValue());
						bc.setCode(null);
						if(ordoBiological.getId() != null) {
							bc.setParent(ordoBiological);
						}
						repository.save(bc);
					}
				} else {
					continue;
				}
				
			}
		}
		return null;
	}

}
