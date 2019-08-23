package com.defu.permission.init;

import com.defu.permission.annotation.Permission;
import com.defu.permission.util.StringConvertUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 判断bean是否带RestController注解；如带此注解，则获取其所有方法，并取得每个方法上的Permission注解中的value值作为权限标识，
 * 另外，取得每个方法上的ApiOperation注解中的value作为权限描述。并将所有的方法缓存起来用于初始化。
 **/

@Slf4j
@Lazy
@Component
public class PermissionAnnotationScannerConfigurer implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (null != AnnotationUtils.findAnnotation(bean.getClass(), RestController.class)) {
            addPermissionDefinition(bean.getClass());
        }
        return bean;
    }

    public void addPermissionDefinition(Class<?> beanType) {
        Assert.notNull(beanType, "beanType can't be null");
        if (null == AnnotationUtils.findAnnotation(beanType, RestController.class)) {
            return;
        }
        /**
         * 遍历所有方法
         */
        Method[] methods = beanType.getMethods();
        for (Method method : methods) {
            Permission permissionAnno = AnnotationUtils.findAnnotation(method, Permission.class);
            if(permissionAnno == null){
                continue;
            }

            String completePermissionMark = StringConvertUtil.convertMethod(method);
            ApiOperation apiOperationAnno = AnnotationUtils.findAnnotation(method, ApiOperation.class); // 默认描述与权限标识相同，如有apiOperation注解则使用其value
            String permissionDescription = "";
            if (null != apiOperationAnno) {
                permissionDescription = apiOperationAnno.value();
            }
            PermissionsCache.put(permissionDescription, completePermissionMark);
        }
    }
}
