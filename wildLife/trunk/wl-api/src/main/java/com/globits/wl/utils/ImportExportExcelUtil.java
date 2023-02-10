package com.globits.wl.utils;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.GroupLayout.Alignment;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress8Bit;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.format.CellFormatType;
import org.apache.poi.ss.usermodel.BorderExtent;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellAddress;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.PropertyTemplate;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;

import com.globits.core.dto.AdministrativeUnitDto;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FarmProductTarget;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataFormDto;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.BiologicalClassDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FarmProductTargetDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.ForestProductsListDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.InjectionPlantDto;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.Report18Dto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.ReportFormAnimalEggDto;
import com.globits.wl.dto.ReportFormAnimalGiveBirthDto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.functiondto.AnimalReportDataFromCityDto;
import com.globits.wl.dto.functiondto.AnimalReportDataFromDistrictDto;
import com.globits.wl.dto.functiondto.AnimalReportDataFromWardDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.DashboardSearchDto;
import com.globits.wl.dto.functiondto.DataDvhdDto;
import com.globits.wl.dto.functiondto.FarmAnimal2017Dto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.Report18CityDto;
import com.globits.wl.dto.functiondto.Report18DistrictDto;
import com.globits.wl.dto.functiondto.Report18WardDto;
import com.globits.wl.dto.functiondto.UserExportDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.dto.report.LiveStockProductReportDto;
import com.globits.wl.dto.report.TotalReportDto;
import com.globits.wl.dto.report.TotalReportSubDetailDto;
import com.globits.wl.dto.report.TotalReportSubDto;
import org.springframework.util.StringUtils;

public class ImportExportExcelUtil {

	private static Boolean isScanIndexMode = true;

	private static Hashtable<String, Integer> hashAdministrativeUnitColumnConfig = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> hashAnimalColumnConfig = new Hashtable<String, Integer>();
	private static Hashtable<String, Integer> hashFarmColumnConfig = new Hashtable<String, Integer>();

	private static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	private static DecimalFormat numberFormatter = new DecimalFormat("######################");

	public static String stringData(String columnConfigName, Row currentRow, Cell currentCell,
			Hashtable<String, Integer> hashSubjectKnowlegeColumnConfig) {
		Integer index = hashSubjectKnowlegeColumnConfig.get(columnConfigName);
		if (index != null) {
			currentCell = currentRow.getCell(index);// studentCode
			if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
				double value = currentCell.getNumericCellValue();
				String stringData = numberFormatter.format(value);
				return stringData.trim();
			} else if (currentCell != null && currentCell.getStringCellValue() != null) {
				String stringData = currentCell.getStringCellValue();
				return stringData.trim();
			}
		}
		return null;
	} 

	public static Date dateData(String columnConfigName, Row currentRow, Cell currentCell,
			Hashtable<String, Integer> hashSubjectKnowlegeColumnConfig) {
		Integer index = hashSubjectKnowlegeColumnConfig.get(columnConfigName);
		if (index != null) {
			currentCell = currentRow.getCell(index);
			if (currentCell != null && currentCell.getStringCellValue() != null) {
				String strDateData = currentCell.getStringCellValue();
				if (strDateData != null) {
					try {
						Date dateData = dateFormat.parse(strDateData);
						return dateData;
					} catch (Exception ex) {

					}
				}
			}
		}
		return null;
	}

	public static List<FmsAdministrativeUnitDto> importAdministrativeUnitFromInputStream(InputStream is) {
		List<FmsAdministrativeUnitDto> ret = new ArrayList<FmsAdministrativeUnitDto>();
		hashAdministrativeUnitColumnConfig.put("city", 0);// tỉnh thành phố
		hashAdministrativeUnitColumnConfig.put("cityCode", 1);// mã tỉnh
//		hashAdministrativeUnitColumnConfig.put("cityArea", 2);// diện tích tỉnh
//		hashAdministrativeUnitColumnConfig.put("cityLongtidu", 3);// kinh độ tỉnh
//		hashAdministrativeUnitColumnConfig.put("citylatidu", 4);// vĩ độ tỉnh
		hashAdministrativeUnitColumnConfig.put("district", 2);// quận huyện
		hashAdministrativeUnitColumnConfig.put("districtCode", 3); // mã quận huyện
//		hashAdministrativeUnitColumnConfig.put("districtArea", 7);// diện tích huyện
//		hashAdministrativeUnitColumnConfig.put("districtLongtidu", 8);// kinh độ huyện
//		hashAdministrativeUnitColumnConfig.put("districtlatidu", 9);// vĩ độ huyện
		hashAdministrativeUnitColumnConfig.put("wards", 4);// phường xã
		hashAdministrativeUnitColumnConfig.put("wardsCode", 5); // mã phường xã
//		hashAdministrativeUnitColumnConfig.put("wardsArea", 12);// diện tích xã
//		hashAdministrativeUnitColumnConfig.put("wardsLongtidu", 13);// kinh độ xã
//		hashAdministrativeUnitColumnConfig.put("wardslatidu", 14);// vĩ độ xã

		try {
			@SuppressWarnings("resource")

			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			// Iterator<Row> iterator = datatypeSheet.iterator();
			int rowIndex = 1;
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					FmsAdministrativeUnitDto unitCity = new FmsAdministrativeUnitDto();
					Integer index = hashAdministrativeUnitColumnConfig.get("city");
					if (index != null) {
						currentCell = currentRow.getCell(index);// subjectCode
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String name = currentCell.getStringCellValue();
							unitCity.setName(name);
						}
					}
					index = hashAdministrativeUnitColumnConfig.get("cityCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);// subjectName
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String code = currentCell.getStringCellValue();
							unitCity.setCode(code);
							unitCity.setLevel(1);// cấp 1 - tỉnh thành phố
						}
					}
//					index = hashAdministrativeUnitColumnConfig.get("cityArea");
//					if (index != null) {
//						currentCell = currentRow.getCell(index);// subjectName
//						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
//								&& currentCell.getNumericCellValue() > 0) {
//							double codeD = currentCell.getNumericCellValue();
//							unitCity.setTotalAcreage(codeD);
//						} else if (currentCell != null && currentCell.getStringCellValue() != null
//								&& currentCell.getStringCellValue().length() > 0) {
//							String code = currentCell.getStringCellValue();
//							if (code != null && code.length() > 0)
//								unitCity.setTotalAcreage(Double.valueOf(code));
//
//						}
//					}
					if (!containsUnit(unitCity.getCode(), ret)) {
						ret.add(unitCity);
					}

					FmsAdministrativeUnitDto unitDistrict = new FmsAdministrativeUnitDto();
					index = hashAdministrativeUnitColumnConfig.get("district");
					if (index != null) {
						currentCell = currentRow.getCell(index);// subjectNameEng
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String name = currentCell.getStringCellValue();
							unitDistrict.setName(name);
						}
					}

					index = hashAdministrativeUnitColumnConfig.get("districtCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);//
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String code = currentCell.getStringCellValue();
							unitDistrict.setCode(code);
							unitDistrict.setLevel(2);// cấp 2- quận huyện
							unitDistrict.setParentDto(unitCity);
						}
					}
//					index = hashAdministrativeUnitColumnConfig.get("districtArea");
//					if (index != null) {
//						currentCell = currentRow.getCell(index);// subjectName
//						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
//								&& currentCell.getNumericCellValue() > 0) {
//							double codeD = currentCell.getNumericCellValue();
//							unitDistrict.setTotalAcreage(codeD);
//						} else if (currentCell != null && currentCell.getStringCellValue() != null
//								&& currentCell.getStringCellValue().length() > 0) {
//							String code = currentCell.getStringCellValue();
//							if (code != null && code.length() > 0)
//								unitDistrict.setTotalAcreage(Double.valueOf(code));
//
//						}
//					}
					if (!containsUnit(unitDistrict.getCode(), ret)) {
						ret.add(unitDistrict);
					}

					FmsAdministrativeUnitDto unitWards = new FmsAdministrativeUnitDto();
					index = hashAdministrativeUnitColumnConfig.get("wards");
					if (index != null) {
						currentCell = currentRow.getCell(index);// subjectNameEng
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String name = currentCell.getStringCellValue();
							unitWards.setName(name);
						}
					}

					index = hashAdministrativeUnitColumnConfig.get("wardsCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);//
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String code = currentCell.getStringCellValue();
							unitWards.setCode(code);
							unitWards.setLevel(3);// cấp 3- phường / xã/ thị trấn
							unitWards.setParentDto(unitDistrict);
						}
					}
//					index = hashAdministrativeUnitColumnConfig.get("wardsArea");
//					if (index != null) {
//						currentCell = currentRow.getCell(index);// subjectName
//						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
//								&& currentCell.getNumericCellValue() > 0) {
//							double codeD = currentCell.getNumericCellValue();
//							unitWards.setTotalAcreage(codeD);
//						} else if (currentCell != null && currentCell.getStringCellValue() != null
//								&& currentCell.getStringCellValue().length() > 0) {
//							String code = currentCell.getStringCellValue();
//							if (code != null && code.length() > 0)
//								unitWards.setTotalAcreage(Double.valueOf(code));
//
//						}
//					}
					if (!containsUnit(unitWards.getCode(), ret)) {
						ret.add(unitWards);
					}

				}
				rowIndex++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static List<BiologicalClassDto> importBiologicalClassFromInputStream(InputStream is) {
		List<BiologicalClassDto> ret = new ArrayList<BiologicalClassDto>();
		hashAdministrativeUnitColumnConfig.put("Class_EN", 0);// tên lớp tiếng anh
		hashAdministrativeUnitColumnConfig.put("Class_VN", 1);// tên lớp tiếng việt
		hashAdministrativeUnitColumnConfig.put("Class_code", 2); // chữ cái đầu tạo mẫu
		hashAdministrativeUnitColumnConfig.put("Order", 3);// tên bộ tiếng anh
		hashAdministrativeUnitColumnConfig.put("Order_VN", 4); // tên bộ tiếng việt
		hashAdministrativeUnitColumnConfig.put("Family_E", 5);// tên họ tiếng anh
		hashAdministrativeUnitColumnConfig.put("Family_VN", 6); // tên họ tiếng việt
		
		try {
			@SuppressWarnings("resource")

			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			int rowIndex = 1;
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					BiologicalClassDto dto = new BiologicalClassDto();
					Integer index = hashAdministrativeUnitColumnConfig.get("Class_EN");
					if (index != null) {
						currentCell = currentRow.getCell(index); 
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String classAnimalE = currentCell.getStringCellValue();
							dto.setClassAnimalE(classAnimalE.trim());
						}
					}
					
					index = hashAdministrativeUnitColumnConfig.get("Class_VN");
					if (index != null) {
						currentCell = currentRow.getCell(index); 
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String classAnimal = currentCell.getStringCellValue();
							dto.setClassAnimal(classAnimal.trim());
						}
					}
					
					index = hashAdministrativeUnitColumnConfig.get("Class_code");
					if (index != null) {
						currentCell = currentRow.getCell(index); 
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String classCode = currentCell.getStringCellValue();
							dto.setClassCode(classCode.trim());
						}
					}
					
					index = hashAdministrativeUnitColumnConfig.get("Order");
					if (index != null) {
						currentCell = currentRow.getCell(index); 
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String ordoE = currentCell.getStringCellValue();
							dto.setOrdoE(ordoE.trim());
						}
					}
					
					index = hashAdministrativeUnitColumnConfig.get("Order_VN");
					if (index != null) {
						currentCell = currentRow.getCell(index); 
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String ordo = currentCell.getStringCellValue();
							dto.setOrdo(ordo.trim());
						}
					}
					
					index = hashAdministrativeUnitColumnConfig.get("Family_E");
					if (index != null) {
						currentCell = currentRow.getCell(index); 
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String familyE = currentCell.getStringCellValue();
							dto.setFamilyE(familyE.trim());
						}
					}
					
					index = hashAdministrativeUnitColumnConfig.get("Family_VN");
					if (index != null) {
						currentCell = currentRow.getCell(index); 
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String family = currentCell.getStringCellValue();
							dto.setFamily(family.trim());
						}
					}
					
					ret.add(dto);
				}
				rowIndex++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<AnimalDto> importAnimal(InputStream is) {
		hashAnimalColumnConfig.put("code", 0);// mã động vật
		hashAnimalColumnConfig.put("name", 1);// tên động vật
		hashAnimalColumnConfig.put("description", 2);// mô tả
		hashAnimalColumnConfig.put("type", 3); // loại (cha)
		hashAnimalColumnConfig.put("orginal", 4);// nguồn gốc
		hashAnimalColumnConfig.put("animalType", 5);// nguồn gốc
		hashAnimalColumnConfig.put("product", 6); // hướng sản phẩm
		try {

			List<AnimalDto> ret = new ArrayList<AnimalDto>();

			@SuppressWarnings("resource")
			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);

			int rowIndex = 1;

			AnimalDto animalDto = new AnimalDto();
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				int number = 0; // khởi tạo số phẩn tử con
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					Integer index = hashAnimalColumnConfig.get("code");
					if (index != null) {
						currentCell = currentRow.getCell(index);// courseSubjectCode
						if (currentCell != null && currentCell.getStringCellValue() != null) {
							String code = currentCell.getStringCellValue();
							// Nếu có mã không phải '' thì setCode
							if (code.length() > 0) {
								animalDto.setCode(code);
								;// Set code để tìm kiếm cho trùng
								boolean isNotChange = true;
								number = 1; // phần tử đầu tiên
								String tempCode = null;
								String name = null;
								String description = null;
								String parent = null;
								String orignalName = null;
								String typeName = null;

								// kiểm tra các dòng tiếp theo để xem có bao nhiêu phần tử trong 1 animal

								int tempRowIndex = rowIndex + 1;
								while (isNotChange && tempRowIndex <= num) {
									Row tempRow = datatypeSheet.getRow(tempRowIndex);
									Cell tempCell = null;
									Cell nameCell = null;
									Cell descriptionCell = null;
									Cell parentCell = null;
									Cell orignalCell = null;
									Cell typeCell = null;
									if (tempRow != null) {
										tempCell = tempRow.getCell(index);
										nameCell = tempRow.getCell(hashAnimalColumnConfig.get("name"));// tên động vật
										descriptionCell = tempRow.getCell(hashAnimalColumnConfig.get("description"));// mô
																														// tả
										parentCell = tempRow.getCell(hashAnimalColumnConfig.get("type"));// cha
										orignalCell = tempRow.getCell(hashAnimalColumnConfig.get("orginal"));// nguồn
																												// gốc
										typeCell = tempRow.getCell(hashAnimalColumnConfig.get("animalType"));// loại vật
																												// nuôi

										if (tempCell != null && tempCell.getStringCellValue() != null) {
											tempCode = tempCell.getStringCellValue();
											if (nameCell != null) {
												name = nameCell.getStringCellValue();

											}
											if (descriptionCell != null) {
												description = descriptionCell.getStringCellValue();
												// animalDto.setDescription(description);
											}
											if (parentCell != null) {
												// AnimalDto an=new AnimalDto();
												parent = parentCell.getStringCellValue();
												// an.setName(parent);
												// animalDto.setParent(an);
											}
											if (orignalCell != null) {
												// OriginalDto originalDto=new OriginalDto();
												orignalName = orignalCell.getStringCellValue();
												// originalDto.setName(orignalName);
												// animalDto.setOriginalDto(originalDto);
											}
											if (typeCell != null) {
												// AnimalTypeDto typeDto=new AnimalTypeDto();
												typeName = typeCell.getStringCellValue();
												// typeDto.setName(typeName);
												// animalDto.setTypeDto(typeDto);
											}

											// nếu mã lớp là khoảng trắng và mã giáo viên không phải khoản trắng thì số
											// phần tử tăng lên
											// (xét thêm mã giáo viên vì trường hợp dòng cuối cùng sẽ lấy được rất nhiều
											// ô có khoảng trắng ở cột số 1, sẽ điền dữ liệu rỗng vào dto)
											if (tempCode == code && tempCode.length() > 0 && name.length() > 0) {// &&
																													// staffCode.length()>0
												number++;
												tempRowIndex++;

												animalDto.setName(name);
												animalDto.setDescription(description);
												if (parent != null && parent.length() > 0) {
													AnimalDto an = new AnimalDto();
													an.setName(parent);
													animalDto.setParent(an);
												}
												if (orignalName != null && orignalName.length() > 0) {
													OriginalDto originalDto = new OriginalDto();
													originalDto.setName(orignalName);
													animalDto.setOriginalDto(originalDto);
												}
												if (typeName != null && typeName.length() > 0) {
													AnimalTypeDto typeDto = new AnimalTypeDto();
													typeDto.setName(typeName);
													animalDto.setTypeDto(typeDto);
												}
											}
											// nếu có code thì đã nhảy sang code của animal khác
											if (tempCode != code) {// || staffCode.length()<=0
												if (number == 1) {
													tempRow = datatypeSheet.getRow(tempRowIndex - 1);
													if (tempRow != null) {
														nameCell = tempRow.getCell(hashAnimalColumnConfig.get("name"));// tên
																														// động
																														// vật
														descriptionCell = tempRow
																.getCell(hashAnimalColumnConfig.get("description"));// mô
																													// tả
														parentCell = tempRow
																.getCell(hashAnimalColumnConfig.get("type"));// cha
														orignalCell = tempRow
																.getCell(hashAnimalColumnConfig.get("orginal"));// nguồn
																												// gốc
														typeCell = tempRow
																.getCell(hashAnimalColumnConfig.get("animalType"));// loại
																													// vật
																													// nuôi
														if (nameCell != null) {
															name = nameCell.getStringCellValue();
															animalDto.setName(name);

														}
														if (descriptionCell != null) {
															description = descriptionCell.getStringCellValue();
															animalDto.setDescription(description);
														}
														if (parentCell != null) {
															AnimalDto an = new AnimalDto();
															parent = parentCell.getStringCellValue();
															an.setName(parent);
															animalDto.setParent(an);
														}
														if (orignalCell != null) {
															OriginalDto originalDto = new OriginalDto();
															orignalName = orignalCell.getStringCellValue();
															originalDto.setName(orignalName);
															animalDto.setOriginalDto(originalDto);
														}
														if (typeCell != null) {
															AnimalTypeDto typeDto = new AnimalTypeDto();
															typeName = typeCell.getStringCellValue();
															typeDto.setName(typeName);
															animalDto.setTypeDto(typeDto);
														}
													}
												}
												isNotChange = false;
											}
											if (name.length() <= 0) {
												break;
											}

										} else {
											number++;
											tempRowIndex++;
										}
									} else {
										number++;
										tempRowIndex++;
									}
								}
								System.out.println(code + ": " + number);
							}
						}
					}
					Set<ProductTargetDto> dtos = new HashSet<ProductTargetDto>();
					if (number > 0) {
						for (int i = 0; i < number; i++) {
							ProductTargetDto timeTableDto = new ProductTargetDto();
							index = hashAnimalColumnConfig.get("product");
							if (index != null) {
								currentCell = currentRow.getCell(index);// staffCode
								if (currentCell != null && currentCell.getStringCellValue() != null) {
									String name = currentCell.getStringCellValue();

									timeTableDto.setName(name);
								}
							}

							dtos.add(timeTableDto);
							if (i == (number - 1)) {
								animalDto.setAnimalProductTargets(dtos);
								ret.add(animalDto);
								animalDto = new AnimalDto();
							}
							rowIndex++;
							currentRow = datatypeSheet.getRow(rowIndex);
						}
					} else {
						rowIndex++;
					}
				} else {
					rowIndex++;
				}
//				rowIndex++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//tran huu dat them hàm importanimalnew start
	public static List<AnimalDto> importAnimalNew(InputStream is) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		DataFormatter dataFormatter = new DataFormatter();

		try {

			List<AnimalDto> ret = new ArrayList<AnimalDto>();

			@SuppressWarnings("resource")
			Workbook workbook = new HSSFWorkbook(is);
			//Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			Row row = null;
			Cell cell = null;

			int rowIndex = 1;

			
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				AnimalDto rawData = new AnimalDto();
				row = datatypeSheet.getRow(rowIndex);
				if (row == null) {
					break;
				}
				
				cell = row.getCell(1);//code
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setCode(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setCode(cell.getStringCellValue());
					}
				}
				
				cell = row.getCell(2);//ten viet nam
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setName(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setName(cell.getStringCellValue());
					}
				}
				cell = row.getCell(3);//ten khacs
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setOtherName(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setOtherName(cell.getStringCellValue());
					}
				}
				cell = row.getCell(4);//ten khoa hocj
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setScienceName(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setScienceName(cell.getStringCellValue());
					}
				}
				cell = row.getCell(5);//teen tiengs anh
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setEnName(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setEnName(cell.getStringCellValue());
					}
				}
				cell = row.getCell(6);//lop tieng viet
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setAnimalClass(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setAnimalClass(cell.getStringCellValue());
					}
				}
				
				cell = row.getCell(7);//order vn
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setOrdo(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setOrdo(cell.getStringCellValue());
					}
				}
				cell = row.getCell(8);//ho tieng viett
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setFamily(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setFamily(cell.getStringCellValue());
					}
				}
				
				cell = row.getCell(9);//cite04
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setCites(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setCites(cell.getStringCellValue());
					}
				}
				
				cell = row.getCell(10);//nghị định 64
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setVnlist(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setVnlist(cell.getStringCellValue());
					}
				}
				cell = row.getCell(11);//nghị định 06
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setVnlist06(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setVnlist06(cell.getStringCellValue());
					}
				}
				
				cell = row.getCell(12);//mức độ bảo vệ
				if (cell != null) {
					try {
						rawData.setProtectionLevel(new Double(cell.getNumericCellValue()).intValue());
					} catch (Exception e) {
						try {
							Integer value = Integer.valueOf(cell.getStringCellValue());
							rawData.setProtectionLevel(value);
						} catch (Exception e2) {
							System.out.println("lỗi chuyển string sang integer của mức độ bảo vệ tại  dòng "+rowIndex);
						}
					}
//					try {
//						if (cell.getCellTypeEnum() == CellType.NUMERIC) {
//							rawData.setProtectionLevel(Integer.parseInt(String.valueOf(cell.getNumericCellValue())));
//						} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
//							rawData.setProtectionLevel(Integer.parseInt(String.valueOf(cell.getNumericCellValue())));
//						}
//					}catch (Exception e) {
//						System.out.println("lỗi chuyển string sang integer của mức độ bảo vệ tại dòng"+rowIndex);
//					}
				}
				
				cell = row.getCell(13);//bảo vệ
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setAnimalGroup(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setAnimalGroup(cell.getStringCellValue());
					}
				}
				
				cell = row.getCell(14);//tên la tinh khác
				if (cell != null) {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setSynonym(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setSynonym(cell.getStringCellValue());
					}
				}
				
				
				
//				cell = row.getCell(7);//lop khoa hocj
//				if (cell != null) {
//					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
//						rawData.setAnimalClassSci(String.valueOf(cell.getNumericCellValue()));
//					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
//						rawData.setAnimalClassSci(cell.getStringCellValue());
//					}
//				}
				
				
//				cell = row.getCell(9);//order
//				if (cell != null) {
//					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
//						rawData.setOrdoSci(String.valueOf(cell.getNumericCellValue()));
//					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
//						rawData.setOrdoSci(cell.getStringCellValue());
//					}
//				}
				
				
				
//				cell = row.getCell(11);//ho tineg anh
//				if (cell != null) {
//					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
//						rawData.setFamilySci(String.valueOf(cell.getNumericCellValue()));
//					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
//						rawData.setFamilySci(cell.getStringCellValue());
//					}
//				}
				
				
				cell = row.getCell(15);//hình thức sinh sản
				if (cell != null) {
					
					try {
						rawData.setReproductionForm(new Double(cell.getNumericCellValue()).intValue());
					} catch (Exception e) {
						try {
							Integer value = Integer.valueOf(cell.getStringCellValue());
							rawData.setReproductionForm(value);
						} catch (Exception e2) {
							System.out.println("lỗi chuyển string sang integer của hình thức sinh sản tại dòng"+rowIndex);
						}
					}
					
//					try {
//						if (cell.getCellTypeEnum() == CellType.NUMERIC) {
//							rawData.setReproductionForm(Integer.parseInt(String.valueOf(cell.getNumericCellValue())));
//						} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
//							rawData.setReproductionForm(Integer.parseInt(String.valueOf(cell.getNumericCellValue())));
//						}
//					}catch (Exception e) {
//						System.out.println("lỗi chuyển string sang integer của hình thức sinh sản tại dòng"+rowIndex);
//					}
					
				}
				ret.add(rawData);
				rowIndex++;
				
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	//tran huu dat them hàm importanimalnew end

	public static boolean containsAnimal(String code, List<AnimalDto> ret) {
		for (AnimalDto o : ret) {
			if (o != null && o.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public static boolean containsUnit(String code, List<FmsAdministrativeUnitDto> ret) {
		for (FmsAdministrativeUnitDto o : ret) {
			if (o != null && o.getCode().equals(code)) {
				return true;
			}
		}
		return false;
	}

	public static List<FarmDto> importFarmFromInputStream(InputStream is) {
		List<FarmDto> ret = new ArrayList<FarmDto>();
		hashFarmColumnConfig.put("farmCode", 0);// mã định danh cơ sở
		hashFarmColumnConfig.put("name", 1);// tên cơ sở chăn nuôi
		hashFarmColumnConfig.put("farmerName", 2);// Người đại diện
		hashFarmColumnConfig.put("village", 3); // thôn,xóm,ấp
		hashFarmColumnConfig.put("district", 4);// thành phố
		hashFarmColumnConfig.put("ward", 5); // phường xã
		hashFarmColumnConfig.put("city", 6); // tỉnh
		hashFarmColumnConfig.put("animalQuantity", 7); // số lượng vật nuôi tối đa
		hashFarmColumnConfig.put("animal", 8); // loại gia cầm nuôi
		hashFarmColumnConfig.put("product", 9); // hướng sản phẩm
		hashFarmColumnConfig.put("phoneNumber", 15); // số điện thoại cơ sở
		hashFarmColumnConfig.put("description", 19); // mô tả
		hashFarmColumnConfig.put("landArea", 28); // diện tích mặt bằng
		hashFarmColumnConfig.put("cmndOwner", 37); // cmnd người đại diện
		hashFarmColumnConfig.put("numberRegistration", 38); // số đăng ký kinh doanh
		hashFarmColumnConfig.put("phoneNumberOwner", 39); // số điện thoại người đại diện
		hashFarmColumnConfig.put("vetStaffName", 46); // Tên thú ý
		hashFarmColumnConfig.put("vetStaffAddress", 47); // địa chỉ thú y
		hashFarmColumnConfig.put("vetStaffPhone", 48); // số điện thoại thú y

		try {
			@SuppressWarnings("resource")

			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			// Iterator<Row> iterator = datatypeSheet.iterator();
			int rowIndex = 2;
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					FarmDto farm = new FarmDto();
					farm.setImport(true);
					Integer index = hashFarmColumnConfig.get("farmCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);// mã định danh
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double codeD = currentCell.getNumericCellValue();
							String code = numberFormatter.format(codeD);
							farm.setCode(code);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String code = currentCell.getStringCellValue();
							farm.setCode(code);
						}
					}
					index = hashFarmColumnConfig.get("name");
					if (index != null) {
						currentCell = currentRow.getCell(index);// tên
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String name = currentCell.getStringCellValue();
							farm.setName(name);

						}
					}
					index = hashFarmColumnConfig.get("farmerName");
					if (index != null) {
						currentCell = currentRow.getCell(index);// Người đại diện
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String ownerName = currentCell.getStringCellValue();
							farm.setOwnerName(ownerName);
						}
					}

					index = hashFarmColumnConfig.get("village");
					if (index != null) {
						currentCell = currentRow.getCell(index);// địa chỉ chi tiết
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double addressD = currentCell.getNumericCellValue();
							String address = numberFormatter.format(addressD);
							farm.setAdressDetail(address);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String village = currentCell.getStringCellValue();
							farm.setAdressDetail(village);
						}
					}

					index = hashFarmColumnConfig.get("ward");
					if (index != null) {
						currentCell = currentRow.getCell(index);// phường
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String name = currentCell.getStringCellValue();
							if (name != null) {
								String[] output = name.split("-");
								if (output != null && output.length > 0) {
									FmsAdministrativeUnitDto auDto = new FmsAdministrativeUnitDto();
									auDto.setCode(output[0]);// xử lý lấy mã và tên
									auDto.setName(output[1]);// xử lý lấy mã và tên
									farm.setAdministrativeUnit(auDto);
								}

							}

						}
					}

					index = hashFarmColumnConfig.get("animalQuantity");
					if (index != null) {
						currentCell = currentRow.getCell(index);// số lượng vật nuôi tối đa
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							Double maxNumberOfAnimal = currentCell.getNumericCellValue();
							if (maxNumberOfAnimal > 0)
								farm.setMaxNumberOfAnimal(maxNumberOfAnimal.intValue());
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String maxNumberOfAnimal = currentCell.getStringCellValue();
							farm.setMaxNumberOfAnimal(Integer.valueOf(maxNumberOfAnimal));

						}
					}

					index = hashFarmColumnConfig.get("animal");
					if (index != null) {
						currentCell = currentRow.getCell(index);// loại gia cầm
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String animal = currentCell.getStringCellValue();
							if (animal != null) {
								Set<AnimalDto> dtos = new HashSet<AnimalDto>();
								if (animal != null && animal.contains(":")) {
									String[] output = animal.split(":");
									if (output != null && output.length > 0) {
										for (int i = 0; i < output.length; i++) {
											AnimalDto dto = new AnimalDto();
											dto.setCode(output[i]);
											dtos.add(dto);
										}
									}
								} else {
									AnimalDto dto = new AnimalDto();
									dto.setCode(animal);
									dtos.add(dto);
								}
								farm.setFarmAnimals(dtos);
							}

						}
					}
					index = hashFarmColumnConfig.get("product");
					if (index != null) {
						currentCell = currentRow.getCell(index);// hướng sản phẩm
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							List<ProductTargetDto> products = new ArrayList<ProductTargetDto>();
							String product = currentCell.getStringCellValue();
							if (product != null && product.contains(":")) {
								String[] output = product.split(":");
								if (output != null && output.length > 0) {
									for (int i = 0; i < output.length; i++) {
										ProductTargetDto dto = new ProductTargetDto();
										dto.setCode(output[i]);
										products.add(dto);
									}
								}
							} else {
								ProductTargetDto dto = new ProductTargetDto();
								dto.setCode(product);
								products.add(dto);
							}
							// xử lý cắt chuỗi lấy hướng sản phẩm
							farm.setFarmProductTargets(products);
						}
					}

					index = hashFarmColumnConfig.get("phoneNumber");
					if (index != null) {
						currentCell = currentRow.getCell(index);// điện thoại cơ sở chăn nuôi
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double phoneNumber = currentCell.getNumericCellValue();
							String phone = numberFormatter.format(phoneNumber);
							farm.setPhoneNumber("0" + phone);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String phoneNumber = currentCell.getStringCellValue();
							farm.setPhoneNumber("0" + phoneNumber);// phải xử lý thêm khi đổi đầu số
						}
					}

					index = hashFarmColumnConfig.get("description");
					if (index != null) {
						currentCell = currentRow.getCell(index);// mô tả
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double area = currentCell.getNumericCellValue();
							String des = numberFormatter.format(area);
							farm.setDescription(des);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String des = currentCell.getStringCellValue();
							farm.setDescription(des);

						}
					}

					index = hashFarmColumnConfig.get("landArea");
					if (index != null) {
						currentCell = currentRow.getCell(index);// diện tích mặt bằng
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double area = currentCell.getNumericCellValue();
							farm.setTotalAcreage(area);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String area = currentCell.getStringCellValue();
							farm.setTotalAcreage(Double.valueOf(area));
						}
					}

					index = hashFarmColumnConfig.get("cmndOwner");
					if (index != null) {
						currentCell = currentRow.getCell(index);// CMND người đại diện
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double cmndD = currentCell.getNumericCellValue();
							String cmnd = numberFormatter.format(cmndD);
							farm.setOwnerCitizenCode(cmnd);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String cmnd = currentCell.getStringCellValue();
							farm.setOwnerCitizenCode(cmnd);
						}
					}
					index = hashFarmColumnConfig.get("numberRegistration");
					if (index != null) {
						currentCell = currentRow.getCell(index);// Số đăng ký kinh doanh
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double numberReg = currentCell.getNumericCellValue();
							String number = numberFormatter.format(numberReg);
							farm.setBusinessRegistrationNumber(number);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String numberReg = currentCell.getStringCellValue();
							farm.setBusinessRegistrationNumber(numberReg);
						}
					}

					index = hashFarmColumnConfig.get("phoneNumberOwner");
					if (index != null) {
						currentCell = currentRow.getCell(index);// Số điện thoại người đại diện
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double numberPhoneOwner = currentCell.getNumericCellValue();
							String phone = numberFormatter.format(numberPhoneOwner);
							farm.setOwnerPhoneNumber("0" + phone);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String numberPhoneOwner = currentCell.getStringCellValue();
							farm.setOwnerPhoneNumber("0" + numberPhoneOwner);
						}
					}

					index = hashFarmColumnConfig.get("vetStaffName");
					if (index != null) {
						currentCell = currentRow.getCell(index);// tên thú y
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double staff = currentCell.getNumericCellValue();
							String name = numberFormatter.format(staff);
							farm.setVetStaffName(name);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String nameStaff = currentCell.getStringCellValue();
							farm.setVetStaffName(nameStaff);
						}
					}

					index = hashFarmColumnConfig.get("vetStaffAddress");
					if (index != null) {
						currentCell = currentRow.getCell(index);// địa chỉ thú y
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double staff = currentCell.getNumericCellValue();
							String name = numberFormatter.format(staff);
							farm.setVetStaffAdress(name);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String addressStaff = currentCell.getStringCellValue();
							farm.setVetStaffAdress(addressStaff);
						}
					}
					index = hashFarmColumnConfig.get("vetStaffPhone");
					if (index != null) {
						currentCell = currentRow.getCell(index);// sđt thú y
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double phoneStaff = currentCell.getNumericCellValue();
							String phone = numberFormatter.format(phoneStaff);
							farm.setVetStaffPhoneNumber("0" + phone);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String phoneStaff = currentCell.getStringCellValue();
							farm.setVetStaffPhoneNumber("0" + phoneStaff);
						}
					}
					farm.setStatus(1);
					ret.add(farm);

				}
				rowIndex++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<FarmDto> importWLFarmFromInputStream(InputStream is) throws ParseException {
		List<FarmDto> ret = new ArrayList<FarmDto>();
		hashFarmColumnConfig = new Hashtable<String, Integer>();
		hashFarmColumnConfig.put("farmCode", 0);// mã định danh cơ sở

		try {
			@SuppressWarnings("resource")

			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			// Iterator<Row> iterator = datatypeSheet.iterator();
			int rowIndex = 1;
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					FarmDto farm = new FarmDto();
					farm.setImport(true);
					Integer index = 5;
					if (index != null) {
						currentCell = currentRow.getCell(index);// mã cũ
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double codeD = currentCell.getNumericCellValue();
							String code = String.valueOf(codeD);
							farm.setOldCode(code);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String code = currentCell.getStringCellValue();
							farm.setOldCode(code);
						}
					}
					index = 6;
					if (index != null) {
						currentCell = currentRow.getCell(index);// mã đăng ký kinh doanh
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double codeD = currentCell.getNumericCellValue();
							String code = String.valueOf(codeD);
							farm.setBusinessRegistrationNumber(code);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String code = currentCell.getStringCellValue();
							farm.setBusinessRegistrationNumber(code);
						}
					}
					index = 7;
					if (index != null) {
						currentCell = currentRow.getCell(index);// gcs Zone
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setGcsZone(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setGcsZone(val);
						}
					}
					index = 8;
					if (index != null) {
						currentCell = currentRow.getCell(index);// gcs Zone
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							farm.setGcsLong(valDouble);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							Double valDouble = Double.valueOf(val);
							farm.setGcsLong(valDouble);
						}
					}
					index = 9;
					if (index != null) {
						currentCell = currentRow.getCell(index);// gcs Zone
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							farm.setGcsLat(valDouble);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							Double valDouble = Double.valueOf(val);
							farm.setGcsLat(valDouble);
						}
					}
					index = 10;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Gcs Accuracy
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							farm.setGcsAccuracy(valDouble);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							Double valDouble = Double.valueOf(val);
							farm.setGcsAccuracy(valDouble);
						}
					}
					index = 11;
					if (index != null) {
						currentCell = currentRow.getCell(index);// GCS Elevation
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							farm.setGcsElevation(valDouble);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							Double valDouble = Double.valueOf(val);
							farm.setGcsElevation(valDouble);
						}
					}
					index = 12;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interview date
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							Date valDate = currentCell.getDateCellValue();
							farm.setInterviewDate(valDate);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							Date valDate = new SimpleDateFormat("dd/MM/yyyy").parse(val);
							farm.setInterviewDate(valDate);
						}
					}
					index = 13;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interview Start Time
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valInStartTime = currentCell.getNumericCellValue();
							String val = String.valueOf(valInStartTime);
							farm.setInterviewStartTime(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String valInStartTime = currentCell.getStringCellValue();
							farm.setInterviewStartTime(valInStartTime);
						}
					}
					index = 14;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interview Finish Time
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valInFinTime = currentCell.getNumericCellValue();
							String val = String.valueOf(valInFinTime);
							farm.setInterviewFinTime(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setInterviewFinTime(val);
						}
					}
					index = 15;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interviewer
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setInterviewer(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setInterviewer(val);
						}
					}
					index = 16;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interview name
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setInName(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setInName(val);
						}
					}
					index = 17;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interview age
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							Integer val = (int) Math.round(valDouble);
							farm.setInAge(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							Integer inAge = Integer.parseInt(val);
							farm.setInAge(inAge);
						}
					}
					index = 18;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interview gender
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setInGen(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null) {
							String val = currentCell.getStringCellValue();
							farm.setInGen(val);
						}
					}
					index = 19;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interview telephone
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setInTel(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setInTel(val);
						}
					}
					index = 21;
					if (index != null) {
						currentCell = currentRow.getCell(index);// gcs Zone
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setOwnerAge((int) valDouble);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setOwnerAge(Integer.valueOf(val));
						}
					}
					index = 20;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Name
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setName(val);
							farm.setOwnerName(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setName(val);
							farm.setOwnerName(val);
						}
					}
					index = 22;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Name
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setOwnerGender(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setOwnerGender(val);
						}
					}
					index = 23;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Name
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setOwnerPhoneNumber(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setOwnerPhoneNumber(val);
						}
					}
					index = 24;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Ca_name
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setCaName(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setCaName(val);
						}
					}
					index = 25;
					if (index != null) {
						currentCell = currentRow.getCell(index);// ca_age
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							Integer val = (int) Math.round(valDouble);
							farm.setCaAge(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							Integer inAge = Integer.parseInt(val);
							farm.setCaAge(inAge);
						}
					}
					index = 26;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Ca_gen
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setCaGen(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null) {
							String val = currentCell.getStringCellValue();
							farm.setCaGen(val);
						}
					}
					index = 27;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interview date
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							Date valDate = currentCell.getDateCellValue();
							farm.setBusinessRegistrationDate(valDate);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							Date valDate = new SimpleDateFormat("dd/MM/yyyy").parse(val);
							farm.setBusinessRegistrationDate(valDate);
						}
					}
					index = 29;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Name
						String administrativeCode = null;
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							administrativeCode = val;
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							administrativeCode = val;
						} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.FORMULA
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							administrativeCode = val;
						}
						if (administrativeCode != null) {
							farm.setAdministrativeUnit(new FmsAdministrativeUnitDto());
							farm.getAdministrativeUnit().setCode(administrativeCode);
						}
					}
					index = 35;
					if (index != null) {
						currentCell = currentRow.getCell(index);// type
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setType(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null) {
							String val = currentCell.getStringCellValue();
							farm.setType(val);
						}
					}
					index = 36;
					if (index != null) {
						currentCell = currentRow.getCell(index.intValue());// Interview date
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valYear = currentCell.getNumericCellValue();
							String val = String.valueOf(valYear);
							farm.setYearRegistration(val);
						} else if (currentCell != null && currentCell.getNumericCellValue() > 0) {
							String val = NumberFormat.getNumberInstance().format(currentCell.getNumericCellValue());
							farm.setYearRegistration(val);
						}
					}

					farm.setStatus(1);
					ret.add(farm);
				}
				rowIndex++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<FarmDto> importWL2017FarmFromInputStream(InputStream is) throws ParseException {
		List<FarmDto> ret = new ArrayList<FarmDto>();
		hashFarmColumnConfig = new Hashtable<String, Integer>();
		hashFarmColumnConfig.put("farmCode", 0);// mã định danh cơ sở

		try {
			@SuppressWarnings("resource")

			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			// Iterator<Row> iterator = datatypeSheet.iterator();
			int rowIndex = 1;
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					FarmDto farm = new FarmDto();
					farm.setImport(true);

					Integer index = 0;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Name
						String administrativeCode = null;
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf((int) valDouble);
							administrativeCode = val;
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							administrativeCode = val;
						} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.FORMULA
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							administrativeCode = val;
						}
						if (administrativeCode != null) {
							farm.setAdministrativeUnit(new FmsAdministrativeUnitDto());
							farm.getAdministrativeUnit().setCode(administrativeCode);
						}
					}

					index = 3;
					if (index != null) {
						currentCell = currentRow.getCell(index);// mã cũ
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double codeD = currentCell.getNumericCellValue();
							String village = String.valueOf(codeD);
							farm.setVillage(village);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String village = currentCell.getStringCellValue();
							farm.setVillage(village);
						}
					}
					index = 4;
					if (index != null) {
						currentCell = currentRow.getCell(index);
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setMapCode(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setMapCode(val);
						}
					}
					index = 8;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Interview date
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							Date valDate = currentCell.getDateCellValue();
							farm.setBusinessRegistrationDate(valDate);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							Date valDate = new SimpleDateFormat("dd/MM/yyyy").parse(val);
							farm.setBusinessRegistrationDate(valDate);
						}
					}
					index = 10;
					if (index != null) {
						currentCell = currentRow.getCell(index);// gcs Zone
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							farm.setLongitude(String.valueOf(valDouble));
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setLongitude(val);
						}
					}
					index = 11;
					if (index != null) {
						currentCell = currentRow.getCell(index);// gcs Zone
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							farm.setLatitude(String.valueOf(valDouble));
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setLatitude(val);
						}
					}

					index = 15;
					if (index != null) {
						currentCell = currentRow.getCell(index);// Name
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setName(val);
							farm.setOwnerName(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setName(val);
							farm.setOwnerName(val);
						}
					}
					index = 16;
					if (index != null) {
						currentCell = currentRow.getCell(index);// mã cũ
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double valDouble = currentCell.getNumericCellValue();
							String val = String.valueOf(valDouble);
							farm.setAdressDetail(val);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String val = currentCell.getStringCellValue();
							farm.setAdressDetail(val);
						}
					}

					farm.setStatus(1);
					ret.add(farm);
				}
				rowIndex++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<ImportExportAnimalDto> importImportAnimalFromInputStream(InputStream is) {
		List<ImportExportAnimalDto> ret = new ArrayList<ImportExportAnimalDto>();
		hashFarmColumnConfig.put("batchCode", 0);// số lô.
		hashFarmColumnConfig.put("animal", 1);// loại vật nuôi
		hashFarmColumnConfig.put("animalName", 2);// Tên vật nuôi
		hashFarmColumnConfig.put("product", 3); // hướng sản phẩm
		hashFarmColumnConfig.put("quantityAnimal", 4);// số lượng(con)
		hashFarmColumnConfig.put("status", 5); // Trạng thái(hoàn thành, tạo mới)
		hashFarmColumnConfig.put("remainQuantity", 6); // Số lượng còn lại
		hashFarmColumnConfig.put("dateImport", 7); // ngày nhập
		hashFarmColumnConfig.put("totalCount", 8); // tổng số đã xuất
		hashFarmColumnConfig.put("district", 9); // quận huyện
		hashFarmColumnConfig.put("ward", 10); // xã phường
		hashFarmColumnConfig.put("nameFarm", 28); // cơ sở chăn nuôi
		hashFarmColumnConfig.put("injectionDate", 31); // ngày tiêm phòng
		hashFarmColumnConfig.put("description", 32); // diễn giải
		hashFarmColumnConfig.put("place", 33); // địa điểm
		hashFarmColumnConfig.put("farmCode", 36); // mã định danh

		try {
			@SuppressWarnings("resource")

			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			// Iterator<Row> iterator = datatypeSheet.iterator();
			int rowIndex = 2;
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					ImportExportAnimalDto farm = new ImportExportAnimalDto();
					Integer index = hashFarmColumnConfig.get("batchCode");
					System.out.println("batchCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);// mã lô
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double codeD = currentCell.getNumericCellValue();
							String batchCode = numberFormatter.format(codeD);
							farm.setBatchCode(batchCode);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String batchCode = currentCell.getStringCellValue();
							farm.setBatchCode(batchCode);
						}
					}
					index = hashFarmColumnConfig.get("animal");
					System.out.println("animal");
					if (index != null) {
						currentCell = currentRow.getCell(index);// tên loại gia cầm
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String name = currentCell.getStringCellValue();
							AnimalDto parent = new AnimalDto();
							parent.setName(name);
							farm.setParent(parent);

						}
					}
					index = hashFarmColumnConfig.get("animalName");
					System.out.println("animalName");
					if (index != null) {
						currentCell = currentRow.getCell(index);// tên vật nuôi
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String name = currentCell.getStringCellValue();
							AnimalDto animal = new AnimalDto();
							animal.setName(name);
							farm.setAnimal(animal);
						}
					}

					index = hashFarmColumnConfig.get("product");
					System.out.println("product");
					if (index != null) {
						currentCell = currentRow.getCell(index);// hướng sản phẩm
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							ProductTargetDto dto = new ProductTargetDto();
							String product = currentCell.getStringCellValue();
							dto.setName(product);
							farm.setProductTarget(dto);
						}
					}

					index = hashFarmColumnConfig.get("quantityAnimal");
					System.out.println("quantityAnimal");
					if (index != null) {
						currentCell = currentRow.getCell(index);// số lượng con
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() >= 0) {
							double quantityAnimal = currentCell.getNumericCellValue();
							farm.setQuantity(quantityAnimal);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String quantityAnimal = currentCell.getStringCellValue();
							farm.setQuantity(Double.valueOf(quantityAnimal));

						}
					}
					index = hashFarmColumnConfig.get("remainQuantity");
					System.out.println("remainQuantity");
					if (index != null) {
						currentCell = currentRow.getCell(index);// số lượng còn lại
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() >= 0) {
							double remain = currentCell.getNumericCellValue();
							farm.setRemainQuantity(remain);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String remain = currentCell.getStringCellValue();
							farm.setRemainQuantity(Double.valueOf(remain));

						}
					}

					index = hashFarmColumnConfig.get("dateImport");
					System.out.println("dateImport");
					if (index != null) {
						currentCell = currentRow.getCell(index);// ngày nhập đàn
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							Date dateImport = currentCell.getDateCellValue();
							farm.setDateImport(dateImport);
							System.out.println(dateImport);
						} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
							if (currentCell != null && currentCell.getStringCellValue() != null) {
								String strBirthdate = currentCell.getStringCellValue();
								if (strBirthdate != null && strBirthdate.length() > 1) {
									try {
										Date dateImport = new SimpleDateFormat("dd-MM-yyyy").parse(strBirthdate);
										// Date dateImport = dateFormat.parse(strBirthdate);
										farm.setDateImport(dateImport);
									} catch (Exception ex) {
										System.out.println(ex.getMessage());
									}
								}
							}
						}
					}

					index = hashFarmColumnConfig.get("description");
					System.out.println("description");
					if (index != null) {
						currentCell = currentRow.getCell(index);// diễn giải
						try {
							if (currentCell != null && currentCell.getStringCellValue() != null
									&& currentCell.getStringCellValue().length() > 1) {
								String des = currentCell.getStringCellValue();
								farm.setDescription(des);
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
						}
					}

					FarmDto farmDto = new FarmDto();
					index = hashFarmColumnConfig.get("farmCode");
					System.out.println("farmCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);// mã định danh
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double area = currentCell.getNumericCellValue();
							String code = numberFormatter.format(area);
							farmDto.setCode(code);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String code = currentCell.getStringCellValue();
							farmDto.setCode(code);
						}
					}

					index = hashFarmColumnConfig.get("nameFarm");
					System.out.println("nameFarm");
					if (index != null) {
						currentCell = currentRow.getCell(index);// tên cơ sở chăn nuôi
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double area = currentCell.getNumericCellValue();
							String name = numberFormatter.format(area);
							farmDto.setName(name);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String name = currentCell.getStringCellValue();
							farmDto.setName(name);
						}
					}
					index = hashFarmColumnConfig.get("injectionDate");
					System.out.println("injectionDate");
					if (index != null) {
						Set<InjectionPlantDto> injectionPlants = new HashSet<InjectionPlantDto>();
						currentCell = currentRow.getCell(index);// ngày tiêm phòng
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {

							InjectionPlantDto dto = new InjectionPlantDto();
							Date dateImport = currentCell.getDateCellValue();
							if (dateImport != null) {
								dto.setInjectionDate(dateImport);
								injectionPlants.add(dto);
							}

							System.out.println(dateImport);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String date = currentCell.getStringCellValue();
							if (date != null) {

								if (date != null && date.contains(",")) {
									String[] output = date.split(",");
									if (output != null && output.length > 0) {
										for (int i = 0; i < output.length; i++) {
											InjectionPlantDto dto = new InjectionPlantDto();
											if (output[i] != null) {
												try {
													Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(output[i]);
													// Date dateImport = dateFormat.parse(output[i]);
													dto.setInjectionDate(date1);
													injectionPlants.add(dto);
												} catch (Exception ex) {
													System.out.println(ex.getMessage());
												}
											}

										}
									}
								} else {
									InjectionPlantDto dto = new InjectionPlantDto();
									if (date != null) {
										try {
											Date dateImport = dateFormat.parse(date);
											dto.setInjectionDate(dateImport);
											injectionPlants.add(dto);
										} catch (Exception ex) {
											System.out.println(ex.getMessage());
										}
									}

								}

							}

						}
						farm.setInjectionPlants(injectionPlants);
					}

					if (farmDto != null && farmDto.getCode() != null) {
						farm.setFarm(farmDto);
					}
					farm.setType(1);// nhập đàn
					ret.add(farm);

				}
				rowIndex++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<ImportExportAnimalDto> importExportAnimalFromInputStream(InputStream is) {
		List<ImportExportAnimalDto> ret = new ArrayList<ImportExportAnimalDto>();
		hashFarmColumnConfig.put("nameFarm", 0); // cơ sở chăn nuôi
		hashFarmColumnConfig.put("batchCode", 1);// số lô
		hashFarmColumnConfig.put("number", 2);// số chứng từ
		hashFarmColumnConfig.put("dateExport", 3); // ngày xuất
		hashFarmColumnConfig.put("description", 4); // diễn giải
		hashFarmColumnConfig.put("quantityAnimal", 5);// số lượng(con)
		hashFarmColumnConfig.put("animal", 6);// loại vật nuôi
		hashFarmColumnConfig.put("animalName", 8);// Tên vật nuôi
		hashFarmColumnConfig.put("product", 9); // hướng sản phẩm

		hashFarmColumnConfig.put("exportType", 11); // loại phiếu
		hashFarmColumnConfig.put("reason", 35); // lý do
		hashFarmColumnConfig.put("farmCode", 36); // mã định danh
		hashFarmColumnConfig.put("totalAmount", 42); // tổng trọng
		hashFarmColumnConfig.put("district", 34); // quận huyện
		hashFarmColumnConfig.put("ward", 43); // xã phường

		try {
			@SuppressWarnings("resource")

			Workbook workbook = new XSSFWorkbook(is);
			Sheet datatypeSheet = workbook.getSheetAt(0);
			// Iterator<Row> iterator = datatypeSheet.iterator();
			int rowIndex = 2;
			int num = datatypeSheet.getLastRowNum();
			while (rowIndex <= num) {
				Row currentRow = datatypeSheet.getRow(rowIndex);
				Cell currentCell = null;
				if (currentRow != null) {
					ImportExportAnimalDto farm = new ImportExportAnimalDto();
					FarmDto farmDto = new FarmDto();

					Integer index = hashFarmColumnConfig.get("nameFarm");
					if (index != null) {
						currentCell = currentRow.getCell(index);// tên cơ sở chăn nuôi
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double area = currentCell.getNumericCellValue();
							String name = numberFormatter.format(area);
							farmDto.setName(name);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String name = currentCell.getStringCellValue();
							farmDto.setName(name);
						}
					}
					index = hashFarmColumnConfig.get("batchCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);// mã lô
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double codeD = currentCell.getNumericCellValue();
							String batchCode = numberFormatter.format(codeD);
							farm.setBatchCode(batchCode);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String batchCode = currentCell.getStringCellValue();
							farm.setBatchCode(batchCode);
						}
					}
					index = hashFarmColumnConfig.get("number");
					if (index != null) {
						currentCell = currentRow.getCell(index);// số chứng từ
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() >= 0) {
							double codeD = currentCell.getNumericCellValue();
							String voucherCode = numberFormatter.format(codeD);
							farm.setVoucherCode(voucherCode);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String voucherCode = currentCell.getStringCellValue();
							farm.setVoucherCode(voucherCode);
						}
					}
					index = hashFarmColumnConfig.get("dateExport");
					if (index != null) {
						currentCell = currentRow.getCell(index);// ngày xuất
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC) {
							Date dateExport = currentCell.getDateCellValue();
							farm.setDateExport(dateExport);
							System.out.println(dateExport);
						} else if (currentCell != null && currentCell.getCellTypeEnum() == CellType.STRING) {
							if (currentCell != null && currentCell.getStringCellValue() != null) {
								String strBirthdate = currentCell.getStringCellValue();
								if (strBirthdate != null && strBirthdate.length() > 1) {
									try {
										Date dateExport = new SimpleDateFormat("dd-MM-yyyy").parse(strBirthdate);
										// Date dateImport = dateFormat.parse(strBirthdate);
										farm.setDateExport(dateExport);
									} catch (Exception ex) {
										System.out.println(ex.getMessage());
									}
								}
							}
						}
					}
					index = hashFarmColumnConfig.get("description");
					if (index != null) {
						currentCell = currentRow.getCell(index);// diễn giải
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double codeD = currentCell.getNumericCellValue();
							String des = numberFormatter.format(codeD);
							farm.setDescription(des);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String des = currentCell.getStringCellValue();
							farm.setDescription(des);

						}
					}
					index = hashFarmColumnConfig.get("quantityAnimal");
					if (index != null) {
						currentCell = currentRow.getCell(index);// số lượng con
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() >= 0) {
							double quantityAnimal = currentCell.getNumericCellValue();
							farm.setQuantity(quantityAnimal);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String quantityAnimal = currentCell.getStringCellValue();
							farm.setQuantity(Double.valueOf(quantityAnimal));

						}
					}

					index = hashFarmColumnConfig.get("animal");
					if (index != null) {
						currentCell = currentRow.getCell(index);// tên loại gia cầm
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double codeD = currentCell.getNumericCellValue();
							String name = numberFormatter.format(codeD);
							AnimalDto parent = new AnimalDto();
							parent.setName(name);
							farm.setParent(parent);

						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String name = currentCell.getStringCellValue();
							AnimalDto parent = new AnimalDto();
							parent.setName(name);
							farm.setParent(parent);

						}
					}
					index = hashFarmColumnConfig.get("animalName");
					if (index != null) {
						currentCell = currentRow.getCell(index);// tên vật nuôi
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() > 0) {
							double codeD = currentCell.getNumericCellValue();
							String name = numberFormatter.format(codeD);
							AnimalDto animal = new AnimalDto();
							animal.setName(name);
							farm.setAnimal(animal);

						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String name = currentCell.getStringCellValue();
							AnimalDto animal = new AnimalDto();
							animal.setName(name);
							farm.setAnimal(animal);
						}
					}

					index = hashFarmColumnConfig.get("product");
					if (index != null) {
						currentCell = currentRow.getCell(index);// hướng sản phẩm
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							ProductTargetDto dto = new ProductTargetDto();
							String product = currentCell.getStringCellValue();
							dto.setName(product);
							farm.setProductTarget(dto);
						}
					}

					index = hashFarmColumnConfig.get("exportType");
					if (index != null) {
						currentCell = currentRow.getCell(index);// loại phiếu
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String code = currentCell.getStringCellValue();
							if (code != null && code.equals("Xuất bán")) {
								farm.setExportType(1);
							} else if (code != null && code.equals("Xuất khác")) {
								farm.setExportType(2);
							} else if (code != null && code.equals("Điều chuyển loại")) {
								farm.setExportType(3);
							}

						}
					}

					index = hashFarmColumnConfig.get("reason");
					if (index != null) {
						currentCell = currentRow.getCell(index);// lý do
						if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String code = currentCell.getStringCellValue();
							farm.setExportReason(code);

						}
					}

					index = hashFarmColumnConfig.get("farmCode");
					if (index != null) {
						currentCell = currentRow.getCell(index);// mã định danh
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() >= 0) {
							double area = currentCell.getNumericCellValue();
							String code = numberFormatter.format(area);
							farmDto.setCode(code);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String code = currentCell.getStringCellValue();
							farmDto.setCode(code);
						}
					}

					index = hashFarmColumnConfig.get("totalAmount");
					if (index != null) {
						currentCell = currentRow.getCell(index);// tổng trọng lượng
						if (currentCell != null && currentCell.getCellTypeEnum() == CellType.NUMERIC
								&& currentCell.getNumericCellValue() >= 0) {
							double amount = currentCell.getNumericCellValue();
							farm.setAmount(amount);
						} else if (currentCell != null && currentCell.getStringCellValue() != null
								&& currentCell.getStringCellValue().length() > 1) {
							String code = currentCell.getStringCellValue();
							Double amount = Double.valueOf(code);
							farm.setAmount(amount);
						}
					}

					if (farmDto != null && farmDto.getCode() != null) {
						farm.setFarm(farmDto);
					}
					farm.setType(-1);// xuất đàn
					ret.add(farm);

				}
				rowIndex++;
			}
			return ret;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static HSSFRow createRow(HSSFSheet worksheet, int rowIndex, int numberColumn, CellStyle cellStyle) {
		HSSFRow row = worksheet.createRow(rowIndex);
		for (int i = 0; i < numberColumn; i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
		}
		return row;
	}

	public static String fromWeekToWeek(Date startDate, int fromWeek, int toWeek) {
		String duration = "";
		if (startDate != null) {
			DateFormat f = new SimpleDateFormat("dd/MM");
			Date dt = startDate;
			Calendar c = Calendar.getInstance();
			c.setTime(dt);
			c.add(Calendar.DATE, fromWeek * 7 - 1);
			dt = c.getTime();
			duration += f.format(dt).toString();

			Date td = startDate;
			c = Calendar.getInstance();
			c.setTime(td);
			c.add(Calendar.DATE, toWeek * 7 - 1);
			td = c.getTime();
			f = new SimpleDateFormat("dd/MM/yyyy");
			duration += "-" + f.format(td).toString();
		}
		return duration;
	}

	public static void exportFileUserSearch(HttpServletResponse response, List<UserExportDto> list) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Danh sách người dùng");
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());

		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setColor(HSSFFont.COLOR_NORMAL);
		font.setFontName("");
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setFont(font);
		// headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerCellStyle.setWrapText(false);

		CellStyle dateCellStyle = workbook.createCellStyle();
		short df = workbook.createDataFormat().getFormat("dd/MM/yyyy");
		dateCellStyle.setDataFormat(df);
		dateCellStyle.setBorderTop(BorderStyle.THIN);
		dateCellStyle.setBorderBottom(BorderStyle.THIN);
		dateCellStyle.setBorderRight(BorderStyle.THIN);
		dateCellStyle.setBorderLeft(BorderStyle.THIN);

		CellStyle normalCellStyle = workbook.createCellStyle();
		normalCellStyle.setBorderTop(BorderStyle.THIN);
		normalCellStyle.setBorderBottom(BorderStyle.THIN);
		normalCellStyle.setBorderRight(BorderStyle.THIN);
		normalCellStyle.setBorderLeft(BorderStyle.THIN);
		normalCellStyle.setWrapText(true);

		int rowIndex = 0;
		int columnIndex = 0;
		Row row = null;
		Cell cell = null;
		// Header
		// title
		String title = "Danh sách người dùng";
		row = sheet.createRow(rowIndex);
		cell = row.createCell(columnIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 8));
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue(title);

		row = sheet.createRow(++rowIndex);// header
		cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("STT");
		sheet.setColumnWidth(columnIndex++, 6 * 256);

		cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("Họ và tên");
		sheet.setColumnWidth(columnIndex++, 30 * 256);

		cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("Tên đăng nhập");
		sheet.setColumnWidth(columnIndex++, 25 * 256);

		cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("Giới tính");

		sheet.setColumnWidth(columnIndex++, 25 * 256);
		cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("Email");
		sheet.setColumnWidth(columnIndex++, 25 * 256);

		cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("Quyền người dùng");
		sheet.setColumnWidth(columnIndex++, 25 * 256);

		cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("Phường/Xã");
		sheet.setColumnWidth(columnIndex++, 25 * 256);

		cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("Quận/Huyện");
		sheet.setColumnWidth(columnIndex++, 25 * 256);

		cell = row.createCell(columnIndex);
		cell.setCellStyle(headerCellStyle);
		cell.setCellValue("Tỉnh/Thành");
		sheet.setColumnWidth(columnIndex++, 25 * 256);

		// End header
		rowIndex++;
		int count = 1;
		for (UserExportDto userDto : list) {
			columnIndex = 0;
			row = sheet.createRow(rowIndex);
			// STT
			cell = row.createCell(columnIndex);
			cell.setCellValue(count++);
			cell.setCellStyle(normalCellStyle);
			// ho ten
			cell = row.createCell(++columnIndex);
			cell.setCellValue(userDto.getDisplayName());
			cell.setCellStyle(normalCellStyle);
			// ten dang nhap
			cell = row.createCell(++columnIndex);
			cell.setCellValue(userDto.getUsername());
			cell.setCellStyle(normalCellStyle);
			// giới tính
			cell = row.createCell(++columnIndex);
			if (userDto.getGender() != null && "M".equalsIgnoreCase(userDto.getGender())) {
				cell.setCellValue("Nam");
			} else if (userDto.getGender() != null && "F".equalsIgnoreCase(userDto.getGender())) {
				cell.setCellValue("Nữ");
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(normalCellStyle);

			// email
			cell = row.createCell(++columnIndex);
			cell.setCellValue(userDto.getEmail());
			cell.setCellStyle(dateCellStyle);

			// quyền người dùng
			cell = row.createCell(++columnIndex);
			String roleNames = "";
			if (userDto.getRoles() != null) {
				for (RoleDto roleDto : userDto.getRoles()) {
					if (roleDto != null && roleDto.getName() != null) {
						roleNames += roleDto.getName() + ", ";
					}
				}
			}
			roleNames = roleNames.substring(0, roleNames.length() - 2);
			cell.setCellValue(roleNames);
			cell.setCellStyle(normalCellStyle);

			// phường xã
			cell = row.createCell(++columnIndex);
			if (userDto.getWard() != null)
				cell.setCellValue(userDto.getWard().getName());
			cell.setCellStyle(normalCellStyle);
			// quận huyện
			cell = row.createCell(++columnIndex);
			if (userDto.getDistrict() != null)
				cell.setCellValue(userDto.getDistrict().getName());
			cell.setCellStyle(normalCellStyle);
			// tỉnh thành
			cell = row.createCell(++columnIndex);
			if (userDto.getProvince() != null)
				cell.setCellValue(userDto.getProvince().getName());
			cell.setCellStyle(normalCellStyle);

			rowIndex++;
		}
		workbook.write(response.getOutputStream());
//		workbook.close();

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DanhSachNguoiDung.xls");
		response.flushBuffer();
	}

	public static void main(String[] agrs) {
		String du = fromWeekToWeek(new Date(), 2, 3);
		try {

//			ImportExportExcelUtil.importAdministrativeUnitFromInputStream(new FileInputStream(new File("D:\\fms\\fms\\fms-app\\document\\importAdministrativeUnit.xlsx")));
//			ImportExportExcelUtil.importAnimal(new FileInputStream(new File("D:\\fms\\fms\\fms-app\\document\\importAnimal.xlsx")));
//			ImportExportExcelUtil.importFarmFromInputStream(
//					new FileInputStream(new File("D:\\fms\\fms\\fms-app\\document\\FarmData.xlsx")));
			List<FarmDto> ret = ImportExportExcelUtil.importWLFarmFromInputStream(new FileInputStream(
					new File("C:\\Working\\wildlife\\wl\\Doc\\01-So lieu tong hop Dong Nai 2015.xlsx")));
			System.out.print(ret.size());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public static void exportDashboardView(InputStream is, HttpServletResponse response, DashboardSearchDto dto,
			FarmDto farm, List<ImportExportAnimalDto> listImportAnimal, List<ImportExportAnimalDto> listExportAnimal)
			throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(0);
		workbook.setSheetName(0, "Chi tiết trang trại");
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());

		HSSFFont font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setColor(HSSFFont.COLOR_NORMAL);
		font.setFontName("");
		headerCellStyle.setBorderTop(BorderStyle.THIN);
		headerCellStyle.setBorderBottom(BorderStyle.THIN);
		headerCellStyle.setBorderRight(BorderStyle.THIN);
		headerCellStyle.setBorderLeft(BorderStyle.THIN);
		headerCellStyle.setFont(font);
		// headerCellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		headerCellStyle.setWrapText(false);

		CellStyle dateCellStyle = workbook.createCellStyle();
		short df = workbook.createDataFormat().getFormat("dd/MM/yyyy");
		dateCellStyle.setDataFormat(df);
		dateCellStyle.setBorderTop(BorderStyle.THIN);
		dateCellStyle.setBorderBottom(BorderStyle.THIN);
		dateCellStyle.setBorderRight(BorderStyle.THIN);
		dateCellStyle.setBorderLeft(BorderStyle.THIN);

		CellStyle normalCellStyle = workbook.createCellStyle();
		normalCellStyle.setBorderTop(BorderStyle.THIN);
		normalCellStyle.setBorderBottom(BorderStyle.THIN);
		normalCellStyle.setBorderRight(BorderStyle.THIN);
		normalCellStyle.setBorderLeft(BorderStyle.THIN);
		normalCellStyle.setWrapText(true);

		int rowIndex = 0;
		int columnIndex = 2;
		Row row = null;
		Cell cell = null;
		// Header
		// Tên cơ sở chăn nuôi
		row = sheet.getRow(rowIndex);
		cell = row.getCell(columnIndex);
		if (farm.getName() != null)
			cell.setCellValue(farm.getName());
		// Mã định danh cơ sở
		row = sheet.getRow(rowIndex);
		cell = row.getCell(columnIndex + 8);
		if (farm.getCode() != null)
			cell.setCellValue(farm.getCode());

		// số điện thoại
		row = sheet.getRow(++rowIndex);
		cell = row.getCell(columnIndex);
		cell.setCellValue(farm.getPhoneNumber());

		// Loại vật nuôi
		row = sheet.getRow(rowIndex);
		cell = row.getCell(columnIndex + 8);
		cell.setCellValue(dto.getTypeAnimalStr());

		// Thành phố
		row = sheet.getRow(++rowIndex);
		cell = row.getCell(columnIndex);
		cell.setCellValue(farm.getProvinceName());

		// Quận huyện
		row = sheet.getRow(rowIndex);
		cell = row.getCell(columnIndex + 8);
		cell.setCellValue(farm.getDistrictName());

		// Phường xã
		row = sheet.getRow(++rowIndex);
		cell = row.getCell(columnIndex);
		if (farm.getAdministrativeUnit() != null && farm.getAdministrativeUnit().getName() != null)
			cell.setCellValue(farm.getAdministrativeUnit().getName());

		// thôn ấp
		row = sheet.getRow(rowIndex);
		cell = row.getCell(columnIndex + 8);
		if (farm.getVillage() != null)
			cell.setCellValue(farm.getVillage());

		// Địa chỉ
		row = sheet.getRow(++rowIndex);
		cell = row.getCell(columnIndex);
		if (farm.getAdressDetail() != null)
			cell.setCellValue(farm.getAdressDetail());

		// Quy mô hiện tại
		row = sheet.getRow(rowIndex);
		cell = row.getCell(columnIndex + 8);
		if (farm.getBalanceNumber() != null)
			cell.setCellValue(farm.getBalanceNumber());

		// Địa chỉ
		row = sheet.getRow(++rowIndex);
		cell = row.getCell(columnIndex);
		if (farm.getIsOutSourcing() != null && farm.getIsOutSourcing() == true) {
			cell.setCellValue("Có");
		} else
			cell.setCellValue("Không");

		// Hình thức sở hữu
		row = sheet.getRow(rowIndex);
		cell = row.getCell(columnIndex + 8);
		if (farm.getOwnership() != null && farm.getOwnership().getName() != null)
			cell.setCellValue(farm.getOwnership().getName());

		rowIndex = rowIndex + 5;
		row = sheet.getRow(rowIndex);
		cell = row.getCell(0);
		CellStyle cellStyle = cell.getCellStyle();
		CellStyle rowStyle = row.getRowStyle();
		short height = row.getHeight();
		int rowStartTable = rowIndex;
		int lenImport = 0;
		int lenExport = 0;
		if (listExportAnimal != null) {
			lenExport = listExportAnimal.size();
		}
		if (listImportAnimal != null) {
			lenImport = listImportAnimal.size();
		}
		int maxSize = Math.max(lenExport, lenImport);
		if (maxSize < 4) {
			maxSize = 4;
		}

		sheet.shiftRows(rowStartTable, rowStartTable + maxSize, maxSize, true, true);
		sheet.createRow(rowStartTable + maxSize);
		// Nhập đàn
		int i = 0;
		for (ImportExportAnimalDto importExportAnimal : listImportAnimal) {
			if (importExportAnimal != null) {
				if (sheet.getRow(i + rowStartTable) != null) {
					row = sheet.getRow(i + rowStartTable);
				} else
					row = sheet.createRow(i + rowStartTable);
				if (rowStyle != null) {
					row.setRowStyle(rowStyle);
				}
				row.setHeight(height);
				int j = 0;// cell column
				// STT
				cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(i + 1);
				// Mã lô
				cell = row.createCell(++j);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(importExportAnimal.getBatchCode());
				// Giống vật nuôi
				cell = row.createCell(++j);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(importExportAnimal.getAnimal().getName());
				// số lượng con
				cell = row.createCell(++j);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(importExportAnimal.getQuantity());
				// số lượng còn lại
				cell = row.createCell(++j);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(importExportAnimal.getRemainQuantity());
				// số lượng trứng
				cell = row.createCell(++j);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(importExportAnimal.getQuantityEgg());
				// Ngày nhập
				cell = row.createCell(++j);
				cell.setCellStyle(cellStyle);
				if (importExportAnimal.getDateImport() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
					cell.setCellValue(format.format(importExportAnimal.getDateImport()));
				}
				i++;
			}
		}
		// Xuất đàn
		i = 0;
		for (ImportExportAnimalDto importExportAnimal : listExportAnimal) {
			if (importExportAnimal != null) {
				if (sheet.getRow(i + rowStartTable) != null) {
					row = sheet.getRow(i + rowStartTable);
				} else
					row = sheet.createRow(i + rowStartTable);
				if (rowStyle != null) {
					row.setRowStyle(rowStyle);
				}
				row.setHeight(height);
				int j = 0;// cell column
				// STT
				cell = row.createCell(j + 8);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(i + 1);
				// Mã lô
				cell = row.createCell(++j + 8);
				cell.setCellStyle(cellStyle);
				if (importExportAnimal.getImportAnimal() != null)
					cell.setCellValue(importExportAnimal.getImportAnimal().getBatchCode());
				// Số lượng
				cell = row.createCell(++j + 8);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(importExportAnimal.getQuantity());
				// Khối lượng
				cell = row.createCell(++j + 8);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(importExportAnimal.getAmount());
				// số lượng còn lại
				cell = row.createCell(++j + 8);
				cell.setCellStyle(cellStyle);
				if (importExportAnimal.getAnimal() != null)
					cell.setCellValue(importExportAnimal.getAnimal().getName());
				// Ngày nhập
				cell = row.createCell(++j + 8);
				cell.setCellStyle(cellStyle);
				if (importExportAnimal.getDateExport() != null) {
					SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyy");
					cell.setCellValue(format.format(importExportAnimal.getDateExport()));
				}
				i++;
			}
		}

		rowIndex = rowIndex + maxSize + 3;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(2);
		cell.setCellValue(dto.getTotalAmountMeat() + "(Kg)");

		row = sheet.getRow(rowIndex);
		cell = row.createCell(10);
		cell.setCellValue(dto.getTotalAmountEgg() + "(Quả)");

		row = sheet.getRow(++rowIndex);
		cell = row.createCell(0);
		cell.setCellValue("Từ tháng " + dto.getFromMonthMeat() + " đến tháng " + dto.getToMonthMeat() + " năm "
				+ dto.getYearMeat());

		row = sheet.getRow(rowIndex);
		cell = row.createCell(8);
		cell.setCellValue(
				"Từ tháng " + dto.getFromMonthEgg() + " đến tháng " + dto.getToMonthEgg() + " năm " + dto.getYearEgg());

		rowIndex = rowIndex + 2;
		rowStartTable = rowIndex;

		int lenMeat = 0;
		int lenEgg = 0;
		if (dto.getListQuantityMeats() != null) {
			lenMeat = dto.getListQuantityMeats().size();
		}
		if (dto.getListExportEggs() != null) {
			lenEgg = dto.getListExportEggs().size();
		}
		maxSize = Math.max(lenEgg, lenMeat);
		sheet.shiftRows(rowStartTable, rowStartTable + maxSize, maxSize, true, true);
		sheet.createRow(rowStartTable + maxSize);
		i = 0;
		if (dto.getListQuantityMeats() != null)
			for (InventoryReportDto inventory : dto.getListQuantityMeats()) {
				if (inventory != null) {
					if (sheet.getRow(i + rowStartTable) != null) {
						row = sheet.getRow(i + rowStartTable);
					} else
						row = sheet.createRow(i + rowStartTable);
					row.setHeight(height);
					int j = 0;// cell column
					// Tháng
					cell = row.createCell(j);
					CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(inventory.getMonthInYear());
					cell = row.createCell(j + 1);
					cell.setCellStyle(cellStyle);
					CellRangeAddress cellRangeAddress = new CellRangeAddress(i + rowStartTable, i + rowStartTable, 0,
							1);
					sheet.addMergedRegion(cellRangeAddress);
					// Năm
					cell = row.createCell(j + 2);
					CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(inventory.getAmount());
					cell = row.createCell(j + 2 + 1);
					cell.setCellStyle(cellStyle);
					CellRangeAddress cellRangeAddress2 = new CellRangeAddress(i + rowStartTable, i + rowStartTable, 2,
							3);
					sheet.addMergedRegion(cellRangeAddress2);
					i++;
				}
			}

		i = 0;
		if (dto.getListExportEggs() != null)
			for (InventoryReportDto inventory : dto.getListExportEggs()) {
				if (inventory != null) {
					if (sheet.getRow(i + rowStartTable) != null) {
						row = sheet.getRow(i + rowStartTable);
					} else
						row = sheet.createRow(i + rowStartTable);
					row.setHeight(height);
					int j = 0;// cell column
					// Tháng
					cell = row.createCell(j + 8);
					CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(inventory.getMonthInYear());
					cell = row.createCell(j + 9);
					cell.setCellStyle(cellStyle);
					CellRangeAddress cellRangeAddress = new CellRangeAddress(i + rowStartTable, i + rowStartTable, 8,
							9);
					sheet.addMergedRegion(cellRangeAddress);
					// Năm
					cell = row.createCell(j + 10);
					CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(inventory.getQuantity());
					cell = row.createCell(j + 11);
					cell.setCellStyle(cellStyle);
					CellRangeAddress cellRangeAddress2 = new CellRangeAddress(i + rowStartTable, i + rowStartTable, 10,
							11);
					sheet.addMergedRegion(cellRangeAddress2);
					i++;
				}
			}

		workbook.write(response.getOutputStream());
		workbook.close();

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DanhSachNguoiDung.xls");
		response.flushBuffer();
	}

	public static void exportTotalReport(InputStream is, OutputStream out, TotalReportDto dto) throws IOException {
		HSSFWorkbook workbook = new HSSFWorkbook(is);
		HSSFSheet sheet = workbook.getSheetAt(0);
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());

		int rowIndex = 1;
		int columnIndex = 2;
		Row row = null;
		Cell cell = null;
		Row tempRow = sheet.getRow(8);
		Cell tempCell = tempRow.getCell(0);
		CellStyle cellStyle = tempCell.getCellStyle();
		tempCell = tempRow.getCell(1);
		CellStyle cellStyleNumberBold = tempCell.getCellStyle();

		tempRow = sheet.getRow(7);
		tempCell = tempRow.getCell(0);
		CellStyle cellStyle2 = tempCell.getCellStyle();

		tempRow = sheet.getRow(0);
		tempCell = tempRow.getCell(0);
		CellStyle cellStyle0 = tempCell.getCellStyle();

		tempCell = tempRow.getCell(2);
		CellStyle cellStyleNumber = tempCell.getCellStyle();

		tempRow = sheet.getRow(1);
		tempCell = tempRow.getCell(0);
		CellStyle cellStyle3 = tempCell.getCellStyle();

		if (dto != null) {
			if (dto.getListSub() != null && dto.getListSub().size() > 0) {
				for (TotalReportSubDto sub : dto.getListSub()) {
					if (sub.getReportType() == 1) {
						int range = 0;
						if (sub.getSubType() == 1) {
							range = 4;
						} else if (sub.getSubType() == 2) {
							range = 5;
						}
						Row row1 = sheet.getRow(rowIndex);
						cell = row1.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue("Gia cầm");
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + range));

						Row row2 = sheet.getRow(rowIndex + 1);
						cell = row2.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(sub.getAnimalName());
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 1, rowIndex + 1, columnIndex, columnIndex + range));

						Row row3 = sheet.getRow(rowIndex + 2);
						cell = row3.createCell(columnIndex);
						cell.setCellStyle(cellStyle0);

						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 2, rowIndex + 2, columnIndex, columnIndex + range));
						cell.setCellValue("Chia ra");

						Row row4 = sheet.getRow(rowIndex + 3);
						for (int i = 0; i < sub.getListSubDetail().size(); i++) {
							cell = row4.createCell(columnIndex + i);
							cell.setCellValue(sub.getListSubDetail().get(i).getName());
							CellRangeAddress rangeAd = new CellRangeAddress(rowIndex + 3, rowIndex + 5, columnIndex + i,
									columnIndex + i);
							sheet.addMergedRegion(rangeAd);
							cell.setCellStyle(cellStyle0);
						}
						columnIndex += range + 1;
					}
					if (sub.getReportType() == 2) {
						int range = 4;

						Row row1 = sheet.getRow(rowIndex);
						cell = row1.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue("Gia cầm");
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + range));

						Row row2 = sheet.getRow(rowIndex + 1);
						cell = row2.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(sub.getAnimalName());
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 1, rowIndex + 1, columnIndex, columnIndex + range));

						Row row3 = sheet.getRow(rowIndex + 2);
						cell = row3.createCell(columnIndex);
						cell.setCellStyle(cellStyle0);

						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 2, rowIndex + 2, columnIndex, columnIndex + range));
						cell.setCellValue("Chia ra");

						Row row4 = sheet.getRow(rowIndex + 3);
						for (int i = 0; i < sub.getListSubDetail().size(); i++) {
							cell = row4.createCell(columnIndex + i);
							cell.setCellValue(sub.getListSubDetail().get(i).getName());
							CellRangeAddress rangeAd = new CellRangeAddress(rowIndex + 3, rowIndex + 5, columnIndex + i,
									columnIndex + i);
							sheet.addMergedRegion(rangeAd);
							cell.setCellStyle(cellStyle0);
						}
						columnIndex += range + 1;
					}
					if (sub.getReportType() == 3) {
						int range = 3;

						Row row1 = sheet.getRow(rowIndex);
						cell = row1.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue("Gia cầm");
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + range));

						Row row2 = sheet.getRow(rowIndex + 1);
						cell = row2.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(sub.getAnimalName());
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 1, rowIndex + 1, columnIndex, columnIndex + range));

						Row row3 = sheet.getRow(rowIndex + 2);
						cell = row3.createCell(columnIndex);
						cell.setCellStyle(cellStyle0);

						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 2, rowIndex + 2, columnIndex, columnIndex + range));
						cell.setCellValue("Chia ra");

						Row row4 = sheet.getRow(rowIndex + 3);
						for (int i = 0; i < sub.getListSubDetail().size(); i++) {
							cell = row4.createCell(columnIndex + i);
							cell.setCellValue(sub.getListSubDetail().get(i).getName());
							CellRangeAddress rangeAd = new CellRangeAddress(rowIndex + 3, rowIndex + 5, columnIndex + i,
									columnIndex + i);
							sheet.addMergedRegion(rangeAd);
							cell.setCellStyle(cellStyle0);
						}
						columnIndex += range + 1;
					}
					if (sub.getReportType() == 4) {
						int range = 2;

						Row row1 = sheet.getRow(rowIndex);
						cell = row1.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue("Gia cầm");
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + range));

						Row row2 = sheet.getRow(rowIndex + 1);
						cell = row2.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(sub.getAnimalName());
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 1, rowIndex + 1, columnIndex, columnIndex + range));

						Row row3 = sheet.getRow(rowIndex + 2);
						cell = row3.createCell(columnIndex);
						cell.setCellStyle(cellStyle0);

						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 2, rowIndex + 2, columnIndex, columnIndex + range));
						cell.setCellValue("Chia ra");

						Row row4 = sheet.getRow(rowIndex + 3);
						for (int i = 0; i < sub.getListSubDetail().size(); i++) {
							cell = row4.createCell(columnIndex + i);
							cell.setCellValue(sub.getListSubDetail().get(i).getName());
							CellRangeAddress rangeAd = new CellRangeAddress(rowIndex + 3, rowIndex + 5, columnIndex + i,
									columnIndex + i);
							sheet.addMergedRegion(rangeAd);
							cell.setCellStyle(cellStyle0);
						}
						columnIndex += range + 1;
					}
					if (sub.getReportType() == 5) {
						int range = 1;

						Row row1 = sheet.getRow(rowIndex);
						cell = row1.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue("Gia cầm");
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + range));

						Row row2 = sheet.getRow(rowIndex + 1);
						cell = row2.createCell(columnIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(sub.getAnimalName());
						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 1, rowIndex + 1, columnIndex, columnIndex + range));

						Row row3 = sheet.getRow(rowIndex + 2);
						cell = row3.createCell(columnIndex);
						cell.setCellStyle(cellStyle0);

						sheet.addMergedRegion(
								new CellRangeAddress(rowIndex + 2, rowIndex + 2, columnIndex, columnIndex + range));
						cell.setCellValue("Chia ra");

						Row row4 = sheet.getRow(rowIndex + 3);
						for (int i = 0; i < sub.getListSubDetail().size(); i++) {
							cell = row4.createCell(columnIndex + i);
							cell.setCellValue(sub.getListSubDetail().get(i).getName());
							CellRangeAddress rangeAd = new CellRangeAddress(rowIndex + 3, rowIndex + 5, columnIndex + i,
									columnIndex + i);
							sheet.addMergedRegion(rangeAd);
							cell.setCellStyle(cellStyle0);
						}

//						Row row5 = sheet.getRow(rowIndex + 4);
//						for (int i = 0; i < sub.getListSubDetail().size(); i++) {
//							cell = row5.createCell(columnIndex + i);
//							cell.setCellValue(sub.getListSubDetail().get(i).getUnitName());
//							cell.setCellStyle(cellStyle0);
//						}						
						columnIndex += range + 1;
					}
				}
			}
		}
		rowIndex = 9;
		boolean checkTotalCol = false;
		Row headNumRow = sheet.getRow(8);
		Cell cellNumRow = null;
		if (dto.getListAdministrativeUnit() != null && dto.getListAdministrativeUnit().size() > 0) {
			double grandTotal = 0;
			for (int i = 0; i < dto.getListAdministrativeUnit().size(); i++) {
				columnIndex = 2;
				FmsAdministrativeUnitDto adDto = dto.getListAdministrativeUnit().get(i);

				row = sheet.createRow(rowIndex + i);
				cell = row.createCell(0);
				if (adDto.getLevel() > 2) {
					cell.setCellStyle(cellStyle);
				} else if (adDto.getLevel() == 2) {
					cell.setCellStyle(cellStyle2);
				} else {
					cell.setCellStyle(cellStyle3);
				}
				cell.setCellValue(dto.getListAdministrativeUnit().get(i).getName());

				int totalQuantity = 0;
				if (dto.getListSub() != null && dto.getListSub().size() > 0) {
					int j = 0;
					for (TotalReportSubDto sub : dto.getListSub()) {
						int range = 0;
						double totalCol = 0D;

						if (sub.getReportType() == 1) {
							// Detail 0-->4
							if (sub.getSubType() == 1) {
								range = 4;

								// Số con hiện có
								TotalReportSubDetailDto subDetailDto = sub.getListSubDetail().get(0);

								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumQuantity = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										// Xã
										if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 0);
											cell.setCellValue(ivn.getQuantity());
											cell.setCellStyle(cellStyleNumber);
											totalQuantity += ivn.getQuantity();
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getQuantity();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 0);
										cell.setCellValue(sumQuantity);
										cell.setCellStyle(cellStyleNumberBold);
										totalQuantity += sumQuantity;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 0);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}
								// Con thịt
								subDetailDto = sub.getListSubDetail().get(1);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumQuantity = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										// Xã
										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 1);
											cell.setCellValue(ivn.getQuantity());
											cell.setCellStyle(cellStyleNumber);
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getQuantity();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 1);
										cell.setCellValue(sumQuantity);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumQuantity;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 1);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}
								// Tr đó: Gà công nghiệp
								subDetailDto = sub.getListSubDetail().get(2);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumQuantity = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										// Xã
										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 2);
											cell.setCellValue(ivn.getQuantity());
											cell.setCellStyle(cellStyleNumber);
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getQuantity();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 2);
										cell.setCellValue(sumQuantity);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumQuantity;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 2);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}

								// Gà đẻ trứng
								subDetailDto = sub.getListSubDetail().get(3);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumQuantity = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 3);
											cell.setCellValue(ivn.getQuantity());
											cell.setCellStyle(cellStyleNumber);
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getQuantity();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 3);
										cell.setCellValue(sumQuantity);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumQuantity;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 3);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}

								// Tr đó: Gà công nghiệp
								subDetailDto = sub.getListSubDetail().get(4);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumQuantity = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 4);
											cell.setCellValue(ivn.getQuantity());
											cell.setCellStyle(cellStyleNumber);
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getQuantity();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 4);
										cell.setCellValue(sumQuantity);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumQuantity;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 4);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}
								columnIndex += range + 1;
							}
							// Detail 0-->5
							else if (sub.getSubType() == 2) {
								range = 5;
								// Số con xuất chuồng
								TotalReportSubDetailDto subDetailDto = sub.getListSubDetail().get(0);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumQuantity = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 0);
											cell.setCellValue(ivn.getQuantity());
											cell.setCellStyle(cellStyleNumber);
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getQuantity();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 0);
										cell.setCellValue(sumQuantity);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumQuantity;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 0);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}

								}

								// Tr đó: Gà công nghiệp
								subDetailDto = sub.getListSubDetail().get(1);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumQuantity = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 1);
											cell.setCellValue(ivn.getQuantity());
											cell.setCellStyle(cellStyleNumber);
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getQuantity();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 1);
										cell.setCellValue(sumQuantity);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumQuantity;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 1);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}

//								Sản lượng thịt hơi xuất chuồng
								subDetailDto = sub.getListSubDetail().get(2);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumAmount = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {

										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 2);
											cell.setCellValue(ivn.getAmount());
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumAmount += ivn.getAmount();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumAmount += ivn.getAmount();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumAmount += ivn.getAmount();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getAmount();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 2);
										cell.setCellValue(sumAmount);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumAmount;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 2);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}

//								Tr đó: Gà công nghiệp
								subDetailDto = sub.getListSubDetail().get(3);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumAmount = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 3);
											cell.setCellValue(ivn.getAmount());
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumAmount += ivn.getAmount();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumAmount += ivn.getAmount();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumAmount += ivn.getAmount();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getAmount();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 3);
										cell.setCellValue(sumAmount);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumAmount;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 3);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}

//								Sản lượng trứng trong kỳ
								subDetailDto = sub.getListSubDetail().get(4);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumQuantity = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 4);
											cell.setCellValue(ivn.getQuantity());
											cell.setCellStyle(cellStyleNumber);
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getQuantity();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 4);
										cell.setCellValue(sumQuantity);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumQuantity;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 4);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}

//								Tr đó: Gà công nghiệp
								subDetailDto = sub.getListSubDetail().get(5);
								if (subDetailDto.getListInventoryReport() != null
										&& subDetailDto.getListInventoryReport().size() > 0) {
									double sumQuantity = 0D;
									for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
										if (ivn.getWardId().equals(adDto.getId())) {
											cell = row.createCell(columnIndex + 5);
											cell.setCellValue(ivn.getQuantity());
											cell.setCellStyle(cellStyleNumber);
										}
										// Huyện
										else if (adDto.getLevel().equals(2)
												&& ivn.getDistrictId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Tỉnh
										else if (adDto.getLevel().equals(3)
												&& ivn.getProvinceId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Vùng
										else if (adDto.getLevel().equals(4)
												&& ivn.getRegionId().equals(adDto.getId())) {
											sumQuantity += ivn.getQuantity();
										}
										// Cả nước
										if (!checkTotalCol) {
											totalCol += ivn.getQuantity();
										}
									}
									if (adDto.getLevel() > 1) {
										cell = row.createCell(columnIndex + 5);
										cell.setCellValue(sumQuantity);
										cell.setCellStyle(cellStyleNumberBold);
//										totalQuantity += sumQuantity;
									}
									// Cả nước
									if (!checkTotalCol) {
										cellNumRow = headNumRow.createCell(columnIndex + 5);
										cellNumRow.setCellStyle(cellStyleNumberBold);
										cellNumRow.setCellValue(totalCol);
										totalCol = 0;
									}
								}
								columnIndex += range + 1;
							}
						}
						if (sub.getReportType() == 2) {
							range = 4;
							TotalReportSubDetailDto subDetailDto = sub.getListSubDetail().get(0);
//							Số con hiện có
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumQuantity = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 0);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 0);
									cell.setCellValue(sumQuantity);
									cell.setCellStyle(cellStyleNumberBold);
									totalQuantity += sumQuantity;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 0);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}
//							Tr đó: Vịt đẻ trứng
							subDetailDto = sub.getListSubDetail().get(1);
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumQuantity = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 1);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
//										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 1);
									cell.setCellValue(sumQuantity);
									cell.setCellStyle(cellStyleNumberBold);
//									totalQuantity += sumQuantity;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 1);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}

//							Số con xuất chuồng
							subDetailDto = sub.getListSubDetail().get(2);
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumQuantity = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 2);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
//										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 2);
									cell.setCellValue(sumQuantity);
									cell.setCellStyle(cellStyleNumberBold);
//									totalQuantity += sumQuantity;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 2);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}
							// Sản lượng thịt hơi xuất chuồng
							subDetailDto = sub.getListSubDetail().get(3);
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumAmount = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 3);
										cell.setCellValue(ivn.getAmount());
										cell.setCellStyle(cellStyleNumber);
//										totalQuantity += ivn.getAmount();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumAmount += ivn.getAmount();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumAmount += ivn.getAmount();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumAmount += ivn.getAmount();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getAmount();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 3);
									cell.setCellValue(sumAmount);
									cell.setCellStyle(cellStyleNumberBold);
//									totalQuantity += sumAmount;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 3);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}

//							Sản lượng trứng trong kỳ
							subDetailDto = sub.getListSubDetail().get(4);
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumAmount = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 4);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
//										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumAmount += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumAmount += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumAmount += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 4);
									cell.setCellValue(sumAmount);
									cell.setCellStyle(cellStyleNumberBold);
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 4);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}

							columnIndex += range + 1;
						}
						if (sub.getReportType() == 3) {
							range = 3;
							TotalReportSubDetailDto subDetailDto = sub.getListSubDetail().get(0);
//							Số con hiện có
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumQuantity = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 0);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 0);
									cell.setCellValue(sumQuantity);
									cell.setCellStyle(cellStyleNumberBold);
									totalQuantity += sumQuantity;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 0);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}

//							Số con xuất chuồng
							subDetailDto = sub.getListSubDetail().get(1);
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumQuantity = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 1);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
//										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 1);
									cell.setCellValue(sumQuantity);
									cell.setCellStyle(cellStyleNumberBold);
//									totalQuantity += sumQuantity;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 1);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}
							// Sản lượng thịt hơi xuất chuồng
							subDetailDto = sub.getListSubDetail().get(2);
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumAmount = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 2);
										cell.setCellValue(ivn.getAmount());
										cell.setCellStyle(cellStyleNumber);
//										totalQuantity += ivn.getAmount();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumAmount += ivn.getAmount();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumAmount += ivn.getAmount();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumAmount += ivn.getAmount();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getAmount();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 2);
									cell.setCellValue(sumAmount);
									cell.setCellStyle(cellStyleNumberBold);
//									totalQuantity += sumAmount;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 2);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}

//							Sản lượng trứng trong kỳ
							subDetailDto = sub.getListSubDetail().get(3);
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumAmount = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 3);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
//										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumAmount += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumAmount += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumAmount += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 3);
									cell.setCellValue(sumAmount);
									cell.setCellStyle(cellStyleNumberBold);
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 3);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}
							columnIndex += range + 1;
						}
						if (sub.getReportType() == 4) {
							range = 2;
							TotalReportSubDetailDto subDetailDto = sub.getListSubDetail().get(0);
//							Số con hiện có
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumQuantity = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 0);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 0);
									cell.setCellValue(sumQuantity);
									cell.setCellStyle(cellStyleNumberBold);
									totalQuantity += sumQuantity;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 0);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}

//							Số con xuất chuồng
							subDetailDto = sub.getListSubDetail().get(1);
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumQuantity = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 1);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
//										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 1);
									cell.setCellValue(sumQuantity);
									cell.setCellStyle(cellStyleNumberBold);
//									totalQuantity += sumQuantity;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 1);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}
							// Sản lượng thịt hơi xuất chuồng
							subDetailDto = sub.getListSubDetail().get(2);
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumAmount = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 2);
										cell.setCellValue(ivn.getAmount());
										cell.setCellStyle(cellStyleNumber);
//										totalQuantity += ivn.getAmount();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumAmount += ivn.getAmount();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumAmount += ivn.getAmount();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumAmount += ivn.getAmount();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getAmount();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 2);
									cell.setCellValue(sumAmount);
									cell.setCellStyle(cellStyleNumberBold);
//									totalQuantity += sumAmount;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 2);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}
							columnIndex += range + 1;
						}
						if (sub.getReportType() == 5) {
							range = 1;
							TotalReportSubDetailDto subDetailDto = sub.getListSubDetail().get(0);
//							Số con hiện có
							if (subDetailDto.getListInventoryReport() != null
									&& subDetailDto.getListInventoryReport().size() > 0) {
								double sumQuantity = 0D;
								for (InventoryReportDto ivn : subDetailDto.getListInventoryReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 0);
										cell.setCellValue(ivn.getQuantity());
										cell.setCellStyle(cellStyleNumber);
										totalQuantity += ivn.getQuantity();
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumQuantity += ivn.getQuantity();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getQuantity();
									}
								}
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 0);
									cell.setCellValue(sumQuantity);
									cell.setCellStyle(cellStyleNumberBold);
									totalQuantity += sumQuantity;
								}
								// Cả nước
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 0);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}

							subDetailDto = sub.getListSubDetail().get(1);
//							Số con hiện có
							if (subDetailDto.getListLiveStockProductReport() != null
									&& subDetailDto.getListLiveStockProductReport().size() > 0) {
								double sumQuantity = 0D;
								for (LiveStockProductReportDto ivn : subDetailDto.getListLiveStockProductReport()) {
									// Xã
									if (adDto.getLevel().equals(1) && ivn.getWardId().equals(adDto.getId())) {
										cell = row.createCell(columnIndex + 1);
										cell.setCellValue(ivn.getAmount());
										cell.setCellStyle(cellStyleNumber);
									}
									// Huyện
									else if (adDto.getLevel().equals(2) && ivn.getDistrictId().equals(adDto.getId())) {
										sumQuantity += ivn.getAmount();
									}
									// Tỉnh
									else if (adDto.getLevel().equals(3) && ivn.getProvinceId().equals(adDto.getId())) {
										sumQuantity += ivn.getAmount();
									}
									// Vùng
									else if (adDto.getLevel().equals(4) && ivn.getRegionId().equals(adDto.getId())) {
										sumQuantity += ivn.getAmount();
									}
									// Cả nước
									if (!checkTotalCol) {
										totalCol += ivn.getAmount();
									}
								}
								// Nếu là cấp huyện - tỉnh - xã
								if (adDto.getLevel() > 1) {
									cell = row.createCell(columnIndex + 1);
									cell.setCellValue(sumQuantity);
									cell.setCellStyle(cellStyleNumberBold);
								}
								// Cả nước (chỉ chạy 1 lần)
								if (!checkTotalCol) {
									cellNumRow = headNumRow.createCell(columnIndex + 1);
									cellNumRow.setCellStyle(cellStyleNumberBold);
									cellNumRow.setCellValue(totalCol);
									totalCol = 0;
								}
							}
							columnIndex += range + 1;
						}
						j += 1;
					}
					checkTotalCol = true;
				}
				// Ô tổng số của dòng
				cell = row.createCell(1);
				cell.setCellValue(totalQuantity);
				if (adDto.getLevel() > 1) {
					cell.setCellStyle(cellStyleNumberBold);
				} else {
					cell.setCellStyle(cellStyleNumber);
				}
				if (adDto.getLevel() == 4) {
					grandTotal += totalQuantity;
				}
			}
			// Tổng cộng cả nước
			tempRow = sheet.getRow(8);
			tempCell = tempRow.getCell(1);
			tempCell.setCellValue(grandTotal);
		}
		workbook.write(out);
		workbook.close();
	}

	public static List<AnimalDto> getAnimalDtosFromInputStream(InputStream bis) throws IOException {
		Workbook workbook = new HSSFWorkbook(bis);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		// Iterator<Row> iterator = datatypeSheet.iterator();
		List<AnimalDto> result = new ArrayList<AnimalDto>();
		int rowIndex = 1;
		int num = datatypeSheet.getLastRowNum();
		while (rowIndex <= num) {
			Row row = datatypeSheet.getRow(rowIndex);
			int cellIndex = 0;
			if (row != null) {
				AnimalDto animalDto = new AnimalDto();
				// Scentifi Name
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					animalDto.setScienceName(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					animalDto.setScienceName(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				cellIndex++;// Name
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					animalDto.setName(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					animalDto.setName(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				cellIndex++;// English name
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					animalDto.setEnName(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					animalDto.setEnName(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				cellIndex++;// Code
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					animalDto.setCode(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					animalDto.setCode(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				cellIndex++;// CITES
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					animalDto.setCites(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					animalDto.setCode(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				cellIndex++;// vnList
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					animalDto.setVnlist(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					animalDto.setVnlist(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				cellIndex++;// AniGroup
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					animalDto.setAniGroup(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					animalDto.setAniGroup(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				cellIndex++;// Breed
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					animalDto.setBreed(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					animalDto.setBreed(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				cellIndex++;// animal Class
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					animalDto.setAnimalClass(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					animalDto.setAnimalClass(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				result.add(animalDto);
				rowIndex++;

			}
		}
		return result;
	}

	@SuppressWarnings("resource")
	public static List<FarmAnimal2017Dto> getFarmImportAnimalFromInputStream(InputStream is) throws IOException {
		Workbook workbook = new XSSFWorkbook(is);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		// FarmAnimal2017Dto
		List<FarmAnimal2017Dto> result = new ArrayList<FarmAnimal2017Dto>();
		int rowIndex = 1;
		int num = datatypeSheet.getLastRowNum();
		while (rowIndex <= num) {
			Row row = datatypeSheet.getRow(rowIndex);
			int cellIndex = 0;
			if (row != null) {
				FarmAnimal2017Dto dto = new FarmAnimal2017Dto();
				// Date collection
				if (row.getCell(cellIndex) != null) {
					try {
						dto.setDateCollect(row.getCell(cellIndex).getDateCellValue());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				cellIndex++;
				// FARM ID
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					dto.setFarmId(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					dto.setFarmId(row.getCell(cellIndex).getNumericCellValue() + "");
				}

				// ANIMAL ID
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					dto.setAnimalId(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					dto.setAnimalId(str);
				}
				// ANIMAL TYPE ID
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					dto.setAnimalTypeId(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					dto.setAnimalTypeId(row.getCell(cellIndex).getStringCellValue());
				}
				// Total head
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setTotalHead(Long.parseLong(row.getCell(cellIndex).getStringCellValue()));
					} catch (Exception e) {
						dto.setTotalHead(null);
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					dto.setTotalHead(Long.parseLong(str));
				}
				// male
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setMale(Long.parseLong(row.getCell(cellIndex).getStringCellValue()));
					} catch (Exception e) {
						dto.setMale(null);
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					dto.setMale(Long.parseLong(str));
				}
				// female
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setFeMale(Long.parseLong(row.getCell(cellIndex).getStringCellValue()));
					} catch (Exception e) {
						dto.setFeMale(null);
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					dto.setFeMale(Long.parseLong(str));
				}
				// gender unknown
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setGenderUn(Long.parseLong(row.getCell(cellIndex).getStringCellValue()));
					} catch (Exception e) {
						dto.setGenderUn(null);
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					dto.setGenderUn(Long.parseLong(str));
				}
				// source
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					dto.setSource(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					dto.setSource(row.getCell(cellIndex).getStringCellValue());
				}
				// purpose
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					dto.setPurpose(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					dto.setPurpose(row.getCell(cellIndex).getStringCellValue());
				}

				// register date
				cellIndex++;
				if (row.getCell(cellIndex) != null) {
					try {
						dto.setRegistrationDate(row.getCell(cellIndex).getDateCellValue());
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				result.add(dto);
				rowIndex++;

			}
		}
		return result;
	}

	public static List<FarmAnimal2017Dto> getBearInfoInputStream(ByteArrayInputStream pkg) throws IOException {
		Workbook workbook = new XSSFWorkbook(pkg);
		Sheet datatypeSheet = workbook.getSheetAt(0);
		// FarmAnimal2017Dto
		List<FarmAnimal2017Dto> result = new ArrayList<FarmAnimal2017Dto>();
		int rowIndex = 1;
		int num = datatypeSheet.getLastRowNum();
		while (rowIndex <= num) {
			Row row = datatypeSheet.getRow(rowIndex);
			int cellIndex = 0;
			if (row != null) {
				FarmAnimal2017Dto dto = new FarmAnimal2017Dto();
				// Date collection
				Cell current = row.getCell(cellIndex);
				if (current != null) {
					try {
						Date dateImport = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH).parse("01-Jun-2016");
						if (dateImport != null)
							dto.setDateCollect(dateImport);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// FARM ID
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					dto.setFarmId(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					dto.setFarmId(row.getCell(cellIndex).getStringCellValue() + "");
				}
				// BEAR TYPE
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					dto.setAnimalTypeId(row.getCell(cellIndex).getStringCellValue());
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					dto.setAnimalTypeId(row.getCell(cellIndex).getStringCellValue() + "");
				}
				// Total head
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setTotalHead(new Long(row.getCell(cellIndex).getStringCellValue()));
					} catch (Exception e) {
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					dto.setTotalHead(new Long(str));
				}
				// male
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setMale(new Long(row.getCell(cellIndex).getStringCellValue()));
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					try {
						dto.setMale(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// Female
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setFeMale(new Long(row.getCell(cellIndex).getStringCellValue()));
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					try {
						dto.setFeMale(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// Gender_Unknown
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setGenderUn(new Long(row.getCell(cellIndex).getStringCellValue()));
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					try {
						dto.setGenderUn(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// Total tag
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setTotalTag(new Long(row.getCell(cellIndex).getStringCellValue()));
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					try {
						dto.setTotalTag(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// TAG ID
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					try {
						dto.setTagId(row.getCell(cellIndex).getStringCellValue());
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					row.getCell(cellIndex).setCellType(CellType.STRING);
					String str = row.getCell(cellIndex).getStringCellValue() + "";
//					if(str.substring(str.length()-2, str.length()).equals(".0")) {
//						str = str.substring(0, str.length() - 2);
//					}
					try {
						dto.setTagId(str);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// total notag
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					String str = row.getCell(cellIndex).getStringCellValue();
					try {
						dto.setTotalNoTag(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					try {
						dto.setTotalNoTag(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// total infarm
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					String str = row.getCell(cellIndex).getStringCellValue();
					try {
						dto.setTotalInfarm(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					try {
						dto.setTotalInfarm(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// total other
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					String str = row.getCell(cellIndex).getStringCellValue();
					try {
						dto.setTotalOther(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					try {
						dto.setTotalOther(new Long(str));
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// register Status
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					String str = row.getCell(cellIndex).getStringCellValue();
					try {
						dto.setRegisterStatus(str);
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					try {
						dto.setRegisterStatus(str);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				// note
				cellIndex++;
				if (row.getCell(cellIndex) != null && row.getCell(cellIndex).getCellTypeEnum() == CellType.STRING) {
					String str = row.getCell(cellIndex).getStringCellValue();
					try {
						dto.setNote(str);
					} catch (Exception e) {
						// TODO: handle exception
					}
				} else if (row.getCell(cellIndex) != null
						&& row.getCell(cellIndex).getCellTypeEnum() == CellType.NUMERIC) {
					String str = row.getCell(cellIndex).getNumericCellValue() + "";
					if (str.substring(str.length() - 2, str.length()).equals(".0")) {
						str = str.substring(0, str.length() - 2);
					}
					try {
						dto.setNote(str);
					} catch (Exception e) {
						// TODO: handle exception
					}
				}

				result.add(dto);
				rowIndex++;

			}
		}
		return result;
	}

	public static void setBorderThin4CellStyle(CellStyle cellStyle) {
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
	}

	public static void exportAnimalReportData(List<AnimalReportData> list, Map<String, OriginalDto> originalSet,
			Map<String, ProductTargetDto> productTargetSet, InputStream is, OutputStream fileOutputStream)
			throws IOException {
		Workbook workbook = new HSSFWorkbook(is);

		Sheet sheet = workbook.getSheetAt(0);
		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFillBackgroundColor(IndexedColors.GREY_40_PERCENT.getIndex());

		PropertyTemplate pt = new PropertyTemplate();

		int rowIndex = 9;
		int cellIndex = 0;
		Row row = null;
		Cell cell = null;
		int totalFarm = 0;
		Map<Long, Long> mapFarm = new HashMap<Long, Long>();

		CellStyle cellStyle = workbook.createCellStyle();
		Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		font.setFontHeightInPoints((short) 12);
		cellStyle.setAlignment(HorizontalAlignment.LEFT);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFont(font);
		setBorderThin4CellStyle(cellStyle);

		CellStyle cellStyle2 = workbook.createCellStyle();
		Font font2 = workbook.createFont();
		font2.setFontName("Times New Roman");
		font2.setFontHeightInPoints((short) 11);
		font2.setBold(false);
		font2.setItalic(false);
		cellStyle2.setAlignment(HorizontalAlignment.LEFT);
		cellStyle2.setVerticalAlignment(VerticalAlignment.BOTTOM);
		cellStyle2.setFont(font2);
		setBorderThin4CellStyle(cellStyle2);

		CellStyle cellStyle3 = workbook.createCellStyle();
		Font italic = workbook.createFont();
		italic.setFontName("Times New Roman");
		italic.setFontHeightInPoints((short) 11);
		italic.setBold(false);
		italic.setItalic(true);
		cellStyle3.setAlignment(HorizontalAlignment.LEFT);
		cellStyle3.setVerticalAlignment(VerticalAlignment.BOTTOM);
		cellStyle3.setFont(italic);
		setBorderThin4CellStyle(cellStyle3);

		CellStyle cellStyle4 = workbook.createCellStyle();
		Font font4 = workbook.createFont();
		font4.setFontName("Times New Roman");
		font4.setFontHeightInPoints((short) 11);
		cellStyle4.setAlignment(HorizontalAlignment.RIGHT);
		cellStyle4.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle4.setFont(font4);
		setBorderThin4CellStyle(cellStyle4);

		CellStyle cellStyle5 = workbook.createCellStyle();
		Font font5 = workbook.createFont();
		font5.setFontName("Times New Roman");
		font5.setFontHeightInPoints((short) 11);
		cellStyle5.setAlignment(HorizontalAlignment.CENTER);
		cellStyle5.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle5.setFont(font5);
		setBorderThin4CellStyle(cellStyle5);

		CellStyle cellStyle6 = workbook.createCellStyle();
		Font font6 = workbook.createFont();
		font6.setFontName("Times New Roman");
		font6.setFontHeightInPoints((short) 10);
		font6.setItalic(true);
		cellStyle6.setFont(font6);

		CellStyle cellStyle7 = workbook.createCellStyle();
		Font font7 = workbook.createFont();
		font7.setFontName("Times New Roman");
		font7.setFontHeightInPoints((short) 10.5);
		font7.setBold(true);
		font7.setItalic(true);
		cellStyle7.setFont(font7);

		int maxSize = list.size();
		int rowStartTable = 10;
		sheet.shiftRows(rowStartTable, rowStartTable + maxSize, maxSize, true, true);
//		sheet.createRow(rowStartTable + maxSize);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		int index = 0;
		int totalReport = 0;
		String originalTitle = "     1. Cột 12:";
		String productTargetTitle = "     2. Cột 13:";
		for (AnimalReportData entity : list) {
			if (entity.getFarm() != null && entity.getFarm().getId() != null
					&& !mapFarm.containsKey(entity.getFarm().getId())) {
				totalFarm++;
				mapFarm.put(entity.getFarm().getId(), entity.getFarm().getId());
			}
			cellIndex = 0;
			if (sheet.getRow(rowStartTable + index) != null) {
				row = sheet.getRow(rowStartTable + index);
			} else {
				row = sheet.createRow(rowStartTable + index);
			}
			cell = row.createCell(cellIndex);
			cell.setCellValue(index + 1);
			cell.setCellStyle(cellStyle);

			String province = "";
			String district = "";
			String ward = "";
			if (entity.getFarm() != null && entity.getFarm().getAdministrativeUnit() != null) {
				ward = entity.getFarm().getAdministrativeUnit().getName();
				if (entity.getFarm().getAdministrativeUnit().getParent() != null) {
					district = entity.getFarm().getAdministrativeUnit().getParent().getName();
					if (entity.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
						province = entity.getFarm().getAdministrativeUnit().getParent().getParent().getName();
					}
				}
			}
			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(province);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(district);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(ward);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getFarm() != null && entity.getFarm().getOwnerName() != null)
				cell.setCellValue(entity.getFarm().getOwnerName());
			else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle);

			cellIndex++;// Ten Thong thuong
			cell = row.createCell(cellIndex);
			if (entity.getAnimal() != null && entity.getAnimal().getName() != null)
				cell.setCellValue(entity.getAnimal().getName());
			else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle2);

			cellIndex++;// Ten Khoa hoc
			cell = row.createCell(cellIndex);
			if (entity.getAnimal() != null && entity.getAnimal().getScienceName() != null)
				cell.setCellValue(entity.getAnimal().getScienceName());
			else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle3);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getTotal() != null) {
				cell.setCellValue(entity.getTotal() + "");
				totalReport += entity.getTotal();
			} else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle4);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getMale() != null)
				cell.setCellValue(entity.getMale() + "");
			else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle4);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getFemale() != null)
				cell.setCellValue(entity.getFemale() + "");
			else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle4);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getUnGender() != null)
				cell.setCellValue(entity.getUnGender() + "");
			else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle4);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getSource() != null) {
				cell.setCellValue(entity.getSource());
			} else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getPurpose() != null) {
				cell.setCellValue(entity.getPurpose());
			} else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle5);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getFarm() != null && entity.getFarm().getBusinessRegistrationDate() != null) {
				cell.setCellValue(formatter.format(entity.getFarm().getBusinessRegistrationDate()));
			} else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle5);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getFarm() != null && entity.getFarm().getDescription() != null) {
				cell.setCellValue(entity.getFarm().getDescription());
			} else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle5);

			index++;
		}
		// Xóa dòng đứng trước dòng có index = rowStartTable + maxSize+1
		sheet.shiftRows(rowStartTable + maxSize + 1, rowStartTable + maxSize + 1, -1);
		row = sheet.getRow(rowStartTable + maxSize);
		cell = row.getCell(0);
		cell.setCellValue("Tổng: " + totalFarm + " trại");

		cell = row.getCell(7);
		cell.setCellValue(totalReport);

		for (String key : productTargetSet.keySet()) {
			productTargetTitle += " (" + key + ") " + productTargetSet.get(key).getName() + ";";
		}
		for (String key : originalSet.keySet()) {
			originalTitle += " (" + key + ") " + originalSet.get(key).getName() + ";";
		}

		if (row != null) {
			row = sheet.createRow(rowStartTable + maxSize + 3);
			cell = row.createCell(0);
			cell.setCellValue("Ghi chú:");
			cell.setCellStyle(cellStyle7);
		}

		row = sheet.getRow(rowStartTable + maxSize + 4);
		if (row != null) {
			cell = row.createCell(4);
			cell.setCellValue(originalTitle);
			cell.setCellStyle(cellStyle6);
		} else {
			row = sheet.createRow(rowStartTable + maxSize + 4);
			cell = row.createCell(4);
			cell.setCellValue(originalTitle);
			cell.setCellStyle(cellStyle6);
		}
		row = sheet.getRow(rowStartTable + maxSize + 5);
		if (row != null) {
			cell = row.createCell(4);
			cell.setCellValue(productTargetTitle);
			cell.setCellStyle(cellStyle6);
		} else {
			row = sheet.createRow(rowStartTable + maxSize + 5);
			cell = row.createCell(4);
			cell.setCellValue(productTargetTitle);
			cell.setCellStyle(cellStyle6);
		}
		row = sheet.getRow(rowStartTable + maxSize + 6);
		if (row != null) {
			cell = row.createCell(4);
			cell.setCellValue(
					"   3.  Động vật hoang dã nguy cấp, quý, hiếm là các loài động vật được quy định tại Nghị định số 32/2006/NĐ-CP và Thông tư số 04/2017/TT-BNNPTNT");
			cell.setCellStyle(cellStyle6);
		} else {
			row = sheet.createRow(rowStartTable + maxSize + 6);
			cell = row.createCell(4);
			cell.setCellValue(
					"   3.  Động vật hoang dã nguy cấp, quý, hiếm là các loài động vật được quy định tại Nghị định số 32/2006/NĐ-CP và Thông tư số 04/2017/TT-BNNPTNT");
			cell.setCellStyle(cellStyle6);
		}

		workbook.write(fileOutputStream);

	}

	public static void exportAnimalReportDataByYearMonth(List<AnimalReportData> list, List<AnimalReportData> listFarm,
			OutputStream out, AnimalReportDataSearchDto searchDto, String titleHeader) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("ThongTinDan");
		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Times New Roman");
		font.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 10);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);
		sheet.createRow(1);
		sheet.createRow(4);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(5);
		cell.setCellValue("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM");
		cell.setCellStyle(cellStyleNoBoder);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 5, 8));
		row = sheet.getRow(cellIndex + 1);
		cell = row.createCell(5);
		cell.setCellValue("Độc lập - Tự do - Hạnh phúc");
		cell.setCellStyle(cellStyleNoBoder);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex + 1, rowIndex + 1, 5, 8));
		row = sheet.getRow(4);
		cell = row.createCell(0);
		cell.setCellValue(("Cơ sở dữ liệu Cơ sở nuôi Động vật hoang dã" + titleHeader).toUpperCase());
		cell.setCellStyle(cellStyleNoBoder);
		row.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 15));

		rowIndex = 7;
		cellIndex = 0;
		sheet.createRow(7);
		sheet.createRow(8);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tỉnh");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Huyện");
		sheet.setColumnWidth(cellIndex, 18 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xã");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ấp/Thôn");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số cơ sở");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên cơ sở");
		sheet.setColumnWidth(cellIndex, 18 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên loài nuôi");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học(tên latin)");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã loài");
		sheet.setColumnWidth(cellIndex, 10 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng 2017");
		sheet.setColumnWidth(cellIndex, 10 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể bố mẹ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn giống hậu bị");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng con dưới 1 tuổi(chưa trưởng thành)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng cá thể trên 1 tuổi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tình trạng đăng ký");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số đăng ký");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Năm đăng ký");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mục đích gây nuôi");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Nguồn gốc");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		int rowStartTable = 9;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		int index = 0;
		for (AnimalReportData entity : list) {
			cellIndex = 0;
			if (sheet.getRow(rowStartTable + index) != null) {
				row = sheet.getRow(rowStartTable + index);
			} else {
				row = sheet.createRow(rowStartTable + index);
			}

			String province = "";
			String district = "";
			String ward = "";
			if (entity.getFarm() != null && entity.getFarm().getAdministrativeUnit() != null) {
				ward = entity.getFarm().getAdministrativeUnit().getName();
				if (entity.getFarm().getAdministrativeUnit().getParent() != null) {
					district = entity.getFarm().getAdministrativeUnit().getParent().getName();
					if (entity.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
						province = entity.getFarm().getAdministrativeUnit().getParent().getParent().getName();
					}
				}
			}
			cell = row.createCell(cellIndex);
			cell.setCellValue(province);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(district);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(ward);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getFarm() != null && entity.getFarm().getAdressDetail() != null)
				cell.setCellValue(entity.getFarm().getVillage());
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getFarm() != null && entity.getFarm().getCode() != null)
				cell.setCellValue(entity.getFarm().getCode());
			else
				cell.setCellValue("");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getFarm() != null && entity.getFarm().getName() != null)
				cell.setCellValue(entity.getFarm().getName());
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue("");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getAnimal() != null && entity.getAnimal().getName() != null)
				cell.setCellValue(entity.getAnimal().getName());
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getAnimal() != null && entity.getAnimal().getScienceName() != null)
				cell.setCellValue(entity.getAnimal().getScienceName());
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getAnimal() != null && entity.getAnimal().getCode() != null)
				cell.setCellValue(entity.getAnimal().getCode());
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getTotal() != null)
				cell.setCellValue(entity.getTotal());
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellStyle(cellStyle);

			index++;
		}

		sheet = workbook.createSheet("ThongTinHo");
		rowIndex = 0;
		cellIndex = 0;
		sheet.createRow(0);
		sheet.createRow(1);
		sheet.createRow(4);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(5);
		cell.setCellValue("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM");
		cell.setCellStyle(cellStyleNoBoder);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, 5, 8));
		row = sheet.getRow(cellIndex + 1);
		cell = row.createCell(5);
		cell.setCellValue("Độc lập - Tự do - Hạnh phúc");
		cell.setCellStyle(cellStyleNoBoder);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex + 1, rowIndex + 1, 5, 8));
		row = sheet.getRow(4);
		cell = row.createCell(0);
		cell.setCellValue(("Cơ sở dữ liệu các trại nuôi" + titleHeader).toUpperCase());
		cell.setCellStyle(cellStyleNoBoder);
		row.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(4, 4, 0, 15));

		rowIndex = 7;
		cellIndex = 0;
		row = sheet.createRow(rowIndex);

		cell = row.createCell(cellIndex);
		cell.setCellValue("STT");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 14 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tỉnh");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 20 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Huyện");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 20 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xã");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 20 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Thôn/Ấp");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 20 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên cơ sở");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Người đại diện");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số (hệ thống)");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã đăng ký cũ");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã đăng ký mới");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Loại hình nuôi");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mục đích nuôi");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 20 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số điện thoại");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Kinh độ");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);

		cellIndex++;
		cell = row.createCell(cellIndex);
		cell.setCellValue("Vĩ độ");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		rowIndex++;
		int dem = 1;
		for (AnimalReportData entity : listFarm) {

			String province = "";
			String district = "";
			String ward = "";
			if (entity.getFarm() != null && entity.getFarm().getAdministrativeUnit() != null) {
				ward = entity.getFarm().getAdministrativeUnit().getName();
				if (entity.getFarm().getAdministrativeUnit().getParent() != null) {
					district = entity.getFarm().getAdministrativeUnit().getParent().getName();
					if (entity.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
						province = entity.getFarm().getAdministrativeUnit().getParent().getParent().getName();
					}
				}
			}
			cellIndex = 0;
			row = sheet.createRow(rowIndex);
			cell = row.createCell(cellIndex);
			cell.setCellValue(dem);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(province);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(district);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(ward);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(entity.getFarm().getVillage() != null ? entity.getFarm().getVillage() : "");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(entity.getFarm().getName() != null ? entity.getFarm().getName() : "");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(entity.getFarm().getOwnerName() != null ? entity.getFarm().getOwnerName() : "");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(entity.getFarm().getCode() != null ? entity.getFarm().getCode() : "");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(
					entity.getFarm().getOldRegistrationCode() != null ? entity.getFarm().getOldRegistrationCode() : "");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(
					entity.getFarm().getNewRegistrationCode() != null ? entity.getFarm().getNewRegistrationCode() : "");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			if (entity.getFarm().getMethodFeed() != null) {
				if (entity.getFarm().getMethodFeed() == 1) {
					cell.setCellValue("Nuôi sinh trưởng");
				} else if (entity.getFarm().getMethodFeed() == 2) {
					cell.setCellValue("Nuôi sinh sản");
				} else if (entity.getFarm().getMethodFeed() == 3) {
					cell.setCellValue("Nuôi khác");
				} else {
					cell.setCellValue("");
				}
			} else {
				cell.setCellValue("");
			}
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			String productTargets = "";
			int count = 1;
			for (FarmProductTarget farmProduct : entity.getFarm().getFarmProductTargets()) {
				if (farmProduct != null && farmProduct.getProductTarget() != null) {
					if (count == 1) {
						count++;
					} else {
						productTargets += ", ";
					}
					productTargets += farmProduct.getProductTarget().getName();
				}
			}
			cell.setCellValue(productTargets);
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(entity.getFarm().getPhoneNumber() != null ? entity.getFarm().getPhoneNumber() : "");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(entity.getFarm().getLongitude() != null ? entity.getFarm().getLongitude() : "");
			cell.setCellStyle(cellStyle);

			cellIndex++;
			cell = row.createCell(cellIndex);
			cell.setCellValue(entity.getFarm().getLatitude() != null ? entity.getFarm().getLatitude() : "");
			cell.setCellStyle(cellStyle);
			dem++;
			rowIndex++;
		}

		workbook.write(out);
	}

	public static void downloadFileExcelForm16A(List<ReportPeriod> list, String titleHeader,
			ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("BieuMau1A");
		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 10);
		font.setFontName("Times New Roman");
		font.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 10);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);
		sheet.createRow(2);

		row = sheet.getRow(0);
		cell = row.createCell(0);
		cell.setCellValue(("Dữ liệu biểu mẫu 16 " + titleHeader).toUpperCase());
		cell.setCellStyle(cellStyleNoBoder);
		row.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 15));

		rowIndex = 1;
		cellIndex = 0;
		sheet.createRow(1);
		sheet.createRow(2);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tỉnh/Thành");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Quận/Huyện");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xã/Phường");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số cơ sở");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên cơ sở");
		sheet.setColumnWidth(cellIndex, 18 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Loài nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên VN");
		sheet.setColumnWidth(cellIndex, 35 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học");
		sheet.setColumnWidth(cellIndex, 35 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã loài");
//		sheet.setColumnWidth(cellIndex, 35*256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng số cá thể");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 3));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);
		cell = row.createCell(cellIndex + 3);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể bố mẹ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn giống hậu bị");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng con dưới 1 tuổi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng cá thể trên 1 tuổi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Nhập cơ sở (mua, sinh sản ..vv)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xuất cơ sở (bán, cho tặng, chết…)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ghi chú");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xác nhận của cơ quan kiểm lâm/ thủy sản");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã đơn vị hành chính cấp xã");
//		sheet.setColumnWidth(cellIndex, 14*256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
//		sheet.setColumnWidth(cellIndex, 14*256);
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tình trạng đăng ký");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số đăng ký");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày đăng ký");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellValue("Kinh độ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellValue("Vĩ độ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellStyle(cellStyleBoldTable);

		int rowStartTable = 3;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (list != null && list.size() > 0) {
			for (ReportPeriod reportPeriodDto : list) {
				if (reportPeriodDto.getReportItems() != null && reportPeriodDto.getReportItems().size() > 0) {
					for (ReportForm16 reportForm16dto : reportPeriodDto.getReportItems()) {
						row = sheet.createRow(rowStartTable);
						cellIndex = -1;

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(
								reportPeriodDto.getProvince().getName());

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(reportPeriodDto.getDistrict().getName());

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(reportPeriodDto.getAdministrativeUnit().getName());

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(reportPeriodDto.getFarm().getCode());

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(reportPeriodDto.getFarm().getName());

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						String dateStr = "";
						if (reportForm16dto.getDateReport() != null) {
							try {
								dateStr = formatter.format(reportForm16dto.getDateReport());
							} catch (Exception e) {
								dateStr = "";
							}
							cell.setCellValue(dateStr);
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getAnimal() != null && reportForm16dto.getAnimal().getName() != null) {
							cell.setCellValue(reportForm16dto.getAnimal().getName());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getAnimal() != null
								&& reportForm16dto.getAnimal().getScienceName() != null) {
							cell.setCellValue(reportForm16dto.getAnimal().getScienceName());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getAnimal() != null && reportForm16dto.getAnimal().getCode() != null) {
							cell.setCellValue(reportForm16dto.getAnimal().getCode());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getTotal() != null) {
							cell.setCellValue(reportForm16dto.getTotal());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getMale() != null) {
							cell.setCellValue(reportForm16dto.getMale());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getFemale() != null) {
							cell.setCellValue(reportForm16dto.getFemale());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getUnGender() != null) {
							cell.setCellValue(reportForm16dto.getUnGender());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getMaleParent() != null) {
							cell.setCellValue(reportForm16dto.getMaleParent());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getFemaleParent() != null) {
							cell.setCellValue(reportForm16dto.getFemaleParent());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getMaleGilts() != null) {
							cell.setCellValue(reportForm16dto.getMaleGilts());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getFemaleGilts() != null) {
							cell.setCellValue(reportForm16dto.getFemaleGilts());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getChildUnder1YearOld() != null) {
							cell.setCellValue(reportForm16dto.getChildUnder1YearOld());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getMaleChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16dto.getMaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getFemaleChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16dto.getFemaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getUnGenderChildOver1YearOld() != null) {
							cell.setCellValue(reportForm16dto.getUnGenderChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getMaleImport() != null) {
							cell.setCellValue(reportForm16dto.getMaleImport());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getFemaleImport() != null) {
							cell.setCellValue(reportForm16dto.getFemaleImport());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getUnGenderImport() != null) {
							cell.setCellValue(reportForm16dto.getUnGenderImport());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getMaleExport() != null) {
							cell.setCellValue(reportForm16dto.getMaleExport());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getFemaleExport() != null) {
							cell.setCellValue(reportForm16dto.getFemaleExport());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getUnGenderExport() != null) {
							cell.setCellValue(reportForm16dto.getUnGenderExport());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getNote() != null) {
							cell.setCellValue(reportForm16dto.getNote());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportForm16dto.getConfirmForestProtection() != null) {
							cell.setCellValue(reportForm16dto.getConfirmForestProtection());
						} else {
							cell.setCellValue("");
						}

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(reportPeriodDto.getFarm().getAdministrativeUnit().getCode());

						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						String hasReg = "";
						if (reportPeriodDto.getFarm() != null && reportPeriodDto.getFarm().getStatus() != null
								&& reportPeriodDto.getFarm().getStatus() == 3) {
							hasReg = "Đã đăng ký";
						} else if (reportPeriodDto.getFarm() != null && reportPeriodDto.getFarm().getStatus() != null
								&& reportPeriodDto.getFarm().getStatus() == 2) {
							hasReg = "Chưa đăng ký";
						}
						cell.setCellValue(hasReg);

						// Mã số đăng ký
						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						cell.setCellValue(reportPeriodDto.getFarm().getNewRegistrationCode());

						// Năm đăng ký
						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportPeriodDto.getFarm().getIssuingCodeDate() != null)
							cell.setCellValue(reportPeriodDto.getFarm().getIssuingCodeDate());
						rowStartTable++;
						// Kinh độ
						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportPeriodDto.getFarm() != null && reportPeriodDto.getFarm().getLongitude() != null) {
							cell.setCellValue(reportPeriodDto.getFarm().getLongitude());
						} else {
							cell.setCellValue("");
						}
						// Vĩ độ
						cellIndex++;
						cell = row.createCell(cellIndex);
						cell.setCellStyle(cellStyle);
						if (reportPeriodDto.getFarm() != null && reportPeriodDto.getFarm().getLatitude() != null) {
							cell.setCellValue(reportPeriodDto.getFarm().getLatitude());
						} else {
							cell.setCellValue("");
						}
						// Map Code
//						cellIndex++;
//						cell = row.createCell(cellIndex);
//						cell.setCellStyle(cellStyle);
//						if(reportPeriodDto.getFarm() != null && reportPeriodDto.getFarm().getLatitude() != null) {
//							cell.setCellValue(reportPeriodDto.getFarm().getMapCode());
//						}else {
//							cell.setCellValue("");
//						}
					}
				}
			}
		}

		workbook.write(outputStream);
	}

	public static List<DataDvhdDto> getForm16AFromExcelFile(InputStream fis, int sheetIndex, int rowStart)
			throws IOException {
		List<DataDvhdDto> results = new ArrayList<DataDvhdDto>();
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);

		int rowIndex = 2;
//		int cellIndex = 0;
		Row row = null;
		Cell cell = null;
		int lastRow = sheet.getLastRowNum();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		DataFormatter dataFormatter = new DataFormatter();
//		String val = dataFormatter.formatCellValue(sheet.getRow(row).getCell(col));

		while (rowIndex <= lastRow) {
			DataDvhdDto rawData = new DataDvhdDto();
//			cellIndex = 0;
			row = sheet.getRow(rowIndex);
			if (row == null) {
				break;
			}
//			System.out.println(rowIndex);
			cell = row.getCell(0);// Tên Tỉnh
			if (cell != null && cell.getStringCellValue() != null)
				rawData.setPronvinceName(cell.getStringCellValue());

			// cellIndex++;
			cell = row.getCell(1);// Tên Huyện
			if (cell != null && cell.getStringCellValue() != null)
				rawData.setDistrictName(cell.getStringCellValue());

			// cellIndex++;
			cell = row.getCell(2);// Tên Xã
			try {
				rawData.setWardName(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setWardName("");
			}

			cell = row.getCell(3);// Ấp/Thôn
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setVillage(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setVillage(cell.getStringCellValue());
				}
			}

			// cellIndex++;
			// cellIndex++;
			cell = row.getCell(4);// Mã cơ sở
			try {
				rawData.setFarmCode(cell.getStringCellValue());
			} catch (Exception e) {
				try {
					String farmCode = (new BigDecimal(cell.getNumericCellValue())).toPlainString();
					rawData.setFarmCode(farmCode);
				} catch (Exception e2) {
					rawData.setFarmCode("");
				}
			}

			// cellIndex++;
			cell = row.getCell(5);// Tên cơ sở
			try {
				rawData.setFarmName(cell.getStringCellValue());
			} catch (Exception e) {
				try {
					String farmName = (new BigDecimal(cell.getNumericCellValue())).toPlainString();
					rawData.setFarmName(farmName);
				} catch (Exception e2) {
					rawData.setFarmName("");
				}
			}
			
			// cellIndex++;
			cell = row.getCell(6);// Ngày tháng/năm cập nhật
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC && cell.getDateCellValue() != null) {
				rawData.setDateUpdate(cell.getDateCellValue());
			} else {
				if (cell != null && cell.getStringCellValue() != null) {
					try {
						rawData.setDateUpdate(formatter.parse(cell.getStringCellValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.out.println("Lỗi Ngày tháng/năm cập nhật tại dòng :" + rowIndex);
					}
				}
			}

			// cellIndex++;
			cell = row.getCell(7);// Tên loài nuôi
			try {
				rawData.setAnimalName(cell.getStringCellValue());
			} catch (Exception e) {
				try {
					rawData.setAnimalName(cell.getNumericCellValue() + "");
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			// cellIndex++;
			cell = row.getCell(8);// Tên Khoa học
			try {
				rawData.setAnimalScienceName(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setAnimalScienceName("");
			}

			// cellIndex++;
			cell = row.getCell(9);// Mã Loài
			try {
				rawData.setAnimalCode(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setAnimalCode("");
			}

//			//cellIndex++;
//			cell = row.getCell(9);//Số lượng 2016
//			try {
//				rawData.setQuantity2015(cell.getNumericCellValue());
//			} catch (Exception e) {
//				rawData.setQuantity2015(0d);
//			}
//			//cellIndex++;
//			cell = row.getCell(10);// Số lượng 2018
//			try {
//				rawData.setQuantity2017(cell.getNumericCellValue());
//			} catch (Exception e) {
//				rawData.setQuantity2017(0d);
//			}

			// cellIndex++;
			cell = row.getCell(11);// Đực bố mẹ
			try {
				rawData.setMaleParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setMaleParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(12);// Cái bố mẹ
			try {
				rawData.setFemaleParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setFemaleParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(13);// Đực hậu bị
			try {
				rawData.setMaleGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setMaleGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(14);// Cái hậu bị
			try {
				rawData.setFemaleGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setFemaleGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(15);// Số con dưới 1 tuổi
			try {
				rawData.setChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(16);// Số con trên 1 tuổi đực
			try {
				rawData.setMaleChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setMaleChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(17);// Số con trên 1 tuổi cái
			try {
				rawData.setFemaleChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setFemaleChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(18);// Số con trên 1 tuổi không xác định
			try {
				rawData.setUnGenderChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setUnGenderChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(19);// Tình trạng đăng ký
			if(cell!=null) {
				try {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setRegisterStatus(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setRegisterStatus(cell.getStringCellValue());
					}
				} catch (Exception e) {
					rawData.setRegisterStatus("");
				}
			}
			
			// cellIndex++;
			cell = row.getCell(20);// mã số cs(theo 06/2019)
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					DecimalFormat df = new DecimalFormat("#");
					String d = df.format(cell.getNumericCellValue());
					rawData.setRegisterCode(d);
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					try {
						rawData.setRegisterCode(cell.getStringCellValue());
					} catch (Exception e) {
						rawData.setRegisterCode("");
					}
				}
			}

			// cellIndex++;
			cell = row.getCell(21);// Ngày cấp mã số đk theo 06
//			try {
//				rawData.setRegisterYear(new Double(cell.getNumericCellValue()).intValue());
//			} catch (Exception e) {
//				rawData.setRegisterYear(0);
//			}
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC && cell.getDateCellValue() != null) {
				rawData.setDateRegistration(cell.getDateCellValue());
			} else {
				if (cell != null && cell.getStringCellValue() != null && cell.getStringCellValue() != "") {
					try {
						rawData.setDateRegistration(formatter.parse(cell.getStringCellValue()));
					} catch (ParseException e) {

						// TODO Auto-generated catch block
//						e.printStackTrace();
						System.out.println("Lỗi Ngày cấp mã đăng ký theo 06 tại dòng :" + rowIndex);
						System.out.println(cell.getStringCellValue());
					}
				}
			}
			// cellIndex++;
			cell = row.getCell(22);//Mã khác/ số giấy đăng ký
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setOldRegistrationCode(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOldRegistrationCode(cell.getStringCellValue());
				}
			}
			//Ngày cấp
			cell = row.getCell(23);
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC && cell.getDateCellValue() != null) {
				rawData.setDateOtherRegistration(cell.getDateCellValue());
			} else {
				if (cell != null && cell.getStringCellValue() != null && cell.getStringCellValue() != "") {
					try {
						rawData.setDateOtherRegistration(formatter.parse(cell.getStringCellValue()));
					} catch (ParseException e) {
						System.out.println("Lỗi Ngày cấp giấy cndk(chứng nhận đăng kí) tại dòng :" + rowIndex);
						System.out.println(cell.getStringCellValue());
					}
				}
			}
			//Số đăng ký kinh doanh
			cell = row.getCell(24);
			if(cell!=null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					DecimalFormat df = new DecimalFormat("#");
					String d = df.format(cell.getNumericCellValue());
					rawData.setBusinessRegistrationNumber(d);
					//rawData.setOwnerPhoneNumber(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setBusinessRegistrationNumber(cell.getStringCellValue());
				}
			}
		
			// cellIndex++;
			cell = row.getCell(25);// Mã mục đích gây nuôi
			try {
				rawData.setProductTargetCode(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setProductTargetCode("");
			}

			// cellIndex++;
			cell = row.getCell(26);// Mã nguồn gốc
			try {
				rawData.setOriginalCode(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setOriginalCode("");
			}

//			Số điện thoại
			cell = row.getCell(27);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					DecimalFormat df = new DecimalFormat("#");
					String d = df.format(cell.getNumericCellValue());
					rawData.setPhoneNumber(d);
					//rawData.setPhoneNumber(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setPhoneNumber(cell.getStringCellValue());
				}
			}

//			Kinh độ
			cell = row.getCell(29);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setLongitude(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setLongitude(cell.getStringCellValue());
				}
			}

			// Vĩ độ
			cell = row.getCell(30);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setLatitude(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setLatitude(cell.getStringCellValue());
				}
			}
			// người đại diện
			cell = row.getCell(31);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setOwnerName(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOwnerName(cell.getStringCellValue());
				}
			}
//			CMND/ căn cước
			cell = row.getCell(33);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					DecimalFormat df = new DecimalFormat("#");
					String d = df.format(cell.getNumericCellValue());
					rawData.setOwnerCitizenCode(d);
					//rawData.setOwnerCitizenCode(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOwnerCitizenCode(cell.getStringCellValue());
				}
			}
//			Điện thoại chủ cơ sở
			cell = row.getCell(34);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					DecimalFormat df = new DecimalFormat("#");
					String d = df.format(cell.getNumericCellValue());
					rawData.setOwnerPhoneNumber(d);
					//rawData.setOwnerPhoneNumber(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOwnerPhoneNumber(cell.getStringCellValue());
				}
			}
			// cellIndex++;
			cell = row.getCell(35);// Mã wardCode
			try {
				rawData.setWardCode(cell.getStringCellValue());
			} catch (Exception e) {
				try {
					rawData.setWardCode(new Double(cell.getNumericCellValue()).longValue() + "");
				} catch (Exception e2) {
					rawData.setWardCode("");
				}
			}
			
			
			//cell = row.getCell(36);// Map code - FarmID
			
			results.add(rawData);
			rowIndex++;
		}

		return results;
	}

	// tran huu dat them ham moi import du lieu start
	public static List<DataDvhdDto> getForm16AFromExcelFileUpdate(InputStream fis, int sheetIndex, int rowStart)
			throws IOException {
		List<DataDvhdDto> results = new ArrayList<DataDvhdDto>();
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);

		int rowIndex = 2;
		Row row = null;
		Cell cell = null;
		int lastRow = sheet.getLastRowNum();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		DataFormatter dataFormatter = new DataFormatter();

		while (rowIndex <= lastRow) {
			DataDvhdDto rawData = new DataDvhdDto();
//			cellIndex = 0;
			row = sheet.getRow(rowIndex);
			if (row == null) {
				break;
			}
			cell = row.getCell(1);// Tên Tỉnh
			if (cell != null && cell.getStringCellValue() != null)
				rawData.setPronvinceName(cell.getStringCellValue());

			// cellIndex++;
			cell = row.getCell(2);// Tên Huyện
			if (cell != null && cell.getStringCellValue() != null)
				rawData.setDistrictName(cell.getStringCellValue());

			// cellIndex++;
			cell = row.getCell(3);// Tên Xã
			try {
				rawData.setWardName(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setWardName("");
			}

			cell = row.getCell(4);// Ấp/Thôn
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setVillage(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setVillage(cell.getStringCellValue());
				}
			}

			// so nha
			cell = row.getCell(5);
//			try {
//				rawData.setApartmentNumber(new Double(cell.getNumericCellValue()).intValue());
//			} catch (Exception e) {
//				try {
//					Integer value = Integer.valueOf(cell.getStringCellValue());
//					rawData.setApartmentNumber(value);
//				} catch (Exception e2) {
//					// TODO: handle exception
//				}
//			}
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setAdressDetail(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setAdressDetail(cell.getStringCellValue());
				}
			}
			
			cell = row.getCell(6);// Mã wardCode//Mã xã (Theo danh mục ĐVHC của Quyết định 124/2004/QĐ-TTg)
			try {
				rawData.setWardCode(cell.getStringCellValue());
			} catch (Exception e) {
				try {
					rawData.setWardCode(new Double(cell.getNumericCellValue()).longValue() + "");
				} catch (Exception e2) {
					rawData.setWardCode("");
				}
			}

			// cellIndex++;
			cell = row.getCell(7);// Mã cơ sở
			try {
				rawData.setFarmCode(cell.getStringCellValue());
			} catch (Exception e) {
				try {
					String farmCode = (new BigDecimal(cell.getNumericCellValue())).toPlainString();
					rawData.setFarmCode(farmCode);
				} catch (Exception e2) {
					rawData.setFarmCode("");
				}
			}

			// cellIndex++;
			cell = row.getCell(8);// Tên cơ sở
			try {
				rawData.setFarmName(cell.getStringCellValue());
			} catch (Exception e) {
				try {
					String farmName = (new BigDecimal(cell.getNumericCellValue())).toPlainString();
					rawData.setFarmName(farmName);
				} catch (Exception e2) {
					rawData.setFarmName("");
				}
			}

			// cellIndex++;
			cell = row.getCell(9);// Ngày tháng/năm cập nhật
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC && cell.getDateCellValue() != null) {
				rawData.setDateUpdate(cell.getDateCellValue());
			} else {
				if (cell != null && cell.getStringCellValue() != null && cell.getStringCellValue() != "") {
					try {
						rawData.setDateUpdate(formatter.parse(cell.getStringCellValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
					}
				}
			}

			// cellIndex++;
			cell = row.getCell(10);// Tên loài nuôi
			try {
				rawData.setAnimalName(cell.getStringCellValue());
			} catch (Exception e) {
				try {
					rawData.setAnimalName(cell.getNumericCellValue() + "");
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			// cellIndex++;
			cell = row.getCell(11);// Tên Khoa học
			try {
				rawData.setAnimalScienceName(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setAnimalScienceName("");
			}

			// cellIndex++;
			cell = row.getCell(12);// Mã Loài
			try {
				rawData.setAnimalCode(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setAnimalCode("");
			}

			cell = row.getCell(13);// Toongr so ca the
			try {
				rawData.setTotal(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setTotal(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(14);// Đực bố mẹ
			try {
				rawData.setMaleParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setMaleParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(15);// Cái bố mẹ
			try {
				rawData.setFemaleParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setFemaleParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(16);// không xác định bố mẹ
			try {
				rawData.setUnGenderParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setUnGenderParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			cell = row.getCell(17);// Đực hậu bị
			try {
				rawData.setMaleGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setMaleGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(18);// Cái hậu bị
			try {
				rawData.setFemaleGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setFemaleGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(19);// không rõ hậu bị
			try {
				rawData.setUnGenderGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setUnGenderGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			cell = row.getCell(20);// con dưới 1 tuổi đực
			try {
				rawData.setMaleChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setMaleChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(21);// con dưới 1 tuổi cái
			try {
				rawData.setFemaleChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setFemaleChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			// cellIndex++;
			cell = row.getCell(22);// Số con dưới 1 tuổi không rõ
			try {
				rawData.setChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			// cellIndex++;
			cell = row.getCell(23);// Số con trên 1 tuổi đực
			try {
				rawData.setMaleChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setMaleChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(24);// Số con trên 1 tuổi cái
			try {
				rawData.setFemaleChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setFemaleChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			// cellIndex++;
			cell = row.getCell(25);// Số con trên 1 tuổi không xác định
			try {
				rawData.setUnGenderChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setUnGenderChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(26);// tổng nhập
			try {
				rawData.setTotalImport(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setTotalImport(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(27);// khai bao bo me duc
			try {
				rawData.setImportMaleParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportMaleParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			cell = row.getCell(28);// khai bao bo me cai
			try {
				rawData.setImportFemaleParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportFemaleParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(29);// khai bao bo me không xác định
			try {
				rawData.setImportUnGenderParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportUnGenderParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			cell = row.getCell(30);// khai bao hau bi duc
			try {
				rawData.setImportMaleGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportMaleGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(31);// khai bao hau bi cai
			try {
				rawData.setImportFemaleGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportFemaleGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(32);// khai bao hau bi không rõ
			try {
				rawData.setImportUnGenderGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportUnGenderGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(33);// con dưới 1 tuổi đực nhập
			try {
				rawData.setImportMaleChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportMaleChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(34);// con dưới 1 tuổi cái nhập
			try {
				rawData.setImportFemaleChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportFemaleChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			cell = row.getCell(35);// con dưới 1 tuổi không rõ giới tính nhập
			try {
				rawData.setImportChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(36);// khai bao ca the tren 1 tuoi duc
			try {
				rawData.setImportMaleChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportMaleChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(37);// khai bao ca the tren 1 tuoi cai
			try {
				rawData.setImportFemaleChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportFemaleChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(38);// khai bao ca the tren 1 tuoi khong xac dinh
			try {
				rawData.setImportUnGenderChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setImportUnGenderChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(39);// khai bao li do
			try {
				rawData.setImportReason(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setImportReason("");
			}
			// xuat ra
			cell = row.getCell(40);// tổng xuất
			try {
				rawData.setTotalExport(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setTotalExport(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(41);// khai bao bo me duc
			try {
				rawData.setExportMaleParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportMaleParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			cell = row.getCell(42);// khai bao bo me cai
			try {
				rawData.setExportFemaleParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportFemaleParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(43);// khai bao bo me không rõ
			try {
				rawData.setExportUnGenderParent(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportUnGenderParent(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			cell = row.getCell(44);// khai bao hau bi duc
			try {
				rawData.setExportMaleGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportMaleGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(45);// khai bao hau bi cai
			try {
				rawData.setExportFemaleGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportFemaleGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(46);//  hậu bị không rõ   xuất  
			try {
				rawData.setExportUnGenderGilts(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportUnGenderGilts(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(47);// con dưới 1 tuổi đực xuất
			try {
				rawData.setExportMaleChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportMaleChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}
			
			cell = row.getCell(48);// con dưới 1 tuổi cái xuất
			try {
				rawData.setExportFemaleChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportFemaleChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			cell = row.getCell(49);// khai bao ca the duoi 1 tuoi không rõ
			try {
				rawData.setExportChildUnder1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportChildUnder1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}
			}

			cell = row.getCell(50);// khai bao ca the tren 1 tuoi duc
			try {
				rawData.setExportMaleChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportMaleChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(51);// khai bao ca the tren 1 tuoi cai
			try {
				rawData.setExportFemaleChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportFemaleChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}

			cell = row.getCell(52);// khai bao ca the tren 1 tuoi khong xac dinh
			try {
				rawData.setExportUnGenderChildOver1YearOld(new Double(cell.getNumericCellValue()).intValue());
			} catch (Exception e) {
				try {
					Integer value = Integer.valueOf(cell.getStringCellValue());
					rawData.setExportUnGenderChildOver1YearOld(value);
				} catch (Exception e2) {
					// TODO: handle exception
				}

			}
			cell = row.getCell(53);// khai bao li do
			if (cell != null) {
				try {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setExportReason(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setExportReason(cell.getStringCellValue());
					}
				} catch (Exception e) {
					rawData.setExportReason("");
				}

			}

			// cellIndex++;
			cell = row.getCell(54);// Tình trạng đăng ký
			if (cell != null) {
				try {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						rawData.setRegisterStatus(String.valueOf(cell.getNumericCellValue()));
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setRegisterStatus(cell.getStringCellValue());
					}
				} catch (Exception e) {
					rawData.setRegisterStatus("");
				}

			}

			// cellIndex++;
			cell = row.getCell(55);// mã theo co so 06
			if (cell != null) {
				try {
					if (cell.getCellTypeEnum() == CellType.NUMERIC) {
						DecimalFormat df = new DecimalFormat("#");
						String d = df.format(cell.getNumericCellValue());
						rawData.setRegisterCode(d);
					} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
						rawData.setRegisterCode(cell.getStringCellValue());
					}
				} catch (Exception e) {
					rawData.setRegisterCode("");
				}

			}
			// cellIndex++;
			cell = row.getCell(56);// ngày cấp mã đk theo 06
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC && cell.getDateCellValue() != null) {
				rawData.setDateRegistration(cell.getDateCellValue());
			} else {
				if (cell != null && cell.getStringCellValue() != null && cell.getStringCellValue() != "") {
					try {
						rawData.setDateRegistration(formatter.parse(cell.getStringCellValue()));
					} catch (ParseException e) {

						// TODO Auto-generated catch block
//									e.printStackTrace();
						System.out.println("Lỗi Ngày cấp giấy cndk(chứng nhận đăng kí) tại dòng :" + rowIndex);
						System.out.println(cell.getStringCellValue());
					}
				}
			}

			// cellIndex++;
			cell = row.getCell(57);// Mã Số giấy CN đăng ký(mã khác)
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					DecimalFormat df = new DecimalFormat("#");
					String d = df.format(cell.getNumericCellValue());
					rawData.setOldRegistrationCode(d);
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOldRegistrationCode(cell.getStringCellValue());
				}
			}

			// Ngày cấp giấy CN đk(ngày cấp mã khác) //
			cell = row.getCell(58);
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC && cell.getDateCellValue() != null) {
				rawData.setDateOtherRegistration(cell.getDateCellValue());
			} else {
				if (cell != null && cell.getStringCellValue() != null && cell.getStringCellValue() != "") {
					try {
						rawData.setDateOtherRegistration(formatter.parse(cell.getStringCellValue()));
					} catch (ParseException e) {
						System.out.println("Lỗi Ngày cấp giấy CN dk tại dòng :" + rowIndex);
						System.out.println(cell.getStringCellValue());
					}
				}
			}
			
			// so gcndkkd
			cell = row.getCell(59);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					DecimalFormat df = new DecimalFormat("#");
					String d = df.format(cell.getNumericCellValue());
					rawData.setBusinessRegistrationNumber(d);
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setBusinessRegistrationNumber(cell.getStringCellValue());
				}

			} else {
				rawData.setBusinessRegistrationNumber("");
			}
			
			// cellIndex++;
			cell = row.getCell(60);// Mã mục đích gây nuôi
			try {
				rawData.setProductTargetCode(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setProductTargetCode("");
			}
			
			// Hình thức nuôi
			cell = row.getCell(61);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setHusbandryMethods(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
							rawData.setHusbandryMethods(cell.getStringCellValue());
				}
			}else {
				rawData.setHusbandryMethods("");
			}

			// cellIndex++;
			cell = row.getCell(62);// Mã nguồn gốc
			try {
				rawData.setOriginalCode(cell.getStringCellValue());
			} catch (Exception e) {
				rawData.setOriginalCode("");
			}

//			Số điện thoại
			cell = row.getCell(63);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setPhoneNumber(new BigDecimal(cell.getNumericCellValue()).toPlainString());
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setPhoneNumber(cell.getStringCellValue());
				}
			}

//			Kinh độ
			cell = row.getCell(64);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setLongitude(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setLongitude(cell.getStringCellValue());
				}
			}

			// Vĩ độ
			cell = row.getCell(65);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setLatitude(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setLatitude(cell.getStringCellValue());
				}
			}

			// dien tich truong trai
			cell = row.getCell(66);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setLodgingAcreage(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setLodgingAcreage(cell.getStringCellValue());
				}
			}

			// ngay bat dau nuoi
			cell = row.getCell(67);
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC && cell.getDateCellValue() != null) {
				rawData.setStartDate(cell.getDateCellValue());
			} else {
				if (cell != null && cell.getStringCellValue() != null && cell.getStringCellValue() != "") {
					try {
						rawData.setStartDate(formatter.parse(cell.getStringCellValue()));
					} catch (ParseException e) {

						// TODO Auto-generated catch block
//						e.printStackTrace();
						System.out.println("Lỗi Ngày bắt đầu nuôi tại dòng :" + rowIndex);
						System.out.println(cell.getStringCellValue());
					}
				}
			}

			// người đại diện
			cell = row.getCell(68);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setOwnerName(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOwnerName(cell.getStringCellValue());
				}
			}

			// nam sinh
			cell = row.getCell(69);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setOwnerYearOfBirth(String.valueOf ((int)cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOwnerYearOfBirth(cell.getStringCellValue());
				}
			}

//			CMND/ căn cước
			cell = row.getCell(70);

			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					// rawData.setOwnerCitizenCode(String.valueOf(cell.getNumericCellValue()));
					rawData.setOwnerCitizenCode(new BigDecimal(cell.getNumericCellValue()).toPlainString());
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOwnerCitizenCode(cell.getStringCellValue());
				}
			}

			// ngày cấp cmnd/căn cước
			cell = row.getCell(71);
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC && cell.getDateCellValue() != null) {
				rawData.setOwnerCitizenDate(cell.getDateCellValue());
			} else {
				if (cell != null && cell.getStringCellValue() != null && cell.getStringCellValue() != "") {
					try {
						rawData.setOwnerCitizenDate(formatter.parse(cell.getStringCellValue()));
					} catch (ParseException e) {
						// TODO Auto-generated catch block
//						e.printStackTrace();						
						System.out.println("Lỗi ngày cấp cmnd/căn cước tại dòng :" + rowIndex);
						System.out.println(cell.getStringCellValue());
					}
				}
			}

			// noi cap
			cell = row.getCell(72);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setOwnerCitizenLocation(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOwnerCitizenLocation(cell.getStringCellValue());
				}
			} else {
				rawData.setOwnerCitizenLocation("");
			}

//			Điện thoại chủ cơ sở
			cell = row.getCell(73);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					//rawData.setOwnerPhoneNumber(String.valueOf(cell.getNumericCellValue()));
					rawData.setOwnerPhoneNumber(new BigDecimal(cell.getNumericCellValue()).toPlainString());
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setOwnerPhoneNumber(cell.getStringCellValue());
				}
			}
			// ttbvmt
			cell = row.getCell(74);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setTtbvmt(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setTtbvmt(cell.getStringCellValue());
				}
			} else {
				rawData.setTtbvmt("");
			}

			// ngay cap ttbvmt
			cell = row.getCell(75);
			if (cell != null && cell.getCellTypeEnum() == CellType.NUMERIC && cell.getDateCellValue() != null) {
				rawData.setTtbvmtDate(cell.getDateCellValue());
			} else {
				if (cell != null && cell.getStringCellValue() != null && cell.getStringCellValue() != "") {
					try {
						rawData.setTtbvmtDate(formatter.parse(cell.getStringCellValue()));
					} catch (ParseException e) {
						System.out.println("Lỗi Ngày cấp ttbvmt tại dòng :" + rowIndex);
						System.out.println(cell.getStringCellValue());
					}
				}
			}

			// chu thich
			cell = row.getCell(76);
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setNote(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setNote(cell.getStringCellValue());
				}
			} else {
				rawData.setNote("");
			}

			results.add(rawData);
			rowIndex++;
		}

		return results;
	}
	// tran huu dat them ham moi import du lieu end

	public static void downloadFileImportExcelForm16A(List<ReportForm16Dto> list, String titleHeader,
			ServletOutputStream outputStream) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DuLieuImport");
		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBold(true);

		Font font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 12);
		font1.setFontName("Times New Roman");

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFont(font1);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 13);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		CellStyle cellHidden = workbook.createCellStyle();
		cellHidden.setBorderBottom(BorderStyle.THIN);
		cellHidden.setBorderLeft(BorderStyle.THIN);
		cellHidden.setBorderTop(BorderStyle.THIN);
		cellHidden.setBorderRight(BorderStyle.THIN);
		cellHidden.setWrapText(true);
		cellHidden.setAlignment(HorizontalAlignment.CENTER);
		cellHidden.setVerticalAlignment(VerticalAlignment.CENTER);
		cellHidden.setFont(font);
		cellHidden.setHidden(true);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);
		sheet.createRow(2);

		row = sheet.getRow(2);
		cell = row.createCell(0);
//		cell.setCellValue(("Dữ liệu Import Excel biểu mẫu 16A " + titleHeader).toUpperCase());
//		cell.setCellStyle(cellStyleNoBoder);
//		row.setHeightInPoints(20);
//		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 15));

		rowIndex = 0;
		cellIndex = 0;
		sheet.createRow(1);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("STT");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 8 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tỉnh");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("1");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Huyện");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("2");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xã");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("3");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ấp/Thôn");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("4");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số nhà");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("5");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã xã (Theo danh mục ĐVHC của Quyết định 124/2004/QĐ-TTg)");
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("6");
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số hệ thống");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("7");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên cơ sở");
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("8");
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("9");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên loài nuôi");
		sheet.setColumnWidth(cellIndex, 35 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("10");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học (tên latin)");
		sheet.setColumnWidth(cellIndex, 35 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("11");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã loài");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("12");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng số cá thể");
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("13");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn bố mẹ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);


		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn giống hậu bị");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng con non (dưới 1 tuổi hoặc chưa trưởng thành)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

//		row = sheet.getRow(rowIndex + 1);
//		cell = row.createCell(cellIndex);
//		sheet.setColumnWidth(cellIndex, 14 * 256);
//		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng cá thể trên 1 tuổi (không làm giống)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		// tran huu dat them cell danh sach nhap xuat cua form 16 start
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 8));
		cell.setCellValue("Khai báo tăng đàn");
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 3);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 4);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 5);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 6);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 7);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 8);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 9);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 10);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 11);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 12);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 13);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng tăng");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Bố mẹ đực");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Bố mẹ cái");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Bố mẹ không xác định");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Hậu bị đực");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Hậu bị cái");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Hậu bị không xác định");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể dưới 1 tuổi đực");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể dưới 1 tuổi cái");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể dưới 1 tuổi không xác định");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể trên 1 tuổi đực");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể trên 1 tuổi cái");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể trên 1 tuổi không xác định");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Lý do tăng đàn");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 8));
		cell.setCellValue("Khai báo giảm đàn");
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 3);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 4);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 5);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 6);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 7);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 8);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 9);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 10);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 11);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 12);
		cell.setCellStyle(cellStyleBoldTable);
		
		cell = row.createCell(cellIndex + 13);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng giảm");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Bố mẹ đực");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Bố mẹ cái");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Bố mẹ không xác định");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Hậu bị đực");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Hậu bị cái");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Hậu bị không xác định");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể dưới 1 tuổi đực");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể dưới 1 tuổi cái");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể dưới 1 tuổi không xác định");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể trên 1 tuổi đực");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể trên 1 tuổi cái");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể trên 1 tuổi không xác định");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Lý do giảm đàn");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		// tran huu dat them cell danh sach nhap xuat cua form 16 end

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tình trạng đăng ký (Chưa ĐK=2, Đã ĐK=1)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số CS(theo 06/2019)");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày cấp mã(06/2019)");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số khác/ số giấy đăng ký");
		sheet.setColumnWidth(cellIndex, 15 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("Ngày cấp");
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("Giấy đăng ký kinh doanh");
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mục đích gây nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Hình thức nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Nguồn gốc");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số điện thoại của cơ sở");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellValue("Kinh độ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellValue("Vĩ độ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("Diện tích chuồng trại");
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("Ngày bắt đầu nuôi");
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Chủ cơ sở/ Đại diện/ Chủ lâm sản");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 5));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 3);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 4);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 5);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Họ và tên");
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Năm sinh");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("CMND/Căn cước");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày cấp CMND/Căn cước");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Nơi Cấp CMND/Căn cước");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Điện thoại");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

//		cellIndex++;
//		row = sheet.getRow(rowIndex);
//		cell = row.createCell(cellIndex);
//		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
//		cell.setCellValue("Farm ID");
//		cell.setCellStyle(cellStyleBoldTable);
//		sheet.setColumnHidden(57, true);

//		row = sheet.getRow(rowIndex + 1);
//		cell = row.createCell(cellIndex);
//		sheet.setColumnWidth(cellIndex, 35 * 256);
//		cell.setCellStyle(cellHidden);

		// tran huu dat them cell các thông tin của cơ sở nuôi còn lại start

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("TT BVMT");
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("Ngày Cấp TTBVMT");
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("Chú thích");
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("Farm ID");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(76, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 0 * 256);
		cell.setCellStyle(cellHidden);
		

		// tran huu dat them cell các thông tin của cơ sở nuôi còn lại end

		int rowStartTable = 2;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		if (list != null && list.size() > 0) {
			String previousFarmCode = "";
			String previousAnimalCode = "";
			for (ReportForm16Dto reportForm16Item : list) {
				if (reportForm16Item != null) {

					row = sheet.createRow(rowStartTable);
					cellIndex = -1;
//					cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
//					cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//					if (previousFarmCode.equals(reportForm16Item.getFarm().getCode())
//							&& previousAnimalCode.equals(reportForm16Item.getAnimal().getCode())) {
//						XSSFColor duplicateColor = new XSSFColor(java.awt.Color.YELLOW); // Yellow
//						cellStyle.setFillBackgroundColor(duplicateColor);
//						cellStyle.setFillForegroundColor(duplicateColor);
//						cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//					}
					// stt
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(rowStartTable - 1);

					// Tỉnh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getProvinceDto() != null
							&& reportForm16Item.getProvinceDto().getName() != null) {
						cell.setCellValue(reportForm16Item.getProvinceDto().getName());
					} else
						cell.setCellValue("");

					// Huyện
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getDistrictDto() != null
							&& reportForm16Item.getDistrictDto().getName() != null) {
						cell.setCellValue(reportForm16Item.getDistrictDto().getName());
					} else
						cell.setCellValue("");

					// Xã
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAdministrativeUnitDto() != null
							&& reportForm16Item.getAdministrativeUnitDto().getName() != null) {
						cell.setCellValue(reportForm16Item.getAdministrativeUnitDto().getName());
					} else
						cell.setCellValue("");

					// Thôn/Ấp
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getVillage() != null)
						cell.setCellValue(reportForm16Item.getFarm().getVillage());
					else
						cell.setCellValue("");

					// Số nhà
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getApartmentNumber() != null)
						cell.setCellValue(reportForm16Item.getFarm().getApartmentNumber());
					else
						cell.setCellValue("");
					// Mã xã
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getAdministrativeUnitDto() != null) {
						cell.setCellValue(reportForm16Item.getAdministrativeUnitDto().getCode());
					} else {
						cell.setCellValue("");
					}

					// Mã cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getCode() != null)
						cell.setCellValue(reportForm16Item.getFarm().getCode());
					else
						cell.setCellValue("");

					// Tên cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getName() != null)
						cell.setCellValue(reportForm16Item.getFarm().getName());
					else
						cell.setCellValue("");

					// Ngày import
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String dateStr = "";
					if (reportForm16Item.getDateReport() != null) {
						try {
							dateStr = formatter.format(reportForm16Item.getDateReport());
						} catch (Exception e) {
							dateStr = "";
						}
						cell.setCellValue(dateStr);
					}
					// tên loài nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAnimal() != null && reportForm16Item.getAnimal().getName() != null) {
						cell.setCellValue(reportForm16Item.getAnimal().getName());
					} else {
						cell.setCellValue("");
					}

					// Tên khoa học
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAnimal() != null && reportForm16Item.getAnimal().getScienceName() != null) {
						cell.setCellValue(reportForm16Item.getAnimal().getScienceName());
					} else {
						cell.setCellValue("");
					}

					// mã loài
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAnimal() != null && reportForm16Item.getAnimal().getCode() != null) {
						cell.setCellValue(reportForm16Item.getAnimal().getCode());
					} else {
						cell.setCellValue("");
					}

					// Tổng số cá thể
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getTotal() != null) {
						cell.setCellValue(reportForm16Item.getTotal());
					} else {
						cell.setCellValue("");
					}

					// Đực bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getMaleParent() != null) {
						cell.setCellValue(reportForm16Item.getMaleParent());
					} else {
						cell.setCellValue("");
					}
					// Cái bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFemaleParent() != null) {
						cell.setCellValue(reportForm16Item.getFemaleParent());
					} else {
						cell.setCellValue("");
					}
					// Không xác định bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getUnGenderParent() != null) {
						cell.setCellValue(reportForm16Item.getUnGenderParent());
					} else {
						cell.setCellValue("");
					}
					
					// đực hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getMaleGilts() != null) {
						cell.setCellValue(reportForm16Item.getMaleGilts());
					} else {
						cell.setCellValue("");
					}
					// cái hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFemaleGilts() != null) {
						cell.setCellValue(reportForm16Item.getFemaleGilts());
					} else {
						cell.setCellValue("");
					}
					// không xác định hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getUnGenderGilts() != null) {
						cell.setCellValue(reportForm16Item.getUnGenderGilts());
					} else {
						cell.setCellValue("");
					}

					// con dưới 1 tuổi đực
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getMaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getMaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con dưới 1 tuổi cái
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFemaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getFemaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con dưới 1 tuổi không xác định
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}

					// đực trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getMaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getMaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// cái trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFemaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getFemaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// không xác định trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getUnGenderChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getUnGenderChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}

					// tổng nhập
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getTotalImport() != null) {
						cell.setCellValue(reportForm16Item.getTotalImport());
					} else {
						cell.setCellValue("");
					}
					
					// bố mẹ đực nhập
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportMaleParent() != null) {
						cell.setCellValue(reportForm16Item.getImportMaleParent());
					} else {
						cell.setCellValue("");
					}
					//bố mẹ cái nhập
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportFemaleParent() != null) {
						cell.setCellValue(reportForm16Item.getImportFemaleParent());
					} else {
						cell.setCellValue("");
					}
					//bố mẹ không xác định nhập
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportUnGenderParent() != null) {
						cell.setCellValue(reportForm16Item.getImportUnGenderParent());
					} else {
						cell.setCellValue("");
					}
					
					// hậu bị đực   nhập  
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportMaleGilts() != null) {
						cell.setCellValue(reportForm16Item.getImportMaleGilts());
					} else {
						cell.setCellValue("");
					}
					//  hậu bị cái   nhập  
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportMaleGilts() != null) {
						cell.setCellValue(reportForm16Item.getImportMaleGilts());
					} else {
						cell.setCellValue("");
					}
					// hậu bị không rõ   nhập  
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportUnGenderGilts() != null) {
						cell.setCellValue(reportForm16Item.getImportUnGenderGilts());
					} else {
						cell.setCellValue("");
					}

					// con dưới 1 tuổi đực nhập
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportMaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getImportMaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con dưới 1 tuổi cái nhập
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportFemaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getImportFemaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con dưới 1 tuổi không rõ giới tính nhập
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getImportChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					
					// con trên 1 tuổi 1 tuổi đực nhap vao co so
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportMaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getImportMaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con trên 1 tuổi 1 tuổi cái nhap vao co so
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportFemaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getImportFemaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con trên 1 tuổi 1 tuổi không rõ giới tính nhap vao co so
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportUnGenderChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getImportUnGenderChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}

					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getImportReason() != null) {
						cell.setCellValue(reportForm16Item.getImportReason());
					} else {
						cell.setCellValue("");
					}
					
					// tổng xuất 
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getTotalExport() != null) {
						cell.setCellValue(reportForm16Item.getTotalExport());
					} else {
						cell.setCellValue("");
					}

					// bố mẹ đực   xuất 
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportMaleParent() != null) {
						cell.setCellValue(reportForm16Item.getExportMaleParent());
					} else {
						cell.setCellValue("");
					}
					// bố mẹ cái   xuất 
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportFemaleParent() != null) {
						cell.setCellValue(reportForm16Item.getExportFemaleParent());
					} else {
						cell.setCellValue("");
					}
					// bố mẹ không rõ   xuất  
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportUnGenderParent() != null) {
						cell.setCellValue(reportForm16Item.getExportUnGenderParent());
					} else {
						cell.setCellValue("");
					}
					
					// hậu bị đực   xuất  
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportMaleGilts() != null) {
						cell.setCellValue(reportForm16Item.getExportMaleGilts());
					} else {
						cell.setCellValue("");
					}
					// hậu bị cái   xuất  
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportMaleGilts() != null) {
						cell.setCellValue(reportForm16Item.getExportMaleGilts());
					} else {
						cell.setCellValue("");
					}
					//  hậu bị không rõ   xuất  
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportMaleGilts() != null) {
						cell.setCellValue(reportForm16Item.getExportMaleGilts());
					} else {
						cell.setCellValue("");
					}
					
					// con dưới 1 tuổi đực xuất
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportMaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getExportMaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con dưới 1 tuổi cái xuất
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportFemaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getExportFemaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con dưới 1 tuổi không rõ xuất
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getExportChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}

					// con trên 1 tuổi 1 tuổi đực xuat ra khoi co so
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportMaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getExportMaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con trên 1 tuổi 1 tuổi cái xuat ra khoi co so
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportFemaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getExportFemaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// con trên 1 tuổi 1 tuổi không rõ giới tính xuat ra khoi co so
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportUnGenderChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getExportUnGenderChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}

					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getExportReason() != null) {
						cell.setCellValue(reportForm16Item.getExportReason());
					} else {
						cell.setCellValue("");
					}
					// tran huu dat them du lieu nhap xuat form 16 end

					// Tình trạng đk
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getStatus() != null
							&& (reportForm16Item.getFarm().getStatus() == 3 || reportForm16Item.getFarm().getStatus() == 1)) {
						cell.setCellValue(1);
					} else if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getStatus() != null
							&& reportForm16Item.getFarm().getStatus() == 2) {
						cell.setCellValue(2);
					} else
						cell.setCellValue("");

					// Mã số CS theo 06/2019
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getNewRegistrationCode() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getNewRegistrationCode());
					} else
						cell.setCellValue("");
					// ngày cấp theo 06
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String dateRegis = "";
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getDateRegistration() != null) {
						try {
							dateRegis = formatter.format(reportForm16Item.getFarm().getDateRegistration());
						} catch (Exception e) {
							dateRegis = "";
						}
						cell.setCellValue(dateRegis);
					} else {
						cell.setCellValue("");
					}
					// Mã khác/ số giấy đăng ký
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getOldRegistrationCode() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getOldRegistrationCode());
					} else {
						cell.setCellValue("");
					}
					//Ngày cấp mã khác	
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String dateOtherRegis = "";
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getDateOtherRegistration() != null) {
						try {
							dateOtherRegis = formatter.format(reportForm16Item.getFarm().getDateOtherRegistration());
						} catch (Exception e) {
							dateOtherRegis = "";
						}
						cell.setCellValue(dateOtherRegis);
					} else {
						cell.setCellValue("");
					}
					cellIndex++;// so giay chung nhan dang ki kinh doanh
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getBusinessRegistrationNumber() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getBusinessRegistrationNumber());
					} else {
						cell.setCellValue("");
					}
					// Mục đích nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getFarmProductTargets() != null
							&& reportForm16Item.getFarm().getFarmProductTargets().size() > 0) {
						String productTarrget = "";
						for (ProductTargetDto fpt : reportForm16Item.getFarm().getFarmProductTargets()) {
							List<String> listPT = new ArrayList<String>();
							listPT.add(fpt.getCode());
							if (listPT.size() > 0) {
								for (int p = 0; p < listPT.size(); p++) {
									if (listPT.get(p) != null) {
										if(productTarrget.length()>0) {
											productTarrget += ", " +listPT.get(p);
										}else {
											productTarrget += listPT.get(p);
										}
										
									}else {
										productTarrget += "";
									}
								}
							}
						}
						cell.setCellValue(productTarrget);
					} else {
						cell.setCellValue("");
					}
					
					// Hình thức nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getFarmHusbandryMethods() != null && reportForm16Item.getFarm().getFarmHusbandryMethods().size() > 0) {
						cell.setCellValue(reportForm16Item.getFarm().getFarmHusbandryMethods().iterator().next().getHusbandryMethod().getCode());
					} else {
						cell.setCellValue("");
					}
					
					// Nguồn gốc
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAnimal() != null && reportForm16Item.getOriginal() != null) {
						cell.setCellValue(reportForm16Item.getOriginal().getCode());
					} else {
						cell.setCellValue("");
					}
					// Số điện thoại
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm().getPhoneNumber() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getPhoneNumber());
					} else {
						cell.setCellValue("");
					}
					// Kinh độ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getLongitude() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getLongitude());
					} else {
						cell.setCellValue("");
					}
					// Vĩ độ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getLatitude() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getLatitude());
					} else {
						cell.setCellValue("");
					}
					// diện tích
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getLodgingAcreage() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getLodgingAcreage());
					} else {
						cell.setCellValue("");
					}
					// ngày bắt đầu nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String dateStart = "";
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getStartDate() != null) {
						try {
							dateStart = formatter.format(reportForm16Item.getFarm().getStartDate());
						} catch (Exception e) {
							dateStart = "";
						}
						cell.setCellValue(dateStart);
					} else {
						cell.setCellValue("");
					}
					// Họ tên
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getOwnerName() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getOwnerName());
					} else {
						cell.setCellValue("");
					}

					// Năm sinh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getOwnerDob() != null) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy");
						cell.setCellValue(format.format(reportForm16Item.getFarm().getOwnerDob()));
					} else {
						cell.setCellValue("");
					}

					// CMND/căn cước
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getOwnerCitizenCode() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getOwnerCitizenCode());
					} else {
						cell.setCellValue("");
					}

					// ngày cấp CMND/căn cước
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String citizen = "";
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getOwnerCitizenDate() != null) {
						try {
							citizen = formatter.format(reportForm16Item.getFarm().getOwnerCitizenDate());
						} catch (Exception e) {
							citizen = "";
						}
						cell.setCellValue(citizen);
					} else {
						cell.setCellValue("");
					}

					// nơi cấp CMND/căn cước
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getOwnerCitizenLocation() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getOwnerCitizenLocation());
					} else {
						cell.setCellValue("");
					}
					// số điện thoại chủ cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getOwnerPhoneNumber() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getOwnerPhoneNumber());
					} else {
						cell.setCellValue("");
					}

					// tran huu dat them du lieu của farm start
					//TTBVMT
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getTtbvmt() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getTtbvmt());
					} else {
						cell.setCellValue("");
					}
					//TTBVMT Date
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String datettbvmt = "";
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getTtbvmtDate() != null) {
						try {
							datettbvmt = formatter.format(reportForm16Item.getFarm().getTtbvmtDate());
						} catch (Exception e) {
							datettbvmt = "";
						}
						cell.setCellValue(datettbvmt);
					} else {
						cell.setCellValue("");
					}

					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getNote() != null) {
						cell.setCellValue(reportForm16Item.getNote());
					} else {
						cell.setCellValue("");
					}
					
					// Farm ID
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellHidden);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getMapCode() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getMapCode());
					} else {
						cell.setCellValue("");
					}
//
					
					rowStartTable++;
					// tran huu dat them du lieu của farm end
				}

			}
		}
		workbook.write(outputStream);
	}

	public static void downloadFileImportExcelForm16AOld(List<ReportForm16Dto> list, String titleHeader,
			ServletOutputStream outputStream) throws IOException {
		XSSFWorkbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet("DuLieuImport");
		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBold(true);

		Font font1 = workbook.createFont();
		font1.setFontHeightInPoints((short) 12);
		font1.setFontName("Times New Roman");

		XSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setFont(font1);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 13);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		CellStyle cellHidden = workbook.createCellStyle();
		cellHidden.setBorderBottom(BorderStyle.THIN);
		cellHidden.setBorderLeft(BorderStyle.THIN);
		cellHidden.setBorderTop(BorderStyle.THIN);
		cellHidden.setBorderRight(BorderStyle.THIN);
		cellHidden.setWrapText(true);
		cellHidden.setAlignment(HorizontalAlignment.CENTER);
		cellHidden.setVerticalAlignment(VerticalAlignment.CENTER);
		cellHidden.setFont(font);
		cellHidden.setHidden(true);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);
		sheet.createRow(2);

		row = sheet.getRow(2);
		cell = row.createCell(0);
//		cell.setCellValue(("Dữ liệu Import Excel biểu mẫu 16A " + titleHeader).toUpperCase());
//		cell.setCellStyle(cellStyleNoBoder);
//		row.setHeightInPoints(20);
//		sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 15));

		rowIndex = 0;
		cellIndex = 0;
		sheet.createRow(1);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tỉnh");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("1");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Huyện");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("2");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xã");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("3");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ấp/Thôn");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("4");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số hệ thống");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("5");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên cơ sở");
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("6");
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("7");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên loài nuôi");
		sheet.setColumnWidth(cellIndex, 35 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("8");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học (tên latin)");
		sheet.setColumnWidth(cellIndex, 35 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("9");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã loài");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("10");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng số lượng");
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("11");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn bố mẹ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn giống hậu bị");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng con dưới 1 tuổi (chưa trưởng thành)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng cá thể trên 1 tuổi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tình trạng đăng ký (Chưa ĐK=2, Đã ĐK=1)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số CS (theo 06/2019)");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày cấp mã (06/2019)");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số khác/ số giấy đăng ký");
		sheet.setColumnWidth(cellIndex, 15 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày cấp");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("Giấy đăng ký kinh doanh");
		cell.setCellStyle(cellStyleBoldTable);
		//sheet.setColumnHidden(34, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 35 * 256);
		//cell.setCellStyle(cellHidden);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mục đích gây nuôi (Z,Q,R,S,O hoặc T)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Nguồn gốc (C,R,U,W,NK,I)");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số điện thoại");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Zalo");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellValue("Kinh độ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellValue("Vĩ độ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 18 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Chủ cơ sở/ Đại diện");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 3));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 3);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Họ và tên");
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Năm sinh");
		sheet.setColumnWidth(cellIndex, 13 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("CMND/Căn cước");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Điện thoại");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã xã (Theo danh mục ĐVHC của Quyết định 124/2004/QĐ-TTg)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellValue("Farm ID");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(36, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 35 * 256);
		cell.setCellStyle(cellHidden);
		
		

		int rowStartTable = 2;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

		if (list != null && list.size() > 0) {
			String previousFarmCode = "";
			String previousAnimalCode = "";
			for (ReportForm16Dto reportForm16Item : list) {
				if (reportForm16Item != null) {

					row = sheet.createRow(rowStartTable);
					cellIndex = -1;
//					cellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
//					cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
//					if (previousFarmCode.equals(reportForm16Item.getFarm().getCode())
//							&& previousAnimalCode.equals(reportForm16Item.getAnimal().getCode())) {
//						XSSFColor duplicateColor = new XSSFColor(java.awt.Color.YELLOW); // Yellow
//						cellStyle.setFillBackgroundColor(duplicateColor);
//						cellStyle.setFillForegroundColor(duplicateColor);
//						cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
//					}
					// Tỉnh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getProvinceDto() != null
							&& reportForm16Item.getProvinceDto().getName() != null) {
						cell.setCellValue(reportForm16Item.getProvinceDto().getName());
					} else {
						cell.setCellValue("");
					}
					// Huyện
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getDistrictDto() != null
							&& reportForm16Item.getDistrictDto().getName() != null) {
						cell.setCellValue(reportForm16Item.getDistrictDto().getName());
					} else {
						cell.setCellValue("");
					}
					// Xã
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAdministrativeUnitDto() != null
							&& reportForm16Item.getAdministrativeUnitDto().getName() != null) {
						cell.setCellValue(reportForm16Item.getAdministrativeUnitDto().getName());
					} else {
						cell.setCellValue("");
					}
					// Thôn/Ấp
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getVillage() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getVillage());
					} else {
						cell.setCellValue("");
					}

					// Mã cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getCode() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getCode());
					} else {
						cell.setCellValue("");
					}
					// Tên cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getName() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getName());
					} else {
						cell.setCellValue("");
					}
					// Ngày import
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String dateStr = "";
					if (reportForm16Item.getDateReport() != null) {
						try {
							dateStr = formatter.format(reportForm16Item.getDateReport());
						} catch (Exception e) {
							dateStr = "";
						}
						cell.setCellValue(dateStr);
					}
					// tên loài nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAnimal() != null && reportForm16Item.getAnimal().getName() != null) {
						cell.setCellValue(reportForm16Item.getAnimal().getName());
					} else {
						cell.setCellValue("");
					}

					// Tên khoa học
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAnimal() != null && reportForm16Item.getAnimal().getScienceName() != null) {
						cell.setCellValue(reportForm16Item.getAnimal().getScienceName());
					} else {
						cell.setCellValue("");
					}

					// mã loài
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAnimal() != null && reportForm16Item.getAnimal().getCode() != null) {
						cell.setCellValue(reportForm16Item.getAnimal().getCode());
					} else {
						cell.setCellValue("");
					}

					// Tổng số cá thể
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getTotal() != null) {
						cell.setCellValue(reportForm16Item.getTotal());
					} else {
						cell.setCellValue("");
					}

					// Đực bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getMaleParent() != null) {
						cell.setCellValue(reportForm16Item.getMaleParent());
					} else {
						cell.setCellValue("");
					}
					// Cái bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFemaleParent() != null) {
						cell.setCellValue(reportForm16Item.getFemaleParent());
					} else {
						cell.setCellValue("");
					}
					// đực hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getMaleGilts() != null) {
						cell.setCellValue(reportForm16Item.getMaleGilts());
					} else {
						cell.setCellValue("");
					}
					// cái hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFemaleGilts() != null) {
						cell.setCellValue(reportForm16Item.getFemaleGilts());
					} else {
						cell.setCellValue("");
					}

					// con dưới 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}

					// đực trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getMaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getMaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// cái trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFemaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getFemaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// không xác định trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getUnGenderChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16Item.getUnGenderChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}

					// Tình trạng đk
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getStatus() != null
							&& (reportForm16Item.getFarm().getStatus() == 3 || reportForm16Item.getFarm().getStatus() == 1)) {
						cell.setCellValue(1);
					} else if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getStatus() != null
							&& reportForm16Item.getFarm().getStatus() == 2) {
						cell.setCellValue(2);
					} else
						cell.setCellValue("");
					// Mã số đk theo 06
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getNewRegistrationCode() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getNewRegistrationCode());
					} else {
						cell.setCellValue("");
					}
					// ngày cấp mã số đk theo 06
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String dateRegis = "";
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getDateRegistration() != null) {
						try {
							dateRegis = formatter.format(reportForm16Item.getFarm().getDateRegistration());
						} catch (Exception e) {
							dateRegis = "";
						}
						cell.setCellValue(dateRegis);
					} else {
						cell.setCellValue("");
					}
					// Số giấy cndk
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getOldRegistrationCode() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getOldRegistrationCode());
					} else {
						cell.setCellValue("");
					}
					// Ngày cấp mã số giấy CN đăng ký
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String dateOtherRegis = "";
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getDateOtherRegistration() != null) {
						try {
							dateOtherRegis = formatter.format(reportForm16Item.getFarm().getDateOtherRegistration());
						} catch (Exception e) {
							dateOtherRegis = "";
						}
						cell.setCellValue(dateOtherRegis);
					} else {
						cell.setCellValue("");
					}
					// Số đk kinh doanh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellHidden);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getBusinessRegistrationNumber() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getBusinessRegistrationNumber());
					} else {
						cell.setCellValue("");
					}
					// Mục đích nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getFarmProductTargets() != null
							&& reportForm16Item.getFarm().getFarmProductTargets().size() > 0) {
						String productTarrget = "";
						for (ProductTargetDto fpt : reportForm16Item.getFarm().getFarmProductTargets()) {
							List<String> listPT = new ArrayList<String>();
							listPT.add(fpt.getCode());
							if (listPT.size() > 0) {
								for (int p = 0; p < listPT.size(); p++) {
									if (listPT.get(p) != null) {
										if(productTarrget.length()>0) {
											productTarrget += ", " +listPT.get(p);
										}else {
											productTarrget += listPT.get(p);
										}
										
									}else {
										productTarrget += "";
									}
								}
							}
						}
						cell.setCellValue(productTarrget);
					} else {
						cell.setCellValue("");
					}
					// Nguồn gốc
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getAnimal() != null && reportForm16Item.getOriginal() != null) {
						cell.setCellValue(reportForm16Item.getOriginal().getCode());
					} else {
						cell.setCellValue("");
					}

					// Số điện thoại
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm().getPhoneNumber() != null)
						cell.setCellValue(reportForm16Item.getFarm().getPhoneNumber());
					else {
						cell.setCellValue("");
					}
					// zalo
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm().getOwnerPhoneNumber() != null)
						cell.setCellValue(reportForm16Item.getFarm().getOwnerPhoneNumber());
					else {
						cell.setCellValue("");
					}

					// Kinh độ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getLongitude() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getLongitude());
					} else {
						cell.setCellValue("");
					}
					// Vĩ độ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getLatitude() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getLatitude());
					} else {
						cell.setCellValue("");
					}

					// Họ tên
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getOwnerName() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getOwnerName());
					} else {
						cell.setCellValue("");
					}

					// Năm sinh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getOwnerDob() != null) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy");
						cell.setCellValue(format.format(reportForm16Item.getFarm().getOwnerDob()));
					} else {
						cell.setCellValue("");
					}

					// CMND/căn cước
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getOwnerCitizenCode() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getOwnerCitizenCode());
					} else {
						cell.setCellValue("");
					}

					// số điện thoại chủ cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null
							&& reportForm16Item.getFarm().getOwnerPhoneNumber() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getOwnerPhoneNumber());
					} else {
						cell.setCellValue("");
					}
					// Mã xã
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getAdministrativeUnitDto() != null) {
						cell.setCellValue(reportForm16Item.getAdministrativeUnitDto().getCode());
					} else {
						cell.setCellValue("");
					}
					
					// Farm ID
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellHidden);
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getMapCode() != null) {
						cell.setCellValue(reportForm16Item.getFarm().getMapCode());
					} else {
						cell.setCellValue("");
					}
//
					if (reportForm16Item.getFarm() != null && reportForm16Item.getFarm().getCode() != null) {
						previousFarmCode = reportForm16Item.getFarm().getCode();
					}
					if (reportForm16Item.getAnimal() != null && reportForm16Item.getAnimal().getCode() != null) {
						previousAnimalCode = reportForm16Item.getAnimal().getCode();
					}
					

					rowStartTable++;

				}

			}
		}
		workbook.write(outputStream);
	}

	public static void exportReportForm16DataFollow(List<ReportForm16> list, String titleHeader,
			ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		int index = 1;
		if (list != null && list.size() > 0) {
			for (ReportForm16 reportForm16 : list) {
				Sheet sheet = workbook.createSheet(
						index + "-" + reportForm16.getAnimal().getCode() + " - " + reportForm16.getAnimal().getName());
			

				PropertyTemplate pt = new PropertyTemplate();
				Font font = workbook.createFont();
				font.setFontHeightInPoints((short) 12);
				font.setFontName("Times New Roman");
				font.setBold(true);
				// them font cho table tran huu dat
				Font font1 = workbook.createFont();
				font.setFontHeightInPoints((short) 12);
				font.setFontName("Times New Roman");
				font.setBold(true);

				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setWrapText(true);
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle.setFont(font1);

				Font fontNoBorder = workbook.createFont();
				fontNoBorder.setFontHeightInPoints((short) 13);
				fontNoBorder.setBold(true);
				fontNoBorder.setFontName("Times New Roman");

				CellStyle cellStyleNoBoder = workbook.createCellStyle();
				cellStyleNoBoder.setWrapText(true);
				cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
				cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleNoBoder.setFont(fontNoBorder);

				CellStyle cellStyleBoldTable = workbook.createCellStyle();
				cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
				cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
				cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
				cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
				cellStyleBoldTable.setWrapText(true);
				cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
				cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleBoldTable.setFont(font);

				Row row = null;
				Cell cell = null;

				int rowIndex = 0;
				int cellIndex = 0;
				sheet.createRow(0);
				sheet.createRow(2);

				row = sheet.getRow(2);
				cell = row.createCell(0);

				rowIndex = 0;
				cellIndex = 0;
				sheet.createRow(1);
				index = index + 1;

				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tỉnh");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("1");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Huyện");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("2");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Xã");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("3");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Ấp/Thôn");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("4");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Mã số hệ thống");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("5");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tên cơ sở");
				sheet.setColumnWidth(cellIndex, 18 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("6");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Ngày");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("7");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tên loài nuôi");
				sheet.setColumnWidth(cellIndex, 35 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("8");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tên khoa học (tên latin)");
				sheet.setColumnWidth(cellIndex, 35 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("9");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Mã loài");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("10");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tổng số lượng");
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("12");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đàn bố mẹ");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 7 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 7 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đàn giống hậu bị");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 7 * 256);

				cellIndex++;
				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnWidth(cellIndex, 7 * 256);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Số lượng con dưới 1 tuổi (chưa trưởng thành)");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Số lượng cá thể trên 1 tuổi");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Đực");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Cái");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Không xác định");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tình trạng đăng ký (Chưa ĐK=2, Đã ĐK=1)");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Số giấy CN đăng ký");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Mã số đăng ký");
				sheet.setColumnWidth(cellIndex, 15 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Năm đăng ký");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Mục đích gây nuôi");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Nguồn gốc");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Số điện thoại");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Zalo");
				sheet.setColumnWidth(cellIndex, 9 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 18 * 256);
				cell.setCellValue("Kinh độ");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 18 * 256);
				cell.setCellValue("Vĩ độ");
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 14 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Chủ cơ sở/ đại diện");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 3));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 3);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Họ và tên");
				sheet.setColumnWidth(cellIndex, 25 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Năm sinh");
				sheet.setColumnWidth(cellIndex, 13 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("CMND/Căn cước");
				sheet.setColumnWidth(cellIndex, 20 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Điện thoại");
				sheet.setColumnWidth(cellIndex, 20 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
				cell.setCellValue("Mã xã (Theo danh mục ĐVHC của Quyết định 124/2004/QĐ-TTg)");
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 35 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				int rowStartTable = 2;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				if (reportForm16 != null) {
					row = sheet.createRow(rowStartTable);
					cellIndex = -1;

					// Tỉnh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getProvince() != null && reportForm16.getProvince().getName() != null) {
						cell.setCellValue(
								reportForm16.getProvince().getName());
					} else
						cell.setCellValue("");

					// Huyện
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getDistrict() != null && reportForm16.getDistrict().getName() != null) {
						cell.setCellValue(reportForm16.getDistrict().getName());
					} else
						cell.setCellValue("");

					// Xã
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAdministrativeUnit() != null && reportForm16.getAdministrativeUnit().getName() != null) {
						cell.setCellValue(reportForm16.getAdministrativeUnit().getName());
					} else
						cell.setCellValue("");

					// Thôn/Ấp
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getVillage() != null)
						cell.setCellValue(reportForm16.getFarm().getVillage());
					else
						cell.setCellValue("");

					// Mã cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getCode() != null)
						cell.setCellValue(reportForm16.getFarm().getCode());
					else
						cell.setCellValue("");

					// Tên cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getName() != null)
						cell.setCellValue(reportForm16.getFarm().getName());
					else
						cell.setCellValue("");

					// Ngày import
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String dateStr = "";
					if (reportForm16.getDateReport() != null) {
						try {
							dateStr = formatter.format(reportForm16.getDateReport());
						} catch (Exception e) {
							dateStr = "";
						}
						cell.setCellValue(dateStr);
					}
					// tên loài nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getName() != null) {
						cell.setCellValue(reportForm16.getAnimal().getName());
					} else {
						cell.setCellValue("");
					}

					// Tên khoa học
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getScienceName() != null) {
						cell.setCellValue(reportForm16.getAnimal().getScienceName());
					} else {
						cell.setCellValue("");
					}

					// mã loài
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getCode() != null) {
						cell.setCellValue(reportForm16.getAnimal().getCode());
					} else {
						cell.setCellValue("");
					}

					// Tổng số cá thể
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getTotal() != null) {
						cell.setCellValue(reportForm16.getTotal());
					} else {
						cell.setCellValue("");
					}

					// Đực bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getMaleParent() != null) {
						cell.setCellValue(reportForm16.getMaleParent());
					} else {
						cell.setCellValue("");
					}
					// Cái bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFemaleParent() != null) {
						cell.setCellValue(reportForm16.getFemaleParent());
					} else {
						cell.setCellValue("");
					}
					// đực hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getMaleGilts() != null) {
						cell.setCellValue(reportForm16.getMaleGilts());
					} else {
						cell.setCellValue("");
					}
					// cái hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFemaleGilts() != null) {
						cell.setCellValue(reportForm16.getFemaleGilts());
					} else {
						cell.setCellValue("");
					}

					// con dưới 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}

					// đực trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getMaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getMaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// cái trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFemaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getFemaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// không xác định trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getUnGenderChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getUnGenderChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}

					// Tình trạng đk
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getStatus() != null
							&& reportForm16.getFarm().getStatus() == 3) {
						cell.setCellValue(1);
					} else if (reportForm16.getFarm() != null && reportForm16.getFarm().getStatus() != null
							&& reportForm16.getFarm().getStatus() == 2) {
						cell.setCellValue(2);
					} else
						cell.setCellValue("");

					// Số giấy cndk
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getNewRegistrationCode() != null) {
						cell.setCellValue(reportForm16.getFarm().getNewRegistrationCode());
					} else
						cell.setCellValue("");

					// Mã số đk
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOldRegistrationCode() != null) {
						cell.setCellValue(reportForm16.getFarm().getOldRegistrationCode());
					} else
						cell.setCellValue("");

					// Năm đăng ký
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getYearRegistration() != null) {
						cell.setCellValue(reportForm16.getFarm().getYearRegistration());
					} else
						cell.setCellValue("");

					// Mục đích nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getFarmProductTargets() != null
							&& reportForm16.getFarm().getFarmProductTargets().size() > 0) {
						String productTarrget = "";
						for (FarmProductTarget fpt : reportForm16.getFarm().getFarmProductTargets()) {
							List<String> listPT = new ArrayList<String>();
							listPT.add(fpt.getProductTarget().getCode());
							if (listPT.size() > 0) {
								for (int p = 0; p < listPT.size(); p++) {
									if (listPT.get(p) != null)
										productTarrget += listPT.get(p);
									if (listPT.size() - 1 != p) {
										productTarrget += ", ";
									} else
										productTarrget += "";
								}
							}
						}
						cell.setCellValue(productTarrget);
					} else
						cell.setCellValue("");

					// Nguồn gốc
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getOriginal() != null) {
						cell.setCellValue(reportForm16.getAnimal().getOriginal().getCode());
					} else
						cell.setCellValue("");

					// Số điện thoại
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm().getPhoneNumber() != null)
						cell.setCellValue(reportForm16.getFarm().getPhoneNumber());
					else
						cell.setCellValue("");

					// Zalo
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm().getOwnerPhoneNumber() != null)
						cell.setCellValue(reportForm16.getFarm().getOwnerPhoneNumber());
					rowStartTable++;

					// Kinh độ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getLongitude() != null) {
						cell.setCellValue(reportForm16.getFarm().getLongitude());
					} else {
						cell.setCellValue("");
					}
					// Vĩ độ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getLatitude() != null) {
						cell.setCellValue(reportForm16.getFarm().getLatitude());
					} else {
						cell.setCellValue("");
					}
					// Họ tên
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOwnerName() != null) {
						cell.setCellValue(reportForm16.getFarm().getOwnerName());
					} else {
						cell.setCellValue("");
					}

					// Năm sinh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOwnerDob() != null) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy");
						cell.setCellValue(format.format(reportForm16.getFarm().getOwnerDob()));
					} else {
						cell.setCellValue("");
					}

					// CMND/căn cước
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOwnerCitizenCode() != null) {
						cell.setCellValue(reportForm16.getFarm().getOwnerCitizenCode());
					} else {
						cell.setCellValue("");
					}
					// số điện thoại chủ cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOwnerPhoneNumber() != null) {
						cell.setCellValue(reportForm16.getFarm().getOwnerPhoneNumber());
					} else {
						cell.setCellValue("");
					}
					// Mã xã
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getAdministrativeUnit() != null) {
						cell.setCellValue(reportForm16.getFarm().getAdministrativeUnit().getCode());
					} else {
						cell.setCellValue("");
					}
					rowStartTable++;

				}

			}
		}
		workbook.write(outputStream);
	}

	public static void exportReportAnimalDangerousCitesToExcel(List<AnimalReportDataFormDto> list, String subTitle,
			ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("DuLieuBaoCaoHoatDongNuoiDVHD");
		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 13);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		CellStyle cellStyleHidden = workbook.createCellStyle();
		cellStyleHidden.setBorderBottom(BorderStyle.THIN);
		cellStyleHidden.setBorderLeft(BorderStyle.THIN);
		cellStyleHidden.setBorderTop(BorderStyle.THIN);
		cellStyleHidden.setBorderRight(BorderStyle.THIN);
		cellStyleHidden.setWrapText(true);
		cellStyleHidden.setAlignment(HorizontalAlignment.CENTER);
		cellStyleHidden.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleHidden.setFont(font);
		cellStyleHidden.setHidden(true);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);
		sheet.createRow(2);

		row = sheet.getRow(0);
		cell = row.createCell(0);
		cell.setCellValue(
				"BÁO CÁO HOẠT ĐỘNG NUÔI ĐỘNG VẬT RỪNG NGUY CẤP, QUÝ, HIẾM, ĐỘNG VẬT RỪNG THÔNG THƯỜNG VÀ ĐỘNG VẬT THUỘC CÁC PHỤ LỤC CITES "
						+ subTitle);
		cell.setCellStyle(cellStyleNoBoder);
		row.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 24));

		rowIndex = 1;
		cellIndex = 0;
		sheet.createRow(rowIndex);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("STT");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 5 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 5 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tỉnh");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Huyện");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xã");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ấp/Thôn");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số cơ sở nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Họ tên chủ nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Loài nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		sheet.setColumnWidth(cellIndex, 40 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên loài nuôi(Tiếng Việt)");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã loài");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn bố mẹ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn giống hậu bị");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể dưới 1 tuổi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số cá thể trên 1 tuổi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 3));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 3);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số cơ sở nuôi (theo nghị định 06)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày được cấp mã số");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mục đích nuôi");
		sheet.setColumnWidth(cellIndex, 15 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ghi chú");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Kinh độ");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		cellIndex++;

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Vĩ độ");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã đơn vị hành chính cấp xã");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã đơn vị hành chính cấp huyện");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã đơn vị hành chính cấp tỉnh");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		int rowStartTable = 3;
		Integer index = 1;
		Boolean isTheSameFarm = false;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (list != null && list.size() > 0) {
			for (AnimalReportDataFormDto ardf : list) {
				row = sheet.createRow(rowStartTable);
				cellIndex = 0;
				// STT
				cell = row.createCell(cellIndex);
				cell.setCellValue(index);
				cell.setCellStyle(cellStyle);

				// Tỉnh
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
//				if (ardf.getFarm() != null && ardf.getFarm().getProvinceName()!= null && ardf.getFarm().getProvinceName().length() >0) {
//					cell.setCellValue(ardf.getFarm().getProvinceName());
//				} 
				if (ardf.getProvinceDto() != null && ardf.getProvinceDto().getName() != null
						&& ardf.getProvinceDto().getName().length() > 0) {
					cell.setCellValue(ardf.getProvinceDto().getName());
				} else
					cell.setCellValue("");

				// Huyện
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
//				if (ardf.getFarm() != null && ardf.getFarm().getDistrictName() != null
//						&& ardf.getFarm().getDistrictName().length() >0 ) {
//					cell.setCellValue(ardf.getFarm().getDistrictName());
//				}
				if (ardf.getDistrictDto() != null && ardf.getDistrictDto().getName() != null
						&& ardf.getDistrictDto().getName().length() > 0) {
					cell.setCellValue(ardf.getDistrictDto().getName());
				} else
					cell.setCellValue("");

				// Xã
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
//				if (ardf.getFarm() != null && ardf.getFarm().getWardsName() != null
//						&& ardf.getFarm().getWardsName().length() >0) {
//					cell.setCellValue(ardf.getFarm().getWardsName());
//				}
				if (ardf.getAdministrativeUnitDto() != null && ardf.getAdministrativeUnitDto().getName() != null
						&& ardf.getAdministrativeUnitDto().getName().length() > 0) {
					cell.setCellValue(ardf.getAdministrativeUnitDto().getName());
				} else
					cell.setCellValue("");

				// Thôn/Ấp
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarm() != null && ardf.getFarm().getVillage() != null)
					cell.setCellValue(ardf.getFarm().getVillage());
				else
					cell.setCellValue("");

				// mã số cơ sở nuôi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarm() != null && ardf.getFarm().getCode() != null) {
					cell.setCellValue(ardf.getFarm().getCode());
				} else
					cell.setCellValue("");

				// Tên cơ sở
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarm() != null && ardf.getFarm().getName() != null)
					cell.setCellValue(ardf.getFarm().getName());
				else
					cell.setCellValue("");

				// tên loài nuôi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getAnimal() != null && ardf.getAnimal().getName() != null) {
					cell.setCellValue(ardf.getAnimal().getName());
				} else {
					cell.setCellValue("");
				}

				// Tên khoa học
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getAnimal() != null && ardf.getAnimal().getScienceName() != null) {
					cell.setCellValue(ardf.getAnimal().getScienceName());
				} else {
					cell.setCellValue("");
				}

				// Mã loài
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getAnimal() != null && ardf.getAnimal().getCode() != null) {
					cell.setCellValue(ardf.getAnimal().getCode());
				} else {
					cell.setCellValue("");
				}

				// Tổng số cá thể
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				Integer toTal = 0;
				if (ardf.getTotalParent() != null) {
					toTal += ardf.getTotalParent();
				}
				if (ardf.getTotalGilts() != null) {
					toTal += ardf.getTotalGilts();
				}
				if (ardf.getTotalChild() != null) {
					toTal += ardf.getTotalChild();
				}
				if (ardf.getTotalChildUnder1YO() != null) {
					toTal += ardf.getTotalChildUnder1YO();
				}
				if (toTal > 0) {
					cell.setCellValue(toTal);
				} else {
					cell.setCellValue(0);
				}

				// Tổng số cá thể đàn bố mẹ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getTotalParent() != null) {
					cell.setCellValue(ardf.getTotalParent());
				} else {
					cell.setCellValue(0);
				}

				// Đực bố mẹ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getMaleParent() != null) {
					cell.setCellValue(ardf.getMaleParent());
				} else {
					cell.setCellValue(0);
				}
				// Cái bố mẹ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFemaleParent() != null) {
					cell.setCellValue(ardf.getFemaleParent());
				} else {
					cell.setCellValue(0);
				}
				// Tổng số cá thể đàn hậu bị
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getTotalGilts() != null) {
					cell.setCellValue(ardf.getTotalGilts());
				} else {
					cell.setCellValue(0);
				}
				// đực hậu bị
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getMaleGilts() != null) {
					cell.setCellValue(ardf.getMaleGilts());
				} else {
					cell.setCellValue(0);
				}
				// cái hậu bị
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFemaleGilts() != null) {
					cell.setCellValue(ardf.getFemaleGilts());
				} else {
					cell.setCellValue(0);
				}

				// con dưới 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getTotalChildUnder1YO() != null) {
					cell.setCellValue(ardf.getTotalChildUnder1YO());
				} else {
					cell.setCellValue(0);
				}

				// Tổng cá thể trên 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getTotalChild() != null) {
					cell.setCellValue(ardf.getTotalChild());
				} else {
					cell.setCellValue(0);
				}

				// đực trên 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getMaleChild() != null) {
					cell.setCellValue(ardf.getMaleChild());
				} else {
					cell.setCellValue(0);
				}
				// cái trên 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFemaleChild() != null) {
					cell.setCellValue(ardf.getFemaleChild());
				} else {
					cell.setCellValue(0);
				}
				// không xác định trên 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getUnGenderChild() != null) {
					cell.setCellValue(ardf.getUnGenderChild());
				} else {
					cell.setCellValue(0);
				}
				// Mã số cơ sở nuôi (theo nghị định 06/2019)
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarm() != null && ardf.getFarm().getNewRegistrationCode() != null) {
					cell.setCellValue(ardf.getFarm().getNewRegistrationCode());
				} else {
					cell.setCellValue("");
				}

				// ngày được cấp mã số
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarm() != null && ardf.getFarm().getFoundingDate() != null) {
					cell.setCellValue(formatter.format(ardf.getFarm().getFoundingDate()));
				} else
					cell.setCellValue("");

				// mục đích nuôi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getProductTargets() != null && ardf.getProductTargets().length() > 0) {
					cell.setCellValue(ardf.getProductTargets());
				} else
					cell.setCellValue("");

				// ghi chú
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getNote() != null && ardf.getNote().length() > 0) {
					cell.setCellValue(ardf.getNote());
				} else
					cell.setCellValue("");

				// Kinh độ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarm() != null && ardf.getFarm().getLongitude() != null) {
					cell.setCellValue(ardf.getFarm().getLongitude());
				} else
					cell.setCellValue("");

				// Vĩ độ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarm() != null && ardf.getFarm().getLatitude() != null) {
					cell.setCellValue(ardf.getFarm().getLatitude());
				} else
					cell.setCellValue("");

				// Mã xã
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getAdministrativeUnitDto() != null && ardf.getAdministrativeUnitDto().getCode() != null) {
					cell.setCellValue(ardf.getAdministrativeUnitDto().getCode());
				} else
					cell.setCellValue("");

				// Mã huyện
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getAdministrativeUnitDto() != null && ardf.getAdministrativeUnitDto().getCode() != null) {
					cell.setCellValue(ardf.getDistrictDto().getCode());
				} else
					cell.setCellValue("");

				// Mã tỉnh
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getAdministrativeUnitDto() != null && ardf.getAdministrativeUnitDto().getCode() != null) {
					cell.setCellValue(ardf.getProvinceDto().getCode());
				} else
					cell.setCellValue("");

				index++;
				rowStartTable++;
			}
		}
		workbook.write(outputStream);
		workbook.close();
	}
	
	public static void exportReportAnimalDangerousCitesToExcelNativeQuery(List<Report18Dto> list, String subTitle,
			ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("DuLieuBaoCaoHoatDongNuoiDVHD");
		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 13);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		CellStyle cellStyleHidden = workbook.createCellStyle();
		cellStyleHidden.setBorderBottom(BorderStyle.THIN);
		cellStyleHidden.setBorderLeft(BorderStyle.THIN);
		cellStyleHidden.setBorderTop(BorderStyle.THIN);
		cellStyleHidden.setBorderRight(BorderStyle.THIN);
		cellStyleHidden.setWrapText(true);
		cellStyleHidden.setAlignment(HorizontalAlignment.CENTER);
		cellStyleHidden.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleHidden.setFont(font);
		cellStyleHidden.setHidden(true);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);
		sheet.createRow(2);

		row = sheet.getRow(0);
		cell = row.createCell(0);
		cell.setCellValue(
				"BÁO CÁO HOẠT ĐỘNG NUÔI ĐỘNG VẬT RỪNG NGUY CẤP, QUÝ, HIẾM, ĐỘNG VẬT RỪNG THÔNG THƯỜNG VÀ ĐỘNG VẬT THUỘC CÁC PHỤ LỤC CITES "
						+ subTitle);
		cell.setCellStyle(cellStyleNoBoder);
		row.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 24));

		rowIndex = 1;
		cellIndex = 0;
		sheet.createRow(rowIndex);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("STT");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 5 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 5 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tỉnh");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Huyện");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xã");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ấp/Thôn");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số cơ sở nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Họ tên chủ nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Loài nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		sheet.setColumnWidth(cellIndex, 40 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên loài nuôi(Tiếng Việt)");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã loài");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng");
		sheet.setColumnWidth(cellIndex, 14 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn bố mẹ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn giống hậu bị");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);
		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnWidth(cellIndex, 7 * 256);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cá thể dưới 1 tuổi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số cá thể trên 1 tuổi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 3));
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 2);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 3);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tổng");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Không xác định");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số cơ sở nuôi (theo nghị định 06)");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		sheet.setColumnWidth(cellIndex, 25 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày được cấp mã số");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mục đích nuôi");
		sheet.setColumnWidth(cellIndex, 15 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

//		cellIndex++;
//		row = sheet.getRow(rowIndex);
//		cell = row.createCell(cellIndex);
//		cell.setCellValue("Hình thức nuôi");
//		sheet.setColumnWidth(cellIndex, 15 * 256);
//		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
//		cell.setCellStyle(cellStyleBoldTable);
//
//		row = sheet.getRow(rowIndex + 1);
//		cell = row.createCell(cellIndex);
//		sheet.setColumnWidth(cellIndex, 14 * 256);
//		cell.setCellStyle(cellStyleBoldTable);
//
//		cellIndex++;
//		row = sheet.getRow(rowIndex);
//		cell = row.createCell(cellIndex);
//		cell.setCellValue("Trạng thái");
//		sheet.setColumnWidth(cellIndex, 8 * 256);
//		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
//		cell.setCellStyle(cellStyleBoldTable);
//
//		row = sheet.getRow(rowIndex + 1);
//		cell = row.createCell(cellIndex);
//		sheet.setColumnWidth(cellIndex, 14 * 256);
//		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ghi chú");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Kinh độ");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		cellIndex++;

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Vĩ độ");
		sheet.setColumnWidth(cellIndex, 9 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã đơn vị hành chính cấp xã");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã đơn vị hành chính cấp huyện");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã đơn vị hành chính cấp tỉnh");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		sheet.setColumnWidth(cellIndex, 14 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		int rowStartTable = 3;
		Integer index = 1;
		Boolean isTheSameFarm = false;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (list != null && list.size() > 0) {
			for (Report18Dto ardf : list) {
				row = sheet.createRow(rowStartTable);
				cellIndex = 0;
				// STT
				cell = row.createCell(cellIndex);
				cell.setCellValue(index);
				cell.setCellStyle(cellStyle);

				// Tỉnh
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
//				if (ardf.getFarm() != null && ardf.getFarm().getProvinceName()!= null && ardf.getFarm().getProvinceName().length() >0) {
//					cell.setCellValue(ardf.getFarm().getProvinceName());
//				} 
				if (ardf.getProvName() != null &&  ardf.getProvName().length() > 0) {
					cell.setCellValue(ardf.getProvName());
				} else
					cell.setCellValue("");

				// Huyện
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
//				if (ardf.getFarm() != null && ardf.getFarm().getDistrictName() != null
//						&& ardf.getFarm().getDistrictName().length() >0 ) {
//					cell.setCellValue(ardf.getFarm().getDistrictName());
//				}
				if (ardf.getDisName() != null && ardf.getDisName().length() > 0) {
					cell.setCellValue(ardf.getDisName());
				} else
					cell.setCellValue("");

				// Xã
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
//				if (ardf.getFarm() != null && ardf.getFarm().getWardsName() != null
//						&& ardf.getFarm().getWardsName().length() >0) {
//					cell.setCellValue(ardf.getFarm().getWardsName());
//				}
				if (ardf.getWardName() != null && ardf.getWardName().length() > 0) {
					cell.setCellValue(ardf.getWardName());
				} else
					cell.setCellValue("");

				// Thôn/Ấp
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarmVillage() != null && ardf.getFarmVillage().length()>0 )
					cell.setCellValue(ardf.getFarmVillage());
				else
					cell.setCellValue("");

				// mã số cơ sở nuôi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarmCode() != null && ardf.getFarmCode().length()>0) {
					cell.setCellValue(ardf.getFarmCode());
				} else
					cell.setCellValue("");

				// Tên cơ sở
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarmName() != null && ardf.getFarmName().length()>0)
					cell.setCellValue(ardf.getFarmName());
				else
					cell.setCellValue("");

				// tên loài nuôi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getAnimalName() != null && ardf.getAnimalName().length()>0) {
					cell.setCellValue(ardf.getAnimalName());
				} else {
					cell.setCellValue("");
				}

				// Tên khoa học
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getAnimalSciName() != null && ardf.getAnimalSciName().length()>0) {
					cell.setCellValue(ardf.getAnimalSciName());
				} else {
					cell.setCellValue("");
				}

				// Mã loài
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getAnimalCode() != null && ardf.getAnimalCode().length()>0) {
					cell.setCellValue(ardf.getAnimalCode());
				} else {
					cell.setCellValue("");
				}

				// Tổng số cá thể
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
//				Integer toTal = 0;
//				if (ardf.getTotalParent() != null) {
//					toTal += ardf.getTotalParent();
//				}
//				if (ardf.getTotalGilts() != null) {
//					toTal += ardf.getTotalGilts();
//				}
//				if (ardf.getTotalChild() != null) {
//					toTal += ardf.getTotalChild();
//				}
//				if (ardf.getTotalChildUnder1YO() != null) {
//					toTal += ardf.getTotalChildUnder1YO();
//				}
				if (ardf.getTotal()!=null) {
					cell.setCellValue(ardf.getTotal());
				} else {
					cell.setCellValue(0);
				}

				// Tổng số cá thể đàn bố mẹ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getTotalParent() != null) {
					cell.setCellValue(ardf.getTotalParent());
				} else {
					cell.setCellValue(0);
				}

				// Đực bố mẹ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getMaleParent() != null) {
					cell.setCellValue(ardf.getMaleParent());
				} else {
					cell.setCellValue(0);
				}
				// Cái bố mẹ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFemaleParent() != null) {
					cell.setCellValue(ardf.getFemaleParent());
				} else {
					cell.setCellValue(0);
				}
				// Tổng số cá thể đàn hậu bị
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getTotalGilts() != null) {
					cell.setCellValue(ardf.getTotalGilts());
				} else {
					cell.setCellValue(0);
				}
				// đực hậu bị
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getMaleGilts() != null) {
					cell.setCellValue(ardf.getMaleGilts());
				} else {
					cell.setCellValue(0);
				}
				// cái hậu bị
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFemaleGilts() != null) {
					cell.setCellValue(ardf.getFemaleGilts());
				} else {
					cell.setCellValue(0);
				}

				// con dưới 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getTotalChildUnder1YO() != null) {
					cell.setCellValue(ardf.getTotalChildUnder1YO());
				} else {
					cell.setCellValue(0);
				}

				// Tổng cá thể trên 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getTotalChildOver1YO() != null) {
					cell.setCellValue(ardf.getTotalChildOver1YO());
				} else {
					cell.setCellValue(0);
				}

				// đực trên 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getMaleChildOver1YearOld() != null) {
					cell.setCellValue(ardf.getMaleChildOver1YearOld());
				} else {
					cell.setCellValue(0);
				}
				// cái trên 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFemaleChildOver1YearOld() != null) {
					cell.setCellValue(ardf.getFemaleChildOver1YearOld());
				} else {
					cell.setCellValue(0);
				}
				// không xác định trên 1 tuổi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getUnGenderChildOver1YearOld() != null) {
					cell.setCellValue(ardf.getUnGenderChildOver1YearOld());
				} else {
					cell.setCellValue(0);
				}
				// Mã số cơ sở nuôi (theo nghị định 06/2019)
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarmNewRegistrationCode() != null && ardf.getFarmNewRegistrationCode().length()>0) {
					cell.setCellValue(ardf.getFarmNewRegistrationCode());
				} else {
					cell.setCellValue("");
				}

				// ngày được cấp mã số
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getFarmDateRegistration() != null) {
					cell.setCellValue(formatter.format(ardf.getFarmDateRegistration()));
				} else
					cell.setCellValue("");

				// mục đích nuôi
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				String target="";
				if(ardf.getT()!=null) {
					target+="T, ";
				}
				if(ardf.getZ()!=null) {
					target+="Z, ";
				}
				if(ardf.getPTM()!=null) {
					target+="PTM, ";
				}
				if(ardf.getS()!=null) {
					target+="S, ";
				}
				if(ardf.getO()!=null) {
					target+="O, ";
				}
				if(ardf.getR()!=null) {
					target+="R, ";
				}
				if(ardf.getQ()!=null) {
					target+="Q, ";
				}
				if(StringUtils.hasText(target)){
					target = target.substring(0,target.length()-2);
				}
				cell.setCellValue(target);
//				if (ardf.getProductTargets() != null && ardf.getProductTargets().length() > 0) {
//					cell.setCellValue(ardf.getProductTargets());
//				} else
//					cell.setCellValue("");

				// hình thức nuôi
//				cellIndex++;
//				cell = row.createCell(cellIndex);
//				cell.setCellStyle(cellStyle);
//				String methodFeed="";
//				if(ardf.getSS()!=null) {
//					methodFeed+="SS, ";
//				}
//				if(ardf.getST()!=null) {
//					methodFeed+="ST, ";
//				}
//				if(ardf.getSSTT()!=null) {
//					methodFeed+="SS-TT, ";
//				}
//				cell.setCellValue(methodFeed);
//
//				// trạng thái
//				cellIndex++;
//				cell = row.createCell(cellIndex);
//				cell.setCellStyle(cellStyle);
//				if (ardf.getStatusFarm() != null) {
//					cell.setCellValue(ardf.getStatusFarm());
//				}else {
//					cell.setCellValue("");
//				}

				// ghi chú
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				cell.setCellValue("");
//				if (ardf.getNote() != null && ardf.getNote().length() > 0) {
//					cell.setCellValue(ardf.getNote());
//				} else
//					cell.setCellValue("");

				// Kinh độ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);				
				if (ardf.getFarmLongitude() != null && ardf.getFarmLongitude().length()>0) {
					cell.setCellValue(ardf.getFarmLongitude());
				} else
					cell.setCellValue("");

				// Vĩ độ
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);				
				if (ardf.getFarmLatitude() != null && ardf.getFarmLatitude().length()>0) {
					cell.setCellValue(ardf.getFarmLatitude());
				} else
					cell.setCellValue("");

				// Mã xã
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getWardCode() != null && ardf.getWardCode().length()>0) {
					cell.setCellValue(ardf.getWardCode());
				} else
					cell.setCellValue("");

				// Mã huyện
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getDisCode() != null && ardf.getDisCode().length()>0) {
					cell.setCellValue(ardf.getDisCode());
				} else
					cell.setCellValue("");

				// Mã tỉnh
				cellIndex++;
				cell = row.createCell(cellIndex);
				cell.setCellStyle(cellStyle);
				if (ardf.getProvCode() != null && ardf.getProvCode().length()>0) {
					cell.setCellValue(ardf.getProvCode());
				} else
					cell.setCellValue("");

				index++;
				rowStartTable++;
			}
		}
		workbook.write(outputStream);
		workbook.close();
	}

	public static void exportReportAnimalDangerousCitesToExcelByForm(List<AnimalReportDataFromCityDto> listCity,
			String title, ServletOutputStream out, InputStream ip) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(ip);
		CreationHelper createHelper = workbook.getCreationHelper();
		if (listCity != null && listCity.size() > 0) {
			Sheet sheet = null;
			sheet = workbook.getSheetAt(0);
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("Times New Roman");
			font.setBold(true);

			Font iFont = workbook.createFont();
			iFont.setFontHeightInPoints((short) 9);
			iFont.setFontName("Times New Roman");
			iFont.setItalic(true);

			CellStyle cellStyleBoldTable = workbook.createCellStyle();
			cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
			cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
			cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
			cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
			cellStyleBoldTable.setWrapText(true);
			cellStyleBoldTable.setAlignment(HorizontalAlignment.LEFT);
			cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleBoldTable.setFont(font);

			CellStyle cellStyleBoldCenter = workbook.createCellStyle();
			cellStyleBoldCenter.setBorderBottom(BorderStyle.THIN);
			cellStyleBoldCenter.setBorderLeft(BorderStyle.THIN);
			cellStyleBoldCenter.setBorderTop(BorderStyle.THIN);
			cellStyleBoldCenter.setBorderRight(BorderStyle.THIN);
			cellStyleBoldCenter.setWrapText(true);
			cellStyleBoldCenter.setAlignment(HorizontalAlignment.CENTER);
			cellStyleBoldCenter.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleBoldCenter.setFont(font);

			CellStyle cellStyleCenterI = workbook.createCellStyle();
			cellStyleCenterI.setWrapText(true);
			cellStyleCenterI.setAlignment(HorizontalAlignment.CENTER);
			cellStyleCenterI.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleCenterI.setFont(iFont);

			CellStyle cellStyleCenter = workbook.createCellStyle();
			cellStyleCenter.setWrapText(true);
			cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
			cellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleCenter.setFont(font);

			Row headerRow = null;
			Cell headerCell = null;

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			if (title != null && title.length() > 0) {
				if (title.toLowerCase().contains("tỉnh")) {
					title = title.replace("Tỉnh ", "");
				}
				headerRow = sheet.getRow(1);
				headerCell = headerRow.getCell(0);
				try {
					title = title.trim();
					headerCell.setCellValue("SỞ NÔNG NGHIỆP VÀ PHÁT TRIỂN NÔNG THÔN " + title.toUpperCase());
				} catch (Exception e) {
					// TODO: handle exception
				}

				headerRow = sheet.getRow(3);
				headerCell = headerRow.getCell(6);
				headerCell.setCellValue(title + ", ngày     tháng      năm      ");
			}

			int rowStartTable = 14;
			int rowIndex = rowStartTable; // dòng bắt đầu
			int index = 0;
//				int totalSize = listCity.size();	
			Row templateRow = sheet.getRow(13);

			CellStyle rowStyle = templateRow.getRowStyle();
			CellStyle cell0 = templateRow.getCell(0).getCellStyle();
			CellStyle cell1 = templateRow.getCell(1).getCellStyle();
			CellStyle cell2 = templateRow.getCell(2).getCellStyle();
			CellStyle cell3 = templateRow.getCell(3).getCellStyle();
//			cell3.setAlignment(HorizontalAlignment.LEFT);
			CellStyle cell4 = templateRow.getCell(4).getCellStyle();
			CellStyle cell5 = templateRow.getCell(5).getCellStyle();
			CellStyle cell6 = templateRow.getCell(6).getCellStyle();
			CellStyle cell7 = templateRow.getCell(7).getCellStyle();
			CellStyle cell8 = templateRow.getCell(8).getCellStyle();
			CellStyle cell9 = templateRow.getCell(9).getCellStyle();
			CellStyle cell10 = templateRow.getCell(10).getCellStyle();

			CellStyle cell11 = templateRow.getCell(11).getCellStyle();
			CellStyle cell12 = templateRow.getCell(12).getCellStyle();
			CellStyle cell13 = templateRow.getCell(13).getCellStyle();
			CellStyle cell14 = templateRow.getCell(14).getCellStyle();
			CellStyle cell15 = templateRow.getCell(15).getCellStyle();
			CellStyle cell16 = templateRow.getCell(16).getCellStyle();
			CellStyle cell17 = templateRow.getCell(17).getCellStyle();
			CellStyle cell18 = templateRow.getCell(18).getCellStyle();
			CellStyle cell19 = templateRow.getCell(19).getCellStyle();
			CellStyle cell20 = templateRow.getCell(20).getCellStyle();
			CellStyle cell21 = templateRow.getCell(21).getCellStyle();
			CellStyle cell22 = templateRow.getCell(22).getCellStyle();
			CellStyle cell23 = templateRow.getCell(23).getCellStyle();
			CellStyle cell24 = templateRow.getCell(24).getCellStyle();
			CellStyle cell25 = templateRow.getCell(25).getCellStyle();
			PropertyTemplate pt = new PropertyTemplate();
			Row row = null;
			Cell cell = null;
			LocalDateTime today = LocalDateTime.now();
			short height = 300;
			int indexDistrict = 0;
			int indexD = 0;
			int indexWard = 0;
			int indexFarm = 0;
			int indexCity = 0;
			for (int i = 0; i < listCity.size(); i++) {
//					row = sheet.createRow(i+rowStartTable +indexDistrict+ indexWard);
				indexCity = 0;// mỗi tỉnh lại bắt đầu lại số thứ tự của cơ sở
				row = sheet.createRow(rowStartTable);
				cell = row.createCell(0);
				cell.setCellStyle(cell0);
				cell.setCellValue("");

				cell = row.createCell(1);
				cell.setCellStyle(cellStyleBoldCenter);
				cell.setCellValue(i + 1);
				// Tên cơ sở và địa chỉ
				cell = row.createCell(2);
				cell.setCellStyle(cellStyleBoldTable);
				if (listCity.get(i).getName() != null)
					cell.setCellValue(listCity.get(i).getName());
				else
					cell.setCellValue("");
				// tên loài nuôi
				cell = row.createCell(3);
				cell.setCellStyle(cell3);
				cell.setCellValue("");
				// Tên khoa học
				cell = row.createCell(4);
				cell.setCellStyle(cell4);
				cell.setCellValue("");
				// Tổng số cá thể
				cell = row.createCell(5);
				cell.setCellStyle(cellStyleBoldCenter);
				Integer toTal = 0;
				if (listCity.get(i).getTotalParent() != null) {
					toTal += listCity.get(i).getTotalParent();
				}
				if (listCity.get(i).getTotalGilts() != null) {
					toTal += listCity.get(i).getTotalGilts();
				}
				if (listCity.get(i).getTotalChild() != null) {
					toTal += listCity.get(i).getTotalChild();
				}
				if (listCity.get(i).getTotalChildUnder1YO() != null) {
					toTal += listCity.get(i).getTotalChildUnder1YO();
				}
				if (toTal > 0) {
					cell.setCellValue(toTal);
				} else {
					cell.setCellValue("");
				}
				// Tổng số cá thể đàn bố mẹ
				cell = row.createCell(6);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getTotalParent() != null) {
					cell.setCellValue(listCity.get(i).getTotalParent());
				} else {
					cell.setCellValue("");
				}
				// Đực bố mẹ
				cell = row.createCell(7);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getMaleParent() != null) {
					cell.setCellValue(listCity.get(i).getMaleParent());
				} else {
					cell.setCellValue("");
				}
				// Cái bố mẹ
				cell = row.createCell(8);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getFemaleParent() != null) {
					cell.setCellValue(listCity.get(i).getFemaleParent());
				} else {
					cell.setCellValue("");
				}
				// Tổng số cá thể đàn hậu bị
				cell = row.createCell(9);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getTotalGilts() != null) {
					cell.setCellValue(listCity.get(i).getTotalGilts());
				} else {
					cell.setCellValue("");
				}
				// đực hậu bị
				cell = row.createCell(10);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getMaleGilts() != null) {
					cell.setCellValue(listCity.get(i).getMaleGilts());
				} else {
					cell.setCellValue("");
				}
				// cái hậu bị
				cell = row.createCell(11);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getFemaleGilts() != null) {
					cell.setCellValue(listCity.get(i).getFemaleGilts());
				} else {
					cell.setCellValue("");
				}
				// con dưới 1 tuổi
				cell = row.createCell(12);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getTotalChildUnder1YO() != null) {
					cell.setCellValue(listCity.get(i).getTotalChildUnder1YO());
				} else {
					cell.setCellValue("");
				}
				// Tổng cá thể trên 1 tuổi
				cell = row.createCell(13);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getTotalChild() != null) {
					cell.setCellValue(listCity.get(i).getTotalChild());
				} else {
					cell.setCellValue("");
				}
				// đực trên 1 tuổi
				cell = row.createCell(14);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getMaleChild() != null) {
					cell.setCellValue(listCity.get(i).getMaleChild());
				} else {
					cell.setCellValue("");
				}
				// cái trên 1 tuổi
				cell = row.createCell(15);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getFemaleChild() != null) {
					cell.setCellValue(listCity.get(i).getFemaleChild());
				} else {
					cell.setCellValue("");
				}
				// không xác định trên 1 tuổi
				cell = row.createCell(16);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getUnGenderChild() != null) {
					cell.setCellValue(listCity.get(i).getUnGenderChild());
				} else {
					cell.setCellValue("");
				}
				// mã số cơ sở nuôi
				cell = row.createCell(17);
				cell.setCellStyle(cell17);
				cell.setCellValue("");

				// ngày được cấp mã số
				cell = row.createCell(18);
				cell.setCellStyle(cell18);
				cell.setCellValue("");
				// mục đích nuôi
				cell = row.createCell(19);
				cell.setCellStyle(cell19);
				cell.setCellValue("");
				// ghi chú
				cell = row.createCell(20);
				cell.setCellStyle(cell20);
				cell.setCellValue("");
				// mã loài
				cell = row.createCell(21);
				cell.setCellStyle(cell21);
				cell.setCellValue("");
				// mã hệ thống
				cell = row.createCell(22);
				cell.setCellStyle(cell22);
				cell.setCellValue("");
				rowStartTable++;
				// mã xã
				cell = row.createCell(23);
				cell.setCellStyle(cell23);
				cell.setCellValue("");
				rowStartTable++;
				// mã quận/huyện
				cell = row.createCell(24);
				cell.setCellStyle(cell24);
				cell.setCellValue("");
				// mã tỉnh/thành phố
				cell = row.createCell(25);
				cell.setCellStyle(cell25);
				cell.setCellValue("");

				rowStartTable++;
				// view cấp huyện
				indexDistrict = 0;
				if (listCity.get(i).getListDistrict() != null && listCity.get(i).getListDistrict().size() > 0) {
					indexDistrict = listCity.get(i).getListDistrict().size();
					List<AnimalReportDataFromDistrictDto> listDistrict = listCity.get(i).getListDistrict();
					for (int d = 0; d < listDistrict.size(); d++) {
						index = 0;// mỗi huyện lại bắt đầu lại số thứ tự của cơ sở
//							row = sheet.createRow(i+rowStartTable +indexD+d+2);
						row = sheet.createRow(rowStartTable);
						cell = row.createCell(0);
						cell.setCellStyle(cell0);
						cell.setCellValue("");
						cell = row.createCell(1);
						cell.setCellStyle(cellStyleBoldCenter);
						// chuyển số nguyên sang bảng chữ cái
						cell.setCellValue(NumberUtils.CharNumerals(d + 1));
//						cell.setCellValue(d+1);
						// Tên cơ sở và địa chỉ
						cell = row.createCell(2);
						cell.setCellStyle(cellStyleBoldTable);
						if (listDistrict.get(d).getName() != null)
							cell.setCellValue(listDistrict.get(d).getName());
						else
							cell.setCellValue("");
						// tên loài nuôi
						cell = row.createCell(3);
						cell.setCellStyle(cell3);
						cell.setCellValue("");
						// Tên khoa học
						cell = row.createCell(4);
						cell.setCellStyle(cell4);
						cell.setCellValue("");
						// Tổng số cá thể
						cell = row.createCell(5);
						cell.setCellStyle(cellStyleBoldCenter);
						toTal = 0;
						if (listDistrict.get(d).getTotalParent() != null) {
							toTal += listDistrict.get(d).getTotalParent();
						}
						if (listDistrict.get(d).getTotalGilts() != null) {
							toTal += listDistrict.get(d).getTotalGilts();
						}
						if (listDistrict.get(d).getTotalChild() != null) {
							toTal += listDistrict.get(d).getTotalChild();
						}
						if (listDistrict.get(d).getTotalChildUnder1YO() != null) {
							toTal += listDistrict.get(d).getTotalChildUnder1YO();
						}
						if (toTal > 0) {
							cell.setCellValue(toTal);
						} else {
							cell.setCellValue("");
						}
						// Tổng số cá thể đàn bố mẹ
						cell = row.createCell(6);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getTotalParent() != null) {
							cell.setCellValue(listDistrict.get(d).getTotalParent());
						} else {
							cell.setCellValue("");
						}
						// Đực bố mẹ
						cell = row.createCell(7);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getMaleParent() != null) {
							cell.setCellValue(listDistrict.get(d).getMaleParent());
						} else {
							cell.setCellValue("");
						}
						// Cái bố mẹ
						cell = row.createCell(8);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getFemaleParent() != null) {
							cell.setCellValue(listDistrict.get(d).getFemaleParent());
						} else {
							cell.setCellValue("");
						}
						// Tổng số cá thể đàn hậu bị
						cell = row.createCell(9);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getTotalGilts() != null) {
							cell.setCellValue(listDistrict.get(d).getTotalGilts());
						} else {
							cell.setCellValue("");
						}
						// đực hậu bị
						cell = row.createCell(10);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getMaleGilts() != null) {
							cell.setCellValue(listDistrict.get(d).getMaleGilts());
						} else {
							cell.setCellValue("");
						}
						// cái hậu bị
						cell = row.createCell(11);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getFemaleGilts() != null) {
							cell.setCellValue(listDistrict.get(d).getFemaleGilts());
						} else {
							cell.setCellValue("");
						}
						// con dưới 1 tuổi
						cell = row.createCell(12);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getTotalChildUnder1YO() != null) {
							cell.setCellValue(listDistrict.get(d).getTotalChildUnder1YO());
						} else {
							cell.setCellValue("");
						}
						// Tổng cá thể trên 1 tuổi
						cell = row.createCell(13);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getTotalChild() != null) {
							cell.setCellValue(listDistrict.get(d).getTotalChild());
						} else {
							cell.setCellValue("");
						}
						// đực trên 1 tuổi
						cell = row.createCell(14);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getMaleChild() != null) {
							cell.setCellValue(listDistrict.get(d).getMaleChild());
						} else {
							cell.setCellValue("");
						}
						// cái trên 1 tuổi
						cell = row.createCell(15);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getFemaleChild() != null) {
							cell.setCellValue(listDistrict.get(d).getFemaleChild());
						} else {
							cell.setCellValue("");
						}
						// không xác định trên 1 tuổi
						cell = row.createCell(16);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getUnGenderChild() != null) {
							cell.setCellValue(listDistrict.get(d).getUnGenderChild());
						} else {
							cell.setCellValue("");
						}
						// mã số cơ sở nuôi
						cell = row.createCell(17);
						cell.setCellStyle(cell17);
						cell.setCellValue("");

						// ngày được cấp mã số
						cell = row.createCell(18);
						cell.setCellStyle(cell18);
						cell.setCellValue("");
						// mục đích nuôi
						cell = row.createCell(19);
						cell.setCellStyle(cell19);
						cell.setCellValue("");
						// ghi chú
						cell = row.createCell(20);
						cell.setCellStyle(cell20);
						cell.setCellValue("");
						// mã loài
						cell = row.createCell(21);
						cell.setCellStyle(cell21);
						cell.setCellValue("");
						// mã hệ thống
						cell = row.createCell(22);
						cell.setCellStyle(cell22);
						cell.setCellValue("");
						rowStartTable++;
						// mã xã
						cell = row.createCell(23);
						cell.setCellStyle(cell23);
						cell.setCellValue("");

						// mã quận/huyện
						cell = row.createCell(24);
						cell.setCellStyle(cell24);
						cell.setCellValue("");
						// mã Tỉnh
						cell = row.createCell(25);
						cell.setCellStyle(cell25);
						cell.setCellValue("");

						rowStartTable++;
						// view cấp xã
						indexWard = 0;
						if (listDistrict.get(d).getListWard() != null && listDistrict.get(d).getListWard().size() > 0) {
							indexWard = listDistrict.get(d).getListWard().size();
							List<AnimalReportDataFromWardDto> listWard = listDistrict.get(d).getListWard();
							for (int w = 0; w < listWard.size(); w++) {
//									row = sheet.createRow(i+rowStartTable +indexD+w+2);
								row = sheet.createRow(rowStartTable);
								cell = row.createCell(0);
								cell.setCellStyle(cell0);
								cell.setCellValue("");
								cell = row.createCell(1);
								cell.setCellStyle(cellStyleBoldCenter);
								// chuyển số nguyên sang số la mã
								cell.setCellValue(NumberUtils.RomanNumerals(w + 1));
								// Tên cơ sở và địa chỉ
								cell = row.createCell(2);
								cell.setCellStyle(cellStyleBoldTable);
								if (listWard.get(w).getName() != null)
									cell.setCellValue(listWard.get(w).getName());
								else
									cell.setCellValue("");
								// tên loài nuôi
								cell = row.createCell(3);
								cell.setCellStyle(cell3);
								cell.setCellValue("");
								// Tên khoa học
								cell = row.createCell(4);
								cell.setCellStyle(cell4);
								cell.setCellValue("");
								// Tổng số cá thể
								cell = row.createCell(5);
								cell.setCellStyle(cellStyleBoldCenter);
								toTal = 0;
								if (listWard.get(w).getTotalParent() != null) {
									toTal += listWard.get(w).getTotalParent();
								}
								if (listWard.get(w).getTotalGilts() != null) {
									toTal += listWard.get(w).getTotalGilts();
								}
								if (listWard.get(w).getTotalChild() != null) {
									toTal += listWard.get(w).getTotalChild();
								}
								if (listWard.get(w).getTotalChildUnder1YO() != null) {
									toTal += listWard.get(w).getTotalChildUnder1YO();
								}
								if (toTal > 0) {
									cell.setCellValue(toTal);
								} else {
									cell.setCellValue("");
								}
								// Tổng số cá thể đàn bố mẹ
								cell = row.createCell(6);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getTotalParent() != null) {
									cell.setCellValue(listWard.get(w).getTotalParent());
								} else {
									cell.setCellValue("");
								}
								// Đực bố mẹ
								cell = row.createCell(7);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getMaleParent() != null) {
									cell.setCellValue(listWard.get(w).getMaleParent());
								} else {
									cell.setCellValue("");
								}
								// Cái bố mẹ
								cell = row.createCell(8);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getFemaleParent() != null) {
									cell.setCellValue(listWard.get(w).getFemaleParent());
								} else {
									cell.setCellValue("");
								}
								// Tổng số cá thể đàn hậu bị
								cell = row.createCell(9);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getTotalGilts() != null) {
									cell.setCellValue(listWard.get(w).getTotalGilts());
								} else {
									cell.setCellValue("");
								}
								// đực hậu bị
								cell = row.createCell(10);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getMaleGilts() != null) {
									cell.setCellValue(listWard.get(w).getMaleGilts());
								} else {
									cell.setCellValue("");
								}
								// cái hậu bị
								cell = row.createCell(11);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getFemaleGilts() != null) {
									cell.setCellValue(listWard.get(w).getFemaleGilts());
								} else {
									cell.setCellValue("");
								}
								// con dưới 1 tuổi
								cell = row.createCell(12);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getTotalChildUnder1YO() != null) {
									cell.setCellValue(listWard.get(w).getTotalChildUnder1YO());
								} else {
									cell.setCellValue("");
								}
								// Tổng cá thể trên 1 tuổi
								cell = row.createCell(13);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getTotalChild() != null) {
									cell.setCellValue(listWard.get(w).getTotalChild());
								} else {
									cell.setCellValue("");
								}
								// đực trên 1 tuổi
								cell = row.createCell(14);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getMaleChild() != null) {
									cell.setCellValue(listWard.get(w).getMaleChild());
								} else {
									cell.setCellValue("");
								}
								// cái trên 1 tuổi
								cell = row.createCell(15);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getFemaleChild() != null) {
									cell.setCellValue(listWard.get(w).getFemaleChild());
								} else {
									cell.setCellValue("");
								}
								// không xác định trên 1 tuổi
								cell = row.createCell(16);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getUnGenderChild() != null) {
									cell.setCellValue(listWard.get(w).getUnGenderChild());
								} else {
									cell.setCellValue("");
								}
								// mã số cơ sở nuôi
								cell = row.createCell(17);
								cell.setCellStyle(cell17);
								cell.setCellValue("");

								// ngày được cấp mã số
								cell = row.createCell(18);
								cell.setCellStyle(cell18);
								cell.setCellValue("");
								// mục đích nuôi
								cell = row.createCell(19);
								cell.setCellStyle(cell19);
								cell.setCellValue("");
								// ghi chú
								cell = row.createCell(20);
								cell.setCellStyle(cell20);
								cell.setCellValue("");
								// mã loài
								cell = row.createCell(21);
								cell.setCellStyle(cell21);
								cell.setCellValue("");
								// mã hệ thống
								cell = row.createCell(22);
								cell.setCellStyle(cell22);
								cell.setCellValue("");
								rowStartTable++;
								// mã xã
								cell = row.createCell(23);
								cell.setCellStyle(cell23);
								cell.setCellValue("");
								// mã quận/huyện
								cell = row.createCell(24);
								cell.setCellStyle(cell24);
								cell.setCellValue("");
								// mã tỉnh
								cell = row.createCell(25);
								cell.setCellStyle(cell25);
								cell.setCellValue("");

								rowStartTable++;
								// view phần cơ sở
								indexFarm = 0;
								if (listWard.get(w).getList() != null && listWard.get(w).getList().size() > 0) {
									List<AnimalReportDataFormDto> listFarm = listWard.get(w).getList();
									indexFarm = listWard.get(w).getList().size();
									// danh sách cơ sở duy nhất
									Hashtable<Long, AnimalReportDataFormDto> hashFarm = new Hashtable<Long, AnimalReportDataFormDto>();
									for (int f = 0; f < listFarm.size(); f++) {
										row = sheet.createRow(rowStartTable);
										// stt
										boolean isIndex = true;
										int indexDup = 0;
										List<AnimalReportDataFormDto> listFarmDup = listWard.get(w).getList();
										AnimalReportDataFormDto farm = hashFarm.get(listFarm.get(f).getFarm().getId());
										if (farm == null) {
											farm = new AnimalReportDataFormDto();
											farm = listFarm.get(f);
											hashFarm.put(farm.getFarm().getId(), farm);
											isIndex = true;
											for (int dup = 0; dup < listFarmDup.size(); dup++) {
												if (listFarm.get(f).getFarm() != null
														&& listFarm.get(f).getFarm().getId() != null
														&& listFarmDup.get(dup).getFarm() != null
														&& listFarmDup.get(dup).getFarm().getId() != null
														&& listFarmDup.get(dup).getFarm().getId()
																.equals(listFarm.get(f).getFarm().getId())) {
													indexDup++;
												}
											}

										} else {
											isIndex = false;
										}
										if (isIndex && indexDup <= 1) {
											cell = row.createCell(0);
											cell.setCellStyle(cell0);
											cell.setCellValue(indexCity + 1);

											cell = row.createCell(1);
											cell.setCellStyle(cell1);
											cell.setCellValue(index + 1);

											// Tên cơ sở và địa chỉ
											cell = row.createCell(2);
											cell.setCellStyle(cell2);
											if (listFarm.get(f).getFarm() != null
													&& listFarm.get(f).getFarm().getName() != null)
												cell.setCellValue(listFarm.get(f).getFarm().getName());
											else
												cell.setCellValue("");
										} else if (isIndex && indexDup > 1) {
											indexDup = indexDup - 1;
											cell = row.createCell(0);
											cell.setCellStyle(cell0);
											cell.setCellValue(indexCity + 1);
											sheet.addMergedRegion(new CellRangeAddress(rowStartTable,
													rowStartTable + indexDup, 0, 0));
											pt.drawBorders(
													new CellRangeAddress(rowStartTable, rowStartTable + indexDup, 0, 0),
													BorderStyle.THIN, BorderExtent.ALL);
											cell = row.createCell(1);
											cell.setCellStyle(cell1);
											cell.setCellValue(index + 1);
											sheet.addMergedRegion(new CellRangeAddress(rowStartTable,
													rowStartTable + indexDup, 1, 1));
											pt.drawBorders(
													new CellRangeAddress(rowStartTable, rowStartTable + indexDup, 1, 1),
													BorderStyle.THIN, BorderExtent.ALL);
											// Tên cơ sở và địa chỉ
											cell = row.createCell(2);
											cell.setCellStyle(cell2);
											if (listFarm.get(f).getFarm() != null
													&& listFarm.get(f).getFarm().getName() != null)
												cell.setCellValue(listFarm.get(f).getFarm().getName());
											else
												cell.setCellValue("");
											sheet.addMergedRegion(new CellRangeAddress(rowStartTable,
													rowStartTable + indexDup, 2, 2));
											pt.drawBorders(
													new CellRangeAddress(rowStartTable, rowStartTable + indexDup, 2, 2),
													BorderStyle.THIN, BorderExtent.ALL);
										}

										// tên loài nuôi
										cell = row.createCell(3);
										cell.setCellStyle(cell3);
										if (listFarm.get(f).getAnimal() != null
												&& listFarm.get(f).getAnimal().getName() != null) {
											cell.setCellValue(listFarm.get(f).getAnimal().getName());
										} else {
											cell.setCellValue("");
										}
										// Tên khoa học
										cell = row.createCell(4);
										cell.setCellStyle(cell4);
										if (listFarm.get(f).getAnimal() != null
												&& listFarm.get(f).getAnimal().getScienceName() != null) {
											cell.setCellValue(listFarm.get(f).getAnimal().getScienceName());
										} else {
											cell.setCellValue("");
										}
										// Tổng số cá thể
										cell = row.createCell(5);
										cell.setCellStyle(cell5);
										toTal = 0;
										if (listFarm.get(f).getTotalParent() != null) {
											toTal += listFarm.get(f).getTotalParent();
										}
										if (listFarm.get(f).getTotalGilts() != null) {
											toTal += listFarm.get(f).getTotalGilts();
										}
										if (listFarm.get(f).getTotalChild() != null) {
											toTal += listFarm.get(f).getTotalChild();
										}
										if (listFarm.get(f).getTotalChildUnder1YO() != null) {
											toTal += listFarm.get(f).getTotalChildUnder1YO();
										}
										if (toTal > 0) {
											cell.setCellValue(toTal);
										} else {
											cell.setCellValue("");
										}
										// Tổng số cá thể đàn bố mẹ
										cell = row.createCell(6);
										cell.setCellStyle(cell6);
										if (listFarm.get(f).getTotalParent() != null) {
											cell.setCellValue(listFarm.get(f).getTotalParent());
										} else {
											cell.setCellValue("");
										}
										// Đực bố mẹ
										cell = row.createCell(7);
										cell.setCellStyle(cell7);
										if (listFarm.get(f).getMaleParent() != null) {
											cell.setCellValue(listFarm.get(f).getMaleParent());
										} else {
											cell.setCellValue("");
										}
										// Cái bố mẹ
										cell = row.createCell(8);
										cell.setCellStyle(cell8);
										if (listFarm.get(f).getFemaleParent() != null) {
											cell.setCellValue(listFarm.get(f).getFemaleParent());
										} else {
											cell.setCellValue("");
										}
										// Tổng số cá thể đàn hậu bị
										cell = row.createCell(9);
										cell.setCellStyle(cell9);
										if (listFarm.get(f).getTotalGilts() != null) {
											cell.setCellValue(listFarm.get(f).getTotalGilts());
										} else {
											cell.setCellValue("");
										}
										// đực hậu bị
										cell = row.createCell(10);
										cell.setCellStyle(cell10);
										if (listFarm.get(f).getMaleGilts() != null) {
											cell.setCellValue(listFarm.get(f).getMaleGilts());
										} else {
											cell.setCellValue("");
										}
										// cái hậu bị
										cell = row.createCell(11);
										cell.setCellStyle(cell11);
										if (listFarm.get(f).getFemaleGilts() != null) {
											cell.setCellValue(listFarm.get(f).getFemaleGilts());
										} else {
											cell.setCellValue("");
										}
										// con dưới 1 tuổi
										cell = row.createCell(12);
										cell.setCellStyle(cell12);
										if (listFarm.get(f).getTotalChildUnder1YO() != null) {
											cell.setCellValue(listFarm.get(f).getTotalChildUnder1YO());
										} else {
											cell.setCellValue("");
										}
										// Tổng cá thể trên 1 tuổi
										cell = row.createCell(13);
										cell.setCellStyle(cell13);
										if (listFarm.get(f).getTotalChild() != null) {
											cell.setCellValue(listFarm.get(f).getTotalChild());
										} else {
											cell.setCellValue("");
										}
										// đực trên 1 tuổi
										cell = row.createCell(14);
										cell.setCellStyle(cell14);
										if (listFarm.get(f).getMaleChild() != null) {
											cell.setCellValue(listFarm.get(f).getMaleChild());
										} else {
											cell.setCellValue("");
										}
										// cái trên 1 tuổi
										cell = row.createCell(15);
										cell.setCellStyle(cell15);
										if (listFarm.get(f).getFemaleChild() != null) {
											cell.setCellValue(listFarm.get(f).getFemaleChild());
										} else {
											cell.setCellValue("");
										}
										// không xác định trên 1 tuổi
										cell = row.createCell(16);
										cell.setCellStyle(cell16);
										if (listFarm.get(f).getUnGenderChild() != null) {
											cell.setCellValue(listFarm.get(f).getUnGenderChild());
										} else {
											cell.setCellValue("");
										}
										// mã số cơ sở nuôi mã mới
										cell = row.createCell(17);
										cell.setCellStyle(cell17);
										if (listFarm.get(f).getFarm() != null
												&& listFarm.get(f).getFarm().getNewRegistrationCode() != null) {
											cell.setCellValue(listFarm.get(f).getFarm().getNewRegistrationCode());
										} else
											cell.setCellValue("");
										// ngày được cấp mã số
										cell = row.createCell(18);
										cell.setCellStyle(cell18);
										if (listFarm.get(f).getFarm() != null
												&& listFarm.get(f).getFarm().getFoundingDate() != null) {
											cell.setCellValue(
													formatter.format(listFarm.get(f).getFarm().getFoundingDate()));
										} else
											cell.setCellValue("");
										// mục đích nuôi
										cell = row.createCell(19);
										cell.setCellStyle(cell19);
										if (listFarm.get(f).getProductTargets() != null
												&& listFarm.get(f).getProductTargets().length() > 0) {
											cell.setCellValue(listFarm.get(f).getProductTargets());
										} else
											cell.setCellValue("");
										// ghi chú
										cell = row.createCell(20);
										cell.setCellStyle(cell20);
										if (listFarm.get(f).getNote() != null
												&& listFarm.get(f).getNote().length() > 0) {
											cell.setCellValue(listFarm.get(f).getNote());
										} else
											cell.setCellValue("");
										// mã loài
										cell = row.createCell(21);
										cell.setCellStyle(cell21);
										if (listFarm.get(f).getAnimal() != null
												&& listFarm.get(f).getAnimal().getCode() != null) {
											cell.setCellValue(listFarm.get(f).getAnimal().getCode());
										} else
											cell.setCellValue("");
										// mã hệ thống
										cell = row.createCell(22);
										cell.setCellStyle(cell22);
										if (listFarm.get(f).getFarm() != null
												&& listFarm.get(f).getFarm().getCode() != null) {
											cell.setCellValue(listFarm.get(f).getFarm().getCode());
										} else
											cell.setCellValue("");
										// mã xã
										cell = row.createCell(23);
										cell.setCellStyle(cell23);
										if (listFarm.get(f).getAdministrativeUnitDto() != null
												&& listFarm.get(f).getAdministrativeUnitDto().getCode() != null) {
											cell.setCellValue(listFarm.get(f).getAdministrativeUnitDto().getCode());
										} else
											cell.setCellValue("");

										// mã quận/huyện
										cell = row.createCell(24);
										cell.setCellStyle(cell24);
										if (listFarm.get(f).getDistrictDto() != null
												&& listFarm.get(f).getDistrictDto().getCode() != null) {
											cell.setCellValue(listFarm.get(f).getDistrictDto().getCode());
										} else
											cell.setCellValue("");
										// mã tỉnh thành phố
										cell = row.createCell(25);
										cell.setCellStyle(cell25);
										if (listFarm.get(f).getProvinceDto() != null
												&& listFarm.get(f).getProvinceDto().getCode() != null) {
											cell.setCellValue(listFarm.get(f).getProvinceDto().getCode());
										} else
											cell.setCellValue("");
										rowStartTable++;
										if (isIndex) {
											index++;
											indexCity++;
										}

									}
									indexD = indexD + indexFarm;
								}
							}
							indexD = indexD + indexWard;
						}
					}
					indexD = indexD + indexDistrict;
				}
			}

			pt.applyBorders(sheet);
			Row footerRow = sheet.createRow(rowStartTable + 2);
			Cell footerCell = footerRow.createCell(1);
			footerCell.setCellValue("NGƯỜI LẬP");
			footerCell.setCellStyle(cellStyleCenter);
			sheet.addMergedRegion(new CellRangeAddress(rowStartTable + 2, rowStartTable + 2, 1, 5));
			footerCell = footerRow.createCell(12);
			footerCell.setCellValue("THỦ TRƯỞNG ĐƠN VỊ");
			footerCell.setCellStyle(cellStyleCenter);
			sheet.addMergedRegion(new CellRangeAddress(rowStartTable + 2, rowStartTable + 2, 12, 19));

			Row sfooterRow = sheet.createRow(rowStartTable + 3);
			footerCell = sfooterRow.createCell(1);
			footerCell.setCellValue("(Ký và ghi rõ họ tên)");
			footerCell.setCellStyle(cellStyleCenterI);
			sheet.addMergedRegion(new CellRangeAddress(rowStartTable + 3, rowStartTable + 3, 1, 5));
			footerCell = sfooterRow.createCell(12);
			footerCell.setCellValue("(Ký, ghi rõ họ tên và đóng dấu)");
			footerCell.setCellStyle(cellStyleCenterI);
			sheet.addMergedRegion(new CellRangeAddress(rowStartTable + 3, rowStartTable + 3, 12, 19));
			System.out.print("index: " + rowIndex);
//			}
		}
		workbook.write(out);
		workbook.close();
	}
	
	public static void exportReportAnimalDangerousCitesToExcelByFormNativeQuery(List<Report18CityDto> listCity,
			String title, ServletOutputStream out, InputStream ip) throws IOException {

		XSSFWorkbook workbook = new XSSFWorkbook(ip);
		CreationHelper createHelper = workbook.getCreationHelper();
		if (listCity != null && listCity.size() > 0) {
			Sheet sheet = null;
			sheet = workbook.getSheetAt(0);
			Font font = workbook.createFont();
			font.setFontHeightInPoints((short) 12);
			font.setFontName("Times New Roman");
			font.setBold(true);

			Font iFont = workbook.createFont();
			iFont.setFontHeightInPoints((short) 9);
			iFont.setFontName("Times New Roman");
			iFont.setItalic(true);

			CellStyle cellStyleBoldTable = workbook.createCellStyle();
			cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
			cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
			cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
			cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
			cellStyleBoldTable.setWrapText(true);
			cellStyleBoldTable.setAlignment(HorizontalAlignment.LEFT);
			cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleBoldTable.setFont(font);

			CellStyle cellStyleBoldCenter = workbook.createCellStyle();
			cellStyleBoldCenter.setBorderBottom(BorderStyle.THIN);
			cellStyleBoldCenter.setBorderLeft(BorderStyle.THIN);
			cellStyleBoldCenter.setBorderTop(BorderStyle.THIN);
			cellStyleBoldCenter.setBorderRight(BorderStyle.THIN);
			cellStyleBoldCenter.setWrapText(true);
			cellStyleBoldCenter.setAlignment(HorizontalAlignment.CENTER);
			cellStyleBoldCenter.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleBoldCenter.setFont(font);
		

			CellStyle cellStyleCenterI = workbook.createCellStyle();
			cellStyleCenterI.setWrapText(true);
			cellStyleCenterI.setAlignment(HorizontalAlignment.CENTER);
			cellStyleCenterI.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleCenterI.setFont(iFont);

			CellStyle cellStyleCenter = workbook.createCellStyle();
			cellStyleCenter.setWrapText(true);
			cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
			cellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
			cellStyleCenter.setFont(font);
			
			CellStyle cellHidden = workbook.createCellStyle();
			cellHidden.setBorderBottom(BorderStyle.THIN);
			cellHidden.setBorderLeft(BorderStyle.THIN);
			cellHidden.setBorderTop(BorderStyle.THIN);
			cellHidden.setBorderRight(BorderStyle.THIN);
			cellHidden.setWrapText(true);
			cellHidden.setAlignment(HorizontalAlignment.CENTER);
			cellHidden.setVerticalAlignment(VerticalAlignment.CENTER);
			cellHidden.setFont(font);
			cellHidden.setHidden(true);
			
			

			Row headerRow = null;
			Cell headerCell = null;

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			if (title != null && title.length() > 0) {
				if (title.toLowerCase().contains("tỉnh")) {
					title = title.replace("Tỉnh ", "");
				}
				headerRow = sheet.getRow(1);
				headerCell = headerRow.getCell(0);
				try {
					title = title.trim();
					headerCell.setCellValue("SỞ NÔNG NGHIỆP VÀ PHÁT TRIỂN NÔNG THÔN " + title.toUpperCase());
				} catch (Exception e) {
					// TODO: handle exception
				}

				headerRow = sheet.getRow(3);
				headerCell = headerRow.getCell(6);
				headerCell.setCellValue(title + ", ngày     tháng      năm      ");
			}

			int rowStartTable = 14;
			int rowIndex = rowStartTable; // dòng bắt đầu
			int index = 0;
//				int totalSize = listCity.size();	
			Row templateRow = sheet.getRow(13);

			CellStyle rowStyle = templateRow.getRowStyle();
			CellStyle cell0 = templateRow.getCell(0).getCellStyle();
			CellStyle cell1 = templateRow.getCell(1).getCellStyle();
			CellStyle cell2 = templateRow.getCell(2).getCellStyle();
			CellStyle cell3 = templateRow.getCell(3).getCellStyle();
//			cell3.setAlignment(HorizontalAlignment.LEFT);
			CellStyle cell4 = templateRow.getCell(4).getCellStyle();
			CellStyle cell5 = templateRow.getCell(5).getCellStyle();
			CellStyle cell6 = templateRow.getCell(6).getCellStyle();
			CellStyle cell7 = templateRow.getCell(7).getCellStyle();
			CellStyle cell8 = templateRow.getCell(8).getCellStyle();
			CellStyle cell9 = templateRow.getCell(9).getCellStyle();
			CellStyle cell10 = templateRow.getCell(10).getCellStyle();

			CellStyle cell11 = templateRow.getCell(11).getCellStyle();
			CellStyle cell12 = templateRow.getCell(12).getCellStyle();
			CellStyle cell13 = templateRow.getCell(13).getCellStyle();
			CellStyle cell14 = templateRow.getCell(14).getCellStyle();
			CellStyle cell15 = templateRow.getCell(15).getCellStyle();
			CellStyle cell16 = templateRow.getCell(16).getCellStyle();
			CellStyle cell17 = templateRow.getCell(17).getCellStyle();
			CellStyle cell18 = templateRow.getCell(18).getCellStyle();
			CellStyle cell19 = templateRow.getCell(19).getCellStyle();
			CellStyle cell20 = templateRow.getCell(20).getCellStyle();
			CellStyle cell21 = templateRow.getCell(21).getCellStyle();
			CellStyle cell22 = templateRow.getCell(22).getCellStyle();
			CellStyle cell23 = templateRow.getCell(23).getCellStyle();
			CellStyle cell24 = templateRow.getCell(24).getCellStyle();
			CellStyle cell25 = templateRow.getCell(25).getCellStyle();
			CellStyle cell29 = templateRow.getCell(29).getCellStyle();
			cell29.setWrapText(true);		
			cell29.setAlignment(HorizontalAlignment.CENTER);
			cell29.setVerticalAlignment(VerticalAlignment.CENTER);
			
			PropertyTemplate pt = new PropertyTemplate();
			Row row = null;
			Cell cell = null;
			LocalDateTime today = LocalDateTime.now();
			short height = 300;
			int indexDistrict = 0;
			int indexD = 0;
			int indexWard = 0;
			int indexFarm = 0;
			int indexCity = 0;
			for (int i = 0; i < listCity.size(); i++) {
//					row = sheet.createRow(i+rowStartTable +indexDistrict+ indexWard);
				indexCity = 0;// mỗi tỉnh lại bắt đầu lại số thứ tự của cơ sở
				row = sheet.createRow(rowStartTable);
				cell = row.createCell(0);
				cell.setCellStyle(cell0);
				cell.setCellValue("");

				cell = row.createCell(1);
				cell.setCellStyle(cellStyleBoldCenter);
				cell.setCellValue(i + 1);
				// Tên cơ sở và địa chỉ
				cell = row.createCell(2);
				cell.setCellStyle(cellStyleBoldTable);
				if (listCity.get(i).getName() != null)
					cell.setCellValue(listCity.get(i).getName());
				else
					cell.setCellValue("");
				// tên loài nuôi
				cell = row.createCell(3);
				cell.setCellStyle(cell3);
				cell.setCellValue("");
				// Tên khoa học
				cell = row.createCell(4);
				cell.setCellStyle(cell4);
				cell.setCellValue("");
				// Tổng số cá thể
				cell = row.createCell(5);
				cell.setCellStyle(cellStyleBoldCenter);
//				Integer toTal = 0;
//				if (listCity.get(i).getTotalParent() != null) {
//					toTal += listCity.get(i).getTotalParent();
//				}
//				if (listCity.get(i).getTotalGilts() != null) {
//					toTal += listCity.get(i).getTotalGilts();
//				}
//				if (listCity.get(i).getTotalChild() != null) {
//					toTal += listCity.get(i).getTotalChild();
//				}
//				if (listCity.get(i).getTotalChildUnder1YO() != null) {
//					toTal += listCity.get(i).getTotalChildUnder1YO();
//				}
				if (listCity.get(i).getTotal()!=null) {
					cell.setCellValue(listCity.get(i).getTotal());
				} else {
					cell.setCellValue("");
				}
				// Tổng số cá thể đàn bố mẹ
				cell = row.createCell(6);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getTotalParent() != null) {
					cell.setCellValue(listCity.get(i).getTotalParent());
				} else {
					cell.setCellValue("");
				}
				// Đực bố mẹ
				cell = row.createCell(7);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getMaleParent() != null) {
					cell.setCellValue(listCity.get(i).getMaleParent());
				} else {
					cell.setCellValue("");
				}
				// Cái bố mẹ
				cell = row.createCell(8);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getFemaleParent() != null) {
					cell.setCellValue(listCity.get(i).getFemaleParent());
				} else {
					cell.setCellValue("");
				}
				// Tổng số cá thể đàn hậu bị
				cell = row.createCell(9);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getTotalGilts() != null) {
					cell.setCellValue(listCity.get(i).getTotalGilts());
				} else {
					cell.setCellValue("");
				}
				// đực hậu bị
				cell = row.createCell(10);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getMaleGilts() != null) {
					cell.setCellValue(listCity.get(i).getMaleGilts());
				} else {
					cell.setCellValue("");
				}
				// cái hậu bị
				cell = row.createCell(11);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getFemaleGilts() != null) {
					cell.setCellValue(listCity.get(i).getFemaleGilts());
				} else {
					cell.setCellValue("");
				}
				// con dưới 1 tuổi
				cell = row.createCell(12);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getTotalChildUnder1YO() != null) {
					cell.setCellValue(listCity.get(i).getTotalChildUnder1YO());
				} else {
					cell.setCellValue("");
				}
				// Tổng cá thể trên 1 tuổi
				cell = row.createCell(13);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getTotalChildOver1YO() != null) {
					cell.setCellValue(listCity.get(i).getTotalChildOver1YO());
				} else {
					cell.setCellValue("");
				}
				// đực trên 1 tuổi
				cell = row.createCell(14);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getMaleChildOver1YearOld() != null) {
					cell.setCellValue(listCity.get(i).getMaleChildOver1YearOld());
				} else {
					cell.setCellValue("");
				}
				// cái trên 1 tuổi
				cell = row.createCell(15);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getFemaleChildOver1YearOld() != null) {
					cell.setCellValue(listCity.get(i).getFemaleChildOver1YearOld());
				} else {
					cell.setCellValue("");
				}
				// không xác định trên 1 tuổi
				cell = row.createCell(16);
				cell.setCellStyle(cellStyleBoldCenter);
				if (listCity.get(i).getUnGenderChildOver1YearOld() != null) {
					cell.setCellValue(listCity.get(i).getUnGenderChildOver1YearOld());
				} else {
					cell.setCellValue("");
				}
				// mã số cơ sở nuôi
				cell = row.createCell(17);
				cell.setCellStyle(cell17);
				cell.setCellValue("");

				// ngày được cấp mã số
				cell = row.createCell(18);
				cell.setCellStyle(cell18);
				cell.setCellValue("");
				// mục đích nuôi
				cell = row.createCell(19);
				cell.setCellStyle(cell19);
				cell.setCellValue("");
				// ghi chú
//				cell = row.createCell(20);
//				cell.setCellStyle(cell20);
//				cell.setCellValue("");
				
				row = sheet.getRow(rowIndex);
				cell = row.createCell(20);
				cell.setCellStyle(cell20);
				cell.setCellValue("");
				sheet.setColumnWidth(20, 9 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 20, 20));
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnHidden(20,true);
				
				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(20);
				sheet.setColumnWidth(20, 0 * 256);
				cell.setCellStyle(cellHidden);
				
				// mã loài
//				cell = row.createCell(21);				
//				cell.setCellStyle(cell21);
//				cell.setCellValue("");
				row = sheet.getRow(rowIndex);
				cell = row.createCell(21);
				cell.setCellStyle(cell21);
				cell.setCellValue("");
				sheet.setColumnWidth(21, 9 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 21, 21));
				cell.setCellStyle(cellStyleBoldTable);
				sheet.setColumnHidden(21,true);
				
				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(21);
				sheet.setColumnWidth(21, 0 * 256);
				cell.setCellStyle(cellHidden);
				
				// mã hệ thống
				cell = row.createCell(22);
				cell.setCellStyle(cell22);
				cell.setCellValue("");
				
//				rowStartTable++;
				// mã xã
				cell = row.createCell(23);
				cell.setCellStyle(cell23);
				cell.setCellValue("");
//				rowStartTable++;
				// mã quận/huyện
				cell = row.createCell(24);
				cell.setCellStyle(cell24);
				cell.setCellValue("");
				// mã tỉnh/thành phố
				cell = row.createCell(25);
				cell.setCellStyle(cell25);
				cell.setCellValue("");

				rowStartTable++;
				// view cấp huyện
				indexDistrict = 0;
				if (listCity.get(i).getListDistrict() != null && listCity.get(i).getListDistrict().size() > 0) {
					indexDistrict = listCity.get(i).getListDistrict().size();
					List<Report18DistrictDto> listDistrict = listCity.get(i).getListDistrict();
					for (int d = 0; d < listDistrict.size(); d++) {
						index = 0;// mỗi huyện lại bắt đầu lại số thứ tự của cơ sở
//							row = sheet.createRow(i+rowStartTable +indexD+d+2);
						row = sheet.createRow(rowStartTable);
						cell = row.createCell(0);
						cell.setCellStyle(cell0);
						cell.setCellValue("");
						cell = row.createCell(1);
						cell.setCellStyle(cellStyleBoldCenter);
						// chuyển số nguyên sang bảng chữ cái
						cell.setCellValue(NumberUtils.CharNumerals(d + 1));
//						cell.setCellValue(d+1);
						// Tên cơ sở và địa chỉ
						cell = row.createCell(2);
						cell.setCellStyle(cellStyleBoldTable);
						if (listDistrict.get(d).getName() != null)
							cell.setCellValue(listDistrict.get(d).getName());
						else
							cell.setCellValue("");
						// tên loài nuôi
						cell = row.createCell(3);
						cell.setCellStyle(cell3);
						cell.setCellValue("");
						// Tên khoa học
						cell = row.createCell(4);
						cell.setCellStyle(cell4);
						cell.setCellValue("");
						// Tổng số cá thể
						cell = row.createCell(5);
						cell.setCellStyle(cellStyleBoldCenter);
//						toTal = 0;
//						if (listDistrict.get(d).getTotalParent() != null) {
//							toTal += listDistrict.get(d).getTotalParent();
//						}
//						if (listDistrict.get(d).getTotalGilts() != null) {
//							toTal += listDistrict.get(d).getTotalGilts();
//						}
//						if (listDistrict.get(d).getTotalChild() != null) {
//							toTal += listDistrict.get(d).getTotalChild();
//						}
//						if (listDistrict.get(d).getTotalChildUnder1YO() != null) {
//							toTal += listDistrict.get(d).getTotalChildUnder1YO();
//						}
						if (listDistrict.get(d).getTotal()!=null) {
							cell.setCellValue(listDistrict.get(d).getTotal());
						} else {
							cell.setCellValue("");
						}
						// Tổng số cá thể đàn bố mẹ
						cell = row.createCell(6);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getTotalParent() != null) {
							cell.setCellValue(listDistrict.get(d).getTotalParent());
						} else {
							cell.setCellValue("");
						}
						// Đực bố mẹ
						cell = row.createCell(7);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getMaleParent() != null) {
							cell.setCellValue(listDistrict.get(d).getMaleParent());
						} else {
							cell.setCellValue("");
						}
						// Cái bố mẹ
						cell = row.createCell(8);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getFemaleParent() != null) {
							cell.setCellValue(listDistrict.get(d).getFemaleParent());
						} else {
							cell.setCellValue("");
						}
						// Tổng số cá thể đàn hậu bị
						cell = row.createCell(9);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getTotalGilts() != null) {
							cell.setCellValue(listDistrict.get(d).getTotalGilts());
						} else {
							cell.setCellValue("");
						}
						// đực hậu bị
						cell = row.createCell(10);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getMaleGilts() != null) {
							cell.setCellValue(listDistrict.get(d).getMaleGilts());
						} else {
							cell.setCellValue("");
						}
						// cái hậu bị
						cell = row.createCell(11);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getFemaleGilts() != null) {
							cell.setCellValue(listDistrict.get(d).getFemaleGilts());
						} else {
							cell.setCellValue("");
						}
						// con dưới 1 tuổi
						cell = row.createCell(12);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getTotalChildUnder1YO() != null) {
							cell.setCellValue(listDistrict.get(d).getTotalChildUnder1YO());
						} else {
							cell.setCellValue("");
						}
						// Tổng cá thể trên 1 tuổi
						cell = row.createCell(13);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getTotalChildOver1YO() != null) {
							cell.setCellValue(listDistrict.get(d).getTotalChildOver1YO());
						} else {
							cell.setCellValue("");
						}
						// đực trên 1 tuổi
						cell = row.createCell(14);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getMaleChildOver1YearOld() != null) {
							cell.setCellValue(listDistrict.get(d).getMaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}
						// cái trên 1 tuổi
						cell = row.createCell(15);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getFemaleChildOver1YearOld() != null) {
							cell.setCellValue(listDistrict.get(d).getFemaleChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}
						// không xác định trên 1 tuổi
						cell = row.createCell(16);
						cell.setCellStyle(cellStyleBoldCenter);
						if (listDistrict.get(d).getUnGenderChildOver1YearOld() != null) {
							cell.setCellValue(listDistrict.get(d).getUnGenderChildOver1YearOld());
						} else {
							cell.setCellValue("");
						}
						// mã số cơ sở nuôi
						cell = row.createCell(17);
						cell.setCellStyle(cell17);
						cell.setCellValue("");

						// ngày được cấp mã số
						cell = row.createCell(18);
						cell.setCellStyle(cell18);
						cell.setCellValue("");
						// mục đích nuôi
						cell = row.createCell(19);
						cell.setCellStyle(cell19);
						cell.setCellValue("");
						// ghi chú
						cell = row.createCell(20);
						cell.setCellStyle(cellHidden);
						cell.setCellValue("");
						// mã loài
						cell = row.createCell(21);						
						cell.setCellStyle(cellHidden);
						cell.setCellValue("");
						
						// mã hệ thống
						cell = row.createCell(22);
						cell.setCellStyle(cell22);
						cell.setCellValue("");
					
//						rowStartTable++;
						// mã xã
						cell = row.createCell(23);
						cell.setCellStyle(cell23);
						cell.setCellValue("");

						// mã quận/huyện
						cell = row.createCell(24);
						cell.setCellStyle(cell24);
						cell.setCellValue("");
						// mã Tỉnh
						cell = row.createCell(25);
						cell.setCellStyle(cell25);
						cell.setCellValue("");

						rowStartTable++;
						// view cấp xã
						indexWard = 0;
						if (listDistrict.get(d).getListWard() != null && listDistrict.get(d).getListWard().size() > 0) {
							indexWard = listDistrict.get(d).getListWard().size();
							List<Report18WardDto> listWard = listDistrict.get(d).getListWard();
							for (int w = 0; w < listWard.size(); w++) {
//									row = sheet.createRow(i+rowStartTable +indexD+w+2);
								row = sheet.createRow(rowStartTable);
								cell = row.createCell(0);
								cell.setCellStyle(cell0);
								cell.setCellValue("");
								cell = row.createCell(1);
								cell.setCellStyle(cellStyleBoldCenter);
								// chuyển số nguyên sang số la mã
								cell.setCellValue(NumberUtils.RomanNumerals(w + 1));
								// Tên cơ sở và địa chỉ
								cell = row.createCell(2);
								cell.setCellStyle(cellStyleBoldTable);
								if (listWard.get(w).getName() != null)
									cell.setCellValue(listWard.get(w).getName());
								else
									cell.setCellValue("");
								// tên loài nuôi
								cell = row.createCell(3);
								cell.setCellStyle(cell3);
								cell.setCellValue("");
								// Tên khoa học
								cell = row.createCell(4);
								cell.setCellStyle(cell4);
								cell.setCellValue("");
								// Tổng số cá thể
								cell = row.createCell(5);
								cell.setCellStyle(cellStyleBoldCenter);
//								toTal = 0;
//								if (listWard.get(w).getTotalParent() != null) {
//									toTal += listWard.get(w).getTotalParent();
//								}
//								if (listWard.get(w).getTotalGilts() != null) {
//									toTal += listWard.get(w).getTotalGilts();
//								}
//								if (listWard.get(w).getTotalChild() != null) {
//									toTal += listWard.get(w).getTotalChild();
//								}
//								if (listWard.get(w).getTotalChildUnder1YO() != null) {
//									toTal += listWard.get(w).getTotalChildUnder1YO();
//								}
								if (listWard.get(w).getTotal()!=null) {
									cell.setCellValue(listWard.get(w).getTotal());
								} else {
									cell.setCellValue("");
								}
								// Tổng số cá thể đàn bố mẹ
								cell = row.createCell(6);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getTotalParent() != null) {
									cell.setCellValue(listWard.get(w).getTotalParent());
								} else {
									cell.setCellValue("");
								}
								// Đực bố mẹ
								cell = row.createCell(7);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getMaleParent() != null) {
									cell.setCellValue(listWard.get(w).getMaleParent());
								} else {
									cell.setCellValue("");
								}
								// Cái bố mẹ
								cell = row.createCell(8);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getFemaleParent() != null) {
									cell.setCellValue(listWard.get(w).getFemaleParent());
								} else {
									cell.setCellValue("");
								}
								// Tổng số cá thể đàn hậu bị
								cell = row.createCell(9);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getTotalGilts() != null) {
									cell.setCellValue(listWard.get(w).getTotalGilts());
								} else {
									cell.setCellValue("");
								}
								// đực hậu bị
								cell = row.createCell(10);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getMaleGilts() != null) {
									cell.setCellValue(listWard.get(w).getMaleGilts());
								} else {
									cell.setCellValue("");
								}
								// cái hậu bị
								cell = row.createCell(11);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getFemaleGilts() != null) {
									cell.setCellValue(listWard.get(w).getFemaleGilts());
								} else {
									cell.setCellValue("");
								}
								// con dưới 1 tuổi
								cell = row.createCell(12);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getTotalChildUnder1YO() != null) {
									cell.setCellValue(listWard.get(w).getTotalChildUnder1YO());
								} else {
									cell.setCellValue("");
								}
								// Tổng cá thể trên 1 tuổi
								cell = row.createCell(13);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getTotalChildOver1YO() != null) {
									cell.setCellValue(listWard.get(w).getTotalChildOver1YO());
								} else {
									cell.setCellValue("");
								}
								// đực trên 1 tuổi
								cell = row.createCell(14);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getMaleChildOver1YearOld() != null) {
									cell.setCellValue(listWard.get(w).getMaleChildOver1YearOld());
								} else {
									cell.setCellValue("");
								}
								// cái trên 1 tuổi
								cell = row.createCell(15);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getFemaleChildOver1YearOld() != null) {
									cell.setCellValue(listWard.get(w).getFemaleChildOver1YearOld());
								} else {
									cell.setCellValue("");
								}
								// không xác định trên 1 tuổi
								cell = row.createCell(16);
								cell.setCellStyle(cellStyleBoldCenter);
								if (listWard.get(w).getUnGenderChildOver1YearOld() != null) {
									cell.setCellValue(listWard.get(w).getUnGenderChildOver1YearOld());
								} else {
									cell.setCellValue("");
								}
								// mã số cơ sở nuôi
								cell = row.createCell(17);
								cell.setCellStyle(cell17);
								cell.setCellValue("");

								// ngày được cấp mã số
								cell = row.createCell(18);
								cell.setCellStyle(cell18);
								cell.setCellValue("");
								// mục đích nuôi
								cell = row.createCell(19);
								cell.setCellStyle(cell19);
								cell.setCellValue("");
								// ghi chú
								cell = row.createCell(20);
								cell.setCellStyle(cell20);
								cell.setCellValue("");
								// mã loài
								cell = row.createCell(21);
								cell.setCellStyle(cell21);
								cell.setCellValue("");
								// mã hệ thống
								cell = row.createCell(22);
								cell.setCellStyle(cell22);
								cell.setCellValue("");
//								rowStartTable++;
								// mã xã
								cell = row.createCell(23);
								cell.setCellStyle(cell23);
								cell.setCellValue("");
								// mã quận/huyện
								cell = row.createCell(24);
								cell.setCellStyle(cell24);
								cell.setCellValue("");
								// mã tỉnh
								cell = row.createCell(25);
								cell.setCellStyle(cell25);
								cell.setCellValue("");

								rowStartTable++;
								// view phần cơ sở
								indexFarm = 0;
								if (listWard.get(w).getList() != null && listWard.get(w).getList().size() > 0) {
									List<Report18Dto> listFarm = listWard.get(w).getList();
									indexFarm = listWard.get(w).getList().size();
									// danh sách cơ sở duy nhất
									Hashtable<Long, Report18Dto> hashFarm = new Hashtable<Long, Report18Dto>();
									for (int f = 0; f < listFarm.size(); f++) {
										row = sheet.createRow(rowStartTable);
										// stt
										boolean isIndex = true;
										int indexDup = 0;
										List<Report18Dto> listFarmDup = listWard.get(w).getList();
										Report18Dto farm = hashFarm.get(listFarm.get(f).getFarmId().longValue());
										if (farm == null) {
											farm = new Report18Dto();
											farm = listFarm.get(f);
											hashFarm.put(farm.getFarmId().longValue(), farm);
											isIndex = true;
											for (int dup = 0; dup < listFarmDup.size(); dup++) {
												if (listFarm.get(f).getFarmId() != null
													
														&& listFarmDup.get(dup).getFarmId() != null
													
														&& listFarmDup.get(dup).getFarmId()
																.equals(listFarm.get(f).getFarmId())) {
													indexDup++;
												}
											}

										} else {
											isIndex = false;
										}
										if (isIndex && indexDup <= 1) {
											cell = row.createCell(0);
											cell.setCellStyle(cell0);
											cell.setCellValue(indexCity + 1);

											cell = row.createCell(1);
											cell.setCellStyle(cell1);
											cell.setCellValue(index + 1);

											// Tên cơ sở và địa chỉ
											cell = row.createCell(2);
											cell.setCellStyle(cell2);
											if (listFarm.get(f).getFarmName() != null
													&& listFarm.get(f).getFarmName().length()>0)
												cell.setCellValue(listFarm.get(f).getFarmName());
											else
												cell.setCellValue("");
										} else if (isIndex && indexDup > 1) {
											indexDup = indexDup - 1;
											cell = row.createCell(0);
											cell.setCellStyle(cell0);
											cell.setCellValue(indexCity + 1);
											sheet.addMergedRegion(new CellRangeAddress(rowStartTable,
													rowStartTable + indexDup, 0, 0));
											pt.drawBorders(
													new CellRangeAddress(rowStartTable, rowStartTable + indexDup, 0, 0),
													BorderStyle.THIN, BorderExtent.ALL);
											cell = row.createCell(1);
											cell.setCellStyle(cell1);
											cell.setCellValue(index + 1);
											sheet.addMergedRegion(new CellRangeAddress(rowStartTable,
													rowStartTable + indexDup, 1, 1));
											pt.drawBorders(
													new CellRangeAddress(rowStartTable, rowStartTable + indexDup, 1, 1),
													BorderStyle.THIN, BorderExtent.ALL);
											// Tên cơ sở và địa chỉ
											cell = row.createCell(2);
											cell.setCellStyle(cell2);
											if (listFarm.get(f).getFarmName() != null
													&& listFarm.get(f).getFarmName().length()>0)
												cell.setCellValue(listFarm.get(f).getFarmName());
											else
												cell.setCellValue("");
											sheet.addMergedRegion(new CellRangeAddress(rowStartTable,
													rowStartTable + indexDup, 2, 2));
											pt.drawBorders(
													new CellRangeAddress(rowStartTable, rowStartTable + indexDup, 2, 2),
													BorderStyle.THIN, BorderExtent.ALL);
										}

										// tên loài nuôi
										cell = row.createCell(3);
										cell.setCellStyle(cell3);
										if (listFarm.get(f).getAnimalName() != null
												&& listFarm.get(f).getAnimalName().length()>0) {
											cell.setCellValue(listFarm.get(f).getAnimalName());
										} else {
											cell.setCellValue("");
										}
										// Tên khoa học
										cell = row.createCell(4);
										cell.setCellStyle(cell4);
										if (listFarm.get(f).getAnimalSciName() != null
												&& listFarm.get(f).getAnimalSciName().length()>0) {
											cell.setCellValue(listFarm.get(f).getAnimalSciName());
										} else {
											cell.setCellValue("");
										}
										// Tổng số cá thể
										cell = row.createCell(5);
										cell.setCellStyle(cell5);
//										toTal = 0;
//										if (listFarm.get(f).getTotalParent() != null) {
//											toTal += listFarm.get(f).getTotalParent();
//										}
//										if (listFarm.get(f).getTotalGilts() != null) {
//											toTal += listFarm.get(f).getTotalGilts();
//										}
//										if (listFarm.get(f).getTotalChild() != null) {
//											toTal += listFarm.get(f).getTotalChild();
//										}
//										if (listFarm.get(f).getTotalChildUnder1YO() != null) {
//											toTal += listFarm.get(f).getTotalChildUnder1YO();
//										}
										if (listFarm.get(f).getTotal()!=null) {
											cell.setCellValue(listFarm.get(f).getTotal());
										} else {
											cell.setCellValue("");
										}
										// Tổng số cá thể đàn bố mẹ
										cell = row.createCell(6);
										cell.setCellStyle(cell6);
										if (listFarm.get(f).getTotalParent() != null) {
											cell.setCellValue(listFarm.get(f).getTotalParent());
										} else {
											cell.setCellValue("");
										}
										// Đực bố mẹ
										cell = row.createCell(7);
										cell.setCellStyle(cell7);
										if (listFarm.get(f).getMaleParent() != null) {
											cell.setCellValue(listFarm.get(f).getMaleParent());
										} else {
											cell.setCellValue("");
										}
										// Cái bố mẹ
										cell = row.createCell(8);
										cell.setCellStyle(cell8);
										if (listFarm.get(f).getFemaleParent() != null) {
											cell.setCellValue(listFarm.get(f).getFemaleParent());
										} else {
											cell.setCellValue("");
										}
										// Tổng số cá thể đàn hậu bị
										cell = row.createCell(9);
										cell.setCellStyle(cell9);
										if (listFarm.get(f).getTotalGilts() != null) {
											cell.setCellValue(listFarm.get(f).getTotalGilts());
										} else {
											cell.setCellValue("");
										}
										// đực hậu bị
										cell = row.createCell(10);
										cell.setCellStyle(cell10);
										if (listFarm.get(f).getMaleGilts() != null) {
											cell.setCellValue(listFarm.get(f).getMaleGilts());
										} else {
											cell.setCellValue("");
										}
										// cái hậu bị
										cell = row.createCell(11);
										cell.setCellStyle(cell11);
										if (listFarm.get(f).getFemaleGilts() != null) {
											cell.setCellValue(listFarm.get(f).getFemaleGilts());
										} else {
											cell.setCellValue("");
										}
										// con dưới 1 tuổi
										cell = row.createCell(12);
										cell.setCellStyle(cell12);
										if (listFarm.get(f).getTotalChildUnder1YO() != null) {
											cell.setCellValue(listFarm.get(f).getTotalChildUnder1YO());
										} else {
											cell.setCellValue("");
										}
										// Tổng cá thể trên 1 tuổi
										cell = row.createCell(13);
										cell.setCellStyle(cell13);
										if (listFarm.get(f).getTotalChildOver1YO() != null) {
											cell.setCellValue(listFarm.get(f).getTotalChildOver1YO());
										} else {
											cell.setCellValue("");
										}
										// đực trên 1 tuổi
										cell = row.createCell(14);
										cell.setCellStyle(cell14);
										if (listFarm.get(f).getMaleChildOver1YearOld() != null) {
											cell.setCellValue(listFarm.get(f).getMaleChildOver1YearOld());
										} else {
											cell.setCellValue("");
										}
										// cái trên 1 tuổi
										cell = row.createCell(15);
										cell.setCellStyle(cell15);
										if (listFarm.get(f).getFemaleChildOver1YearOld() != null) {
											cell.setCellValue(listFarm.get(f).getFemaleChildOver1YearOld());
										} else {
											cell.setCellValue("");
										}
										// không xác định trên 1 tuổi
										cell = row.createCell(16);
										cell.setCellStyle(cell16);
										if (listFarm.get(f).getUnGenderChildOver1YearOld() != null) {
											cell.setCellValue(listFarm.get(f).getUnGenderChildOver1YearOld());
										} else {
											cell.setCellValue("");
										}
										// mã số cơ sở nuôi mã mới
										cell = row.createCell(17);
										cell.setCellStyle(cell17);
										if (listFarm.get(f).getFarmNewRegistrationCode() != null
												&& listFarm.get(f).getFarmNewRegistrationCode().length()>0) {
											cell.setCellValue(listFarm.get(f).getFarmNewRegistrationCode());
										} else
											cell.setCellValue("");
										// ngày được cấp mã số
										cell = row.createCell(18);
										cell.setCellStyle(cell18);
										if (listFarm.get(f).getFarmDateRegistration() != null) {
											cell.setCellValue(
													formatter.format(listFarm.get(f).getFarmDateRegistration()));
										} else
											cell.setCellValue("");
										// mục đích nuôi
										cell = row.createCell(19);
										cell.setCellStyle(cell19);
										//=>sửa mục đích nuôi
										String target="";
										if(listFarm.get(f).getT()!=null) {
											target+="T ";
										}
										if(listFarm.get(f).getZ()!=null) {
											target+="Z ";
										}
										if(listFarm.get(f).getPTM()!=null) {
											target+="PTM ";
										}
										if(listFarm.get(f).getS()!=null) {
											target+="S ";
										}
										if(listFarm.get(f).getO()!=null) {
											target+="O ";
										}
										if(listFarm.get(f).getR()!=null) {
											target+="R ";
										}
										if(listFarm.get(f).getQ()!=null) {
											target+="Q ";
										}
										cell.setCellValue(target);
										//cell.setCellValue("");
										
										// hình thức nuôi
										cell = row.createCell(20);
										cell.setCellStyle(cell19);
										String target1="";
										if(listFarm.get(f).getSS()!=null) {
											target1+="SS ";
										}
										if(listFarm.get(f).getST()!=null) {
											target1+="ST ";
										}
										if(listFarm.get(f).getSSTT()!=null) {
											target1+="SS-TT ";
										}
										
										cell.setCellValue(target1);
										//cell.setCellValue("");
										
										// trạng thái
										cell = row.createCell(21);
										cell.setCellStyle(cell20);
										if(listFarm.get(f).getStatusFarm()!=null) {
											cell.setCellValue(listFarm.get(f).getStatusFarm());
										}else {
											cell.setCellValue("");
										}
										cell.setCellStyle(cellHidden);
										
											
										// ghi chú
										cell = row.createCell(22);
										cell.setCellStyle(cell20);
										cell.setCellValue("");
											
										// mã loài
										cell = row.createCell(23);
										cell.setCellStyle(cell21);
										if (listFarm.get(f).getAnimalCode() != null
												&& listFarm.get(f).getAnimalCode().length()>0) {
											cell.setCellValue(listFarm.get(f).getAnimalCode());
										} else
											cell.setCellValue("");
										// mã hệ thống
										cell = row.createCell(24);
										cell.setCellStyle(cell22);
										if (listFarm.get(f).getFarmCode() != null
												&& listFarm.get(f).getFarmCode().length()>0) {
											cell.setCellValue(listFarm.get(f).getFarmCode());
										} else
											cell.setCellValue("");
										// mã xã
										cell = row.createCell(25);
										cell.setCellStyle(cell23);
										if (listFarm.get(f).getWardCode() != null
												&& listFarm.get(f).getWardCode().length()>0) {
											cell.setCellValue(listFarm.get(f).getWardCode());
										} else
											cell.setCellValue("");

										// mã quận/huyện
										cell = row.createCell(26);
										cell.setCellStyle(cell24);
										if (listFarm.get(f).getDisCode() != null
												&& listFarm.get(f).getDisCode().length()>0) {
											cell.setCellValue(listFarm.get(f).getDisCode());
										} else
											cell.setCellValue("");
										// mã tỉnh thành phố
										cell = row.createCell(27);
										cell.setCellStyle(cell25);
										if (listFarm.get(f).getProvCode() != null
												&& listFarm.get(f).getProvCode().length()>0) {
											cell.setCellValue(listFarm.get(f).getProvCode());
										} else
											cell.setCellValue("");
										rowStartTable++;
										if (isIndex) {
											index++;
											indexCity++;
										}
										
										//cites
										cell = row.createCell(28);
										cell.setCellStyle(cell23);
										if (listFarm.get(f).getCites() != null
												&& listFarm.get(f).getCites().length()>0) {
											cell.setCellValue(listFarm.get(f).getCites());
										} else
											cell.setCellValue("");
										
										//06
										cell = row.createCell(29);
										cell.setCellStyle(cell23);
										if (listFarm.get(f).getVnlist06() != null
												&& listFarm.get(f).getVnlist06().length()>0) {
											cell.setCellValue(listFarm.get(f).getVnlist06());
										} else
											cell.setCellValue("");
										
										//64
										cell = row.createCell(30);
										cell.setCellStyle(cell23);
										if (listFarm.get(f).getVnlist() != null
												&& listFarm.get(f).getVnlist().length()>0) {
											cell.setCellValue(listFarm.get(f).getVnlist());
										} else
											cell.setCellValue("");
										
										//Mã khác
										cell = row.createCell(31);
										cell.setCellStyle(cell29);
										
										if (listFarm.get(f).getFarmOldRegistrationCode() != null&&listFarm.get(f).getFarmOldRegistrationCode() != "") {
											cell.setCellValue(listFarm.get(f).getFarmOldRegistrationCode());
										} else
											cell.setCellValue("");
										

									}
			
									indexD = indexD + indexFarm;
								}
							}
							indexD = indexD + indexWard;
						}
					}
					indexD = indexD + indexDistrict;
				}
			}
			
			cell= row.createCell(21);
			cell.setCellStyle(cellHidden);
			
			cell= row.createCell(22);
			cell.setCellStyle(cellHidden);

			pt.applyBorders(sheet);
			Row footerRow = sheet.createRow(rowStartTable + 2);
			Cell footerCell = footerRow.createCell(1);
			footerCell.setCellValue("NGƯỜI LẬP");
			footerCell.setCellStyle(cellStyleCenter);
			sheet.addMergedRegion(new CellRangeAddress(rowStartTable + 2, rowStartTable + 2, 1, 5));
			footerCell = footerRow.createCell(12);
			footerCell.setCellValue("THỦ TRƯỞNG ĐƠN VỊ");
			footerCell.setCellStyle(cellStyleCenter);
			sheet.addMergedRegion(new CellRangeAddress(rowStartTable + 2, rowStartTable + 2, 12, 19));

			Row sfooterRow = sheet.createRow(rowStartTable + 3);
			footerCell = sfooterRow.createCell(1);
			footerCell.setCellValue("(Ký và ghi rõ họ tên)");
			footerCell.setCellStyle(cellStyleCenterI);
			sheet.addMergedRegion(new CellRangeAddress(rowStartTable + 3, rowStartTable + 3, 1, 5));
			footerCell = sfooterRow.createCell(12);
			footerCell.setCellValue("(Ký, ghi rõ họ tên và đóng dấu)");
			footerCell.setCellStyle(cellStyleCenterI);
			sheet.addMergedRegion(new CellRangeAddress(rowStartTable + 3, rowStartTable + 3, 12, 19));
			System.out.print("index: " + rowIndex);
//			}
		}
		workbook.write(out);
		workbook.close();
	}

	public static void exportReportFormAnimalEgg16C(List<ReportFormAnimalEggDto> list, String titleHeader,
			ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		int index = 1;

		Sheet sheet = workbook.createSheet("So16C");

		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 13);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);

		row = sheet.getRow(0);
		cell = row.createCell(0);
		cell.setCellValue((titleHeader).toUpperCase());
		cell.setCellStyle(cellStyleNoBoder);
		row.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

		rowIndex = 1;
		cellIndex = 0;
		sheet.createRow(1);
		sheet.createRow(2);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("STT");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số cơ sở");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên cơ sở");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Loài nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên VN");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã loài");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn bố mẹ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng trứng");
		sheet.setColumnWidth(cellIndex, 10 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số lượng trứng được đưa vào ấp");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non nở");
		sheet.setColumnWidth(cellIndex, 10 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non bị chết");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non còn sống");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non cộng dồn theo thời gian");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non tách khỏi khu nuôi dưỡng (tách đàn)");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non còn lại");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ghi chú");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		int rowStartTable = 3;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (list != null && list.size() > 0) {
			for (ReportFormAnimalEggDto reportForm16 : list) {
				if (reportForm16 != null) {
					row = sheet.createRow(rowStartTable);
					cellIndex = -1;

					// tt
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(index);

					index = index + 1;
					// Mã cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getCode() != null)
						cell.setCellValue(reportForm16.getFarm().getCode());
					else
						cell.setCellValue("");

					// Tên cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getName() != null)
						cell.setCellValue(reportForm16.getFarm().getName());
					else
						cell.setCellValue("");

					// tên loài nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getName() != null) {
						cell.setCellValue(reportForm16.getAnimal().getName());
					} else {
						cell.setCellValue("");
					}

					// Tên khoa học
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getScienceName() != null) {
						cell.setCellValue(reportForm16.getAnimal().getScienceName());
					} else {
						cell.setCellValue("");
					}

					// Mã loài
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getCode() != null) {
						cell.setCellValue(reportForm16.getAnimal().getCode());
					} else {
						cell.setCellValue("");
					}

					// Đực bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getParentMale() != null) {
						cell.setCellValue(reportForm16.getParentMale());
					} else {
						cell.setCellValue("");
					}
					// Cái bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getParentFemale() != null) {
						cell.setCellValue(reportForm16.getParentFemale());
					} else {
						cell.setCellValue("");
					}
					// số lượng trứng
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityEgg() != null) {
						cell.setCellValue(reportForm16.getQuantityEgg());
					} else {
						cell.setCellValue("");
					}
					// Số lượng trứng được đưa vào ấp
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityEggWarm() != null) {
						cell.setCellValue(reportForm16.getQuantityEggWarm());
					} else {
						cell.setCellValue("");
					}

					// Số con non nở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityChildHatch() != null) {
						cell.setCellValue(reportForm16.getQuantityChildHatch());
					} else {
						cell.setCellValue("");
					}

					// Số con con bị chết
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityChildDie() != null) {
						cell.setCellValue(reportForm16.getQuantityChildDie());
					} else {
						cell.setCellValue("");
					}
					// Số con non còn sống
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					Integer amount = 0;
					if (reportForm16.getQuantityChildHatch() != null) {
						amount = reportForm16.getQuantityChildHatch();
					}
					if (reportForm16.getQuantityChildDie() != null) {
						amount = amount - reportForm16.getQuantityChildDie();
					}
					cell.setCellValue(amount);
					// Số con non cộng dồn theo thời gian
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityChildIncrement() != null) {
						cell.setCellValue(reportForm16.getQuantityChildIncrement());
					} else {
						cell.setCellValue("");
					}

					// Số con non tách khỏi khu nuôi nhốt(tách đàn)
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityChildSeparateCaptivity() != null) {
						cell.setCellValue(reportForm16.getQuantityChildSeparateCaptivity());
					} else
						cell.setCellValue("");

					// Số con non còn lại
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					Integer remainQuantity = 0;
					if (reportForm16.getQuantityChildIncrement() != null) {
						remainQuantity = reportForm16.getQuantityChildIncrement();
					}
					if (reportForm16.getQuantityChildSeparateCaptivity() != null) {
						remainQuantity = remainQuantity - reportForm16.getQuantityChildSeparateCaptivity();
					}
					cell.setCellValue(remainQuantity);

					// Ghi chú
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);

					if (reportForm16.getNote() != null) {
						cell.setCellValue(reportForm16.getNote());
					} else {
						cell.setCellValue("");
					}

					rowStartTable++;
				}

			}
		}
		pt.applyBorders(sheet);
		workbook.write(outputStream);
	}

	public static void exportReportFormAnimalEgg16D(List<ReportFormAnimalGiveBirthDto> list, String titleHeader,
			ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		int index = 1;

		Sheet sheet = workbook.createSheet("So16D");

		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 13);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);

		row = sheet.getRow(0);
		cell = row.createCell(0);
		cell.setCellValue((titleHeader).toUpperCase());
		cell.setCellStyle(cellStyleNoBoder);
		row.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

		rowIndex = 1;
		cellIndex = 0;
		sheet.createRow(1);
		sheet.createRow(2);

		rowIndex = 1;
		cellIndex = 0;
		sheet.createRow(1);
		sheet.createRow(2);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("STT");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số cơ sở");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên cơ sở");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Loài nuôi");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 2));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên VN");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã loài");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đàn bố mẹ");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex, cellIndex, cellIndex + 1), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cell = row.createCell(cellIndex + 1);
		cell.setCellStyle(cellStyleBoldTable);

		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Đực");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex + 1);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Cái");
		sheet.setColumnWidth(cellIndex, 7 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non nở");
		sheet.setColumnWidth(cellIndex, 10 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non bị chết");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non còn sống");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non cộng dồn theo thời gian");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non tách khỏi khu nuôi dưỡng (tách đàn)");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số con non còn lại");
		sheet.setColumnWidth(cellIndex, 20 * 256);
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ghi chú");
		sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex));
		pt.drawBorders(new CellRangeAddress(rowIndex, rowIndex + 1, cellIndex, cellIndex), BorderStyle.THIN,
				BorderExtent.ALL);
		cell.setCellStyle(cellStyleBoldTable);

		int rowStartTable = 3;

		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (list != null && list.size() > 0) {
			for (ReportFormAnimalGiveBirthDto reportForm16 : list) {
				if (reportForm16 != null) {
					row = sheet.createRow(rowStartTable);
					cellIndex = -1;

					// tt
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(index);

					index = index + 1;

					// mã cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getCode() != null)
						cell.setCellValue(reportForm16.getFarm().getCode());
					else
						cell.setCellValue("");

					// Tên cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getName() != null)
						cell.setCellValue(reportForm16.getFarm().getName());
					else
						cell.setCellValue("");

					// tên loài nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getName() != null) {
						cell.setCellValue(reportForm16.getAnimal().getName());
					} else {
						cell.setCellValue("");
					}

					// Tên khoa học
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getScienceName() != null) {
						cell.setCellValue(reportForm16.getAnimal().getScienceName());
					} else {
						cell.setCellValue("");
					}
					// Mã loài
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getCode() != null) {
						cell.setCellValue(reportForm16.getAnimal().getCode());
					} else {
						cell.setCellValue("");
					}

					// Đực bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getParentMale() != null) {
						cell.setCellValue(reportForm16.getParentMale());
					} else {
						cell.setCellValue("");
					}
					// Cái bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getParentFemale() != null) {
						cell.setCellValue(reportForm16.getParentFemale());
					} else {
						cell.setCellValue("");
					}

					// Số con non nở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityChildHatch() != null) {
						cell.setCellValue(reportForm16.getQuantityChildHatch());
					} else {
						cell.setCellValue("");
					}

					// Số con con bị chết
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityChildDie() != null) {
						cell.setCellValue(reportForm16.getQuantityChildDie());
					} else {
						cell.setCellValue("");
					}
					// Số con non còn sống
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					Integer amount = 0;
					if (reportForm16.getQuantityChildHatch() != null) {
						amount = reportForm16.getQuantityChildHatch();
					}
					if (reportForm16.getQuantityChildDie() != null) {
						amount = amount - reportForm16.getQuantityChildDie();
					}
					cell.setCellValue(amount);
					// Số con non cộng dồn theo thời gian
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityChildIncrement() != null) {
						cell.setCellValue(reportForm16.getQuantityChildIncrement());
					} else {
						cell.setCellValue("");
					}

					// Số con non tách khỏi khu nuôi nhốt(tách đàn)
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getQuantityChildSeparateCaptivity() != null) {
						cell.setCellValue(reportForm16.getQuantityChildSeparateCaptivity());
					} else
						cell.setCellValue("");

					// Số con non còn lại
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					Integer remainQuantity = 0;
					if (reportForm16.getQuantityChildIncrement() != null) {
						remainQuantity = reportForm16.getQuantityChildIncrement();
					}
					if (reportForm16.getQuantityChildSeparateCaptivity() != null) {
						remainQuantity = remainQuantity - reportForm16.getQuantityChildSeparateCaptivity();
					}
					cell.setCellValue(remainQuantity);

					// Ghi chú
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);

					if (reportForm16.getNote() != null) {
						cell.setCellValue(reportForm16.getNote());
					} else {
						cell.setCellValue("");
					}

					rowStartTable++;
				}

			}
		}
		pt.applyBorders(sheet);
		workbook.write(outputStream);
	}

	public static void exportReportAnimalCurrentByFamilyToExcel(List<Report18Dto> list, String titleHeader,
			ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		int index = 1;

		Sheet sheet = workbook.createSheet("BaoCaoHienTrangNuoiDVHDTheoHo");

		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 13);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);
		sheet.createRow(2);

		row = sheet.getRow(2);
		cell = row.createCell(0);

		row = sheet.getRow(0);
		cell = row.createCell(0);
		cell.setCellValue("BÁO CÁO HIỆN TRẠNG NUÔI ĐỘNG VẬT HOANG DÃ THEO HỌ " + titleHeader.toUpperCase());
		cell.setCellStyle(cellStyleNoBoder);
		row.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

		rowIndex = 1;
		cellIndex = 0;
		sheet.createRow(1);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("STT");
		sheet.setColumnWidth(cellIndex, 5 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Lớp");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Bộ");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Họ");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên loài nuôi");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã loài");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Phân loại theo Cites");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Nghị định 06-2020");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Nghị định 64");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số cơ sở");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên cơ sở nuôi");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tỉnh/Thành phố");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Huyện");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Xã/Phường");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã số");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Ngày cấp mã số");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Số cá thể");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Kinh độ");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Vĩ độ");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		sheet.setColumnHidden(cellIndex, true);

		int rowStartTable = 2;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		if (list != null && list.size() > 0) {
			for (Report18Dto fat : list) {
				if (fat != null) {
					row = sheet.createRow(rowStartTable);
					cellIndex = -1;

					// tt
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(index);

					index = index + 1;
					// Lớp
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getAnimalClass() != null)
						cell.setCellValue(fat.getAnimalClass());
					else
						cell.setCellValue("");
					
					
					
					
					// Bộ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getAnimalOrdo() != null)
						cell.setCellValue(fat.getAnimalOrdo());
					else
						cell.setCellValue("");

					// Họ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getAnimalFamily() != null)
						cell.setCellValue(fat.getAnimalFamily());
					else
						cell.setCellValue("");

					// tên loài nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getAnimalName() != null) {
						cell.setCellValue(fat.getAnimalName());
					} else {
						cell.setCellValue("");
					}

					// Tên khoa học
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getAnimalSciName() != null) {
						cell.setCellValue(fat.getAnimalSciName());
					} else {
						cell.setCellValue("");
					}

					// mã loài nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getAnimalCode() != null) {
						cell.setCellValue(fat.getAnimalCode());
					} else {
						cell.setCellValue("");
					}
					
					// cites
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getCites() != null) {
						cell.setCellValue(fat.getCites());
					} else {
						cell.setCellValue("");
					}
					
					// 06
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getVnlist06() != null) {
						cell.setCellValue(fat.getVnlist06());
					} else {
						cell.setCellValue("");
					}
					
					// 64
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getVnlist() != null) {
						cell.setCellValue(fat.getVnlist());
					} else {
						cell.setCellValue("");
					}

					// Mã số Cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getFarmCode() != null) {
						cell.setCellValue(fat.getFarmCode());
					} else {
						cell.setCellValue("");
					}

					// Tên Cơ sở nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getFarmName() != null) {
						cell.setCellValue(fat.getFarmName());
					} else {
						cell.setCellValue("");
					}

					// Địa chỉ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getProvName()!=null) {
						cell.setCellValue(fat.getProvName());
					}else {
						cell.setCellValue("");
					}
					
					// Địa chỉ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getDisName()!=null) {
						cell.setCellValue(fat.getDisName());
					}else {
						cell.setCellValue("");
					}
					
					// Địa chỉ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getWardName()!=null) {
						cell.setCellValue(fat.getWardName());
					}else {
						cell.setCellValue("");
					}
					
					

					// Mã số
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getFarmNewRegistrationCode() != null) {
						cell.setCellValue(fat.getFarmNewRegistrationCode());
					} else {
						cell.setCellValue("");
					}

					// ngày cấp Mã số
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getFarmDateRegistration() != null) {
						cell.setCellValue(dateFormat.format(fat.getFarmDateRegistration()));
					} else {
						cell.setCellValue("");
					}

					// tổng số cá thể
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getTotal() > 0) {
						cell.setCellValue(fat.getTotal());
					} else {
						cell.setCellValue(0);
					}

					// Kinh độ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getFarmLongitude() != null) {
						cell.setCellValue(fat.getFarmLongitude());
					} else {
						cell.setCellValue("");
					}

					// Vĩ độ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (fat.getFarmLatitude() != null) {
						cell.setCellValue(fat.getFarmLatitude());
					} else {
						cell.setCellValue("");
					}

					rowStartTable++;
				}

			}
		}
		pt.applyBorders(sheet);
		workbook.write(outputStream);
		workbook.close();
	}

	public static void exportAdministrativeUnit(List<FmsAdministrativeUnitDto> list, String titleHeader,
			ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		int index = 1;
		Sheet sheet = workbook.createSheet("DuLieuDonViHanhChinh");
		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 13);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
		sheet.createRow(0);
		sheet.createRow(2);

		row = sheet.getRow(2);
		cell = row.createCell(0);

		row = sheet.getRow(0);
		cell = row.createCell(0);
		cell.setCellValue(titleHeader);
		cell.setCellStyle(cellStyleNoBoder);
		row.setHeightInPoints(20);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));

		rowIndex = 1;
		cellIndex = 0;
		sheet.createRow(1);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tỉnh Thành Phố");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã Thành Phố");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Quận Huyện");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã Quận Huyện");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Phường Xã");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã Phường Xã");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

//		cellIndex++;
//		row = sheet.getRow(rowIndex);
//		cell = row.createCell(cellIndex);
//		cell.setCellValue("Cấp");
//		sheet.setColumnWidth(cellIndex, 30 * 256);
//		cell.setCellStyle(cellStyleBoldTable);

		int rowStartTable = 2;
		if (list != null && list.size() > 0) {
			for (FmsAdministrativeUnitDto administrativeUnit : list) {
				if (administrativeUnit != null) {
					row = sheet.createRow(rowStartTable);
					cellIndex = 0;

					// Tỉnh Thành Phố
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (administrativeUnit.getParentDto() != null) {
						if (administrativeUnit.getParentDto().getParentDto() != null) {
							if (administrativeUnit.getParentDto().getParentDto().getName() != null) {
								cell.setCellValue(administrativeUnit.getParentDto().getParentDto().getName());
							}
						}
					} else
						cell.setCellValue("");

					// Mã TP
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (administrativeUnit.getParentDto() != null) {
						if (administrativeUnit.getParentDto().getParentDto() != null) {
							if (administrativeUnit.getParentDto().getParentDto().getCode() != null) {
								cell.setCellValue(administrativeUnit.getParentDto().getParentDto().getCode());
							}
						}
					} else
						cell.setCellValue("");

					// Quận Huyện
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (administrativeUnit.getParentDto() != null) {
						if (administrativeUnit.getParentDto().getName() != null) {
							cell.setCellValue(administrativeUnit.getParentDto().getName());
						}
					} else
						cell.setCellValue("");

					// Mã Quận Huyện
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (administrativeUnit.getParentDto() != null) {
						if (administrativeUnit.getParentDto().getCode() != null) {
							cell.setCellValue(administrativeUnit.getParentDto().getCode());
						}
					} else
						cell.setCellValue("");

					// Xã Phường
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (administrativeUnit.getName() != null) {
						cell.setCellValue(administrativeUnit.getName());
					} else
						cell.setCellValue("");

					// Mã xã
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (administrativeUnit.getCode() != null) {
						if (administrativeUnit.getCode() != null) {
							cell.setCellValue(administrativeUnit.getCode());
						}
					} else
						cell.setCellValue("");

					rowStartTable++;
				}

			}
		}
		pt.applyBorders(sheet);
		workbook.write(outputStream);
		workbook.close();
	}

	public static void exportAnimal(List<AnimalDto> list, ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		int index = 1;
		Sheet sheet = workbook.createSheet("DuLieuCacLoaiDongVatHoangDa");
		PropertyTemplate pt = new PropertyTemplate();
		Font font = workbook.createFont();
		font.setFontHeightInPoints((short) 12);
		font.setFontName("Times New Roman");
		font.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

		Font fontNoBorder = workbook.createFont();
		fontNoBorder.setFontHeightInPoints((short) 13);
		fontNoBorder.setBold(true);
		fontNoBorder.setFontName("Times New Roman");

		CellStyle cellStyleNoBoder = workbook.createCellStyle();
		cellStyleNoBoder.setWrapText(true);
		cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
		cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleNoBoder.setFont(fontNoBorder);

		CellStyle cellStyleBoldTable = workbook.createCellStyle();
		cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
		cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
		cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
		cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
		cellStyleBoldTable.setWrapText(true);
		cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleBoldTable.setFont(font);

		Row row = null;
		Cell cell = null;

		int rowIndex = 0;
		int cellIndex = 0;
//		sheet.createRow(0);
//		sheet.createRow(2);
//
//		row = sheet.getRow(2);
//		cell = row.createCell(0);
//
//		row = sheet.getRow(0);
//		cell = row.createCell(0);
//		cell.setCellValue("Danh sách các loài động vật hoang dã");
//		cell.setCellStyle(cellStyleNoBoder);
//		row.setHeightInPoints(20);
//		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
//
//		rowIndex = 1;
//		cellIndex = 0;
		sheet.createRow(0);

		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("STT");
		sheet.setColumnWidth(cellIndex, 5 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Mã động vật");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên loài (Tiếng Việt)");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khác");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên khoa học");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Tên tiếng Anh");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Lớp");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Bộ");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Họ");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Phân nhóm theo Phụ lục CITES");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Phân nhóm theo Nghị định 64");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);

		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Phân nhóm theo Nghị định 06");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		//them cac cot thieu trong file import moi
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Protection level");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Protection");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Synonym");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		
		cellIndex++;
		row = sheet.getRow(rowIndex);
		cell = row.createCell(cellIndex);
		cell.setCellValue("Hình thức đẻ trứng");
		sheet.setColumnWidth(cellIndex, 30 * 256);
		cell.setCellStyle(cellStyleBoldTable);
		//them cac cot theiu trong file import moi

		//int rowStartTable = 2;
		int rowStartTable = 1;
		if (list != null && list.size() > 0) {
			for (AnimalDto animalDto : list) {
				if (animalDto != null) {
					row = sheet.createRow(rowStartTable);
					cellIndex = -1;

					// tt
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					cell.setCellValue(index);

					index = index + 1;
					// Mã động vật
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getCode() != null)
						cell.setCellValue(animalDto.getCode());
					else
						cell.setCellValue("");

					// Tên loài
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getName() != null)
						cell.setCellValue(animalDto.getName());
					else
						cell.setCellValue("");

					// tên khác
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getOtherName() != null) {
						cell.setCellValue(animalDto.getOtherName());
					} else {
						cell.setCellValue("");
					}

					// Tên khoa học
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getScienceName() != null) {
						cell.setCellValue(animalDto.getScienceName());
					} else {
						cell.setCellValue("");
					}

					// Tên tiếng anh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getEnName() != null) {
						cell.setCellValue(animalDto.getEnName());
					} else {
						cell.setCellValue("");
					}

					// Lớp động vật
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getAnimalClass() != null) {
						cell.setCellValue(animalDto.getAnimalClass());
					} else {
						cell.setCellValue("");
					}

					// Bộ động vật
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getOrdo() != null) {
						cell.setCellValue(animalDto.getOrdo());
					} else {
						cell.setCellValue("");
					}

					// Họ động vật
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getFamily() != null) {
						cell.setCellValue(animalDto.getFamily());
					} else {
						cell.setCellValue("");
					}

					// phân nhóm theo Cites
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getCites() != null) {
						cell.setCellValue(animalDto.getCites());
					} else {
						cell.setCellValue("");
					}
					// phân nhóm theo nghị định 64
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getVnlist() != null) {
						cell.setCellValue(animalDto.getVnlist());
					} else {
						cell.setCellValue("");
					}
					// phân nhóm theo nghị định 06
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getVnlist06() != null) {
						cell.setCellValue(animalDto.getVnlist06());
					} else {
						cell.setCellValue("");
					}
					
					//mức độ bảo vệ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getProtectionLevel() != null) {
						cell.setCellValue(animalDto.getProtectionLevel());
					} else {
						cell.setCellValue("");
					}
					
					//nhóm bảo vệ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getAnimalGroup() != null) {
						cell.setCellValue(animalDto.getAnimalGroup());
					} else {
						cell.setCellValue("");
					}
					
					//tên la tinh khác
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getSynonym() != null) {
						cell.setCellValue(animalDto.getSynonym());
					} else {
						cell.setCellValue("");
					}
					//hình thức sinh sản
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (animalDto.getReproductionForm() != null) {
						cell.setCellValue(animalDto.getReproductionForm());
					} else {
						cell.setCellValue("");
					}
					
					
					rowStartTable++;
				}

			}
		}
		pt.applyBorders(sheet);
		workbook.write(outputStream);
		workbook.close();
	}
	
	public static void exportForestProductsList(List<ForestProductsListDto> list, AnimalReportDataSearchDto dto,
			Workbook workbook, ServletOutputStream out) throws IOException {
		Font font = workbook.createFont();
		font.setColor(IndexedColors.BLACK.getIndex());

		Font fontTitle = workbook.createFont();
		fontTitle.setColor(IndexedColors.BLACK.getIndex());
		fontTitle.setBold(true);
		fontTitle.setFontHeightInPoints((short) 12);

		Font fontHeader = workbook.createFont();
		fontHeader.setColor(IndexedColors.BLACK.getIndex());
		fontHeader.setBold(true);

		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setBorderTop(BorderStyle.THIN);
		cellStyle.setBorderBottom(BorderStyle.THIN);
		cellStyle.setBorderLeft(BorderStyle.THIN);
		cellStyle.setBorderRight(BorderStyle.THIN);

		CellStyle cellStyleTitle = workbook.createCellStyle();
		cellStyleTitle.setFont(fontTitle);
		cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);

		CellStyle cellStyleHeader = workbook.createCellStyle();
		cellStyleHeader.setFont(fontHeader);
		cellStyleHeader.setVerticalAlignment(VerticalAlignment.CENTER);

		CellStyle cellStyleCenter = workbook.createCellStyle();
		cellStyleCenter.setFont(font);
		cellStyleCenter.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleCenter.setAlignment(HorizontalAlignment.CENTER);
		cellStyleCenter.setBorderTop(BorderStyle.THIN);
		cellStyleCenter.setBorderBottom(BorderStyle.THIN);
		cellStyleCenter.setBorderLeft(BorderStyle.THIN);
		cellStyleCenter.setBorderRight(BorderStyle.THIN);
		cellStyleCenter.setWrapText(true);

		CellStyle cellStyleHeaderTable = workbook.createCellStyle();
		cellStyleHeaderTable.setFont(fontHeader);
		cellStyleHeaderTable.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyleHeaderTable.setAlignment(HorizontalAlignment.CENTER);
		cellStyleHeaderTable.setBorderTop(BorderStyle.THIN);
		cellStyleHeaderTable.setBorderBottom(BorderStyle.THIN);
		cellStyleHeaderTable.setBorderLeft(BorderStyle.THIN);
		cellStyleHeaderTable.setBorderRight(BorderStyle.THIN);

		CellStyle numberCellStyle = workbook.createCellStyle();
		numberCellStyle.setFont(font);
		DataFormat fmt = workbook.createDataFormat();
		numberCellStyle.setDataFormat(fmt.getFormat("#,#"));
		numberCellStyle.setAlignment(HorizontalAlignment.RIGHT);
		numberCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		numberCellStyle.setBorderTop(BorderStyle.THIN);
		numberCellStyle.setBorderBottom(BorderStyle.THIN);
		numberCellStyle.setBorderLeft(BorderStyle.THIN);
		numberCellStyle.setBorderRight(BorderStyle.THIN);

		CellStyle dateCellStyle = workbook.createCellStyle();
		short df = workbook.createDataFormat().getFormat("dd/MM/yyyy");
		dateCellStyle.setDataFormat(df);
		dateCellStyle.setFont(font);
		dateCellStyle.setBorderTop(BorderStyle.THIN);
		dateCellStyle.setBorderBottom(BorderStyle.THIN);
		dateCellStyle.setBorderRight(BorderStyle.THIN);
		dateCellStyle.setBorderLeft(BorderStyle.THIN);

		Sheet sheet = workbook.getSheetAt(0);

		Row row = null;
		Cell cell = null;
		int rowIndex = 3;
		int cellIndex = 0;
		int totalColumn = 9;
		row = sheet.createRow(rowIndex);
		cell = row.createCell(cellIndex);
		
		row = sheet.createRow(rowIndex);
		
		String pattern = "MM/dd/yyyy";
		DateFormat dFormat = new SimpleDateFormat(pattern);
		
		Integer index = 1;
		if (list != null && list.size() > 0) {
			for (ForestProductsListDto item : list) {
				if (item != null && item.getId() != null) {
					row = sheet.createRow(rowIndex);
					// STT
					cellIndex = 0;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyleCenter);
					cell.setCellValue("" + index);

					// Ngày xác nhận
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (item.getDateIssue() != null)
						cell.setCellValue(dFormat.format(item.getDateIssue()));
					else
						cell.setCellValue("");

					// Tên chủ lâm sản
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (item.getFarm() != null && item.getFarm().getOwnerName() != null) {
						cell.setCellValue(item.getFarm().getOwnerName());
					} else
						cell.setCellValue("");

					// địa chỉ chủ lâm sản
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (item.getFarm() != null && item.getFarm().getAdministrativeUnit() != null) {
						FmsAdministrativeUnitDto adress = item.getAdministrativeUnitFrom();
						String adressStr = adress.getName();
						if(adress.getParentDto() != null) {
							adressStr += ", " + adress.getParentDto().getName();
						}
						if(adress.getParentDto().getParentDto() != null) {
							adressStr += ", " + adress.getParentDto().getParentDto().getName();
						}
						cell.setCellValue(adressStr);
					} else
						cell.setCellValue("");

					// Tên lâm sản
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (item.getDetail() != null && item.getDetail().getAnimal() != null) {
						cell.setCellValue(item.getDetail().getAnimal().getName());
						
					} else
						cell.setCellValue("");
					
					// Đơn vị tính
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (item.getDetail() != null && item.getDetail().getUnit() != null ) {
						cell.setCellValue(item.getDetail().getUnit());
					} else
						cell.setCellValue("");
					
					// Tổng lâm sản
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (item.getDetail() != null && item.getDetail().getTotal() != null) {
						cell.setCellValue(item.getDetail().getTotal());
					} else
						cell.setCellValue("");
					
					// Tên chức danh người xác nha
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (item.getUserConfirmName() != null) {
						cell.setCellValue(item.getUserConfirmName());
					} else
						cell.setCellValue("");

					// người kí nhận kí tên
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (item.getOwnerConfirm() != null) {
						cell.setCellValue("");
					} else
						cell.setCellValue("");
					
					// tham chiếu ghi chú
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (item.getDetail() != null && item.getDetail().getNote() != null) {
						cell.setCellValue(item.getDetail().getNote());
					} else
						cell.setCellValue("");
					
					index++;
					rowIndex++;
				}
			}
		}
		workbook.write(out);
		workbook.close();
	}

	public static List<FmsAdministrativeUnitDto> getFmsAdministrativeUnit(InputStream fis, int code, int vn2000)
			throws IOException {
		List<FmsAdministrativeUnitDto> results = new ArrayList<>();
		Workbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);

		int rowIndex = 1;
		Row row = null;
		Cell cell = null;
		int lastRow = sheet.getLastRowNum();
		while (rowIndex <= lastRow) {
			FmsAdministrativeUnitDto rawData = new FmsAdministrativeUnitDto();
			row = sheet.getRow(rowIndex);
			if (row == null) {
				break;
			}
			cell = row.getCell(code);// Tên Tỉnh
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setCode(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setCode(cell.getStringCellValue());
				}
			}
			// cellIndex++;
			cell = row.getCell(vn2000);// Tên Huyện
			if (cell != null) {
				if (cell.getCellTypeEnum() == CellType.NUMERIC) {
					rawData.setVn2000(String.valueOf(cell.getNumericCellValue()));
				} else if (cell.getCellTypeEnum() == CellType.STRING && cell.getStringCellValue() != null) {
					rawData.setVn2000(cell.getStringCellValue());
				}
			}
			results.add(rawData);
			rowIndex++;
		}

		return results;
	}
	public static void exportReportForm16DataFollowNew(List<ReportForm16> list, String titleHeader,
													ServletOutputStream outputStream) throws IOException {
		Workbook workbook = new HSSFWorkbook();
		int index = 1;
		if (list != null && list.size() > 0) {
			for (ReportForm16 reportForm16 : list) {
				Sheet sheet = workbook.createSheet(
						index + "-" + reportForm16.getAnimal().getCode() + " - " + reportForm16.getAnimal().getName());


				PropertyTemplate pt = new PropertyTemplate();
				Font font = workbook.createFont();
				font.setFontHeightInPoints((short) 12);
				font.setFontName("Times New Roman");
				font.setBold(true);
				// them font cho table tran huu dat
				Font font1 = workbook.createFont();
				font.setFontHeightInPoints((short) 12);
				font.setFontName("Times New Roman");
				font.setBold(true);

				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setBorderBottom(BorderStyle.THIN);
				cellStyle.setBorderLeft(BorderStyle.THIN);
				cellStyle.setBorderTop(BorderStyle.THIN);
				cellStyle.setBorderRight(BorderStyle.THIN);
				cellStyle.setWrapText(true);
				cellStyle.setAlignment(HorizontalAlignment.CENTER);
				cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyle.setFont(font1);

				Font fontNoBorder = workbook.createFont();
				fontNoBorder.setFontHeightInPoints((short) 13);
				fontNoBorder.setBold(true);
				fontNoBorder.setFontName("Times New Roman");

				CellStyle cellStyleNoBoder = workbook.createCellStyle();
				cellStyleNoBoder.setWrapText(true);
				cellStyleNoBoder.setAlignment(HorizontalAlignment.CENTER);
				cellStyleNoBoder.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleNoBoder.setFont(fontNoBorder);

				CellStyle cellStyleBoldTable = workbook.createCellStyle();
				cellStyleBoldTable.setBorderBottom(BorderStyle.THIN);
				cellStyleBoldTable.setBorderLeft(BorderStyle.THIN);
				cellStyleBoldTable.setBorderTop(BorderStyle.THIN);
				cellStyleBoldTable.setBorderRight(BorderStyle.THIN);
				cellStyleBoldTable.setWrapText(true);
				cellStyleBoldTable.setAlignment(HorizontalAlignment.CENTER);
				cellStyleBoldTable.setVerticalAlignment(VerticalAlignment.CENTER);
				cellStyleBoldTable.setFont(font);

				Row row = null;
				Cell cell = null;

				int rowIndex = 0;
				int cellIndex = 0;
				sheet.createRow(0);
				sheet.createRow(2);

				row = sheet.getRow(2);
				cell = row.createCell(0);

				rowIndex = 0;
				cellIndex = 0;
				sheet.createRow(1);
				index = index + 1;

				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Tỉnh");
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
				cell.setCellValue("Huyện");
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
				cell.setCellValue("Tình trạng đăng ký (Chưa ĐK=2, Đã ĐK=1)");
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
				cell.setCellValue("Số giấy CN đăng ký");
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

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Mã số đăng ký");
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
				cell.setCellValue("Năm đăng ký");
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
				cell.setCellValue("Mục đích gây nuôi");
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
				cell.setCellValue("Nguồn gốc");
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
				cell.setCellValue("Số điện thoại");
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
				cell.setCellValue("Zalo");
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
				cell.setCellValue("Chủ cơ sở/ đại diện");
				sheet.setColumnWidth(cellIndex, 14 * 256);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex +1, cellIndex, cellIndex + 3));
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 1);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 2);
				cell.setCellStyle(cellStyleBoldTable);

				cell = row.createCell(cellIndex + 3);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Họ và tên");
				sheet.setColumnWidth(cellIndex, 25 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Năm sinh");
				sheet.setColumnWidth(cellIndex, 13 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("CMND/Căn cước");
				sheet.setColumnWidth(cellIndex, 20 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				cell.setCellValue("Điện thoại");
				sheet.setColumnWidth(cellIndex, 20 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				cellIndex++;
				row = sheet.getRow(rowIndex);
				cell = row.createCell(cellIndex);
				sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, cellIndex, cellIndex));
				cell.setCellValue("Mã xã (Theo danh mục ĐVHC của Quyết định 124/2004/QĐ-TTg)");
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 1);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 35 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				row = sheet.getRow(rowIndex + 2);
				cell = row.createCell(cellIndex);
				sheet.setColumnWidth(cellIndex, 35 * 256);
				cell.setCellStyle(cellStyleBoldTable);

				int rowStartTable = 3;
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				if (reportForm16 != null) {
					row = sheet.createRow(rowStartTable);
					cellIndex = -1;

					// Tỉnh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getProvince() != null && reportForm16.getProvince().getName() != null) {
						cell.setCellValue(
								reportForm16.getProvince().getName());
					} else
						cell.setCellValue("");

					// Huyện
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getDistrict() != null && reportForm16.getDistrict().getName() != null) {
						cell.setCellValue(reportForm16.getDistrict().getName());
					} else
						cell.setCellValue("");

					// Xã
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAdministrativeUnit() != null && reportForm16.getAdministrativeUnit().getName() != null) {
						cell.setCellValue(reportForm16.getAdministrativeUnit().getName());
					} else
						cell.setCellValue("");

					// Thôn/Ấp
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getVillage() != null)
						cell.setCellValue(reportForm16.getFarm().getVillage());
					else
						cell.setCellValue("");

					// Mã cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getCode() != null)
						cell.setCellValue(reportForm16.getFarm().getCode());
					else
						cell.setCellValue("");

					// Tên cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getName() != null)
						cell.setCellValue(reportForm16.getFarm().getName());
					else
						cell.setCellValue("");

					// Ngày import
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					String dateStr = "";
					if (reportForm16.getDateReport() != null) {
						try {
							dateStr = formatter.format(reportForm16.getDateReport());
						} catch (Exception e) {
							dateStr = "";
						}
						cell.setCellValue(dateStr);
					}
					// tên loài nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getName() != null) {
						cell.setCellValue(reportForm16.getAnimal().getName());
					} else {
						cell.setCellValue("");
					}

					// Tên khoa học
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getScienceName() != null) {
						cell.setCellValue(reportForm16.getAnimal().getScienceName());
					} else {
						cell.setCellValue("");
					}

					// mã loài
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getCode() != null) {
						cell.setCellValue(reportForm16.getAnimal().getCode());
					} else {
						cell.setCellValue("");
					}

					// Tổng số cá thể
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getTotal() != null) {
						cell.setCellValue(reportForm16.getTotal());
					} else {
						cell.setCellValue("");
					}
					// Tổng số cá thể Đực
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getMale() != null) {
						cell.setCellValue(reportForm16.getMale());
					} else {
						cell.setCellValue("");
					}
					// Tổng số cá thể cái
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFemale() != null) {
						cell.setCellValue(reportForm16.getFemale());
					} else {
						cell.setCellValue("");
					}
					// Tổng số cá thể khác
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getUnGender() != null) {
						cell.setCellValue(reportForm16.getUnGender());
					} else {
						cell.setCellValue("");
					}


					// Đực bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getMaleParent() != null) {
						cell.setCellValue(reportForm16.getMaleParent());
					} else {
						cell.setCellValue("");
					}
					// Cái bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFemaleParent() != null) {
						cell.setCellValue(reportForm16.getFemaleParent());
					} else {
						cell.setCellValue("");
					}
					// Không rõ Bố mẹ
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getUnGenderParent() != null) {
						cell.setCellValue(reportForm16.getUnGenderParent());
					} else {
						cell.setCellValue("");
					}
					// đực hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getMaleGilts() != null) {
						cell.setCellValue(reportForm16.getMaleGilts());
					} else {
						cell.setCellValue("");
					}
					// cái hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFemaleGilts() != null) {
						cell.setCellValue(reportForm16.getFemaleGilts());
					} else {
						cell.setCellValue("");
					}
					// Không rõ hậu bị
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getUnGenderGilts() != null) {
						cell.setCellValue(reportForm16.getUnGenderGilts());
					} else {
						cell.setCellValue("");
					}

					// Đực dưới 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getMaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getMaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// Cái dưới 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFemaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getFemaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// Không xác định dưới 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// đực trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getMaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getMaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// cái trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFemaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getFemaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// không xác định trên 1 tuổi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getUnGenderChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getUnGenderChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}

					// tổng đàn tăng
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getTotalImport() != null) {
						cell.setCellValue(reportForm16.getTotalImport());
					} else {
						cell.setCellValue("");
					}

					// đực bố mẹ tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportMaleParent() != null) {
						cell.setCellValue(reportForm16.getImportMaleParent());
					} else {
						cell.setCellValue("");
					}
					// cái bố mẹ tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportFemaleParent() != null) {
						cell.setCellValue(reportForm16.getImportFemaleParent());
					}else {
						cell.setCellValue("");
					}
					// không xác định bố mẹ tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportUnGenderParent() != null) {
						cell.setCellValue(reportForm16.getImportUnGenderParent());
					} else {
						cell.setCellValue("");
					}



					// đực hậu bị tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportMaleGilts() != null) {
						cell.setCellValue(reportForm16.getImportMaleGilts());
					} else {
						cell.setCellValue("");
					}
					// cái hậu bị tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportFemaleParent() != null) {
						cell.setCellValue(reportForm16.getImportFemaleParent());
					} else {
						cell.setCellValue("");
					}
					// không xác định hậu bị tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportUnGenderGilts() != null) {
						cell.setCellValue(reportForm16.getImportUnGenderGilts());
					} else {
						cell.setCellValue("");
					}

					// đực  dưới 1 tuổi tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportMaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getImportMaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// cái dưới 1 tuổi tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportFemaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getImportFemaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// không xác định dưới 1 tuổi tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getImportChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}

					// đực trên 1 năm tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportMaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getImportMaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// cái trên 1 năm tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportFemaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getImportFemaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// không xác định trên 1 năm tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportUnGenderChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getImportUnGenderChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}

					// Lý do  tăng đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getImportReason() != null) {
						cell.setCellValue(reportForm16.getImportReason());
					} else {
						cell.setCellValue("");
					}

					// tổng đàn giảm
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getTotalExport() != null) {
						cell.setCellValue(reportForm16.getTotalExport());
					} else {
						cell.setCellValue("");
					}

					// đực bố mẹ giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportMaleParent() != null) {
						cell.setCellValue(reportForm16.getExportMaleParent());
					} else {
						cell.setCellValue("");
					}
					// cái bố mẹ giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportFemaleParent() != null) {
						cell.setCellValue(reportForm16.getExportFemaleParent());
					} else {
						cell.setCellValue("");
					}
					// không xác định bố mẹ giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportUnGenderParent() != null) {
						cell.setCellValue(reportForm16.getExportUnGenderParent());
					} else {
						cell.setCellValue("");
					}



					// đực hậu bị giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportMaleGilts() != null) {
						cell.setCellValue(reportForm16.getExportMaleGilts());
					} else {
						cell.setCellValue("");
					}
					// cái hậu bị giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportFemaleParent() != null) {
						cell.setCellValue(reportForm16.getExportFemaleParent());
					} else {
						cell.setCellValue("");
					}
					// không xác định hậu bị giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportUnGenderGilts() != null) {
						cell.setCellValue(reportForm16.getExportUnGenderGilts());
					} else {
						cell.setCellValue("");
					}

					// đực  dưới 1 tuổi giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportMaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getExportMaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// cái dưới 1 tuổi giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportFemaleChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getExportFemaleChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}
					// không xác định dưới 1 tuổi giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportChildUnder1YearOld() != null) {
						cell.setCellValue(reportForm16.getExportChildUnder1YearOld());
					} else {
						cell.setCellValue("");
					}

					// đực trên 1 năm giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportMaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getExportMaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// cái trên 1 năm giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportFemaleChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getExportFemaleChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}
					// không xác định trên 1 năm giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportUnGenderChildOver1YearOld() != null) {
						cell.setCellValue(reportForm16.getExportUnGenderChildOver1YearOld());
					} else {
						cell.setCellValue("");
					}

					// Lý do  giảm đàn
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getExportReason() != null) {
						cell.setCellValue(reportForm16.getExportReason());
					} else {
						cell.setCellValue("");
					}

					// Tình trạng đk
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getStatus() != null
							&& reportForm16.getFarm().getStatus() == 3) {
						cell.setCellValue(1);
					} else if (reportForm16.getFarm() != null && reportForm16.getFarm().getStatus() != null
							&& reportForm16.getFarm().getStatus() == 2) {
						cell.setCellValue(2);
					} else
						cell.setCellValue("");

					// Số giấy cndk
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getNewRegistrationCode() != null) {
						cell.setCellValue(reportForm16.getFarm().getNewRegistrationCode());
					} else
						cell.setCellValue("");

					// Mã số đk
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOldRegistrationCode() != null) {
						cell.setCellValue(reportForm16.getFarm().getOldRegistrationCode());
					} else
						cell.setCellValue("");

					// Năm đăng ký
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getYearRegistration() != null) {
						cell.setCellValue(reportForm16.getFarm().getYearRegistration());
					} else
						cell.setCellValue("");

					// Mục đích nuôi
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getFarmProductTargets() != null
							&& reportForm16.getFarm().getFarmProductTargets().size() > 0) {
						String productTarrget = "";
						for (FarmProductTarget fpt : reportForm16.getFarm().getFarmProductTargets()) {
							List<String> listPT = new ArrayList<String>();
							listPT.add(fpt.getProductTarget().getCode());
							if (listPT.size() > 0) {
								for (int p = 0; p < listPT.size(); p++) {
									if (listPT.get(p) != null)
										productTarrget += listPT.get(p);
									if (listPT.size() - 1 != p) {
										productTarrget += ", ";
									} else
										productTarrget += "";
								}
							}
						}
						cell.setCellValue(productTarrget);
					} else
						cell.setCellValue("");

					// Nguồn gốc
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getAnimal() != null && reportForm16.getAnimal().getOriginal() != null) {
						cell.setCellValue(reportForm16.getAnimal().getOriginal().getCode());
					} else
						cell.setCellValue("");

					// Số điện thoại
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm().getPhoneNumber() != null)
						cell.setCellValue(reportForm16.getFarm().getPhoneNumber());
					else
						cell.setCellValue("");

					// Zalo
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm().getOwnerPhoneNumber() != null)
						cell.setCellValue(reportForm16.getFarm().getOwnerPhoneNumber());
					rowStartTable++;

//					// Kinh độ
//					cellIndex++;
//					cell = row.createCell(cellIndex);
//					cell.setCellStyle(cellStyle);
//					if (reportForm16.getFarm() != null && reportForm16.getFarm().getLongitude() != null) {
//						cell.setCellValue(reportForm16.getFarm().getLongitude());
//					} else {
//						cell.setCellValue("");
//					}
//					// Vĩ độ
//					cellIndex++;
//					cell = row.createCell(cellIndex);
//					cell.setCellStyle(cellStyle);
//					if (reportForm16.getFarm() != null && reportForm16.getFarm().getLatitude() != null) {
//						cell.setCellValue(reportForm16.getFarm().getLatitude());
//					} else {
//						cell.setCellValue("");
//					}
					// Họ tên
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOwnerName() != null) {
						cell.setCellValue(reportForm16.getFarm().getOwnerName());
					} else {
						cell.setCellValue("");
					}

					// Năm sinh
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOwnerDob() != null) {
						SimpleDateFormat format = new SimpleDateFormat("yyyy");
						cell.setCellValue(format.format(reportForm16.getFarm().getOwnerDob()));
					} else {
						cell.setCellValue("");
					}

					// CMND/căn cước
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOwnerCitizenCode() != null) {
						cell.setCellValue(reportForm16.getFarm().getOwnerCitizenCode());
					} else {
						cell.setCellValue("");
					}
					// số điện thoại chủ cơ sở
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getOwnerPhoneNumber() != null) {
						cell.setCellValue(reportForm16.getFarm().getOwnerPhoneNumber());
					} else {
						cell.setCellValue("");
					}
					// Mã xã
					cellIndex++;
					cell = row.createCell(cellIndex);
					cell.setCellStyle(cellStyle);
					if (reportForm16.getFarm() != null && reportForm16.getFarm().getAdministrativeUnit() != null) {
						cell.setCellValue(reportForm16.getFarm().getAdministrativeUnit().getCode());
					} else {
						cell.setCellValue("");
					}
					rowStartTable++;

				}

			}
		}
		workbook.write(outputStream);
	}


}