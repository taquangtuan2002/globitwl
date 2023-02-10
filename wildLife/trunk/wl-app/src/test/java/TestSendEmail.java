import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.UserService;
import com.globits.wl.config.DatabaseConfig;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.OneTimeToken;
import com.globits.wl.dto.functiondto.ForgotPasswordDto;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.OneTimeTokenRepository;
import com.globits.wl.utils.EmailUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseConfig.class)
@Transactional(propagation = Propagation.SUPPORTS)
public class TestSendEmail {
	@Autowired
	private OneTimeTokenRepository oneTimeTokenRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	FarmRepository farmRepository;
	@Autowired
	UserService userService;
//	@Test
	public void sendEmail() {
		ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
		forgotPasswordDto.setEmail("kematthangbang@gmail.com");
		forgotPasswordDto.setUsername("01.281.00002");
		forgotPasswordDto.setLocation("fms_old/#/");
		forgotPasswordDto.setHost("localhost");
		
		ForgotPasswordDto test = sendEmailForgotPassword(forgotPasswordDto); 
		forgotPasswordDto.setToken(forgotPasswordDto.getToken());
		forgotPasswordDto.setPhoneNumber("0392532723");
		checkToken(forgotPasswordDto);
		
		forgotPasswordDto.setPassword("123456");
		forgotPasswordDto.setConfirmPassword("123456");
		forgotPasswordDto.setToken(forgotPasswordDto.getToken());
		changePassword(forgotPasswordDto);
	}
//	@Test
	public void checkToken() {
		ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
		forgotPasswordDto.setEmail("kematthangbang@gmail.com");
		forgotPasswordDto.setUsername("01.281.00002");
		forgotPasswordDto.setLocation("fms_old/#/");
		forgotPasswordDto.setHost("localhost");
		forgotPasswordDto.setToken("459a0bf4aee9f62afaee9016fa38bc83");
		forgotPasswordDto.setPhoneNumber("0392532723");
		
		ForgotPasswordDto test = checkToken(forgotPasswordDto); 
		System.out.println("check token");
	}
	@Test
	public void changePassword() {
		ForgotPasswordDto forgotPasswordDto = new ForgotPasswordDto();
		forgotPasswordDto.setEmail("kematthangbang@gmail.com");
		forgotPasswordDto.setUsername("01.281.00002");
		forgotPasswordDto.setLocation("fms_old/#/");
		forgotPasswordDto.setHost("localhost");
		
		forgotPasswordDto.setPassword("123456");
		forgotPasswordDto.setConfirmPassword("123456");
		forgotPasswordDto.setPhoneNumber("0393532723");
		forgotPasswordDto.setToken("459a0bf4aee9f62afaee9016fa38bc83");
		changePassword(forgotPasswordDto);
	}
	
