package com.globits.wl.rest;

import java.awt.Shape;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.wp.usermodel.Paragraph;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFFooter;
import org.apache.poi.xwpf.usermodel.XWPFHeader;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.joda.time.LocalDateTime;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTRow;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblBorders;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import com.globits.core.domain.AdministrativeUnit;
import com.globits.core.domain.FileDescription;
import com.globits.core.dto.FileDescriptionDto;
import com.globits.core.repository.AdministrativeUnitRepository;
import com.globits.core.service.FileDescriptionService;
import com.globits.core.utils.FileUtils;
import com.globits.security.domain.User;
import com.globits.wl.domain.Animal;
import com.globits.wl.domain.AnimalReportData;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.FmsAdministrativeUnit;
import com.globits.wl.domain.Original;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.domain.ReportPeriod;
import com.globits.wl.domain.UserAttachments;
import com.globits.wl.dto.AnimalDto;
import com.globits.wl.dto.AnimalReportDataFormDto;
import com.globits.wl.dto.BiologicalClassDto;
import com.globits.wl.dto.AnimalCertificateDetailDto;
import com.globits.wl.dto.AnimalCertificateDto;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.FarmHusbandryMethodDto;
import com.globits.wl.dto.FmsAdministrativeUnitDto;
import com.globits.wl.dto.ForestProductsListDetailDto;
import com.globits.wl.dto.ForestProductsListDto;
import com.globits.wl.dto.ImportExportAnimalDto;
import com.globits.wl.dto.OriginalDto;
import com.globits.wl.dto.ProductTargetDto;
import com.globits.wl.dto.Report18Dto;
import com.globits.wl.dto.ReportForm16Dto;
import com.globits.wl.dto.ReportFormAnimalEggDto;
import com.globits.wl.dto.ReportFormAnimalGiveBirthDto;
import com.globits.wl.dto.ReportPeriodDto;
import com.globits.wl.dto.UserAttachmentsDto;
import com.globits.wl.dto.functiondto.AnimalReportDataFromCityDto;
import com.globits.wl.dto.functiondto.AnimalReportDataFromDistrictDto;
import com.globits.wl.dto.functiondto.AnimalReportDataFromWardDto;
import com.globits.wl.dto.functiondto.AnimalReportDataSearchDto;
import com.globits.wl.dto.functiondto.AnimalSearchDto;
import com.globits.wl.dto.functiondto.DashboardSearchDto;
import com.globits.wl.dto.functiondto.DataDvhdDto;
import com.globits.wl.dto.functiondto.FarmAdministrativeUnitDto;
import com.globits.wl.dto.functiondto.FarmAnimal2017Dto;
import com.globits.wl.dto.functiondto.FarmAnimalTotalDto;
import com.globits.wl.dto.functiondto.FarmSearchDto;
import com.globits.wl.dto.functiondto.ImportExportAnimalSearchDto;
import com.globits.wl.dto.functiondto.ImportResultDto;
import com.globits.wl.dto.functiondto.ImpotList16Dto;
import com.globits.wl.dto.functiondto.Report18CityDto;
import com.globits.wl.dto.functiondto.Report18DistrictDto;
import com.globits.wl.dto.functiondto.Report18WardDto;
import com.globits.wl.dto.functiondto.ReportFormAnimalEggSearchDto;
import com.globits.wl.dto.functiondto.ReportParamDto;
import com.globits.wl.dto.functiondto.SearchReportForm16Dto;
import com.globits.wl.dto.functiondto.SearchReportPeriodDto;
import com.globits.wl.dto.functiondto.UserUserAdministrativeUnitDto;
import com.globits.wl.dto.report.TotalReportDto;
import com.globits.wl.repository.AnimalRepository;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.FmsAdministrativeUnitRepository;
import com.globits.wl.repository.OriginalRepository;
import com.globits.wl.repository.ReportPeriodRepository;
import com.globits.wl.repository.UserAttachmentsRepository;
import com.globits.wl.service.AnimalReportDataService;
import com.globits.wl.service.AnimalService;
import com.globits.wl.service.BiologicalClassService;
import com.globits.wl.service.AnimalCertificateService;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.FmsAdministrativeUnitService;
import com.globits.wl.service.FmsUserService;
import com.globits.wl.service.ForestProductsListService;
import com.globits.wl.service.ImportExportAnimalService;
import com.globits.wl.service.OriginalService;
import com.globits.wl.service.ProductTargetService;
import com.globits.wl.service.ReportForm16Service;
import com.globits.wl.service.ReportFormAnimalEggService;
import com.globits.wl.service.ReportFormAnimalGiveBirthService;
import com.globits.wl.service.ReportPeriodService;
import com.globits.wl.service.ReportService;
import com.globits.wl.service.UserAttachmentsService;
import com.globits.wl.utils.FarmMapServiceUtil;
import com.globits.wl.utils.HelperUtil;
import com.globits.wl.utils.ImportExportExcelUtil;
import com.globits.wl.utils.WLConstant;
import com.microsoft.schemas.vml.CTGroup;
import com.microsoft.schemas.vml.CTShape;

@Controller
@RequestMapping("/api/file")
@Transactional
public class RestFileUploadController {
	@Autowired
	UserAttachmentsRepository userAttachmentsRepository;

	@Autowired
	FmsAdministrativeUnitService fmsAdministrativeUnitService;

	@Autowired
	private Environment env;

	@Autowired
	AnimalService animalService;

	@Autowired
	FarmService farmService;

	@Autowired
	private FileDescriptionService fileService;

	@Autowired
	ImportExportAnimalService importExportAnimalService;

	@Autowired
	private FmsAdministrativeUnitRepository fmsAdministrativeUnitRepository;

	@Autowired
	private OriginalRepository originalRepository;

	@Autowired
	FmsUserService fmsUserService;

	@Autowired
	ResourceLoader resourceLoader;
	@Autowired
	private ForestProductsListService forestProductsListService;

	@Autowired
	private UserAttachmentsService userAttachmentsService;

	@Autowired
	private FarmRepository farmRepository;
	@Autowired
	ReportPeriodRepository reportPeriodRepository;
	@Autowired
	AnimalRepository animalRepository;
	@Autowired
	private AnimalReportDataService animalReportDataService;
	@Autowired
	private OriginalService originalService;
	@Autowired
	private ProductTargetService productTargetService;
	@Autowired
	private ReportPeriodService reportPeriodService;
	@Autowired
	private ReportForm16Service reportForm16Service;
	@Autowired
	private ReportService reportService;
	@Autowired
	private ReportFormAnimalEggService reportFormAnimalEggService;
	@Autowired
	private ReportFormAnimalGiveBirthService reportFormAnimalGiveBirthService;
	@Autowired
	private BiologicalClassService biologicalClassService;
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private AnimalCertificateService animalCertificateService;

