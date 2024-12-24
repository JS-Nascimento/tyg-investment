package br.dev.jstec.tyginvestiment.clients.brapi;

import br.dev.jstec.tyginvestiment.clients.brapi.dto.BrapiApiDataListDto;
import br.dev.jstec.tyginvestiment.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "brapiClient", url = "${brapi-api.base-url}", configuration = FeignConfig.class)
public interface BrapiClient {

    @GetMapping("${brapi-api.service.get-assets-list}")
    BrapiApiDataListDto getAssetInfo(@RequestParam("token") String token);
}
