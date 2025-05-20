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

-- 사용자
INSERT INTO `user` VALUES("admin1", "$2a$12$L7IovRMdbD4aZUJ0stXkseHCX6/mxyVEM8IdrkWODngVfPQoVjga2", "admin1@example.com", "010-2313-6023", "12345", "부산광역시 부산진구", "행복동 101-1", "admin", NOW());
INSERT INTO `user` VALUES("member1", "$2a$10$/dP6IfOvrnvJxovfLgIuS.c2HLQQfHK8ni/fpmBY4zq1ktFf1CU32", "jas06113@gmail.com", "010-4356-1697", "48277", "부산 수영구 민락동 161-25", "행복아파트 103동 803호", "member", "2025-05-12");
INSERT INTO `member` VALUES("member1", "이현민", "m", "2025-05-12", NULL, "normal", "family", "1999-04-16");

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

-- 상품
INSERT INTO `product_image` VALUES (1, "/upload/product/t-shirt.jpg", "/upload/product/t-shirt.jpg" ,"/upload/product/t-shirt.jpg", "/upload/product/t-shirt.jpg");

INSERT INTO `product_category` VALUES(1, "의류", 1);
INSERT INTO `product_category` VALUES(2, "화장품", 8);
INSERT INTO `product_category` VALUES(3, "식품", 9);
INSERT INTO `product_category` VALUES(4, "생활/건강", 23);
INSERT INTO `product_category` VALUES(5, "가전", 27);


INSERT INTO `product_subcategory` VALUES(1, 1, "상의", 1);
INSERT INTO `product_subcategory` VALUES(2, 1, "하의", 2);
INSERT INTO `product_subcategory` VALUES(3, 1, "악세서리", 3);
INSERT INTO `product_subcategory` VALUES(4, 2, "폼클렌징", 4);
INSERT INTO `product_subcategory` VALUES(5, 2, "파운데이션", 5);
INSERT INTO `product_subcategory` VALUES(6, 2, "마스크팩", 6);

INSERT INTO `product` VALUES("2025010001", 1, 1,"112-12-12345", "seller1", "맨투맨", "맨투맨입니다", 39000, 39, 10, 200, 2500, 1, "on_sale", 1, "통신판매업", 1, "국내산", "new");
INSERT INTO `product` VALUES("2025010002", 1, 1,"112-12-12525", "seller2", "후드티", "후드티입니다", 49000, 100, 10, 200, 2500, 1, "on_sale", 1, "통신판매업", 1, "국내산", "new");
INSERT INTO `product` VALUES("2025010003", 1, 1,"112-12-12525", "seller2", "카고팬츠", "카고팬츠입니다", 49000, 100, 10, 200, 2500, 1, "on_sale", 1, "통신판매업", 1, "국내산", "new");
INSERT INTO `product_options` VALUES (1, "2025010001", "사이즈", "S");
INSERT INTO `product_options` VALUES (2, "2025010001", "사이즈", "M");
INSERT INTO `product_options` VALUES (3, "2025010001", "사이즈", "L");
INSERT INTO `product_options` VALUES (4, "2025010001", "사이즈", "XL");
INSERT INTO `product_options` VALUES (5, "2025010002", "사이즈", "FREE");

-- 회원(member)
INSERT INTO `user` VALUES ("abc123", "abc@123", "abc123@example.com", "010-1111-2222", "12345", "부산광역시", "부산진구", "member", NOW());
INSERT INTO `member` (`user_id`, `name`, `gender`, `recent_login_date`, `birth_date`) VALUES ("abc123", "장보고", "m", NOW(), "2003-01-01");
INSERT INTO `user` VALUES ("xyz123", "abc@123", "xyz123@example.com", "010-1111-2223", "12345", "부산광역시", "부산진구", "member", NOW());
INSERT INTO `member` (`user_id`, `name`, `gender`, `recent_login_date`, `birth_date`) VALUES ("xyz123", "이성계", "m", NOW(), "2003-01-01");
INSERT INTO `user` VALUES ("jas06113", "abc@123", "jas06113@example.com", "010-1211-2222", "12345", "부산광역시", "부산진구", "member", NOW());
INSERT INTO `member` (`user_id`, `name`, `gender`, `recent_login_date`, `birth_date`) VALUES ("jas06113","이현민", "m", NOW(), "2003-01-01");

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

