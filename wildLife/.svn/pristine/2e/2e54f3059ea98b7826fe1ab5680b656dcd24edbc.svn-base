package com.globits.wl.utils;

public class WLConstant {
	public static boolean isImporting = false;

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_DLP = "ROLE_DLP"; // cục chăn nuôi
	public static final String ROLE_SDAH = "ROLE_SDAH"; // tỉnh
	public static final String ROLE_DISTRICT = "ROLE_DISTRICT"; // huyện
	public static final String ROLE_WARD = "ROLE_WARD"; // xã
	public static final String ROLE_FAMER = "ROLE_FAMER";
	public static final String ROLE_USER = "ROLE_USER";
	public static final String ROLE_SDAH_VIEW = "ROLE_SDAH_VIEW";
	public static final String ROLE_APPROVED_SPECIES = "ROLE_APPROVED_SPECIES"; // người phê duyệt yêu cầu thêm loài
	
	//tran huu dat them bien email
	public static final String LIST_EMAIL = "LIST_EMAIL";
	//tran huu dat them bien email
	public static final Long TimeEditData = 24L;// Thời gian tối đa cho phép sửa dữ liệu
	public static String FolderPath = "";

	public static int COMPLIANCE_SPACED_TIME = 14;// Thời gian tuân thủ giãn cách ( tính theo ngày )

	public static String BEAR_CODE = "BEAR";// Mã code xác định loài vật nuôi là gấu
	public static int YEAR_OLDS = 365;
	public static String DUPLICATE_CHIP_CODE = "ErrorDuplicatChipCode";

	public static enum ProductTargetCode {
		ReproductiveRaising("RAISING_REPRODUCTIVE"), // Nuôi sinh sản
		GrowUpRaising("RAISING_GROW_UP"), // Nuôi sinh trưởng
		MEAT("MEAT"), EGG("EGG");

		private String value;