	public ForgotPasswordDto sendEmailForgotPassword(ForgotPasswordDto forgotPasswordDto) {

		ForgotPasswordDto result = new ForgotPasswordDto();

		Date dateNow = new Date();
		try {
			if (forgotPasswordDto != null && StringUtils.hasText(forgotPasswordDto.getLocation())
					&& StringUtils.hasText(forgotPasswordDto.getHost())) {
				if (StringUtils.hasText(forgotPasswordDto.getUsername())) {
					User user = userRepository.findByUsername(forgotPasswordDto.getUsername());
					if (user != null) {
						if (user.getEmail().equals(forgotPasswordDto.getEmail())) {
							String location = forgotPasswordDto.getLocation().substring(0,
									forgotPasswordDto.getLocation().indexOf("/#/"));
							String subject = "Yêu cầu lấy lại mật khẩu truy cập";
							String body = "Bạn đã yêu cầu lấy lại mật khẩu tài khoản: '" + user.getUsername()
									+ "'. Bạn vui lòng nhấn vào đường link bên dưới để đổi lại mật khẩu (Liên kết chỉ có thể sử dụng được trong '"
									+ EmailUtil.EXPRIED_TIME_CHANGE_PASSWORD
									+ "' phút kể từ khi bạn yêu cầu lấy lại mật khẩu).\n";
							body += location + EmailUtil.URL_PAGE_CHANGE_PASSWORD;

							/*
							 * String encrypt = EncryptAndDecrypt.encrypt(forgotPasswordDto.getUsername(),
							 * Constants.Key_EncryptAndDecrypt);
							 */
							String encrypt = EmailUtil.getMd5(forgotPasswordDto.getUsername());
							if (encrypt == null) {
								result.setCaseResult(EmailUtil.ResultChangePassword.Error.getValue());
								result.setText("EncryptAndDecrypt.encrypt");
								result.setSuccess(false);
								return result;
							}
							String converKeyToURL = URLEncoder.encode(encrypt, "UTF-8");
							body += converKeyToURL;
							boolean sendEmail = EmailUtil.sendEmail(forgotPasswordDto.getHost(),
									forgotPasswordDto.getEmail(), subject, body);
							if (sendEmail) {
								OneTimeToken token = oneTimeTokenRepository
										.findByUsername(forgotPasswordDto.getUsername());
								if (token == null) {
									token = new OneTimeToken();
									token.setUsername(forgotPasswordDto.getUsername());
									token.setCreateDate(new LocalDateTime());
									token.setLastUpdateDate(dateNow);
									token.setExpriedTime(new Date(dateNow.getYear(), dateNow.getMonth(),
											dateNow.getDate(), dateNow.getHours(),
											dateNow.getMinutes() + EmailUtil.EXPRIED_TIME_CHANGE_PASSWORD,
											dateNow.getSeconds()));
									token.setToken(encrypt);
								} else {
									token.setModifyDate(new LocalDateTime());
									token.setLastUpdateDate(new Date());
									token.setExpriedTime(new Date(dateNow.getYear(), dateNow.getMonth(),
											dateNow.getDate(), dateNow.getHours(),
											dateNow.getMinutes() + EmailUtil.EXPRIED_TIME_CHANGE_PASSWORD,
											dateNow.getSeconds()));
									token.setToken(encrypt);
								}

								token = oneTimeTokenRepository.save(token);
								if (token != null) {
									result.setSuccess(true);
									return result;
								}
							}
						} else {
							result.setCaseResult(EmailUtil.ResultChangePassword.EmailDoesNotMatchUsername.getValue());
							result.setText("EmailDoesNotMatchUsername");
							result.setSuccess(false);
							return result;
						}
					} else {
						result.setCaseResult(EmailUtil.ResultChangePassword.UserIsNull.getValue());
						result.setText("UserIsNull");
						result.setSuccess(false);
						return result;
					}
				} else {
					result.setCaseResult(EmailUtil.ResultChangePassword.UsernameIsNull.getValue());
					result.setText("UsernameIsNull");
					result.setSuccess(false);
					return result;
				}
			} else {
				result.setCaseResult(EmailUtil.ResultChangePassword.LocationIsNull.getValue());
				result.setText("LocationIsNull");
				result.setSuccess(false);
				return result;
			}

			result.setCaseResult(EmailUtil.ResultChangePassword.Error.getValue());
			result.setText("Error");
			result.setSuccess(false);
			return result;

		} catch (Exception e) {
			result.setCaseResult(EmailUtil.ResultChangePassword.Error.getValue());
			result.setText(e.toString());
			result.setSuccess(false);
			return result;
		}
	}
	
