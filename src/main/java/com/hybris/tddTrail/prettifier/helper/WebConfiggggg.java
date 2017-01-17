package com.hybris.tddTrail.prettifier.helper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfiggggg {
	private final Logger LOG = LoggerFactory.getLogger(WebConfiggggg.class);
    
	
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
}