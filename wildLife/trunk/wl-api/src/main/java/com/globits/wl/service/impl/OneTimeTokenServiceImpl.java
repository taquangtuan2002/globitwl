package com.globits.wl.service.impl;

import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.globits.security.domain.User;
import com.globits.security.dto.UserDto;
import com.globits.security.repository.UserRepository;
import com.globits.security.service.UserService;
import com.globits.wl.domain.Farm;
import com.globits.wl.domain.OneTimeToken;
import com.globits.wl.domain.UserAttachments;
import com.globits.wl.dto.functiondto.ForgotPasswordDto;
import com.globits.wl.repository.FarmRepository;
import com.globits.wl.repository.OneTimeTokenRepository;
import com.globits.wl.repository.UserAttachmentsRepository;
import com.globits.wl.service.OneTimeTokenService;
import com.globits.wl.utils.EmailUtil;

@Service
public class OneTimeTokenServiceImpl implements OneTimeTokenService{
	@Autowired
	private OneTimeTokenRepository oneTimeTokenRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	FarmRepository farmRepository;
	@Autowired
	UserService userService;
	
	@Resource
	private UserAttachmentsRepository userAttachmentsRepository;
	
	
	@Override
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
							String subject = "Y??u c???u l???y l???i m???t kh???u truy c???p";
							String body = "B???n ???? y??u c???u l???y l???i m???t kh???u t??i kho???n: '" + user.getUsername()
									+ "'. B???n vui l??ng nh???n v??o ???????ng link b??n d?????i ????? ?????i l???i m???t kh???u (Li??n k???t ch??? c?? th??? s??? d???ng ???????c trong '"
									+ EmailUtil.EXPRIED_TIME_CHANGE_PASSWORD
									+ "' ph??t k??? t??? khi b???n y??u c???u l???y l???i m???t kh???u).\n";
							body += location + EmailUtil.URL_PAGE_CHANGE_PASSWORD;

							/*
							 * String encrypt = EncryptAndDecrypt.encrypt(forgotPasswordDto.getUsername(),
							 * Constants.Key_EncryptAndDecrypt);
							 */
							String encrypt = EmailUtil.getMd5(forgotPasswordDto.getUsername()+ System.currentTimeMillis());
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
	
	@Override
	public ForgotPasswordDto checkToken(ForgotPasswordDto forgotPasswordDto) {
		ForgotPasswordDto result = new ForgotPasswordDto();
		Date dateNow = new Date();
		// L???y token client ?????y l??n ki???m tra v???i db b???ng one_time_token ????? l???y ra b???n
		// ghi token c???a user y??u c???u ?????i m???t kh???u
		if (forgotPasswordDto.getToken() != null && StringUtils.hasText(forgotPasswordDto.getToken())) {

			/*
			 * String encrypt = EncryptAndDecrypt.decrypt(forgotPasswordDto.getToken(),
			 * Constants.Key_EncryptAndDecrypt);
			 */
			String encrypt = forgotPasswordDto.getToken();
			if (encrypt == null) {
				// N???u token null tr??? v??? th??ng b??o LinkIsBrokenOrExpired
				result.setCaseResult(EmailUtil.ResultChangePassword.LinkIsBrokenOrExpired.getValue());
				result.setText("LinkIsBrokenOrExpired");
				result.setSuccess(false);
				return result;
			}
			OneTimeToken token = oneTimeTokenRepository.findByToken(encrypt);
			if (token != null && token.getToken() != null && StringUtils.hasText(token.getToken())
					&& token.getToken().equals(forgotPasswordDto.getToken())) {
				// Ki???m tra th???i gian c???a token c??n s??? d???ng ???????c hay kh??ng
				if (token.getExpriedTime() != null && token.getExpriedTime().after(dateNow)) {
					// N???u token h???p l??? ( c?? th??? s??? d???ng ???????c ) th?? s??? ki???m tra ti???p ?????n s??? ??i???n
					// tho???i x??c th???c
					if (forgotPasswordDto.getPhoneNumber() != null
							&& StringUtils.hasText(forgotPasswordDto.getPhoneNumber())) {
						User user = userRepository.findByUsername(token.getUsername());
						List<UserAttachments> userAttachmentsList = userAttachmentsRepository.findUserAttachmentsByUserId(user.getId());
						//List<Farm> farms = farmRepository.findByCreatedBy(token.getUsername());
						// N???u kh??ng l???y ???????c user theo username ??? b???ng one_time_token s??? tr??? v??? null.
						// C?? l???i
						if (userAttachmentsList == null || userAttachmentsList.size() == 0) {
							return null;
						}
						// N???u s??? ??i???n tho???i c???a user get v??? ???????c tr??ng v???i s??? ??i???n tho???i ng?????i d??ng
						// nh???p th?? tr??? v??? PhoneNumberSuccess
						for(UserAttachments f : userAttachmentsList) {
							if (f.getPhoneNumber()!=null && f.getPhoneNumber().equals(forgotPasswordDto.getPhoneNumber())) {
								result.setPhoneNumberSuccess(true);
								result.setSuccess(true);
								return result;
							}
						}
						
					}

					// N???u ch??a nh???p ho???c nh???p sai s??? ??i???n tho???i s??? tr??? v??? th??ng b??o
					// PhoneNumberNotValided
					result.setPhoneNumberSuccess(false);
					result.setCaseResult(EmailUtil.ResultChangePassword.PhoneNumberNotValided.getValue());
					result.setText("PhoneNumberNotValided");
					result.setSuccess(false);
					return result;
				} else {
					// N???u qu?? th???i gian s??? d???ng toke s??? tr??? v??? th??ng b??o LinkIsExpired
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
	
	@Override
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

								// khi c???p nh???t m???t kh???u th??nh c??ng th?? x??a token trong one time token
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
