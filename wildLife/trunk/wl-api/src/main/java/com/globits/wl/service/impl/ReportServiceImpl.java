package com.globits.wl.service.impl;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.commons.lang.time.DateUtils;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.globits.core.dto.AdministrativeUnitDto;
import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.domain.AnimalProductTarget;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.FarmProductTarget;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataDto;
import com.globits.wl.dto.AnimalReportDataFormDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.Report18Dto;
import com.globits.wl.dto.functiondto.AnimalReportDataReportDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ReportAccordingToTheRedBookDto;
import com.globits.wl.dto.functiondto.ReportImportExportTimeDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.functiondto.ReportQuantityByCategoryCitesDto;
import com.globits.wl.dto.report.EggReportDto;
import com.globits.wl.dto.report.EggSummaryReportDto;
import com.globits.wl.dto.report.EggTypeReportDto;
import com.globits.wl.dto.report.FarmSummaryReportDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.dto.report.ObjectReportDto;
import com.globits.wl.dto.report.ProductivityForecastReportDto;
import com.globits.wl.repository.AnimalReportDataRepository;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.ImportExportAnimalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.service.AnimalService;
import com.globits.wl.service.ExportEggService;
import com.globits.wl.service.FmsAdministrativeUnitService;
import com.globits.wl.service.ImportExportAnimalService;
import com.globits.wl.service.ReportService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.NumberUtils;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ReportServiceImpl extends GenericServiceImpl<ImportExportAnimal, Long> implements ReportService {

    @Autowired
    private ImportExportAnimalService importAnimalService;
    @Autowired
    private ExportEggService exportEggService;
    @Autowired
    private ImportExportAnimalRepository importExportAnimalRepository;
    @Autowired
    private AnimalRepository animalRepository;
    @Autowired
    private AnimalService animalService;
    @Autowired
    private ProductTargetRepository productTargetRepository;
    @Autowired
    AnimalReportDataRepository animalReportDataRepository;
    @Autowired
    private UserAdministrativeUnitService userAdministrativeUnitService;
    @Autowired
    private FmsAdministrativeUnitService fmsAdministrativeUnitService;

    @Override
    public List<FarmSummaryReportDto> getMeatYieldReport(ReportParamDto paramDto) throws ParseException {
        // TODO Auto-generated method stub
        List<FarmSummaryReportDto> result = new ArrayList<FarmSummaryReportDto>();
        if (paramDto != null) {
            List<InventoryReportDto> listInventoryReportDto = importAnimalService.getQuantityReport(paramDto);

            for (InventoryReportDto irDto : listInventoryReportDto) {
                FarmSummaryReportDto dto = new FarmSummaryReportDto();
                dto.setParentlName(irDto.getParentlName());
                dto.setRegionId(irDto.getRegionId());
                dto.setRegionName(irDto.getRegionName());
                dto.setProvinceId(irDto.getProvinceId());
                dto.setProvinceName(irDto.getProvinceName());
                dto.setQuantity(irDto.getQuantity());

                for (InventoryReportDto inventoryReportDto : listInventoryReportDto) {

                    if (dto.getProvinceId().equals(inventoryReportDto.getProvinceId())) {
                        dto.setTotalFarmAmount(dto.getTotalFarmAmount() + inventoryReportDto.getAmount());

                        if (inventoryReportDto.getParentlName().toLowerCase().contains("G??".toLowerCase())) {
                            dto.setChickenFarmQuantity(
                                    dto.getChickenFarmQuantity() + inventoryReportDto.getCountFarm());
                            dto.setChickenFarmAmount(dto.getChickenFarmAmount() + inventoryReportDto.getAmount());
                        } else if (inventoryReportDto.getParentlName().toLowerCase().contains("v???t".toLowerCase())) {
                            dto.setDuckFarmQuantity(dto.getDuckFarmQuantity() + inventoryReportDto.getCountFarm());
                            dto.setDuckFarmAmount(dto.getDuckFarmAmount() + inventoryReportDto.getAmount());
                        } else {
                            dto.setOtherFarmQuantity(dto.getOtherFarmQuantity() + inventoryReportDto.getCountFarm());
                            dto.setOtherFarmAmount(dto.getOtherFarmAmount() + inventoryReportDto.getAmount());
                        }
                    }

                }
                if (!checkHasDtoInListResult(result, dto)) {
                    result.add(dto);
                }
            }
        }
        return result;
    }

    private boolean checkHasDtoInListResult(List<FarmSummaryReportDto> result, FarmSummaryReportDto dto) {
        if (result != null && result.size() > 0) {
            for (FarmSummaryReportDto farmSummaryReportDto : result) {
                if (farmSummaryReportDto.getProvinceId().equals(dto.getProvinceId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * B???ng t???ng h???p tr???ng
     */
    @Override
    public List<EggSummaryReportDto> getEggSummaryReport(ReportParamDto paramDto) throws ParseException {
        List<EggSummaryReportDto> result = new ArrayList<EggSummaryReportDto>();
        if (paramDto != null) {
            // list view danh s??ch
            List<InventoryReportDto> list = new ArrayList<InventoryReportDto>();
            Hashtable<Long, EggSummaryReportDto> hashAll = new Hashtable<Long, EggSummaryReportDto>();
            if (paramDto.getIsSeedlevel() != null && paramDto.getIsSeedlevel() == false) {
                ReportParamDto paramDto1 = paramDto;
                paramDto1.setIsSeedlevel(null);// tr?????ng h???p c???p gi???ng th????ng ph???m
                List<InventoryReportDto> list1 = exportEggService.getQuantityReport(paramDto1);
                ReportParamDto paramDto2 = paramDto;
                paramDto2.setLevels(null);
                paramDto2.setIsSeedlevel(false);// tr?????ng h???p nh???p ????n
                List<InventoryReportDto> list2 = exportEggService.getQuantityReport(paramDto2);
                if (list1 != null && list1.size() > 0) {
                    list.addAll(list1);
                }
                if (list2 != null && list2.size() > 0) {
                    list.addAll(list2);
                }

            }
            if (list == null || (list != null && list.size() <= 0))
                list = exportEggService.getQuantityReport(paramDto);
            if (list != null && list.size() > 0) {
                for (InventoryReportDto irDto : list) {
                    EggSummaryReportDto dto = hashAll.get(irDto.getProvinceId());
                    if (dto == null) {
                        dto = new EggSummaryReportDto();
                        dto.setRegionId(irDto.getRegionId());
                        dto.setRegionName(irDto.getRegionName());
                        dto.setProvinceId(irDto.getProvinceId());
                        dto.setProvinceName(irDto.getProvinceName());
                        hashAll.put(irDto.getProvinceId(), dto);
                        result.add(dto);
                    }

                }
                // t??nh t???ng s??? con ????? tr???ng
                if (paramDto.getGroupByItems() != null && paramDto.getGroupByItems().size() > 0) {
                    paramDto.setGroupByItems(new ArrayList<String>());
                    paramDto.getGroupByItems().add("region");
                    paramDto.getGroupByItems().add("province");
                    paramDto.getGroupByItems().add("importEgg");
                }
                Hashtable<Long, ObjectReportDto> hashAllImportExport = new Hashtable<Long, ObjectReportDto>();
                List<InventoryReportDto> listTotal = new ArrayList<InventoryReportDto>();
                if (paramDto.getIsSeedlevel() != null && paramDto.getIsSeedlevel() == false) {
                    ReportParamDto paramDto1 = paramDto;
                    paramDto1.setIsSeedlevel(null);// tr?????ng h???p c???p gi???ng th????ng ph???m
                    List<InventoryReportDto> list1 = exportEggService.getQuantityReport(paramDto1);
                    ReportParamDto paramDto2 = paramDto;
                    paramDto2.setLevels(null);
                    paramDto2.setIsSeedlevel(false);// tr?????ng h???p nh???p ????n
                    List<InventoryReportDto> list2 = exportEggService.getQuantityReport(paramDto2);
                    if (list1 != null && list1.size() > 0) {
                        listTotal.addAll(list1);
                    }
                    if (list2 != null && list2.size() > 0) {
                        listTotal.addAll(list2);
                    }

                }
                if (listTotal == null || (listTotal != null && listTotal.size() <= 0))
                    listTotal = exportEggService.getQuantityReport(paramDto);
                if (listTotal != null && listTotal.size() > 0) {
                    for (EggSummaryReportDto dto : result) {
                        for (InventoryReportDto item : listTotal) {
                            if (item.getRegionId() != null && item.getProvinceId() != null && dto.getRegionId() != null
                                    && dto.getProvinceId() != null && dto.getRegionId().equals(item.getRegionId())
                                    && dto.getProvinceId().equals(item.getProvinceId())) {
                                // dto.setTotalEggCount(dto.getTotalEggCount()+item.getQuantity());
                                if (item.getImportExportAnimal() != null
                                        && item.getImportExportAnimal().getId() != null) {
                                    ObjectReportDto oDto = hashAllImportExport
                                            .get(item.getImportExportAnimal().getId());
                                    if (oDto == null) {
                                        oDto = new ObjectReportDto();
                                        oDto.setId(item.getImportExportAnimal().getId());
                                        hashAllImportExport.put(item.getImportExportAnimal().getId(), oDto);
                                        ImportExportAnimal iea = importExportAnimalRepository
                                                .findById(item.getImportExportAnimal().getId());
                                        if (iea != null) {
                                            dto.setTotalEggCount(dto.getTotalEggCount() + iea.getQuantity());
                                        }
                                    }

                                }
                            }
                        }
                    }

                }

                // ??o???n x??? l?? ph???n t??nh s??? qu??? tr???ng g??,v???t, kh??c
                for (EggSummaryReportDto dto : result) {
                    List<ObjectReportDto> reportCoes = new ArrayList<ObjectReportDto>();
                    List<ObjectReportDto> eggTypes = new ArrayList<ObjectReportDto>();
                    Hashtable<String, ObjectReportDto> hashEgg = new Hashtable<String, ObjectReportDto>();
                    Hashtable<Integer, ObjectReportDto> hashEggType = new Hashtable<Integer, ObjectReportDto>();
                    for (InventoryReportDto item : list) {
                        if (item.getReportCode() != null) {
                            ObjectReportDto i = hashEgg.get(item.getReportCode());
                            if (i == null) {
                                i = new ObjectReportDto();
                                i.setCode(item.getReportCode());
                                i.setName(item.getReportName());
                                reportCoes.add(i);
                                hashEgg.put(item.getReportCode(), i);
                            }
                        }
                        if (item.getEggType() != null) {
                            ObjectReportDto iType = hashEggType.get(item.getEggType());
                            if (iType == null) {
                                iType = new ObjectReportDto();
                                iType.setCode(item.getEggType() + "");
                                if (item.getEggType() != null
                                        && item.getEggType().equals(WLConstant.EggType.commodity.getValue())) {
                                    iType.setName("Th????ng ph???m");
                                } else {
                                    iType.setName("Tr???ng gi???ng");
                                }
                                iType.setType(item.getEggType());
                                hashEggType.put(item.getEggType(), iType);
                                eggTypes.add(iType);
                            }
                        }

                    }
                    dto.setReportCoes(reportCoes);
                    dto.setEggTypes(eggTypes);
                }
                for (EggSummaryReportDto dto : result) {
                    List<EggReportDto> eggs = new ArrayList<EggReportDto>();
                    if (dto.getReportCoes() != null && dto.getReportCoes().size() > 0) {
                        for (ObjectReportDto op : dto.getReportCoes()) {
                            EggReportDto view = new EggReportDto();
                            view.setCode(op.getCode());
                            view.setName(op.getName());
                            eggs.add(view);
                        }
                    }
                    dto.setEggs(eggs);
                }

                for (EggSummaryReportDto dto : result) {
                    if (dto.getEggs() != null && dto.getEggs().size() > 0) {
                        for (EggReportDto eDto : dto.getEggs()) {
                            List<EggTypeReportDto> eggTypes = new ArrayList<EggTypeReportDto>();
                            Hashtable<Integer, EggTypeReportDto> hashAllEggType = new Hashtable<Integer, EggTypeReportDto>();
                            for (InventoryReportDto item : list) {
                                if (dto.getRegionId() != null && item.getRegionId() != null
                                        && dto.getRegionId().equals(item.getRegionId()) && dto.getProvinceId() != null
                                        && item.getProvinceId() != null
                                        && dto.getProvinceId().equals(item.getProvinceId()) && eDto.getCode() != null
                                        && item.getReportCode() != null
                                        && eDto.getCode().equals(item.getReportCode())) {
                                    EggTypeReportDto view = hashAllEggType.get(item.getEggType());
                                    if (view == null) {
                                        view = new EggTypeReportDto();
                                        view.setId(1L);
                                        view.setCode(item.getEggType() + "");
                                        view.setValue(item.getEggType());
                                        view.setName(item.getEggTypeName());
                                        view.setQuantity(item.getQuantity());
                                        hashAllEggType.put(item.getEggType(), view);
                                        eggTypes.add(view);
                                    } else {
                                        for (EggTypeReportDto eggTypeReportDto : eggTypes) {
                                            if (item.getEggType() != null
                                                    && eggTypeReportDto.getValue() == item.getEggType()) {
                                                double q = eggTypeReportDto.getQuantity() + item.getQuantity();
                                                eggTypeReportDto.setQuantity(q);
                                            }
                                        }

                                    }

                                }
                            }
                            eDto.setEggTypeDto(eggTypes);
                        }
                    }

                }

            }

        }
        return result;
    }

    public List<InventoryReportDto> getQuantityReport(ReportParamDto paramDto) throws ParseException {
        if (paramDto != null) {
            List<InventoryReportDto> ret = new ArrayList<InventoryReportDto>();
            List<InventoryReportDto> subRet = new ArrayList<InventoryReportDto>();
            if (paramDto.getReportType() == WLConstant.ReportType.list.getValue()) {
                if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.years.getValue()) {
                    for (int i = paramDto.getFromYear(); i <= paramDto.getToYear(); i++) {
                        Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                        if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                            fromDate = WLDateTimeUtil.numberToDate(1, 1, i);
                        }
                        Date toDate = WLDateTimeUtil.numberToDate(31, 12, i);
                        paramDto.setFromDate(fromDate);
                        paramDto.setToDate(toDate);
                        subRet = this.getSumQuantity(paramDto);
                        // subRet = this.searchDto(paramDto);
                        if (subRet != null) {
                            for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                                importExportAnimalDto.setYearReport(i);
                            }
                            ret.addAll(subRet);
                        }
                    }
                } else if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.months.getValue()) {
                    for (int i = paramDto.getFromMonth(); i <= paramDto.getToMonth(); i++) {
                        Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                        if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                            fromDate = WLDateTimeUtil.numberToDate(1, i, paramDto.getCurrentYear());
                        }
                        Date toDate = WLDateTimeUtil.getLastDayOfMonth(i, paramDto.getCurrentYear());
                        paramDto.setFromDate(fromDate);
                        paramDto.setToDate(toDate);
                        subRet = this.getSumQuantity(paramDto);
                        // subRet = this.searchDto(paramDto);
                        if (subRet != null) {
                            for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                                importExportAnimalDto.setYearReport(paramDto.getCurrentYear());
                                importExportAnimalDto.setMonthReport(i);
                            }
                            ret.addAll(subRet);
                        }
                    }
                } else if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.fromToMonth.getValue()) {
                    Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getFromMonth(), paramDto.getFromYear());
                    }
                    Date toDate = WLDateTimeUtil.numberToDate(31, paramDto.getToMonth(), paramDto.getToYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantity(paramDto);
                    // subRet = this.searchDto(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getFromYear());
                            importExportAnimalDto.setMonthReport(paramDto.getFromMonth());
                            importExportAnimalDto.setToMonthReport(paramDto.getToMonth());
                            importExportAnimalDto.setToYearReport(paramDto.getToYear());
                        }
                        ret.addAll(subRet);
                    }

                }
            } else if (paramDto.getReportType() == WLConstant.ReportType.compare.getValue()) {
                if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.years.getValue()) {
                    Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, 1, paramDto.getFromYear());
                    }

                    Date toDate = WLDateTimeUtil.numberToDate(31, 12, paramDto.getFromYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantity(paramDto);
                    // subRet = this.searchDto(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getFromYear());
                        }
                        ret.addAll(subRet);
                    }

                    fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, 1, paramDto.getToYear());
                    }
                    toDate = WLDateTimeUtil.numberToDate(31, 12, paramDto.getToYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantity(paramDto);
                    // subRet = this.searchDto(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getToYear());
                        }
                        ret.addAll(subRet);
                    }
                } else if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.months.getValue()) {
                    Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getFromMonth(), paramDto.getFromYear());
                    }
                    Date toDate = WLDateTimeUtil.getLastDayOfMonth(paramDto.getFromMonth(), paramDto.getFromYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantity(paramDto);
                    // subRet = this.searchDto(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getFromYear());
                            importExportAnimalDto.setMonthReport(paramDto.getFromMonth());
                        }
                        ret.addAll(subRet);
                    }

                    fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getToMonth(), paramDto.getToYear());
                    }
                    toDate = WLDateTimeUtil.getLastDayOfMonth(paramDto.getToMonth(), paramDto.getToYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantity(paramDto);
                    // subRet = this.searchDto(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getToYear());
                            importExportAnimalDto.setMonthReport(paramDto.getToMonth());
                        }
                        ret.addAll(subRet);
                    }
                }
            }
            return ret;
        }
        return null;
    }

    public List<InventoryReportDto> searchDto(ReportParamDto searchDto) {
        List<ImportExportAnimalDto> ret = new ArrayList<ImportExportAnimalDto>();

        String sql = " select new com.globits.wl.dto.ImportExportAnimalDto(fa) from ImportExportAnimal fa  where (1=1)";

        String whereClause = "";

        if (searchDto.getFarmId() != null) {
            whereClause += " and (fa.farm.id= :farmId)";
        }
        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            whereClause += " and (fa.farm.administrativeUnit.id= :wardsId)";
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            whereClause += " and (fa.farm.administrativeUnit.parent.id= :districtId)";
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            whereClause += " and (fa.farm.administrativeUnit.parent.parent.id= :cityId)";
        }
        if (searchDto.getFromDate() != null) {
            whereClause += " AND fa.dateIssue >= :fromDate ";
        }
        if (searchDto.getToDate() != null) {
            whereClause += " AND fa.dateIssue<= :toDate ";
        }

        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            whereClause += " AND fa.animal.parent.id = :animalParentId ";
        }

        whereClause += " and (fa.type= :type)";

        sql += whereClause;

        Query q = manager.createQuery(sql, ImportExportAnimalDto.class);

        if (searchDto.getFarmId() != null) {
            q.setParameter("farmId", searchDto.getFarmId());
        }
        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            q.setParameter("wardsId", searchDto.getWardId());
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            q.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            q.setParameter("cityId", searchDto.getProvinceId());
        }
        if (searchDto.getFromDate() != null) {
            q.setParameter("fromDate", searchDto.getFromDate());
        }
        if (searchDto.getToDate() != null) {
            q.setParameter("toDate", searchDto.getToDate());
        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            q.setParameter("animalParentId", searchDto.getAnimalParentId());
        }
        q.setParameter("type", searchDto.getType());

        ret = q.getResultList();
        List<InventoryReportDto> list = new ArrayList<InventoryReportDto>();
        if (ret != null && ret.size() > 0) {
            for (ImportExportAnimalDto dto : ret) {
                InventoryReportDto item = new InventoryReportDto();
                item.setId(dto.getId());
                item.setBatchCode(dto.getBatchCode());
                item.setQuantity(dto.getQuantity());
                item.setRemainQuantity(dto.getRemainQuantity());
                if (dto.getAnimal() != null && dto.getAnimal().getParent() != null) {
                    item.setParentId(dto.getAnimal().getParent().getId());
                    item.setParentlName(dto.getAnimal().getParent().getName());
                }
                item.setType(dto.getType());
                list.add(item);
            }

        }
        return list;
    }

    public List<InventoryReportDto> getSumQuantity(ReportParamDto paramDto) throws ParseException {

        String sql = " SELECT ";
        if (paramDto.getType() != null
                && (paramDto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()
                || paramDto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue())) {
            sql += "SUM(iea.quantity),SUM(iea.amount),SUM((iea.dayOld + DATEDIFF(day,(iea.dateIssue),(:toDate)))) %s ";// T??nh
            // t???ng
            // xu???t
            // ho???c
            // nh???p
            // ????n
        } else {
            sql += "SUM(iea.quantity * iea.type),SUM(iea.amount),SUM((iea.dayOld + DATEDIFF(day,(iea.dateIssue),(:toDate)))) %s ";// T??nh
            // t???n
            // kho
        }
        sql += "FROM ImportExportAnimal iea WHERE 1=1 ";

        String whereClause = "";
        if (paramDto.getFromDate() != null) {
            whereClause += " AND iea.dateIssue >= :fromDate ";
        }
        if (paramDto.getToDate() != null) {
            whereClause += " AND iea.dateIssue<= :toDate ";
        }
        if (paramDto.getBatchCode() != null && paramDto.getBatchCode() != "") {
            whereClause += " AND iea.batchCode = :batchCode ";
        }
        if (paramDto.getListAnimalIds() != null && paramDto.getListAnimalIds().size() > 0) {
            whereClause += " AND iea.animal.id in (:listAnimalIds) ";
        }
        if (paramDto.getAnimalTypeId() != null && paramDto.getAnimalTypeId() > 0) {
            whereClause += " AND iea.animal.type.id = :animalTypeId ";
        }
        if (paramDto.getAnimalParentId() != null && paramDto.getAnimalParentId() > 0) {
            whereClause += " AND iea.animal.parent.id = :animalParentId ";
        }
        if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
            whereClause += " AND iea.farm.id = :farmId ";
        }
        if (paramDto.getProductTargetId() != null && paramDto.getProductTargetId() > 0) {
            whereClause += " AND iea.productTarget.id = :productTargetId ";
        }
        if (paramDto.getOriginalId() != null && paramDto.getOriginalId() > 0) {
            whereClause += " AND iea.original.id = :originalId ";
        }
        if (paramDto.getWardId() != null && paramDto.getWardId() > 0) {
            whereClause += " AND iea.farm.administrativeUnit.id = :wardId ";
        }
        if (paramDto.getDistrictId() != null && paramDto.getDistrictId() > 0) {
            whereClause += " AND iea.farm.administrativeUnit.parent.id = :districtId ";
        }
        if (paramDto.getProvinceId() != null && paramDto.getProvinceId() > 0) {
            whereClause += " AND iea.farm.administrativeUnit.parent.parent.id = :provinceId ";
        }
        if (paramDto.getRegionId() != null && paramDto.getRegionId() > 0) {
            whereClause += " AND iea.farm.administrativeUnit.parent.parent.region.id = :regionId ";
        }
        if (paramDto.getExportReason() != null && paramDto.getExportReason().length() > 0) {
            whereClause += " AND iea.exportReason = :exportReason ";
        }
        if (paramDto.getExportType() > 0) {
            whereClause += " AND iea.exportType = :exportType ";
        }
        if (paramDto.getType() != null
                && (paramDto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()
                || paramDto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue())) {
            whereClause += " AND iea.type = :type ";
        }
        if (paramDto.getOwnershipId() != null && paramDto.getOwnershipId() > 0L) {
            whereClause += " AND iea.farm.ownership.id = :ownershipId ";
        }
        // if(paramDto.getNumberExceptedDate()>0) {
        // whereClause+=" AND iea.lifeCycle >=:lifeCycle ";
        // }
        if (paramDto.getToDate() != null) {
            // whereClause+=" AND (iea.dateIssue + 7) >=:toDate1 ";
            whereClause += " AND DATEADD(DAY,iea.lifeCycle,iea.dateIssue)  >=:toDate1 ";
        }

        String animal = " ";
        String animalParent = " ";
        String animalType = " ";
        String batchCode = " ";
        String farm = " ";
        String original = " ";
        String productTarget = " ";
        String ward = " ";
        String district = " ";
        String province = " ";
        String region = " ";
        String exportReason = " ";
        String ownership = " ";

        String orderByClause = "";

        List<String> columns = new ArrayList<String>();
        columns.add(WLConstant.FunctionAndGroupByItem.quantity.getValue());
        columns.add(WLConstant.FunctionAndGroupByItem.amount.getValue());
        columns.add(WLConstant.FunctionAndGroupByItem.dayOld.getValue());
        // columns.add(FMSConstant.FunctionAndGroupByItem.dayOld.getValue());

        String groupByClause = "";
        String selectClause = "";
        if (paramDto.getGroupByItems() != null && paramDto.getGroupByItems().size() > 0) {

            for (String grItem : paramDto.getGroupByItems()) {
                if (WLConstant.FunctionAndGroupByItem.animal.getValue().equals(grItem)) {
                    groupByClause += " iea.animal.id,iea.animal.name,iea.animal.weightGain,iea.animal.farmingTime,iea.animal.loss, ";
                    animal = " ,iea.animal.id,iea.animal.name,iea.animal.weightGain,iea.animal.farmingTime,iea.animal.loss  ";
                    selectClause += animal;
                    columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue() + "name");
                    columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue() + "weightGain");
                    columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue() + "farmingTime");
                    columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue() + "loss");
                }
                if (WLConstant.FunctionAndGroupByItem.animalParent.getValue().equals(grItem)) {
                    groupByClause += " iea.animal.parent.id,iea.animal.parent.name, ";
                    animalParent = " ,iea.animal.parent.id,iea.animal.parent.name ";
                    selectClause += animalParent;
                    columns.add(WLConstant.FunctionAndGroupByItem.animalParent.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.animalParent.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.animalType.getValue().equals(grItem)) {
                    groupByClause += " iea.animal.type.id,iea.animal.type.name, ";
                    animalType = " ,iea.animal.type.id,iea.animal.type.name ";
                    selectClause += animalType;
                    columns.add(WLConstant.FunctionAndGroupByItem.animalType.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.animalType.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.batchCode.getValue().equals(grItem)) {
                    groupByClause += " iea.batchCode, ";
                    batchCode = " ,iea.batchCode ";
                    selectClause += batchCode;
                    columns.add(WLConstant.FunctionAndGroupByItem.batchCode.getValue());
                }
                if (WLConstant.FunctionAndGroupByItem.farm.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.id,iea.farm.name, ";
                    farm = " ,iea.farm.id,iea.farm.name ";
                    selectClause += farm;
                    columns.add(WLConstant.FunctionAndGroupByItem.farm.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.farm.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.original.getValue().equals(grItem)) {
                    groupByClause += " iea.original.id,iea.original.name, ";
                    original = " ,iea.original.id,iea.original.name ";
                    selectClause += original;
                    columns.add(WLConstant.FunctionAndGroupByItem.original.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.original.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.productTarget.getValue().equals(grItem)) {
                    groupByClause += " iea.productTarget.id,iea.productTarget.name, ";
                    productTarget = " ,iea.productTarget.id,iea.productTarget.name ";
                    selectClause += productTarget;
                    columns.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.ward.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.administrativeUnit.id,iea.farm.administrativeUnit.name, ";
                    ward = " ,iea.farm.administrativeUnit.id,iea.farm.administrativeUnit.name ";
                    selectClause += ward;
                    columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.district.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.administrativeUnit.parent.id,iea.farm.administrativeUnit.parent.name, ";
                    district = " ,iea.farm.administrativeUnit.parent.id,iea.farm.administrativeUnit.parent.name ";
                    selectClause += district;
                    columns.add(WLConstant.FunctionAndGroupByItem.district.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.district.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.province.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.administrativeUnit.parent.parent.id,iea.farm.administrativeUnit.parent.parent.name, ";
                    province = " ,iea.farm.administrativeUnit.parent.parent.id,iea.farm.administrativeUnit.parent.parent.name ";
                    selectClause += province;
                    columns.add(WLConstant.FunctionAndGroupByItem.province.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.province.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.region.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.administrativeUnit.parent.parent.region.id,iea.farm.administrativeUnit.parent.parent.region.name, ";
                    region = " ,iea.farm.administrativeUnit.parent.parent.region.id,iea.farm.administrativeUnit.parent.parent.region.name ";
                    selectClause += region;
                    columns.add(WLConstant.FunctionAndGroupByItem.region.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.region.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.exportReason.getValue().equals(grItem)) {
                    groupByClause += " iea.exportReason, ";
                    exportReason = " ,iea.exportReason ";
                    selectClause += exportReason;
                    columns.add(WLConstant.FunctionAndGroupByItem.exportReason.getValue());
                }
                if (WLConstant.FunctionAndGroupByItem.ownership.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.ownership.id,iea.farm.ownership.name, ";
                    region = " ,iea.farm.ownership.id,iea.farm.ownership.name ";
                    selectClause += region;
                    columns.add(WLConstant.FunctionAndGroupByItem.ownership.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.ownership.getValue() + "name");
                }
            }
        }
        sql = String.format(sql, selectClause);
        if (groupByClause != "") {
            groupByClause = " GROUP BY " + groupByClause.substring(0, groupByClause.length() - 2);
        }

        sql = sql + whereClause + groupByClause;
        System.out.println(sql);
        Query q = manager.createQuery(sql);

        if (paramDto.getFromDate() != null) {
            q.setParameter("fromDate", paramDto.getFromDate());
        }
        if (paramDto.getToDate() != null) {
            q.setParameter("toDate", paramDto.getToDate());
        }
        if (paramDto.getBatchCode() != null && paramDto.getBatchCode() != "") {
            q.setParameter("batchCode", paramDto.getBatchCode());
        }
        if (paramDto.getListAnimalIds() != null && paramDto.getListAnimalIds().size() > 0) {
            q.setParameter("listAnimalIds", paramDto.getListAnimalIds());
        }
        if (paramDto.getAnimalTypeId() != null && paramDto.getAnimalTypeId() > 0) {
            q.setParameter("animalTypeId", paramDto.getAnimalTypeId());
        }
        if (paramDto.getAnimalParentId() != null && paramDto.getAnimalParentId() > 0) {
            q.setParameter("animalParentId", paramDto.getAnimalParentId());
        }
        if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
            q.setParameter("farmId", paramDto.getFarmId());
        }
        if (paramDto.getProductTargetId() != null && paramDto.getProductTargetId() > 0) {
            q.setParameter("productTargetId", paramDto.getProductTargetId());
        }
        if (paramDto.getOriginalId() != null && paramDto.getOriginalId() > 0) {
            q.setParameter("originalId", paramDto.getOriginalId());
        }
        if (paramDto.getWardId() != null && paramDto.getWardId() > 0) {
            q.setParameter("wardId", paramDto.getWardId());
        }
        if (paramDto.getDistrictId() != null && paramDto.getDistrictId() > 0) {
            q.setParameter("districtId", paramDto.getDistrictId());
        }
        if (paramDto.getProvinceId() != null && paramDto.getProvinceId() > 0) {
            q.setParameter("provinceId", paramDto.getProvinceId());
        }
        if (paramDto.getRegionId() != null && paramDto.getRegionId() > 0) {
            q.setParameter("regionId", paramDto.getRegionId());
        }
        if (paramDto.getExportReason() != null && paramDto.getExportReason().length() > 0) {
            q.setParameter("exportReason", paramDto.getExportReason());
        }
        if (paramDto.getExportType() > 0) {
            q.setParameter("exportType", paramDto.getExportType());
        }
        if (paramDto.getType() != null
                && (paramDto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()
                || paramDto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue())) {
            q.setParameter("type", paramDto.getType());
        }
        if (paramDto.getOwnershipId() != null && paramDto.getOwnershipId() > 0L) {
            q.setParameter("ownershipId", paramDto.getOwnershipId());
        }
        if (paramDto.getToDate() != null) {
            q.setParameter("toDate1", paramDto.getToDate());
        }
        // if(paramDto.getNumberExceptedDate()>0) {
        // q.setParameter("lifeCycle", paramDto.getNumberExceptedDate());
        // }

        List<Object[]> results = q.getResultList();
        List<InventoryReportDto> ret = new ArrayList<InventoryReportDto>();

        if (results != null) {
            for (Object[] re : results) {
                if (paramDto.getIsSumTotal() != null && paramDto.getIsSumTotal()) {
                    if (re != null && re[0] != null) {
                        InventoryReportDto io = new InventoryReportDto(re, columns);
                        ret.add(io);
                    }

                } else {
                    InventoryReportDto io = new InventoryReportDto(re, columns);
                    ret.add(io);
                }

            }
        }
        return ret;
    }

    /**
     * H??m d??? b??o n??ng su???t tr???ng theo lo???i v???t nu??i - v??ng
     */
    @Override
    public List<ProductivityForecastReportDto> getEggProductivityForecastReportDto(ReportParamDto paramDto)
            throws ParseException {
        List<ProductivityForecastReportDto> ret = new ArrayList<ProductivityForecastReportDto>();
        List<ProductivityForecastReportDto> ret1 = new ArrayList<ProductivityForecastReportDto>();
        // l???y danh s??ch c??c phi???u nh???p trong kho???n th???i gian b??o c??o
        long day = 0;
        List<InventoryReportDto> list = this.getQuantityReportEgg(paramDto);
        if (paramDto.getFromDate() != null && paramDto.getToDate() != null) {
            day = ChronoUnit.DAYS.between(paramDto.getFromDate().toInstant(), paramDto.getToDate().toInstant());

        }
        ReportParamDto paramDto1 = paramDto;
        if (paramDto1.getGroupByItems() != null && paramDto1.getGroupByItems().size() > 0) {
            List<String> animalParenst = new ArrayList<String>();
            for (String item : paramDto1.getGroupByItems()) {
                if (item.equals(WLConstant.FunctionAndGroupByItem.animal.getValue())) {
                    animalParenst.add(item);
                }
            }
            paramDto1.getGroupByItems().removeAll(animalParenst);
        }

        List<InventoryReportDto> list1 = this.getQuantityReportEgg(paramDto);
        if (list1 != null && list1.size() > 0) {
            for (InventoryReportDto inventoryReportDto : list1) {
                ProductivityForecastReportDto item = new ProductivityForecastReportDto();
                item.setParentId(inventoryReportDto.getParentId());
                item.setParentlName(inventoryReportDto.getParentlName());
                item.setRegionId(inventoryReportDto.getRegionId());
                item.setRegionName(inventoryReportDto.getRegionName());
                ret1.add(item);
            }
        }
        // nh??m c??c lo??i v???t nu??i v??o 1 ?????i t?????ng

        if (list != null && list.size() > 0 && ret1 != null && ret1.size() > 0) {
            for (ProductivityForecastReportDto re : ret1) {
                for (InventoryReportDto dto : list) {
                    if (re.getParentId() != null && re.getRegionId() != null && dto.getRegionId() != null
                            && dto.getParentId() != null && re.getParentId().equals(dto.getParentId())
                            && re.getRegionId().equals(dto.getRegionId())) {
                        double eggYield = (double) dto.getEggYield() / 365;
                        double quantityMeat = eggYield * day * dto.getQuantity();
                        quantityMeat = re.getQuantityEgg() + quantityMeat;
                        re.setQuantityEgg(quantityMeat);
                    }
                }
            }

        }
        // //query t???t c??? c??c lo???i
        if (paramDto.getGroupByItems() != null && paramDto.getGroupByItems().size() > 0) {
            List<String> animalParenst = new ArrayList<String>();
            for (String item : paramDto.getGroupByItems()) {
                if (item.equals(WLConstant.FunctionAndGroupByItem.animalParent.getValue())) {
                    animalParenst.add(item);
                }
            }
            paramDto.getGroupByItems().removeAll(animalParenst);
        }
        // paramDto.setGroupByItems(new ArrayList<String>());
        paramDto.getGroupByItems().add("animalId");
        // paramDto.getGroupByItems().add("region");
        paramDto.setIsSumTotal(true);
        List<InventoryReportDto> lst = this.getQuantityReportEgg(paramDto);
        if (lst != null && lst.size() > 0) {
            Hashtable<Long, ProductivityForecastReportDto> hashAllRegion = new Hashtable<Long, ProductivityForecastReportDto>();
            for (InventoryReportDto dto : lst) {
                ProductivityForecastReportDto item = hashAllRegion.get(dto.getRegionId());
                if (item == null) {
                    item = new ProductivityForecastReportDto();
                    // item.setParentId(dto.getParentId());
                    item.setParentlName("T???t c??? c??c gi???ng");
                    // item.setQuantity(dto.getQuantity());
                    item.setRegionId(dto.getRegionId());
                    item.setRegionName(dto.getRegionName());
                    double quantityMeat = (double) dto.getEggYield() / 365 * day * dto.getQuantity();
                    quantityMeat = item.getQuantityEgg() + quantityMeat;
                    item.setQuantityEgg(quantityMeat);
                    ret.add(item);
                    hashAllRegion.put(dto.getRegionId(), item);
                } else {
                    for (ProductivityForecastReportDto dto1 : ret) {
                        if (dto1.getRegionId() != null && dto.getRegionId() != null
                                && dto1.getRegionId().equals(dto.getRegionId())) {
                            // dto1.setQuantity(dto1.getQuantity()+dto.getQuantity());//s??? l?????ng
                            // dto1.setRemainQuantity(dto1.getRemainQuantity()+dto.getRemainQuantity());
                            // dto1.setNumberOfDayRasied(dto1.getNumberOfDayRasied()+dto.getNumberOfDayRasied());//s???
                            // ng??y ???? nu??i
                            double quantityMeat = (double) dto.getEggYield() / 365 * day * dto.getQuantity();
                            quantityMeat = dto1.getQuantityEgg() + quantityMeat;
                            dto1.setQuantityEgg(quantityMeat);
                        }
                    }
                }

            }
        }

        if (ret1 != null && ret1.size() > 0) {
            ret.addAll(ret1);
        }
        return ret;
    }

    public List<InventoryReportDto> getSumQuantityEgg(ReportParamDto paramDto) throws ParseException {

        String sql = " SELECT ";
        if (paramDto.getType() != null
                && (paramDto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()
                || paramDto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue())) {
            sql += "SUM(iea.quantity),SUM(iea.amount) %s ";// T??nh t???ng xu???t ho???c nh???p ????n
        } else {
            sql += "SUM(iea.quantity * iea.type),SUM(iea.amount) %s ";// T??nh t???n kho
        }
        sql += "FROM ImportExportAnimal iea WHERE 1=1 ";

        String whereClause = "";
        if (paramDto.getFromDate() != null) {
            whereClause += " AND iea.dateIssue >= :fromDate ";
        }
        if (paramDto.getToDate() != null) {
            whereClause += " AND iea.dateIssue<= :toDate ";
        }
        if (paramDto.getBatchCode() != null && paramDto.getBatchCode() != "") {
            whereClause += " AND iea.batchCode = :batchCode ";
        }
        if (paramDto.getListAnimalIds() != null && paramDto.getListAnimalIds().size() > 0) {
            whereClause += " AND iea.animal.id in (:listAnimalIds) ";
        }
        if (paramDto.getAnimalTypeId() != null && paramDto.getAnimalTypeId() > 0) {
            whereClause += " AND iea.animal.type.id = :animalTypeId ";
        }
        if (paramDto.getAnimalParentId() != null && paramDto.getAnimalParentId() > 0) {
            whereClause += " AND iea.animal.parent.id = :animalParentId ";
        }
        if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
            whereClause += " AND iea.farm.id = :farmId ";
        }
        if (paramDto.getProductTargetId() != null && paramDto.getProductTargetId() > 0) {
            whereClause += " AND iea.productTarget.id = :productTargetId ";
        }
        if (paramDto.getOriginalId() != null && paramDto.getOriginalId() > 0) {
            whereClause += " AND iea.original.id = :originalId ";
        }
        if (paramDto.getWardId() != null && paramDto.getWardId() > 0) {
            whereClause += " AND iea.farm.administrativeUnit.id = :wardId ";
        }
        if (paramDto.getDistrictId() != null && paramDto.getDistrictId() > 0) {
            whereClause += " AND iea.farm.administrativeUnit.parent.id = :districtId ";
        }
        if (paramDto.getProvinceId() != null && paramDto.getProvinceId() > 0) {
            whereClause += " AND iea.farm.administrativeUnit.parent.parent.id = :provinceId ";
        }
        if (paramDto.getRegionId() != null && paramDto.getRegionId() > 0) {
            whereClause += " AND iea.farm.administrativeUnit.parent.parent.region.id = :regionId ";
        }
        if (paramDto.getExportReason() != null && paramDto.getExportReason().length() > 0) {
            whereClause += " AND iea.exportReason = :exportReason ";
        }
        if (paramDto.getExportType() > 0) {
            whereClause += " AND iea.exportType = :exportType ";
        }
        if (paramDto.getType() != null
                && (paramDto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()
                || paramDto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue())) {
            whereClause += " AND iea.type = :type ";
        }
        if (paramDto.getOwnershipId() != null && paramDto.getOwnershipId() > 0L) {
            whereClause += " AND iea.farm.ownership.id = :ownershipId ";
        }

        if (paramDto.getToDate() != null) {
            // whereClause+=" AND (iea.dateIssue + 7) >=:toDate1 ";
            whereClause += " AND DATEADD(DAY,iea.lifeCycle,iea.dateIssue)  >=:toDate1 ";
        }

        String animal = " ";
        String animalParent = " ";
        String animalType = " ";
        String batchCode = " ";
        String farm = " ";
        String original = " ";
        String productTarget = " ";
        String ward = " ";
        String district = " ";
        String province = " ";
        String region = " ";
        String exportReason = " ";
        String ownership = " ";

        String orderByClause = "";

        List<String> columns = new ArrayList<String>();
        columns.add(WLConstant.FunctionAndGroupByItem.quantity.getValue());
        columns.add(WLConstant.FunctionAndGroupByItem.amount.getValue());

        String groupByClause = "";
        String selectClause = "";
        if (paramDto.getGroupByItems() != null && paramDto.getGroupByItems().size() > 0) {

            for (String grItem : paramDto.getGroupByItems()) {
                if (WLConstant.FunctionAndGroupByItem.animal.getValue().equals(grItem)) {
                    groupByClause += " iea.animal.id,iea.animal.name,iea.animal.eggYield, ";
                    animal = " ,iea.animal.id,iea.animal.name,iea.animal.eggYield  ";
                    selectClause += animal;
                    columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue() + "name");
                    columns.add(WLConstant.FunctionAndGroupByItem.animal.getValue() + "eggYield");

                }
                if (WLConstant.FunctionAndGroupByItem.animalParent.getValue().equals(grItem)) {
                    groupByClause += " iea.animal.parent.id,iea.animal.parent.name, ";
                    animalParent = " ,iea.animal.parent.id,iea.animal.parent.name ";
                    selectClause += animalParent;
                    columns.add(WLConstant.FunctionAndGroupByItem.animalParent.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.animalParent.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.animalType.getValue().equals(grItem)) {
                    groupByClause += " iea.animal.type.id,iea.animal.type.name, ";
                    animalType = " ,iea.animal.type.id,iea.animal.type.name ";
                    selectClause += animalType;
                    columns.add(WLConstant.FunctionAndGroupByItem.animalType.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.animalType.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.batchCode.getValue().equals(grItem)) {
                    groupByClause += " iea.batchCode, ";
                    batchCode = " ,iea.batchCode ";
                    selectClause += batchCode;
                    columns.add(WLConstant.FunctionAndGroupByItem.batchCode.getValue());
                }
                if (WLConstant.FunctionAndGroupByItem.farm.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.id,iea.farm.name, ";
                    farm = " ,iea.farm.id,iea.farm.name ";
                    selectClause += farm;
                    columns.add(WLConstant.FunctionAndGroupByItem.farm.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.farm.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.original.getValue().equals(grItem)) {
                    groupByClause += " iea.original.id,iea.original.name, ";
                    original = " ,iea.original.id,iea.original.name ";
                    selectClause += original;
                    columns.add(WLConstant.FunctionAndGroupByItem.original.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.original.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.productTarget.getValue().equals(grItem)) {
                    groupByClause += " iea.productTarget.id,iea.productTarget.name, ";
                    productTarget = " ,iea.productTarget.id,iea.productTarget.name ";
                    selectClause += productTarget;
                    columns.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.productTarget.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.ward.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.administrativeUnit.id,iea.farm.administrativeUnit.name, ";
                    ward = " ,iea.farm.administrativeUnit.id,iea.farm.administrativeUnit.name ";
                    selectClause += ward;
                    columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.district.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.administrativeUnit.parent.id,iea.farm.administrativeUnit.parent.name, ";
                    district = " ,iea.farm.administrativeUnit.parent.id,iea.farm.administrativeUnit.parent.name ";
                    selectClause += district;
                    columns.add(WLConstant.FunctionAndGroupByItem.district.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.district.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.province.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.administrativeUnit.parent.parent.id,iea.farm.administrativeUnit.parent.parent.name, ";
                    province = " ,iea.farm.administrativeUnit.parent.parent.id,iea.farm.administrativeUnit.parent.parent.name ";
                    selectClause += province;
                    columns.add(WLConstant.FunctionAndGroupByItem.province.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.province.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.region.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.administrativeUnit.parent.parent.region.id,iea.farm.administrativeUnit.parent.parent.region.name, ";
                    region = " ,iea.farm.administrativeUnit.parent.parent.region.id,iea.farm.administrativeUnit.parent.parent.region.name ";
                    selectClause += region;
                    columns.add(WLConstant.FunctionAndGroupByItem.region.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.region.getValue() + "name");
                }
                if (WLConstant.FunctionAndGroupByItem.exportReason.getValue().equals(grItem)) {
                    groupByClause += " iea.exportReason, ";
                    exportReason = " ,iea.exportReason ";
                    selectClause += exportReason;
                    columns.add(WLConstant.FunctionAndGroupByItem.exportReason.getValue());
                }
                if (WLConstant.FunctionAndGroupByItem.ownership.getValue().equals(grItem)) {
                    groupByClause += " iea.farm.ownership.id,iea.farm.ownership.name, ";
                    region = " ,iea.farm.ownership.id,iea.farm.ownership.name ";
                    selectClause += region;
                    columns.add(WLConstant.FunctionAndGroupByItem.ownership.getValue() + "id");
                    columns.add(WLConstant.FunctionAndGroupByItem.ownership.getValue() + "name");
                }
            }
        }
        sql = String.format(sql, selectClause);
        if (groupByClause != "") {
            groupByClause = " GROUP BY " + groupByClause.substring(0, groupByClause.length() - 2);
        }

        sql = sql + whereClause + groupByClause;
        System.out.println(sql);
        Query q = manager.createQuery(sql);

        if (paramDto.getFromDate() != null) {
            q.setParameter("fromDate", paramDto.getFromDate());
        }
        if (paramDto.getToDate() != null) {
            q.setParameter("toDate", paramDto.getToDate());
        }
        if (paramDto.getBatchCode() != null && paramDto.getBatchCode() != "") {
            q.setParameter("batchCode", paramDto.getBatchCode());
        }
        if (paramDto.getListAnimalIds() != null && paramDto.getListAnimalIds().size() > 0) {
            q.setParameter("listAnimalIds", paramDto.getListAnimalIds());
        }
        if (paramDto.getAnimalTypeId() != null && paramDto.getAnimalTypeId() > 0) {
            q.setParameter("animalTypeId", paramDto.getAnimalTypeId());
        }
        if (paramDto.getAnimalParentId() != null && paramDto.getAnimalParentId() > 0) {
            q.setParameter("animalParentId", paramDto.getAnimalParentId());
        }
        if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
            q.setParameter("farmId", paramDto.getFarmId());
        }
        if (paramDto.getProductTargetId() != null && paramDto.getProductTargetId() > 0) {
            q.setParameter("productTargetId", paramDto.getProductTargetId());
        }
        if (paramDto.getOriginalId() != null && paramDto.getOriginalId() > 0) {
            q.setParameter("originalId", paramDto.getOriginalId());
        }
        if (paramDto.getWardId() != null && paramDto.getWardId() > 0) {
            q.setParameter("wardId", paramDto.getWardId());
        }
        if (paramDto.getDistrictId() != null && paramDto.getDistrictId() > 0) {
            q.setParameter("districtId", paramDto.getDistrictId());
        }
        if (paramDto.getProvinceId() != null && paramDto.getProvinceId() > 0) {
            q.setParameter("provinceId", paramDto.getProvinceId());
        }
        if (paramDto.getRegionId() != null && paramDto.getRegionId() > 0) {
            q.setParameter("regionId", paramDto.getRegionId());
        }
        if (paramDto.getExportReason() != null && paramDto.getExportReason().length() > 0) {
            q.setParameter("exportReason", paramDto.getExportReason());
        }
        if (paramDto.getExportType() > 0) {
            q.setParameter("exportType", paramDto.getExportType());
        }
        if (paramDto.getType() != null
                && (paramDto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()
                || paramDto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue())) {
            q.setParameter("type", paramDto.getType());
        }
        if (paramDto.getOwnershipId() != null && paramDto.getOwnershipId() > 0L) {
            q.setParameter("ownershipId", paramDto.getOwnershipId());
        }
        if (paramDto.getToDate() != null) {
            q.setParameter("toDate1", paramDto.getToDate());
        }

        List<Object[]> results = q.getResultList();
        List<InventoryReportDto> ret = new ArrayList<InventoryReportDto>();

        if (results != null) {
            for (Object[] re : results) {
                if (paramDto.getIsSumTotal() != null && paramDto.getIsSumTotal()) {
                    if (re != null && re[0] != null) {
                        InventoryReportDto io = new InventoryReportDto(re, columns);
                        ret.add(io);
                    }

                } else {
                    InventoryReportDto io = new InventoryReportDto(re, columns);
                    ret.add(io);
                }

            }
        }
        return ret;
    }

    public List<InventoryReportDto> getQuantityReportEgg(ReportParamDto paramDto) throws ParseException {
        if (paramDto != null) {
            List<InventoryReportDto> ret = new ArrayList<InventoryReportDto>();
            List<InventoryReportDto> subRet = new ArrayList<InventoryReportDto>();
            if (paramDto.getReportType() == WLConstant.ReportType.list.getValue()) {
                if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.years.getValue()) {
                    for (int i = paramDto.getFromYear(); i <= paramDto.getToYear(); i++) {
                        Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                        if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                            fromDate = WLDateTimeUtil.numberToDate(1, 1, i);
                        }
                        Date toDate = WLDateTimeUtil.numberToDate(31, 12, i);
                        paramDto.setFromDate(fromDate);
                        paramDto.setToDate(toDate);
                        subRet = this.getSumQuantityEgg(paramDto);
                        // subRet = this.searchDto(paramDto);
                        if (subRet != null) {
                            for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                                importExportAnimalDto.setYearReport(i);
                            }
                            ret.addAll(subRet);
                        }
                    }
                } else if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.months.getValue()) {
                    for (int i = paramDto.getFromMonth(); i <= paramDto.getToMonth(); i++) {
                        Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                        if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                            fromDate = WLDateTimeUtil.numberToDate(1, i, paramDto.getCurrentYear());
                        }
                        Date toDate = WLDateTimeUtil.getLastDayOfMonth(i, paramDto.getCurrentYear());
                        paramDto.setFromDate(fromDate);
                        paramDto.setToDate(toDate);
                        subRet = this.getSumQuantityEgg(paramDto);
                        if (subRet != null) {
                            for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                                importExportAnimalDto.setYearReport(paramDto.getCurrentYear());
                                importExportAnimalDto.setMonthReport(i);
                            }
                            ret.addAll(subRet);
                        }
                    }
                } else if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.fromToMonth.getValue()) {
                    Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getFromMonth(), paramDto.getFromYear());
                    }
                    Date toDate = WLDateTimeUtil.numberToDate(31, paramDto.getToMonth(), paramDto.getToYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantityEgg(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getFromYear());
                            importExportAnimalDto.setMonthReport(paramDto.getFromMonth());
                            importExportAnimalDto.setToMonthReport(paramDto.getToMonth());
                            importExportAnimalDto.setToYearReport(paramDto.getToYear());
                        }
                        ret.addAll(subRet);
                    }

                }
            } else if (paramDto.getReportType() == WLConstant.ReportType.compare.getValue()) {
                if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.years.getValue()) {
                    Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, 1, paramDto.getFromYear());
                    }

                    Date toDate = WLDateTimeUtil.numberToDate(31, 12, paramDto.getFromYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantityEgg(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getFromYear());
                        }
                        ret.addAll(subRet);
                    }

                    fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, 1, paramDto.getToYear());
                    }
                    toDate = WLDateTimeUtil.numberToDate(31, 12, paramDto.getToYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantityEgg(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getToYear());
                        }
                        ret.addAll(subRet);
                    }
                } else if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.months.getValue()) {
                    Date fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getFromMonth(), paramDto.getFromYear());
                    }
                    Date toDate = WLDateTimeUtil.getLastDayOfMonth(paramDto.getFromMonth(), paramDto.getFromYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantityEgg(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getFromYear());
                            importExportAnimalDto.setMonthReport(paramDto.getFromMonth());
                        }
                        ret.addAll(subRet);
                    }

                    fromDate = WLDateTimeUtil.numberToDate(1, 1, 1970);
                    if (paramDto.getStartTimeType() == WLConstant.StartTimeType.aMomentInTime.getValue()) {
                        fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getToMonth(), paramDto.getToYear());
                    }
                    toDate = WLDateTimeUtil.getLastDayOfMonth(paramDto.getToMonth(), paramDto.getToYear());
                    paramDto.setFromDate(fromDate);
                    paramDto.setToDate(toDate);
                    subRet = this.getSumQuantityEgg(paramDto);
                    if (subRet != null) {
                        for (ImportExportAnimalDto importExportAnimalDto : subRet) {
                            importExportAnimalDto.setYearReport(paramDto.getToYear());
                            importExportAnimalDto.setMonthReport(paramDto.getToMonth());
                        }
                        ret.addAll(subRet);
                    }
                }
            }
            return ret;
        }
        return null;
    }

    /**
     * Chi ti???t v??? d??? b??o n??ng su???t tr???ng c???a c??c gi???ng v???t nu??i.
     */
    @Override
    public List<ProductivityForecastReportDto> getEggDetailProductivityForecastReportDto(ReportParamDto paramDto)
            throws ParseException {
        List<ProductivityForecastReportDto> ret = new ArrayList<ProductivityForecastReportDto>();

        // l???y danh s??ch c??c phi???u nh???p trong kho???n th???i gian b??o c??o
        long day = 0;
        List<InventoryReportDto> list = this.getQuantityReportEgg(paramDto);
        if (paramDto.getFromDate() != null && paramDto.getToDate() != null) {
            day = ChronoUnit.DAYS.between(paramDto.getFromDate().toInstant(), paramDto.getToDate().toInstant());
        }

        if (list != null && list.size() > 0) {
            for (InventoryReportDto inventoryReportDto : list) {
                ProductivityForecastReportDto item = new ProductivityForecastReportDto();
                item.setParentId(inventoryReportDto.getParentId());
                item.setParentlName(inventoryReportDto.getParentlName());
                item.setRegionId(inventoryReportDto.getRegionId());
                item.setRegionName(inventoryReportDto.getRegionName());
                item.setAnimalId(inventoryReportDto.getAnimalId());
                item.setAnimalName(inventoryReportDto.getAnimalName());
                item.setEggYield(inventoryReportDto.getEggYield());
                item.setQuantity(inventoryReportDto.getQuantity());
                double eggYield = (double) inventoryReportDto.getEggYield() / 365;
                double quantityMeat = eggYield * day * inventoryReportDto.getQuantity();
                item.setQuantityEgg(quantityMeat);
                ret.add(item);
            }
        }
        return ret;
    }

    /**
     * Chi ti???t v??? d??? b??o n??ng su???t th???t c???a c??c gi???ng v???t nu??i.
     */
    @Override
    public List<ProductivityForecastReportDto> getMeatDetailProductivityForecastReportDto(ReportParamDto paramDto)
            throws ParseException {
        List<ProductivityForecastReportDto> ret = new ArrayList<ProductivityForecastReportDto>();
        List<InventoryReportDto> list = this.getQuantityReport(paramDto);

        if (list != null && list.size() > 0) {
            for (InventoryReportDto inventoryReportDto : list) {
                ProductivityForecastReportDto item = new ProductivityForecastReportDto();
                item.setParentId(inventoryReportDto.getParentId());
                item.setParentlName(inventoryReportDto.getParentlName());
                item.setRegionId(inventoryReportDto.getRegionId());
                item.setRegionName(inventoryReportDto.getRegionName());
                item.setAnimalId(inventoryReportDto.getAnimalId());
                item.setAnimalName(inventoryReportDto.getAnimalName());
                item.setQuantity(inventoryReportDto.getQuantity());
                item.setWeightGain(inventoryReportDto.getWeightGain());
                item.setFarmingTime(inventoryReportDto.getFarmingTime());
                item.setLoss(inventoryReportDto.getLoss());
                item.setNumberOfDayRasied(inventoryReportDto.getNumberOfDayRasied());
                double quantityMeat = inventoryReportDto.getQuantity()
                        * (inventoryReportDto.getWeightGain() / inventoryReportDto.getFarmingTime())
                        * inventoryReportDto.getNumberOfDayRasied() * (100 - inventoryReportDto.getLoss()) / 100;
                item.setQuantityMeat(quantityMeat);
                ret.add(item);
            }
        }

        return ret;
    }

    @Override
    public List<ProductivityForecastReportDto> getMeatProductivityForecastReportDto(ReportParamDto paramDto)
            throws ParseException {
        NumberFormat formatter = new DecimalFormat("#0.00");
        List<ProductivityForecastReportDto> ret = new ArrayList<ProductivityForecastReportDto>();

        List<ProductTarget> listProductTarget = productTargetRepository
                .findByCode(WLConstant.ProductTargetCode.MEAT.getValue());
        if (listProductTarget == null || listProductTarget.size() <= 0) {
            return null;
        }
        paramDto.setProductTargetId(listProductTarget.get(0).getId());
        List<ImportExportAnimalDto> listImportExportAnimalDto = null;
        // l???y danh s??ch c??c l???n nh???p ????n c??n t???n kho trong kho???ng th???i gian b??o c??o
        paramDto.setRemainingQuantityGreaterThanZero(true);
        listImportExportAnimalDto = this.getlistImportAnimalByReportParamDto(paramDto);

        if (listImportExportAnimalDto == null || listImportExportAnimalDto.size() <= 0) {
            return null;
        }

        Hashtable<Long, Hashtable<Long, ProductivityForecastReportDto>> hashAllAnimalParentInRegion = new Hashtable<Long, Hashtable<Long, ProductivityForecastReportDto>>();
        for (ImportExportAnimalDto importExportAnimalDto : listImportExportAnimalDto) {
            Long regionId = null;
            Long parentId = null;
            if (importExportAnimalDto.getRegion() != null && importExportAnimalDto.getRegion().getId() != null) {
                regionId = importExportAnimalDto.getRegion().getId();
            }
            if (importExportAnimalDto.getAnimal() != null && importExportAnimalDto.getAnimal().getParent() != null
                    && importExportAnimalDto.getAnimal().getParent().getId() != null) {
                parentId = importExportAnimalDto.getAnimal().getParent().getId();
            }

            if (regionId != null && parentId != null) {
                Double totalQuantityMeat = 0.0;
                // t??nh chu k???
                Double periods = this.calculatePeriodsBy(importExportAnimalDto, paramDto);
                if (periods != null) {
                    Double quantityMeat = (periods * importExportAnimalDto.getRemainQuantity()
                            * importExportAnimalDto.getAnimal().getWeightGain()
                            * (100 - importExportAnimalDto.getAnimal().getLoss())) / 100;

                    totalQuantityMeat = NumberUtils.round(quantityMeat, 2);
                }

                Hashtable<Long, ProductivityForecastReportDto> animalParentInRegion = hashAllAnimalParentInRegion
                        .get(parentId);
                if (animalParentInRegion == null) {

                    Hashtable<Long, ProductivityForecastReportDto> newAnimalParentInRegion = new Hashtable<Long, ProductivityForecastReportDto>();

                    ProductivityForecastReportDto item = new ProductivityForecastReportDto();
                    item.setParentId(parentId);
                    item.setParentlName(importExportAnimalDto.getAnimal().getParent().getName());
                    item.setRegionId(regionId);
                    item.setRegionName(importExportAnimalDto.getRegion().getName());
                    item.setQuantityMeat(totalQuantityMeat);

                    ret.add(item);
                    newAnimalParentInRegion.put(regionId, item);
                    hashAllAnimalParentInRegion.put(parentId, newAnimalParentInRegion);
                } else {

                    ProductivityForecastReportDto item = animalParentInRegion.get(regionId);
                    if (item == null) {

                        item = new ProductivityForecastReportDto();
                        item.setParentId(parentId);
                        item.setParentlName(importExportAnimalDto.getAnimal().getParent().getName());
                        item.setRegionId(regionId);
                        item.setRegionName(importExportAnimalDto.getRegion().getName());
                        item.setQuantityMeat(totalQuantityMeat);

                        ret.add(item);
                        animalParentInRegion.put(regionId, item);
                        hashAllAnimalParentInRegion.put(parentId, animalParentInRegion);
                    } else {
                        for (ProductivityForecastReportDto dto1 : ret) {
                            if (dto1.getRegionId() != null && dto1.getRegionId().equals(regionId)
                                    && dto1.getParentId() != null && dto1.getParentId().equals(parentId)) {
                                dto1.setQuantityMeat(NumberUtils.round(dto1.getQuantityMeat() + totalQuantityMeat, 2));
                            }
                        }
                    }

                }
            }

        }

        return ret;
    }

    private Double calculatePeriodsBy(ImportExportAnimalDto importExportAnimalDto, ReportParamDto paramDto)
            throws ParseException {
        // C??ng th???c t??nh chu k???
        // Th???i gian b???t ?????u 1 chu k??? ch??nh l?? th???i gian nh???p ????n (DateIssue) trong b???ng
        // importExportAnimal v???i type = 1
        // Th???i gian c???a chu k??? ch??nh l?? th???i gian nu??i (FarmingTime) trong b???ng animal
        // Th???i gian tu??n th??? gi??n c??ch - t???m th???i hard code trong Constant
        // 1 chu k??? = th???i gian b???t ?????u + th???i gian c???a chu k???
        // Mu???n b???t ?????u 1 chu k??? m???i c???n ph???i + th??m th???i gian tu??n th??? gi??n c??ch

        if (importExportAnimalDto.getDateIssue() != null) {
            Date fromDate = WLDateTimeUtil.getStartOfDay(importExportAnimalDto.getDateIssue());
            Date toDate = null; // th???i gian cu???i c???a k??? d??? b??o
            Double periods = 0.0;
            if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.years.getValue()) {
                toDate = WLDateTimeUtil.getEndOfDay(WLDateTimeUtil.numberToDate(31, 12, paramDto.getCurrentYear()));
            } else if (paramDto.getPeriodType() == WLConstant.ReportPeriodType.months.getValue()) {
                toDate = WLDateTimeUtil.getLastDayOfMonth(paramDto.getCurrentMonth(), paramDto.getCurrentYear());
            }

            if (fromDate != null && toDate != null && importExportAnimalDto.getAnimal() != null
                    && importExportAnimalDto.getAnimal().getFarmingTime() > 0) {
                double farmingTime = importExportAnimalDto.getAnimal().getFarmingTime();
                // t??nh chu k???
                // C???ng th??m th???i gian tu??n th??? gi??n c??ch ????? b???t ?????u chu k??? m???i sau m???i v??ng for
                for (Date i = fromDate; i.before(toDate); i = DateUtils.addDays(i, WLConstant.COMPLIANCE_SPACED_TIME)) {
                    i = WLDateTimeUtil.getStartOfDay(DateUtils.addDays(i, (int) farmingTime));

                    // N???u th???i gian i + th???i gian nu??i v???n nh??? h??n th???i gian k???t th??c k??? d??? b??o th??
                    // chu k??? s??? + 1
                    if (i.before(toDate)) {
                        periods = periods + 1;
                    }
                    // N???u th???i gian i + th???i gian nu??i m?? l???n h??n th???i gian k???t th??c k??? d??? b??o th??
                    // chu k??? s??? t??nh theo c??ng th???c
                    // ( 1 * (S??? ng??y nu??i ???????c c???a chu k??? / t???ng s??? ng??y c???a 1 chu k???) * 100 ) /
                    // 100
                    else if (i.after(toDate)) {
                        long diff = i.getTime() - toDate.getTime();
                        int days = (int) (diff / (1000 * 60 * 60 * 24));

                        int daysInPeriods = (int) farmingTime - days;
                        if (daysInPeriods > 0) {
                            double c = (1 * (daysInPeriods / importExportAnimalDto.getAnimal().getFarmingTime()) * 100)
                                    / 100;
                            periods = periods + c;
                        } else {
                            periods = periods + 1;
                        }
                    }

                }
                return periods;
            }
        }
        return null;
    }

    private List<ImportExportAnimalDto> getlistImportAnimalByReportParamDto(ReportParamDto searchDto) {
        List<ImportExportAnimalDto> ret = new ArrayList<ImportExportAnimalDto>();
        String sql = " select new com.globits.wl.dto.ImportExportAnimalDto(fa) from ImportExportAnimal fa  where (1=1)";

        String whereClause = "";

        if (searchDto.getFarmId() != null) {
            whereClause += " and (fa.farm.id= :farmId)";
        }
        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            whereClause += " and (fa.farm.administrativeUnit.id= :wardsId)";
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            whereClause += " and (fa.farm.administrativeUnit.parent.id= :districtId)";
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            whereClause += " and (fa.farm.administrativeUnit.parent.parent.id= :cityId)";
        }
        if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
            whereClause += " and (fa.farm.administrativeUnit.parent.parent.region.id= :regionId)";
        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            whereClause += " AND fa.animal.parent.id = :animalParentId ";
        }
        if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
            whereClause += " AND fa.productTarget.id = :productTargetId ";
        }

        whereClause += " and (fa.type= :type)";

        sql += whereClause;

        Query q = manager.createQuery(sql, ImportExportAnimalDto.class);

        if (searchDto.getFarmId() != null) {
            q.setParameter("farmId", searchDto.getFarmId());
        }
        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            q.setParameter("wardsId", searchDto.getWardId());
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            q.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            q.setParameter("cityId", searchDto.getProvinceId());
        }
        if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
            q.setParameter("regionId", searchDto.getRegionId());
        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            q.setParameter("animalParentId", searchDto.getAnimalParentId());
        }
        if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
            q.setParameter("productTargetId", searchDto.getProductTargetId());
        }
        q.setParameter("type", WLConstant.ImportExportAnimalType.importAnimal.getValue());

        return ret = q.getResultList();
    }

    @Override
    public List<ProductivityForecastReportDto> getPredictTheNumberOfLiveMeatSlightly(ReportParamDto paramDto)
            throws ParseException {
        List<ProductivityForecastReportDto> ret = new ArrayList<ProductivityForecastReportDto>();

        List<ProductTarget> listProductTarget = productTargetRepository
                .findByCode(WLConstant.ProductTargetCode.MEAT.getValue());
        if (listProductTarget == null || listProductTarget.size() <= 0) {
            return null;
        }
        paramDto.setProductTargetId(listProductTarget.get(0).getId());
        List<ImportExportAnimalDto> listImportExportAnimalDto = null;
        // l???y danh s??ch c??c l???n nh???p ????n c??n t???n kho trong kho???ng th???i gian b??o c??o
        paramDto.setRemainingQuantityGreaterThanZero(true);
        listImportExportAnimalDto = this.getlistImportAnimalByReportParamDto(paramDto);

        if (listImportExportAnimalDto == null || listImportExportAnimalDto.size() <= 0) {
            return null;
        }

        Hashtable<Long, Hashtable<Long, ProductivityForecastReportDto>> hashAllAnimalParentInRegion = new Hashtable<Long, Hashtable<Long, ProductivityForecastReportDto>>();
        for (ImportExportAnimalDto importExportAnimalDto : listImportExportAnimalDto) {
            Long regionId = null;
            Long parentId = null;
            if (importExportAnimalDto.getRegion() != null && importExportAnimalDto.getRegion().getId() != null) {
                regionId = importExportAnimalDto.getRegion().getId();
            }
            if (importExportAnimalDto.getAnimal() != null && importExportAnimalDto.getAnimal().getParent() != null
                    && importExportAnimalDto.getAnimal().getParent().getId() != null) {
                parentId = importExportAnimalDto.getAnimal().getParent().getId();
            }

            if (regionId != null && parentId != null) {
                Double totalQuantityChildren = 0.0;
                // t??nh chu k???
                Double periods = this.calculatePeriodsBy(importExportAnimalDto, paramDto);
                if (periods != null) {
                    Double quantityChildren = (periods * importExportAnimalDto.getRemainQuantity()
                            * (100 - importExportAnimalDto.getAnimal().getLoss())) / 100;

                    totalQuantityChildren = quantityChildren;
                }

                Hashtable<Long, ProductivityForecastReportDto> animalParentInRegion = hashAllAnimalParentInRegion
                        .get(parentId);
                if (animalParentInRegion == null) {

                    Hashtable<Long, ProductivityForecastReportDto> newAnimalParentInRegion = new Hashtable<Long, ProductivityForecastReportDto>();

                    ProductivityForecastReportDto item = new ProductivityForecastReportDto();
                    item.setParentId(parentId);
                    item.setParentlName(importExportAnimalDto.getAnimal().getParent().getName());
                    item.setRegionId(regionId);
                    item.setRegionName(importExportAnimalDto.getRegion().getName());
                    item.setQuantityChildren(totalQuantityChildren.intValue());
                    item.setMonthReport(paramDto.getCurrentMonth());
                    item.setYearReport(paramDto.getCurrentYear());
                    ret.add(item);
                    newAnimalParentInRegion.put(regionId, item);
                    hashAllAnimalParentInRegion.put(parentId, newAnimalParentInRegion);
                } else {

                    ProductivityForecastReportDto item = animalParentInRegion.get(regionId);
                    if (item == null) {

                        item = new ProductivityForecastReportDto();
                        item.setParentId(parentId);
                        item.setParentlName(importExportAnimalDto.getAnimal().getParent().getName());
                        item.setRegionId(regionId);
                        item.setRegionName(importExportAnimalDto.getRegion().getName());
                        item.setQuantityChildren(totalQuantityChildren.intValue());
                        item.setMonthReport(paramDto.getCurrentMonth());
                        item.setYearReport(paramDto.getCurrentYear());
                        ret.add(item);
                        animalParentInRegion.put(regionId, item);
                        hashAllAnimalParentInRegion.put(parentId, animalParentInRegion);
                    } else {
                        for (ProductivityForecastReportDto dto1 : ret) {
                            if (dto1.getRegionId() != null && dto1.getRegionId().equals(regionId)
                                    && dto1.getParentId() != null && dto1.getParentId().equals(parentId)) {
                                dto1.setQuantityChildren(dto1.getQuantityChildren() + totalQuantityChildren.intValue());
                            }
                        }
                    }

                }
            }

        }

        return ret;
    }

    @Override
    public List<ReportAccordingToTheRedBookDto> reportNumberAnimalsAndNumberFarmsAccordingToTheRedBook(
            ReportParamDto searchDto) {

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

        List<ReportAccordingToTheRedBookDto> ret = new ArrayList<ReportAccordingToTheRedBookDto>();
        List<ReportAccordingToTheRedBookDto> retSum = new ArrayList<ReportAccordingToTheRedBookDto>();
        String sql = " SELECT new com.globits.wl.dto.functiondto.ReportAccordingToTheRedBookDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), a.vnlist06, ard.year) from AnimalReportData ard, "
                + " Animal a WHERE a.id = ard.animal.id AND ard.year is not null AND ard.month is null AND ard.day is null AND ard.total>0 ";
        String sqlSum = " SELECT new com.globits.wl.dto.functiondto.ReportAccordingToTheRedBookDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), '', ard.year) from AnimalReportData ard, "
                + " Animal a WHERE a.id = ard.animal.id AND ard.year is not null AND ard.month is null AND ard.day is null AND ard.total>0 ";
        String whereClause = "";
        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            whereClause += " and (ard.farm.administrativeUnit.id= :wardsId)";
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            whereClause += " and (ard.farm.administrativeUnit.parent.id= :districtId)";
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            whereClause += " and (ard.farm.administrativeUnit.parent.parent.id= :cityId)";
        }
        if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
            whereClause += " and (ard.farm.administrativeUnit.parent.parent.region.id= :regionId)";
        }
        if (!isAdmin) {
            whereClause += " and (ard.farm.administrativeUnit.id in (:listWardId)) ";
        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            whereClause += " AND ard.animal.parent.id = :animalParentId ";
        }
        if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
            whereClause += " AND ard.productTarget.id = :productTargetId ";
        }

        if (searchDto.getListAnimalIds() != null && searchDto.getListAnimalIds().size() > 0) {
            whereClause += " AND ard.animal.id in (:listAnimalIds) ";
        }
        if (searchDto.getCurrentYear() > 0) {
            whereClause += " AND ard.year = :currentYear ";
        }
        if (StringUtils.hasText(searchDto.getAnimalClass())) {
            whereClause += " AND ard.animal.animalClass = :animalClass ";
        }
        //List animalClass
        if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
            whereClause += " AND ard.animal.animalClass in (:animalClass) ";
        }
        //
        if (StringUtils.hasText(searchDto.getOrdo())) {
            whereClause += " AND ard.animal.ordo = :ordo ";
        }
        //List OrdoAnimal
        if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
            whereClause += " AND ard.animal.ordo in (:ordo) ";
        }
        //
        if (StringUtils.hasText(searchDto.getFamily())) {
            whereClause += " AND ard.animal.family = :family ";
        }
        //List FamilyAnimal
        if (searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
            whereClause += " AND ard.animal.family in (:family) ";
        }
        //
        sql += whereClause;
        sqlSum += whereClause;
        Query q = manager.createQuery(sql + " GROUP BY a.vnlist06, ard.year", ReportAccordingToTheRedBookDto.class);
        Query qSum = manager.createQuery(sqlSum + " GROUP BY ard.year", ReportAccordingToTheRedBookDto.class);
        if (searchDto.getFarmId() != null) {
            q.setParameter("farmId", searchDto.getFarmId());
            qSum.setParameter("farmId", searchDto.getFarmId());
        }
        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            q.setParameter("wardsId", searchDto.getWardId());
            qSum.setParameter("wardsId", searchDto.getWardId());
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            q.setParameter("districtId", searchDto.getDistrictId());
            qSum.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            q.setParameter("cityId", searchDto.getProvinceId());
            qSum.setParameter("cityId", searchDto.getProvinceId());
        }
        if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
            q.setParameter("regionId", searchDto.getRegionId());
            qSum.setParameter("regionId", searchDto.getRegionId());
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
            qSum.setParameter("listWardId", listWardId);
        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            q.setParameter("animalParentId", searchDto.getAnimalParentId());
            qSum.setParameter("animalParentId", searchDto.getAnimalParentId());
        }
        if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
            q.setParameter("productTargetId", searchDto.getProductTargetId());
            qSum.setParameter("productTargetId", searchDto.getProductTargetId());
        }
        if (searchDto.getListAnimalIds() != null && searchDto.getListAnimalIds().size() > 0) {
            q.setParameter("listAnimalIds", searchDto.getListAnimalIds());
            qSum.setParameter("listAnimalIds", searchDto.getListAnimalIds());
        }
        if (searchDto.getCurrentYear() > 0) {
            q.setParameter("currentYear", searchDto.getCurrentYear());
            qSum.setParameter("currentYear", searchDto.getCurrentYear());
        }
        if (StringUtils.hasText(searchDto.getAnimalClass())) {
            q.setParameter("animalClass", searchDto.getAnimalClass());
            qSum.setParameter("animalClass", searchDto.getAnimalClass());
        }
        if (StringUtils.hasText(searchDto.getOrdo())) {
            q.setParameter("ordo", searchDto.getOrdo());
            qSum.setParameter("ordo", searchDto.getOrdo());
        }
        if (StringUtils.hasText(searchDto.getFamily())) {
            q.setParameter("family", searchDto.getFamily());
            qSum.setParameter("family", searchDto.getFamily());
        }
        //list animal class
        if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
            q.setParameter("animalClass", searchDto.getListAnimalClass());
            qSum.setParameter("animalClass", searchDto.getListAnimalClass());
        }
        //list ordo
        if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
            q.setParameter("ordo", searchDto.getListAnimalOrdo());
            qSum.setParameter("ordo", searchDto.getListAnimalOrdo());
        }
        //list family animal
        if (searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
            q.setParameter("family", searchDto.getListAnimalFamily());
            qSum.setParameter("family", searchDto.getListAnimalFamily());
        }
        ret = q.getResultList();
        retSum = qSum.getResultList();
        if (ret != null && ret.size() > 0) {
            for (ReportAccordingToTheRedBookDto reportAccordingToTheRedBookDto : ret) {
                if (reportAccordingToTheRedBookDto.getVnList06() == null
                        || !StringUtils.hasText(reportAccordingToTheRedBookDto.getVnList06())) {
                    reportAccordingToTheRedBookDto.setVnList06("Kh??ng x??c ?????nh");
                }
            }
        }
        if (retSum != null && retSum.size() > 0) {
            for (ReportAccordingToTheRedBookDto sumDto : retSum) {
                sumDto.setVnList06("* T???ng");
                ret.add(sumDto);
            }
        }
        return ret;
    }

    @Override
    public List<ReportQuantityByCategoryCitesDto> reportNumberAnimalsAndNumberFarmsCategoryCitest(ReportParamDto searchDto) {

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

        List<ReportQuantityByCategoryCitesDto> ret = new ArrayList<ReportQuantityByCategoryCitesDto>();
        List<ReportQuantityByCategoryCitesDto> retSum = new ArrayList<ReportQuantityByCategoryCitesDto>();

        String sql = " SELECT new com.globits.wl.dto.functiondto.ReportQuantityByCategoryCitesDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), a.cites, ard.year) from AnimalReportData ard, "
                + " Animal a WHERE a.id = ard.animal.id AND ard.year is not null AND ard.month is null AND ard.day is null AND ard.total>0 ";
        String sqlSum = " SELECT new com.globits.wl.dto.functiondto.ReportQuantityByCategoryCitesDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), '', ard.year) from AnimalReportData ard, "
                + " Animal a WHERE a.id = ard.animal.id AND ard.year is not null AND ard.month is null AND ard.day is null AND ard.total>0 ";
        String whereClause = "";

        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            whereClause += " and (ard.farm.administrativeUnit.id= :wardsId)";
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            whereClause += " and (ard.farm.administrativeUnit.parent.id= :districtId)";
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            whereClause += " and (ard.farm.administrativeUnit.parent.parent.id= :cityId)";
        }
        if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
            whereClause += " and (ard.farm.administrativeUnit.parent.parent.region.id= :regionId)";
        }
        if (!isAdmin) {
            whereClause += " and (ard.farm.administrativeUnit.id in (:listWardId)) ";
        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            whereClause += " AND ard.animal.parent.id = :animalParentId ";
        }
        if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
            whereClause += " AND ard.productTarget.id = :productTargetId ";
        }
        String groupByClause = " GROUP BY a.cites, ard.year ";
        sql += whereClause;
        sqlSum += whereClause;
        Query q = manager.createQuery(sql + groupByClause, ReportQuantityByCategoryCitesDto.class);
        Query qSum = manager.createQuery(sqlSum + "GROUP BY ard.year", ReportQuantityByCategoryCitesDto.class);

        if (searchDto.getFarmId() != null) {
            q.setParameter("farmId", searchDto.getFarmId());
            qSum.setParameter("farmId", searchDto.getFarmId());
        }
        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            q.setParameter("wardsId", searchDto.getWardId());
            qSum.setParameter("wardsId", searchDto.getWardId());
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            q.setParameter("districtId", searchDto.getDistrictId());
            qSum.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            q.setParameter("cityId", searchDto.getProvinceId());
            qSum.setParameter("cityId", searchDto.getProvinceId());
        }
        if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
            q.setParameter("regionId", searchDto.getRegionId());
            qSum.setParameter("regionId", searchDto.getRegionId());
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
            qSum.setParameter("listWardId", listWardId);
        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            q.setParameter("animalParentId", searchDto.getAnimalParentId());
            qSum.setParameter("animalParentId", searchDto.getAnimalParentId());
        }
        if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
            q.setParameter("productTargetId", searchDto.getProductTargetId());
            qSum.setParameter("productTargetId", searchDto.getProductTargetId());
        }
        ret = q.getResultList();
        retSum = qSum.getResultList();
        if (ret != null && ret.size() > 0) {
            for (ReportQuantityByCategoryCitesDto reportQuantityByCategoryCitesDto : ret) {
                if (reportQuantityByCategoryCitesDto.getCites() == null
                        || !StringUtils.hasText(reportQuantityByCategoryCitesDto.getCites())) {
                    reportQuantityByCategoryCitesDto.setCites("Kh??ng x??c ?????nh");
                }
            }
        }

        if (retSum != null && retSum.size() > 0) {
            for (ReportQuantityByCategoryCitesDto sumDto : retSum) {
                sumDto.setCites("* T???ng");
                ret.add(sumDto);
            }
        }
        return ret;
    }

    @Override
    public List<FarmAnimalTotalDto> getReportAnimalsCurrent(FarmSearchDto dto) {
        List<FarmAnimalTotalDto> results = new ArrayList<FarmAnimalTotalDto>();
        String whereClause = " WHERE ard.month is null AND ard.day is null";
        if (dto.getAnimalId() != null) {
            whereClause += " AND ard.animal.id = :animalId";
        }
        if (dto.getWard() != null) {
            whereClause += " AND ard.farm.administrativeUnit.id = :unitId";
        } else if (dto.getDistrict() != null) {
            whereClause += " AND ard.farm.administrativeUnit.parent.id = :unitId";
        } else if (dto.getProvince() != null) {
            whereClause += " AND ard.farm.administrativeUnit.parent.parent.id = :unitId";
        }
        String sql = "select new com.globits.wl.dto.functiondto.FarmAnimalTotalDto(ard.animal, ard.year, SUM(ard.total), (SELECT count(distinct ard2.farm) FROM AnimalReportData ard2 WHERE ard2.year = ard.year AND ard2.animal.id = ard.animal.id) ) FROM AnimalReportData ard"
                + whereClause + " GROUP BY ard.year, ard.animal order by ard.year desc";
        Query query = manager.createQuery(sql, FarmAnimalTotalDto.class);

        if (dto.getAnimalId() != null) {
            query.setParameter("animalId", dto.getAnimalId());
        }
        if (dto.getWard() != null) {
            query.setParameter("unitId", dto.getWard());
        } else if (dto.getDistrict() != null) {
            query.setParameter("unitId", dto.getDistrict());
        } else if (dto.getProvince() != null) {
            query.setParameter("unitId", dto.getProvince());
        }
        results = query.getResultList();
        return results;
    }

    /**
     * Caculate Sum animal by administrative and year H??m t??nh b??o c??o hi???n tr???ng
     * nu??i theo 1 lo??i , n??m c??ng ????n v??? ????n v??? h??nh ch??nh, n???u kh??ng ch???n th?? c???
     * n?????c nh??m theo t???nh( t???nh nh??m theo huy???n, huy???n nh??m theo x??)
     */
    @Override
    public List<FarmAnimalTotalDto> reportCurrentStatusByAnimalAdministrative(FarmSearchDto dto) {
        List<FarmAnimalTotalDto> results = new ArrayList<FarmAnimalTotalDto>();

        String sql = "select new com.globits.wl.dto.functiondto.FarmAnimalTotalDto(ard.farm.administrativeUnit.parent.parent, ard.animal, ard.year,ard.total), (SELECT count(distinct ard2.farm) FROM AnimalReportData ard2 WHERE ard2.farm.administrativeUnit.parent.parent.id = ard.farm.administrativeUnit.parent.parent.id AND ard2.year = ard.year ) ) FROM AnimalReportData ard ";
        String whereClause = " WHERE 1=1 ";
        String groupBy = " GROUP BY ard.farm.administrativeUnit.parent.parent, ard.year, ard.animal";
        if (dto.getAnimalId() != null) {
            whereClause += " AND ard.animal.id = :animalId ";
        }

        if (dto.getWard() != null) {
            whereClause += " AND ard.farm.administrativeUnit.id = :unitId";
        } else if (dto.getDistrict() != null) {
            whereClause += " AND ard.farm.administrativeUnit.parent.id = :unitId";
        } else if (dto.getProvince() != null) {
            whereClause += " AND ard.farm.administrativeUnit.parent.parent.id = :unitId";
        }
        if (dto.getYear() > 0) {
            whereClause += " AND ard.year = :year";
        }
        String sqlTmp = sql + whereClause + groupBy;
        if (dto.getWard() != null) {
            sqlTmp = sqlTmp.replace("farm.administrativeUnit.parent.parent", "farm.administrativeUnit");
        } else if (dto.getDistrict() != null) {
            sqlTmp = sqlTmp.replace("farm.administrativeUnit.parent.parent", "farm.administrativeUnit.parent");
        }
        System.out.println(sqlTmp);
        Query query = manager.createQuery(sqlTmp, FarmAnimalTotalDto.class);
        if (dto.getAnimalId() != null) {
            query.setParameter("animalId", dto.getAnimalId());
        }
        if (dto.getWard() != null) {
            query.setParameter("unitId", dto.getWard());
        } else if (dto.getDistrict() != null) {
            query.setParameter("unitId", dto.getDistrict());
        } else if (dto.getProvince() != null) {
            query.setParameter("unitId", dto.getProvince());
        }
        if (dto.getYear() > 0) {
            query.setParameter("year", dto.getYear());
        }

        results = query.getResultList();
        return results;
    }

    @Override
    public List<FarmAnimalTotalDto> reportCurrentStatusByLastAnimalAdministrative(FarmSearchDto dto) {
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
        List<FarmAnimalTotalDto> results = new ArrayList<FarmAnimalTotalDto>();
        String administrativeUnitStr = "";
        if (dto.getWard() != null) {
            administrativeUnitStr += "ard.farm.administrativeUnit";
        } else if (dto.getDistrict() != null) {
            administrativeUnitStr += "ard.farm.administrativeUnit";
        } else if (dto.getProvince() != null) {
            administrativeUnitStr += "ard.farm.administrativeUnit.parent";
        } else {
            administrativeUnitStr = "ard.farm.administrativeUnit.parent.parent";
        }
        String sql = "select new com.globits.wl.dto.functiondto.FarmAnimalTotalDto(" + administrativeUnitStr
                + ", ard.animal, ard.year, SUM(ard.total), " + "(COUNT(distinct ard.farm.id)) ) "
                + "FROM AnimalReportData ard ";
        String whereClause = " WHERE ard.month is NULL AND ard.day is NULL AND ard.type != 0 ";

        if (dto.getAnimalId() != null) {
            whereClause += " AND ard.animal.id = :animalId ";
        }

        if (dto.getWard() != null) {
            whereClause += " AND ard.farm.administrativeUnit.id = :unitId";
        } else if (dto.getDistrict() != null) {
            whereClause += " AND ard.farm.administrativeUnit.parent.id = :unitId";
        } else if (dto.getProvince() != null) {
            whereClause += " AND ard.farm.administrativeUnit.parent.parent.id = :unitId";
        }
        if (!isAdmin) {
            whereClause += " AND (ard.farm.administrativeUnit.id in (:listWardId)) ";
        }
        if (dto.getYear() > 0) {
            whereClause += " AND ard.year = :year";
        }
        if (dto.getAnimalClass() != null) {
            whereClause += " AND ard.animal.animalClass = :animalClass";
        }
        if (dto.getOrdo() != null) {
            whereClause += " AND ard.animal.ordo = :ordo";
        }
        if (dto.getFamily() != null) {
            whereClause += " AND ard.animal.family = :family";
        }
        whereClause += " AND ard.total > 0";
        String groupBy = " GROUP BY " + administrativeUnitStr + ", ard.year, ard.animal.id";

        String sqlTmp = sql + whereClause + groupBy;
//		if(dto.getDistrict() != null) {
//			sqlTmp = sqlTmp.replace("tfarm.administrativeUnit.parent.parent", "farm.administrativeUnit");
//		}else if(dto.getProvince() != null) {
//			sqlTmp = sqlTmp.replace("tfarm.administrativeUnit.parent.parent", "farm.administrativeUnit.parent");
//		}else {
//			sqlTmp = sqlTmp.replace("tfarm", "farm");
//		}
//		System.out.println(sqlTmp);
        Query query = manager.createQuery(sqlTmp, FarmAnimalTotalDto.class);
        if (dto.getAnimalId() != null) {
            query.setParameter("animalId", dto.getAnimalId());
        }
        if (dto.getWard() != null) {
            query.setParameter("unitId", dto.getWard());
        } else if (dto.getDistrict() != null) {
            query.setParameter("unitId", dto.getDistrict());
        } else if (dto.getProvince() != null) {
            query.setParameter("unitId", dto.getProvince());
        }
        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }
        if (dto.getYear() > 0) {
            query.setParameter("year", dto.getYear());
        }
        if (dto.getAnimalClass() != null) {
            query.setParameter("animalClass", dto.getAnimalClass());
//			whereClause += " AND ard.animal.animalClass = :animalClass";
        }
        if (dto.getOrdo() != null) {
            query.setParameter("ordo", dto.getOrdo());
//			whereClause += " AND ard.animal.ordo = :ordo";
        }
        if (dto.getFamily() != null) {
            query.setParameter("family", dto.getFamily());
//			whereClause += " AND ard.animal.family = :family";
        }
        results = query.getResultList();
        return results;
    }

    @Override
    public List<ReportImportExportTimeDto> getReportNumberTimeImportExport(ImportExportAnimalSearchDto searchDto) {
        List<ReportImportExportTimeDto> res = new ArrayList<ReportImportExportTimeDto>();

        searchDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
        List<ReportImportExportTimeDto> listImport = getReportImportExportTimeBySearchDto(searchDto);
        searchDto.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());
        List<ReportImportExportTimeDto> listExport = getReportImportExportTimeBySearchDto(searchDto);

        Map<String, ReportImportExportTimeDto> map = new HashMap<String, ReportImportExportTimeDto>();
        for (ReportImportExportTimeDto item : listImport) {
            String key = item.getFarm().getId() + "-" + item.getAnimal().getId();
            if (map.containsKey(key)) {
                ReportImportExportTimeDto reportImportExport = map.get(key);
            } else {
                item.setRemainQuantity((item.getTotalImport() != null ? item.getTotalImport() : 0));
                map.put(key, item);
            }
        }
        for (ReportImportExportTimeDto item : listExport) {
            if (item.getTotalImport() != null && item.getTotalImport().doubleValue() == 80) {
                System.out.println("");
            }
            String key = item.getFarm().getId() + "-" + item.getAnimal().getId();
            if (map.containsKey(key)) {
                ReportImportExportTimeDto reportImportExport = map.get(key);
                reportImportExport.setCountFarmExport(item.getCountFarmExport());
                reportImportExport.setTotalExport((item.getTotalExport() != null ? item.getTotalExport() : 0));
                reportImportExport.setTotalTimeExport((item.getTotalTimeExport() != null ? item.getTotalTimeExport() : 0));
                reportImportExport.setRemainQuantity(
                        (reportImportExport.getTotalImport() != null ? reportImportExport.getTotalImport() : 0)
                                - (item.getTotalExport() != null ? item.getTotalExport() : 0));
                map.put(key, reportImportExport);
            } else {
                map.put(key, item);
            }
        }
        for (ReportImportExportTimeDto item : map.values()) {
            res.add(item);
        }
        return res;
    }

    public List<ReportImportExportTimeDto> getReportImportExportTimeBySearchDto(ImportExportAnimalSearchDto searchDto) {
        List<ReportImportExportTimeDto> result = new ArrayList<ReportImportExportTimeDto>();
        String sql = "SELECT new com.globits.wl.dto.functiondto.ReportImportExportTimeDto(iea.farm, iea.animal, COUNT(distinct iea.farm), SUM(iea.quantity), COUNT(iea), iea.type) "
                + " FROM ImportExportAnimal iea ";
        String whereClause = " WHERE 1=1";
        String groupBy = " GROUP BY iea.farm, iea.animal, iea.type";

        if (searchDto.getFarmId() != null) {
            whereClause += " AND iea.farm.id = :farmId";
        }
        if (searchDto.getAnimalId() != null) {
            whereClause += " AND iea.animal.id = :animalId";
        }
        if (searchDto.getFromDate() != null) {
            whereClause += " AND iea.dateIssue >= :fromDate";
        }
        if (searchDto.getToDate() != null) {
            whereClause += " AND iea.dateIssue <= :toDate";
        }
        List<Long> listAu = new ArrayList<Long>();
        if (searchDto.getAuId() != null) {
            listAu = fmsAdministrativeUnitService.getAllAdministrativeUnitIdByParentId(searchDto.getAuId());
            listAu.add(searchDto.getAuId());

            whereClause += " AND iea.farm.administrativeUnit.id in (:listAu) ";
        }

        whereClause += " AND iea.type = :type";

        String sqlStr = sql + whereClause + groupBy;
        Query query = manager.createQuery(sqlStr, ReportImportExportTimeDto.class);

        if (searchDto.getFarmId() != null) {
            query.setParameter("farmId", searchDto.getFarmId());
        }
        if (searchDto.getAnimalId() != null) {
            query.setParameter("animalId", searchDto.getAnimalId());
        }
        if (searchDto.getFromDate() != null) {
            query.setParameter("fromDate", searchDto.getFromDate());
        }
        if (searchDto.getToDate() != null) {
            query.setParameter("toDate", searchDto.getToDate());
        }
        if (searchDto.getAuId() != null) {
            query.setParameter("listAu", listAu);
        }
        query.setParameter("type", searchDto.getType());

        result = query.getResultList();
        return result;
    }

    @Override
    public List<AnimalReportDataFormDto> reportActivityOfEndangeredPreciousRareNormarlAndCites(
            AnimalReportDataSearchDto searchDto) {
        List<AnimalReportDataFormDto> result = new ArrayList<AnimalReportDataFormDto>();
//		AND ard.type != 0 AND (ard.farm.isDuplicate is null OR ard.farm.isDuplicate=false)
        List<AnimalReportData> animalReportDataDtos = new ArrayList<AnimalReportData>();
        String sql = "select ard FROM AnimalReportData ard";
        String whereClause =
//				" WHERE ard.month is null AND ard.day is null AND ard.year is not null and ard.total is not null and ard.total > 0 ";
                " WHERE ard.month is null AND ard.day is null AND ard.year is not null AND ard.total!=0 ";
        String groupBy = " ";

        if (searchDto.getAnimalId() != null) {
            whereClause += " AND ard.animal.id = :animalId";
        }
        if (searchDto.getAnimalOrdo() != null) {
            whereClause += " AND ard.animal.ordo = :animalOrdo";
        }
        if (searchDto.getAnimalClass() != null) {
            whereClause += " AND ard.animal.animalClass = :animalClass";
        }
        if (searchDto.getAnimalFamily() != null) {
            whereClause += " AND ard.animal.family = :animalFamily";
        }
        if (searchDto.getFarmId() != null) {
            whereClause += " AND ard.farm.id = :farmId";
        }
        if (searchDto.getYear() != null) {
            whereClause += " AND ard.year = :year";
        }

        if (searchDto.getAnimalCites() != null) {
            whereClause += " AND ard.animal.cites = :animalCites";
        }
        if (searchDto.getAnimalVnlist() != null) {
            whereClause += " AND ard.animal.vnlist = :animalVnlist";
        }
        if (searchDto.getAnimalVnlist06() != null) {
            whereClause += " AND ard.animal.vnlist06 = :animalVnlist06";
        }

//		if (searchDto.getProvinceId() != null) {
//			whereClause += " AND ard.farm.administrativeUnit.parent.parent.id = :provinceId";
//		}
//		if (searchDto.getDistrictId() != null) {
//			whereClause += " AND ard.farm.administrativeUnit.parent.id = :districtId";
//		}
//		if (searchDto.getCommuneId() != null) {
//			whereClause += " AND ard.farm.administrativeUnit.id = :communeId";
//		}
        //s???a l???i query ?????a ch??? theo b??o c??o
        if (searchDto.getProvinceId() != null) {
            whereClause += " AND ard.province.id = :provinceId";
        }
        if (searchDto.getDistrictId() != null) {
            whereClause += " AND ard.district.id = :districtId";
        }
        if (searchDto.getCommuneId() != null) {
            whereClause += " AND ard.administrativeUnit.id = :communeId";
        }
        groupBy = " ORDER BY ard.province.name asc, ard.district.name asc, ard.administrativeUnit.name asc, ard.farm.code asc, ard.farm.name asc ";
        String sqlStr = sql + whereClause + groupBy;
        Query query = manager.createQuery(sqlStr, AnimalReportData.class);

        if (searchDto.getAnimalId() != null) {
            query.setParameter("animalId", searchDto.getAnimalId());
        }
        if (searchDto.getFarmId() != null) {
            query.setParameter("farmId", searchDto.getFarmId());
        }
        if (searchDto.getYear() != null) {
            query.setParameter("year", searchDto.getYear());
        }
        if (searchDto.getProvinceId() != null) {
            query.setParameter("provinceId", searchDto.getProvinceId());
        }
        if (searchDto.getDistrictId() != null) {
            query.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getCommuneId() != null) {
            query.setParameter("communeId", searchDto.getCommuneId());
        }
        if (searchDto.getAnimalClass() != null) {
            query.setParameter("animalClass", searchDto.getAnimalClass());
        }
        if (searchDto.getAnimalOrdo() != null) {
            query.setParameter("animalOrdo", searchDto.getAnimalOrdo());
        }
        if (searchDto.getAnimalFamily() != null) {
            query.setParameter("animalFamily", searchDto.getAnimalFamily());
        }
        if (searchDto.getAnimalCites() != null) {
            query.setParameter("animalCites", searchDto.getAnimalCites());
        }
        if (searchDto.getAnimalVnlist() != null) {
            query.setParameter("animalVnlist", searchDto.getAnimalVnlist());
        }
        if (searchDto.getAnimalVnlist06() != null) {
            query.setParameter("animalVnlist06", searchDto.getAnimalVnlist06());
        }
        animalReportDataDtos = query.getResultList();
        Map<String, AnimalReportDataFormDto> map = new HashMap<String, AnimalReportDataFormDto>();
        for (AnimalReportData item : animalReportDataDtos) {
            if (item != null && item.getFarm() != null && item.getAnimal() != null) {
                String key = item.getFarm().getId() + "-" + item.getAnimal().getId();
                if (map.containsKey(key)) {
                    AnimalReportDataFormDto animalReportDataFormDto = map.get(key);
                    if (item.getType() == WLConstant.AnimalReportDataType.animalParent.getValue().intValue()) {
                        if (item.getMale() != null)
                            animalReportDataFormDto.setMaleParent(animalReportDataFormDto.getMaleParent() + item.getMale());
                        if (item.getFemale() != null)
                            animalReportDataFormDto.setFemaleParent(animalReportDataFormDto.getFemaleParent() + item.getFemale());
                        if (item.getTotal() != null)
                            animalReportDataFormDto.setTotalParent(animalReportDataFormDto.getTotalParent() + item.getTotal());
                    } else if (item.getType() == WLConstant.AnimalReportDataType.childOver1YearOld.getValue()
                            .intValue()) {
                        if (item.getMale() != null)
                            animalReportDataFormDto.setMaleChild(animalReportDataFormDto.getMaleChild() + item.getMale());
                        if (item.getFemale() != null)
                            animalReportDataFormDto.setFemaleChild(animalReportDataFormDto.getFemaleChild() + item.getFemale());
                        if (item.getUnGender() != null)
                            animalReportDataFormDto.setUnGenderChild(animalReportDataFormDto.getUnGenderChild() + item.getUnGender());
                        if (item.getTotal() != null)
                            animalReportDataFormDto.setTotalChild(animalReportDataFormDto.getTotalChild() + item.getTotal());
                    } else if (item.getType() == WLConstant.AnimalReportDataType.childUnder1YearOld.getValue()
                            .intValue()
                            || item.getType() == WLConstant.AnimalReportDataType.unDefine.getValue()
                            .intValue()) {
                        if (item.getTotal() != null)
                            animalReportDataFormDto.setTotalChildUnder1YO(animalReportDataFormDto.getTotalChildUnder1YO() + item.getTotal());
                    } else if (item.getType() == WLConstant.AnimalReportDataType.gilts.getValue().intValue()) {
                        if (item.getMale() != null)
                            animalReportDataFormDto.setMaleGilts(animalReportDataFormDto.getMaleGilts() + item.getMale());
                        if (item.getFemale() != null)
                            animalReportDataFormDto.setFemaleGilts(animalReportDataFormDto.getFemaleGilts() + item.getFemale());
                        if (item.getTotal() != null)
                            animalReportDataFormDto.setTotalGilts(animalReportDataFormDto.getTotalGilts() + item.getTotal());
                    }
//					else if(item.getType() == WLConstant.AnimalReportDataType.importAnimal.getValue().intValue()) {
//						if(item.getMale()!=null)
//							animalReportDataFormDto.setMaleChild(animalReportDataFormDto.getMaleChild() + item.getMale());
//						if(item.getFemale()!=null)
//							animalReportDataFormDto.setFemaleChild(animalReportDataFormDto.getFemaleChild() + item.getFemale());
//						if(item.getUnGender()!=null)
//							animalReportDataFormDto.setUnGenderChild(animalReportDataFormDto.getUnGenderChild() + item.getUnGender());
//					}

                } else {
                    AnimalReportDataFormDto animalReportDataFormDto = new AnimalReportDataFormDto();
                    AnimalDto animalDto = new AnimalDto();
                    animalDto.setId(item.getAnimal().getId());
                    animalDto.setName(item.getAnimal().getName());
                    animalDto.setScienceName(item.getAnimal().getScienceName());
                    animalDto.setCode(item.getAnimal().getCode());
                    String productTargetStr = "";
                    if (item.getAnimal().getAnimalProductTargets() != null) {
                        for (AnimalProductTarget animalProductTarget : item.getAnimal().getAnimalProductTargets()) {
                            if (animalProductTarget != null && animalProductTarget.getProductTarget() != null
                                    && animalProductTarget.getProductTarget().getName() != null) {
                                productTargetStr += animalProductTarget.getProductTarget().getName() + ", ";
                            }
                        }
                    }
                    if (productTargetStr.length() >= 2) {
                        productTargetStr = productTargetStr.substring(0, productTargetStr.length() - 2);
                    }
                    animalReportDataFormDto.setProductTargets(productTargetStr);
                    animalReportDataFormDto.setAnimal(animalDto);
                    if (item.getAdministrativeUnit() != null) {
                        FmsAdministrativeUnitDto wardDto = new FmsAdministrativeUnitDto();
                        wardDto.setId(item.getAdministrativeUnit().getId());
                        wardDto.setCode(item.getAdministrativeUnit().getCode());
                        wardDto.setName(item.getAdministrativeUnit().getName());
                        animalReportDataFormDto.setAdministrativeUnitDto(wardDto);
                    }
                    if (item.getDistrict() != null) {
                        FmsAdministrativeUnitDto districtDto = new FmsAdministrativeUnitDto();
                        districtDto.setId(item.getDistrict().getId());
                        districtDto.setCode(item.getDistrict().getCode());
                        districtDto.setName(item.getDistrict().getName());
                        animalReportDataFormDto.setDistrictDto(districtDto);
                    }
                    if (item.getProvince() != null) {
                        FmsAdministrativeUnitDto provinceDto = new FmsAdministrativeUnitDto();
                        provinceDto.setId(item.getProvince().getId());
                        provinceDto.setCode(item.getProvince().getCode());
                        provinceDto.setName(item.getProvince().getName());
                        animalReportDataFormDto.setProvinceDto(provinceDto);
                    }
                    FarmDto farmDto = new FarmDto();
                    farmDto.setOwnerName(item.getFarm().getOwnerName());
                    farmDto.setOwnerAdress(item.getFarm().getOwnerAdress());
                    farmDto.setFoundingDate(item.getFarm().getFoundingDate());
                    farmDto.setNewRegistrationCode(item.getFarm().getNewRegistrationCode());
                    farmDto.setVillage(item.getFarm().getVillage());
                    farmDto.setId(item.getFarm().getId());
                    farmDto.setName(item.getFarm().getName());
                    farmDto.setCode(item.getFarm().getCode());
                    farmDto.setNewRegistrationCode(item.getFarm().getNewRegistrationCode());
                    farmDto.setLongitude(item.getFarm().getLongitude());
                    farmDto.setLatitude(item.getFarm().getLatitude());
                    String purposeStr = "";
                    if (item.getFarm().getFarmProductTargets() != null && item.getFarm().getFarmProductTargets().size() > 0) {
                        for (FarmProductTarget pt : item.getFarm().getFarmProductTargets()) {
                            if (purposeStr != null && purposeStr.length() > 0) {
                                purposeStr = purposeStr + ", ";
                            }
                            purposeStr = purposeStr + pt.getProductTarget().getCode();
                        }
                        animalReportDataFormDto.setProductTargets(purposeStr);
                    }
                    animalReportDataFormDto.setFarm(farmDto);
                    if (item.getFarm().getAdministrativeUnit() != null) {
                        // this.administrativeUnit = new
                        // FmsAdministrativeUnitDto(farm.getAdministrativeUnit());
                        farmDto.setWardsName(item.getFarm().getAdministrativeUnit().getName());
                        farmDto.setWardCode(item.getFarm().getAdministrativeUnit().getCode());
                        farmDto.setWardId(item.getFarm().getAdministrativeUnit().getId());

                        if (item.getFarm().getAdministrativeUnit().getParent() != null) {
                            farmDto.setDistrictName(item.getFarm().getAdministrativeUnit().getParent().getName());
                            farmDto.setDistrictCode(item.getFarm().getAdministrativeUnit().getParent().getCode());
                            farmDto.setDistrictId(item.getFarm().getAdministrativeUnit().getParent().getId());
                        }

                        if (item.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
                            farmDto.setProvinceCode(
                                    item.getFarm().getAdministrativeUnit().getParent().getParent().getCode());
                            farmDto.setProvinceName(
                                    item.getFarm().getAdministrativeUnit().getParent().getParent().getName());
                            farmDto.setProvinceId(
                                    item.getFarm().getAdministrativeUnit().getParent().getParent().getId());
                        }

                    }
                    if (item.getType() == WLConstant.AnimalReportDataType.animalParent.getValue().intValue()) {
                        if (item.getMale() != null)
                            animalReportDataFormDto.setMaleParent(item.getMale());
                        if (item.getFemale() != null)
                            animalReportDataFormDto.setFemaleParent(item.getFemale());
                        if (item.getTotal() != null)
                            animalReportDataFormDto.setTotalParent(animalReportDataFormDto.getTotalParent() + item.getTotal());
                    } else if (item.getType() == WLConstant.AnimalReportDataType.childOver1YearOld.getValue()
                            .intValue()) {
                        if (item.getMale() != null)
                            animalReportDataFormDto.setMaleChild(item.getMale());
                        if (item.getFemale() != null)
                            animalReportDataFormDto.setFemaleChild(item.getFemale());
                        if (item.getUnGender() != null)
                            animalReportDataFormDto.setUnGenderChild(item.getUnGender());
                        if (item.getTotal() != null)
                            animalReportDataFormDto.setTotalChild(item.getTotal());
                    } else if (item.getType() == WLConstant.AnimalReportDataType.childUnder1YearOld.getValue()
                            .intValue()
                            || item.getType() == WLConstant.AnimalReportDataType.unDefine.getValue()
                            .intValue()) {
                        if (item.getTotal() != null)
                            animalReportDataFormDto.setTotalChildUnder1YO(item.getTotal());

                    } else if (item.getType() == WLConstant.AnimalReportDataType.gilts.getValue().intValue()) {
                        if (item.getMale() != null)
                            animalReportDataFormDto.setMaleGilts(item.getMale());
                        if (item.getFemale() != null)
                            animalReportDataFormDto.setFemaleGilts(item.getFemale());
                        if (item.getTotal() != null)
                            animalReportDataFormDto.setTotalGilts(item.getTotal());
                    }
//					else if(item.getType() == WLConstant.AnimalReportDataType.importAnimal.getValue().intValue()) {
//						if(item.getMale()!=null)
//							animalReportDataFormDto.setMaleChild(item.getMale());
//						if(item.getFemale()!=null)
//							animalReportDataFormDto.setFemaleChild(item.getFemale());
//						if(item.getUnGender()!=null)
//							animalReportDataFormDto.setUnGenderChild(item.getUnGender());
//					}
//					else if(item.getType() == WLConstant.AnimalReportDataType.exportAnimal.getValue().intValue()) {
//						if(item.getMale()!=null) {
//							Integer ret = item.getMale();
//							if(animalReportDataFormDto.getMaleChild()!=null) {
//
//							}
//						}
//					}
                    map.put(key, animalReportDataFormDto);
                }
            }
        }

        for (AnimalReportDataFormDto animalReportDataFormDto : map.values()) {
//			if( (animalReportDataFormDto.getTotalParent() != null && animalReportDataFormDto.getTotalParent() != 0) || (animalReportDataFormDto.getTotalGilts() != null && animalReportDataFormDto.getTotalGilts() != 0  ) || (animalReportDataFormDto.getTotalChildUnder1YO() != null && animalReportDataFormDto.getTotalChildUnder1YO() != 0) || (animalReportDataFormDto.getTotalChild() != null && animalReportDataFormDto.getTotalChild() != 0) ) {
            result.add(animalReportDataFormDto);
//			}
        }
        return result;
    }

    @Override
    public List<Report18Dto> reportActivityOfEndangeredPreciousRareNormarlAndCitesNativeQuery(
            AnimalReportDataSearchDto dto) {
        String listcode = "select pro.code from ProductTarget pro ";
        Query q = manager.createQuery(listcode);
        List<String> listCode = q.getResultList();

        String sql = "SELECT * " +
                " FROM " +
                "  (SELECT ROW_NUMBER() OVER (PARTITION BY f.farm_id," +
                "                                          f.animal_id" +
                "                             ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc) AS rowNumber," +
                "                            f.date_report AS dateReport," +
                "                            p.year," +
                "                            p.month," +
                "                            p.date," +
                "                            prov.id AS provId," +
                "                            prov.name AS provName," +
                "                            prov.code AS provCode," +
                "                            dis.id AS disId," +
                "                            dis.name AS disName," +
                "                            dis.code AS disCode," +
                "                            w.id AS wardId," +
                "                            w.name AS wardName," +
                "                            w.code AS wardCode," +
                "                            a.id AS animalId," +
                "                            a.name AS animalName," +
                "                            a.science_name AS animalSciName," +
                "                            a.other_name AS animalOtherName," +
                "                            a.code AS animalCode," +
                "                            a.ordo AS animalOrdo," +
                "                            a.animal_class AS animalClass," +
                "                            a.family AS animalFamily," +
                "                            a.cites," +
                "                            a.vnlist," +
                "                            a.vnlist06," +
                "                            fa.id AS farmId," +
                "                            fa.name AS farmName," +
                "                            fa.code AS farmCode," +
                "                            fa.map_code AS farmMapCode," +
                "                            fa.new_registration_code AS farmNewRegistrationCode," +
                "                            fa.old_registration_code AS farmOldRegistrationCode," +
                "                            fa.date_other_registration AS farmDateOtherRegistration," +
                "                            fa.date_registration AS farmDateRegistration," +
                "                            fa.longitude AS farmLongitude," +
                "                            fa.latitude AS farmLatitude," +
                "                            fa.village AS farmVillage," +
                "                            fa.status_farm AS statusFarm," +
                "                            f.total AS total," +
                "							 f.male_parent + f.female_parent AS totalParent," +
                "                            f.male_parent AS maleParent," +
                "                            f.female_parent AS femaleParent," +
                "							 f.male_gilts + f.female_gilts AS totalGilts," +
                "                            f.male_gilts AS maleGilts," +
                "                            f.female_gilts AS femaleGilts," +
                "							 f.child_under_1year_old AS totalChildUnder1YO," +
//				"                            f.male_child_under_1year_old + f.female_child_under_1year_old + f.child_under_1year_old AS totalChildUnder1YO," +
//				"                            f.male_child_over_1year_old + f.female_child_over_1year_old + f.un_gender_child_over_1year_old AS totalChildOver1YO," +
                "                            f.male_child_over_1year_old AS maleChildOver1YearOld," +
                "                            f.female_child_over_1year_old AS femaleChildOver1YearOld," +
                "                            f.un_gender_child_over_1year_old AS unGenderChildOver1YearOld," +
                "                            fa.founding_date AS farmFoundingDate,tb2.T,tb2.Z,tb2.O,tb2.Q,tb2.R,tb2.S,tb2.PTM,td2.ST,td2.SS,td2.[SS-ST] AS SSTT" +
                "   FROM tbl_report_form16 f" +
                "   INNER JOIN tbl_report_period p ON f.report_period_id = p.id" +
                "   INNER JOIN tbl_animal a ON a.id = f.animal_id" +
                "   INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate = 0) " +
                "   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id" +
                "   AND prov.parent_id IS NULL" +
                "   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id" +
                "   AND dis.parent_id IS NOT NULL" +
                "   INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id" +
                "   AND w.parent_id IS NOT NULL"
//				"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//				"   AND w.parent_id IS NOT NULL" +
//				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//				"   AND dis.parent_id IS NOT NULL"
                + "	left join (select * from (select fpt.farm_id ,pt.id,pt.code from tbl_farm_product_target fpt  inner join tbl_product_target pt on pt.id=fpt.product_target_id) as tb3  pivot (sum(id) for tb3.code in ([T],[O],[Z],Q,R,S,PTM)) as piv) as tb2 on tb2.farm_id=f.farm_id "
                + " left join (select * from (select fhm.farm_id,hm.id,hm.code from tbl_farm_husbandry_method fhm inner join tbl_husbandry_method hm on hm.id=fhm.husbandry_method_id) as td3 pivot (sum(id) for td3.code in ([ST],[SS],[SS-ST])) as piv1) as td2 on td2.farm_id=f.farm_id) AS tb1 where tb1.rowNumber=1 and tb1.total<>0 ";

        //String whereClause = " where tb1.rowNumber=1 and tb1.total<>0 ";
//		String orderBy = " ORDER BY ard.province.name asc, ard.district.name asc, ard.administrativeUnit.name asc, ard.farm.code asc, ard.farm.name asc ";

        String whereClause = " ";
//        if (dto.getYear() != null) {
//            whereClause += " and tb1.year = :year ";
//        }

        if (dto.getDateReport() != null) {
//        	whereClause += " AND (tb1.year < :yearReport "
//                    + " OR (tb1.year <= :yearReport AND tb1.month < :monthReport) "
//                    + " OR (tb1.year <= :yearReport AND tb1.month <= :monthReport AND tb1.date <= :dateReport)) AND tb1.year = :yearReport";
            whereClause += " AND (tb1.year = :yearReport AND tb1.dateReport <= :dateReport) ";
        }

        if (dto.getAnimalId() != null) {
            whereClause += " AND tb1.animalId = :animalId";
        }
        //list
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            whereClause += " AND tb1.animalId in (:animalIds)";
        }
        if (dto.getAnimalOrdo() != null) {
            whereClause += " AND tb1.animalOrdo = :animalOrdo";
        }
        //list
        if (dto.getListAnimalOrdo() != null && dto.getListAnimalOrdo().size() > 0) {
            whereClause += " AND tb1.animalOrdo in (:listAnimalOrdo)";
        }
        if (dto.getAnimalClass() != null) {
            whereClause += " AND tb1.animalClass = :animalClass";
        }
        //list
        if (dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
            whereClause += " AND tb1.animalClass in (:listAnimalClass)";
        }
        if (dto.getAnimalFamily() != null) {
            whereClause += " AND tb1.animalFamily = :animalFamily";
        }
        //list
        if (dto.getListAnimalFamily() != null && dto.getListAnimalFamily().size() > 0) {
            whereClause += " AND tb1.animalFamily in (:listAnimalFamily)";
        }
        if (dto.getFarmId() != null) {
            whereClause += " AND tb1.farmId = :farmId";
        }

        if (dto.getAnimalCites() != null) {
            whereClause += " AND tb1.cites = :animalCites";
        }
        //list
        if (dto.getListAnimalCites() != null && dto.getListAnimalCites().size() > 0) {
            whereClause += " AND tb1.cites in (:listAnimalCites)";
        }
        if (dto.getAnimalVnlist() != null) {
            whereClause += " AND tb1.vnlist = :animalVnlist";
        }
        //list
        if (dto.getListAnimalVnlist() != null && dto.getListAnimalVnlist().size() > 0) {
            whereClause += " AND tb1.vnlist in (:listAnimalVnlist)";
        }
        if (dto.getAnimalVnlist06() != null) {
            whereClause += " AND tb1.vnlist06 = :animalVnlist06";
        }
        //list
        if (dto.getListAnimalVnlist06() != null && dto.getListAnimalVnlist06().size() > 0) {
            whereClause += " AND tb1.vnlist06 in (:listAnimalVnlist06)";
        }
        if (dto.getProvinceId() != null) {
            whereClause += " AND tb1.provId = :provinceId";
        }
        if (dto.getDistrictId() != null) {
            whereClause += " AND tb1.disId = :districtId";
        }
        if (dto.getCommuneId() != null) {
            whereClause += " AND tb1.wardId = :communeId";
        }

        //pivot+= sql + pivot2 + whereClause;
        sql += whereClause;
