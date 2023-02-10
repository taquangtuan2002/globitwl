import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.globits.core.repository.PersonRepository;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.RoleService;
import com.globits.security.service.UserService;
import com.globits.wl.config.DatabaseConfig;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.AnimalType;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ProductTarget;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalTypeDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.FarmAnimal2017Dto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.AnimalTypeRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ProductTargetRepository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.AnimalService;
import com.globits.wl.service.ImportExportAnimalService;
import com.globits.wl.utils.ImportExportExcelUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseConfig.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class ImportExportAnimalTest {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	UserRepository repository;
	
	@Autowired
	PersonRepository personRepository;
	@Autowired
	ImportExportAnimalService importExportAnimalService;	
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	AnimalService animalService;
	@Autowired
	FarmRepository farmRepository;
	@Autowired
	AnimalRepository animalRepository;
	@Autowired
	AnimalTypeRepository animalTypeRepository;
	@Autowired
	OriginalRepository originalRepository;
	@Autowired
	ProductTargetRepository productTargetRepository;
	@Autowired
	AnimalReportDataService animalReportDataService;
//	@Test
//	public void getInventory() throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");
//		String dateInString = "31-08-1970 10:20:56";
//		Date fromDate = sdf.parse(dateInString);
//
//		Date toDate = new Date();
//		List<String>  groupByItems = new ArrayList<String>();
////		groupByItems.add(FMSConstant.GroupByItem.province.getValue());
////		groupByItems.add(FMSConstant.GroupByItem.district.getValue());
////		groupByItems.add(FMSConstant.GroupByItem.region.getValue());
//		groupByItems.add(FMSConstant.GroupByItem.farm.getValue());
//		AnimalInventoryParamDto paramDto = new AnimalInventoryParamDto();
//		paramDto.setFromDate(fromDate);
//		paramDto.setToDate(toDate);
//		paramDto.setGroupByItems(groupByItems);
//		List<InventoryReportDto> ret = importExportAnimalService.getInventory(paramDto);
//		if(ret!=null && ret.size()>0) {
//			System.out.print(ret.size());
//			for (ImportExportAnimalDto importExportAnimalDto : ret) {
//				System.out.println(importExportAnimalDto.getQuantity()+"|"+importExportAnimalDto.getAmount());
//			}
//		}		
//	}
//	@Test
	public void testImport() throws IOException {
//		Resource resource = (Resource) resourceLoader.getResource("C:\\Users\\Admin\\Documents\\Zalo Received Files\\FAO_old2\\Animal code list-Thu.xls");
//		 ByteArrayInputStream byteArrayInputStream =  new ByteArrayInputStream(IOUtils.toByteArray(fileInputStream));
		File file = new File("C:\\Users\\Admin\\Documents\\Zalo Received Files\\FAO_old2\\Animal_Code.xls");
		FileInputStream fileInputStream  = new FileInputStream(file);
		//ByteArrayInputStream byteArrayInputStream =  new ByteArrayInputStream(IOUtils.toByteArray(fileInputStream));
		List<AnimalDto> list = ImportExportExcelUtil.getAnimalDtosFromInputStream(fileInputStream);
//		animalService.saveListAnimal(list);
		System.out.println(list.size());
	}
//	@Test
	public void testImportFarmAnimal2017() throws IOException, InvalidFormatException {
		// Import file animal
		File file = new File("C:\\Users\\Admin\\Documents\\Zalo Received Files\\FAO_old2\\FAO_02062020_2.xlsx");
		OPCPackage pkg = OPCPackage.open(file);
//		FileInputStream fileInputStream  = new FileInputStream(pkg);
//		List<FarmAnimal2017Dto> listInput = ImportExportExcelUtil.getFarmImportAnimalFromInputStream(pkg);
//		System.out.println(listInput.size());
		
//		importExportAnimalService.saveFarmAnimalList(listInput);
	}
	
//	@Test
	public void testImportBearInfo() throws IOException, InvalidFormatException {
		//Import bear
		File file = new File("C:\\Users\\Admin\\Documents\\Zalo Received Files\\FAO_old2\\FAO_02062020_4_bear.xlsx");
		OPCPackage pkg = OPCPackage.open(file);
//		List<FarmAnimal2017Dto> listInput = ImportExportExcelUtil.getBearInfoInputStream(pkg);
//		System.out.println(listInput.size());
		
//		importExportAnimalService.saveImportAnimalBear(listInput);
	}
	@Test
	public void testReportData() {
//		AnimalReportDataSearchDto searchDto = new AnimalReportDataSearchDto();
//		animalReportDataService.reportAnimalAccordingBySearch(searchDto);
		AnimalReportDataSearchDto searchDto = new AnimalReportDataSearchDto();
		searchDto.setYear(2020);
		searchDto.setMonth(7);
		animalReportDataService.convertFromImportExportAnimal2ReportAnimalData(searchDto);
	}
	
}
