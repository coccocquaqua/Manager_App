package com.example.manager_app.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.Locale;

@Configuration
public class MultilanguageConfig implements WebMvcConfigurer {
    @Bean(name = "localeResolver") //localeResolver dùng để xác định ngôn ngữ hiện tại của ứng dụng
    public LocaleResolver getLocaleResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver(); //CookieLocaleResolver lưu thông tin ngôn ngữ vào cookie
        resolver.setCookieDomain("myAppLocaleCookie"); //setCookieDomain() thiết lập tên của cookie
        resolver.setDefaultLocale(Locale.ENGLISH); //setDefaultLocale() thiết lập ngôn ngữ mặc định
        // 60 minutes
        resolver.setCookieMaxAge(60 * 60); //setCookieMaxAge() thiết lập thời gian tồn tại của cookie
        return resolver;
    }

    @Bean(name = "messageSource") //messageSource dùng để lấy thông tin từ file properties
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource(); //ReloadableResourceBundleMessageSource lớp cung cấp các phương thức để lấy thông tin từ file properties
        messageResource.setBasename("classpath:i18n/messages"); //setBasename() thiết lập đường dẫn file properties
        messageResource.setDefaultEncoding("UTF-8"); //setDefaultEncoding() thiết lập bảng mã mặc định
        return messageResource;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) { //addInterceptors() thêm interceptor để thay đổi ngôn ngữ
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor(); //LocaleChangeInterceptor dùng để thay đổi ngôn ngữ
        localeInterceptor.setParamName("language"); //setParamName() thiết lập tên tham số để thay đổi ngôn ngữ
        registry.addInterceptor(localeInterceptor).addPathPatterns("/*");  //addPathPatterns() thiết lập đường dẫn để thay đổi ngôn ngữ
    }
}
