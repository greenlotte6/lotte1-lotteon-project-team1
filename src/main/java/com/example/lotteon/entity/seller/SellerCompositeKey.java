package com.example.lotteon.entity.seller;

import com.example.lotteon.entity.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class SellerCompositeKey {

  @Column(name = "business_number")
  private String businessNumber;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

}
