package com.defu.permission.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * description:
 * create by: guardian_liu
 * date: 2019/5/13
 * time: 18:24
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String name;

    private String secret;

    private long expire;  //单位秒

    private String keyPrefix;

}