	/**
	 * POST /uploadFile -> receive and locally save a file.
	 * 
	 * @param uploadfile The uploaded file as Multipart file parameter in the HTTP
	 *                   request. The RequestParam name must be the same of the
	 *                   attribute "name" in the input tag with type file.
	 * 
	 * @return An http OK status in case of success, an http 4xx status in case of
	 *         errors.
	 */
	@RequestMapping(value = "/fileupload", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> uploadFile(@RequestParam("uploadfile") MultipartFile uploadfile) {

		try {
			// Get the filename and build the local file path
			String filename = uploadfile.getOriginalFilename();
			String directory = "C:\\temp";
			String filepath = Paths.get(directory, filename).toString();

			// Save the file locally
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filepath)));
			stream.write(uploadfile.getBytes());
			stream.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	} // method uploadFile

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importAdministrativeUnit", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importAdministrativeUnitFile(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException {
		// try {
		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<FmsAdministrativeUnitDto> listFmsAdministrativeUnit = ImportExportExcelUtil
				.importAdministrativeUnitFromInputStream(bis);
		fmsAdministrativeUnitService.saveOrUpdateList(listFmsAdministrativeUnit);
		// } catch (Exception e) {
		// System.out.println(e.getMessage());
		// return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		// }
		return new ResponseEntity<>(HttpStatus.OK);
	} // method upload AdministrativeUnit

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importBiologicalClassFile", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importBiologicalClassFile(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException {
		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<BiologicalClassDto> listBiologicalClass = ImportExportExcelUtil.importBiologicalClassFromInputStream(bis);

		biologicalClassService.saveOrUpdateListImport(listBiologicalClass);
		return new ResponseEntity<>(HttpStatus.OK);
	} // method upload AdministrativeUnit

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importAnimal", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importAnimal(@RequestParam("uploadfile") MultipartFile uploadfile) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
			List<AnimalDto> list = ImportExportExcelUtil.importAnimal(bis);
			animalService.saveOrUpdateList(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	} // method upload Animal

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importAnimalNew", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importAnimalNew(@RequestParam("uploadfile") MultipartFile uploadfile) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
			List<AnimalDto> list = ImportExportExcelUtil.importAnimalNew(bis);
			animalService.saveOrUpdateList(list);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importFarm", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importFarm(@RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<FarmDto> list = ImportExportExcelUtil.importFarmFromInputStream(bis);
		farmService.saveListImportFarm(list);

		return new ResponseEntity<>(HttpStatus.OK);
	} // method upload Farm

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importWLFarm", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importWLFarm(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException, ParseException {

		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<FarmDto> list = ImportExportExcelUtil.importWLFarmFromInputStream(bis);
		farmService.saveListImportFarm(list);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importWL2017Farm", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importWL2017Farm(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException, ParseException {

		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<FarmDto> list = ImportExportExcelUtil.importWL2017FarmFromInputStream(bis);
		farmService.saveListImportFarm(list);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importExAnimal", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importExAnimal(@RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<ImportExportAnimalDto> list = ImportExportExcelUtil.importImportAnimalFromInputStream(bis);
		importExportAnimalService.saveOrUpdateList(list);

		return new ResponseEntity<>(HttpStatus.OK);
	} // method upload Farm

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/exportImAnimal", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportImAnimal(@RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<ImportExportAnimalDto> list = ImportExportExcelUtil.importExportAnimalFromInputStream(bis);
		importExportAnimalService.saveOrUpdateList(list);

		return new ResponseEntity<>(HttpStatus.OK);
	} // method upload Farm

	// tran huu dat tạo hàm xuất file word start
	@RequestMapping(value = "/downloadForestList2", method = { RequestMethod.POST })
	public void downloadForestList2(WebRequest request, HttpServletResponse response,
			@RequestBody ForestProductsListDto dto) {
		ForestProductsListDto forestProductsListDto = null;
		String strDateFormat = "dd-MM-yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		if (dto.getId() != null && dto != null) {
			forestProductsListDto = forestProductsListService.getById(dto.getId());
		}

		if (forestProductsListDto != null) {
			try {
				try (XWPFDocument document = new XWPFDocument()) {
					// them footer
					XWPFHeaderFooterPolicy headerFooterPolicy = document.getHeaderFooterPolicy();
					if (headerFooterPolicy == null) {
						headerFooterPolicy = document.createHeaderFooterPolicy();
					}

					String addressde = "";
					if (forestProductsListDto.getFarm() != null) {
						if (forestProductsListDto.getFarm().getWardsName() != null) {
							if (addressde.length() > 0) {
								addressde += ", " + forestProductsListDto.getFarm().getWardsName();
							} else {
								addressde += forestProductsListDto.getFarm().getWardsName();
							}
						}
						if (forestProductsListDto.getFarm().getDistrictName() != null) {
							if (addressde.length() > 0) {
								addressde += ", " + forestProductsListDto.getFarm().getDistrictName();
							} else {
								addressde += forestProductsListDto.getFarm().getDistrictName();
							}
						}
						if (forestProductsListDto.getFarm().getProvinceName() != null) {
							if (addressde.length() > 0) {
								addressde += ", " + forestProductsListDto.getFarm().getProvinceName();
							} else {
								addressde += forestProductsListDto.getFarm().getProvinceName();
							}
						}
					}

					String addressDetails = "";
					if (forestProductsListDto.getFarm() != null) {
						if (forestProductsListDto.getFarm().getApartmentNumber() != null) {
							if (addressDetails.length() > 0) {
								addressDetails += ", " + forestProductsListDto.getFarm().getApartmentNumber();
							} else {
								addressDetails += forestProductsListDto.getFarm().getApartmentNumber();
							}
						}
						if (forestProductsListDto.getFarm().getAdressDetail() != null) {
							if (addressDetails.length() > 0) {
								addressDetails += ", " + forestProductsListDto.getFarm().getAdressDetail();
							} else {
								addressDetails += forestProductsListDto.getFarm().getAdressDetail();
							}
						}
						if (forestProductsListDto.getFarm().getWardsName() != null) {
							if (addressDetails.length() > 0) {
								addressDetails += ", " + forestProductsListDto.getFarm().getWardsName();
							} else {
								addressDetails += forestProductsListDto.getFarm().getWardsName();
							}
						}
						if (forestProductsListDto.getFarm().getDistrictName() != null) {
							if (addressDetails.length() > 0) {
								addressDetails += ", " + forestProductsListDto.getFarm().getDistrictName();
							} else {
								addressDetails += forestProductsListDto.getFarm().getDistrictName();
							}
						}
						if (forestProductsListDto.getFarm().getProvinceName() != null) {
							if (addressDetails.length() > 0) {
								addressDetails += ", " + forestProductsListDto.getFarm().getProvinceName();
							} else {
								addressDetails += forestProductsListDto.getFarm().getProvinceName();
							}
						}
					}
					String addressDetailsTo = "";
					if (forestProductsListDto.getAdministrativeUnitTo() != null) {
						if (forestProductsListDto.getAdministrativeUnitTo().getName() != null) {
							if (addressDetailsTo.length() > 0) {
								addressDetailsTo += ", " + forestProductsListDto.getAdministrativeUnitTo().getName();
							} else {
								addressDetailsTo += forestProductsListDto.getAdministrativeUnitTo().getName();
							}
						}
						if (forestProductsListDto.getAdministrativeUnitTo().getParentDto() != null) {
							if (forestProductsListDto.getAdministrativeUnitTo().getParentDto().getName() != null) {
								if (addressDetailsTo.length() > 0) {
									addressDetailsTo += ", "
											+ forestProductsListDto.getAdministrativeUnitTo().getParentDto().getName();
								} else {
									addressDetailsTo += forestProductsListDto.getAdministrativeUnitTo().getParentDto()
											.getName();
								}
							}
						}
					}

					XWPFParagraph paragraph = document.createParagraph();// tạo biến để viết paragraph

					paragraph = document.createParagraph();
					XWPFRun runBold = paragraph.createRun();
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(9);
					runBold.setBold(true);// set chu dam
					runBold.setText(
							"Mẫu số 04. Bảng kê lâm sản (Áp dụng đối với động vật rừng; bộ phận, dẫn xuất của động vật rừng)");
					runBold.addBreak();

					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontSize(12);
					runBold.setFontFamily("Times New Roman");
					runBold.setBold(true);// set biến viết chữ đậm
					runBold.setText("CỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM");
					runBold.addBreak();
					runBold.setText("Độc lập - Tự do - Hạnh phúc");
					runBold.addBreak();
					runBold.setText("---------------");
					// ------heets doan text tieu de----------
					paragraph = document.createParagraph();
					paragraph.setAlignment(ParagraphAlignment.RIGHT);// set chu ben phai
					runBold = paragraph.createRun();
					runBold.setFontSize(8);
					runBold.setFontFamily("Times New Roman");
					runBold.setText("Tờ số:...../Tổng số tờ .....");
					runBold.addBreak();

					// ---het doan text ben phai--------

					paragraph = document.createParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(16);
					runBold.setBold(true);// set kieu viet dam
					runBold.setText("BẢNG KÊ LÂM SẢN");

					// -- het doan text tieu de------
					paragraph = document.createParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setItalic(true);// set chu nghieng
					runBold.setText("(Áp dụng đối với động vật rừng; bộ phận, dẫn xuất của động vật rừng)");
					runBold.addBreak();
					runBold.setText("Số: …./….(1)");
					runBold.addBreak();

					// bat dau noi dung

					paragraph = document.createParagraph();
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(12);
					runBold.setBold(true);// set chu dam
					runBold.setText("Thông tin chung:");

					paragraph = document.createParagraph();
					// paragraph.setSpacingBetween(1.25);
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setText("Tên chủ lâm sản: " + forestProductsListDto.getFarm().getName());
					runBold.addBreak();
					runBold.setText(
							"Giấy đăng ký kinh doanh/mã số doanh nghiệp (đối với doanh nghiệp):................");
					runBold.addBreak();
					runBold.setText("Địa chỉ: " + addressde);
					runBold.addBreak();
					runBold.setText("Số điện thoại liên hệ:	"
							+ (forestProductsListDto.getFarm().getOwnerPhoneNumber() == null ? ".........."
									: forestProductsListDto.getFarm().getOwnerPhoneNumber()));
					runBold.addBreak();
					String Code = "";
					if (forestProductsListDto.getFarm().getNewRegistrationCode() != null
							&& forestProductsListDto.getFarm().getOldRegistrationCode() != null) {
						Code += forestProductsListDto.getFarm().getNewRegistrationCode();
					}

					if (forestProductsListDto.getFarm().getNewRegistrationCode() != null) {
						Code += forestProductsListDto.getFarm().getNewRegistrationCode();
					}

					if (forestProductsListDto.getFarm().getOldRegistrationCode() != null) {
						Code += forestProductsListDto.getFarm().getOldRegistrationCode();
					}

					runBold.setText("Nguồn gốc lâm sản (2):	"
							+ (forestProductsListDto.getOriginal() == null ? "..........."
									: forestProductsListDto.getOriginal())
							+ "             Mã số cơ sở nuôi:" + (Code == null ? ".........." : Code));
					runBold.addBreak();
					runBold.setText("Số hóa đơn kèm theo (nếu có): "
							+ (forestProductsListDto.getInvoiceCode() == null ? "........"
									: forestProductsListDto.getInvoiceCode())
							+ ";			Ngày: " + (forestProductsListDto.getInvoiceDate() == null ? "......."
									: dateFormat.format(forestProductsListDto.getInvoiceDate())));
					runBold.addBreak();
					runBold.setText("Phương tiện vận chuyển (nếu có): "
							+ (forestProductsListDto.getVehicle() == null ? "........."
									: forestProductsListDto.getVehicle())
							+ "          biển số/số hiệu phương tiện:  "
							+ (forestProductsListDto.getVehicleRegistrationPlate() == null ? "........."
									: forestProductsListDto.getVehicleRegistrationPlate()));
					runBold.addBreak();
					runBold.setText("Thời gian vận chuyển:  "
							+ (forestProductsListDto.getTransportDuration() == null ? "........."
									: forestProductsListDto.getTransportDuration())
							+ "ngày;");
					runBold.addTab();
					runBold.setText("Từ Ngày: " + (forestProductsListDto.getTransportStart() == null ? "........"
							: dateFormat.format(forestProductsListDto.getTransportStart())));
					runBold.setText("     Đến Ngày: " + (forestProductsListDto.getTransportEnd() == null ? "......."
							: dateFormat.format(forestProductsListDto.getTransportEnd())));
					runBold.addBreak();
					runBold.setText("Vận chuyển từ: " + addressDetails + "          Đến: "
							+ (forestProductsListDto.getBuyerName() == null ? ""
									: ("(ông/bà) " + forestProductsListDto.getBuyerName() + ", "))
							+ addressDetailsTo + (forestProductsListDto.getBuyerDetailAddress() == null ? ""
									: (" (" + forestProductsListDto.getBuyerDetailAddress() + ")")));
					runBold.addBreak();

					// bắt đầu vẽ bảng trên word;

					XWPFTable table = document.createTable();// tạo biến để vẽ bảng;

					// tạo header của bảng
					XWPFTableRow tableRowOne = table.getRow(0);

					tableRowOne.getCell(0).setText("TT");
					paragraph = tableRowOne.getCell(0).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("TT");
					tableRowOne.getCell(0).removeParagraph(0);
					tableRowOne.getCell(0).setParagraph(paragraph);

					tableRowOne.addNewTableCell();
					paragraph = tableRowOne.getCell(1).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("Tên loài");
					tableRowOne.getCell(1).removeParagraph(0);
					tableRowOne.getCell(1).setParagraph(paragraph);

					tableRowOne.addNewTableCell().setText(null);

					tableRowOne.addNewTableCell();
					paragraph = tableRowOne.getCell(3).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("Nhóm loài");
					tableRowOne.getCell(3).removeParagraph(0);
					tableRowOne.getCell(3).setParagraph(paragraph);

					tableRowOne.addNewTableCell().setText("Số hiệu nhãn đánh dấu (nếu có)");
					paragraph = tableRowOne.getCell(4).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("Số hiệu nhãn đánh dấu (nếu có)");
					tableRowOne.getCell(4).removeParagraph(0);
					tableRowOne.getCell(4).setParagraph(paragraph);

					tableRowOne.addNewTableCell();
					paragraph = tableRowOne.getCell(5).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("Số lượng");
					tableRowOne.getCell(5).removeParagraph(0);
					tableRowOne.getCell(5).setParagraph(paragraph);

					tableRowOne.addNewTableCell();
					paragraph = tableRowOne.getCell(6).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("Trọng lượng");
					tableRowOne.getCell(6).removeParagraph(0);
					tableRowOne.getCell(6).setParagraph(paragraph);

					tableRowOne.addNewTableCell();
					paragraph = tableRowOne.getCell(7).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("Đơn vị tính");
					tableRowOne.getCell(7).removeParagraph(0);
					tableRowOne.getCell(7).setParagraph(paragraph);

					tableRowOne.addNewTableCell();
					paragraph = tableRowOne.getCell(8).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("Ghi chú");
					tableRowOne.getCell(8).removeParagraph(0);
					tableRowOne.getCell(8).setParagraph(paragraph);

					XWPFTableRow tableRowTwo = table.createRow();
					tableRowTwo.getCell(0).setText(null);

					tableRowTwo.getCell(1);
					paragraph = tableRowTwo.getCell(1).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("Tên phổ thông");
					tableRowTwo.getCell(1).removeParagraph(0);
					tableRowTwo.getCell(1).setParagraph(paragraph);

					tableRowTwo.getCell(2);
					paragraph = tableRowTwo.getCell(2).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setBold(true);
					runBold.setText("Tên khoa học");
					tableRowTwo.getCell(2).removeParagraph(0);
					tableRowTwo.getCell(2).setParagraph(paragraph);

					tableRowTwo.getCell(3).setText(null);
					tableRowTwo.getCell(4).setText(null);
					tableRowTwo.getCell(5).setText(null);
					tableRowTwo.getCell(6).setText(null);
					tableRowTwo.getCell(7).setText(null);
					tableRowTwo.getCell(8).setText(null);

					// in danh sach cac ban ke lam san
					XWPFTableRow tableRow = null;
					int stt = 1;
					String detailsAnimal = "";
					if (forestProductsListDto.getDetails() != null && forestProductsListDto.getDetails().size() > 0) {

						for (ForestProductsListDetailDto element : forestProductsListDto.getDetails()) {

							if (detailsAnimal.length() > 0) {
								detailsAnimal += ", ";
							}
							if (element.getTotal() != null) {
								detailsAnimal += element.getTotal() + " con ";
							}
							if (element.getAnimal() != null && element.getAnimal().getName() != null) {
								detailsAnimal += element.getAnimal().getName() + " ";
							}

							tableRow = table.createRow();
							paragraph = tableRow.getCell(0).addParagraph();
							paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
							runBold = paragraph.createRun();
							runBold.setFontFamily("Times New Roman");
							runBold.setFontSize(10);
							runBold.setText("" + stt);
							tableRow.getCell(0).removeParagraph(0);
							tableRow.getCell(0).setParagraph(paragraph);
							stt += 1;

							paragraph = tableRow.getCell(1).addParagraph();
							paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
							runBold = paragraph.createRun();
							runBold.setFontFamily("Times New Roman");
							runBold.setFontSize(10);
							runBold.setText(element.getAnimal().getName());
							tableRow.getCell(1).removeParagraph(0);
							tableRow.getCell(1).setParagraph(paragraph);

							paragraph = tableRow.getCell(2).addParagraph();
							paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
							runBold = paragraph.createRun();
							runBold.setFontFamily("Times New Roman");
							runBold.setFontSize(10);
							runBold.setText(element.getAnimal().getScienceName());
							tableRow.getCell(2).removeParagraph(0);
							tableRow.getCell(2).setParagraph(paragraph);

							paragraph = tableRow.getCell(3).addParagraph();
							paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
							runBold = paragraph.createRun();
							runBold.setFontFamily("Times New Roman");
							runBold.setFontSize(10);
							runBold.setText(element.getAnimal().getAnimalGroup());
							tableRow.getCell(3).removeParagraph(0);
							tableRow.getCell(3).setParagraph(paragraph);

							paragraph = tableRow.getCell(4).addParagraph();
							paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
							runBold = paragraph.createRun();
							runBold.setFontFamily("Times New Roman");
							runBold.setFontSize(10);
							runBold.setText(element.getCode());
							tableRow.getCell(4).removeParagraph(0);
							tableRow.getCell(4).setParagraph(paragraph);

							paragraph = tableRow.getCell(5).addParagraph();
							paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
							runBold = paragraph.createRun();
							runBold.setFontFamily("Times New Roman");
							runBold.setFontSize(10);
							runBold.setText(element.getTotal() != null ? element.getTotal().toString() : "");
							tableRow.getCell(5).removeParagraph(0);
							tableRow.getCell(5).setParagraph(paragraph);

							paragraph = tableRow.getCell(6).addParagraph();
							paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
							runBold = paragraph.createRun();
							runBold.setFontFamily("Times New Roman");
							runBold.setFontSize(10);
							runBold.setText(element.getAmount() != null ? element.getAmount().toString() : "");
							tableRow.getCell(6).removeParagraph(0);
							tableRow.getCell(6).setParagraph(paragraph);

							paragraph = tableRow.getCell(7).addParagraph();
							paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
							runBold = paragraph.createRun();
							runBold.setFontFamily("Times New Roman");
							runBold.setFontSize(10);
							runBold.setText(element.getUnit());
							tableRow.getCell(7).removeParagraph(0);
							tableRow.getCell(7).setParagraph(paragraph);

							paragraph = tableRow.getCell(8).addParagraph();
							paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
							runBold = paragraph.createRun();
							runBold.setFontFamily("Times New Roman");
							runBold.setFontSize(10);
							runBold.setText(element.getNote());
							tableRow.getCell(8).removeParagraph(0);
							tableRow.getCell(8).setParagraph(paragraph);
						}
					}

					// merge cell in table
					// CTVMerge vmerge = CTVMerge.Factory.newInstance();
//				    XWPFTableCell cell = table.getRow(0).getCell(1);
//				    XWPFTableCell cell2 = table.getRow(0).getCell(2);
//				    cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
//				    cell2.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
					XWPFTableCell cellMerge2 = null;
					XWPFTableCell cellMerge1 = null;
					for (int col = 0; col < 9; col++) {
						if (col != 1 && col != 2) {
							cellMerge1 = table.getRow(0).getCell(col);// lay cell o thu col hang 0
							cellMerge2 = table.getRow(1).getCell(col);// lay cell o thu col hang 1
							cellMerge1.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
							cellMerge2.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
						}
					}
					// mer 2 cell in row
					for (int colIndex = 1; colIndex <= 2; colIndex++) {
						XWPFTableCell cell = table.getRow(0).getCell(colIndex);
						CTHMerge hmerge = CTHMerge.Factory.newInstance();
						if (colIndex == 1) {
							// The first merged cell is set with RESTART merge value
							hmerge.setVal(STMerge.RESTART);
						} else {
							// Cells which join (merge) the first one, are set with CONTINUE
							hmerge.setVal(STMerge.CONTINUE);
							// and the content should be removed
							for (int i = cell.getParagraphs().size(); i > 0; i--) {
								cell.removeParagraph(0);
							}
							cell.addParagraph();
						}
						// Try getting the TcPr. Not simply setting an new one every time.
						CTTcPr tcPr = cell.getCTTc().getTcPr();
						if (tcPr != null) {
							tcPr.setHMerge(hmerge);
						} else {
							// only set an new TcPr if there is not one already
							tcPr = CTTcPr.Factory.newInstance();
							tcPr.setHMerge(hmerge);
							cell.getCTTc().setTcPr(tcPr);
						}
					}
					// mer 2 cell in row

					// hết bảng.
					paragraph = document.createParagraph();
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.addBreak();
					runBold.setText(
							"Tổng số lượng và trọng lượng từng loài động vật rừng, bộ phận và dẫn xuất của chúng có trong bảng kê: "
									+ detailsAnimal);
					runBold.addBreak();
					paragraph.setSpacingBetween(1);
					// phan ki ten cua ban ke
					table = document.createTable(3, 2);
					CTTblBorders borders = table.getCTTbl().getTblPr().addNewTblBorders();
					CTBorder hBorder = borders.addNewInsideV();
					hBorder.setVal(STBorder.NONE);
					hBorder = borders.addNewInsideV();
					hBorder.setVal(STBorder.NONE);
					hBorder = borders.addNewBottom();
					hBorder.setVal(STBorder.NONE);
					hBorder = borders.addNewLeft();
					hBorder.setVal(STBorder.NONE);
					hBorder = borders.addNewRight();
					hBorder.setVal(STBorder.NONE);
					hBorder = borders.addNewTop();
					hBorder.setVal(STBorder.NONE);

					// chinh width cua bang cuoi
					for (int col = 0; col < 2; col++) {
						CTTblWidth tblWidth = CTTblWidth.Factory.newInstance();
						tblWidth.setW(BigInteger.valueOf(1 * 6000));
						tblWidth.setType(STTblWidth.DXA);
						for (int row = 0; row < 3; row++) {
							CTTcPr tcPr = table.getRow(row).getCell(col).getCTTc().getTcPr();
							if (tcPr != null) {
								tcPr.setTcW(tblWidth);
							} else {
								tcPr = CTTcPr.Factory.newInstance();
								tcPr.setTcW(tblWidth);
								table.getRow(row).getCell(col).getCTTc().setTcPr(tcPr);
							}
						}
					}
					// chinh width cua bang cuoi

					tableRowOne = table.getRow(0);

					paragraph = tableRowOne.getCell(0).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setItalic(true);// set chu nghieng
					runBold.setText(".........Ngày........tháng......năm 20…........");
					tableRowOne.getCell(0).removeParagraph(0);
					tableRowOne.getCell(0).setParagraph(paragraph);
					tableRowOne.getCell(1).setParagraph(paragraph);

					tableRowOne = table.getRow(1);
					paragraph = tableRowOne.getCell(0).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					// runBold.setItalic(true);//set chu nghieng
					runBold.setBold(true);
					runBold.setText("XÁC NHẬN CỦA CƠ QUAN KIỂM LÂM SỞ TẠI");
					tableRowOne.getCell(0).removeParagraph(0);
					tableRowOne.getCell(0).setParagraph(paragraph);

					paragraph = tableRowOne.getCell(1).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					// runBold.setItalic(true);//set chu nghieng
					runBold.setBold(true);
					runBold.setText("TỔ CHỨC, CÁ NHÂN LẬP BẢNG KÊ LÂM SẢN");
					tableRowOne.getCell(1).removeParagraph(0);
					tableRowOne.getCell(1).setParagraph(paragraph);

					tableRowOne = table.getRow(2);
					paragraph = tableRowOne.getCell(0).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					// runBold.setItalic(true);//set chu nghieng
					// runBold.setBold(true);
					runBold.setText("Vào sổ số: …/…");
					tableRowOne.getCell(0).removeParagraph(0);
					tableRowOne.getCell(0).setParagraph(paragraph);

					paragraph = tableRowOne.getCell(0).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setItalic(true);// set chu nghieng
					// runBold.setBold(true);
					runBold.setText("(Người có thẩm quyền ký, ghi rõ họ tên, đóng dấu)");

					paragraph = tableRowOne.getCell(1).addParagraph();
					paragraph.setAlignment(ParagraphAlignment.CENTER);// set text center
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setItalic(true);// set chu nghieng
					// runBold.setBold(true);
					runBold.setText("(Ký, ghi rõ họ tên, đóng dấu đối với tổ chức; ký ghi rõ họ tên đối với cá nhân)");
					tableRowOne.getCell(1).removeParagraph(0);
					tableRowOne.getCell(1).setParagraph(paragraph);

					XWPFFooter footer = headerFooterPolicy.createFooter(XWPFHeaderFooterPolicy.DEFAULT);

					paragraph = footer.createParagraph();
					paragraph.setAlignment(ParagraphAlignment.LEFT);
					runBold = paragraph.createRun();
					runBold.setFontFamily("Times New Roman");
					runBold.setFontSize(10);
					runBold.setItalic(true);// set chu nghieng
					runBold.setText(
							"_____________________________________________________________________________________________");
					runBold.addBreak();
					runBold.setText("Bảng kê được tạo bởi: Hạt kiểm "
							+ (forestProductsListDto.getAdministrativeUnitFrom() == null ? ""
									: forestProductsListDto.getAdministrativeUnitFrom().getName())
							+ (forestProductsListDto.getAdministrativeUnitFrom() == null ? ""
									: (forestProductsListDto.getAdministrativeUnitFrom().getParentDto() == null ? ""
											: ("- " + forestProductsListDto.getAdministrativeUnitFrom().getParentDto()
													.getName()))));
					runBold.addBreak();
					runBold.setText("Email: "
							+ (forestProductsListDto.getConfirmByUser() == null ? ""
									: forestProductsListDto.getConfirmByUser().getEmail())
							+ (forestProductsListDto.getConfirmByUser() == null ? ""
									: (forestProductsListDto.getConfirmByUser().getPerson() == null ? ""
											: (forestProductsListDto.getConfirmByUser().getPerson()
													.getPhoneNumber() == null ? ""
															: ("- ĐT: " + forestProductsListDto.getConfirmByUser()
																	.getPerson().getPhoneNumber())))));

					document.write(response.getOutputStream());
				}
				response.setContentType("application/msword");
				response.setHeader("Content-Disposition", "attachment; filename=BangKeLamSan.doc");
				response.flushBuffer();

			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		} else {
			System.out.println("ko co ban ghi");
		}

	}

	@RequestMapping(value = "/downloadForestList", method = { RequestMethod.POST })
	public void downloadForestList(WebRequest request, HttpServletResponse response,
			@RequestBody ForestProductsListDto dto) {
		ForestProductsListDto forestProductsListDto = null;
		String strDateFormat = "dd-MM-yyyy";
		SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat);
		if (dto.getId() != null && dto != null) {
			forestProductsListDto = forestProductsListService.getById(dto.getId());
		}

		if (forestProductsListDto != null) {
			Resource resource = resourceLoader.getResource("classpath:template/BangKeLamSan_mau_in.docx");
			try (XWPFDocument document = new XWPFDocument(resource.getInputStream())) {
//				XWPFTable table0 = document.getTableArray(0);
//				if(table0 != null) {
//					XWPFTableRow row = table0.getRow(0);
//					XWPFTableCell cell = row.getTableCells().get(0);
//					if (forestProductsListDto.getProvinceName() == null && forestProductsListDto.getOrganizationName() == null) {
//						row = table0.getRow(0);
//						cell = row.getTableCells().get(0);
//						for (XWPFParagraph paragraph : cell.getParagraphs()) {
//							List<XWPFRun> runs = paragraph.getRuns();
//							for (XWPFRun r : runs) {
//								String text = "";
//								r.setText(text,0);
//							}
//						}
//					}else {
//						for (XWPFParagraph paragraph: cell.getParagraphs()) {
//							List<XWPFRun> runs = paragraph.getRuns();
//							for (XWPFRun r : runs) {
//								String text = r.getText(0);
//								if(text != null && text.contains("TINH_PTNT") && forestProductsListDto.getProvinceName() != null) {
//									text = text.replace("TINH_PTNT", forestProductsListDto.getProvinceName());
//									text = text.toUpperCase();
//									r.setText(text, 0);
//								} 
//								if(text != null && text.contains("Co_Quan") && forestProductsListDto.getOrganizationName() !=null) {
//									text = text.replace("Co_Quan", forestProductsListDto.getOrganizationName());
//									r.setText(text, 0); 
//								}
//							}
//						}
//					}
//					CTGroup ctGroup = CTGroup.Factory.newInstance();
//					CTShape ctShape = ctGroup.addNewShape();
//				}

				XWPFTable table1 = document.getTableArray(1);
				if (table1 != null) {
					XWPFTableRow oldRow = table1.getRow(0);
					XWPFTableCell cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getFarm() != null) {
							text = forestProductsListDto.getFarm().getOwnerName();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(1);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getFarm().getBusinessRegistrationNumber() != null) {
							text = forestProductsListDto.getFarm().getBusinessRegistrationNumber();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(2);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(1);
						// nếu địa chỉ chủ lâm sản khác địa chỉ cơ sở
						if (forestProductsListDto.getFarm().getOwnerAdministrativeUnit() != null) {
							String addressDetails = "";

							if (forestProductsListDto.getFarm().getOwnerVillage() != null) {
								addressDetails += forestProductsListDto.getFarm().getOwnerVillage();
							}
							if (forestProductsListDto.getFarm().getOwnerAdministrativeUnit() != null) {
								if (addressDetails.length() > 0) {
									addressDetails += ", "
											+ forestProductsListDto.getFarm().getOwnerAdministrativeUnit().getName();
								} else {
									addressDetails += forestProductsListDto.getFarm().getOwnerAdministrativeUnit()
											.getName();
								}
							}
							if (forestProductsListDto.getFarm().getOwnerAdministrativeUnit() != null) {
								addressDetails += ", " + forestProductsListDto.getFarm().getOwnerAdministrativeUnit()
										.getParentDto().getName();
							}
							if (forestProductsListDto.getFarm().getOwnerAdministrativeUnit() != null) {
								addressDetails += ", " + forestProductsListDto.getFarm().getOwnerAdministrativeUnit()
										.getParentDto().getParentDto().getName();
							}
							run.setText(addressDetails, 0);
						} else if (forestProductsListDto.getFarm().getOwnerAdministrativeUnit() == null
								&& forestProductsListDto.getFarm().getAdministrativeUnit() != null) {
							String addressDetails = "";
//							if (forestProductsListDto.getFarm().getAdressDetail() != null) {
//								if (addressDetails.length() > 0) {
//									addressDetails += ", " + forestProductsListDto.getFarm().getAdressDetail();
//								} else {
//									addressDetails += forestProductsListDto.getFarm().getAdressDetail();
//								}
//							}
							if (forestProductsListDto.getFarm().getVillage() != null) {
								if (addressDetails.length() > 0) {
									addressDetails += ", " + forestProductsListDto.getFarm().getVillage();
								} else {
									addressDetails += forestProductsListDto.getFarm().getVillage();
								}
							}
							if (forestProductsListDto.getFarm().getAdministrativeUnit() != null) {
								if (addressDetails.length() > 0) {
									addressDetails += ", "
											+ forestProductsListDto.getFarm().getAdministrativeUnit().getName();
								} else {
									addressDetails += forestProductsListDto.getFarm().getAdministrativeUnit().getName();
								}
							}
							if (forestProductsListDto.getFarm().getDistrictName() != null) {
								addressDetails += ", " + forestProductsListDto.getFarm().getDistrictName();
							}
							if (forestProductsListDto.getFarm().getProvinceName() != null) {
								addressDetails += ", " + forestProductsListDto.getFarm().getProvinceName();
							}
							run.setText(addressDetails, 0);
						}
					}

					oldRow = table1.getRow(3);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getFarm() != null) {
							text = forestProductsListDto.getFarm().getPhoneNumber();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(4);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getOriginal() != null) {
							text = forestProductsListDto.getOriginal();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(5);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getFarm() != null) {
							text = forestProductsListDto.getFarm().getName();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(6);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String addressDetails = "";

//						if (forestProductsListDto.getFarm().getAdressDetail() != null) {
//							addressDetails += forestProductsListDto.getFarm().getAdressDetail();
//						}
						if (forestProductsListDto.getFarm().getVillage() != null) {
							if (addressDetails.length() > 0) {
								addressDetails += ", " + forestProductsListDto.getFarm().getVillage();
							} else {
								addressDetails += forestProductsListDto.getFarm().getVillage();
							}
						}
						if (forestProductsListDto.getFarm().getAdministrativeUnit() != null) {
							if (addressDetails.length() > 0) {
								addressDetails += ", "
										+ forestProductsListDto.getFarm().getAdministrativeUnit().getName();
							} else {
								addressDetails += forestProductsListDto.getFarm().getAdministrativeUnit().getName();
							}
						}
						if (forestProductsListDto.getFarm().getAdministrativeUnit() != null) {
							addressDetails += ", "
									+ forestProductsListDto.getFarm().getAdministrativeUnit().getParentDto().getName();
						}
						if (forestProductsListDto.getFarm().getAdministrativeUnit() != null) {
							addressDetails += ", " + forestProductsListDto.getFarm().getAdministrativeUnit()
									.getParentDto().getParentDto().getName();
						}
						run.setText(addressDetails, 0);
					}

					oldRow = table1.getRow(7);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getFarm().getNewRegistrationCode() != null) {
							text = forestProductsListDto.getFarm().getNewRegistrationCode();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(7);
					cell = oldRow.getTableCells().get(3);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getFarm().getDateRegistration() != null) {
							text = dateFormat.format(forestProductsListDto.getFarm().getDateRegistration());
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(8);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getInvoiceCode() != null) {
							text = forestProductsListDto.getInvoiceCode();
						}

						run.setText(text, 0);
					}

					oldRow = table1.getRow(8);
					cell = oldRow.getTableCells().get(3);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getInvoiceDate() != null) {
							text = dateFormat.format(forestProductsListDto.getInvoiceDate());
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(9);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getVehicle() != null) {
							text = forestProductsListDto.getVehicle();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(9);
					cell = oldRow.getTableCells().get(3);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getVehicleRegistrationPlate() != null) {
							text = forestProductsListDto.getVehicleRegistrationPlate();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(10);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getTransportDuration() != null) {
							text = forestProductsListDto.getTransportDuration() + " ngày";
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(10);
					cell = oldRow.getTableCells().get(3);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getTransportStart() != null) {
							text = dateFormat.format(forestProductsListDto.getTransportStart());
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(10);
					cell = oldRow.getTableCells().get(5);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getTransportEnd() != null) {
							text = dateFormat.format(forestProductsListDto.getTransportEnd());
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(11);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getFarm() != null) {
//							(forestProductsListDto.getFarm().getAdressDetail() != null
//									? forestProductsListDto.getFarm().getAdressDetail() + ", "
//									: "")
//									+ 
							text = (forestProductsListDto.getFarm().getVillage() != null
											? forestProductsListDto.getFarm().getVillage() + ", "
											: "")
									+ (forestProductsListDto.getCommuneFrom() != null
											? forestProductsListDto.getCommuneFrom().getName() + ", "
											: "");
//									+ (forestProductsListDto.getAdministrativeUnitFrom().getName() != null
//											? forestProductsListDto.getAdministrativeUnitFrom().getName() + ", "
//											: "")
//									+ (forestProductsListDto.getAdministrativeUnitFrom().getParentDto()
//											.getName() != null
//													? forestProductsListDto.getAdministrativeUnitFrom().getParentDto()
//															.getName()
//													: "");
							if(forestProductsListDto.getAdministrativeUnitFrom() != null) {
								text += (forestProductsListDto.getAdministrativeUnitFrom().getName() != null
										? forestProductsListDto.getAdministrativeUnitFrom().getName() + ", "
										: "")
								+ (forestProductsListDto.getAdministrativeUnitFrom().getParentDto().getName() != null
												? forestProductsListDto.getAdministrativeUnitFrom().getParentDto().getName()
												: "");
							}
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(12);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (forestProductsListDto.getAdministrativeUnitTo() != null) {
							text = (forestProductsListDto.getBuyerName() != null
									? "(ông/bà) " + forestProductsListDto.getBuyerName() + ", "
									: "")
//									+ (forestProductsListDto.getBuyerDetailAddress() != null
//											? forestProductsListDto.getBuyerDetailAddress() + ", "
//											: "")
									+ (forestProductsListDto.getVillageTo() != null
											? forestProductsListDto.getVillageTo() + ", "
											: "")
									+ (forestProductsListDto.getCommuneTo() != null
											? forestProductsListDto.getCommuneTo().getName() + ", "
											: "")
									+ (forestProductsListDto.getAdministrativeUnitTo().getName() != null
											? forestProductsListDto.getAdministrativeUnitTo().getName() + ", "
											: "")
									+ (forestProductsListDto.getAdministrativeUnitTo().getParentDto().getName() != null
											? forestProductsListDto.getAdministrativeUnitTo().getParentDto().getName()
											: "")
									+ (forestProductsListDto.getBuyerPhoneNumber() != null
											? " (" + forestProductsListDto.getBuyerPhoneNumber() + ")"
											: "");
						}
						run.setText(text, 0);
					}
				}

				XWPFTable table = document.getTableArray(2);
				String detailsAnimal = "";
				if (table != null) {
					XWPFTableRow oldRow = table.getRow(2);

					int stt = 1;
					if (forestProductsListDto.getDetails() != null) {
						for (ForestProductsListDetailDto element : forestProductsListDto.getDetails()) {

							if (detailsAnimal.length() > 0) {
								detailsAnimal += "; ";
							}
							if (element.getTotal() != null) {
								detailsAnimal += element.getTotal() + " cá thể ";
							} else {
								detailsAnimal += "0 cá thể ";
							}
							if (element.getAnimal() != null && element.getAnimal().getName() != null) {
								detailsAnimal += element.getAnimal().getName();
							}
							if (element.getAnimal() != null && element.getAnimal().getScienceName() != null) {
								detailsAnimal += " (" + element.getAnimal().getScienceName() + ")";
							}
							if (element.getMale() != null) {
								if (element.getMale() > 0) {
									detailsAnimal += " " + String.valueOf(element.getMale()) + " cá thể đực";
								}
							}
							if (element.getFemale() != null) {
								if (element.getFemale() > 0) {
									if (element.getMale() > 0) {
										detailsAnimal += ", " + String.valueOf(element.getFemale()) + " cá thể cái";
									} else {
										detailsAnimal += " " + String.valueOf(element.getFemale()) + " cá thể cái";
									}

								}

							}
							if (element.getUnGender() != null) {
								if (element.getUnGender() > 0) {
									if (element.getMale() > 0 || element.getFemale() > 0) {
										detailsAnimal += ", " + String.valueOf(element.getUnGender())
												+ " cá thể không rõ giới tính";
									} else {
										detailsAnimal += " " + String.valueOf(element.getUnGender())
												+ " cá thể không rõ giới tính";
									}

								}
							}
							if (element.getAmount() != null) {
								detailsAnimal += ", tổng trọng lượng " + String.valueOf(element.getAmount()) + " kg";
							}

							CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
							XWPFTableRow newRow = new XWPFTableRow(ctrow, table);

							XWPFTableCell cell = newRow.getTableCells().get(0);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(String.valueOf(stt), 0);
							}

							cell = newRow.getTableCells().get(1);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(element.getAnimal().getName() != null ? element.getAnimal().getName() : "",
										0);
							}

							cell = newRow.getTableCells().get(2);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(element.getAnimal().getScienceName() != null
										? element.getAnimal().getScienceName()
										: "", 0);
							}

							cell = newRow.getTableCells().get(3);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(element.getAnimal().getAnimalGroup() != null
										? element.getAnimal().getAnimalGroup()
										: "", 0);
							}

							cell = newRow.getTableCells().get(4);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(element.getCode() != null ? element.getCode() : "", 0);
							}

							cell = newRow.getTableCells().get(5);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(element.getTotal() != null ? element.getTotal().toString() : "", 0);
							}

							cell = newRow.getTableCells().get(7);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(element.getAmount() != null ? element.getAmount().toString() : "", 0);
							}

							cell = newRow.getTableCells().get(6);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(element.getUnit() != null ? element.getUnit() : "", 0);
							}

							cell = newRow.getTableCells().get(8);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(element.getNote() != null ? element.getNote() : "", 0);
							}

							table.addRow(newRow);
							stt++;
						}
					}
					table.removeRow(2);
				}

				XWPFTable table3 = document.getTableArray(3);
				String strDateFormatYear = "YYYY";
				String strDateFormatDay = "dd";
				String strDateFormatMonth = "MM";
				SimpleDateFormat dateFormatYear = new SimpleDateFormat(strDateFormatYear);
				SimpleDateFormat dateFormatMonth = new SimpleDateFormat(strDateFormatMonth);
				SimpleDateFormat dateFormatDay = new SimpleDateFormat(strDateFormatDay);
				if (table3 != null) {
					XWPFTableRow row = table3.getRow(0);
					XWPFTableCell cell = row.getTableCells().get(0);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("XN_l")
									&& forestProductsListDto.getAddressConfirm() != null) {
								text = text.replace("XN_l", forestProductsListDto.getAddressConfirm());
								r.setText(text, 0);
							} else {
								text = text.replace("XN_l", "........");
								r.setText(text, 0);
							}
							if (text != null && text.contains("day_left")
									&& forestProductsListDto.getStatusConfirmDate() != null) {
								text = text.replace("day_left",
										dateFormatDay.format(forestProductsListDto.getStatusConfirmDate()));
								r.setText(text, 0);
							} else if (text != null && text.contains("day_left")) {
								text = text.replace("day_left", "........");
								r.setText(text, 0);
							}
							if (text != null && text.contains("month_left")
									&& forestProductsListDto.getStatusConfirmDate() != null) {
								text = text.replace("month_left",
										dateFormatMonth.format(forestProductsListDto.getStatusConfirmDate()));
								r.setText(text, 0);
							} else if (text != null && text.contains("month_left")) {
								text = text.replace("month_left", "........");
								r.setText(text, 0);
							}
							if (text != null && text.contains("year_left")
									&& forestProductsListDto.getStatusConfirmDate() != null) {
								text = text.replace("year_left",
										dateFormatYear.format(forestProductsListDto.getStatusConfirmDate()));
								r.setText(text, 0);
							} else if (text != null && text.contains("year_left")) {
								text = text.replace("year_left", "........");
								r.setText(text, 0);
							}

						}
					}

					row = table3.getRow(0);
					cell = row.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("XN_")
									&& forestProductsListDto.getAddressCreatedFpl() != null) {
								text = text.replace("XN_", forestProductsListDto.getAddressCreatedFpl());
								r.setText(text, 0);
							} else {
								text = text.replace("XN_", "........");
								r.setText(text, 0);
							}
							if (text != null && text.contains("d_") && forestProductsListDto.getDateIssue() != null) {
								text = text.replace("d_", dateFormatDay.format(forestProductsListDto.getDateIssue()));
								r.setText(text, 0);
							} else if (text != null && text.contains("d_")) {
								text = text.replace("d_", "........");
								r.setText(text, 0);
							}
							if (text != null && text.contains("m_") && forestProductsListDto.getDateIssue() != null) {
								text = text.replace("m_", dateFormatMonth.format(forestProductsListDto.getDateIssue()));
								r.setText(text, 0);
							} else if (text != null && text.contains("m_")) {
								text = text.replace("m_", "........");
								r.setText(text, 0);
							}
							if (text != null && text.contains("year_")
									&& forestProductsListDto.getDateIssue() != null) {
								text = text.replace("year_",
										dateFormatYear.format(forestProductsListDto.getDateIssue()));
								r.setText(text, 0);
							} else if (text != null && text.contains("year_")) {
								text = text.replace("year_", "........");
								r.setText(text, 0);
							}
						}
					}
					row = table3.getRow(1);
					cell = row.getTableCells().get(0);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("So_BKLS") && forestProductsListDto.getCode() != null) {
								text = text.replace("So_BKLS", forestProductsListDto.getCode());
								r.setText(text, 0);
							}
						}
					}
					row = table3.getRow(2);
					cell = row.getTableCells().get(0);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("CK_Trai")
									&& forestProductsListDto.getSignatureName() != null) {
								text = text.replace("CK_Trai", forestProductsListDto.getSignatureName());
								r.setText(text, 0);
							} else {
								r.setText("", 0);
							}
						}
					}
					row = table3.getRow(2);
					cell = row.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("CK_Phai")
									&& forestProductsListDto.getOrganizationCreatedFplName() != null) {
								text = text.replace("CK_Phai", forestProductsListDto.getOrganizationCreatedFplName());
								r.setText(text, 0);
							} else {
								r.setText("", 0);
							}

						}
					}

				}

				XWPFWordExtractor extractor = new XWPFWordExtractor(document);
				List<XWPFParagraph> paragraphs = document.getParagraphs();
				if (paragraphs != null && paragraphs.size() > 0) {
					for (XWPFParagraph paragraph : paragraphs) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("Tong_So_Luong")) {
								text = text.replace("Tong_So_Luong", detailsAnimal + ".");
								r.setText(text, 0);
							}
							if (text != null && text.contains("So_BKLS") && forestProductsListDto.getCode() != null) {
								text = text.replace("So_BKLS", forestProductsListDto.getCode());
								r.setText(text, 0);
							}

