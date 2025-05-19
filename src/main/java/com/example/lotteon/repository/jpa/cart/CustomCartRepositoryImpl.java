package com.example.lotteon.repository.jpa.cart;

import com.example.lotteon.entity.product.Cart;
import com.example.lotteon.entity.product.QCart;
import com.example.lotteon.entity.product.QProduct;
import com.example.lotteon.entity.user.QMember;
import com.example.lotteon.entity.user.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class CustomCartRepositoryImpl implements CustomCartRepository {

  private final JPAQueryFactory query;
  private final QCart cart = QCart.cart;
  private final QProduct product = QProduct.product;
  private final QMember member = QMember.member;
  private final QUser user = QUser.user;

  @Override
  public List<Cart> findAllByMemberId(String memberId) {
    return query.selectFrom(cart)
        .join(cart.product).on(cart.product.id.eq(product.id))
        .join(cart.member, member)
        .join(member.memberId.user, user)
        .where(user.id.eq(memberId))
        .fetch();
  }

  @Override
  @Transactional
  public void deleteByMemberId(String memberId) {
    List<Integer> targetIds = query.select(cart.id).from(cart)
        .join(cart.product).on(cart.product.id.eq(product.id))
        .join(cart.member, member)
        .join(member.memberId.user, user)
        .where(user.id.eq(memberId))
        .fetch();

    for (Integer targetId : targetIds) {
      query.delete(cart)
          .where(cart.id.eq(targetId))
          .execute();
    }
  }
}
