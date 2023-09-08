package com.atguigu.system.filter;

import com.atguigu.common.result.Result;
import com.atguigu.common.result.ResultCodeEnum;
import com.atguigu.common.util.ResponseUtil;
import com.atguigu.model.system.SysUser;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 认证解析token过滤器
 * </p>
 */
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private RedisTemplate redisTemplate;

    public TokenAuthenticationFilter(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("uri:"+request.getRequestURI());
        //如果是登录接口，直接放行
        if("/admin/system/index/login".equals(request.getRequestURI()) || "/api/oss/upload/".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }

        //获取认证信息
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        //如果获取到认证信息，那么我们就通过工具类将认证信息保存在上下文对象中。这样后面的组件就直接从上下文对象中可以获取认证对象
        if(null != authentication) {
            SecurityContextHolder.getContext().setAuthentication(authentication);
            //放行请求
            chain.doFilter(request, response);
        } else {
            //如果没有获取到认证信息，说明非法，则抛出异常：你无权访问
            ResponseUtil.out(response, Result.build(null, ResultCodeEnum.PERMISSION));
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        // 从请求头中获取token
        String token = request.getHeader("token");

        if (!StringUtils.isEmpty(token)) {
            //从redis中读取对应的sysUser信息
            SysUser sysUser = (SysUser)redisTemplate.boundValueOps(token).get();
            if (null != sysUser) {
                //获取sysUser的权限信息（目前我们在登录验证的时候还没有去查询权限信息，所以此处一定是走else）
                if (null != sysUser.getUserPermsList() && sysUser.getUserPermsList().size() > 0) {
                    List<SimpleGrantedAuthority> authorities = sysUser.getUserPermsList().stream().filter(code -> !StringUtils.isEmpty(code.trim())).map(code -> new SimpleGrantedAuthority(code.trim())).collect(Collectors.toList());
                    //返回一个认证之后包含权限的对象
                    return new UsernamePasswordAuthenticationToken(sysUser.getUsername(), null, authorities);
                } else {
                    //返回一个认证之后没有权限的对象
                    return new UsernamePasswordAuthenticationToken(sysUser.getUsername(), null,Collections.emptyList());
                }
            }
        }
        return null;
    }
}
