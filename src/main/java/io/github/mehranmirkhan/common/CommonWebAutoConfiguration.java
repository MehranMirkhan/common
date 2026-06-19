package io.github.mehranmirkhan.common;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.DispatcherServlet;
import io.github.mehranmirkhan.common.error.CommonExceptionHandler;
import io.github.mehranmirkhan.common.error.CommonJpaExceptionHandler;

@AutoConfiguration
@ConditionalOnClass(DispatcherServlet.class)
public class CommonWebAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public CommonExceptionHandler commonExceptionHandler() {
    return new CommonExceptionHandler();
  }

  @Configuration(proxyBeanMethods = false)
  @ConditionalOnClass(ConstraintViolationException.class)
  static class JpaExceptionHandlerConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public CommonJpaExceptionHandler commonJpaExceptionHandler() {
      return new CommonJpaExceptionHandler();
    }
  }
}
