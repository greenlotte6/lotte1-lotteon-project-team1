package com.example.lotteon.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartDTO {

    private String member_id;
    private int product_id;
    private LocalDate register_date;
    private int quantity;
    private int price;
    private int dil;
    private int totalPrice;
}