#비디오
INSERT INTO video (youtube_url, thumbnail_url, title, description)
VALUES 
('https://www.youtube.com/watch?v=9ZtsI8Hn1rg', 'https://img.youtube.com/vi/9ZtsI8Hn1rg/0.jpg', '[롯데ON] 4월9일 롯데를 가지세요! 4,300만의 브랜드 대축제!', '4.9 당신은 여기로 옵니다. 롯데 온라인 쇼핑 페스타');

#쿠폰 타입
INSERT INTO coupon_type (id, name) VALUES
(1, '개별상품할인'),
(2, '주문상품할인'),
(3, '배송비 무료');

#쿠폰혜택
INSERT INTO coupon_benefit(id, benefit) VALUES
(1, '1,000원 할인'),
(2, '2,000원 할인'),
(3, '3,000원 할인'),
(4, '4,000원 할인'),
(5, '5,000원 할인'),
(6, '10% 할인'),
(7, '20% 할인'),
(8, '30% 할인'),
(9, '40% 할인'),
(10, '50% 할인'),
(11, '배송비 무료');

#주문상태
INSERT INTO `order_status` VALUES (1, "payment_waiting") ;
INSERT INTO `order_status`  VALUES (2, "paid");
INSERT INTO `order_status`  VALUES (3, "prepare_delivery");
INSERT INTO `order_status`  VALUES (4, "on_delivery"),
(5, "delivered"), 
(6, "purchase_confirmed"), 
(7, "cancel_requested"),
(8, "cancelled"), 
(9, "refund_requetsed"), 
(10, "refunded"), 
(11, "exchange_requested"), 
(12, "exchanged");

# 주문
INSERT INTO `order` VALUES("202500001", "jas06113", "신용카드", "이현민", "010-2351-2341", "12345", "부산광역시", "남구", "빠른 배송 부탁드립니다", 2, NOW());
INSERT INTO `order` VALUES("202500002", "abc123", "신용카드", "장보고", "010-2311-3511", "12345", "부산광역시", "남구", "빠른 배송 부탁드립니다", 1, NOW());
INSERT INTO `order` VALUES("202500003", "xyz123", "신용카드", "이성계", "010-2451-1230", "12345", "부산광역시", "남구", "빠른 배송 부탁드립니다", 1, NOW());
INSERT INTO `order` VALUES("202500004", "jas06113", "신용카드","이현민", "010-2351-2341", "12345", "부산광역시", "남구", "빠른 배송 부탁드립니다", 3, NOW());

INSERT INTO `order_item` VALUES(1, "202500001", "2025010001", 1, 37600);
INSERT INTO `order_item` VALUES(2, "202500001", "2025010002", 1, 46600);
INSERT INTO `order_item` VALUES(3, "202500001", "2025010003", 1, 46600);

INSERT INTO `order_item` VALUES(4, "202500002", "2025010001", 2, 72700);

INSERT INTO `order_item` VALUES(5, "202500003", "2025010002", 2, 90700);
INSERT INTO `order_item` VALUES(6, "202500004", "2025010001", 4, 142900);

# 매출
INSERT INTO `sales` VALUES(1, "202500001", "112-12-12345", "seller1");
INSERT INTO `sales` VALUES(2, "202500002", "112-12-12345", "seller1");
INSERT INTO `sales` VALUES(3, "202500004", "112-12-12345", "seller1");
INSERT INTO `sales` VALUES(4, "202500001", "112-12-12525", "seller2");
INSERT INTO `sales` VALUES(5, "202500003", "112-12-12525", "seller2");


