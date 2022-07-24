package net.markz.logfileparser.api.controller;

import net.markz.logfileparser.api.dao.AnalyticsDao;
import net.markz.logfileparser.api.services.AnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AnalyticsControllerIT {

    private AnalyticsService analyticsService;


    private AnalyticsController analyticsController;

    @Autowired


    @BeforeEach
    public void beforeEach() {
        final var env = mock(Environment.class);
        when(env.getActiveProfiles()).thenReturn(new String[]{ "test" });

        analyticsService = new AnalyticsService(new AnalyticsDao(), env);

        analyticsController = new AnalyticsController(analyticsService);
    }

    @Test
    void parse_exampleLog_correct() {
        // given
        final var testFileName = "programming-task-example-data.log";

        // when
        final var response = analyticsController.getAnalytics(testFileName);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final var top3MostActiveIpAddresses = response.getBody().getData().getTop3MostActiveIpAddresses();
        final var top3MostVisitedUrls = response.getBody().getData().getTop3MostVisitedUrls();

        assertEquals(11, response.getBody().getData().getNumUniqueIpAddresses());
        assertEquals(3, top3MostActiveIpAddresses.size());
        assertEquals("[168.41.191.40, 177.71.128.21, 50.112.00.11]", top3MostActiveIpAddresses.toString());

        assertEquals(3, top3MostVisitedUrls.size());
        assertEquals("[\"GET /docs/manage-websites/ HTTP/1.1\", \"GET / HTTP/1.1\", \"GET /asset.css HTTP/1.1\"]", top3MostVisitedUrls.toString());

    }

    @Test
    void parse_exampleLog_10_times_in_size_correct() {
        // given
        final var testFileName = "example-log-times-10.log";

        // when
        final var response = analyticsController.getAnalytics(testFileName);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());

        final var top3MostActiveIpAddresses = response.getBody().getData().getTop3MostActiveIpAddresses();
        final var top3MostVisitedUrls = response.getBody().getData().getTop3MostVisitedUrls();

        assertEquals(11, response.getBody().getData().getNumUniqueIpAddresses());
        assertEquals(3, top3MostActiveIpAddresses.size());
        assertEquals("[168.41.191.40, 177.71.128.21, 50.112.00.11]", top3MostActiveIpAddresses.toString());

        assertEquals(3, top3MostVisitedUrls.size());
        assertEquals("[\"GET /docs/manage-websites/ HTTP/1.1\", \"GET / HTTP/1.1\", \"GET /asset.css HTTP/1.1\"]", top3MostVisitedUrls.toString());

    }

    @Test
    void parse_exampleLog_100_times_in_size_correct() {
        final var testFileName = "example-log-times-100.log";

        final var response = analyticsController.getAnalytics(testFileName);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        final var top3MostActiveIpAddresses = response.getBody().getData().getTop3MostActiveIpAddresses();
        final var top3MostVisitedUrls = response.getBody().getData().getTop3MostVisitedUrls();

        assertEquals(11, response.getBody().getData().getNumUniqueIpAddresses());
        assertEquals(3, top3MostActiveIpAddresses.size());
        assertEquals("[168.41.191.40, 177.71.128.21, 50.112.00.11]", top3MostActiveIpAddresses.toString());

        assertEquals(3, top3MostVisitedUrls.size());
        assertEquals("[\"GET /docs/manage-websites/ HTTP/1.1\", \"GET / HTTP/1.1\", \"GET /asset.css HTTP/1.1\"]", top3MostVisitedUrls.toString());

    }

    @Test
    void parse_exampleLog_1000_times_in_size_correct() {
        final var testFileName = "example-log-times-1000.log";

        final var response = analyticsController.getAnalytics(testFileName);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        final var top3MostActiveIpAddresses = response.getBody().getData().getTop3MostActiveIpAddresses();
        final var top3MostVisitedUrls = response.getBody().getData().getTop3MostVisitedUrls();

        assertEquals(11, response.getBody().getData().getNumUniqueIpAddresses());
        assertEquals(3, top3MostActiveIpAddresses.size());
        assertEquals("[168.41.191.40, 177.71.128.21, 50.112.00.11]", top3MostActiveIpAddresses.toString());

        assertEquals(3, top3MostVisitedUrls.size());
        assertEquals("[\"GET /docs/manage-websites/ HTTP/1.1\", \"GET / HTTP/1.1\", \"GET /asset.css HTTP/1.1\"]", top3MostVisitedUrls.toString());

    }

    @Test
    void parse_exampleLog_10000_times_in_size_correct() {
        final var testFileName = "example-log-times-10000.log";

        final var response = analyticsController.getAnalytics(testFileName);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        final var top3MostActiveIpAddresses = response.getBody().getData().getTop3MostActiveIpAddresses();
        final var top3MostVisitedUrls = response.getBody().getData().getTop3MostVisitedUrls();

        assertEquals(11, response.getBody().getData().getNumUniqueIpAddresses());
        assertEquals(3, top3MostActiveIpAddresses.size());
        assertEquals("[168.41.191.40, 177.71.128.21, 50.112.00.11]", top3MostActiveIpAddresses.toString());

        assertEquals(3, top3MostVisitedUrls.size());
        assertEquals("[\"GET /docs/manage-websites/ HTTP/1.1\", \"GET / HTTP/1.1\", \"GET /asset.css HTTP/1.1\"]", top3MostVisitedUrls.toString());

    }

    @Test
    void parse_wrongFormat() {
        final var testFileName = "wrong-format.log";

        final var response = analyticsController.getAnalytics(testFileName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody().getData().getTop3MostVisitedUrls());
        assertNull(response.getBody().getData().getTop3MostActiveIpAddresses());
        assertNull(response.getBody().getData().getNumUniqueIpAddresses());

    }

    @Test
    void parse_wrongIpAddressFormat() {
        final var testFileName = "wrong-ip-address-format.log";

        final var response = analyticsController.getAnalytics(testFileName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody().getData().getTop3MostVisitedUrls());
        assertNull(response.getBody().getData().getTop3MostActiveIpAddresses());
        assertNull(response.getBody().getData().getNumUniqueIpAddresses());
    }

    @Test
    void parse_noUrl() {
        final var testFileName = "wrong-url.log";

        final var response = analyticsController.getAnalytics(testFileName);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNull(response.getBody().getData().getTop3MostVisitedUrls());
        assertNull(response.getBody().getData().getTop3MostActiveIpAddresses());
        assertNull(response.getBody().getData().getNumUniqueIpAddresses());
    }
}