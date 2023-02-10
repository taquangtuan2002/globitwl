package com.globits.wl.service.impl;

import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.functiondto.SearchReportForm16Dto;
import com.globits.wl.dto.report.ReportForm16ForExcelDto;
import com.globits.wl.repository.ReportForm16Repository;
import com.globits.wl.service.ExportForm16Service;
import com.globits.wl.utils.WLDateTimeUtil;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.jpa.TypedParameterValue;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.type.StandardBasicTypes;
import org.joda.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExportForm16ServiceImpl implements ExportForm16Service {

	private final Logger LOGGER = LoggerFactory.getLogger(ExportForm16ServiceImpl.class);

	@Autowired
	public EntityManager manager;

	@Autowired
	ResourceLoader resourceLoader;

	@Autowired
	private ReportForm16Repository reportForm16Repository;

	@Override
	public Workbook getReportForm16(SearchReportForm16Dto search) {
		List<ReportForm16ForExcelDto> data = this.getDataForExcel(search);
		List<ReportForm16ForExcelDto> animals = this.getDataAnimalForExcel(search);
		return writeDataToWorkBook(data, animals, search);
	}

	public List<ReportForm16ForExcelDto> getDataAnimalForExcel(SearchReportForm16Dto search) {
		StringBuilder whereBuilder = new StringBuilder();

		if (!ObjectUtils.isEmpty(search.getProvinceId())) {
			whereBuilder.append(" and prov.id = :provinceId ");
		}
		if (!ObjectUtils.isEmpty(search.getDateReport())) {
			whereBuilder.append(" and f16.date_report  <= :dateReport ");
		}
		if (!ObjectUtils.isEmpty(search.getDistrictId())) {
			whereBuilder.append(" and dis.id  = :districtId ");
		}
		if (!ObjectUtils.isEmpty(search.getWardId())) {
			whereBuilder.append(" and w.id  = :wardId ");
		}

		String sql = "select distinct tb1.animalId, tb1.animalName, tb1.cites " + "from (\n"
				+ "         select ROW_NUMBER() OVER (PARTITION BY f16.farm_id,f16.animal_id ORDER BY f16.date_report,f16.modify_date,f16.create_date DESC )\n"
				+ "                                         AS rowNumber,\n"
				+ "                f16.animal_id            as animalId,\n"
				+ "                ta.name                  as animalName,\n"
				+ "                ta.cites 				as cites\n"
				+ "         from tbl_report_form16 as f16\n"
				+ "                  INNER JOIN tbl_report_period p ON f16.report_period_id = p.id\n"
				+ "                  inner join tbl_animal ta on f16.animal_id = ta.id\n"
				+ "                  INNER JOIN tbl_fms_administrative_unit prov ON prov.id = p.province_id AND prov.parent_id IS NULL\n"
				+ "                  INNER JOIN tbl_fms_administrative_unit dis ON dis.id = p.district_id AND dis.parent_id IS NOT NULL\n"
				+ "                  INNER JOIN tbl_fms_administrative_unit w\n"
				+ "                             ON w.id = p.administrative_unit_id AND w.parent_id IS NOT NULL\n"
				+ "         where ta.cites is not null and ta.code != ''\n" + "           and f16.total <> 0\n"
				+ whereBuilder + "     ) as tb1\n" + "where tb1.rowNumber = 1\n"
				+" 					 ORDER BY tb1.cites";

		Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class)
				.setResultTransformer(new AliasToBeanResultTransformer(ReportForm16ForExcelDto.class));
		if (!ObjectUtils.isEmpty(search.getProvinceId())) {
			query.setParameter("provinceId", search.getProvinceId());
		}
		if (!ObjectUtils.isEmpty(search.getDateReport())) {
			query.setParameter("dateReport", search.getDateReport());
		}
		if (!ObjectUtils.isEmpty(search.getDistrictId())) {
			query.setParameter("districtId", search.getDistrictId());
		}
		if (!ObjectUtils.isEmpty(search.getWardId())) {
			query.setParameter("wardId", search.getWardId());
		}
		List<ReportForm16ForExcelDto> rs = (List<ReportForm16ForExcelDto>) query.getResultList();
		return rs;
	}
	@Override
	public List<ReportForm16ForExcelDto> getDataForExcel(SearchReportForm16Dto search) {
		StringBuilder whereBuilder = new StringBuilder();

		if (!ObjectUtils.isEmpty(search.getProvinceId())) {
			whereBuilder.append(" and prov.id = :provinceId ");
		}
		if (!ObjectUtils.isEmpty(search.getDateReport())) {
			whereBuilder.append(" and f16.date_report  <= :dateReport AND p.year=:year");
		}
		if (!ObjectUtils.isEmpty(search.getDistrictId())) {
			whereBuilder.append(" and dis.id  = :districtId ");
		}
		if (!ObjectUtils.isEmpty(search.getWardId())) {
			whereBuilder.append(" and w.id  = :wardId ");
		}
//		String whereClause = " ";
//		 if (search.getDateReport() != null) {	        
//	            whereClause += " AND (tb1.year = :year AND tb1.dateReport <= :dateReport) ";
//	        }
//		 if (search.getProvinceId() != null) {
//	            whereClause += " AND tb1.provinceId = :provinceId ";
//	        }
//	        if (search.getDistrictId() != null) {
//	            whereClause += " AND tb1.districtId = :districtId ";
//	        }
//	        if (search.getWardId() != null) {
//	            whereClause += " AND tb1.communeId = :communeId ";
//	        }
//	
		String sql = "select *\n" + "from (\n"
				+ "         select ROW_NUMBER() OVER (PARTITION BY f16.farm_id,f16.animal_id ORDER BY f16.date_report Desc,f16.modify_date Desc,f16.create_date DESC )\n"
				+ "                                         AS rowNumber,\n"
				+ "                tf.id                    as farmId,\n"
				+ "                tf.name                  as farmName,\n"
				+ "                tf.adress_detail         as addressDetails,\n"
				+ "                prov.id                  as provinceId,\n"
				+ "                prov.name                as provinceName,\n"
				+ "                dis.id                   as districtId,\n"
				+ "                dis.name                 as districtName,\n"
				+ "                w.id                     as communeId,\n"
				+ "                w.name                   as communeName,\n"
				+ "                tf.new_registration_code as newRegistrationCode,\n"
				+ "                tf.date_registration     as dateRegistration,\n"
				+ "                tf.phone_number          as phoneNumber,\n"
				+ "                f16.date_report          as importExportDate,\n"
				+ "                f16.total                as total,\n"
				+ "                f16.note                 as note,\n"
				+ "                f16.animal_id            as animalId,\n"
				+ "                ta.name                  as animalName,\n"
				+ "                ta.science_name          as animalScienceName,\n"
				+ "                tac.code                 as certificate,\n"
				+ "                tac.dateIssue            as registerDate \n"			    
				+ "         from tbl_report_form16 as f16\n"
				+ "                  INNER JOIN tbl_report_period p ON f16.report_period_id = p.id\n"
				+ "                  inner join tbl_animal ta on f16.animal_id = ta.id\n"
				+ "                  inner join tbl_farm tf on f16.farm_id = tf.id\n"
				+ "                  left join tbl_animal_certificate tac on tf.id = tac.farm_id\n"
				+ "                  INNER JOIN tbl_fms_administrative_unit prov ON prov.id = p.province_id AND prov.parent_id IS NULL\n"
				+ "                  INNER JOIN tbl_fms_administrative_unit dis ON dis.id = p.district_id AND dis.parent_id IS NOT NULL\n"
				+ "                  INNER JOIN tbl_fms_administrative_unit w\n"
				+ "                             ON w.id = p.administrative_unit_id AND w.parent_id IS NOT NULL\n"
				+ "         where ta.cites is not null and ta.code != ''\n" + "           \n"
//				+ "         where ta.cites is not null and ta.code != ''\n" + "           and f16.total <> 0\n"

				+ whereBuilder + "     ) as tb1\n" + "where tb1.rowNumber = 1 and tb1.total <> 0 \n" + "order by tb1.farmId, tb1.animalId";

		Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class)
				.setResultTransformer(new AliasToBeanResultTransformer(ReportForm16ForExcelDto.class));
		System.out.println(sql);
		String sqlTotal = "select count(Distinct tb1.farmId) as totalFarm , sum (tb1.total) as totalAnimal, tb1.animalId\n" + "from (\n"
				+ "         select ROW_NUMBER() OVER (PARTITION BY f16.farm_id,f16.animal_id ORDER BY f16.date_report Desc,f16.modify_date Desc,f16.create_date DESC )\n"
				+ "                                         AS rowNumber,\n"
				+ "                tf.id                    as farmId,\n"
				+ "                f16.total                as total,\n"
				+ "                f16.animal_id            as animalId\n"     
				+ "         from tbl_report_form16 as f16\n"
				+ "                  INNER JOIN tbl_report_period p ON f16.report_period_id = p.id\n"
				+ "                  inner join tbl_animal ta on f16.animal_id = ta.id\n"
				+ "                  inner join tbl_farm tf on f16.farm_id = tf.id\n"
				+ "                  left join tbl_animal_certificate tac on tf.id = tac.farm_id\n"
				+ "                  INNER JOIN tbl_fms_administrative_unit prov ON prov.id = p.province_id AND prov.parent_id IS NULL\n"
				+ "                  INNER JOIN tbl_fms_administrative_unit dis ON dis.id = p.district_id AND dis.parent_id IS NOT NULL\n"
				+ "                  INNER JOIN tbl_fms_administrative_unit w\n"
				+ "                             ON w.id = p.administrative_unit_id AND w.parent_id IS NOT NULL\n"
				+ "         where ta.cites is not null and ta.code != ''\n" + "           \n"
