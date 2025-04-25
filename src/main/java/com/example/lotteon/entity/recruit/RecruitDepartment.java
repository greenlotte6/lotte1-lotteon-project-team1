package com.example.lotteon.entity.recruit;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recruit_department")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecruitDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;
}
