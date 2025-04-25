package com.example.lotteon.entity.recruit;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

import java.time.LocalDate;


@Entity
@Table(name = "recruit")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Recruit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private RecruitDepartment dept;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "career_id")
    private RecruitCareer career;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employment_type_id")
    private EmploymentType hireType;
    private String title;

    @Column(name = "description")
    private String status;

    private LocalDate dateFrom;
    private LocalDate dateTo;


}
