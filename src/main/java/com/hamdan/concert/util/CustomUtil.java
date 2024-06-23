package com.hamdan.concert.util;

import org.apache.logging.log4j.util.Strings;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class CustomUtil {
    private static final Random RANDOM = new Random();

    private CustomUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateConcertCode(String name) {
        if (Strings.isBlank(name)) {
            return null;
        }

        String nameParam = name.toLowerCase().replace(" ", "");
        int randomNum = RANDOM.nextInt(1000);

        return String.format("%s%s%05d", "concert-", nameParam, randomNum);
    }

    public static String generateTransactionCode() {
        LocalDateTime currentDate = LocalDateTime.now();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MMddyyyyHHmmssSSS");
        int randomNum = RANDOM.nextInt(100000000);

        return String.format("%s%s%010d", "CT-", dateFormat.format(currentDate), randomNum);
    }

    public static boolean validateTimeFormat(String time) {
        boolean isValid = false;

        if (Strings.isBlank(time)) {
            return isValid;
        }
        try {
            LocalTime.parse(time);
            isValid = true;
        } catch (Exception e) {
            // ignore
        }

        return isValid;
    }
}