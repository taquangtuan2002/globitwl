package com.globits.wl.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONException;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalProductTarget;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.AnimalRequired;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.FarmAnimalProductTargetExist;
import com.globits.wl.domain.ForestProductsListDetail;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.IndividualAnimal;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportFormAnimalEgg;
import com.globits.wl.domain.ReportFormAnimalGiveBirth;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalMapDto;
import com.globits.wl.dto.BiologicalClassDto;
import com.globits.wl.dto.FarmAnimalDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.functiondto.AnimalSearchDto;
import com.globits.wl.dto.functiondto.MergeAnimal;
import com.globits.wl.repository.AnimalReportDataRepository;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.AnimalRequiredRepository;
import com.globits.wl.repository.AnimalTypeRepository;
import com.globits.wl.repository.BiologicalClassRepository;
import com.globits.wl.repository.FarmAnimalProductTargetExistRepository;
import com.globits.wl.repository.ForestProductsListDetailRepository;
import com.globits.wl.repository.ImportExportAnimalRepository;
import com.globits.wl.repository.IndividualAnimalRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.repository.ReportForm16Repository;
import com.globits.wl.repository.ReportFormAnimalEggRepository;
import com.globits.wl.repository.ReportFormAnimalGiveBirthRepository;
import com.globits.wl.service.AnimalService;
import com.globits.wl.service.AnimalTypeService;
import com.globits.wl.service.FarmSizeService;
import com.globits.wl.utils.FarmMapServiceUtil;
import com.globits.wl.utils.WLConstant;

@Service
public class AnimalServiceImpl extends GenericServiceImpl<Animal, Long> implements AnimalService {
	@Autowired
	private AnimalRepository animalRepository;
	@Autowired
	private OriginalRepository originalRepository;
	@Autowired
	private AnimalTypeRepository animalTypeRepository;
	@Autowired
	private ProductTargetRepository productTargetRepository;
	@Autowired
	AnimalTypeService animalTypeService;
	@Autowired
	FarmSizeService farmSizeService;
	@Autowired
	private BiologicalClassRepository biologicalClassRepository;
	@Autowired
	private ReportForm16Repository reportForm16Repository;
	@Autowired
	private AnimalReportDataRepository animalReportDataRepository;

	@Autowired
	private AnimalRequiredRepository animalRequiredRepository;
	
	@Autowired
	private FarmAnimalProductTargetExistRepository farmAnimalProductTargetExistRepository;
	
	@Autowired
	private ForestProductsListDetailRepository forestProductsListDetailRepository;
	
	@Autowired
	private ImportExportAnimalRepository importExportAnimalRepository;
	
	@Autowired
	private IndividualAnimalRepository individualAnimalRepository;
	
	@Autowired
	private ReportFormAnimalEggRepository reportFormAnimalEggRepository;
	
	@Autowired
	private ReportFormAnimalGiveBirthRepository reportFormAnimalGiveBirthRepository;
	
	@Override
	public Page<AnimalDto> getAll(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
//		animalTypeService.checkDuplicateCode("");
//		farmSizeService.checkDuplicateCode("");
		return this.animalRepository.getAll(pageable);
	}

	@Override
	public AnimalDto getAnimalById(Long id) {
		return this.animalRepository.getAnimalById(id);
	}

	@Override
	public AnimalDto createAnimal(AnimalDto dto) {
		return updateAnimal(null, dto);
	}

