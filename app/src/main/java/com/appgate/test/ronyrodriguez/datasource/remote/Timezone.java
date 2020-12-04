package com.appgate.test.ronyrodriguez.datasource.remote;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timezone {

    @SerializedName("sunrise")
    @Expose
    private String sunrise;
    @SerializedName("lng")
    @Expose
    private double lng;
    @SerializedName("countryCode")
    @Expose
    private String countryCode;
    @SerializedName("gmtOffset")
    @Expose
    private int gmtOffset;
    @SerializedName("rawOffset")
    @Expose
    private int rawOffset;
    @SerializedName("sunset")
    @Expose
    private String sunset;
    @SerializedName("timezoneId")
    @Expose
    private String timezoneId;
    @SerializedName("dstOffset")
    @Expose
    private int dstOffset;
    @SerializedName("countryName")
    @Expose
    private String countryName;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("lat")
    @Expose
    private double lat;

    public String getSunrise() {
        return sunrise;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getGmtOffset() {
        return gmtOffset;
    }

    public void setGmtOffset(int gmtOffset) {
        this.gmtOffset = gmtOffset;
    }

    public int getRawOffset() {
        return rawOffset;
    }

    public void setRawOffset(int rawOffset) {
        this.rawOffset = rawOffset;
    }

    public String getSunset() {
        return sunset;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public String getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(String timezoneId) {
        this.timezoneId = timezoneId;
    }

    public int getDstOffset() {
        return dstOffset;
    }

    public void setDstOffset(int dstOffset) {
        this.dstOffset = dstOffset;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }
}