	public ForgotPasswordDto checkToken(ForgotPasswordDto forgotPasswordDto) {
		ForgotPasswordDto result = new ForgotPasswordDto();
		Date dateNow = new Date();
		// Lấy token client đẩy lên kiểm tra với db bảng one_time_token để lấy ra bản
		// ghi token của user yêu cầu đổi mật khẩu
		if (forgotPasswordDto.getToken() != null && StringUtils.hasText(forgotPasswordDto.getToken())) {

			/*
			 * String encrypt = EncryptAndDecrypt.decrypt(forgotPasswordDto.getToken(),
			 * Constants.Key_EncryptAndDecrypt);
			 */
			String encrypt = forgotPasswordDto.getToken();
			if (encrypt == null) {
				// Nếu token null trả về thông báo LinkIsBrokenOrExpired
				result.setCaseResult(EmailUtil.ResultChangePassword.LinkIsBrokenOrExpired.getValue());
				result.setText("LinkIsBrokenOrExpired");
				result.setSuccess(false);
				return result;
			}
			OneTimeToken token = oneTimeTokenRepository.findByToken(encrypt);
			if (token != null && token.getToken() != null && StringUtils.hasText(token.getToken())
					&& token.getToken().equals(forgotPasswordDto.getToken())) {
				// Kiểm tra thời gian của token còn sử dụng được hay không
				if (token.getExpriedTime() != null && token.getExpriedTime().after(dateNow)) {
					// Nếu token hợp lệ ( có thể sử dụng được ) thì sẽ kiểm tra tiếp đến số điện
					// thoại xác thực
					if (forgotPasswordDto.getPhoneNumber() != null
							&& StringUtils.hasText(forgotPasswordDto.getPhoneNumber())) {
						List<Farm> farms = farmRepository.findByCode(token.getUsername());
						// Nếu không lấy được user theo username ở bảng one_time_token sẽ trả về null.
						// Có lỗi
						if (farms == null || farms.size() == 0) {
							return null;
						}
						// Nếu số điện thoại của user get về được trùng với số điện thoại người dùng
						// nhập thì trả về PhoneNumberSuccess
						if (farms.get(0).getOwnerPhoneNumber().equals(forgotPasswordDto.getPhoneNumber())) {
							result.setPhoneNumberSuccess(true);
							result.setSuccess(true);
							return result;
						}
					}

					// Nếu chưa nhập hoặc nhập sai số điện thoại sẽ trả về thông báo
					// PhoneNumberNotValided
					result.setPhoneNumberSuccess(false);
					result.setCaseResult(EmailUtil.ResultChangePassword.PhoneNumberNotValided.getValue());
					result.setText("PhoneNumberNotValided");
					result.setSuccess(false);
					return result;
				} else {
					// Nếu quá thời gian sử dụng toke sẽ trả về thông báo LinkIsExpired
					result.setCaseResult(EmailUtil.ResultChangePassword.LinkIsExpired.getValue());
					result.setText("LinkIsExpired");
					result.setSuccess(false);
					return result;
				}
			}
		}

		result.setCaseResult(EmailUtil.ResultChangePassword.LinkIsBrokenOrExpired.getValue());
		result.setText("LinkIsBrokenOrExpired");
		result.setSuccess(false);
		return result;

	}
	
	public ForgotPasswordDto changePassword(ForgotPasswordDto forgotPasswordDto) {
		ForgotPasswordDto result = new ForgotPasswordDto();
		if (forgotPasswordDto.getToken() != null && StringUtils.hasText(forgotPasswordDto.getToken())) {

			ForgotPasswordDto checkToken = this.checkToken(forgotPasswordDto);
			if (checkToken != null && checkToken.isSuccess()) {

				/*
				 * String encrypt = EncryptAndDecrypt.decrypt(forgotPasswordDto.getToken(),
				 * Constants.Key_EncryptAndDecrypt);
				 */
				String encrypt = forgotPasswordDto.getToken();
				if (encrypt == null) {
					result.setCaseResult(EmailUtil.ResultChangePassword.Error.getValue());
					result.setText("EncryptAndDecrypt.encrypt");
					result.setSuccess(false);
					return result;
				}

				OneTimeToken token = oneTimeTokenRepository.findByToken(encrypt);
				if (token != null && token.getToken() != null && StringUtils.hasText(token.getToken())
						&& token.getToken().equals(forgotPasswordDto.getToken())) {
					if (token != null) {
						User user = userRepository.findByUsername(token.getUsername());
						if (user != null && forgotPasswordDto.getPassword() != null
								&& forgotPasswordDto.getConfirmPassword() != null
								&& StringUtils.hasText(forgotPasswordDto.getPassword())
								&& forgotPasswordDto.getPassword().equals(forgotPasswordDto.getConfirmPassword())) {
							user.setChangePassword(true);
							user.setPassword(forgotPasswordDto.getPassword());
							user.setConfirmPassword(forgotPasswordDto.getConfirmPassword());
							UserDto userDto = userService.changePassword(new UserDto(user));
							if (userDto != null) {

								// khi cập nhật mật khẩu thành công thì xóa token trong one time token
								token.setModifyDate(new LocalDateTime());
								token.setLastResetDate(new Date());
								token.setToken(null);
								token.setExpriedTime(null);

								token = oneTimeTokenRepository.save(token);
								if (token != null) {
									result.setSuccess(true);
									return result;
								}
							}
						}
					}
				}
			} else {
				return checkToken;
			}
		} else {
			result.setCaseResult(EmailUtil.ResultChangePassword.LinkIsBrokenOrExpired.getValue());
			result.setText("LinkIsBrokenOrExpired");
			result.setSuccess(false);
			return result;
		}

		result.setCaseResult(EmailUtil.ResultChangePassword.Error.getValue());
		result.setText("Error");
		result.setSuccess(false);
		return result;
	}
}