# 배송 회사
INSERT INTO delivery_company VALUES(1, "롯데택배");
INSERT INTO delivery_company VALUES(2, "한진택배");
INSERT INTO delivery_company VALUES(3, "CJ 대한통운");
INSERT INTO delivery_company VALUES(4, "우체국 택배");

#배송
INSERT INTO delivery VALUES(1, "D20250100001", "202500004", 1, "2025-04-08 12:28:03");

INSERT INTO return_reason (id, reason) VALUES
(1, '반품 단순 변심'),
(2, '반품 파손 및 불량'),
(3, '반품 주문 실수'),
(4, '반품 기타'),
(5, '교환 단순 교환'),
(6, '교환 파손 및 불량'),
(7, '교환 주문 실수'),
(8, '교환 기타');
########################################################################################################
SELECT @@GLOBAL.sql_mode;
SELECT @@SESSION.sql_mode;
SET GLOBAL sql_mode=(SELECT REPLACE(@@sql_mode,'ONLY_FULL_GROUP_BY',''));

# 전체 주문 데이터에서 현재 접속중인 판매자(이 경우, seller2)가 판매하는 제품을 포함하는 데이터,
# 그리고 해당 주문에 포함된 전체 상품 중 현재 접속한 판매자가 판매하는 상품의 종류의 수(= 주문 건수)
SELECT
`order`.*,
COUNT(`order_item`.product_id)
FROM `order`
JOIN `order_item`
ON `order_item`.order_number = `order`.order_number
JOIN `member`
ON `order`.member_id = `member`.user_id
JOIN `product`
ON `order_item`.product_id = `product`.id
WHERE `product`.seller_user_id="seller2"
GROUP BY `order`.order_number;

# 최고 관리자가 주문현황에 접속했을 경우, 주문건수는 해당 주문에 포함된 전체 상품의 종류의 수를 의미함.
SELECT 
  `o`.order_number,
  m.user_id,
  m.`name`,
  o.payment,
  o.status_id,
  o.order_date,
  p.id,
  s.business_number,
  SUM(
    ((p.price - (p.price * p.discount_rate / 100))) * oi.amount + p.delivery_fee
  ) AS total_price,
  COUNT(`oi`.product_id) AS `order_item_count`
FROM `order` o
JOIN `order_item` oi ON oi.order_number = o.order_number
JOIN `product` p ON oi.product_id = p.id
JOIN `seller` s ON p.seller_user_id = s.user_id
JOIN `member` m ON m.user_id=o.member_id
JOIN `order_status` os ON o.status_id=os.id
WHERE p.seller_user_id = 'seller1'
GROUP BY o.order_number;

SELECT
o.*,
oi.*
FROM `order` o
JOIN `order_item` oi ON oi.order_number = o.order_number
JOIN product p ON oi.product_id = p.id
JOIN seller s ON p.seller_user_id = s.user_id
WHERE o.order_number = "202500001" AND s.user_id="seller1";

# 송장번호, 택배사, 주문번호, 수령인, 상품명, 건수, 물품합계, 배송비, 배송상태, 접수일
SELECT 
d.delivery_number,
dc.company_name,
d.order_number,
o.recipient_name,
SUM(
 ((p.price - (p.price * p.discount_rate / 100))) * oi.amount + p.delivery_fee) AS total_price,
COUNT(`oi`.product_id) AS `order_item_count`,
SUM(p.delivery_fee) AS `total_delivery_fee`,
d.receipt_date
FROM `delivery` d
JOIN `delivery_company` dc
ON d.delivery_company_id=dc.id
JOIN `order` o
ON d.order_number = o.order_number
JOIN `order_item` oi
ON o.order_number = oi.order_number
JOIN `product` p
ON oi.product_id = p.id
WHERE p.seller_user_id = "seller1"
GROUP BY o.order_number;

# 배송 상세
SELECT
d.*,
oi.*
FROM `delivery` AS d
JOIN `order` AS `o`
ON o.order_number = d.order_number
JOIN `order_item` AS `oi`
ON o.order_number = oi.order_number
JOIN `product` AS p
ON oi.product_id = p.id
WHERE p.seller_user_id = "seller1"
GROUP BY o.order_number;

