package com.globits.wl.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.Drug;
import com.globits.wl.domain.ExportEgg;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.ImportDrug;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.Unit;
import com.globits.wl.dto.ImportDrugDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.functiondto.ImportDrugSearchDto;
import com.globits.wl.dto.functiondto.ReportManagerDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.repository.DrugRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.ImportDrugRepository;
import com.globits.wl.repository.ImportExportAnimalRepository;
import com.globits.wl.repository.UnitRepository;
import com.globits.wl.service.ImportDrugService;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ImportDrugServiceImpl extends GenericServiceImpl<ImportDrug, Long> implements ImportDrugService {
	@Autowired
	private ImportDrugRepository importDrugRepository;
	@Autowired
	private DrugRepository drugRepository;
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private UnitRepository unitRepository;
	@Autowired
	private ImportExportAnimalRepository importExportAnimalRepository;

	@Override
	public Page<ImportDrugDto> getListImportDrug(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.importDrugRepository.getListImportDrug(pageable);
	}

	@Override
	public ImportDrugDto getImportDrugById(Long id) {
		return this.importDrugRepository.getImportDrugById(id);
	}

	@Override
	public ImportDrugDto saveImportDrug(ImportDrugDto importDrugDto, Long id) {
		ImportDrug importDrug = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		double firstQuantity = 0;
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}
		if (id != null) {// trường hợp edit
			importDrug = this.importDrugRepository.findOne(id);
		} else if (importDrugDto.getId() != null) {
			importDrug = this.importDrugRepository.findOne(importDrugDto.getId());
		}
		if(importDrug != null) {
			firstQuantity = importDrug.getQuantity();
		}
		if(importDrugDto.getQuantity() <= 0) {
			return null;// bat buoc nhap quantity > 0
		}
		Double remainQuantity = null;
		if(WLConstant.ImportExportDrug.importDrug.getValue().intValue() == importDrugDto.getType().intValue()) {
			if(importDrugDto.getId()!= null) {
				remainQuantity = this.checkRemainQuantity(importDrugDto.getId());
			}else {
				remainQuantity = importDrugDto.getQuantity();
			}
			if(importDrugDto.getUnit() == null) {
				return null;// don vi tinh khong duoc null
			}
			if(importDrugDto.getDrug() == null) {
				return null;// thuoc khong duoc null
			}
		}else if(WLConstant.ImportExportDrug.exportDrug.getValue().intValue() == importDrugDto.getType().intValue()) {
			remainQuantity = this.checkRemainQuantity(importDrugDto.getImportDrug().getId());
		}
		if (importDrug == null) {// trường hợp thêm mới
			importDrug = new ImportDrug();
			importDrug.setCreateDate(currentDate);
			importDrug.setCreatedBy(currentUserName);
		}
		if(importDrugDto.getCode() != null)
			importDrug.setCode(importDrugDto.getCode());
		if(importDrugDto.getQuantity() != 0)
			importDrug.setQuantity(importDrugDto.getQuantity());
		
		if(importDrugDto.getPreservation() !=  null)
			importDrug.setPreservation(importDrugDto.getPreservation());

		if(importDrugDto.getDrug()!=null && importDrugDto.getDrug().getId()!=null) {
			Drug bran=drugRepository.findOne(importDrugDto.getDrug().getId());
			if(bran!=null)
				importDrug.setDrug(bran);
		}
			
		if (importDrugDto.getFarm() != null) {
			Farm farm = this.farmRepository.findOne(importDrugDto.getFarm().getId());			
			importDrug.setFarm(farm);
		}
		importDrug.setPrice(importDrugDto.getPrice());
		if(importDrugDto.getSupplier()!=null)
			importDrug.setSupplier(importDrugDto.getSupplier());
		
		Unit unit = null;
		if(importDrugDto.getUnit()!= null && importDrugDto.getUnit().getId() != null) {
			unit = unitRepository.findOne(importDrugDto.getUnit().getId());
		}
		importDrug.setUnit(unit);
		
		importDrug.setDateOfManufacture(importDrugDto.getDateOfManufacture());
		importDrug.setExpiryDate(importDrugDto.getExpiryDate());
		importDrug.setProducer(importDrugDto.getProducer());
		importDrug.setSymbolCode(importDrugDto.getSymbolCode());
		importDrug.setTradenames(importDrugDto.getTradenames());
		importDrug.setBatchCodeManufacture(importDrugDto.getBatchCodeManufacture());
		
		
		if(importDrugDto.getType().intValue() == WLConstant.ImportExportDrug.importDrug.getValue().intValue()) {// type = 1 : nhap thuoc
			// check null dau vao
			if(importDrugDto.getDateImport()== null) {
				return null;
			}
			if(id != null) {// update
				if(importDrugDto.getQuantity() < (firstQuantity - remainQuantity.doubleValue()) ) {
					importDrugDto.setCode("-5");
					return importDrugDto;// so luong sua cua nhập khong duoc nho hon so luong export da duoc dung;
				}
				if(!this.validDateImport(WLDateTimeUtil.getStartOfDay(importDrugDto.getDateImport()), importDrugDto.getId())) {
					importDrugDto.setCode("-4");
					return importDrugDto;//  ngay import lon hon ngay export
				}
				if(importDrugDto.getDateImport() != null) {
					importDrug.setDateImport(importDrugDto.getDateImport());
				}
				importDrug.setRemainQuantity(importDrugDto.getQuantity() - firstQuantity + remainQuantity.doubleValue()  );
				this.updateListExport(importDrug);
			}else {// tao moi
				if(importDrugDto.getDateImport() != null) {
					importDrug.setDateImport(importDrugDto.getDateImport());
				}
				importDrug.setType(WLConstant.ImportExportDrug.importDrug.getValue().intValue());
				if(importDrugDto.getDateImport()!= null) {
					String batchCode = this.autoGenBathCode(importDrugDto.getDateImport());
					importDrug.setCode(batchCode);
				}
				importDrug.setRemainQuantity(importDrugDto.getQuantity());
				this.updateListExport(importDrug);
			}
		}else {
			if(importDrugDto.getImportDrug() == null || importDrugDto.getImportDrug().getId() == null ) {
				return null;// khong co importDrug 
			}
			if(importDrugDto.getDateImport() != null) {
				importDrug.setDateImport(importDrugDto.getDateImport());
			}
			if(importDrugDto.getImportExportAnimal() != null && importDrugDto.getImportExportAnimal().getId() != null) {
				ImportExportAnimal importExportAnimal = null;
				importExportAnimal = importExportAnimalRepository.findById(importDrugDto.getImportExportAnimal().getId());
				importDrug.setImportExportAnimal(importExportAnimal);
			}
			if(importDrugDto.getType().intValue() == WLConstant.ImportExportDrug.exportDrug.getValue().intValue()) {// type = -1 xuat thuoc
				if(importDrugDto.getQuantity() > (remainQuantity +  firstQuantity)) {
					importDrugDto.setCode("-3");
					return importDrugDto;// so luong xuat khong duoc lon hon so luong con lai
				}
				importDrug.setType(WLConstant.ImportExportDrug.exportDrug.getValue().intValue());
				ImportDrug importDrugParent = null;
				if(importDrugDto.getImportDrug()!= null && importDrugDto.getImportDrug().getId() != null) {
					importDrugParent = importDrugRepository.findOne(importDrugDto.getImportDrug().getId());
					if(importDrugParent != null && importDrugParent.getDateImport() != null && importDrug.getDateImport() != null && importDrug.getDateImport().compareTo(WLDateTimeUtil.getStartOfDay(importDrugParent.getDateImport())) < 0  ) {
						importDrugDto.setCode("-2");
						return importDrugDto;//  ngay xuat nho hon ngay nhap
					}
					if(importDrugParent != null && importDrugParent.getExpiryDate() != null && importDrug.getDateImport() != null && WLDateTimeUtil.getStartOfDay(importDrug.getDateImport()).compareTo(importDrugParent.getExpiryDate()) > 0  ) {
						importDrugDto.setCode("-6");
						return importDrugDto;//  ngày xuất nhỏ hơn hạn sử dụng
					}
					importDrug.setCode(importDrugParent.getCode());
					importDrug.setDrug(importDrugParent.getDrug());
					importDrug.setFarm(importDrugParent.getFarm());
					importDrug.setPreservation(importDrugParent.getPreservation());
					importDrug.setPrice(importDrugParent.getPrice());
					importDrug.setSupplier(importDrugParent.getSupplier());
					importDrug.setUnit(importDrugParent.getUnit());
					
					importDrugParent.setRemainQuantity(remainQuantity + firstQuantity - importDrugDto.getQuantity());
					importDrug.setImportDrug(importDrugParent);
					this.importDrugRepository.save(importDrugParent);
				}else {
					return null;// khong lam khi khong co phieu import;
				}
			}else {
				return null;// khong co gia tri type phu hop
			}
		}
		
		this.importDrugRepository.save(importDrug);
		importDrugDto.setId(importDrug.getId());
		return importDrugDto;
	}

	@Override
	public ImportDrugDto removeImportDrug(Long id) {
		ImportDrugDto exportAnimalDto = new ImportDrugDto();
		ImportDrug importDrug = null;
		ImportDrug feed = importDrugRepository.findOne(id);
		
		if(feed.getImportDrug()!= null && feed.getImportDrug().getId() != null) {
			importDrug= importDrugRepository.findOne(feed.getImportDrug().getId());
		}
		if (feed != null) {
			exportAnimalDto = new ImportDrugDto(feed);
			if(importDrug != null) {// xóa xuất
				importDrug.setRemainQuantity(importDrug.getRemainQuantity() + feed.getQuantity());
				this.importDrugRepository.save(importDrug);
			}else {// xóa nhập
				if(importDrug == null) {
					List<ImportDrug> listExport = importDrugRepository.findExportByImport(feed.getId());
					if(listExport != null && listExport.size() > 0) {
						exportAnimalDto.setCode("-1");
						return exportAnimalDto;// Bản ghi này dữ liệu đã được sử dụng để xuất không thể xóa
					}
				}
			}
			this.importDrugRepository.delete(id);
		}
		return exportAnimalDto;
	}

	@Override
	public Page<ImportDrugDto> searchDto(ImportDrugSearchDto searchDto, int pageIndex, int pageSize) {
		if (pageIndex > 0)
			pageIndex = pageIndex - 1;
		else
			pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);

		String namecode = searchDto.getNameOrCode();
		String sql = " select new com.globits.wl.dto.ImportDrugDto(fa) from ImportDrug fa  where (1=1)";
		String sqlCount = "select count(fa.id) from ImportDrug fa where (1=1)";
		String whereClause = "";
		if (namecode != null && namecode.length() > 0) {
			whereClause += " and (fa.code like :namecode or fa.farm.name like :namecode or fa.farm.code like :namecode )";
		}
		if (searchDto.getFarmId() != null) {
			whereClause += " and (fa.farm.id= :farmId)";
		}
		if (searchDto.getDrugId() != null) {//
			whereClause += " and (fa.drug.id= :drugId)";
		}

		if (searchDto.getFromDate() != null) {//
			whereClause += " and (fa.dateImport>= :fromDate)";
		}
		if (searchDto.getToDate() != null) {//
			whereClause += " and (fa.dateImport<= :toDate)";
		}
		if (searchDto.getProvince() != null && searchDto.getProvince() > 0) {
			whereClause += " and (fa.farm.administrativeUnit.parent.parent.id= :provinceId)";
		}
		if (searchDto.getDistrict() != null && searchDto.getDistrict() > 0) {
			whereClause += " and (fa.farm.administrativeUnit.parent.id= :districtId)";
		}
		if (searchDto.getWard() != null && searchDto.getWard() > 0) {
			whereClause += " and (fa.farm.administrativeUnit.id= :wardId)";
		}
		if (searchDto.getType() != null) {
			whereClause += " and (fa.type= :type)";
		}

		sql += whereClause;
		sql += " order by fa.dateImport desc ";
		sqlCount += whereClause;

		Query q = manager.createQuery(sql, ImportDrugDto.class);
		Query qCount = manager.createQuery(sqlCount);

		if (namecode != null && namecode.length() > 0) {
			q.setParameter("namecode", '%' + namecode + '%');
			qCount.setParameter("namecode", '%' + namecode + '%');
		}
		if (searchDto.getFarmId() != null) {
			q.setParameter("farmId", searchDto.getFarmId());
			qCount.setParameter("farmId", searchDto.getFarmId());
		}
		if (searchDto.getDrugId() != null) {//
			q.setParameter("drugId", searchDto.getDrugId());
			qCount.setParameter("drugId", searchDto.getDrugId());
		}

		if (searchDto.getFromDate() != null) {//
			q.setParameter("fromDate", searchDto.getFromDate());
			qCount.setParameter("fromDate", searchDto.getFromDate());
		}
		if (searchDto.getToDate() != null) {//
			searchDto.setToDate(WLDateTimeUtil.getEndOfDay(searchDto.getToDate()));
			q.setParameter("toDate", searchDto.getToDate());
			qCount.setParameter("toDate", searchDto.getToDate());
		}
		if (searchDto.getProvince() != null && searchDto.getProvince() > 0) {
			q.setParameter("provinceId", searchDto.getProvince());
			qCount.setParameter("provinceId", searchDto.getProvince());
		}
		if (searchDto.getDistrict() != null && searchDto.getDistrict() > 0) {
			q.setParameter("districtId", searchDto.getDistrict());
			qCount.setParameter("districtId", searchDto.getDistrict());
		}
		if (searchDto.getWard() != null && searchDto.getWard() > 0) {
			q.setParameter("wardId", searchDto.getWard());
			qCount.setParameter("wardId", searchDto.getWard());
		}
		if (searchDto.getType() != null) {
			q.setParameter("type", searchDto.getType());
			qCount.setParameter("type", searchDto.getType());
		}
		q.setFirstResult((pageIndex) * pageSize);
		q.setMaxResults(pageSize);

		Long numberResult = 0L;
		Object obj = qCount.getSingleResult();
		if (obj != null) {
			numberResult = (Long) obj;
		}
		Page<ImportDrugDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
		return page;
	}

	@Override
	public ReportManagerDto getSumQuantity(ImportDrugSearchDto paramDto) {
		ReportManagerDto ret = new ReportManagerDto();
		Double quantity = sumQuantity(paramDto);
		int count = count(paramDto);
		ret.setQuantity(quantity);
		ret.setCount(count);
		return ret;
	}

	public double sumQuantity(ImportDrugSearchDto paramDto) {
		Double ret = 0D;
		String sql = " SELECT ";
		// sql+= "SUM(iea.quantity),count(iea.id) ";//Tính tổng
		if(paramDto.getType()==null || 
				(paramDto.getType()!=WLConstant.ImportExportEggType.importEgg.getValue()
					&& paramDto.getType()!=WLConstant.ImportExportEggType.exportEgg.getValue()
				))
			{			
				sql+= "SUM(iea.quantity*iea.type) ";//Tính tổng tồn
			}
			else {
				sql+= "SUM(iea.quantity) ";//Tính tổng nhập hoặc xuất
			}
		sql += "FROM ImportDrug iea WHERE 1=1 ";
		String namecode = paramDto.getNameOrCode();
		String whereClause = "";
		if (namecode != null && namecode.length() > 0) {
			whereClause += " and ( iea.farm.name like :namecode or iea.farm.code like :namecode or iea.code like :namecode)";
		}
		if (paramDto.getFromDate() != null) {
			whereClause += " AND iea.dateImport >= :fromDate ";
		}
		if (paramDto.getToDate() != null) {
			whereClause += " AND iea.dateImport<= :toDate ";
		}

		if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
			whereClause += " AND iea.farm.id = :farmId ";
		}
		if (paramDto.getProvince() != null && paramDto.getProvince() > 0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.parent.id= :provinceId)";
		}
		if (paramDto.getDistrict() != null && paramDto.getDistrict() > 0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.id= :districtId)";
		}
		if (paramDto.getWard() != null && paramDto.getWard() > 0) {
			whereClause += " and (iea.farm.administrativeUnit.id= :wardId)";
		}
		if (paramDto.getType() != null) {
			whereClause += " and (iea.type= :type)";
		}

		sql = sql + whereClause;
		System.out.println(sql);
		Query q = manager.createQuery(sql);
		if (namecode != null && namecode.length() > 0) {
			q.setParameter("namecode", '%' + namecode + '%');
		}
		if (paramDto.getFromDate() != null) {
			q.setParameter("fromDate", paramDto.getFromDate());
		}
		if (paramDto.getToDate() != null) {
			paramDto.setToDate(WLDateTimeUtil.getEndOfDay(paramDto.getToDate()));
			q.setParameter("toDate", paramDto.getToDate());
		}

		if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
			q.setParameter("farmId", paramDto.getFarmId());
		}
		if (paramDto.getProvince() != null && paramDto.getProvince() > 0) {
			q.setParameter("provinceId", paramDto.getProvince());

		}
		if (paramDto.getDistrict() != null && paramDto.getDistrict() > 0) {
			q.setParameter("districtId", paramDto.getDistrict());

		}
		if (paramDto.getWard() != null && paramDto.getWard() > 0) {
			q.setParameter("wardId", paramDto.getWard());

		}
		if (paramDto.getType() != null) {
			q.setParameter("type", paramDto.getType());
		}
		Double results = (Double) q.getSingleResult();

		if (results != null && results > 0) {
			ret = results;
		}

		return ret;
	}

	public int count(ImportDrugSearchDto paramDto) {
		int ret = 0;
		String sql = " SELECT ";
		// sql+= "SUM(iea.quantity),count(iea.id) ";//Tính tổng
		sql += "count(iea.id) ";// Tính tổng
		sql += "FROM ImportDrug iea WHERE 1=1 ";

		String namecode = paramDto.getNameOrCode();
		String whereClause = "";
		if (namecode != null && namecode.length() > 0) {
			whereClause += " and (iea.farm.name like :namecode or iea.farm.code like :namecode or iea.code like :namecode)";
		}
		if (paramDto.getFromDate() != null) {
			whereClause += " AND iea.dateImport >= :fromDate ";
		}
		if (paramDto.getToDate() != null) {
			whereClause += " AND iea.dateImport<= :toDate ";
		}

		if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
			whereClause += " AND iea.farm.id = :farmId ";
		}
		if (paramDto.getProvince() != null && paramDto.getProvince() > 0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.parent.id= :provinceId)";
		}
		if (paramDto.getDistrict() != null && paramDto.getDistrict() > 0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.id= :districtId)";
		}
		if (paramDto.getWard() != null && paramDto.getWard() > 0) {
			whereClause += " and (iea.farm.administrativeUnit.id= :wardId)";
		}
		if (paramDto.getType() != null) {
			whereClause += " and (iea.type= :type)";
		}

		sql = sql + whereClause;
		System.out.println(sql);
		Query q = manager.createQuery(sql);
		if (namecode != null && namecode.length() > 0) {
			q.setParameter("namecode", '%' + namecode + '%');
		}
		if (paramDto.getFromDate() != null) {
			q.setParameter("fromDate", paramDto.getFromDate());
		}
		if (paramDto.getToDate() != null) {
			paramDto.setToDate(WLDateTimeUtil.getEndOfDay(paramDto.getToDate()));
			q.setParameter("toDate", paramDto.getToDate());
		}

		if (paramDto.getFarmId() != null && paramDto.getFarmId() > 0) {
			q.setParameter("farmId", paramDto.getFarmId());
		}
		if (paramDto.getProvince() != null && paramDto.getProvince() > 0) {
			q.setParameter("provinceId", paramDto.getProvince());

		}
		if (paramDto.getDistrict() != null && paramDto.getDistrict() > 0) {
			q.setParameter("districtId", paramDto.getDistrict());

		}
		if (paramDto.getWard() != null && paramDto.getWard() > 0) {
			q.setParameter("wardId", paramDto.getWard());

		}
		if (paramDto.getType() != null) {
			q.setParameter("type", paramDto.getType());
		}
		Long results = (Long) q.getSingleResult();

		if (results != null && results > 0) {
			ret = results.intValue();
		}

		return ret;
	}

	public String autoGenBathCode(Date importDate) {
		String bathCode = "";
		String y = "";
		String m = "";
		String d = "";
		Integer max = 0;
		String code = "";
		Date end = null;
		if (importDate != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(importDate);
			int year = cal.get(Calendar.YEAR);
			y = String.valueOf(year);
			y = y.substring(2, 4);
			int month = cal.get(Calendar.MONTH) + 1;
			if (month < 10) {
				m = "0" + String.valueOf(month);
			} else {
				m = String.valueOf(month);
			}
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if (day < 10) {
				d = "0" + String.valueOf(day);
			} else {
				d = String.valueOf(day);
			}
			bathCode = y + m + d;
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			importDate = cal.getTime();
			end = WLDateTimeUtil.getEndOfDay(importDate);
			max = this.importDrugRepository.count(1, importDate, end);
			if (max != null && max > 0) {
				max = max + 1;
			} else {
				max = 1;
			}
			code = bathCode;
			bathCode = bathCode + "-" + max;

			// phòng trường hợp đã tồn tại bathCode
			List<ImportDrug> list = importDrugRepository
					.findByTypeAndBatchCode(WLConstant.ImportExportDrug.importDrug.getValue(), bathCode);
			if (list != null && list.size() > 0) {
				max = max + 1;
				bathCode = code + "-" + max;
			}
		}
		return bathCode;
	}

	public Double checkRemainQuantity(Long importDrugId) {
		ImportDrug importDrug = importDrugRepository.findOne(importDrugId);
		if (importDrug != null) {
			List<ImportDrug> listExport = importDrugRepository.findExportByImport(importDrugId);
			if (listExport != null && listExport.size() > 0) {
				Double ret = importDrug.getQuantity();
				for (ImportDrug exportDrug : listExport) {
					ret -= exportDrug.getQuantity();
				}
				return ret;
			} else {
				return importDrug.getQuantity();
			}
		}
		return null;
	}
	
	public boolean validDateImport(Date dateImport, Long importDrugId) {
		List<ImportDrug> exportDrugs = this.importDrugRepository.getListExportByGTImportDateAndImportDrug(dateImport, importDrugId);
		if(exportDrugs!= null && exportDrugs.size() > 0) {
			for(ImportDrug importDrug: exportDrugs) {
				if(importDrug != null) {
					return false;
				}
			}
		}
		return true;
	}
	void updateListExport(ImportDrug importDrug) {
		List<ImportDrug> listExport = this.importDrugRepository.findExportByImport(importDrug.getId());
		if(listExport != null && listExport.size() > 0) {
			for(ImportDrug export : listExport) {
				if(export != null) {
					export.setCode(importDrug.getCode());
					export.setDrug(importDrug.getDrug());
					export.setFarm(importDrug.getFarm());
					export.setPreservation(importDrug.getPreservation());
					export.setPrice(importDrug.getPrice());
					export.setSupplier(importDrug.getSupplier());
					export.setUnit(importDrug.getUnit());
				}
			}
			this.importDrugRepository.save(listExport);
		}
	}

	@Override
	public boolean validDelete(Long importId) {
		List<ImportDrug> exportDrugs = this.importDrugRepository.findExportByImport(importId);
		if(exportDrugs!= null && exportDrugs.size() > 0) {
			for(ImportDrug importDrug: exportDrugs) {
				if(importDrug!= null) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public ReportManagerDto reportInventory(ImportDrugSearchDto paramDto) {
		ReportManagerDto reportManagerDto = new ReportManagerDto();
		paramDto.setType(WLConstant.ImportExportDrug.importDrug.getValue());
		double totalImport = this.sumQuantity(paramDto);
		paramDto.setType(WLConstant.ImportExportDrug.exportDrug.getValue());
		double totalExport = this.sumQuantity(paramDto);
		paramDto.setType(null);
		double totalInventory = this.sumQuantity(paramDto);
		
		
		reportManagerDto.setTotalExport(totalExport);
		reportManagerDto.setTotalImport(totalImport);
		reportManagerDto.setTotalInventory(totalInventory);
		return reportManagerDto;
	}

}
