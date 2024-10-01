package br.dev.jstec.tyginvestiment.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtils {

    public class DateUtil {

        public static LocalDate parseDate(String dateStr) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return LocalDate.parse(dateStr, formatter);
            } catch (DateTimeParseException e) {
                return null;
            }
        }
    }
}
