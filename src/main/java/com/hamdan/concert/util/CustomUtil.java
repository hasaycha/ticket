package com.hamdan.concert.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class CustomUtil {
    private static final Random RANDOM = new Random();

    private CustomUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateTransactionCode() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("MMddyyyyhhmmssmmm");
        int randomNum = RANDOM.nextInt(100000000);

        return String.format("%s%s%010d", "CT-", dateFormat.format(currentDate), randomNum);
    }
}