//							if(text != null && text.contains("Tong_SoTo")) {
//								text = text.replace("Tong_SoTo", String.valueOf(pageCount));
//								r.setText(text, 0);
//							}
						}
					}
				}

				for (XWPFFooter footer : document.getFooterList()) {
					for (XWPFParagraph paragraph : footer.getParagraphs()) {
						for (XWPFRun run : paragraph.getRuns()) {
							String text = run.text();
							if (text != null && text.contains("Bang_Ke")
									&& forestProductsListDto.getAdministrativeUnitFrom() != null) {

								String bangKe = forestProductsListDto.getAdministrativeUnitFrom().getName() + " - "
										+ forestProductsListDto.getAdministrativeUnitFrom().getParentDto().getName();
								text = text.replace("Bang_Ke", bangKe);
								run.setText(text, 0);
							}
							if (text != null && text.contains("E_Footer")
									&& forestProductsListDto.getConfirmByUser() != null) {
								String efooter = "";
								String email = forestProductsListDto.getConfirmByUser().getEmail();
								if (forestProductsListDto.getConfirmByUser().getPerson().getPhoneNumber() != null) {
									String phoneNumber = forestProductsListDto.getConfirmByUser().getPerson()
											.getPhoneNumber();
									efooter = email + " - " + phoneNumber;
								} else {
									efooter = email;
								}

								text = text.replace("E_Footer", efooter);
								run.setText(text, 0);
							}
						}
					}
				}
				XWPFHeader header = document.createHeader(HeaderFooterType.DEFAULT);
				XWPFParagraph paragraphHeader = document.createParagraph();
				paragraphHeader = header.getParagraphArray(1);
				if (paragraphHeader == null)
					paragraphHeader = header.createParagraph();
				paragraphHeader.setAlignment(ParagraphAlignment.RIGHT);
				XWPFRun runHeader = paragraphHeader.createRun();
				runHeader.setText("Tờ số ");
				paragraphHeader.getCTP().addNewFldSimple().setInstr("PAGE \\* MERGEFORMAT");
				runHeader = paragraphHeader.createRun();
				runHeader.setText(" / Tổng số tờ: ");
				paragraphHeader.getCTP().addNewFldSimple().setInstr("NUMPAGES \\* MERGEFORMAT");
				int pageCount2 = document.getProperties().getExtendedProperties().getUnderlyingProperties().getPages();

				// Print Page Count
				System.out.println(pageCount2);
//				    header = document.createHeader(HeaderFooterType.FIRST);
//					paragraph = header.createParagraph();
//					paragraph.setAlignment(ParagraphAlignment.RIGHT);
//					run = paragraph.createRun();  
//					run.setText("");
				document.write(response.getOutputStream());
//				document.close();

				response.setContentType("application/msword");
				response.setHeader("Content-Disposition", "attachment; filename=BangKeLamSan.docx");
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
				// TODO: handle exception
			}
		}
	}
	// tran huu dat tạo hàm xuất file word end

	@RequestMapping(value = "/downloadAnimalCertificate", method = { RequestMethod.POST })
	public void downloadanimalCertificate(WebRequest request, HttpServletResponse response,
			@RequestBody AnimalCertificateDto dto) {
		AnimalCertificateDto animalCertificateDto = null;
		if (dto.getId() != null && dto != null) {
			animalCertificateDto = animalCertificateService.getAnimalCertificateById(dto.getId());
		}
		if (animalCertificateDto != null) {
			Resource resource = resourceLoader.getResource("classpath:template/GiayChungNhanTraiNuoi_mau_in.docx");
			try (XWPFDocument document = new XWPFDocument(resource.getInputStream())) {
				XWPFTable table0 = document.getTableArray(0);
				if (table0 != null) {
					XWPFTableRow row = table0.getRow(0);
					XWPFTableCell cell = row.getTableCells().get(0);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("TINH_PTNT")
									&& animalCertificateDto.getOrganizationProvinceName() != null) {
								text = text.replace("TINH_PTNT", animalCertificateDto.getOrganizationProvinceName());
								text = text.toUpperCase();
								r.setText(text, 0);
							}

							if (text != null && text.contains("Co_Quan")
									&& animalCertificateDto.getOrganizationName() != null) {
								text = text.replace("Co_Quan", animalCertificateDto.getOrganizationName());
								r.setText(text, 0);
							}
						}
					}
				}


				String DateFormat = "dd/MM/YYYY";
				SimpleDateFormat dateFormat = new SimpleDateFormat(DateFormat);
				String strDateFormatYear = "YYYY";
				String strDateFormatDay = "dd";
				String strDateFormatMonth = "MM";
				SimpleDateFormat dateFormatYear = new SimpleDateFormat(strDateFormatYear);
				SimpleDateFormat dateFormatMonth = new SimpleDateFormat(strDateFormatMonth);
				SimpleDateFormat dateFormatDay = new SimpleDateFormat(strDateFormatDay);
				XWPFTable table1 = document.getTableArray(1);
				if (table1 != null) {
					XWPFTableRow oldRow = table1.getRow(0);
					XWPFTableCell cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (animalCertificateDto.getFarm() != null) {
							text = animalCertificateDto.getCode();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(1);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (animalCertificateDto.getFarm() != null) {
							text = animalCertificateDto.getFarm().getName();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(2);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (animalCertificateDto.getFarm() != null) {
							text = animalCertificateDto.getFarm().getAdministrativeUnit().getName() + " - "
									+ animalCertificateDto.getFarm().getDistrictName() + " - "
									+ animalCertificateDto.getFarm().getProvinceName();
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(3);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (animalCertificateDto.getFarm().getFoundingDate() != null) {
							text = dateFormat.format(animalCertificateDto.getFarm().getFoundingDate());
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(3);
					cell = oldRow.getTableCells().get(3);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (animalCertificateDto.getFarm().getDateRegistration() != null) {
							text = dateFormat.format(animalCertificateDto.getFarm().getDateRegistration());
						}
						run.setText(text, 0);
					}

					oldRow = table1.getRow(4);
					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						XWPFRun run = paragraph.getRuns().get(0);
						String text = "";
						if (animalCertificateDto.getContentProvided() != null) {
							text = animalCertificateDto.getContentProvided();
						}
						run.setText(text, 0);
					}
				}

				XWPFTable table2 = document.getTableArray(2);
				String totalAnimal = "";
				String detailsAnimal = "";
				if (table2 != null) {
					XWPFTableRow oldRow = table2.getRow(1);

					int stt = 1;
					if (animalCertificateDto.getDetails() != null) {
						for (AnimalCertificateDetailDto element : animalCertificateDto.getDetails()) {
							totalAnimal = "";
							detailsAnimal = "";
							if (element.getTotal() != null) {
								totalAnimal += element.getTotal() + " cá thể ";
							} else {
								totalAnimal += "0 cá thể ";
							}

							if (element.getMale() != null) {
								if (element.getMale() > 0) {
									detailsAnimal += String.valueOf(element.getMale()) + " cá thể đực, ";
								}
							}
							if (element.getFemale() != null) {
								if (element.getFemale() > 0) {
									detailsAnimal += String.valueOf(element.getFemale()) + " cá thể cái, ";
								}

							}
							if (element.getUnGender() != null) {
								if (element.getUnGender() > 0) {
									detailsAnimal += String.valueOf(element.getUnGender())
											+ " cá thể không rõ giới tính";
								}
							}

							CTRow ctrow = CTRow.Factory.parse(oldRow.getCtRow().newInputStream());
							XWPFTableRow newRow = new XWPFTableRow(ctrow, table2);

							XWPFTableCell cell = newRow.getTableCells().get(0);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								run.setText(String.valueOf(stt), 0);
							}
							cell = newRow.getTableCells().get(1);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								List<XWPFRun> runs = paragraph.getRuns();
								for (XWPFRun r : runs) {
									String text = r.getText(0);
									if (text != null && text.contains("L_N") && element.getAnimal().getName() != null) {
										text = text.replace("L_N", element.getAnimal().getName());
										r.setText(text, 0);
									}
									if (text != null && text.contains("Text_2")
											&& element.getAnimal().getName() != null) {
										text = text.replace("Text_2", element.getAnimal().getScienceName());
										r.setText(text, 0);
									}
								}
							}
							cell = newRow.getTableCells().get(2);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								List<XWPFRun> runs = paragraph.getRuns();
								for (XWPFRun r : runs) {
									String text = r.getText(0);
									if (text != null && text.contains("Text")) {
										text = text.replace("Text", totalAnimal);
										r.setText(text, 0);
									}
									if (text != null && text.contains("Te_2")) {
										text = text.replace("Te_2", detailsAnimal);
										r.setText(text, 0);
									}
//									System.out.print(text.contains("Te_2"));
								}
							}

							cell = newRow.getTableCells().get(3);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								if (element.getOriginal() != null) {
									run.setText(element.getOriginal(), 0);
								} else {
									run.setText("", 0);
								}
							}
							cell = newRow.getTableCells().get(4);
							for (XWPFParagraph paragraph : cell.getParagraphs()) {
								XWPFRun run = paragraph.getRuns().get(0);
								if (element.getOriginal() != null) {
									run.setText(element.getNote(), 0);
								} else {
									run.setText("", 0);
								}
							}
							table2.addRow(newRow);
							stt++;
						}
					}
					table2.removeRow(1);
				}

				XWPFTable table3 = document.getTableArray(3);
				if (table3 != null) {
					XWPFTableRow oldRow = table3.getRow(0);
					XWPFTableCell cell = oldRow.getTableCells().get(0);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("Noi_nh1")
									&& animalCertificateDto.getRecipientFirst() != null
									&& !animalCertificateDto.getRecipientFirst().equals("")) {
								text = text.replace("Noi_nh1", "-" + animalCertificateDto.getRecipientFirst());
								r.setText(text, 0);
							} else if (text != null && text.contains("Noi_nh1")
									&& (animalCertificateDto.getRecipientFirst() == null
											|| animalCertificateDto.getRecipientFirst().equals(""))) {
								text = text.replace("Noi_nh1", "");
								r.setText(text, 0);
							}
							if (text != null && text.contains("Noi_nh2")
									&& animalCertificateDto.getRecipientSecond() != null
									&& !animalCertificateDto.getRecipientSecond().equals("")) {
								text = text.replace("Noi_nh2", "-" + animalCertificateDto.getRecipientSecond());
								r.setText(text, 0);
							} else if (text != null && text.contains("Noi_nh2")
									&& (animalCertificateDto.getRecipientSecond() == null
											|| animalCertificateDto.getRecipientSecond().equals(""))) {
								text = text.replace("Noi_nh2", "");
								r.setText(text, 0);
							}
							if (text != null && text.contains("Noi_nh3")
									&& animalCertificateDto.getRecipientThird() != null
									&& !animalCertificateDto.getRecipientThird().equals("")) {
								text = text.replace("Noi_nh3", "-" + animalCertificateDto.getRecipientThird());
								r.setText(text, 0);
							} else if (text != null && text.contains("Noi_nh3")
									&& (animalCertificateDto.getRecipientThird() == null
											|| animalCertificateDto.getRecipientThird().equals(""))) {
								text = text.replace("Noi_nh3", "");
								r.setText(text, 0);
							}
							Boolean isRecipientFourthNull = false; // nếu người dùng không nhập ô nơi nhận khác
							if(animalCertificateDto.getRecipientOther() == null || animalCertificateDto.getRecipientOther().equals("")) {
								isRecipientFourthNull = true;
							}
							if (text != null && text.contains("Noi_nh4")
									&& animalCertificateDto.getRecipientFourth() != null
									&& !animalCertificateDto.getRecipientFourth().equals("")) {
								text = text.replace("Noi_nh4", isRecipientFourthNull == true ? "": "-" + animalCertificateDto.getRecipientFourth());
								r.setText(text, 0);
							} else if (text != null && text.contains("Noi_nh4")
									&& (animalCertificateDto.getRecipientFourth() == null
											|| animalCertificateDto.getRecipientFourth().equals(""))) {
								text = text.replace("Noi_nh4", "");
								r.setText(text, 0);
							}
							if (text != null && text.contains("Noi_nh5")
									&& animalCertificateDto.getRecipientOther() != null
									&& !animalCertificateDto.getRecipientOther().equals("")) {
								text = text.replace("Noi_nh5","-" + animalCertificateDto.getRecipientOther());
								r.setText(text, 0);
							} else if (text != null && text.contains("Noi_nh5")
									&& (animalCertificateDto.getRecipientOther() == null
											|| animalCertificateDto.getRecipientOther().equals(""))) {
								text = text.replace("Noi_nh5",animalCertificateDto.getRecipientFourth() != null ? "-" + animalCertificateDto.getRecipientFourth() : "");
								r.setText(text, 0);
							}
						}

					}

					cell = oldRow.getTableCells().get(1);
					for (XWPFParagraph paragraph : cell.getParagraphs()) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("CCP_CCT")
									&& animalCertificateDto.getSignerName() != null) {
								text = text.replace("CCP_CCT", animalCertificateDto.getSignerName());
								r.setText(text, 0);
							}else if(text != null && text.contains("CCP_CCT")
									&& (animalCertificateDto.getSignerName() == null || animalCertificateDto.getSignerName() == "")) {
								text = text.replace("CCP_CCT", "");
								r.setText(text, 0);
							}
							if (text != null && text.contains("K/t")
									&& animalCertificateDto.getTypeSigner().equals("Chi cục trưởng")) {
								text = text.replace("K/t", "");
								r.setText(text, 0);
							} else if (text != null && text.contains("K/t")
									&& !animalCertificateDto.getTypeSigner().equals("Chi cục trưởng")) {
								text = text.replace("K/t", "KT.");
								r.setText(text, 0);
							}
							if (text != null && text.contains("Pho_CCT")
									&& animalCertificateDto.getTypeSigner().equals("Chi cục trưởng")) {
								text = text.replace("Pho_CCT", "");
								r.setText(text, 0);
							} else if (text != null && text.contains("Pho_CCT")
									&& animalCertificateDto.getTypeSigner().equals("Phó chi cục trưởng")) {
								text = text.replace("Pho_CCT", "PHÓ CHI CỤC TRƯỞNG");
								r.setText(text, 0);
							}
						}
					}
				}

				XWPFWordExtractor extractor = new XWPFWordExtractor(document);
				List<XWPFParagraph> paragraphs = document.getParagraphs();
				if (paragraphs != null && paragraphs.size() > 0) {
					for (XWPFParagraph paragraph : paragraphs) {
						List<XWPFRun> runs = paragraph.getRuns();
						for (XWPFRun r : runs) {
							String text = r.getText(0);
							if (text != null && text.contains("T_XN")
									&& animalCertificateDto.getProvinceName() != null) {
								Boolean isTP = false;
								String tamp = animalCertificateDto.getProvinceName().toLowerCase();
								if(tamp.equals("hà nội") 
										|| tamp.equals("hải phòng")
										|| tamp.equals("đà nẵng")
										|| tamp.equals("hồ chí minh") 
										|| tamp.equals("cần thơ")) {
									isTP = true;
								}
								String nameProvince = "Chi cục kiểm lâm " + (isTP?"Thành Phố ":"Tỉnh ") + animalCertificateDto.getProvinceName();
//								text = text.replace("T_XN", animalCertificateDto.getFarm().getAdministrativeUnit()
//										.getParentDto().getParentDto().getName().toUpperCase());
								text = text.replace("T_XN", nameProvince.toUpperCase());
								r.setText(text, 0);
							} else if (text != null && text.contains("T_XN")
									&& animalCertificateDto.getRecipientThird() == null) {
								text = text.replace("T_XN", "...");
								r.setText(text, 0);
							}
							if (text != null && text.contains("d_t")
									&& animalCertificateDto.getFarm().getDateRegistration() != null) {
								text = text.replace("d_t",
										dateFormatDay.format(animalCertificateDto.getFarm().getDateRegistration()));
								r.setText(text, 0);
							} else if (text != null && text.contains("d_t")) {
								text = text.replace("d_t", "...");
								r.setText(text, 0);
							}
							if (text != null && text.contains("m_t")
									&& animalCertificateDto.getFarm().getDateRegistration() != null) {
								String tam = dateFormatMonth
										.format(animalCertificateDto.getFarm().getDateRegistration());
								text = text.replace("m_t", tam);
								r.setText(text, 0);
							} else if (text != null && text.contains("m_t")) {
								text = text.replace("m_t", "...");
								r.setText(text, 0);
							}
							if (text != null && text.contains("y_t")
									&& animalCertificateDto.getFarm().getDateRegistration() != null) {
								text = text.replace("y_t",
										dateFormatYear.format(animalCertificateDto.getFarm().getDateRegistration()));
								r.setText(text, 0);
							} else if (text != null && text.contains("y_t")) {
								text = text.replace("y_t", "...");
								r.setText(text, 0);
							}

							if (text != null && text.contains("Tinh_XN")
									&& animalCertificateDto.getProvinceName() != null) {
								text = text.replace("Tinh_XN", animalCertificateDto.getProvinceName());
								r.setText(text, 0);
							} else if (text != null && text.contains("Tinh_XN")
									&& (animalCertificateDto.getProvinceName() == null
											|| animalCertificateDto.getProvinceName().equals(""))) {
								text = text.replace("Tinh_XN", "...");
								r.setText(text, 0);
							}
						}
					}
				}

				document.write(response.getOutputStream());

				response.setContentType("application/msword");
				response.setHeader("Content-Disposition", "attachment; filename=GiayChungNhanTraiNuoi.docx");
				response.flushBuffer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	@RequestMapping(value = "/downloadFarm", method = { RequestMethod.POST })
	public void downloadFarm(WebRequest request, HttpServletResponse response, @RequestBody FarmSearchDto dto) {
		List<FarmDto> list = new ArrayList<FarmDto>();
		String title = "Danh sách cơ sở nuôi động vật hoang dã";
		if (dto.getProvince() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvince());
			if (pronvince != null && pronvince.getName() != null) {
				title += " " + pronvince.getName();
				if (dto.getDistrict() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrict());
					if (district != null && district.getName() != null) {
						title += ", " + district.getName();
						if (dto.getWard() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWard());
							if (commune != null && commune.getName() != null) {
								title += ", " + commune.getName();
							}
						}
					}
				}
			}
		}
		list = farmService.searchFarmDto(dto);
		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet(title);
			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);

			HSSFFont fontHeader = workbook.createFont();
			fontHeader.setFontHeightInPoints((short) 12);
			fontHeader.setColor(HSSFFont.COLOR_NORMAL);
			fontHeader.setFontName("Arial");
			fontHeader.setBold(true);
			headerCellStyle.setBorderTop(BorderStyle.THIN);
			headerCellStyle.setBorderBottom(BorderStyle.THIN);
			headerCellStyle.setBorderRight(BorderStyle.THIN);
			headerCellStyle.setBorderLeft(BorderStyle.THIN);
			headerCellStyle.setFont(fontHeader);
			headerCellStyle.setWrapText(false);

			int rowCount = 1;
			int columnCount = 0;
			HSSFRow row;
			HSSFCell cell;

			// for header row
			row = worksheet.createRow(rowCount);
			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("STT");
			worksheet.setColumnWidth(columnCount - 1, 6 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tỉnh/Thành phố");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Quận/Huyện");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Phường/Xã");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Thôn/Ấp");
			worksheet.setColumnWidth(columnCount - 1, 25 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Đường phố/Ngõ/Xóm");
			worksheet.setColumnWidth(columnCount - 1, 40 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số nhà");
			worksheet.setColumnWidth(columnCount - 1, 40 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mã hệ thống");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mã tạm");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tên cơ sở");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mã số CS (theo 06/2019)");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày cấp mã (06/2019)");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mã số khác/ số giấy đăng ký");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày cấp");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Giấy đăng ký kinh doanh");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mục đích nuôi");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Hình thức nuôi");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tình trạng đăng ký");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Lý do chưa đăng ký");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày đăng ký gần nhất");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày ngưng hoạt động");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày thành lập");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Trạng thái");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số điện thoại cơ sở");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày bắt đầu nuôi");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Người đại diện");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Năm sinh");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số CMND/Căn cước CD/ Hộ chiếu");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày cấp số CMND/CCCD/Hộ chiếu");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Nơi cấp số CMND/CCCD/Hộ chiếu");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số điện thoại");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Email");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Kinh độ");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Vĩ độ");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Diện tích truồng trại");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			// title row
			int titleRow = 0;
			row = worksheet.createRow(titleRow++);
			HSSFCellStyle style0 = workbook.createCellStyle();
			HSSFFont font0 = workbook.createFont();
			font0.setFontHeightInPoints((short) 13);
			// font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style0.setFont(font0);
			style0.setAlignment(HorizontalAlignment.CENTER);
			cell = row.createCell(0);
			cell.setCellStyle(style0);
			cell.setCellValue(title.toUpperCase());
			worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));

			HSSFCellStyle dateCellStyle = workbook.createCellStyle();
			short df = workbook.createDataFormat().getFormat("dd/MM/yyyy");
			dateCellStyle.setDataFormat(df);
			dateCellStyle.setBorderTop(BorderStyle.THIN);
			dateCellStyle.setBorderBottom(BorderStyle.THIN);
			dateCellStyle.setBorderRight(BorderStyle.THIN);
			dateCellStyle.setBorderLeft(BorderStyle.THIN);
			HSSFCellStyle normalCellStyle = workbook.createCellStyle();
			normalCellStyle.setBorderTop(BorderStyle.THIN);
			normalCellStyle.setBorderBottom(BorderStyle.THIN);
			normalCellStyle.setBorderRight(BorderStyle.THIN);
			normalCellStyle.setBorderLeft(BorderStyle.THIN);
			normalCellStyle.setWrapText(true);
			if (list != null && list.size() > 0) {
				for (FarmDto s : list) {

					rowCount++;
					columnCount = 0;

					row = worksheet.createRow(rowCount);
					// số thứ tự
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(rowCount - titleRow);

					// city
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getProvinceName() != null)
						cell.setCellValue(s.getProvinceName());
					// district
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getDistrictName() != null)
						cell.setCellValue(s.getDistrictName());

					// wards
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getWardsName() != null)
						cell.setCellValue(s.getWardsName());

					// Thôn ấp
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getVillage() != null)
						cell.setCellValue(s.getVillage());

					// đường phố/ ngõ xóm
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getAdressDetail() != null)
						cell.setCellValue(s.getAdressDetail());

					// số nhà
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getApartmentNumber() != null)
						cell.setCellValue(s.getApartmentNumber());

					// mã định danh
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getCode());

					// mã tạm
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getMapCode());

					// Tên cơ sở
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getName());

					// mã số CS(theo 06/2019)
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getNewRegistrationCode() != null && s.getNewRegistrationCode().equalsIgnoreCase("") == false)
						cell.setCellValue(s.getNewRegistrationCode());
					else
						cell.setCellValue("");

					// Ngày cấp mã (06/2019)
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getDateRegistration() != null)
						// cell.setCellValue(s.getIssuingCodeDate());
						cell.setCellValue(s.getDateRegistration());
					else
						cell.setCellValue("");

					// mã khác/ số giấy đăng ký
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getOldRegistrationCode() != null && s.getOldRegistrationCode().equalsIgnoreCase("") == false)
						cell.setCellValue(s.getOldRegistrationCode());
					else
						cell.setCellValue("");

					// Ngày cấp (mã khác)
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getDateOtherRegistration() != null)
						cell.setCellValue(s.getDateOtherRegistration());
					else
						cell.setCellValue("");

					// Giấy đăng ký kinh doanh
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellType(cell.CELL_TYPE_STRING);
					if (s.getBusinessRegistrationNumber() != null) {
						cell.setCellValue(s.getBusinessRegistrationNumber());
					} else {
						cell.setCellValue("");
					}
					// mục đích gây nuôi
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getFarmProductTargets() != null && s.getFarmProductTargets().size() > 0) {
						String productTarrget = "";
						for (ProductTargetDto fpt : s.getFarmProductTargets()) {
							if (productTarrget.length() > 0) {
								productTarrget += ", " + fpt.getName();
							} else {
								productTarrget += fpt.getName();
							}
						}
						cell.setCellValue(productTarrget);
					} else
						cell.setCellValue("");
					// Hình thức nuôi
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getFarmHusbandryMethods() != null && s.getFarmHusbandryMethods().size() > 0) {
						String farmHusb = "";
						for (FarmHusbandryMethodDto farmhusb : s.getFarmHusbandryMethods()) {
							if (farmHusb.length() > 0) {
								if (farmhusb.getHusbandryMethod() != null
										&& farmhusb.getHusbandryMethod().getId() != null) {
									if (farmhusb.getHusbandryMethod().getId() == 1) {
										farmHusb += ", Sinh trưởng";
									}
									if (farmhusb.getHusbandryMethod().getId() == 2) {
										farmHusb += ", Sinh sản";
									}
									if (farmhusb.getHusbandryMethod().getId() == 3) {
										farmHusb += ", Sinh sản - Sinh trưởng";
									}

								}
							} else {
								if (farmhusb.getHusbandryMethod() != null
										&& farmhusb.getHusbandryMethod().getId() != null) {
									if (farmhusb.getHusbandryMethod().getId() == 1) {
										farmHusb += "Sinh trưởng";
									}
									if (farmhusb.getHusbandryMethod().getId() == 2) {
										farmHusb += "Sinh sản";
									}
									if (farmhusb.getHusbandryMethod().getId() == 3) {
										farmHusb += "Sinh sản - Sinh trưởng";
									}

								}
							}
						}
						cell.setCellValue(farmHusb);
					}
