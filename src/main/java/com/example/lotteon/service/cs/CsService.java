package com.example.lotteon.service.cs;

import com.example.lotteon.dto.cs.NoticeDTO;
import com.example.lotteon.entity.cs.Article_Type;
import com.example.lotteon.entity.cs.Notice;
import com.example.lotteon.repository.cs.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class CsService {

    private final NoticeRepository noticeRepository;
    private final ModelMapper modelMapper;

    public int noticeRegister(NoticeDTO noticeDTO) {

        Notice notice = Notice.builder()
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .hit(0)
                .register_date(LocalDate.now())
                .type_id(Article_Type.builder().id(noticeDTO.getType_id()).build())
                .build();

        //Notice notice = modelMapper.map(noticeDTO, Notice.class);

        //JPA 저장
        Notice savedNotice = noticeRepository.save(notice);

        //저장한 글번호 반환
        return savedNotice.getId();
    }

    public NoticeDTO findById(int id) {

        Optional<Notice> optNotice = noticeRepository.findById(id);

        if (optNotice.isPresent()) {
            Notice notice = optNotice.get();
            Article_Type type = notice.getType_id();

            // ⭐ 수동 변환으로 문제 해결
            NoticeDTO noticeDTO = NoticeDTO.builder()
                    .id(notice.getId())
                    .title(notice.getTitle())
                    .content(notice.getContent())
                    .register_date(notice.getRegister_date().toString())
                    .hit(notice.getHit())
                    .type_id(notice.getType_id().getId()) // 핵심
                    .subtype_name(type.getSubtype_name())
                    .build();

            notice.setHit(noticeDTO.getHit() + 1);
            noticeRepository.save(notice);

            return noticeDTO;

        }
        return null;
    }
}
