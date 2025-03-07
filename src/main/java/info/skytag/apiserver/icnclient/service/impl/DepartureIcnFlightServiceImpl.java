package info.skytag.apiserver.icnclient.service.impl;

import info.skytag.apiserver.flight.model.dto.request.FlightReqDto;
import info.skytag.apiserver.icnclient.client.IcnClient;
import info.skytag.apiserver.icnclient.dto.response.FlightResDto;
import info.skytag.apiserver.icnclient.dto.response.flight_res_detail.FlightItem;
import info.skytag.apiserver.icnclient.service.IcnFlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartureIcnFlightServiceImpl implements IcnFlightService {

    private final IcnClient icnClient;

    @Override
    public List<FlightItem> getFlightListByDate(int pageNo, int numOfRows, LocalDate searchDate) {
        FlightReqDto req = new FlightReqDto(pageNo, numOfRows, searchDate);
        FlightResDto res = icnClient.getFlightDepartures(req);

        return res.response().body().items();
    }

    @Override
    public Integer getTotalNum(LocalDate searchDate) {
        FlightReqDto req = new FlightReqDto(1, 1, searchDate);
        FlightResDto res = icnClient.getFlightDepartures(req);

        return res.response().body().totalCount();
    }
}
