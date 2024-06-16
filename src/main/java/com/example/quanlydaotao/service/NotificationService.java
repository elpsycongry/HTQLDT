package com.example.quanlydaotao.service;

import com.example.quanlydaotao.model.Notification;
import com.example.quanlydaotao.model.NotificationToUser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface NotificationService {
    Notification addNotification(Notification notification);

    List<NotificationToUser> findAllNotiWithUserId(Long receiverId);

    String getTimestamp(LocalDateTime localDateTime);

    void saveNotiToUser(NotificationToUser notificationToUser);

    List<NotificationToUser> findAllNotiWithUserIdAndStatus(Long id, Boolean isRead);

    Optional<NotificationToUser> findNotificationToUserById(Long id);

    void saveNotiToUserByRoles(Notification notification, String role);
}
