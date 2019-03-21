package com.easyt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyt.constant.MessagesErroEnum;
import com.easyt.constant.ProfileEnum;
import com.easyt.constant.StatusEnum;
import com.easyt.constant.VerificationTokenType;
import com.easyt.converter.ConverterHelper;
import com.easyt.entity.Token;
import com.easyt.entity.User;
import com.easyt.exception.ApplicationException;
import com.easyt.exception.UnauthorizedException;
import com.easyt.repository.TokenRepository;
import com.easyt.repository.UserRepository;
import com.easyt.request.LoginRequest;
import com.easyt.response.UserResponse;
import com.easyt.service.AuthenticationService;
import com.easyt.util.Encryption;
import com.easyt.util.SendMail;


@Service
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TokenRepository tokenRepository;
	
	@Override
	public void verifyUserAuthenticated(String token) throws UnauthorizedException {
		if (token == null || token.trim().isEmpty())
			throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name());
		Optional<Token> tokenDB = tokenRepository
				.findOneByUser_StatusNotAndDateOfExpirationAfterDateNowAndValueAndTypeOrType(	token,
																								VerificationTokenType.REFRESH_SESSION.getName(),
																								VerificationTokenType.SESSION.getName(),
																								StatusEnum.INACTIVE.getDesc(),
																								new Date());
		if (tokenDB == null || !tokenDB.isPresent())
			throw new UnauthorizedException(HttpStatus.UNAUTHORIZED.name());
	}
	
	@Override
	public void changeMyPassword(String token, String password) throws ApplicationException {
		if ((token == null || token.trim().isEmpty()) ||
				(password == null || password.trim().isEmpty()))
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		if (password.length() < 6 || password.length() > 12)
			throw new ApplicationException(MessagesErroEnum.PASSWORD_OUT_OF_THE_STANDARD.getMessage());
		Optional<Token> tokenDB = tokenRepository
				.findOneByUser_StatusNotAndDateOfExpirationAfterDateNowAndValueAndType(	token,
																						VerificationTokenType.REDEFINE_PASSWORD.getName(),
																						StatusEnum.INACTIVE.getDesc(),
																						new Date());
		if (tokenDB == null || !tokenDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.TOKEN_EXPIRED.getMessage());
		tokenDB.get().getUser().setPassword(Encryption.encrypt(password));
		tokenDB.get().getUser().setEditionDate(Calendar.getInstance());
		userRepository.save(tokenDB.get().getUser());
	}
	
	@Override
	public String refreshToken(String token) throws ApplicationException {
		if (token == null || token.trim().isEmpty())
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<Token> tokenDB = tokenRepository
				.findOneByUser_StatusNotAndDateOfExpirationAfterDateNowAndValueAndTypeOrType(	token,
																								VerificationTokenType.REFRESH_SESSION.getName(),
																								VerificationTokenType.SESSION.getName(),
																								StatusEnum.INACTIVE.getDesc(),
																								new Date());
		if (tokenDB == null || !tokenDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.TOKEN_EXPIRED.getMessage());
		Token accessToken = new Token(tokenDB.get().getUser(), VerificationTokenType.SESSION);
		Token accessTokenDB = tokenRepository.save(accessToken);
		if (accessTokenDB == null)
			throw new ApplicationException(MessagesErroEnum.CREATE_TOKEN_ERROR.getMessage());
		return accessTokenDB.getValue();		
	}
	
	@Override
	public void getUserByToken(String token) throws ApplicationException {
		if (token == null || token.trim().isEmpty())
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Optional<Token> tokenDB = tokenRepository
				.findOneByUser_StatusNotAndDateOfExpirationAfterDateNowAndValueAndType(	token,
																						VerificationTokenType.REDEFINE_PASSWORD.getName(),
																						StatusEnum.INACTIVE.getDesc(),
																						new Date());
		if (tokenDB == null || !tokenDB.isPresent())
			throw new ApplicationException(MessagesErroEnum.TOKEN_EXPIRED.getMessage());
	}
	
	@Override
	public SendMail forgotMyPassword(String request) throws ApplicationException {
		if (request == null || request.trim().isEmpty())
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		SendMail dest = new SendMail();
		Optional<User> user = userRepository.findOneByStatusNotAndEmailOrTelephoneNumber(StatusEnum.INACTIVE, request, request);
		if (user == null || !user.isPresent())
			throw new ApplicationException(MessagesErroEnum.EMAIL_OR_TELEPHONE_NUMBER_NOT_FOUND_OR_WITHOUT_ACCESS.getMessage());
		Token token = new Token(user.get(), VerificationTokenType.REDEFINE_PASSWORD);
		Token tokenDB = tokenRepository.save(token);
		dest.setName(user.get().getName());
		dest.setEmail(user.get().getEmail());
		dest.setToken(tokenDB.getValue());
		return dest;
	}
	
	@Override
	public UserResponse loginMobile(LoginRequest user) throws ApplicationException {
		if ((user.getPassword() != null && !user.getPassword().trim().isEmpty()) &&
				(user.getEmail() == null || user.getEmail().trim().isEmpty()))
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		// LOGIN PELO FACEBOOKID
		if (StringUtils.isNotBlank(user.getFacebookId())) {
			Optional<User> userFaceId = userRepository
					.findOneByFacebookIdAndStatusNotAndProfileOrProfile(user.getFacebookId().trim(),
																		StatusEnum.INACTIVE, 
																		ProfileEnum.STUDENT, 
																		ProfileEnum.DRIVER);
			// ENCONTROU PELO FACEBOOKID
			if (userFaceId != null && userFaceId.isPresent())
				return generateAccessTokenAndRefreshToken(userFaceId.get());
			// NÃO ENCONTROU PELO FACEBOOKID, TENTA ACHAR PELO E-MAIL SE TIVER
			if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
				Optional<User> userEmail = userRepository
						.findOneByEmailAndStatusNotAndProfileOrProfile(	user.getEmail().trim(),
																		StatusEnum.INACTIVE,
																		ProfileEnum.STUDENT,
																		ProfileEnum.DRIVER);
				// ENCONTROU PELO E-MAIL
				if (userEmail != null && userEmail.isPresent())
					return generateAccessTokenAndRefreshToken(userEmail.get());
			}
			// NÃO ENCONTROU PELO FACEBOOKID E E-MAIL
			return null;
		}
		// LOGIN PELO GOOGLEID
		if (StringUtils.isNotBlank(user.getGoogleId())) {
			Optional<User> userGoogId = userRepository
					.findOneByGoogleIdAndStatusNotAndProfileOrProfile(user.getGoogleId().trim(),
																	  StatusEnum.INACTIVE, 
																	  ProfileEnum.STUDENT, 
																	  ProfileEnum.DRIVER);
			// ENCONTROU PELO GOOGLEID
			if (userGoogId != null && userGoogId.isPresent())
				return generateAccessTokenAndRefreshToken(userGoogId.get());				
			// NÃO ENCONTROU PELO GOOGLEID, TENTA ACHAR PELO E-MAIL SE TIVER
			if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
				Optional<User> userEmail = userRepository
						.findOneByEmailAndStatusNotAndProfileOrProfile(	user.getEmail().trim(),
																		StatusEnum.INACTIVE,
																		ProfileEnum.STUDENT,
																		ProfileEnum.DRIVER);
				// ENCONTROU PELO E-MAIL
				if (userEmail != null && userEmail.isPresent())
					return generateAccessTokenAndRefreshToken(userEmail.get());
			}
			return null;			
		}
		// LOGIN COM E-MAIL E SENHA
		if (StringUtils.isNotBlank(user.getEmail())) {
			// TODO: VER OUTRA MANEIRA DE VALIDAR E-MAIL
			/*if(!ApplicationUtil.isValidEmail(user.getEmail().trim()))
				throw new ApplicationException(MessagesErroEnum.EMAIL_INVALID.getMessage());*/
			Optional<User> userEmail = userRepository
					.findOneByEmailAndStatusNotAndProfileOrProfile(user.getEmail().trim(),
							   									   StatusEnum.INACTIVE,
							   									   ProfileEnum.STUDENT,
							   									   ProfileEnum.DRIVER);
			// ENCONTROU PELO E-MAIL
			if (userEmail != null && userEmail.isPresent()) {
				try {
					// EXISTE O USUÁRIO, MAS NÃO POSSUI SENHA
					if(userEmail.get().getPassword() == null || 
							userEmail.get().getPassword().trim().isEmpty())
						throw new ApplicationException(MessagesErroEnum.LOGIN_SOCIAL_NETWORK.getMessage());
					if (Encryption.verify(user.getPassword(), userEmail.get().getPassword()))
						return generateAccessTokenAndRefreshToken(userEmail.get());
					throw new ApplicationException(MessagesErroEnum.EMAIL_OR_PASSWORD_INVALIDS.getMessage());
				} catch (Exception e) {
					throw new ApplicationException(MessagesErroEnum.EMAIL_OR_PASSWORD_INVALIDS.getMessage());
				}
			}
			return null;			
		}
		return null;		
	}
	
	@Override
	public UserResponse loginPanel(LoginRequest user) throws ApplicationException {
		if ((user.getEmail() == null || user.getEmail().trim().isEmpty()) || 
				(user.getPassword() == null || user.getPassword().trim().isEmpty())) 
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		// TODO: VER OUTRA MANEIRA DE VALIDAR E-MAIL
		/*if(!ApplicationUtil.isValidEmail(user.getEmail().trim()))
			throw new ApplicationException(MessagesErroEnum.EMAIL_INVALID.getMessage());*/
		Optional<User> userDB = userRepository
				.findOneByEmailAndStatusNotAndProfileOrProfile(user.getEmail().trim(), 
															StatusEnum.INACTIVE, 
															ProfileEnum.GENERALMANAGER, 
															ProfileEnum.MODERATOR);
		if (userDB == null || !userDB.isPresent()) 
			throw new ApplicationException(MessagesErroEnum.EMAIL_WITHOUT_ACCESS_PANEL.getMessage());
		try {
			// EXISTE O USUÁRIO, MAS NÃO POSSUI SENHA
			if(userDB.get().getPassword() == null || userDB.get().getPassword().trim().isEmpty())
				throw new ApplicationException(MessagesErroEnum.LOGIN_SOCIAL_NETWORK.getMessage());
			if (Encryption.verify(user.getPassword(), userDB.get().getPassword())) {
				return generateAccessTokenAndRefreshToken(userDB.get());
			}
			throw new ApplicationException(MessagesErroEnum.EMAIL_OR_PASSWORD_INVALIDS.getMessage());
		} catch (Exception e) {
			throw new ApplicationException(MessagesErroEnum.EMAIL_OR_PASSWORD_INVALIDS.getMessage());
		}
	}
	
	public UserResponse generateAccessTokenAndRefreshToken(User user) throws ApplicationException {
		Token accessToken = new Token(user, VerificationTokenType.SESSION);
		Token accessTokenDB = tokenRepository.save(accessToken);
		Token refreshToken = new Token(user, VerificationTokenType.REFRESH_SESSION);
		Token refreshTokenDB = tokenRepository.save(refreshToken);
		if (user.getTokens() == null || user.getTokens().isEmpty()) {
			user.setTokens(new ArrayList<>());
		}
		user.getTokens().add(accessTokenDB);
		user.getTokens().add(refreshTokenDB);
		return ConverterHelper.convertUserToResponse(userRepository.save(user), accessTokenDB.getValue(), refreshTokenDB.getValue());
	}

}
