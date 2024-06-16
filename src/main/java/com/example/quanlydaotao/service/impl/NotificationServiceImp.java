package com.example.quanlydaotao.service.impl;

import com.example.quanlydaotao.model.Notification;
import com.example.quanlydaotao.model.NotificationToUser;
import com.example.quanlydaotao.model.Role;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.repository.INotificationRepository;
import com.example.quanlydaotao.repository.INotificationToUserRepository;
import com.example.quanlydaotao.repository.UserRepository;
import com.example.quanlydaotao.service.NotificationService;
import com.example.quanlydaotao.service.RoleService;
import com.example.quanlydaotao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class NotificationServiceImp implements NotificationService {
    @Autowired
    private INotificationRepository notificationRepository;
    @Autowired
    private INotificationToUserRepository notiToUserRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Override
    public Notification addNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    @Override
    public List<NotificationToUser> findAllNotiWithUserId(Long receiverId) {
        return notiToUserRepository.findAllByUser(userRepository.findById(receiverId).get());
    }

    @Override
    public String getTimestamp(LocalDateTime localDateTime) {
        LocalDateTime time = LocalDateTime.now();
        LocalDateTime currentTime = LocalDateTime.of(time.getYear(),time.getMonth(),time.getDayOfMonth(),time.getHour()+7,time.getMinute(),time.getSecond());
        Duration duration = Duration.between(localDateTime, currentTime);
        long seconds = duration.getSeconds();

        if (seconds < 0){
            return "0 giây trước";
        } else if (seconds < 60) {
            return seconds + " giây trước";
        } else if (seconds < 3600) {
            long minutes = seconds / 60;
            return minutes + " phút trước";
        } else if (seconds < 86400) {
            long hours = seconds / 3600;
            return hours + " giờ trước";
        } else if (seconds < 2592000) { // 30 ngày
            long days = seconds / 86400;
            return days + " ngày trước";
        } else if (seconds < 31536000) { // 365 ngày
            long months = seconds / 2592000;
            return months + " tháng trước";
        } else {
            long years = seconds / 31536000;
            return years + " năm trước";
        }
    }

    @Override
    public void saveNotiToUser(NotificationToUser notificationToUser) {
        notiToUserRepository.save(notificationToUser);
    }

    @Override
    public List<NotificationToUser> findAllNotiWithUserIdAndStatus(Long id, Boolean isRead) {
        return notiToUserRepository.findAllByUserAndIsRead(userRepository.findById(id).get(), isRead);
    }

    @Override
    public Optional<NotificationToUser> findNotificationToUserById(Long id) {
        return notiToUserRepository.findById(id);
    }

    @Override
    public void saveNotiToUserByRoles(Notification notification, String role) {
        NotificationToUser notificationToUser = new NotificationToUser();
        notificationToUser.setNotification(notification);
        Role role_ADMIN = roleService.findByName("ROLE_ADMIN");
        Iterable<User> users = userService.findUsersByRoles(role_ADMIN);
        for (User user : users) {
            notificationToUser.setUser(user);
            notificationToUser.setIsRead(false);
            notiToUserRepository.save(notificationToUser);
        }
    }
}
