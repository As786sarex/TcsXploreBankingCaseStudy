package com.xplore.casestudy.bankapplication.config;


import com.xplore.casestudy.bankapplication.services.UserPrincipleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AuthConfiguration extends WebSecurityConfigurerAdapter {


    public static final String ROLE_EXECUTIVE = "EXECUTIVE";
    public static final String ROLE_CASHIER = "CASHIER";
    public static final String ROLE_ADMIN = "ADMIN";


    private final UserPrincipleService userPrincipleService;
    private final CustomAuthSuccessHandler customAuthSuccessHandler;

    public AuthConfiguration(UserPrincipleService userPrincipleService, CustomAuthSuccessHandler customAuthSuccessHandler) {
        this.userPrincipleService = userPrincipleService;
        this.customAuthSuccessHandler = customAuthSuccessHandler;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers("/static/**", "/css/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers(
                        "/js/**",
                        "/css/**",
                        "/img/**",
                        "/webjars/**").permitAll()
                .antMatchers("/accounts/search_account",
                        "/accounts/deposit_money/{id}",
                        "/accounts/withdraw_money/{id}",
                        "/accounts/transfer_money/{id}",
                        "/accounts/statement_by_transaction_cnt/{id}",
                        "/accounts/statement_by_transaction_cnt/{id}/{number}",
                        "/accounts/statement_by_transaction_date/{id}",
                        "/accounts/statement_by_transaction_date_table/{id}").hasAnyAuthority(ROLE_CASHIER, ROLE_ADMIN)
                .antMatchers("/customer/create_customer",
                        "/customer/update_customer",
                        "/customer/delete_customer",
                        "/customer/customer_status",
                        "/accounts/create_account",
                        "/accounts/delete_account",
                        "/accounts/account_status").hasAnyAuthority(ROLE_EXECUTIVE, ROLE_ADMIN)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(customAuthSuccessHandler)
                .permitAll()
                .and()
                .logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(getPasswordEncoder());
        provider.setUserDetailsService(this.userPrincipleService);

        return provider;
    }

    @Bean
    PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