	@Override
	public AnimalDto updateAnimal(Long id, AnimalDto dto) {
		// Khoadv42 ngày 7h30 ngày 1/6/2020 if (dto != null && dto.getOriginalDto()!=
		// null )
		if (dto != null) {
			boolean isNew = false;
			Animal animal = null;
			if (id != null) {
				animal = this.animalRepository.findOne(id);
			} else if (dto.getId() != null) {
				animal = this.animalRepository.findOne(dto.getId());
			}
			if (animal == null) {
				animal = new Animal();
				isNew = true;
			}

			if (dto.getDescription() != null) {
				animal.setDescription(dto.getDescription());
			}
			animal.setName(dto.getName());			
			if (dto.getOrderCode() != null) {
				animal.setOrderCode(dto.getOrderCode());
			} else {
				animal.setOrderCode(0);
			}
			// Mặc định ReportCode = Code
			if (dto.getReportCode() == null || dto.getReportCode() == "") {
				dto.setReportCode(dto.getCode());
			}
			// Mặc định ReportName = Name
			if (dto.getReportName() == null || dto.getReportName() == "") {
				dto.setReportName(dto.getName());
			}
			animal.setReportCode(dto.getReportCode());
			animal.setReportName(dto.getReportName());
			animal.setLiveStockMethod(dto.getLiveStockMethod());
			animal.setReportType(dto.getReportType());
			animal.setFarmingTime(dto.getFarmingTime());
			animal.setWeightGain(dto.getWeightGain());
			animal.setLoss(dto.getLoss());
			animal.setEggYield(dto.getEggYield());
			animal.setScienceName(dto.getScienceName());
			animal.setEnName(dto.getEnName());
			animal.setCites(dto.getCites());
			animal.setVnlist(dto.getVnlist());
			animal.setAniGroup(dto.getAniGroup());
			animal.setOldCode(dto.getOldCode());
			animal.setOrdo(dto.getOrdo());
			animal.setFamily(dto.getFamily());
			animal.setFamilySci(dto.getFamilySci());
			animal.setSource(dto.getSource());
			animal.setProtectionLevel(dto.getProtectionLevel()); 
			if (dto.getAnimalClass() != null && dto.getAnimalClass().length() > 0) {
				animal.setAnimalClass(dto.getAnimalClass());
			}
			if(isNew) {
				String animalClass = animal.getAnimalClass().trim().toLowerCase();
				String code = this.generateCode(dto, animalClass);
				animal.setCode(code);
			}
			animal.setCites(dto.getCites());
			animal.setVnlist(dto.getVnlist());
			animal.setVnlist06(dto.getVnlist06());
			animal.setReproductionForm(dto.getReproductionForm());
			animal.setOtherName(dto.getOtherName());
			if (dto.getOriginalDto() != null) {
				Original original = null;
				if (dto.getOriginalDto().getId() != null) {
					original = this.originalRepository.findOne(dto.getOriginalDto().getId());
				}
				if (original == null) {
					original = new Original();
//					original.setCreateDate(currentDate);
//					original.setCreatedBy(currentUserName);
				}
				original.setCode(dto.getOriginalDto().getCode());
				original.setDescription(dto.getOriginalDto().getDescription());
				original.setName(dto.getOriginalDto().getName());
				animal.setOriginal(original);

			} else {
				animal.setOriginal(null);
			}
			if (dto.getTypeDto() != null) {
				AnimalType animalType = null;
				if (dto.getTypeDto().getId() != null) {
					animalType = this.animalTypeRepository.findOne(dto.getTypeDto().getId());
				}
				if (animalType == null) {
					animalType = new AnimalType();
//					animalType.setCreateDate(currentDate);
//					animalType.setCreatedBy(currentUserName);
				}
				animalType.setCode(dto.getTypeDto().getCode());
				animalType.setDescription(dto.getTypeDto().getDescription());
				animalType.setName(dto.getTypeDto().getName());
				animal.setType(animalType);

			} else {
				animal.setType(null);
			}
//			if (dto.getProductTargetDto() != null) {
//				ProductTarget productTarget = null;
//				if (dto.getProductTargetDto().getId() != null) {
//					productTarget = this.productTargetRepository.findOne(dto.getProductTargetDto().getId());
//				}
//				if (productTarget == null) {
//					productTarget = new ProductTarget();
//					productTarget.setCreateDate(currentDate);
//					productTarget.setCreatedBy(currentUserName);
//				}
//				productTarget.setCode(dto.getProductTargetDto().getCode());
//				productTarget.setDescription(dto.getProductTargetDto().getDescription());
//				productTarget.setName(dto.getProductTargetDto().getName());
//				animal.setProductTarget(productTarget);
//
//			}
			if (dto.getParent() != null && dto.getParent().getId() != null) {
				Animal parent = animalRepository.findOne(dto.getParent().getId());
				animal.setParent(parent);
			} else {
				animal.setParent(null);
			}

			/** AnimalProductTarget */
			Set<AnimalProductTarget> animalProductTargets = new HashSet<AnimalProductTarget>();
			if (dto.getAnimalProductTargets() != null && dto.getAnimalProductTargets().size() > 0) {
				for (ProductTargetDto farmAnimalTypeDto : dto.getAnimalProductTargets()) {
					if (farmAnimalTypeDto != null) {
						ProductTarget productTarget = productTargetRepository.findOne(farmAnimalTypeDto.getId());
						if (productTarget != null) {
							AnimalProductTarget fpt = new AnimalProductTarget();
							fpt.setAnimal(animal);
							fpt.setProductTarget(productTarget);
							animalProductTargets.add(fpt);
						}
					}

				}
				if (animalProductTargets != null && animalProductTargets.size() > 0) {
					if (animal.getAnimalProductTargets() == null) {
						animal.setAnimalProductTargets(animalProductTargets);
					} else {
						animal.getAnimalProductTargets().clear();
						animal.getAnimalProductTargets().addAll(animalProductTargets);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (animal.getAnimalProductTargets() != null) {
					animal.getAnimalProductTargets().clear();
				}
			}
			//cập nhật Nhóm cho động vật
			String ag = updateAnimalGroup(dto);
			if(ag != null) {
				animal.setAnimalGroup(ag);
			}
			animal = this.animalRepository.save(animal);
			dto.setId(animal.getId());
			return dto;
		}
		return null;
	}
	
	public String generateCode(AnimalDto dto , String animalClass)
	{
		//List<String> animalClassSci = animalRepository.getListAnimalClassScienceName(dto.getAnimalClass());
		String code = "";
		String sql = " select fa.code from Animal fa  where (1=1)";
		Query q = manager.createQuery(sql, String.class);
		List<String> list = q.getResultList();
		long count = animalRepository.count();
		int i = 0;
		String newCode = "";
		if(animalClass==null && animalClass=="") {
			return "";
		}
		if(animalClass.equals("Lớp lưỡng cư".trim().toLowerCase())) {
			code = "A";
 		} else if(animalClass.equals("Lớp bò sát".trim().toLowerCase())) {
			code = "R";
		} else if(animalClass.equals("Lớp chim".trim().toLowerCase())) {
			code = "B";
		} else if(animalClass.equals("Lớp cá vây tia".trim().toLowerCase())) {
			code = "F";
		} else if(animalClass.equals("Lớp côn trùng".trim().toLowerCase())) {
			code = "CT";
		} else if(animalClass.equals("Lớp hình nhện".trim().toLowerCase())) {
			code = "CT";
		} else if(animalClass.equals("Lớp thú".trim().toLowerCase())) {
			code = "M";
		} else {
			code = "A";
		}
		newCode = code + count;
		while(i < list.size())
		{			
			if(newCode.equals(list.get(i)))
			{
				count = count + 1;
				newCode = code + count;
			}
			i++;
		}
		return newCode;		
	}

	@Override
	public ResponseEntity<Boolean> deleteAnimal(Long id) {
		if (id != null) {
			this.animalRepository.delete(id);
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}

	@Override
	public ResponseEntity<Boolean> deleteAnimals(List<Long> ids) {
		if (ids != null && ids.size() > 0) {
			for (Long id : ids) {
				this.animalRepository.delete(id);
			}
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
	}

	@Override
	public List<AnimalDto> getAllDto() {
		return this.animalRepository.getAllDto();
	}

	@Override
	public List<AnimalDto> getListParent() {
		// TODO Auto-generated method stub
		return this.animalRepository.getListParent();
	}

	@Override
	public List<AnimalDto> getListByParent(Long parentId) {
		// TODO Auto-generated method stub
		return this.animalRepository.getListByParent(parentId);
	}

	@Override
	public Page<AnimalDto> searchAnimalDto(AnimalSearchDto searchDto, int pageIndex, int pageSize) {
		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		String namecode = searchDto.getNameOrCode();

		String sql = " select new com.globits.wl.dto.AnimalDto(fa) from Animal fa  where (1=1)";
		String sqlCount = "select count(fa.id) from Animal fa where (1=1)";
		String whereClause = "";
		if (namecode != null && namecode.length() > 0) {
			whereClause += " and (fa.code like :namecode or fa.name like :namecode or fa.scienceName like :namecode or fa.enName like :namecode or fa.otherName like :namecode) ";
		}
		//thanh 
		if (searchDto.getOtherName() != null && searchDto.getOtherName().length() > 0) {
			whereClause += " AND fa.otherName like :otherName ";
		}
		//thanh 
		if (searchDto.getAnimalClass() != null && searchDto.getAnimalClass().length() > 0) {
			whereClause += " AND fa.animalClass like :animalClass ";
		}
		if(searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
			whereClause += " AND fa.animalClass in :animalClasss ";
		}
		if (searchDto.getOrdo() != null && searchDto.getOrdo().length() > 0) {
			whereClause += " AND fa.ordo like :ordo ";
		}
		if(searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
			whereClause += " AND fa.ordo in :ordos ";
		}
		if (searchDto.getFamily() != null && searchDto.getFamily().length() > 0) {
			whereClause += " AND fa.family like :family ";
		}
		if(searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
			whereClause += " AND fa.family in :familys";
		}
		if (searchDto.getVnlist06() != null && searchDto.getVnlist06().length() > 0) {
			whereClause += " AND fa.vnlist06 like :vnlist06 ";
		}
		if(searchDto.getVnList06s() != null && searchDto.getVnList06s().size() > 0) {
			whereClause += " AND fa.vnlist06 in :vnlist06s";
		}
		if (searchDto.getVnlist() != null && searchDto.getVnlist().length() > 0) {
			whereClause += " AND fa.vnlist like :vnlist ";
		}
		if (searchDto.getCites() != null && searchDto.getCites().length() > 0) {
			whereClause += " AND fa.cites like :cites ";
		}
		if (searchDto.getReproductionForm() != null) {
			whereClause += " AND fa.reproductionForm=:reproductionForm ";
		}
		sql += whereClause;
		sql += " order by fa.code asc ";
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, AnimalDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (namecode != null && namecode.length() > 0) {
			q.setParameter("namecode", '%' + namecode + '%');
			qCount.setParameter("namecode", '%' + namecode + '%');
		}
		//thanh
		if (searchDto.getOtherName() != null && searchDto.getOtherName().length() > 0) {
			q.setParameter("otherName", '%' + searchDto.getOtherName() + '%');
			qCount.setParameter("otherName", '%' + searchDto.getOtherName() + '%');
		}
		//thanh
		
		// Lớp
		if (searchDto.getAnimalClass() != null && searchDto.getAnimalClass().length() > 0) {
			q.setParameter("animalClass", '%' + searchDto.getAnimalClass() + '%');
			qCount.setParameter("animalClass", '%' + searchDto.getAnimalClass() + '%');
		}
		
		if(searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
			q.setParameter("animalClasss", searchDto.getListAnimalClass());
			qCount.setParameter("animalClasss", searchDto.getListAnimalClass());
		}
		// Bộ
		if (searchDto.getOrdo() != null && searchDto.getOrdo().length() > 0) {
			q.setParameter("ordo", '%' + searchDto.getOrdo() + '%');
			qCount.setParameter("ordo", '%' + searchDto.getOrdo() + '%');
		}
		if(searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
			q.setParameter("ordos", searchDto.getListAnimalOrdo());
			qCount.setParameter("ordos", searchDto.getListAnimalOrdo());
		}
		// HỌ
		if (searchDto.getFamily() != null && searchDto.getFamily().length() > 0) {
			q.setParameter("family", '%' + searchDto.getFamily() + '%');
			qCount.setParameter("family", '%' + searchDto.getFamily() + '%');
		}
		if(searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
			q.setParameter("familys", searchDto.getListAnimalFamily());
			qCount.setParameter("familys", searchDto.getListAnimalFamily());
		}
		if (searchDto.getVnlist06() != null && searchDto.getVnlist06().length() > 0) {
			q.setParameter("vnlist06", searchDto.getVnlist06());
			qCount.setParameter("vnlist06", searchDto.getVnlist06());
		}
		if(searchDto.getVnList06s() != null && searchDto.getVnList06s().size() > 0) {
			q.setParameter("vnlist06s", searchDto.getVnList06s());
			qCount.setParameter("vnlist06s", searchDto.getVnList06s());
		}
		if (searchDto.getVnlist() != null && searchDto.getVnlist().length() > 0) {
			q.setParameter("vnlist", '%' + searchDto.getVnlist() + '%');
			qCount.setParameter("vnlist", '%' + searchDto.getVnlist() + '%');
		}
		if (searchDto.getCites() != null && searchDto.getCites().length() > 0) {
			q.setParameter("cites",  searchDto.getCites() );
			qCount.setParameter("cites",  searchDto.getCites());
		}
		if (searchDto.getReproductionForm() != null) {
			q.setParameter("reproductionForm", searchDto.getReproductionForm());
			qCount.setParameter("reproductionForm", searchDto.getReproductionForm());
		}
		q.setFirstResult((pageIndex) * pageSize);
		q.setMaxResults(pageSize);

		Long numberResult = (Long) qCount.getSingleResult();
		Page<AnimalDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
		return page;
	}

	@Override
	public List<String> getListAnimalClass() {
		String sql = " SELECT DISTINCT(a.animalClass) FROM Animal a ";
		Query q = manager.createQuery(sql, String.class);
		return q.getResultList();
	}
	
	@Override
	public List<String> getListAnimalClassSci() {
		String sql = " SELECT DISTINCT(a.animalClassSci) FROM Animal a ";
		Query q = manager.createQuery(sql, String.class);
		return q.getResultList();
	}

	@Override
	public List<String> getListAnimalOrdo(String animalClass) {
		String sql = " SELECT DISTINCT(a.ordo) FROM Animal a WHERE 1=1 ";
		if (animalClass != null && animalClass.length() > 0) {
			sql += " AND a.animalClass=:animalClass ";
		}
		Query q = manager.createQuery(sql, String.class);
		if (animalClass != null && animalClass.length() > 0) {
			q.setParameter("animalClass", animalClass);
		}
		return q.getResultList();
	}
	
	//	Search List
	@Override
	public List<String> getListAnimalOrdoParam(AnimalSearchDto dto) {
		String sql = " SELECT DISTINCT(a.ordo) FROM Animal a WHERE 1=1 ";
		if(dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
			sql += " AND a.animalClass in :animalClass ";
		}
		Query q = manager.createQuery(sql, String.class);
		if(dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
			q.setParameter("animalClass", dto.getListAnimalClass());
		}
		return q.getResultList();
	}

	@Override
	public List<String> getListAnimalFamily(String animalClass, String ordo) {
		String sql = " SELECT DISTINCT(a.family) FROM Animal a WHERE 1=1  ";
		if (animalClass != null && animalClass.length() > 0) {
			sql += " AND a.animalClass=:animalClass ";
		}
		if (ordo != null && ordo.length() > 0) {
			sql += " AND a.ordo=:ordo ";
		}
		Query q = manager.createQuery(sql, String.class);
		if (animalClass != null && animalClass.length() > 0) {
			q.setParameter("animalClass", animalClass);
		}
		if (ordo != null && ordo.length() > 0) {
			q.setParameter("ordo", ordo);
		}
		return q.getResultList();
	}
	
	// Search List
	@Override
	public List<String> getListAnimalFamilyParam(AnimalSearchDto dto) {
		String sql = " SELECT DISTINCT(a.family) FROM Animal a WHERE 1=1  ";
		if(dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
			sql += " AND a.animalClass in :animalClass ";
		}
		if(dto.getListAnimalOrdo() != null && dto.getListAnimalOrdo().size() > 0) {
			sql += " AND a.ordo in :ordo";
		}
		Query q = manager.createQuery(sql, String.class);
		if (dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
			q.setParameter("animalClass", dto.getListAnimalClass());
		}
		if (dto.getListAnimalOrdo() != null && dto.getListAnimalOrdo().size() > 0) {
			q.setParameter("ordo", dto.getListAnimalOrdo());
		}
		return q.getResultList();
	}
	
	@Override
	public List<String> getListAnimalOrdoSci(String animalClassSci) {
		String sql = " SELECT DISTINCT(a.ordoSci) FROM Animal a WHERE 1=1 ";
		if (animalClassSci != null && animalClassSci.length() > 0) {
			sql += " AND a.animalClassSci=:animalClassSci ";
		}
		Query q = manager.createQuery(sql, String.class);
		if (animalClassSci != null && animalClassSci.length() > 0) {
			q.setParameter("animalClassSci", animalClassSci);
		}
		return q.getResultList();
	}
	
	@Override
	public List<String> getListAnimalFamilySci(String animalClassSci, String ordoSci) {
		String sql = " SELECT DISTINCT(a.familySci) FROM Animal a WHERE 1=1  ";
		if (animalClassSci != null && animalClassSci.length() > 0) {
			sql += " AND a.animalClassSci=:animalClassSci ";
		}
		if (ordoSci != null && ordoSci.length() > 0) {
			sql += " AND a.ordoSci=:ordoSci ";
		}
		Query q = manager.createQuery(sql, String.class);
		if (animalClassSci != null && animalClassSci.length() > 0) {
			q.setParameter("animalClassSci", animalClassSci);
		}
		if (ordoSci != null && ordoSci.length() > 0) {
			q.setParameter("ordoSci", ordoSci);
		}
		return q.getResultList();
	}

	@Override
	public AnimalDto checkDuplicateCode(String code) {
		AnimalDto viewCheckDuplicateCodeDto = new AnimalDto();
		Animal animal = null;
		List<Animal> list = animalRepository.findByCode(code);
		if (list != null && list.size() > 0) {
			animal = list.get(0);
		}
		if (list == null) {
			viewCheckDuplicateCodeDto.setDuplicate(false);
		} else if (list != null && list.size() > 0) {
			viewCheckDuplicateCodeDto.setDuplicate(true);
			viewCheckDuplicateCodeDto.setDupCode(animal.getCode());
			viewCheckDuplicateCodeDto.setDupName(animal.getName());
		}
		return viewCheckDuplicateCodeDto;
	}
	
	@Override
	public AnimalDto changeCode(Long id, String code) {
		if(id != null || code != null) {
			Animal a = animalRepository.getOne(id);
			if(a == null) {
				return null;
			}
			a.setCode(code);
			a = animalRepository.save(a);
			return new AnimalDto(a);
		} else {
			return null;
		}
	}
	
	@Override
	public AnimalDto changeReproductionForm(Long id, Integer reproductionForm) {
		if(id != null || reproductionForm != null) {
			Animal a = animalRepository.getOne(id);
			if(a == null) {
				return null;
			}
			a.setReproductionForm(reproductionForm);
			a = animalRepository.save(a);
			return new AnimalDto(a);
		} else {
			return null;
		}
	}

	@Override
	public List<AnimalDto> saveOrUpdateList(List<AnimalDto> listAnimal) {
		ArrayList<AnimalDto> ret = new ArrayList<AnimalDto>();
		for (int i = 0; i < listAnimal.size(); i++) {
			AnimalDto dto = listAnimal.get(i);

			saveOrUpdateAnimal(dto.getId(), dto);
		}
		return ret;
	}

	@Override
	public AnimalDto saveOrUpdateAnimal(Long id, AnimalDto dto) {
		if (dto != null && dto.getName() != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			Boolean isUpdate = false;
			Animal animal = null;
			if (id != null) {
				animal = this.animalRepository.findOne(id);
			} else if (dto.getId() != null) {
				animal = this.animalRepository.findOne(dto.getId());
			} else if (dto.getCode() != null) {
				List<Animal> list = animalRepository.findByCode(dto.getCode());
				if (list != null && list.size() > 0) {
					animal = list.get(0);
					isUpdate = true;
				}
			}
//			else if(dto.getName()!=null) {
//				List<Animal> list= this.animalRepository.findByName(dto.getName());
//				if (list != null && list.size() > 0) {
//					animal = list.get(0);
//				}
//			}else if(dto.getScienceName()!=null) {
//				List<Animal> list= this.animalRepository.findByAnimalName(dto.getScienceName());
//				if (list != null && list.size() > 0) {
//					animal = list.get(0);
//				}
//			}
			if (animal == null) {
				animal = new Animal();
				animal.setCreateDate(currentDate);
				animal.setCreatedBy(currentUserName);
			}
			
			if(isUpdate == false) {
				// nếu có mã import thì dùng luôn làm mã hệ thống
				animal.setCode(dto.getCode().trim());
				
				List<BiologicalClassDto> listBiologicalClass = null;
				// nếu có lớp thì lấy chữ cái đứng đầu làm mã
				if(animal.getAnimalClass() != null) {
					listBiologicalClass = biologicalClassRepository.getBiologicalClassByName(animal.getAnimalClass(), WLConstant.BiologicalClass.animalClass.getValue());
				}
				if(listBiologicalClass != null && listBiologicalClass.size() > 0) {
					for(BiologicalClassDto bDto : listBiologicalClass) {
						if(bDto.getCode() != null) {
							long count = animalRepository.count();
							count = count + 1;
							animal.setCode(bDto.getCode() + count);
							break;
						}
					}
				}
			} 
				
			if (dto.getDescription() != null) {
				animal.setDescription(dto.getDescription());
			}
//			if (dto.getName() != null) {
			animal.setName(dto.getName());
//			}
			// Mặc định ReportCode = Code
			if (dto.getReportCode() == null || dto.getReportCode() == "") {
				dto.setReportCode(dto.getCode());
			}
			// Mặc định ReportName = Name
			if (dto.getReportName() == null || dto.getReportName() == "") {
				dto.setReportName(dto.getName());
			}
			animal.setLiveStockMethod(dto.getLiveStockMethod());
			animal.setReportType(dto.getReportType());

			animal.setReportCode(dto.getReportCode());
			animal.setReportName(dto.getReportName());

			animal.setFarmingTime(dto.getFarmingTime());
			animal.setWeightGain(dto.getWeightGain());
			animal.setLoss(dto.getLoss());
			animal.setEggYield(dto.getEggYield());
			
			//them cac truong chưa có
			animal.setProtectionLevel(dto.getProtectionLevel());
			animal.setSynonym(dto.getSynonym());
			animal.setOtherName(dto.getOtherName());
			animal.setScienceName(dto.getScienceName());
			animal.setEnName(dto.getEnName());
			animal.setAnimalClass(dto.getAnimalClass());
			animal.setOrdo(dto.getOrdo());
			animal.setFamily(dto.getFamily());
			animal.setCites(dto.getCites());
			animal.setVnlist(dto.getVnlist());
			animal.setVnlist06(dto.getVnlist06());
			animal.setReproductionForm(dto.getReproductionForm());
			animal.setSource(dto.getSource());
			
			
			//them các trường chưa có
			
			if (dto.getOriginalDto() != null) {
				Original original = null;
				if (dto.getOriginalDto().getId() != null) {
					original = this.originalRepository.findOne(dto.getOriginalDto().getId());
				} else if (dto.getOriginalDto().getCode() != null) {
					List<Original> list = originalRepository.findByCode(dto.getOriginalDto().getCode());
					if (list != null && list.size() > 0) {
						original = list.get(0);
					}
				} else if (dto.getOriginalDto().getName() != null) {
					List<Original> list = originalRepository.findByName(dto.getOriginalDto().getName());
					if (list != null && list.size() > 0) {
						original = list.get(0);
					}
				}

				animal.setOriginal(original);

			} else {
				animal.setOriginal(null);
			}
			if (dto.getTypeDto() != null) {
				AnimalType animalType = null;
				if (dto.getTypeDto().getId() != null) {
					animalType = this.animalTypeRepository.findOne(dto.getTypeDto().getId());
				} else if (dto.getTypeDto().getCode() != null) {
					List<AnimalType> list = animalTypeRepository.findByCode(dto.getTypeDto().getCode());
					if (list != null && list.size() > 0) {
						animalType = list.get(0);
					}
				} else if (dto.getTypeDto().getName() != null) {
					List<AnimalType> list = animalTypeRepository.findByName(dto.getTypeDto().getName());
					if (list != null && list.size() > 0) {
						animalType = list.get(0);
					}
				}

				animal.setType(animalType);

			} else {
				animal.setType(null);
			}

			if (dto.getParent() != null && dto.getParent().getId() != null) {
				Animal parent = animalRepository.findOne(dto.getParent().getId());
				animal.setParent(parent);
			} else if (dto.getParent() != null && dto.getParent().getCode() != null) {
				List<Animal> list = animalRepository.findByCode(dto.getParent().getCode());
				if (list != null && list.size() > 0) {
					animal.setParent(list.get(0));
				}
			} else if (dto.getParent() != null && dto.getParent().getName() != null) {
				List<Animal> list = animalRepository.findByName(dto.getParent().getName());
				if (list != null && list.size() > 0) {
					animal.setParent(list.get(0));
				}
			} else {
				animal.setParent(null);
			}

			/** AnimalProductTarget */
			Set<AnimalProductTarget> animalProductTargets = new HashSet<AnimalProductTarget>();
			if (dto.getAnimalProductTargets() != null && dto.getAnimalProductTargets().size() > 0) {
				for (ProductTargetDto farmAnimalTypeDto : dto.getAnimalProductTargets()) {
					if (farmAnimalTypeDto != null) {
						ProductTarget productTarget = null;
						if (farmAnimalTypeDto.getId() != null) {
							productTarget = productTargetRepository.findOne(farmAnimalTypeDto.getId());
						} else if (farmAnimalTypeDto.getCode() != null) {
							List<ProductTarget> list = productTargetRepository.findByCode(farmAnimalTypeDto.getCode());
							if (list != null && list.size() > 0) {
								productTarget = list.get(0);
							}
						} else if (farmAnimalTypeDto.getName() != null) {
							List<ProductTarget> list = productTargetRepository.findByName(farmAnimalTypeDto.getName());
							if (list != null && list.size() > 0) {
								productTarget = list.get(0);
							}
						}

						if (productTarget != null) {
							AnimalProductTarget fpt = new AnimalProductTarget();
							fpt.setAnimal(animal);
							fpt.setProductTarget(productTarget);
							animalProductTargets.add(fpt);
						}
					}

				}
				if (animalProductTargets != null && animalProductTargets.size() > 0) {
					if (animal.getAnimalProductTargets() == null) {
						animal.setAnimalProductTargets(animalProductTargets);
					} else {
						animal.getAnimalProductTargets().clear();
						animal.getAnimalProductTargets().addAll(animalProductTargets);
					}
				}

			} else {// Nếu submit list trống lên thì xóa hết
				if (animal.getAnimalProductTargets() != null) {
					animal.getAnimalProductTargets().clear();
				}
			}
			
			//cập nhật Nhóm cho động vật
			String ag = updateAnimalGroup(dto);
			if(ag != null) {
				animal.setAnimalGroup(ag);
			}
			animal = this.animalRepository.save(animal);
			dto.setId(animal.getId());
			return dto;
		}
		return null;
	}

	@Override
	public void saveListAnimal(List<AnimalDto> list) {
		for (AnimalDto dto : list) {
			if (dto != null) {
				this.createAnimal(dto);
			}
		}

	}

	public void pushAnimalToMap() throws ClientProtocolException, JSONException, IOException {
		List<Animal> listAnimals = animalRepository.findAll();
		if (listAnimals != null && listAnimals.size() > 0) {
			for (Animal a : listAnimals) {
				AnimalMapDto animalMapDto = new AnimalMapDto(a);
				FarmMapServiceUtil.createAnimalMap(animalMapDto);
			}
		}
	}

	@Override
	public List<Animal> getListAnimalByFamily(String family) {
		List<Animal> listAnimals = animalRepository.getListAnimalByFamily(family);
		if (listAnimals != null && listAnimals.size() > 0)
			return listAnimals;
		else
			return null;
	}

	@Override
	public List<String> getVnList06() {
		String sql = " SELECT DISTINCT(a.vnlist06) FROM Animal a WHERE 1=1 ";
		Query q = manager.createQuery(sql, String.class);
		return q.getResultList();
	}

	@Override
	public List<String> getListCites() {
		String sql = " SELECT DISTINCT(a.cites) FROM Animal a WHERE 1=1 AND a.cites != 'EN'";
		Query q = manager.createQuery(sql, String.class);
		return q.getResultList();
	}

	@Override
	public List<String> getVnList() {
		String sql = " SELECT DISTINCT(a.vnlist) FROM Animal a WHERE 1=1 ";
		Query q = manager.createQuery(sql, String.class);
		return q.getResultList();
	}

	@Override
	public List<FarmAnimalDto> getListAnimalByFarmId(Long farmId) {
		if (farmId != null) {
			List<FarmAnimalDto> listFarmAnimals = null;
			String sql = "select fa from FarmAnimal fa where fa.farm.id =: farmId";
			Query q = manager.createQuery(sql, FarmAnimalDto.class);
			q.setParameter("farmId", farmId);
			listFarmAnimals = q.getResultList();
			if (listFarmAnimals != null) {
				return listFarmAnimals;
			}
		}
		return null;
	}

	public String updateAnimalGroup(AnimalDto dto) {
		//		Nhóm 1: I cites, IB và NĐ64.
		//		Nhóm 2: II,III cites và IIB
		//		Nhóm 3: Động vật rừng thông thường/ hoang dã khác
//		if (dto.getCites() != null && dto.getCites().length() > 0 && dto.getVnlist() != null && dto.getVnlist().length() >0 && dto.getVnlist06() != null && dto.getVnlist06().length() > 0) {
			if ((dto.getCites()!=null && "I".equals(dto.getCites())) 
					|| (dto.getVnlist06()!=null && "IB".equals(dto.getVnlist06()))
					|| (dto.getVnlist()!=null && "64".equals(dto.getVnlist()))) {// ĐV thuộc Nhóm 1
				return WLConstant.AnimalGroup.GROUP1.getName();
			} else if (	(dto.getCites()!=null && "II".equals(dto.getCites())) 
						|| (dto.getCites()!=null && "III".contentEquals(dto.getCites()))
						|| (dto.getVnlist06()!=null && "IIB".equals(dto.getVnlist06()))) {// ĐV thuộc Nhóm 2
				return WLConstant.AnimalGroup.GROUP2.getName();
			} else {
				return WLConstant.AnimalGroup.GROUP3.getName();
			}	
//		}
	}

	@Override
	public void updateGroupToAllAnimal() {
		List<AnimalDto> listAnimal = animalRepository.getAllDto();
		for (int i = 0; i < listAnimal.size(); i++) {
			AnimalDto dto = listAnimal.get(i);
			saveOrUpdateAnimal(dto.getId(), dto);
		}
	}

	@Override
	public List<String> getListAnimalGroup() {
		String sql = " SELECT DISTINCT(a.animalGroup) FROM Animal a WHERE a.animalGroup is not null ";
		Query q = manager.createQuery(sql, String.class);
		return q.getResultList();
	}

	@Override
	public AnimalDto mergeAnimal(MergeAnimal dto) {
		if(dto!=null && dto.getAnimalKeep()!=null && dto.getAnimalDelete()!=null) {
			if(dto.getAnimalKeep().getId()!=null && dto.getAnimalDelete().getId()!=null) {
				Animal animalKeep= animalRepository.findOne(dto.getAnimalKeep().getId());
				Animal animalDelete= animalRepository.findOne(dto.getAnimalDelete().getId());
				
				if(animalKeep!=null && animalDelete!=null) {
					//xử lí form 16
					String sqlF16= " SELECT s FROM ReportForm16 s WHERE  s.animal.id =:idDelete";
					Query q16 = manager.createQuery(sqlF16, ReportForm16.class);
					q16.setParameter("idDelete", animalDelete.getId());
					List<ReportForm16> F16s= q16.getResultList();
					if(F16s!=null && F16s.size()>0) {
						for(ReportForm16 rp16: F16s) {
							rp16.setAnimal(animalKeep);
							rp16= reportForm16Repository.save(rp16);
						}
					}
					
					//xử lí animal
					String sqlanimal= " SELECT s FROM Animal s WHERE  s.parent.id =:idDelete";
					Query qanimal = manager.createQuery(sqlanimal, Animal.class);
					qanimal.setParameter("idDelete", animalDelete.getId());
					List<Animal> as= qanimal.getResultList();
					if(as!=null && as.size()>0) {
						for(Animal item: as) {
							item.setParent(animalKeep);
							item= animalRepository.save(item);
						}
					}
					
					//xử lí animalrp
					String sqlanimalrp= " SELECT s FROM AnimalReportData s WHERE  s.animal.id =:idDelete";
					Query qanimalName = manager.createQuery(sqlanimalrp, AnimalReportData.class);
					qanimalName.setParameter("idDelete", animalDelete.getId());
					List<AnimalReportData> ans= qanimalName.getResultList();
					if(ans!=null && ans.size()>0) {
						for(AnimalReportData item: ans) {
							item.setAnimal(animalKeep);
							item= animalReportDataRepository.save(item);
						}
					}
					
					//xử lí animalrp
					String sqlanimalrq= " SELECT s FROM AnimalRequired s WHERE  s.parent.id =:idDelete";
					Query qanimalrq = manager.createQuery(sqlanimalrq, AnimalRequired.class);
					qanimalrq.setParameter("idDelete", animalDelete.getId());
					List<AnimalRequired> arq= qanimalrq.getResultList();
					if(arq!=null && arq.size()>0) {
						for(AnimalRequired item: arq) {
							item.setParent(animalKeep);
							item= animalRequiredRepository.save(item);
						}
					}
					
					
					//xử lí FarmAnimalProductTargetExist
					String sqlFAPT= " SELECT s FROM FarmAnimalProductTargetExist s WHERE   s.animal.id =:idDelete";
					Query qFAPT = manager.createQuery(sqlFAPT, FarmAnimalProductTargetExist.class);
					qFAPT.setParameter("idDelete", animalDelete.getId());
					List<FarmAnimalProductTargetExist> fapt= qFAPT.getResultList();
					if(fapt!=null && fapt.size()>0) {
						for(FarmAnimalProductTargetExist item: fapt) {
							item.setAnimal(animalKeep);
							item= farmAnimalProductTargetExistRepository.save(item);
						}
					}
					
					//xử lí ForestProductsListDetail
					String sqlFPLT= " SELECT s FROM ForestProductsListDetail s WHERE  s.animal.id =:idDelete";
					Query qFPLT = manager.createQuery(sqlFPLT, ForestProductsListDetail.class);
					qFPLT.setParameter("idDelete", animalDelete.getId());
					List<ForestProductsListDetail> FPLT= qFPLT.getResultList();
					if(FPLT!=null && FPLT.size()>0) {
						for(ForestProductsListDetail item: FPLT) {
							item.setAnimal(animalKeep);
							item= forestProductsListDetailRepository.save(item);
						}
					}
					
					//xử lí ImportExportAnimal
					String sqlIEA= " SELECT s FROM ImportExportAnimal s WHERE  s.animal.id =:idDelete";
					Query qIEA = manager.createQuery(sqlIEA, ImportExportAnimal.class);
					qIEA.setParameter("idDelete", animalDelete.getId());
					List<ImportExportAnimal> IEA= qIEA.getResultList();
					if(IEA!=null && IEA.size()>0) {
						for(ImportExportAnimal item: IEA) {
							item.setAnimal(animalKeep);
							item= importExportAnimalRepository.save(item);
						}
					}
					
					//xử lí IndividualAnimal
					String sqlIA= " SELECT s FROM IndividualAnimal s WHERE   s.animal.id =:idDelete";
					Query qIA = manager.createQuery(sqlIA, IndividualAnimal.class);
					qIA.setParameter("idDelete", animalDelete.getId());
					List<IndividualAnimal> IA= qIA.getResultList();
					if(IA!=null && IA.size()>0) {
						for(IndividualAnimal item: IA) {
							item.setAnimal(animalKeep);
							item= individualAnimalRepository.save(item);
						}
					}
					
					//xử lí ReportFormAnimalEgg
					String sqlRFAE= " SELECT s FROM ReportFormAnimalEgg s WHERE  s.animal.id =:idDelete";
					Query qRFAE = manager.createQuery(sqlRFAE, ReportFormAnimalEgg.class);
					qRFAE.setParameter("idDelete", animalDelete.getId());
					List<ReportFormAnimalEgg> RFAE= qRFAE.getResultList();
					if(RFAE!=null && RFAE.size()>0) {
						for(ReportFormAnimalEgg item: RFAE) {
							item.setAnimal(animalKeep);
							item= reportFormAnimalEggRepository.save(item);
						}
					}
					
					//xử lí ReportFormAnimalGiveBirth
					String sqlRFAGB= " SELECT s FROM ReportFormAnimalGiveBirth s WHERE  s.animal.id =:idDelete";
					Query qRFAGB = manager.createQuery(sqlRFAGB, ReportFormAnimalGiveBirth.class);
					qRFAGB.setParameter("idDelete", animalDelete.getId());
					List<ReportFormAnimalGiveBirth> RFAGB= qRFAGB.getResultList();
					if(RFAGB!=null && RFAGB.size()>0) {
						for(ReportFormAnimalGiveBirth item: RFAGB) {
							item.setAnimal(animalKeep);
							item= reportFormAnimalGiveBirthRepository.save(item);
						}
					}
					
					animalRepository.delete(animalDelete);
					
					return new AnimalDto(animalKeep);
					
				}
				
				
			}
		}
		return null;
	}

}