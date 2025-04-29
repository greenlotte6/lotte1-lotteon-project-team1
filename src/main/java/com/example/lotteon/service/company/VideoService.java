package com.example.lotteon.service.company;

import com.example.lotteon.entity.company.Video;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.lotteon.repository.company.VideoRepository;

import java.util.List;


@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    public List<Video> getAllVideos() {
        return videoRepository.findAll();
    }
}
