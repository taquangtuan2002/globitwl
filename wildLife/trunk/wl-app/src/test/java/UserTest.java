
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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




@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseConfig.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class UserTest {
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	UserRepository repository;
	
	@Autowired
	PersonRepository personRepository;
	@Test
	public void testGetUser() {
		
//		Person p = personRepository.findOne(912L);
		UserDto user = userService.findByUserId(1L);
		user.setPassword("123456");
		user.setConfirmPassword("123456");

		userService.changePassword(user);
		
//		User user = repository.findOne(912L);
//		UserFilterDto filter = new UserFilterDto();
//		RoleDto[] roles =new RoleDto[1];
//		roles[0]= new RoleDto();
//		roles[0].setId(1L);
//		filter.setRoles(roles);
//		userService.findAllPageable(filter, 1, 5);
	}
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
}
