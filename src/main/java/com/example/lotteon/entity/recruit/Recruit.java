package com.example.lotteon.entity.recruit;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CollectionId;

import java.time.LocalDate;


@Entity
@Table(name = "recruit")
@Getter
@Setter
@ToString
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

    // 채용 상세 내용
    // getter와 setter 추가
    private String content;



}
