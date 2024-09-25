package br.dev.jstec.tyginvestiment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TygInvestimentApplication {

    public static void main(String[] args) {
        SpringApplication.run(TygInvestimentApplication.class, args);
    }

}
