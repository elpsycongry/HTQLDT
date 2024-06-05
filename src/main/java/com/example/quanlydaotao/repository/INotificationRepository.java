package com.example.quanlydaotao.repository;

import com.example.quanlydaotao.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface INotificationRepository extends JpaRepository<Notification, Long> {

}
