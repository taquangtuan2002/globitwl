import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.xmlbeans.ResourceLoader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.globits.wl.config.DatabaseConfig;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.OriginalService;
import com.globits.wl.service.ProductTargetService;
import com.globits.wl.utils.ImportExportExcelUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseConfig.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class ExportAnimalReportDataTest {
	@Autowired
	private AnimalReportDataService animalReportDataService;
	@Autowired
	private OriginalService originalService;
	@Autowired 
	private ProductTargetService productTargetService;
	@Test
	public void test() throws IOException {
		File fileTemplate = new File("E:\\fms_old2\\wildLife\\trunk\\wl-api\\src\\main\\resources\\DVHD_ExportTemplate_2017.xls");
		FileInputStream fileInputStream = new FileInputStream(fileTemplate);
		File file = new File("E:\\renpy\\testExport\\test.xls");
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		
		List<ProductTargetDto> listProductTargets = productTargetService.getAllProductTarget();
		List<OriginalDto> listOrignals = originalService.getAll();
		
		Map<String, ProductTargetDto> productTargetSet = new HashMap<String, ProductTargetDto>();
		Map<String, OriginalDto> originalSet = new HashMap<String, OriginalDto>();
		for(ProductTargetDto productTargetDto:listProductTargets) {
			if(productTargetDto != null) {
				productTargetSet.put(productTargetDto.getCode(), productTargetDto);
			}
		}
		for(OriginalDto originalDto: listOrignals) {
			if(originalDto != null) {
				originalSet.put(originalDto.getCode(), originalDto);
			}
		}
		List<AnimalReportData> list = animalReportDataService.getList(null,null,null,null,null,null,null,null,null);
		ImportExportExcelUtil.exportAnimalReportData(list,originalSet, productTargetSet, fileInputStream, fileOutputStream);
	}
}