//					if (s.getMethodFeed() != null && s.getMethodFeed() == 1)
//						cell.setCellValue("Nuôi sinh trưởng");
//					else if (s.getMethodFeed() != null && s.getMethodFeed() == 2)
//						cell.setCellValue("Nuôi sinh sản");
//					else if (s.getMethodFeed() != null && s.getMethodFeed() == 3)
//						cell.setCellValue("Nuôi khác");
//					else
//						cell.setCellValue("");

					// Tình trạng đăng ký
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getStatus() != null && s.getStatus() == 2)
						cell.setCellValue("Chưa đăng ký");
					else if (s.getStatus() != null && (s.getStatus() == 3 || s.getStatus() == 1))
						cell.setCellValue("Đã đăng ký");
					else
						cell.setCellValue("");

					// Lý do chưa đăng ký
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getReasonNotYetRegister() != null)
						cell.setCellValue(s.getReasonNotYetRegister());
					else
						cell.setCellValue("");

					// Ngày đăng ký gần nhất
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getBusinessRegistrationDate() != null)
						cell.setCellValue(s.getBusinessRegistrationDate());
					else
						cell.setCellValue("");

					// Ngày ngưng hoạt động
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getStopDate() != null)
						cell.setCellValue(s.getStopDate());
					else
						cell.setCellValue("");

					// Ngày thành lập
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getFoundingDate() != null)
						cell.setCellValue(s.getFoundingDate());
					else
						cell.setCellValue("");

					// Trạng thái hoạt động
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getStatusFarm() != null && s.getStatusFarm() == 0)
						cell.setCellValue("Đang hoạt động");
					else if (s.getStatusFarm() != null && s.getStatusFarm() == 1)
						cell.setCellValue("Ngưng hoạt động");
					else
						cell.setCellValue("");

					// số điện thoại cơ sở
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellType(cell.CELL_TYPE_STRING);
					if (s.getPhoneNumber() != null) {
						cell.setCellValue(s.getPhoneNumber());
					} else {
						cell.setCellValue("");
					}

					// ngày bắt đầu nuôi
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getFoundingDate() != null)
						cell.setCellValue(s.getFoundingDate());
					else
						cell.setCellValue("");

					// người đại diện
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getOwnerName() != null)
						cell.setCellValue(s.getOwnerName());
					else
						cell.setCellValue("");
					// năm sinh
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getOwnerDob() != null)
						cell.setCellValue(s.getOwnerDob());
					else
						cell.setCellValue("");
					// Số CMND/Căn cước CD/ Hộ chiếu
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getOwnerCitizenCode() != null)
						cell.setCellValue(s.getOwnerCitizenCode());
					else
						cell.setCellValue("");

					// ngày cấp
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getOwnerCitizenDate() != null)
						cell.setCellValue(s.getOwnerCitizenDate());
					else
						cell.setCellValue("");

					// nơi cấp
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getOwnerCitizenLocation() != null)
						cell.setCellValue(s.getOwnerCitizenLocation());
					else
						cell.setCellValue("");

					// sdt
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellType(cell.CELL_TYPE_STRING);
					if (s.getOwnerPhoneNumber() != null)
						cell.setCellValue(s.getOwnerPhoneNumber());
					else
						cell.setCellValue("");
					// email
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getOwnerEmail() != null)
						cell.setCellValue(s.getOwnerEmail());
					else
						cell.setCellValue("");

					// kinh do
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getLongitude() != null)
						cell.setCellValue(s.getLongitude());
					else
						cell.setCellValue("");
					// vi do
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getLatitude() != null)
						cell.setCellValue(s.getLatitude());
					else
						cell.setCellValue("");

					// diện tích truồng trại
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getLodgingAcreage() != null) {
						cell.setCellValue(s.getLodgingAcreage());
					} else {
						cell.setCellValue("");
					}
				}
			}

			workbook.write(response.getOutputStream());

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DanhSachCoSoNuoiDVHD.xls");
			response.flushBuffer();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	@RequestMapping(value = "/downloadImportAnimal", method = { RequestMethod.POST })
	public void downloadImportAnimal(WebRequest request, HttpServletResponse response,
			@RequestBody ImportExportAnimalSearchDto dto) {
		List<ImportExportAnimalDto> list = new ArrayList<ImportExportAnimalDto>();
		String title = "Danh sách lần nhập đàn";
		list = importExportAnimalService.searchDto(dto);

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet(title);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);

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

			int rowCount = 1;// ????
			int columnCount = 0;
			HSSFRow row;
			HSSFCell cell;

			// for header row
			row = worksheet.createRow(rowCount);
			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("STT");
			worksheet.setColumnWidth(columnCount - 1, 6 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mã lô");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mã định danh");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tên cơ sở chăn nuôi");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tên vật nuôi");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Hướng sản phẩm");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày nhập");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số lượng(con)");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số lượng còn lại(con)");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày tuổi");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số ngày dự kiến nuôi");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tên NCC");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Địa chỉ NCC");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Diễn giải");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số điện thoại");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			// title row
			int titleRow = 0;
			row = worksheet.createRow(titleRow++);
			HSSFCellStyle style0 = workbook.createCellStyle();
			HSSFFont font0 = workbook.createFont();
			font0.setFontHeightInPoints((short) 13);
			// font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style0.setFont(font0);
			// style0.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cell = row.createCell(0);
			cell.setCellStyle(style0);
			cell.setCellValue(title.toUpperCase());
			worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));

			HSSFCellStyle dateCellStyle = workbook.createCellStyle();
			short df = workbook.createDataFormat().getFormat("dd/MM/yyyy");
			dateCellStyle.setDataFormat(df);
			dateCellStyle.setBorderTop(BorderStyle.THIN);
			dateCellStyle.setBorderBottom(BorderStyle.THIN);
			dateCellStyle.setBorderRight(BorderStyle.THIN);
			dateCellStyle.setBorderLeft(BorderStyle.THIN);
			HSSFCellStyle normalCellStyle = workbook.createCellStyle();
			normalCellStyle.setBorderTop(BorderStyle.THIN);
			normalCellStyle.setBorderBottom(BorderStyle.THIN);
			normalCellStyle.setBorderRight(BorderStyle.THIN);
			normalCellStyle.setBorderLeft(BorderStyle.THIN);

			if (list != null && list.size() > 0) {
				for (ImportExportAnimalDto s : list) {

					rowCount++;
					columnCount = 0;

					row = worksheet.createRow(rowCount);
					// stt
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(rowCount - titleRow);

					// bath-code
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getBatchCode());

					// code
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getFarm() != null && s.getFarm().getCode() != null)
						cell.setCellValue(s.getFarm().getCode());

					// name
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getFarm() != null && s.getFarm().getName() != null)
						cell.setCellValue(s.getFarm().getName());

					// animal
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getAnimal() != null && s.getAnimal().getName() != null)
						cell.setCellValue(s.getAnimal().getName());
					// product
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getProductTarget() != null && s.getProductTarget().getName() != null)
						cell.setCellValue(s.getProductTarget().getName());

					// date
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getDateImport() != null)
						cell.setCellValue(s.getDateImport());
					// quantity
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getQuantity());
					// re-quantity
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getRemainQuantity());

					// DayOld
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getDayOld());

					// LifeCycle
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getLifeCycle());

					// supplierName
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getSupplierName());

					// supplierAdress
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getSupplierAdress());

					// description
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getDescription());

					// số điện thoại
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getFarm() != null && s.getFarm().getOwnerPhoneNumber() != null)
						cell.setCellValue(s.getFarm().getOwnerPhoneNumber());
				}
			}

			workbook.write(response.getOutputStream());

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DanhSachLanNhapDan.xls");
			response.flushBuffer();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	@RequestMapping(value = "/downloadExportAnimal", method = { RequestMethod.POST })
	public void downloadExportAnimal(WebRequest request, HttpServletResponse response,
			@RequestBody ImportExportAnimalSearchDto dto) {
		List<ImportExportAnimalDto> list = new ArrayList<ImportExportAnimalDto>();
		String title = "Danh sách lần xuất đàn";
		list = importExportAnimalService.searchDto(dto);

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet(title);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);

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

			int rowCount = 1;// ????
			int columnCount = 0;
			HSSFRow row;
			HSSFCell cell;

			// for header row
			row = worksheet.createRow(rowCount);
			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("STT");
			worksheet.setColumnWidth(columnCount - 1, 6 * 256);

			// cell = row.createCell(columnCount++);
			// cell.setCellStyle(headerCellStyle);
			// cell.setCellValue("Số chứng từ");
			// worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mã lô");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mã định danh");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tên cơ sở chăn nuôi");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số điện thoại");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tên vật nuôi");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Hướng sản phẩm");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Ngày xuất");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Lý do xuất");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Lý do xuất khác");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số lượng(con)");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Khối lượng");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Người mua");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tỉnh tiêu thụ");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Diễn giải");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			// title row
			int titleRow = 0;
			row = worksheet.createRow(titleRow++);
			HSSFCellStyle style0 = workbook.createCellStyle();
			HSSFFont font0 = workbook.createFont();
			font0.setFontHeightInPoints((short) 13);
			// font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style0.setFont(font0);
			// style0.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cell = row.createCell(0);
			cell.setCellStyle(style0);
			cell.setCellValue(title.toUpperCase());
			worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));

			HSSFCellStyle dateCellStyle = workbook.createCellStyle();
			short df = workbook.createDataFormat().getFormat("dd/MM/yyyy");
			dateCellStyle.setDataFormat(df);
			dateCellStyle.setBorderTop(BorderStyle.THIN);
			dateCellStyle.setBorderBottom(BorderStyle.THIN);
			dateCellStyle.setBorderRight(BorderStyle.THIN);
			dateCellStyle.setBorderLeft(BorderStyle.THIN);
			HSSFCellStyle normalCellStyle = workbook.createCellStyle();
			normalCellStyle.setBorderTop(BorderStyle.THIN);
			normalCellStyle.setBorderBottom(BorderStyle.THIN);
			normalCellStyle.setBorderRight(BorderStyle.THIN);
			normalCellStyle.setBorderLeft(BorderStyle.THIN);

			if (list != null && list.size() > 0) {
				for (ImportExportAnimalDto s : list) {

					rowCount++;
					columnCount = 0;

					row = worksheet.createRow(rowCount);
					// stt
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(rowCount - titleRow);

					// VoucherCode
					// cell = row.createCell(columnCount++);
					// cell.setCellStyle(normalCellStyle);
					// cell.setCellValue(s.getVoucherCode());

					// bath-code
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getBatchCode());

					// code
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getFarm() != null && s.getFarm().getCode() != null)
						cell.setCellValue(s.getFarm().getCode());

					// name
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getFarm() != null && s.getFarm().getName() != null)
						cell.setCellValue(s.getFarm().getName());

					// số điện thoại
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getFarm() != null && s.getFarm().getOwnerPhoneNumber() != null)
						cell.setCellValue(s.getFarm().getOwnerPhoneNumber());

					// animal
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getAnimal() != null && s.getAnimal().getName() != null)
						cell.setCellValue(s.getAnimal().getName());
					// product
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getProductTarget() != null && s.getProductTarget().getName() != null)
						cell.setCellValue(s.getProductTarget().getName());

					// date
					cell = row.createCell(columnCount++);
					cell.setCellStyle(dateCellStyle);
					if (s.getDateExport() != null)
						cell.setCellValue(s.getDateExport());
					// ex_type
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					String reason = "";
					if (s.getExportType() == 1) {
						reason = "Xuất bán";
					} else if (s.getExportType() == 2) {
						reason = "Xuất khác";
					} else if (s.getExportType() == 3) {
						reason = "Điều chuyển loại";
					}
					cell.setCellValue(reason);
					// ex_reason
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getExportReason());
					// quantity
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getQuantity());
					// amount
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getAmount());

					// buyerName
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getBuyerName());

					// provincial
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					if (s.getProvincial() != null && s.getProvincial().getName() != null)
						cell.setCellValue(s.getProvincial().getName());

					// description
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getDescription());
				}
			}

			workbook.write(response.getOutputStream());

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DanhSachLanNhapDan.xls");
			response.flushBuffer();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	@RequestMapping(value = "/downloadListDistrict/{code}/{id}/{level}", method = { RequestMethod.POST })
	public void downloadListDistrictByProvince(WebRequest request, HttpServletResponse response,
			@PathVariable("code") String code, @PathVariable("id") Long id, @PathVariable("level") Integer level) {
		List<FarmAdministrativeUnitDto> list = new ArrayList<FarmAdministrativeUnitDto>();
		String title = "Số lượng gia cầm ";
		if (level != null && level == 2) {
			title = title + "của các huyện thuộc tỉnh ";
		} else if (level != null && level == 3) {
			title = title + "của các xã thuộc huyện ";
		}
		if (code != null) {
			List<FmsAdministrativeUnit> aus = fmsAdministrativeUnitRepository.findListByMapCode(code);
			if (aus != null && aus.size() > 0) {
				title = title + aus.get(0).getName();
			}
		} else if (id != null && id > 0L) {
			FmsAdministrativeUnit au = fmsAdministrativeUnitRepository.findById(id);
			if (au != null)
				title = title + au.getName();
		}
		if (code != null) {
			list = farmService.countFarmListAdministrativeUnitByMapCode(code, level);
		} else if (id != null) {
			list = farmService.countFarmListAdministrativeUnitById(id, level);
		}

		try {
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet worksheet = workbook.createSheet(title);

			HSSFCellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
			headerCellStyle.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);

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

			int rowCount = 1;// ????
			int columnCount = 0;
			HSSFRow row;
			HSSFCell cell;

			// for header row
			row = worksheet.createRow(rowCount);
			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("STT");
			worksheet.setColumnWidth(columnCount - 1, 6 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Mã");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Tên");
			worksheet.setColumnWidth(columnCount - 1, 30 * 256);

			cell = row.createCell(columnCount++);
			cell.setCellStyle(headerCellStyle);
			cell.setCellValue("Số lượng(con)");
			worksheet.setColumnWidth(columnCount - 1, 20 * 256);

			// title row
			int titleRow = 0;
			row = worksheet.createRow(titleRow++);
			HSSFCellStyle style0 = workbook.createCellStyle();
			HSSFFont font0 = workbook.createFont();
			font0.setFontHeightInPoints((short) 13);
			// font0.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			style0.setFont(font0);
			// style0.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			cell = row.createCell(0);
			cell.setCellStyle(style0);
			cell.setCellValue(title.toUpperCase());
			worksheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnCount - 1));

			HSSFCellStyle dateCellStyle = workbook.createCellStyle();
			short df = workbook.createDataFormat().getFormat("dd/MM/yyyy");
			dateCellStyle.setDataFormat(df);
			dateCellStyle.setBorderTop(BorderStyle.THIN);
			dateCellStyle.setBorderBottom(BorderStyle.THIN);
			dateCellStyle.setBorderRight(BorderStyle.THIN);
			dateCellStyle.setBorderLeft(BorderStyle.THIN);
			HSSFCellStyle normalCellStyle = workbook.createCellStyle();
			normalCellStyle.setBorderTop(BorderStyle.THIN);
			normalCellStyle.setBorderBottom(BorderStyle.THIN);
			normalCellStyle.setBorderRight(BorderStyle.THIN);
			normalCellStyle.setBorderLeft(BorderStyle.THIN);

			if (list != null && list.size() > 0) {
				for (FarmAdministrativeUnitDto s : list) {

					rowCount++;
					columnCount = 0;

					row = worksheet.createRow(rowCount);
					// stt
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(rowCount - titleRow);

					// Code
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getCodeAu());

					// name
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getNameAu());

					// quantity
					cell = row.createCell(columnCount++);
					cell.setCellStyle(normalCellStyle);
					cell.setCellValue(s.getQuantity());

				}
			}

			workbook.write(response.getOutputStream());

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=SoLuongGiaCam.xls");
			response.flushBuffer();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return;
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER" })
	@RequestMapping(value = "/uploadArticleImg", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<FileDescriptionDto> uploadAttachment(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException {
		FileDescriptionDto result = null;
		if (WLConstant.FolderPath == null) {
			WLConstant.FolderPath = env.getProperty("crm.file.folder");
		}

		String filePath = uploadfile.getOriginalFilename();
		filePath = WLConstant.FolderPath + filePath;
		FileOutputStream stream = new FileOutputStream(filePath);
		try {
			stream.write(uploadfile.getBytes());
		} finally {
			stream.close();
		}

		FileDescription file = new FileDescription();
		file.setContentSize(uploadfile.getSize());
		file.setContentType(uploadfile.getContentType());
		file.setName(uploadfile.getOriginalFilename());
		file.setFilePath(filePath);
		file = fileService.save(file);

		result = new FileDescriptionDto(file);

		return new ResponseEntity<FileDescriptionDto>(result, HttpStatus.OK);
	} // method upload Student

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH" })
	@RequestMapping(value = "/exportSearchUser", method = RequestMethod.POST)
	public void exportSearchUser(WebRequest request, HttpServletResponse response,
			@RequestBody UserUserAdministrativeUnitDto searchDto) {
		fmsUserService.exportSearchUser(request, response, searchDto);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH", "ROLE_DISTRICT", "ROLE_WARD", "ROLE_FAMER", "ROLE_USER" })
	@RequestMapping(value = "/exportDashboardView", method = RequestMethod.POST)
	public void exportDetailViewDashboard(WebRequest request, HttpServletResponse response,
			@RequestBody DashboardSearchDto dto) throws IOException {
		FarmDto farm = this.farmService.getById(dto.getFarmId());
		ImportExportAnimalSearchDto searchDto = new ImportExportAnimalSearchDto();
		searchDto.setFarmId(farm.getId());
		searchDto.setNameOrCode(farm.getCode());
		searchDto.setIsImportExportAnimalSeed(false);
		searchDto.setType(WLConstant.ImportExportAnimalType.importAnimal.getValue());
		List<ImportExportAnimalDto> listImportAnimal = importExportAnimalService.getAllBySearch(searchDto);
		searchDto.setType(WLConstant.ImportExportAnimalType.exportAnimal.getValue());
		List<ImportExportAnimalDto> listExportAnimal = importExportAnimalService.getAllBySearch(searchDto);
		Resource resource = resourceLoader.getResource("classpath:ExportFileDashboard.xls");
		InputStream is = resource.getInputStream();
		ImportExportExcelUtil.exportDashboardView(is, response, dto, farm, listImportAnimal, listExportAnimal);
	}

	@Secured({ "ROLE_ADMIN", "ROLE_DLP", "ROLE_SDAH" })
	@RequestMapping(value = "/exportToTalReport", method = RequestMethod.POST)
	public void exportToTalReport(WebRequest request, HttpServletResponse response, @RequestBody ReportParamDto dto)
			throws IOException {
		TotalReportDto ret = importExportAnimalService.getTotalReport(dto.getFromDate(), dto.getToDate());
		Resource resource = resourceLoader.getResource("classpath:BaoCaoTongHopChanNuoi.xls");
		InputStream ip = resource.getInputStream();
		OutputStream out = response.getOutputStream();
		ImportExportExcelUtil.exportTotalReport(ip, out, ret);
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTongHop.xls");
		response.flushBuffer();
	}

	/** import File FAO_02062020 sheet AnimalCode */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importAnimalCode", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importExAnimalCodeFile(@RequestParam("uploadfile") MultipartFile uploadfile)
			throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		// List<AnimalDto> list =
		// ImportExportExcelUtil.importImportAnimalFromInputStream(bis);
		// FileInputStream fileInputStream = new FileInputStream(bis);
		List<AnimalDto> list = ImportExportExcelUtil.getAnimalDtosFromInputStream(bis);
		animalService.saveListAnimal(list);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/** import File FAO_02062020 sheet FarmAnimal2017 */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importFarmAnimal", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importFarmAnimal(@RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<FarmAnimal2017Dto> listInput = ImportExportExcelUtil.getFarmImportAnimalFromInputStream(bis);
		importExportAnimalService.saveFarmAnimalList(listInput);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	/** import File FAO_02062020 sheet BEAR */
	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/importFarmBear", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> importFarmBear(@RequestParam("uploadfile") MultipartFile uploadfile) throws IOException {

		ByteArrayInputStream bis = new ByteArrayInputStream(uploadfile.getBytes());
		List<FarmAnimal2017Dto> listInput = ImportExportExcelUtil.getBearInfoInputStream(bis);
		importExportAnimalService.saveImportAnimalBear(listInput);

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/exportAnimalReportData", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportAnimalReportData(WebRequest request, HttpServletResponse response,
			@RequestBody AnimalReportDataSearchDto dto) throws IOException {
		Resource resource = resourceLoader.getResource("classpath:DVHD_ExportTemplate_2017.xls");
		List<AnimalReportData> list = animalReportDataService.getListAnimalReportData(dto);
		List<ProductTargetDto> listProductTargets = productTargetService.getAllProductTarget();
		List<OriginalDto> listOrignals = originalService.getAll();
		Map<String, ProductTargetDto> productTargetSet = new HashMap<String, ProductTargetDto>();
		Map<String, OriginalDto> originalSet = new HashMap<String, OriginalDto>();
		for (ProductTargetDto productTargetDto : listProductTargets) {
			if (productTargetDto != null) {
				productTargetSet.put(productTargetDto.getCode(), productTargetDto);
			}
		}
		for (OriginalDto originalDto : listOrignals) {
			if (originalDto != null) {
				originalSet.put(originalDto.getCode(), originalDto);
			}
		}
		Collections.sort(list, new Comparator<AnimalReportData>() {
			@Override
			public int compare(final AnimalReportData object1, final AnimalReportData object2) {
				String province1 = "";
				String district1 = "";
				String ward1 = "";

				String province2 = "";
				String district2 = "";
				String ward2 = "";
				if (object1 != null && object2 != null) {
					if (object1.getFarm() != null && object1.getFarm().getAdministrativeUnit() != null) {
						ward1 = object1.getFarm().getAdministrativeUnit().getName();
						if (object1.getFarm().getAdministrativeUnit().getParent() != null) {
							district1 = object1.getFarm().getAdministrativeUnit().getParent().getName();
							if (object1.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
								province1 = object1.getFarm().getAdministrativeUnit().getParent().getParent().getName();
							}
						}
					}

					if (object2.getFarm() != null && object2.getFarm().getAdministrativeUnit() != null) {
						ward2 = object2.getFarm().getAdministrativeUnit().getName();
						if (object2.getFarm().getAdministrativeUnit().getParent() != null) {
							district2 = object2.getFarm().getAdministrativeUnit().getParent().getName();
							if (object2.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
								province2 = object2.getFarm().getAdministrativeUnit().getParent().getParent().getName();
							}
						}
					}
				}
				int value1 = province2.compareTo(province1);
				if (value1 == 0) {
					int value2 = district2.compareTo(district1);
					if (value2 == 0) {
						int value3 = ward2.compareTo(ward1);
						if (value3 == 0) {
							if (object1.getFarm() != null && object2.getFarm() != null) {
								return object2.getFarm().getId().compareTo(object1.getFarm().getId());
							}
						} else {
							return value3;
						}
					} else {
						return value2;
					}
				}
				return value1;
			}
		});
		ImportExportExcelUtil.exportAnimalReportData(list, originalSet, productTargetSet, resource.getInputStream(),
				response.getOutputStream());
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Secured({ "ROLE_ADMIN" })
	@RequestMapping(value = "/exportAnimalReportDataTemplate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportAnimalReportDataTemplate(WebRequest request, HttpServletResponse response,
			@RequestBody AnimalReportDataSearchDto dto) throws IOException {
		List<AnimalReportData> list = animalReportDataService.getListAnimalReportData(dto);
		List<ProductTargetDto> listProductTargets = productTargetService.getAllProductTarget();
		List<OriginalDto> listOrignals = originalService.getAll();
		String titleHeader = "";

		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getCommuneId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository
									.findById(dto.getCommuneId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}
		List<AnimalReportData> listExport = new ArrayList<AnimalReportData>();
		Set<Long> listFarmId = new HashSet<Long>();
		List<AnimalReportData> listFarm = new ArrayList<AnimalReportData>();
		for (AnimalReportData item : list) {
			if (item.getYear() != null && item.getMonth() == null && item.getQuarter() == null) {
				listExport.add(item);
			}
			if (item.getFarm() != null && !listFarmId.contains(item.getFarm().getId())) {
				listFarm.add(item);
				listFarmId.add(item.getFarm().getId());
			}
		}

		Map<String, ProductTargetDto> productTargetSet = new HashMap<String, ProductTargetDto>();
		Map<String, OriginalDto> originalSet = new HashMap<String, OriginalDto>();

		for (ProductTargetDto productTargetDto : listProductTargets) {
			if (productTargetDto != null) {
				productTargetSet.put(productTargetDto.getCode(), productTargetDto);
			}
		}
		for (OriginalDto originalDto : listOrignals) {
			if (originalDto != null) {
				originalSet.put(originalDto.getCode(), originalDto);
			}
		}
		Collections.sort(listExport, new Comparator<AnimalReportData>() {
			@Override
			public int compare(final AnimalReportData object1, final AnimalReportData object2) {
				String province1 = "";
				String district1 = "";
				String ward1 = "";

				String province2 = "";
				String district2 = "";
				String ward2 = "";
				if (object1 != null && object2 != null) {
					if (object1.getFarm() != null && object1.getFarm().getAdministrativeUnit() != null) {
						ward1 = object1.getFarm().getAdministrativeUnit().getName();
						if (object1.getFarm().getAdministrativeUnit().getParent() != null) {
							district1 = object1.getFarm().getAdministrativeUnit().getParent().getName();
							if (object1.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
								province1 = object1.getFarm().getAdministrativeUnit().getParent().getParent().getName();
							}
						}
					}

					if (object2.getFarm() != null && object2.getFarm().getAdministrativeUnit() != null) {
						ward2 = object2.getFarm().getAdministrativeUnit().getName();
						if (object2.getFarm().getAdministrativeUnit().getParent() != null) {
							district2 = object2.getFarm().getAdministrativeUnit().getParent().getName();
							if (object2.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
								province2 = object2.getFarm().getAdministrativeUnit().getParent().getParent().getName();
							}
						}
					}
				}
				int value1 = province2.compareTo(province1);
				if (value1 == 0) {
					int value2 = district2.compareTo(district1);
					if (value2 == 0) {
						int value3 = ward2.compareTo(ward1);
						if (value3 == 0) {
							if (object1.getFarm() != null && object2.getFarm() != null) {
								return object2.getFarm().getId().compareTo(object1.getFarm().getId());
							}
						} else {
							return value3;
						}
					} else {
						return value2;
					}
				}
				return value1;
			}
		});

		Collections.sort(listFarm, new Comparator<AnimalReportData>() {
			@Override
			public int compare(final AnimalReportData object1, final AnimalReportData object2) {
				String province1 = "";
				String district1 = "";
				String ward1 = "";

				String province2 = "";
				String district2 = "";
				String ward2 = "";
				if (object1 != null && object2 != null) {
					if (object1.getFarm() != null && object1.getFarm().getAdministrativeUnit() != null) {
						ward1 = object1.getFarm().getAdministrativeUnit().getName();
						if (object1.getFarm().getAdministrativeUnit().getParent() != null) {
							district1 = object1.getFarm().getAdministrativeUnit().getParent().getName();
							if (object1.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
								province1 = object1.getFarm().getAdministrativeUnit().getParent().getParent().getName();
							}
						}
					}

					if (object2.getFarm() != null && object2.getFarm().getAdministrativeUnit() != null) {
						ward2 = object2.getFarm().getAdministrativeUnit().getName();
						if (object2.getFarm().getAdministrativeUnit().getParent() != null) {
							district2 = object2.getFarm().getAdministrativeUnit().getParent().getName();
							if (object2.getFarm().getAdministrativeUnit().getParent().getParent() != null) {
								province2 = object2.getFarm().getAdministrativeUnit().getParent().getParent().getName();
							}
						}
					}
				}
				int value1 = province2.compareTo(province1);
				if (value1 == 0) {
					int value2 = district2.compareTo(district1);
					if (value2 == 0) {
						int value3 = ward2.compareTo(ward1);
						if (value3 == 0) {
							if (object1.getFarm() != null && object2.getFarm() != null) {
								return object2.getFarm().getId().compareTo(object1.getFarm().getId());
							}
						} else {
							return value3;
						}
					} else {
						return value2;
					}
				}
				return value1;
			}
		});

		ImportExportExcelUtil.exportAnimalReportDataByYearMonth(listExport, listFarm, response.getOutputStream(), dto,
				titleHeader);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/userAttachments", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<UserAttachmentsDto> uploadUserAttachments(
			@RequestParam("uploadfile") MultipartFile uploadfile) {
		UserAttachmentsDto result = null;
		if (WLConstant.FolderPath == null) {
			WLConstant.FolderPath = env.getProperty("wl.file.folder");
		}
		try {
			String filePath = uploadfile.getOriginalFilename();
			filePath = WLConstant.FolderPath + filePath;
			FileOutputStream stream = new FileOutputStream(filePath);
			try {
				stream.write(uploadfile.getBytes());
			} finally {
				stream.close();
			}

			String pattern = "dd-MM-yyyy_HH-mm-ss";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String originalFileName = uploadfile.getOriginalFilename();
			String fileNameSaveFolder = simpleDateFormat.format(new Date()) + "_" + originalFileName;

			FileDescription file = new FileDescription();
			file.setContentSize(uploadfile.getSize());
			file.setContentType(uploadfile.getContentType());
			file.setName(fileNameSaveFolder);
			file.setFilePath(filePath);
			file = fileService.save(file);

			result = new UserAttachmentsDto();
			result.setFileName(originalFileName);
			result.setFile(new FileDescriptionDto(file));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<UserAttachmentsDto>(result, HttpStatus.OK);
	} // method upload Student

	@RequestMapping(value = "/downloadFileExcelForm16A", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> downloadFileExcelForm16A(WebRequest request, HttpServletResponse response,
			@RequestBody SearchReportPeriodDto dto) throws IOException {
		List<ReportPeriod> list = reportPeriodService.getAllEntity(dto);

		String titleHeader = "";

		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getWardId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWardId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}

		ImportExportExcelUtil.downloadFileExcelForm16A(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=BieuMau16A.xls");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Xuất excel mẫu 16 tại mục Mẫu 16: xuất đầy đủ các lần import
	@RequestMapping(value = "/downloadFileImportExcelForm16ADetail", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> downloadFileImportExcelForm16ADetail(WebRequest request, HttpServletResponse response,
			@RequestBody SearchReportForm16Dto dto) throws IOException {
		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16DtoDetail(dto);
		// List<ReportForm16Dto> list =
		// reportForm16Service.getListReportForm16DtoNativeQueryForExport(dto);
		for (ReportForm16Dto r : list) {
			// Bo sung start
			if (r.getForm16Id() != null) {
				r.setId(Long.parseLong(r.getForm16Id().toString()));
			}
			if (r.getFarmId() != null) {
				FarmDto farm = farmService.getById(Long.parseLong(r.getFarmId().toString()));
				r.setFarm(farm);
			}
			if (r.getPeriodId() != null) {
				ReportPeriodDto reportPeriod = reportPeriodService.getById(Long.parseLong(r.getPeriodId().toString()));
				r.setReportPeriod(reportPeriod);
			}
			if (r.getAnimalId() != null) {
				AnimalDto animal = animalService.getAnimalById(Long.parseLong(r.getAnimalId().toString()));
				r.setAnimal(animal);
			}
			if (r.getProvId() != null) {
				FmsAdministrativeUnitDto provinceDto = fmsAdministrativeUnitService
						.getAdministrativeUnitById(Long.parseLong(r.getProvId().toString()));
				r.setProvinceDto(provinceDto);
			}
			if (r.getDisId() != null) {
				FmsAdministrativeUnitDto districtDto = fmsAdministrativeUnitService
						.getAdministrativeUnitById(Long.parseLong(r.getDisId().toString()));
				r.setDistrictDto(districtDto);
			}
			if (r.getWardId() != null) {
				FmsAdministrativeUnitDto administrativeUnitDto = fmsAdministrativeUnitService
						.getAdministrativeUnitById(Long.parseLong(r.getWardId().toString()));
				r.setAdministrativeUnitDto(administrativeUnitDto);
			}
			if (r.getOriginalId() != null) {
				OriginalDto original = originalService.getOriginalById(Long.parseLong(r.getOriginalId().toString()));
				r.setOriginal(original);
			}
		}
		// Bo sung end
		String titleHeader = "";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getWardId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWardId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}

		if (dto.getDay() != null) {
			titleHeader += " - Ngày " + dto.getDay();
		}
		if (dto.getMonth() != null) {
			titleHeader += " - Tháng " + dto.getMonth();
		}
		if (dto.getYear() != null) {
			titleHeader += " - Năm " + dto.getYear();
		}
//			for(ReportForm16Dto r : list) {
//				if(r.getFarm().getLatitude()!=null&&r.getFarm().getLatitude().indexOf('E')!=-1) {
//					DecimalFormat df = new DecimalFormat("#");
//					try {
//						double a = Double.parseDouble(r.getFarm().getLatitude());
//						String d = df.format(a);
//						System.out.println(d);
////						Farm farm = this.farmRepository.findOne(r.getFarm().getId());
////						farm.setLatitude(d);
//						r.getFarm().setLatitude(d);
//					}catch(Exception e) {
//						System.out.println(r.getFarm().getName()+" Ép kiểu vĩ độ lỗi");
//					}
//				}
//				if(r.getFarm().getLongitude()!=null&&r.getFarm().getLongitude().indexOf('E')!=-1) {
//					DecimalFormat df = new DecimalFormat("#");
//					try {
//						double a = Double.parseDouble(r.getFarm().getLongitude());
//						String d = df.format(a);
//						System.out.println(d);
////						Farm farm = this.farmRepository.findOne(r.getFarm().getId());
////						farm.setLatitude(d);
//						r.getFarm().setLongitude(d);
//					}catch(Exception e) {
//						System.out.println(r.getFarm().getName()+" Ép kiểu kinh độ lỗi");
//					}
//				}
//			}
		ImportExportExcelUtil.downloadFileImportExcelForm16AOld(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuImport.xlsx");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/downloadFileImportExcelForm16A", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> downloadFileImportExcelForm16A(WebRequest request, HttpServletResponse response,
			@RequestBody SearchReportForm16Dto dto) throws IOException {
		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16Dto(dto);
		// List<ReportForm16Dto> list =
		// reportForm16Service.getListReportForm16DtoNativeQueryForExport(dto);
//		for(ReportForm16Dto r : list) {
//			//Bo sung start
//			if(r.getForm16Id() != null) {
//				r.setId(Long.parseLong(r.getForm16Id().toString()));
//			}
//			if(r.getFarmId() != null) {
//				FarmDto farm = farmService.getById(Long.parseLong(r.getFarmId().toString()));
//				r.setFarm(farm);
//			}
//			if(r.getPeriodId() != null) {
//				ReportPeriodDto reportPeriod = reportPeriodService.getById(Long.parseLong(r.getPeriodId().toString()));
//				r.setReportPeriod(reportPeriod);
//			}
//			if(r.getAnimalId() != null) {
//				AnimalDto animal = animalService.getAnimalById(Long.parseLong(r.getAnimalId().toString()));
//				r.setAnimal(animal);
//			}
//			if(r.getProvId() != null) {
//				FmsAdministrativeUnitDto provinceDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getProvId().toString()));
//				r.setProvinceDto(provinceDto);
//			}
//			if(r.getDisId() != null) {
//				FmsAdministrativeUnitDto districtDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getDisId().toString()));
//				r.setDistrictDto(districtDto);
//			}
//			if(r.getWardId() != null) {
//				FmsAdministrativeUnitDto administrativeUnitDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getWardId().toString()));
//				r.setAdministrativeUnitDto(administrativeUnitDto);
//			}
//			if(r.getOriginalId() != null) {
//				OriginalDto original =  originalService.getOriginalById(Long.parseLong(r.getOriginalId().toString()));
//				r.setOriginal(original);
//			}
//		}
		// Bo sung end
		String titleHeader = "";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getWardId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWardId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}

		if (dto.getDay() != null) {
			titleHeader += " - Ngày " + dto.getDay();
		}
		if (dto.getMonth() != null) {
			titleHeader += " - Tháng " + dto.getMonth();
		}
		if (dto.getYear() != null) {
			titleHeader += " - Năm " + dto.getYear();
		}
		for (ReportForm16Dto r : list) {
			if (r.getFarm().getLatitude() != null && r.getFarm().getLatitude().indexOf('E') != -1) {
				DecimalFormat df = new DecimalFormat("#");
				try {
					double a = Double.parseDouble(r.getFarm().getLatitude());
					String d = df.format(a);
					System.out.println(d);
//					Farm farm = this.farmRepository.findOne(r.getFarm().getId());
//					farm.setLatitude(d);
					r.getFarm().setLatitude(d);
				} catch (Exception e) {
					System.out.println(r.getFarm().getName() + " Ép kiểu vĩ độ lỗi");
				}
			}
			if (r.getFarm().getLongitude() != null && r.getFarm().getLongitude().indexOf('E') != -1) {
				DecimalFormat df = new DecimalFormat("#");
				try {
					double a = Double.parseDouble(r.getFarm().getLongitude());
					String d = df.format(a);
					System.out.println(d);
//					Farm farm = this.farmRepository.findOne(r.getFarm().getId());
//					farm.setLatitude(d);
					r.getFarm().setLongitude(d);
				} catch (Exception e) {
					System.out.println(r.getFarm().getName() + " Ép kiểu kinh độ lỗi");
				}
			}
		}
		ImportExportExcelUtil.downloadFileImportExcelForm16AOld(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuImport.xlsx");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// Xuất excel mẫu 16 tại mục Import dữ liệu báo cáo: xuất ra dữ liệu báo cáo gần
	// đây nhất theo động vật của mỗi Farm
	@RequestMapping(value = "/downloadFileImportExcelForm16AInImportReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> downloadFileImportExcelForm16AInImportReport(WebRequest request,
			HttpServletResponse response, @RequestBody SearchReportForm16Dto dto) throws IOException {
//		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16Dto(dto);
		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16DtoNativeQueryForExport(dto);
		Set<Long> listFarmId = new HashSet<Long>();
		Set<Long> listPeriodId = new HashSet<Long>();
		Set<Long> listAnimalId = new HashSet<Long>();
		Set<Long> listProvId = new HashSet<Long>();
		Set<Long> listDisId = new HashSet<Long>();
		Set<Long> listWardId = new HashSet<Long>();
		Set<Long> listOriginalId = new HashSet<Long>();

		for (ReportForm16Dto r : list) {
			// Bo sung start
			if (r.getForm16Id() != null) {
				r.setId(Long.parseLong(r.getForm16Id().toString()));
			}
			if (r.getFarmId() != null) {
				listFarmId.add(r.getFarmId().longValue());
//				FarmDto farm = farmService.getById(Long.parseLong(r.getFarmId().toString()));
//				r.setFarm(farm);
			}
			if (r.getPeriodId() != null) {
				listPeriodId.add(r.getPeriodId().longValue());
//				ReportPeriodDto reportPeriod = reportPeriodService.getById(Long.parseLong(r.getPeriodId().toString()));
//				r.setReportPeriod(reportPeriod);
			}
			if (r.getAnimalId() != null) {
				listAnimalId.add(r.getAnimalId().longValue());
//				AnimalDto animal = animalService.getAnimalById(Long.parseLong(r.getAnimalId().toString()));
//				r.setAnimal(animal);
			}
			if (r.getProvId() != null) {
				listProvId.add(r.getProvId().longValue());
//				FmsAdministrativeUnitDto provinceDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getProvId().toString()));
//				r.setProvinceDto(provinceDto);
			}
			if (r.getDisId() != null) {
				listDisId.add(r.getDisId().longValue());
//				FmsAdministrativeUnitDto districtDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getDisId().toString()));
//				r.setDistrictDto(districtDto);
			}
			if (r.getWardId() != null) {
				listWardId.add(r.getWardId().longValue());
//				FmsAdministrativeUnitDto administrativeUnitDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getWardId().toString()));
//				r.setAdministrativeUnitDto(administrativeUnitDto);
			}
			if (r.getOriginalId() != null) {
				listOriginalId.add(r.getOriginalId().longValue());
//				OriginalDto original =  originalService.getOriginalById(Long.parseLong(r.getOriginalId().toString()));
//				r.setOriginal(original);
			}
		}
		List<Farm> farms = farmRepository.findAll(listFarmId);
		List<ReportPeriod> reportPeriods = reportPeriodRepository.findAll(listPeriodId);
		List<Animal> animals = animalRepository.findAll(listAnimalId);
		List<FmsAdministrativeUnit> provs = fmsAdministrativeUnitRepository.findAll(listProvId);
		List<FmsAdministrativeUnit> districts = fmsAdministrativeUnitRepository.findAll(listDisId);
		List<FmsAdministrativeUnit> wards = fmsAdministrativeUnitRepository.findAll(listWardId);
		List<Original> originals = originalRepository.findAll(listOriginalId);
		for (ReportForm16Dto l : list) {
			for (Farm f : farms) {
				if (l.getFarmId() != null && f.getId().equals(l.getFarmId().longValue())) {
					l.setFarm(new FarmDto(f, true));
				}
			}
			for (Animal a : animals) {
				if (l.getAnimalId() != null && a.getId().equals(l.getAnimalId().longValue())) {
					l.setAnimal(new AnimalDto(a, true));
				}
			}
			for (ReportPeriod r : reportPeriods) {
				if (l.getPeriodId() != null && r.getId().equals(l.getPeriodId().longValue())) {
					l.setReportPeriod(new ReportPeriodDto(r));
				}
			}
			for (FmsAdministrativeUnit ad : provs) {
				if (l.getProvId() != null && ad.getId().equals(l.getProvId().longValue())) {
					l.setProvinceDto(new FmsAdministrativeUnitDto(ad, 1));
				}
			}
			for (FmsAdministrativeUnit ad : districts) {
				if (l.getDisId() != null && ad.getId().equals(l.getDisId().longValue())) {
					l.setDistrictDto(new FmsAdministrativeUnitDto(ad, 1));
				}
			}
			for (FmsAdministrativeUnit ad : wards) {
				if (l.getWardId() != null && ad.getId().equals(l.getWardId().longValue())) {
					l.setAdministrativeUnitDto(new FmsAdministrativeUnitDto(ad, 1));
				}
			}
			for (Original original : originals) {
				if (l.getOriginalId() != null && original.getId().equals(l.getOriginalId().longValue())) {
					l.setOriginal(new OriginalDto(original));
				}
			}
		}

		// Bo sung end
		String titleHeader = "";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getWardId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWardId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}

		if (dto.getDay() != null) {
			titleHeader += " - Ngày " + dto.getDay();
		}
		if (dto.getMonth() != null) {
			titleHeader += " - Tháng " + dto.getMonth();
		}
		if (dto.getYear() != null) {
			titleHeader += " - Năm " + dto.getYear();
		}
//		for(ReportForm16Dto r : list) {
//			if(r.getFarm().getLatitude()!=null&&r.getFarm().getLatitude().indexOf('E')!=-1) {
//				DecimalFormat df = new DecimalFormat("#");
//				try {
//					double a = Double.parseDouble(r.getFarm().getLatitude());
//					String d = df.format(a);
//					System.out.println(d);
////					Farm farm = this.farmRepository.findOne(r.getFarm().getId());
////					farm.setLatitude(d);
//					r.getFarm().setLatitude(d);
//				}catch(Exception e) {
//					System.out.println(r.getFarm().getName()+" Ép kiểu vĩ độ lỗi");
//				}
//			}
//			if(r.getFarm().getLongitude()!=null&&r.getFarm().getLongitude().indexOf('E')!=-1) {
//				DecimalFormat df = new DecimalFormat("#");
//				try {
//					double a = Double.parseDouble(r.getFarm().getLongitude());
//					String d = df.format(a);
//					System.out.println(d);
////					Farm farm = this.farmRepository.findOne(r.getFarm().getId());
////					farm.setLatitude(d);
//					r.getFarm().setLongitude(d);
//				}catch(Exception e) {
//					System.out.println(r.getFarm().getName()+" Ép kiểu kinh độ lỗi");
//				}
//			}
//		}
		ImportExportExcelUtil.downloadFileImportExcelForm16AOld(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuImport.xlsx");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// tran huu dat them file import new
	@RequestMapping(value = "/downloadFileImportExcelForm16ANew", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> downloadFileImportExcelForm16ANew(WebRequest request, HttpServletResponse response,
			@RequestBody SearchReportForm16Dto dto) throws IOException {
		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16Dto(dto);
//		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16DtoNativeQueryForExport(dto);
		// Bo sung start
//		for(ReportForm16Dto r : list) {
//			if(r.getForm16Id() != null) {
//				r.setId(Long.parseLong(r.getForm16Id().toString()));
//			}
//			if(r.getFarmId() != null) {
//				FarmDto farm = farmService.getById(Long.parseLong(r.getFarmId().toString()));
//				r.setFarm(farm);
//			}
//			if(r.getPeriodId() != null) {
//				ReportPeriodDto reportPeriod = reportPeriodService.getById(Long.parseLong(r.getPeriodId().toString()));
//				r.setReportPeriod(reportPeriod);
//			}
//			if(r.getAnimalId() != null) {
//				AnimalDto animal = animalService.getAnimalById(Long.parseLong(r.getAnimalId().toString()));
//				r.setAnimal(animal);
//			}
//			if(r.getProvId() != null) {
//				FmsAdministrativeUnitDto provinceDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getProvId().toString()));
//				r.setProvinceDto(provinceDto);
//			}
//			if(r.getDisId() != null) {
//				FmsAdministrativeUnitDto districtDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getDisId().toString()));
//				r.setDistrictDto(districtDto);
//			}
//			if(r.getWardId() != null) {
//				FmsAdministrativeUnitDto administrativeUnitDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getWardId().toString()));
//				r.setAdministrativeUnitDto(administrativeUnitDto);
//			}
//			if(r.getOriginalId() != null) {
//				OriginalDto original =  originalService.getOriginalById(Long.parseLong(r.getOriginalId().toString()));
//				r.setOriginal(original);
//			}
//		}
		// Bo sung end
		String titleHeader = "";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getWardId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWardId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}

		if (dto.getDay() != null) {
			titleHeader += " - Ngày " + dto.getDay();
		}
		if (dto.getMonth() != null) {
			titleHeader += " - Tháng " + dto.getMonth();
		}
		if (dto.getYear() != null) {
			titleHeader += " - Năm " + dto.getYear();
		}
		for (ReportForm16Dto r : list) {
			if (r.getFarm().getLatitude() != null && r.getFarm().getLatitude().indexOf('E') != -1) {
				DecimalFormat df = new DecimalFormat("#");
				try {
					double a = Double.parseDouble(r.getFarm().getLatitude());
					String d = df.format(a);
					System.out.println(d);
//					Farm farm = this.farmRepository.findOne(r.getFarm().getId());
//					farm.setLatitude(d);
					r.getFarm().setLatitude(d);
				} catch (Exception e) {
					System.out.println(r.getFarm().getName() + " Ép kiểu vĩ độ lỗi");
				}
			}
//			if(r.getFarm().getLongitude()!=null&&r.getFarm().getLongitude().indexOf('E')!=-1) {
//				DecimalFormat df = new DecimalFormat("#");
//				try {
//					double a = Double.parseDouble(r.getFarm().getLongitude());
//					String d = df.format(a);
//					System.out.println(d);
////					Farm farm = this.farmRepository.findOne(r.getFarm().getId());
////					farm.setLatitude(d);
//					r.getFarm().setLongitude(d);
//				}catch(Exception e) {
//					System.out.println(r.getFarm().getName()+" Ép kiểu kinh độ lỗi");
//				}
//			}
		}
		ImportExportExcelUtil.downloadFileImportExcelForm16A(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuImport.xlsx");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// tran huu dat them file import new end
	@RequestMapping(value = "/downloadFileImportExcelForm16ANewInImportReport", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> downloadFileImportExcelForm16ANewInImportReport(WebRequest request,
			HttpServletResponse response, @RequestBody SearchReportForm16Dto dto) throws IOException {
//		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16Dto(dto);
		List<ReportForm16Dto> list = reportForm16Service.getListReportForm16DtoNativeQueryForExport(dto);
		// Bo sung start
		Set<Long> listFarmId = new HashSet<Long>();
		Set<Long> listPeriodId = new HashSet<Long>();
		Set<Long> listAnimalId = new HashSet<Long>();
		Set<Long> listProvId = new HashSet<Long>();
		Set<Long> listDisId = new HashSet<Long>();
		Set<Long> listWardId = new HashSet<Long>();
		Set<Long> listOriginalId = new HashSet<Long>();

		for (ReportForm16Dto r : list) {
			// Bo sung start
			if (r.getForm16Id() != null) {
				r.setId(Long.parseLong(r.getForm16Id().toString()));
			}
			if (r.getFarmId() != null) {
				listFarmId.add(r.getFarmId().longValue());
//				FarmDto farm = farmService.getById(Long.parseLong(r.getFarmId().toString()));
//				r.setFarm(farm);
			}
			if (r.getPeriodId() != null) {
				listPeriodId.add(r.getPeriodId().longValue());
//				ReportPeriodDto reportPeriod = reportPeriodService.getById(Long.parseLong(r.getPeriodId().toString()));
//				r.setReportPeriod(reportPeriod);
			}
			if (r.getAnimalId() != null) {
				listAnimalId.add(r.getAnimalId().longValue());
//				AnimalDto animal = animalService.getAnimalById(Long.parseLong(r.getAnimalId().toString()));
//				r.setAnimal(animal);
			}
			if (r.getProvId() != null) {
				listProvId.add(r.getProvId().longValue());
//				FmsAdministrativeUnitDto provinceDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getProvId().toString()));
//				r.setProvinceDto(provinceDto);
			}
			if (r.getDisId() != null) {
				listDisId.add(r.getDisId().longValue());
//				FmsAdministrativeUnitDto districtDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getDisId().toString()));
//				r.setDistrictDto(districtDto);
			}
			if (r.getWardId() != null) {
				listWardId.add(r.getWardId().longValue());
//				FmsAdministrativeUnitDto administrativeUnitDto = fmsAdministrativeUnitService.getAdministrativeUnitById(Long.parseLong(r.getWardId().toString()));
//				r.setAdministrativeUnitDto(administrativeUnitDto);
			}
			if (r.getOriginalId() != null) {
				listOriginalId.add(r.getOriginalId().longValue());
//				OriginalDto original =  originalService.getOriginalById(Long.parseLong(r.getOriginalId().toString()));
//				r.setOriginal(original);
			}
		}
		List<Farm> farms = farmRepository.findAll(listFarmId);
		List<ReportPeriod> reportPeriods = reportPeriodRepository.findAll(listPeriodId);
		List<Animal> animals = animalRepository.findAll(listAnimalId);
		List<FmsAdministrativeUnit> provs = fmsAdministrativeUnitRepository.findAll(listProvId);
		List<FmsAdministrativeUnit> districts = fmsAdministrativeUnitRepository.findAll(listDisId);
		List<FmsAdministrativeUnit> wards = fmsAdministrativeUnitRepository.findAll(listWardId);
		List<Original> originals = originalRepository.findAll(listOriginalId);
		for (ReportForm16Dto l : list) {
			for (Farm f : farms) {
				if (l.getFarmId() != null && f.getId().equals(l.getFarmId().longValue())) {
					l.setFarm(new FarmDto(f, true));
				}
			}
			for (Animal a : animals) {
				if (l.getAnimalId() != null && a.getId().equals(l.getAnimalId().longValue())) {
					l.setAnimal(new AnimalDto(a, true));
				}
			}
			for (ReportPeriod r : reportPeriods) {
				if (l.getPeriodId() != null && r.getId().equals(l.getPeriodId().longValue())) {
					l.setReportPeriod(new ReportPeriodDto(r));
				}
			}
			for (FmsAdministrativeUnit ad : provs) {
				if (l.getProvId() != null && ad.getId().equals(l.getProvId().longValue())) {
					l.setProvinceDto(new FmsAdministrativeUnitDto(ad, 1));
				}
			}
			for (FmsAdministrativeUnit ad : districts) {
				if (l.getDisId() != null && ad.getId().equals(l.getDisId().longValue())) {
					l.setDistrictDto(new FmsAdministrativeUnitDto(ad, 1));
				}
			}
			for (FmsAdministrativeUnit ad : wards) {
				if (l.getWardId() != null && ad.getId().equals(l.getWardId().longValue())) {
					l.setAdministrativeUnitDto(new FmsAdministrativeUnitDto(ad, 1));
				}
			}
			for (Original original : originals) {
				if (l.getOriginalId() != null && original.getId().equals(l.getOriginalId().longValue())) {
					l.setOriginal(new OriginalDto(original));
				}
			}
		}
		// Bo sung end
		String titleHeader = "";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getWardId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWardId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}

		if (dto.getDay() != null) {
			titleHeader += " - Ngày " + dto.getDay();
		}
		if (dto.getMonth() != null) {
			titleHeader += " - Tháng " + dto.getMonth();
		}
		if (dto.getYear() != null) {
			titleHeader += " - Năm " + dto.getYear();
		}
//		for(ReportForm16Dto r : list) {
//			if(r.getFarm().getLatitude()!=null&&r.getFarm().getLatitude().indexOf('E')!=-1) {
//				DecimalFormat df = new DecimalFormat("#");
//				try {
//					double a = Double.parseDouble(r.getFarm().getLatitude());
//					String d = df.format(a);
//					System.out.println(d);
////					Farm farm = this.farmRepository.findOne(r.getFarm().getId());
////					farm.setLatitude(d);
//					r.getFarm().setLatitude(d);
//				}catch(Exception e) {
//					System.out.println(r.getFarm().getName()+" Ép kiểu vĩ độ lỗi");
//				}
//			}
////			if(r.getFarm().getLongitude()!=null&&r.getFarm().getLongitude().indexOf('E')!=-1) {
////				DecimalFormat df = new DecimalFormat("#");
////				try {
////					double a = Double.parseDouble(r.getFarm().getLongitude());
////					String d = df.format(a);
////					System.out.println(d);
//////					Farm farm = this.farmRepository.findOne(r.getFarm().getId());
//////					farm.setLatitude(d);
////					r.getFarm().setLongitude(d);
////				}catch(Exception e) {
////					System.out.println(r.getFarm().getName()+" Ép kiểu kinh độ lỗi");
////				}
////			}
//		}
		ImportExportExcelUtil.downloadFileImportExcelForm16A(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuImport.xlsx");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/exportReportForm16DataByDistrictAndWard", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportReportForm16DataFollow(WebRequest request, HttpServletResponse response,
			@RequestBody SearchReportForm16Dto dto) throws IOException {
		List<ReportForm16> list = reportForm16Service.getListReportForm16(dto);
		String titleHeader = "";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getWardId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWardId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}

		if (dto.getDay() != null) {
			titleHeader += " - Ngày " + dto.getDay();
		}
		if (dto.getMonth() != null) {
			titleHeader += " - Tháng " + dto.getMonth();
		}
		if (dto.getYear() != null) {
			titleHeader += " - Năm " + dto.getYear();
		}
	
		ImportExportExcelUtil.exportReportForm16DataFollowNew(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuHuyenXa.xls");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/exportReportAnimalDangerousCitesToExcel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportReportAnimalDangerousCitesToExcel(WebRequest request, HttpServletResponse response,
			@RequestBody AnimalReportDataSearchDto dto) throws IOException {
		List<AnimalReportDataFormDto> list = reportService.reportActivityOfEndangeredPreciousRareNormarlAndCites(dto);
		String subTitle = "";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				subTitle += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						subTitle += ", " + district.getName();
						if (dto.getCommuneId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository
									.findById(dto.getCommuneId());
							if (commune != null && commune.getName() != null) {
								subTitle += ", " + commune.getName();
							}
						}
					}
				}
			}
		}
		if (dto.getYear() != null) {
			subTitle += " - Năm " + dto.getYear();
		}
		ImportExportExcelUtil.exportReportAnimalDangerousCitesToExcel(list, subTitle, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuBaoCaoHoatDongNuoiDVHD.xls");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	// new
	@RequestMapping(value = "/exportReportAnimalDangerousCitesToExcelNativeQuery", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportReportAnimalDangerousCitesToExcelNativeQuery(WebRequest request,
			HttpServletResponse response, @RequestBody AnimalReportDataSearchDto dto) throws IOException {
		List<Report18Dto> list = reportService.reportActivityOfEndangeredPreciousRareNormarlAndCitesNativeQuery(dto);
		String subTitle = "";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				subTitle += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						subTitle += ", " + district.getName();
						if (dto.getCommuneId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository
									.findById(dto.getCommuneId());
							if (commune != null && commune.getName() != null) {
								subTitle += ", " + commune.getName();
							}
						}
					}
				}
			}
		}
		if (dto.getYear() != null) {
			subTitle += " - Năm " + dto.getYear();
		}
		ImportExportExcelUtil.exportReportAnimalDangerousCitesToExcelNativeQuery(list, subTitle,
				response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuBaoCaoHoatDongNuoiDVHD.xls");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/* Bao cao DV gay nuoi theo Nd 06 quy III-2020 */
	@RequestMapping(value = "/exportReportAnimalDangerousCitesToExcelByForm", method = { RequestMethod.POST })
	// @Secured({ Const.ROLE_ADMIN, })
	public void exportReportAnimalDangerousCitesToExcelByForm(WebRequest request, HttpServletResponse response,
			@RequestBody AnimalReportDataSearchDto dto) {
		List<AnimalReportDataFormDto> list = reportService.reportActivityOfEndangeredPreciousRareNormarlAndCites(dto);
		Hashtable<Long, AnimalReportDataFromCityDto> hashCity = new Hashtable<Long, AnimalReportDataFromCityDto>();
		Hashtable<Long, AnimalReportDataFromDistrictDto> hashDisTrict = new Hashtable<Long, AnimalReportDataFromDistrictDto>();
		Hashtable<Long, AnimalReportDataFromWardDto> hashWard = new Hashtable<Long, AnimalReportDataFromWardDto>();
		List<AnimalReportDataFromCityDto> listCity = new ArrayList<AnimalReportDataFromCityDto>();
		List<AnimalReportDataFromDistrictDto> listDistrict = new ArrayList<AnimalReportDataFromDistrictDto>();
		List<AnimalReportDataFromWardDto> listWard = new ArrayList<AnimalReportDataFromWardDto>();
		if (list != null && list.size() > 0) {
			for (AnimalReportDataFormDto animalReportDataFormDto : list) {
				AnimalReportDataFromCityDto city = hashCity.get(animalReportDataFormDto.getProvinceDto().getId());
				if (city == null) {
					city = new AnimalReportDataFromCityDto();
					city.setId(animalReportDataFormDto.getProvinceDto().getId());
					city.setName(animalReportDataFormDto.getProvinceDto().getName());
					hashCity.put(city.getId(), city);
					listCity.add(city);
				}
				AnimalReportDataFromDistrictDto item = hashDisTrict
						.get(animalReportDataFormDto.getDistrictDto().getId());
				if (item == null) {
					item = new AnimalReportDataFromDistrictDto();
					item.setId(animalReportDataFormDto.getDistrictDto().getId());
					item.setName(animalReportDataFormDto.getDistrictDto().getName());
					item.setParentDistrictId(animalReportDataFormDto.getProvinceDto().getId());
					hashDisTrict.put(item.getId(), item);
					listDistrict.add(item);
				}
				AnimalReportDataFromWardDto itemWard = hashWard
						.get(animalReportDataFormDto.getAdministrativeUnitDto().getId());
				if (itemWard == null) {
					itemWard = new AnimalReportDataFromWardDto();
					itemWard.setId(animalReportDataFormDto.getAdministrativeUnitDto().getId());
					itemWard.setName(animalReportDataFormDto.getAdministrativeUnitDto().getName());
					itemWard.setParentWardId(animalReportDataFormDto.getDistrictDto().getId());
					itemWard.setList(new ArrayList<AnimalReportDataFormDto>());
					itemWard.getList().add(animalReportDataFormDto);

					itemWard.setFemaleBearHasChip(animalReportDataFormDto.getFemaleBearHasChip());
					itemWard.setDay(animalReportDataFormDto.getDay());
					itemWard.setFemaleBearNoChip(animalReportDataFormDto.getFemaleBearNoChip());
					itemWard.setFemaleChild(animalReportDataFormDto.getFemaleChild());
					itemWard.setFemaleChildUnder1YO(animalReportDataFormDto.getFemaleChildUnder1YO());
					itemWard.setFemaleGilts(animalReportDataFormDto.getFemaleGilts());
					itemWard.setFemaleParent(animalReportDataFormDto.getFemaleParent());
					itemWard.setMaleBearHasChip(animalReportDataFormDto.getMaleBearHasChip());
					itemWard.setMaleBearNoChip(animalReportDataFormDto.getMaleBearNoChip());
					itemWard.setMaleChild(animalReportDataFormDto.getMaleChild());
					itemWard.setMaleChildUnder1YO(animalReportDataFormDto.getMaleChildUnder1YO());
					itemWard.setMaleGilts(animalReportDataFormDto.getMaleGilts());
					itemWard.setMaleParent(animalReportDataFormDto.getMaleParent());
					itemWard.setMonth(animalReportDataFormDto.getMonth());
					itemWard.setTotalBearHasChip(animalReportDataFormDto.getTotalBearHasChip());
					itemWard.setTotalBearNoChip(animalReportDataFormDto.getTotalBearNoChip());
					itemWard.setTotalChild(animalReportDataFormDto.getTotalChild());
					itemWard.setTotalChildUnder1YO(animalReportDataFormDto.getTotalChildUnder1YO());
					itemWard.setTotalGilts(animalReportDataFormDto.getTotalGilts());
					itemWard.setTotalNormal(animalReportDataFormDto.getTotalNormal());
					itemWard.setTotalParent(animalReportDataFormDto.getTotalParent());
					itemWard.setUnGenderChild(animalReportDataFormDto.getUnGenderChild());
					itemWard.setUnGenderChildUnder1YO(animalReportDataFormDto.getUnGenderChildUnder1YO());
					itemWard.setUnGenderParent(animalReportDataFormDto.getUnGenderParent());
					itemWard.setYear(animalReportDataFormDto.getYear());

					hashWard.put(itemWard.getId(), itemWard);
					listWard.add(itemWard);
				} else {
					// itemWard.getList().add(animalReportDataFormDto);
					for (AnimalReportDataFromWardDto i : listWard) {
						if (i.getId().equals(itemWard.getId())) {
							if (i.getFemaleBearHasChip() != null)
								i.setFemaleBearHasChip(
										i.getFemaleBearHasChip() + animalReportDataFormDto.getFemaleBearHasChip());
							else
								i.setFemaleBearHasChip(animalReportDataFormDto.getFemaleBearHasChip());
							if (i.getDay() != null)
								i.setDay(i.getDay() + animalReportDataFormDto.getDay());
							else
								i.setDay(animalReportDataFormDto.getDay());
							if (i.getFemaleBearNoChip() != null)
								i.setFemaleBearNoChip(
										i.getFemaleBearNoChip() + animalReportDataFormDto.getFemaleBearNoChip());
							else
								i.setFemaleBearNoChip(animalReportDataFormDto.getFemaleBearNoChip());
							if (i.getFemaleChild() != null)
								i.setFemaleChild(i.getFemaleChild() + animalReportDataFormDto.getFemaleChild());
							else
								i.setFemaleChild(animalReportDataFormDto.getFemaleChild());
							if (i.getFemaleChildUnder1YO() != null)
								i.setFemaleChildUnder1YO(
										i.getFemaleChildUnder1YO() + animalReportDataFormDto.getFemaleChildUnder1YO());
							else
								i.setFemaleChildUnder1YO(animalReportDataFormDto.getFemaleChildUnder1YO());
							if (i.getFemaleGilts() != null)
								i.setFemaleGilts(i.getFemaleGilts() + animalReportDataFormDto.getFemaleGilts());
							else
								i.setFemaleGilts(animalReportDataFormDto.getFemaleGilts());

							if (i.getFemaleParent() != null)
								i.setFemaleParent(i.getFemaleParent() + animalReportDataFormDto.getFemaleParent());
							else
								i.setFemaleParent(animalReportDataFormDto.getFemaleParent());

							if (i.getMaleBearHasChip() != null)
								i.setMaleBearHasChip(
										i.getMaleBearHasChip() + animalReportDataFormDto.getMaleBearHasChip());
							else
								i.setMaleBearHasChip(animalReportDataFormDto.getMaleBearHasChip());
							if (i.getMaleBearNoChip() != null)
								i.setMaleBearNoChip(
										i.getMaleBearNoChip() + animalReportDataFormDto.getMaleBearNoChip());
							else
								i.setMaleBearNoChip(animalReportDataFormDto.getMaleBearNoChip());
							if (i.getMaleChild() != null)
								i.setMaleChild(i.getMaleChild() + animalReportDataFormDto.getMaleChild());
							else
								i.setMaleChild(animalReportDataFormDto.getMaleChild());
							if (i.getMaleChildUnder1YO() != null)
								i.setMaleChildUnder1YO(
										i.getMaleChildUnder1YO() + animalReportDataFormDto.getMaleChildUnder1YO());
							else
								i.setMaleChildUnder1YO(animalReportDataFormDto.getMaleChildUnder1YO());
							if (i.getMaleGilts() != null)
								i.setMaleGilts(i.getMaleGilts() + animalReportDataFormDto.getMaleGilts());
							else
								i.setMaleGilts(animalReportDataFormDto.getMaleGilts());
							if (i.getMaleParent() != null)
								i.setMaleParent(i.getMaleParent() + animalReportDataFormDto.getMaleParent());
							else
								i.setMaleParent(animalReportDataFormDto.getMaleParent());

							// i.setMonth(animalReportDataFormDto.getMonth());
							if (i.getTotalBearHasChip() != null)
								i.setTotalBearHasChip(
										i.getTotalBearHasChip() + animalReportDataFormDto.getTotalBearHasChip());
							else
								i.setTotalBearHasChip(animalReportDataFormDto.getTotalBearHasChip());
							if (i.getTotalBearNoChip() != null)
								i.setTotalBearNoChip(
										i.getTotalBearNoChip() + animalReportDataFormDto.getTotalBearNoChip());
							else
								i.setTotalBearNoChip(animalReportDataFormDto.getTotalBearNoChip());
							if (i.getTotalChild() != null)
								i.setTotalChild(i.getTotalChild() + animalReportDataFormDto.getTotalChild());
							else
								i.setTotalChild(animalReportDataFormDto.getTotalChild());
							if (i.getTotalChildUnder1YO() != null)
								i.setTotalChildUnder1YO(
										i.getTotalChildUnder1YO() + animalReportDataFormDto.getTotalChildUnder1YO());
							else
								i.setTotalChildUnder1YO(animalReportDataFormDto.getTotalChildUnder1YO());
							if (i.getTotalGilts() != null)
								i.setTotalGilts(i.getTotalGilts() + animalReportDataFormDto.getTotalGilts());
							else
								i.setTotalGilts(animalReportDataFormDto.getTotalGilts());
							if (i.getTotalNormal() != null)
								i.setTotalNormal(i.getTotalNormal() + animalReportDataFormDto.getTotalNormal());
							else
								i.setTotalNormal(animalReportDataFormDto.getTotalNormal());
							if (i.getTotalParent() != null)
								i.setTotalParent(i.getTotalParent() + animalReportDataFormDto.getTotalParent());
							else
								i.setTotalParent(animalReportDataFormDto.getTotalParent());
							if (i.getUnGenderChild() != null)
								i.setUnGenderChild(i.getUnGenderChild() + animalReportDataFormDto.getUnGenderChild());
							else
								i.setUnGenderChild(animalReportDataFormDto.getUnGenderChild());
							if (i.getUnGenderChildUnder1YO() != null)
								i.setUnGenderChildUnder1YO(i.getUnGenderChildUnder1YO()
										+ animalReportDataFormDto.getUnGenderChildUnder1YO());
							else
								i.setUnGenderChildUnder1YO(animalReportDataFormDto.getUnGenderChildUnder1YO());
							if (i.getUnGenderParent() != null)
								i.setUnGenderParent(
										i.getUnGenderParent() + animalReportDataFormDto.getUnGenderParent());
							else
								i.setUnGenderParent(animalReportDataFormDto.getUnGenderParent());
							// i.setYear(animalReportDataFormDto.getYear());
							i.getList().add(animalReportDataFormDto);
						}
					}
				}
			}
			if (listDistrict != null && listDistrict.size() > 0 && listWard != null && listWard.size() > 0) {
				for (AnimalReportDataFromWardDto ward : listWard) {
					// sort list cơ sở theo thứ tự để gộp 1 cơ sở vào 1 nhóm
					if (ward.getList() != null && ward.getList().size() > 0) {
						Collections.sort(ward.getList(), new SortFarm());
					}
					for (AnimalReportDataFromDistrictDto district : listDistrict) {
						if (district.getListWard() == null) {
							district.setListWard(new ArrayList<AnimalReportDataFromWardDto>());
						}
						if (district.getId().equals(ward.getParentWardId())) {
							district.getListWard().add(ward);

							if (district.getFemaleBearHasChip() != null)
								district.setFemaleBearHasChip(
										district.getFemaleBearHasChip() + ward.getFemaleBearHasChip());
							else
								district.setFemaleBearHasChip(ward.getFemaleBearHasChip());

							if (district.getFemaleBearNoChip() != null)
								district.setFemaleBearNoChip(
										district.getFemaleBearNoChip() + ward.getFemaleBearNoChip());
							else
								district.setFemaleBearNoChip(ward.getFemaleBearNoChip());
							if (district.getFemaleChild() != null)
								district.setFemaleChild(district.getFemaleChild() + ward.getFemaleChild());
							else
								district.setFemaleChild(ward.getFemaleChild());
							if (district.getFemaleChildUnder1YO() != null)
								district.setFemaleChildUnder1YO(
										district.getFemaleChildUnder1YO() + ward.getFemaleChildUnder1YO());
							else
								district.setFemaleChildUnder1YO(ward.getFemaleChildUnder1YO());
							if (district.getFemaleGilts() != null)
								district.setFemaleGilts(district.getFemaleGilts() + ward.getFemaleGilts());
							else
								district.setFemaleGilts(ward.getFemaleGilts());
							if (district.getFemaleParent() != null)
								district.setFemaleParent(district.getFemaleParent() + ward.getFemaleParent());
							else
								district.setFemaleParent(ward.getFemaleParent());
							if (district.getMaleBearHasChip() != null)
								district.setMaleBearHasChip(district.getMaleBearHasChip() + ward.getMaleBearHasChip());
							else
								district.setMaleBearHasChip(ward.getMaleBearHasChip());
							if (district.getMaleBearNoChip() != null)
								district.setMaleBearNoChip(district.getMaleBearNoChip() + ward.getMaleBearNoChip());
							else
								district.setMaleBearNoChip(ward.getMaleBearNoChip());
							if (district.getMaleChild() != null)
								district.setMaleChild(district.getMaleChild() + ward.getMaleChild());
							else
								district.setMaleChild(ward.getMaleChild());
							if (district.getMaleChildUnder1YO() != null)
								district.setMaleChildUnder1YO(
										district.getMaleChildUnder1YO() + ward.getMaleChildUnder1YO());
							else
								district.setMaleChildUnder1YO(ward.getMaleChildUnder1YO());
							if (district.getMaleGilts() != null)
								district.setMaleGilts(district.getMaleGilts() + ward.getMaleGilts());
							else
								district.setMaleGilts(ward.getMaleGilts());
							if (district.getMaleParent() != null)
								district.setMaleParent(district.getMaleParent() + ward.getMaleParent());
							else
								district.setMaleParent(ward.getMaleParent());

							// i.setMonth(animalReportDataFormDto.getMonth());
							if (district.getTotalBearHasChip() != null)
								district.setTotalBearHasChip(
										district.getTotalBearHasChip() + ward.getTotalBearHasChip());
							else
								district.setTotalBearHasChip(ward.getTotalBearHasChip());
							if (district.getTotalBearNoChip() != null)
								district.setTotalBearNoChip(district.getTotalBearNoChip() + ward.getTotalBearNoChip());
							else
								district.setTotalBearNoChip(ward.getTotalBearNoChip());
							if (district.getTotalChild() != null)
								district.setTotalChild(district.getTotalChild() + ward.getTotalChild());
							else
								district.setTotalChild(ward.getTotalChild());
							if (district.getTotalChildUnder1YO() != null)
								district.setTotalChildUnder1YO(
										district.getTotalChildUnder1YO() + ward.getTotalChildUnder1YO());
							else
								district.setTotalChildUnder1YO(ward.getTotalChildUnder1YO());
							if (district.getTotalGilts() != null)
								district.setTotalGilts(district.getTotalGilts() + ward.getTotalGilts());
							else
								district.setTotalGilts(ward.getTotalGilts());
							if (district.getTotalNormal() != null)
								district.setTotalNormal(district.getTotalNormal() + ward.getTotalNormal());
							else
								district.setTotalNormal(ward.getTotalNormal());
							if (district.getTotalParent() != null)
								district.setTotalParent(district.getTotalParent() + ward.getTotalParent());
							else
								district.setTotalParent(ward.getTotalParent());
							if (district.getUnGenderChild() != null)
								district.setUnGenderChild(district.getUnGenderChild() + ward.getUnGenderChild());
							else
								district.setUnGenderChild(ward.getUnGenderChild());
							if (district.getUnGenderChildUnder1YO() != null)
								district.setUnGenderChildUnder1YO(
										district.getUnGenderChildUnder1YO() + ward.getUnGenderChildUnder1YO());
							else
								district.setUnGenderChildUnder1YO(ward.getUnGenderChildUnder1YO());
							if (district.getUnGenderParent() != null)
								district.setUnGenderParent(district.getUnGenderParent() + ward.getUnGenderParent());
							else
								district.setUnGenderParent(ward.getUnGenderParent());
						}
					}
				}
			}
			if (listCity != null && listCity.size() > 0 && listDistrict != null && listDistrict.size() > 0) {
				for (AnimalReportDataFromDistrictDto district : listDistrict) {
					for (AnimalReportDataFromCityDto city : listCity) {
						if (city.getListDistrict() == null) {
							city.setListDistrict(new ArrayList<AnimalReportDataFromDistrictDto>());
						}
						if (city.getId().equals(district.getParentDistrictId())) {
							city.getListDistrict().add(district);

							if (city.getFemaleBearHasChip() != null)
								city.setFemaleBearHasChip(
										city.getFemaleBearHasChip() + district.getFemaleBearHasChip());
							else
								city.setFemaleBearHasChip(district.getFemaleBearHasChip());

							if (city.getFemaleBearNoChip() != null)
								city.setFemaleBearNoChip(city.getFemaleBearNoChip() + district.getFemaleBearNoChip());
							else
								city.setFemaleBearNoChip(district.getFemaleBearNoChip());
							if (city.getFemaleChild() != null)
								city.setFemaleChild(city.getFemaleChild() + district.getFemaleChild());
							else
								city.setFemaleChild(district.getFemaleChild());
							if (city.getFemaleChildUnder1YO() != null)
								city.setFemaleChildUnder1YO(
										city.getFemaleChildUnder1YO() + district.getFemaleChildUnder1YO());
							else
								city.setFemaleChildUnder1YO(district.getFemaleChildUnder1YO());
							if (city.getFemaleGilts() != null)
								city.setFemaleGilts(city.getFemaleGilts() + district.getFemaleGilts());
							else
								city.setFemaleGilts(district.getFemaleGilts());
							if (city.getFemaleParent() != null)
								city.setFemaleParent(city.getFemaleParent() + district.getFemaleParent());
							else
								city.setFemaleParent(district.getFemaleParent());
							if (city.getMaleBearHasChip() != null)
								city.setMaleBearHasChip(city.getMaleBearHasChip() + district.getMaleBearHasChip());
							else
								city.setMaleBearHasChip(district.getMaleBearHasChip());
							if (city.getMaleBearNoChip() != null)
								city.setMaleBearNoChip(city.getMaleBearNoChip() + district.getMaleBearNoChip());
							else
								city.setMaleBearNoChip(district.getMaleBearNoChip());
							if (city.getMaleChild() != null)
								city.setMaleChild(city.getMaleChild() + district.getMaleChild());
							else
								city.setMaleChild(district.getMaleChild());
							if (city.getMaleChildUnder1YO() != null)
								city.setMaleChildUnder1YO(
										city.getMaleChildUnder1YO() + district.getMaleChildUnder1YO());
							else
								city.setMaleChildUnder1YO(district.getMaleChildUnder1YO());
							if (city.getMaleGilts() != null)
								city.setMaleGilts(city.getMaleGilts() + district.getMaleGilts());
							else
								city.setMaleGilts(district.getMaleGilts());
							if (city.getMaleParent() != null)
								city.setMaleParent(city.getMaleParent() + district.getMaleParent());
							else
								city.setMaleParent(district.getMaleParent());

							// i.setMonth(animalReportDataFormDto.getMonth());
							if (city.getTotalBearHasChip() != null)
								city.setTotalBearHasChip(city.getTotalBearHasChip() + district.getTotalBearHasChip());
							else
								city.setTotalBearHasChip(district.getTotalBearHasChip());
							if (city.getTotalBearNoChip() != null)
								city.setTotalBearNoChip(city.getTotalBearNoChip() + district.getTotalBearNoChip());
							else
								city.setTotalBearNoChip(district.getTotalBearNoChip());
							if (city.getTotalChild() != null)
								city.setTotalChild(city.getTotalChild() + district.getTotalChild());
							else
								city.setTotalChild(district.getTotalChild());
							if (city.getTotalChildUnder1YO() != null)
								city.setTotalChildUnder1YO(
										city.getTotalChildUnder1YO() + district.getTotalChildUnder1YO());
							else
								city.setTotalChildUnder1YO(district.getTotalChildUnder1YO());
							if (city.getTotalGilts() != null)
								city.setTotalGilts(city.getTotalGilts() + district.getTotalGilts());
							else
								city.setTotalGilts(district.getTotalGilts());
							if (city.getTotalNormal() != null)
								city.setTotalNormal(city.getTotalNormal() + district.getTotalNormal());
							else
								city.setTotalNormal(district.getTotalNormal());
							if (city.getTotalParent() != null)
								city.setTotalParent(city.getTotalParent() + district.getTotalParent());
							else
								city.setTotalParent(district.getTotalParent());
							if (city.getUnGenderChild() != null)
								city.setUnGenderChild(city.getUnGenderChild() + district.getUnGenderChild());
							else
								city.setUnGenderChild(district.getUnGenderChild());
							if (city.getUnGenderChildUnder1YO() != null)
								city.setUnGenderChildUnder1YO(
										city.getUnGenderChildUnder1YO() + district.getUnGenderChildUnder1YO());
							else
								city.setUnGenderChildUnder1YO(district.getUnGenderChildUnder1YO());
							if (city.getUnGenderParent() != null)
								city.setUnGenderParent(city.getUnGenderParent() + district.getUnGenderParent());
							else
								city.setUnGenderParent(district.getUnGenderParent());
						}
					}
				}
			}
		}
		try {
			// sort theo tỉnh > huyện> xã > mã loài> tên hộ
			if (listCity != null && listCity.size() > 0) {
				Collections.sort(listCity, new SortCity());
				for (AnimalReportDataFromCityDto city : listCity) {
					if (city.getListDistrict() != null && city.getListDistrict().size() > 0) {
						Collections.sort(city.getListDistrict(), new SortDistrict());
						for (AnimalReportDataFromDistrictDto d : city.getListDistrict()) {
							if (d.getListWard() != null && d.getListWard().size() > 0) {
								Collections.sort(d.getListWard(), new SortWard());
								for (AnimalReportDataFromWardDto ward : listWard) {
									if (ward.getList() != null && ward.getList().size() > 0) {
										Collections.sort(ward.getList(), new SortAnimal());
										Collections.sort(ward.getList(), new SortFarmString());
									}
								}
							}
						}
					}
				}
			}
			String subTitle = "";
			if (dto.getProvinceId() != null) {
				FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
				if (pronvince != null && pronvince.getName() != null) {
					subTitle += " " + pronvince.getName();
					if (dto.getDistrictId() != null) {
						FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
						if (district != null && district.getName() != null) {
							subTitle += ", " + district.getName();
							if (dto.getCommuneId() != null) {
								FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository
										.findById(dto.getCommuneId());
								if (commune != null && commune.getName() != null) {
									subTitle += ", " + commune.getName();
								}
							}
						}
					}
				}
			}
//			 if(dto.getYear() != null) {
//			 subTitle += " - Năm "+ dto.getYear();
//			 }
			Resource resource = resourceLoader.getResource("classpath:BaocaoDVgaynuoitheoNd06.xlsx");
			InputStream ip = resource.getInputStream();
			ImportExportExcelUtil.exportReportAnimalDangerousCitesToExcelByForm(listCity, subTitle,
					response.getOutputStream(), ip);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BaocaoDVgaynuoitheoNd06.xlsx");
			response.flushBuffer();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/* Bao cao DV gay nuoi theo Nd 06 quy III-2020 -native query */
	@RequestMapping(value = "/exportReportAnimalDangerousCitesToExcelByFormNativeQuery", method = {
			RequestMethod.POST })
	// @Secured({ Const.ROLE_ADMIN, })
	public void exportReportAnimalDangerousCitesToExcelByFormNativeQuery(WebRequest request,
			HttpServletResponse response, @RequestBody AnimalReportDataSearchDto dto) {
		List<Report18Dto> list = reportService.reportActivityOfEndangeredPreciousRareNormarlAndCitesNativeQuery(dto);
		Hashtable<Long, Report18CityDto> hashCity = new Hashtable<Long, Report18CityDto>();
		Hashtable<Long, Report18DistrictDto> hashDisTrict = new Hashtable<Long, Report18DistrictDto>();
		Hashtable<Long, Report18WardDto> hashWard = new Hashtable<Long, Report18WardDto>();
		List<Report18CityDto> listCity = new ArrayList<Report18CityDto>();
		List<Report18DistrictDto> listDistrict = new ArrayList<Report18DistrictDto>();
		List<Report18WardDto> listWard = new ArrayList<Report18WardDto>();
		if (list != null && list.size() > 0) {
			for (Report18Dto report18Dto : list) {
				Report18CityDto city = hashCity.get(report18Dto.getProvId().longValue());
				if (city == null) {
					city = new Report18CityDto();
					city.setId(report18Dto.getProvId().longValue());
					city.setName(report18Dto.getProvName());
					hashCity.put(city.getId(), city);
					listCity.add(city);
				}
				Report18DistrictDto item = hashDisTrict.get(report18Dto.getDisId().longValue());
				if (item == null) {
					item = new Report18DistrictDto();
					item.setId(report18Dto.getDisId().longValue());
					item.setName(report18Dto.getDisName());
					item.setParentDistrictId(report18Dto.getProvId().longValue());
					hashDisTrict.put(item.getId(), item);
					listDistrict.add(item);
				}
				Report18WardDto itemWard = hashWard.get(report18Dto.getWardId().longValue());
				if (itemWard == null) {
					itemWard = new Report18WardDto();
					itemWard.setId(report18Dto.getWardId().longValue());
					itemWard.setName(report18Dto.getWardName());
					itemWard.setParentWardId(report18Dto.getDisId().longValue());
					itemWard.setList(new ArrayList<Report18Dto>());
					itemWard.getList().add(report18Dto);

					if (report18Dto.getTotal() != null)
						itemWard.setTotal(report18Dto.getTotal());
					else
						itemWard.setTotal(0);
					if (report18Dto.getTotalParent() != null)
						itemWard.setTotalParent(report18Dto.getTotalParent());
					else
						itemWard.setTotalParent(0);
					if (report18Dto.getMaleParent() != null)
						itemWard.setMaleParent(report18Dto.getMaleParent());
					else
						itemWard.setMaleParent(0);
					if (report18Dto.getFemaleParent() != null)
						itemWard.setFemaleParent(report18Dto.getFemaleParent());
					else
						itemWard.setFemaleParent(0);
					if (report18Dto.getTotalGilts() != null)
						itemWard.setTotalGilts(report18Dto.getTotalGilts());
					else
						itemWard.setTotalGilts(0);
					if (report18Dto.getFemaleGilts() != null)
						itemWard.setFemaleGilts(report18Dto.getFemaleGilts());
					else
						itemWard.setFemaleGilts(0);

					if (report18Dto.getMaleGilts() != null)
						itemWard.setMaleGilts(report18Dto.getMaleGilts());
					else
						itemWard.setMaleGilts(0);

					if (report18Dto.getTotalChildUnder1YO() != null)
						itemWard.setTotalChildUnder1YO(report18Dto.getTotalChildUnder1YO());
					else
						itemWard.setTotalChildUnder1YO(0);
					if (report18Dto.getTotalChildOver1YO() != null)
						itemWard.setTotalChildOver1YO(report18Dto.getTotalChildOver1YO());
					else
						itemWard.setTotalChildOver1YO(0);
					if (report18Dto.getMaleChildOver1YearOld() != null)
						itemWard.setMaleChildOver1YearOld(report18Dto.getMaleChildOver1YearOld());
					else
						itemWard.setMaleChildOver1YearOld(0);
					if (report18Dto.getFemaleChildOver1YearOld() != null)
						itemWard.setFemaleChildOver1YearOld(report18Dto.getFemaleChildOver1YearOld());
					else
						itemWard.setFemaleChildOver1YearOld(0);
					if (report18Dto.getUnGenderChildOver1YearOld() != null)
						itemWard.setUnGenderChildOver1YearOld(report18Dto.getUnGenderChildOver1YearOld());
					else
						itemWard.setUnGenderChildOver1YearOld(0);
					itemWard.setMonth(report18Dto.getMonth());
					itemWard.setYear(report18Dto.getYear());

					hashWard.put(itemWard.getId(), itemWard);
					listWard.add(itemWard);
				} else {
					// itemWard.getList().add(animalReportDataFormDto);
					for (Report18WardDto i : listWard) {
						if (i.getId().equals(itemWard.getId())) {
							if (i.getTotal() == null) {
								i.setTotal(0);
							}
							if (report18Dto.getTotal() != null)
								i.setTotal(i.getTotal() + report18Dto.getTotal());

							if (i.getTotalParent() == null) {
								i.setTotalParent(0);
							}
							if (i.getTotalParent() != null && report18Dto.getTotalParent() != null)
								i.setTotalParent(i.getTotalParent() + report18Dto.getTotalParent());
							if (i.getMaleParent() == null) {
								i.setMaleParent(0);
							}
							if (i.getMaleParent() != null && report18Dto.getMaleParent() != null)
								i.setMaleParent(i.getMaleParent() + report18Dto.getMaleParent());
							if (i.getFemaleParent() == null) {
								i.setFemaleParent(0);
							}
							if (i.getFemaleParent() != null && report18Dto.getFemaleParent() != null)
								i.setFemaleParent(i.getFemaleParent() + report18Dto.getFemaleParent());
							if (i.getTotalGilts() == null) {
								i.setTotalGilts(0);
							}
							if (i.getTotalGilts() != null && report18Dto.getTotalGilts() != null)
								i.setTotalGilts(i.getTotalGilts() + report18Dto.getTotalGilts());
							if (i.getMaleGilts() == null) {
								i.setMaleGilts(0);
							}
							if (i.getMaleGilts() != null && report18Dto.getMaleGilts() != null)
								i.setMaleGilts(i.getMaleGilts() + report18Dto.getMaleGilts());

							if (i.getFemaleGilts() == null) {
								i.setFemaleGilts(0);
							}
							if (i.getFemaleGilts() != null && report18Dto.getFemaleGilts() != null)
								i.setFemaleGilts(i.getFemaleGilts() + report18Dto.getFemaleGilts());

							if (i.getTotalChildUnder1YO() == null) {
								i.setTotalChildUnder1YO(0);
							}
							if (i.getTotalChildUnder1YO() != null && report18Dto.getTotalChildUnder1YO() != null)
								i.setTotalChildUnder1YO(
										i.getTotalChildUnder1YO() + report18Dto.getTotalChildUnder1YO());

							if (i.getTotalChildOver1YO() == null) {
								i.setTotalChildOver1YO(0);
							}
							if (report18Dto.getTotalChildOver1YO() != null) {
								i.setTotalChildOver1YO(i.getTotalChildOver1YO() + report18Dto.getTotalChildOver1YO());
							}

							if (i.getMaleChildOver1YearOld() == null) {
								i.setMaleChildOver1YearOld(0);
							}
							if (i.getMaleChildOver1YearOld() != null
									&& report18Dto.getMaleChildOver1YearOld() != null) {
								i.setMaleChildOver1YearOld(
										i.getMaleChildOver1YearOld() + report18Dto.getMaleChildOver1YearOld());
							}
							if (i.getFemaleChildOver1YearOld() == null) {
								i.setFemaleChildOver1YearOld(0);
							}
							if (i.getFemaleChildOver1YearOld() != null
									&& report18Dto.getFemaleChildOver1YearOld() != null) {
								i.setFemaleChildOver1YearOld(
										i.getFemaleChildOver1YearOld() + report18Dto.getFemaleChildOver1YearOld());
							}
							if (i.getUnGenderChildOver1YearOld() == null) {
								i.setUnGenderChildOver1YearOld(0);
							}
							if (i.getUnGenderChildOver1YearOld() != null
									&& report18Dto.getUnGenderChildOver1YearOld() != null) {
								i.setUnGenderChildOver1YearOld(
										i.getUnGenderChildOver1YearOld() + report18Dto.getUnGenderChildOver1YearOld());
							}

							// i.setYear(animalReportDataFormDto.getYear());
							i.getList().add(report18Dto);
						}
					}
				}
			}
			if (listDistrict != null && listDistrict.size() > 0 && listWard != null && listWard.size() > 0) {
				for (Report18WardDto ward : listWard) {
					// sort list cơ sở theo thứ tự để gộp 1 cơ sở vào 1 nhóm
					if (ward.getList() != null && ward.getList().size() > 0) {
						Collections.sort(ward.getList(), new SortFarm18());
					}
					for (Report18DistrictDto district : listDistrict) {
						if (district.getListWard() == null) {
							district.setListWard(new ArrayList<Report18WardDto>());
						}
						if (district.getId().equals(ward.getParentWardId())) {
							district.getListWard().add(ward);
							if (district.getTotal() == null) {
								district.setTotal(0);
							}
							if (district.getTotal() != null && ward.getTotal() != null)
								district.setTotal(district.getTotal() + ward.getTotal());

							if (district.getTotalParent() == null) {
								district.setTotalParent(0);
							}
							if (district.getTotalParent() != null && ward.getTotalParent() != null)
								district.setTotalParent(district.getTotalParent() + ward.getTotalParent());

							if (district.getFemaleParent() == null) {
								district.setFemaleParent(0);
							}
							if (district.getFemaleParent() != null && ward.getFemaleParent() != null)
								district.setFemaleParent(district.getFemaleParent() + ward.getFemaleParent());
							if (district.getMaleParent() == null) {
								district.setMaleParent(0);
							}
							if (district.getMaleParent() != null && ward.getMaleParent() != null)
								district.setMaleParent(district.getMaleParent() + ward.getMaleParent());
							if (district.getTotalGilts() == null) {
								district.setTotalGilts(0);
							}
							if (district.getTotalGilts() != null && ward.getTotalGilts() != null)
								district.setTotalGilts(district.getTotalGilts() + ward.getTotalGilts());
							if (district.getMaleGilts() == null) {
								district.setMaleGilts(0);
							}
							if (district.getMaleGilts() != null && ward.getMaleGilts() != null)
								district.setMaleGilts(district.getMaleGilts() + ward.getMaleGilts());
							if (district.getFemaleGilts() == null) {
								district.setFemaleGilts(0);
							}
							if (district.getFemaleGilts() != null && ward.getFemaleGilts() != null)
								district.setFemaleGilts(district.getFemaleGilts() + ward.getFemaleGilts());

							if (district.getTotalChildUnder1YO() == null) {
								district.setTotalChildUnder1YO(0);
							}
							if (district.getTotalChildUnder1YO() != null && ward.getTotalChildUnder1YO() != null)
								district.setTotalChildUnder1YO(
										district.getTotalChildUnder1YO() + ward.getTotalChildUnder1YO());

							if (district.getTotalChildOver1YO() == null) {
								district.setTotalChildOver1YO(0);
							}
							if (district.getTotalChildOver1YO() != null && ward.getTotalChildOver1YO() != null)
								district.setTotalChildOver1YO(
										district.getTotalChildOver1YO() + ward.getTotalChildOver1YO());
							if (district.getUnGenderChildOver1YearOld() == null) {
								district.setUnGenderChildOver1YearOld(0);
							}
							if (district.getUnGenderChildOver1YearOld() != null
									&& ward.getUnGenderChildOver1YearOld() != null)
								district.setUnGenderChildOver1YearOld(
										district.getUnGenderChildOver1YearOld() + ward.getUnGenderChildOver1YearOld());
							if (district.getMaleChildOver1YearOld() == null) {
								district.setMaleChildOver1YearOld(0);
							}
							if (district.getMaleChildOver1YearOld() != null && ward.getMaleChildOver1YearOld() != null)
								district.setMaleChildOver1YearOld(
										district.getMaleChildOver1YearOld() + ward.getMaleChildOver1YearOld());
							if (district.getFemaleChildOver1YearOld() == null) {
								district.setFemaleChildOver1YearOld(0);
							}
							if (district.getFemaleChildOver1YearOld() != null
									&& ward.getFemaleChildOver1YearOld() != null)
								district.setFemaleChildOver1YearOld(
										district.getFemaleChildOver1YearOld() + ward.getFemaleChildOver1YearOld());

						}
					}
				}
			}
			if (listCity != null && listCity.size() > 0 && listDistrict != null && listDistrict.size() > 0) {
				for (Report18DistrictDto district : listDistrict) {
					for (Report18CityDto city : listCity) {
						if (city.getListDistrict() == null) {
							city.setListDistrict(new ArrayList<Report18DistrictDto>());
						}
						if (city.getId().equals(district.getParentDistrictId())) {
							city.getListDistrict().add(district);

							if (city.getTotal() == null) {
								city.setTotal(0);
							}
							if (city.getTotal() != null)
								city.setTotal(city.getTotal() + district.getTotal());

							if (city.getTotalParent() == null) {
								city.setTotalParent(0);
							}
							if (city.getTotalParent() != null && district.getTotalParent() != null)
								city.setTotalParent(city.getTotalParent() + district.getTotalParent());
							if (city.getFemaleParent() == null) {
								city.setFemaleParent(0);
							}
							if (city.getFemaleParent() != null && district.getFemaleParent() != null)
								city.setFemaleParent(city.getFemaleParent() + district.getFemaleParent());

							if (city.getFemaleGilts() == null) {
								city.setFemaleGilts(0);
							}
							if (city.getFemaleGilts() != null && district.getFemaleGilts() != null)
								city.setFemaleGilts(city.getFemaleGilts() + district.getFemaleGilts());
							if (city.getMaleParent() == null) {
								city.setMaleParent(0);
							}
							if (city.getMaleParent() != null && district.getMaleParent() != null)
								city.setMaleParent(city.getMaleParent() + district.getMaleParent());

							if (city.getTotalGilts() == null) {
								city.setTotalGilts(0);
							}
							if (city.getTotalGilts() != null && district.getTotalGilts() != null)
								city.setTotalGilts(city.getTotalGilts() + district.getTotalGilts());
							if (city.getMaleGilts() == null) {
								city.setMaleGilts(0);
							}
							if (city.getMaleGilts() != null && district.getMaleGilts() != null)
								city.setMaleGilts(city.getMaleGilts() + district.getMaleGilts());
							if (city.getTotalChildUnder1YO() == null) {
								city.setTotalChildUnder1YO(0);
							}

							if (city.getTotalChildUnder1YO() != null && district.getTotalChildUnder1YO() != null)
								city.setTotalChildUnder1YO(
										city.getTotalChildUnder1YO() + district.getTotalChildUnder1YO());
							if (city.getTotalChildOver1YO() == null) {
								city.setTotalChildOver1YO(0);
							}
							if (city.getTotalChildOver1YO() != null && district.getTotalChildOver1YO() != null)
								city.setTotalChildOver1YO(
										city.getTotalChildOver1YO() + district.getTotalChildOver1YO());
							if (city.getUnGenderChildOver1YearOld() == null) {
								city.setUnGenderChildOver1YearOld(0);
							}
							if (city.getUnGenderChildOver1YearOld() != null
									&& district.getUnGenderChildOver1YearOld() != null)
								city.setUnGenderChildOver1YearOld(
										city.getUnGenderChildOver1YearOld() + district.getUnGenderChildOver1YearOld());
							if (city.getMaleChildOver1YearOld() == null) {
								city.setMaleChildOver1YearOld(0);
							}
							if (city.getMaleChildOver1YearOld() != null && district.getMaleChildOver1YearOld() != null)
								city.setMaleChildOver1YearOld(
										city.getMaleChildOver1YearOld() + district.getMaleChildOver1YearOld());
							if (city.getFemaleChildOver1YearOld() == null) {
								city.setFemaleChildOver1YearOld(0);
							}
							if (city.getFemaleChildOver1YearOld() != null
									&& district.getFemaleChildOver1YearOld() != null)
								city.setFemaleChildOver1YearOld(
										city.getFemaleChildOver1YearOld() + district.getFemaleChildOver1YearOld());

						}
					}
				}
			}
		}
		try {
			// sort theo tỉnh > huyện> xã > mã loài> tên hộ
			if (listCity != null && listCity.size() > 0) {
				Collections.sort(listCity, new SortCity18());
				for (Report18CityDto city : listCity) {
					if (city.getListDistrict() != null && city.getListDistrict().size() > 0) {
						Collections.sort(city.getListDistrict(), new SortDistrict18());
						for (Report18DistrictDto d : city.getListDistrict()) {
							if (d.getListWard() != null && d.getListWard().size() > 0) {
								Collections.sort(d.getListWard(), new SortWard18());
								for (Report18WardDto ward : listWard) {
									if (ward.getList() != null && ward.getList().size() > 0) {
										Collections.sort(ward.getList(), new SortAnimal18());
										Collections.sort(ward.getList(), new SortFarmString18());
									}
								}
							}
						}
					}
				}
			}
			String subTitle = "";
			if (dto.getProvinceId() != null) {
				FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
				if (pronvince != null && pronvince.getName() != null) {
					subTitle += " " + pronvince.getName();
					if (dto.getDistrictId() != null) {
						FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
						if (district != null && district.getName() != null) {
							subTitle += ", " + district.getName();
							if (dto.getCommuneId() != null) {
								FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository
										.findById(dto.getCommuneId());
								if (commune != null && commune.getName() != null) {
									subTitle += ", " + commune.getName();
								}
							}
						}
					}
				}
			}
//			 if(dto.getYear() != null) {
//			 subTitle += " - Năm "+ dto.getYear();
//			 }
			Resource resource = resourceLoader.getResource("classpath:BaocaoDVgaynuoitheoNd06.xlsx");
			InputStream ip = resource.getInputStream();
			ImportExportExcelUtil.exportReportAnimalDangerousCitesToExcelByFormNativeQuery(listCity, subTitle,
					response.getOutputStream(), ip);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BaocaoDVgaynuoitheoNd06.xlsx");
			response.flushBuffer();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/exportReportAnimalCurrentByFamilyToExcel", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportReportAnimalCurrentByFamilyToExcel(WebRequest request, HttpServletResponse response,
			@RequestBody AnimalReportDataSearchDto dto) throws IOException {
		List<Report18Dto> list = reportService.reportCurrentStatusByFamilyNewByAdministrativeUnit(dto);
		String subTitle = "";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				subTitle += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						subTitle += ", " + district.getName();
						if (dto.getCommuneId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository
									.findById(dto.getCommuneId());
							if (commune != null && commune.getName() != null) {
								subTitle += ", " + commune.getName();
							}
						}
					}
				}
			}
		}
		if (dto.getYear() != null) {
			subTitle += " - Năm " + dto.getYear();
		}
		ImportExportExcelUtil.exportReportAnimalCurrentByFamilyToExcel(list, subTitle, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuBaoCaoHoatDongNuoiDVHDTheoHo.xls");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/updateListFromFileImport/{sheetIndex}/{rowStart}", method = RequestMethod.POST)
	@Secured({ "ROLE_ADMIN", WLConstant.ROLE_SDAH, WLConstant.ROLE_DISTRICT, WLConstant.ROLE_DLP })
	@ResponseBody
	public ResponseEntity<ImportResultDto<DataDvhdDto>> updateListFromFileImport(
			@RequestParam("uploadfile") MultipartFile uploadfile, @PathVariable("sheetIndex") int sheetIndex,
			@PathVariable("rowStart") int rowStart) throws IOException {
		// if(WLConstant.isImporting) {
		// Object res = new Object();
		// res="Hàm import Đang chạy";
		// return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		// }
		// else {
		WLConstant.isImporting = true;
		try {
			List<DataDvhdDto> list = ImportExportExcelUtil.getForm16AFromExcelFile(uploadfile.getInputStream(),
					sheetIndex, rowStart);
			System.out.println(list.size());
			WLConstant.isImporting = false;
			// ImportResultDto<DataDvhdDto> ret =
			// reportPeriodService.updateFromFileImport(list);
			ImportResultDto<DataDvhdDto> ret = new ImportResultDto<DataDvhdDto>();
			for (DataDvhdDto rawData : list) {
				rawData = reportPeriodService.updateFromFileImportByOneRow(rawData);
				ret.setTotalRow(list.size());
				if (rawData != null && (rawData.getErrContent() == null || rawData.getErrContent().length() == 0)
						&& rawData.getFarm() != null) {
					ret.setTotalSuccess(ret.getTotalSuccess() + 1);
					List<Long> animalIds = reportPeriodService.getListAnimalIds(rawData.getFarmId(), rawData.getYear());
					// Cập nhật tới báo cáo theo năm
					animalReportDataService.updateAnimalReportDataByFarmAnimalYear(rawData.getFarmId(), animalIds,
							rawData.getYear());
					List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService
							.getAllAnimalLastReportedByRecordMonthDayIsNull(rawData.getFarmId(), null);
					if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
						FarmAnimalTotalDto farmAnimalTotalDto = listAnimalReportTotal.get(0);
						if (farmAnimalTotalDto != null) {
							try {
								FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
							} catch (Exception e) {
								// e.printStackTrace();
								continue;
							}

						}
					}
				} else {
					ret.setTotalErr(ret.getTotalErr() + 1);
					ret.getListErr().add(rawData);
				}
			}
			return new ResponseEntity<ImportResultDto<DataDvhdDto>>(ret, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			WLConstant.isImporting = false;
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// }
	}

	@RequestMapping(value = "/importlist", method = RequestMethod.POST)
	@Secured({ "ROLE_ADMIN" })
	@ResponseBody
	public ResponseEntity<?> importList(List<DataDvhdDto> list) throws IOException {
		try {
			WLConstant.isImporting = false;
			reportPeriodService.updateListFromFileImport(list);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			WLConstant.isImporting = false;
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/exportReportFormAnimalEgg16C", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportReportFormAnimalEgg16C(WebRequest request, HttpServletResponse response,
			@RequestBody ReportFormAnimalEggSearchDto dto) throws IOException {
		List<ReportFormAnimalEggDto> list = reportFormAnimalEggService.getList(dto);
		String titleHeader = "Dữ liệu biểu mẫu 16C";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getWardId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWardId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}

		if (dto.getDay() != null) {
			titleHeader += " - Ngày " + dto.getDay();
		}
		if (dto.getMonth() != null) {
			titleHeader += " - Tháng " + dto.getMonth();
		}
		if (dto.getYear() != null) {
			titleHeader += " - Năm " + dto.getYear();
		}
		ImportExportExcelUtil.exportReportFormAnimalEgg16C(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuMau16C.xls");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/exportReportFormAnimalGiveBirth16D", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportReportFormAnimalGiveBirth16D(WebRequest request, HttpServletResponse response,
			@RequestBody ReportFormAnimalEggSearchDto dto) throws IOException {
		List<ReportFormAnimalGiveBirthDto> list = reportFormAnimalGiveBirthService.getList(dto);
		String titleHeader = "Dữ liệu biểu mẫu 16D";
		if (dto.getProvinceId() != null) {
			FmsAdministrativeUnit pronvince = fmsAdministrativeUnitRepository.findById(dto.getProvinceId());
			if (pronvince != null && pronvince.getName() != null) {
				titleHeader += " " + pronvince.getName();
				if (dto.getDistrictId() != null) {
					FmsAdministrativeUnit district = fmsAdministrativeUnitRepository.findById(dto.getDistrictId());
					if (district != null && district.getName() != null) {
						titleHeader += ", " + district.getName();
						if (dto.getWardId() != null) {
							FmsAdministrativeUnit commune = fmsAdministrativeUnitRepository.findById(dto.getWardId());
							if (commune != null && commune.getName() != null) {
								titleHeader += ", " + commune.getName();
							}
						}
					}
				}
			}
		}

		if (dto.getDay() != null) {
			titleHeader += " - Ngày " + dto.getDay();
		}
		if (dto.getMonth() != null) {
			titleHeader += " - Tháng " + dto.getMonth();
		}
		if (dto.getYear() != null) {
			titleHeader += " - Năm " + dto.getYear();
		}
		ImportExportExcelUtil.exportReportFormAnimalEgg16D(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuMau16D.xls");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	/* Mẫu file import dữ liệu mẫu 16 */
	@RequestMapping(value = "/downloadTemplateImportForm16", method = { RequestMethod.POST })
	// @Secured({ Const.ROLE_ADMIN, })
	public void downloadTemplateImportForm16(WebRequest request, HttpServletResponse response) {
		try {
			Resource resource = resourceLoader.getResource("classpath:TemplateImportDataForm16.xlsx");
			InputStream ip = resource.getInputStream();
			ImportExportExcelUtil.exportReportAnimalDangerousCitesToExcelByForm(null, null, response.getOutputStream(),
					ip);
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=TemplateImportDataForm16.xlsx");
			response.flushBuffer();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/exportForestProductsList", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportForestProductsList(WebRequest request, HttpServletResponse response,
			@RequestBody AnimalReportDataSearchDto dto) throws IOException {

		Workbook wbook = null;
		try (InputStream template = resourceLoader.getResource("classpath:DanhSachBangKeLamSan.xlsx")
				.getInputStream()) {
			XSSFWorkbook tmp = new XSSFWorkbook(template);
			Sheet sheet = tmp.getSheetAt(0);
			wbook = new SXSSFWorkbook(tmp, 100);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (wbook == null) {
			throw new RuntimeException();
		}

		Page<ForestProductsListDto> list = forestProductsListService.getSearchByPage(dto, 1, 100000000);
		List<ForestProductsListDto> results = new ArrayList<ForestProductsListDto>();
		if (list != null && list.getContent().size() > 0) {
			for (ForestProductsListDto d : list.getContent()) {
				if (d.getDetails() != null && d.getDetails().size() > 0) {
					for (ForestProductsListDetailDto detail : d.getDetails()) {
						ForestProductsListDto c = new ForestProductsListDto(d);
						c.setDetail(detail);
						results.add(c);
					}
				}
			}
		}

		ImportExportExcelUtil.exportForestProductsList(results, dto, wbook, response.getOutputStream());

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DanhSachBangKeLamSan.xlsx");
		response.flushBuffer();

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/exportAnimal", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportAnimalToExcelFile(WebRequest request, HttpServletResponse response,
			@RequestBody AnimalSearchDto dto) throws IOException {
		List<AnimalDto> list = animalService.searchAnimalDto(dto, 1, 10000).getContent();
		ImportExportExcelUtil.exportAnimal(list, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuCacLoaiDongVatHoangDa.xls");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/updateListFromFileImportByListDto", method = RequestMethod.POST)
	@Secured({ "ROLE_ADMIN", WLConstant.ROLE_SDAH, WLConstant.ROLE_DISTRICT, WLConstant.ROLE_DLP })
	@ResponseBody
	public ResponseEntity<ImportResultDto<DataDvhdDto>> updateListFromFileImportByListDto(
			@RequestBody List<DataDvhdDto> list) throws IOException {

		WLConstant.isImporting = true;
		try {
			if (list == null || list.size() <= 0) {
				return null;
			}

			WLConstant.isImporting = false;
			ImportResultDto<DataDvhdDto> ret = new ImportResultDto<DataDvhdDto>();
			for (DataDvhdDto rawData : list) {
				rawData = reportPeriodService.updateFromFileImportByOneRow(rawData);
				ret.setTotalRow(list.size());
				if (rawData != null && (rawData.getErrContent() == null || rawData.getErrContent().length() == 0)
						&& rawData.getFarm() != null) {
					ret.setTotalSuccess(ret.getTotalSuccess() + 1);
//					List<Long> animalIds = reportPeriodService.getListAnimalIds(rawData.getFarmId(), rawData.getYear());
					// Cập nhật tới báo cáo theo năm
//					animalReportDataService.updateAnimalReportDataByFarmAnimalYear(rawData.getFarmId(), animalIds, rawData.getYear());
//					Cập nhật bản đồ
//					List<FarmAnimalTotalDto> listAnimalReportTotal = animalReportDataService.getAllAnimalLastReportedByRecordMonthDayIsNull(rawData.getFarmId(), null);
//					if (listAnimalReportTotal != null && listAnimalReportTotal.size() > 0) {
//						FarmAnimalTotalDto farmAnimalTotalDto = listAnimalReportTotal.get(0);
//						if (farmAnimalTotalDto != null) {
//							try {
//								FarmMapServiceUtil.pushFarmAnimalMap(farmAnimalTotalDto);
//							} catch (Exception e) {
//								continue;
//							}
//
//						}
//					}
				} else {
					ret.setTotalErr(ret.getTotalErr() + 1);
					ret.getListErr().add(rawData);
				}
			}
			return new ResponseEntity<ImportResultDto<DataDvhdDto>>(ret, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			WLConstant.isImporting = false;
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/uploadFileAndCallApiUpdateListFromFileImportByListDto/{sheetIndex}/{rowStart}", method = RequestMethod.POST)
	@Secured({ "ROLE_ADMIN", WLConstant.ROLE_SDAH, WLConstant.ROLE_DISTRICT, WLConstant.ROLE_DLP })
	@ResponseBody
	public ResponseEntity<ImportResultDto<DataDvhdDto>> uploadFileAndCallApiUpdateListFromFileImportByListDto(
			@RequestParam("uploadfile") MultipartFile uploadfile, @PathVariable("sheetIndex") int sheetIndex,
			@PathVariable("rowStart") int rowStart) throws IOException {

//		WLConstant.isImporting = true;
//		try {
//			List<DataDvhdDto> list = ImportExportExcelUtil.getForm16AFromExcelFile(uploadfile.getInputStream(),
//					sheetIndex, rowStart);
//			ImportResultDto<DataDvhdDto> ret = new ImportResultDto<DataDvhdDto>();
//			String url = env.getProperty("url.call_api_update_list_from_file_import_by_list_dto");
//			ImpotList16Dto dto = new  ImpotList16Dto();
//			dto.setList(list);
//			ret = HelperUtil.callApiUpdateListFromFileImportByListDto(url, dto);
//			return new ResponseEntity<ImportResultDto<DataDvhdDto>>(ret, HttpStatus.OK);
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//			WLConstant.isImporting = false;
//			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//		}

		try {
			List<DataDvhdDto> list = ImportExportExcelUtil.getForm16AFromExcelFile(uploadfile.getInputStream(),
					sheetIndex, rowStart);
			// List<DataDvhdDto> list = null;
			ImportResultDto<DataDvhdDto> ret = new ImportResultDto<DataDvhdDto>();
			String url = env.getProperty("url.call_api_update_list_from_file_import_by_list_dto");
			// String url
			// ="http://localhost:8098/wl/public/publicAPI/updateListFromFileImportByListDto";
			System.out.println(list.size());
			ImpotList16Dto dto = new ImpotList16Dto();
			dto.setList(list);
			ret = HelperUtil.callApiUpdateListFromFileImportByListDto(url, dto);
			return new ResponseEntity<ImportResultDto<DataDvhdDto>>(ret, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			// WLConstant.isImporting = false;
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/uploadFileAndCallApiUpdateListFromFileImportByListDtoNew/{sheetIndex}/{rowStart}", method = RequestMethod.POST)
	@Secured({ "ROLE_ADMIN", WLConstant.ROLE_SDAH, WLConstant.ROLE_DISTRICT, WLConstant.ROLE_DLP })
	@ResponseBody
	public ResponseEntity<ImportResultDto<DataDvhdDto>> uploadFileAndCallApiUpdateListFromFileImportByListDtoNew(
			@RequestParam("uploadfile") MultipartFile uploadfile, @PathVariable("sheetIndex") int sheetIndex,
			@PathVariable("rowStart") int rowStart) throws IOException {
		try {
			List<DataDvhdDto> list = ImportExportExcelUtil.getForm16AFromExcelFileUpdate(uploadfile.getInputStream(),
					sheetIndex, rowStart);
			ImportResultDto<DataDvhdDto> ret = new ImportResultDto<DataDvhdDto>();
			String url = env.getProperty("url.call_api_update_list_from_file_import_by_list_dto");
			ImpotList16Dto dto = new ImpotList16Dto();
			dto.setList(list);

			ret = HelperUtil.callApiUpdateListFromFileImportByListDto(url, dto);

			return new ResponseEntity<ImportResultDto<DataDvhdDto>>(ret, HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/exportAdministrativeUnit/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<?> exportAdministrativeUnitToExcelFile(WebRequest request, HttpServletResponse response,
			@PathVariable("id") Long id) throws IOException {
		List<FmsAdministrativeUnitDto> list = fmsAdministrativeUnitService.getListWard(id);
		String titleHeader = "Danh sách đơn vị hành chính";
		if (id != null) {
			FmsAdministrativeUnit adm = fmsAdministrativeUnitRepository.findById(id);
			if (adm != null && adm.getName() != null) {
				titleHeader = titleHeader + " " + adm.getName();
			}
		}
		ImportExportExcelUtil.exportAdministrativeUnit(list, titleHeader, response.getOutputStream());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=DuLieuDonViHanhChinh.xls");
		response.flushBuffer();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@RequestMapping(value = "/getFileTutorialImportDataForm16", method = RequestMethod.GET)
	@ResponseBody
	public void getFileTutorialImportDataForm16(WebRequest request, HttpServletResponse response) throws IOException {
		try {
//			File file = ResourceUtils.getFile("classpath:HuongDanImportDuLieuBaoCao.doc");
			Resource resource = resourceLoader.getResource("classpath:HuongDanImportDuLieuBaoCao.doc");
//			byte[] data = FileUtils.readFileToByteArray(file);
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=" + resource.getFilename());
			response.setContentLength((int) resource.contentLength());
			FileCopyUtils.copy(resource.getInputStream(), response.getOutputStream());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@RequestMapping(value = "/getFileTemplateCodeAnime2019", method = RequestMethod.GET)
	@ResponseBody
	public void getFileTemplateCodeAnime2019(WebRequest request, HttpServletResponse response) throws IOException {

		Workbook wbook = null;

		try (InputStream template = resourceLoader.getResource("classpath:TemplateCodeAnime2019.xlsx")
				.getInputStream()) {
			XSSFWorkbook tmp = new XSSFWorkbook(template);
			Sheet sheet = tmp.getSheetAt(0);
			wbook = new SXSSFWorkbook(tmp, 5000);
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (wbook == null) {
			throw new RuntimeException();
		}

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=TemplateCodeAnime2019.xlsx");
		response.flushBuffer();

		try {
			wbook.write(response.getOutputStream());
			response.flushBuffer();
			response.getOutputStream().close();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

//		try {
//			Resource resource = resourceLoader.getResource("classpath:TemplateCodeAnime2019.xlsx");
//			InputStream ip = resource.getInputStream();
//			ImportExportExcelUtil.exportReportAnimalDangerousCitesToExcelByForm(null, null, response.getOutputStream(),
//					ip);
//			response.setContentType("application/vnd.ms-excel");
//			response.setHeader("Content-Disposition", "attachment; filename=TemplateCodeAnime2019.xlsx");
//			response.flushBuffer();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

}

class SortFarm implements Comparator<AnimalReportDataFormDto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(AnimalReportDataFormDto a, AnimalReportDataFormDto b) {
		try {
			if (a.getFarm() != null && a.getFarm().getId() != null && b.getFarm() != null
					&& b.getFarm().getId() != null) {
				return (int) (a.getFarm().getId() - b.getFarm().getId());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortFarm18 implements Comparator<Report18Dto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(Report18Dto a, Report18Dto b) {
		try {
			if (a.getFarmId() != null && b.getFarmId() != null) {
				return (int) (a.getFarmId().intValue() - b.getFarmId().intValue());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortFarmString implements Comparator<AnimalReportDataFormDto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(AnimalReportDataFormDto a, AnimalReportDataFormDto b) {
		try {
			if (a.getFarm() != null && a.getFarm().getName() != null && a.getFarm().getName().length() > 0
					&& b.getFarm() != null && b.getFarm().getName() != null && b.getFarm().getName().length() > 0) {
				return a.getFarm().getName().compareTo(b.getFarm().getName());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortFarmString18 implements Comparator<Report18Dto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(Report18Dto a, Report18Dto b) {
		try {
			if (a.getFarmName() != null && a.getFarmName().length() > 0 && b.getFarmName() != null
					&& b.getFarmName().length() > 0) {
				return a.getFarmName().compareTo(b.getFarmName());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortCity implements Comparator<AnimalReportDataFromCityDto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(AnimalReportDataFromCityDto a, AnimalReportDataFromCityDto b) {
		try {
			if (a.getName() != null && a.getName().length() > 0 && b.getName() != null && b.getName().length() > 0) {
				return a.getName().compareTo(b.getName());

			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortCity18 implements Comparator<Report18CityDto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(Report18CityDto a, Report18CityDto b) {
		try {
			if (a.getName() != null && a.getName().length() > 0 && b.getName() != null && b.getName().length() > 0) {
				return a.getName().compareTo(b.getName());

			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortDistrict implements Comparator<AnimalReportDataFromDistrictDto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(AnimalReportDataFromDistrictDto a, AnimalReportDataFromDistrictDto b) {
		try {
			if (a.getName() != null && a.getName().length() > 0 && b.getName() != null && b.getName().length() > 0) {
				return a.getName().compareTo(b.getName());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortDistrict18 implements Comparator<Report18DistrictDto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(Report18DistrictDto a, Report18DistrictDto b) {
		try {
			if (a.getName() != null && a.getName().length() > 0 && b.getName() != null && b.getName().length() > 0) {
				return a.getName().compareTo(b.getName());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortWard implements Comparator<AnimalReportDataFromWardDto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(AnimalReportDataFromWardDto a, AnimalReportDataFromWardDto b) {
		try {
			if (a.getName() != null && a.getName().length() > 0 && b.getName() != null && b.getName().length() > 0) {
				return a.getName().compareTo(b.getName());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortWard18 implements Comparator<Report18WardDto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(Report18WardDto a, Report18WardDto b) {
		try {
			if (a.getName() != null && a.getName().length() > 0 && b.getName() != null && b.getName().length() > 0) {
				return a.getName().compareTo(b.getName());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortAnimal implements Comparator<AnimalReportDataFormDto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(AnimalReportDataFormDto a, AnimalReportDataFormDto b) {
		try {
			if (a.getAnimal() != null && a.getAnimal().getCode() != null && b.getAnimal() != null
					&& b.getAnimal().getCode() != null) {
				return Integer.valueOf(a.getAnimal().getCode()) - Integer.valueOf(b.getAnimal().getCode());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}

class SortAnimal18 implements Comparator<Report18Dto> {
	// Used for sorting in ascending order of
	// roll number
	public int compare(Report18Dto a, Report18Dto b) {
		try {
			if (a.getAnimalCode() != null && b.getAnimalCode() != null) {
				return Integer.valueOf(a.getAnimalCode()) - Integer.valueOf(b.getAnimalCode());
			} else
				return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}

	}
}