		private ProductTargetCode(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	// GroupBy Items
	public static enum FunctionAndGroupByItem {
		batchCode("batchCode"), animal("animalId"), animalReportCode("animalReportCode"), // Mã vật nuôi làm báo cáo
		animalReportName("animalReportName"), // Tên vật nuôi làm báo cáo
		farm("farm"), original("original"), ward("ward"), district("district"), province("province"), region("region"),
		productTarget("productTarget"), seedLevel("seedLevel"), // Cấp giống
		animalType("animalType"), animalParent("animalParent"), animalParentReportCode("animalParentReportCode"), // Mã
																													// vật
																													// cha
																													// nuôi
																													// làm
																													// báo
																													// cáo
		animalParentReportName("animalParentReportName"), // Tên vật cha nuôi làm báo cáo
		quantity("quantity"), amount("amount"), sumMale("sumMale"), sumFeMale("sumFeMale"), sumUnGen("sumUnGen"),
		exportReason("exportReason"), countFarm("countFarm"), eggType("eggType"), // Loại trứng: thương phẩm hay giống
		code("code"), status("status"), totalAcreage("totalAcreage"), lodgingAcreage("lodgingAcreage"),
		maxNumberOfAnimal("maxNumberOfAnimal"), balanceNumber("balanceNumber"), liveStockProduct("liveStockProduct"),
		ownership("ownership"), report("report"), importEgg("importEgg"), dayOld("dayOld"),
		liveStockMethod("liveStockMethod"), animalReportDataType("animalReportDataType");// Phương pháp nuôi: công
																							// nghiệp hay phi công
																							// nghiệp

		private String value;

		private FunctionAndGroupByItem(String value) {
			this.value = value;
		}

		public String getValue() {
			return value;
		}
	}

	/*
	 * Kiểu báo cáo
	 */
	public static enum ReportType {
		list(0), // Liệt kê số liệu tuần tự theo thời gian
		compare(1);// So sánh 2 mốc thời gian

		private Integer value;

		private ReportType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum StartTimeType {
		beginOfTime(0), // tính thời gian bắt đầu từ trước tới giờ
		aMomentInTime(1);// tính thời gian bắt đầu từ mốc nào đó

		private Integer value;

		private StartTimeType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/*
	 * Kỳ cáo cáo
	 */
	public static enum ReportPeriodType {
		years(0), // Báo cáo theo năm
		months(1), // Báo cáo theo tháng
		fromToMonth(2);// Báo cáo theo từ tháng đến tháng

		private Integer value;

		private ReportPeriodType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/*
	 * Kiểu tiêm
	 */
	public static enum InjectionType {
		vaccin(0), // Vắc xin
		antibiotic(1), // Kháng sinh
		other(2);// Khác

		private Integer value;

		private InjectionType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/*
	 * Loại phiếu (nhập hay xuất đàn)
	 */
	public static enum ImportExportAnimalType {
		importAnimal(1), // Nhập đàn
		exportAnimal(-1);// Xuất đàn

		private Integer value;

		private ImportExportAnimalType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/*
	 * Cấp giống
	 */
	public static enum SeedLevel {
		commodity(0), // thương phẩm
		parents(1), // bố mẹ
		grandParents(2), // ông bà
		greaGgrandParents(3), // cụ kị
		animalParent(4), // Con bố mẹ
		animalNewBorn(5), // Con non
		gilts(6),// Đàn giống hậu bị
		;

		private Integer value;

		private SeedLevel(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/*
	 * Loại phiếu xuất
	 */
	public static enum ExportAnimalType {
		exportSale(1), // xuất bán
		otherSale(2), // xuất khác
		exChangeType(3);// điều chuyển loại

		private Integer value;

		private ExportAnimalType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum ImportExportEggType {
		importEgg(1), // Nhập trứng
		exportEgg(-1);// Xuất trứng

		private Integer value;

		private ImportExportEggType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum EggType {
		commodity(0), // Nhập trứng
		seed(1);// Xuất trứng

		private Integer value;

		private EggType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum FarmStatus {
		active(0), // Đang hoạt động
		disable(1);// Ngưng hoạt động

		private Integer value;

		private FarmStatus(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum AnimalFeedType {
		importAnimalFeed(1), // Nhập
		exportAnimalFeed(-1);// Xuất

		private Integer value;

		private AnimalFeedType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum ImportExportDrug {
		importDrug(1), // Nhập thuốc
		exportDrug(-1);// Xuất thuốc

		private Integer value;

		private ImportExportDrug(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum AnimalLiveStockMethod {
		organic(0), // Nuôi phi công nghiệp
		noneOrganic(1);// Nuôi công nghiệp

		private Integer value;

		private AnimalLiveStockMethod(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum AnimalGender {
		Male(1), // Con đực
		Female(2), // Con cái
		UnGender(0);// Khong ro

		private Integer value;

		private AnimalGender(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum AnimalReportDataType {
		animalParent(1, "Đàn bố mẹ"), 
		childOver1YearOld(2, "Con non trên 1 tuổi"),
		childUnder1YearOld(3, "Con non dưới 1 tuổi"), 
		gilts(4, "Con hậu bị"), 
		hasChip(5, "Có chíp"),
		noChip(6, "Không có chip"), 
		unDefine(0, "Không xác định"),
		importAnimal(7,"Nhập động vật"),
		exportAnimal(8,"Xuất động vật");

		private Integer value;
		private String description;

		private AnimalReportDataType(Integer value, String description) {
			this.value = value;
			this.description = description;
		}

		public Integer getValue() {
			return value;
		}

		public String getDescription() {
			return description;
		}
	}

	public static enum AnimalReportDataFormType {
		firstType(1), secondType(2), thirdType(3);

		private Integer value;

		private AnimalReportDataFormType(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	public static enum ReportPeriod16Type {
		Report16A(1), Report16B(2);

		private Integer value;

		private ReportPeriod16Type(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/** Hình thức sinh sản: là sinh con đẻ trứng hay đẻ con */
	public static enum ReproductionForm {
		giveAnimalBirths(1), // loài đẻ con
		giveEggsBirth(2); // loài đẻ trứng

		private Integer value;

		private ReproductionForm(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

	/// Trạng thái của 1 loài được yêu cầu thêm vào danh sách loài
	public static enum AnimalRequiredStatus {
		PENDING(1, "pending"), // Yêu cầu mới
		DONE(2, "done"), // đã được chấp nhận thêm vào danh sách
		REJECTED(3, "rejected"), // đã bị từ chối,
		DELETED(4, "deleted"),// Đã bị xóa
		NEW(5,"new");

		private Integer value;
		private String name;

		private AnimalRequiredStatus(Integer value, String name) {
			this.value = value;
			this.name = name;
		}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}

	/// Nhóm động vật
	public static enum AnimalGroup {
		GROUP1(1, "Nhóm 1: I cites, IB và NĐ64"), // nhóm 1
		GROUP2(2, "Nhóm 2: II,III cites và IIB"), // nhóm 2
		GROUP3(3, "Nhóm 3: Động vật rừng thông thường/ hoang dã khác"); // Nhóm 3
		private Integer value;
		private String name;

		private AnimalGroup(Integer value, String name) {
			    this.value = value;
			    this.name = name;
			}

		public Integer getValue() {
			return value;
		}

		public String getName() {
			return name;
		}
	}
	
	public static enum BiologicalClass {
		animalClass(1), // lớp
		ordo(2), // bộ
		family(3);// họ

		private Integer value;

		private BiologicalClass(Integer value) {
			this.value = value;
		}

		public Integer getValue() {
			return value;
		}
	}

}
