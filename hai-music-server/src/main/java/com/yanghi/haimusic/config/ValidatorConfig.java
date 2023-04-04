package com.yanghi.haimusic.config;

import com.yanghi.haimusic.validators.validators.AllowedValuesValidator;
import com.yanghi.haimusic.validators.validators.OnlyOneFieldValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
public class ValidatorConfig {

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        MethodValidationPostProcessor processor = new MethodValidationPostProcessor();
        processor.setValidator(validator());
        return processor;
    }


    // 注册自定义校验器

    @Bean
    public AllowedValuesValidator allowedValuesValidator(){
        return new AllowedValuesValidator();
    }

    @Bean
    public OnlyOneFieldValidator onlyOneFieldValidator() {
        return new OnlyOneFieldValidator();
    }

}
