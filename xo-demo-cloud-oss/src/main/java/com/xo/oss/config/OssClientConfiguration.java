package com.xo.oss.config;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.xo.oss.util.OssUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: xo
 * @DATE: 2020/10/15
 * @Description: OssClientConfiguration
 **/
@Configuration
@ConditionalOnProperty("bbt.oss.endpoint")
@ConditionalOnClass(OSSClient.class)
@EnableConfigurationProperties(OssProperties.class)
public class OssClientConfiguration {

    private final OssProperties ossProperties;

    public OssClientConfiguration(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    @Bean
    @ConditionalOnMissingBean
    public OSSClient ossClient() {
        // 创建ClientConfiguration实例，按照您的需要修改默认参数。
        ClientConfiguration conf = new ClientConfiguration();
        // 开启支持CNAME。CNAME是指将自定义域名绑定到存储空间上。
        conf.setSupportCname(true);
        return new OSSClient(ossProperties.getEndpoint(), ossProperties.getAccessKeyId(), ossProperties.getAccessKeySecret(), conf);
    }

    @Bean
    @ConditionalOnMissingBean
    public OssUtil ossUtil(OSSClient ossClient) {
        return new OssUtil(ossClient, ossProperties.getBucketName());
    }

}
