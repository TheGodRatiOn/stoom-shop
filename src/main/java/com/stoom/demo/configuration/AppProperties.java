package com.stoom.demo.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppProperties {

    private final AccessToken accessToken = new AccessToken();

    private final RefreshToken refreshToken = new RefreshToken();

    public interface Token {

        String getSecret();
        Long getExpire();
    }

    @Getter
    @Setter
    public static class AccessToken implements Token {
        private final String ACCESS_SECRET = "stoomAPIaccesstoken";
        private final long ACCESS_EXPIRE = 36000000;

        private String secret = ACCESS_SECRET;
        private Long expire = ACCESS_EXPIRE;
    }

    @Setter
    @Getter
    public static class RefreshToken implements Token {
        private final String REFRESH_SECRET = "stoomAPIrefreshtoken";
        private final long REFRESH_EXPIRE = 864000000;

        private String secret = REFRESH_SECRET;
        private Long expire = REFRESH_EXPIRE;
    }
}
