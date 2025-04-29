/*
    작성자 : 손준오(sjo112777)

*/
package com.example.lotteon.entity.cs;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "faq")
public class Faq {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private String content;
    private int hit;

    @CreationTimestamp
    private LocalDate register_date;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private Article_Type type_id;
}