//        if (dto.getAnimalVnlist06ByForm() != null && dto.getAnimalVnlist06ByForm() == true) {
//            sql += " Except " + sql + " and tb1.vnlist06 = :animalVnlist06KQL ";
//        }
        Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(Report18Dto.class));

        //query.setParameter("listcode", listCode);
//        if (dto.getYear() != null) {
//            query.setParameter("year", dto.getYear());
//        }

        if (dto.getDateReport() != null) {
            Integer yearReport = WLDateTimeUtil.getYear(dto.getDateReport());
//             Integer monthReport = WLDateTimeUtil.getMonth(dto.getDateReport());
//             Integer dateReport = WLDateTimeUtil.getDay(dto.getDateReport());
            query.setParameter("yearReport", yearReport);
            query.setParameter("dateReport", dto.getDateReport());
        }

        if (dto.getAnimalId() != null) {
            query.setParameter("animalId", dto.getAnimalId());
        }
        //list
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            query.setParameter("animalIds", dto.getAnimalIds());
        }
        if (dto.getFarmId() != null) {
            query.setParameter("farmId", dto.getFarmId());
        }
        if (dto.getProvinceId() != null) {
            query.setParameter("provinceId", dto.getProvinceId());
        }
        if (dto.getDistrictId() != null) {
            query.setParameter("districtId", dto.getDistrictId());
        }
        if (dto.getCommuneId() != null) {
            query.setParameter("communeId", dto.getCommuneId());
        }
        if (dto.getAnimalClass() != null) {
            query.setParameter("animalClass", dto.getAnimalClass());
        }
        //list
        if (dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
            query.setParameter("listAnimalClass", dto.getListAnimalClass());
        }
        if (dto.getAnimalOrdo() != null) {
            query.setParameter("animalOrdo", dto.getAnimalOrdo());
        }
        //list
        if (dto.getListAnimalOrdo() != null && dto.getListAnimalOrdo().size() > 0) {
            query.setParameter("listAnimalOrdo", dto.getListAnimalOrdo());
        }
        if (dto.getAnimalFamily() != null) {
            query.setParameter("animalFamily", dto.getAnimalFamily());
        }
        //list
        if (dto.getListAnimalFamily() != null && dto.getListAnimalFamily().size() > 0) {
            query.setParameter("listAnimalFamily", dto.getListAnimalFamily());
        }
        if (dto.getAnimalCites() != null) {
            query.setParameter("animalCites", dto.getAnimalCites());
        }
        //list
        if (dto.getListAnimalCites() != null && dto.getListAnimalCites().size() > 0) {
            query.setParameter("listAnimalCites", dto.getListAnimalCites());
        }
        if (dto.getAnimalVnlist() != null) {
            query.setParameter("animalVnlist", dto.getAnimalVnlist());
        }
        //list
        if (dto.getListAnimalVnlist() != null && dto.getListAnimalVnlist().size() > 0) {
            query.setParameter("listAnimalVnlist", dto.getListAnimalVnlist());
        }
        if (dto.getAnimalVnlist06() != null) {
            query.setParameter("animalVnlist06", dto.getAnimalVnlist06());
        }
