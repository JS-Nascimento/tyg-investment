package br.dev.jstec.tyginvestiment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
@EnableAsync
@EnableConfigurationProperties
public class TygInvestimentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TygInvestimentApplication.class, args);
    }

}
