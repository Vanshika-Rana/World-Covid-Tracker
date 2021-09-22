package io.javabrains.Covid.Tracker.services;

import io.javabrains.Covid.Tracker.models.CovidStats;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class CovidData {
    private static String COVID_DATA_URL = "https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";


    private List<CovidStats> allStats = new ArrayList<>();

    public List<CovidStats> getAllStats() {
        return allStats;
    }

    @PostConstruct
    @Scheduled(cron = "* 1 * * * *")
    public void fetchCovidData() throws IOException, InterruptedException {
        List<CovidStats> newStats = new ArrayList<>();
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(COVID_DATA_URL))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader = new StringReader(response.body());
        Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);
        for (CSVRecord record : records) {
            CovidStats location = new CovidStats();
            location.setStates(record.get("Province/State"));
            location.setCountry(record.get("Country/Region"));
            int latestCases = Integer.parseInt(record.get(record.size()-1));
            int prevDayCases = Integer.parseInt(record.get(record.size()-2));
            location.setTotalCases(latestCases);
            location.setNewCases(latestCases-prevDayCases);
            newStats.add(location);
        }
        this.allStats=newStats;
    }
}