//        if (dto.getAnimalVnlist06() != null) {
//            query.setParameter("animalVnlist06", dto.getAnimalVnlist06());
//        }
        //list
        if (dto.getListAnimalVnlist06() != null && dto.getListAnimalVnlist06().size() > 0) {
            query.setParameter("listAnimalVnlist06", dto.getListAnimalVnlist06());
        }
//        if (dto.getAnimalVnlist06ByForm() != null && dto.getAnimalVnlist06ByForm() == true) {
//            query.setParameter("animalVnlist06KQL", "KQL");
//        }

        List<Report18Dto> result = (List<Report18Dto>) query.getResultList();

        return result;
    }

    @Override
    public List<FarmAnimalTotalDto> reportCurrentStatusByFamily(AnimalReportDataSearchDto searchDto) {
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

        String sql = "select new com.globits.wl.dto.functiondto.FarmAnimalTotalDto(ard.farm, ard.animal, SUM(ard.total) , ard.year) FROM AnimalReportData ard ";
//		String whereClause = " WHERE ard.month is null AND ard.day is null AND ard.type != 0";
        String whereClause = " WHERE ard.month is null AND ard.day is null AND ard.year is not null AND ard.total>0 ";
        String groupBy = " GROUP BY ard.year, ard.farm, ard.animal ";// HAVING SUM(ard.total) > 0
        if (searchDto.getYear() != null) {
            whereClause += " AND ard.year = :year";
        }
        if (searchDto.getAnimalFamily() != null) {
            whereClause += " AND ard.animal.family like :family";
        }
        //list
        if (searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
            whereClause += " AND ard.animal.family in (:listAnimalFamily)";
        }
        if (searchDto.getAnimalOrdo() != null) {
            whereClause += " AND ard.animal.ordo like :ordo";
        }
        //list  
        if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
            whereClause += " AND ard.animal.ordo in (:listAnimalOrdo)";
        }
        if (searchDto.getAnimalClass() != null) {
            whereClause += " AND ard.animal.animalClass like :animalClass";
        }
        //list  
        if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
            whereClause += " AND ard.animal.animalClass in (:listAnimalClass)";
        }
        if (searchDto.getCommuneId() != null) {
            whereClause += " AND ard.farm.administrativeUnit.id = :communeId";
        }
        if (searchDto.getDistrictId() != null) {
            whereClause += " AND ard.farm.administrativeUnit.parent.id = :districtId";
        }
        if (searchDto.getProvinceId() != null) {
            whereClause += " AND ard.farm.administrativeUnit.parent.parent.id = :provinceId";
        }

        if (!isAdmin) {
            whereClause += " AND (ard.farm.administrativeUnit.id in (:listWardId)) ";
        }

        if (searchDto.getAnimalCites() != null) {
            whereClause += " AND ard.animal.cites like :animalCites";
        }
        //list 
        if (searchDto.getListAnimalCites() != null && searchDto.getListAnimalCites().size() > 0) {
            whereClause += " AND ard.animal.cites in (:listAnimalCites)";
        }
        if (searchDto.getAnimalVnlist() != null) {
            whereClause += " AND ard.animal.vnlist like :animalVnlist";
        }
        if (searchDto.getAnimalVnlist06() != null) {
            whereClause += " AND ard.animal.vnlist06 like :animalVnlist06";
        }
        if (searchDto.getAnimalId() != null) {
            whereClause += " AND ard.animal.id = :animalId";
        }
        //list  
        if (searchDto.getAnimalIds() != null && searchDto.getAnimalIds().size() > 0) {
            whereClause += " AND ard.animal.id in (:animalIds)";
        }
        if (searchDto.getAnimalGroup() != null) {
            whereClause += " AND ard.animal.animalGroup like :animalGroup";
        }

        Query query = manager.createQuery(sql + whereClause + groupBy, FarmAnimalTotalDto.class);
        if (searchDto.getYear() != null) {
            query.setParameter("year", searchDto.getYear());
        }
        if (searchDto.getAnimalFamily() != null) {
            query.setParameter("family", searchDto.getAnimalFamily());
        }
        //list
        if (searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
            query.setParameter("listAnimalFamily", searchDto.getListAnimalFamily());
        }
        if (searchDto.getAnimalOrdo() != null) {
            query.setParameter("ordo", searchDto.getAnimalOrdo());
        }
        //list
        if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
            query.setParameter("listAnimalOrdo", searchDto.getListAnimalOrdo());
        }
        if (searchDto.getAnimalClass() != null) {
            query.setParameter("animalClass", searchDto.getAnimalClass());
        }
        //list 
        if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
            query.setParameter("listAnimalClass", searchDto.getListAnimalClass());
        }
        if (searchDto.getCommuneId() != null) {
            query.setParameter("communeId", searchDto.getCommuneId());
        }
        if (searchDto.getDistrictId() != null) {
            query.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getProvinceId() != null) {
            query.setParameter("provinceId", searchDto.getProvinceId());
        }

        if (!isAdmin) {
            query.setParameter("listWardId", listWardId);
        }

        if (searchDto.getAnimalCites() != null) {
            query.setParameter("animalCites", searchDto.getAnimalCites());
        }
        //list
        if (searchDto.getListAnimalCites() != null && searchDto.getListAnimalCites().size() > 0) {
            query.setParameter("listAnimalCites", searchDto.getListAnimalCites());
        }
        if (searchDto.getAnimalVnlist() != null) {
            query.setParameter("animalVnlist", searchDto.getAnimalVnlist());
        }
        if (searchDto.getAnimalVnlist06() != null) {
            query.setParameter("animalVnlist06", searchDto.getAnimalVnlist06());
        }
        if (searchDto.getAnimalId() != null) {
            query.setParameter("animalId", searchDto.getAnimalId());
        }
        //list
        if (searchDto.getAnimalIds() != null && searchDto.getAnimalIds().size() > 0) {
            query.setParameter("animalIds", searchDto.getAnimalIds());
        }
        if (searchDto.getAnimalGroup() != null) {
            query.setParameter("animalGroup", searchDto.getAnimalGroup());
        }
        return query.getResultList();
    }

    @Override
    public List<AnimalReportDataReportDto> getAnimalReportDataReport(ReportParamDto dto) {
        String whereClause = " where 1=1 ";
        String groupBy = " ";
        String sqlSelect = "";
//        String sql = " FROM (SELECT year,provinceId,provinceName,districtId,districtName,wardId,wardName,farmId,animalId,total FROM"
//                + "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
//                + "  f.date_report AS dateReport,"
//                + "  p.year,"
//                + "  prov.id AS provinceId,"
//                + "  prov.name AS provinceName,"
//                + "  prov.code AS provCode,"
//                + "  dis.id AS districtId,"
//                + "  dis.name AS districtName,"
//                + "  dis.code AS disCode,"
//                + "  w.id AS wardId,"
//                + "  w.name AS wardName,"
//                + "  w.code AS wardCode,"
//                + "  a.id AS animalId,"
//                + "  a.name AS animalName,"
//                + "  a.code AS animalCode,"
//                + "  fa.id AS farmId,"
//                + "  fa.name AS farmName,"
//                + "  fa.code AS farmCode,"
//                + "  f.total AS total"
//                + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
//                + "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
//                + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
//                + "  AND prov.parent_id IS NULL"
//                + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
//                + "  AND dis.parent_id IS NOT NULL"
//                + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
//                + "  AND w.parent_id IS NOT NULL"
//                + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//                
////				+"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
////				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
////				"   AND w.parent_id IS NOT NULL" +
////				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
////				"   AND dis.parent_id IS NOT NULL"
//
//                + " ) as tb where tb.rowNumber=1 and tb.total<>0 ) as t ) as tb1 ";

        String sql = " FROM (SELECT "
        		+ "year, "
        		+ "month,date,"
        		+ " provinceId,provinceName,districtId,districtName,wardId,wardName,farmId,animalId,total FROM"
                + "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
                + "  f.date_report AS dateReport,"
                + "  p.year,"
                + "  p.month,"
                + "  p.date,"
                + "  prov.id AS provinceId,"
                + "  prov.name AS provinceName,"
                + "  prov.code AS provCode,"
                + "  dis.id AS districtId,"
                + "  dis.name AS districtName,"
                + "  dis.code AS disCode,"
                + "  w.id AS wardId,"
                + "  w.name AS wardName,"
                + "  w.code AS wardCode,"
                + "  a.id AS animalId,"
                + "  a.name AS animalName,"
                + "  a.code AS animalCode,"
                + "  fa.id AS farmId,"
                + "  fa.name AS farmName,"
                + "  fa.code AS farmCode,"
                + "  f.total AS total"
                + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
                + "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
                + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
                + "  AND prov.parent_id IS NULL"
                + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
                + "  AND dis.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
                + "  AND w.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
                + " ) as tb where tb.rowNumber=1 and tb.total<>0 ) as t ) as tb1 ";
//        if (dto.getYear() != null) {
//            whereClause += " and tb1.year = :year ";
//        }
        if (dto.getDateReport() != null) {
            whereClause += " AND (tb1.year < :yearReport "
                    + " OR (tb1.year <= :yearReport AND tb1.month < :monthReport) "
                    + " OR (tb1.year <= :yearReport AND tb1.month <= :monthReport AND tb1.date <= :dateReport)) AND tb1.year = :yearReport";
        }
        if (dto.getProvinceId() != null) {
            whereClause += " AND tb1.provinceId = :provinceId";
        }
        if (dto.getDistrictId() != null) {
            whereClause += " AND tb1.districtId = :districtId";
        }
        if (dto.getWardId() != null) {
            whereClause += " AND tb1.wardId = :wardId";
        }
        if (dto.isShowWardData() == false) {
            if (dto.getWardId() != null) {
                groupBy = " GROUP BY "
//                		+ "year,month,date,"
                		+ " provinceId,provinceName,districtId,districtName,wardId,wardName order by "
//                		+ "year DESC,"
                		+ "provinceId,districtId,wardId";
                sqlSelect = "SELECT "
//                		+ " year,"
                		+ "provinceId,provinceName,districtId,districtName,wardId,wardName,COUNT(DISTINCT farmId) AS 'numberOfFam', COUNT(DISTINCT animalId) AS 'numberOfAnimal', sum(total) AS total";
            } else {
                if (dto.getDistrictId() != null) {
                    groupBy = " GROUP BY "
//                    		+ "year,month,date,"
                    		+ " provinceId,provinceName,districtId,districtName,wardId,wardName order by "
//                    		+ " year DESC,"
                    		+ " provinceId,districtId,wardId";
                    sqlSelect = "SELECT "
//                    		+ "year,"
                    		+ " provinceId,provinceName,districtId,districtName,wardId,wardName,COUNT(DISTINCT farmId) AS 'numberOfFam', COUNT(DISTINCT animalId) AS 'numberOfAnimal', sum(total) AS total";
                } else {
                    if (dto.getProvinceId() != null) {
                        groupBy = " GROUP BY "
//                        		+ "year,month,date,"
                        		+ " provinceId,provinceName,districtId,districtName order by "
//                        		+ " year DESC,"
                        		+ " provinceId,districtId";
                        sqlSelect = "SELECT "
//                        		+ "year,"
                        		+ " provinceId,provinceName,districtId,districtName,COUNT(DISTINCT farmId) AS 'numberOfFam', COUNT(DISTINCT animalId) AS 'numberOfAnimal', sum(total) AS total";
                    } else {
                        groupBy = " GROUP BY "
//                        		+ "year,month,date,"
                        		+ " provinceId,provinceName order by "
//                        		+ "year DESC,"
                        		+ " provinceId";
                        sqlSelect = "SELECT "
//                        		+ "year, "
                        		+ "provinceId,provinceName,COUNT(DISTINCT farmId) AS 'numberOfFam', COUNT(DISTINCT animalId) AS 'numberOfAnimal', sum(total) AS total";
                    }
                }
            }

        } else {
            groupBy = " GROUP BY "
//            		+ "year,month,date,"
            		+ " provinceId,provinceName,districtId,districtName,wardId,wardName order by "
//            		+ "year DESC,"
            		+ " provinceId,districtId,wardId";
            sqlSelect = "SELECT "
//            		+ "year,"
            		+ " provinceId,provinceName,districtId,districtName,wardId,wardName,COUNT(DISTINCT farmId) AS 'numberOfFam', COUNT(DISTINCT animalId) AS 'numberOfAnimal', sum(total) AS total";
        }

        sqlSelect += sql + whereClause + groupBy;

        Query query = manager.createNativeQuery(sqlSelect).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(AnimalReportDataReportDto.class));
