package Reis.PFE.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import Reis.PFE.Entities.Notification;
import Reis.PFE.dao.NotificationRepository;
@Service
public class NotificationService {
	@Autowired
	private NotificationRepository notificationRepository;
	@Autowired
	private SimpMessagingTemplate template;
public void save(Notification notification)
{
	this.notificationRepository.save(notification);
}
public void createAndSendNotification(String desc)
{
	Notification notification=new Notification(null,desc,LocalDateTime.now());
	this.notificationRepository.save(notification);
	template.convertAndSend("/topic/notification", notification);
}

}
