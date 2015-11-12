package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Spring Securityの設定
 */
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /* 
     * 特定のリクエストに対して「セキュリティ設定」を無視する設定など、全体に関わる設定
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 静的リソースへのアクセスに対してのセキュリティ設定を無視する
        web.ignoring().antMatchers("/webjars/**", "/css/**", "/js/**");
    }


    /* 
     * 認可の設定や、ログイン・ログアウトに関する設定
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 認可設定
        http.authorizeRequests().antMatchers("/loginForm").permitAll().anyRequest().authenticated();
        // ログイン設定
        http.formLogin().loginProcessingUrl("/login").loginPage("/loginForm").failureUrl("/loginForm?error") 
                .defaultSuccessUrl("/customers", true).usernameParameter("username").passwordParameter("password")
                .and();
        // ログアウト設定
        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout**")).logoutSuccessUrl("/loginForm");
    }

    /**
     * 認証に関する設定
     */
    @Configuration
    static class AuthenticationConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        UserDetailsService userDetailsService;

        // パスワードをハッシュ化するクラスの定義
        @Bean
        PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        /* 
         * 認証ユーザー取得の設定、パスワード照合時の設定
         */
        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        }

    }

}