#각 상점(seller) 별 매출
SELECT
s.seller_business_number,
COUNT(case when o.status_id=2 then 1 ELSE NULL END) AS `paid_order_count`,
COUNT(case when o.status_id=4 then 1 ELSE NULL END) AS `on_delivery_count`,
COUNT(case when o.status_id=5 then 1 ELSE NULL END) AS `delivered_order_count`,
COUNT(case when o.status_id=6 then 1 ELSE NULL END) AS `purchase_confirmed_count`,
COUNT(DISTINCT o.order_number) AS order_count,
SUM(oi.total_price) AS total_price,
SUM(case when o.status_id=6 then ((p.price - (p.price * p.discount_rate / 100))) * oi.amount ELSE 0 end) AS confirmed_total_price
FROM `sales` s
JOIN `seller`
ON `s`.seller_business_number = seller.business_number
JOIN `order` o
ON s.order_number=o.order_number
JOIN `order_status` os
ON o.status_id = os.id
JOIN `order_item` oi
ON o.order_number=oi.order_number
JOIN `product` p
ON oi.product_id = p.id AND p.seller_business_number = s.seller_business_number
#WHERE o.order_date BETWEEN
#  DATE_FORMAT(DATE_SUB(CURDATE(), INTERVAL 1 MONTH), '%Y-%m-01') AND
#  LAST_DAY(DATE_SUB(CURDATE(), INTERVAL 1 MONTH))
WHERE o.order_date = CURDATE()
GROUP BY s.seller_business_number
order BY total_price asc;

# 특정 상점 매출
SELECT
s.seller_business_number,
COUNT(case when o.status_id=2 then 1 ELSE NULL END) AS `paid_order_count`,
COUNT(case when o.status_id=4 then 1 ELSE NULL END) AS `on_delivery_count`,
COUNT(case when o.status_id=5 then 1 ELSE NULL END) AS `delivered_order_count`,
COUNT(case when o.status_id=6 then 1 ELSE NULL END) AS `purchase_confirmed_count`,
COUNT(DISTINCT o.order_number) AS order_count,
SUM(oi.total_price) AS total_price2,
SUM(case when o.status_id=6 then SUM(oi.total_price) ELSE 0 end) AS confirmed_total_price
FROM `sales` s
JOIN `seller`
ON `s`.seller_business_number = seller.business_number
JOIN `order` o
ON s.order_number=o.order_number
JOIN `order_status` os
ON o.status_id = os.id
JOIN `order_item` oi
ON o.order_number=oi.order_number
JOIN `product` p
ON oi.product_id = p.id AND p.seller_business_number = s.seller_business_number
WHERE s.seller_business_number = "112-12-12345"
GROUP BY s.seller_business_number;

SELECT
*
FROM product_subcategory psc
JOIN product_category pc
ON psc.category_id = pc.id
GROUP BY psc.id;

SELECT
pc.id AS `category_id`,
GROUP_CONCAT(psc.category_id SEPARATOR ',') AS `subcategory_ids`
FROM product_category pc
left JOIN  product_subcategory psc
ON pc.id = psc.category_id;

SELECT 
  c.id AS category_id,
  JSON_ARRAYAGG(sc.id) AS subcategory_ids
FROM 
  product_category c
left JOIN 
  product_subcategory sc ON c.id = sc.category_id
GROUP BY 
  c.id;
  
  
SELECT
`order`.order_date AS `order_date`,
COUNT(case when `order`.status_id = 2 then 1 ELSE NULL END) AS "paid_count",
COUNT(case when `order`.status_id = 3 then 1 ELSE NULL END) AS "delivery_prepare_count",
COUNT(case when `order`.status_id = 4 then 1 ELSE NULL END) AS "on_delivery_count"
FROM `order`
WHERE `order`.order_date BETWEEN "2025-05-13" AND "2025-05-20"
GROUP BY `order`.order_date;