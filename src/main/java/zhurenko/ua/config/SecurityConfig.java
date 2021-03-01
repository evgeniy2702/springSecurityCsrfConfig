package zhurenko.ua.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import zhurenko.ua.security.OwnerDetailsService;
import zhurenko.ua.security.RefererRedirectionAuthenticationSuccessHandler;

@Configuration
@Import({WebSecurityBeanConfig.class})
@EnableWebSecurity(debug = true)
@ComponentScan("zhurenko.ua")
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//    private AuthenticationSuccessHandler authenticationSuccessHandler;

    private PasswordEncoder passwordEncoder;

    private AuthenticationFailureHandler authenticationFailureHandler;

    private AccessDeniedHandler accessDeniedHandler;

    private LogoutSuccessHandler logoutSuccessHandler;

    private AuthenticationEntryPoint authenticationEntryPoint;

    private OwnerDetailsService ownerDetailsService;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setOwnerDetailsService(OwnerDetailsService ownerDetailsService) {
        this.ownerDetailsService = ownerDetailsService;
    }

//    @Autowired
//    public void setAuthenticationSuccessHandler(AuthenticationSuccessHandler authenticationSuccessHandler) {
//        this.authenticationSuccessHandler = authenticationSuccessHandler;
//    }

    @Autowired
    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Autowired
    public void setAccessDeniedHandler(AccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Autowired
    public void setLogoutSuccessHandler(LogoutSuccessHandler logoutSuccessHandler) {
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Autowired
    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/book/new").hasRole("USER")
                .antMatchers("/admin").hasRole("ADMIN")
                .antMatchers("/registration").anonymous()
                .antMatchers("/login","/").permitAll()
                .anyRequest().authenticated()
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)
            .and()
            .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login/process")
                .defaultSuccessUrl("/showBook")
                .failureUrl("/login")
                //.usernameParameter("email")
                .failureForwardUrl("/login?error = true")
                .successHandler(new RefererRedirectionAuthenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler)
            .and()
            .logout(logout -> logout
                .logoutUrl("/leave/authentication")
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/")
                //.logoutSuccessHandler(logoutSuccessHandler)
            );


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(ownerDetailsService).passwordEncoder(passwordEncoder);
    }


}
