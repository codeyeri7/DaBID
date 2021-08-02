package com.ssafy.api.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GoogleOauth {
    @Value("${sns.google.url}")
    private String GOOGLE_SNS_BASE_URL;
    @Value("${sns.google.client.id}")
    private String GOOGLE_SNS_CLIENT_ID;
    @Value("${sns.google.callback.url}")
    private String GOOGLE_SNS_CALLBACK_URL;
    @Value("${sns.google.client.secret}")
    private String GOOGLE_SNS_CLIENT_SECRET;
    @Value("${sns.google.token.base.url}")
    private String GOOGLE_SNS_TOKEN_BASE_URL;

    /**
     * 각 Social Login 페이지로 Redirect 처리할 URL Build
     * 사용자로부터 로그인 요청을 받아 Social Login Server 인증용 code 요청
     */
    public String getOauthRedirectURL() {
        Map<String, Object> params = new HashMap<>();
        params.put("scope", "profile");
        params.put("response_type", "code");
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);

        String parameterString = params.entrySet().stream()
                .map(x -> x.getKey() + "=" + x.getValue())
                .collect(Collectors.joining("&"));

        // https://accounts.google.com/o/oauth2/v2/auth?scope=profile&response_type=code&client_id=~~~&redirect_uri=~~~
        return GOOGLE_SNS_BASE_URL + "?" + parameterString;
    }

    /**
     * API Server로부터 받은 code를 활용하여 사용자 인증 정보 요청
     * @param code API Server 에서 받아온 code
     * @return API 서버로 부터 응답받은 Json 형태의 결과를 string으로 반환
     */
    public String requestAccessToken(String code) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> params = new HashMap<>();
        params.put("code", code);
        params.put("client_id", GOOGLE_SNS_CLIENT_ID);
        params.put("client_secret", GOOGLE_SNS_CLIENT_SECRET);
        params.put("redirect_uri", GOOGLE_SNS_CALLBACK_URL);
        params.put("grant_type", "authorization_code");

        ResponseEntity<String> responseEntity =
                restTemplate.postForEntity(GOOGLE_SNS_TOKEN_BASE_URL, params, String.class);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            Gson gson = new GsonBuilder().create();
            Map<String, Object> map = gson.fromJson(responseEntity.getBody(), Map.class);

            for (Map.Entry<String, Object> entry : map.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue());
            }

            Map<String, Object> paramForEmail = new HashMap<>();
            paramForEmail.put("access_token", map.get("access_token"));
            ResponseEntity<String> response =
                    restTemplate.postForEntity("https://www.googleapis.com/oauth2/v2/userinfo",
                            paramForEmail, String.class);

            System.out.println(response.getBody());
//            Map<String, Object> map2 = gson.fromJson(response.getBody(), Map.class);
//            for (Map.Entry<String, Object> entry : map2.entrySet()) {
//                System.out.println(entry.getKey() + "=" + entry.getValue());
//            }


//            return responseEntity.getBody();
            return response.getBody();
        }
        return null;
    }
}