//        if (dto.getYear() != null) {
//            query.setParameter("year", dto.getYear());
//        }
        if (dto.getDateReport() != null) {
            Integer yearReport = WLDateTimeUtil.getYear(dto.getDateReport());
            Integer monthReport = WLDateTimeUtil.getMonth(dto.getDateReport());
            Integer dateReport = WLDateTimeUtil.getDay(dto.getDateReport());
            query.setParameter("yearReport", yearReport);
            query.setParameter("monthReport", monthReport);
            query.setParameter("dateReport", dateReport);
        }
        if (dto.getProvinceId() != null) {
            query.setParameter("provinceId", dto.getProvinceId());
        }
        if (dto.getDistrictId() != null) {
            query.setParameter("districtId", dto.getDistrictId());
        }
        if (dto.getWardId() != null) {
            query.setParameter("wardId", dto.getWardId());
        }
        List<AnimalReportDataReportDto> result = (List<AnimalReportDataReportDto>) query.getResultList();

        return result;
    }


    public List<AnimalReportDataReportDto> getAnimalReportDataReport1(ReportParamDto dto) {
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
//		String groupByClause = "";//" GROUP BY ard.animal.id, ard.animal.name, ard.animal.code, ard.animal.scienceName "
////				+ " ,ard.animal.cites,ard.animal.vnlist,ard.animal.animalClass,ard.animal.ordo,ard.animal.family "
////				+ " ,ard.year "
////				+ " ,ard.farm.administrativeUnit.id,ard.farm.administrativeUnit.name "
////				+ " ,ard.farm.administrativeUnit.parent.id,ard.farm.administrativeUnit.parent.name "
////				+ " ,ard.farm.administrativeUnit.parent.parent.id,ard.farm.administrativeUnit.parent.parent.name ";
//		String sumGroupByClause="";
//		boolean hasGroupBy=false;
//		boolean hasSumTotalFarm = false;
//		if(dto.getGroupByItems()!=null && dto.getGroupByItems().size()>0) {
//			groupByClause+=" GROUP BY ";
//			sumGroupByClause+=" GROUP BY ";
//			hasGroupBy=true;
//		}
//		String animalName="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("animalName")) {
//			animalName="ard.animal.name";
//			groupByClause+=animalName+",";
//			hasSumTotalFarm=true;
//		}
//		String animalId="0L";//
//		if(hasGroupBy && dto.getGroupByItems().contains("animalId")) {
//			animalId="ard.animal.id";
//			groupByClause+=animalId+",";
//			hasSumTotalFarm=true;
//		}
//		String animalCode="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("animalCode")) {
//			animalCode="ard.animal.code";
//			groupByClause+=animalCode+",";
//			hasSumTotalFarm=true;
//		}
//		String scienceName="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("scienceName")) {
//			scienceName="ard.animal.scienceName";
//			groupByClause+=scienceName+",";
//			hasSumTotalFarm=true;
//		}
//		String animalGroup="''";
//		if(hasGroupBy && dto.getGroupByItems().contains("animalGroup")) {
//			animalGroup="ard.animal.animalGroup";
//			groupByClause+=animalGroup+",";
//			hasSumTotalFarm=true;
//		}
//		String cites="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("cites")) {
//			cites="ard.animal.cites";
//			groupByClause+=cites+",";
//			hasSumTotalFarm=true;
//		}
//		String vnlist="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("vnlist")) {
//			vnlist="ard.animal.vnlist";
//			groupByClause+=vnlist+",";
//			hasSumTotalFarm=true;
//		}
//		String animalClass="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("animalClass")) {
//			animalClass="ard.animal.animalClass";
//			groupByClause+=animalClass+",";
//			hasSumTotalFarm=true;
//		}
//		String ordo="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("ordo")) {
//			ordo="ard.animal.ordo";
//			groupByClause+=ordo+",";
//			hasSumTotalFarm=true;
//		}
//		String family="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("family")) {
//			family="ard.animal.family";
//			groupByClause+=family+",";
//			hasSumTotalFarm=true;
//		}
//		String year="0";//
//		if(hasGroupBy && dto.getGroupByItems().contains("year")) {
//			year="ard.year";
//			groupByClause+=year+",";
//			sumGroupByClause+=year+",";
//		}
//		String provinceId="0L";//
//		if(hasGroupBy && dto.getGroupByItems().contains("provinceId")) {
////			provinceId="ard.farm.administrativeUnit.parent.parent.id";
//			provinceId="ard.province.id";
//			groupByClause+=provinceId+",";
//			sumGroupByClause+=provinceId+",";
//		}
//		String provinceName="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("provinceName")) {
////			provinceName="ard.farm.administrativeUnit.parent.parent.name";
//			provinceName="ard.province.name";
//			groupByClause+=provinceName+",";
//			sumGroupByClause+=provinceName+",";
//		}
//		String districtId="0L";//
//		if(hasGroupBy && dto.getGroupByItems().contains("districtId")) {
////			districtId="ard.farm.administrativeUnit.parent.id";
//			districtId="ard.district.id";
//			groupByClause+=districtId+",";
//			sumGroupByClause+=districtId+",";
//		}
//		String districtName="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("districtName")) {
////			districtName="ard.farm.administrativeUnit.parent.name";
//			districtName="ard.district.name";
//			groupByClause+=districtName+",";
//			sumGroupByClause+=districtName+",";
//		}
//		String wardId="0L";//
//		if(hasGroupBy && dto.getGroupByItems().contains("wardId")) {
////			wardId="ard.farm.administrativeUnit.id";
//			wardId="ard.administrativeUnit.id";
//			groupByClause+=wardId+",";
//			sumGroupByClause+=wardId+",";
//		}
//		String wardName="''";//
//		if(hasGroupBy && dto.getGroupByItems().contains("wardName")) {
////			wardName="ard.farm.administrativeUnit.name";
//			wardName="ard.administrativeUnit.name";
//			groupByClause+=wardName+",";
//			sumGroupByClause+=wardName+",";
//		}
//		if(groupByClause.length()>0) {
//			groupByClause = groupByClause.substring(0,groupByClause.length() - 1);
//			sumGroupByClause = sumGroupByClause.substring(0,sumGroupByClause.length() - 1);
//		}
//
//
////		AnimalReportData ard = new AnimalReportData();
////		AnimalReportDataReportDto retDto = new AnimalReportDataReportDto
////		(numberOfFam, animalId, animalName, animalCode, scienceName, cites, vnlist,
////		animalClass, ordo, family, year, total, male, female, unGender,
////		provinceId, provinceName, districtId, districtName, wardId, wardName)
////																		long, long, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, int, long, long, long, long, java.lang.String, long, java.lang.String, long, java.lang.String, long
//		String sql = " SELECT new com.globits.wl.dto.functiondto.AnimalReportDataReportDto("
//				+ "	COUNT(DISTINCT ard.farm.id),"+animalId+", "+animalName+", "+animalCode+","+scienceName
//				+ " ,"+cites+","+vnlist+","+animalClass+","+ordo+","+family
//				+ " ,"+year+", SUM(ard.total), SUM(ard.male), SUM(ard.female), SUM(ard.unGender)"
//				+ " ,"+provinceId+","+provinceName
//				+ " ,"+districtId+","+districtName
//				+ " ,"+wardId+","+wardName+","+animalGroup+",COUNT(DISTINCT ard.animal.id)"
//				+ "	) "
//				+ " FROM AnimalReportData ard WHERE ard.month is null AND ard.day is null AND ard.year is not null AND ard.total!=0 ";
//		String sqlSum = " SELECT new com.globits.wl.dto.functiondto.AnimalReportDataReportDto("
//				+ "	COUNT(DISTINCT ard.farm.id),0L, '', '',''"
//				+ " ,'','','','',''"
//				+ " ,"+year+", SUM(ard.total), SUM(ard.male), SUM(ard.female), SUM(ard.unGender)"
//				+ " ,"+provinceId+","+provinceName
//				+ " ,"+districtId+","+districtName
//				+ " ,"+wardId+","+wardName+",'',COUNT(DISTINCT ard.animal.id)"
//				+ "	) "
//				+ " FROM AnimalReportData ard WHERE ard.month is null AND ard.day is null AND ard.year is not null AND ard.total!=0 ";
//
//		String whereClause = " ";
//
//		if (dto.getYear() != null) {
//			whereClause += " and (ard.year =:year )";
//		}
//		if (dto.getMonth() != null) {
//			whereClause += " and (ard.month =:month )";
//		}
//		if (dto.getDate() != null) {
//			whereClause += " and (ard.day =:day )";
//		}
//		// l???y theo l???p
//		if (dto.getAnimalClass() != null && StringUtils.hasText(dto.getAnimalClass())) {
//			whereClause += " and (ard.animal.animalClass like :animalClass )";
//		}
//		// l???y theo b???
//		if (dto.getOrdo() != null && StringUtils.hasText(dto.getOrdo())) {
//			whereClause += " and (ard.animal.ordo like :animalOrdo )";
//		}
//		// l???y theo h???
//		if (dto.getFamily() != null && StringUtils.hasText(dto.getFamily())) {
//			whereClause += " and (ard.animal.family like :animalFamily )";
//		}
//		if (dto.getListAnimalIds() != null && dto.getListAnimalIds().size() > 0) {
//			whereClause += " and (ard.animal.id in (:listAnimalIds) )";
//		}
////		if (dto.getWardId() != null) {
////			whereClause += " and (ard.farm.administrativeUnit.id =:wardId )";
////		} else if (dto.getDistrictId() != null) {
////			whereClause += " and (ard.farm.administrativeUnit.parent.id =:districtId )";
////		} else if (dto.getProvinceId() != null) {
////			whereClause += " and (ard.farm.administrativeUnit.parent.parent.id =:provinceId )";
////		}
////		if (!isAdmin) {
////			whereClause += " and (ard.farm.administrativeUnit.id in (:listWardId)) ";
////		}
//		if (dto.getWardId() != null) {
//			whereClause += " and (ard.administrativeUnit.id =:wardId )";
//		} else if (dto.getDistrictId() != null) {
//			whereClause += " and (ard.district.id =:districtId )";
//		} else if (dto.getProvinceId() != null) {
//			whereClause += " and (ard.province.id =:provinceId )";
//		}
//		if (!isAdmin) {
//			whereClause += " and (ard.administrativeUnit.id in (:listWardId)) ";
//		}
//		sql += whereClause + groupByClause;
//		sqlSum += whereClause + sumGroupByClause;
//		Query q = manager.createQuery(sql, AnimalReportDataReportDto.class);
//		Query qSum = manager.createQuery(sqlSum, AnimalReportDataReportDto.class);
//
//		if (dto.getYear() != null) {
//			q.setParameter("year", dto.getYear());
//			qSum.setParameter("year", dto.getYear());
//		}
//		if (dto.getMonth() != null) {
//			q.setParameter("month", dto.getMonth());
//			qSum.setParameter("month", dto.getMonth());
//		}
//		if (dto.getDate() != null) {
//			q.setParameter("day", dto.getDate());
//			qSum.setParameter("day", dto.getDate());
//		}
//		if (dto.getListAnimalIds() != null && dto.getListAnimalIds().size() >0) {
//			q.setParameter("listAnimalIds", dto.getListAnimalIds());
//			qSum.setParameter("listAnimalIds", dto.getListAnimalIds());
//		}
//		if (dto.getWardId() != null) {
//			q.setParameter("wardId", dto.getWardId());
//			qSum.setParameter("wardId", dto.getWardId());
//		} else if (dto.getDistrictId() != null) {
//			q.setParameter("districtId", dto.getDistrictId());
//			qSum.setParameter("districtId", dto.getDistrictId());
//		} else if (dto.getProvinceId() != null) {
//			q.setParameter("provinceId", dto.getProvinceId());
//			qSum.setParameter("provinceId", dto.getProvinceId());
//		}
//		if (!isAdmin) {
//			q.setParameter("listWardId", listWardId);
//			qSum.setParameter("listWardId", listWardId);
//		}
//		if (dto.getAnimalClass() != null && StringUtils.hasText(dto.getAnimalClass())) {
//			q.setParameter("animalClass", "%" + dto.getAnimalClass() + "%");
//			qSum.setParameter("animalClass", "%" + dto.getAnimalClass() + "%");
//		}
//		if (dto.getOrdo() != null && StringUtils.hasText(dto.getOrdo())) {
//			q.setParameter("animalOrdo", "%" + dto.getOrdo() + "%");
//			qSum.setParameter("animalOrdo", "%" + dto.getOrdo() + "%");
//		}
//		if (dto.getFamily() != null && StringUtils.hasText(dto.getFamily())) {
//			q.setParameter("animalFamily", "%" + dto.getFamily() + "%");
//			qSum.setParameter("animalFamily", "%" + dto.getFamily() + "%");
//		}
//		List<AnimalReportDataReportDto> ret = q.getResultList();
//		if(hasSumTotalFarm) {
//			List<AnimalReportDataReportDto> retSum = qSum.getResultList();
//
//			if(ret!=null && ret.size()>0 && retSum!=null && retSum.size()>0) {
//				for (AnimalReportDataReportDto sumDto : retSum) {
//					sumDto.setAnimalClass("T???ng");
//					sumDto.setAnimalCode("T???ng");
//					sumDto.setAnimalName("T???ng");
//					sumDto.setScienceName("T???ng");
//					sumDto.setCites("T???ng");
//					sumDto.setVnlist("T???ng");
//					sumDto.setOrdo("T???ng");
//					sumDto.setFamily("T???ng");
//					sumDto.setAnimalGroup("T???ng");
//					ret.add(sumDto);
//				}
//			}
//		}
        return null;
    }

    @Override
    public List<ReportAccordingToTheRedBookDto> reportNumberAnimalsAndNumberFarmsAccordingToTheRedBookNewByAdministrativeUnit(
            ReportParamDto searchDto) {

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

        List<ReportAccordingToTheRedBookDto> result = new ArrayList<ReportAccordingToTheRedBookDto>();


        String whereClause = "";
        String sql = "SELECT year, COUNT(DISTINCT farmId) AS 'totalNumberOfCamps', sum(total) AS totalNumberOfChildren, vnlist06 AS vnList06";
        String orderby = " GROUP BY year, vnlist06 ";

        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            whereClause += " AND tb.wardId = :wardId";
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            whereClause += "  AND tb.districtId = :districtId";
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            whereClause += " AND tb.provinceId = :provinceId";
        }

        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            whereClause += " AND tb.animalParentId = :animalParentId ";
        }

        if (searchDto.getListAnimalIds() != null && searchDto.getListAnimalIds().size() > 0) {
            whereClause += " AND tb.animalId in (:listAnimalIds) ";
        }
        if (searchDto.getCurrentYear() > 0) {
            whereClause += " AND tb.year = :currentYear ";
        }
        if (searchDto.getDateReport() != null) {
            whereClause += " AND (tb.year < :findYear or " +
                    "(tb.year <= :findYear ANd tb.month < :findMonth) or " +
                    "(tb.year <= :findYear ANd tb.month <= :findMonth AND tb.date <= :findDate)) AND tb.year = :findYear";
        }
        if (StringUtils.hasText(searchDto.getAnimalClass())) {
            whereClause += " AND tb.animalClass = :animalClass ";
        }
        //List animalClass
        if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
            whereClause += " AND tb.animalClass in (:listAnimalClass) ";
        }
        //
        if (StringUtils.hasText(searchDto.getOrdo())) {
            whereClause += " AND tb.animalOrdo = :animalOrdo ";
        }
        //List OrdoAnimal
        if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
            whereClause += " AND tb.animalOrdo in (:listAnimalOrdo) ";
        }
        //
        if (StringUtils.hasText(searchDto.getFamily())) {
            whereClause += " AND tb.animalFamily = :animalFamily ";
        }
        //List FamilyAnimal
        if (searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
            whereClause += " AND tb.animalFamily in (:listAnimalFamily) ";
        }
        //
        if (searchDto.getVnList06s() != null && searchDto.getVnList06s().size() > 0) {
            if (searchDto.getVnList06s().contains("T???ng")) {
                sql = "SELECT year,COUNT(DISTINCT farmId) AS 'totalNumberOfCamps', sum(total) AS totalNumberOfChildren";
                orderby = " GROUP BY year ";
            } else if (searchDto.getVnList06s().contains("Kh??ng x??c ?????nh")) {
                whereClause += " AND isnull(tb.vnlist06,'') = '' ";
            } else {
                whereClause += " AND tb.vnlist06 in (:vnList06s)";
            }
        }

        String sql1 = " FROM "
                + " (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
                + "  f.date_report AS dateReport,"
                + "  p.year,"
                + "  p.month,"
                + "  p.date,"
                + "  prov.id AS provinceId,"
                + "  prov.name AS provinceName,"
                + "  prov.code AS provinceCode,"
                + "  dis.id AS districtId,"
                + "  dis.name AS districtName,"
                + "  dis.code AS districtCode,"
                + "  w.id AS wardId,"
                + "  w.name AS wardName,"
                + "  w.code AS wardCode,"
                + "  a.vnlist06,"
                + "	 a.animal_group AS animalGroup, "
                + "  a.id AS animalId,"
                + "	 a.ordo AS animalOrdo,"
                + "  a.animal_class AS animalClass,"
                + "  a.family AS animalFamily,"
                + "  fa.id AS farmId,"
                + "  f.total AS total"
                + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
                + "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
                + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
                + "  AND prov.parent_id IS NULL"
                + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
                + "  AND dis.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
                + "  AND w.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//				+"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//				"   AND w.parent_id IS NOT NULL" +
