package com.example.lotteon.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionCartDTO {
    private int productId;  // 상품 ID
    private String productName;  // 상품명
    private int price;  // 상품 가격
    private int discountRate;  // 할인율
    private int point; //포인트
    private int dil; //배송비
    private int quantity;  // 수량
    private int totalPrice;  // 총합 (상품 가격 * 수량)
}
