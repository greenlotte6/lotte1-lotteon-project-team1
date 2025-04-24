package com.example.lotteon.entity.recruit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "recruit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recruit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String dept;
    private String career;
    private String hireType;
    private String title;
    private String status;

    private LocalDate startDate;
    private LocalDate endDate;
}
