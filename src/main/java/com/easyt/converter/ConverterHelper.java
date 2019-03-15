package com.easyt.converter;

import java.util.ArrayList;
import java.util.List;

import com.easyt.entity.Institution;
import com.easyt.entity.Notification;
import com.easyt.entity.Permission;
import com.easyt.entity.User;
import com.easyt.response.InstitutionResponse;
import com.easyt.response.NotificationResponse;
import com.easyt.response.PermissionResponse;
import com.easyt.response.UserResponse;
import com.easyt.util.ApplicationUtil;

public class ConverterHelper {
	
	public static NotificationResponse convertNotificationToResponse(Notification notification) {
		NotificationResponse res = new NotificationResponse();
		if (notification.getId() != null)
			res.setId(notification.getId());
		if (notification.getTitle() != null && !notification.getTitle().trim().isEmpty())
			res.setTitle(notification.getTitle());
		if (notification.getText() != null && !notification.getText().trim().isEmpty())
			res.setText(notification.getText());
		if (notification.getIdAssociatedType() != null)
			res.setIdAssociatedType(notification.getIdAssociatedType());
		if (notification.getTargetAudience() != null)
			res.setTargetAudience(notification.getTargetAudience().toString());
		return res;
	}
	
	public static InstitutionResponse convertInstitutionToResponse(Institution institution) {
		InstitutionResponse res = new InstitutionResponse();
		if (institution.getId() != null)
			res.setId(institution.getId());
		if (institution.getName() != null && !institution.getName().trim().isEmpty())
			res.setName(institution.getName());
		if (institution.getAddress() != null && !institution.getAddress().trim().isEmpty())
			res.setAddress(institution.getAddress());
		if (institution.getState() != null && !institution.getState().trim().isEmpty())
			res.setState(institution.getState());
		if (institution.getCity() != null && !institution.getCity().trim().isEmpty())
			res.setCity(institution.getCity());
		if (institution.getLatitude() != null)
			res.setLatitude(institution.getLatitude());
		if (institution.getLongitude() != null)
			res.setLongitude(institution.getLongitude());
		if (institution.getPeriod() != null)
			res.setPeriod(institution.getPeriod().toString());
		if (institution.getStatus() != null)
			res.setStatus(institution.getStatus().toString());
		if (institution.getEmail() != null && !institution.getEmail().trim().isEmpty())
			res.setEmail(institution.getEmail());
		if (institution.getTelephoneNumber() != null && !institution.getTelephoneNumber().trim().isEmpty())
			res.setTelephoneNumber(institution.getTelephoneNumber());
		return res;
	}
	
	public static PermissionResponse convertPermissionToResponse(Permission permission) {
		PermissionResponse res = new PermissionResponse();
		if (permission.getId() != null)
			res.setId(permission.getId());
		if (permission.getDescription() != null && !permission.getDescription().trim().isEmpty()) {
			res.setDescription(permission.getDescription());
			String name = permission.getDescription();
			name = name.replaceAll("_", " ");
			res.setName(name);
		}
		res.setManager(permission.getManager());
		res.setModerator(permission.getModerator());
		res.setDriver(permission.getDriver());
		res.setStudent(permission.getStudent());
		if (permission.getStatus() != null)
			res.setStatus(permission.getStatus().toString());
		return res;
	}
	
	public static UserResponse convertUserToResponse(User user, String accessToken, String refreshToken) {
		UserResponse res = new UserResponse();
		if (user.getId() != null)
			res.setId(user.getId());
		if (user.getName() != null && !user.getName().trim().isEmpty())
			res.setName(user.getName());
		if (user.getEmail() != null && !user.getEmail().trim().isEmpty())
			res.setEmail(user.getEmail());
		if (user.getMediaUrl() != null && !user.getMediaUrl().trim().isEmpty())
			res.setMediaUrl(user.getMediaUrl());
		if (user.getProfile() != null)
			res.setProfile(user.getProfile().toString());
		if (user.getStatus() != null)
			res.setStatus(user.getStatus().toString());
		if (accessToken != null && !accessToken.trim().isEmpty())
			res.setAccessToken(accessToken);
		if (refreshToken != null && !refreshToken.trim().isEmpty())
			res.setRefreshToken(refreshToken);
		if (user.getAddress() != null && !user.getAddress().trim().isEmpty())
			res.setAddress(user.getAddress());
		if (user.getCity() != null && !user.getCity().trim().isEmpty())
			res.setCity(user.getCity());
		if (user.getState() != null && !user.getState().trim().isEmpty())
			res.setState(user.getState());
		if (user.getDateOfBirth() != null)
			res.setDateOfBirth(ApplicationUtil.calendarToTimestamp(user.getDateOfBirth()));
		if (user.getPeriod() != null)
			res.setPeriod(user.getPeriod().toString());
		if (user.getCompanyName() != null && !user.getCompanyName().trim().isEmpty())
			res.setCompanyName(user.getCompanyName());
		if (user.getDocumentRegisterNumber() != null && !user.getDocumentRegisterNumber().trim().isEmpty())
			res.setDocumentRegisterNumber(user.getDocumentRegisterNumber());
		if (user.getRadius() != null)
			res.setRadius(user.getRadius());
		if (user.getCpf() != null && !user.getCpf().trim().isEmpty())
			res.setCpf(user.getCpf());
		if (user.getDocumentCategory() != null)
			res.setDocumentCategory(user.getDocumentCategory().toString());
		if (user.getInstitutions() != null && !user.getInstitutions().isEmpty()) {
			List<InstitutionResponse> institutions = new ArrayList<>();
			for (Institution institution : user.getInstitutions()) {
				InstitutionResponse objectInstitution = new InstitutionResponse();
				objectInstitution = convertInstitutionToResponse(institution);
				institutions.add(objectInstitution);
			}
			res.setInstitutions(institutions);
		}
		return res;
	}

}