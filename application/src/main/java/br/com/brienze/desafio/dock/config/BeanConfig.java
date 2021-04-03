package br.com.brienze.desafio.dock.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@Configuration
public class BeanConfig {
	
	private static final String OBJECT_MAPPER_FORMAT_DATE = "yyyy-MM-dd'T'HH:mm:ss";
	private static final String OBJECT_MAPPER_TIME_ZONE = "America/Sao_Paulo";
	
	@Bean
	@Profile("test")
	public ObjectMapper objectMapper() {
		final DateFormat df = new SimpleDateFormat(OBJECT_MAPPER_FORMAT_DATE);
		return new ObjectMapper()
				.registerModule(new JavaTimeModule())
				.setDateFormat(df)
				.setTimeZone(TimeZone.getTimeZone(OBJECT_MAPPER_TIME_ZONE));
	}
	
	@Bean
	@Profile("test")
	public RestTemplate restTemplate() {
		 return new RestTemplate(new HttpComponentsClientHttpRequestFactory());
	}
	
}
