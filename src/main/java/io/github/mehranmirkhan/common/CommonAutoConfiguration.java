package io.github.mehranmirkhan.common;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfiguration
public class CommonAutoConfiguration {
  public CommonAutoConfiguration() {
    log.info("Common Auto Configuration is loaded.");
  }

  public static void main(String[] args) {}
}
