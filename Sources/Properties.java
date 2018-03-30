
package com.example.smith.appathon.Sources;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Properties {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("mag")
    @Expose
    private Double mag;
    @SerializedName("time")
    @Expose
    private Integer time;
    @SerializedName("felt")
    @Expose
    private Object felt;
    @SerializedName("tsunami")
    @Expose
    private Integer tsunami;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getMag() {
        return mag;
    }

    public void setMag(Double mag) {
        this.mag = mag;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Object getFelt() {
        return felt;
    }

    public void setFelt(Object felt) {
        this.felt = felt;
    }

    public Integer getTsunami() {
        return tsunami;
    }

    public void setTsunami(Integer tsunami) {
        this.tsunami = tsunami;
    }

}
