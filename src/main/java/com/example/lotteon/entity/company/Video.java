package com.example.lotteon.entity.company;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@Entity
@Getter
@Setter
@Table(name = "video")
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Video {

    @Id
    private Long id;
    private String title;
    private String youtube_url;
    private String thumbnail_url;
    private String description;

    // 필요한 추가 필드 및 메소드
}
