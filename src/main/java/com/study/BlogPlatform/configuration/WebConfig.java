package com.study.BlogPlatform.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.Locale;

/**
 * Конфигурация веб-приложения для настройки интернационализации и представлений.
 * <p>
 * Этот класс настраивает обработку локализации в приложении, используя
 * CookieLocaleResolver для хранения выбранного языка пользователя и
 * LocaleChangeInterceptor для изменения языка через параметр запроса.
 * Также настраивается Thymeleaf как шаблонизатор для обработки представлений.
 * </p>
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Создает бин для загрузки сообщений из файлов ресурсов.
     *
     * @return MessageSource объект, который загружает сообщения из файлов
     *         ресурсов, расположенных в classpath:messages.
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    /**
     * Создает бин для разрешения локали с использованием cookie.
     *
     * @return LocaleResolver объект, который сохраняет выбранный язык в cookie
     *         и устанавливает английский язык как язык по умолчанию.
     */
    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }

    /**
     * Создает интерсептор для изменения локали.
     *
     * @return LocaleChangeInterceptor объект, который позволяет изменять
     *         локаль через параметр запроса "lang".
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang"); // Параметр запроса для изменения локали (например, ?lang=fr)
        return lci;
    }

    /**
     * Создает бин для разрешения представлений Thymeleaf.
     *
     * @param templateEngine объект SpringTemplateEngine, используемый для
     *                      обработки шаблонов Thymeleaf.
     * @return ViewResolver объект, который разрешает представления Thymeleaf
     *         с кодировкой UTF-8.
     */
    @Bean
    public ViewResolver thymeleafViewResolver(SpringTemplateEngine templateEngine) {
        ThymeleafViewResolver resolver = new ThymeleafViewResolver();
        resolver.setTemplateEngine(templateEngine);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }

    /**
     * Добавляет интерсепторы в реестр обработки запросов.
     *
     * @param registry реестр интерсепторов, в который добавляются
     *                 интерсепторы для обработки запросов.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
