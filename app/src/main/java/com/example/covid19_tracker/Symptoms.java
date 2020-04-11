package com.example.covid19_tracker;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Symptoms {
    public final int cough;
    public final int sniffles;
    public final int soreThroat;
    public final int muscleAches;
    public final int fever;
    public final int difficultyBreathing;
    String date;

    Symptoms(int cough, int sniffles, int soreThroat, int muscleAches, int fever, int difficultyBreathing){
        if (cough > 10 || cough < 0 || sniffles > 10 || sniffles < 0 || soreThroat > 10 || soreThroat < 0 ||
                muscleAches > 10 || muscleAches < 0 || fever > 10 || fever < 0 || difficultyBreathing > 10 || difficultyBreathing < 0){
            throw new IllegalArgumentException("symptom level out of bound");
        }
        this.cough = cough;
        this.sniffles = sniffles;
        this.soreThroat = soreThroat;
        this.muscleAches = muscleAches;
        this.fever = fever;
        this.difficultyBreathing = difficultyBreathing;
        date = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(new Date());
    }

}
