package com.bumu.arya.admin.devops.result;

/**
 * @author Allen 2018-02-11
 **/
public class TotalVisitsResult {

    String title;

    Long visits;

    public TotalVisitsResult(String title, Long visits) {
        this.title = title;
        this.visits = visits;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getVisits() {
        return visits;
    }

    public void setVisits(Long visits) {
        this.visits = visits;
    }

    @Override
    public String toString() {
        return "TotalVisitsResult{" +
                "title='" + title + '\'' +
                ", visits=" + visits +
                '}';
    }
}
