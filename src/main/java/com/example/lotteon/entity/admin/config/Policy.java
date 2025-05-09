package com.example.lotteon.entity.admin.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "terms")
public class Policy {

  @Id
  @Column(name = "id")
  private int id;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;
}
