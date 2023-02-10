package com.globits.wl.service.impl;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmReportPeriod;
import com.globits.wl.domain.IndividualAnimal;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.AnimalReportDataFormDto;
import com.globits.wl.dto.FarmReportPeriodDto;
import com.globits.wl.repository.AnimalReportDataRepository;
import com.globits.wl.repository.FarmReportPeriodRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.FarmReportPeriodService;
import com.globits.wl.utils.WLConstant;

@Service
public class FarmReportPeriodServiceImpl extends GenericServiceImpl<FarmReportPeriod, Long>
		implements FarmReportPeriodService {
	@Autowired
	private FarmReportPeriodRepository farmReportPeriodRepository;
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	AnimalReportDataService animalReportDataService;
	@Autowired
	AnimalReportDataRepository animalReportDataRepository;
	
	@Override
	public Integer saveAnimalReportData(FarmReportPeriodDto dto) {
		if(dto!=null && dto.getListAnimalReportDataFormDto()!=null &&  dto.getListAnimalReportDataFormDto().size()>0) {
			FarmReportPeriod ret = new FarmReportPeriod();
			if(dto.getFarmId()!=null && dto.getFarmId()>0L) {
				Farm f = farmRepository.findOne(dto.getFarmId());
				ret.setFarm(f);
			}
			ret.setType(dto.getType());
			ret.setYear(dto.getYear());			
			ret.setQuarter(dto.getQuarter());
			ret.setMonth(dto.getMonth());
			ret.setDay(dto.getDay());
			
			ret.setTitle(dto.getTitle());
			
			ret = farmReportPeriodRepository.save(ret);
			for (AnimalReportDataFormDto re : dto.getListAnimalReportDataFormDto()) {
				re.setFarmReportPeriodId(ret.getId());
			}
			if(ret.getType()==WLConstant.AnimalReportDataFormType.firstType.getValue()) {
				return animalReportDataService.saveListAnimalReport(dto.getListAnimalReportDataFormDto());
			}
			else if(ret.getType()==WLConstant.AnimalReportDataFormType.secondType.getValue()) {
				return animalReportDataService.saveListBearReport(dto.getListAnimalReportDataFormDto());
			}
			else if(ret.getType()==WLConstant.AnimalReportDataFormType.thirdType.getValue()) {
				return animalReportDataService.saveNormalAnimal(dto.getListAnimalReportDataFormDto());
			}
		}
		return null;
	}

	@Override
	public FarmReportPeriodDto getById(Long id, Integer type) {
		// TODO Auto-generated method stub
		FarmReportPeriodDto result = null;
		if (id != null && id > 0 && type != null) {
			FarmReportPeriod entity = farmReportPeriodRepository.getOne(id);
			if (entity != null && entity.getId() != null) {
				result = new FarmReportPeriodDto(entity, false);
				List<AnimalReportData> listAnimalReportData = animalReportDataRepository.getAllByFarmReportPeriodId(entity.getId());
				if (listAnimalReportData != null && listAnimalReportData.size() > 0) {					
					
					Hashtable<UUID, AnimalReportDataFormDto> hashMap = new Hashtable<UUID, AnimalReportDataFormDto>();
					Hashtable<UUID, AnimalReportDataFormDto> hashMapBear = new Hashtable<UUID, AnimalReportDataFormDto>();
					Hashtable<UUID, AnimalReportDataFormDto> hashMapNormal = new Hashtable<UUID, AnimalReportDataFormDto>();

					for (AnimalReportData animalReportData : listAnimalReportData) {

						if (animalReportData.getFormId() != null && animalReportData.getFormType() != null && animalReportData
								.getFormType().equals(WLConstant.AnimalReportDataFormType.firstType.getValue())) {
							
							AnimalReportDataFormDto item = hashMap.get(animalReportData.getFormId());
							if (item == null) {
								item = new AnimalReportDataFormDto();
								item.setAnimal(new AnimalDto(animalReportData.getAnimal(), false));
								item.setSource(animalReportData.getSource());
								item.setPurpose(animalReportData.getPurpose());
								item.setYear(animalReportData.getYear());
							}

							if (animalReportData.getType() == WLConstant.AnimalReportDataType.animalParent.getValue()) {// Đàn bố mẹ
								item.setMaleParent(animalReportData.getMale());
								item.setFemaleParent(animalReportData.getFemale());
								item.setUnGenderParent(animalReportData.getUnGender());
								
							} else if (animalReportData.getType() == WLConstant.AnimalReportDataType.childOver1YearOld.getValue()) {// Con non trên 1 tuổi
								item.setMaleChild(animalReportData.getMale());
								item.setFemaleChild(animalReportData.getFemale());
								item.setUnGenderChild(animalReportData.getUnGender());

							} else if (animalReportData.getType() == WLConstant.AnimalReportDataType.childUnder1YearOld.getValue()) {// Con non dưới 1 tuổi
								item.setMaleChildUnder1YO(animalReportData.getMale());
								item.setFemaleChildUnder1YO(animalReportData.getFemale());
								item.setUnGenderChildUnder1YO(animalReportData.getUnGender());
							}

							hashMap.put(animalReportData.getFormId(), item);
						}
						else if (animalReportData.getFormId() != null && animalReportData.getFormType() != null && animalReportData
								.getFormType().equals(WLConstant.AnimalReportDataFormType.secondType.getValue())) {

							
							AnimalReportDataFormDto item = hashMapBear.get(animalReportData.getFormId());
							if (item == null) {
								item = new AnimalReportDataFormDto();
								item.setAnimal(new AnimalDto(animalReportData.getAnimal(), false));
								item.setSource(animalReportData.getSource());
								item.setPurpose(animalReportData.getPurpose());
								item.setYear(animalReportData.getYear());
							}

							if (animalReportData.getType() == WLConstant.AnimalReportDataType.hasChip.getValue()) {// Đàn bố mẹ
								item.setMaleBearHasChip(animalReportData.getMale());
								item.setFemaleBearHasChip(animalReportData.getFemale());
								item.setTotalBearHasChip(animalReportData.getTotal());
								String chipCodes="";
								if(animalReportData.getIndividualAnimals()!=null && animalReportData.getIndividualAnimals().size()>0) {
									for (IndividualAnimal individualAnimal : animalReportData.getIndividualAnimals()) {
										chipCodes+=individualAnimal.getCode()+";";
									}
								}
								item.setChipCodes(chipCodes);
								
							} else if (animalReportData.getType() == WLConstant.AnimalReportDataType.noChip.getValue()) {// Con non trên 1 tuổi
								item.setMaleBearNoChip(animalReportData.getMale());
								item.setFemaleBearNoChip(animalReportData.getFemale());
								item.setTotalBearNoChip(animalReportData.getTotal());
								item.setReasonBornAtFarm(animalReportData.getReasonBornAtFarm());
								item.setReasonOther(animalReportData.getReasonOther());
							}
							
							hashMapBear.put(animalReportData.getFormId(), item);
							
						}
						else if(animalReportData.getFormId() != null 
								&& animalReportData.getFormType() != null 
								&& animalReportData.getFormType().equals(WLConstant.AnimalReportDataFormType.thirdType.getValue())) {
							
							AnimalReportDataFormDto item = hashMapNormal.get(animalReportData.getFormId());
							if (item == null) {
								item = new AnimalReportDataFormDto();
								item.setAnimal(new AnimalDto(animalReportData.getAnimal(), false));
								item.setSource(animalReportData.getSource());
								item.setPurpose(animalReportData.getPurpose());
								item.setYear(animalReportData.getYear());
								item.setTotalNormal(animalReportData.getTotal());;
							}
							hashMapNormal.put(animalReportData.getFormId(), item);
						}
						
					}
					
					if (type.equals(WLConstant.AnimalReportDataFormType.firstType.getValue())) {
						result.setListAnimalReportDataFormDto(new ArrayList<>(hashMap.values()));
					}
					else if (type.equals(WLConstant.AnimalReportDataFormType.secondType.getValue())) {
						result.setListAnimalReportDataFormDto(new ArrayList<>(hashMapBear.values()));
					}
					else if (type.equals(WLConstant.AnimalReportDataFormType.thirdType.getValue())) {
						result.setListAnimalReportDataFormDto(new ArrayList<>(hashMapNormal.values()));
					}
				}
			}
		}
		
		return result;
	}
	
	
	public List<FarmReportPeriodDto> searchList(FarmReportPeriodDto dto) {
		int type = dto.getType();
		Long farmId = dto.getFarmId();
		return farmReportPeriodRepository.getByFarmReportPeriod(farmId, type);
	}

	@Override
	public Integer updateAnimalReportData(FarmReportPeriodDto dto) {
		if(dto!=null && dto.getListAnimalReportDataFormDto()!=null &&  dto.getListAnimalReportDataFormDto().size()>0) {
			FarmReportPeriod ret = new FarmReportPeriod();
			
			ret = farmReportPeriodRepository.findOne(dto.getId());
			for (AnimalReportDataFormDto re : dto.getListAnimalReportDataFormDto()) {
				re.setFarmReportPeriodId(ret.getId());
			}
			if(ret.getType()==WLConstant.AnimalReportDataFormType.firstType.getValue()) {
				return animalReportDataService.saveListAnimalReport(dto.getListAnimalReportDataFormDto());
			}
			else if(ret.getType()==WLConstant.AnimalReportDataFormType.secondType.getValue()) {
				return animalReportDataService.saveListBearReport(dto.getListAnimalReportDataFormDto());
			}
			else if(ret.getType()==WLConstant.AnimalReportDataFormType.thirdType.getValue()) {
				return animalReportDataService.saveNormalAnimal(dto.getListAnimalReportDataFormDto());
			}
		}
		return null;
	}

}