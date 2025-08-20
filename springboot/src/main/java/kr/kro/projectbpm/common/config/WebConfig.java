package kr.kro.projectbpm.common.config;

import kr.kro.projectbpm.common.intercepter.GlobalInterceptor;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * WebConfig는 Spring MVC의 설정을 담당하는 클래스입니다.
 * 이 클래스는 인터셉터를 등록하고, HTTP 요청을 HTTPS로 리다이렉트하는 Tomcat 설정을 포함합니다.
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final GlobalInterceptor globalInterceptor;

    /**
     * addInterceptors 메서드는 글로벌 인터셉터를 등록합니다.
     * 이 인터셉터는 모든 요청에 대해 공통적으로 처리할 로직을 정의합니다.
     *
     * @param registry InterceptorRegistry 객체로, 인터셉터를 등록하는 데 사용됩니다.
     * @see GlobalInterceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(globalInterceptor);
    }

    /**
     * redirectHttpToHttps 메서드는 HTTP 요청을 HTTPS로 리다이렉트하는 Tomcat 설정을 정의합니다.
     * 이 설정은 보안을 강화하고, 모든 트래픽을 암호화된 연결로 강제합니다. (내장 톰캣에서만)
     *
     * @return TomcatServletWebServerFactory 객체로, Tomcat 서버의 설정을 포함합니다.
     */
    @Bean
    public TomcatServletWebServerFactory redirectHttpToHttps() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };

        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(443);
        tomcat.addAdditionalTomcatConnectors(connector);

        return tomcat;
    }
}
