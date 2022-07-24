package net.markz.logfileparser.api.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import net.markz.logfileparser.api.AnalyticsApiDelegate;
import net.markz.logfileparser.api.services.AnalyticsService;
import net.markz.logfileparser.api.util.Utils;
import net.markz.logfileparser.model.AnalyticsDto;
import net.markz.logfileparser.model.GetAnalyticsResponse;
import org.apache.commons.collections4.Get;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Builder
@Component
@Slf4j
public class AnalyticsController implements AnalyticsApiDelegate {

    private final AnalyticsService analyticsService;

    @Override
    public ResponseEntity<GetAnalyticsResponse> getAnalytics(final String logFileName) {
        return Utils.handleException(
                () -> new GetAnalyticsResponse().data(analyticsService.getAnalytics(logFileName)),
                new GetAnalyticsResponse().data(new AnalyticsDto())
        );
    }



}
