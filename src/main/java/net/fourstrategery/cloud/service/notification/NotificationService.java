package net.fourstrategery.cloud.service.notification;

import net.fourstrategery.cloud.entity.PlayerEntity;

public interface NotificationService {

	public void sendNotification(PlayerEntity player, String data);
}
