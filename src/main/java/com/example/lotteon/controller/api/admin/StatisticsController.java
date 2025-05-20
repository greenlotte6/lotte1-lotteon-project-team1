package com.example.lotteon.controller.api.admin;

import com.example.lotteon.service.admin.statistics.StatisticsService;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/stat")
public class StatisticsController {

  private final StatisticsService statService;
  private final Gson gson;

  @GetMapping("/chart")
  public ResponseEntity<String> getBarStat() {
    LocalDate weekAgo = LocalDate.now().minusDays(14);
    LocalDate today = LocalDate.now();
    String barData = statService.getBarData(weekAgo, today);
    //String pieData = statService.getPieData(weekAgo, today);

    JsonObject json = new JsonObject();
    json.addProperty("barData", barData);
    //json.addProperty("pieData", pieData);

    String jsonBody = gson.toJson(json);

    return ResponseEntity.ok(jsonBody);
  }
}
