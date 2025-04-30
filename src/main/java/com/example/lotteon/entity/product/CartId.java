package com.example.lotteon.entity.product;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {

    private String memberId;
    private int productId; // 👈 엔티티가 아닌 기본타입

    public CartId() {}

    public CartId(String memberId, int productId) {
        this.memberId = memberId;
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartId)) return false;
        CartId cartId = (CartId) o;
        return productId == cartId.productId &&
                Objects.equals(memberId, cartId.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, productId);
    }
}


