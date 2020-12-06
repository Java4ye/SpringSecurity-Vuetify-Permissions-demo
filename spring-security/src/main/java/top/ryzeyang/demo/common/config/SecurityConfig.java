package top.ryzeyang.demo.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import top.ryzeyang.demo.common.filter.JwtAuthenticationTokenFilter;
import top.ryzeyang.demo.utils.JwtTokenUtil;

/**
 * @author Ken
 * @date 2020/11/5 上午 09:40
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    final AuthenticationFailureHandler authenticationFailureHandler;
    final AuthenticationSuccessHandler authenticationSuccessHandler;
    final AuthenticationEntryPoint authenticationEntryPoint;
    final AccessDeniedHandler accessDeniedHandler;
    final LogoutSuccessHandler logoutSuccessHandler;

    public SecurityConfig(AuthenticationFailureHandler authenticationFailureHandler, AuthenticationSuccessHandler authenticationSuccessHandler, AuthenticationEntryPoint authenticationEntryPoint, AccessDeniedHandler accessDeniedHandler, LogoutSuccessHandler logoutSuccessHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
        this.logoutSuccessHandler = logoutSuccessHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean("users")
    public UserDetailsService users() {
        UserDetails user = User.builder()
                .username("user")
                .password("{bcrypt}$2a$10$1.NSMxlOyMgJHxOi8CWwxuU83G0/HItXxRoBO4QWZMTDp0tzPbCf.")
                .roles("USER")
//                roles 和 authorities 不能并存
//                .authorities(new String[]{"system:user:query", "system:user:edit", "system:user:delete"})
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password("{bcrypt}$2a$10$1.NSMxlOyMgJHxOi8CWwxuU83G0/HItXxRoBO4QWZMTDp0tzPbCf.")
//                .roles("USER", "ADMIN")
                .roles("ADMIN")
//                .authorities("system:user:create")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl result = new RoleHierarchyImpl();
        result.setHierarchy("ROLE_ADMIN > ROLE_USER");
        return result;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(users());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**", "/css/**", "/images/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 自定义异常处理
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler)

                // 权限
                .and()
                .authorizeRequests()
                // 一个是必须待身份信息但是不校验权限。
                .antMatchers("/", "/mock/login").permitAll()
                //只允许匿名访问
                .antMatchers("/hello").anonymous()
                .anyRequest()
                .authenticated()

                // 表单登录
//                .and()
//                .formLogin()
//                .successHandler(authenticationSuccessHandler)
//                .failureHandler(authenticationFailureHandler)
//                .loginProcessingUrl("/login")
//                .permitAll()

                // 注销
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .permitAll()

                // 关闭csrf  会在页面中生成一个csrf_token
                .and()
                .csrf()
                .disable()

                // 基于token，所以不需要session
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

        .and()
        .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
        ;
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter(){
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }
}
