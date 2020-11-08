package com.stoom.demo.security.token;

import com.stoom.demo.configuration.AppProperties;
import com.stoom.demo.services.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AccessTokenProvider extends TokenProvider {

    public static final String HEADER = "Authorization";

    private static final String TOKEN_TYPE = "Bearer";

    public AccessTokenProvider(AppProperties appProperties,
                               UserDetailsService userDetailsService){
        super(HEADER,
                TOKEN_TYPE,
                appProperties.getAccessToken(),
                userDetailsService);
    }
}

