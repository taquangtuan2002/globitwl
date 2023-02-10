package com.globits.wl.service.impl;

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
import com.globits.wl.domain.Bran;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.ImportAnimalFeed;
import com.globits.wl.domain.Unit;
import com.globits.wl.dto.ImportAnimalFeedDto;
import com.globits.wl.dto.functiondto.ImportAnimalFeedSearchDto;
import com.globits.wl.dto.functiondto.ImportDrugSearchDto;
import com.globits.wl.dto.functiondto.ReportManagerDto;
import com.globits.wl.repository.BranRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.ImportAnimalFeedRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.repository.UnitRepository;
import com.globits.wl.service.ImportAnimalFeedService;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ImportAnimalFeedServiceImpl extends GenericServiceImpl<ImportAnimalFeed, Long>  implements ImportAnimalFeedService{
	@Autowired
	private ImportAnimalFeedRepository importAnimalFeedRepository;
	@Autowired
	private BranRepository branRepository;
	@Autowired
	private FarmRepository farmRepository; 
	@Autowired
	private ProductTargetRepository productTargetRepository;	
	@Autowired
	private OriginalRepository originalRepository;
	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;
	@Autowired
	private UnitRepository unitRepository;
	
	@Override
	public Page<ImportAnimalFeedDto> getListImportAnimalFeed(int pageIndex, int pageSize) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.importAnimalFeedRepository.getListAnimalFeedByType(pageable, WLConstant.AnimalFeedType.importAnimalFeed.getValue());
	}

	@Override
	public ImportAnimalFeedDto getImportAnimalFeedById(Long id) {
		return this.importAnimalFeedRepository.getAnimalFeedById(id);
	}

	
	
	@Override
	public ImportAnimalFeedDto saveImportAnimalFeed(ImportAnimalFeedDto importAnimalFeedDto, Long id) {
		ImportAnimalFeed importAnimalFeed = null;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User modifiedUser = null;
		LocalDateTime currentDate = LocalDateTime.now();
		String currentUserName = "Unknown User";
		if (authentication != null) {
			modifiedUser = (User) authentication.getPrincipal();
			currentUserName = modifiedUser.getUsername();
		}

		Double remainQuantity = null;
		double firstQuantity = 0;
		
		//chekc valid
		if (importAnimalFeedDto.getDateIssue() == null || importAnimalFeedDto.getBran() == null || importAnimalFeedDto.getBran().getId() == null || importAnimalFeedDto.getQuantity() < 1
				|| importAnimalFeedDto.getUnit() == null || importAnimalFeedDto.getUnit().getId() == null ||importAnimalFeedDto.getDateIssue().after(WLDateTimeUtil.getEndOfDay(new Date()))) {
			return null;
		}
		
		if (id != null) {// trường hợp edit
			importAnimalFeed = this.importAnimalFeedRepository.findOne(id);
		} else if (importAnimalFeedDto.getId() != null) {
			importAnimalFeed = this.importAnimalFeedRepository.findOne(importAnimalFeedDto.getId());
		}

		if (importAnimalFeed == null) {// trường hợp thêm mới
			importAnimalFeed = new ImportAnimalFeed();
			importAnimalFeed.setCreateDate(currentDate);
			importAnimalFeed.setCreatedBy(currentUserName);
			importAnimalFeed.setType(WLConstant.AnimalFeedType.importAnimalFeed.getValue());
			importAnimalFeed.setCode(autoGenBathCode(new Date()));
			importAnimalFeed.setRemainQuantity(importAnimalFeedDto.getQuantity());
		}
		else {
			//Nếu trường hợp sửa sẽ check valid số lượng và ngày.
			firstQuantity = importAnimalFeed.getQuantity();
			remainQuantity = this.checkRemainQuantity(importAnimalFeed.getId());

			if(importAnimalFeedDto.getQuantity() < (firstQuantity - remainQuantity.doubleValue()) ) {
				importAnimalFeedDto.setCode("-5");
				return importAnimalFeedDto;// so luong sua cua nhập khong duoc nho hon so luong export da duoc dung;
			}
			if(!this.validDateImport(importAnimalFeedDto.getDateIssue(), importAnimalFeedDto.getId())) {
				importAnimalFeedDto.setCode("-4");
				return importAnimalFeedDto;//  ngay import lon hon ngay export
			}
			if(importAnimalFeedDto.getDateIssue() != null) {
				importAnimalFeed.setDateIssue(importAnimalFeedDto.getDateIssue());
			}
			importAnimalFeed.setRemainQuantity(importAnimalFeedDto.getQuantity() - firstQuantity + remainQuantity.doubleValue()  );
		}
		
		importAnimalFeed.setExpiryDate(importAnimalFeedDto.getExpiryDate());
		importAnimalFeed.setProductionFacilities(importAnimalFeedDto.getProductionFacilities());
		importAnimalFeed.setSymbolCode(importAnimalFeedDto.getSymbolCode());
		importAnimalFeed.setLotNumberByManufacturer(importAnimalFeedDto.getLotNumberByManufacturer());
		importAnimalFeed.setDateOfManufacture(importAnimalFeedDto.getDateOfManufacture());

		if(importAnimalFeedDto.getDateIssue() != null)
			importAnimalFeed.setDateIssue(importAnimalFeedDto.getDateIssue());
		if(importAnimalFeedDto.getQuantity() != 0)
			importAnimalFeed.setQuantity(importAnimalFeedDto.getQuantity());
		if(importAnimalFeedDto.getAmount() != 0)
			importAnimalFeed.setAmount(importAnimalFeedDto.getAmount());
		if(importAnimalFeedDto.getDescription() !=  null)
			importAnimalFeed.setDescription(importAnimalFeedDto.getDescription());
		
		if(importAnimalFeedDto.getBran()!=null && importAnimalFeedDto.getBran().getId()!=null) {
			Bran bran=branRepository.findOne(importAnimalFeedDto.getBran().getId());
			if(bran!=null)
				importAnimalFeed.setBran(bran);
		}
			
		if (importAnimalFeedDto.getFarm() != null) {
			Farm farm = this.farmRepository.findOne(importAnimalFeedDto.getFarm().getId());			
			importAnimalFeed.setFarm(farm);
		}
		importAnimalFeed.setPrice(importAnimalFeedDto.getPrice());
		if(importAnimalFeedDto.getSupplier()!=null)
		importAnimalFeed.setSupplier(importAnimalFeedDto.getSupplier());
		if (importAnimalFeedDto.getUnit() != null) {
			Unit unit = unitRepository.findOne(importAnimalFeedDto.getUnit().getId());
			importAnimalFeed.setUnit(unit);
		}
		
		this.importAnimalFeedRepository.save(importAnimalFeed);
		importAnimalFeedDto.setId(importAnimalFeed.getId());

		this.updateListExport(importAnimalFeed);
		return importAnimalFeedDto;
	}

	private void updateListExport(ImportAnimalFeed importAnimalFeed) {
		List<ImportAnimalFeed> listExport = this.importAnimalFeedRepository.findExportByImport(importAnimalFeed.getId());
		if(listExport != null && listExport.size() > 0) {
			for(ImportAnimalFeed export : listExport) {
				if(export != null) {
					export.setCode(importAnimalFeed.getCode());
					export.setBran(importAnimalFeed.getBran());
					export.setFarm(importAnimalFeed.getFarm());
					export.setPrice(importAnimalFeed.getPrice());
					export.setUnit(importAnimalFeed.getUnit());
				}
			}
			this.importAnimalFeedRepository.save(listExport);
		}
	}

	private boolean validDateImport(Date dateImport, Long importDrugId) {
		dateImport = WLDateTimeUtil.getStartOfDay(dateImport);
		List<ImportAnimalFeed> exportAnimalFeeds = this.importAnimalFeedRepository.getListExportByGTImportDateAndImportAnimalFeed(dateImport, importDrugId);
		if(exportAnimalFeeds!= null && exportAnimalFeeds.size() > 0) {
			return false;
		}
		return true;
	}

	private Double checkRemainQuantity(Long id) {
		ImportAnimalFeed importAnimalFeed = importAnimalFeedRepository.findOne(id);
		if (importAnimalFeed != null) {
			List<ImportAnimalFeed> listExport = importAnimalFeedRepository.findExportByImport(id);
			if (listExport != null && listExport.size() > 0) {
				Double ret = importAnimalFeed.getQuantity();
				for (ImportAnimalFeed exportDrug : listExport) {
					ret -= exportDrug.getQuantity();
				}
				return ret;
			} else {
				return importAnimalFeed.getQuantity();
			}
		}
		return null;
	}

	@Override
	public ImportAnimalFeedDto removeImportAnimalFeed(Long id) {
		ImportAnimalFeedDto exportAnimalDto = new ImportAnimalFeedDto();
		ImportAnimalFeed feed = importAnimalFeedRepository.findOne(id);
		
		if (feed != null) {
			List<ImportAnimalFeed> listExport = importAnimalFeedRepository.findExportByImport(feed.getId());
			if(listExport != null && listExport.size() > 0) {
				exportAnimalDto.setCode("-1");
				return exportAnimalDto;// Bản ghi này dữ liệu đã được sử dụng để xuất không thể xóa
			}
			this.importAnimalFeedRepository.delete(id);
			exportAnimalDto = new ImportAnimalFeedDto(feed);
		}
		return exportAnimalDto;
	}

	@Override
	public Page<ImportAnimalFeedDto> searchDto(
			ImportAnimalFeedSearchDto searchDto, int pageIndex, int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		String namecode = searchDto.getNameOrCode();
		String sql = " select new com.globits.wl.dto.ImportAnimalFeedDto(fa) from ImportAnimalFeed fa  where (1=1)";
		String sqlCount = "select count(fa.id) from ImportAnimalFeed fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.code like :namecode or fa.farm.name like :namecode or fa.farm.code like :namecode )";
		}
		if(searchDto.getFarmId()!=null){
			whereClause += " and (fa.farm.id= :farmId)";
		}
		if(searchDto.getBranId()!=null) {//
			whereClause += " and (fa.bran.id= :branId)";
		}
		if(searchDto.getType()!=null) {//
			whereClause += " and (fa.type= :type)";
		}
		if(searchDto.getFromDate()!=null) {//
			whereClause += " and (fa.dateIssue>= :fromDate)";
		}
		if(searchDto.getToDate()!=null) {//
			whereClause += " and (fa.dateIssue<= :toDate)";
		}
		if(searchDto.getProvince()!=null && searchDto.getProvince()>0) {
			whereClause += " and (fa.farm.administrativeUnit.parent.parent.id= :provinceId)";
		}
		if(searchDto.getDistrict()!=null && searchDto.getDistrict()>0) {
			whereClause += " and (fa.farm.administrativeUnit.parent.id= :districtId)";
		}
		if(searchDto.getWard()!=null && searchDto.getWard()>0) {
			whereClause += " and (fa.farm.administrativeUnit.id= :wardId)";
		}
				
		sql +=whereClause;
		sql +=" order by fa.dateIssue desc ";
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,ImportAnimalFeedDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}	
		if(searchDto.getFarmId()!=null){
			q.setParameter("farmId", searchDto.getFarmId());
			qCount.setParameter("farmId", searchDto.getFarmId());
		}
		if(searchDto.getBranId()!=null) {//
			q.setParameter("branId", searchDto.getBranId());
			qCount.setParameter("branId", searchDto.getBranId());			
		}
		if(searchDto.getType()!=null) {//
			q.setParameter("type", searchDto.getType());
			qCount.setParameter("type", searchDto.getType());
		}
		if(searchDto.getFromDate()!=null) {//
			q.setParameter("fromDate", searchDto.getFromDate());
			qCount.setParameter("fromDate", searchDto.getFromDate());
		}
		if(searchDto.getToDate()!=null) {//
			searchDto.setToDate(WLDateTimeUtil.getEndOfDay(searchDto.getToDate()));
			q.setParameter("toDate", searchDto.getToDate());
			qCount.setParameter("toDate", searchDto.getToDate());
		}		
		if(searchDto.getProvince()!=null && searchDto.getProvince()>0) {
			q.setParameter("provinceId", searchDto.getProvince());
			qCount.setParameter("provinceId", searchDto.getProvince());
		}
		if(searchDto.getDistrict()!=null && searchDto.getDistrict()>0) {
			q.setParameter("districtId", searchDto.getDistrict());
			qCount.setParameter("districtId", searchDto.getDistrict());
		}
		if(searchDto.getWard()!=null && searchDto.getWard()>0) {
			q.setParameter("wardId", searchDto.getWard());
			qCount.setParameter("wardId", searchDto.getWard());
		}
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<ImportAnimalFeedDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;	
	}

	@Override
	public ReportManagerDto getSumQuantity(ImportAnimalFeedSearchDto paramDto) {
		ReportManagerDto ret=new ReportManagerDto();
		Double quantity=sumQuantity(paramDto);
		int count=count(paramDto);
		ret.setQuantity(quantity);
		ret.setCount(count);
		return ret;
	}
	public double sumQuantity(ImportAnimalFeedSearchDto paramDto ){
		Double ret=0D;
		String sql=" SELECT ";
		if(paramDto.getType()==null || 
				(paramDto.getType()!=WLConstant.AnimalFeedType.importAnimalFeed.getValue()
					&& paramDto.getType()!=WLConstant.AnimalFeedType.exportAnimalFeed.getValue()
				))
			{			
				sql+= "SUM(iea.quantity*iea.type) ";//Tính tổng tồn
			}
			else {
				sql+= "SUM(iea.quantity) ";//Tính tổng nhập hoặc xuất
			}
		sql+= "FROM ImportAnimalFeed iea WHERE 1=1 ";
		String namecode = paramDto.getNameOrCode();
		String whereClause="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and ( iea.farm.name like :namecode or iea.farm.code like :namecode )";
		}
		if(paramDto.getFromDate()!=null) {
			whereClause+=" AND iea.dateIssue >= :fromDate ";
		}
		if(paramDto.getToDate()!=null) {
			whereClause+=" AND iea.dateIssue<= :toDate ";
		}
		
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {
			whereClause+=" AND iea.farm.id = :farmId ";
		}		
		if(paramDto.getProvince()!=null && paramDto.getProvince()>0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.parent.id= :provinceId)";
		}
		if(paramDto.getDistrict()!=null && paramDto.getDistrict()>0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.id= :districtId)";
		}
		if(paramDto.getWard()!=null && paramDto.getWard()>0) {
			whereClause += " and (iea.farm.administrativeUnit.id= :wardId)";
		}
		if(paramDto.getBranId() != null) {
			whereClause += " and iea.bran.id = :branId ";
		}
		if (paramDto.getType() != null) {
			whereClause += " and (iea.type= :type)";
		}
		
		sql = sql + whereClause ;
		System.out.println(sql);
		Query q = manager.createQuery(sql);
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');			
		}	
		if(paramDto.getFromDate()!=null) {
			q.setParameter("fromDate", paramDto.getFromDate());
		}
		if(paramDto.getToDate()!=null) {
			paramDto.setToDate(WLDateTimeUtil.getEndOfDay(paramDto.getToDate()));
			q.setParameter("toDate", paramDto.getToDate());
		}
		
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {		
			q.setParameter("farmId", paramDto.getFarmId());
		}	
		if(paramDto.getProvince()!=null && paramDto.getProvince()>0) {
			q.setParameter("provinceId", paramDto.getProvince());
			
		}
		if(paramDto.getDistrict()!=null && paramDto.getDistrict()>0) {
			q.setParameter("districtId", paramDto.getDistrict());
			
		}
		if(paramDto.getWard()!=null && paramDto.getWard()>0) {
			q.setParameter("wardId", paramDto.getWard());
			
		}
		if(paramDto.getBranId() != null) {
			q.setParameter("branId", paramDto.getBranId());
		}
		if (paramDto.getType() != null) {
			q.setParameter("type", paramDto.getType());
		}
		
		
		Double results = (Double)q.getSingleResult();
		
		if(results!=null && results>0) {
			ret=results;		
		}
		
		return ret;
	}
	public int count(ImportAnimalFeedSearchDto paramDto ){
		int ret=0;
		String sql=" SELECT ";
		//sql+= "SUM(iea.quantity),count(iea.id)  ";//Tính tổng 
		sql+= "count(iea.id) ";//Tính tổng 
		sql+= "FROM ImportAnimalFeed iea WHERE 1=1 ";
		
		String namecode = paramDto.getNameOrCode();
		String whereClause="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (iea.farm.name like :namecode or iea.farm.code like :namecode )";
		}
		if(paramDto.getFromDate()!=null) {
			whereClause+=" AND iea.dateIssue >= :fromDate ";
		}
		if(paramDto.getToDate()!=null) {
			whereClause+=" AND iea.dateIssue <= :toDate ";
		}
		
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {
			whereClause+=" AND iea.farm.id = :farmId ";
		}		
		if(paramDto.getProvince()!=null && paramDto.getProvince()>0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.parent.id= :provinceId)";
		}
		if(paramDto.getDistrict()!=null && paramDto.getDistrict()>0) {
			whereClause += " and (iea.farm.administrativeUnit.parent.id= :districtId)";
		}
		if(paramDto.getWard()!=null && paramDto.getWard()>0) {
			whereClause += " and (iea.farm.administrativeUnit.id= :wardId)";
		}
		if (paramDto.getType() != null) {
			whereClause += " and (iea.type= :type)";
		}
		
		sql = sql + whereClause ;
		System.out.println(sql);
		Query q = manager.createQuery(sql);
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');			
		}	
		if(paramDto.getFromDate()!=null) {
			q.setParameter("fromDate", paramDto.getFromDate());
		}
		if(paramDto.getToDate()!=null) {
			paramDto.setToDate(WLDateTimeUtil.getEndOfDay(paramDto.getToDate()));
			q.setParameter("toDate", paramDto.getToDate());
		}
		
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {		
			q.setParameter("farmId", paramDto.getFarmId());
		}		
		if(paramDto.getProvince()!=null && paramDto.getProvince()>0) {
			q.setParameter("provinceId", paramDto.getProvince());
			
		}
		if(paramDto.getDistrict()!=null && paramDto.getDistrict()>0) {
			q.setParameter("districtId", paramDto.getDistrict());
			
		}
		if(paramDto.getWard()!=null && paramDto.getWard()>0) {
			q.setParameter("wardId", paramDto.getWard());
			
		}
		if (paramDto.getType() != null) {
			q.setParameter("type", paramDto.getType());
		}
		
		Long results = (Long)q.getSingleResult();
		
		if(results!=null && results>0) {
			ret=results.intValue();		
		}
		
		return ret;
	}
	public String autoGenBathCode(Date importDate){
		String bathCode="";
		String y="";
		String m="";
		String d="";
		Integer max=0;
		String code="";
		Date end=null;
		if(importDate!=null){
			Calendar cal = Calendar.getInstance();
			cal.setTime(importDate);
			int year = cal.get(Calendar.YEAR);
			 y=String.valueOf(year);
			y=y.substring(2, 4);
			int month = cal.get(Calendar.MONTH) +1;
			if(month<10){
				m="0"+String.valueOf(month);
			}else{
				m=String.valueOf(month);
			}
			int day = cal.get(Calendar.DAY_OF_MONTH);
			if(day<10){
				d="0"+String.valueOf(day);
			}else{
				d=String.valueOf(day);
			}
			bathCode=y+m+d;
			cal.set(Calendar.MILLISECOND, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			importDate=cal.getTime();
			end=WLDateTimeUtil.getEndOfDay(importDate);
			max=this.importAnimalFeedRepository.count(WLConstant.AnimalFeedType.importAnimalFeed.getValue(), importDate,end);
			if(max!=null && max>0){
				max=max+1;
			}else{
				max=1;
			}
			code=bathCode;
			bathCode=bathCode +"-"+max;
			
			//phòng trường hợp đã tồn tại bathCode
			List<ImportAnimalFeedDto> list=importAnimalFeedRepository.findByTypeAndBatchCode(WLConstant.AnimalFeedType.importAnimalFeed.getValue(), bathCode);
			if(list!=null && list.size()>0) {
				max=max+1;
				bathCode=code +"-"+max;
			}
		}
		return bathCode;
	}

	@Override
	public ReportManagerDto reportInventory(ImportAnimalFeedSearchDto paramDto) {
		ReportManagerDto reportManagerDto = new ReportManagerDto();
		paramDto.setType(WLConstant.AnimalFeedType.importAnimalFeed.getValue());
		double totalImport = this.sumQuantity(paramDto);
		paramDto.setType(WLConstant.AnimalFeedType.exportAnimalFeed.getValue());
		double totalExport = this.sumQuantity(paramDto);
		paramDto.setType(null);
		double totalInventory = this.sumQuantity(paramDto);
		
		reportManagerDto.setTotalExport(totalExport);
		reportManagerDto.setTotalImport(totalImport);
		reportManagerDto.setTotalInventory(totalInventory);
		return reportManagerDto;
	}

	@Override
	public boolean validDateIssue(Long id) {
		if(id != null) {
			List<ImportAnimalFeed> listExport = importAnimalFeedRepository.findExportByImport(id);
			if(listExport != null && listExport.size() > 0) {
				return false;
			}
		}
		return true;
	}

}
