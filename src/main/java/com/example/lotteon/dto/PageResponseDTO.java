package com.example.lotteon.dto;

import com.example.lotteon.dto.cs.NoticeDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDTO {

    private List<NoticeDTO> dtoList;

    private int type_id;
    private int pg;
    private int size;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev, next;

    private boolean first, last; // 첫 페이지와 마지막 페이지 여부
    private int lastPage; // 마지막 페이지 번호 추가

    private String filter;

    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<NoticeDTO> dtoList, int total){
        this.type_id = pageRequestDTO.getType_id();
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;

        this.filter = pageRequestDTO.getFilter();

        this.startNo = total - ((pg - 1) * size);
        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;

        int last = (int) (Math.ceil(total / (double) size));
        this.end = end > last ? last : end;
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }
}





