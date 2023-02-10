package com.globits.wl.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.hibernate.transform.AliasToBeanResultTransformer;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.core.service.impl.GenericServiceImpl;
import com.globits.core.utils.SecurityUtils;
import com.globits.security.domain.User;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportFormAnimalEgg;
import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.dto.Report18Dto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.ReportFormAnimalEggDto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.ReportFormAnimalEggSearchDto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.ReportForm16Repository;
import com.globits.wl.repository.ReportFormAnimalEggRepository;
import com.globits.wl.repository.ReportPeriodRepository;
import com.globits.wl.service.ReportForm16Service;
import com.globits.wl.service.ReportFormAnimalEggService;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.service.UserAdministrativeUnitService;
import com.globits.wl.utils.WLConstant;
import com.globits.wl.utils.WLDateTimeUtil;

@Service
public class ReportFormAnimalEggServiceImpl extends GenericServiceImpl<ReportFormAnimalEgg, Long> implements ReportFormAnimalEggService {
	@Autowired
	private ReportFormAnimalEggRepository reportFormAnimalEggRepository;
	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	private AnimalRepository animalRepository;

	@Autowired
	private ReportPeriodService reportPeriodService;
	@Autowired
	private ReportPeriodRepository reportPeriodRepository;

	@Autowired
	ReportForm16Service reportForm16Service;
	
	@Autowired
	private ReportForm16Repository reportForm16Repository;
	
