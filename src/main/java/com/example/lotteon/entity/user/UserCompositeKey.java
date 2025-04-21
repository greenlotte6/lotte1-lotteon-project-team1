package com.example.lotteon.entity.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class UserCompositeKey implements Serializable {

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

}
