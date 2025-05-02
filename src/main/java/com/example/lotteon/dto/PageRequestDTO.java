package com.example.lotteon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {

    private int id;
    private String seller_id;
    private int coupon_id;
    private String user_id;

    @Builder.Default
    private int no = 1;

    @Builder.Default
    private int type_id = 0;

    @Builder.Default
    private int pg = 1;

    @Builder.Default
    private int size = 10;

    private String filter;

    private String name;

    private String searchType;
    private String keyword;

    @Builder.Default
    private boolean isMainPage = false;  // 메인 페이지 여부 (기본값 false)

    // 메인 페이지 여부 설정
    public void setIsMainPage(boolean isMainPage) {
        this.isMainPage = isMainPage;
    }

    // 글목록 페이징 처리를 위한 Pageable 객체 생성 메서드
    public Pageable getPageable(String sort) {
        // 메인 페이지일 경우 최신 5개만 가져오는 로직
        if (isMainPage) {
            return PageRequest.of(0, 5, Sort.by(sort).descending());
        } else {
            return PageRequest.of(this.pg - 1, this.size, Sort.by(sort).descending());
        }
    }
}
