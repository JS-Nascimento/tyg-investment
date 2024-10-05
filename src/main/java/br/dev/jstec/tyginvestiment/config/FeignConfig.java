package br.dev.jstec.tyginvestiment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.optionals.OptionalDecoder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.Random;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class FeignConfig {

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(jacksonHttpMessageConverterFactory());
    }

    private final Random random = new Random();

    @Bean
    public RequestInterceptor dynamicRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                log.info("Feign request to: {}", requestTemplate.url());
                log.info("Feign request headers: {}", requestTemplate.headers());
            }
        };
    }

    @Bean
    public Decoder feignDecoder() {
        return new OptionalDecoder(new SpringDecoder(jacksonHttpMessageConverterFactory()));
    }


    private ObjectFactory<HttpMessageConverters> jacksonHttpMessageConverterFactory() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        HttpMessageConverter<Object> jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        return () -> new HttpMessageConverters(jacksonConverter);
    }
}