INSERT INTO `product_image` VALUES (1, "", "" ,"", "");

INSERT INTO `product_category` VALUES(1, "의류");

INSERT INTO `product_subcategory` VALUES(1, "상의");

INSERT INTO `product` VALUES(1, 1, 1, "맨투맨", "맨투맨입니다", "무신사", 39000, 39, 10, 200, 2500, 1, "신제품", 1, "통신판매업", 1, "국내산");

INSERT INTO `product_options` VALUES (1, 1, "사이즈", "S");
INSERT INTO `product_options` VALUES (2, 1, "사이즈", "M");
INSERT INTO `product_options` VALUES (3, 1, "사이즈", "L");
INSERT INTO `product_options` VALUES (4, 1, "사이즈", "XL");

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

-- 상점(seller)
INSERT INTO `user` VALUES ("seller1", "$2a$12$7nR.CgoesCyfsETEl74Dtuk0Mu2wEzmJPCljlJddYY14UmIY100uG", "seller1@example.com", "051-123-4567", "12345", "부산광역시 부산진구", "행복로 127-11", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-12345", "seller1", "김유신", "(주)행복상점", "2025-부산진구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller2", "abc123", "seller2@example.com", "051-123-4561", "12345", "부산광역시 부산진구", "행복로 127-11", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-12525", "seller2", "김유신", "(주)행복상점", "2025-부산진구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller3", "abc123", "seller3@example.com", "051-111-2222", "12345", "부산광역시", "해운대구", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-11111", "seller3", "김유신", "(주)행복상점", "2025-해운대구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller4", "abc123", "seller4@example.com", "051-111-2223", "12345", "부산광역시", "해운대구", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-11112", "seller4", "김유신", "(주)행복상점", "2025-해운대구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller5", "abc123", "seller5@example.com", "051-111-2224", "12345", "부산광역시", "해운대구", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-11113", "seller5", "김유신", "(주)행복상점", "2025-해운대구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller6", "abc123", "seller6@example.com", "051-111-2225", "12345", "부산광역시", "해운대구", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-11114", "seller6", "김유신", "(주)행복상점", "2025-해운대구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller7", "abc123", "seller7@example.com", "051-111-2226", "12345", "부산광역시", "해운대구", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-11115", "seller7", "김유신", "(주)행복상점", "2025-해운대구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller8", "abc123", "seller8@example.com", "051-111-2227", "12345", "부산광역시", "해운대구", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-11116", "seller8", "김유신", "(주)행복상점", "2025-해운대구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller9", "abc123", "seller9@example.com", "051-111-2228", "12345", "부산광역시", "해운대구", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-11117", "seller9", "김유신", "(주)행복상점", "2025-해운대구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller10", "abc123", "seller11@example.com", "051-111-2230", "12345", "부산광역시", "해운대구", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-11118", "seller10", "김유신", "(주)행복상점", "2025-해운대구-12345", "0503-1234-5678", "ready");
INSERT INTO `user` VALUES ("seller11", "abc123", "seller10@example.com", "051-111-2229", "12345", "부산광역시", "해운대구", "seller", NOW());
INSERT INTO `seller` VALUES ("112-12-11119", "seller11", "김유신", "(주)행복상점", "2025-해운대구-12345", "0503-1234-5678", "ready");

-- 회원(member)
INSERT INTO `user` VALUES ("abc123", "abc@123", "abc123@example.com", "010-1111-2222", "12345", "부산광역시", "부산진구", "member", NOW());
INSERT INTO `member` (`user_id`, `name`, `gender`, `recent_login_date`) VALUES ("abc123", "장보고", "m", NOW());
INSERT INTO `user` VALUES ("jas06113", "abc@123", "jas06113@example.com", "010-1211-2222", "12345", "부산광역시", "부산진구", "member", NOW());
INSERT INTO `member` (`user_id`, `name`, `gender`, `recent_login_date`) VALUES ("jas06113","이현민", "m", NOW());

-- 포인트 내역(point_history)
INSERT INTO `point` VALUES(1, "abc123", 1000, "회원가입 기념 포인트 1,000원", NOW(), 1000);
INSERT INTO `point` VALUES(2, "jas06113", 1000, "회원가입 기념 포인트 1,000원", NOW(), 1000);
INSERT INTO `point` VALUES(3, "jas06113", 1000, "회원가입 기념 포인트 1,000원", NOW(), 2000);
INSERT INTO `point` VALUES(4, "jas06113", 1000, "회원가입 기념 포인트 1,000원", NOW(), 3000);
INSERT INTO `point` VALUES(5, "jas06113", 1000, "회원가입 기념 포인트 1,000원", NOW(), 4000);
INSERT INTO `point` VALUES(6, "jas06113", 1000, "회원가입 기념 포인트 1,000원", NOW(), 5000);

INSERT INTO `terms` VALUES (1, "구매자 약관", "구매자 약관 테스트");
INSERT INTO `terms` VALUES (2, "판매자 약관", "판매자 약관 테스트");
INSERT INTO `terms` VALUES (3, "해외사업자 약관", "해외사업자 약관 테스트");
INSERT INTO `terms` VALUES (4, "위치기반서비스 약관", "위치기반서비스 약관 테스트");
INSERT INTO `terms` VALUES (5, "개인정보처리기본약관", "개인정보처리기본 약관 테스트");

SELECT *
FROM `point`
JOIN `member`
ON `member`.user_id = `point`.member_id
JOIN `user`
ON `member`.`user_id`=`user`.id;