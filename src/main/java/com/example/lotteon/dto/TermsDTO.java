package com.example.lotteon.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class TermsDTO {

   private int id;
   private String title;
   private String content;

}
