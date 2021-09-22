package io.javabrains.Covid.Tracker.models;

public class CovidStats {
    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    @Override
    public String toString() {
        return "CovidStats{" +
                "states='" + states + '\'' +
                ", country='" + country + '\'' +
                ", totalCases=" + totalCases +
                '}';
    }

    private String states;
    private String country;
    private int totalCases;

    public int getNewCases() {
        return newCases;
    }

    public void setNewCases(int newCases) {
        this.newCases = newCases;
    }

    private int newCases;

}
