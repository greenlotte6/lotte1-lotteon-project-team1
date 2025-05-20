package com.example.lotteon.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

public class ElasticSearchConfig {

  public RestClient restClient() {
    // 기본 인증 정보 제공 (username, password)
    BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(
        new org.apache.http.auth.AuthScope("15.164.97.245", 9201), // EC2 서버의 호스트와 포트
        new UsernamePasswordCredentials("elastic", "abc123") // 사용자명과 비밀번호
    );

    // RestClient 설정 (EC2에서 리스닝하는 9201 포트에 연결)
    return RestClient.builder(
            new HttpHost("15.164.97.245", 9201, "http") // EC2 퍼블릭 IP와 포트
        )
        .setHttpClientConfigCallback(httpClientBuilder ->
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider)
        )
        .build();
  }
}
