package com.example.quanlydaotao.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "role_user")
public class RoleUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role roleId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
}
