package com.defu.permission.init;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/8/23
 * time: 10:39
 */
@Component
public class PermissionsCache {

    static final Map<String, String> permissionDefinitions = new ConcurrentHashMap<>();

    public static Map<String, String> getPermissionDefinitions() {
        return permissionDefinitions;
    }

    public static void put(String key,String value){
        permissionDefinitions.put(key,value);
    }

}
