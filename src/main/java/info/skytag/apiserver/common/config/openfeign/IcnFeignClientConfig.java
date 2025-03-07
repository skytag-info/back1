package info.skytag.apiserver.common.config.openfeign;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IcnFeignClientConfig {
    @Value("${icn.api.service.key}")
    private String serviceKey;

    @Bean
    public RequestInterceptor requestInterceptor() {
        return template -> {
            // serviceKey 추가
            template.query("serviceKey", serviceKey);

            // type=json 추가
            template.query("type", "json");
        };
    }

}
