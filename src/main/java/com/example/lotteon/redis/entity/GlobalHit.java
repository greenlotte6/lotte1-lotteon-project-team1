package com.example.lotteon.redis.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@Data
@RedisHash(value = "global")
@NoArgsConstructor
@AllArgsConstructor
public class GlobalHit {

  @Id
  private final String id = "counter";

  private long hit;

  @TimeToLive
  private long ttl;
}
