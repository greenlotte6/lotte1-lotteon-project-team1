/*
    작성자 : 손준오(sjo112777)
    내용:
*/
package com.example.lotteon.dto.cs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Article_TypeDTO {

    private int id;
    private String name;
    private String subtype_name;

}
