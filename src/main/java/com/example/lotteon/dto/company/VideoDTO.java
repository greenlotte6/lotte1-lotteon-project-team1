package com.example.lotteon.dto.company;

import lombok.*;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class VideoDTO {
    private Long id;
    private String youtubeUrl;
    private String thumbnailUrl; // ★ thumbnail 객체 대신 String url로
    private String title;
    private String description;



}
