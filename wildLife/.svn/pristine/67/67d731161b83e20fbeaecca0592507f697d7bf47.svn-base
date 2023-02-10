
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.globits.core.domain.Person;
import com.globits.core.dto.PersonDto;
import com.globits.core.repository.PersonRepository;
import com.globits.security.domain.Role;
import com.globits.security.domain.User;
import com.globits.security.dto.RoleDto;
import com.globits.security.dto.UserDto;
import com.globits.security.dto.UserFilterDto;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.RoleService;
import com.globits.security.service.UserService;
import com.globits.wl.config.DatabaseConfig;
import com.globits.wl.domain.ReportForm16;
import com.globits.wl.dto.FarmDto;
import com.globits.wl.dto.report.TotalReportDto;
import com.globits.wl.service.FarmService;
import com.globits.wl.service.ImportExportAnimalService;
import com.globits.wl.service.ReportForm16Service;
import com.globits.wl.utils.ImportExportExcelUtil;




@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseConfig.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class FarmTest {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	UserRepository repository;
	
	@Autowired
	PersonRepository personRepository;
	
	@Autowired
	FarmService farmService;
	
	@Autowired
	private ResourceLoader resourceLoader;
	@Autowired
	ImportExportAnimalService importExportAnimalService;
	@Autowired
	ReportForm16Service reportForm16Service;
	//@Test
	public void testImportFarm() throws FileNotFoundException, ParseException {
		List<FarmDto> ret = ImportExportExcelUtil.importWL2017FarmFromInputStream(new FileInputStream(new File("C:\\Users\\ADMIN\\Documents\\Zalo Received Files\\FAO_02062020.xlsx")));
		System.out.print(ret.size());
		farmService.saveListImportFarm(ret);
	}
	
//	@Test
	public void testReport() throws ParseException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
		Date fromDate = sdf.parse("01/01/2010 00:00");
		Date toDate = new Date();
		TotalReportDto ret = importExportAnimalService.getTotalReport(fromDate, toDate);
		Resource resource = resourceLoader.getResource("classpath:BaoCaoTongHopChanNuoi.xls");
		InputStream ip = resource.getInputStream();
		FileOutputStream out = new FileOutputStream("D:\\Test\\exportTotalReport"+toDate.getTime()+".xls");
		ImportExportExcelUtil.exportTotalReport(ip, out, ret);
	}
	
//	@Test
	public void testList() {
		List<String> target = new ArrayList<String>();
		List<String> target1 = new ArrayList<String>();
		List<String> target2 = new ArrayList<String>();
		String obj = "d";
		target1.add(obj);
		
		System.out.println("1 step");
		for (String string : target1) {
			System.out.print(string+",");
		}
		System.out.println("");
		
		
		obj = "e";
		target1.add(obj);		
		System.out.println("2 step");
		for (String string : target1) {
			System.out.print(string+",");
		}
		System.out.println("");
		
//		target.add("a");
//		target.add("b");
//		target.add("c");
//		target1 = target;
//		target2.addAll(target);
//		System.out.println("target1");
//		for (String string : target1) {
//			System.out.print(string+",");
//		}
//		System.out.println("");
//		System.out.println("target2");		
//		for (String string : target2) {
//			System.out.print(string+",");
//		}
//		System.out.println("");
//		
//		target.add(obj);
//		target  = new ArrayList<String>();
//		obj="change";
//		target.add(obj);
//		target2=target;
//		System.out.println("target1");
//		for (String string : target1) {
//			System.out.print(string+",");
//		}
//		System.out.println("");
//		System.out.println("target2");
//		for (String string : target2) {
//			System.out.print(string+",");
//		}
//		System.out.println("");
	}
//	@Test
	public void timeTest() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm", Locale.ENGLISH);
	    Date firstDate = sdf.parse("06/24/2017 00:00");
	    Date secondDate = sdf.parse("06/24/2017 18:36");
	 
	    long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
	    long diff = TimeUnit.HOURS.convert(diffInMillies, TimeUnit.MILLISECONDS);
	    System.out.println(diff);
	}
//	@Test
	public void testCharUTF8() throws FileNotFoundException, UnsupportedEncodingException {
		String a="Hướng trứng";
		String b = "trứng";
		System.out.println(a.toLowerCase().indexOf("trứng".toLowerCase()));
		System.out.println(a.indexOf(b));
		System.out.println(a.toLowerCase().contains("trứng".toLowerCase()));
		System.out.println(a.contains(b));
		
		String str1 = "Hướng trứng";
	    String str2 = "trứng";
	    byte[] arr = str1.getBytes("UTF-8");
	    byte[] brr = str2.getBytes("UTF-8");
	    System.out.println(arr.toString());
	    String arr1="";
	    String brr1="";
	    for(byte z: arr) {
	    	arr1+=z;
	         System.out.println(z);
	      }
	      System.out.println(arr1.toString());
	    for(byte z: brr) {
	    	brr1+=z;
	         System.out.println(z);
	      }
	    System.out.println(brr1.toString());
	    
	    System.out.println(arr.toString().contains(brr.toString()));
	    System.out.println(arr1.contains(brr1));
	}
	
//	@Test
//	public void testImport() throws FileNotFoundException {
//		InputStream ip = new FileInputStream("C:\\Users\\ADMIN\\Desktop\\FAO_DOC\\nh\\TrangTraiTN_06-15-03-2020.xls.xlsx");
//		List<FarmDto> list = ImportExportExcelUtil.importFarmFromInputStream(ip);
//		farmService.saveListImportFarm(list);		
//	}
//	private RoleDto findAndCreateRole(String roleName) {
//		RoleDto role = roleService.findByName(roleName);
//		
//		if(role!=null) {
//			return role;
//		}
//		
//		role = new RoleDto();
//		role.setName(roleName);
//		role = roleService.saveOne(role);
//		return role;
//	}
//	@Test
//	public void createRoleTest() {
//		Role role = findAndCreateRole("ROLE_ADMIN");
//		return;
//	}
////	@Test
//	public void createAdminTest() {
//		String username = "admin";
//		UserDto user = userService.findByUsername(username);
//		HashSet<RoleDto> roles = new HashSet<RoleDto>();
//		
//		if(user==null) {
//			user = new UserDto();
//			user.setActive(true);
//			user.setEmail(username+"@globits.net");
//			user.setPassword(username);
//			user.setUsername(username);
//			PersonDto p = new PersonDto();
//			p.setFirstName(username);
//			p.setLastName("system");
//			user.setPerson(p);
//			user = userService.save(user);
//		}
//		
//		Role role = findAndCreateRole("ROLE_ADMIN");
//		roles.add(new RoleDto(role));
////		role = findAndCreateRole("ROLE_USER");
////		roles.add(new RoleDto(role));
//		user.setRoles(roles);
//		userService.save(user);
//	}
	

	@Test
	public void testGetListReportForm16By() throws ParseException {
		Long farmId = 37353L;
		Long animalId = 313L;
		Integer year = 2019;
		List<ReportForm16> results = reportForm16Service.getListBy(farmId, animalId, year);
		if (results != null) {
			System.out.println("s");
			System.out.println(results.size());
		}
		else {
			System.out.println("f");
		}
	}
	
}
