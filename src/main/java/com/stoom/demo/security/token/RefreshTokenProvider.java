package com.stoom.demo.security.token;

import com.stoom.demo.configuration.AppProperties;
import com.stoom.demo.services.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenProvider extends TokenProvider {

    public static final String HEADER = "Refresh-token";

    private static final String TOKEN_TYPE = "Bearer";

    public RefreshTokenProvider(AppProperties appProperties,
                                UserDetailsService userDetailsService){
        super(HEADER,
                TOKEN_TYPE,
                appProperties.getRefreshToken(),
                userDetailsService);
    }
}
