package com.example.quanlydaotao.dto;

import com.example.quanlydaotao.model.NotificationToUser;
import com.example.quanlydaotao.model.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationDTO {
    private Long id;
    private Long creatorId;
    private Long receiverId;
    private String content;
    private Boolean isRead;
    private LocalDateTime timeCreate;
    private String timestamp;
    private String roleReceiver;
    private List<NotificationToUser> notificationToUserList;
    private List<Long> listReceiverId;
    private List<String> listRoleReceiver;
}
