package com.globits.wl.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.codehaus.jettison.json.JSONException;
import org.springframework.data.domain.Page;

import com.globits.core.service.GenericService;
import com.globits.wl.domain.ExportEgg;
import com.globits.wl.domain.Farm;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.DoubtsOverlapDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.functiondto.DensityRegionDto;
import com.globits.wl.dto.functiondto.FarmAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.FarmDuplicateDoubtsDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.functiondto.SearchDto;
import com.globits.wl.dto.report.FarmReportDto;

public interface FarmService extends GenericService<Farm, Long>{

	public Page<FarmDto> getPageDto(int pageIndex, int pageSize);

	public List<FarmDto> getAll();

	public FarmDto updateFarm(Long id, FarmDto dto);

	public FarmDto createFarm(FarmDto dto);

	public FarmDto deleteById(Long id);

	public FarmDto getById(Long id);
	public List<AnimalDto> removeListByParent(Long parentId, List<AnimalDto> list);
	public Page<FarmDto> searchFarmDto(FarmSearchDto searchDto, int pageIndex, int pageSize);
    public String autoGenericCode(String codeDistrict,Long districtId, String codeCity);
    public List<FarmDto> saveListImportFarm(List<FarmDto> listFarm);

    public List<FarmDto> searchFarmDto(FarmSearchDto searchDto);
    public FarmAdministrativeUnitDto countFarmByAdministrativeUnit(Long provinceId,int level);
    public List<FarmAdministrativeUnitDto> countFarmByListAdministrativeUnit(List<Long> provinceId,int level);
    public List<FarmAdministrativeUnitDto> countFarmListAdministrativeUnitByMapCode(String mapCode,int level);
    public List<FarmAdministrativeUnitDto> countFarmListAdministrativeUnitById(Long id,int level);
    public void countBalanceNumberAllFarm();
    public void countBalanceNumberByFarm(Long farmId);
    public DensityRegionDto countDensityRegion(Long regionId);
    public List<DensityRegionDto> countDensityListRegion();
    public DensityRegionDto countDensityMapCodeAu(String mapCode);
    public Boolean deleteFarmAnimalProductTargetExist();
    public Boolean deleteFarmProductTargetExist();
    public Boolean deleteFarmAnimalProductTargetExistByFarmId(Long farmId);
    public Boolean deleteFarmProductTargetExistByFarmId(Long farmId);

	public List<FarmDto> getFarmByUserLogin();

	public List<FarmReportDto> getSumQuantity(ReportParamDto paramDto);

	public void setCodeForAllFarm();

	public FarmDto mergeFarm(Long farmId, Long duplicateFarmId);

	public List<FarmDuplicateDoubtsDto> farmDupDoubt(String yearA, String yearB);
	public FarmDto getByIdSummary(Long id);

	public List<FarmDuplicateDoubtsDto> farmDupDoubtCommune(String yearA, String yearB);

	public void updateAllFarmInfoToMap(int pageIndex, int pageSize);

	public void updateAllFarmCreatedFarmToMap(int pageIndex, int pageSize);

	public FarmDto updateCoordinates(String farmCode, String lat, String lng)
			throws ClientProtocolException, JSONException, IOException;

	public void updateAllFarmCreatedFarmToMap(int pageIndex, int pageSize, int year, Long provinceId);

	void updateAllFarmInfoToMap(int pageIndex, int pageSize, int year, Long provinceId);

	FarmDto reGenerateFarmCode(Long farmId);

	Farm updateFarmToEntity(Long id, FarmDto dto);
	
	public Page<DoubtsOverlapDto> searchByPageDoubtsOverlap(SearchDto dto);
	
	//tran huu dat 
	public FarmDto mergeFarmNew(Long farmId, Long duplicateFarmId, FarmDto farmMergeDto);
	//tran huu dat

	
}