	@Autowired
	private UserAdministrativeUnitService userAdministrativeUnitService;
	@Override
	public ReportFormAnimalEggDto save(ReportFormAnimalEggDto dto) {
		if(dto != null) {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			User modifiedUser = null;
			LocalDateTime currentDate = LocalDateTime.now();
			String currentUserName = "Unknown User";
			if (authentication != null) {
				modifiedUser = (User) authentication.getPrincipal();
				currentUserName = modifiedUser.getUsername();
			}
			boolean isNew=false;
			ReportFormAnimalEgg entity = null;
			if(dto.getId() != null) {
				entity = reportFormAnimalEggRepository.findOne(dto.getId());
			}
			if(entity == null) {
				entity = new ReportFormAnimalEgg();
				entity.setCreatedBy(currentUserName);
				entity.setCreateDate(currentDate);
				isNew=true;
			}else {
				entity.setModifiedBy(currentUserName);
				entity.setModifyDate(currentDate);
			}
			Farm farm = null;
			if(dto.getFarm() != null && dto.getFarm().getId() != null) {
				farm = farmRepository.findOne(dto.getFarm().getId());
				entity.setFarm(farm);				
			}
			Animal animal = null;
			if(dto.getAnimal() != null && dto.getAnimal().getId() != null) {
				animal = animalRepository.findOne(dto.getAnimal().getId());
				entity.setAnimal(animal);
			}
			
			//Tạo hoặc update 1 form16 với dữ liệu cập nhật từ con non mới nở ra
			
			//1: Dùng service kiểm tra xem farm đã có report16 nào chưa
			//, nếu có thì kiểm tra xem ngày có trùng ngày hiện tại hay không, trùng thì update lại 
			//số liệu, không trùng thì lấy dữ liệu của report gần đây nhất.
			
			//2: Điều kiện để tạo rp16 là: Trùng Animal + Trùng Farm 
			
			List<ReportForm16Dto> list = reportForm16Service.getListReportForm16ByFarmIdAndAnimalId(dto.getFarm().getId(),dto.getAnimal().getId());
			//Query để lấy ra DateReport gần nhất
//			String sql="SELECT t.id as form16Id,"
//					+ "t.child_under_1year_old as unGenderChildUnder1YearOld,"
//					+ "t.date_report as dateReport,"
//					+ "t.female as totalFemale,"
//					+ "t.female_child_over_1year_old as femaleChildOver1YearOld,"
//					+ "t.female_gilts as femaleGilts,"
//					+ "t.female_parent as femaleParent,"
//					+ "t.male as totalMale,"
//					+ "t.male_child_over_1year_old as maleChildOver1YearOld,"
//					+ "t.male_gilts as maleGilts,"
//					+ "t.male_parent as maleParent,"
//					+ "t.total as total,"
////					+ "t.type as type,"
//					+ "t.un_gender as totalUnGender,"
//					+ "t.un_gender_child_over_1year_old as unGenderChildOver1YearOld,"
//					+ "t.animal_id as animalId,"
//					+ "t.farm_id as farmId,"
//					+ "t.report_period_id as periodId,"
//					+ "t.administrative_unit_id as administrativeUnitId,"
//					+ "t.district_id as disId,"
//					+ "t.province_id as provId,"
//					+ "t.animal_group as animalGroup,"
//					+ "t.cites as cites,"
//					+ "t.vnlist as vnlist,"
//					+ "t.vnlist06 as vnlist06,"
//					+ "t.original_id as original,"
//					+ "t.female_child_under_1year_old as femaleChildUnder1YearOld,"
//					+ "t.male_child_under_1year_old as maleChildUnder1YearOld FROM tbl_report_form16 t INNER JOIN (select max(t1.date_report) as MaxDate , t1.farm_id from tbl_report_form16 t1 group by t1.farm_id) tm on t.farm_id = tm.farm_id  where t.farm_id = "+dto.getFarm().getId()+" and t.animal_id = "+dto.getAnimal().getId()+" and t.date_report = tm.MaxDate";
////					+ "t.male_child_under_1year_old as maleChildUnder1YearOld FROM tbl_report_form16 t INNER JOIN (select max(t1.date_report) as MaxDate , t1.farm_id from tbl_report_form16 t1 group by t1.farm_id) tm on t.farm_id = tm.farm_id  where t.farm_id = "+dto.getFarm().getId()+" and t.animal_id = "+dto.getAnimal().getId()+" and t.date_report = tm.MaxDate and t.date_report<"+dto.getDateReport()+"";
//			Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(Report18Dto.class));
//			List<Report18Dto> listLastDateForm16 = query.getResultList();
			String sql="select top(1) id as form16Id "
					+ "from tbl_report_form16 where farm_id = "+dto.getFarm().getId()+" and animal_id = "+dto.getAnimal().getId()+" group by id, date_report order by date_report desc";
//					+ "t.male_child_under_1year_old as maleChildUnder1YearOld FROM tbl_report_form16 t INNER JOIN (select max(t1.date_report) as MaxDate , t1.farm_id from tbl_report_form16 t1 group by t1.farm_id) tm on t.farm_id = tm.farm_id  where t.farm_id = "+dto.getFarm().getId()+" and t.animal_id = "+dto.getAnimal().getId()+" and t.date_report = tm.MaxDate and t.date_report<"+dto.getDateReport()+"";
			Query query = manager.createNativeQuery(sql).unwrap(org.hibernate.query.Query.class).setResultTransformer(new AliasToBeanResultTransformer(Report18Dto.class));
			
			@SuppressWarnings("unchecked")
			List<Report18Dto> listLastDateForm16 = (List<Report18Dto>)query.getResultList();
			
			ReportForm16 rp16Last = null;
			if(listLastDateForm16!=null) {
				rp16Last = reportForm16Repository.getOne(Long.parseLong(listLastDateForm16.get(0).getForm16Id().toString()));
				
			}
			ReportPeriodDto reportPeriodDto = null;
			ReportPeriod reportPeriod = null;
			List<ReportPeriod> reportPeriods = null;
			ReportForm16Dto rp16Dto = null;
			ReportForm16 eUpdate = null;
			ReportForm16 eNew = null;
			if(list!=null && list.size()>0) {
				for(int i=0; i<list.size(); i++) {
					Long dtoAnimalId = dto.getAnimal().getId();
					Long rp16AnimalId = list.get(i).getAnimal().getId();
					if(dtoAnimalId.equals(rp16AnimalId)==true) {
						SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
						String dateRp16 = sdf.format(list.get(i).getDateReport());
						String dateEgg = sdf.format(dto.getDateReport());
						if(dateRp16.equals(dateEgg)) {
							eUpdate = reportForm16Repository.getOne(list.get(i).getId());
							break;
						}else{
							//Trường hợp không thấy form16 để update
							//Kiểm tra xem có period tại ngày đó hay chưa
							java.util.Date date = dto.getDateReport();
							Calendar cal = Calendar.getInstance();
							cal.setTime(date);
							int year = cal.get(Calendar.YEAR);
							int month = cal.get(Calendar.MONTH)+1;
							int day = cal.get(Calendar.DATE);
							reportPeriods  = reportPeriodRepository.findReportPeriodByYearMonthDate(year,month,day,dto.getFarm().getId(),1);
							
						}
					}
				}
			}
			//update
			if(eUpdate!=null && eUpdate.getId()!=null) {
				if(isNew) {
					//trường hợp có 16 mà chưa có trứng
					int qch=0;
					int qcd=0;
					if(dto.getQuantityChildHatch()!=null && dto.getQuantityChildDie()!=null) {
						 qch=dto.getQuantityChildHatch();
						 qcd=dto.getQuantityChildDie();
					}else if(dto.getQuantityChildHatch()!=null && dto.getQuantityChildDie()==null){
						qch=dto.getQuantityChildHatch();
					}else if(dto.getQuantityChildHatch()==null && dto.getQuantityChildDie()!=null){
						qcd=dto.getQuantityChildDie();
					}
					if(eUpdate.getImportChildUnder1YearOld()!=null) {
						eUpdate.setImportChildUnder1YearOld(eUpdate.getImportChildUnder1YearOld()+dto.getQuantityChildHatch());
					}else {
						eUpdate.setImportChildUnder1YearOld(0+qch);
					}
					if(eUpdate.getExportChildUnder1YearOld()!=null) {
						eUpdate.setExportChildUnder1YearOld(eUpdate.getExportChildUnder1YearOld()+dto.getQuantityChildDie());
					}else {
						eUpdate.setExportChildUnder1YearOld(0+qcd);
					}
					if(eUpdate.getTotalImport()!=null) {
						eUpdate.setTotalImport(eUpdate.getTotalImport()+qch);
					}else {
						eUpdate.setTotalImport(0+qch);
					}
					if(eUpdate.getTotalExport()!=null) {
						eUpdate.setTotalExport(eUpdate.getTotalExport()+qcd);
					}else {
						eUpdate.setTotalExport(0+qcd);
					}
					if(eUpdate.getChildUnder1YearOld()!=null) {
						eUpdate.setChildUnder1YearOld(eUpdate.getChildUnder1YearOld()+qch-qcd);
					}else {
						eUpdate.setChildUnder1YearOld(0+qch-qcd);
					}
					if(eUpdate.getUnGender()!=null) {
						eUpdate.setUnGender(eUpdate.getUnGender()+qch-qcd);
					}else {
						eUpdate.setUnGender(0+qch-qcd);
					}
					if(eUpdate.getTotal()!=null) {
						eUpdate.setTotal(eUpdate.getTotal()+qch-qcd);
					}else {
						eUpdate.setTotal(0+qch-qcd);
					}
				}else {
					//trường hợp có 16 và đã có trứng
					int qch=0;
					int qcd=0;
					if(dto.getQuantityChildHatch()!=null && dto.getQuantityChildDie()!=null) {
						 qch=dto.getQuantityChildHatch();
						 qcd=dto.getQuantityChildDie();
					}else if(dto.getQuantityChildHatch()!=null && dto.getQuantityChildDie()==null){
						qch=dto.getQuantityChildHatch();
					}else if(dto.getQuantityChildHatch()==null && dto.getQuantityChildDie()!=null){
						qcd=dto.getQuantityChildDie();
					}
					if(eUpdate.getImportChildUnder1YearOld()!=null&&entity.getQuantityChildHatch()!=null) {
						eUpdate.setImportChildUnder1YearOld(eUpdate.getImportChildUnder1YearOld()+dto.getQuantityChildHatch()-entity.getQuantityChildHatch());
					}else {
						eUpdate.setImportChildUnder1YearOld(0+qch);
					}
					if(eUpdate.getExportChildUnder1YearOld()!=null&&entity.getQuantityChildDie()!=null) {
						eUpdate.setExportChildUnder1YearOld(eUpdate.getExportChildUnder1YearOld()+dto.getQuantityChildDie()-entity.getQuantityChildDie());
					}else {
						eUpdate.setExportChildUnder1YearOld(0+qcd);
					}
					if(eUpdate.getTotalImport()!=null&&entity.getQuantityChildHatch()!=null) {
						eUpdate.setTotalImport(eUpdate.getTotalImport()+qch-entity.getQuantityChildHatch());
					}else {
						eUpdate.setTotalImport(0+qch);
					}
					if(eUpdate.getTotalExport()!=null&&entity.getQuantityChildDie()!=null) {
						eUpdate.setTotalExport(eUpdate.getTotalExport()+qcd-entity.getQuantityChildDie());
					}else {
						eUpdate.setTotalExport(0+qcd);
					}
					if(eUpdate.getChildUnder1YearOld()!=null&&entity.getQuantityChildHatch()!=null&&entity.getQuantityChildDie()!=null) {
						eUpdate.setChildUnder1YearOld(eUpdate.getChildUnder1YearOld()+qch-qcd-entity.getQuantityChildHatch()+entity.getQuantityChildDie());
					}else {
						eUpdate.setChildUnder1YearOld(0+qch-qcd);
					}
					if(eUpdate.getUnGender()!=null&&entity.getQuantityChildHatch()!=null&&entity.getQuantityChildDie()!=null) {
						eUpdate.setUnGender(eUpdate.getUnGender()+qch-qcd-entity.getQuantityChildHatch()+entity.getQuantityChildDie());
					}else {
						eUpdate.setUnGender(0+qch-qcd);
					}
					if(eUpdate.getTotal()!=null&&entity.getQuantityChildHatch()!=null&&entity.getQuantityChildDie()!=null) {
						eUpdate.setTotal(eUpdate.getTotal()+qch-qcd-entity.getQuantityChildHatch()+entity.getQuantityChildDie());
					}else {
						eUpdate.setTotal(0+qch-qcd);
					}
				}
				
				
			}else{
				//Nếu có rồi thì trỏ vào
				if(reportPeriods!= null && reportPeriods.size()>0) {
					reportPeriodDto = reportPeriodService.getById(reportPeriods.get(0).getId());
					//reportPeriod = reportPeriods.get(0);
				}else {
					//Nếu chưa có period thì tạo 1 period mới
					reportPeriodDto = new ReportPeriodDto();
					if(dto.getDateReport() != null) {
						java.util.Date date = dto.getDateReport();
						Calendar cal = Calendar.getInstance();
						cal.setTime(date);
						int year = cal.get(Calendar.YEAR);
						int month = cal.get(Calendar.MONTH)+1;
						int day = cal.get(Calendar.DATE);
						
						reportPeriodDto.setYear(year);
						reportPeriodDto.setMonth(month);
						reportPeriodDto.setDate(day);
					}
					reportPeriodDto.setType(1);
					if(dto.getFarm() != null && dto.getFarm().getId() != null) {
						reportPeriodDto.setFarm(dto.getFarm());
					}
				}
				//Thêm form16 đối với khai form16 của loài mới trong period
				List<ReportForm16Dto> list16Dto = new ArrayList<ReportForm16Dto>();
				rp16Dto = new ReportForm16Dto();
				eNew = new ReportForm16();
				int qch=0;
				int qcd=0;
				if(dto.getQuantityChildHatch()!=null && dto.getQuantityChildDie()!=null) {
					 qch=dto.getQuantityChildHatch();
					 qcd=dto.getQuantityChildDie();
				}else if(dto.getQuantityChildHatch()!=null && dto.getQuantityChildDie()==null){
					qch=dto.getQuantityChildHatch();
				}else if(dto.getQuantityChildHatch()==null && dto.getQuantityChildDie()!=null){
					qcd=dto.getQuantityChildDie();
				}
				rp16Dto.setType(1);
				rp16Dto.setDateReport(dto.getDateReport());
				rp16Dto.setFarm(dto.getFarm());
				rp16Dto.setAnimal(dto.getAnimal());
				rp16Dto.setImportChildUnder1YearOld(qch);
				rp16Dto.setExportChildUnder1YearOld(qcd);
				
				if(rp16Last!=null) {
					if(rp16Last.getMale()!=null) {
						rp16Dto.setMale(rp16Last.getMale());
					}
					if(rp16Last.getFemale()!=null) {
						rp16Dto.setFemale(rp16Last.getFemale());
					}
					if(rp16Last.getMaleParent()!=null) {
						rp16Dto.setMaleParent(rp16Last.getMaleParent());
					}
					if(rp16Last.getFemaleParent()!=null) {
						rp16Dto.setFemaleParent(rp16Last.getFemaleParent());
					}
					if(rp16Last.getTotalImport()!=null) {
						rp16Dto.setTotalImport(rp16Last.getTotalImport()+qch);
					}else {
						rp16Dto.setTotalImport(0+qch);
					}
					if(rp16Last.getTotalExport()!=null) {
						rp16Dto.setTotalExport(rp16Last.getTotalExport()+qcd);
					}else {
						rp16Dto.setTotalExport(0+qcd);
					}
					if(rp16Last.getChildUnder1YearOld()!=null) {
						rp16Dto.setChildUnder1YearOld(rp16Last.getChildUnder1YearOld()+qch-qcd);				
					}else {
						rp16Dto.setChildUnder1YearOld(0+qch-qcd);	
					}
					if(rp16Last.getUnGender()!=null) {
						rp16Dto.setUnGender(rp16Last.getUnGender()+qch-qcd);
					}else {
						rp16Dto.setUnGender(0+qch-qcd);
					}
					if(rp16Last.getTotal()!=null) {
						rp16Dto.setTotal(rp16Last.getTotal()+qch-qcd);
					}else {
						rp16Dto.setTotal(0+qch-qcd);
					}
					if(rp16Last.getMaleGilts()!=null) {
						rp16Dto.setMaleGilts(rp16Last.getMaleGilts());
					}
					if(rp16Last.getFemaleGilts()!=null) {
						rp16Dto.setFemaleGilts(rp16Last.getFemaleGilts());
					}
					if(rp16Last.getMaleChildOver1YearOld()!=null) {
						rp16Dto.setMaleChildOver1YearOld(rp16Last.getMaleChildOver1YearOld());
					}
					if(rp16Last.getFemaleChildOver1YearOld()!=null) {
						rp16Dto.setFemaleChildOver1YearOld(rp16Last.getFemaleChildOver1YearOld());
					}
					if(rp16Last.getUnGenderChildOver1YearOld()!=null) {
						rp16Dto.setUnGenderChildOver1YearOld(rp16Last.getUnGenderChildOver1YearOld());
					}
					
				}else {
					System.out.println();
					rp16Dto.setMaleParent(dto.getParentMale());
					rp16Dto.setFemaleParent(dto.getParentFemale());
					rp16Dto.setTotalImport(qch);
					rp16Dto.setTotalExport(qcd);
					rp16Dto.setChildUnder1YearOld(qch-qcd);
					rp16Dto.setUnGender(qch-qcd);
					rp16Dto.setTotal(qch-qcd);		
				}
				
				if(reportPeriodDto!=null) {
					if(reportPeriodDto.getReportItems()==null) {
						reportPeriodDto.setReportItems(new HashSet<ReportForm16Dto>());
					}
					reportPeriodDto.getReportItems().add(rp16Dto);
					reportPeriodService.saveOrUpdate(reportPeriodDto);
				}
				
			}
			
			entity.setDateReport(dto.getDateReport());
			entity.setParentMale(dto.getParentMale());
			entity.setParentFemale(dto.getParentFemale());
			entity.setQuantityEgg(dto.getQuantityEgg());
			entity.setQuantityEggWarm(dto.getQuantityEggWarm());
			entity.setQuantityChildHatch(dto.getQuantityChildHatch());
			entity.setQuantityChildDie(dto.getQuantityChildDie());
			entity.setQuantityChildLive(dto.getQuantityChildLive());
			entity.setQuantityChildIncrement(dto.getQuantityChildIncrement());
//			entity.setRemainQuantity(dto.getRemainQuantity());
			Integer remainQuantity=0;
			if(dto.getQuantityChildIncrement()!=null) {
				remainQuantity=dto.getQuantityChildIncrement();
			}
			if(dto.getQuantityChildSeparateCaptivity()!=null) {
				remainQuantity=remainQuantity-dto.getQuantityChildSeparateCaptivity();
			}
			entity.setRemainQuantity(remainQuantity);
			entity.setQuantityChildSeparateCaptivity(dto.getQuantityChildSeparateCaptivity());
			entity.setNote(dto.getNote());
			 
			entity = reportFormAnimalEggRepository.save(entity);
			

			if(farm!=null && animal!=null)
			this.updateQuantityByFarmAnimal(farm.getId(),animal.getId(),isNew);
			return new ReportFormAnimalEggDto(entity);
		}
		return null;
	}

