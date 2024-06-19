package org.books.api.services;

import org.books.api.errors.NotExhaustiveYear;
import org.springframework.stereotype.Service;

import java.text.Normalizer;

@Service
public class UtilsService {
    public boolean isStringEqualIgnoreCase(String value, String value2) {
        String truncatedValue = replaceAccent(value).toLowerCase();
        String truncatedValue2 = replaceAccent(value2).toLowerCase();

        return truncatedValue.trim()
                             .equalsIgnoreCase(truncatedValue2.trim());
    }

    public String replaceAccent(String text) {
        return Normalizer
                .normalize(text, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public boolean isPublicationYearInPastOrPresent(String yearToTest, String currentYear) {
        try {
            int castedYearToTest = Integer.parseInt(yearToTest);
            int castedCurrentYear = Integer.parseInt(currentYear);
            return castedYearToTest <= castedCurrentYear;
        } catch (NumberFormatException e) {
            throw new NotExhaustiveYear(yearToTest);
        }

    }
}
