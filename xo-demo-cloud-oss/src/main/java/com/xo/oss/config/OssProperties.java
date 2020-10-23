package com.xo.oss.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author: xo
 * @DATE: 2020/10/15
 * @Description: OssProperties
 **/
@Data
@ConfigurationProperties(prefix = "bbt.oss")
public class OssProperties {

    /**
     * endpoint
     */
    private String endpoint;

    /**
     * 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维
     * 请登录 https://ram.console.aliyun.com 创建RAM账号。
     */
    private String accessKeyId;

    /**
     * 应用appKey
     */
    private String accessKeySecret;

    /**
     * bucketName
     */
    private String bucketName;


}
