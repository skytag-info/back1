package info.skytag.apiserver.flight.repository.dao;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import info.skytag.apiserver.aircraft.model.dto.response.AircraftResponse;
import info.skytag.apiserver.airline.model.dto.response.AirlineResponse;
import info.skytag.apiserver.airport.model.dto.response.AirportResponse;
import info.skytag.apiserver.common.util.querydsl.SortUtil;
import info.skytag.apiserver.flight.model.dto.FlightResponse;
import info.skytag.apiserver.flight.model.dto.request.FlightSearchRequest;
import info.skytag.apiserver.flight.model.entity.Arrival;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

import static info.skytag.apiserver.aircraft.model.entity.QAircraftKor.aircraftKor;
import static info.skytag.apiserver.airline.model.entity.QAirline.airline;
import static info.skytag.apiserver.airport.model.entity.QAirport.airport;
import static info.skytag.apiserver.flight.model.entity.QArrival.arrival;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class ArrivalDAO {
    // 빈으로 등록된 JPAQueryFactory 주입
    private final JPAQueryFactory queryFactory;

    public Page<FlightResponse> searchArrivalList(FlightSearchRequest request) {
        Pageable pageable = request.toPageable();

        // 정렬 조건 적용 - 재사용 가능한 유틸리티 메서드 사용
        OrderSpecifier<?>[] orderSpecifiers = SortUtil.getOrderSpecifiers(
                pageable,
                Arrival.class,
                arrival,
                "scheduleDatetime",  // 기본 정렬 필드
                Order.DESC      // 기본 정렬 방향
        );

        JPAQuery<FlightResponse> query = queryFactory.from(arrival)
                .select(Projections.constructor(FlightResponse.class,
                        // AirportResponse 생성: 필요한 필드들을 전달 (예시)
                        Projections.constructor(AirportResponse.class,
                                airport.airportCode   // 예: 공항 코드
                                , airport.airportName    // 예: 공항 이름
                                , airport.city    // 예: 공항 이름
                                , airport.country    // 예: 공항 이름
                                , airport.type    // 예: 공항 이름
                        ),
                        // AirlineResponse 생성: 필요한 필드들을 전달 (예시)
                        Projections.constructor(AirlineResponse.class,
                                airline.airlineName    // 예: 항공사 이름
                                , airline.iataCode   // 예: 항공사 코드
                                , airline.icaoCode    // 예: 항공사 이름
                                , airline.country    // 예: 항공사 이름
                                , airline.contactInfo    // 예: 항공사 이름
                                , airline.websiteUrl    // 예: 항공사 이름
                        ),
                        // aircraftResponse 생성: 필요한 필드들을 전달 (예시)
                        Projections.constructor(AircraftResponse.class,
                                aircraftKor.aircraftRegNo,
                                aircraftKor.businessType,
                                aircraftKor.aircraftType,
                                aircraftKor.serialNumber,
                                aircraftKor.manufacturingDate,
                                aircraftKor.age,
                                aircraftKor.registrationDate,
                                aircraftKor.seatingCapacity,
                                aircraftKor.maximumTakeoffWeight,
                                aircraftKor.parkingArea,
                                aircraftKor.acquisitionType
                        ),
                        arrival.typeOfFlight,
                        arrival.flightId,
                        arrival.masterFlightId,
                        arrival.scheduleDatetime,
                        arrival.gateNumber,
                        arrival.terminalId,
                        arrival.exitNumber
                ))
                .join(airport).on(airport.airportId.eq(arrival.airport.airportId))
                .join(airline).on(airline.airlineId.eq(arrival.airline.airlineId))
                .join(aircraftKor).on(aircraftKor.aircraftId.eq(arrival.arrivalId))
                .where(searchArrivalListPredicate(request))
                ;

        Long total = queryFactory
                .select(arrival.count())
                .from(arrival)
                .join(airport).on(airport.airportId.eq(arrival.airport.airportId))
                .join(airline).on(airline.airlineId.eq(arrival.airline.airlineId))
                .join(aircraftKor).on(aircraftKor.aircraftId.eq(arrival.arrivalId))
                .where(searchArrivalListPredicate(request))
                .fetchOne();

        // 정렬조건 적용
        query.orderBy(orderSpecifiers);
        // 페이지네이션 적용
        query.offset(pageable.getOffset())
                .limit(pageable.getPageSize());
//        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.asc("flightId")));
        SortUtil.sort(pageable, query, Arrival.class, "arrival");

//        List<FlightResponse> content = query
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();


        return new PageImpl<>(query.fetch(), pageable, total);
    }

    private BooleanBuilder searchArrivalListPredicate(FlightSearchRequest request){
        BooleanBuilder builder = new BooleanBuilder();

        String airportCode = request.getAirportCode();
        String airportName = request.getAirportName();
        String airportCity = request.getAirportCity();
        String airportCountry = request.getAirportCountry();

        String airlineName = request.getAirlineName();
        String iataCode = request.getIataCode();
        String icaoCode = request.getIcaoCode();
        String airlineCountry = request.getAirlineCountry();
        String airlineContactInfo = request.getAirlineContactInfo();
        String aircraftRegNo = request.getAircraftRegNo();

        String businessType = request.getBusinessType();
        String aircraftType = request.getAircraftType();
        String serialNumber = request.getAircraftSerialNumber();
        LocalDateTime manufacturingStartDate = request.getManufacturingStartDate();
        LocalDateTime manufacturingEndDate = request.getManufacturingEndDate();
        Integer aircraftAge = request.getAircraftAge();
        LocalDateTime registrationStartDate = request.getRegistrationStartDate();
        LocalDateTime registrationEndDate = request.getRegistrationEndDate();
        Integer minSeatingCapacity = request.getMinSeatingCapacity() == null ? Integer.MIN_VALUE : request.getMinSeatingCapacity().intValue();
        Integer maxSeatingCapacity = request.getMaxSeatingCapacity() == null ? Integer.MAX_VALUE : request.getMaxSeatingCapacity();
        String maximumTakeoffWeight = request.getMaximumTakeoffWeight();

        String typeOfFlight = request.getTypeOfFlight();
        String flightId = request.getFlightId();
        String masterFlightId = request.getMasterFlightId();
        LocalDateTime scheduleStartDatetime = request.getScheduleStartDatetime();
        LocalDateTime scheduleEndDatetime = request.getScheduleEndDatetime();
        String gateNumber = request.getGateNumber();
        String terminalId = request.getTerminalId();
        String exitNumber = request.getExitNumber();

        if(hasText(airportCode)){
            builder.and(airport.airportCode.containsIgnoreCase(airportCode));
        }

        if(hasText(airportName)){
            builder.and(airport.airportName.containsIgnoreCase(airportName));
        }

        if(hasText(airportCity)){
            builder.and(airport.city.containsIgnoreCase(airportCity));
        }

        if(hasText(airportCountry)){
            builder.and(airport.country.containsIgnoreCase(airportCountry));
        }

        if(hasText(airlineName)){
            builder.and(airline.airlineName.containsIgnoreCase(airlineName));
        }

        if(hasText(iataCode)){
            builder.and(airline.iataCode.containsIgnoreCase(iataCode));
        }
        if(hasText(icaoCode)){
            builder.and(airline.icaoCode.containsIgnoreCase(icaoCode));
        }
        if(hasText(airlineCountry)){
            builder.and(airline.country.containsIgnoreCase(airlineCountry));
        }
        if(hasText(airlineContactInfo)){
            builder.and(airline.contactInfo.containsIgnoreCase(airlineContactInfo));
        }
        if(hasText(airlineContactInfo)){
            builder.and(airline.contactInfo.containsIgnoreCase(airlineContactInfo));
        }
        if(hasText(aircraftRegNo)){
            builder.and(aircraftKor.aircraftRegNo.containsIgnoreCase(aircraftRegNo));
        }
        if(hasText(businessType)){
            builder.and(aircraftKor.businessType.containsIgnoreCase(businessType));
        }
        if(hasText(aircraftType)){
            builder.and(aircraftKor.aircraftType.containsIgnoreCase(aircraftType));
        }
        if(hasText(serialNumber)){
            builder.and(aircraftKor.serialNumber.containsIgnoreCase(serialNumber));
        }

        // 시작일과 종료일이 모두 있는 경우
        if (manufacturingStartDate != null && manufacturingEndDate != null) {
            builder.and(aircraftKor.manufacturingDate.between(manufacturingStartDate, manufacturingEndDate));
        }
        // 시작일만 있는 경우
        else if (manufacturingStartDate != null) {
            builder.and(aircraftKor.manufacturingDate.goe(manufacturingStartDate));
        }
        // 종료일만 있는 경우
        else if (manufacturingEndDate != null) {
            builder.and(aircraftKor.manufacturingDate.loe(manufacturingEndDate));
        }

        if(aircraftAge != null){
            builder.and(aircraftKor.age.eq(aircraftAge));
        }

        // 시작일과 종료일이 모두 있는 경우
        if (registrationStartDate != null && registrationEndDate != null) {
            builder.and(aircraftKor.registrationDate.between(registrationStartDate, registrationEndDate));
        }
        // 시작일만 있는 경우
        else if (registrationStartDate != null) {
            builder.and(aircraftKor.registrationDate.goe(registrationStartDate));
        }
        // 종료일만 있는 경우
        else if (registrationEndDate != null) {
            builder.and(aircraftKor.registrationDate.loe(registrationEndDate));
        }

        // 최대 좌석수
        if (minSeatingCapacity != null && maxSeatingCapacity != null) {
            builder.and(aircraftKor.seatingCapacity.between(minSeatingCapacity, maxSeatingCapacity));
        }
        else if (minSeatingCapacity != null) {
            builder.and(aircraftKor.seatingCapacity.goe(minSeatingCapacity));
        }
        else if (maxSeatingCapacity != null) {
            builder.and(aircraftKor.seatingCapacity.loe(maxSeatingCapacity));
        }

        if(maximumTakeoffWeight != null){
            builder.and(aircraftKor.maximumTakeoffWeight.goe(maximumTakeoffWeight));
        }

        if(hasText(typeOfFlight)){
            builder.and(arrival.typeOfFlight.eq(typeOfFlight));
        }
        if(hasText(flightId)){
            builder.and(arrival.flightId.containsIgnoreCase(flightId));
        }
        if(hasText(masterFlightId)){
            builder.and(arrival.masterFlightId.containsIgnoreCase(masterFlightId));
        }

        // 시작일과 종료일이 모두 있는 경우
        if (scheduleStartDatetime != null && scheduleEndDatetime != null) {
            builder.and(arrival.scheduleDatetime.between(scheduleStartDatetime, scheduleEndDatetime));
        }
        // 시작일만 있는 경우
        else if (scheduleStartDatetime != null) {
            builder.and(arrival.scheduleDatetime.goe(scheduleStartDatetime));
        }
        // 종료일만 있는 경우
        else if (scheduleEndDatetime != null) {
            builder.and(arrival.scheduleDatetime.loe(scheduleEndDatetime));
        }

        if(hasText(gateNumber)){
            builder.and(arrival.gateNumber.eq(gateNumber));
        }
        if(hasText(terminalId)){
            builder.and(arrival.terminalId.containsIgnoreCase(terminalId));
        }

        return builder;
    }
}
