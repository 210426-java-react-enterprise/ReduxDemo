package com.revature.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.revature")
@Import({AOPConfig.class,SwaggerConfig.class})
public class AppConfig {
}