	@Override
	public List<ReportFormAnimalEggDto> getAll(AnimalReportDataSearchDto searchDto) {
		String sql ="select new com.globits.wl.dto.ReportFormAnimalEggDto(rf) FROM ReportFormAnimalEgg rf ";
		String whereClause = " where 1=1 ";
		String orderBy = " order by rf.dateReport desc ";
		if(searchDto.getFarmId() != null) {
			whereClause +=" AND rf.farm.id = :farmId";
		}
		if(searchDto.getAnimalId() != null) {
			whereClause += " AND rf.animal.id = :animalId";
		}
		Query query = manager.createQuery(sql + whereClause + orderBy, ReportFormAnimalEggDto.class);
		if(searchDto.getFarmId() != null) {
			query.setParameter("farmId", searchDto.getFarmId());
		}
		if(searchDto.getAnimalId() != null) {
			query.setParameter("animalId", searchDto.getAnimalId());
		}
		return query.getResultList();
	}

	@Override
	public Page<ReportFormAnimalEggDto> getPage(AnimalReportDataSearchDto searchDto, int pageIndex, int pageSize) {
		if(pageIndex > 0) {
			pageIndex--;
		}else pageIndex = 0;
		Pageable pageable = new PageRequest(pageIndex, pageSize);
		String sql ="select new com.globits.wl.dto.ReportFormAnimalEggDto(rf) FROM ReportFormAnimalEgg rf ";
		String sqlCount = "select count(*) FROM ReportFormAnimalEgg rf ";
		
		String whereClause = " where 1=1 ";
		String orderBy = " order by rf.dateReport desc ";
		if(searchDto.getFarmId() != null) {
			whereClause +=" AND rf.farm.id = :farmId";
		}
		if(searchDto.getAnimalId() != null) {
			whereClause += " AND rf.animal.id = :animalId";
		}
		Query query = manager.createQuery(sql + whereClause + orderBy, ReportFormAnimalEggDto.class);
		Query queryCount = manager.createQuery(sqlCount + whereClause, Long.class);
		if(searchDto.getFarmId() != null) {
			query.setParameter("farmId", searchDto.getFarmId());
			queryCount.setParameter("farmId", searchDto.getFarmId());
		}
		if(searchDto.getAnimalId() != null) {
			query.setParameter("animalId", searchDto.getAnimalId());
			queryCount.setParameter("animalId", searchDto.getAnimalId());
		}
		query.setFirstResult(pageIndex* pageSize);
		query.setMaxResults(pageSize);
		Long total = 0L;
		Object object = queryCount.getSingleResult();
		if(object != null) {
			total = (Long) object;
		}
		Page<ReportFormAnimalEggDto> page = new PageImpl<ReportFormAnimalEggDto>(query.getResultList(), pageable, total);
		return page;
	}

