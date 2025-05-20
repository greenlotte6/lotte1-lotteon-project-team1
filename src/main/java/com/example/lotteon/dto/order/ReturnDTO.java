package com.example.lotteon.dto.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReturnDTO {

    private int id;
    private String order_number;
    private String member_id;
    private int reason_id;
    private String description;

}
