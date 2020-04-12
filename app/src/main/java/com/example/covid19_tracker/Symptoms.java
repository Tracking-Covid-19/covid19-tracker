package com.example.covid19_tracker;
import java.util.GregorianCalendar;

public class Symptoms {

    private static String name;
    private int intensity;
    private static GregorianCalendar date;
    private String additionalDetails;

    Symptoms (String name, String additionalDetails, int intensity) {
        this.intensity = intensity;
        if (intensity > 10 || intensity < 0){
            throw new IllegalArgumentException("Please enter an intensity between 0 and 10.");
        }
        this.name = name;
        this.additionalDetails = additionalDetails;
    }

    Symptoms (String name, int intensity) {
        this.intensity = intensity;
        this.name = name;
    }
    public static GregorianCalendar getDate() {
        GregorianCalendar symptomDate = date;
        return symptomDate;
    }
    public static String getName() {
        return name;
    }
}