//				+ "         where ta.cites is not null and ta.code != ''\n" + "           and f16.total <> 0\n"
				+ whereBuilder + "     ) as tb1\n" + "where tb1.rowNumber = 1 and tb1.total <> 0 \n" + "group by tb1.animalId";
		Query queryTotal = manager.createNativeQuery(sqlTotal).unwrap(org.hibernate.query.Query.class)
				.setResultTransformer(new AliasToBeanResultTransformer(ReportForm16ForExcelDto.class));
		System.out.println(queryTotal);
		if (!ObjectUtils.isEmpty(search.getProvinceId())) {
			query.setParameter("provinceId", search.getProvinceId());
			queryTotal.setParameter("provinceId", search.getProvinceId());
		}
		if (!ObjectUtils.isEmpty(search.getDateReport())) {
			query.setParameter("dateReport", search.getDateReport());
			queryTotal.setParameter("dateReport", search.getDateReport());
		}
		if (!ObjectUtils.isEmpty(search.getDateReport())) {
			query.setParameter("year", WLDateTimeUtil.getYear(search.getDateReport()));
			queryTotal.setParameter("year", WLDateTimeUtil.getYear(search.getDateReport()));
		}
		
		if (!ObjectUtils.isEmpty(search.getDistrictId())) {
			query.setParameter("districtId", search.getDistrictId());
			queryTotal.setParameter("districtId", search.getDistrictId());
		}
		if (!ObjectUtils.isEmpty(search.getWardId())) {
			query.setParameter("wardId", search.getWardId());
			queryTotal.setParameter("wardId", search.getWardId());
		}
		
