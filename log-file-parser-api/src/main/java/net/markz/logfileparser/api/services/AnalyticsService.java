package net.markz.logfileparser.api.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.markz.logfileparser.api.dao.AnalyticsDao;
import net.markz.logfileparser.model.AnalyticsDto;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class AnalyticsService {
    private final AnalyticsDao analyticsDao;

    public AnalyticsDto getAnalytics() {
        return analyticsDao.getAnalytics();
    }
}
