package com.security.security.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "email" , nullable = false,unique = true)
    public String email;

    @Column(name = "phone" , nullable = false,unique = true)
    public String phone;

    @Column(name = "password" , nullable = false)
    public  String password;

    @CreationTimestamp
    public LocalDateTime createdAt;

    public Integer status;


    public String role;
}
