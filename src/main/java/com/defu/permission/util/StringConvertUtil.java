package com.defu.permission.util;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/8/23
 * time: 11:08
 */
public class StringConvertUtil {


    public static String toConvert(String url){
        if(url.startsWith("/")){
            return url;
        }
        return "/"+url;
    }


    /**
     * 将每一个符合要求的method转换成权限的固定格式字符串
     * @param method
     * @return
     */
    public static String convertMethod(Method method){
        StringBuilder convertUrl = new StringBuilder();
        RequestMapping requestMapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        if(requestMapping != null){
            convertUrl.append(requestMapping.method()[0].name());
        }

        if(AnnotationUtils.findAnnotation(method.getDeclaringClass(), RequestMapping.class)!=null){
            convertUrl.append(toConvert(AnnotationUtils.findAnnotation(method.getDeclaringClass(), RequestMapping.class).value()[0]));
        }

        if(requestMapping != null){
            switch (requestMapping.method()[0]){
                case GET:
                    convertUrl.append(toConvert(AnnotationUtils.findAnnotation(method, GetMapping.class).value()[0]));
                    break;
                case PUT:
                    convertUrl.append(toConvert(AnnotationUtils.findAnnotation(method, PutMapping.class).value()[0]));
                    break;
                case POST:
                    convertUrl.append(toConvert(AnnotationUtils.findAnnotation(method, PostMapping.class).value()[0]));
                    break;
                case DELETE:
                    convertUrl.append(toConvert(AnnotationUtils.findAnnotation(method, DeleteMapping.class).value()[0]));
                    break;
                default:
                    if(requestMapping.value().length>0){
                        convertUrl.append(toConvert(requestMapping.value()[0]));
                    }
            }
        }
        return convertUrl.toString();
    }

}
