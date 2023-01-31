package ru.otus.highload.infrastructure

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain


@EnableWebSecurity
@Configuration
class SecurityConfiguration(
    private val userDetailsService: UserDetailsService
) {

    fun configure(web: WebSecurity) {
        web.ignoring().antMatchers("/")
    }

    @Bean
    fun authManager(http: HttpSecurity): AuthenticationManager =
        http.getSharedObject(AuthenticationManagerBuilder::class.java)
            .authenticationProvider(authProvider())
            .build()


    @Bean
    fun authProvider(): DaoAuthenticationProvider {
        val authProvider = DaoAuthenticationProvider()
        authProvider.setUserDetailsService(userDetailsService)
        authProvider.setPasswordEncoder(encoder())
        return authProvider
    }

    @Bean
    fun encoder(): PasswordEncoder {
        return BCryptPasswordEncoder(11)
    }

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
            .httpBasic()
            .and()
            .authorizeRequests()
           // .antMatchers("/actuator/**", "/api/v1/swagger-ui/**").permitAll()
            .antMatchers("/user/register").permitAll()
            .antMatchers("/user/get/**").authenticated()
            .and()
            .formLogin()
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.NEVER)
            .and()
            .logout()
            .invalidateHttpSession(true)
            .deleteCookies()
            .and()
            .exceptionHandling()
        return http.build()
    }
}

//@Bean
//fun filterChain(http: HttpSecurity): SecurityFilterChain {
//    http.csrf()
//        .disable()
//        .authorizeRequests()
//        .antMatchers(
//            "/login*",
//            "/logout*",
//            "/signin/**",
//            "/signup/**",
//            "/customLogin",
//            "/user/registration*",
//            "/registrationConfirm*",
//            "/expiredAccount*",
//            "/registration*",
//            "/badUser*",
//            "/user/resendRegistrationToken*",
//            "/forgetPassword*",
//            "/user/resetPassword*",
//            "/user/savePassword*",
//            "/updatePassword*",
//            "/user/changePassword*",
//            "/emailError*",
//            "/resources/**",
//            "/old/user/registration*",
//            "/successRegister*",
//            "/qrcode*",
//            "/user/enableNewLoc*"
//        )
//        .permitAll()
//        .antMatchers("/invalidSession*")
//        .anonymous()
//        .antMatchers("/user/updatePassword*")
//        .hasAuthority("CHANGE_PASSWORD_PRIVILEGE")
//        .anyRequest()
//        .hasAuthority("READ_PRIVILEGE")
//        .and()
//        .formLogin()
//        .loginPage("/login")
//        .defaultSuccessUrl("/homepage.html")
//        .failureUrl("/login?error=true")
//        .successHandler(myAuthenticationSuccessHandler)
//        .failureHandler(authenticationFailureHandler)
//        .authenticationDetailsSource(authenticationDetailsSource)
//        .permitAll()
//        .and()
//        .sessionManagement()
//        .invalidSessionUrl("/invalidSession.html")
//        .maximumSessions(1)
//        .sessionRegistry(sessionRegistry())
//        .and()
//        .sessionFixation()
//        .none()
//        .and()
//        .logout()
//        .logoutSuccessHandler(myLogoutSuccessHandler)
//        .invalidateHttpSession(true)
//        .logoutSuccessUrl("/logout.html?logSucc=true")
//        .deleteCookies("JSESSIONID")
//        .permitAll()
//        .and()
//        .rememberMe()
//        .rememberMeServices(rememberMeServices())
//        .key("theKey")
//    return http.build()
//}
