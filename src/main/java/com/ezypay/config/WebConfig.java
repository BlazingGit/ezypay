package com.ezypay.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ezypay.controller", "com.ezypay.service"})
public class WebConfig {

}
