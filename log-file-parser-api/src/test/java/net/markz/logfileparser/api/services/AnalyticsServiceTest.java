package net.markz.logfileparser.api.services;

import net.markz.logfileparser.api.dao.AnalyticsDao;
import org.junit.jupiter.api.BeforeEach;


class AnalyticsServiceTest {

    private AnalyticsService analyticsService;


    @BeforeEach
    public void beforeEach() {
        analyticsService = new AnalyticsService(new AnalyticsDao());
    }

}