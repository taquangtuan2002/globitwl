package com.globits.wl.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
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

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.security.domain.User;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.ExportEgg;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.ImportExportAnimal;
import com.globits.wl.domain.ImportExportLiveStockProduct;
import com.globits.wl.domain.InjectionPlant;
import com.globits.wl.domain.InjectionTime;
import com.globits.wl.domain.LiveStockProduct;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.domain.SeedLevel;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.ImportExportLiveStockProductDto;
import com.globits.wl.dto.InjectionPlantDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ObjectDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.report.InventoryReportDto;
import com.globits.wl.dto.report.LiveStockProductReportDto;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.ImportExportAnimalRepository;
import com.globits.wl.repository.ImportExportLiveStockProductRepository;
import com.globits.wl.repository.InjectionPlantRepository;
import com.globits.wl.repository.InjectionTimeRepository;
import com.globits.wl.repository.LiveStockProductRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.ImportExportLivelStockProductService;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ImportExportLiveStockProductServiceImpl extends GenericServiceImpl<ImportExportLiveStockProduct, Long> implements ImportExportLivelStockProductService {

	@Autowired
	private ImportExportLiveStockProductRepository importExportLiveStockProductRepository;
	
	@Autowired
	private FarmRepository farmRepository;

	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;
	
	@Autowired
	private LiveStockProductRepository liveStockProductRepository;

	@Override
	public Page<ImportExportLiveStockProductDto> getByPage(int pageIndex, int pageSize, int type) {
		if (pageIndex > 1) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		return this.importExportLiveStockProductRepository.getByPage(pageable,type);
	}

	@Override
	public ImportExportLiveStockProductDto getById(Long id) {
		ImportExportLiveStockProductDto ret=new ImportExportLiveStockProductDto();
		ret=this.importExportLiveStockProductRepository.getById(id);
		return ret;
	}

	@Override
	public ImportExportLiveStockProductDto save(Long id, ImportExportLiveStockProductDto dto) {
		if (dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			boolean isNew=false;
			double remainQuantity=0;//số lượng còn lại
			double remainAmount=0;//Khối lượng còn lại
			ImportExportLiveStockProduct old=null;
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}

			ImportExportLiveStockProduct importExportLSP = null;
			ImportExportLiveStockProduct importRecord = null;//Phiếu nhập, trong trường hợp xuất sản phẩm chăn nuôi
			//Check các điều kiện, nếu không đạt thì không lưu lại
			if(dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue() 
					&&( dto.getFarm()==null 
					|| dto.getDateIssue()==null 
					|| dto.getLiveStockProductDto()==null 
					
					|| ((Double.valueOf(dto.getQuantity())==null || dto.getQuantity()<=0) 
							&& (Double.valueOf(dto.getAmount())==null || dto.getAmount()<=0))
					)
				) {
				return null;
			}
			else if(dto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				if(dto.getFarm()==null 
						|| dto.getDateIssue()==null
						|| ((Double.valueOf(dto.getQuantity())==null || dto.getQuantity()<=0) 
								&& (Double.valueOf(dto.getAmount())==null || dto.getAmount()<=0))
						) {
					return null;
				}
				if(dto.getImportLiveStockPorduct()!=null
						&& dto.getImportLiveStockPorduct().getId()!=null
						&& dto.getImportLiveStockPorduct().getId()>0L) {
					importRecord = importExportLiveStockProductRepository.findOne(dto.getImportLiveStockPorduct().getId());
				}
				if(importRecord==null) {//Nếu không thấy phiếu nhập (trong trường hợp xuất sản phẩm chăn nuôi) thì trả về null
					return null;
				}
				else {
					//số lượng còn lại
					remainQuantity = this.checkRemainQuantity(importRecord.getId());
					// tính khối lượng còn lại
					remainAmount = this.checkRemainAmount(importRecord.getId());
				}
			}

			if(id!=null) {
				importExportLSP = this.importExportLiveStockProductRepository.findOne(id);
				old=this.importExportLiveStockProductRepository.findOne(id);;
			}
			else if (dto.getId() != null) {
				importExportLSP = this.importExportLiveStockProductRepository.findOne(dto.getId());
				old=this.importExportLiveStockProductRepository.findOne(dto.getId());
			}
			else if (importExportLSP == null) {
				importExportLSP = new ImportExportLiveStockProduct();
				isNew=true;
				importExportLSP.setCreateDate(currentDate);
				importExportLSP.setCreatedBy(currentUserName);								
			}
			importExportLSP.setModifiedBy(currentUserName);
			importExportLSP.setModifyDate(currentDate);
			importExportLSP.setType(dto.getType());
			importExportLSP.setDateIssue(dto.getDateIssue());
			//Nếu số lượng xuất SPCN nhiều hơn số lượng còn lại thì trả về null, đã tổng quát cả trường hợp sửa và thêm mới
			//Hoặc khối lượng xuất SPCN nhiều hơn khối lượng còn lại thì trả về null
			if(importExportLSP.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()
					&& (dto.getQuantity()>remainQuantity+importExportLSP.getQuantity()|| dto.getAmount()>remainAmount+importExportLSP.getAmount())) {
				return null;
			}
			
			if (dto.getFarm() != null && dto.getFarm().getId() != null) {
				Farm farm = this.farmRepository.findOne(dto.getFarm().getId());							
				importExportLSP.setFarm(farm);
			}
			LiveStockProduct lsp = null;
			if(dto.getLiveStockProductDto()!=null && dto.getLiveStockProductDto().getId()!=null) {
				lsp = liveStockProductRepository.findOne(dto.getLiveStockProductDto().getId());
			}
			if (lsp == null) {
				return null;
			}
			importExportLSP.setLiveStockProduct(lsp);
			if (lsp.getUnitAmount() != null && lsp.getUnitAmount().getId() != null) {
				importExportLSP.setAmount(dto.getAmount());
			}else {
				importExportLSP.setAmount(0);
			}

			if (lsp.getUnitQuantity() != null && lsp.getUnitQuantity().getId() != null) {
				importExportLSP.setQuantity(dto.getQuantity());
			}else {
				importExportLSP.setQuantity(0);
			}

			if(isNew && dto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				String code=autoGenBathCode(dto.getDateIssue());
				importExportLSP.setBatchCode(code);
			}else if (dto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				importExportLSP.setBatchCode(importRecord.getBatchCode());
				
			}
			if (dto.getDateIssue() != null) {
				importExportLSP.setDateIssue(importExportLSP.getDateIssue());
			}			
			
			importExportLSP.setDescription(dto.getDescription());
	
			if(dto.getProvincial() != null && dto.getProvincial().getId() != null) {
				FmsAdministrativeUnit province = this.fmsAdministrativeUnitRepository.findOne(dto.getProvincial().getId());
				importExportLSP.setProvince(province);
			}
			importExportLSP.setVoucherCode(dto.getVoucherCode());
			importExportLSP.setExportType(dto.getExportType());
			importExportLSP.setExportReason(dto.getExportReason());
			importExportLSP.setBuyerName(dto.getBuyerName());
			importExportLSP.setBuyerAdress(dto.getBuyerAdress());			
			importExportLSP.setImportLiveStockPorduct(importRecord);
			
			importExportLSP = this.importExportLiveStockProductRepository.save(importExportLSP);
			
			if(importExportLSP.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {
				remainQuantity = this.checkRemainQuantity(importExportLSP.getId());
				importExportLSP.setRemainQuantity(remainQuantity);
				remainAmount = this.checkRemainAmount(importExportLSP.getId());
				importExportLSP.setRemainAmount(remainAmount);
				importExportLSP = this.importExportLiveStockProductRepository.save(importExportLSP);
				//cập nhật lại xuất sản phẩm chăn nuôi nếu có thay đổi về farm, liveStockProduct
				if(importExportLSP.getFarm() != null && importExportLSP.getId() != null && importExportLSP.getLiveStockProduct()!=null ) {
					this.updateDateInfoExport(importExportLSP.getId(), importExportLSP.getFarm(), importExportLSP.getLiveStockProduct());
				}
			}
			else if(importExportLSP.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()) {
				if(importExportLSP.getImportLiveStockPorduct()!=null) {
					remainQuantity = this.checkRemainQuantity(importExportLSP.getImportLiveStockPorduct().getId());
					importExportLSP.getImportLiveStockPorduct().setRemainQuantity(remainQuantity);
					remainAmount = this.checkRemainAmount(importExportLSP.getImportLiveStockPorduct().getId());
					importExportLSP.getImportLiveStockPorduct().setRemainAmount(remainAmount);
					this.importExportLiveStockProductRepository.save(importExportLSP.getImportLiveStockPorduct());
				}
				
			}			
			dto.setId(importExportLSP.getId());
			
			return dto;
		}
		return null;
	}
	public Double checkRemainQuantity(Long importLiveStockProductId) {
		ImportExportLiveStockProduct importLiveStockProduct=importExportLiveStockProductRepository.findOne(importLiveStockProductId);
		if(importLiveStockProduct!=null) {
			List<ImportExportLiveStockProduct> listExport = importExportLiveStockProductRepository.findExportByImport(importLiveStockProductId);
			if(listExport!=null && listExport.size()>0) {
				double ret=importLiveStockProduct.getQuantity();
				for (ImportExportLiveStockProduct importExport : listExport) {
					ret-=importExport.getQuantity();
				}
				return ret;
			}
			else {
				return importLiveStockProduct.getQuantity();
			}
		}
		return null;
	}
	
	public Double checkRemainAmount(Long importLiveStockProductId) {
		ImportExportLiveStockProduct importLiveStockProduct=importExportLiveStockProductRepository.findOne(importLiveStockProductId);
		if(importLiveStockProduct!=null) {
			List<ImportExportLiveStockProduct> listExport = importExportLiveStockProductRepository.findExportByImport(importLiveStockProductId);
			if(listExport!=null && listExport.size()>0) {
				double ret=importLiveStockProduct.getAmount();
				for (ImportExportLiveStockProduct importExport : listExport) {
					ret-=importExport.getAmount();
				}
				return ret;
			}
			else {
				return importLiveStockProduct.getAmount();
			}
		}
		return null;
	}

	@Override
	public Page<ImportExportLiveStockProductDto> searchDto(ImportExportAnimalSearchDto searchDto, int pageIndex,
			int pageSize) {
		if(pageIndex > 0) pageIndex = pageIndex-1;
		else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		
		Date fromDate = null;
		Date toDate = null;
		if (searchDto.getFromDate() != null) {
			fromDate = WLDateTimeUtil.getStartOfDay(searchDto.getFromDate());
		}
		if (searchDto.getToDate() != null) {
			toDate = WLDateTimeUtil.getEndOfDay(searchDto.getToDate());
		}
		
		String namecode = searchDto.getNameOrCode();
		String sql = " select new com.globits.wl.dto.ImportExportLiveStockProductDto(fa) from ImportExportLiveStockProduct fa  where (1=1)";
		String sqlCount = "select count(fa.id) from ImportExportLiveStockProduct fa where (1=1)";
		String whereClause ="";
		if(namecode!=null && namecode.length()>0) {
			whereClause += " and (fa.batchCode like :namecode or fa.farm.code like :namecode or fa.farm.name like :namecode )";
		}
		if(searchDto.getFarmId()!=null){
			whereClause += " and (fa.farm.id= :farmId)";
		}
		if(searchDto.getCreate_by()!=null){
			whereClause += " and (fa.created_by= :created_by)";
		}
		if(searchDto.getCreate_date()!=null){
			whereClause += " and (fa.create_date= :create_date)";
		}
		if(searchDto.getWardsId()!=null) {//đây là query theo xã
			whereClause += " and (fa.farm.administrativeUnit.id= :wardsId)";
		}
		if(searchDto.getDistrictId()!=null) {//đây là query theo huyện
			whereClause += " and (fa.farm.administrativeUnit.parent.id= :districtId)";
		}
		if(searchDto.getCityId()!=null) {
			whereClause += " and fa.farm.administrativeUnit.parent.parent.id  = :provinceId";
		}

		if(searchDto.getFromDate()!=null){
			whereClause += " and (fa.dateIssue >= :fromDate)";
		}
		if(searchDto.getToDate()!=null){
			whereClause += " and (fa.dateIssue <= :toDate)";
		}
		
		whereClause += " and (fa.type= :type)";
						
		sql +=whereClause;
		
		sql +=" order by fa.dateIssue desc, fa.batchCode desc ";
		
		sqlCount+=whereClause;

		Query q = manager.createQuery(sql,ImportExportLiveStockProductDto.class);
		Query qCount = manager.createQuery(sqlCount);
		
		
		if(namecode!=null && namecode.length()>0) {
			q.setParameter("namecode", '%'+namecode+'%');
			qCount.setParameter("namecode", '%'+namecode+'%');
		}	
		if(searchDto.getFarmId()!=null){
			q.setParameter("farmId", searchDto.getFarmId());
			qCount.setParameter("farmId", searchDto.getFarmId());
		}
		if(searchDto.getCreate_by()!=null){
			q.setParameter("created_by", searchDto.getCreate_by());
			qCount.setParameter("created_by", searchDto.getCreate_by());
		}
		if(searchDto.getCreate_date()!=null){
			q.setParameter("create_date", searchDto.getCreate_date());
			qCount.setParameter("create_date", searchDto.getCreate_date());
		}
		if(searchDto.getWardsId()!=null) {//đây là query theo xã
			q.setParameter("wardsId", searchDto.getWardsId());
			qCount.setParameter("wardsId", searchDto.getWardsId());			
		}
		if(searchDto.getDistrictId()!=null) {//đây là query theo huyện
			q.setParameter("districtId", searchDto.getDistrictId());
			qCount.setParameter("districtId", searchDto.getDistrictId());
		}
		if(searchDto.getCityId()!=null ) {//đây là query theo tỉnh 
			q.setParameter("provinceId", searchDto.getCityId());
			qCount.setParameter("provinceId", searchDto.getCityId());
		}
		
		if(searchDto.getFromDate()!=null) {
			q.setParameter("fromDate", fromDate);
			qCount.setParameter("fromDate", fromDate);
		}
		if(searchDto.getToDate()!=null) {
			q.setParameter("toDate", toDate);
			qCount.setParameter("toDate", toDate);
		}
		
		q.setParameter("type", searchDto.getType());
		qCount.setParameter("type", searchDto.getType());
		
		
		q.setFirstResult((pageIndex)*pageSize);
		q.setMaxResults(pageSize);
		
		Long numberResult =(Long)qCount.getSingleResult();
		Page<ImportExportLiveStockProductDto> page = new PageImpl<>(q.getResultList(), pageable,numberResult);		
		return page;
	}

	@Override
	public String autoGenBathCode(Date importDate) {
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
			max=this.importExportLiveStockProductRepository.count(1, importDate,end);
			if(max!=null && max>0){
				max=max+1;
			}else{
				max=1;
			}
			code=bathCode;
			bathCode=bathCode +"-"+max;
			
			//phòng trường hợp đã tồn tại bathCode
			List<ImportExportLiveStockProduct> list=importExportLiveStockProductRepository.findByTypeAndBatchCode(WLConstant.ImportExportAnimalType.importAnimal.getValue(), bathCode);
			if(list!=null && list.size()>0) {
				max=max+1;
				bathCode=code +"-"+max;
			}
		}
		return bathCode;
	}

	@Override
	public ObjectDto deleteById(Long id) {
		ObjectDto dto=new ObjectDto();
		if (id != null) {
			ImportExportLiveStockProduct iea=this.importExportLiveStockProductRepository.findOne(id);	
			if(iea!=null){
				if(iea.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue()){
					if(iea.getImportLiveStockPorduct()!=null && iea.getImportLiveStockPorduct().getId()!=null){
						ImportExportLiveStockProduct im=this.importExportLiveStockProductRepository.findOne(iea.getImportLiveStockPorduct().getId());
						if(im!=null ){
							double remain=im.getRemainQuantity() + iea.getQuantity();
							im.setRemainQuantity(remain);
							double remainAmount=im.getRemainAmount() + iea.getAmount();
							im.setRemainAmount(remainAmount);
							this.importExportLiveStockProductRepository.save(im);
						}
					}
				}else if(iea.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()) {					
					if(iea!=null && iea.getId()!=null && iea.getFarm()!=null &&  iea.getFarm().getId()!=null && iea.getBatchCode()!=null){
						List<ImportExportLiveStockProduct> list=this.importExportLiveStockProductRepository.findBy(WLConstant.ImportExportAnimalType.exportAnimal.getValue(), iea.getFarm().getId(), iea.getBatchCode());
						if(list!=null && list.size()>0) {
							dto.setCode("-2");
							dto.setName("Không thể xóa bản ghi nhập  vì còn dữ liệu xuất !");
							return dto;
						}
					}
				}
				
				this.importExportLiveStockProductRepository.delete(id);	
				
				return dto;
			}
			
		}
		return dto;
	}

	@Override
	public List<LiveStockProductReportDto> getSumQuantity(ReportParamDto paramDto) {
		String sql=" SELECT ";
		if(paramDto.getType()!=null && 
				(paramDto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue() || 
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()))
		{
			sql+= "SUM(iea.quantity),SUM(iea.amount),COUNT(iea.farm.id) %s ";//Tính tổng xuất hoặc nhập 
		}
		else 
		{
			sql+= "SUM(iea.quantity * iea.type),SUM(iea.amount * iea.type),COUNT(iea.farm.id) %s ";//Tính tồn kho
		}
		sql+= "FROM ImportExportLiveStockProduct iea WHERE 1=1 ";
		
		String whereClause="";
		if(paramDto.getFromDate()!=null) {
			whereClause+=" AND iea.dateIssue >= :fromDate ";
		}
		if(paramDto.getToDate()!=null) {
			whereClause+=" AND iea.dateIssue<= :toDate ";
		}
		if(paramDto.getBatchCode()!=null && paramDto.getBatchCode()!="") {
			whereClause+=" AND iea.batchCode = :batchCode ";
		}
		
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {
			whereClause+=" AND iea.farm.id = :farmId ";
		}		
			
		if(paramDto.getLiveStockProductId()!=null && paramDto.getLiveStockProductId()>0) {
			whereClause+=" AND iea.liveStockProduct.id = :liveStockProductId ";
		}
		if(paramDto.getWardId()!=null && paramDto.getWardId()>0) {
			whereClause+=" AND iea.farm.administrativeUnit.id = :wardId ";
		}
		if(paramDto.getDistrictId()!=null && paramDto.getDistrictId()>0) {
			whereClause+=" AND iea.farm.administrativeUnit.parent.id = :districtId ";
		}
		if(paramDto.getProvinceId()!=null && paramDto.getProvinceId()>0) {
			whereClause+=" AND iea.farm.administrativeUnit.parent.parent.id = :provinceId ";
		}
		if(paramDto.getRegionId()!=null && paramDto.getRegionId()>0) {
			whereClause+=" AND iea.farm.administrativeUnit.parent.parent.region.id = :regionId ";
		}
//		if(paramDto.getExportReason()!=null && paramDto.getExportReason().length()>0) {
//			whereClause+=" AND iea.exportReason = :exportReason ";
//		}
//		if(paramDto.getExportType()>0) {
//			whereClause+=" AND iea.exportType = :exportType ";			
//		}
		if(paramDto.getType()!=null && 
				(paramDto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue() || 
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()))
		{
			whereClause+=" AND iea.type = :type ";
		}
		if(paramDto.getOwnershipId()!=null && paramDto.getOwnershipId()>0L) {
			whereClause+=" AND iea.farm.ownership.id = :ownershipId ";
		}
		if(paramDto.getSalanganeHouseType()!=null) {
			whereClause+=" AND iea.farm.salanganeHouseType = :salanganeHouseType ";
		}
		
				
		String batchCode = " ";
		String farm = " ";
		String liveStockProduct = " ";
		String ward = " ";
		String district = " ";
		String province = " ";
		String region = " ";
		
		String orderByClause="";
		
		List<String> columns = new ArrayList<String>();
		columns.add(WLConstant.FunctionAndGroupByItem.quantity.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.amount.getValue());
		columns.add(WLConstant.FunctionAndGroupByItem.countFarm.getValue());
		
		String groupByClause="";
		String selectClause="";
		if(paramDto.getGroupByItems()!=null && paramDto.getGroupByItems().size()>0) {
			
			for (String grItem : paramDto.getGroupByItems()) {
				
				if(WLConstant.FunctionAndGroupByItem.batchCode.getValue().equals(grItem)) {
					groupByClause+=" iea.batchCode, ";
					batchCode=" ,iea.batchCode ";
					selectClause+=batchCode;
					columns.add(WLConstant.FunctionAndGroupByItem.batchCode.getValue());
				}
				if(WLConstant.FunctionAndGroupByItem.farm.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.id,iea.farm.name, ";
					farm=" ,iea.farm.id,iea.farm.name ";
					selectClause+=farm;
					columns.add(WLConstant.FunctionAndGroupByItem.farm.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.farm.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.liveStockProduct.getValue().equals(grItem)) {
					groupByClause+=" iea.liveStockProduct.id,iea.liveStockProduct.name, ";
					liveStockProduct=" ,iea.liveStockProduct.id,iea.liveStockProduct.name ";
					selectClause+=liveStockProduct;
					columns.add(WLConstant.FunctionAndGroupByItem.liveStockProduct.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.liveStockProduct.getValue()+"name");
				}
				
				if(WLConstant.FunctionAndGroupByItem.ward.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.administrativeUnit.id,iea.farm.administrativeUnit.name, ";
					ward=" ,iea.farm.administrativeUnit.id,iea.farm.administrativeUnit.name ";
					selectClause+=ward;
					columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.ward.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.district.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.administrativeUnit.parent.id,iea.farm.administrativeUnit.parent.name, ";
					district=" ,iea.farm.administrativeUnit.parent.id,iea.farm.administrativeUnit.parent.name ";
					selectClause+=district;
					columns.add(WLConstant.FunctionAndGroupByItem.district.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.district.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.province.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.administrativeUnit.parent.parent.id,iea.farm.administrativeUnit.parent.parent.name, ";
					province=" ,iea.farm.administrativeUnit.parent.parent.id,iea.farm.administrativeUnit.parent.parent.name ";
					selectClause+=province;
					columns.add(WLConstant.FunctionAndGroupByItem.province.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.province.getValue()+"name");
				}
				if(WLConstant.FunctionAndGroupByItem.region.getValue().equals(grItem)) {
					groupByClause+=" iea.farm.administrativeUnit.parent.parent.region.id,iea.farm.administrativeUnit.parent.parent.region.name, ";
					region=" ,iea.farm.administrativeUnit.parent.parent.region.id,iea.farm.administrativeUnit.parent.parent.region.name ";
					selectClause+=region;
					columns.add(WLConstant.FunctionAndGroupByItem.region.getValue()+"id");
					columns.add(WLConstant.FunctionAndGroupByItem.region.getValue()+"name");
				}
				
			}
		}
		sql = String.format(sql, selectClause);
		if(groupByClause!="") {
			groupByClause = " GROUP BY "+ groupByClause.substring(0, groupByClause.length() - 2);
		}	
		
		sql = sql + whereClause + groupByClause;
		System.out.println(sql);
		Query q = manager.createQuery(sql);
		
		if(paramDto.getFromDate()!=null) {
			q.setParameter("fromDate", paramDto.getFromDate());
		}
		if(paramDto.getToDate()!=null) {
			q.setParameter("toDate", paramDto.getToDate());
		}
		if(paramDto.getBatchCode()!=null && paramDto.getBatchCode()!="") {
			q.setParameter("batchCode", paramDto.getBatchCode());
		}
		
		if(paramDto.getFarmId()!=null && paramDto.getFarmId()>0) {		
			q.setParameter("farmId", paramDto.getFarmId());
		}		
		if(paramDto.getLiveStockProductId()!=null && paramDto.getLiveStockProductId()>0) {
			q.setParameter("liveStockProductId", paramDto.getLiveStockProductId());
		}		
		
		if(paramDto.getWardId()!=null && paramDto.getWardId()>0) {
			q.setParameter("wardId", paramDto.getWardId());
		}
		if(paramDto.getDistrictId()!=null && paramDto.getDistrictId()>0) {
			q.setParameter("districtId", paramDto.getDistrictId());
		}
		if(paramDto.getProvinceId()!=null && paramDto.getProvinceId()>0) {
			q.setParameter("provinceId", paramDto.getProvinceId());
		}
		if(paramDto.getRegionId()!=null && paramDto.getRegionId()>0) {
			q.setParameter("regionId", paramDto.getRegionId());
		}
			
		if(paramDto.getType()!=null && 
				(paramDto.getType()==WLConstant.ImportExportAnimalType.exportAnimal.getValue() || 
				 paramDto.getType()==WLConstant.ImportExportAnimalType.importAnimal.getValue()))
		{
			q.setParameter("type", paramDto.getType());			
		}
		if(paramDto.getOwnershipId()!=null && paramDto.getOwnershipId()>0L) {
			q.setParameter("ownershipId", paramDto.getOwnershipId());
		}
		if(paramDto.getSalanganeHouseType()!=null) {
			q.setParameter("salanganeHouseType", paramDto.getSalanganeHouseType());
		}
		
		List<Object[]> results = q.getResultList();
		List<LiveStockProductReportDto> ret = new ArrayList<LiveStockProductReportDto>();
		
		if(results!=null) {
			for (Object[] re : results) {
				LiveStockProductReportDto io = new LiveStockProductReportDto(re,columns);				
				ret.add(io);
			}
		}
		return ret;
	}

	@Override
	public List<LiveStockProductReportDto> getQuantityReport(ReportParamDto paramDto) throws ParseException {
		if(paramDto!=null) {
			List<LiveStockProductReportDto> ret = new ArrayList<LiveStockProductReportDto>();
			List<LiveStockProductReportDto> subRet = new ArrayList<LiveStockProductReportDto>();
			if(paramDto.getReportType()==WLConstant.ReportType.list.getValue()) {
				if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.years.getValue()) {
					for (int i = paramDto.getFromYear(); i <= paramDto.getToYear(); i++) {
						Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);
						if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
							fromDate = WLDateTimeUtil.numberToDate(1, 1, i);
						}
					    Date toDate= WLDateTimeUtil.numberToDate(31, 12, i);				    
					    paramDto.setFromDate(fromDate);
					    paramDto.setToDate(toDate);				    
					    subRet = this.getSumQuantity(paramDto);
					    if(subRet!=null) {
					    	for (ImportExportLiveStockProductDto importExportDto : subRet) {
					    		importExportDto.setYearReport(i);
							}
					    	ret.addAll(subRet);
					    }
					}
				}
				else if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.months.getValue()) {
					for (int i = paramDto.getFromMonth(); i <= paramDto.getToMonth(); i++) {
						Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);
						if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
							fromDate = WLDateTimeUtil.numberToDate(1, i, paramDto.getCurrentYear());
						}
					    Date toDate= WLDateTimeUtil.getLastDayOfMonth(i, paramDto.getCurrentYear());
					    paramDto.setFromDate(fromDate);
					    paramDto.setToDate(toDate);				    
					    subRet = this.getSumQuantity(paramDto);
					    if(subRet!=null) {
					    	for (ImportExportLiveStockProductDto importExportDto : subRet) {
					    		importExportDto.setYearReport(paramDto.getCurrentYear());
					    		importExportDto.setMonthReport(i);
							}
					    	ret.addAll(subRet);
					    }
					}
				}
				else if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.fromToMonth.getValue()) {
					Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);
					if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getFromMonth(), paramDto.getFromYear());
					}
					Date toDate= WLDateTimeUtil.numberToDate(31, paramDto.getToMonth(), paramDto.getToYear());	
					paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportLiveStockProductDto importExportDto : subRet) {
				    		importExportDto.setYearReport(paramDto.getFromYear());
				    		importExportDto.setMonthReport(paramDto.getFromMonth());
				    		importExportDto.setToMonthReport(paramDto.getToMonth());
				    		importExportDto.setToYearReport(paramDto.getToYear());
						}
				    	ret.addAll(subRet);
				    }
					 
					
				}
			}
			else if(paramDto.getReportType()==WLConstant.ReportType.compare.getValue()) {
				if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.years.getValue()) {
					Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);
					if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, 1, paramDto.getFromYear());
					}
					
				    Date toDate= WLDateTimeUtil.numberToDate(31, 12, paramDto.getFromYear());				    
				    paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportLiveStockProductDto importExportDto : subRet) {
				    		importExportDto.setYearReport(paramDto.getFromYear());
						}
				    	ret.addAll(subRet);
				    }
				    
				    fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);			
				    if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, 1, paramDto.getToYear());
					}
				    toDate= WLDateTimeUtil.numberToDate(31, 12, paramDto.getToYear());				    
				    paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportLiveStockProductDto importExportDto : subRet) {
				    		importExportDto.setYearReport(paramDto.getToYear());
						}
				    	ret.addAll(subRet);
				    }				    
				}
				else if(paramDto.getPeriodType()==WLConstant.ReportPeriodType.months.getValue()) {
					Date fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);				
					if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getFromMonth(), paramDto.getFromYear());
					}
				    Date toDate= WLDateTimeUtil.getLastDayOfMonth(paramDto.getFromMonth(), paramDto.getFromYear());
				    paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportLiveStockProductDto importExportDto : subRet) {
				    		importExportDto.setYearReport(paramDto.getFromYear());
				    		importExportDto.setMonthReport(paramDto.getFromMonth());
						}
				    	ret.addAll(subRet);
				    }
				    
				    fromDate= WLDateTimeUtil.numberToDate(1, 1, 1970);		
				    if(paramDto.getStartTimeType()==WLConstant.StartTimeType.aMomentInTime.getValue()) {
						fromDate = WLDateTimeUtil.numberToDate(1, paramDto.getToMonth(), paramDto.getToYear());
					}
				    toDate= WLDateTimeUtil.getLastDayOfMonth(paramDto.getToMonth(), paramDto.getToYear());
				    paramDto.setFromDate(fromDate);
				    paramDto.setToDate(toDate);				    
				    subRet = this.getSumQuantity(paramDto);
				    if(subRet!=null) {
				    	for (ImportExportLiveStockProductDto importExportDto : subRet) {
				    		importExportDto.setYearReport(paramDto.getToYear());
				    		importExportDto.setMonthReport(paramDto.getToMonth());
						}
				    	ret.addAll(subRet);
				    }
				}
			}
			return ret;
		}
		return null;
	}
	
	//Cập nhật lại cơ sở chăn nuôi và sản phẩm chăn nuôi nếu có sự thay đổi khi nhập sản phẩm chăn nuôi
	public void updateDateInfoExport(Long importId,Farm farm, LiveStockProduct liveStockProduct) {
		List<ImportExportLiveStockProduct>  list = importExportLiveStockProductRepository.findByImportIdAndType(importId);
		
		if(list != null && list.size() > 0) {
			for(ImportExportLiveStockProduct export: list) {
				if(export != null) {
					export.setFarm(farm);
					export.setLiveStockProduct(liveStockProduct);
				}
			}
		}
		importExportLiveStockProductRepository.save(list);
	}

}