//				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//				"   AND dis.parent_id IS NOT NULL"
                + " ) as tb where tb.rowNumber=1 and tb.total<>0 " + whereClause;
        Query q = manager.createNativeQuery(sql + sql1 + orderby).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(ReportAccordingToTheRedBookDto.class));


        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            q.setParameter("wardId", searchDto.getWardId());
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            q.setParameter("districtId", searchDto.getDistrictId());

        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            q.setParameter("provinceId", searchDto.getProvinceId());

        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            q.setParameter("animalParentId", searchDto.getAnimalParentId());

        }

        if (searchDto.getListAnimalIds() != null && searchDto.getListAnimalIds().size() > 0) {
            q.setParameter("listAnimalIds", searchDto.getListAnimalIds());

        }
        if (searchDto.getCurrentYear() > 0) {
            q.setParameter("currentYear", searchDto.getCurrentYear());

        }
        if (searchDto.getDateReport() != null) {
            Integer findYear = WLDateTimeUtil.getYear(searchDto.getDateReport());
            Integer findMonth = WLDateTimeUtil.getMonth(searchDto.getDateReport());
            Integer findDate = WLDateTimeUtil.getDay(searchDto.getDateReport());
            q.setParameter("findYear", findYear);
            q.setParameter("findMonth", findMonth);
            q.setParameter("findDate", findDate);
        }
        if (StringUtils.hasText(searchDto.getAnimalClass())) {
            q.setParameter("animalClass", searchDto.getAnimalClass());

        }
        //list animal class
        if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
            q.setParameter("listAnimalClass", searchDto.getListAnimalClass());

        }
        if (StringUtils.hasText(searchDto.getOrdo())) {
            q.setParameter("animalOrdo", searchDto.getOrdo());

        }
        //list ordo
        if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
            q.setParameter("listAnimalOrdo", searchDto.getListAnimalOrdo());

        }

        if (StringUtils.hasText(searchDto.getFamily())) {
            q.setParameter("animalFamily", searchDto.getFamily());

        }
        //list family animal
        if (searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
            q.setParameter("listAnimalFamily", searchDto.getListAnimalFamily());
        }

