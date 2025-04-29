package com.example.lotteon.entity.recruit;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Year;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recruit_career")
public class RecruitCareer {

    @Id
    private int id;
    private String career_year;
}
