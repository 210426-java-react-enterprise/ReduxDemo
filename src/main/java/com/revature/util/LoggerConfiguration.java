package com.revature.util;


import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;

public class LoggerConfiguration {

    private LoggerConfiguration(){
        super();
    }

    public static void configureLog4J(){
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();
        LayoutComponentBuilder standard
                = builder.newLayout("PatternLayout");
        standard.addAttribute("pattern", "%d [%t] %-5level: %msg%n%throwable");

        AppenderComponentBuilder rollingFile = builder.newAppender("rolling","RollingFile");
        rollingFile.addAttribute("fileName","info.log");
        rollingFile.addAttribute("filePattern","rolling-%d{MM-dd-yy}.log.gz");
        rollingFile.add(standard);
        builder.add(rollingFile);

        AppenderComponentBuilder rollingFile2 = builder.newAppender("rolling","RollingFile");
        rollingFile2.addAttribute("fileName","error.log");
        rollingFile2.addAttribute("filePattern","rolling-%d{MM-dd-yy}.log.gz");
        rollingFile2.add(standard);
        builder.add(rollingFile2);

        Configurator.initialize(builder.build());
    }
}
