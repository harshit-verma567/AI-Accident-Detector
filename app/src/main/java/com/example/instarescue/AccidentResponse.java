package com.example.instarescue;

import com.google.gson.annotations.SerializedName;

public class AccidentResponse {

    @SerializedName("detect")
    private boolean accidentDetected;

    @SerializedName("lat")
    private double latitude;

    @SerializedName("long")
    private double longitude;

    @SerializedName("img")
    private String imageBase64;

    public boolean isAccidentDetected() {
        return accidentDetected;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getImageBase64() {
        return imageBase64;
    }
}
