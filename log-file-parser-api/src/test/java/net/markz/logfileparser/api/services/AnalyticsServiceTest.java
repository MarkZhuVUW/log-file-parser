package net.markz.logfileparser.api.services;

import net.markz.logfileparser.api.dao.AnalyticsDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AnalyticsServiceTest {

    private AnalyticsService analyticsService;

    @BeforeEach
    public void beforeEach() {
        final var env = mock(Environment.class);
        when(env.getActiveProfiles()).thenReturn(new String[]{ "test" });

        analyticsService = new AnalyticsService(new AnalyticsDao(), env);
    }


    @Test
    void test_happy_path() {
        final var testFileName = "programming-task-example-data.log";
        analyticsService.getAnalytics(testFileName);
        assertTrue(true);
    }
}