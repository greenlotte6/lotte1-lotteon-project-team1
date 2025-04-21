package com.example.lotteon.entity.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
public class UserID implements Serializable {

  @OneToOne
  @JoinColumn(name = "id")
  private User user;

}
