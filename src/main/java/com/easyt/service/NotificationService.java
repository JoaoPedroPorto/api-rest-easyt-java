package com.easyt.service;

import com.easyt.exception.ApplicationException;
import com.easyt.request.NotificationRequest;
import com.easyt.response.NotificationResponse;

public interface NotificationService {

	NotificationResponse createNotification(NotificationRequest notification) throws ApplicationException;

	void associateNotificationToUsers(NotificationResponse notification) throws ApplicationException;

}
