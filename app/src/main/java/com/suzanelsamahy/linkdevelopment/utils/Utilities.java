package com.suzanelsamahy.linkdevelopment.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilities {

    public static String parsePublishedDate(String publishedDate) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = dateFormat.parse(publishedDate);

            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
            outputFormat.format(date);
            return outputFormat.format(date);
        } catch (ParseException ex) {
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");
            return outputFormat.format(new Date());
        }
    }

}
