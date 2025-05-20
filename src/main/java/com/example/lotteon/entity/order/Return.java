package com.example.lotteon.entity.order;

import com.example.lotteon.entity.user.Member;
import com.google.gson.annotations.SerializedName;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "return")
public class Return {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JoinColumn(name = "order_number")
    @OneToOne
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "user_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "reason_id")
    private ReturnReason returnReason;

    private String description;

}