//		if(searchDto.getVnlist06()!=null && searchDto.getVnlist06().equals("T???ng")==false && searchDto.getVnlist06().equals("Kh??ng x??c ?????nh")==false) {
//			q.setParameter("vnList06s", searchDto.getVnlist06());
//
//		}
        if (searchDto.getVnList06s() != null && searchDto.getVnList06s().size() > 0 && searchDto.getVnList06s().contains("T???ng") == false && searchDto.getVnList06s().contains("Kh??ng x??c ?????nh") == false) {
            q.setParameter("vnList06s", searchDto.getVnList06s());
        }
        result = q.getResultList();
        if (result != null && result.size() > 0) {
            for (ReportAccordingToTheRedBookDto reportAccordingToTheRedBookDto : result) {
                if (reportAccordingToTheRedBookDto.getVnList06() == null
                        || !StringUtils.hasText(reportAccordingToTheRedBookDto.getVnList06())) {
                    reportAccordingToTheRedBookDto.setVnList06("Kh??ng x??c ?????nh");
                } else if (searchDto.getVnlist06() != null && searchDto.getVnlist06().equals("T???ng")) {
                    reportAccordingToTheRedBookDto.setVnList06("T???ng");
                }

            }
        }

        if (searchDto.getVnlist06() == null) {
            whereClause = "";
            sql = "SELECT year, COUNT(DISTINCT farmId) AS 'totalNumberOfCamps', sum(total) AS totalNumberOfChildren";
            orderby = " GROUP BY year ";
            if (searchDto.getWardId() != null) {// ????y l?? query theo x??
                whereClause += " AND tb.wardId = :wardId";
            }
            if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
                whereClause += "  AND tb.districtId = :districtId";
            }
            if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
                whereClause += " AND tb.provinceId = :provinceId";
            }

            if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
                whereClause += " AND tb.animalParentId = :animalParentId ";
            }

            if (searchDto.getListAnimalIds() != null && searchDto.getListAnimalIds().size() > 0) {
                whereClause += " AND tb.animalId in (:listAnimalIds) ";
            }
            if (searchDto.getCurrentYear() > 0) {
                whereClause += " AND tb.year = :currentYear ";
            }
            if (searchDto.getDateReport() != null) {
                whereClause += " AND (tb.year < :findYear or " +
                        "(tb.year <= :findYear ANd tb.month < :findMonth) or " +
                        "(tb.year <= :findYear ANd tb.month <= :findMonth AND tb.date <= :findDate))   AND tb.year = :findYear";
            }
            if (StringUtils.hasText(searchDto.getAnimalClass())) {
                whereClause += " AND tb.animalClass = :animalClass ";
            }
            //List animalClass
            if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
                whereClause += " AND tb.animalClass in (:listAnimalClass) ";
            }
            //

            if (StringUtils.hasText(searchDto.getOrdo())) {
                whereClause += " AND tb.animalOrdo = :animalOrdo ";
            }
            //List OrdoAnimal
            if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
                whereClause += " AND tb.animalOrdo in (:listAnimalOrdo) ";
            }
            //
            if (StringUtils.hasText(searchDto.getFamily())) {
                whereClause += " AND tb.animalFamily = :animalFamily ";
            }
            //List FamilyAnimal
            if (searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
                whereClause += " AND tb.animalFamily in (:listAnimalFamily) ";
            }
            //
            sql1 = " FROM (SELECT farmId,total,vnlist06,year FROM"
                    + "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
                    + "  f.date_report AS dateReport,"
                    + "  p.year,"
                    + "  p.month,"
                    + "  p.date,"
                    + "  prov.id AS provinceId,"
                    + "  prov.name AS provinceName,"
                    + "  prov.code AS provinceCode,"
                    + "  dis.id AS districtId,"
                    + "  dis.name AS districtName,"
                    + "  dis.code AS districtCode,"
                    + "  w.id AS wardId,"
                    + "  w.name AS wardName,"
                    + "  w.code AS wardCode,"
                    + "  a.vnlist06,"
                    + "	 a.animal_group AS animalGroup, "
                    + "  a.id AS animalId,"
                    + "	 a.ordo AS animalOrdo,"
                    + "  a.animal_class AS animalClass,"
                    + "  a.family AS animalFamily,"
                    + "  fa.id AS farmId,"
                    + "  f.total AS total"
                    + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
                    + "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
                    + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
                    + "  AND prov.parent_id IS NULL"
                    + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
                    + "  AND dis.parent_id IS NOT NULL"
                    + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
                    + "  AND w.parent_id IS NOT NULL"
                    + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//						+"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//						"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//						"   AND w.parent_id IS NOT NULL" +
//						"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//						"   AND dis.parent_id IS NOT NULL"
                    + " ) as tb where tb.rowNumber=1 and tb.total<>0 " + whereClause + " ) as t ) as tb1 ";
            q = manager.createNativeQuery(sql + sql1 + orderby).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(ReportAccordingToTheRedBookDto.class));


            if (searchDto.getWardId() != null) {// ????y l?? query theo x??
                q.setParameter("wardId", searchDto.getWardId());
            }
            if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
                q.setParameter("districtId", searchDto.getDistrictId());

            }
            if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
                q.setParameter("provinceId", searchDto.getProvinceId());

            }
            if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
                q.setParameter("animalParentId", searchDto.getAnimalParentId());

            }

            if (searchDto.getListAnimalIds() != null && searchDto.getListAnimalIds().size() > 0) {
                q.setParameter("listAnimalIds", searchDto.getListAnimalIds());

            }
            if (searchDto.getCurrentYear() > 0) {
                q.setParameter("currentYear", searchDto.getCurrentYear());

            }
            if (searchDto.getDateReport() != null) {
                Integer findYear = WLDateTimeUtil.getYear(searchDto.getDateReport());
                Integer findMonth = WLDateTimeUtil.getMonth(searchDto.getDateReport());
                Integer findDate = WLDateTimeUtil.getDay(searchDto.getDateReport());
                q.setParameter("findYear", findYear);
                q.setParameter("findMonth", findMonth);
                q.setParameter("findDate", findDate);
            }
            if (StringUtils.hasText(searchDto.getAnimalClass())) {
                q.setParameter("animalClass", searchDto.getAnimalClass());

            }
            //list animal class
            if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
                q.setParameter("listAnimalClass", searchDto.getListAnimalClass());

            }
            //
            if (StringUtils.hasText(searchDto.getOrdo())) {
                q.setParameter("animalOrdo", searchDto.getOrdo());

            }
            //list ordo
            if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
                q.setParameter("listAnimalOrdo", searchDto.getListAnimalOrdo());

            }
            if (StringUtils.hasText(searchDto.getFamily())) {
                q.setParameter("animalFamily", searchDto.getFamily());

            }
            //list family animal
            if (searchDto.getListAnimalFamily() != null) {
                q.setParameter("listAnimalFamily", searchDto.getListAnimalFamily());
            }
            //

            List<ReportAccordingToTheRedBookDto> result1 = q.getResultList();
            if (result1 != null && result1.size() > 0) {
                for (ReportAccordingToTheRedBookDto reportAccordingToTheRedBookDto : result1) {

                    reportAccordingToTheRedBookDto.setVnList06("T???ng");

                }
            }
            result.addAll(result1);
        }
        return result;
    }

    @Override
    public List<ReportQuantityByCategoryCitesDto> reportNumberAnimalsAndNumberFarmsCategoryCitestNewByAdministrativeUnit(
            ReportParamDto searchDto) {

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
        List<ReportQuantityByCategoryCitesDto> result = new ArrayList<ReportQuantityByCategoryCitesDto>();

//		List<ReportQuantityByCategoryCitesDto> ret = new ArrayList<ReportQuantityByCategoryCitesDto>();
//		List<ReportQuantityByCategoryCitesDto> retSum = new ArrayList<ReportQuantityByCategoryCitesDto>();

//		String sql = " SELECT new com.globits.wl.dto.functiondto.ReportQuantityByCategoryCitesDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), a.cites, ard.year) from AnimalReportData ard, "
//				+ " Animal a WHERE a.id = ard.animal.id AND ard.year is not null AND ard.month is null AND ard.day is null AND ard.total!=0 AND ard.total!=null ";
//		String sqlSum = " SELECT new com.globits.wl.dto.functiondto.ReportQuantityByCategoryCitesDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), '', ard.year) from AnimalReportData ard, "
//				+ " Animal a WHERE a.id = ard.animal.id AND ard.year is not null AND ard.month is null AND ard.day is null AND ard.total!=0 AND ard.total!=null ";
        String whereClause = "";
        String sql = "SELECT year,COUNT(DISTINCT farmId) AS 'totalNumberOfCamps', sum(total) AS totalNumberOfChildren, cites";
        String orderby = " GROUP BY year,cites ";
        if (searchDto.getProvinceId() != null) {
            whereClause += " AND tb.provinceId = :provinceId";
        }
        if (searchDto.getDistrictId() != null) {
            whereClause += " AND tb.districtId = :districtId";
        }
        if (searchDto.getWardId() != null) {
            whereClause += " AND tb.wardId = :wardId";
        }
        if (searchDto.getListCites() != null && searchDto.getListCites().size() > 0) {
            if (searchDto.getListCites().contains("T???ng")) {
                sql = "SELECT year,COUNT(DISTINCT farmId) AS 'totalNumberOfCamps', sum(total) AS totalNumberOfChildren";
                orderby = " GROUP BY year ";
            } else {
                whereClause += " AND tb.cites in (:listCites)";
            }
        }
        String sql1 = " FROM "
                + " (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
                + "  f.date_report AS dateReport,"
                + "  p.year,"
                + "  prov.id AS provinceId,"
                + "  prov.name AS provinceName,"
                + "  prov.code AS provCode,"
                + "  dis.id AS districtId,"
                + "  dis.name AS districtName,"
                + "  dis.code AS disCode,"
                + "  w.id AS wardId,"
                + "  w.name AS wardName,"
                + "  w.code AS wardCode,"
                + "  a.cites, "
                + "  fa.id AS farmId,"
                + "  f.total AS total"
                + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
                + "  INNER JOIN (select * from tbl_animal where tbl_animal.cites != N'EN' OR tbl_animal.cites IS NULL) a ON a.id = f.animal_id"
                //+ "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
                + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
                + "  AND prov.parent_id IS NULL"
                + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
                + "  AND dis.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
                + "  AND w.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//				+"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//				"   AND w.parent_id IS NOT NULL" +
//				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//				"   AND dis.parent_id IS NOT NULL"
                + " ) as tb where tb.rowNumber=1 and tb.total<>0 " + whereClause;
        Query query = manager.createNativeQuery(sql + sql1 + orderby).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(ReportQuantityByCategoryCitesDto.class));


        if (searchDto.getProvinceId() != null) {
            query.setParameter("provinceId", searchDto.getProvinceId());
        }
        if (searchDto.getDistrictId() != null) {
            query.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getWardId() != null) {
            query.setParameter("wardId", searchDto.getWardId());
        }
//        if (searchDto.getListCites() != null && searchDto.getListCites().size() > 0) {
//            if (searchDto.getListCites().contains("T???ng")) {
//            } else {
//                query.setParameter("listCites", searchDto.getListCites());
//            }
//        }

        if (searchDto.getListCites() != null && searchDto.getListCites().size() > 0 && searchDto.getListCites().contains("T???ng") == false && searchDto.getListCites().contains("Kh??ng x??c ?????nh") == false) {
            query.setParameter("listCites", searchDto.getListCites());
        }
        result = query.getResultList();
        if (result != null && result.size() > 0) {
            for (ReportQuantityByCategoryCitesDto reportQuantityByCategoryCitesDto : result) {
                if (reportQuantityByCategoryCitesDto.getCites() == null
                        || !StringUtils.hasText(reportQuantityByCategoryCitesDto.getCites())) {
                    reportQuantityByCategoryCitesDto.setCites("Kh??ng x??c ?????nh");
                } else if (searchDto.getCites() != null && searchDto.getCites().equals("T???ng")) {
                    reportQuantityByCategoryCitesDto.setCites("T???ng");
                }

            }
        }


        if (searchDto.getCites() == null) {
            whereClause = "";
            sql = "SELECT year, COUNT(DISTINCT farmId) AS 'totalNumberOfCamps', sum(total) AS totalNumberOfChildren";
            orderby = " GROUP BY year ";
            if (searchDto.getWardId() != null) {// ????y l?? query theo x??
                whereClause += " AND tb.wardId = :wardId";
            }
            if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
                whereClause += "  AND tb.districtId = :districtId";
            }
            if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
                whereClause += " AND tb.provinceId = :provinceId";
            }

            if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
                whereClause += " AND tb.animalParentId = :animalParentId ";
            }

            if (searchDto.getListAnimalIds() != null && searchDto.getListAnimalIds().size() > 0) {
                whereClause += " AND tb.animalId in (:listAnimalIds) ";
            }
            if (searchDto.getCurrentYear() > 0) {
                whereClause += " AND tb.year = :currentYear ";
            }
            if (searchDto.getDateReport() != null) {
                whereClause += " AND (tb.year < :findYear or " +
                        "(tb.year <= :findYear ANd tb.month < :findMonth) or " +
                        "(tb.year <= :findYear ANd tb.month <= :findMonth AND tb.date <= :findDate))   AND tb.year = :findYear";
            }
            if (StringUtils.hasText(searchDto.getAnimalClass())) {
                whereClause += " AND tb.animalClass = :animalClass ";
            }
            //List animalClass
            if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
                whereClause += " AND tb.animalClass in (:listAnimalClass) ";
            }
            //

            if (StringUtils.hasText(searchDto.getOrdo())) {
                whereClause += " AND tb.animalOrdo = :animalOrdo ";
            }
            //List OrdoAnimal
            if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
                whereClause += " AND tb.animalOrdo in (:listAnimalOrdo) ";
            }
            //
            if (StringUtils.hasText(searchDto.getFamily())) {
                whereClause += " AND tb.animalFamily = :animalFamily ";
            }
            //List FamilyAnimal
            if (searchDto.getListAnimalFamily() != null && searchDto.getListAnimalFamily().size() > 0) {
                whereClause += " AND tb.animalFamily in (:listAnimalFamily) ";
            }
            //
            sql1 = " FROM (SELECT farmId,total,cites,year FROM"
                    + "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
                    + "  f.date_report AS dateReport,"
                    + "  p.year,"
                    + "  p.month,"
                    + "  p.date,"
                    + "  prov.id AS provinceId,"
                    + "  prov.name AS provinceName,"
                    + "  prov.code AS provinceCode,"
                    + "  dis.id AS districtId,"
                    + "  dis.name AS districtName,"
                    + "  dis.code AS districtCode,"
                    + "  w.id AS wardId,"
                    + "  w.name AS wardName,"
                    + "  w.code AS wardCode,"
                    + "  a.cites,"
                    + "	 a.animal_group AS animalGroup, "
                    + "  a.id AS animalId,"
                    + "	 a.ordo AS animalOrdo,"
                    + "  a.animal_class AS animalClass,"
                    + "  a.family AS animalFamily,"
                    + "  fa.id AS farmId,"
                    + "  f.total AS total"
                    + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
                    + "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
                    + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
                    + "  AND prov.parent_id IS NULL"
                    + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
                    + "  AND dis.parent_id IS NOT NULL"
                    + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
                    + "  AND w.parent_id IS NOT NULL"
                    + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//						+"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//						"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//						"   AND w.parent_id IS NOT NULL" +
//						"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//						"   AND dis.parent_id IS NOT NULL"
                    + " ) as tb where tb.rowNumber=1 and tb.total<>0 " + whereClause + " ) as t ) as tb1 ";
            query = manager.createNativeQuery(sql + sql1 + orderby).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(ReportQuantityByCategoryCitesDto.class));


            if (searchDto.getWardId() != null) {// ????y l?? query theo x??
                query.setParameter("wardId", searchDto.getWardId());
            }
            if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
                query.setParameter("districtId", searchDto.getDistrictId());

            }
            if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
                query.setParameter("provinceId", searchDto.getProvinceId());

            }
            if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
                query.setParameter("animalParentId", searchDto.getAnimalParentId());

            }

            if (searchDto.getListAnimalIds() != null && searchDto.getListAnimalIds().size() > 0) {
                query.setParameter("listAnimalIds", searchDto.getListAnimalIds());

            }
            if (searchDto.getCurrentYear() > 0) {
                query.setParameter("currentYear", searchDto.getCurrentYear());

            }
            if (searchDto.getDateReport() != null) {
                Integer findYear = WLDateTimeUtil.getYear(searchDto.getDateReport());
                Integer findMonth = WLDateTimeUtil.getMonth(searchDto.getDateReport());
                Integer findDate = WLDateTimeUtil.getDay(searchDto.getDateReport());
                query.setParameter("findYear", findYear);
                query.setParameter("findMonth", findMonth);
                query.setParameter("findDate", findDate);
            }
            if (StringUtils.hasText(searchDto.getAnimalClass())) {
                query.setParameter("animalClass", searchDto.getAnimalClass());

            }
            //list animal class
            if (searchDto.getListAnimalClass() != null && searchDto.getListAnimalClass().size() > 0) {
                query.setParameter("listAnimalClass", searchDto.getListAnimalClass());

            }
            //
            if (StringUtils.hasText(searchDto.getOrdo())) {
                query.setParameter("animalOrdo", searchDto.getOrdo());

            }
            //list ordo
            if (searchDto.getListAnimalOrdo() != null && searchDto.getListAnimalOrdo().size() > 0) {
                query.setParameter("listAnimalOrdo", searchDto.getListAnimalOrdo());

            }
            if (StringUtils.hasText(searchDto.getFamily())) {
                query.setParameter("animalFamily", searchDto.getFamily());

            }
            //list family animal
            if (searchDto.getListAnimalFamily() != null) {
                query.setParameter("listAnimalFamily", searchDto.getListAnimalFamily());
            }
            //

            List<ReportQuantityByCategoryCitesDto> result1 = query.getResultList();
            if (result1 != null && result1.size() > 0) {
                for (ReportQuantityByCategoryCitesDto reportQuantityByCategoryCitesDto : result1) {
                    reportQuantityByCategoryCitesDto.setCites("T???ng");

                }
            }
            result.addAll(result1);
        }
        return result;
    }

//		sql += whereClause;
//		sqlSum += whereClause;
//		Query q = manager.createQuery(sql+groupByClause, ReportQuantityByCategoryCitesDto.class);
//		Query qSum = manager.createQuery(sqlSum+"GROUP BY ard.year", ReportQuantityByCategoryCitesDto.class);

//		if (searchDto.getFarmId() != null) {
//			q.setParameter("farmId", searchDto.getFarmId());
//			qSum.setParameter("farmId", searchDto.getFarmId());
//		}
//		if (searchDto.getWardId() != null) {// ????y l?? query theo x??
//			q.setParameter("wardsId", searchDto.getWardId());
//			qSum.setParameter("wardsId", searchDto.getWardId());
//		}
//		if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
//			q.setParameter("districtId", searchDto.getDistrictId());
//			qSum.setParameter("districtId", searchDto.getDistrictId());
//		}
//		if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
//			q.setParameter("cityId", searchDto.getProvinceId());
//			qSum.setParameter("cityId", searchDto.getProvinceId());
//		}
//		if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
//			q.setParameter("regionId", searchDto.getRegionId());
//			qSum.setParameter("regionId", searchDto.getRegionId());
//		}
//		if (!isAdmin) {
//			q.setParameter("listWardId", listWardId);
//			qSum.setParameter("listWardId", listWardId);
//		}
//		if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
//			q.setParameter("animalParentId", searchDto.getAnimalParentId());
//			qSum.setParameter("animalParentId", searchDto.getAnimalParentId());
//		}
//		if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
//			q.setParameter("productTargetId", searchDto.getProductTargetId());
//			qSum.setParameter("productTargetId", searchDto.getProductTargetId());
//		}
//		ret = q.getResultList();
//		retSum = qSum.getResultList();
//		if (ret != null && ret.size() > 0) {
//			for (ReportQuantityByCategoryCitesDto reportQuantityByCategoryCitesDto : ret) {
//				if (reportQuantityByCategoryCitesDto.getCites() == null
//						|| !StringUtils.hasText(reportQuantityByCategoryCitesDto.getCites())) {
//					reportQuantityByCategoryCitesDto.setCites("Kh??ng x??c ?????nh");
//				}
//			}
//		}
//
//		if(retSum!=null && retSum.size()>0) {
//			for (ReportQuantityByCategoryCitesDto sumDto : retSum) {
//				sumDto.setCites("T???ng");
//				ret.add(sumDto);
//			}
//		}
//        List<ReportQuantityByCategoryCitesDto> ret = query.getResultList();
//        if (searchDto.getListCites() != null && searchDto.getListCites().size() > 0) {
//            if (searchDto.getListCites().contains("T???ng")) {
//                for (ReportQuantityByCategoryCitesDto s : ret) {
//                    s.setCites("T???ng");
//                }
//            }
//        }
//        return ret;


    @Override
    public List<Report18Dto> reportCurrentStatusByFamilyNewByAdministrativeUnit(
            AnimalReportDataSearchDto dto) {
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

        String sql = "SELECT * " +
                " FROM " +
                "  (SELECT ROW_NUMBER() OVER (PARTITION BY f.farm_id," +
                "                                          f.animal_id,p.year" +
                "                             ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc) AS rowNumber," +
                "                            f.date_report AS dateReport," +
                "                            p.year," +
                "                            p.month," +
                "                            p.date," +
                "                            prov.id AS provId," +
                "                            prov.name AS provName," +
                "                            prov.code AS provCode," +
                "                            dis.id AS disId," +
                "                            dis.name AS disName," +
                "                            dis.code AS disCode," +
                "                            w.id AS wardId," +
                "                            w.name AS wardName," +
                "                            w.code AS wardCode," +
                "                            a.id AS animalId," +
                "                            a.name AS animalName," +
                "                            a.science_name AS animalSciName," +
                "                            a.other_name AS animalOtherName," +
                "                            a.code AS animalCode," +
                "                            a.ordo AS animalOrdo," +
                "                            a.animal_class AS animalClass," +
                "                            a.family AS animalFamily," +
                "                            a.cites," +
                "                            a.vnlist," +
                "                            a.vnlist06," +
                "							 a.animal_group as animalGroup, " +
                "                            fa.id AS farmId," +
                "                            fa.name AS farmName," +
                "                            fa.code AS farmCode," +
                "                            fa.new_registration_code AS farmNewRegistrationCode," +
                "                            fa.old_registration_code AS farmOldRegistrationCode," +
                "                            fa.date_registration AS farmDateRegistration," +
                "                            fa.longitude AS farmLongitude," +
                "                            fa.latitude AS farmLatitude," +
                "                            fa.village AS farmVillage," +
                "                            f.total AS total," +
                "							 f.male_parent + f.female_parent AS totalParent," +
                "                            f.male_parent AS maleParent," +
                "                            f.female_parent AS femaleParent," +
                "							 f.male_gilts + f.female_gilts AS totalGilts," +
                "                            f.male_gilts AS maleGilts," +
                "                            f.female_gilts AS femaleGilts," +
                "                            f.male_child_under_1year_old + f.female_child_under_1year_old + f.child_under_1year_old AS totalChildUnder1YO," +
//				"                            f.male_child_over_1year_old + f.female_child_over_1year_old + f.un_gender_child_over_1year_old AS totalChildOver1YO," +
                "                            f.male_child_over_1year_old AS maleChildOver1YearOld," +
                "                            f.female_child_over_1year_old AS femaleChildOver1YearOld," +
                "                            f.un_gender_child_over_1year_old AS unGenderChildOver1YearOld," +
                "                            fa.founding_date AS farmFoundingDate" +
                "   FROM tbl_report_form16 f" +
                "   INNER JOIN tbl_report_period p ON f.report_period_id = p.id" +
                "   INNER JOIN tbl_animal a ON a.id = f.animal_id" +
                "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id" +
                "  AND prov.parent_id IS NULL" +
                "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id" +
                "  AND dis.parent_id IS NOT NULL" +
                "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id" +
                "  AND w.parent_id IS NOT NULL" +
                "   INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)" +
//				"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//				"   AND w.parent_id IS NOT NULL" +
//				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//				"   AND dis.parent_id IS NOT NULL"
                "  ) AS tb1";

        String whereClause = " where tb1.rowNumber=1 and tb1.total<>0 ";
//		String orderBy = " ORDER BY ard.province.name asc, ard.district.name asc, ard.administrativeUnit.name asc, ard.farm.code asc, ard.farm.name asc ";

//        if (dto.getYear() != null) {
//            whereClause += " and tb1.year = :year ";
//        }
        if (dto.getDateReport() != null) {
            whereClause += " AND (tb1.year < :findYear or " +
                    "(tb1.year <= :findYear ANd tb1.month < :findMonth) or " +
                    "(tb1.year <= :findYear ANd tb1.month <= :findMonth AND tb1.date <= :findDate)) AND tb1.year = :findYear";
        }
        if (dto.getAnimalId() != null) {
            whereClause += " AND tb1.animalId = :animalId";
        }
        //list  
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            whereClause += " AND tb1.animalId in (:animalIds)";
        }

        if (dto.getAnimalOrdo() != null) {
            whereClause += " AND tb1.animalOrdo = :animalOrdo";
        }
        //list  
        if (dto.getListAnimalOrdo() != null && dto.getListAnimalOrdo().size() > 0) {
            whereClause += " AND tb1.animalOrdo in (:listAnimalOrdo)";
        }
        if (dto.getAnimalClass() != null) {
            whereClause += " AND tb1.animalClass = :animalClass";
        }
        //list  
        if (dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
            whereClause += " AND tb1.animalClass in (:listAnimalClass)";
        }
        if (dto.getAnimalFamily() != null) {
            whereClause += " AND tb1.animalFamily = :animalFamily";
        }
        //list
        if (dto.getListAnimalFamily() != null && dto.getListAnimalFamily().size() > 0) {
            whereClause += " AND tb1.animalFamily in (:listAnimalFamily)";
        }
        if (dto.getFarmId() != null) {
            whereClause += " AND tb1.farmId = :farmId";
        }

        if (dto.getAnimalCites() != null) {
            whereClause += " AND tb1.cites = :animalCites";
        }
        //list 
        if (dto.getListAnimalCites() != null && dto.getListAnimalCites().size() > 0) {
            whereClause += " AND tb1.cites in (:listAnimalCites)";
        }
        if (dto.getAnimalVnlist() != null) {
            whereClause += " AND tb1.vnlist = :animalVnlist";
        }
        //list
        if (dto.getListAnimalVnlist() != null && dto.getListAnimalVnlist().size() > 0) {
            whereClause += " AND tb1.vnlist in (:listAnimalVnlist)";
        }
        if (dto.getAnimalVnlist06() != null) {
            whereClause += " AND tb1.vnlist06 = :animalVnlist06";
        }
        //list
        if (dto.getListAnimalVnlist06() != null && dto.getListAnimalVnlist06().size() > 0) {
            whereClause += " AND tb1.vnlist06 in (:listAnimalVnlist06)";
        }
        if (dto.getProvinceId() != null) {
            whereClause += " AND tb1.provId = :provinceId";
        }
        if (dto.getDistrictId() != null) {
            whereClause += " AND tb1.disId = :districtId";
        }
        if (dto.getCommuneId() != null) {
            whereClause += " AND tb1.wardId = :communeId";
        }
        if (dto.getAnimalGroup() != null) {
            whereClause += " AND tb1.animalGroup = :animalGroup";
        }
        //list
        if (dto.getListAnimalGroup() != null && dto.getListAnimalGroup().size() > 0) {
            whereClause += " AND tb1.animalGroup in (:listAnimalGroup)";
        }
        sql += whereClause;

        Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(Report18Dto.class));
