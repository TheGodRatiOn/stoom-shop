package com.stoom.demo.configuration;

import com.stoom.demo.security.filter.DefaultTokenAuthFilter;
import com.stoom.demo.security.token.AccessTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    public static final String[] WHITE_LIST = {
            "/stoom/game/all",
            "/stoom/game/getGame",
            "/stoom/game/getGameByTitle",
            "/stoom/user/registerUser",
            "/stoom/user/authUser",
            "/stoom/user/refreshToken",
            "/configuration/**",
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    private final AccessTokenProvider accessTokenProvider;

    private final UserDetailsService userDetailsService;

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(WHITE_LIST);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf()
                .disable();
        httpSecurity
                .cors();
        httpSecurity
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        httpSecurity.
                antMatcher("/stoom/**")
                .userDetailsService(userDetailsService)
                .addFilterBefore(new DefaultTokenAuthFilter(accessTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest()
                .authenticated();
    }

    @Bean
    @Primary
    public AuthenticationManager stoomAuthenticationManagerBean() throws Exception{
        return super.authenticationManager();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception{
        authenticationManagerBuilder.userDetailsService(userDetailsService);
    }

    @Bean
    PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder(10);}
}

