package com.globits.wl.service;

import com.globits.wl.dto.functiondto.ForgotPasswordDto;

public interface OneTimeTokenService {

	ForgotPasswordDto changePassword(ForgotPasswordDto forgotPasswordDto);

	ForgotPasswordDto checkToken(ForgotPasswordDto forgotPasswordDto);

	ForgotPasswordDto sendEmailForgotPassword(ForgotPasswordDto forgotPasswordDto);

}
