package com.example.lotteon.dto.product;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private String member_id;
    private int product_id;
    private String register_date;
    private int quantity;
    private int price;
    private int totalPrice;
}
