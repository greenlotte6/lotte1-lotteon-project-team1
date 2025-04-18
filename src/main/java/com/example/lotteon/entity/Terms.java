package com.example.lotteon.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "testterms")
public class Terms {

    @Id
    private int num;
    private String terms1;
    private String terms2;
    private String terms3;
    private String terms4;

}
