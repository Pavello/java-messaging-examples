package io.github.pavello.messaging.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
public class ConversionConfig {
	    @Bean
	    public MappingJackson2MessageConverter mappingJackson2MessageConverter() {
	        var converter = new MappingJackson2MessageConverter();
	        converter.setTargetType(MessageType.TEXT);
	        converter.setTypeIdPropertyName("_type");
	        return converter;
	    }
}
