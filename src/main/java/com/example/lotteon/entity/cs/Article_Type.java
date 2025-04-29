/*
    작성자 : 손준오(sjo112777)

*/
package com.example.lotteon.entity.cs;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "article_type")
public class Article_Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String subtype_name;

}
