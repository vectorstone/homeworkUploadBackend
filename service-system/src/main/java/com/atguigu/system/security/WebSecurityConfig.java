package com.atguigu.system.security;
import com.atguigu.system.filter.TokenAuthenticationFilter;
import com.atguigu.system.filter.TokenLoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
/**
 * @Description:
 * @Author: Gavin
 * @Date: 6/14/2023 8:24 PM
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //这是配置的关键,决定哪些接口开启防护,哪些接口绕过防护
        http
                //关闭csrf
                .csrf().disable()
                //开启跨域以便前端调用接口
                .cors().and()
                .authorizeRequests()
                //指定某些接口不需要通过验证即可访问,登录接口不需要认证
                .antMatchers("/admin/system/index/login").permitAll()
                //这里的意思是其他的所有的接口需要认证才能访问
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new TokenAuthenticationFilter(redisTemplate), TokenLoginFilter.class)
                .addFilter(new TokenLoginFilter(authenticationManager(),redisTemplate));
        //禁用session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }
    /**
     * 配置哪些请求不拦截
     * 排除swagger相关请求
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/favicon.ico","/swagger-resources/**",
                "/webjars/**", "/v2/**", "/doc.html","/api/oss/upload","/admin/system/sysUser","/admin/system/image/**");
    }
}
