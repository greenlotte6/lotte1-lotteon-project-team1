package com.example.lotteon.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "testuser")
public class User {

    @Id
    private String uid;
    private String pass;
    private String name;
    private String nick;
    private String email;
    private String hp;

    @Column(nullable = false)
    private String role;

    private String zip;
    private String addr1;
    private String addr2;
    private String regip;

    @CreationTimestamp
    private LocalDateTime regDate;

    private String leaveDate;

    @PrePersist
    public void prePersist(){
        if(this.role == null){
            this.role = "USER";
        }
    }

}
