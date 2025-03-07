package info.skytag.apiserver.icnclient.service;

import info.skytag.apiserver.icnclient.dto.response.flight_res_detail.FlightItem;

import java.time.LocalDate;
import java.util.List;

public interface IcnFlightService {
    List<FlightItem> getFlightListByDate(int pageNo, int numOfRows, LocalDate searchDate);
    Integer getTotalNum(LocalDate searchDate);

}
