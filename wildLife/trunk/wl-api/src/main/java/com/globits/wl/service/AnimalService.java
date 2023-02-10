package com.globits.wl.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.Animal;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.FarmAnimalDto;
import com.globits.wl.dto.functiondto.AnimalSearchDto;
import com.globits.wl.dto.functiondto.MergeAnimal;


public interface AnimalService  extends GenericService<Animal, Long>{

	Page<AnimalDto> getAll(int pageIndex, int pageSize);

	AnimalDto getAnimalById(Long id);

	AnimalDto createAnimal(AnimalDto dto);

	AnimalDto updateAnimal(Long id, AnimalDto dto);

	ResponseEntity<Boolean> deleteAnimal(Long id);

	ResponseEntity<Boolean> deleteAnimals(List<Long> ids);

	List<AnimalDto> getAllDto();
	List<AnimalDto> getListParent();
	List<AnimalDto> getListByParent(Long parentId);
	Page<AnimalDto> searchAnimalDto(AnimalSearchDto searchDto, int pageIndex, int pageSize);
	AnimalDto checkDuplicateCode(String code);
	List<AnimalDto> saveOrUpdateList(List<AnimalDto> listAnimal);
	AnimalDto saveOrUpdateAnimal(Long id, AnimalDto dto);

	public void saveListAnimal(List<AnimalDto> list);

	List<String> getListAnimalClass();

	List<String> getListAnimalOrdo(String animalClass);
	
	List<String> getListAnimalOrdoParam(AnimalSearchDto dto); // Search List
	
	List<String> getListAnimalOrdoSci(String animalClassSci);

	List<String> getListAnimalFamily(String animalClass, String ordo);
	
	List<String> getListAnimalFamilyParam(AnimalSearchDto dto); // Search List
	
	List<String> getListAnimalFamilySci(String animalClassSci, String ordoSci);
	
	List<Animal> getListAnimalByFamily(String family);
	
	List<String> getListAnimalClassSci();
	
	List<String> getVnList06();
	List<String> getListCites();
	List<String> getVnList();
	List<String> getListAnimalGroup();
	
	List<FarmAnimalDto> getListAnimalByFarmId(Long farmId);
	void updateGroupToAllAnimal();
	
	AnimalDto changeCode(Long id, String code);
	
	AnimalDto changeReproductionForm(Long id, Integer reproductionForm);
	
	AnimalDto mergeAnimal(MergeAnimal dto);
}
