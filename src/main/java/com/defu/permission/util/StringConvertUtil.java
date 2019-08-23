package com.defu.permission.util;

import org.springframework.web.bind.annotation.RequestMapping;

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

        if(method.getAnnotation(RequestMapping.class) != null){
            convertUrl.append(method.getAnnotation(RequestMapping.class).method()[0].name());
        }

        if(method.getDeclaringClass().getAnnotation(RequestMapping.class)!=null){
            convertUrl.append(toConvert(method.getDeclaringClass().getAnnotation(RequestMapping.class).value()[0]));
        }

        if(method.getAnnotation(RequestMapping.class)!=null){
            convertUrl.append(toConvert(method.getAnnotation(RequestMapping.class).value()[0]));
        }
        return convertUrl.toString();
    }

}
