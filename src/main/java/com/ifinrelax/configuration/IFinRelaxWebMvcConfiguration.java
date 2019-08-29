package com.ifinrelax.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * @author Timur Berezhnoi
 */
@Configuration
@EnableWebMvc
@Profile({"prod"})
class IFinRelaxWebMvcConfiguration extends WebMvcConfigurerAdapter {

    private static final String VIEW_PREFIX = "/";
    private static final String VIEW_SUFFIX = ".html";
    private static final boolean EXPOSE_CONTEXT_BEANS_AS_ATTRIBUTES = true;

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix(VIEW_PREFIX);
        resolver.setSuffix(VIEW_SUFFIX);
        resolver.setExposeContextBeansAsAttributes(EXPOSE_CONTEXT_BEANS_AS_ATTRIBUTES);
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**/").addResourceLocations("/dist/");
    }

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
}