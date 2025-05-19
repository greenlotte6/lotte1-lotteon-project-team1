package com.example.lotteon.service.company;

import com.example.lotteon.entity.company.Video;
import com.example.lotteon.repository.jpa.company.VideoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VideoService {

  private final VideoRepository videoRepository;

  public List<Video> getAllVideos() {
    return videoRepository.findAll();
  }
}
