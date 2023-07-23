package com.example.newsfeed.Services;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverterService {

//    public static void main(String[] args) throws ParseException {
//        Date date = new Date();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//
//        System.out.println(dateFormat.format(date));
//        convertDate(dateFormat.format(date));
//    }

    public static String convertDate(String inputDate) {
        // Get the current date and time
        Date currentDate = new Date();

        // Convert the input date string to a Date object
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date inputDateTime;
        try {
            inputDateTime = dateFormat.parse(inputDate);
        } catch (Exception e) {
            System.out.println("Invalid input date format. Please use 'yyyy-MM-dd HH:mm:ss' format.");
            return null;
        }

        // Calculate the time difference
        long timeDifferenceMillis = currentDate.getTime() - inputDateTime.getTime();
        if (timeDifferenceMillis < 0) {
            System.out.println("Input date cannot be in the future.");
            return null;
        }

        // Convert time difference to "ago" string format
        String timeAgo = getTimeAgoString(timeDifferenceMillis);
//        System.out.println(timeAgo);
        return timeAgo;
    }

    private static String getTimeAgoString(long timeDifferenceMillis) {
        long seconds = timeDifferenceMillis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;
        long days = hours / 24;
        long weeks = days / 7;
        long months = days / 30;
        long years = days / 365;

        if (years > 0) {
            return years + (years == 1 ? " year ago" : " years ago");
        } else if (months > 0) {
            return months + (months == 1 ? " month ago" : " months ago");
        } else if (weeks > 0) {
            return weeks + (weeks == 1 ? " week ago" : " weeks ago");
        } else if (days > 0) {
            return days + (days == 1 ? " day ago" : " days ago");
        } else if (hours > 0) {
            return hours + (hours == 1 ? " hour ago" : " hours ago");
        } else if (minutes > 0) {
            return minutes + (minutes == 1 ? " minute ago" : " minutes ago");
        } else {
            return seconds + (seconds == 1 ? " second ago" : " seconds ago");
        }
    }


}