//
//        if (dto.getYear() != null) {
//            query.setParameter("year", dto.getYear());
//        }
        if (dto.getDateReport() != null) {
            Integer findYear = WLDateTimeUtil.getYear(dto.getDateReport());
            Integer findMonth = WLDateTimeUtil.getMonth(dto.getDateReport());
            Integer findDate = WLDateTimeUtil.getDay(dto.getDateReport());
            query.setParameter("findYear", findYear);
            query.setParameter("findMonth", findMonth);
            query.setParameter("findDate", findDate);
        }

        if (dto.getAnimalId() != null) {
            query.setParameter("animalId", dto.getAnimalId());
        }
        //list
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            query.setParameter("animalIds", dto.getAnimalIds());
        }
        if (dto.getFarmId() != null) {
            query.setParameter("farmId", dto.getFarmId());
        }
        if (dto.getProvinceId() != null) {
            query.setParameter("provinceId", dto.getProvinceId());
        }
        if (dto.getDistrictId() != null) {
            query.setParameter("districtId", dto.getDistrictId());
        }
        if (dto.getCommuneId() != null) {
            query.setParameter("communeId", dto.getCommuneId());
        }
        if (dto.getAnimalClass() != null) {
            query.setParameter("animalClass", dto.getAnimalClass());
        }
        //list 
        if (dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
            query.setParameter("listAnimalClass", dto.getListAnimalClass());
        }
        if (dto.getAnimalOrdo() != null) {
            query.setParameter("animalOrdo", dto.getAnimalOrdo());
        }
        //list
        if (dto.getListAnimalOrdo() != null && dto.getListAnimalOrdo().size() > 0) {
            query.setParameter("listAnimalOrdo", dto.getListAnimalOrdo());
        }
        if (dto.getAnimalFamily() != null) {
            query.setParameter("animalFamily", dto.getAnimalFamily());
        }
        //list
        if (dto.getListAnimalFamily() != null && dto.getListAnimalFamily().size() > 0) {
            query.setParameter("listAnimalFamily", dto.getListAnimalFamily());
        }
        if (dto.getAnimalCites() != null) {
            query.setParameter("animalCites", dto.getAnimalCites());
        }
        //list
        if (dto.getListAnimalCites() != null && dto.getListAnimalCites().size() > 0) {
            query.setParameter("listAnimalCites", dto.getListAnimalCites());
        }
        if (dto.getAnimalVnlist() != null) {
            query.setParameter("animalVnlist", dto.getAnimalVnlist());
        }
        //list
        if (dto.getListAnimalVnlist() != null && dto.getListAnimalVnlist().size() > 0) {
            query.setParameter("listAnimalVnlist", dto.getListAnimalVnlist());
        }
        if (dto.getAnimalVnlist06() != null) {
            query.setParameter("animalVnlist06", dto.getAnimalVnlist06());
        }
        //list
        if (dto.getListAnimalVnlist06() != null && dto.getListAnimalVnlist06().size() > 0) {
            query.setParameter("listAnimalVnlist06", dto.getListAnimalVnlist06());
        }
        if (dto.getAnimalGroup() != null) {
            query.setParameter("animalGroup", dto.getAnimalGroup());
        }
        //list
        if (dto.getListAnimalGroup() != null && dto.getListAnimalGroup().size() > 0) {
            query.setParameter("listAnimalGroup", dto.getListAnimalGroup());
        }
        List<Report18Dto> result = (List<Report18Dto>) query.getResultList();

        return result;
    }

    @Override
    public List<ReportAccordingToTheRedBookDto> reportNumberAnimalsAndNumberFarmsAccordingToTheRedBookNewByReportForm16AndAdministrativeUnit(
            ReportParamDto searchDto) {

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

        List<ReportAccordingToTheRedBookDto> ret = new ArrayList<ReportAccordingToTheRedBookDto>();
        List<ReportAccordingToTheRedBookDto> retSum = new ArrayList<ReportAccordingToTheRedBookDto>();
        String sql = " SELECT new com.globits.wl.dto.functiondto.ReportAccordingToTheRedBookDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), a.vnlist06, ard.reportPeriod.year) from ReportForm16 ard, "
                + " Animal a WHERE a.id = ard.animal.id AND ard.dateReport is not null AND ard.total>0 ";
        String sqlSum = " SELECT new com.globits.wl.dto.functiondto.ReportAccordingToTheRedBookDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), '', ard.reportPeriod.year) from ReportForm16 ard, "
                + " Animal a WHERE a.id = ard.animal.id AND ard.dateReport is not null AND ard.total>0 ";
        String whereClause = "";
        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            whereClause += " and (ard.administrativeUnit.id= :wardsId)";
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            whereClause += " and (ard.district.id= :districtId)";
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            whereClause += " and (ard.province.id= :cityId)";
        }
        if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
            whereClause += " and (ard.farm.administrativeUnit.parent.parent.region.id= :regionId)";
        }
        if (!isAdmin) {
            whereClause += " and (ard.farm.administrativeUnit.id in (:listWardId)) ";
        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            whereClause += " AND ard.animal.parent.id = :animalParentId ";
        }
		/*if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
			whereClause += " AND ard.productTarget.id = :productTargetId ";
		}*/

        if (searchDto.getListAnimalIds() != null) {
            whereClause += " AND ard.animal.id in (:listAnimalIds) ";
        }
        if (searchDto.getCurrentYear() > 0) {
            whereClause += " AND ard.year = :currentYear ";
        }
        if (StringUtils.hasText(searchDto.getAnimalClass())) {
            whereClause += " AND ard.animal.animalClass = :animalClass ";
        }
        if (StringUtils.hasText(searchDto.getOrdo())) {
            whereClause += " AND ard.animal.ordo = :ordo ";
        }
        if (StringUtils.hasText(searchDto.getFamily())) {
            whereClause += " AND ard.animal.family = :family ";
        }
        sql += whereClause;
        sqlSum += whereClause;
        Query q = manager.createQuery(sql + " GROUP BY a.vnlist06,  ard.reportPeriod.year", ReportAccordingToTheRedBookDto.class);
        Query qSum = manager.createQuery(sqlSum + " GROUP BY  ard.reportPeriod.year", ReportAccordingToTheRedBookDto.class);
        if (searchDto.getFarmId() != null) {
            q.setParameter("farmId", searchDto.getFarmId());
            qSum.setParameter("farmId", searchDto.getFarmId());
        }
        if (searchDto.getWardId() != null) {// ????y l?? query theo x??
            q.setParameter("wardsId", searchDto.getWardId());
            qSum.setParameter("wardsId", searchDto.getWardId());
        }
        if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
            q.setParameter("districtId", searchDto.getDistrictId());
            qSum.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
            q.setParameter("cityId", searchDto.getProvinceId());
            qSum.setParameter("cityId", searchDto.getProvinceId());
        }
        if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
            q.setParameter("regionId", searchDto.getRegionId());
            qSum.setParameter("regionId", searchDto.getRegionId());
        }
        if (!isAdmin) {
            q.setParameter("listWardId", listWardId);
            qSum.setParameter("listWardId", listWardId);
        }
        if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
            q.setParameter("animalParentId", searchDto.getAnimalParentId());
            qSum.setParameter("animalParentId", searchDto.getAnimalParentId());
        }
		/*if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
			q.setParameter("productTargetId", searchDto.getProductTargetId());
			qSum.setParameter("productTargetId", searchDto.getProductTargetId());
		}*/
        if (searchDto.getListAnimalIds() != null) {
            q.setParameter("listAnimalIds", searchDto.getListAnimalIds());
            qSum.setParameter("listAnimalIds", searchDto.getListAnimalIds());
        }
        if (searchDto.getCurrentYear() > 0) {
            q.setParameter("currentYear", searchDto.getCurrentYear());
            qSum.setParameter("currentYear", searchDto.getCurrentYear());
        }
        if (StringUtils.hasText(searchDto.getAnimalClass())) {
            q.setParameter("animalClass", searchDto.getAnimalClass());
            qSum.setParameter("animalClass", searchDto.getAnimalClass());
        }
        if (StringUtils.hasText(searchDto.getOrdo())) {
            q.setParameter("ordo", searchDto.getOrdo());
            qSum.setParameter("ordo", searchDto.getOrdo());
        }
        if (StringUtils.hasText(searchDto.getFamily())) {
            q.setParameter("family", searchDto.getFamily());
            qSum.setParameter("family", searchDto.getFamily());
        }
        ret = q.getResultList();
        retSum = qSum.getResultList();
        if (ret != null && ret.size() > 0) {
            for (ReportAccordingToTheRedBookDto reportAccordingToTheRedBookDto : ret) {
                if (reportAccordingToTheRedBookDto.getVnList06() == null
                        || !StringUtils.hasText(reportAccordingToTheRedBookDto.getVnList06())) {
                    reportAccordingToTheRedBookDto.setVnList06("Kh??ng x??c ?????nh");
                }
            }
        }
        if (retSum != null && retSum.size() > 0) {
            for (ReportAccordingToTheRedBookDto sumDto : retSum) {
                sumDto.setVnList06("* T???ng");
                ret.add(sumDto);
            }
        }
        return ret;
    }

    @Override
    public List<ReportImportExportTimeDto> getReportImportExportForm16(ImportExportAnimalSearchDto searchDto) {
        // TODO Auto-generated method stub
        List<ReportImportExportTimeDto> res = new ArrayList<ReportImportExportTimeDto>();

        searchDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
        List<ReportImportExportTimeDto> listImport = getReportImportExportForm16BySearchDto(searchDto);
        searchDto.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());
        List<ReportImportExportTimeDto> listExport = getReportImportExportForm16BySearchDto(searchDto);

        if (listImport != null) {
            res.addAll(listImport);
        }
        if (listExport != null && listExport.size() > 0) {
            for (ReportImportExportTimeDto export : listExport) {
                if (export.getAnimal() == null) {
                    continue;
                }
                Boolean check = false;
                for (ReportImportExportTimeDto r : res) {
                    if (r.getAnimal() == null) {
                        continue;
                    }
                    if (r.getAnimal().getId() == export.getAnimal().getId()) {
                        r.setCountFarmExport(export.getCountFarmExport());
                        r.setTotalTimeExport(export.getTotalTimeExport());
                        r.setTotalExport(export.getTotalExport());
                        check = true;
                        break;
                    }
                }

                if (check == false) {
                    res.add(export);
                }
            }
        }

        return res;
    }

    public List<ReportImportExportTimeDto> getReportImportExportForm16BySearchDto(ImportExportAnimalSearchDto searchDto) {
        List<ReportImportExportTimeDto> result = new ArrayList<ReportImportExportTimeDto>();
        String sql = "";
        String whereClause = " WHERE (1=1) ";
        String groupBy = " GROUP BY rp.animal.id";
        String having = "";

        if (searchDto.getType() == WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
            sql += "SELECT new com.globits.wl.dto.functiondto.ReportImportExportTimeDto(rp.animal, count(DISTINCT rp.farm.id), SUM(rp.totalImport), COUNT(rp.animal.id), 1L) "
                    + " FROM ReportForm16 rp ";
            whereClause += " and (rp.totalImport is not null ) and (rp.totalImport <> 0) ";
        }

        if (searchDto.getType() == WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
            sql += "SELECT new com.globits.wl.dto.functiondto.ReportImportExportTimeDto(rp.animal, count(DISTINCT rp.farm.id), SUM(rp.totalExport), COUNT(rp.animal.id), -1L) "
                    + " FROM ReportForm16 rp ";
            whereClause += " and (rp.totalExport is not null ) and (rp.totalExport <> 0) ";
        }

        List<Long> listAu = new ArrayList<Long>();
        if (searchDto.getAuId() != null) {
            listAu = fmsAdministrativeUnitService.getAllAdministrativeUnitIdByParentId(searchDto.getAuId());
            listAu.add(searchDto.getAuId());

            whereClause += " AND rp.farm.administrativeUnit.id in (:listAu) ";
        }

        if (searchDto.getFarmId() != null) {
            whereClause += " AND rp.farm.id = :farmId";
        }
        if (searchDto.getAnimalId() != null) {
            whereClause += " AND rp.animal.id = :animalId";
        }
        if (searchDto.getFromDate() != null) {
            whereClause += " AND rp.dateReport >= :fromDate";
        }
        if (searchDto.getToDate() != null) {
            whereClause += " AND rp.dateReport <= :toDate";
        }

        String sqlStr = sql + whereClause + groupBy + having;
        Query query = manager.createQuery(sqlStr, ReportImportExportTimeDto.class);

        if (searchDto.getFarmId() != null) {
            query.setParameter("farmId", searchDto.getFarmId());
        }
        if (searchDto.getAnimalId() != null) {
            query.setParameter("animalId", searchDto.getAnimalId());
        }
        if (searchDto.getFromDate() != null) {
            query.setParameter("fromDate", searchDto.getFromDate());
        }
        if (searchDto.getToDate() != null) {
            query.setParameter("toDate", searchDto.getToDate());
        }
        if (searchDto.getAuId() != null) {
            query.setParameter("listAu", listAu);
        }

        result = query.getResultList();
        return result;
    }

    @Override
    public List<AnimalReportDataReportDto> getAnimalReportDataReport04(ReportParamDto searchDto) {

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

//		List<ReportQuantityByCategoryCitesDto> ret = new ArrayList<ReportQuantityByCategoryCitesDto>();
//		List<ReportQuantityByCategoryCitesDto> retSum = new ArrayList<ReportQuantityByCategoryCitesDto>();

//		String sql = " SELECT new com.globits.wl.dto.functiondto.ReportQuantityByCategoryCitesDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), a.cites, ard.year) from AnimalReportData ard, "
//				+ " Animal a WHERE a.id = ard.animal.id AND ard.year is not null AND ard.month is null AND ard.day is null AND ard.total!=0 AND ard.total!=null ";
//		String sqlSum = " SELECT new com.globits.wl.dto.functiondto.ReportQuantityByCategoryCitesDto(COUNT(DISTINCT ard.farm.id), SUM(ard.total), '', ard.year) from AnimalReportData ard, "
//				+ " Animal a WHERE a.id = ard.animal.id AND ard.year is not null AND ard.month is null AND ard.day is null AND ard.total!=0 AND ard.total!=null ";
        String whereClause = "";
        String sql = "SELECT year,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total,COUNT(DISTINCT animalId) AS 'numberOfAnimal', animalGroup";
        String orderby = " GROUP BY year,animalGroup ORDER BY year DESC,animalGroup ";
        if (searchDto.getProvinceId() != null) {
            whereClause += " AND tb.provinceId = :provinceId";
        }
        if (searchDto.getDistrictId() != null) {
            whereClause += " AND tb.districtId = :districtId";
        }
        if (searchDto.getWardId() != null) {
            whereClause += " AND tb.wardId = :wardId";
        }
        if (searchDto.getListAnimalGroup() != null) {
            if (searchDto.getListAnimalGroup().contains("T???ng")) {
                sql = "SELECT year,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total,COUNT(DISTINCT animalId) AS 'numberOfAnimal' ";
                orderby = " GROUP BY year ORDER BY year DESC ";
            } else {
                whereClause += " AND tb.animalGroup in (:listAnimalGroup)";
            }

        }
        sql += " FROM (SELECT year,farmId,total,animalGroup,animalId FROM"
                + "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
                + "  f.date_report AS dateReport,"
                + "  p.year,"
                + "  prov.id AS provinceId,"
                + "  prov.name AS provinceName,"
                + "  prov.code AS provCode,"
                + "  dis.id AS districtId,"
                + "  dis.name AS districtName,"
                + "  dis.code AS disCode,"
                + "  w.id AS wardId,"
                + "  w.name AS wardName,"
                + "  w.code AS wardCode,"
                + "  a.id AS animalId,"
                + "  a.animal_group as animalGroup, "
                + "  a.cites, "
                + "  fa.id AS farmId,"
                + "  f.total AS total"
                + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
                + "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
                + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
                + "  AND prov.parent_id IS NULL"
                + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
                + "  AND dis.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
                + "  AND w.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//				"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//				"   AND w.parent_id IS NOT NULL" +
//				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//				"   AND dis.parent_id IS NOT NULL"
                + " ) as tb where tb.rowNumber=1 and tb.total<>0 " + whereClause + " ) as t ) as tb1 ";
        Query query = manager.createNativeQuery(sql + orderby).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(AnimalReportDataReportDto.class));


        if (searchDto.getProvinceId() != null) {
            query.setParameter("provinceId", searchDto.getProvinceId());
        }
        if (searchDto.getDistrictId() != null) {
            query.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getWardId() != null) {
            query.setParameter("wardId", searchDto.getWardId());
        }
//		if(searchDto.getAnimalGroup()!=null) {
//			if(searchDto.getAnimalGroup().equals("T???ng")) {
//			}else {
//				query.setParameter("listAnimalGroup", searchDto.getAnimalGroup());
//			}
//		}
        if (searchDto.getListAnimalGroup() != null && searchDto.getListAnimalGroup().size() > 0) {
            if (searchDto.getListAnimalGroup().contains("T???ng")) {

            } else {
                query.setParameter("listAnimalGroup", searchDto.getListAnimalGroup());
            }
        }
//		sql += whereClause;
//		sqlSum += whereClause;
//		Query q = manager.createQuery(sql+groupByClause, ReportQuantityByCategoryCitesDto.class);
//		Query qSum = manager.createQuery(sqlSum+"GROUP BY ard.year", ReportQuantityByCategoryCitesDto.class);

//		if (searchDto.getFarmId() != null) {
//			q.setParameter("farmId", searchDto.getFarmId());
//			qSum.setParameter("farmId", searchDto.getFarmId());
//		}
//		if (searchDto.getWardId() != null) {// ????y l?? query theo x??
//			q.setParameter("wardsId", searchDto.getWardId());
//			qSum.setParameter("wardsId", searchDto.getWardId());
//		}
//		if (searchDto.getDistrictId() != null) {// ????y l?? query theo huy???n
//			q.setParameter("districtId", searchDto.getDistrictId());
//			qSum.setParameter("districtId", searchDto.getDistrictId());
//		}
//		if (searchDto.getProvinceId() != null) {// ????y l?? query theo t???nh
//			q.setParameter("cityId", searchDto.getProvinceId());
//			qSum.setParameter("cityId", searchDto.getProvinceId());
//		}
//		if (searchDto.getRegionId() != null) {// ????y l?? query theo v??ng
//			q.setParameter("regionId", searchDto.getRegionId());
//			qSum.setParameter("regionId", searchDto.getRegionId());
//		}
//		if (!isAdmin) {
//			q.setParameter("listWardId", listWardId);
//			qSum.setParameter("listWardId", listWardId);
//		}
//		if (searchDto.getAnimalParentId() != null && searchDto.getAnimalParentId() > 0) {
//			q.setParameter("animalParentId", searchDto.getAnimalParentId());
//			qSum.setParameter("animalParentId", searchDto.getAnimalParentId());
//		}
//		if (searchDto.getProductTargetId() != null && searchDto.getProductTargetId() > 0) {
//			q.setParameter("productTargetId", searchDto.getProductTargetId());
//			qSum.setParameter("productTargetId", searchDto.getProductTargetId());
//		}
//		ret = q.getResultList();
//		retSum = qSum.getResultList();
//		if (ret != null && ret.size() > 0) {
//			for (ReportQuantityByCategoryCitesDto reportQuantityByCategoryCitesDto : ret) {
//				if (reportQuantityByCategoryCitesDto.getCites() == null
//						|| !StringUtils.hasText(reportQuantityByCategoryCitesDto.getCites())) {
//					reportQuantityByCategoryCitesDto.setCites("Kh??ng x??c ?????nh");
//				}
//			}
//		}
//
//		if(retSum!=null && retSum.size()>0) {
//			for (ReportQuantityByCategoryCitesDto sumDto : retSum) {
//				sumDto.setCites("T???ng");
//				ret.add(sumDto);
//			}
//		}
        List<AnimalReportDataReportDto> ret = query.getResultList();
        if (searchDto.getAnimalGroup() != null) {
            if (searchDto.getAnimalGroup().equals("T???ng")) {
                for (AnimalReportDataReportDto s : ret) {
                    s.setAnimalGroup("T???ng");
                }
            }
        }
        return ret;
    }

    @Override
    public List<AnimalReportDataReportDto> getAnimalReportDataReport05(ReportParamDto dto) {
        String whereClause = "";
        String groupBy = " ";
        String sqlSelect = "";
//		if (dto.getYear() != null) {
//			whereClause += " AND tb.year = :year ";
//		}
        if (dto.getDateReport() != null) {
            whereClause += " AND (tb.year < :yearReport "
                    + " OR (tb.year <= :yearReport AND tb.month < :monthReport) "
                    + " OR (tb.year <= :yearReport AND tb.month <= :monthReport AND tb.date <= :dateReport)) AND tb.year = :yearReport";
        }
        if (dto.getProvinceId() != null) {
            whereClause += " AND tb.provinceId = :provinceId";
        }
        if (dto.getDistrictId() != null) {
            whereClause += " AND tb.districtId = :districtId";
        }
        if (dto.getWardId() != null) {
            whereClause += " AND tb.wardId = :wardId";
        }
        if (dto.getAnimalClass() != null) {
            whereClause += " AND tb.animalClass = :animalClass ";
        }

        if (dto.getOrdo() != null) {
            whereClause += " AND tb.animalOrdo = :animalOrdo ";
        }

        if (dto.getFamily() != null) {
            whereClause += " AND tb.animalFamily = :animalFamily ";
        }
        if (dto.getListAnimalIds() != null && dto.getListAnimalIds().size() > 0) {
            whereClause += " AND tb.animalId in (:animalId) ";
        }
        //List animal class
        if (dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
            whereClause += " AND tb.animalClass in (:listAnimalClass)";
        }
        //List animal ordo
        if (dto.getListAnimalOrdo() != null && dto.getListAnimalOrdo().size() > 0) {
            whereClause += " AND tb.animalOrdo in (:listAnimalOrdo)";
        }
        //List animal family
        if (dto.getListAnimalFamily() != null && dto.getListAnimalFamily().size() > 0) {
            whereClause += " AND tb.animalFamily in (:listAnimalFamily)";
        }
//		groupBy= " GROUP BY year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalId,animalName,animalClass,animalFamily,animalOrdo order by year DESC,provinceId,districtId,wardId,animalId";
//		sqlSelect="SELECT year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalId,animalName,animalClass,animalFamily as family,animalOrdo as ordo,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total";
//		String sql=" FROM (SELECT year,provinceId,provinceName,districtId,districtName,wardId,wardName,farmId,animalId,animalName,total,animalClass,animalFamily,animalOrdo FROM"
//				+ "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
//				+ "  f.date_report AS dateReport,"
//				+ "  p.year,"
//				+ "  prov.id AS provinceId,"
//				+ "  prov.name AS provinceName,"
//				+ "  prov.code AS provCode,"
//				+ "  dis.id AS districtId,"
//				+ "  dis.name AS districtName,"
//				+ "  dis.code AS disCode,"
//				+ "  w.id AS wardId,"
//				+ "  w.name AS wardName,"
//				+ "  w.code AS wardCode,"
//				+ "  a.id AS animalId,"
//				+ "  a.name AS animalName,"
//				+ "  a.code AS animalCode,"
//				+ "  a.animal_class AS animalClass,"
//				+ "  a.family AS animalFamily,"
//				+ "  a.ordo AS animalOrdo,"
//				+ "  fa.id AS farmId,"
//				+ "  fa.name AS farmName,"
//				+ "  fa.code AS farmCode,"
//				+ "  f.total AS total"
//				+ "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
//				+ "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
//				+ "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
//				+ "  AND prov.parent_id IS NULL"
//				+ "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
//				+ "  AND dis.parent_id IS NOT NULL"
//				+ "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
//				+ "  AND w.parent_id IS NOT NULL"
//				+ "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//
////				"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
////				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
////				"   AND w.parent_id IS NOT NULL" +
////				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
////				"   AND dis.parent_id IS NOT NULL"
//
//				+ " ) as tb where tb.rowNumber=1 and tb.total<>0 "+ whereClause + " ) as t ) as tb1 ";

        groupBy = " GROUP BY year,month,date,provinceId,provinceName,districtId,districtName,wardId,wardName,animalId,animalName,animalClass,animalFamily,animalOrdo order by year DESC, month DESC, date DESC,provinceId,districtId,wardId,animalId";
        sqlSelect = "SELECT year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalId,animalName,animalClass,animalFamily as family,animalOrdo as ordo,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total";
        String sql = " FROM (SELECT year,month,date,provinceId,provinceName,districtId,districtName,wardId,wardName,farmId,animalId,animalName,total,animalClass,animalFamily,animalOrdo FROM"
                + "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
                + "  f.date_report AS dateReport,"
                + "  p.year,"
                + "  p.month,"
                + "  p.date,"
                + "  prov.id AS provinceId,"
                + "  prov.name AS provinceName,"
                + "  prov.code AS provCode,"
                + "  dis.id AS districtId,"
                + "  dis.name AS districtName,"
                + "  dis.code AS disCode,"
                + "  w.id AS wardId,"
                + "  w.name AS wardName,"
                + "  w.code AS wardCode,"
                + "  a.id AS animalId,"
                + "  a.name AS animalName,"
                + "  a.code AS animalCode,"
                + "  a.animal_class AS animalClass,"
                + "  a.family AS animalFamily,"
                + "  a.ordo AS animalOrdo,"
                + "  fa.id AS farmId,"
                + "  fa.name AS farmName,"
                + "  fa.code AS farmCode,"
                + "  f.total AS total"
                + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
                + "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
                + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
                + "  AND prov.parent_id IS NULL"
                + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
                + "  AND dis.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
                + "  AND w.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
                + " ) as tb where tb.rowNumber=1 and tb.total<>0 " + whereClause + " ) as t ) as tb1 ";

        sqlSelect += sql + groupBy;

        Query query = manager.createNativeQuery(sqlSelect).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(AnimalReportDataReportDto.class));
//		if (dto.getYear() != null) {
//			query.setParameter("year", dto.getYear());
//		}
        if (dto.getDateReport() != null) {
            Integer yearReport = WLDateTimeUtil.getYear(dto.getDateReport());
            Integer monthReport = WLDateTimeUtil.getMonth(dto.getDateReport());
            Integer dateReport = WLDateTimeUtil.getDay(dto.getDateReport());
            query.setParameter("yearReport", yearReport);
            query.setParameter("monthReport", monthReport);
            query.setParameter("dateReport", dateReport);
        }
        if (dto.getProvinceId() != null) {
            query.setParameter("provinceId", dto.getProvinceId());
        }
        if (dto.getDistrictId() != null) {
            query.setParameter("districtId", dto.getDistrictId());
        }
        if (dto.getWardId() != null) {
            query.setParameter("wardId", dto.getWardId());
        }
        if (dto.getAnimalClass() != null) {
            query.setParameter("animalClass", dto.getAnimalClass());
        }

        if (dto.getOrdo() != null) {
            query.setParameter("animalOrdo", dto.getOrdo());
        }

        if (dto.getFamily() != null) {
            query.setParameter("animalFamily", dto.getFamily());
        }
        if (dto.getListAnimalIds() != null && dto.getListAnimalIds().size() > 0) {
            query.setParameter("animalId", dto.getListAnimalIds());
        }
        //List animal class
        if (dto.getListAnimalClass() != null && dto.getListAnimalClass().size() > 0) {
            query.setParameter("listAnimalClass", dto.getListAnimalClass());
        }
        //List animal ordo
        if (dto.getListAnimalOrdo() != null && dto.getListAnimalOrdo().size() > 0) {
            query.setParameter("listAnimalOrdo", dto.getListAnimalOrdo());
        }
        //List animal family
        if (dto.getListAnimalFamily() != null && dto.getListAnimalFamily().size() > 0) {
            query.setParameter("listAnimalFamily", dto.getListAnimalFamily());
        }
        List<AnimalReportDataReportDto> result = (List<AnimalReportDataReportDto>) query.getResultList();

        return result;
    }

    @Override
    public List<AnimalReportDataReportDto> getAnimalReportDataReport07(ReportParamDto dto) {
        //cites
        String whereClause = " where 1=1 ";
        String groupBy = " ";
        String sqlSelect = "";
        String sql = " FROM (SELECT year,month,date,provinceId,provinceName,districtId,districtName,wardId,wardName,farmId,animalId,total,animalName,scienceName,status,cites,vnlist06 FROM"
                + "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
                + "  f.date_report AS dateReport,"
                + "  p.year,"
                + "  p.month,"
                + "  p.date,"
                + "  prov.id AS provinceId,"
                + "  prov.name AS provinceName,"
                + "  prov.code AS provCode,"
                + "  dis.id AS districtId,"
                + "  dis.name AS districtName,"
                + "  dis.code AS disCode,"
                + "  w.id AS wardId,"
                + "  w.name AS wardName,"
                + "  w.code AS wardCode,"
                + "  a.id AS animalId,"
                + "  a.name AS animalName,"
                + "  a.code AS animalCode,"
                + "  a.science_name AS scienceName,"
                + "  a.cites,"
                + "  a.vnlist06,"
                + "  fa.status,"
                + "  fa.id AS farmId,"
                + "  fa.name AS farmName,"
                + "  fa.code AS farmCode,"
                + "  f.total AS total"
                + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
                + "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
                + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
                + "  AND prov.parent_id IS NULL"
                + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
                + "  AND dis.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
                + "  AND w.parent_id IS NOT NULL"
//				+ "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)) as tb where tb.rowNumber=1 and tb.total<>0 and tb.cites IS NOT NULL  ) as t ) as tb1 ";
                + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//				"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//				"   AND w.parent_id IS NOT NULL" +
//				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//				"   AND dis.parent_id IS NOT NULL"
                + " ) as tb where tb.rowNumber=1 and tb.total<>0) as t ) as tb1 ";


//        if (dto.getYear() != null) {
//            whereClause += " and tb1.year = :year ";
//        }
        if (dto.getDateReport() != null) {
            whereClause += " AND (tb1.year < :findYear or " +
                    "(tb1.year <= :findYear ANd tb1.month < :findMonth) or " +
                    "(tb1.year <= :findYear ANd tb1.month <= :findMonth AND tb1.date <= :findDate)) AND tb1.year = :findYear ";
        }
        if (dto.getProvinceId() != null) {
            whereClause += " AND tb1.provinceId = :provinceId";
        }
        if (dto.getDistrictId() != null) {
            whereClause += " AND tb1.districtId = :districtId";
        }
        if (dto.getWardId() != null) {
            whereClause += " AND tb1.wardId = :wardId";
        }
        if (dto.getVnList06s() != null && dto.getVnList06s().size() > 0) {
            if (dto.getVnList06s().contains("T???ng")) {
                sqlSelect = "SELECT year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalName,scienceName,cites, vnlist06,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total, sum(case when status = 3 then 1 else 0 end) as numberOfFarmStatus\";";
                groupBy = " GROUP BY year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalName,scienceName,cites, vnlist06 order by year DESC,provinceId,districtId,wardId";
            } else if (dto.getVnList06s().contains("Kh??ng x??c ?????nh")) {
                whereClause += " AND isnull(tb1.vnlist06,'') = '' ";
            } else {
                whereClause += " AND tb1.vnlist06 in (:vnList06s)";
            }
        }
        groupBy = " GROUP BY year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalName,scienceName,cites, vnlist06 order by year DESC,provinceId,districtId,wardId";
//		sqlSelect="SELECT year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalName,scienceName,cites,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total, sum(case when status = 3 then 1 else 0 end) as numberOfFarmStatus,wardName as cites07  ";
        sqlSelect = "SELECT year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalName,scienceName,cites, vnlist06,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total, sum(case when status = 3 then 1 else 0 end) as numberOfFarmStatus";


        sqlSelect += sql + whereClause + groupBy;

        Query query = manager.createNativeQuery(sqlSelect).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(AnimalReportDataReportDto.class));
