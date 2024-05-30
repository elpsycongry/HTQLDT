package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.NotificationToUser;
import com.example.quanlydaotao.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface INotificationToUserRepository extends JpaRepository<NotificationToUser, Long> {
    List<NotificationToUser> findAllByUser(User user);
    List<NotificationToUser> findAllByUserAndIsRead(User user, Boolean isRead);


}
