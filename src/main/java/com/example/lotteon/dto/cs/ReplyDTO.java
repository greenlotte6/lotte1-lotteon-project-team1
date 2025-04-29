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
public class ReplyDTO {

    private int id;
    private int qna_id;
    private String content;

    private QnaDTO qna;

}
