package com.example.comp2005_api;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class AdmissionService
{
    private final ApiHelper apiHelper;
    // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");

    public AdmissionService(ApiHelper apiHelper) {
        this.apiHelper = apiHelper;
    }

    // F3: Identify which month has the most admissions.
    public String getMonthWithMostAdmissions() {
        try {
            Admission[] admissions = apiHelper.getAllAdmissions();
            Map<String, Integer> admissionsByMonth = new HashMap<>(); // "YYYY-MM"

            for (Admission admission : admissions) {
                String admissionDateStr = admission.getAdmissionDate();
                if (admissionDateStr == null) { continue; }

                LocalDateTime admissionDate;
                try {
                    admissionDate = LocalDateTime.parse(admissionDateStr);
                } catch (Exception e) {
                    // Failed to parse date, skip to next
                    continue;
                }

                String yearMonthKey = admissionDate.format(formatter);

                int count = admissionsByMonth.getOrDefault(yearMonthKey, 0);
                admissionsByMonth.put(yearMonthKey, count+1);
            }

            int maxCount = -1;
            String mostCommonMonth = null;
            // https://stackoverflow.com/questions/46898/how-do-i-efficiently-iterate-over-each-entry-in-a-java-map
            for (Map.Entry<String, Integer> entry : admissionsByMonth.entrySet()) {
                if (entry.getValue() > maxCount) {
                    maxCount = entry.getValue();
                    mostCommonMonth = entry.getKey();
                }
            }

            return mostCommonMonth != null ? mostCommonMonth : "No admissions found.";

        } catch (Exception e) {
            return "No admissions found.";
        }
    }
}