//		if (search.getDateReport() != null) {
//			Integer year = WLDateTimeUtil.getYear(search.getDateReport());
//            query.setParameter("year", year);
//            query.setParameter("dateReport", search.getDateReport());
//        }
		
		
		List<ReportForm16ForExcelDto> rsTotal = (List<ReportForm16ForExcelDto>) queryTotal.getResultList();
		List<ReportForm16ForExcelDto> rs = (List<ReportForm16ForExcelDto>) query.getResultList();
		List<ReportForm16ForExcelDto> reportForm16ForExcelDtoList = new ArrayList<>();
		if (rs != null && rs.size() >0){
			for (ReportForm16ForExcelDto reportForm16ForExcelDto : rs){
				for (ReportForm16ForExcelDto reportForm16ForExcelDtoTotal : rsTotal){
					if (reportForm16ForExcelDtoTotal.getAnimalId().equals(reportForm16ForExcelDto.getAnimalId())){
						reportForm16ForExcelDto.setTotalFarm(reportForm16ForExcelDtoTotal.getTotalFarm());
						reportForm16ForExcelDto.setTotalAnimal(reportForm16ForExcelDtoTotal.getTotalAnimal());
						reportForm16ForExcelDtoList.add(reportForm16ForExcelDto);
					}
				}
			}
		}
		return reportForm16ForExcelDtoList;
	}

	private Workbook writeDataToWorkBook(List<ReportForm16ForExcelDto> data, List<ReportForm16ForExcelDto> animals,
			SearchReportForm16Dto search) {
		Workbook workBook = null;
		try (InputStream template = resourceLoader.getResource("classpath:template/excel/BC1_3.xlsx")
				.getInputStream()) {
			workBook = new XSSFWorkbook(template);
		} catch (IOException e) {
			LOGGER.error("Error reading workbook");
		}

		if (workBook == null) {
			throw new RuntimeException();
		}

		Font font = workBook.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short)11);

		Font fontBoldTitle = workBook.createFont();
		font.setFontName("Times New Roman");
		fontBoldTitle.setBold(true);
		fontBoldTitle.setFontHeightInPoints((short)11);

		CellStyle titleCellStyle =  workBook.createCellStyle();
		titleCellStyle.setFont(fontBoldTitle);

		CellStyle tableHeadCellStyle = workBook.createCellStyle();
		tableHeadCellStyle.setFont(font);
		Sheet sheet =  workBook.getSheetAt(0);
		int columnCount = 15;
		Row row;
		Cell cell;
		if(!CollectionUtils.isEmpty(data)){
			row = sheet.getRow(1);
			cell = row.getCell(0);
			cell.setCellValue(cell.getStringCellValue()+" "+ data.get(0).getProvinceName());
		}
		row = sheet.getRow(7);
		List<ReportForm16ForExcelDto> animalSorted = animals.stream()
				.sorted(Comparator.comparing(ReportForm16ForExcelDto::getCites,Comparator.nullsLast(Comparator.naturalOrder())))
				.collect(Collectors.toList());
		for (ReportForm16ForExcelDto animal : animalSorted) {
			cell=row.createCell(columnCount++);
			cell.setCellStyle(titleCellStyle);
			try {
				if(StringUtils.hasText(animal.getCites())) {
					cell.setCellValue("("+ animal.getCites() + ") " + animal.getAnimalName());
				}else {
					cell.setCellValue(animal.getAnimalName());
				}
			}catch (Exception e) {
				cell.setCellValue("");
			}
		}
		cell = row.createCell(columnCount++);
		cell.setCellStyle(titleCellStyle);
		cell.setCellValue("Đánh dấu mẫu vật");
		cell = row.createCell(columnCount++);
		cell.setCellStyle(titleCellStyle);
		cell.setCellValue("Nguồn gốc");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		int rownum = 8;// vị trí bắt đầu sét dữ liệu
		int stt = 1;
		int cellNum= 0;
		int total=0;
		Set<BigInteger> farmIds = new HashSet<>();
		for (ReportForm16ForExcelDto dto : data) {
			farmIds.add(dto.getFarmId());
			row = sheet.createRow(rownum++);
			cellNum = 0;
			if(row !=null){
				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				cell.setCellValue(stt++);

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getFarmName());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getAddressDetails());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getCommuneName());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getDistrictName());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getNewRegistrationCode());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getPhoneNumber());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(sdf.format(dto.getDateRegistration()) );
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(sdf.format(dto.getImportExportDate()));
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getAnimalName());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getAnimalScienceName());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getTotal());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getNote());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(dto.getCertificate());
				}catch (Exception e){
					cell.setCellValue("");
				}

				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(sdf.format(dto.getRegisterDate()));
				}catch (Exception e){
					cell.setCellValue("");
				}
				for (ReportForm16ForExcelDto animal : animalSorted) {
					cell=row.createCell(cellNum++);
					cell.setCellStyle(tableHeadCellStyle);
					if(animal.getAnimalId().equals(dto.getAnimalId())){
						cell.setCellValue(dto.getTotal());
						total+=dto.getTotal();
						if(animal.getTotal()!=null){
							animal.setTotal(animal.getTotal()+dto.getTotal());
						}else{
							animal.setTotal(dto.getTotal());
						}
					}else{
						cell.setCellValue("");
					}
				}
				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue("");
				}catch (Exception e){
					cell.setCellValue("");
				}
				cell = row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue("");
				}catch (Exception e){
					cell.setCellValue("");
				}

			}
		}
		row = sheet.createRow(rownum++);
		cellNum = 0;
		if(row !=null){
			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("Tổng");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue(farmIds.size());

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue(total);

			cell = row.createCell(cellNum++);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");
			for (ReportForm16ForExcelDto animal : animalSorted) {
				cell=row.createCell(cellNum++);
				cell.setCellStyle(tableHeadCellStyle);
				try{
					cell.setCellValue(animal.getTotal());
				}catch (Exception e){
					cell.setCellValue("");
				}
			}
			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");
			cell = row.createCell(cellNum++);
			cell.setCellStyle(tableHeadCellStyle);
			cell.setCellValue("");

		}
		return workBook;
	}


	@Override
	public Workbook exportReportForm16(SearchReportForm16Dto search) {
		TypedParameterValue farmId = new TypedParameterValue(StandardBasicTypes.LONG, search.getFarmId());
		TypedParameterValue fromDate = new TypedParameterValue(StandardBasicTypes.DATE, search.getFromDate());
		TypedParameterValue toDate = new TypedParameterValue(StandardBasicTypes.DATE, search.getToDate());
		TypedParameterValue textSearch = new TypedParameterValue(StandardBasicTypes.STRING, search.getTextSearch());
		TypedParameterValue year = new TypedParameterValue(StandardBasicTypes.INTEGER, search.getYear());
		TypedParameterValue month = new TypedParameterValue(StandardBasicTypes.INTEGER, search.getMonth());
		TypedParameterValue day = new TypedParameterValue(StandardBasicTypes.INTEGER, search.getDay());
		TypedParameterValue animalClass = new TypedParameterValue(StandardBasicTypes.STRING, search.getAnimalClass());
		TypedParameterValue animalOrdo = new TypedParameterValue(StandardBasicTypes.STRING, search.getAnimalOrdo());
		TypedParameterValue animalFamily = new TypedParameterValue(StandardBasicTypes.STRING, search.getAnimalFamily());
		TypedParameterValue animalId = new TypedParameterValue(StandardBasicTypes.LONG, search.getAnimalId());
		TypedParameterValue provinceId = new TypedParameterValue(StandardBasicTypes.LONG, search.getProvinceId());
		TypedParameterValue districtId = new TypedParameterValue(StandardBasicTypes.LONG, search.getDistrictId());
		TypedParameterValue wardId = new TypedParameterValue(StandardBasicTypes.LONG, search.getWardId());


		List<ReportForm16Dto> data = reportForm16Repository.findAllForReportExcel(farmId, fromDate, toDate,textSearch,year,month,day,
				animalClass,animalOrdo,animalFamily,animalId,provinceId,districtId,wardId);
		if (!CollectionUtils.isEmpty(data)) {
			Map<AnimalDto, List<ReportForm16Dto>> dataMap = new HashMap<>();
			for (ReportForm16Dto dto : data) {
				if (dto.getAnimal() != null) {
					if (dataMap.containsKey(dto.getAnimal())) {
						dataMap.get(dto.getAnimal()).add(dto);
					} else {
						List<ReportForm16Dto> list = new ArrayList<>();
						list.add(dto);
						dataMap.put(dto.getAnimal(), list);
					}

				}
			}
			return insertDataReportForm16(dataMap, search);
		}

		return null;
	}

	private Workbook insertDataReportForm16(Map<AnimalDto,List<ReportForm16Dto>> data, SearchReportForm16Dto search){
		Workbook workBook = new XSSFWorkbook();
		int index = 1;
		if (!CollectionUtils.isEmpty(data)) {
			Font font = workBook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("Times New Roman");
			font.setBold(true);

			Font font1 = workBook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("Times New Roman");

			CellStyle cellStyle = workBook.createCellStyle();
			cellStyle.setBorderBottom(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setWrapText(true);
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyle.setFont(font1);

			Font fontNoBorder = workBook.createFont();
			fontNoBorder.setFontHeightInPoints((short) 13);
			fontNoBorder.setBold(true);
			fontNoBorder.setFontName("Times New Roman");

			CellStyle cellStyleNoBoder = workBook.createCellStyle();
			cellStyleNoBoder.setWrapText(true);
			cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
			cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleNoBoder.setFont(fontNoBorder);

			CellStyle cellStyleBoldTable = workBook.createCellStyle();
			cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
			cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
			cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
			cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
			cellStyleBoldTable.setWrapText(true);
			cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
			cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleBoldTable.setFont(fontNoBorder);
			for(Map.Entry<AnimalDto,List<ReportForm16Dto>> entry : data.entrySet()){
				String sheetName = index++ + "-" + entry.getKey().getCode() + " - " + entry.getKey().getName();
				sheetName = sheetName.replaceAll("\\\\", "");
				sheetName = sheetName.replaceAll("/", "");
				Sheet sheet = workBook.createSheet(sheetName);
				Row row = null;
				Cell cell = null;
				int rowIndex = 0;
				int cellIndex = 0;
				sheet.createRow(0);
				sheet.createRow(1);
				sheet.createRow(2);

				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tỉnh");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Huyện");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Xã");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Ấp/Thôn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Mã số hệ thống");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tên cơ sở");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Ngày");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tên Loài nuôi");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex +1));
				cell.setCellStyle(cellStyleBoldTable);

//				row = sheet.getRow(rowIndex + 1);
//				cell = row.createCell(cellIndex);
//				sheet.setColumnWidth(cellIndex, 14 * 256);
//				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);


				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tên Việt Nam");
				sheet.setColumnWidth(cellIndex, 17 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tên Khoa học");
				sheet.setColumnWidth(cellIndex, 17 * 256);
				cell.setCellStyle(cellStyleBoldTable);



				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Mã loài");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tổng số lượng");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 3));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tổng");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 9 * 256);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đàn bố mẹ");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex+1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 9 * 256);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đàn giống hậu bị");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 9 * 256);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 9 * 256);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 9 * 256);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Số lượng con dưới 1 tuổi (chưa trưởng thành)");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex +2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 9 * 256);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 9 * 256);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 9 * 256);


				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Số lượng cá thể trên 1 tuổi");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tổng tăng đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +2, cellIndex, cellIndex ));
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Bố mẹ tăng đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Hậu bị tăng đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cá thể dưới 1 tuổi tăng đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cá thể trên 1 tuổi tăng đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Lý do tăng");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +2, cellIndex, cellIndex ));
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tổng giảm đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +2, cellIndex, cellIndex ));
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Bố mẹ giảm đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Hậu bị giảm đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cá thể dưới 1 tuổi giảm đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cá thể trên 1 tuổi giảm đàn");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Lý do giảm");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +2, cellIndex, cellIndex ));
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Ghi chú");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);
				int rowStartTable = 3;
				List<ReportForm16Dto> dataInsert = entry.getValue().stream()
						.sorted(Comparator.comparing(ReportForm16Dto::getDateReport,Comparator.nullsFirst(Comparator.naturalOrder())).reversed())
						.collect(Collectors.toList());
				for (ReportForm16Dto reportForm16 : dataInsert) {
					SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					if (reportForm16 != null) {
						row = sheet.createRow(rowStartTable++);
						cellIndex = 0;

						// Tỉnh
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try{
							cell.setCellValue(reportForm16.getProvinceDto().getName());
						} catch (Exception e) {
							cell.setCellValue("");
						}

						// Huyện
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try{
							cell.setCellValue(reportForm16.getDistrictDto().getName());
						} catch (Exception e) {
							cell.setCellValue("");
						}
						// Xã
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try{
							cell.setCellValue(reportForm16.getAdministrativeUnitDto().getName());
						} catch (Exception e) {
							cell.setCellValue("");
						}
						// Thôn/Ấp
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try{
							cell.setCellValue(reportForm16.getFarm().getVillage());
						} catch (Exception e) {
							cell.setCellValue("");
						}
						// Mã cơ sở
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try{
							cell.setCellValue(reportForm16.getFarm().getCode());
						} catch (Exception e) {
							cell.setCellValue("");
						}
						// Tên cơ sở
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try{
							cell.setCellValue(reportForm16.getFarm().getName());
						} catch (Exception e) {
							cell.setCellValue("");
						}
						// Ngày import
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try {
							cell.setCellValue(formatter.format(reportForm16.getDateReport()));
						} catch (Exception e) {
							cell.setCellValue("");
						}

						// tên loài nuôi
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try {
							cell.setCellValue(reportForm16.getAnimal().getName());
						} catch (Exception e) {
							cell.setCellValue("");
						}

						// Tên khoa học
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try {
							cell.setCellValue(reportForm16.getAnimal().getScienceName());
						} catch (Exception e) {
							cell.setCellValue("");
						}

						// mã loài
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try {
							cell.setCellValue(reportForm16.getAnimal().getCode());
						} catch (Exception e) {
							cell.setCellValue("");
						}

						// Tổng số cá thể
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						try {
							cell.setCellValue(reportForm16.getTotal());
						} catch (Exception e) {
							cell.setCellValue("");
						}
						// Tổng số cá thể Đực
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getMale() != null) {
							cell.setCellValue(reportForm16.getMale());
						} else {
							cell.setCellValue("");
						}
						// Tổng số cá thể cái
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getFemale() != null) {
							cell.setCellValue(reportForm16.getFemale());
						} else {
							cell.setCellValue("");
						}
						// Tổng số cá thể khác
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getUnGender() != null) {
							cell.setCellValue(reportForm16.getUnGender());
						} else {
							cell.setCellValue("");
						}

						// Đực bố mẹ
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getMaleParent() != null) {
							cell.setCellValue(reportForm16.getMaleParent());
						} else {
							cell.setCellValue("");
						}
						// Cái bố mẹ
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getFemaleParent() != null) {
							cell.setCellValue(reportForm16.getFemaleParent());
						} else {
							cell.setCellValue("");
						}
						// Không rõ Bố mẹ
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getUnGenderParent() != null) {
							cell.setCellValue(reportForm16.getUnGenderParent());
						} else {
							cell.setCellValue("");
						}
						// đực hậu bị
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getMaleGilts() != null) {
							cell.setCellValue(reportForm16.getMaleGilts());
						} else {
							cell.setCellValue("");
						}
						// cái hậu bị
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getFemaleGilts() != null) {
							cell.setCellValue(reportForm16.getFemaleGilts());
						} else {
							cell.setCellValue("");
						}
						// Không rõ hậu bị
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getUnGenderGilts() != null) {
							cell.setCellValue(reportForm16.getUnGenderGilts());
						} else {
							cell.setCellValue("");
						}

						// Đực dưới 1 tuổi
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getMaleChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16.getMaleChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}
						// Cái dưới 1 tuổi
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getFemaleChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16.getFemaleChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}
						// Không xác định dưới 1 tuổi
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16.getChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}
						// đực trên 1 tuổi
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getMaleChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16.getMaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}
						// cái trên 1 tuổi
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getFemaleChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16.getFemaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}
						// không xác định trên 1 tuổi
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getUnGenderChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16.getUnGenderChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}

						// tổng đàn tăng
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getTotalImport() != null) {
							cell.setCellValue(reportForm16.getTotalImport());
						} else {
							cell.setCellValue("");
						}

						// đực bố mẹ tăng đàn
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportMaleParent() != null) {
							cell.setCellValue(reportForm16.getImportMaleParent());
						} else {
							cell.setCellValue("");
						}
						// cái bố mẹ tăng đàn
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportFemaleParent() != null) {
							cell.setCellValue(reportForm16.getImportFemaleParent());
						}else {
							cell.setCellValue("");
						}
						// không xác định bố mẹ tăng đàn
						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportUnGenderParent() != null) {
							cell.setCellValue(reportForm16.getImportUnGenderParent());
						} else {
							cell.setCellValue("");
						}

						// đực hậu bị tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportMaleGilts() != null) {
							cell.setCellValue(reportForm16.getImportMaleGilts());
						} else {
							cell.setCellValue("");
						}
						// cái hậu bị tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportFemaleGilts() != null) {
							cell.setCellValue(reportForm16.getImportFemaleGilts());
						} else {
							cell.setCellValue("");
						}
						// không xác định hậu bị tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportUnGenderGilts() != null) {
							cell.setCellValue(reportForm16.getImportUnGenderGilts());
						} else {
							cell.setCellValue("");
						}

						// đực  dưới 1 tuổi tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportMaleChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16.getImportMaleChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}
						// cái dưới 1 tuổi tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportFemaleChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16.getImportFemaleChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}
						// không xác định dưới 1 tuổi tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16.getImportChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}

						// đực trên 1 năm tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportMaleChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16.getImportMaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}
						// cái trên 1 năm tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportFemaleChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16.getImportFemaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}
						// không xác định trên 1 năm tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportUnGenderChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16.getImportUnGenderChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}

						// Lý do  tăng đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getImportReason() != null) {
							cell.setCellValue(reportForm16.getImportReason());
						} else {
							cell.setCellValue("");
						}

						// tổng đàn giảm

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getTotalExport() != null) {
							cell.setCellValue(reportForm16.getTotalExport());
						} else {
							cell.setCellValue("");
						}

						// đực bố mẹ giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportMaleParent() != null) {
							cell.setCellValue(reportForm16.getExportMaleParent());
						} else {
							cell.setCellValue("");
						}
						// cái bố mẹ giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportFemaleParent() != null) {
							cell.setCellValue(reportForm16.getExportFemaleParent());
						} else {
							cell.setCellValue("");
						}
						// không xác định bố mẹ giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportUnGenderParent() != null) {
							cell.setCellValue(reportForm16.getExportUnGenderParent());
						} else {
							cell.setCellValue("");
						}



						// đực hậu bị giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportMaleGilts() != null) {
							cell.setCellValue(reportForm16.getExportMaleGilts());
						} else {
							cell.setCellValue("");
						}
						// cái hậu bị giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportFemaleGilts() != null) {
							cell.setCellValue(reportForm16.getExportFemaleGilts());
						} else {
							cell.setCellValue("");
						}
						// không xác định hậu bị giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportUnGenderGilts() != null) {
							cell.setCellValue(reportForm16.getExportUnGenderGilts());
						} else {
							cell.setCellValue("");
						}

						// đực  dưới 1 tuổi giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportMaleChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16.getExportMaleChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}
						// cái dưới 1 tuổi giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportFemaleChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16.getExportFemaleChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}
						// không xác định dưới 1 tuổi giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16.getExportChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}

						// đực trên 1 năm giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportMaleChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16.getExportMaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}
						// cái trên 1 năm giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportFemaleChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16.getExportFemaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}
						// không xác định trên 1 năm giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportUnGenderChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16.getExportUnGenderChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}

						// Lý do  giảm đàn

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getExportReason() != null) {
							cell.setCellValue(reportForm16.getExportReason());
						} else {
							cell.setCellValue("");
						}

						cell = row.createCell(cellIndex++);
						cell.setCellStyle(cellStyle);
						if (reportForm16.getNote() != null) {
							cell.setCellValue(reportForm16.getNote());
						} else {
							cell.setCellValue("");
						}
					}

				}
			}


		}

		return workBook;
	}
}
