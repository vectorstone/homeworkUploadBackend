package com.atguigu.system.config;


import com.aliyun.oss.common.utils.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Author: Gavin
 * @Date: 7/6/2023 7:32 PM
 */
@Configuration
@Data
@ConfigurationProperties(prefix = "aliyun.oss")
@Slf4j
public class OssProperties implements InitializingBean {
    private String schema;
    private String endpoint;
    private String bucketName;
    // private String accessKeyId;
    // private String accessKeySecret;
    public static String SCHEMA;
    public static String ENDPOINT;
    public static String BUCKETNAME;
    //这个其实可以不需要,因为创建了一个配置类,直接从系统的环境变量里面获取
    public static String ACCESS_KEY_ID;
    //这个同上,也可以不需要
    public static String ACCESS_KEY_SECRET;

    //bean的属性初始化完之后,将属性赋值给静态成员变量,方便其他地方里面使用
    @Override
    public void afterPropertiesSet() {
        SCHEMA = this.schema;
        ENDPOINT = this.endpoint;
        BUCKETNAME = this.bucketName;
        // ACCESS_KEY_ID = this.accessKeyId;
        // ACCESS_KEY_SECRET = this.accessKeySecret;
        ACCESS_KEY_ID = StringUtils.trim(System.getenv("OSS_ACCESS_KEY_ID"));
        ACCESS_KEY_SECRET = StringUtils.trim(System.getenv("OSS_ACCESS_KEY_SECRET"));
    }

}
