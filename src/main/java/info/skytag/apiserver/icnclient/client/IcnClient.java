package info.skytag.apiserver.icnclient.client;

import info.skytag.apiserver.common.config.openfeign.IcnFeignClientConfig;
import info.skytag.apiserver.flight.model.dto.request.FlightReqDto;
import info.skytag.apiserver.icnclient.dto.response.FlightResDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "icn", url = "${icn.client.url}", configuration = IcnFeignClientConfig.class)
public interface IcnClient {

    @GetMapping(value = "/getFltArrivalsDeOdp")
    FlightResDto getFlightArrivals(@SpringQueryMap FlightReqDto flightReqDto);

    @GetMapping(value = "/getFltDeparturesDeOdp")
    FlightResDto getFlightDepartures(@SpringQueryMap FlightReqDto flightReqDto);
}
