package com.example.lotteon.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SiteDTO { //JSON의 최상위 노드의 이름이 site여야 함. ex: "site":{"title": "hello", "subtitle":"world"}

  private String title;

  private String subtitle;
}
