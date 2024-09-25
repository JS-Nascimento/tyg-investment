package br.dev.jstec.tyginvestiment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.RequestInterceptor;
import feign.codec.Encoder;
import feign.codec.Decoder;
import feign.optionals.OptionalDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
public class FeignConfig {

    @Bean
    public Encoder feignEncoder() {
        return new SpringEncoder(jacksonHttpMessageConverterFactory());
    }

    @Bean
    public Decoder feignDecoder() {
        return new OptionalDecoder(new SpringDecoder(jacksonHttpMessageConverterFactory()));
    }

    private ObjectFactory<HttpMessageConverters> jacksonHttpMessageConverterFactory() {
        ObjectMapper objectMapper = new ObjectMapper();
        HttpMessageConverter<Object> jacksonConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        return () -> new HttpMessageConverters(jacksonConverter);
    }
}