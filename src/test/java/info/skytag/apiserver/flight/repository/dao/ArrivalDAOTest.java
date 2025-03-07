package info.skytag.apiserver.flight.repository.dao;

import info.skytag.apiserver.common.dto.request.SortCriteria;
import info.skytag.apiserver.flight.model.dto.FlightResponse;
import info.skytag.apiserver.flight.model.dto.request.FlightSearchRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArrivalDAOTest {
    @Autowired
    private ArrivalDAO arrivalDAO;

    @Test
    void searchDepartureList() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("flightId")));

        FlightSearchRequest request = new FlightSearchRequest();
        request.setPage(0);
        request.setSize(100);
        request.setSorts(List.of(
                SortCriteria.builder().field("scheduleDatetime").direction("asc").build()
                , SortCriteria.builder().field("flightId").direction("desc").build()
        ));
        Page<FlightResponse> resultPage = arrivalDAO.searchArrivalList(request);
        Assertions.assertNotNull(resultPage, "결과 Page는 null이 아니어야 합니다.");
        Assertions.assertTrue(resultPage.getTotalElements() >= 0, "전체 건수는 0 이상이어야 합니다.");
        Assertions.assertTrue(resultPage.getContent().size() <= pageable.getPageSize(),
                "조회된 항목 개수는 페이지 크기보다 작거나 같아야 합니다.");
    }
}