package br.dev.jstec.tyginvestiment.clients.brapi;

import br.dev.jstec.tyginvestiment.clients.brapi.dto.BrapiApiDataListDto;
import br.dev.jstec.tyginvestiment.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "brapiClient", url = "${brapi-api.base-url}", configuration = FeignConfig.class)
public interface BrapiClient {

    @GetMapping("${brapi-api.service.get-assets-list}")
    BrapiApiDataListDto getAssetList(@RequestParam("token") String token);

    @GetMapping("${brapi-api.service.get-assets-info}/{tickers}")
    BrapiApiDataListDto getAssetInfo(@PathVariable("tickers") List<String> tickers,
                                     @RequestParam("token") String token,
                                     @RequestParam(value = "range", required = false) BrapiRangeOptions range,
                                     @RequestParam(value = "interval", required = false) BrapiIntervalOptions interval,
                                     @RequestParam(value = "fundamental", defaultValue = "false") boolean fundamental,
                                     @RequestParam(value = "dividends", defaultValue = "false") boolean dividends,
                                     @RequestParam(value = "modules", required = false) List<BrapiModuleOptions> modules);


}
