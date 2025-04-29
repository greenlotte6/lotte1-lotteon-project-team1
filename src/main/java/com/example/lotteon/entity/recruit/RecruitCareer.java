package com.example.lotteon.entity.recruit;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.Year;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "recruit_career")
@Getter
@Setter
@NoArgsConstructor
public class RecruitCareer {

    @Id
    private int id;

    private String career_year;


    @Column(name = "career_year")
    private String careerYear;

}
