package com.example.quanlydaotao.controller;

import com.example.quanlydaotao.dto.NotificationDTO;
import com.example.quanlydaotao.model.Notification;
import com.example.quanlydaotao.model.NotificationToUser;
import com.example.quanlydaotao.model.Role;
import com.example.quanlydaotao.model.User;
import com.example.quanlydaotao.repository.RoleRepository;
import com.example.quanlydaotao.repository.UserRepository;
import com.example.quanlydaotao.service.NotificationService;
import com.example.quanlydaotao.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public ResponseEntity<?> testCode (){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findAllNotiUser (@PathVariable("id") Long id, @RequestParam(required = false) String type){
        List<NotificationToUser> listData = new ArrayList<>();
        if (type == null || type.equals("all")) {
            listData = notificationService.findAllNotiWithUserId(id);
        } else if (type.equals("un_read")) {
            listData = notificationService.findAllNotiWithUserIdAndStatus(id, false);
        }

           listData.sort((n1, n2) -> n2.getNotification().getTimestamp().compareTo(n1.getNotification().getTimestamp()));

        ArrayList<NotificationDTO> list = new ArrayList<>();
        for (NotificationToUser notificationToUser : listData) {
            NotificationDTO notificationDTO = new NotificationDTO();
            notificationDTO.setContent(notificationToUser.getNotification().getContent());
            notificationDTO.setId(notificationToUser.getId());
            notificationDTO.setIsRead(notificationToUser.getIsRead());
            notificationDTO.setTimeCreate(notificationToUser.getNotification().getTimestamp());
            notificationDTO.setTimestamp(notificationService.getTimestamp(notificationToUser.getNotification().getTimestamp()));
            notificationDTO.setLink(notificationToUser.getNotification().getLink());
            list.add(notificationDTO);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> setStatusNotification (@RequestBody List<NotificationDTO> notificationlist){
        if (notificationlist != null && !notificationlist.isEmpty()) {
            for (NotificationDTO notificationDTO : notificationlist) {
                NotificationToUser notification = notificationService.findNotificationToUserById(notificationDTO.getId()).get();
                notification.setIsRead(notificationDTO.getIsRead());
                notificationService.saveNotiToUser(notification);
            }
        }
        return new ResponseEntity<>(true,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNotification(@RequestBody NotificationDTO notification){
        Notification notificationEntity = new Notification();
        notificationEntity.setContent(notification.getContent());
        notificationEntity.setTimestamp(LocalDateTime.now());


        if (notification.getCreatorId() != null) {
            notificationEntity.setCreator(userRepository.findById(notification.getCreatorId()).get());
        }

        // Link chuyển trang khi click vào notification
        if (notification.getContent() != null){
            notificationEntity.setLink(notification.getLink());
        }
        try {
            notificationEntity = notificationService.addNotification(notificationEntity);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // Xu ly sach nguoi nhan thong bao
        List<User> receiverList = new ArrayList<>();

        List<String> roles = notification.getListRoleReceiver();

        // Lay danh sach theo id neu co
        if (notification.getListReceiverId() != null){
            for (Long id : notification.getListReceiverId()){
                User user = userRepository.findById(id).get();
                receiverList.add(user);
            }
        }

        // Lay danh sach theo role neu co
        else if (roles != null){;
            receiverList = userRepository.findAllByRoles(roleRepository.findByName(roles.get(0)));
           for (int i = 1; i < roles.size(); i++){
               List<User> userList = userRepository.findAllByRoles(roleRepository.findByName(roles.get(i)));
               for (User user : userList){
                   if (!receiverList.contains(user)){
                       receiverList.add(user);
                   }
               }
           }
        }

        else if (notification.getListReceiverEmail() != null) {
            for (String email : notification.getListReceiverEmail()){
                User user = userRepository.findByEmail(email);
                if (!receiverList.contains(user)){
                    receiverList.add(user);
                }
            }
        }

        // Gui thong bao
        for (User user : receiverList) {
            NotificationToUser notificationToUser = new NotificationToUser();
            notificationToUser.setNotification(notificationEntity);
            notificationToUser.setUser(user);
            notificationToUser.setIsRead(false);
            notificationService.saveNotiToUser(notificationToUser);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
