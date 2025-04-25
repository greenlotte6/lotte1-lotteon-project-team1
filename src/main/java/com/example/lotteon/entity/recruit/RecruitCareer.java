package com.example.lotteon.entity.recruit;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recruit_career")
public class RecruitCareer {

    @Id
    private int id;
    private String type;
}
