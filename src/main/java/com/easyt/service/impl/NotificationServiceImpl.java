package com.easyt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.easyt.constant.MessagesErroEnum;
import com.easyt.constant.ProfileEnum;
import com.easyt.constant.StatusEnum;
import com.easyt.constant.TargetAudienceEnum;
import com.easyt.converter.ConverterHelper;
import com.easyt.entity.Notification;
import com.easyt.entity.NotificationUser;
import com.easyt.entity.NotificationUserId;
import com.easyt.entity.User;
import com.easyt.exception.ApplicationException;
import com.easyt.repository.NotificationRepository;
import com.easyt.repository.NotificationUserRepository;
import com.easyt.repository.UserRepository;
import com.easyt.request.NotificationRequest;
import com.easyt.response.NotificationResponse;
import com.easyt.service.NotificationService;

@Service
@Transactional
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NotificationUserRepository notificationUserRepository;
	
	public TargetAudienceEnum selectTargetAudience(String targetAudience) throws ApplicationException {
		if (TargetAudienceEnum.ALLUSERS.toString().equals(targetAudience))
			return TargetAudienceEnum.ALLUSERS;
		if (TargetAudienceEnum.DRIVER.toString().equals(targetAudience))
			return TargetAudienceEnum.DRIVER;
		if (TargetAudienceEnum.STUDENT.toString().equals(targetAudience))
			return TargetAudienceEnum.STUDENT;
		throw new ApplicationException(MessagesErroEnum.PERIOD_NOT_FOUND.getMessage());
	}
	
	@Override
	public NotificationResponse createNotification(NotificationRequest notification) throws ApplicationException {
		if ((notification.getTitle() == null || notification.getTitle().trim().isEmpty()) ||
				(notification.getText() == null || notification.getText().trim().isEmpty()) ||
				(notification.getTargetAudience() == null || notification.getTargetAudience().trim().isEmpty()))
			throw new ApplicationException(MessagesErroEnum.PARAMETER_EMPTY_OR_NULL.getMessage());
		Notification newNotification = new Notification();
		newNotification.setCreationDate(Calendar.getInstance());
		newNotification.setTitle(notification.getTitle());
		newNotification.setText(notification.getText());
		newNotification.setStatus(StatusEnum.ACTIVE);
		newNotification.setTargetAudience(selectTargetAudience(notification.getTargetAudience()));
		return ConverterHelper.convertNotificationToResponse(notificationRepository.save(newNotification));
	}
	
	@Override
	public void associateNotificationToUsers(NotificationResponse notification) throws ApplicationException {
		List<User> users = listUsersByTypeNotification(selectTargetAudience(notification.getTargetAudience()));
		if (users == null || users.isEmpty())
			return;
		Optional<Notification> notify = notificationRepository
				.findOneByIdAndStatusNot(notification.getId(), StatusEnum.INACTIVE);
		if ((notify != null && notify.isPresent()) &&
				(notify.get().getUsers() == null || notify.get().getUsers().isEmpty()))
			notify.get().setUsers(new ArrayList<>());
		for (User user : users) {
			NotificationUser notifyUser = new NotificationUser();
			notifyUser.setId(new NotificationUserId());
			notifyUser.getId().setNotification(notify.get());
			notifyUser.getId().setUser(user);
			if (notify.get().getUsers().contains(notifyUser))
				continue;
			notifyUser = notificationUserRepository.save(notifyUser);
			if (notifyUser == null)
				continue;
			notify.get().getUsers().add(notifyUser);
		}
		notificationRepository.save(notify.get());
	}
	
	public List<User> listUsersByTypeNotification(TargetAudienceEnum targetAudience) {
		if (targetAudience.equals(TargetAudienceEnum.DRIVER)) {
			return userRepository
					.findAllByStatusNotAndProfileOrderByNameAsc(StatusEnum.INACTIVE, ProfileEnum.DRIVER)
					.collect(Collectors.toList());
		}
		if (targetAudience.equals(TargetAudienceEnum.STUDENT)) {
			return userRepository
					.findAllByStatusNotAndProfileOrderByNameAsc(StatusEnum.INACTIVE, ProfileEnum.STUDENT)
					.collect(Collectors.toList());
		}
		return userRepository
				.findAllByStatusNotAndProfileOrProfileOrderByNameAsc(StatusEnum.INACTIVE, ProfileEnum.DRIVER, ProfileEnum.STUDENT)
				.collect(Collectors.toList());
	}
	
}
