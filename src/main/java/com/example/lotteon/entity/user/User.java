package com.example.lotteon.entity.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "user")
public class User {

  private static final String[] ROLES = {"member", "seller", "admin"};

  @Id
  private String id;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "contact")
  private String contact;

  @Column(name = "role", nullable = false)
  private String role;

  @Column(name = "zip")
  private String zip;

  @Column(name = "address")
  private String address;

  @Column(name = "address_detail")
  private String addressDetail;

  @CreationTimestamp
  @Column(name = "register_date")
  private LocalDate registerDate;

  //@PrePersist
  //public void prePersist() {
  //  if (this.role == null) {
  //    this.role = "member";
  //  }
  //}
}
