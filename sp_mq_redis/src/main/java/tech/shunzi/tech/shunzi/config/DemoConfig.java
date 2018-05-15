package tech.shunzi.tech.shunzi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.core.env.Environment;

@SpringBootConfiguration
public class DemoConfig {

    @Autowired
    private Environment environment;

}
