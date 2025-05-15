package com.example.lotteon.dto.order;

import com.example.lotteon.entity.order.Order;
import com.example.lotteon.entity.order.OrderStatus;
import com.example.lotteon.entity.user.Member;
import com.example.lotteon.entity.user.MemberId;
import com.example.lotteon.entity.user.User;
import com.google.gson.annotations.SerializedName;
import com.querydsl.core.Tuple;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
public class MypageOrderWrapper {

    @SerializedName("order")
    private final Order order;

    @SerializedName("itemCount")
    private final int itemCount;

    @SerializedName("total_price")
    private final long totalPrice;

    @SerializedName("product_name")
    private final String productName;

    @SerializedName("image_path")
    private final String imagePath;

    private final String companyName;

    private final String businessNumber;

    private MypageOrderWrapper(Order order, int itemCount, long totalPrice, String productName, String imagePath, String companyName, String businessNumber) {
        this.order = order;
        this.itemCount = itemCount;
        this.totalPrice = totalPrice;
        this.productName = productName;
        this.imagePath = imagePath;
        this.companyName = companyName;
        this.businessNumber = businessNumber;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private Tuple tuple;

        public Builder tuples(Tuple tuple) {
            this.tuple = tuple;
            return this;
        }

        public MypageOrderWrapper build() {
            String orderNumber = tuple.get(0, String.class);
            String userId = tuple.get(1, String.class);
            String memberName = tuple.get(2, String.class);
            String payment = tuple.get(3, String.class);
            int statusId = tuple.get(4, int.class);
            LocalDate orderDate = tuple.get(5, LocalDate.class);
            int itemCount = tuple.get(6, int.class);
            long totalPrice = tuple.get(7, Long.class);
            String productName = tuple.get(8, String.class); // 8번째 인덱스에 있음
            String imagePath = tuple.get(9, String.class); // 9번째 인덱스 (아래 쿼리에서 추가 예정)
            String companyName = tuple.get(10, String.class);
            String businessNumber = tuple.get(11, String.class); // 마지막 인덱스


            User user = User.builder().id(userId).build();
            MemberId memberId = MemberId.builder().user(user).build();
            Member member = Member.builder().memberId(memberId).name(memberName).build();
            OrderStatus status = OrderStatus.builder().id(statusId).build();
            Order order = Order.builder()
                    .orderNumber(orderNumber)
                    .member(member)
                    .payment(payment)
                    .status(status)
                    .orderDate(orderDate)
                    .build();

            return new MypageOrderWrapper(order, itemCount, totalPrice, productName, imagePath, companyName, businessNumber);
        }
    }
}


