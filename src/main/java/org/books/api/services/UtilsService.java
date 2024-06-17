package org.books.api.services;

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
}
