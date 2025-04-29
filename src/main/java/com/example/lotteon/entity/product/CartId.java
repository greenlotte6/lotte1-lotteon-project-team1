package com.example.lotteon.entity.product;

import java.io.Serializable;
import java.util.Objects;

public class CartId implements Serializable {

    private String memberId;
    private int product;
    public CartId() {}

    public CartId(String memberId, int product) {
        this.memberId = memberId;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartId)) return false;
        CartId cartId = (CartId) o;
        return product == cartId.product &&
                Objects.equals(memberId, cartId.memberId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(memberId, product);
    }
}