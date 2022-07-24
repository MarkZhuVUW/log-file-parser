package net.markz.logfileparser.api.dao;

import lombok.extern.slf4j.Slf4j;
import net.markz.logfileparser.api.exception.LogFileParserServiceException;
import net.markz.logfileparser.model.AnalyticsDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Repository
@Slf4j
public class AnalyticsDao {

    private static final List<String> httpMethods = Arrays
            .stream(HttpMethod.values())
            .map(Enum::name)
            .toList();
    /**
     * ip address regex
     */
    private static final Pattern ipAddressPattern = Pattern.compile("(\\d+(?:\\.\\d+){3})");

    /**
     * quoted string regex used for finding urls.
     */
    private static final Pattern quotedStrPattern = Pattern.compile("\"([^\"]*?)\"");


    public AnalyticsDto getAnalytics(final String logFilePath) {

        log.info("Start processing log file path={}", logFilePath);

        final var ipAddressOccurrences = new HashMap<String, Long>();
        final var urlOccurrences = new HashMap<String, Long>();

        try (
                final var inputStream = new FileInputStream(logFilePath);
                final var scanner = new Scanner(inputStream, StandardCharsets.UTF_8);
        ) {
            while (scanner.hasNextLine()) {
                final var line = scanner.nextLine();

                // process ip address occurrences
                final var ipAddress = getIpAddressInLine(line);
                ipAddressOccurrences.put(
                        ipAddress,
                        ipAddressOccurrences.getOrDefault(ipAddress, 0L) + 1
                );


                // process url occurrences
                final var url = getUrlInLine(line);
                urlOccurrences.put(
                        url,
                        urlOccurrences.getOrDefault(url, 0L) + 1
                );


            }
            // note that Scanner suppresses exceptions
            if (scanner.ioException() != null) {
                throw scanner.ioException();
            }

            log.info("ip addresse occurrences map={}", ipAddressOccurrences);
            log.info("url occurrences map={}", urlOccurrences);

            // get top 3 most active ip addresses
            final var top3IpAddresses = getTop3SortedByValue(ipAddressOccurrences);
            // get top 3 most visited urls
            final var top3MostVisitedUrls = getTop3SortedByValue(urlOccurrences);
            
            return new AnalyticsDto()
                    .numUniqueIpAddresses(ipAddressOccurrences.size())
                    .top3MostActiveIpAddresses(top3IpAddresses)
                    .top3MostVisitedUrls(top3MostVisitedUrls);
        } catch (IOException e) {
            throw new LogFileParserServiceException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    private List<String> getTop3SortedByValue(Map<String, Long> occurrences) {
        return occurrences
                .entrySet()
                .stream()
                .sorted((entry1, entry2) -> {
                    if(entry1.getValue().equals(entry2.getValue())) {
                        // sort by lexicographical ordering of urls if occurrences are equal
                        return entry1.getKey().compareTo(entry2.getKey());
                    }
                    return Long.compare(entry2.getValue(), entry1.getValue());
                })
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();

    }
    private String getUrlInLine(final String line) {
        final Matcher matcher = quotedStrPattern.matcher(line);
        final var badRequest = new LogFileParserServiceException(HttpStatus.BAD_REQUEST, "line=" + line + "does not have a url or its format is incorrect");

        if(!matcher.find()) {
            throw badRequest;
        }


        for (int i = 0; i < matcher.groupCount(); i++) {
            
            final var httpMethod = matcher.group(i).replace("\"", "").split(" ")[0];
            if(httpMethods.contains(httpMethod)) {
                return matcher.group(i);
            }
        }
        
        // we have not found any matching url
        throw badRequest;
    }

    private String getIpAddressInLine(final String line) {
        final Matcher matcher = ipAddressPattern.matcher(line);

        if(!matcher.find() || matcher.groupCount() > 1) {
            throw new LogFileParserServiceException(HttpStatus.BAD_REQUEST, "line=" + line + "does not have an ip address or its format is incorrect");
        }
        
        return matcher.group(1);
    }
}
