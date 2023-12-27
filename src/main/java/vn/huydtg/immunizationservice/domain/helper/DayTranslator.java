package vn.huydtg.immunizationservice.domain.helper;

import java.util.HashMap;
import java.util.Map;

public class DayTranslator {
    private static final Map<String, String> dayTranslations = new HashMap<>();
    private static final Map<String, String> reverseDayTranslations = new HashMap<>();
    static {
        dayTranslations.put("Mon", "MONDAY");
        dayTranslations.put("Tue", "TUESDAY");
        dayTranslations.put("Wed", "WEDNESDAY");
        dayTranslations.put("Thu", "THURSDAY");
        dayTranslations.put("Fri", "FRIDAY");
        dayTranslations.put("Sat", "SATURDAY");
        dayTranslations.put("Sun", "SUNDAY");

        for (Map.Entry<String, String> entry : dayTranslations.entrySet()) {
            reverseDayTranslations.put(entry.getValue(), entry.getKey());
        }
    }

    public String translate(String dayAbbreviation) {
        return dayTranslations.getOrDefault(dayAbbreviation, dayAbbreviation);
    }

    public String reverseTranslate(String dayFull) {
        return reverseDayTranslations.getOrDefault(dayFull, dayFull);
    }
}
