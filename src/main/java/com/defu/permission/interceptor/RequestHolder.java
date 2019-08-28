package com.defu.permission.interceptor;

import com.defu.permission.repo.EmpDO;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/6/5
 * time: 14:14
 */
public class RequestHolder {

    private final static ThreadLocal<EmpDO> userLocal = new InheritableThreadLocal<>();

    public static void add(EmpDO empDO) {
        userLocal.set(empDO);
    }

    public static EmpDO getUser() {
        return userLocal.get();
    }

    public static void remove() {
        userLocal.remove();
    }
}
