package com.study.BlogPlatform.configuration;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурация для перенаправления HTTP на HTTPS в приложении.
 * <p>
 * Этот класс настраивает встроенный сервер Tomcat для обработки входящих HTTP-запросов
 * и перенаправления их на HTTPS. Он определяет порт для HTTP и порт для перенаправления.
 * </p>
 */
@Configuration
public class HttpsRedirectConfig {

    /**
     * Создает и настраивает экземпляр {@link ServletWebServerFactory} для Tomcat.
     * <p>
     * Этот метод настраивает Tomcat так, чтобы он принимал запросы по HTTP на порту 8081
     * и перенаправлял их на HTTPS на порт 8443.
     * </p>
     *
     * @return настроенный экземпляр {@link ServletWebServerFactory}
     *
     * @author Azerty
     */
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(connector -> {
            connector.setScheme("http");
            connector.setSecure(false);
            connector.setPort(8081); // порт HTTP
            connector.setRedirectPort(8443); // перенаправление на HTTPS
        });
        return factory;
    }
}