	@Override
	public ReportFormAnimalEggDto getById(Long id) {
		return new ReportFormAnimalEggDto(reportFormAnimalEggRepository.findOne(id));
	}

	@Override
	public Boolean deleteById(Long id) {
		Boolean flag = false;
		try {
			ReportFormAnimalEgg rfae=reportFormAnimalEggRepository.findOne(id);
			if(rfae!=null) {
				updateForm16BeforeDelete16C(rfae);
				reportFormAnimalEggRepository.delete(id);
				flag = true;
				updateQuantityByFarmAnimal(rfae.getFarm().getId(),rfae.getAnimal().getId(),true);
			}
			
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}

	@Override
	public Integer quantityChildIncrement(ReportFormAnimalEggSearchDto dto) {
		Long id=0L;
		Date date=null;
		if(dto.getId()!=null && dto.getId()>0) {
			id=dto.getId();
//			ReportFormAnimalEgg ae=reportFormAnimalEggRepository.findOne(id);
//			if(ae!=null && ae.getDateReport()!=null) {
//				date=ae.getDateReport();
//			}
			
		}
		String sql ="select new com.globits.wl.dto.ReportFormAnimalEggDto(rf) FROM ReportFormAnimalEgg rf ";
		
		String whereClause = " where rf.farm.id = :farmId AND rf.animal.id = :animalId AND rf.dateReport < :dateReport ";
		if (id != null && id > 0) {
			whereClause += " AND rf.id != :id ";
//			if (date != null) {
//				whereClause += " AND rf.dateReport < :dateReport ";
//			}
		}
		
		String orderBy = " order by rf.dateReport desc ";

		Query query = manager.createQuery(sql + whereClause + orderBy, ReportFormAnimalEggDto.class);

		query.setParameter("farmId", dto.getFarmId());
		query.setParameter("animalId", dto.getAnimalId());
		query.setParameter("dateReport", dto.getDateReport());
		if (id != null && id > 0) {
			query.setParameter("id", id);
//			if (date != null) {
//				query.setParameter("dateReport",date);
//			}
		}
		
		List<ReportFormAnimalEggDto> list = query.getResultList();
		
//		List<ReportFormAnimalEggDto> list=reportFormAnimalEggRepository.getChildIncrement(dto.getFarmId(),dto.getAnimalId(), dto.getDateReport());
		if(list!=null && list.size()>0) {
			Integer quantity=0;
			for (ReportFormAnimalEggDto reportFormAnimalEggDto : list) {
				quantity=quantity+reportFormAnimalEggDto.getRemainQuantity();		
			}
			return quantity;
		}
		return null;
	}
	/**
	 * Cập nhật lại số con non cộng dồn theo thời gian + số con non còn lại
	 * @param farmId
	 */
	public void updateQuantityByFarmAnimal(Long farmId, Long animalId,boolean isNew) {
		if(isNew) {//trường hợp thêm mới
			List<ReportFormAnimalEgg> list=reportFormAnimalEggRepository.getListByFarmAnimal(farmId,animalId);
			if(list!=null && list.size()>0) {
				if(list.size()==1) {
					return ;
				}else {
					ReportFormAnimalEgg firstAE=list.get(0);
					int quantity=firstAE.getRemainQuantity();
					for(int i=1;i<list.size();i++) {
						list.get(i).setQuantityChildIncrement(quantity);
						Integer remainQuantity=0;
						if(list.get(i).getQuantityChildIncrement()!=null) {
							remainQuantity=list.get(i).getQuantityChildIncrement();
						}
						if(list.get(i).getQuantityChildSeparateCaptivity()!=null) {
							remainQuantity=remainQuantity-list.get(i).getQuantityChildSeparateCaptivity();
						}
						list.get(i).setRemainQuantity(remainQuantity);
						reportFormAnimalEggRepository.save(list.get(i));
						quantity=quantity+list.get(i).getRemainQuantity();
					}
					
				}
			}
			
		}else {//trường hợp sửa
			List<Long> listAnimalId=reportFormAnimalEggRepository.getListAnimalIdByFarm(farmId);
			if(listAnimalId!=null && listAnimalId.size()>0) {
				for (Long long1 : listAnimalId) {
					List<ReportFormAnimalEgg> list=reportFormAnimalEggRepository.getListByFarmAnimal(farmId,long1);
					if(list!=null && list.size()>0) {
						if(list.size()==1) {
							continue ;
						}else {
							ReportFormAnimalEgg firstAE=list.get(0);
							int quantity=firstAE.getRemainQuantity();
							for(int i=1;i<list.size();i++) {
								list.get(i).setQuantityChildIncrement(quantity);
								Integer remainQuantity=0;
								if(list.get(i).getQuantityChildIncrement()!=null) {
									remainQuantity=list.get(i).getQuantityChildIncrement();
								}
								if(list.get(i).getQuantityChildSeparateCaptivity()!=null) {
									remainQuantity=remainQuantity-list.get(i).getQuantityChildSeparateCaptivity();
								}
								list.get(i).setRemainQuantity(remainQuantity);
								reportFormAnimalEggRepository.save(list.get(i));
								quantity=quantity+list.get(i).getRemainQuantity();
							}
							
						}
					}
				}
			}
		}
		
	}
	
	public void updateForm16BeforeDelete16C(ReportFormAnimalEgg rfae){
		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16ByFarmId(rfae.getFarm().getId());

		ReportForm16Dto rp16Dto = null;
		ReportForm16 eUpdate = null;
		ReportForm16 eNew = null;
		if(list!=null && list.size()>0) {
			for(int i=0; i<list.size(); i++) {
				Long dtoAnimalId = rfae.getAnimal().getId();
				Long rp16AnimalId = list.get(i).getAnimal().getId();
				if(dtoAnimalId.equals(rp16AnimalId)==true) {
					SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					String dateRp16 = sdf.format(list.get(i).getDateReport());
					String dateEgg = sdf.format(rfae.getDateReport());
					if(dateRp16.equals(dateEgg)) {
						eUpdate = reportForm16Repository.getOne(list.get(i).getId());
						break;	
					}
				}
			}
		}
		//update
		if(eUpdate!=null && eUpdate.getId()!=null) {
			int qch=0;
			int qcd=0;
			if(rfae.getQuantityChildHatch()!=null && rfae.getQuantityChildDie()!=null) {
				 qch=rfae.getQuantityChildHatch();
				 qcd=rfae.getQuantityChildDie();
			}else if(rfae.getQuantityChildHatch()!=null && rfae.getQuantityChildDie()==null){
				qch=rfae.getQuantityChildHatch();
			}else if(rfae.getQuantityChildHatch()==null && rfae.getQuantityChildDie()!=null){
				qcd=rfae.getQuantityChildDie();
			}
			eUpdate.setTotal(eUpdate.getTotal()+qcd-qch);
			eUpdate.setUnGender(eUpdate.getUnGender()-qch+qcd);
			eUpdate.setChildUnder1YearOld(eUpdate.getChildUnder1YearOld()+qcd-qch);
			eUpdate.setTotalExport(eUpdate.getTotalExport()-qcd);
			eUpdate.setTotalImport(eUpdate.getTotalImport()-qch);
			if(eUpdate.getExportChildUnder1YearOld()-qcd<=0) {
				eUpdate.setExportChildUnder1YearOld(null);
			}else {
				eUpdate.setExportChildUnder1YearOld(eUpdate.getExportChildUnder1YearOld()-qcd);
			}
			if(eUpdate.getImportChildUnder1YearOld()-qch<=0) {
				eUpdate.setImportChildUnder1YearOld(null);
			}else {
				eUpdate.setImportChildUnder1YearOld(eUpdate.getImportChildUnder1YearOld()-qch);
			}
			
		}
	}

	@Override
	public List<ReportFormAnimalEggDto> saveList(List<ReportFormAnimalEggDto> list) {
		if(list != null) {
			for(ReportFormAnimalEggDto dto: list) {
				dto = this.save(dto);
				//tạo report period tương ứng
//				this.saveReportFormAnimalEggToReportPeriod(dto);
			}
			
		}
		return list;
	}
	
	public ReportPeriodDto saveReportFormAnimalEggToReportPeriod(ReportFormAnimalEggDto dto) {
		ReportPeriodDto reportPeriodDto = null;
		ReportForm16Dto reportForm16Dto = null;
		List<ReportPeriod> reportPeriods = null;
		if(dto != null && dto.getDateReport() != null && dto.getFarm() != null && dto.getFarm().getId() != null) {
			Integer year = dto.getDateReport().getYear() + 1900;
			Integer month =  dto.getDateReport().getMonth() + 1;
			Integer date =  dto.getDateReport().getDate();
			Long farmId = dto.getFarm().getId();
			//tìm report period xem đã tạo chưa
			reportPeriods  = reportPeriodRepository.findReportPeriodByYearMonthDate(year,month,date,farmId,1);
		}
		if(reportPeriods != null && reportPeriods.size()>0) {
			reportPeriodDto = new ReportPeriodDto(reportPeriods.get(0));
		}else {
			reportPeriodDto = new ReportPeriodDto();
			if(dto.getDateReport() != null) {
				reportPeriodDto.setYear(dto.getDateReport().getYear() + 1900);
				reportPeriodDto.setMonth(dto.getDateReport().getMonth() + 1);
				reportPeriodDto.setDate(dto.getDateReport().getDate());
			}
			reportPeriodDto.setType(1);
			if(dto.getFarm() != null && dto.getFarm().getId() != null) {
				reportPeriodDto.setFarm(dto.getFarm());
			}
		}
		
		reportForm16Dto = new ReportForm16Dto();
		reportForm16Dto.setType(1);
		reportForm16Dto.setDateReport(dto.getDateReport());
		reportForm16Dto.setFarm(dto.getFarm());
		reportForm16Dto.setAnimal(dto.getAnimal());
		reportForm16Dto.setChildUnder1YearOld(dto.getRemainQuantity());
		reportForm16Dto.setTotal(dto.getRemainQuantity());
		if(reportPeriodDto.getReportItems()==null) {
			reportPeriodDto.setReportItems(new HashSet<ReportForm16Dto>());
		}
		reportPeriodDto.getReportItems().add(reportForm16Dto);
		return this.reportPeriodService.saveOrUpdate(reportPeriodDto);
	}

	@Override
	public Page<ReportFormAnimalEggDto> searchByPage(ReportFormAnimalEggSearchDto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return null;
			}
		}
		if(searchDto != null) {
			int pageIndex = searchDto.getPageIndex();
			int pageSize = searchDto.getPageSize();

			if (pageIndex > 0)
				pageIndex = pageIndex - 1;
			else
				pageIndex = 0;
			Pageable pageable = new PageRequest(pageIndex, pageSize);

			String sql = "select new com.globits.wl.dto.ReportFormAnimalEggDto(rp) from ReportFormAnimalEgg rp where (1=1)";
			String sqlCount = "select count(rp.id) from ReportFormAnimalEgg rp  where (1=1)";
			String whereClause = "";
			
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				whereClause += " and (rp.farm.code like :text or rp.farm.name like :text )";
			}
			if (searchDto.getYear() != null) {
				whereClause += " and (rp.reportFormAnimalEggPeriod.year =:year )";
			}
			if (searchDto.getMonth() != null) {
				whereClause += " and (rp.reportFormAnimalEggPeriod.month =:month )";
			}
			if (searchDto.getDay() != null) {
				whereClause += " and (rp.reportFormAnimalEggPeriod.date =:day )";
			}
			// lấy theo lớp
			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				whereClause += " and (rp.animal.animalClass like :animalClass )";
			}
			// lấy theo bộ
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				whereClause += " and (rp.animal.ordo like :animalOrdo )";
			}
			// lấy theo họ
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				whereClause += " and (rp.animal.family like :animalFamily )";
			}
			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}
			if (searchDto.getWardId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.parent.id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.parent.parent.id =:provinceId )";
			}
			if(!isAdmin) {
				whereClause += " and (rp.farm.administrativeUnit.id in (:listWardId)) ";
			}
			if (searchDto.getType() != null) {
				whereClause += " and (rp.type =:type )";
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				whereClause += " and (rp.reportFormAnimalEggPeriod.id =:reportPeriodId )";
			}
			if (searchDto.getFarmId() != null) {
				whereClause += " and (rp.farm.id =:farmId )";
			}

			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}

			if (searchDto.getDateReport() != null) {
				whereClause += " and (rp.dateReport =:dateReport )";
			}
			if(searchDto.getFromDate()!=null) {
				whereClause += " and (rp.dateReport >=:fromDate )";
			}
			if(searchDto.getToDate()!=null) {
				whereClause += " and (rp.dateReport <:toDate )";
			}

			sql += whereClause;
			sql += " order by rp.farm.code asc, rp.animal.code asc, rp.dateReport asc ";
			sqlCount += whereClause;

			Query q = manager.createQuery(sql, ReportFormAnimalEggDto.class);
			Query qCount = manager.createQuery(sqlCount);

			if (searchDto.getYear() != null) {
				q.setParameter("year", searchDto.getYear());
				qCount.setParameter("year", searchDto.getYear());
			}
			if (searchDto.getMonth() != null) {
				q.setParameter("month", searchDto.getMonth());
				qCount.setParameter("month", searchDto.getMonth());
			}
			if (searchDto.getDay() != null) {
				q.setParameter("day", searchDto.getDay());
				qCount.setParameter("day", searchDto.getDay());
			}
			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}
			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
				qCount.setParameter("wardId", searchDto.getWardId());
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
				qCount.setParameter("districtId", searchDto.getDistrictId());
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
				qCount.setParameter("provinceId", searchDto.getProvinceId());
			}
			if(!isAdmin) {
				q.setParameter("listWardId", listWardId);
				qCount.setParameter("listWardId", listWardId);
			}
			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				q.setParameter("animalClass", "%" + searchDto.getAnimalClass() + "%");
				qCount.setParameter("animalClass", "%" + searchDto.getAnimalClass() + "%");
			}
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				q.setParameter("animalOrdo", "%" + searchDto.getAnimalOrdo() + "%");
				qCount.setParameter("animalOrdo", "%" + searchDto.getAnimalOrdo() + "%");
			}
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				q.setParameter("animalFamily", "%" + searchDto.getAnimalFamily() + "%");
				qCount.setParameter("animalFamily", "%" + searchDto.getAnimalFamily() + "%");
			}
			
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				q.setParameter("text", "%" + searchDto.getTextSearch() + "%");
				qCount.setParameter("text", "%" + searchDto.getTextSearch() + "%");
			}
			if (searchDto.getType() != null) {
				q.setParameter("type", searchDto.getType());
				qCount.setParameter("type", searchDto.getType());
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				q.setParameter("reportPeriodId", searchDto.getReportPeriodId());
				qCount.setParameter("reportPeriodId", searchDto.getReportPeriodId());
			}
			if (searchDto.getFarmId() != null) {
				q.setParameter("farmId", searchDto.getFarmId());
				qCount.setParameter("farmId", searchDto.getFarmId());
			}

			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				qCount.setParameter("animalId", searchDto.getAnimalId());
			}

			if (searchDto.getDateReport() != null) {
				q.setParameter("dateReport", searchDto.getDateReport());
				qCount.setParameter("dateReport", searchDto.getDateReport());
			}
			if(searchDto.getFromDate()!=null) {
				q.setParameter("fromDate", searchDto.getFromDate());
				qCount.setParameter("fromDate", searchDto.getFromDate());
			}
			if(searchDto.getToDate()!=null) {
				searchDto.setToDate(WLDateTimeUtil.getEndOfDay(searchDto.getToDate()));
				q.setParameter("toDate", searchDto.getToDate());
				qCount.setParameter("toDate", searchDto.getToDate());
			}

			q.setFirstResult((pageIndex) * pageSize);
			q.setMaxResults(pageSize);

			Long numberResult = (Long) qCount.getSingleResult();
			Page<ReportFormAnimalEggDto> page = new PageImpl<>(q.getResultList(), pageable, numberResult);
			return page;
		}
		return null;
	}

	@Override
	public List<ReportFormAnimalEggDto> getList(ReportFormAnimalEggSearchDto searchDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User currentUser = null;
		if (authentication != null) {
			currentUser = (User) authentication.getPrincipal();
		}
		boolean isAdmin = SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_ADMIN) || SecurityUtils.isUserInRole(currentUser, WLConstant.ROLE_DLP);
		List<Long> listWardId = null;
		if(!isAdmin) {
			listWardId = userAdministrativeUnitService.getListWardIdByLoginUser();
			if(listWardId==null || listWardId.size()==0) {
				return null;
			}
		}
		if(searchDto != null) {
			
			String sql = "select new com.globits.wl.dto.ReportFormAnimalEggDto(rp) from ReportFormAnimalEgg rp where (1=1)";
			
			String whereClause = "";
			
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				whereClause += " and (rp.farm.code like :text or rp.farm.name like :text )";
			}
			if (searchDto.getYear() != null) {
				whereClause += " and (rp.reportFormAnimalEggPeriod.year =:year )";
			}
			if (searchDto.getMonth() != null) {
				whereClause += " and (rp.reportFormAnimalEggPeriod.month =:month )";
			}
			if (searchDto.getDay() != null) {
				whereClause += " and (rp.reportFormAnimalEggPeriod.date =:day )";
			}
			// lấy theo lớp
			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				whereClause += " and (rp.animal.animalClass like :animalClass )";
			}
			// lấy theo bộ
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				whereClause += " and (rp.animal.ordo like :animalOrdo )";
			}
			// lấy theo họ
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				whereClause += " and (rp.animal.family like :animalFamily )";
			}
			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}
			if (searchDto.getWardId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.id =:wardId )";
			} else if (searchDto.getDistrictId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.parent.id =:districtId )";
			} else if (searchDto.getProvinceId() != null) {
				whereClause += " and (rp.farm.administrativeUnit.parent.parent.id =:provinceId )";
			}
			if(!isAdmin) {
				whereClause += " and (rp.farm.administrativeUnit.id in (:listWardId)) ";
			}
			if (searchDto.getType() != null) {
				whereClause += " and (rp.type =:type )";
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				whereClause += " and (rp.reportFormAnimalEggPeriod.id =:reportPeriodId )";
			}
			if (searchDto.getFarmId() != null) {
				whereClause += " and (rp.farm.id =:farmId )";
			}

			if (searchDto.getAnimalId() != null) {
				whereClause += " and (rp.animal.id =:animalId )";
			}

			if (searchDto.getDateReport() != null) {
				whereClause += " and (rp.dateReport =:dateReport )";
			}
			if(searchDto.getFromDate()!=null) {
				whereClause += " and (rp.dateReport >=:fromDate )";
			}
			if(searchDto.getToDate()!=null) {
				whereClause += " and (rp.dateReport <:toDate )";
			}

			sql += whereClause;
			sql += " order by rp.farm.code asc, rp.animal.code asc, rp.dateReport asc ";
			

			Query q = manager.createQuery(sql, ReportFormAnimalEggDto.class);
		
			if (searchDto.getYear() != null) {
				q.setParameter("year", searchDto.getYear());
				
			}
			if (searchDto.getMonth() != null) {
				q.setParameter("month", searchDto.getMonth());
				
			}
			if (searchDto.getDay() != null) {
				q.setParameter("day", searchDto.getDay());
			
			}
			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
				
			}
			if (searchDto.getWardId() != null) {
				q.setParameter("wardId", searchDto.getWardId());
			
			} else if (searchDto.getDistrictId() != null) {
				q.setParameter("districtId", searchDto.getDistrictId());
			
			} else if (searchDto.getProvinceId() != null) {
				q.setParameter("provinceId", searchDto.getProvinceId());
				
			}
			if(!isAdmin) {
				q.setParameter("listWardId", listWardId);
			
			}
			if (searchDto.getAnimalClass() != null && StringUtils.hasText(searchDto.getAnimalClass())) {
				q.setParameter("animalClass", "%" + searchDto.getAnimalClass() + "%");
				
			}
			if (searchDto.getAnimalOrdo() != null && StringUtils.hasText(searchDto.getAnimalOrdo())) {
				q.setParameter("animalOrdo", "%" + searchDto.getAnimalOrdo() + "%");
				
			}
			if (searchDto.getAnimalFamily() != null && StringUtils.hasText(searchDto.getAnimalFamily())) {
				q.setParameter("animalFamily", "%" + searchDto.getAnimalFamily() + "%");
				
			}
			
			if (searchDto.getTextSearch() != null && StringUtils.hasText(searchDto.getTextSearch())) {
				q.setParameter("text", "%" + searchDto.getTextSearch() + "%");
				
			}
			if (searchDto.getType() != null) {
				q.setParameter("type", searchDto.getType());
				
			}
			if (searchDto.getReportPeriodId() != null && searchDto.getReportPeriodId() > 0L) {
				q.setParameter("reportPeriodId", searchDto.getReportPeriodId());
			
			}
			if (searchDto.getFarmId() != null) {
				q.setParameter("farmId", searchDto.getFarmId());
				
			}

			if (searchDto.getAnimalId() != null) {
				q.setParameter("animalId", searchDto.getAnimalId());
			
			}

			if (searchDto.getDateReport() != null) {
				q.setParameter("dateReport", searchDto.getDateReport());
				
			}
			if(searchDto.getFromDate()!=null) {
				q.setParameter("fromDate", searchDto.getFromDate());
				
			}
			if(searchDto.getToDate()!=null) {
				searchDto.setToDate(WLDateTimeUtil.getEndOfDay(searchDto.getToDate()));
				q.setParameter("toDate", searchDto.getToDate());
				
			}
		
			List<ReportFormAnimalEggDto> page = q.getResultList();
			return page;
		}
		return null;
	}
	
	
}
