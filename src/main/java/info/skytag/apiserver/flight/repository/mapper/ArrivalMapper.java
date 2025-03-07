package info.skytag.apiserver.flight.repository.mapper;

import info.skytag.apiserver.flight.model.dto.FlightDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArrivalMapper {
    void bulkInsertArrival(@Param("list") List<FlightDto> flightDtos);
    void bulkInsertDeparture(@Param("list") List<FlightDto> flightDtos);

}