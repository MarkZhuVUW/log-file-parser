package net.markz.logfileparser.api.controller;

import net.markz.logfileparser.api.dao.AnalyticsDao;
import net.markz.logfileparser.api.services.AnalyticsService;
import org.junit.jupiter.api.BeforeEach;



class AnalyticsControllerIT {

    private AnalyticsService analyticsService;


    private AnalyticsController analyticsController;

    @BeforeEach
    public void beforeEach() {
        analyticsService = new AnalyticsService(new AnalyticsDao());

        analyticsController = new AnalyticsController(analyticsService);
    }

}