//        if (dto.getYear() != null) {
//            query.setParameter("year", dto.getYear());
//        }
        if (dto.getDateReport() != null) {
            Integer findYear = WLDateTimeUtil.getYear(dto.getDateReport());
            Integer findMonth = WLDateTimeUtil.getMonth(dto.getDateReport());
            Integer findDate = WLDateTimeUtil.getDay(dto.getDateReport());
            query.setParameter("findYear", findYear);
            query.setParameter("findMonth", findMonth);
            query.setParameter("findDate", findDate);
        }
        if (dto.getProvinceId() != null) {
            query.setParameter("provinceId", dto.getProvinceId());
        }
        if (dto.getDistrictId() != null) {
            query.setParameter("districtId", dto.getDistrictId());
        }
        if (dto.getWardId() != null) {
            query.setParameter("wardId", dto.getWardId());
        }
        if (dto.getVnList06s() != null && dto.getVnList06s().size() > 0 && dto.getVnList06s().contains("T???ng") == false && dto.getVnList06s().contains("Kh??ng x??c ?????nh") == false) {
            query.setParameter("vnList06s", dto.getVnList06s());
        }
        List<AnimalReportDataReportDto> result = (List<AnimalReportDataReportDto>) query.getResultList();

//
//		sql=" FROM (SELECT year,provinceId,provinceName,districtId,districtName,wardId,wardName,farmId,animalId,total,animalName,scienceName,status,cites FROM"
//				+ "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC)) AS rowNumber ,"
//				+ "  f.date_report AS dateReport,"
//				+ "  p.year,"
//				+ "  prov.id AS provinceId,"
//				+ "  prov.name AS provinceName,"
//				+ "  prov.code AS provCode,"
//				+ "  dis.id AS districtId,"
//				+ "  dis.name AS districtName,"
//				+ "  dis.code AS disCode,"
//				+ "  w.id AS wardId,"
//				+ "  w.name AS wardName,"
//				+ "  w.code AS wardCode,"
//				+ "  a.id AS animalId,"
//				+ "  a.name AS animalName,"
//				+ "  a.code AS animalCode,"
//				+ "  a.science_name AS scienceName,"
//				+ "  a.vnlist06,"
//				+ "  a.cites,"
//				+ "  fa.status,"
//				+ "  fa.id AS farmId,"
//				+ "  fa.name AS farmName,"
//				+ "  fa.code AS farmCode,"
//				+ "  f.total AS total"
//				+ "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
//				+ "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
//				+ "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
//				+ "  AND prov.parent_id IS NULL"
//				+ "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
//				+ "  AND dis.parent_id IS NOT NULL"
//				+ "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
//				+ "  AND w.parent_id IS NOT NULL"
//				+ "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)) as tb where tb.rowNumber=1 and tb.total<>0 and tb.vnlist06 = 'DVRTT'  ) as t ) as tb1 ";
//
//			groupBy= " GROUP BY year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalName,scienceName,cites order by year DESC,provinceId,districtId,wardId";
//			sqlSelect="SELECT year,provinceId,provinceName,districtId,districtName,wardId,wardName,animalName,scienceName,cites,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total, sum(case when status = 3 then 1 else 0 end) as numberOfFarmStatus  ";
//
//		sqlSelect+= sql + whereClause+groupBy;
//		query = manager.createNativeQuery(sqlSelect).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer( AnimalReportDataReportDto.class));
//		if (dto.getYear() != null) {
//			query.setParameter("year", dto.getYear());
//		}
//		if (dto.getProvinceId() != null) {
//			query.setParameter("provinceId", dto.getProvinceId());
//		}
//		if (dto.getDistrictId() != null) {
//			query.setParameter("districtId", dto.getDistrictId());
//		}
//		if (dto.getWardId() != null) {
//			query.setParameter("wardId", dto.getWardId());
//		}
//		List<AnimalReportDataReportDto> result1 = (List<AnimalReportDataReportDto>)query.getResultList();
//		result.addAll(result1);
        if (result != null && result.size() > 0) {
            for (AnimalReportDataReportDto animalReportDataReportDto : result) {
                if (animalReportDataReportDto.getVnlist06() == null
                        || !StringUtils.hasText(animalReportDataReportDto.getVnlist06())) {
                    animalReportDataReportDto.setVnlist06("Kh??ng x??c ?????nh");
                }
            }
        }

        return result;
    }

    @Override
    public List<Report18Dto> report24(AnimalReportDataSearchDto dto) {
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

        String sql = " " +
                "  " +
                "  SELECT " +
                "                            f.date_report AS dateReport," +
                "                            p.year," +
                "                            p.month," +
                "                            p.date," +
                "                            prov.id AS provId," +
                "                            prov.name AS provName," +
                "                            prov.code AS provCode," +
                "                            dis.id AS disId," +
                "                            dis.name AS disName," +
                "                            dis.code AS disCode," +
                "                            w.id AS wardId," +
                "                            w.name AS wardName," +
                "                            w.code AS wardCode," +
                "                            a.id AS animalId," +
                "                            a.name AS animalName," +
                "                            a.science_name AS animalSciName," +
                "                            a.other_name AS animalOtherName," +
                "                            a.code AS animalCode," +
                "                            a.ordo AS animalOrdo," +
                "                            a.animal_class AS animalClass," +
                "                            a.family AS animalFamily," +
                "                            a.cites," +
                "                            a.vnlist," +
                "                            a.vnlist06," +
                "							 a.animal_group as animalGroup, " +
                "                            fa.id AS farmId," +
                "                            fa.name AS farmName," +
                "                            fa.code AS farmCode," +
                "                            fa.new_registration_code AS farmNewRegistrationCode," +
                "                            fa.old_registration_code AS farmOldRegistrationCode," +
                "                            fa.date_registration AS farmDateRegistration," +
                "                            fa.longitude AS farmLongitude," +
                "                            fa.latitude AS farmLatitude," +
                "                            fa.village AS farmVillage," +
                "							 f.total, " +
                "                            f.total_export AS totalExport," +
                "                            f.total_import AS totalImport," +
                "                            f.import_male_parent AS importMaleParent," +
                "                            f.import_female_parent AS importFemaleParent," +
                "                            f.import_unGender_parent AS importUnGenderParent," +
                "                            f.import_male_gilts AS importMaleGilts," +
                "                            f.import_female_gilts AS importFemaleGilts," +
                "                            f.import_unGender_gilts AS importUnGenderGilts," +
                "                            f.import_male_child_under_1year_old AS importMaleChildUnder1YearOld," +
                "                            f.import_female_child_under_1year_old AS importFemaleChildUnder1YearOld," +
                "                            f.import_child_under_1_year_old AS importChildUnder1YearOld," +
                "                            f.import_male_child_over_1_year_old AS importMaleChildOver1YearOld," +
                "                            f.import_female_child_over_year_old AS importFemaleChildOver1YearOld," +
                "                            f.import_ungender_child_over_1_year_old AS importUnGenderChildOver1YearOld," +
                "                            f.import_reason AS importReason," +
                "                            f.note AS note," +
                "							 o.name as original,	" +
                "                            fa.founding_date AS farmFoundingDate" +
                "   FROM tbl_report_form16 f" +
                "   INNER JOIN tbl_report_period p ON f.report_period_id = p.id" +
                "   INNER JOIN tbl_animal a ON a.id = f.animal_id" +
                "   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id" +
                "   AND prov.parent_id IS NULL" +
                "   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id" +
                "   AND dis.parent_id IS NOT NULL" +
                "   INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id" +
                "   AND w.parent_id IS NOT NULL" +
                "   INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0) "
//				"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//				"   AND w.parent_id IS NOT NULL" +
//				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//				"   AND dis.parent_id IS NOT NULL"
                + " LEFT JOIN tbl_original o ON o.id = a.original_id  ";

        String whereClause = " where ((f.total_import IS NOT NULL AND f.total_import <> 0) OR "
                + "( (f.total_import IS NULL OR f.total_import=0 ) AND (f.total_export IS NULL OR f.total_export=0 ) AND (f.total IS NOT NULL AND f.total<>0 ) )) ";
        String orderBy = " ORDER BY f.date_report DESC,fa.name,a.name ";

        if (dto.getFromDate() != null) {
            whereClause += " and f.date_report >= :fromDate ";
        }
        if (dto.getToDate() != null) {
            whereClause += " and f.date_report <= :toDate ";
        }

//		if (dto.getYear() != null) {
//			whereClause += " and tb1.year = :year ";
//		}

        if (dto.getAnimalId() != null) {
            whereClause += " AND a.id = :animalId";
        }
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            whereClause += " AND a.id in (:listAnimalId)";
        }
        if (dto.getAnimalOrdo() != null) {
            whereClause += " AND a.ordo = :animalOrdo";
        }
        if (dto.getAnimalClass() != null) {
            whereClause += " AND a.animal_class = :animalClass";
        }
        if (dto.getAnimalFamily() != null) {
            whereClause += " AND a.family = :animalFamily";
        }
        if (dto.getFarmId() != null) {
            whereClause += " AND fa.id = :farmId";
        }

        if (dto.getAnimalCites() != null) {
            whereClause += " AND a.cites = :animalCites";
        }
        if (dto.getAnimalVnlist() != null) {
            whereClause += " AND a.vnlist = :animalVnlist";
        }
        if (dto.getAnimalVnlist06() != null) {
            whereClause += " AND a.vnlist06 = :animalVnlist06";
        }

        if (dto.getProvinceId() != null) {
            whereClause += " AND prov.id = :provinceId";
        }
        if (dto.getDistrictId() != null) {
            whereClause += " AND dis.id = :districtId";
        }
        if (dto.getCommuneId() != null) {
            whereClause += " AND w.id = :communeId";
        }
        if (dto.getAnimalGroup() != null) {
            whereClause += " AND a.animal_group = :animalGroup";
        }

        sql += whereClause + orderBy;

        Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(Report18Dto.class));

        if (dto.getFromDate() != null) {
            query.setParameter("fromDate", dto.getFromDate());
        }
        if (dto.getToDate() != null) {
            query.setParameter("toDate", dto.getToDate());
        }
//		if (dto.getYear() != null) {
//			query.setParameter("year", dto.getYear());
//		}

        if (dto.getAnimalId() != null) {
            query.setParameter("animalId", dto.getAnimalId());
        }
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            query.setParameter("listAnimalId", dto.getAnimalIds());
        }
        if (dto.getFarmId() != null) {
            query.setParameter("farmId", dto.getFarmId());
        }
        if (dto.getProvinceId() != null) {
            query.setParameter("provinceId", dto.getProvinceId());
        }
        if (dto.getDistrictId() != null) {
            query.setParameter("districtId", dto.getDistrictId());
        }
        if (dto.getCommuneId() != null) {
            query.setParameter("communeId", dto.getCommuneId());
        }
        if (dto.getAnimalClass() != null) {
            query.setParameter("animalClass", dto.getAnimalClass());
        }
        if (dto.getAnimalOrdo() != null) {
            query.setParameter("animalOrdo", dto.getAnimalOrdo());
        }
        if (dto.getAnimalFamily() != null) {
            query.setParameter("animalFamily", dto.getAnimalFamily());
        }
        if (dto.getAnimalCites() != null) {
            query.setParameter("animalCites", dto.getAnimalCites());
        }
        if (dto.getAnimalVnlist() != null) {
            query.setParameter("animalVnlist", dto.getAnimalVnlist());
        }
        if (dto.getAnimalVnlist06() != null) {
            query.setParameter("animalVnlist06", dto.getAnimalVnlist06());
        }
        if (dto.getAnimalGroup() != null) {
            query.setParameter("animalGroup", dto.getAnimalGroup());
        }

        List<Report18Dto> result = (List<Report18Dto>) query.getResultList();

        return result;
    }

    @Override
    public List<Report18Dto> report25(AnimalReportDataSearchDto dto) {
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

        String sql = "SELECT * " +
                " FROM " +
                "  (SELECT " +
                "                            f.date_report AS dateReport," +
                "                            p.year," +
                "                            p.month," +
                "                            p.date," +
                "                            prov.id AS provId," +
                "                            prov.name AS provName," +
                "                            prov.code AS provCode," +
                "                            dis.id AS disId," +
                "                            dis.name AS disName," +
                "                            dis.code AS disCode," +
                "                            w.id AS wardId," +
                "                            w.name AS wardName," +
                "                            w.code AS wardCode," +
                "                            a.id AS animalId," +
                "                            a.name AS animalName," +
                "                            a.science_name AS animalSciName," +
                "                            a.other_name AS animalOtherName," +
                "                            a.code AS animalCode," +
                "                            a.ordo AS animalOrdo," +
                "                            a.animal_class AS animalClass," +
                "                            a.family AS animalFamily," +
                "                            a.cites," +
                "                            a.vnlist," +
                "                            a.vnlist06," +
                "							 a.animal_group as animalGroup, " +
                "                            fa.id AS farmId," +
                "                            fa.name AS farmName," +
                "                            fa.code AS farmCode," +
                "                            fa.new_registration_code AS farmNewRegistrationCode," +
                "                            fa.old_registration_code AS farmOldRegistrationCode," +
                "                            fa.date_registration AS farmDateRegistration," +
                "                            fa.longitude AS farmLongitude," +
                "                            fa.latitude AS farmLatitude," +
                "                            fa.village AS farmVillage," +

                "                            f.total_export AS totalExport," +
                "                            f.export_male_parent AS exportMaleParent," +
                "                            f.export_female_parent AS exportFemaleParent," +
                "                            f.export_unGender_parent AS exportUnGenderParent," +
                "                            f.export_male_gilts AS exportMaleGilts," +
                "                            f.export_female_gilts AS exportFemaleGilts," +
                "                            f.export_unGender_gilts AS exportUnGenderGilts," +
                "                            f.export_male_child_under_1year_old AS exportMaleChildUnder1YearOld," +
                "                            f.export_female_child_under_1year_old AS exportFemaleChildUnder1YearOld," +
                "                            f.export_child_under_1_year_old AS exportChildUnder1YearOld," +
                "                            f.export_male_child_over_1_year_old AS exportMaleChildOver1YearOld," +
                "                            f.export_female_chid_over_1_year_old AS exportFemaleChildOver1YearOld," +
                "                            f.export_ungender_child_over_1_year_old AS exportUnGenderChildOver1YearOld," +
                "                            f.export_reason AS exportReason," +
                "                            f.note AS note," +
                "							 fpld.amount,	" +
                "							 adto.name as toAdName,	" +
                "							 adfrom.name as fromAdName,	" +
                "							 fpl.transportStart as transportStart,	" +
                "							 fpl.transportEnd as transportEnd,	" +
                "							 o.name as original,	" +
                "                            fa.founding_date AS farmFoundingDate" +
                "   FROM tbl_report_form16 f" +
                "   INNER JOIN tbl_report_period p ON f.report_period_id = p.id" +
                "   INNER JOIN tbl_animal a ON a.id = f.animal_id" +
                "   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id" +
                "   AND prov.parent_id IS NULL" +
                "   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id" +
                "   AND dis.parent_id IS NOT NULL" +
                "   INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id" +
                "   AND w.parent_id IS NOT NULL" +
                "   INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//				"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//				"   AND w.parent_id IS NOT NULL" +
//				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//				"   AND dis.parent_id IS NOT NULL"
                + " LEFT JOIN tbl_forest_products_list_detail fpld ON fpld.report_form_16_id = f.id"
                + " LEFT JOIN tbl_forest_products_list fpl ON fpl.id = fpld.forest_products_list_id "
                + " LEFT JOIN tbl_fms_administrative_unit adto ON adto.id = fpl.administrative_unit_to_id "
                + " LEFT JOIN tbl_fms_administrative_unit adfrom ON adfrom.id = fpl.administrative_unit_from_id "
                + " LEFT JOIN tbl_original o ON o.id = a.original_id) AS tb1";

        String whereClause = " where tb1.totalExport<>0 AND tb1.totalExport IS NOT NULL ";
        String orderBy = " ORDER BY tb1.dateReport DESC,tb1.farmName,tb1.animalName ";

        if (dto.getFromDate() != null) {
            whereClause += " and tb1.dateReport >= :fromDate ";
        }
        if (dto.getToDate() != null) {
            whereClause += " and tb1.dateReport <= :toDate ";
        }

//		if (dto.getYear() != null) {
//			whereClause += " and tb1.year = :year ";
//		}

        if (dto.getAnimalId() != null) {
            whereClause += " AND tb1.animalId = :animalId";
        }
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            whereClause += " AND tb1.animalId in (:listAnimalId)";
        }
        if (dto.getAnimalOrdo() != null) {
            whereClause += " AND tb1.animalOrdo = :animalOrdo";
        }
        if (dto.getAnimalClass() != null) {
            whereClause += " AND tb1.animalClass = :animalClass";
        }
        if (dto.getAnimalFamily() != null) {
            whereClause += " AND tb1.animalFamily = :animalFamily";
        }
        if (dto.getFarmId() != null) {
            whereClause += " AND tb1.farmId = :farmId";
        }

        if (dto.getAnimalCites() != null) {
            whereClause += " AND tb1.cites = :animalCites";
        }
        if (dto.getAnimalVnlist() != null) {
            whereClause += " AND tb1.vnlist = :animalVnlist";
        }
        if (dto.getAnimalVnlist06() != null) {
            whereClause += " AND tb1.vnlist06 = :animalVnlist06";
        }

        if (dto.getProvinceId() != null) {
            whereClause += " AND tb1.provId = :provinceId";
        }
        if (dto.getDistrictId() != null) {
            whereClause += " AND tb1.disId = :districtId";
        }
        if (dto.getCommuneId() != null) {
            whereClause += " AND tb1.wardId = :communeId";
        }
        if (dto.getAnimalGroup() != null) {
            whereClause += " AND tb1.animalGroup = :animalGroup";
        }

        sql += whereClause + orderBy;

        Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(Report18Dto.class));

        if (dto.getFromDate() != null) {
            query.setParameter("fromDate", dto.getFromDate());
        }
        if (dto.getToDate() != null) {
            query.setParameter("toDate", dto.getToDate());
        }
//		if (dto.getYear() != null) {
//			query.setParameter("year", dto.getYear());
//		}

        if (dto.getAnimalId() != null) {
            query.setParameter("animalId", dto.getAnimalId());
        }
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            query.setParameter("listAnimalId", dto.getAnimalIds());
        }
        if (dto.getFarmId() != null) {
            query.setParameter("farmId", dto.getFarmId());
        }
        if (dto.getProvinceId() != null) {
            query.setParameter("provinceId", dto.getProvinceId());
        }
        if (dto.getDistrictId() != null) {
            query.setParameter("districtId", dto.getDistrictId());
        }
        if (dto.getCommuneId() != null) {
            query.setParameter("communeId", dto.getCommuneId());
        }
        if (dto.getAnimalClass() != null) {
            query.setParameter("animalClass", dto.getAnimalClass());
        }
        if (dto.getAnimalOrdo() != null) {
            query.setParameter("animalOrdo", dto.getAnimalOrdo());
        }
        if (dto.getAnimalFamily() != null) {
            query.setParameter("animalFamily", dto.getAnimalFamily());
        }
        if (dto.getAnimalCites() != null) {
            query.setParameter("animalCites", dto.getAnimalCites());
        }
        if (dto.getAnimalVnlist() != null) {
            query.setParameter("animalVnlist", dto.getAnimalVnlist());
        }
        if (dto.getAnimalVnlist06() != null) {
            query.setParameter("animalVnlist06", dto.getAnimalVnlist06());
        }
        if (dto.getAnimalGroup() != null) {
            query.setParameter("animalGroup", dto.getAnimalGroup());
        }

        List<Report18Dto> result = (List<Report18Dto>) query.getResultList();

        return result;
    }

    @Override
    public List<Report18Dto> report26(AnimalReportDataSearchDto dto) {
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

        String sql = "SELECT * FROM ( SELECT fpl.modify_date AS dateReport,"
                + " fpl.id AS rowNumber,"
                + " fpl.buyerName,"
                + " fpl.code as fplCode,"
                + " fa.name AS farmName,"
                + " fa.adress_detail AS adressDetail,"
                + " prov.id AS provId,"
                + " prov.name AS provName,"
                + " prov.code AS provCode,"
                + " dis.id AS disId,"
                + " dis.name AS disName,"
                + " dis.code AS disCode,"
                + " w.id AS wardId,"
                + " w.name AS wardName,"
                + " w.code AS wardCode,"
                + " a.name AS animalName,"
                + " a.id AS animalId,"
                + " fpld.unit,"
                + " fpld.total,"
                + "	fpld.note  "
                + " FROM ((tbl_forest_products_list fpl "
                + " INNER JOIN tbl_farm fa ON fa.id = fpl.farm_id and (fa.isDuplicate is null or fa.isDuplicate =0)) "
                //+ " LEFT JOIN (select max(id) as id, max(province_id) as province_id, max(district_id) as district_id, max(administrative_unit_id) as administrative_unit_id, farm_id  from tbl_report_period group by farm_id) p ON p.farm_id = fa.id"
//				+ " INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
//				+ " AND prov.parent_id IS NULL"
//				+ " INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
//				+ " AND dis.parent_id IS NOT NULL"
//				+ " INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
//				+ " AND w.parent_id IS NOT NULL"
                + "   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
                "   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
                "   AND w.parent_id IS NOT NULL" +
                "   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
                "   AND dis.parent_id IS NOT NULL"
                + " RIGHT JOIN tbl_forest_products_list_detail fpld on fpld.forest_products_list_id = fpl.id) "
                + " LEFT JOIN tbl_animal a ON a.id = fpld.animal_id WHERE fpl.cancel_status = :cancelStatus) AS tb1 WHERE 1=1 ";

        String whereClause = " ";
        String orderBy = " ORDER BY tb1.rowNumber,tb1.dateReport DESC";
        if (dto.getFromDate() != null) {
            whereClause += " and tb1.dateReport >= :fromDate ";
        }
        if (dto.getToDate() != null) {
            whereClause += " and tb1.dateReport <= :toDate ";
        }
        if (dto.getAnimalId() != null) {
            whereClause += " AND tb1.animalId = :animalId";
        }
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            whereClause += " AND tb1.animalId in (:animalIds)";
        }
        if (dto.getProvinceId() != null) {
            whereClause += " AND tb1.provId = :provinceId";
        }
        if (dto.getDistrictId() != null) {
            whereClause += " AND tb1.disId = :districtId";
        }
        if (dto.getCommuneId() != null) {
            whereClause += " AND tb1.wardId = :communeId";
        }
        sql += whereClause + orderBy;

        Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(Report18Dto.class));
        query.setParameter("cancelStatus", 3);
        if (dto.getFromDate() != null) {
            query.setParameter("fromDate", dto.getFromDate());
        }
        if (dto.getToDate() != null) {
            query.setParameter("toDate", dto.getToDate());
        }
        if (dto.getAnimalId() != null) {
            query.setParameter("animalId", dto.getAnimalId());
        }
        if (dto.getAnimalIds() != null && dto.getAnimalIds().size() > 0) {
            query.setParameter("animalIds", dto.getAnimalIds());
        }
        if (dto.getProvinceId() != null) {
            query.setParameter("provinceId", dto.getProvinceId());
        }
        if (dto.getDistrictId() != null) {
            query.setParameter("districtId", dto.getDistrictId());
        }
        if (dto.getCommuneId() != null) {
            query.setParameter("communeId", dto.getCommuneId());
        }
        @SuppressWarnings("unchecked")
        List<Report18Dto> result = (List<Report18Dto>) query.getResultList();

        return result;
    }

    @Override
    public List<AnimalReportDataReportDto> getAnimalReportDataReport04s(ReportParamDto searchDto) {
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
        String sql = "SELECT year,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total,COUNT(DISTINCT animalId) AS 'numberOfAnimal', protectionLevel";
        String orderby = " GROUP BY year,protectionLevel ORDER BY year,protectionLevel DESC  ";
        if (searchDto.getProvinceId() != null) {
            whereClause += " AND tb.provinceId = :provinceId";
        }
        if (searchDto.getDistrictId() != null) {
            whereClause += " AND tb.districtId = :districtId";
        }
        if (searchDto.getWardId() != null) {
            whereClause += " AND tb.wardId = :wardId";
        }

        if (searchDto.getListprotectionLevel() != null) {
            sql = "SELECT year,COUNT(DISTINCT farmId) AS 'numberOfFam', sum(total) AS total,COUNT(DISTINCT animalId) AS 'numberOfAnimal',protectionLevel ";
            orderby = " GROUP BY year,protectionLevel ORDER BY year,protectionLevel DESC ";
            if (searchDto.getListprotectionLevel() != null && searchDto.getListprotectionLevel().size() > 0) {
                whereClause += " AND tb.protectionLevel in :listprotectionLevel";
            }
        }
        sql += " FROM (SELECT year,farmId,total,protectionLevel,animalId FROM "
                + "( SELECT * FROM (SELECT  (ROW_NUMBER() OVER (PARTITION BY f.farm_id,f.animal_id,p.year ORDER BY f.date_report DESC,f.modify_date desc,f.create_date desc)) AS rowNumber ,"
                + "  f.date_report AS dateReport,"
                + "  p.year,"
                + "  prov.id AS provinceId,"
                + "  prov.name AS provinceName,"
                + "  prov.code AS provCode,"
                + "  dis.id AS districtId,"
                + "  dis.name AS districtName,"
                + "  dis.code AS disCode,"
                + "  w.id AS wardId,"
                + "  w.name AS wardName,"
                + "  w.code AS wardCode,"
                + "  a.id AS animalId,"
                + "  a.protection_level AS protectionLevel,"
                + "  a.cites, "
                + "  fa.id AS farmId,"
                + "  f.total AS total"
                + "  FROM tbl_report_form16 f INNER JOIN tbl_report_period p ON f.report_period_id = p.id"
                + "  INNER JOIN tbl_animal a ON a.id = f.animal_id"
                + "  INNER JOIN tbl_fms_administrative_unit prov ON prov.id=p.province_id"
                + "  AND prov.parent_id IS NULL"
                + "  INNER JOIN tbl_fms_administrative_unit dis ON dis.id=p.district_id"
                + "  AND dis.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_fms_administrative_unit w ON w.id=p.administrative_unit_id"
                + "  AND w.parent_id IS NOT NULL"
                + "  INNER JOIN tbl_farm fa ON fa.id=f.farm_id and  (fa.isDuplicate is null or fa.isDuplicate =0)"
//   				"   INNER JOIN tbl_fms_administrative_unit w ON w.id=fa.administrative_unit_id" +
//   				"   INNER JOIN tbl_fms_administrative_unit dis ON dis.id=w.parent_id" +
//   				"   AND w.parent_id IS NOT NULL" +
//   				"   INNER JOIN tbl_fms_administrative_unit prov ON prov.id=dis.parent_id" +
//   				"   AND dis.parent_id IS NOT NULL"
                + " ) as tb where tb.rowNumber=1 and tb.total<>0 " + whereClause + " ) as t ) as tb1 ";
        Query query = manager.createNativeQuery(sql + orderby).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(AnimalReportDataReportDto.class));


        if (searchDto.getProvinceId() != null) {
            query.setParameter("provinceId", searchDto.getProvinceId());
        }
        if (searchDto.getDistrictId() != null) {
            query.setParameter("districtId", searchDto.getDistrictId());
        }
        if (searchDto.getWardId() != null) {
            query.setParameter("wardId", searchDto.getWardId());
        }

        if (searchDto.getListprotectionLevel() != null && searchDto.getListprotectionLevel().size() > 0) {

            query.setParameter("listprotectionLevel", searchDto.getListprotectionLevel());

        }

        List<AnimalReportDataReportDto> ret = query.getResultList();

        return ret;
    }


}
