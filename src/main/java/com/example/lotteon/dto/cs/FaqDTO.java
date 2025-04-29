/*
    작성자 : 손준오(sjo112777)

*/
package com.example.lotteon.dto.cs;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class FaqDTO {

    private int id;
    private String title;
    private String content;
    private String register_date;
    private int hit;
    private int type_id;

    private String name;
    private String subtype_name;
}

