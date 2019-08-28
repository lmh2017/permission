package com.defu.permission.interceptor;

import com.alibaba.fastjson.JSON;
import com.defu.permission.repo.EmpAutoRepo;
import com.defu.permission.util.JwtProperties;
import com.defu.permission.util.JwtUtil;
import com.defu.permission.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private EmpAutoRepo empDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUrl = request.getRequestURI();
        log.info("----------------接收来自客户端的请求：{}",requestUrl);
        String token = request.getHeader(jwtProperties.getName());
        if(null == token || !jwtUtil.verifyToken(token)){  //token验证不通过
            responseNotAuth(response);
            return false;
        }else {
            String key = jwtUtil.decodeToken(token);
            String curToken = redisUtil.getToken(key);
            if(curToken!=null && token.equals(curToken)){
                RequestHolder.add(empDao.getOne(Integer.parseInt(key)));
                return true;
            }else {
                responseNotAuth(response);
                return false;
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("请求结束，清除当前用户信息");
        RequestHolder.remove();
    }

    private static void responseNotAuth(HttpServletResponse response){
        PrintWriter pw = null;
        try {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=utf-8");
            pw = response.getWriter();
            pw.write(JSON.toJSONString(ResponseUtil.error(CommonConstants.RESPONSE_CODE_ANONYMOUS,"token校验不通过，请重新登录")));
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null != pw){
                pw.flush();
                pw.close();
            }
        }
    }
}
