package io.hskim.learnapi.config;

import dev.akkinoc.util.YamlResourceBundle;
import java.util.Locale;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

  /**
   * 기본 지역값 설정, KOREAN = 'ko'
   * @return
   */
  @Bean
  public AcceptHeaderLocaleResolver localeResolver() {
    AcceptHeaderLocaleResolver acceptHeaderLocaleResolver = new AcceptHeaderLocaleResolver();

    acceptHeaderLocaleResolver.setDefaultLocale(Locale.KOREAN);

    return acceptHeaderLocaleResolver;
  }

  /**
   * 요청 파라미터에 lang 정보로 locale 변경
   * @return
   */
  @Bean
  public LocaleChangeInterceptor localeChangeInterceptor() {
    LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();

    localeChangeInterceptor.setParamName("lang");

    return localeChangeInterceptor;
  }

  /**
   * yaml 파일 파싱용 MessageSource
   * @param basename
   * @param encoding
   * @return
   */
  @Bean
  public MessageSource messageSource(
    @Value("${spring.messages.basename:i18n/}") String basename,
    @Value("${spring.messages.encoding:ko}") String encoding
  ) {
    YamlMessageSource ms = new YamlMessageSource();

    ms.setBasenames(basename + "/exception");
    ms.setDefaultEncoding(encoding);
    ms.setAlwaysUseMessageFormat(true);
    ms.setUseCodeAsDefaultMessage(true);
    ms.setFallbackToSystemLocale(true);

    return ms;
  }

  /**
   * yaml 파싱용 클래스
   */
  private static class YamlMessageSource extends ResourceBundleMessageSource {

    @Override
    protected ResourceBundle doGetBundle(String basename, Locale locale) {
      return ResourceBundle.getBundle(
        basename,
        locale,
        YamlResourceBundle.Control.INSTANCE
      );
    }
  }
}
