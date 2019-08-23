package com.defu.permission.advisor;

import com.defu.permission.repo.EmpAutoRepo;
import com.defu.permission.repo.EmpDO;
import com.defu.permission.util.JwtProperties;
import com.defu.permission.util.JwtUtil;
import com.defu.permission.util.StringConvertUtil;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/8/22
 * time: 11:36
 */
@Component
public class PermissionAdvice implements MethodInterceptor {

    @Autowired
    private EmpAutoRepo empAutoRepo;

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private JwtUtil jwtUtil;

    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        hasPermission(methodInvocation.getMethod());
        return methodInvocation.proceed();
    }

    private boolean hasPermission(Method method) throws UnauthorisedException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //获取请求头中的token，重要 要不然怎么知道谁访问了接口，怎么判断权限
        String token = request.getHeader(jwtProperties.getName());

        EmpDO empDO = empAutoRepo.getOne(Integer.valueOf(jwtUtil.decodeToken(token)));

        //通过相同的方式校验权限
        StringConvertUtil.convertMethod(method);

        //自己通过存入数据库的表结构比较是否满足该用户权限
        //todo

        if(false){
            HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            throw new UnauthorisedException("无权限访问");
        }
        return true;
    }
}
