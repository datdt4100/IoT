package com.example.iotapp.model;

public class TemperatureData {
    private int id;
    private double value;
    private String date;

    public TemperatureData(double value, String date) {
        this.id = id;
        this.value = value;
        this.date = date;

    }

    @Override
    public String toString() {
        return "TemperatureData{" +
                "id=" + id +
                ", value=" + value +
                ", date='" + date + '\'' +
                '}';
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
