package com.defu.permission.advisor;

import com.defu.permission.annotation.Permission;
import org.aopalliance.aop.Advice;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/8/22
 * time: 11:33
 */
@Component
public class PermissionAdvisor extends AbstractPointcutAdvisor {

    private Pointcut pointcut;

    @Autowired
    private PermissionAdvice advice;

    public PermissionAdvisor() {
        this.pointcut = new AnnotationMatchingPointcut(Controller.class, Permission.class,true);
    }

    public Pointcut getPointcut() {
        return pointcut;
    }

    public Advice getAdvice() {
        return advice;
    }
}
