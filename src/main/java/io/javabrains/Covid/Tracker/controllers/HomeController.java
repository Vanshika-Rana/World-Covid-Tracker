package io.javabrains.Covid.Tracker.controllers;

import io.javabrains.Covid.Tracker.models.CovidStats;
import io.javabrains.Covid.Tracker.services.CovidData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    CovidData covidData;

    @GetMapping("/")
    public String home(Model model){
        List<CovidStats> allStats = covidData.getAllStats();
        int totalReportedCases = allStats.stream().mapToInt(stat-> stat.getTotalCases()).sum();
        model.addAttribute("location", allStats);
        model.addAttribute("totalReportedCases",totalReportedCases);
        return "home";
    }
}
