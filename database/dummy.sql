INSERT INTO `product_category` VALUES(1, "의류");

INSERT INTO `product_subcategory` VALUES(1, "상의");

INSERT INTO `product_options` VALUES (1, "사이즈", "S, M, L, XL");

INSERT INTO `product_image` VALUES (1, "", "" ,"", "");

INSERT INTO `product` VALUES(1, 1, 1, 1, "맨투맨", "맨투맨입니다", "무신사", 39000, 39, 10, 200, 2500, 1, "신제품", 1, "통신판매업", 1, "국내산");

-- 쿠폰/혜택/이벤트
INSERT INTO article_type (id, name, subtype_name) VALUES
(1, '공지사항', '고객서비스'),
(2, '공지사항', '이벤트'),
(3, '공지사항', '안전거래'),
(4, '공지사항', '위해상품');

-- 쿠폰/혜택/이벤트
INSERT INTO article_type (id, name, subtype_name) VALUES
(5, '회원', '가입'),
(6, '회원', '탈퇴'),
(7, '회원', '회원정보'),
(8, '회원', '로그인');


-- 쿠폰/혜택/이벤트
INSERT INTO article_type (id, name, subtype_name) VALUES
(9,  '쿠폰/혜택/이벤트', '쿠폰/할인혜택'),
(10, '쿠폰/혜택/이벤트', '포인트'),
(11, '쿠폰/혜택/이벤트', '제휴'),
(12, '쿠폰/혜택/이벤트', '이벤트');

-- 주문/결제
INSERT INTO article_type (id, name, subtype_name) VALUES
(13, '주문/결제', '상품'),
(14, '주문/결제', '결제'),
(15, '주문/결제', '구매내역'),
(16, '주문/결제', '영수증/증빙');

-- 배송
INSERT INTO article_type (id, name, subtype_name) VALUES
(17, '배송', '배송상태/기간'),
(18, '배송', '배송정보확인/변경'),
(19, '배송', '해외배송'),
(20, '배송', '당일배송'),
(21, '배송', '해외직구');

-- 취소/반품/교환
INSERT INTO article_type (id, name, subtype_name) VALUES
(22, '취소/반품/교환', '반품신청/철회'),
(23, '취소/반품/교환', '반품정보확인/변경'),
(24, '취소/반품/교환', '교환 AS신청/철회'),
(25, '취소/반품/교환', '교환정보확인/변경'),
(26, '취소/반품/교환', '취소신청/철회'),
(27, '취소/반품/교환', '취소확인/환불정보');

-- 여행/숙박/항공
INSERT INTO article_type (id, name, subtype_name) VALUES
(28, '여행/숙박/항공', '여행/숙박'),
(29, '여행/숙박/항공', '항공');

-- 안전거래
INSERT INTO article_type (id, name, subtype_name) VALUES
(30, '안전거래', '서비스 이용규칙 위반'),
(31, '안전거래', '지식재산권침해'),
(32, '안전거래', '법령 및 정책위반 상품'),
(33, '안전거래', '게시물 정책위반'),
(34, '안전거래', '직거래/외부거래유도'),
(35, '안전거래', '표시광고'),
(36, '안전거래', '청소년 위해상품/이미지');

-- 공지사항
INSERT INTO `notice` VALUES (1, "공지사항 입니다", "공지사항 테스트 입니다", NOW(), 1, 0);

INSERT INTO `notice` VALUES (2, "공지사항 입니다2", "공지사항 테스트 입니다", NOW(), 2, 0);

INSERT INTO `notice` VALUES (3, "공지사항 입니다3", "공지사항 테스트 입니다", NOW(), 3, 0);

INSERT INTO `notice` VALUES (4, "공지사항 입니다", "공지사항 테스트 입니다", NOW(), 4, 0);

-- 자주묻는질문 > 회원
INSERT INTO `faq` VALUES (1, "가입 관련 FAQ입니다.", 5, "가입 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (2, "탈퇴 관련 FAQ입니다.", 6, "탈퇴 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (3, "회원정보 관련 FAQ입니다.", 7, "회원정보 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (4, "로그인 관련 FAQ입니다.", 8, "로그인 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (5, "가입 관련 FAQ입니다.", 5, "가입 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (6, "탈퇴 관련 FAQ입니다.", 6, "탈퇴 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (7, "회원정보 관련 FAQ입니다.", 7, "회원정보 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (8, "로그인 관련 FAQ입니다.", 8, "로그인 관련 FAQ 테스트 입니다.", NOW(), 0);

-- 자주묻는질문 > 쿠폰/혜택/이벤트
INSERT INTO `faq` VALUES (9, "쿠폰/할인혜택 관련 FAQ입니다.", 9, "쿠폰/할인혜택FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (10, "포인트 관련 FAQ입니다.", 10, "포인트 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (11, "제휴 관련 FAQ입니다.", 11, "제휴 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (12, "이벤트 관련 FAfaqQ입니다.", 12, "이벤트 관련 FAQ 테스트 입니다.", NOW(), 0);

-- 자주묻는질문 > 주문/결제
INSERT INTO `faq` VALUES (13, "상품 관련 FAQ입니다.", 13, "상품 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (14, "결제 관련 FAQ입니다.", 14, "결제 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (15, "구매내역 관련 FAQ입니다.", 15, "구매내역 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (16, "영수증/증빙 관련 FAQ입니다.", 16, "영수증/증빙 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (17, "상품 관련 FAQ입니다.", 13, "상품 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (18, "결제 관련 FAQ입니다.", 14, "결제 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (19, "구매내역 관련 FAQ입니다.", 15, "구매내역 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (20, "영수증/증빙 관련 FAQ입니다.", 16, "영수증/증빙 관련 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (21, "상품 관련 FAQ입니다.", 13, "상품 FAQ 테스트 입니다.", NOW(), 0);
INSERT INTO `faq` VALUES (22, "결제 관련 FAQ입니다.", 14, "결제 관련 FAQ 테스트 입니다.", NOW(), 0);
