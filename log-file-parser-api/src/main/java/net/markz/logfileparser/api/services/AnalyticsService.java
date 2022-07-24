package net.markz.logfileparser.api.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.markz.logfileparser.api.dao.AnalyticsDao;
import net.markz.logfileparser.model.AnalyticsDto;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@AllArgsConstructor
public class AnalyticsService {
    private final AnalyticsDao analyticsDao;
    private final Environment env;

    public AnalyticsDto getAnalytics(final String logFileName) {


        final String filePath = Arrays.stream(env.getActiveProfiles()).toList().contains("local") ?
                "/home/files/" + logFileName :
                "../local-development/files/" + logFileName;

        return analyticsDao.getAnalytics(filePath);
    }
}
